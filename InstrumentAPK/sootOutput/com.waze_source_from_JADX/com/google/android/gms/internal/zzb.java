package com.google.android.gms.internal;

import java.util.Collections;
import java.util.Map;

/* compiled from: dalvik_source_com.waze.apk */
public interface zzb {

    /* compiled from: dalvik_source_com.waze.apk */
    public static class zza {
        public byte[] data;
        public String zza;
        public long zzb;
        public long zzc;
        public long zzd;
        public long zze;
        public Map<String, String> zzf = Collections.emptyMap();

        public boolean zza() throws  {
            return this.zzd < System.currentTimeMillis();
        }

        public boolean zzb() throws  {
            return this.zze < System.currentTimeMillis();
        }
    }

    void initialize() throws ;

    zza zza(String str) throws ;

    void zza(String str, zza com_google_android_gms_internal_zzb_zza) throws ;
}
