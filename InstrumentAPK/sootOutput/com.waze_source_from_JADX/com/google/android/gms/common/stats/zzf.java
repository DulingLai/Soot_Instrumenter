package com.google.android.gms.common.stats;

import android.content.Context;
import android.content.Intent;
import android.os.PowerManager.WakeLock;
import android.os.Process;
import android.text.TextUtils;
import dalvik.annotation.Signature;
import java.util.List;

/* compiled from: dalvik_source_com.waze.apk */
public class zzf {
    public static String zza(WakeLock $r0, String $r1) throws  {
        String $r2 = String.valueOf(String.valueOf((((long) Process.myPid()) << 32) | ((long) System.identityHashCode($r0))));
        if (TextUtils.isEmpty($r1)) {
            $r1 = "";
        }
        $r1 = String.valueOf($r1);
        return $r1.length() != 0 ? $r2.concat($r1) : new String($r2);
    }

    static List<String> zzac(@Signature({"(", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)", "Ljava/util/List", "<", "Ljava/lang/String;", ">;"}) List<String> $r0) throws  {
        return ($r0 != null && $r0.size() == 1 && "com.google.android.gms".equals($r0.get(0))) ? null : $r0;
    }

    public static String zze(Context $r0, Intent $r1) throws  {
        return String.valueOf((((long) System.identityHashCode($r0)) << 32) | ((long) System.identityHashCode($r1)));
    }

    static String zzhc(String $r0) throws  {
        return "com.google.android.gms".equals($r0) ? null : $r0;
    }
}
