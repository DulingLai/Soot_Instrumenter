package com.google.android.gms.internal;

import dalvik.annotation.Signature;

/* compiled from: dalvik_source_com.waze.apk */
public final class zzawa implements zzavh {
    private final zzavo bXt;

    public zzawa(zzavo $r1) throws  {
        this.bXt = $r1;
    }

    static zzavg<?> zza(@Signature({"(", "Lcom/google/android/gms/internal/zzavo;", "Lcom/google/android/gms/internal/zzauo;", "Lcom/google/android/gms/internal/zzawk", "<*>;", "Lcom/google/android/gms/internal/zzavi;", ")", "Lcom/google/android/gms/internal/zzavg", "<*>;"}) zzavo $r0, @Signature({"(", "Lcom/google/android/gms/internal/zzavo;", "Lcom/google/android/gms/internal/zzauo;", "Lcom/google/android/gms/internal/zzawk", "<*>;", "Lcom/google/android/gms/internal/zzavi;", ")", "Lcom/google/android/gms/internal/zzavg", "<*>;"}) zzauo $r1, @Signature({"(", "Lcom/google/android/gms/internal/zzavo;", "Lcom/google/android/gms/internal/zzauo;", "Lcom/google/android/gms/internal/zzawk", "<*>;", "Lcom/google/android/gms/internal/zzavi;", ")", "Lcom/google/android/gms/internal/zzavg", "<*>;"}) zzawk<?> $r2, @Signature({"(", "Lcom/google/android/gms/internal/zzavo;", "Lcom/google/android/gms/internal/zzauo;", "Lcom/google/android/gms/internal/zzawk", "<*>;", "Lcom/google/android/gms/internal/zzavi;", ")", "Lcom/google/android/gms/internal/zzavg", "<*>;"}) zzavi $r3) throws  {
        Class $r4 = $r3.value();
        if (zzavg.class.isAssignableFrom($r4)) {
            return (zzavg) $r0.zzb(zzawk.zzq($r4)).hv();
        }
        if (zzavh.class.isAssignableFrom($r4)) {
            return ((zzavh) $r0.zzb(zzawk.zzq($r4)).hv()).zza($r1, $r2);
        }
        throw new IllegalArgumentException("@JsonAdapter value must be TypeAdapter or TypeAdapterFactory reference.");
    }

    public <T> zzavg<T> zza(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/internal/zzauo;", "Lcom/google/android/gms/internal/zzawk", "<TT;>;)", "Lcom/google/android/gms/internal/zzavg", "<TT;>;"}) zzauo $r1, @Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/internal/zzauo;", "Lcom/google/android/gms/internal/zzawk", "<TT;>;)", "Lcom/google/android/gms/internal/zzavg", "<TT;>;"}) zzawk<T> $r2) throws  {
        zzavi $r5 = (zzavi) $r2.hN().getAnnotation(zzavi.class);
        return $r5 == null ? null : zza(this.bXt, $r1, $r2, $r5);
    }
}
