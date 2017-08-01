package com.google.android.gms.signin.internal;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

/* compiled from: dalvik_source_com.waze.apk */
public class AuthAccountResult extends AbstractSafeParcelable implements Result {
    public static final Creator<AuthAccountResult> CREATOR = new zza();
    private int bgr;
    private Intent bgs;
    final int mVersionCode;

    public AuthAccountResult() throws  {
        this(0, null);
    }

    AuthAccountResult(int $i0, int $i1, Intent $r1) throws  {
        this.mVersionCode = $i0;
        this.bgr = $i1;
        this.bgs = $r1;
    }

    public AuthAccountResult(int $i0, Intent $r1) throws  {
        this(2, $i0, $r1);
    }

    public Status getStatus() throws  {
        return this.bgr == 0 ? Status.CQ : Status.CU;
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        zza.zza(this, $r1, $i0);
    }

    public int zzcnb() throws  {
        return this.bgr;
    }

    public Intent zzcnc() throws  {
        return this.bgs;
    }
}
