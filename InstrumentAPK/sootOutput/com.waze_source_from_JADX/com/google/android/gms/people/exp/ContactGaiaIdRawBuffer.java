package com.google.android.gms.people.exp;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.people.PeopleConstants.Endpoints;
import com.google.android.gms.people.model.ContactGaiaId;

/* compiled from: dalvik_source_com.waze.apk */
public class ContactGaiaIdRawBuffer extends zza implements ContactGaiaId {
    public ContactGaiaIdRawBuffer(DataHolder $r1) throws  {
        super($r1);
    }

    public String getContactInfo() throws  {
        return getString("value");
    }

    public String getGaiaId() throws  {
        return getString(Endpoints.KEY_TARGET_GAIA_ID);
    }

    public int getType() throws  {
        return getInteger("type");
    }
}
