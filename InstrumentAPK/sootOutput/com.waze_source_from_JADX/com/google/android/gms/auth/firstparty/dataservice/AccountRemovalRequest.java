package com.google.android.gms.auth.firstparty.dataservice;

import android.accounts.Account;
import android.os.Parcel;
import android.text.TextUtils;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

/* compiled from: dalvik_source_com.waze.apk */
public class AccountRemovalRequest extends AbstractSafeParcelable {
    public static final AccountRemovalRequestCreator CREATOR = new AccountRemovalRequestCreator();
    Account account;
    @Deprecated
    String accountName;
    final int version;

    public AccountRemovalRequest() throws  {
        this.version = 2;
    }

    AccountRemovalRequest(int $i0, String $r1, Account $r2) throws  {
        this.version = $i0;
        this.accountName = $r1;
        if ($r2 != null || TextUtils.isEmpty($r1)) {
            this.account = $r2;
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

    public AccountRemovalRequest setAccount(Account $r1) throws  {
        this.account = $r1;
        return this;
    }

    @Deprecated
    public AccountRemovalRequest setAccountName(@Deprecated String $r1) throws  {
        this.accountName = $r1;
        return this;
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        AccountRemovalRequestCreator.zza(this, $r1, $i0);
    }
}
