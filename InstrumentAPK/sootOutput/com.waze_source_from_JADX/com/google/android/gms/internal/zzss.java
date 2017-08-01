package com.google.android.gms.internal;

import android.content.Context;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.internal.zzg;
import com.google.android.gms.common.internal.zzk;
import com.google.android.gms.internal.zzsu.zza;

/* compiled from: dalvik_source_com.waze.apk */
public class zzss extends zzk<zzsu> {
    public zzss(Context $r1, Looper $r2, zzg $r3, ConnectionCallbacks $r4, OnConnectionFailedListener $r5) throws  {
        super($r1, $r2, 39, $r3, $r4, $r5);
    }

    protected /* synthetic */ IInterface zzbc(IBinder $r1) throws  {
        return zzha($r1);
    }

    protected zzsu zzha(IBinder $r1) throws  {
        return zza.zzhc($r1);
    }

    public String zzrg() throws  {
        return "com.google.android.gms.common.service.START";
    }

    protected String zzrh() throws  {
        return "com.google.android.gms.common.internal.service.ICommonService";
    }
}
