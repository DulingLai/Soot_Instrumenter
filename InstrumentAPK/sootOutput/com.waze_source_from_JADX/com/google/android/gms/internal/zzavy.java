package com.google.android.gms.internal;

import dalvik.annotation.Signature;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;

/* compiled from: dalvik_source_com.waze.apk */
public final class zzavy implements zzavh {
    private final zzavo bXt;

    /* compiled from: dalvik_source_com.waze.apk */
    private static final class zza<E> extends zzavg<Collection<E>> {
        private final zzavg<E> bYZ;
        private final zzavt<? extends Collection<E>> bZa;

        public zza(@Signature({"(", "Lcom/google/android/gms/internal/zzauo;", "Ljava/lang/reflect/Type;", "Lcom/google/android/gms/internal/zzavg", "<TE;>;", "Lcom/google/android/gms/internal/zzavt", "<+", "Ljava/util/Collection", "<TE;>;>;)V"}) zzauo $r1, @Signature({"(", "Lcom/google/android/gms/internal/zzauo;", "Ljava/lang/reflect/Type;", "Lcom/google/android/gms/internal/zzavg", "<TE;>;", "Lcom/google/android/gms/internal/zzavt", "<+", "Ljava/util/Collection", "<TE;>;>;)V"}) Type $r2, @Signature({"(", "Lcom/google/android/gms/internal/zzauo;", "Ljava/lang/reflect/Type;", "Lcom/google/android/gms/internal/zzavg", "<TE;>;", "Lcom/google/android/gms/internal/zzavt", "<+", "Ljava/util/Collection", "<TE;>;>;)V"}) zzavg<E> $r3, @Signature({"(", "Lcom/google/android/gms/internal/zzauo;", "Ljava/lang/reflect/Type;", "Lcom/google/android/gms/internal/zzavg", "<TE;>;", "Lcom/google/android/gms/internal/zzavt", "<+", "Ljava/util/Collection", "<TE;>;>;)V"}) zzavt<? extends Collection<E>> $r4) throws  {
            this.bYZ = new zzawi($r1, $r3, $r2);
            this.bZa = $r4;
        }

        public void zza(@Signature({"(", "Lcom/google/android/gms/internal/zzawn;", "Ljava/util/Collection", "<TE;>;)V"}) zzawn $r1, @Signature({"(", "Lcom/google/android/gms/internal/zzawn;", "Ljava/util/Collection", "<TE;>;)V"}) Collection<E> $r2) throws IOException {
            if ($r2 == null) {
                $r1.hM();
                return;
            }
            $r1.hI();
            for (E $r5 : $r2) {
                this.bYZ.zza($r1, $r5);
            }
            $r1.hJ();
        }

        public /* synthetic */ Object zzb(zzawl $r1) throws IOException {
            return zzj($r1);
        }

        public Collection<E> zzj(@Signature({"(", "Lcom/google/android/gms/internal/zzawl;", ")", "Ljava/util/Collection", "<TE;>;"}) zzawl $r1) throws IOException {
            if ($r1.hC() == zzawm.NULL) {
                $r1.nextNull();
                return null;
            }
            Collection $r4 = (Collection) this.bZa.hv();
            $r1.beginArray();
            while ($r1.hasNext()) {
                $r4.add(this.bYZ.zzb($r1));
            }
            $r1.endArray();
            return $r4;
        }
    }

    public zzavy(zzavo $r1) throws  {
        this.bXt = $r1;
    }

    public <T> zzavg<T> zza(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/internal/zzauo;", "Lcom/google/android/gms/internal/zzawk", "<TT;>;)", "Lcom/google/android/gms/internal/zzavg", "<TT;>;"}) zzauo $r1, @Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/internal/zzauo;", "Lcom/google/android/gms/internal/zzawk", "<TT;>;)", "Lcom/google/android/gms/internal/zzavg", "<TT;>;"}) zzawk<T> $r2) throws  {
        Type $r3 = $r2.hO();
        Class $r4 = $r2.hN();
        if (!Collection.class.isAssignableFrom($r4)) {
            return null;
        }
        $r3 = zzavn.zza($r3, $r4);
        return new zza($r1, $r3, $r1.zza(zzawk.zzl($r3)), this.bXt.zzb($r2));
    }
}
