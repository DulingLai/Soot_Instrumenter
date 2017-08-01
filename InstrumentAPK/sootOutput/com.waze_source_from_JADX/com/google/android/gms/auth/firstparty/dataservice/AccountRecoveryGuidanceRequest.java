package com.google.android.gms.auth.firstparty.dataservice;

import android.accounts.Account;
import android.os.Parcel;
import android.text.TextUtils;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

/* compiled from: dalvik_source_com.waze.apk */
public class AccountRecoveryGuidanceRequest extends AbstractSafeParcelable {
    public static final AccountRecoveryGuidanceRequestCreator CREATOR = new AccountRecoveryGuidanceRequestCreator();
    public final Account account;
    @Deprecated
    public final String accountName;
    final int version;

    AccountRecoveryGuidanceRequest(int $i0, String $r1, Account $r2) throws  {
        this.version = $i0;
        this.accountName = $r1;
        if ($r2 != null || TextUtils.isEmpty($r1)) {
            this.account = $r2;
        } else {
            this.account = new Account($r1, "com.google");
        }
    }

    public AccountRecoveryGuidanceRequest(Account $r1) throws  {
        this(1, $r1.name, $r1);
    }

    @Deprecated
    public AccountRecoveryGuidanceRequest(@Deprecated String $r1) throws  {
        this(new Account($r1, "com.google"));
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        AccountRecoveryGuidanceRequestCreator.zza(this, $r1, $i0);
    }
}
