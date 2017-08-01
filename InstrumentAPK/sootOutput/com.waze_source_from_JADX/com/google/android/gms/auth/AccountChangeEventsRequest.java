package com.google.android.gms.auth;

import android.accounts.Account;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

/* compiled from: dalvik_source_com.waze.apk */
public class AccountChangeEventsRequest extends AbstractSafeParcelable {
    public static final Creator<AccountChangeEventsRequest> CREATOR = new zzb();
    Account f25P;
    @Deprecated
    String dL;
    int dN;
    final int mVersion;

    public AccountChangeEventsRequest() throws  {
        this.mVersion = 1;
    }

    AccountChangeEventsRequest(int $i0, int $i1, String $r1, Account $r2) throws  {
        this.mVersion = $i0;
        this.dN = $i1;
        this.dL = $r1;
        if ($r2 != null || TextUtils.isEmpty($r1)) {
            this.f25P = $r2;
        } else {
            this.f25P = new Account($r1, "com.google");
        }
    }

    public Account getAccount() throws  {
        return this.f25P;
    }

    @Deprecated
    public String getAccountName() throws  {
        return this.dL;
    }

    public int getEventIndex() throws  {
        return this.dN;
    }

    public AccountChangeEventsRequest setAccount(Account $r1) throws  {
        this.f25P = $r1;
        return this;
    }

    @Deprecated
    public AccountChangeEventsRequest setAccountName(@Deprecated String $r1) throws  {
        this.dL = $r1;
        return this;
    }

    public AccountChangeEventsRequest setEventIndex(int $i0) throws  {
        this.dN = $i0;
        return this;
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        zzb.zza(this, $r1, $i0);
    }
}
