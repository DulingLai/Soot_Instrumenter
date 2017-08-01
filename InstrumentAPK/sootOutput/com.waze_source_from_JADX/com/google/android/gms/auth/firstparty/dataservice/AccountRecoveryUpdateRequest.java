package com.google.android.gms.auth.firstparty.dataservice;

import android.accounts.Account;
import android.os.Parcel;
import android.text.TextUtils;
import com.google.android.gms.auth.RecoveryResponse;
import com.google.android.gms.auth.firstparty.shared.AppDescription;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

/* compiled from: dalvik_source_com.waze.apk */
public class AccountRecoveryUpdateRequest extends AbstractSafeParcelable implements RecoveryResponse {
    public static final AccountRecoveryUpdateRequestCreator CREATOR = new AccountRecoveryUpdateRequestCreator();
    public final Account account;
    @Deprecated
    public final String accountName;
    public final AppDescription callingAppDescription;
    public final boolean isBroadUse;
    public final String phoneCountryCode;
    public final String phoneNumber;
    public final String secondaryEmail;
    final int version;

    /* compiled from: dalvik_source_com.waze.apk */
    public static class Builder {
        private String gA;
        private String gB;
        private Account gF;
        private String gH;
        private boolean gI;
        private AppDescription gJ;
        @Deprecated
        private String gz;

        public AccountRecoveryUpdateRequest build() throws  {
            return new AccountRecoveryUpdateRequest(1, this.gz, this.gA, this.gB, this.gH, this.gI, this.gJ, this.gF);
        }

        public Builder setAccount(Account $r1) throws  {
            this.gF = $r1;
            return this;
        }

        @Deprecated
        public Builder setAccountName(@Deprecated String $r1) throws  {
            this.gz = $r1;
            return this;
        }

        public Builder setBroadUse(boolean $z0) throws  {
            this.gI = $z0;
            return this;
        }

        public Builder setCallingAppDescription(AppDescription $r1) throws  {
            this.gJ = $r1;
            return this;
        }

        public Builder setPhoneCountryCode(String $r1) throws  {
            this.gH = $r1;
            return this;
        }

        public Builder setPhoneNumber(String $r1) throws  {
            this.gB = $r1;
            return this;
        }

        public Builder setSecondaryEmail(String $r1) throws  {
            this.gA = $r1;
            return this;
        }
    }

    AccountRecoveryUpdateRequest(int $i0, String $r1, String $r2, String $r3, String $r4, boolean $z0, AppDescription $r5, Account $r6) throws  {
        this.version = $i0;
        this.accountName = $r1;
        this.secondaryEmail = $r2;
        this.phoneNumber = $r3;
        this.phoneCountryCode = $r4;
        this.isBroadUse = $z0;
        this.callingAppDescription = $r5;
        if ($r6 != null || TextUtils.isEmpty($r1)) {
            this.account = $r6;
        } else {
            this.account = new Account($r1, "com.google");
        }
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        AccountRecoveryUpdateRequestCreator.zza(this, $r1, $i0);
    }
}
