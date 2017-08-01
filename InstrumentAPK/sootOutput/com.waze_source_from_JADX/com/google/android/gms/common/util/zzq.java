package com.google.android.gms.common.util;

/* compiled from: dalvik_source_com.waze.apk */
public class zzq {
    public static long zzhg(String $r0) throws  {
        if ($r0.length() <= 16) {
            return $r0.length() == 16 ? Long.parseLong($r0.substring(8), 16) | (Long.parseLong($r0.substring(0, 8), 16) << 32) : Long.parseLong($r0, 16);
        } else {
            throw new NumberFormatException(new StringBuilder(String.valueOf($r0).length() + 46).append("Invalid input: ").append($r0).append(" exceeds ").append(16).append(" characters").toString());
        }
    }
}
