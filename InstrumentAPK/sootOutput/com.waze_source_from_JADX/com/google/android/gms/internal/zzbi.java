package com.google.android.gms.internal;

import com.google.android.gms.internal.zzae.zza;
import java.util.concurrent.Callable;

/* compiled from: dalvik_source_com.waze.apk */
public class zzbi implements Callable {
    private final zzax zzaey;
    private final zza zzaha;

    public zzbi(zzax $r1, zza $r2) throws  {
        this.zzaey = $r1;
        this.zzaha = $r2;
    }

    public /* synthetic */ Object call() throws Exception {
        return zzcw();
    }

    public Void zzcw() throws Exception {
        if (this.zzaey.zzcl() != null) {
            this.zzaey.zzcl().get();
        }
        zza $r5 = this.zzaey.zzck();
        if ($r5 != null) {
            synchronized (this.zzaha) {
                zzawz.zza(this.zzaha, zzawz.zzf($r5));
            }
        }
        return null;
    }
}
