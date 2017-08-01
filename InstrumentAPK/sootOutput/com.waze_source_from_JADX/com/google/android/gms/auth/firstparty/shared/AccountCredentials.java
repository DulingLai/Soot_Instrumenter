package com.google.android.gms.auth.firstparty.shared;

import android.accounts.Account;
import android.os.Parcel;
import android.text.TextUtils;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.zzab;

/* compiled from: dalvik_source_com.waze.apk */
public class AccountCredentials extends AbstractSafeParcelable {
    public static final AccountCredentialsCreator CREATOR = new AccountCredentialsCreator();
    String dL;
    String eH;
    boolean ik;
    String il;
    String im;
    String io;
    String ip;
    final String mAccountType;
    final int version;

    @Deprecated
    public AccountCredentials() throws  {
        this("com.google");
    }

    AccountCredentials(int $i0, boolean $z0, String $r1, String $r2, String $r3, String $r4, String $r5, String $r6, String $r7) throws  {
        this.version = $i0;
        this.ik = $z0;
        this.dL = $r1;
        this.il = $r2;
        this.im = $r3;
        this.eH = $r4;
        this.io = $r5;
        this.ip = $r6;
        this.mAccountType = $r7;
    }

    public AccountCredentials(Account $r1) throws  {
        this($r1.type);
        this.dL = $r1.name;
    }

    public AccountCredentials(Parcel $r1) throws  {
        boolean $z0 = true;
        this.version = 2;
        if ($r1.readInt() != 1) {
            $z0 = false;
        }
        this.ik = $z0;
        this.il = $r1.readString();
        this.dL = $r1.readString();
        this.im = $r1.readString();
        this.eH = $r1.readString();
        this.io = $r1.readString();
        this.ip = $r1.readString();
        this.mAccountType = $r1.readString();
    }

    public AccountCredentials(String $r1) throws  {
        this.version = 2;
        this.mAccountType = zzab.zzi($r1, "Account type can't be empty.");
    }

    public Account getAccount() throws  {
        return !TextUtils.isEmpty(this.dL) ? new Account(this.dL, this.mAccountType) : null;
    }

    public String getAccountName() throws  {
        return this.dL;
    }

    public String getAccountType() throws  {
        return this.mAccountType;
    }

    public String getAuthorizationCode() throws  {
        return this.im;
    }

    public String getLongLivedLoginToken() throws  {
        return this.il;
    }

    public String getPassword() throws  {
        return this.eH;
    }

    public String getRedirectUri() throws  {
        return this.ip;
    }

    public String getVerifier() throws  {
        return this.io;
    }

    public boolean isBrowserAuthenticationRequired() throws  {
        return this.ik;
    }

    public AccountCredentials setAccountName(String $r1) throws  {
        this.dL = $r1;
        return this;
    }

    public AccountCredentials setAuthorizationCode(String $r1) throws  {
        this.im = $r1;
        return this;
    }

    public AccountCredentials setBrowserAuthenticationRequired(boolean $z0) throws  {
        this.ik = $z0;
        return this;
    }

    public AccountCredentials setLongLivedLoginToken(String $r1) throws  {
        this.il = $r1;
        return this;
    }

    public AccountCredentials setPassword(String $r1) throws  {
        this.eH = $r1;
        return this;
    }

    public AccountCredentials setRedirectUri(String $r1) throws  {
        this.ip = $r1;
        return this;
    }

    public AccountCredentials setVerifier(String $r1) throws  {
        this.io = $r1;
        return this;
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        AccountCredentialsCreator.zza(this, $r1, $i0);
    }
}
