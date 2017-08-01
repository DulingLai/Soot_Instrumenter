package com.google.android.gms.common.internal;

import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;

/* compiled from: dalvik_source_com.waze.apk */
public class zzak {
    public static String zza(String $r0, String $r1, Context $r2, AttributeSet $r3, boolean $z0, boolean $z1, String $r4) throws  {
        $r0 = $r3 == null ? null : $r3.getAttributeValue($r0, $r1);
        if ($r0 != null && $r0.startsWith("@string/") && $z0) {
            String $r5 = $r0.substring("@string/".length());
            String $r6 = $r2.getPackageName();
            TypedValue $r7 = new TypedValue();
            try {
                $r2.getResources().getValue(new StringBuilder((String.valueOf($r6).length() + 8) + String.valueOf($r5).length()).append($r6).append(":string/").append($r5).toString(), $r7, true);
            } catch (NotFoundException e) {
                Log.w($r4, new StringBuilder((String.valueOf($r1).length() + 30) + String.valueOf($r0).length()).append("Could not find resource for ").append($r1).append(": ").append($r0).toString());
            }
            if ($r7.string != null) {
                $r0 = $r7.string.toString();
            } else {
                $r5 = String.valueOf($r7);
                Log.w($r4, new StringBuilder((String.valueOf($r1).length() + 28) + String.valueOf($r5).length()).append("Resource ").append($r1).append(" was not a string: ").append($r5).toString());
            }
        }
        if (!$z1 || $r0 != null) {
            return $r0;
        }
        Log.w($r4, new StringBuilder(String.valueOf($r1).length() + 33).append("Required XML attribute \"").append($r1).append("\" missing").toString());
        return $r0;
    }
}
