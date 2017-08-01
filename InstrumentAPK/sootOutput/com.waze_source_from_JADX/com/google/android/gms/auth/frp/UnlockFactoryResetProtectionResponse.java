package com.google.android.gms.auth.frp;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

/* compiled from: dalvik_source_com.waze.apk */
public class UnlockFactoryResetProtectionResponse extends AbstractSafeParcelable {
    public static final UnlockFactoryResetProtectionResponseCreator CREATOR = new UnlockFactoryResetProtectionResponseCreator();
    public static final int STATUS_ERROR_INVALID_CREDENTIALS = 3;
    public static final int STATUS_ERROR_NETWORK = 2;
    public static final int STATUS_ERROR_NOT_COMPLIANT = 4;
    public static final int STATUS_ERROR_UNKNOWN = 1;
    public static final int STATUS_OK = 0;
    public final int status;
    final int version;

    public UnlockFactoryResetProtectionResponse(int $i0) throws  {
        this(1, $i0);
    }

    UnlockFactoryResetProtectionResponse(int $i0, int $i1) throws  {
        this.version = $i0;
        this.status = $i1;
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        UnlockFactoryResetProtectionResponseCreator.zza(this, $r1, $i0);
    }
}
