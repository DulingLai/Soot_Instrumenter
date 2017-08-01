package com.google.android.gms.auth.api.credentials.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.support.annotation.Nullable;
import com.google.android.gms.auth.api.Auth.AuthCredentialsOptions;
import com.google.android.gms.auth.api.credentials.internal.zzl.zza;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.internal.zzk;

/* compiled from: dalvik_source_com.waze.apk */
public final class zzg extends zzk<zzl> {
    @Nullable
    private final AuthCredentialsOptions fj;

    public zzg(Context $r1, Looper $r2, com.google.android.gms.common.internal.zzg $r3, AuthCredentialsOptions $r4, ConnectionCallbacks $r5, OnConnectionFailedListener $r6) throws  {
        super($r1, $r2, 68, $r3, $r5, $r6);
        this.fj = $r4;
    }

    protected Bundle zzadn() throws  {
        return this.fj == null ? new Bundle() : this.fj.zzadn();
    }

    AuthCredentialsOptions zzaeb() throws  {
        return this.fj;
    }

    protected /* synthetic */ IInterface zzbc(IBinder $r1) throws  {
        return zzcp($r1);
    }

    protected zzl zzcp(IBinder $r1) throws  {
        return zza.zzcr($r1);
    }

    protected String zzrg() throws  {
        return "com.google.android.gms.auth.api.credentials.service.START";
    }

    protected String zzrh() throws  {
        return "com.google.android.gms.auth.api.credentials.internal.ICredentialsService";
    }
}
