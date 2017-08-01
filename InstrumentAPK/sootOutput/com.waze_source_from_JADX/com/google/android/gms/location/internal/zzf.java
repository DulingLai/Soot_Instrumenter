package com.google.android.gms.location.internal;

import android.app.PendingIntent;
import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzqk.zzb;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingApi;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.GeofencingRequest.Builder;
import dalvik.annotation.Signature;
import java.util.List;

/* compiled from: dalvik_source_com.waze.apk */
public class zzf implements GeofencingApi {

    /* compiled from: dalvik_source_com.waze.apk */
    private static abstract class zza extends com.google.android.gms.location.LocationServices.zza<Status> {
        public zza(GoogleApiClient $r1) throws  {
            super($r1);
        }

        public /* synthetic */ Result zzb(Status $r1) throws  {
            return zzd($r1);
        }

        public Status zzd(Status $r1) throws  {
            return $r1;
        }
    }

    public PendingResult<Status> addGeofences(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/location/GeofencingRequest;", "Landroid/app/PendingIntent;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) GoogleApiClient $r1, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/location/GeofencingRequest;", "Landroid/app/PendingIntent;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) final GeofencingRequest $r2, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/location/GeofencingRequest;", "Landroid/app/PendingIntent;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) final PendingIntent $r3) throws  {
        return $r1.zzd(new zza(this, $r1) {
            final /* synthetic */ zzf avT;

            protected void zza(zzl $r1) throws RemoteException {
                $r1.zza($r2, $r3, (zzb) this);
            }
        });
    }

    @Deprecated
    public PendingResult<Status> addGeofences(@Deprecated @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/util/List", "<", "Lcom/google/android/gms/location/Geofence;", ">;", "Landroid/app/PendingIntent;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) GoogleApiClient $r1, @Deprecated @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/util/List", "<", "Lcom/google/android/gms/location/Geofence;", ">;", "Landroid/app/PendingIntent;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) List<Geofence> $r2, @Deprecated @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/util/List", "<", "Lcom/google/android/gms/location/Geofence;", ">;", "Landroid/app/PendingIntent;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) PendingIntent $r3) throws  {
        Builder $r4 = new Builder();
        $r4.addGeofences($r2);
        $r4.setInitialTrigger(5);
        return addGeofences($r1, $r4.build(), $r3);
    }

    public PendingResult<Status> removeGeofences(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Landroid/app/PendingIntent;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) GoogleApiClient $r1, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Landroid/app/PendingIntent;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) final PendingIntent $r2) throws  {
        return $r1.zzd(new zza(this, $r1) {
            final /* synthetic */ zzf avT;

            protected void zza(zzl $r1) throws RemoteException {
                $r1.zzf($r2, this);
            }
        });
    }

    public PendingResult<Status> removeGeofences(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) GoogleApiClient $r1, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) final List<String> $r2) throws  {
        return $r1.zzd(new zza(this, $r1) {
            final /* synthetic */ zzf avT;

            protected void zza(zzl $r1) throws RemoteException {
                $r1.zza($r2, (zzb) this);
            }
        });
    }
}
