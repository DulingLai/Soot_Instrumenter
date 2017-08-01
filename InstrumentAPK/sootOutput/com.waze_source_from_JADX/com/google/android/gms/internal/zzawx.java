package com.google.android.gms.internal;

import java.nio.charset.Charset;
import java.util.Arrays;

/* compiled from: dalvik_source_com.waze.apk */
public final class zzawx {
    protected static final Charset ISO_8859_1 = Charset.forName("ISO-8859-1");
    protected static final Charset UTF_8 = Charset.forName("UTF-8");
    public static final Object cbB = new Object();

    public static boolean equals(float[] $r0, float[] $r1) throws  {
        return ($r0 == null || $r0.length == 0) ? $r1 == null || $r1.length == 0 : Arrays.equals($r0, $r1);
    }

    public static boolean equals(int[] $r0, int[] $r1) throws  {
        return ($r0 == null || $r0.length == 0) ? $r1 == null || $r1.length == 0 : Arrays.equals($r0, $r1);
    }

    public static boolean equals(long[] $r0, long[] $r1) throws  {
        return ($r0 == null || $r0.length == 0) ? $r1 == null || $r1.length == 0 : Arrays.equals($r0, $r1);
    }

    public static boolean equals(Object[] $r0, Object[] $r1) throws  {
        int $i0 = $r0 == null ? 0 : $r0.length;
        int $i1 = $r1 == null ? 0 : $r1.length;
        int $i2 = 0;
        int $i3 = 0;
        while (true) {
            if ($i3 >= $i0 || $r0[$i3] != null) {
                while ($i2 < $i1 && $r1[$i2] == null) {
                    $i2++;
                }
                boolean $z0 = $i3 >= $i0;
                boolean $z1 = $i2 >= $i1;
                if ($z0 && $z1) {
                    return true;
                }
                if ($z0 != $z1) {
                    return false;
                }
                if (!$r0[$i3].equals($r1[$i2])) {
                    return false;
                }
                $i2++;
                $i3++;
            } else {
                $i3++;
            }
        }
    }

    public static int hashCode(float[] $r0) throws  {
        return ($r0 == null || $r0.length == 0) ? 0 : Arrays.hashCode($r0);
    }

    public static int hashCode(int[] $r0) throws  {
        return ($r0 == null || $r0.length == 0) ? 0 : Arrays.hashCode($r0);
    }

    public static int hashCode(long[] $r0) throws  {
        return ($r0 == null || $r0.length == 0) ? 0 : Arrays.hashCode($r0);
    }

    public static int hashCode(Object[] $r0) throws  {
        int $i0 = 0;
        int $i1 = $r0 == null ? 0 : $r0.length;
        for (int $i2 = 0; $i2 < $i1; $i2++) {
            Object $r1 = $r0[$i2];
            if ($r1 != null) {
                $i0 = ($i0 * 31) + $r1.hashCode();
            }
        }
        return $i0;
    }

    public static void zza(zzaws $r0, zzaws $r1) throws  {
        if ($r0.cbt != null) {
            $r1.cbt = (zzawv) $r0.cbt.clone();
        }
    }

    public static boolean zza(byte[][] $r0, byte[][] $r1) throws  {
        int $i0 = $r0 == null ? 0 : $r0.length;
        int $i1 = $r1 == null ? 0 : $r1.length;
        int $i2 = 0;
        int $i3 = 0;
        while (true) {
            if ($i3 >= $i0 || $r0[$i3] != null) {
                while ($i2 < $i1 && $r1[$i2] == null) {
                    $i2++;
                }
                boolean $z0 = $i3 >= $i0;
                boolean $z1 = $i2 >= $i1;
                if ($z0 && $z1) {
                    return true;
                }
                if ($z0 != $z1) {
                    return false;
                }
                if (!Arrays.equals($r0[$i3], $r1[$i2])) {
                    return false;
                }
                $i2++;
                $i3++;
            } else {
                $i3++;
            }
        }
    }

    public static int zzd(byte[][] $r0) throws  {
        int $i0 = 0;
        int $i1 = $r0 == null ? 0 : $r0.length;
        for (int $i2 = 0; $i2 < $i1; $i2++) {
            byte[] $r1 = $r0[$i2];
            if ($r1 != null) {
                $i0 = ($i0 * 31) + Arrays.hashCode($r1);
            }
        }
        return $i0;
    }
}
