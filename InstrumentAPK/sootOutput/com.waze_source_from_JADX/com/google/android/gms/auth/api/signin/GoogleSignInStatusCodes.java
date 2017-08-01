package com.google.android.gms.auth.api.signin;

import com.google.android.gms.common.api.CommonStatusCodes;

/* compiled from: dalvik_source_com.waze.apk */
public final class GoogleSignInStatusCodes extends CommonStatusCodes {
    public static final int SIGN_IN_CANCELLED = 12501;
    public static final int SIGN_IN_FAILED = 12500;

    private GoogleSignInStatusCodes() throws  {
    }

    public static String getStatusCodeString(int $i0) throws  {
        switch ($i0) {
            case SIGN_IN_FAILED /*12500*/:
                return "A non-recoverable sign in failure occurred";
            default:
                return CommonStatusCodes.getStatusCodeString($i0);
        }
    }
}
