package com.google.android.gms.internal;

import com.google.android.gms.internal.zzae.zza;
import java.lang.reflect.InvocationTargetException;

/* compiled from: dalvik_source_com.waze.apk */
public class zzaz extends zzbp {
    public zzaz(zzax $r1, String $r2, String $r3, zza $r4, int $i0, int $i1) throws  {
        super($r1, $r2, $r3, $r4, $i0, $i1);
    }

    protected void zzct() throws IllegalAccessException, InvocationTargetException {
        synchronized (this.zzaha) {
            this.zzaha.zzct = Long.valueOf(-1);
            this.zzaha.zzct = (Long) this.zzahh.invoke(null, new Object[]{this.zzaey.getContext()});
        }
    }
}
