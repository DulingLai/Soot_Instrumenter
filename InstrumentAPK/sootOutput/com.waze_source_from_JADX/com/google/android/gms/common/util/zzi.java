package com.google.android.gms.common.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.PowerManager;
import android.os.SystemClock;

/* compiled from: dalvik_source_com.waze.apk */
public final class zzi {
    private static IntentFilter MN = new IntentFilter("android.intent.action.BATTERY_CHANGED");
    private static long MO;
    private static float MP = Float.NaN;

    @TargetApi(20)
    public static boolean zzb(PowerManager $r0) throws  {
        return zzr.zzazi() ? $r0.isInteractive() : $r0.isScreenOn();
    }

    @TargetApi(20)
    public static int zzch(Context $r0) throws  {
        byte $b0 = (byte) 1;
        if ($r0 == null || $r0.getApplicationContext() == null) {
            return -1;
        }
        Intent $r3 = $r0.getApplicationContext().registerReceiver(null, MN);
        boolean $z0 = (($r3 == null ? 0 : $r3.getIntExtra("plugged", 0)) & 7) != 0;
        PowerManager $r5 = (PowerManager) $r0.getSystemService("power");
        if ($r5 == null) {
            return -1;
        }
        int $i1 = (zzb($r5) ? (byte) 1 : (byte) 0) << 1;
        if (!$z0) {
            $b0 = (byte) 0;
        }
        return $i1 | $b0;
    }

    public static synchronized float zzci(Context $r0) throws  {
        float $f0;
        synchronized (zzi.class) {
            try {
                if (SystemClock.elapsedRealtime() - MO >= 60000 || Float.isNaN(MP)) {
                    Intent $r2 = $r0.getApplicationContext().registerReceiver(null, MN);
                    if ($r2 != null) {
                        float $f1 = (float) $r2.getIntExtra("scale", -1);
                        MP = ((float) $r2.getIntExtra("level", -1)) / $f1;
                    }
                    MO = SystemClock.elapsedRealtime();
                    $f0 = MP;
                } else {
                    $f0 = MP;
                }
            } catch (Throwable th) {
                Class cls = zzi.class;
            }
        }
        return $f0;
    }
}
