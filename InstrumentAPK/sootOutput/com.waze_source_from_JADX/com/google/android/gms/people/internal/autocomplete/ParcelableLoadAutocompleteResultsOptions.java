package com.google.android.gms.people.internal.autocomplete;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

/* compiled from: dalvik_source_com.waze.apk */
public class ParcelableLoadAutocompleteResultsOptions extends AbstractSafeParcelable {
    public static final Creator<ParcelableLoadAutocompleteResultsOptions> CREATOR = new zzl();
    final long aTW;
    final int aUv;
    final int mVersionCode;
    final String zzaoj;

    ParcelableLoadAutocompleteResultsOptions(int $i0, int $i1, long $l2, String $r1) throws  {
        this.mVersionCode = $i0;
        this.aUv = $i1;
        this.aTW = $l2;
        this.zzaoj = $r1;
    }

    public ParcelableLoadAutocompleteResultsOptions(int $i0, long $l1, String $r1) throws  {
        this(1, $i0, $l1, $r1);
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        zzl.zza(this, $r1, $i0);
    }
}
