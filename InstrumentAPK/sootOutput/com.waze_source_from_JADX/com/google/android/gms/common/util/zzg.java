package com.google.android.gms.common.util;

import android.os.SystemClock;

/* compiled from: dalvik_source_com.waze.apk */
public final class zzg implements Clock {
    private static zzg MI;

    public static synchronized Clock zzayy() throws  {
        Class cls = zzg.class;
        synchronized (cls) {
            try {
                if (MI == null) {
                    MI = new zzg();
                }
                zzg $r0 = MI;
                return $r0;
            } finally {
                cls = zzg.class;
            }
        }
    }

    public long currentTimeMillis() throws  {
        return System.currentTimeMillis();
    }

    public long elapsedRealtime() throws  {
        return SystemClock.elapsedRealtime();
    }

    public long nanoTime() throws  {
        return System.nanoTime();
    }
}
