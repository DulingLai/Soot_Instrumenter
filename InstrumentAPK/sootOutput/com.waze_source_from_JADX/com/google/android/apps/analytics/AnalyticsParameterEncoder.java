package com.google.android.apps.analytics;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/* compiled from: dalvik_source_com.waze.apk */
public class AnalyticsParameterEncoder {
    private AnalyticsParameterEncoder() throws  {
    }

    public static String encode(String $r0) throws  {
        return encode($r0, "UTF-8");
    }

    static String encode(String $r0, String $r1) throws  {
        try {
            $r0 = URLEncoder.encode($r0, $r1).replace("+", "%20");
            return $r0;
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError("URL encoding failed for: " + $r0);
        }
    }
}
