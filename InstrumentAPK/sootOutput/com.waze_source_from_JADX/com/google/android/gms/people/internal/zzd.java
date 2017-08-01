package com.google.android.gms.people.internal;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.zzc;
import com.google.android.gms.people.PeopleConstants.Endpoints;
import com.google.android.gms.people.model.ContactGaiaId;

/* compiled from: dalvik_source_com.waze.apk */
public class zzd extends zzc implements ContactGaiaId {
    public zzd(DataHolder $r1, int $i0) throws  {
        super($r1, $i0);
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
