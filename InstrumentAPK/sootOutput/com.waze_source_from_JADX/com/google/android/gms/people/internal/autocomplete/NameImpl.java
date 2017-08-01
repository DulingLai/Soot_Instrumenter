package com.google.android.gms.people.internal.autocomplete;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.people.Autocomplete.Name;

/* compiled from: dalvik_source_com.waze.apk */
public class NameImpl extends AbstractSafeParcelable implements Name {
    public static final Creator<NameImpl> CREATOR = new zzk();
    final String cr;
    final int mVersionCode;

    NameImpl(int $i0, String $r1) throws  {
        this.mVersionCode = $i0;
        this.cr = $r1;
    }

    public CharSequence getDisplayName() throws  {
        return this.cr;
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        zzk.zza(this, $r1, $i0);
    }
}
