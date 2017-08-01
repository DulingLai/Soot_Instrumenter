package com.waze.phone;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.provider.ContactsContract.Profile;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.util.SparseIntArray;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat;
import com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberType;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import com.waze.AppService;
import com.waze.BuildConfig;
import com.waze.Logger;
import com.waze.NativeManager;
import com.waze.autocomplete.Person;
import com.waze.navigate.DriveToNativeManager;
import com.waze.navigate.DriveToNativeManager.IdsMatchListener;
import com.waze.navigate.DriveToNativeManager.PersonMappingListener;
import com.waze.navigate.social.IdsMatchData;
import com.waze.navigate.social.OnCompleteTaskListener;
import com.waze.settings.SettingsNativeManager;
import com.waze.user.PersonBase;
import com.waze.user.PersonGms;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public abstract class AddressBookImpl {
    protected static final int REQUEST_CODE_RESOLVE_ERR = 9000;
    protected static long abStartTime;
    private static boolean mCleanB4Start = false;
    protected static AtomicBoolean mCreated = new AtomicBoolean(false);
    protected static AddressBookImpl mInstance = null;
    protected boolean bIsLocalPersonInit = false;
    protected boolean mAccountExisted = false;
    protected AccountManager mAccountManager;
    protected GoogleApiClient mClient;
    protected String mCountryId = null;
    protected HashMap<String, PersonGms> mHashPersonArray = new HashMap();
    protected ConcurrentHashMap<Integer, PersonGms> mIds = new ConcurrentHashMap();
    protected Person mLocalPersonData = null;
    NativeManager mNm;
    protected HashMap<String, PersonGms> mPersonMap = new HashMap();
    protected volatile boolean mPhonesInit = false;
    protected ReentrantReadWriteLock mPhonesLock = new ReentrantReadWriteLock();
    protected volatile boolean mStopSync = false;
    protected volatile AtomicBoolean mSyncIsRunning = new AtomicBoolean(false);
    protected Thread mSyncT;
    protected boolean mWasSyncExecuted = false;

    abstract void startSyncThread(boolean z, String str);

    protected abstract boolean syncConditionsMet();

    public static AddressBookImpl getInstance() {
        if (!mCreated.compareAndSet(false, true)) {
            return mInstance;
        }
        abStartTime = System.currentTimeMillis();
        SharedPreferences prefs = AppService.getAppContext().getSharedPreferences(SettingsNativeManager.SETTINGS_NOTIFICATION_CONFIG_NAME, 0);
        String implMode = prefs.getString(SettingsNativeManager.ADDRESS_BOOK_IMPL_VALUE, SettingsNativeManager.ADDRESS_BOOK_IMPL_OLD);
        if (GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(AppService.getAppContext()) == 0) {
            Logger.i("AddressBookImpl: Address book will be implemeted via GmsCore");
            if (implMode.equalsIgnoreCase(SettingsNativeManager.ADDRESS_BOOK_IMPL_OLD) || implMode.equalsIgnoreCase(SettingsNativeManager.ADDRESS_BOOK_IMPL_CONTENT_RESOLVER)) {
                mCleanB4Start = true;
                Logger.w("AddressBookImpl: AddressBook implementation was changed from " + implMode + " to GmsCore. Will delete the contacts_hashes table");
            }
            prefs.edit().putString(SettingsNativeManager.ADDRESS_BOOK_IMPL_VALUE, SettingsNativeManager.ADDRESS_BOOK_IMPL_GMS_CORE).apply();
            mInstance = new AddressBookGmsImpl();
        } else {
            Logger.i("AddressBookImpl: Address book will be implemeted via Content resolver");
            if (implMode.equalsIgnoreCase(SettingsNativeManager.ADDRESS_BOOK_IMPL_OLD) || implMode.equalsIgnoreCase(SettingsNativeManager.ADDRESS_BOOK_IMPL_GMS_CORE)) {
                mCleanB4Start = true;
                Logger.w("AddressBookImpl: AddressBook implementation was changed from " + implMode + " to Content Resolver. Will delete the contacts_hashes table");
            }
            prefs.edit().putString(SettingsNativeManager.ADDRESS_BOOK_IMPL_VALUE, SettingsNativeManager.ADDRESS_BOOK_IMPL_CONTENT_RESOLVER).apply();
            mInstance = new AddressBookContractImpl();
        }
        prefs.edit().commit();
        mInstance.init();
        mInstance.start();
        return mInstance;
    }

    public void init() {
        Logger.d("AddressBookImpl: Init");
        this.mPersonMap.clear();
        this.mIds.clear();
        this.mHashPersonArray.clear();
        this.mSyncIsRunning.set(false);
        this.mNm = NativeManager.getInstance();
    }

    public void start() {
        Logger.d("AddressBookImpl: Start");
        this.mAccountManager = (AccountManager) AppService.getAppContext().getSystemService("account");
        Account[] maccounts = this.mAccountManager.getAccountsByType(BuildConfig.APPLICATION_ID);
        if (maccounts.length > 0) {
            this.mAccountExisted = true;
            Logger.w("AddressBookImpl: Account waze.com exists and will be removed with the data");
            try {
                if (((Boolean) this.mAccountManager.removeAccount(maccounts[0], null, null).getResult()).booleanValue()) {
                    Logger.i("AddressBookImpl: Account waze.com removed successfully");
                } else {
                    Logger.e("AddressBookImpl: Failed removing account waze.com");
                }
                deleteAllContactsDataInDB();
            } catch (Exception e) {
                Logger.e("AddressBookImpl: Failed removing account waze.com: " + e.getMessage());
            }
        } else {
            this.mAccountExisted = false;
            Logger.d("AddressBookImpl: Account waze.com does not exist");
        }
        if (mCleanB4Start) {
            deleteAllContactsDataInDB();
            this.mAccountExisted = true;
        }
    }

    public static void stopSyncThread() {
        if (mInstance != null && mInstance.mSyncT != null) {
            Logger.w("AddressBookImpl: Stopping Sync thread");
            Log.w("WAZE SHUTDOWN", "AddressBookImpl: Stopping Sync thread");
            try {
                mInstance.mSyncT.interrupt();
            } catch (Exception e) {
                Logger.e("An exception occurred while trying to stop AddressBook thread", e);
            }
        }
    }

    private void deleteAllContactsDataInDB() {
        this.mNm.RemoveAllContactsFromDB();
        mCleanB4Start = false;
        Logger.i("AddressBookImpl: All records from CONTACTS_HASHES table were deleted");
    }

    public boolean isConnected() {
        return true;
    }

    public boolean performSync(boolean clearList, String account) {
        if (this.mSyncIsRunning.get()) {
            Logger.i("AddressBookImpl: Sync is already executing");
            return true;
        } else if (ActivityCompat.checkSelfPermission(AppService.getAppContext(), "android.permission.READ_CONTACTS") != 0) {
            Logger.w("AddressBookImpl: Did not receive permission to read contacts yet. Not syncing");
            return false;
        } else if (!syncConditionsMet()) {
            return false;
        } else {
            this.mSyncIsRunning.set(true);
            Logger.d("AddressBookImpl: : performSync");
            startSyncThread(clearList, account);
            return true;
        }
    }

    public void cancelSync() {
        Logger.d("AddressBookImpl: : cancelSync");
        if (this.mSyncIsRunning.get()) {
            this.mStopSync = true;
        }
    }

    protected int[] getIntArray(ArrayList<Integer> ids) {
        int[] ret = new int[ids.size()];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = ((Integer) ids.get(i)).intValue();
        }
        return ret;
    }

    protected void addContactToDb(PersonGms p) {
        this.mNm.AddGmsContactToDB(this.mNm.SHA256(p.getPhone()), (long) p.getID(), p.getGmsId(), p.getLastUpdateContact(), p.getLastUpdateInDB());
    }

    public String GetCountryId() {
        if (this.mCountryId != null) {
            return this.mCountryId;
        }
        this.mCountryId = ((TelephonyManager) AppService.getAppContext().getSystemService("phone")).getSimCountryIso().toUpperCase();
        if (this.mCountryId == null || this.mCountryId.equals("")) {
            this.mCountryId = this.mNm.GetDefaultRegion();
        }
        return this.mCountryId;
    }

    public void SetCountryID(String sCountryID) {
        this.mCountryId = sCountryID;
    }

    public String PhoneFormat(String number, String country) {
        String str = null;
        try {
            PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
            PhoneNumber pn = phoneNumberUtil.parse(number, country);
            if (isValid(pn, phoneNumberUtil)) {
                str = phoneNumberUtil.format(pn, PhoneNumberFormat.E164);
            }
        } catch (NumberParseException e) {
        }
        return str;
    }

    public boolean isValid(PhoneNumber pn, PhoneNumberUtil util) {
        if (this.mNm.ValidateMobileTypeNTV()) {
            PhoneNumberType type = util.getNumberType(pn);
            if (!(type == PhoneNumberType.FIXED_LINE_OR_MOBILE || type == PhoneNumberType.MOBILE || type == PhoneNumberType.PERSONAL_NUMBER)) {
                return false;
            }
        }
        return util.isValidNumber(pn);
    }

    public void getLocalProfileData() {
        Context appContext = AppService.getAppContext();
        if (appContext != null) {
            if (ActivityCompat.checkSelfPermission(appContext, "android.permission.READ_CONTACTS") != 0) {
                Logger.w("AddressBookImpl: Did not receive permission to read contacts yet. Not syncing");
                return;
            }
            Cursor c = appContext.getContentResolver().query(Profile.CONTENT_URI, new String[]{"display_name", "photo_uri"}, null, null, null);
            if (c != null) {
                int count = c.getCount();
                boolean b = c.moveToFirst();
                this.bIsLocalPersonInit = true;
                if (b) {
                    int position = c.getPosition();
                    if (count == 1 && position == 0) {
                        String name = c.getString(0);
                        if (name != null && name.matches("[0-9\\-\\(\\)\\s\\+]+")) {
                            name = null;
                        }
                        this.mLocalPersonData = new Person(name, c.getString(1), "-1");
                    }
                }
                c.close();
            }
        }
    }

    public String getLocalFirstName() {
        if (!this.bIsLocalPersonInit) {
            getLocalProfileData();
        }
        if (this.mLocalPersonData == null) {
            return null;
        }
        String name = this.mLocalPersonData.getName();
        if (name == null) {
            return null;
        }
        String[] words = name.split(" ");
        String sName = "";
        if (words.length == 0) {
            return name;
        }
        for (int i = 0; i < words.length - 1; i++) {
            sName = sName + words[i];
        }
        return sName;
    }

    public String getLocalLastName() {
        if (!this.bIsLocalPersonInit) {
            getLocalProfileData();
        }
        if (this.mLocalPersonData == null) {
            return null;
        }
        String name = this.mLocalPersonData.getName();
        if (name == null) {
            return null;
        }
        String[] words = name.split(" ");
        String sName = new String();
        int nLength = words.length;
        if (nLength != 0) {
            return sName + words[nLength - 1];
        }
        return name;
    }

    public ArrayList<String> GetPhonesHash() {
        ArrayList<String> phonesHash = new ArrayList();
        if (this.mPhonesInit) {
            try {
                this.mPhonesLock.readLock().lock();
                for (Entry<String, PersonGms> entry : this.mPersonMap.entrySet()) {
                    String formattedPhone = (String) entry.getKey();
                    if (formattedPhone != null) {
                        phonesHash.add(this.mNm.SHA256(formattedPhone));
                    }
                }
            } finally {
                this.mPhonesLock.readLock().unlock();
            }
        } else {
            Logger.w("AddressBookImpl: GetPhonesHash: Phone list not updated yet");
        }
        return phonesHash;
    }

    public String getLocalImageURI() {
        if (!this.bIsLocalPersonInit) {
            getLocalProfileData();
        }
        if (this.mLocalPersonData == null) {
            return null;
        }
        return this.mLocalPersonData.getImage();
    }

    public String GetNameFromHash(int ID) {
        if (this.mPhonesInit) {
            try {
                this.mPhonesLock.readLock().lock();
                Person p = (Person) this.mIds.get(Integer.valueOf(ID));
                if (p == null) {
                    return "";
                }
                return p.getName();
            } finally {
                this.mPhonesLock.readLock().unlock();
            }
        } else {
            Logger.w("AddressBookImpl: GetNameFromHash: Phone list not updated yet");
            return "";
        }
    }

    public Person GetPersonFromID(int ID) {
        if (this.mPhonesInit) {
            try {
                this.mPhonesLock.readLock().lock();
                Person p = (Person) this.mIds.get(Integer.valueOf(ID));
                if (p == null) {
                    return null;
                }
                return p;
            } finally {
                this.mPhonesLock.readLock().unlock();
            }
        } else {
            Logger.w("AddressBookImpl: GetPersonFromID: Phone list not updated yet");
            return null;
        }
    }

    public ArrayList<Person> GetPersonArrayFromAddressBook() {
        if (this.mPhonesInit) {
            try {
                this.mPhonesLock.readLock().lock();
                ArrayList<Person> arrayList;
                if (this.mPersonMap == null) {
                    arrayList = new ArrayList();
                    return arrayList;
                }
                arrayList = new ArrayList(this.mPersonMap.values());
                this.mPhonesLock.readLock().unlock();
                return arrayList;
            } finally {
                this.mPhonesLock.readLock().unlock();
            }
        } else {
            Logger.w("AddressBookImpl: GetPersonArrayFromAddressBook: Phone list not updated yet");
            return new ArrayList();
        }
    }

    public void GetPersonArrayWithMapping(final PersonMappingListener pml) {
        if (this.mPhonesInit) {
            final ArrayList<PersonBase> mappedPersonArray = new ArrayList();
            final SparseIntArray PersonIdMatch = new SparseIntArray();
            DriveToNativeManager.getInstance().getIdsMatchData(new IdsMatchListener() {
                public void onComplete(IdsMatchData data) {
                    Logger.d("AddressBookImpl: GetPersonArrayWithMapping:onComplete");
                    if (data != null) {
                        for (int i = 0; i < data.UIDs.length; i++) {
                            PersonIdMatch.put(data.ContactIds[i], data.UIDs[i]);
                        }
                    }
                    AddressBookImpl.this.mPhonesLock.writeLock().lock();
                    if (!AddressBookImpl.this.mPersonMap.isEmpty()) {
                        for (Person p : AddressBookImpl.this.mPersonMap.values()) {
                            Person dup = new Person(p);
                            int uid = PersonIdMatch.get(p.getID());
                            if (uid != 0) {
                                dup.setIsOnWaze(true);
                                dup.setID(uid);
                            }
                            mappedPersonArray.add(dup);
                        }
                    }
                    AddressBookImpl.this.mPhonesLock.writeLock().unlock();
                    Logger.d("AddressBookImpl: GetPersonArrayWithMapping:onComplete total: " + mappedPersonArray.size());
                    pml.onComplete(mappedPersonArray);
                }
            });
            return;
        }
        Logger.w("AddressBookImpl: GetPersonArrayWithMapping: Phone list not updated yet");
        pml.onComplete(null);
    }

    public void fillMapBetweenContactIdToUid(final SparseIntArray personIdMatch, final OnCompleteTaskListener onCompleteTaskListener) {
        DriveToNativeManager.getInstance().getIdsMatchData(new IdsMatchListener() {
            public void onComplete(IdsMatchData data) {
                if (data != null) {
                    for (int i = 0; i < data.UIDs.length; i++) {
                        personIdMatch.put(data.ContactIds[i], data.UIDs[i]);
                    }
                }
                if (onCompleteTaskListener != null) {
                    onCompleteTaskListener.onCompleted();
                }
            }
        });
    }

    public boolean isAddressBookInitialized() {
        return this.mPhonesInit;
    }

    public boolean wasSyncExecuted() {
        return this.mWasSyncExecuted;
    }
}
