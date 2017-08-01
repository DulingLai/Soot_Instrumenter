package com.google.android.gms.internal;

import com.google.android.gms.internal.zzae.zza;
import java.lang.reflect.InvocationTargetException;

/* compiled from: dalvik_source_com.waze.apk */
public class zzba extends zzbp {
    private static final Object zzafc = new Object();
    private static volatile String zzagy = null;

    public zzba(zzax $r1, String $r2, String $r3, zza $r4, int $i0, int $i1) throws  {
        super($r1, $r2, $r3, $r4, $i0, $i1);
    }

    protected void zzct() throws IllegalAccessException, InvocationTargetException {
        this = this;
        this.zzaha.zzdo = "E";
        if (zzagy == null) {
            synchronized (zzafc) {
                if (zzagy == null) {
                    zzagy = (String) this.zzahh.invoke(null, new Object[]{this.zzaey.getContext()});
                }
            }
        }
        zza com_google_android_gms_internal_zzae_zza = this.zzaha;
        this = this;
        synchronized (com_google_android_gms_internal_zzae_zza) {
            this.zzaha.zzdo = zzaj.zza(zzagy.getBytes(), true);
        }
    }
}
