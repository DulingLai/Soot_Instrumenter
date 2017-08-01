package com.google.android.gms.auth.firstparty.delegate;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.os.Bundle;
import android.os.Parcel;
import android.text.TextUtils;
import com.google.android.gms.auth.firstparty.shared.AppDescription;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

/* compiled from: dalvik_source_com.waze.apk */
public class ConfirmCredentialsWorkflowRequest extends AbstractSafeParcelable {
    public static final ConfirmCredentialsWorkflowRequestCreator CREATOR = new ConfirmCredentialsWorkflowRequestCreator();
    Account account;
    @Deprecated
    String accountName;
    AccountAuthenticatorResponse amResponse;
    AppDescription callingAppDescription;
    Bundle options;
    final int version;

    public ConfirmCredentialsWorkflowRequest() throws  {
        this.version = 3;
        this.options = new Bundle();
    }

    ConfirmCredentialsWorkflowRequest(int $i0, String $r1, AppDescription $r2, Bundle $r3, Account $r4, AccountAuthenticatorResponse $r5) throws  {
        this.version = $i0;
        this.accountName = $r1;
        this.callingAppDescription = $r2;
        this.options = $r3;
        if ($r4 != null || TextUtils.isEmpty($r1)) {
            this.account = $r4;
        } else {
            this.account = new Account($r1, "com.google");
        }
        this.amResponse = $r5;
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

    public Bundle getOptions() throws  {
        return new Bundle(this.options);
    }

    public ConfirmCredentialsWorkflowRequest setAccount(Account $r1) throws  {
        this.accountName = $r1 == null ? null : $r1.name;
        this.account = $r1;
        return this;
    }

    @Deprecated
    public ConfirmCredentialsWorkflowRequest setAccountName(@Deprecated String $r1) throws  {
        this.account = TextUtils.isEmpty($r1) ? null : new Account($r1, "com.google");
        this.accountName = $r1;
        return this;
    }

    public ConfirmCredentialsWorkflowRequest setAmResponse(AccountAuthenticatorResponse $r1) throws  {
        this.amResponse = $r1;
        return this;
    }

    public ConfirmCredentialsWorkflowRequest setCallingAppDescription(AppDescription $r1) throws  {
        this.callingAppDescription = $r1;
        return this;
    }

    public ConfirmCredentialsWorkflowRequest setOptions(Bundle $r1) throws  {
        this.options.clear();
        if ($r1 == null) {
            return this;
        }
        this.options.putAll($r1);
        return this;
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        ConfirmCredentialsWorkflowRequestCreator.zza(this, $r1, $i0);
    }
}
