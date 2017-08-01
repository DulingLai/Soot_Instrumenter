package com.google.android.gms.location.reporting;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.zzaa;

/* compiled from: dalvik_source_com.waze.apk */
public final class UploadRequestResult extends AbstractSafeParcelable {
    public static final int CALLER_NOT_AUTHORIZED = 4;
    public static final UploadRequestResultCreator CREATOR = new UploadRequestResultCreator();
    public static final int DURATION_TOO_LONG = 2;
    @Deprecated
    public static final int EXPIRATION_TOO_LATE = 2;
    public static final long FAILURE_REQUEST_ID = -1;
    public static final int ID_NOT_FOUND = 100;
    public static final int INVALID_REQUEST = 5;
    public static final int REPORTING_NOT_ACTIVE = 3;
    public static final int SUCCESS = 0;
    public static final int TOO_MANY_REQUESTS = 6;
    private final int mResultCode;
    private final int mVersionCode;
    private final long xG;

    UploadRequestResult(int $i0, int $i1, long $l2) throws  {
        this.mVersionCode = $i0;
        this.mResultCode = $i1;
        this.xG = $l2;
    }

    public UploadRequestResult(int $i0, long $l1) throws  {
        this(1, $i0, $l1);
    }

    public boolean equals(Object $r1) throws  {
        if (!($r1 instanceof UploadRequestResult)) {
            return false;
        }
        UploadRequestResult $r2 = (UploadRequestResult) $r1;
        return this.xG == $r2.xG ? this.mResultCode == $r2.mResultCode : false;
    }

    public long getRequestId() throws  {
        return this.xG;
    }

    public int getResultCode() throws  {
        return this.mResultCode;
    }

    int getVersionCode() throws  {
        return this.mVersionCode;
    }

    public int hashCode() throws  {
        return zzaa.hashCode(Integer.valueOf(this.mResultCode), Long.valueOf(this.xG));
    }

    public String toString() throws  {
        int $i1 = this.mVersionCode;
        int $i2 = this.mResultCode;
        return "Result{mVersionCode=" + $i1 + ", mResultCode=" + $i2 + ", mRequestId=" + this.xG + "}";
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        UploadRequestResultCreator $r2 = CREATOR;
        UploadRequestResultCreator.zza(this, $r1, $i0);
    }
}
