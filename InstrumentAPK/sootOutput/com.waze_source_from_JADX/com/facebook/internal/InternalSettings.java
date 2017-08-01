package com.facebook.internal;

public class InternalSettings {
    private static volatile String mCustomUserAgent;

    public static void setCustomUserAgent(String $r0) throws  {
        mCustomUserAgent = $r0;
    }

    public static String getCustomUserAgent() throws  {
        return mCustomUserAgent;
    }
}
