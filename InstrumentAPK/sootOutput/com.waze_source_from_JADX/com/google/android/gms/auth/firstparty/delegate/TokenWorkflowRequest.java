package com.google.android.gms.auth.firstparty.delegate;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.os.Bundle;
import android.os.Parcel;
import android.text.TextUtils;
import com.google.android.gms.auth.firstparty.shared.AppDescription;
import com.google.android.gms.auth.firstparty.shared.FACLConfig;
import com.google.android.gms.auth.firstparty.shared.PACLConfig;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

/* compiled from: dalvik_source_com.waze.apk */
public class TokenWorkflowRequest extends AbstractSafeParcelable {
    public static final TokenWorkflowRequestCreator CREATOR = new TokenWorkflowRequestCreator();
    Account account;
    @Deprecated
    String accountName;
    AccountAuthenticatorResponse amResponse;
    AppDescription callingAppDescription;
    FACLConfig hA;
    PACLConfig hB;
    boolean hX;
    String hz;
    Bundle options;
    final int version;

    public TokenWorkflowRequest() throws  {
        this.version = 2;
        this.options = new Bundle();
    }

    TokenWorkflowRequest(int $i0, String $r1, String $r2, Bundle $r3, FACLConfig $r4, PACLConfig $r5, boolean $z0, AppDescription $r6, Account $r7, AccountAuthenticatorResponse $r8) throws  {
        this.version = $i0;
        this.hz = $r1;
        this.accountName = $r2;
        this.options = $r3;
        this.hA = $r4;
        this.hB = $r5;
        this.hX = $z0;
        this.callingAppDescription = $r6;
        if ($r7 != null || TextUtils.isEmpty($r2)) {
            this.account = $r7;
        } else {
            this.account = new Account($r2, "com.google");
        }
        this.amResponse = $r8;
    }

    public Account getAccount() throws  {
        return this.account;
    }

    @Deprecated
    public String getAccountName() throws  {
        return this.accountName;
    }

    public AccountAuthenticatorResponse getAmResponse() throws  {
        return this.amResponse;
    }

    public AppDescription getCallingAppDescription() throws  {
        return this.callingAppDescription;
    }

    public FACLConfig getFaclData() throws  {
        return this.hA;
    }

    public Bundle getOptions() throws  {
        return new Bundle(this.options);
    }

    public PACLConfig getPaclData() throws  {
        return this.hB;
    }

    public String getService() throws  {
        return this.hz;
    }

    public boolean isSuppressingProgressUx() throws  {
        return this.hX;
    }

    public TokenWorkflowRequest setAccount(Account $r1) throws  {
        this.accountName = $r1 == null ? null : $r1.name;
        this.account = $r1;
        return this;
    }

    @Deprecated
    public TokenWorkflowRequest setAccountName(@Deprecated String $r1) throws  {
        this.account = TextUtils.isEmpty($r1) ? null : new Account($r1, "com.google");
        this.accountName = $r1;
        return this;
    }

    public TokenWorkflowRequest setAmResponse(AccountAuthenticatorResponse $r1) throws  {
        this.amResponse = $r1;
        return this;
    }

    public TokenWorkflowRequest setCallingAppDescription(AppDescription $r1) throws  {
        this.callingAppDescription = $r1;
        return this;
    }

    public TokenWorkflowRequest setFaclData(FACLConfig $r1) throws  {
        this.hA = $r1;
        return this;
    }

    public TokenWorkflowRequest setOptions(Bundle $r1) throws  {
        this.options.clear();
        if ($r1 == null) {
            return this;
        }
        this.options.putAll($r1);
        return this;
    }

    public TokenWorkflowRequest setPaclData(PACLConfig $r1) throws  {
        this.hB = $r1;
        return this;
    }

    public TokenWorkflowRequest setService(String $r1) throws  {
        this.hz = $r1;
        return this;
    }

    public TokenWorkflowRequest setSuppressingProgressUx(boolean $z0) throws  {
        this.hX = $z0;
        return this;
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        TokenWorkflowRequestCreator.zza(this, $r1, $i0);
    }
}
