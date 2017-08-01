package com.google.android.gms.internal;

import com.google.android.gms.internal.zzae.zza;
import java.lang.reflect.InvocationTargetException;

/* compiled from: dalvik_source_com.waze.apk */
public class zzbg extends zzbp {
    private long startTime;

    public zzbg(zzax $r1, String $r2, String $r3, zza $r4, long $l0, int $i1, int $i2) throws  {
        super($r1, $r2, $r3, $r4, $i1, $i2);
        this.startTime = $l0;
    }

    protected void zzct() throws IllegalAccessException, InvocationTargetException {
        long $l0 = ((Long) this.zzahh.invoke(null, new Object[0])).longValue();
        synchronized (this.zzaha) {
            this.zzaha.zzej = Long.valueOf($l0);
            if (this.startTime != 0) {
                this.zzaha.zzdh = Long.valueOf($l0 - this.startTime);
                this.zzaha.zzdm = Long.valueOf(this.startTime);
            }
        }
    }
}
