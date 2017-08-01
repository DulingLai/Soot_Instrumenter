package com.google.android.gms.people.internal;

import android.os.Bundle;
import android.util.Log;
import com.google.android.gms.common.internal.zzab;

/* compiled from: dalvik_source_com.waze.apk */
public class zzl {
    public static boolean isEnabled() throws  {
        return Log.isLoggable("PeopleClientCall", 3);
    }

    public static String zzd(Object... $r0) throws  {
        StringBuilder $r1 = new StringBuilder();
        zzab.zzbn($r0.length % 2 == 0);
        String $r2 = "";
        for (int $i1 = 0; $i1 < $r0.length; $i1 += 2) {
            $r1.append($r2);
            $r1.append($r0[$i1]);
            $r1.append("=");
            $r1.append($r0[$i1 + 1]);
            $r2 = ", ";
        }
        return $r1.toString();
    }

    public static void zzh(String $r0, Object... $r1) throws  {
        StringBuilder $r2 = new StringBuilder();
        $r2.append($r0);
        $r2.append("(");
        $r0 = "";
        for (Object $r3 : $r1) {
            $r2.append($r0);
            if ($r3 instanceof Bundle) {
                $r2.append(zzo.zzax((Bundle) $r3));
            } else {
                $r2.append($r3);
            }
            $r0 = ", ";
        }
        $r2.append(")");
        Log.d("PeopleClientCall", $r2.toString(), Log.isLoggable("PeopleClientCall", 2) ? new Throwable("STACK TRACE (It's not crash!)") : null);
    }
}
