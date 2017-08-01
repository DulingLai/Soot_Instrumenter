package com.google.android.gms.common.internal;

import android.os.Looper;
import android.text.TextUtils;
import dalvik.annotation.Signature;

/* compiled from: dalvik_source_com.waze.apk */
public final class zzab {
    public static int zza(int $i0, Object $r0) throws  {
        if ($i0 != 0) {
            return $i0;
        }
        throw new IllegalArgumentException(String.valueOf($r0));
    }

    public static long zza(long $l0, Object $r0) throws  {
        if ($l0 != 0) {
            return $l0;
        }
        throw new IllegalArgumentException(String.valueOf($r0));
    }

    public static void zza(boolean $z0, Object $r0) throws  {
        if (!$z0) {
            throw new IllegalStateException(String.valueOf($r0));
        }
    }

    public static void zza(boolean $z0, String $r0, Object... $r1) throws  {
        if (!$z0) {
            throw new IllegalStateException(String.format($r0, $r1));
        }
    }

    public static <T> T zzag(@Signature({"<T:", "Ljava/lang/Object;", ">(TT;)TT;"}) T $r0) throws  {
        if ($r0 != null) {
            return $r0;
        }
        throw new NullPointerException("null reference");
    }

    public static void zzaxb() throws  {
        zzgq("Must not be called on the main application thread");
    }

    public static <T> T zzb(@Signature({"<T:", "Ljava/lang/Object;", ">(TT;", "Ljava/lang/Object;", ")TT;"}) T $r0, @Signature({"<T:", "Ljava/lang/Object;", ">(TT;", "Ljava/lang/Object;", ")TT;"}) Object $r1) throws  {
        if ($r0 != null) {
            return $r0;
        }
        throw new NullPointerException(String.valueOf($r1));
    }

    public static void zzb(boolean $z0, Object $r0) throws  {
        if (!$z0) {
            throw new IllegalArgumentException(String.valueOf($r0));
        }
    }

    public static void zzb(boolean $z0, String $r0, Object... $r1) throws  {
        if (!$z0) {
            throw new IllegalArgumentException(String.format($r0, $r1));
        }
    }

    public static void zzbm(boolean $z0) throws  {
        if (!$z0) {
            throw new IllegalStateException();
        }
    }

    public static void zzbn(boolean $z0) throws  {
        if (!$z0) {
            throw new IllegalArgumentException();
        }
    }

    public static void zzgp(String $r0) throws  {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            throw new IllegalStateException($r0);
        }
    }

    public static void zzgq(String $r0) throws  {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new IllegalStateException($r0);
        }
    }

    public static String zzgy(String $r0) throws  {
        if (!TextUtils.isEmpty($r0)) {
            return $r0;
        }
        throw new IllegalArgumentException("Given String is empty or null");
    }

    public static String zzi(String $r0, Object $r1) throws  {
        if (!TextUtils.isEmpty($r0)) {
            return $r0;
        }
        throw new IllegalArgumentException(String.valueOf($r1));
    }

    public static int zziq(int $i0) throws  {
        if ($i0 != 0) {
            return $i0;
        }
        throw new IllegalArgumentException("Given Integer is zero");
    }
}
