package com.google.android.gms.common.util;

import dalvik.annotation.Signature;
import java.util.HashMap;

/* compiled from: dalvik_source_com.waze.apk */
public class zzo {
    public static void zza(@Signature({"(", "Ljava/lang/StringBuilder;", "Ljava/util/HashMap", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;)V"}) StringBuilder $r0, @Signature({"(", "Ljava/lang/StringBuilder;", "Ljava/util/HashMap", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;)V"}) HashMap<String, String> $r1) throws  {
        $r0.append("{");
        boolean $z0 = true;
        for (String $r5 : $r1.keySet()) {
            if ($z0) {
                $z0 = false;
            } else {
                $r0.append(",");
            }
            String $r6 = (String) $r1.get($r5);
            $r0.append("\"").append($r5).append("\":");
            if ($r6 == null) {
                $r0.append("null");
            } else {
                $r0.append("\"").append($r6).append("\"");
            }
        }
        $r0.append("}");
    }
}
