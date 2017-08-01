package com.google.android.gms.common.util;

import com.google.android.gms.common.internal.zzaa;
import dalvik.annotation.Signature;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/* compiled from: dalvik_source_com.waze.apk */
public final class zzb {
    public static <T> int zza(@Signature({"<T:", "Ljava/lang/Object;", ">([TT;TT;)I"}) T[] $r0, @Signature({"<T:", "Ljava/lang/Object;", ">([TT;TT;)I"}) T $r1) throws  {
        int $i0 = 0;
        int $i1 = $r0 != null ? $r0.length : 0;
        while ($i0 < $i1) {
            if (zzaa.equal($r0[$i0], $r1)) {
                return $i0;
            }
            $i0++;
        }
        return -1;
    }

    public static void zza(StringBuilder $r0, double[] $r1) throws  {
        int $i0 = $r1.length;
        for (int $i1 = 0; $i1 < $i0; $i1++) {
            if ($i1 != 0) {
                $r0.append(",");
            }
            $r0.append(Double.toString($r1[$i1]));
        }
    }

    public static void zza(StringBuilder $r0, float[] $r1) throws  {
        int $i0 = $r1.length;
        for (int $i1 = 0; $i1 < $i0; $i1++) {
            if ($i1 != 0) {
                $r0.append(",");
            }
            $r0.append(Float.toString($r1[$i1]));
        }
    }

    public static void zza(StringBuilder $r0, int[] $r1) throws  {
        int $i0 = $r1.length;
        for (int $i1 = 0; $i1 < $i0; $i1++) {
            if ($i1 != 0) {
                $r0.append(",");
            }
            $r0.append(Integer.toString($r1[$i1]));
        }
    }

    public static void zza(StringBuilder $r0, long[] $r1) throws  {
        int $i0 = $r1.length;
        for (int $i1 = 0; $i1 < $i0; $i1++) {
            if ($i1 != 0) {
                $r0.append(",");
            }
            $r0.append(Long.toString($r1[$i1]));
        }
    }

    public static <T> void zza(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Ljava/lang/StringBuilder;", "[TT;)V"}) StringBuilder $r0, @Signature({"<T:", "Ljava/lang/Object;", ">(", "Ljava/lang/StringBuilder;", "[TT;)V"}) T[] $r1) throws  {
        int $i0 = $r1.length;
        for (int $i1 = 0; $i1 < $i0; $i1++) {
            if ($i1 != 0) {
                $r0.append(",");
            }
            $r0.append($r1[$i1].toString());
        }
    }

    public static void zza(StringBuilder $r0, String[] $r1) throws  {
        int $i0 = $r1.length;
        for (int $i1 = 0; $i1 < $i0; $i1++) {
            if ($i1 != 0) {
                $r0.append(",");
            }
            $r0.append("\"").append($r1[$i1]).append("\"");
        }
    }

    public static void zza(StringBuilder $r0, boolean[] $r1) throws  {
        int $i0 = $r1.length;
        for (int $i1 = 0; $i1 < $i0; $i1++) {
            if ($i1 != 0) {
                $r0.append(",");
            }
            $r0.append(Boolean.toString($r1[$i1]));
        }
    }

    public static byte[] zza(byte[]... $r0) throws  {
        if ($r0.length == 0) {
            return new byte[0];
        }
        int $i2 = 0;
        for (byte[] $r1 : $r0) {
            $i2 += $r1.length;
        }
        byte[] $r12 = Arrays.copyOf($r0[0], $i2);
        int $i1 = $r0[0].length;
        for ($i2 = 1; $i2 < $r0.length; $i2++) {
            byte[] $r2 = $r0[$i2];
            System.arraycopy($r2, 0, $r12, $i1, $r2.length);
            $i1 += $r2.length;
        }
        return $r12;
    }

    public static Integer[] zza(int[] $r0) throws  {
        if ($r0 == null) {
            return null;
        }
        int $i0 = $r0.length;
        Integer[] $r2 = new Integer[$i0];
        for (int $i1 = 0; $i1 < $i0; $i1++) {
            $r2[$i1] = Integer.valueOf($r0[$i1]);
        }
        return $r2;
    }

    public static <T> ArrayList<T> zzayv() throws  {
        return new ArrayList();
    }

    public static <T> ArrayList<T> zzb(@Signature({"<T:", "Ljava/lang/Object;", ">([TT;)", "Ljava/util/ArrayList", "<TT;>;"}) T[] $r0) throws  {
        ArrayList $r1 = new ArrayList($i0);
        for (Object $r2 : $r0) {
            $r1.add($r2);
        }
        return $r1;
    }

    public static <T> boolean zzb(@Signature({"<T:", "Ljava/lang/Object;", ">([TT;TT;)Z"}) T[] $r0, @Signature({"<T:", "Ljava/lang/Object;", ">([TT;TT;)Z"}) T $r1) throws  {
        return zza((Object[]) $r0, (Object) $r1) >= 0;
    }

    public static <T> ArrayList<T> zzj(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Ljava/util/Collection", "<TT;>;)", "Ljava/util/ArrayList", "<TT;>;"}) Collection<T> $r0) throws  {
        return $r0 == null ? null : new ArrayList($r0);
    }
}
