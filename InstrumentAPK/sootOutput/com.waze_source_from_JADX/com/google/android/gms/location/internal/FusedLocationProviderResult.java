package com.google.android.gms.location.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

/* compiled from: dalvik_source_com.waze.apk */
public final class FusedLocationProviderResult extends AbstractSafeParcelable implements Result {
    public static final Creator<FusedLocationProviderResult> CREATOR = new zze();
    public static final FusedLocationProviderResult avR = new FusedLocationProviderResult(Status.CQ);
    private final Status cp;
    private final int mVersionCode;

    FusedLocationProviderResult(int $i0, Status $r1) throws  {
        this.mVersionCode = $i0;
        this.cp = $r1;
    }

    public FusedLocationProviderResult(Status $r1) throws  {
        this(1, $r1);
    }

    public Status getStatus() throws  {
        return this.cp;
    }

    public int getVersionCode() throws  {
        return this.mVersionCode;
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        zze.zza(this, $r1, $i0);
    }
}
