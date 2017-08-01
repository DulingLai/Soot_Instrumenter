package com.google.android.gms.internal;

import dalvik.annotation.Signature;
import java.io.IOException;

/* compiled from: dalvik_source_com.waze.apk */
public abstract class zzavg<T> {
    public abstract void zza(@Signature({"(", "Lcom/google/android/gms/internal/zzawn;", "TT;)V"}) zzawn com_google_android_gms_internal_zzawn, @Signature({"(", "Lcom/google/android/gms/internal/zzawn;", "TT;)V"}) T t) throws IOException;

    public abstract T zzb(@Signature({"(", "Lcom/google/android/gms/internal/zzawl;", ")TT;"}) zzawl com_google_android_gms_internal_zzawl) throws IOException;

    public final zzauu zzcz(@Signature({"(TT;)", "Lcom/google/android/gms/internal/zzauu;"}) T $r1) throws  {
        try {
            zzawc $r4 = new zzawc();
            zza($r4, $r1);
            return $r4.hG();
        } catch (Throwable $r5) {
            throw new zzauv($r5);
        }
    }
}
