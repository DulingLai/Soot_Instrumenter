package com.google.android.gms.location.reporting;

/* compiled from: dalvik_source_com.waze.apk */
public class OptInResult {
    public static final int ACCOUNT_MISSING = 2;
    public static final int ACCOUNT_NOT_VALID = 3;
    public static final int CAN_NOT_BE_ACTIVATED = 7;
    @Deprecated
    public static final int COMMUNICATION_FAILURE = 8;
    public static final int REMOTE_EXCEPTION = 9;
    @Deprecated
    public static final int REPORTING_NOT_ALLOWED = 1;
    public static final int SENDER_MISSING = 4;
    public static final int SENDER_NON_GOOGLE = 6;
    public static final int SENDER_NOT_AUTHORIZED = 5;
    public static final int SETTINGS_CANNOT_BE_CHANGED = 10;
    public static final int SUCCESS = 0;
    public static final int TAG_TOO_LONG = 11;
    public static final int UNKNOWN = 1;

    private OptInResult() throws  {
    }

    public static int sanitize(int $i0) throws  {
        switch ($i0) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
                return $i0;
            default:
                return 1;
        }
    }
}
