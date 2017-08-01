package com.google.android.gms.plus.internal;

import android.content.Context;
import android.os.IBinder;
import android.view.View;
import com.google.android.gms.dynamic.zze;
import com.google.android.gms.dynamic.zzg;
import com.google.android.gms.plus.PlusOneDummyView;
import com.google.android.gms.plus.internal.zzc.zza;

/* compiled from: dalvik_source_com.waze.apk */
public final class zzh extends zzg<zzc> {
    private static final zzh aYE = new zzh();

    private zzh() throws  {
        super("com.google.android.gms.plus.plusone.PlusOneButtonCreatorImpl");
    }

    public static View zza(Context $r0, int $i0, int $i1, String $r1, int $i2) throws  {
        if ($r1 == null) {
            NullPointerException r9;
            NullPointerException $r2 = r9;
            try {
                r9 = new NullPointerException();
                throw $r2;
            } catch (Exception e) {
                return new PlusOneDummyView($r0, $i0);
            }
        }
        return (View) zze.zzae(((zzc) aYE.zzcm($r0)).zza(zze.zzan($r0), $i0, $i1, $r1, $i2));
    }

    public static View zza(Context $r0, int $i0, int $i1, String $r1, String $r2) throws  {
        if ($r1 == null) {
            NullPointerException r10;
            NullPointerException $r3 = r10;
            try {
                r10 = new NullPointerException();
                throw $r3;
            } catch (Exception e) {
                return new PlusOneDummyView($r0, $i0);
            }
        }
        return (View) zze.zzae(((zzc) aYE.zzcm($r0)).zza(zze.zzan($r0), $i0, $i1, $r1, $r2));
    }

    protected /* synthetic */ Object zzd(IBinder $r1) throws  {
        return zzpa($r1);
    }

    protected zzc zzpa(IBinder $r1) throws  {
        return zza.zzow($r1);
    }
}
