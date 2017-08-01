package com.google.android.gms.internal;

import com.google.android.gms.internal.zzae.zza;
import java.lang.reflect.InvocationTargetException;
import java.nio.ByteBuffer;

/* compiled from: dalvik_source_com.waze.apk */
public class zzbb extends zzbp {
    private static final Object zzafc = new Object();
    private static volatile String zzagz = null;

    public zzbb(zzax $r1, String $r2, String $r3, zza $r4, int $i0, int $i1) throws  {
        super($r1, $r2, $r3, $r4, $i0, $i1);
    }

    protected void zzct() throws IllegalAccessException, InvocationTargetException {
        this = this;
        this.zzaha.zzdn = "E";
        if (zzagz == null) {
            synchronized (zzafc) {
                if (zzagz == null) {
                    zzagz = zzaj.zza(((ByteBuffer) this.zzahh.invoke(null, new Object[]{this.zzaey.getContext()})).array(), true);
                }
            }
        }
        zza com_google_android_gms_internal_zzae_zza = this.zzaha;
        this = this;
        synchronized (com_google_android_gms_internal_zzae_zza) {
            this.zzaha.zzdn = zzagz;
        }
    }
}
