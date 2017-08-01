package com.google.android.gms.internal;

import com.google.android.gms.internal.zzae.zza;
import java.lang.reflect.InvocationTargetException;

/* compiled from: dalvik_source_com.waze.apk */
public class zzbc extends zzbp {
    private static final Object zzafc = new Object();
    private static volatile Long zzcq = null;

    public zzbc(zzax $r1, String $r2, String $r3, zza $r4, int $i0, int $i1) throws  {
        super($r1, $r2, $r3, $r4, $i0, $i1);
    }

    protected void zzct() throws IllegalAccessException, InvocationTargetException {
        zza com_google_android_gms_internal_zzae_zza = this.zzaha;
        this = this;
        com_google_android_gms_internal_zzae_zza.zzdt = Long.valueOf(-1);
        if (zzcq == null) {
            synchronized (zzafc) {
                if (zzcq == null) {
                    zzcq = (Long) this.zzahh.invoke(null, new Object[]{this.zzaey.getContext()});
                }
            }
        }
        com_google_android_gms_internal_zzae_zza = this.zzaha;
        this = this;
        synchronized (com_google_android_gms_internal_zzae_zza) {
            this.zzaha.zzdt = zzcq;
        }
    }
}
