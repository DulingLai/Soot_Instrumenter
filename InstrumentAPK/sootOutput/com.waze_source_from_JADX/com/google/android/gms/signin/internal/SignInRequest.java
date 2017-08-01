package com.google.android.gms.signin.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.ResolveAccountRequest;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

/* compiled from: dalvik_source_com.waze.apk */
public class SignInRequest extends AbstractSafeParcelable {
    public static final Creator<SignInRequest> CREATOR = new zzh();
    final ResolveAccountRequest bgy;
    final int mVersionCode;

    SignInRequest(int $i0, ResolveAccountRequest $r1) throws  {
        this.mVersionCode = $i0;
        this.bgy = $r1;
    }

    public SignInRequest(ResolveAccountRequest $r1) throws  {
        this(1, $r1);
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        zzh.zza(this, $r1, $i0);
    }

    public ResolveAccountRequest zzcnf() throws  {
        return this.bgy;
    }
}
