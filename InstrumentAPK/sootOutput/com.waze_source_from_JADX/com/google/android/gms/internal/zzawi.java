package com.google.android.gms.internal;

import com.google.android.gms.internal.zzawf.zza;
import dalvik.annotation.Signature;
import java.io.IOException;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

/* compiled from: dalvik_source_com.waze.apk */
final class zzawi<T> extends zzavg<T> {
    private final zzavg<T> bXB;
    private final zzauo bZx;
    private final Type bZy;

    zzawi(@Signature({"(", "Lcom/google/android/gms/internal/zzauo;", "Lcom/google/android/gms/internal/zzavg", "<TT;>;", "Ljava/lang/reflect/Type;", ")V"}) zzauo $r1, @Signature({"(", "Lcom/google/android/gms/internal/zzauo;", "Lcom/google/android/gms/internal/zzavg", "<TT;>;", "Ljava/lang/reflect/Type;", ")V"}) zzavg<T> $r2, @Signature({"(", "Lcom/google/android/gms/internal/zzauo;", "Lcom/google/android/gms/internal/zzavg", "<TT;>;", "Ljava/lang/reflect/Type;", ")V"}) Type $r3) throws  {
        this.bZx = $r1;
        this.bXB = $r2;
        this.bZy = $r3;
    }

    private Type zzb(Type $r3, Object $r1) throws  {
        return $r1 != null ? ($r3 == Object.class || ($r3 instanceof TypeVariable) || ($r3 instanceof Class)) ? $r1.getClass() : $r3 : $r3;
    }

    public void zza(@Signature({"(", "Lcom/google/android/gms/internal/zzawn;", "TT;)V"}) zzawn $r1, @Signature({"(", "Lcom/google/android/gms/internal/zzawn;", "TT;)V"}) T $r2) throws IOException {
        zzavg $r4 = this.bXB;
        Type $r5 = zzb(this.bZy, $r2);
        if ($r5 != this.bZy) {
            zzavg $r8 = this.bZx.zza(zzawk.zzl($r5));
            $r4 = $r8;
            if (($r8 instanceof zza) && !(this.bXB instanceof zza)) {
                $r4 = this.bXB;
            }
        }
        $r4.zza($r1, $r2);
    }

    public T zzb(@Signature({"(", "Lcom/google/android/gms/internal/zzawl;", ")TT;"}) zzawl $r1) throws IOException {
        return this.bXB.zzb($r1);
    }
}
