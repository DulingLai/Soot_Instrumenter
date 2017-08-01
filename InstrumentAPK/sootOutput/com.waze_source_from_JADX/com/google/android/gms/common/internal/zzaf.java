package com.google.android.gms.common.internal;

import android.content.Context;
import android.os.IBinder;
import android.view.View;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.dynamic.zze;
import com.google.android.gms.dynamic.zzg;
import com.google.android.gms.dynamic.zzg.zza;

/* compiled from: dalvik_source_com.waze.apk */
public final class zzaf extends zzg<zzx> {
    private static final zzaf Kl = new zzaf();

    private zzaf() throws  {
        super("com.google.android.gms.common.ui.SignInButtonCreatorImpl");
    }

    public static View zzb(Context $r0, int $i0, int $i1, Scope[] $r1) throws zza {
        return Kl.zzc($r0, $i0, $i1, $r1);
    }

    private View zzc(Context $r1, int $i0, int $i1, Scope[] $r2) throws zza {
        try {
            SignInButtonConfig $r3 = new SignInButtonConfig($i0, $i1, $r2);
            return (View) zze.zzae(((zzx) zzcm($r1)).zza(zze.zzan($r1), $r3));
        } catch (Exception $r8) {
            throw new zza("Could not get button with size " + $i0 + " and color " + $i1, $r8);
        }
    }

    public /* synthetic */ Object zzd(IBinder $r1) throws  {
        return zzgz($r1);
    }

    public zzx zzgz(IBinder $r1) throws  {
        return zzx.zza.zzgy($r1);
    }
}
