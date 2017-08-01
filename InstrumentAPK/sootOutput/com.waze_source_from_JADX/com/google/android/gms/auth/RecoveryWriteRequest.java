package com.google.android.gms.auth;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

/* compiled from: dalvik_source_com.waze.apk */
public class RecoveryWriteRequest extends AbstractSafeParcelable {
    public static final RecoveryWriteRequestCreator CREATOR = new RecoveryWriteRequestCreator();
    public String mAccount;
    public boolean mIsBroadUse;
    public String mPhoneCountryCode;
    public String mPhoneNumber;
    public String mSecondaryEmail;
    final int mVersionCode;

    public RecoveryWriteRequest() throws  {
        this.mVersionCode = 1;
    }

    RecoveryWriteRequest(int $i0, String $r1, String $r2, String $r3, String $r4, boolean $z0) throws  {
        this.mVersionCode = $i0;
        this.mAccount = $r1;
        this.mSecondaryEmail = $r2;
        this.mPhoneNumber = $r3;
        this.mPhoneCountryCode = $r4;
        this.mIsBroadUse = $z0;
    }

    public int getVersionCode() throws  {
        return this.mVersionCode;
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        RecoveryWriteRequestCreator.zza(this, $r1, $i0);
    }
}
