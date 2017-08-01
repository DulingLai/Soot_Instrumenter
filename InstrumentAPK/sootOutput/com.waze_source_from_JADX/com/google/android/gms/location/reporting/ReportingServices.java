package com.google.android.gms.location.reporting;

import android.content.Context;
import android.os.Looper;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.ApiOptions.NoOptions;
import com.google.android.gms.common.api.Api.zze;
import com.google.android.gms.common.api.Api.zzf;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.internal.zzg;
import com.google.android.gms.internal.zzxp;
import com.google.android.gms.internal.zzxq;

/* compiled from: dalvik_source_com.waze.apk */
public class ReportingServices {
    public static final Api<NoOptions> API = new Api("ReportingServices.API", cb, ca);
    public static Reporting ReportingApi = new zzxq();
    private static final zzf<zzxp> ca = new zzf();
    private static final com.google.android.gms.common.api.Api.zza<zzxp, NoOptions> cb = new C09651();

    /* compiled from: dalvik_source_com.waze.apk */
    public static abstract class zza<R extends Result> extends com.google.android.gms.internal.zzqk.zza<R, zzxp> {
        public zza(GoogleApiClient $r1) throws  {
            super(ReportingServices.API, $r1);
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    class C09651 extends com.google.android.gms.common.api.Api.zza<zzxp, NoOptions> {
        C09651() throws  {
        }

        public /* synthetic */ zze zza(Context $r1, Looper $r2, zzg $r3, Object $r4, ConnectionCallbacks $r5, OnConnectionFailedListener $r6) throws  {
            return zzaa($r1, $r2, $r3, (NoOptions) $r4, $r5, $r6);
        }

        public zzxp zzaa(Context $r1, Looper $r2, zzg $r3, NoOptions noOptions, ConnectionCallbacks $r5, OnConnectionFailedListener $r6) throws  {
            return new zzxp($r1, $r2, $r3, $r5, $r6);
        }
    }

    private ReportingServices() throws  {
    }
}
