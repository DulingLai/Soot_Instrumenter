package com.google.android.gms.internal;

import android.content.Context;
import java.util.List;

/* compiled from: dalvik_source_com.waze.apk */
public abstract class zzyr {
    private static final Object aNf = new Object();
    private static zzyr aNg;

    public static zzyr zzdj(Context $r0) throws  {
        synchronized (aNf) {
            if (aNg == null) {
                aNg = new zzys($r0.getApplicationContext());
            }
        }
        return aNg;
    }

    public abstract List<zzyq> zzccy() throws ;
}
