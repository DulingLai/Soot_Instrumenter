package com.google.android.gms.common.data;

import dalvik.annotation.Signature;
import java.util.ArrayList;

/* compiled from: dalvik_source_com.waze.apk */
public final class FreezableUtils {
    public static <T, E extends Freezable<T>> ArrayList<T> freeze(@Signature({"<T:", "Ljava/lang/Object;", "E::", "Lcom/google/android/gms/common/data/Freezable", "<TT;>;>(", "Ljava/util/ArrayList", "<TE;>;)", "Ljava/util/ArrayList", "<TT;>;"}) ArrayList<E> $r0) throws  {
        ArrayList $r1 = new ArrayList($r0.size());
        int $i0 = $r0.size();
        for (int $i1 = 0; $i1 < $i0; $i1++) {
            $r1.add(((Freezable) $r0.get($i1)).freeze());
        }
        return $r1;
    }

    public static <T, E extends Freezable<T>> ArrayList<T> freeze(@Signature({"<T:", "Ljava/lang/Object;", "E::", "Lcom/google/android/gms/common/data/Freezable", "<TT;>;>([TE;)", "Ljava/util/ArrayList", "<TT;>;"}) E[] $r0) throws  {
        ArrayList $r1 = new ArrayList($r0.length);
        for (Freezable $r3 : $r0) {
            $r1.add($r3.freeze());
        }
        return $r1;
    }

    public static <T, E extends Freezable<T>> ArrayList<T> freezeIterable(@Signature({"<T:", "Ljava/lang/Object;", "E::", "Lcom/google/android/gms/common/data/Freezable", "<TT;>;>(", "Ljava/lang/Iterable", "<TE;>;)", "Ljava/util/ArrayList", "<TT;>;"}) Iterable<E> $r0) throws  {
        ArrayList $r1 = new ArrayList();
        for (E freeze : $r0) {
            $r1.add(freeze.freeze());
        }
        return $r1;
    }
}
