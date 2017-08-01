package com.google.android.gms.people.internal;

import android.util.Log;

/* compiled from: dalvik_source_com.waze.apk */
public final class zzp {
    private static volatile boolean aQT;

    public static void zzal(String $r0, String $r1) throws  {
        if (zzip(3)) {
            Log.d($r0, $r1);
        }
    }

    public static void zzan(String $r0, String $r1) throws  {
        if (zzip(5)) {
            Log.w($r0, $r1);
        }
    }

    public static void zzao(String $r0, String $r1) throws  {
        if (zzip(6)) {
            Log.e($r0, $r1);
        }
    }

    public static void zzbr(String $r0, String $r1) throws  {
        if (zzip(2)) {
            Log.v($r0, $r1);
        }
    }

    public static void zzc(String $r0, String $r1, Throwable $r2) throws  {
        if (zzip(5)) {
            Log.w($r0, $r1, $r2);
        }
    }

    public static boolean zzcfd() throws  {
        return zzip(3);
    }

    public static boolean zzcfe() throws  {
        return zzip(2);
    }

    public static void zzd(String $r0, String $r1, Throwable $r2) throws  {
        if (zzip(6)) {
            Log.e($r0, $r1, $r2);
        }
    }

    public static boolean zzip(int $i0) throws  {
        return aQT || Log.isLoggable("PeopleService", $i0);
    }
}
