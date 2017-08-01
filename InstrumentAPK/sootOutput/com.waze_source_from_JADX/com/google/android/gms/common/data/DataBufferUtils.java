package com.google.android.gms.common.data;

import android.os.Bundle;
import dalvik.annotation.Signature;
import java.util.ArrayList;

/* compiled from: dalvik_source_com.waze.apk */
public final class DataBufferUtils {
    private DataBufferUtils() throws  {
    }

    public static <T, E extends Freezable<T>> ArrayList<T> freezeAndClose(@Signature({"<T:", "Ljava/lang/Object;", "E::", "Lcom/google/android/gms/common/data/Freezable", "<TT;>;>(", "Lcom/google/android/gms/common/data/DataBuffer", "<TE;>;)", "Ljava/util/ArrayList", "<TT;>;"}) DataBuffer<E> $r0) throws  {
        ArrayList $r1 = new ArrayList($r0.getCount());
        try {
            for (E freeze : $r0) {
                $r1.add(freeze.freeze());
            }
            return $r1;
        } finally {
            $r0.close();
        }
    }

    public static boolean hasData(@Signature({"(", "Lcom/google/android/gms/common/data/DataBuffer", "<*>;)Z"}) DataBuffer<?> $r0) throws  {
        return $r0 != null && $r0.getCount() > 0;
    }

    public static boolean hasNextPage(@Signature({"(", "Lcom/google/android/gms/common/data/DataBuffer", "<*>;)Z"}) DataBuffer<?> $r0) throws  {
        Bundle $r1 = $r0.zzava();
        return ($r1 == null || $r1.getString("next_page_token") == null) ? false : true;
    }

    public static boolean hasPrevPage(@Signature({"(", "Lcom/google/android/gms/common/data/DataBuffer", "<*>;)Z"}) DataBuffer<?> $r0) throws  {
        Bundle $r1 = $r0.zzava();
        return ($r1 == null || $r1.getString("prev_page_token") == null) ? false : true;
    }
}
