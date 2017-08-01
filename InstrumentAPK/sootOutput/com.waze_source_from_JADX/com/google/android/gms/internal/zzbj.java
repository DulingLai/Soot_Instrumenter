package com.google.android.gms.internal;

import com.google.android.gms.internal.zzae.zza;
import java.lang.reflect.InvocationTargetException;

/* compiled from: dalvik_source_com.waze.apk */
public class zzbj extends zzbp {
    private static final Object zzafc = new Object();
    private static volatile Long zzahb = null;

    public zzbj(zzax $r1, String $r2, String $r3, zza $r4, int $i0, int $i1) throws  {
        super($r1, $r2, $r3, $r4, $i0, $i1);
    }

    protected void zzct() throws IllegalAccessException, InvocationTargetException {
        if (zzahb == null) {
            synchronized (zzafc) {
                if (zzahb == null) {
                    zzahb = (Long) this.zzahh.invoke(null, new Object[0]);
                }
            }
        }
        synchronized (this.zzaha) {
            this.zzaha.zzdl = zzahb;
        }
    }
}
