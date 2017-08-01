package com.google.android.gms.auth.firstparty.dataservice;

import android.os.Parcel;
import com.google.android.gms.auth.firstparty.shared.AccountCredentials;
import com.google.android.gms.auth.firstparty.shared.CaptchaSolution;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

/* compiled from: dalvik_source_com.waze.apk */
public class UpdateCredentialsRequest extends AbstractSafeParcelable {
    public static final UpdateCredentialsRequestCreator CREATOR = new UpdateCredentialsRequestCreator();
    AccountCredentials gM;
    CaptchaSolution gq;
    final int version;

    public UpdateCredentialsRequest() throws  {
        this.version = 1;
    }

    UpdateCredentialsRequest(int $i0, AccountCredentials $r1, CaptchaSolution $r2) throws  {
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

    public UpdateCredentialsRequest setAccountCredentials(AccountCredentials $r1) throws  {
        this.gM = $r1;
        return this;
    }

    public UpdateCredentialsRequest setCaptchaSolution(CaptchaSolution $r1) throws  {
        this.gq = $r1;
        return this;
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        UpdateCredentialsRequestCreator.zza(this, $r1, $i0);
    }
}
