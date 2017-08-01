package com.google.android.gms.internal;

import android.content.Context;

/* compiled from: dalvik_source_com.waze.apk */
public class zztc {
    private static zztc Ni = new zztc();
    private zztb Nh = null;

    public static zztb zzcl(Context $r0) throws  {
        return Ni.zzck($r0);
    }

    public synchronized zztb zzck(Context $r1) throws  {
        if (this.Nh == null) {
            if ($r1.getApplicationContext() != null) {
                $r1 = $r1.getApplicationContext();
            }
            this.Nh = new zztb($r1);
        }
        return this.Nh;
    }
}
