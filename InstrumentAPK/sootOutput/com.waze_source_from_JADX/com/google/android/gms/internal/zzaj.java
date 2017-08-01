package com.google.android.gms.internal;

import android.util.Base64;

/* compiled from: dalvik_source_com.waze.apk */
public final class zzaj {
    public static String zza(byte[] $r0, boolean $z0) throws  {
        return Base64.encodeToString($r0, $z0 ? (byte) 11 : (byte) 2);
    }

    public static byte[] zza(String $r0, boolean $z0) throws IllegalArgumentException {
        byte[] $r2 = Base64.decode($r0, $z0 ? (byte) 11 : (byte) 2);
        if ($r2.length != 0 || $r0.length() <= 0) {
            return $r2;
        }
        String $r1 = "Unable to decode ";
        $r0 = String.valueOf($r0);
        throw new IllegalArgumentException($r0.length() != 0 ? $r1.concat($r0) : new String("Unable to decode "));
    }
}
