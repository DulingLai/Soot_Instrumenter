package com.google.android.gms.auth;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

/* compiled from: dalvik_source_com.waze.apk */
public class RecoveryWriteResponse extends AbstractSafeParcelable {
    public static final RecoveryWriteResponseCreator CREATOR = new RecoveryWriteResponseCreator();
    public String mErrorCode;
    final int mVersionCode;

    public RecoveryWriteResponse() throws  {
        this.mVersionCode = 1;
    }

    RecoveryWriteResponse(int $i0, String $r1) throws  {
        this.mVersionCode = $i0;
        this.mErrorCode = $r1;
    }

    public int getVersionCode() throws  {
        return this.mVersionCode;
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        RecoveryWriteResponseCreator.zza(this, $r1, $i0);
    }
}
