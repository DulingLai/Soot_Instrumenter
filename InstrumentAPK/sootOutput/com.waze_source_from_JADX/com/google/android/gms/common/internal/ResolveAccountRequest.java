package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.Nullable;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

/* compiled from: dalvik_source_com.waze.apk */
public class ResolveAccountRequest extends AbstractSafeParcelable {
    public static final Creator<ResolveAccountRequest> CREATOR = new zzac();
    private final int Kg;
    private final GoogleSignInAccount Kh;
    private final Account f31P;
    final int mVersionCode;

    ResolveAccountRequest(int $i0, Account $r1, int $i1, GoogleSignInAccount $r2) throws  {
        this.mVersionCode = $i0;
        this.f31P = $r1;
        this.Kg = $i1;
        this.Kh = $r2;
    }

    public ResolveAccountRequest(Account $r1, int $i0, GoogleSignInAccount $r2) throws  {
        this(2, $r1, $i0, $r2);
    }

    public Account getAccount() throws  {
        return this.f31P;
    }

    public int getSessionId() throws  {
        return this.Kg;
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        zzac.zza(this, $r1, $i0);
    }

    @Nullable
    public GoogleSignInAccount zzaxc() throws  {
        return this.Kh;
    }
}
