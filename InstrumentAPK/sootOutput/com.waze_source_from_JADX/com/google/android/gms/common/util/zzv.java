package com.google.android.gms.common.util;

import com.google.android.gms.common.internal.zzf;
import java.util.regex.Pattern;

/* compiled from: dalvik_source_com.waze.apk */
public class zzv {
    private static final Pattern MX = Pattern.compile("\\$\\{(.*?)\\}");

    public static boolean zzhh(String $r0) throws  {
        return $r0 == null || zzf.IO.zzb($r0);
    }
}
