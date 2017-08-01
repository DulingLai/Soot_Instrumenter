package com.google.android.gms.internal;

import android.content.Context;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.support.annotation.NonNull;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.internal.zzg;
import com.google.android.gms.common.internal.zzk;
import com.google.android.gms.internal.zzna.zza;

/* compiled from: dalvik_source_com.waze.apk */
public class zzmx extends zzk<zzna> {
    public zzmx(Context $r1, Looper $r2, zzg $r3, ConnectionCallbacks $r4, OnConnectionFailedListener $r5) throws  {
        super($r1, $r2, 74, $r3, $r4, $r5);
    }

    protected /* synthetic */ IInterface zzbc(IBinder $r1) throws  {
        return zzcm($r1);
    }

    protected zzna zzcm(IBinder $r1) throws  {
        return zza.zzco($r1);
    }

    @NonNull
    protected String zzrg() throws  {
        return "com.google.android.gms.auth.api.accountactivationstate.START";
    }

    @NonNull
    protected String zzrh() throws  {
        return "com.google.android.gms.auth.api.accountactivationstate.internal.IAccountActivationStateService";
    }
}
