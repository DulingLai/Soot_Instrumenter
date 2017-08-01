package com.google.android.gms.common.util;

import android.database.CharArrayBuffer;
import android.text.TextUtils;

/* compiled from: dalvik_source_com.waze.apk */
public final class zzf {
    public static void zzb(String $r0, CharArrayBuffer $r1) throws  {
        if (TextUtils.isEmpty($r0)) {
            $r1.sizeCopied = 0;
        } else if ($r1.data == null || $r1.data.length < $r0.length()) {
            $r1.data = $r0.toCharArray();
        } else {
            $r0.getChars(0, $r0.length(), $r1.data, 0);
        }
        $r1.sizeCopied = $r0.length();
    }
}
