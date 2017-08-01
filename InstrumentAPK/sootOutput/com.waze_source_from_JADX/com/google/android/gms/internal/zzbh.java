package com.google.android.gms.internal;

import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.ads.identifier.AdvertisingIdClient.Info;
import com.google.android.gms.internal.zzae.zza;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/* compiled from: dalvik_source_com.waze.apk */
public class zzbh extends zzbp {
    public zzbh(zzax $r1, String $r2, String $r3, zza $r4, int $i0, int $i1) throws  {
        super($r1, $r2, $r3, $r4, $i0, $i1);
    }

    private void zzcu() throws IllegalAccessException, InvocationTargetException {
        synchronized (this.zzaha) {
            this.zzaha.zzef = (String) this.zzahh.invoke(null, new Object[]{this.zzaey.getContext()});
        }
    }

    private void zzcv() throws  {
        AdvertisingIdClient $r4 = this.zzaey.zzcq();
        if ($r4 == null) {
            zzp("E1");
            return;
        }
        try {
            Info $r5 = $r4.getInfo();
            String $r6 = zzay.zzo($r5.getId());
            if ($r6 != null) {
                synchronized (this.zzaha) {
                    this.zzaha.zzef = $r6;
                    this.zzaha.zzeh = Boolean.valueOf($r5.isLimitAdTrackingEnabled());
                    this.zzaha.zzeg = Integer.valueOf(5);
                }
                return;
            }
            zzp("E");
        } catch (IOException e) {
            zzp("E");
        }
    }

    private void zzp(String str) throws  {
    }

    protected void zzct() throws IllegalAccessException, InvocationTargetException {
        if (this.zzaey.zzch()) {
            zzcv();
        } else {
            zzcu();
        }
    }
}
