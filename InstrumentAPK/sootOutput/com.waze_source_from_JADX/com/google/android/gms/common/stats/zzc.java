package com.google.android.gms.common.stats;

import com.google.android.gms.internal.zzse;

/* compiled from: dalvik_source_com.waze.apk */
public final class zzc {
    public static zzse<Integer> Mc = zzse.zza("gms:common:stats:max_num_of_events", Integer.valueOf(100));
    public static zzse<Integer> Md = zzse.zza("gms:common:stats:max_chunk_size", Integer.valueOf(100));

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class zza {
        public static zzse<Integer> Me = zzse.zza("gms:common:stats:connections:level", Integer.valueOf(zzd.LOG_LEVEL_OFF));
        public static zzse<String> Mf = zzse.zzai("gms:common:stats:connections:ignored_calling_processes", "");
        public static zzse<String> Mg = zzse.zzai("gms:common:stats:connections:ignored_calling_services", "");
        public static zzse<String> Mh = zzse.zzai("gms:common:stats:connections:ignored_target_processes", "");
        public static zzse<String> Mi = zzse.zzai("gms:common:stats:connections:ignored_target_services", "com.google.android.gms.auth.GetToken");
        public static zzse<Long> Mj = zzse.zza("gms:common:stats:connections:time_out_duration", Long.valueOf(600000));
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class zzb {
        public static zzse<Integer> Me = zzse.zza("gms:common:stats:wakeLocks:level", Integer.valueOf(zzd.LOG_LEVEL_OFF));
        public static zzse<Long> Mj = zzse.zza("gms:common:stats:wakelocks:time_out_duration", Long.valueOf(600000));
    }
}
