package com.google.android.gms.common.util;

import android.text.TextUtils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* compiled from: dalvik_source_com.waze.apk */
public class zzy {
    private static final Pattern MY = Pattern.compile("\\\\u[0-9a-fA-F]{4}");

    public static String unescape(String $r0) throws  {
        if (TextUtils.isEmpty($r0)) {
            return $r0;
        }
        Matcher $r2 = MY.matcher($r0);
        StringBuffer $r3 = null;
        while ($r2.find()) {
            if ($r3 == null) {
                $r3 = new StringBuffer();
            }
            $r2.appendReplacement($r3, new String(Character.toChars(Integer.parseInt($r2.group().substring(2), 16))));
        }
        if ($r3 == null) {
            return $r0;
        }
        $r2.appendTail($r3);
        return $r3.toString();
    }
}
