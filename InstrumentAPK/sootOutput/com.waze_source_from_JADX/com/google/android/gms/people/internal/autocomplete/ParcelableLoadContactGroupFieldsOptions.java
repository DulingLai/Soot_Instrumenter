package com.google.android.gms.people.internal.autocomplete;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

/* compiled from: dalvik_source_com.waze.apk */
public class ParcelableLoadContactGroupFieldsOptions extends AbstractSafeParcelable {
    public static final Creator<ParcelableLoadContactGroupFieldsOptions> CREATOR = new zzm();
    final String aUw;
    final int mVersionCode;

    ParcelableLoadContactGroupFieldsOptions(int $i0, String $r1) throws  {
        this.mVersionCode = $i0;
        this.aUw = $r1;
    }

    public ParcelableLoadContactGroupFieldsOptions(String $r1) throws  {
        this(1, $r1);
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        zzm.zza(this, $r1, $i0);
    }
}
