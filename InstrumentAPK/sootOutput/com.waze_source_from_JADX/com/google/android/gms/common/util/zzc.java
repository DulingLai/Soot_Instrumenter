package com.google.android.gms.common.util;

import android.util.Base64;

/* compiled from: dalvik_source_com.waze.apk */
public final class zzc {
    public static byte[] decode(String $r0) throws  {
        return $r0 == null ? null : Base64.decode($r0, 0);
    }

    public static String encode(byte[] $r0) throws  {
        return $r0 == null ? null : Base64.encodeToString($r0, 0);
    }

    public static byte[] zzhd(String $r0) throws  {
        return $r0 == null ? null : Base64.decode($r0, 10);
    }

    public static String zzq(byte[] $r0) throws  {
        return $r0 == null ? null : Base64.encodeToString($r0, 10);
    }

    public static String zzr(byte[] $r0) throws  {
        return $r0 == null ? null : Base64.encodeToString($r0, 11);
    }
}
