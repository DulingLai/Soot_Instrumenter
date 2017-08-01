package com.google.android.gms.location.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzqk.zzb;
import com.google.android.gms.location.LocationServices.zza;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.SettingsApi;
import dalvik.annotation.Signature;

/* compiled from: dalvik_source_com.waze.apk */
public class zzq implements SettingsApi {
    public PendingResult<LocationSettingsResult> checkLocationSettings(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/location/LocationSettingsRequest;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/location/LocationSettingsResult;", ">;"}) GoogleApiClient $r1, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/location/LocationSettingsRequest;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/location/LocationSettingsResult;", ">;"}) LocationSettingsRequest $r2) throws  {
        return zza($r1, $r2, null);
    }

    public PendingResult<LocationSettingsResult> zza(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/location/LocationSettingsRequest;", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/location/LocationSettingsResult;", ">;"}) GoogleApiClient $r1, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/location/LocationSettingsRequest;", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/location/LocationSettingsResult;", ">;"}) final LocationSettingsRequest $r2, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/location/LocationSettingsRequest;", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/location/LocationSettingsResult;", ">;"}) final String $r3) throws  {
        return $r1.zzc(new zza<LocationSettingsResult>(this, $r1) {
            final /* synthetic */ zzq awp;

            protected void zza(zzl $r1) throws RemoteException {
                $r1.zza($r2, (zzb) this, $r3);
            }

            public /* synthetic */ Result zzb(Status $r1) throws  {
                return zzes($r1);
            }

            public LocationSettingsResult zzes(Status $r1) throws  {
                return new LocationSettingsResult($r1);
            }
        });
    }
}
