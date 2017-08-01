package com.google.android.gms.auth.account;

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
import com.google.android.gms.internal.zzmu;
import com.google.android.gms.internal.zzmv;

/* compiled from: dalvik_source_com.waze.apk */
public class WorkAccount {
    public static final Api<NoOptions> API = new Api("WorkAccount.API", cb, ca);
    public static final WorkAccountApi WorkAccountApi = new zzmu();
    private static final zzf<zzmv> ca = new zzf();
    private static final zza<zzmv, NoOptions> cb = new C06551();

    /* compiled from: dalvik_source_com.waze.apk */
    class C06551 extends zza<zzmv, NoOptions> {
        C06551() throws  {
        }

        public /* synthetic */ zze zza(Context $r1, Looper $r2, zzg $r3, Object $r4, ConnectionCallbacks $r5, OnConnectionFailedListener $r6) throws  {
            return zzd($r1, $r2, $r3, (NoOptions) $r4, $r5, $r6);
        }

        public zzmv zzd(Context $r1, Looper $r2, zzg $r3, NoOptions noOptions, ConnectionCallbacks $r5, OnConnectionFailedListener $r6) throws  {
            return new zzmv($r1, $r2, $r3, $r5, $r6);
        }
    }

    private WorkAccount() throws  {
    }
}
