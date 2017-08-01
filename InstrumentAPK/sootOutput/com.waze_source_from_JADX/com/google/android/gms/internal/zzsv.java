package com.google.android.gms.internal;

import android.util.Log;
import com.google.android.gms.common.internal.zzp;

/* compiled from: dalvik_source_com.waze.apk */
public class zzsv {
    private final String Kc;
    private final zzp Ku;
    private final String mTag;
    private final int zzcyv;

    private zzsv(String $r1, String $r2) throws  {
        this.Kc = $r2;
        this.mTag = $r1;
        this.Ku = new zzp($r1);
        this.zzcyv = getLogLevel();
    }

    public zzsv(String $r1, String... $r2) throws  {
        this($r1, zzc($r2));
    }

    private int getLogLevel() throws  {
        int $i0 = 2;
        while (7 >= $i0 && !Log.isLoggable(this.mTag, $i0)) {
            $i0++;
        }
        return $i0;
    }

    private static String zzc(String... $r0) throws  {
        if ($r0.length == 0) {
            return "";
        }
        StringBuilder $r1 = new StringBuilder();
        $r1.append('[');
        for (String $r2 : $r0) {
            if ($r1.length() > 1) {
                $r1.append(",");
            }
            $r1.append($r2);
        }
        $r1.append(']').append(' ');
        return $r1.toString();
    }

    protected String format(String $r2, Object... $r1) throws  {
        if ($r1 != null && $r1.length > 0) {
            $r2 = String.format($r2, $r1);
        }
        return this.Kc.concat($r2);
    }

    public void zza(String $r1, Object... $r2) throws  {
        if (zzbd(2)) {
            Log.v(this.mTag, format($r1, $r2));
        }
    }

    public boolean zzbd(int $i0) throws  {
        return this.zzcyv <= $i0;
    }
}
