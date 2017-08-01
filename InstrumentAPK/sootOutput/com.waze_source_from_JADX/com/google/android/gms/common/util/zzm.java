package com.google.android.gms.common.util;

import dalvik.annotation.Signature;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

/* compiled from: dalvik_source_com.waze.apk */
public class zzm {
    private static final Pattern MR = Pattern.compile("^(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}$");
    private static final Pattern MS = Pattern.compile("^(?:[0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}$");
    private static final Pattern MT = Pattern.compile("^((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)::((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)$");

    private static String decode(String $r0, String $r2) throws  {
        if ($r2 == null) {
            $r2 = "ISO-8859-1";
        }
        try {
            return URLDecoder.decode($r0, $r2);
        } catch (UnsupportedEncodingException $r3) {
            throw new IllegalArgumentException($r3);
        }
    }

    public static Map<String, String> zza(@Signature({"(", "Ljava/net/URI;", "Ljava/lang/String;", ")", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;"}) URI $r0, @Signature({"(", "Ljava/net/URI;", "Ljava/lang/String;", ")", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;"}) String $r1) throws  {
        Map $r3 = Collections.emptyMap();
        String $r4 = $r0.getRawQuery();
        if ($r4 == null || $r4.length() <= 0) {
            return $r3;
        }
        HashMap $r5 = new HashMap();
        Scanner $r2 = new Scanner($r4);
        $r2.useDelimiter("&");
        while ($r2.hasNext()) {
            String[] $r6 = $r2.next().split("=");
            if ($r6.length == 0 || $r6.length > 2) {
                throw new IllegalArgumentException("bad parameter");
            }
            $r4 = decode($r6[0], $r1);
            String $r8 = null;
            if ($r6.length == 2) {
                $r8 = decode($r6[1], $r1);
            }
            $r5.put($r4, $r8);
        }
        return $r5;
    }
}
