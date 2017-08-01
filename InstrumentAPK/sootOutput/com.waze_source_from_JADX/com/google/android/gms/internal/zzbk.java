package com.google.android.gms.internal;

import com.google.android.gms.internal.zzae.zza;
import java.lang.reflect.InvocationTargetException;

/* compiled from: dalvik_source_com.waze.apk */
public class zzbk extends zzbp {
    private long zzahc = -1;

    public zzbk(zzax $r1, String $r2, String $r3, zza $r4, int $i0, int $i1) throws  {
        super($r1, $r2, $r3, $r4, $i0, $i1);
    }

    protected void zzct() throws IllegalAccessException, InvocationTargetException {
        this.zzaha.zzdc = Long.valueOf(-1);
        if (this.zzahc == -1) {
            this.zzahc = (long) ((Integer) this.zzahh.invoke(null, new Object[]{this.zzaey.getContext()})).intValue();
        }
        zza com_google_android_gms_internal_zzae_zza = this.zzaha;
        this = this;
        synchronized (com_google_android_gms_internal_zzae_zza) {
            this.zzaha.zzdc = Long.valueOf(this.zzahc);
        }
    }
}
