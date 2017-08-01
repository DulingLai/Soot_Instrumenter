package com.google.android.gms.auth.frp;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

/* compiled from: dalvik_source_com.waze.apk */
public class UnlockFactoryResetProtectionRequest extends AbstractSafeParcelable {
    public static final UnlockFactoryResetProtectionRequestCreator CREATOR = new UnlockFactoryResetProtectionRequestCreator();
    public final String accountName;
    public final String accountType;
    public final String password;
    final int version;

    UnlockFactoryResetProtectionRequest(int $i0, String $r1, String $r2, String $r3) throws  {
        this.version = $i0;
        this.accountName = $r1;
        this.password = $r2;
        this.accountType = $r3;
    }

    public static final UnlockFactoryResetProtectionRequest from(String $r0, String $r1) throws  {
        return new UnlockFactoryResetProtectionRequest(1, $r0, $r1, null);
    }

    public static final UnlockFactoryResetProtectionRequest from(String $r0, String $r1, String $r2) throws  {
        return new UnlockFactoryResetProtectionRequest(1, $r0, $r1, $r2);
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        UnlockFactoryResetProtectionRequestCreator.zza(this, $r1, $i0);
    }
}
