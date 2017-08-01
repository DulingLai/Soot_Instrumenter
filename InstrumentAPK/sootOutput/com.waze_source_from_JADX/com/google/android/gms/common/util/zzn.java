package com.google.android.gms.common.util;

import android.text.TextUtils;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: dalvik_source_com.waze.apk */
public final class zzn {
    private static final Pattern MU = Pattern.compile("\\\\.");
    private static final Pattern MV = Pattern.compile("[\\\\\"/\b\f\n\r\t]");

    public static boolean zzf(Object $r0, Object $r1) throws  {
        if ($r0 == null && $r1 == null) {
            return true;
        }
        if ($r0 == null) {
            return false;
        }
        if ($r1 == null) {
            return false;
        }
        if (($r0 instanceof JSONObject) && ($r1 instanceof JSONObject)) {
            JSONObject $r2 = (JSONObject) $r0;
            JSONObject $r3 = (JSONObject) $r1;
            if ($r2.length() != $r3.length()) {
                return false;
            }
            Iterator $r4 = $r2.keys();
            while ($r4.hasNext()) {
                String $r5 = (String) $r4.next();
                if (!$r3.has($r5)) {
                    return false;
                }
                try {
                    if (!zzf($r2.get($r5), $r3.get($r5))) {
                        return false;
                    }
                } catch (JSONException e) {
                    return false;
                }
            }
            return true;
        } else if (!($r0 instanceof JSONArray) || !($r1 instanceof JSONArray)) {
            return $r0.equals($r1);
        } else {
            JSONArray $r6 = (JSONArray) $r0;
            JSONArray $r7 = (JSONArray) $r1;
            if ($r6.length() != $r7.length()) {
                return false;
            }
            int $i0 = 0;
            while ($i0 < $r6.length()) {
                try {
                    if (!zzf($r6.get($i0), $r7.get($i0))) {
                        return false;
                    }
                    $i0++;
                } catch (JSONException e2) {
                    return false;
                }
            }
            return true;
        }
    }

    public static String zzhe(String $r0) throws  {
        if (TextUtils.isEmpty($r0)) {
            return $r0;
        }
        $r0 = zzy.unescape($r0);
        Matcher $r2 = MU.matcher($r0);
        StringBuffer $r3 = null;
        while ($r2.find()) {
            if ($r3 == null) {
                $r3 = new StringBuffer();
            }
            switch ($r2.group().charAt(1)) {
                case '\"':
                    $r2.appendReplacement($r3, "\"");
                    break;
                case '/':
                    $r2.appendReplacement($r3, "/");
                    break;
                case '\\':
                    $r2.appendReplacement($r3, "\\\\");
                    break;
                case 'b':
                    $r2.appendReplacement($r3, "\b");
                    break;
                case 'f':
                    $r2.appendReplacement($r3, "\f");
                    break;
                case 'n':
                    $r2.appendReplacement($r3, "\n");
                    break;
                case 'r':
                    $r2.appendReplacement($r3, "\r");
                    break;
                case 't':
                    $r2.appendReplacement($r3, "\t");
                    break;
                default:
                    throw new IllegalStateException("Found an escaped character that should never be.");
            }
        }
        if ($r3 == null) {
            return $r0;
        }
        $r2.appendTail($r3);
        return $r3.toString();
    }

    public static String zzhf(String $r0) throws  {
        if (TextUtils.isEmpty($r0)) {
            return $r0;
        }
        Matcher $r2 = MV.matcher($r0);
        StringBuffer $r3 = null;
        while ($r2.find()) {
            if ($r3 == null) {
                $r3 = new StringBuffer();
            }
            switch ($r2.group().charAt(0)) {
                case '\b':
                    $r2.appendReplacement($r3, "\\\\b");
                    break;
                case '\t':
                    $r2.appendReplacement($r3, "\\\\t");
                    break;
                case '\n':
                    $r2.appendReplacement($r3, "\\\\n");
                    break;
                case '\f':
                    $r2.appendReplacement($r3, "\\\\f");
                    break;
                case '\r':
                    $r2.appendReplacement($r3, "\\\\r");
                    break;
                case '\"':
                    $r2.appendReplacement($r3, "\\\\\\\"");
                    break;
                case '/':
                    $r2.appendReplacement($r3, "\\\\/");
                    break;
                case '\\':
                    $r2.appendReplacement($r3, "\\\\\\\\");
                    break;
                default:
                    break;
            }
        }
        if ($r3 == null) {
            return $r0;
        }
        $r2.appendTail($r3);
        return $r3.toString();
    }
}
