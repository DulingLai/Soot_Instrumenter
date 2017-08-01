package com.google.android.gms.location.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.internal.zzg;
import com.google.android.gms.common.internal.zzk;
import com.google.android.gms.location.internal.zzi.zza;

/* compiled from: dalvik_source_com.waze.apk */
public class zzb extends zzk<zzi> {
    private final String avF;
    protected final zzp<zzi> avG = new C09491(this);

    /* compiled from: dalvik_source_com.waze.apk */
    class C09491 implements zzp<zzi> {
        final /* synthetic */ zzb avH;

        C09491(zzb $r1) throws  {
            this.avH = $r1;
        }

        public void zzavw() throws  {
            this.avH.zzavw();
        }

        public /* synthetic */ IInterface zzavx() throws DeadObjectException {
            return zzbsg();
        }

        public zzi zzbsg() throws DeadObjectException {
            return (zzi) this.avH.zzavx();
        }
    }

    public zzb(Context $r1, Looper $r2, ConnectionCallbacks $r3, OnConnectionFailedListener $r4, String $r5, zzg $r6) throws  {
        super($r1, $r2, 23, $r6, $r3, $r4);
        this.avF = $r5;
    }

    protected Bundle zzadn() throws  {
        Bundle $r1 = new Bundle();
        $r1.putString("client_name", this.avF);
        return $r1;
    }

    protected /* synthetic */ IInterface zzbc(IBinder $r1) throws  {
        return zzkw($r1);
    }

    protected zzi zzkw(IBinder $r1) throws  {
        return zza.zzkz($r1);
    }

    protected String zzrg() throws  {
        return "com.google.android.location.internal.GoogleLocationManagerService.START";
    }

    protected String zzrh() throws  {
        return "com.google.android.gms.location.internal.IGoogleLocationManagerService";
    }
}
