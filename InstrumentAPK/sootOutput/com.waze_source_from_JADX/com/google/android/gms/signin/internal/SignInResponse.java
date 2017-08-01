package com.google.android.gms.signin.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.ResolveAccountResponse;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

/* compiled from: dalvik_source_com.waze.apk */
public class SignInResponse extends AbstractSafeParcelable {
    public static final Creator<SignInResponse> CREATOR = new zzi();
    private final ConnectionResult Cf;
    private final ResolveAccountResponse bgz;
    final int mVersionCode;

    public SignInResponse(int $i0) throws  {
        this(new ConnectionResult($i0, null), null);
    }

    SignInResponse(int $i0, ConnectionResult $r1, ResolveAccountResponse $r2) throws  {
        this.mVersionCode = $i0;
        this.Cf = $r1;
        this.bgz = $r2;
    }

    public SignInResponse(ConnectionResult $r1, ResolveAccountResponse $r2) throws  {
        this(1, $r1, $r2);
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        zzi.zza(this, $r1, $i0);
    }

    public ConnectionResult zzaxe() throws  {
        return this.Cf;
    }

    public ResolveAccountResponse zzcng() throws  {
        return this.bgz;
    }
}
