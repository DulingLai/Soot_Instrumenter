package com.google.android.gms.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.text.TextUtils;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.Auth.AuthProxyOptions;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.internal.zzg;
import com.google.android.gms.common.internal.zzk;
import com.google.android.gms.internal.zzne.zza;

/* compiled from: dalvik_source_com.waze.apk */
public final class zznc extends zzk<zzne> {
    private final Bundle eB;

    public zznc(Context $r1, Looper $r2, zzg $r3, AuthProxyOptions $r4, ConnectionCallbacks $r5, OnConnectionFailedListener $r6) throws  {
        super($r1, $r2, 16, $r3, $r5, $r6);
        this.eB = $r4 == null ? new Bundle() : $r4.getAuthenticationOptions();
    }

    protected Bundle zzadn() throws  {
        return this.eB;
    }

    public boolean zzaec() throws  {
        zzg $r1 = zzaws();
        return (TextUtils.isEmpty($r1.getAccountName()) || $r1.zzb(Auth.PROXY_API).isEmpty()) ? false : true;
    }

    protected /* synthetic */ IInterface zzbc(IBinder $r1) throws  {
        return zzcs($r1);
    }

    protected zzne zzcs(IBinder $r1) throws  {
        return zza.zzcu($r1);
    }

    protected String zzrg() throws  {
        return "com.google.android.gms.auth.service.START";
    }

    protected String zzrh() throws  {
        return "com.google.android.gms.auth.api.internal.IAuthService";
    }
}
