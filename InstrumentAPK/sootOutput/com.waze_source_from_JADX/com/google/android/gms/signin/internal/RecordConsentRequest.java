package com.google.android.gms.signin.internal;

import android.accounts.Account;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

/* compiled from: dalvik_source_com.waze.apk */
public class RecordConsentRequest extends AbstractSafeParcelable {
    public static final Creator<RecordConsentRequest> CREATOR = new zzf();
    private final Account f39P;
    private final Scope[] bgv;
    private final String fL;
    final int mVersionCode;

    RecordConsentRequest(int $i0, Account $r1, Scope[] $r2, String $r3) throws  {
        this.mVersionCode = $i0;
        this.f39P = $r1;
        this.bgv = $r2;
        this.fL = $r3;
    }

    public Account getAccount() throws  {
        return this.f39P;
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        zzf.zza(this, $r1, $i0);
    }

    public String zzaem() throws  {
        return this.fL;
    }

    public Scope[] zzcnd() throws  {
        return this.bgv;
    }
}
