package com.google.android.gms.location;

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
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.common.internal.zzg;
import com.google.android.gms.location.internal.zzl;

/* compiled from: dalvik_source_com.waze.apk */
public class ActivityRecognition {
    public static final Api<NoOptions> API = new Api("ActivityRecognition.API", cb, ca);
    public static final ActivityRecognitionApi ActivityRecognitionApi = new com.google.android.gms.location.internal.zza();
    public static final String CLIENT_NAME = "activity_recognition";
    private static final zzf<zzl> ca = new zzf();
    private static final com.google.android.gms.common.api.Api.zza<zzl, NoOptions> cb = new C09361();

    /* compiled from: dalvik_source_com.waze.apk */
    class C09361 extends com.google.android.gms.common.api.Api.zza<zzl, NoOptions> {
        C09361() throws  {
        }

        public /* synthetic */ zze zza(Context $r1, Looper $r2, zzg $r3, Object $r4, ConnectionCallbacks $r5, OnConnectionFailedListener $r6) throws  {
            return zzz($r1, $r2, $r3, (NoOptions) $r4, $r5, $r6);
        }

        public zzl zzz(Context $r1, Looper $r2, zzg com_google_android_gms_common_internal_zzg, NoOptions noOptions, ConnectionCallbacks $r5, OnConnectionFailedListener $r6) throws  {
            return new zzl($r1, $r2, $r5, $r6, ActivityRecognition.CLIENT_NAME);
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static abstract class zza<R extends Result> extends com.google.android.gms.internal.zzqk.zza<R, zzl> {
        public zza(GoogleApiClient $r1) throws  {
            super(ActivityRecognition.API, $r1);
        }
    }

    private ActivityRecognition() throws  {
    }

    public static zzl zzr(GoogleApiClient $r0) throws  {
        boolean $z0 = true;
        zzab.zzb($r0 != null, (Object) "GoogleApiClient parameter is required.");
        zzl $r3 = (zzl) $r0.zza(ca);
        if ($r3 == null) {
            $z0 = false;
        }
        zzab.zza($z0, (Object) "GoogleApiClient is not configured to use the ActivityRecognition.API Api. Pass this into GoogleApiClient.Builder#addApi() to use this feature.");
        return $r3;
    }
}
