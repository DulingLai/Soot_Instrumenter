package com.google.android.gms.internal;

import android.content.Context;
import android.os.Looper;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.ApiOptions.NoOptions;
import com.google.android.gms.common.api.Api.zza;
import com.google.android.gms.common.api.Api.zze;
import com.google.android.gms.common.api.Api.zzf;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.internal.zzg;

/* compiled from: dalvik_source_com.waze.apk */
public final class zzso {
    public static final Api<NoOptions> API = new Api("Common.API", cb, ca);
    public static final zzsp Ks = new zzsq();
    public static final zzf<zzss> ca = new zzf();
    private static final zza<zzss, NoOptions> cb = new C08581();

    /* compiled from: dalvik_source_com.waze.apk */
    class C08581 extends zza<zzss, NoOptions> {
        C08581() throws  {
        }

        public /* synthetic */ zze zza(Context $r1, Looper $r2, zzg $r3, Object $r4, ConnectionCallbacks $r5, OnConnectionFailedListener $r6) throws  {
            return zzj($r1, $r2, $r3, (NoOptions) $r4, $r5, $r6);
        }

        public zzss zzj(Context $r1, Looper $r2, zzg $r3, NoOptions noOptions, ConnectionCallbacks $r5, OnConnectionFailedListener $r6) throws  {
            return new zzss($r1, $r2, $r3, $r5, $r6);
        }
    }
}
