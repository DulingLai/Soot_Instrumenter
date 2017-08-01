package com.google.android.gms.people.internal.autocomplete;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.people.Autocomplete.Email;

/* compiled from: dalvik_source_com.waze.apk */
public class EmailImpl extends AbstractSafeParcelable implements Email {
    public static final Creator<EmailImpl> CREATOR = new zzi();
    final String mValue;
    final int mVersionCode;

    EmailImpl(int $i0, String $r1) throws  {
        this.mVersionCode = $i0;
        this.mValue = $r1;
    }

    public CharSequence getValue() throws  {
        return this.mValue;
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        zzi.zza(this, $r1, $i0);
    }
}
