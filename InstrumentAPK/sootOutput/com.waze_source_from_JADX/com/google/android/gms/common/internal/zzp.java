package com.google.android.gms.common.internal;

import android.util.Log;

/* compiled from: dalvik_source_com.waze.apk */
public final class zzp {
    public static final int JZ = (23 - " PII_LOG".length());
    private static final String Ka = null;
    private final String Kb;
    private final String Kc;

    public zzp(String $r1) throws  {
        this($r1, null);
    }

    public zzp(String $r1, String $r2) throws  {
        zzab.zzb((Object) $r1, (Object) "log tag cannot be null");
        zzab.zzb($r1.length() <= 23, "tag \"%s\" is longer than the %d character maximum", $r1, Integer.valueOf(23));
        this.Kb = $r1;
        if ($r2 == null || $r2.length() <= 0) {
            this.Kc = null;
        } else {
            this.Kc = $r2;
        }
    }

    private String zzgw(String $r1) throws  {
        return this.Kc == null ? $r1 : this.Kc.concat($r1);
    }

    public void zzal(String $r1, String $r2) throws  {
        if (zzip(3)) {
            Log.d($r1, zzgw($r2));
        }
    }

    public void zzam(String $r1, String $r2) throws  {
        if (zzip(4)) {
            Log.i($r1, zzgw($r2));
        }
    }

    public void zzan(String $r1, String $r2) throws  {
        if (zzip(5)) {
            Log.w($r1, zzgw($r2));
        }
    }

    public void zzao(String $r1, String $r2) throws  {
        if (zzip(6)) {
            Log.e($r1, zzgw($r2));
        }
    }

    public void zzb(String $r1, String $r2, Throwable $r3) throws  {
        if (zzip(4)) {
            Log.i($r1, zzgw($r2), $r3);
        }
    }

    public void zzc(String $r1, String $r2, Throwable $r3) throws  {
        if (zzip(5)) {
            Log.w($r1, zzgw($r2), $r3);
        }
    }

    public void zzd(String $r1, String $r2, Throwable $r3) throws  {
        if (zzip(6)) {
            Log.e($r1, zzgw($r2), $r3);
        }
    }

    public void zze(String $r1, String $r2, Throwable $r3) throws  {
        if (zzip(7)) {
            Log.e($r1, zzgw($r2), $r3);
            Log.wtf($r1, zzgw($r2), $r3);
        }
    }

    public boolean zzip(int $i0) throws  {
        return Log.isLoggable(this.Kb, $i0);
    }
}
