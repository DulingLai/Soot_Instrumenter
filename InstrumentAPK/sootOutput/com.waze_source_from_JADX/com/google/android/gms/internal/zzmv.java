package com.google.android.gms.internal;

import android.content.Context;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import com.google.android.gms.auth.account.zzb;
import com.google.android.gms.auth.account.zzb.zza;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.internal.zzg;
import com.google.android.gms.common.internal.zzk;

/* compiled from: dalvik_source_com.waze.apk */
public class zzmv extends zzk<zzb> {
    public zzmv(Context $r1, Looper $r2, zzg $r3, ConnectionCallbacks $r4, OnConnectionFailedListener $r5) throws  {
        super($r1, $r2, 120, $r3, $r4, $r5);
    }

    protected /* synthetic */ IInterface zzbc(IBinder $r1) throws  {
        return zzcl($r1);
    }

    protected zzb zzcl(IBinder $r1) throws  {
        return zza.zzck($r1);
    }

    protected String zzrg() throws  {
        return "com.google.android.gms.auth.account.workaccount.START";
    }

    protected String zzrh() throws  {
        return "com.google.android.gms.auth.account.IWorkAccountService";
    }
}
