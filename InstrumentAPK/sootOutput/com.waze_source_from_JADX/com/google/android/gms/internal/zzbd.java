package com.google.android.gms.internal;

import com.google.android.gms.internal.zzae.zza;
import java.lang.reflect.InvocationTargetException;

/* compiled from: dalvik_source_com.waze.apk */
public class zzbd extends zzbp {
    private static final Object zzafc = new Object();
    private static volatile String zzcp = null;

    public zzbd(zzax $r1, String $r2, String $r3, zza $r4, int $i0, int $i1) throws  {
        super($r1, $r2, $r3, $r4, $i0, $i1);
    }

    protected void zzct() throws IllegalAccessException, InvocationTargetException {
        this.zzaha.zzds = "E";
        if (zzcp == null) {
            synchronized (zzafc) {
                if (zzcp == null) {
                    zzcp = (String) this.zzahh.invoke(null, new Object[]{this.zzaey.getContext()});
                }
            }
        }
        synchronized (this.zzaha) {
            this.zzaha.zzds = zzcp;
        }
    }
}
