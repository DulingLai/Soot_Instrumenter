package com.google.android.gms.people.internal.autocomplete;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.people.Autocomplete.ContactGroupId;

/* compiled from: dalvik_source_com.waze.apk */
public class ContactGroupIdImpl extends AbstractSafeParcelable implements ContactGroupId {
    public static final Creator<ContactGroupIdImpl> CREATOR = new zzc();
    final String[] aUo;
    final int mVersionCode;
    final String zzbgd;

    ContactGroupIdImpl(int $i0, String $r1, String[] $r2) throws  {
        this.mVersionCode = $i0;
        this.zzbgd = $r1;
        this.aUo = $r2;
    }

    public String getId() throws  {
        return this.zzbgd;
    }

    public String[] getLegacyId() throws  {
        return this.aUo;
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        zzc.zza(this, $r1, $i0);
    }
}
