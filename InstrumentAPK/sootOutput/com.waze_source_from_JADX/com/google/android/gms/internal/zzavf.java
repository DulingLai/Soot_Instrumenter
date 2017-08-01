package com.google.android.gms.internal;

import dalvik.annotation.Signature;
import java.io.IOException;

/* compiled from: dalvik_source_com.waze.apk */
final class zzavf<T> extends zzavg<T> {
    private zzavg<T> bXB;
    private final zzavc<T> bXQ;
    private final zzaut<T> bXR;
    private final zzauo bXS;
    private final zzawk<T> bXT;
    private final zzavh bXU;

    /* compiled from: dalvik_source_com.waze.apk */
    private static class zza implements zzavh {
        private final zzavc<?> bXQ;
        private final zzaut<?> bXR;
        private final zzawk<?> bXV;
        private final boolean bXW;
        private final Class<?> bXX;

        private zza(@Signature({"(", "Ljava/lang/Object;", "Lcom/google/android/gms/internal/zzawk", "<*>;Z", "Ljava/lang/Class", "<*>;)V"}) Object $r3, @Signature({"(", "Ljava/lang/Object;", "Lcom/google/android/gms/internal/zzawk", "<*>;Z", "Ljava/lang/Class", "<*>;)V"}) zzawk<?> $r1, @Signature({"(", "Ljava/lang/Object;", "Lcom/google/android/gms/internal/zzawk", "<*>;Z", "Ljava/lang/Class", "<*>;)V"}) boolean $z0, @Signature({"(", "Ljava/lang/Object;", "Lcom/google/android/gms/internal/zzawk", "<*>;Z", "Ljava/lang/Class", "<*>;)V"}) Class<?> $r2) throws  {
            this.bXQ = $r3 instanceof zzavc ? (zzavc) $r3 : null;
            this.bXR = $r3 instanceof zzaut ? (zzaut) $r3 : null;
            boolean $z1 = (this.bXQ == null && this.bXR == null) ? false : true;
            zzavm.zzbn($z1);
            this.bXV = $r1;
            this.bXW = $z0;
            this.bXX = $r2;
        }

        public <T> zzavg<T> zza(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/internal/zzauo;", "Lcom/google/android/gms/internal/zzawk", "<TT;>;)", "Lcom/google/android/gms/internal/zzavg", "<TT;>;"}) zzauo $r1, @Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/internal/zzauo;", "Lcom/google/android/gms/internal/zzawk", "<TT;>;)", "Lcom/google/android/gms/internal/zzavg", "<TT;>;"}) zzawk<T> $r2) throws  {
            boolean $z0 = this.bXV != null ? this.bXV.equals($r2) || (this.bXW && this.bXV.hO() == $r2.hN()) : this.bXX.isAssignableFrom($r2.hN());
            if (!$z0) {
                return null;
            }
            return new zzavf(this.bXQ, this.bXR, $r1, $r2, this);
        }
    }

    private zzavf(@Signature({"(", "Lcom/google/android/gms/internal/zzavc", "<TT;>;", "Lcom/google/android/gms/internal/zzaut", "<TT;>;", "Lcom/google/android/gms/internal/zzauo;", "Lcom/google/android/gms/internal/zzawk", "<TT;>;", "Lcom/google/android/gms/internal/zzavh;", ")V"}) zzavc<T> $r1, @Signature({"(", "Lcom/google/android/gms/internal/zzavc", "<TT;>;", "Lcom/google/android/gms/internal/zzaut", "<TT;>;", "Lcom/google/android/gms/internal/zzauo;", "Lcom/google/android/gms/internal/zzawk", "<TT;>;", "Lcom/google/android/gms/internal/zzavh;", ")V"}) zzaut<T> $r2, @Signature({"(", "Lcom/google/android/gms/internal/zzavc", "<TT;>;", "Lcom/google/android/gms/internal/zzaut", "<TT;>;", "Lcom/google/android/gms/internal/zzauo;", "Lcom/google/android/gms/internal/zzawk", "<TT;>;", "Lcom/google/android/gms/internal/zzavh;", ")V"}) zzauo $r3, @Signature({"(", "Lcom/google/android/gms/internal/zzavc", "<TT;>;", "Lcom/google/android/gms/internal/zzaut", "<TT;>;", "Lcom/google/android/gms/internal/zzauo;", "Lcom/google/android/gms/internal/zzawk", "<TT;>;", "Lcom/google/android/gms/internal/zzavh;", ")V"}) zzawk<T> $r4, @Signature({"(", "Lcom/google/android/gms/internal/zzavc", "<TT;>;", "Lcom/google/android/gms/internal/zzaut", "<TT;>;", "Lcom/google/android/gms/internal/zzauo;", "Lcom/google/android/gms/internal/zzawk", "<TT;>;", "Lcom/google/android/gms/internal/zzavh;", ")V"}) zzavh $r5) throws  {
        this.bXQ = $r1;
        this.bXR = $r2;
        this.bXS = $r3;
        this.bXT = $r4;
        this.bXU = $r5;
    }

    private zzavg<T> hs() throws  {
        zzavg $r3 = this.bXB;
        if ($r3 != null) {
            return $r3;
        }
        $r3 = this.bXS.zza(this.bXU, this.bXT);
        this.bXB = $r3;
        return $r3;
    }

    public static zzavh zza(@Signature({"(", "Lcom/google/android/gms/internal/zzawk", "<*>;", "Ljava/lang/Object;", ")", "Lcom/google/android/gms/internal/zzavh;"}) zzawk<?> $r0, @Signature({"(", "Lcom/google/android/gms/internal/zzawk", "<*>;", "Ljava/lang/Object;", ")", "Lcom/google/android/gms/internal/zzavh;"}) Object $r1) throws  {
        return new zza($r1, $r0, false, null);
    }

    public static zzavh zzb(@Signature({"(", "Lcom/google/android/gms/internal/zzawk", "<*>;", "Ljava/lang/Object;", ")", "Lcom/google/android/gms/internal/zzavh;"}) zzawk<?> $r0, @Signature({"(", "Lcom/google/android/gms/internal/zzawk", "<*>;", "Ljava/lang/Object;", ")", "Lcom/google/android/gms/internal/zzavh;"}) Object $r1) throws  {
        return new zza($r1, $r0, $r0.hO() == $r0.hN(), null);
    }

    public void zza(@Signature({"(", "Lcom/google/android/gms/internal/zzawn;", "TT;)V"}) zzawn $r1, @Signature({"(", "Lcom/google/android/gms/internal/zzawn;", "TT;)V"}) T $r2) throws IOException {
        if (this.bXQ == null) {
            hs().zza($r1, $r2);
        } else if ($r2 == null) {
            $r1.hM();
        } else {
            zzavv.zzb(this.bXQ.zza($r2, this.bXT.hO(), this.bXS.bXz), $r1);
        }
    }

    public T zzb(@Signature({"(", "Lcom/google/android/gms/internal/zzawl;", ")TT;"}) zzawl $r1) throws IOException {
        if (this.bXR == null) {
            return hs().zzb($r1);
        }
        zzauu $r5 = zzavv.zzh($r1);
        if ($r5.hk()) {
            return null;
        }
        try {
            try {
                return this.bXR.zzb($r5, this.bXT.hO(), this.bXS.bXy);
            } catch (zzauy $r10) {
                throw $r10;
            }
        } catch (Throwable $r11) {
            throw new zzauy($r11);
        }
    }
}
