package com.google.android.gms.internal;

import java.io.IOException;

/* compiled from: dalvik_source_com.waze.apk */
public final class zzaxc {
    public static final int[] cbE = new int[0];
    public static final long[] cbF = new long[0];
    public static final float[] cbG = new float[0];
    public static final double[] cbH = new double[0];
    public static final boolean[] cbI = new boolean[0];
    public static final String[] cbJ = new String[0];
    public static final byte[][] cbK = new byte[0][];
    public static final byte[] cbL = new byte[0];

    static int zzasn(int $i0) throws  {
        return $i0 & 7;
    }

    public static int zzaso(int $i0) throws  {
        return $i0 >>> 3;
    }

    public static boolean zzb(zzawq $r0, int $i0) throws IOException {
        return $r0.zzart($i0);
    }

    public static int zzba(int $i0, int $i1) throws  {
        return ($i0 << 3) | $i1;
    }

    public static final int zzc(zzawq $r0, int $i0) throws IOException {
        int $i1 = 1;
        int $i2 = $r0.getPosition();
        $r0.zzart($i0);
        while ($r0.ie() == $i0) {
            $r0.zzart($i0);
            $i1++;
        }
        $r0.zzarx($i2);
        return $i1;
    }
}
