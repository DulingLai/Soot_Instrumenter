package com.google.android.gms.common.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;

/* compiled from: dalvik_source_com.waze.apk */
public abstract class zzm {
    private static final Object JL = new Object();
    private static zzm JM;

    public static zzm zzbz(Context $r0) throws  {
        synchronized (JL) {
            if (JM == null) {
                JM = new zzn($r0.getApplicationContext());
            }
        }
        return JM;
    }

    public abstract boolean zza(ComponentName componentName, ServiceConnection serviceConnection, String str) throws ;

    public abstract boolean zza(String str, ServiceConnection serviceConnection, String str2) throws ;

    public abstract boolean zza(String str, String str2, ServiceConnection serviceConnection, String str3) throws ;

    public abstract void zzb(ComponentName componentName, ServiceConnection serviceConnection, String str) throws ;

    public abstract void zzb(String str, ServiceConnection serviceConnection, String str2) throws ;

    public abstract void zzb(String str, String str2, ServiceConnection serviceConnection, String str3) throws ;
}
