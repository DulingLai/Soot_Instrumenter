package com.google.android.gms.auth.firstparty.dataservice;

import android.accounts.Account;
import android.os.Parcel;
import android.text.TextUtils;
import com.google.android.gms.auth.firstparty.shared.CaptchaSolution;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

/* compiled from: dalvik_source_com.waze.apk */
public class GplusInfoRequest extends AbstractSafeParcelable {
    public static final GplusInfoRequestCreator CREATOR = new GplusInfoRequestCreator();
    Account account;
    @Deprecated
    String accountName;
    CaptchaSolution gq;
    final int version;

    public GplusInfoRequest() throws  {
        this.version = 2;
    }

    GplusInfoRequest(int $i0, String $r1, CaptchaSolution $r2, Account $r3) throws  {
        this.version = $i0;
        this.accountName = $r1;
        this.gq = $r2;
        if ($r3 != null || TextUtils.isEmpty($r1)) {
            this.account = $r3;
        } else {
            this.account = new Account($r1, "com.google");
        }
    }

    public Account getAccount() throws  {
        return this.account;
    }

    @Deprecated
    public String getAccountName() throws  {
        return this.accountName;
    }

    public CaptchaSolution getCaptchaSolution() throws  {
        return this.gq;
    }

    public GplusInfoRequest setAccount(Account $r1) throws  {
        this.account = $r1;
        return this;
    }

    @Deprecated
    public GplusInfoRequest setAccountName(@Deprecated String $r1) throws  {
        this.accountName = $r1;
        return this;
    }

    public GplusInfoRequest setCaptchaSolution(CaptchaSolution $r1) throws  {
        this.gq = $r1;
        return this;
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        GplusInfoRequestCreator.zza(this, $r1, $i0);
    }
}
