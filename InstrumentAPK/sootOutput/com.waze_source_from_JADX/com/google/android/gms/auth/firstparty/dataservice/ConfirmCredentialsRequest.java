package com.google.android.gms.auth.firstparty.dataservice;

import android.os.Parcel;
import com.google.android.gms.auth.firstparty.shared.AccountCredentials;
import com.google.android.gms.auth.firstparty.shared.CaptchaSolution;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

/* compiled from: dalvik_source_com.waze.apk */
public class ConfirmCredentialsRequest extends AbstractSafeParcelable {
    public static final ConfirmCredentialsRequestCreator CREATOR = new ConfirmCredentialsRequestCreator();
    AccountCredentials gM;
    CaptchaSolution gq;
    final int version;

    public ConfirmCredentialsRequest() throws  {
        this.version = 1;
    }

    ConfirmCredentialsRequest(int $i0, AccountCredentials $r1, CaptchaSolution $r2) throws  {
        this.version = $i0;
        this.gM = $r1;
        this.gq = $r2;
    }

    public AccountCredentials getAccountCredentials() throws  {
        return this.gM;
    }

    public CaptchaSolution getCaptchaSolution() throws  {
        return this.gq;
    }

    public ConfirmCredentialsRequest setAccountCredentials(AccountCredentials $r1) throws  {
        this.gM = $r1;
        return this;
    }

    public ConfirmCredentialsRequest setCaptchaSolution(CaptchaSolution $r1) throws  {
        this.gq = $r1;
        return this;
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        ConfirmCredentialsRequestCreator.zza(this, $r1, $i0);
    }
}
