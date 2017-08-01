package com.google.android.gms.auth.firstparty.dataservice;

import android.accounts.Account;
import android.os.Parcel;
import android.text.TextUtils;
import com.google.android.gms.auth.Country;
import com.google.android.gms.auth.RecoveryResponse;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import dalvik.annotation.Signature;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* compiled from: dalvik_source_com.waze.apk */
public class AccountRecoveryData extends AbstractSafeParcelable implements RecoveryResponse {
    public static final int ACTION_NONE = 3;
    public static final int ACTION_REQUEST_RECOVERY_INFO = 1;
    public static final int ACTION_VERIFY_RECOVERY_INFO = 2;
    public static final AccountRecoveryDataCreator CREATOR = new AccountRecoveryDataCreator();
    public static final int DETAIL_EMAIL_AND_PHONE = 1003;
    public static final int DETAIL_EMAIL_ONLY = 1001;
    public static final int DETAIL_PHONE_ONLY = 1002;
    public final Account account;
    @Deprecated
    public final String accountName;
    public final String action;
    public final String allowedRecoveryOption;
    public final List<Country> countries;
    public final String defaultCountryCode;
    public final String error;
    public final AccountRecoveryGuidance guidance;
    public final String phoneNumber;
    public final String secondaryEmail;
    public final int version;

    /* compiled from: dalvik_source_com.waze.apk */
    public static class Builder {
        private String gA;
        private String gB;
        private List<Country> gC;
        private String gD;
        private String gE;
        private Account gF;
        private AccountRecoveryGuidance gw;
        private String gx;
        private String gy;
        private String gz;

        public AccountRecoveryData build() throws  {
            return new AccountRecoveryData(1, this.gw, this.gx, this.gy, this.gz, this.gA, this.gB, this.gC, this.gD, this.gE, this.gF);
        }

        public Builder setAccount(Account $r1) throws  {
            this.gF = $r1;
            return this;
        }

        public Builder setAccountName(String $r1) throws  {
            this.gz = $r1;
            return this;
        }

        public Builder setAccountRecoveryGuidance(AccountRecoveryGuidance $r1) throws  {
            this.gw = $r1;
            return this;
        }

        public Builder setAction(String $r1) throws  {
            this.gx = $r1;
            return this;
        }

        public Builder setAllowedRecoveryOption(String $r1) throws  {
            this.gy = $r1;
            return this;
        }

        public Builder setCountryList(@Signature({"(", "Ljava/util/List", "<", "Lcom/google/android/gms/auth/Country;", ">;)", "Lcom/google/android/gms/auth/firstparty/dataservice/AccountRecoveryData$Builder;"}) List<Country> $r1) throws  {
            this.gC = Collections.unmodifiableList(new ArrayList($r1));
            return this;
        }

        public Builder setDefaultCountryCode(String $r1) throws  {
            this.gD = $r1;
            return this;
        }

        public Builder setError(String $r1) throws  {
            this.gE = $r1;
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

    AccountRecoveryData(@Signature({"(I", "Lcom/google/android/gms/auth/firstparty/dataservice/AccountRecoveryGuidance;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/auth/Country;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Landroid/accounts/Account;", ")V"}) int $i0, @Signature({"(I", "Lcom/google/android/gms/auth/firstparty/dataservice/AccountRecoveryGuidance;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/auth/Country;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Landroid/accounts/Account;", ")V"}) AccountRecoveryGuidance $r1, @Signature({"(I", "Lcom/google/android/gms/auth/firstparty/dataservice/AccountRecoveryGuidance;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/auth/Country;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Landroid/accounts/Account;", ")V"}) String $r2, @Signature({"(I", "Lcom/google/android/gms/auth/firstparty/dataservice/AccountRecoveryGuidance;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/auth/Country;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Landroid/accounts/Account;", ")V"}) String $r3, @Signature({"(I", "Lcom/google/android/gms/auth/firstparty/dataservice/AccountRecoveryGuidance;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/auth/Country;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Landroid/accounts/Account;", ")V"}) String $r4, @Signature({"(I", "Lcom/google/android/gms/auth/firstparty/dataservice/AccountRecoveryGuidance;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/auth/Country;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Landroid/accounts/Account;", ")V"}) String $r5, @Signature({"(I", "Lcom/google/android/gms/auth/firstparty/dataservice/AccountRecoveryGuidance;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/auth/Country;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Landroid/accounts/Account;", ")V"}) String $r6, @Signature({"(I", "Lcom/google/android/gms/auth/firstparty/dataservice/AccountRecoveryGuidance;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/auth/Country;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Landroid/accounts/Account;", ")V"}) List<Country> $r7, @Signature({"(I", "Lcom/google/android/gms/auth/firstparty/dataservice/AccountRecoveryGuidance;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/auth/Country;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Landroid/accounts/Account;", ")V"}) String $r8, @Signature({"(I", "Lcom/google/android/gms/auth/firstparty/dataservice/AccountRecoveryGuidance;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/auth/Country;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Landroid/accounts/Account;", ")V"}) String $r9, @Signature({"(I", "Lcom/google/android/gms/auth/firstparty/dataservice/AccountRecoveryGuidance;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/auth/Country;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Landroid/accounts/Account;", ")V"}) Account $r10) throws  {
        this.version = $i0;
        this.guidance = $r1;
        this.action = $r2;
        this.allowedRecoveryOption = $r3;
        this.accountName = $r4;
        this.secondaryEmail = $r5;
        this.phoneNumber = $r6;
        this.countries = $r7 == null ? Collections.EMPTY_LIST : Collections.unmodifiableList($r7);
        this.defaultCountryCode = $r8;
        this.error = $r9;
        if ($r10 != null || TextUtils.isEmpty($r4)) {
            this.account = $r10;
        } else {
            this.account = new Account($r4, "com.google");
        }
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        AccountRecoveryDataCreator.zza(this, $r1, $i0);
    }
}
