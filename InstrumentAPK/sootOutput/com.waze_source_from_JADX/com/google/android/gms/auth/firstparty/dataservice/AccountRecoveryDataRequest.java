package com.google.android.gms.auth.firstparty.dataservice;

import android.accounts.Account;
import android.os.Parcel;
import android.text.TextUtils;
import com.google.android.gms.auth.firstparty.shared.AppDescription;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.zzab;

/* compiled from: dalvik_source_com.waze.apk */
public class AccountRecoveryDataRequest extends AbstractSafeParcelable {
    public static final AccountRecoveryDataRequestCreator CREATOR = new AccountRecoveryDataRequestCreator();
    private static final String gG;
    public final Account account;
    @Deprecated
    public final String accountName;
    public final AppDescription callingAppDescription;
    public final boolean isClearUpdateSuggested;
    public final String requestDescription;
    final int version;

    static {
        String $r1 = String.valueOf(AccountRecoveryDataRequest.class.getSimpleName());
        gG = new StringBuilder(String.valueOf($r1).length() + 2).append("[").append($r1).append("]").toString();
    }

    AccountRecoveryDataRequest(int $i0, String $r1, boolean $z0, AppDescription $r2, String $r3, Account $r4) throws  {
        this.accountName = zzab.zzi($r1, String.valueOf(gG).concat(" accountName cannot be empty or null!"));
        zzab.zzi($r3, String.valueOf(gG).concat(" requestDescription cannot be empty or null!"));
        this.version = $i0;
        this.isClearUpdateSuggested = $z0;
        this.callingAppDescription = (AppDescription) zzab.zzag($r2);
        this.requestDescription = $r3;
        if ($r4 != null || TextUtils.isEmpty($r1)) {
            this.account = $r4;
        } else {
            this.account = new Account($r1, "com.google");
        }
        zzab.zzag(this.account);
    }

    public AccountRecoveryDataRequest(Account $r1, boolean $z0, AppDescription $r2, String $r3) throws  {
        this(1, $r1.name, $z0, $r2, $r3, $r1);
    }

    @Deprecated
    public AccountRecoveryDataRequest(@Deprecated String $r1, @Deprecated boolean $z0, @Deprecated AppDescription $r2, @Deprecated String $r3) throws  {
        this(new Account($r1, "com.google"), $z0, $r2, $r3);
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        AccountRecoveryDataRequestCreator.zza(this, $r1, $i0);
    }
}
