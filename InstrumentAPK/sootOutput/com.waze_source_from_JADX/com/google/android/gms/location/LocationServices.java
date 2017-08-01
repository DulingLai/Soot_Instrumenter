package com.google.android.gms.location;

import android.content.Context;
import android.os.Looper;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.ApiOptions.NoOptions;
import com.google.android.gms.common.api.Api.zze;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.common.internal.zzg;
import com.google.android.gms.location.internal.zzd;
import com.google.android.gms.location.internal.zzf;
import com.google.android.gms.location.internal.zzl;
import com.google.android.gms.location.internal.zzq;

/* compiled from: dalvik_source_com.waze.apk */
public class LocationServices {
    public static final Api<NoOptions> API = new Api("LocationServices.API", cb, ca);
    public static final FusedLocationProviderApi FusedLocationApi = new zzd();
    public static final GeofencingApi GeofencingApi = new zzf();
    public static final SettingsApi SettingsApi = new zzq();
    private static final Api.zzf<zzl> ca = new Api.zzf();
    private static final com.google.android.gms.common.api.Api.zza<zzl, NoOptions> cb = new C09391();

    /* compiled from: dalvik_source_com.waze.apk */
    class C09391 extends com.google.android.gms.common.api.Api.zza<zzl, NoOptions> {
        C09391() throws  {
        }

        public /* synthetic */ zze zza(Context $r1, Looper $r2, zzg $r3, Object $r4, ConnectionCallbacks $r5, OnConnectionFailedListener $r6) throws  {
            return zzz($r1, $r2, $r3, (NoOptions) $r4, $r5, $r6);
        }

        public zzl zzz(Context $r1, Looper $r2, zzg $r3, NoOptions noOptions, ConnectionCallbacks $r5, OnConnectionFailedListener $r6) throws  {
            return new zzl($r1, $r2, $r5, $r6, "locationServices", $r3);
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static abstract class zza<R extends Result> extends com.google.android.gms.internal.zzqk.zza<R, zzl> {
        public zza(GoogleApiClient $r1) throws  {
            super(LocationServices.API, $r1);
        }
    }

    private LocationServices() throws  {
    }

    public static zzl zzr(GoogleApiClient $r0) throws  {
        boolean $z0 = true;
        zzab.zzb($r0 != null, (Object) "GoogleApiClient parameter is required.");
        zzl $r3 = (zzl) $r0.zza(ca);
        if ($r3 == null) {
            $z0 = false;
        }
        zzab.zza($z0, (Object) "GoogleApiClient is not configured to use the LocationServices.API Api. Pass thisinto GoogleApiClient.Builder#addApi() to use this feature.");
        return $r3;
    }
}
