package com.google.android.gms.people.internal.autocomplete;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.people.Autocomplete.Phone;

/* compiled from: dalvik_source_com.waze.apk */
public class PhoneImpl extends AbstractSafeParcelable implements Phone {
    public static final Creator<PhoneImpl> CREATOR = new zzp();
    final String mValue;
    final int mVersionCode;

    PhoneImpl(int $i0, String $r1) throws  {
        this.mVersionCode = $i0;
        this.mValue = $r1;
    }

    public CharSequence getValue() throws  {
        return this.mValue;
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        zzp.zza(this, $r1, $i0);
    }
}
