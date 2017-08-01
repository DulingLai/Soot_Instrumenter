package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

/* compiled from: dalvik_source_com.waze.apk */
public class AuthAccountRequest extends AbstractSafeParcelable {
    public static final Creator<AuthAccountRequest> CREATOR = new zzc();
    final Scope[] BY;
    final IBinder Il;
    Integer Im;
    Integer In;
    final int mVersionCode;

    AuthAccountRequest(int $i0, IBinder $r1, Scope[] $r2, Integer $r3, Integer $r4) throws  {
        this.mVersionCode = $i0;
        this.Il = $r1;
        this.BY = $r2;
        this.Im = $r3;
        this.In = $r4;
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        zzc.zza(this, $r1, $i0);
    }
}
