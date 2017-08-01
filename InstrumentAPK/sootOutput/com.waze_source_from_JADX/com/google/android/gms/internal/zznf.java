package com.google.android.gms.internal;

import android.content.Context;
import android.os.RemoteException;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.proxy.ProxyApi.ProxyResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzqk.zza;

/* compiled from: dalvik_source_com.waze.apk */
abstract class zznf extends zza<ProxyResult, zznc> {
    public zznf(GoogleApiClient $r1) throws  {
        super(Auth.PROXY_API, $r1);
    }

    protected abstract void zza(Context context, zzne com_google_android_gms_internal_zzne) throws RemoteException;

    protected final void zza(zznc $r1) throws RemoteException {
        zza($r1.getContext(), (zzne) $r1.zzavx());
    }

    protected /* synthetic */ Result zzb(Status $r1) throws  {
        return zzn($r1);
    }

    protected ProxyResult zzn(Status $r1) throws  {
        return new zzni($r1);
    }
}
