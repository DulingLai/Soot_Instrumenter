package com.google.android.gms.location;

import com.google.android.gms.common.api.Status;

@Deprecated
/* compiled from: dalvik_source_com.waze.apk */
public final class LocationStatusCodes {
    public static final int ERROR = 1;
    public static final int GEOFENCE_NOT_AVAILABLE = 1000;
    public static final int GEOFENCE_TOO_MANY_GEOFENCES = 1001;
    public static final int GEOFENCE_TOO_MANY_PENDING_INTENTS = 1002;
    public static final int SUCCESS = 0;

    private LocationStatusCodes() throws  {
    }

    public static int zzwm(int $i0) throws  {
        return ($i0 < 0 || $i0 > 1) ? (1000 > $i0 || $i0 > 1002) ? 1 : $i0 : $i0;
    }

    public static Status zzwn(int $i0) throws  {
        switch ($i0) {
            case 1:
                $i0 = 13;
                break;
            default:
                break;
        }
        return new Status($i0);
    }
}
