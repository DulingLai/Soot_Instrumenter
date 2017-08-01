package com.google.android.gms.auth.firstparty.dataservice;

import android.os.Parcel;
import com.google.android.gms.auth.RecoveryResponse;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

/* compiled from: dalvik_source_com.waze.apk */
public class AccountRecoveryUpdateResult extends AbstractSafeParcelable implements RecoveryResponse {
    public static final AccountRecoveryUpdateResultCreator CREATOR = new AccountRecoveryUpdateResultCreator();
    public final String error;
    final int version;

    AccountRecoveryUpdateResult(int $i0, String $r1) throws  {
        this.version = $i0;
        this.error = $r1;
    }

    public AccountRecoveryUpdateResult(String $r1) throws  {
        this(0, $r1);
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        AccountRecoveryUpdateResultCreator.zza(this, $r1, $i0);
    }
}
