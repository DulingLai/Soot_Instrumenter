package com.waze.user;

import com.waze.autocomplete.Person;
import java.util.Date;

public class PersonGms extends Person implements Comparable {
    private String mGmsId;
    private long mLastUpdateContact;
    private long mLastUpdateInDB;

    public PersonGms(String n, String p, String i, int id, long lastUpdateContact, long lastUpdateInDB, String gmsId) {
        super(n, p, i, id);
        this.mLastUpdateContact = lastUpdateContact;
        this.mGmsId = gmsId;
        this.mLastUpdateInDB = lastUpdateInDB;
    }

    public PersonGms(String n, String p, String i, int id, String gmsId) {
        super(n, p, i, id);
        this.mLastUpdateContact = (new Date().getTime() / 1000) - 1;
        this.mLastUpdateInDB = (new Date().getTime() / 1000) - 1;
        this.mGmsId = gmsId;
    }

    public long getLastUpdateContact() {
        return this.mLastUpdateContact;
    }

    public void setLastUpdateContact(long lastUpdate) {
        this.mLastUpdateContact = lastUpdate;
    }

    public long getLastUpdateInDB() {
        return this.mLastUpdateInDB;
    }

    public void setLastUpdateInDB(long mLastUpdateInDB) {
        this.mLastUpdateInDB = mLastUpdateInDB;
    }

    public String getGmsId() {
        return this.mGmsId;
    }

    public void setGmsId(String id) {
        this.mGmsId = id;
    }

    public int compareTo(Object o) {
        if (o == null || !(o instanceof PersonGms)) {
            return 1;
        }
        if (this.mGmsId.compareTo(((PersonGms) o).getGmsId()) == 0) {
            return super.compareTo(o);
        }
        return 1;
    }
}
