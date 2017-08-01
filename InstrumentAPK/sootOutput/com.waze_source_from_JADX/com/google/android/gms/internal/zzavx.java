package com.google.android.gms.internal;

import dalvik.annotation.Signature;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;
import java.util.ArrayList;

/* compiled from: dalvik_source_com.waze.apk */
public final class zzavx<E> extends zzavg<Object> {
    public static final zzavh bYW = new C07941();
    private final Class<E> bYX;
    private final zzavg<E> bYY;

    /* compiled from: dalvik_source_com.waze.apk */
    static class C07941 implements zzavh {
        C07941() throws  {
        }

        public <T> zzavg<T> zza(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/internal/zzauo;", "Lcom/google/android/gms/internal/zzawk", "<TT;>;)", "Lcom/google/android/gms/internal/zzavg", "<TT;>;"}) zzauo $r1, @Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/internal/zzauo;", "Lcom/google/android/gms/internal/zzawk", "<TT;>;)", "Lcom/google/android/gms/internal/zzavg", "<TT;>;"}) zzawk<T> $r2) throws  {
            Type $r3 = $r2.hO();
            if (!($r3 instanceof GenericArrayType) && (!($r3 instanceof Class) || !((Class) $r3).isArray())) {
                return null;
            }
            $r3 = zzavn.zzh($r3);
            return new zzavx($r1, $r1.zza(zzawk.zzl($r3)), zzavn.zzf($r3));
        }
    }

    public zzavx(@Signature({"(", "Lcom/google/android/gms/internal/zzauo;", "Lcom/google/android/gms/internal/zzavg", "<TE;>;", "Ljava/lang/Class", "<TE;>;)V"}) zzauo $r1, @Signature({"(", "Lcom/google/android/gms/internal/zzauo;", "Lcom/google/android/gms/internal/zzavg", "<TE;>;", "Ljava/lang/Class", "<TE;>;)V"}) zzavg<E> $r2, @Signature({"(", "Lcom/google/android/gms/internal/zzauo;", "Lcom/google/android/gms/internal/zzavg", "<TE;>;", "Ljava/lang/Class", "<TE;>;)V"}) Class<E> $r3) throws  {
        this.bYY = new zzawi($r1, $r2, $r3);
        this.bYX = $r3;
    }

    public void zza(zzawn $r1, Object $r2) throws IOException {
        if ($r2 == null) {
            $r1.hM();
            return;
        }
        $r1.hI();
        int $i1 = Array.getLength($r2);
        for (int $i0 = 0; $i0 < $i1; $i0++) {
            this.bYY.zza($r1, Array.get($r2, $i0));
        }
        $r1.hJ();
    }

    public Object zzb(zzawl $r1) throws IOException {
        if ($r1.hC() == zzawm.NULL) {
            $r1.nextNull();
            return null;
        }
        ArrayList $r2 = new ArrayList();
        $r1.beginArray();
        while ($r1.hasNext()) {
            $r2.add(this.bYY.zzb($r1));
        }
        $r1.endArray();
        Object $r6 = Array.newInstance(this.bYX, $r2.size());
        for (int $i0 = 0; $i0 < $r2.size(); $i0++) {
            Array.set($r6, $i0, $r2.get($i0));
        }
        return $r6;
    }
}
