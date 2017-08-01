package com.google.android.gms.internal;

import com.google.android.gms.internal.zzae.zza;
import java.lang.reflect.InvocationTargetException;

/* compiled from: dalvik_source_com.waze.apk */
public class zzbl extends zzbp {
    private static final Object zzafc = new Object();
    private static volatile String zzcs = null;

    public zzbl(zzax $r1, String $r2, String $r3, zza $r4, int $i0, int $i1) throws  {
        super($r1, $r2, $r3, $r4, $i0, $i1);
    }

    protected void zzct() throws IllegalAccessException, InvocationTargetException {
        this.zzaha.zzcs = "E";
        if (zzcs == null) {
            synchronized (zzafc) {
                if (zzcs == null) {
                    zzcs = (String) this.zzahh.invoke(null, new Object[0]);
                }
            }
        }
        synchronized (this.zzaha) {
            this.zzaha.zzcs = zzcs;
        }
    }
}
