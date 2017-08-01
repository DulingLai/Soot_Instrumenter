package com.google.android.gms.auth.firstparty.dataservice;

import android.accounts.Account;
import android.os.Parcel;
import android.text.TextUtils;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.zzab;

/* compiled from: dalvik_source_com.waze.apk */
public class AccountRecoveryGuidance extends AbstractSafeParcelable {
    public static final AccountRecoveryGuidanceCreator CREATOR = new AccountRecoveryGuidanceCreator();
    public final Account account;
    @Deprecated
    public final String accountName;
    public final boolean isRecoveryInfoNeeded;
    public final boolean isRecoveryInterstitialSuggested;
    public final boolean isRecoveryUpdateAllowed;
    final int version;

    AccountRecoveryGuidance(int $i0, String $r1, boolean $z0, boolean $z1, boolean $z2, Account $r2) throws  {
        this.version = $i0;
        this.accountName = $r1;
        this.isRecoveryInfoNeeded = $z0;
        this.isRecoveryInterstitialSuggested = $z1;
        this.isRecoveryUpdateAllowed = $z2;
        if ($r2 != null || TextUtils.isEmpty($r1)) {
            this.account = $r2;
        } else {
            this.account = new Account($r1, "com.google");
        }
    }

    public AccountRecoveryGuidance(Account $r1, boolean $z0, boolean $z1, boolean $z2) throws  {
        this(1, zzab.zzgy($r1.name), $z0, $z1, $z2, $r1);
    }

    @Deprecated
    public AccountRecoveryGuidance(@Deprecated String $r1, @Deprecated boolean $z0, @Deprecated boolean $z1, @Deprecated boolean $z2) throws  {
        this(new Account($r1, "com.google"), $z0, $z1, $z2);
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        AccountRecoveryGuidanceCreator.zza(this, $r1, $i0);
    }
}
