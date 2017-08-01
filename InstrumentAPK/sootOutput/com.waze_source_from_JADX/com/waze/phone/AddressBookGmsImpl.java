package com.waze.phone;

import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.IntentSender.SendIntentException;
import android.os.Bundle;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient.Builder;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.people.Graph.LoadOwnersOptions;
import com.google.android.gms.people.Graph.LoadOwnersResult;
import com.google.android.gms.people.Graph.LoadPhoneNumbersResult;
import com.google.android.gms.people.Notifications.OnDataChanged;
import com.google.android.gms.people.People;
import com.google.android.gms.people.People.PeopleOptions1p;
import com.google.android.gms.people.model.OwnerBuffer;
import com.google.android.gms.people.model.PhoneNumberEntry;
import com.waze.AppService;
import com.waze.Logger;
import com.waze.NativeManager;
import com.waze.NativeManager.AllIdsFromDBListener;
import com.waze.navigate.social.GmsWazeIdsMatchData;
import com.waze.user.PersonGms;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;

public class AddressBookGmsImpl extends AddressBookImpl implements OnDataChanged, ConnectionCallbacks, OnConnectionFailedListener, OnCancelListener {
    private ArrayList<Long> mContactUpdateTimeArray;
    private ArrayList<Integer> mContactWazeIdsArray;
    private HashMap<String, Integer> mGmsToInd = new HashMap();
    private AtomicInteger mLastContactId = new AtomicInteger();
    protected OwnerBuffer mOwnerBuffer = null;
    protected volatile boolean mOwnersLoaded = false;

    class C22351 implements AllIdsFromDBListener {
        C22351() {
        }

        public void onComplete(GmsWazeIdsMatchData data) {
            if (data == null || data.gmsIds.length <= 0) {
                AddressBookGmsImpl.this.mLastContactId.set(0);
                AddressBookGmsImpl.this.mContactWazeIdsArray = new ArrayList();
                AddressBookGmsImpl.this.mContactUpdateTimeArray = new ArrayList();
            } else {
                AddressBookGmsImpl.this.mContactWazeIdsArray = new ArrayList(data.gmsIds.length);
                AddressBookGmsImpl.this.mContactUpdateTimeArray = new ArrayList(data.gmsIds.length);
                for (int i = 0; i < data.gmsIds.length; i++) {
                    AddressBookGmsImpl.this.mGmsToInd.put(data.gmsIds[i], Integer.valueOf(i));
                    AddressBookGmsImpl.this.mContactWazeIdsArray.add(i, Integer.valueOf(data.wazeIds[i]));
                    AddressBookGmsImpl.this.mContactUpdateTimeArray.add(i, Long.valueOf(data.updateDates[i]));
                }
                AddressBookGmsImpl.this.mLastContactId.set(data.wazeIds[0]);
            }
            Logger.i("AddressBookGmsImpl: Total of " + AddressBookGmsImpl.this.mContactUpdateTimeArray.size() + " contacts in DB. Last waze id set to " + AddressBookGmsImpl.this.mLastContactId.get());
            AddressBookGmsImpl.this.completeStart();
        }
    }

    class C22362 implements ResultCallback<LoadOwnersResult> {
        C22362() {
        }

        public void onResult(LoadOwnersResult result) {
            AddressBookGmsImpl.this.onOwnersLoaded(result.getStatus(), result.getOwners());
        }
    }

    private class GmsSyncThread extends Thread {
        private String mAccountName = null;
        private boolean mClearList = true;
        ArrayList<PhoneNumberEntry> mPhoneNumberEntries = new ArrayList();

        public GmsSyncThread(boolean clearList, String account) {
            this.mClearList = clearList;
            this.mAccountName = account;
        }

        public void run() {
            Logger.d("AddressBookGmsImpl: : performSync thread");
            if (AddressBookGmsImpl.this.mStopSync) {
                Logger.i("AddressBookGmsImpl: Stop sync requested");
                return;
            }
            Logger.d("AddressBookGmsImpl: : startLoading");
            if (AddressBookGmsImpl.this.isConnected()) {
                Logger.d("AddressBook: Sync started after " + (System.currentTimeMillis() - AddressBookImpl.abStartTime) + " ms");
                ArrayList<String> accountNames = new ArrayList();
                try {
                    AddressBookGmsImpl.this.mPhonesLock.readLock().lock();
                    if (this.mAccountName == null && AddressBookGmsImpl.this.mOwnerBuffer == null) {
                        Logger.e("AddressBookGmsImpl: sync: No account specified and no Owners found");
                        return;
                    }
                    if (this.mAccountName != null) {
                        Logger.d("AddressBookGmsImpl: sync: Retrieving phone numbers for account ");
                        accountNames.add(this.mAccountName);
                    } else {
                        Logger.d("AddressBookGmsImpl: Retrieving phone numbers for all accounts");
                        for (int i = 0; i < AddressBookGmsImpl.this.mOwnerBuffer.getCount(); i++) {
                            accountNames.add(AddressBookGmsImpl.this.mOwnerBuffer.get(i).getAccountName());
                            Logger.d("AddressBookGmsImpl: sync: added account ");
                        }
                    }
                    AddressBookGmsImpl.this.mPhonesLock.readLock().unlock();
                    try {
                        AddressBookGmsImpl.this.mPhonesLock.writeLock().lock();
                        if (this.mClearList) {
                            Logger.d("AddressBookGmsImpl: Clearing phone numbers for all accounts");
                            AddressBookGmsImpl.this.mPhonesInit = false;
                            AddressBookGmsImpl.this.mPersonMap.clear();
                            AddressBookGmsImpl.this.mIds.clear();
                            AddressBookGmsImpl.this.mHashPersonArray.clear();
                        }
                        AddressBookGmsImpl.this.mPhonesLock.writeLock().unlock();
                        long updateTime = new Date().getTime() / 1000;
                        retrieveAllPhones(accountNames, updateTime);
                        Logger.d("AddressBookGmsImpl: Phone address book is initialised");
                        AddressBookGmsImpl.this.mPhonesInit = true;
                        Logger.i("AddressBookGmsImpl: Performing sync on existing data");
                        boolean bChanged = updateContactsInDB(updateTime);
                        AddressBookGmsImpl.this.mWasSyncExecuted = true;
                        if (AddressBookGmsImpl.this.mStopSync) {
                            Logger.i("AddressBookGmsImpl: Stop sync requested");
                            return;
                        }
                        if (bChanged && (NativeManager.getInstance().IsAccessToContactsEnableNTV() || NativeManager.bToUploadContacts)) {
                            NativeManager.getInstance().ContactUpload();
                        }
                        if (this.mPhoneNumberEntries.size() > 0) {
                            loadPics();
                        }
                        AddressBookGmsImpl.this.mSyncIsRunning.set(false);
                        Logger.d("AddressBook: Sync ended after " + (System.currentTimeMillis() - AddressBookImpl.abStartTime) + " ms");
                        Logger.i("AddressBookGmsImpl: Sync is no longer running");
                        AddressBookGmsImpl.this.mStopSync = false;
                    } catch (Throwable th) {
                        AddressBookGmsImpl.this.mPhonesLock.writeLock().unlock();
                    }
                } finally {
                    AddressBookGmsImpl.this.mPhonesLock.readLock().unlock();
                }
            } else {
                Logger.e("AddressBookGmsImpl: sync: Client not connected yet");
            }
        }

        private void retrieveAllPhones(ArrayList<String> accountNames, long updateTime) {
            Logger.d("AddressBook: phone retrieval started after " + (System.currentTimeMillis() - AddressBookImpl.abStartTime) + " ms");
            Iterator it = accountNames.iterator();
            while (it.hasNext()) {
                String name = (String) it.next();
                long loadStartTime = System.currentTimeMillis();
                LoadPhoneNumbersResult phoneNumbersResult = (LoadPhoneNumbersResult) People.GraphApi.loadPhoneNumbers(AddressBookGmsImpl.this.mClient, name, null).await();
                if (phoneNumbersResult.getStatus().isSuccess()) {
                    Logger.d("AddressBookGmsImpl: Loaded " + phoneNumbersResult.getPhoneNumbers().getCount() + " records in " + (System.currentTimeMillis() - loadStartTime) + " ms for account ");
                    String account = "";
                    prepareGmsPerson(updateTime, phoneNumbersResult);
                } else {
                    Logger.e("AddressBookGmsImpl: Failed getting result for account, reason: " + phoneNumbersResult.getStatus().getStatusMessage() + "; code: " + phoneNumbersResult.getStatus().getStatusCode());
                }
            }
            Logger.d("AddressBook: phone retrieval ended after " + (System.currentTimeMillis() - AddressBookImpl.abStartTime) + " ms");
        }

        private void prepareGmsPerson(long updateTime, LoadPhoneNumbersResult phoneNumbersResult) {
            try {
                Logger.d("AddressBook: prepareGmsPerson started after " + (System.currentTimeMillis() - AddressBookImpl.abStartTime) + " ms");
                this.mPhoneNumberEntries.clear();
                Iterator it = phoneNumbersResult.getPhoneNumbers().iterator();
                while (it.hasNext()) {
                    PhoneNumberEntry entry = (PhoneNumberEntry) it.next();
                    String contactNumber = entry.getPhoneNumber();
                    if (contactNumber == null) {
                        contactNumber = "";
                    }
                    String formattedPhone = AddressBookGmsImpl.this.PhoneFormat(contactNumber, AddressBookGmsImpl.this.GetCountryId());
                    if (formattedPhone != null) {
                        int contactWazeId;
                        String contactName = entry.getName();
                        if (!(contactName == null || contactName.isEmpty())) {
                            contactName = contactName.replaceAll("[\\p{Cc}\\p{Cf}\\p{Co}\\p{Cn}]", "");
                        }
                        String gmsId = entry.getFocusContactId();
                        Long updateTimeContact = entry.getLastUpdateTime();
                        if (contactName == null) {
                            contactName = "";
                        }
                        if (gmsId == null) {
                            gmsId = "-1";
                        }
                        String contactImageUri = "";
                        if (!AddressBookGmsImpl.this.mGmsToInd.containsKey(gmsId)) {
                            contactWazeId = AddressBookGmsImpl.this.mLastContactId.addAndGet(1);
                        } else if (((Integer) AddressBookGmsImpl.this.mGmsToInd.get(gmsId)).intValue() < AddressBookGmsImpl.this.mContactWazeIdsArray.size()) {
                            contactWazeId = ((Integer) AddressBookGmsImpl.this.mContactWazeIdsArray.get(((Integer) AddressBookGmsImpl.this.mGmsToInd.get(gmsId)).intValue())).intValue();
                        } else {
                            Logger.e("AddressBookGMS: Internal error! GMS id " + gmsId + " not mapped to valid index");
                            AddressBookGmsImpl.this.mGmsToInd.remove(gmsId);
                            contactWazeId = AddressBookGmsImpl.this.mLastContactId.addAndGet(1);
                        }
                        if (AddressBookGmsImpl.this.mHashPersonArray.containsKey(formattedPhone)) {
                            AddressBookGmsImpl.this.mIds.put(Integer.valueOf(contactWazeId), AddressBookGmsImpl.this.mHashPersonArray.get(formattedPhone));
                        } else {
                            PersonGms p = new PersonGms(contactName, formattedPhone, contactImageUri, contactWazeId, updateTimeContact.longValue(), updateTime, gmsId);
                            AddressBookGmsImpl.this.mHashPersonArray.put(formattedPhone, p);
                            AddressBookGmsImpl.this.mIds.put(Integer.valueOf(contactWazeId), p);
                            this.mPhoneNumberEntries.add(entry);
                        }
                    }
                }
                if (AddressBookGmsImpl.this.mHashPersonArray.size() > 0) {
                    AddressBookGmsImpl.this.mPhonesLock.writeLock().lock();
                    AddressBookGmsImpl.this.mPersonMap.putAll(AddressBookGmsImpl.this.mHashPersonArray);
                    AddressBookGmsImpl.this.mPhonesLock.writeLock().unlock();
                }
                if (AddressBookGmsImpl.this.mPhonesLock.isWriteLocked()) {
                    AddressBookGmsImpl.this.mPhonesLock.writeLock().unlock();
                }
                Logger.d("AddressBook: prepareGmsPerson ended after " + (System.currentTimeMillis() - AddressBookImpl.abStartTime) + " ms");
            } catch (Throwable th) {
                if (AddressBookGmsImpl.this.mPhonesLock.isWriteLocked()) {
                    AddressBookGmsImpl.this.mPhonesLock.writeLock().unlock();
                }
            }
        }

        private boolean updateContactsInDB(long updateTime) {
            boolean dataChanged = false;
            ArrayList<Integer> ids = new ArrayList();
            try {
                AddressBookGmsImpl.this.mPhonesLock.readLock().lock();
                for (PersonGms p : AddressBookGmsImpl.this.mPersonMap.values()) {
                    if (AddressBookGmsImpl.this.mGmsToInd.containsKey(p.getGmsId())) {
                        long contactUpdateDateInDB;
                        if (!AddressBookGmsImpl.this.mGmsToInd.containsKey(p.getGmsId()) || ((Integer) AddressBookGmsImpl.this.mGmsToInd.get(p.getGmsId())).intValue() >= AddressBookGmsImpl.this.mContactUpdateTimeArray.size()) {
                            Logger.e("AddressBookGms; Internal error! Mismpatch between waze Id, GMS id: " + p.getGmsId() + " and update time!");
                            contactUpdateDateInDB = 0;
                        } else {
                            int ind = ((Integer) AddressBookGmsImpl.this.mGmsToInd.get(p.getGmsId())).intValue();
                            contactUpdateDateInDB = ((Long) AddressBookGmsImpl.this.mContactUpdateTimeArray.get(ind)).longValue();
                            AddressBookGmsImpl.this.mContactUpdateTimeArray.set(ind, Long.valueOf(updateTime));
                        }
                        if (AddressBookGmsImpl.this.mAccountExisted || contactUpdateDateInDB < p.getLastUpdateContact()) {
                            NativeManager.getInstance().RemoveContactFromDB((long) p.getID());
                            p.setLastUpdateInDB(updateTime);
                            AddressBookGmsImpl.this.addContactToDb(p);
                            dataChanged = true;
                        } else {
                            ids.add(Integer.valueOf(p.getID()));
                        }
                    } else {
                        p.setLastUpdateInDB(updateTime);
                        AddressBookGmsImpl.this.addContactToDb(p);
                        AddressBookGmsImpl.this.mContactUpdateTimeArray.add(Long.valueOf(updateTime));
                        AddressBookGmsImpl.this.mContactWazeIdsArray.add(Integer.valueOf(p.getID()));
                        AddressBookGmsImpl.this.mGmsToInd.put(p.getGmsId(), Integer.valueOf(AddressBookGmsImpl.this.mContactUpdateTimeArray.size() - 1));
                        dataChanged = true;
                    }
                }
                if (ids.size() > 0) {
                    int[] idsS = AddressBookGmsImpl.this.getIntArray(ids);
                    Logger.d("AddressBookGmsImpl: Updating update time of unchanged contacts");
                    NativeManager.getInstance().UpdateContactsTimeInDB(idsS, updateTime);
                }
                Logger.i("AddressBookGmsImpl: Deleting obsolete contacts from C dB, before date " + updateTime);
                NativeManager.getInstance().DeleteContactsFromDataBase(updateTime);
                Logger.d("AddressBook: update contacts in db ended after " + (System.currentTimeMillis() - AddressBookImpl.abStartTime) + " ms");
                return dataChanged;
            } finally {
                AddressBookGmsImpl.this.mPhonesLock.readLock().unlock();
            }
        }

        private void loadPics() {
            Logger.d("AddressBook: load pics started after " + (System.currentTimeMillis() - AddressBookImpl.abStartTime) + " ms");
            Iterator it = this.mPhoneNumberEntries.iterator();
            while (it.hasNext()) {
                PhoneNumberEntry entry = (PhoneNumberEntry) it.next();
                String str = "";
                str = entry.getPhotoUri();
                if (str != null) {
                    String gmsId = entry.getFocusContactId();
                    if (AddressBookGmsImpl.this.mGmsToInd.containsKey(gmsId)) {
                        PersonGms p = (PersonGms) AddressBookGmsImpl.this.mIds.get(AddressBookGmsImpl.this.mContactWazeIdsArray.get(((Integer) AddressBookGmsImpl.this.mGmsToInd.get(gmsId)).intValue()));
                        if (p != null) {
                            p.setImage(str);
                        }
                    }
                }
            }
            Logger.d("AddressBook: load pics ended after " + (System.currentTimeMillis() - AddressBookImpl.abStartTime) + " ms");
        }
    }

    public void init() {
        super.init();
        this.mClient = new Builder(AppService.getAppContext()).addApi(People.API_1P, new PeopleOptions1p.Builder().setClientApplicationId(80).build()).addConnectionCallbacks(this).addOnConnectionFailedListener(this).build();
    }

    public void start() {
        super.start();
        this.mNm.GetAllContactIdsFromDB(new C22351());
    }

    protected void completeStart() {
        Logger.d("AddressBookGmsImpl: completeStart");
        this.mClient.connect();
        People.GraphApi.loadOwners(this.mClient, new LoadOwnersOptions().setIncludePlusPages(false)).setResultCallback(new C22362());
        People.NotificationApi.registerOnDataChangedListenerForAllOwners(this.mClient, this, 256);
    }

    protected void onOwnersLoaded(Status status, OwnerBuffer owners) {
        if (status.isSuccess()) {
            this.mOwnersLoaded = true;
            try {
                this.mPhonesLock.writeLock().lock();
                this.mOwnerBuffer = owners;
                if (this.mOwnerBuffer.getCount() == 0) {
                    Logger.e("AddressBookGmsImpl: No owners found");
                    return;
                }
                Logger.i("AddressBookGmsImpl: total of " + this.mOwnerBuffer.getCount() + "owners found");
                performSync(true, null);
                this.mPhonesLock.writeLock().unlock();
            } finally {
                this.mPhonesLock.writeLock().unlock();
            }
        } else {
            this.mOwnersLoaded = false;
            Logger.e("AddressBookGmsImpl: Unable to load owners: code: " + status.getStatusCode() + "; msg: " + status.getStatusMessage());
        }
    }

    public void stop() {
        Logger.d("AddressBookGmsImpl: stop, disconnecting client");
        this.mClient.disconnect();
    }

    public boolean isConnected() {
        return this.mClient.isConnected();
    }

    public void onCancel(DialogInterface dialog) {
        Logger.d("AddressBookGmsImpl: onCancel");
    }

    public void onConnectionFailed(ConnectionResult result) {
        Logger.e("AddressBookGmsImpl: GMS address book connection failed: " + result + "; Trying to reconnect");
        if (result.hasResolution()) {
            try {
                result.startResolutionForResult(AppService.getActiveActivity(), 9000);
            } catch (SendIntentException e) {
                this.mClient.connect();
            }
        } else {
            Logger.e("AddressBookGmsImpl: No resolution for GMS address book connection failed: " + result);
        }
        this.mClient.connect();
    }

    public void onConnected(Bundle arg0) {
        Logger.i("AddressBookGmsImpl: GMS address book connected");
        performSync(true, null);
    }

    public void onConnectionSuspended(int arg0) {
        Logger.d("AddressBookGmsImpl: GMS address book connection suspended");
    }

    public void onDataChanged(String account, String pageId, int scopes) {
        Logger.i("AddressBookGmsImpl: Data changed ");
        performSync(false, account);
    }

    protected boolean syncConditionsMet() {
        if (!isConnected()) {
            Logger.d("AddressBookGmsImpl: Client not connected yet");
            return false;
        } else if (this.mOwnersLoaded && this.mOwnerBuffer != null) {
            return true;
        } else {
            Logger.d("AddressBookGmsImpl: No Owners found");
            return false;
        }
    }

    protected void startSyncThread(boolean clearList, String account) {
        this.mSyncT = new GmsSyncThread(clearList, account);
        this.mSyncT.start();
    }
}
