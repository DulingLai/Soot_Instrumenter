package com.google.android.gms.common;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.IntentSender.SendIntentException;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.Nullable;
import com.facebook.share.internal.ShareConstants;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.zzaa;
import com.waze.analytics.AnalyticsEvents;

/* compiled from: dalvik_source_com.waze.apk */
public final class ConnectionResult extends AbstractSafeParcelable {
    public static final int API_UNAVAILABLE = 16;
    public static final ConnectionResult BB = new ConnectionResult(0);
    public static final int CANCELED = 13;
    public static final Creator<ConnectionResult> CREATOR = new zzb();
    public static final int DEVELOPER_ERROR = 10;
    @Deprecated
    public static final int DRIVE_EXTERNAL_STORAGE_REQUIRED = 1500;
    public static final int INTERNAL_ERROR = 8;
    public static final int INTERRUPTED = 15;
    public static final int INVALID_ACCOUNT = 5;
    public static final int LICENSE_CHECK_FAILED = 11;
    public static final int NETWORK_ERROR = 7;
    public static final int RESOLUTION_REQUIRED = 6;
    public static final int RESTRICTED_PROFILE = 20;
    public static final int SERVICE_DISABLED = 3;
    public static final int SERVICE_INVALID = 9;
    public static final int SERVICE_MISSING = 1;
    public static final int SERVICE_MISSING_PERMISSION = 19;
    public static final int SERVICE_UPDATING = 18;
    public static final int SERVICE_VERSION_UPDATE_REQUIRED = 2;
    public static final int SIGN_IN_FAILED = 17;
    public static final int SIGN_IN_REQUIRED = 4;
    public static final int SUCCESS = 0;
    public static final int TIMEOUT = 14;
    private final String BC;
    private final PendingIntent mPendingIntent;
    final int mVersionCode;
    private final int xL;

    public ConnectionResult(int $i0) throws  {
        this($i0, null, null);
    }

    ConnectionResult(int $i0, int $i1, PendingIntent $r1, String $r2) throws  {
        this.mVersionCode = $i0;
        this.xL = $i1;
        this.mPendingIntent = $r1;
        this.BC = $r2;
    }

    public ConnectionResult(int $i0, PendingIntent $r1) throws  {
        this($i0, $r1, null);
    }

    public ConnectionResult(int $i0, PendingIntent $r1, String $r2) throws  {
        this(1, $i0, $r1, $r2);
    }

    static String getStatusString(int $i0) throws  {
        switch ($i0) {
            case -1:
                return "UNKNOWN";
            case 0:
                return AnalyticsEvents.ANALYTICS_EVENT_SUCCESS;
            case 1:
                return "SERVICE_MISSING";
            case 2:
                return "SERVICE_VERSION_UPDATE_REQUIRED";
            case 3:
                return "SERVICE_DISABLED";
            case 4:
                return "SIGN_IN_REQUIRED";
            case 5:
                return "INVALID_ACCOUNT";
            case 6:
                return "RESOLUTION_REQUIRED";
            case 7:
                return AnalyticsEvents.ANALYTICS_EVENT_NET_ERROR;
            case 8:
                return "INTERNAL_ERROR";
            case 9:
                return "SERVICE_INVALID";
            case 10:
                return "DEVELOPER_ERROR";
            case 11:
                return "LICENSE_CHECK_FAILED";
            case 13:
                return AnalyticsEvents.ANALYTICS_EVENT_VALUE_CANCELED;
            case 14:
                return "TIMEOUT";
            case 15:
                return "INTERRUPTED";
            case 16:
                return "API_UNAVAILABLE";
            case 17:
                return "SIGN_IN_FAILED";
            case 18:
                return "SERVICE_UPDATING";
            case 19:
                return "SERVICE_MISSING_PERMISSION";
            case 20:
                return "RESTRICTED_PROFILE";
            case 21:
                return "API_VERSION_UPDATE_REQUIRED";
            case 42:
                return "UPDATE_ANDROID_WEAR";
            case 99:
                return "UNFINISHED";
            case 1500:
                return "DRIVE_EXTERNAL_STORAGE_REQUIRED";
            default:
                return "UNKNOWN_ERROR_CODE(" + $i0 + ")";
        }
    }

    public boolean equals(Object $r1) throws  {
        if ($r1 == this) {
            return true;
        }
        if (!($r1 instanceof ConnectionResult)) {
            return false;
        }
        ConnectionResult $r2 = (ConnectionResult) $r1;
        return this.xL == $r2.xL && zzaa.equal(this.mPendingIntent, $r2.mPendingIntent) && zzaa.equal(this.BC, $r2.BC);
    }

    public int getErrorCode() throws  {
        return this.xL;
    }

    @Nullable
    public String getErrorMessage() throws  {
        return this.BC;
    }

    @Nullable
    public PendingIntent getResolution() throws  {
        return this.mPendingIntent;
    }

    public boolean hasResolution() throws  {
        return (this.xL == 0 || this.mPendingIntent == null) ? false : true;
    }

    public int hashCode() throws  {
        return zzaa.hashCode(Integer.valueOf(this.xL), this.mPendingIntent, this.BC);
    }

    public boolean isSuccess() throws  {
        return this.xL == 0;
    }

    public void startResolutionForResult(Activity $r1, int $i0) throws SendIntentException {
        if (hasResolution()) {
            $r1.startIntentSenderForResult(this.mPendingIntent.getIntentSender(), $i0, null, 0, 0, 0);
        }
    }

    public String toString() throws  {
        String str = "resolution";
        return zzaa.zzaf(this).zzh("statusCode", getStatusString(this.xL)).zzh(str, this.mPendingIntent).zzh(ShareConstants.WEB_DIALOG_PARAM_MESSAGE, this.BC).toString();
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        zzb.zza(this, $r1, $i0);
    }
}
