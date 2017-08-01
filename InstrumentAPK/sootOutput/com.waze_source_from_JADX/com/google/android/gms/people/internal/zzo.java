package com.google.android.gms.people.internal;

import android.os.Bundle;
import android.util.Base64;
import com.google.android.gms.common.data.DataHolder;
import java.lang.reflect.Array;
import java.util.ArrayList;

/* compiled from: dalvik_source_com.waze.apk */
public final class zzo {
    private static StringBuilder zza(Object $r2, String $r0, StringBuilder $r1) throws  {
        if ($r2 == null) {
            $r1.append("[null]\n");
            return $r1;
        }
        String $r3 = String.valueOf($r0).concat("  ");
        $r1.append("(").append($r2.getClass().getSimpleName()).append(") ");
        if ($r2 instanceof Bundle) {
            Bundle $r7 = (Bundle) $r2;
            if ($r7.isEmpty()) {
                $r1.append("{ }").append("\n");
                return $r1;
            }
            $r1.append("{\n");
            for (String $r6 : $r7.keySet()) {
                $r1.append($r3).append($r6).append(" : ");
                zza($r7.get($r6), $r3, $r1);
            }
            $r1.append($r0).append("}\n");
            return $r1;
        } else if ($r2 instanceof DataHolder) {
            DataHolder dataHolder = (DataHolder) $r2;
            $r1.append(" [");
            if (dataHolder.isClosed()) {
                $r1.append("CLOSED");
            } else {
                $r1.append(dataHolder.getCount());
            }
            $r1.append("] ").append($r2).append("\n");
            return $r1;
        } else if ($r2 instanceof ArrayList) {
            ArrayList $r11 = (ArrayList) $r2;
            if ($r11.isEmpty()) {
                $r1.append("[ ]\n");
                return $r1;
            }
            $r1.append("[\n");
            for ($i0 = 0; $i0 < $r11.size(); $i0++) {
                $r1.append($r3).append($i0).append(" : ");
                zza($r11.get($i0), $r3, $r1);
            }
            $r1.append($r0).append("]\n");
            return $r1;
        } else if ($r2 instanceof byte[]) {
            $i0 = ((byte[]) $r2).length;
            $r1.append(" [").append($i0).append("] ");
            int $i0 = new byte[Math.min($i0, 56)];
            Object $r12 = $i0;
            System.arraycopy($r2, 0, $r12, 0, $i0.length);
            $r1.append(Base64.encodeToString($r12, 0));
            return $r1;
        } else if ($r2 instanceof char[]) {
            $r1.append("\"").append(new String((char[]) $r2)).append("\"\n");
            return $r1;
        } else if ($r2.getClass().isArray()) {
            if (Array.getLength($r2) == 0) {
                $r1.append("[ ]\n");
                return $r1;
            }
            $r1.append("[ ");
            $r1.append(Array.get($r2, 0));
            for ($i0 = 1; $i0 < Array.getLength($r2); $i0++) {
                $r1.append(", ").append(Array.get($r2, $i0));
            }
            $r1.append(" ]\n");
            return $r1;
        } else if ($r2 instanceof String) {
            $r1.append("\"").append($r2).append("\"\n");
            return $r1;
        } else {
            $r1.append($r2).append("\n");
            return $r1;
        }
    }

    public static String zzax(Bundle $r0) throws  {
        return zza($r0, "", new StringBuilder()).toString();
    }
}
