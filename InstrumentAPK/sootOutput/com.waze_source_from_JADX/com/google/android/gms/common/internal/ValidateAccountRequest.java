package com.google.android.gms.common.internal;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

@Deprecated
/* compiled from: dalvik_source_com.waze.apk */
public class ValidateAccountRequest extends AbstractSafeParcelable {
    public static final Creator<ValidateAccountRequest> CREATOR = new zzaj();
    private final Scope[] BY;
    final IBinder Il;
    private final int Kp;
    private final Bundle Kq;
    private final String Kr;
    final int mVersionCode;

    ValidateAccountRequest(int $i0, int $i1, IBinder $r1, Scope[] $r2, Bundle $r3, String $r4) throws  {
        this.mVersionCode = $i0;
        this.Kp = $i1;
        this.Il = $r1;
        this.BY = $r2;
        this.Kq = $r3;
        this.Kr = $r4;
    }

    public String getCallingPackage() throws  {
        return this.Kr;
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        zzaj.zza(this, $r1, $i0);
    }

    public Scope[] zzaxj() throws  {
        return this.BY;
    }

    public int zzaxl() throws  {
        return this.Kp;
    }

    public Bundle zzaxm() throws  {
        return this.Kq;
    }
}
