package com.google.android.gms.common.internal;

import android.os.Looper;
import android.util.Log;

/* compiled from: dalvik_source_com.waze.apk */
public final class zzb {
    public static void zza(boolean $z0, Object $r0) throws  {
        if (!$z0) {
            throw new IllegalStateException(String.valueOf($r0));
        }
    }

    public static void zzab(Object $r0) throws  {
        if ($r0 != null) {
            throw new IllegalArgumentException("non-null reference");
        }
    }

    public static void zzac(Object $r0) throws  {
        if ($r0 == null) {
            throw new IllegalArgumentException("null reference");
        }
    }

    public static void zzbm(boolean $z0) throws  {
        if (!$z0) {
            throw new IllegalStateException();
        }
    }

    public static void zzgp(String $r0) throws  {
        if (Looper.getMainLooper().getThread() != Thread.currentThread()) {
            String $r4 = String.valueOf(Thread.currentThread());
            String $r5 = String.valueOf(Looper.getMainLooper().getThread());
            Log.e("Asserts", new StringBuilder((String.valueOf($r4).length() + 57) + String.valueOf($r5).length()).append("checkMainThread: current thread ").append($r4).append(" IS NOT the main thread ").append($r5).append("!").toString());
            throw new IllegalStateException($r0);
        }
    }

    public static void zzgq(String $r0) throws  {
        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
            String $r4 = String.valueOf(Thread.currentThread());
            String $r5 = String.valueOf(Looper.getMainLooper().getThread());
            Log.e("Asserts", new StringBuilder((String.valueOf($r4).length() + 56) + String.valueOf($r5).length()).append("checkNotMainThread: current thread ").append($r4).append(" IS the main thread ").append($r5).append("!").toString());
            throw new IllegalStateException($r0);
        }
    }
}
