package com.google.android.gms.auth.api.credentials.internal;

import android.content.Context;
import android.os.DeadObjectException;
import android.os.RemoteException;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.internal.zzqk.zza;

/* compiled from: dalvik_source_com.waze.apk */
abstract class zzf<R extends Result> extends zza<R, zzg> {
    zzf(GoogleApiClient $r1) throws  {
        super(Auth.CREDENTIALS_API, $r1);
    }

    protected abstract void zza(Context context, zzl com_google_android_gms_auth_api_credentials_internal_zzl) throws DeadObjectException, RemoteException;

    protected final void zza(zzg $r1) throws DeadObjectException, RemoteException {
        zza($r1.getContext(), (zzl) $r1.zzavx());
    }
}
