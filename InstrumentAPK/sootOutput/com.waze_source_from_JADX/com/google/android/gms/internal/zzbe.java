package com.google.android.gms.internal;

import com.google.android.gms.internal.zzae.zza;
import java.lang.reflect.InvocationTargetException;

/* compiled from: dalvik_source_com.waze.apk */
public class zzbe extends zzbp {
    public zzbe(zzax $r1, String $r2, String $r3, zza $r4, int $i0, int $i1) throws  {
        super($r1, $r2, $r3, $r4, $i0, $i1);
    }

    protected void zzct() throws IllegalAccessException, InvocationTargetException {
        this.zzaha.zzcv = Long.valueOf(-1);
        this.zzaha.zzcw = Long.valueOf(-1);
        int[] $r8 = (int[]) this.zzahh.invoke(null, new Object[]{this.zzaey.getContext()});
        synchronized (this.zzaha) {
            long $l1 = (long) $r8[0];
            this.zzaha.zzcv = Long.valueOf($l1);
            $l1 = (long) $r8[1];
            this.zzaha.zzcw = Long.valueOf($l1);
        }
    }
}
