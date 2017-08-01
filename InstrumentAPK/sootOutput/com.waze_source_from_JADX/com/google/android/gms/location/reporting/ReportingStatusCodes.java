package com.google.android.gms.location.reporting;

import com.google.android.gms.common.api.CommonStatusCodes;

/* compiled from: dalvik_source_com.waze.apk */
public class ReportingStatusCodes extends CommonStatusCodes {
    public static final int CALLER_NON_GOOGLE = 3501;
    public static final int CALLER_NOT_AUTHORIZED = 3500;
    public static final int OPTIN_TAG_TOO_LONG = 3511;
    @Deprecated
    public static final int OPT_IN_COMMUNICATION_FAILURE = 3503;
    public static final int REPORTING_CAN_NOT_BE_ACTIVATED = 3502;
    public static final int REPORTING_NOT_ACTIVE = 3504;
    public static final int SETTINGS_CANNOT_BE_CHANGED = 3510;
    public static final int TOO_MANY_UPLOAD_REQUESTS = 3509;
    public static final int UNKNOWN_ERROR = 3507;
    public static final int UPLOAD_DURATION_TOO_LONG = 3505;
    public static final int UPLOAD_REQUEST_ID_NOT_FOUND = 3506;
    public static final int UPLOAD_REQUEST_INVALID = 3508;

    private ReportingStatusCodes() throws  {
    }

    public static String getStatusCodeString(int $i0) throws  {
        switch ($i0) {
            case 3500:
                return "CALLER_NOT_AUTHORIZED";
            case 3501:
                return "CALLER_NON_GOOGLE";
            case 3502:
                return "REPORTING_CAN_NOT_BE_ACTIVATED";
            case 3503:
                return "OPT_IN_COMMUNICATION_FAILURE";
            case 3504:
                return "REPORTING_NOT_ACTIVE";
            case 3505:
                return "UPLOAD_DURATION_TOO_LONG";
            case 3506:
                return "UPLOAD_REQUEST_ID_NOT_FOUND";
            case 3507:
                return "UNKNOWN_ERROR";
            case 3508:
                return "UPLOAD_REQUEST_INVALID";
            case 3509:
                return "TOO_MANY_UPLOAD_REQUESTS";
            case 3510:
                return "SETTINGS_CANNOT_BE_CHANGED";
            case 3511:
                return "OPTIN_TAG_TOO_LONG";
            default:
                return CommonStatusCodes.getStatusCodeString($i0);
        }
    }
}
