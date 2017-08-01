package com.google.android.gms.people.internal;

import android.content.Context;
import android.os.Bundle;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.zzc;
import com.google.android.gms.people.cp2.AndroidContactsUtils;
import com.google.android.gms.people.model.PhoneNumberEntry;

/* compiled from: dalvik_source_com.waze.apk */
public class zzs extends zzc implements PhoneNumberEntry {
    private final Bundle GV;
    private final Context mContext;

    public zzs(DataHolder $r1, int $i0, Bundle $r2, Context $r3) throws  {
        super($r1, $i0);
        this.GV = $r2;
        this.mContext = $r3;
    }

    public String getFocusContactId() throws  {
        return getString("contact_id");
    }

    public Long getLastUpdateTime() throws  {
        return Long.valueOf(getLong("last_update_time"));
    }

    public String getName() throws  {
        return getString("display_name");
    }

    public String getOwnerAccountName() throws  {
        return this.GV.getString("account");
    }

    public String getPhoneNumber() throws  {
        return getString("phone_number");
    }

    public String getPhotoUri() throws  {
        return AndroidContactsUtils.getPhotoUriFromFocusContactId(this.mContext, getOwnerAccountName(), getFocusContactId());
    }
}
