package com.google.android.gms.auth.firstparty.dataservice;

import android.os.Parcel;
import com.google.android.gms.auth.firstparty.shared.AccountCredentials;
import com.google.android.gms.auth.firstparty.shared.AppDescription;
import com.google.android.gms.auth.firstparty.shared.CaptchaSolution;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

/* compiled from: dalvik_source_com.waze.apk */
public class AccountSignInRequest extends AbstractSafeParcelable {
    public static final AccountSignInRequestCreator CREATOR = new AccountSignInRequestCreator();
    AppDescription callingAppDescription;
    boolean gK;
    boolean gL;
    AccountCredentials gM;
    CaptchaSolution gq;
    final int version;

    public AccountSignInRequest() throws  {
        this.version = 1;
    }

    AccountSignInRequest(int $i0, AppDescription $r1, boolean $z0, boolean $z1, CaptchaSolution $r2, AccountCredentials $r3) throws  {
        this.version = $i0;
        this.callingAppDescription = $r1;
        this.gK = $z0;
        this.gL = $z1;
        this.gq = $r2;
        this.gM = $r3;
    }

    public AccountCredentials getAccountCredentials() throws  {
        return this.gM;
    }

    public AppDescription getCallingAppDescription() throws  {
        return this.callingAppDescription;
    }

    public CaptchaSolution getCaptchaSolution() throws  {
        return this.gq;
    }

    public boolean isAccountCreationInProgress() throws  {
        return this.gK;
    }

    public boolean isSetupWizardInProgress() throws  {
        return this.gL;
    }

    public AccountSignInRequest setAccountCreationInProgress(boolean $z0) throws  {
        this.gK = $z0;
        return this;
    }

    public AccountSignInRequest setAccountCredentials(AccountCredentials $r1) throws  {
        this.gM = $r1;
        return this;
    }

    public AccountSignInRequest setBackupAccount(boolean $z0) throws  {
        this.gL = $z0;
        return this;
    }

    public AccountSignInRequest setCallingAppDescription(AppDescription $r1) throws  {
        this.callingAppDescription = $r1;
        return this;
    }

    public AccountSignInRequest setCaptchaSolution(CaptchaSolution $r1) throws  {
        this.gq = $r1;
        return this;
    }

    @Deprecated
    public AccountSignInRequest setSetupWizardInProgress(@Deprecated boolean $z0) throws  {
        this.gL = $z0;
        return this;
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        AccountSignInRequestCreator.zza(this, $r1, $i0);
    }
}
