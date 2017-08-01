package com.google.android.gms.common.api;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.IntentSender.SendIntentException;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.Nullable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.zzaa;

/* compiled from: dalvik_source_com.waze.apk */
public final class Status extends AbstractSafeParcelable implements Result {
    public static final Status CQ = new Status(0);
    public static final Status CR = new Status(14);
    public static final Creator<Status> CREATOR = new zze();
    public static final Status CS = new Status(8);
    public static final Status CT = new Status(15);
    public static final Status CU = new Status(16);
    public static final Status CV = new Status(17);
    public static final Status CX = new Status(18);
    private final String BC;
    private final PendingIntent mPendingIntent;
    private final int mVersionCode;
    private final int xL;

    public Status(int $i0) throws  {
        this($i0, null);
    }

    Status(int $i0, int $i1, String $r1, PendingIntent $r2) throws  {
        this.mVersionCode = $i0;
        this.xL = $i1;
        this.BC = $r1;
        this.mPendingIntent = $r2;
    }

    public Status(int $i0, String $r1) throws  {
        this(1, $i0, $r1, null);
    }

    public Status(int $i0, String $r1, PendingIntent $r2) throws  {
        this(1, $i0, $r1, $r2);
    }

    private String zzasd() throws  {
        return this.BC != null ? this.BC : CommonStatusCodes.getStatusCodeString(this.xL);
    }

    public boolean equals(Object $r1) throws  {
        if (!($r1 instanceof Status)) {
            return false;
        }
        Status $r2 = (Status) $r1;
        return this.mVersionCode == $r2.mVersionCode ? this.xL == $r2.xL ? zzaa.equal(this.BC, $r2.BC) ? zzaa.equal(this.mPendingIntent, $r2.mPendingIntent) : false : false : false;
    }

    PendingIntent getPendingIntent() throws  {
        return this.mPendingIntent;
    }

    public PendingIntent getResolution() throws  {
        return this.mPendingIntent;
    }

    public Status getStatus() throws  {
        return this;
    }

    public int getStatusCode() throws  {
        return this.xL;
    }

    @Nullable
    public String getStatusMessage() throws  {
        return this.BC;
    }

    int getVersionCode() throws  {
        return this.mVersionCode;
    }

    public boolean hasResolution() throws  {
        return this.mPendingIntent != null;
    }

    public int hashCode() throws  {
        return zzaa.hashCode(Integer.valueOf(this.mVersionCode), Integer.valueOf(this.xL), this.BC, this.mPendingIntent);
    }

    public boolean isCanceled() throws  {
        return this.xL == 16;
    }

    public boolean isInterrupted() throws  {
        return this.xL == 14;
    }

    public boolean isSuccess() throws  {
        return this.xL <= 0;
    }

    public void startResolutionForResult(Activity $r1, int $i0) throws SendIntentException {
        if (hasResolution()) {
            $r1.startIntentSenderForResult(this.mPendingIntent.getIntentSender(), $i0, null, 0, 0, 0);
        }
    }

    public String toString() throws  {
        return zzaa.zzaf(this).zzh("statusCode", zzasd()).zzh("resolution", this.mPendingIntent).toString();
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        zze.zza(this, $r1, $i0);
    }
}
