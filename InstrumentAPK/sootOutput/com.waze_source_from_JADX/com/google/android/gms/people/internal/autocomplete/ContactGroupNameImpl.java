package com.google.android.gms.people.internal.autocomplete;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.people.Autocomplete.ContactGroupName;

/* compiled from: dalvik_source_com.waze.apk */
public class ContactGroupNameImpl extends AbstractSafeParcelable implements ContactGroupName {
    public static final Creator<ContactGroupNameImpl> CREATOR = new zze();
    final String aiv;
    final String mValue;
    final int mVersionCode;

    ContactGroupNameImpl(int $i0, String $r1, String $r2) throws  {
        this.mVersionCode = $i0;
        this.mValue = $r1;
        this.aiv = $r2;
    }

    public CharSequence getFormattedValue() throws  {
        return this.aiv;
    }

    public CharSequence getValue() throws  {
        return this.mValue;
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        zze.zza(this, $r1, $i0);
    }
}
