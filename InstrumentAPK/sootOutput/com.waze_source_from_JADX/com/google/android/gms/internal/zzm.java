package com.google.android.gms.internal;

import dalvik.annotation.Signature;

/* compiled from: dalvik_source_com.waze.apk */
public class zzm<T> {
    public final T result;
    public final com.google.android.gms.internal.zzb.zza zzbe;
    public final zzr zzbf;
    public boolean zzbg;

    /* compiled from: dalvik_source_com.waze.apk */
    public interface zza {
        void zze(zzr com_google_android_gms_internal_zzr) throws ;
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public interface zzb<T> {
        void zzb(@Signature({"(TT;)V"}) T t) throws ;
    }

    private zzm(zzr $r1) throws  {
        this.zzbg = false;
        this.result = null;
        this.zzbe = null;
        this.zzbf = $r1;
    }

    private zzm(@Signature({"(TT;", "Lcom/google/android/gms/internal/zzb$zza;", ")V"}) T $r1, @Signature({"(TT;", "Lcom/google/android/gms/internal/zzb$zza;", ")V"}) com.google.android.gms.internal.zzb.zza $r2) throws  {
        this.zzbg = false;
        this.result = $r1;
        this.zzbe = $r2;
        this.zzbf = null;
    }

    public static <T> zzm<T> zza(@Signature({"<T:", "Ljava/lang/Object;", ">(TT;", "Lcom/google/android/gms/internal/zzb$zza;", ")", "Lcom/google/android/gms/internal/zzm", "<TT;>;"}) T $r0, @Signature({"<T:", "Ljava/lang/Object;", ">(TT;", "Lcom/google/android/gms/internal/zzb$zza;", ")", "Lcom/google/android/gms/internal/zzm", "<TT;>;"}) com.google.android.gms.internal.zzb.zza $r1) throws  {
        return new zzm($r0, $r1);
    }

    public static <T> zzm<T> zzd(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/internal/zzr;", ")", "Lcom/google/android/gms/internal/zzm", "<TT;>;"}) zzr $r0) throws  {
        return new zzm($r0);
    }

    public boolean isSuccess() throws  {
        return this.zzbf == null;
    }
}
