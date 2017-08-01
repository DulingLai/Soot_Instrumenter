package com.google.android.gms.location;

import com.google.android.gms.common.api.CommonStatusCodes;

/* compiled from: dalvik_source_com.waze.apk */
public final class GeofenceStatusCodes extends CommonStatusCodes {
    public static final int GEOFENCE_NOT_AVAILABLE = 1000;
    public static final int GEOFENCE_TOO_MANY_GEOFENCES = 1001;
    public static final int GEOFENCE_TOO_MANY_PENDING_INTENTS = 1002;

    private GeofenceStatusCodes() throws  {
    }

    public static String getStatusCodeString(int $i0) throws  {
        switch ($i0) {
            case 1000:
                return "GEOFENCE_NOT_AVAILABLE";
            case 1001:
                return "GEOFENCE_TOO_MANY_GEOFENCES";
            case 1002:
                return "GEOFENCE_TOO_MANY_PENDING_INTENTS";
            default:
                return CommonStatusCodes.getStatusCodeString($i0);
        }
    }
}
