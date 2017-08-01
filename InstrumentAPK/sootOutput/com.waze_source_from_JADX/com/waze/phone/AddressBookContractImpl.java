package com.waze.phone;

import android.database.Cursor;
import android.os.Build.VERSION;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import com.waze.AppService;
import com.waze.Logger;
import com.waze.NativeManager;
import com.waze.NativeManager.AllIdsFromDBListener;
import com.waze.navigate.social.GmsWazeIdsMatchData;
import com.waze.user.PersonGms;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class AddressBookContractImpl extends AddressBookImpl {
    public static final String[] PROJECTION3 = new String[]{"_id", "data1", "display_name", "version", "photo_uri", "raw_contact_id", "is_primary", "account_type_and_data_set", "raw_contact_id"};
    public static final String[] PROJECTION4 = new String[]{"_id", "data1", "display_name", "version", "raw_contact_id", "is_primary"};
    private HashMap<Integer, Long> mIdToUpdateDate = new HashMap();
    private boolean mIsLowApi = false;

    class C22341 implements AllIdsFromDBListener {
        C22341() {
        }

        public void onComplete(GmsWazeIdsMatchData data) {
            if (data != null && data.wazeIds.length > 0) {
                for (int i = 0; i < data.wazeIds.length; i++) {
                    AddressBookContractImpl.this.mIdToUpdateDate.put(Integer.valueOf(data.wazeIds[i]), Long.valueOf(data.updateDates[i]));
                }
            }
        }
    }

    private class ContactSyncThread extends Thread {
        private String mAccountName = null;
        private boolean mClearList = true;

        private void prepareContacts(android.database.Cursor r21, long r22) {
            /* JADX: method processing error */
/*
Error: java.util.NoSuchElementException
	at java.util.HashMap$HashIterator.nextNode(HashMap.java:1431)
	at java.util.HashMap$KeyIterator.next(HashMap.java:1453)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.applyRemove(BlockFinallyExtract.java:535)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.extractFinally(BlockFinallyExtract.java:175)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.processExceptionHandler(BlockFinallyExtract.java:79)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.visit(BlockFinallyExtract.java:51)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r20 = this;
            r18 = 0;
            r10 = new java.lang.StringBuilder;
            r10.<init>();
            r11 = "AddressBookContractImpl: Selected ";
            r10 = r10.append(r11);
            r11 = r21.getCount();
            r10 = r10.append(r11);
            r11 = " rows from contacts";
            r10 = r10.append(r11);
            r10 = r10.toString();
            com.waze.Logger.d(r10);
            r10 = r21.moveToFirst();	 Catch:{ Exception -> 0x0121, all -> 0x015d }
            if (r10 == 0) goto L_0x0095;	 Catch:{ Exception -> 0x0121, all -> 0x015d }
        L_0x002a:
            r18 = r18 + 1;	 Catch:{ Exception -> 0x0121, all -> 0x015d }
            r10 = 0;	 Catch:{ Exception -> 0x0121, all -> 0x015d }
            r0 = r21;	 Catch:{ Exception -> 0x0121, all -> 0x015d }
            r7 = r0.getInt(r10);	 Catch:{ Exception -> 0x0121, all -> 0x015d }
            r10 = 1;	 Catch:{ Exception -> 0x0121, all -> 0x015d }
            r0 = r21;	 Catch:{ Exception -> 0x0121, all -> 0x015d }
            r13 = r0.getString(r10);	 Catch:{ Exception -> 0x0121, all -> 0x015d }
            r10 = 2;	 Catch:{ Exception -> 0x0121, all -> 0x015d }
            r0 = r21;	 Catch:{ Exception -> 0x0121, all -> 0x015d }
            r4 = r0.getString(r10);	 Catch:{ Exception -> 0x0121, all -> 0x015d }
            r10 = 3;	 Catch:{ Exception -> 0x0121, all -> 0x015d }
            r0 = r21;	 Catch:{ Exception -> 0x0121, all -> 0x015d }
            r10 = r0.getInt(r10);	 Catch:{ Exception -> 0x0121, all -> 0x015d }
            r8 = (long) r10;	 Catch:{ Exception -> 0x0121, all -> 0x015d }
            r6 = 0;	 Catch:{ Exception -> 0x0121, all -> 0x015d }
            r2 = "";	 Catch:{ Exception -> 0x0121, all -> 0x015d }
            r19 = 0;	 Catch:{ Exception -> 0x0121, all -> 0x015d }
            r0 = r20;	 Catch:{ Exception -> 0x0121, all -> 0x015d }
            r10 = com.waze.phone.AddressBookContractImpl.this;	 Catch:{ Exception -> 0x0121, all -> 0x015d }
            r10 = r10.mIsLowApi;	 Catch:{ Exception -> 0x0121, all -> 0x015d }
            if (r10 != 0) goto L_0x00e4;	 Catch:{ Exception -> 0x0121, all -> 0x015d }
        L_0x0059:
            r10 = 4;	 Catch:{ Exception -> 0x0121, all -> 0x015d }
            r0 = r21;	 Catch:{ Exception -> 0x0121, all -> 0x015d }
            r6 = r0.getString(r10);	 Catch:{ Exception -> 0x0121, all -> 0x015d }
            r10 = 5;	 Catch:{ Exception -> 0x0121, all -> 0x015d }
            r0 = r21;	 Catch:{ Exception -> 0x0121, all -> 0x015d }
            r14 = r0.getLong(r10);	 Catch:{ Exception -> 0x0121, all -> 0x015d }
            r10 = 6;	 Catch:{ Exception -> 0x0121, all -> 0x015d }
            r0 = r21;	 Catch:{ Exception -> 0x0121, all -> 0x015d }
            r17 = r0.getInt(r10);	 Catch:{ Exception -> 0x0121, all -> 0x015d }
            r10 = 7;	 Catch:{ Exception -> 0x0121, all -> 0x015d }
            r0 = r21;	 Catch:{ Exception -> 0x0121, all -> 0x015d }
            r2 = r0.getString(r10);	 Catch:{ Exception -> 0x0121, all -> 0x015d }
            r10 = 8;	 Catch:{ Exception -> 0x0121, all -> 0x015d }
            r0 = r21;	 Catch:{ Exception -> 0x0121, all -> 0x015d }
            r19 = r0.getInt(r10);	 Catch:{ Exception -> 0x0121, all -> 0x015d }
        L_0x007d:
            r0 = r20;	 Catch:{ Exception -> 0x0121, all -> 0x015d }
            r10 = com.waze.phone.AddressBookContractImpl.this;	 Catch:{ Exception -> 0x0121, all -> 0x015d }
            r0 = r20;	 Catch:{ Exception -> 0x0121, all -> 0x015d }
            r11 = com.waze.phone.AddressBookContractImpl.this;	 Catch:{ Exception -> 0x0121, all -> 0x015d }
            r11 = r11.GetCountryId();	 Catch:{ Exception -> 0x0121, all -> 0x015d }
            r5 = r10.PhoneFormat(r13, r11);	 Catch:{ Exception -> 0x0121, all -> 0x015d }
            if (r5 != 0) goto L_0x00f3;	 Catch:{ Exception -> 0x0121, all -> 0x015d }
        L_0x008f:
            r10 = r21.moveToNext();	 Catch:{ Exception -> 0x0121, all -> 0x015d }
            if (r10 != 0) goto L_0x002a;	 Catch:{ Exception -> 0x0121, all -> 0x015d }
        L_0x0095:
            r0 = r20;	 Catch:{ Exception -> 0x0121, all -> 0x015d }
            r10 = com.waze.phone.AddressBookContractImpl.this;	 Catch:{ Exception -> 0x0121, all -> 0x015d }
            r10 = r10.mHashPersonArray;	 Catch:{ Exception -> 0x0121, all -> 0x015d }
            r10 = r10.size();	 Catch:{ Exception -> 0x0121, all -> 0x015d }
            if (r10 <= 0) goto L_0x00ca;	 Catch:{ Exception -> 0x0121, all -> 0x015d }
        L_0x00a1:
            r0 = r20;	 Catch:{ Exception -> 0x0121, all -> 0x015d }
            r10 = com.waze.phone.AddressBookContractImpl.this;	 Catch:{ Exception -> 0x0121, all -> 0x015d }
            r10 = r10.mPhonesLock;	 Catch:{ Exception -> 0x0121, all -> 0x015d }
            r10 = r10.writeLock();	 Catch:{ Exception -> 0x0121, all -> 0x015d }
            r10.lock();	 Catch:{ Exception -> 0x0121, all -> 0x015d }
            r0 = r20;	 Catch:{ Exception -> 0x0121, all -> 0x015d }
            r10 = com.waze.phone.AddressBookContractImpl.this;	 Catch:{ Exception -> 0x0121, all -> 0x015d }
            r10 = r10.mPersonMap;	 Catch:{ Exception -> 0x0121, all -> 0x015d }
            r0 = r20;	 Catch:{ Exception -> 0x0121, all -> 0x015d }
            r11 = com.waze.phone.AddressBookContractImpl.this;	 Catch:{ Exception -> 0x0121, all -> 0x015d }
            r11 = r11.mHashPersonArray;	 Catch:{ Exception -> 0x0121, all -> 0x015d }
            r10.putAll(r11);	 Catch:{ Exception -> 0x0121, all -> 0x015d }
            r0 = r20;	 Catch:{ Exception -> 0x0121, all -> 0x015d }
            r10 = com.waze.phone.AddressBookContractImpl.this;	 Catch:{ Exception -> 0x0121, all -> 0x015d }
            r10 = r10.mPhonesLock;	 Catch:{ Exception -> 0x0121, all -> 0x015d }
            r10 = r10.writeLock();	 Catch:{ Exception -> 0x0121, all -> 0x015d }
            r10.unlock();	 Catch:{ Exception -> 0x0121, all -> 0x015d }
        L_0x00ca:
            r0 = r20;
            r10 = com.waze.phone.AddressBookContractImpl.this;
            r10 = r10.mPhonesLock;
            r10 = r10.isWriteLocked();
            if (r10 == 0) goto L_0x00e3;
        L_0x00d6:
            r0 = r20;
            r10 = com.waze.phone.AddressBookContractImpl.this;
            r10 = r10.mPhonesLock;
            r10 = r10.writeLock();
            r10.unlock();
        L_0x00e3:
            return;
        L_0x00e4:
            r10 = 4;
            r0 = r21;	 Catch:{ Exception -> 0x0121, all -> 0x015d }
            r14 = r0.getLong(r10);	 Catch:{ Exception -> 0x0121, all -> 0x015d }
            r10 = 6;	 Catch:{ Exception -> 0x0121, all -> 0x015d }
            r0 = r21;	 Catch:{ Exception -> 0x0121, all -> 0x015d }
            r17 = r0.getInt(r10);	 Catch:{ Exception -> 0x0121, all -> 0x015d }
            goto L_0x007d;	 Catch:{ Exception -> 0x0121, all -> 0x015d }
        L_0x00f3:
            r0 = r20;	 Catch:{ Exception -> 0x0121, all -> 0x015d }
            r10 = com.waze.phone.AddressBookContractImpl.this;	 Catch:{ Exception -> 0x0121, all -> 0x015d }
            r10 = r10.mHashPersonArray;	 Catch:{ Exception -> 0x0121, all -> 0x015d }
            r10 = r10.containsKey(r5);	 Catch:{ Exception -> 0x0121, all -> 0x015d }
            if (r10 != 0) goto L_0x0144;	 Catch:{ Exception -> 0x0121, all -> 0x015d }
        L_0x00ff:
            r3 = new com.waze.user.PersonGms;	 Catch:{ Exception -> 0x0121, all -> 0x015d }
            r12 = "-1";	 Catch:{ Exception -> 0x0121, all -> 0x015d }
            r10 = r22;	 Catch:{ Exception -> 0x0121, all -> 0x015d }
            r3.<init>(r4, r5, r6, r7, r8, r10, r12);	 Catch:{ Exception -> 0x0121, all -> 0x015d }
            r0 = r20;	 Catch:{ Exception -> 0x0121, all -> 0x015d }
            r10 = com.waze.phone.AddressBookContractImpl.this;	 Catch:{ Exception -> 0x0121, all -> 0x015d }
            r10 = r10.mHashPersonArray;	 Catch:{ Exception -> 0x0121, all -> 0x015d }
            r10.put(r5, r3);	 Catch:{ Exception -> 0x0121, all -> 0x015d }
            r0 = r20;	 Catch:{ Exception -> 0x0121, all -> 0x015d }
            r10 = com.waze.phone.AddressBookContractImpl.this;	 Catch:{ Exception -> 0x0121, all -> 0x015d }
            r10 = r10.mIds;	 Catch:{ Exception -> 0x0121, all -> 0x015d }
            r11 = java.lang.Integer.valueOf(r7);	 Catch:{ Exception -> 0x0121, all -> 0x015d }
            r10.put(r11, r3);	 Catch:{ Exception -> 0x0121, all -> 0x015d }
            goto L_0x008f;
        L_0x0121:
            r16 = move-exception;
            r10 = "Exception occurred";	 Catch:{ Exception -> 0x0121, all -> 0x015d }
            r0 = r16;	 Catch:{ Exception -> 0x0121, all -> 0x015d }
            com.waze.Logger.e(r10, r0);	 Catch:{ Exception -> 0x0121, all -> 0x015d }
            r0 = r20;
            r10 = com.waze.phone.AddressBookContractImpl.this;
            r10 = r10.mPhonesLock;
            r10 = r10.isWriteLocked();
            if (r10 == 0) goto L_0x00e3;
        L_0x0136:
            r0 = r20;
            r10 = com.waze.phone.AddressBookContractImpl.this;
            r10 = r10.mPhonesLock;
            r10 = r10.writeLock();
            r10.unlock();
            goto L_0x00e3;
        L_0x0144:
            r0 = r20;	 Catch:{ Exception -> 0x0121, all -> 0x015d }
            r10 = com.waze.phone.AddressBookContractImpl.this;	 Catch:{ Exception -> 0x0121, all -> 0x015d }
            r10 = r10.mIds;	 Catch:{ Exception -> 0x0121, all -> 0x015d }
            r11 = java.lang.Integer.valueOf(r7);	 Catch:{ Exception -> 0x0121, all -> 0x015d }
            r0 = r20;	 Catch:{ Exception -> 0x0121, all -> 0x015d }
            r12 = com.waze.phone.AddressBookContractImpl.this;	 Catch:{ Exception -> 0x0121, all -> 0x015d }
            r12 = r12.mHashPersonArray;	 Catch:{ Exception -> 0x0121, all -> 0x015d }
            r12 = r12.get(r5);	 Catch:{ Exception -> 0x0121, all -> 0x015d }
            r10.put(r11, r12);	 Catch:{ Exception -> 0x0121, all -> 0x015d }
            goto L_0x008f;
        L_0x015d:
            r10 = move-exception;
            r0 = r20;
            r11 = com.waze.phone.AddressBookContractImpl.this;
            r11 = r11.mPhonesLock;
            r11 = r11.isWriteLocked();
            if (r11 == 0) goto L_0x0177;
        L_0x016a:
            r0 = r20;
            r11 = com.waze.phone.AddressBookContractImpl.this;
            r11 = r11.mPhonesLock;
            r11 = r11.writeLock();
            r11.unlock();
        L_0x0177:
            throw r10;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.waze.phone.AddressBookContractImpl.ContactSyncThread.prepareContacts(android.database.Cursor, long):void");
        }

        public ContactSyncThread(boolean clearList, String account) {
            this.mClearList = clearList;
            this.mAccountName = account;
        }

        public void run() {
            Logger.d("AddressBookContractImpl: : performSync thread");
            if (AddressBookContractImpl.this.mStopSync) {
                Logger.i("AddressBookContractImpl: Stop sync requested");
                return;
            }
            Logger.d("AddressBookContractImpl: : startLoading");
            try {
                AddressBookContractImpl.this.mPhonesLock.writeLock().lock();
                if (this.mClearList) {
                    Logger.d("AddressBookContractImpl: Clearing phone numbers for all accounts");
                    AddressBookContractImpl.this.mPersonMap.clear();
                    AddressBookContractImpl.this.mIds.clear();
                    AddressBookContractImpl.this.mHashPersonArray.clear();
                }
                AddressBookContractImpl.this.mPhonesLock.writeLock().unlock();
                long lastUpdate = (new Date().getTime() / 1000) - 1;
                retrieveAllPhones(lastUpdate);
                AddressBookContractImpl.this.mPhonesInit = true;
                Logger.i("AddressBookContractImpl: Performing sync on existing data");
                boolean bChanged = updateContactsInDB(lastUpdate);
                AddressBookContractImpl.this.mWasSyncExecuted = true;
                if (AddressBookContractImpl.this.mStopSync) {
                    Logger.i("AddressBookContractImpl: Stop sync requested");
                    return;
                }
                if (bChanged && (NativeManager.getInstance().IsAccessToContactsEnableNTV() || NativeManager.bToUploadContacts)) {
                    NativeManager.getInstance().ContactUpload();
                }
                AddressBookContractImpl.this.mSyncIsRunning.set(false);
                Logger.i("AddressBookContractImpl: Sync is no longer running");
                AddressBookContractImpl.this.mStopSync = false;
            } catch (Throwable th) {
                AddressBookContractImpl.this.mPhonesLock.writeLock().unlock();
            }
        }

        private void retrieveAllPhones(long lastUpdate) {
            Cursor allContactsCur;
            String selection = "in_visible_group = '1' AND has_phone_number = '1'";
            if (this.mAccountName != null) {
                selection = selection + selection + " AND " + "account_type" + " = '" + this.mAccountName + "'";
            }
            String sortOrder = "display_name COLLATE LOCALIZED ASC";
            if (AddressBookContractImpl.this.mIsLowApi) {
                allContactsCur = AppService.getAppContext().getContentResolver().query(Phone.CONTENT_URI, AddressBookContractImpl.PROJECTION4, selection, null, sortOrder);
            } else {
                allContactsCur = AppService.getAppContext().getContentResolver().query(Phone.CONTENT_URI, AddressBookContractImpl.PROJECTION3, selection, null, sortOrder);
            }
            prepareContacts(allContactsCur, lastUpdate);
            allContactsCur.close();
        }

        private boolean updateContactsInDB(long lastUpdate) {
            boolean dataChanged = false;
            ArrayList<Integer> ids = new ArrayList();
            try {
                AddressBookContractImpl.this.mPhonesLock.readLock().lock();
                for (PersonGms p : AddressBookContractImpl.this.mPersonMap.values()) {
                    if (AddressBookContractImpl.this.mIdToUpdateDate.containsKey(Integer.valueOf(p.getID()))) {
                        long updateDate = ((Long) AddressBookContractImpl.this.mIdToUpdateDate.get(Integer.valueOf(p.getID()))).longValue();
                        if (AddressBookContractImpl.this.mAccountExisted || updateDate < p.getLastUpdateContact()) {
                            NativeManager.getInstance().RemoveContactFromDB((long) p.getID());
                            p.setLastUpdateInDB(lastUpdate);
                            AddressBookContractImpl.this.addContactToDb(p);
                            dataChanged = true;
                        } else {
                            ids.add(Integer.valueOf(p.getID()));
                        }
                    } else {
                        p.setLastUpdateInDB(lastUpdate);
                        AddressBookContractImpl.this.addContactToDb(p);
                        dataChanged = true;
                    }
                }
                if (ids.size() > 0) {
                    int[] idsS = AddressBookContractImpl.this.getIntArray(ids);
                    Logger.d("AddressBookContractImpl: Updating update time of unchanged contacts");
                    NativeManager.getInstance().UpdateContactsTimeInDB(idsS, lastUpdate);
                }
                Logger.i("AddressBookContractImpl: Deleting obsolete contacts from C dB");
                NativeManager.getInstance().DeleteContactsFromDataBase(lastUpdate);
                return dataChanged;
            } finally {
                AddressBookContractImpl.this.mPhonesLock.readLock().unlock();
            }
        }
    }

    public void init() {
        super.init();
        if (VERSION.SDK_INT < 11) {
            this.mIsLowApi = true;
        }
    }

    protected boolean syncConditionsMet() {
        return true;
    }

    public void start() {
        super.start();
        this.mNm.GetAllContactIdsFromDB(new C22341());
    }

    protected void startSyncThread(boolean clearList, String account) {
        this.mSyncT = new ContactSyncThread(clearList, account);
        this.mSyncT.start();
    }
}
