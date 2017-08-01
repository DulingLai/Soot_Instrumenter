package com.google.android.gms.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.ApiOptions.HasOptions;
import com.google.android.gms.common.api.Api.zzf;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.signin.internal.zzg;

/* compiled from: dalvik_source_com.waze.apk */
public final class zzacg {
    public static final Api<zzaci> API = new Api("SignIn.API", cb, ca);
    public static final Api<zza> RQ = new Api("SignIn.INTERNAL_API", bgl, bgk);
    public static final zzf<zzg> bgk = new zzf();
    static final com.google.android.gms.common.api.Api.zza<zzg, zza> bgl = new C07572();
    public static final zzf<zzg> ca = new zzf();
    public static final com.google.android.gms.common.api.Api.zza<zzg, zzaci> cb = new C07561();
    public static final Scope fE = new Scope(Scopes.PROFILE);
    public static final Scope fF = new Scope("email");

    /* compiled from: dalvik_source_com.waze.apk */
    class C07561 extends com.google.android.gms.common.api.Api.zza<zzg, zzaci> {
        C07561() throws  {
        }

        public zzg zza(Context $r1, Looper $r2, com.google.android.gms.common.internal.zzg $r3, zzaci $r4, ConnectionCallbacks $r5, OnConnectionFailedListener $r6) throws  {
            if ($r4 == null) {
                $r4 = zzaci.bgm;
            }
            return new zzg($r1, $r2, true, $r3, $r4, $r5, $r6);
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    class C07572 extends com.google.android.gms.common.api.Api.zza<zzg, zza> {
        C07572() throws  {
        }

        public zzg zza(Context $r1, Looper $r2, com.google.android.gms.common.internal.zzg $r3, zza $r4, ConnectionCallbacks $r5, OnConnectionFailedListener $r6) throws  {
            return new zzg($r1, $r2, false, $r3, $r4.zzcmu(), $r5, $r6);
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static class zza implements HasOptions {
        public Bundle zzcmu() throws  {
            return null;
        }
    }
}
