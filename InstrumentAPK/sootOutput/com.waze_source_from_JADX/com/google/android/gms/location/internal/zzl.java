package com.google.android.gms.location.internal;

import android.app.PendingIntent;
import android.content.Context;
import android.location.Location;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.common.internal.zzg;
import com.google.android.gms.internal.zzrv;
import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.GestureRequest;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationStatusCodes;
import dalvik.annotation.Signature;
import java.util.List;

/* compiled from: dalvik_source_com.waze.apk */
public class zzl extends zzb {
    private final zzk awb;

    /* compiled from: dalvik_source_com.waze.apk */
    private static final class zza extends com.google.android.gms.location.internal.zzh.zza {
        private com.google.android.gms.internal.zzqk.zzb<Status> awc;

        public zza(@Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/common/api/Status;", ">;)V"}) com.google.android.gms.internal.zzqk.zzb<Status> $r1) throws  {
            this.awc = $r1;
        }

        public void zza(int i, PendingIntent pendingIntent) throws  {
            Log.wtf("LocationClientImpl", "Unexpected call to onRemoveGeofencesByPendingIntentResult");
        }

        public void zza(int $i0, String[] strArr) throws  {
            if (this.awc == null) {
                Log.wtf("LocationClientImpl", "onAddGeofenceResult called multiple times");
                return;
            }
            this.awc.setResult(LocationStatusCodes.zzwn(LocationStatusCodes.zzwm($i0)));
            this.awc = null;
        }

        public void zzb(int i, String[] strArr) throws  {
            Log.wtf("LocationClientImpl", "Unexpected call to onRemoveGeofencesByRequestIdsResult");
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    private static final class zzb extends com.google.android.gms.location.internal.zzh.zza {
        private com.google.android.gms.internal.zzqk.zzb<Status> awc;

        public zzb(@Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/common/api/Status;", ">;)V"}) com.google.android.gms.internal.zzqk.zzb<Status> $r1) throws  {
            this.awc = $r1;
        }

        private void zzwt(int $i0) throws  {
            if (this.awc == null) {
                Log.wtf("LocationClientImpl", "onRemoveGeofencesResult called multiple times");
                return;
            }
            this.awc.setResult(LocationStatusCodes.zzwn(LocationStatusCodes.zzwm($i0)));
            this.awc = null;
        }

        public void zza(int $i0, PendingIntent pendingIntent) throws  {
            zzwt($i0);
        }

        public void zza(int i, String[] strArr) throws  {
            Log.wtf("LocationClientImpl", "Unexpected call to onAddGeofencesResult");
        }

        public void zzb(int $i0, String[] strArr) throws  {
            zzwt($i0);
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    private static final class zzc extends com.google.android.gms.location.internal.zzj.zza {
        private com.google.android.gms.internal.zzqk.zzb<LocationSettingsResult> awc;

        public zzc(@Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/location/LocationSettingsResult;", ">;)V"}) com.google.android.gms.internal.zzqk.zzb<LocationSettingsResult> $r1) throws  {
            zzab.zzb($r1 != null, (Object) "listener can't be null.");
            this.awc = $r1;
        }

        public void zza(LocationSettingsResult $r1) throws RemoteException {
            this.awc.setResult($r1);
            this.awc = null;
        }
    }

    public zzl(Context $r1, Looper $r2, ConnectionCallbacks $r3, OnConnectionFailedListener $r4, String $r5) throws  {
        this($r1, $r2, $r3, $r4, $r5, zzg.zzby($r1));
    }

    public zzl(Context $r1, Looper $r2, ConnectionCallbacks $r3, OnConnectionFailedListener $r4, String $r5, zzg $r6) throws  {
        super($r1, $r2, $r3, $r4, $r5, $r6);
        this.awb = new zzk($r1, this.avG);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void disconnect() throws  {
        /*
        r7 = this;
        r0 = r7.awb;
        monitor-enter(r0);
        r1 = r7.isConnected();	 Catch:{ Throwable -> 0x0021 }
        if (r1 == 0) goto L_0x0013;
    L_0x0009:
        r2 = r7.awb;	 Catch:{ Exception -> 0x0018 }
        r2.removeAllListeners();	 Catch:{ Exception -> 0x0018 }
        r2 = r7.awb;	 Catch:{ Exception -> 0x0018 }
        r2.zzbsj();	 Catch:{ Exception -> 0x0018 }
    L_0x0013:
        super.disconnect();	 Catch:{ Throwable -> 0x0021 }
        monitor-exit(r0);	 Catch:{ Throwable -> 0x0021 }
        return;
    L_0x0018:
        r3 = move-exception;
        r4 = "LocationClientImpl";
        r5 = "Client disconnected before listeners could be cleaned up";
        android.util.Log.e(r4, r5, r3);	 Catch:{ Throwable -> 0x0021 }
        goto L_0x0013;
    L_0x0021:
        r6 = move-exception;
        monitor-exit(r0);	 Catch:{ Throwable -> 0x0021 }
        throw r6;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.location.internal.zzl.disconnect():void");
    }

    public Location getLastLocation() throws  {
        return this.awb.getLastLocation();
    }

    public void zza(long $l0, PendingIntent $r1) throws RemoteException {
        zzavw();
        zzab.zzag($r1);
        zzab.zzb($l0 >= 0, (Object) "detectionIntervalMillis must be >= 0");
        ((zzi) zzavx()).zza($l0, true, $r1);
    }

    public void zza(@Signature({"(", "Landroid/app/PendingIntent;", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/common/api/Status;", ">;)V"}) PendingIntent $r1, @Signature({"(", "Landroid/app/PendingIntent;", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/common/api/Status;", ">;)V"}) com.google.android.gms.internal.zzqk.zzb<Status> $r2) throws RemoteException {
        zzavw();
        zzab.zzb((Object) $r1, (Object) "PendingIntent must be specified.");
        zzab.zzb((Object) $r2, (Object) "ResultHolder not provided.");
        ((zzi) zzavx()).zza($r1, new zzrv($r2));
    }

    public void zza(PendingIntent $r1, zzg $r2) throws RemoteException {
        this.awb.zza($r1, $r2);
    }

    public void zza(Location $r1, int $i0) throws RemoteException {
        this.awb.zza($r1, $i0);
    }

    public void zza(@Signature({"(", "Lcom/google/android/gms/location/GeofencingRequest;", "Landroid/app/PendingIntent;", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/common/api/Status;", ">;)V"}) GeofencingRequest $r1, @Signature({"(", "Lcom/google/android/gms/location/GeofencingRequest;", "Landroid/app/PendingIntent;", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/common/api/Status;", ">;)V"}) PendingIntent $r2, @Signature({"(", "Lcom/google/android/gms/location/GeofencingRequest;", "Landroid/app/PendingIntent;", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/common/api/Status;", ">;)V"}) com.google.android.gms.internal.zzqk.zzb<Status> $r3) throws RemoteException {
        zzavw();
        zzab.zzb((Object) $r1, (Object) "geofencingRequest can't be null.");
        zzab.zzb((Object) $r2, (Object) "PendingIntent must be specified.");
        zzab.zzb((Object) $r3, (Object) "ResultHolder not provided.");
        ((zzi) zzavx()).zza($r1, $r2, new zza($r3));
    }

    public void zza(@Signature({"(", "Lcom/google/android/gms/location/GestureRequest;", "Landroid/app/PendingIntent;", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/common/api/Status;", ">;)V"}) GestureRequest $r1, @Signature({"(", "Lcom/google/android/gms/location/GestureRequest;", "Landroid/app/PendingIntent;", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/common/api/Status;", ">;)V"}) PendingIntent $r2, @Signature({"(", "Lcom/google/android/gms/location/GestureRequest;", "Landroid/app/PendingIntent;", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/common/api/Status;", ">;)V"}) com.google.android.gms.internal.zzqk.zzb<Status> $r3) throws RemoteException {
        zzavw();
        zzab.zzb((Object) $r1, (Object) "GestureRequest can't be null.");
        zzab.zzb((Object) $r2, (Object) "Pending Intent must be specified.");
        zzab.zzb((Object) $r3, (Object) "ResultHolder not provided.");
        ((zzi) zzavx()).zza($r1, $r2, new zzrv($r3));
    }

    public void zza(LocationCallback $r1, zzg $r2) throws RemoteException {
        this.awb.zza($r1, $r2);
    }

    public void zza(LocationListener $r1, zzg $r2) throws RemoteException {
        this.awb.zza($r1, $r2);
    }

    public void zza(LocationRequest $r1, PendingIntent $r2, zzg $r3) throws RemoteException {
        this.awb.zza($r1, $r2, $r3);
    }

    public void zza(LocationRequest $r1, LocationListener $r2, Looper $r3, zzg $r4) throws RemoteException {
        synchronized (this.awb) {
            this.awb.zza($r1, $r2, $r3, $r4);
        }
    }

    public void zza(@Signature({"(", "Lcom/google/android/gms/location/LocationSettingsRequest;", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/location/LocationSettingsResult;", ">;", "Ljava/lang/String;", ")V"}) LocationSettingsRequest $r1, @Signature({"(", "Lcom/google/android/gms/location/LocationSettingsRequest;", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/location/LocationSettingsResult;", ">;", "Ljava/lang/String;", ")V"}) com.google.android.gms.internal.zzqk.zzb<LocationSettingsResult> $r2, @Signature({"(", "Lcom/google/android/gms/location/LocationSettingsRequest;", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/location/LocationSettingsResult;", ">;", "Ljava/lang/String;", ")V"}) String $r3) throws RemoteException {
        boolean $z0 = true;
        zzavw();
        zzab.zzb($r1 != null, (Object) "locationSettingsRequest can't be null nor empty.");
        if ($r2 == null) {
            $z0 = false;
        }
        zzab.zzb($z0, (Object) "listener can't be null.");
        ((zzi) zzavx()).zza($r1, new zzc($r2), $r3);
    }

    public void zza(LocationRequestInternal $r1, LocationCallback $r2, Looper $r3, zzg $r4) throws RemoteException {
        synchronized (this.awb) {
            this.awb.zza($r1, $r2, $r3, $r4);
        }
    }

    public void zza(zzg $r1) throws RemoteException {
        this.awb.zza($r1);
    }

    public void zza(@Signature({"(", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/common/api/Status;", ">;)V"}) List<String> $r1, @Signature({"(", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/common/api/Status;", ">;)V"}) com.google.android.gms.internal.zzqk.zzb<Status> $r2) throws RemoteException {
        zzavw();
        boolean $z0 = $r1 != null && $r1.size() > 0;
        zzab.zzb($z0, (Object) "geofenceRequestIds can't be null nor empty.");
        zzab.zzb((Object) $r2, (Object) "ResultHolder not provided.");
        ((zzi) zzavx()).zza((String[]) $r1.toArray(new String[0]), new zzb($r2), getContext().getPackageName());
    }

    public void zzb(PendingIntent $r1) throws RemoteException {
        zzavw();
        zzab.zzag($r1);
        ((zzi) zzavx()).zzb($r1);
    }

    public void zzb(@Signature({"(", "Landroid/app/PendingIntent;", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/common/api/Status;", ">;)V"}) PendingIntent $r1, @Signature({"(", "Landroid/app/PendingIntent;", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/common/api/Status;", ">;)V"}) com.google.android.gms.internal.zzqk.zzb<Status> $r2) throws RemoteException {
        zzavw();
        zzab.zzb((Object) $r1, (Object) "PendingIntent must be specified.");
        zzab.zzb((Object) $r2, (Object) "ResultHolder not provided.");
        ((zzi) zzavx()).zzb($r1, new zzrv($r2));
    }

    public LocationAvailability zzbsi() throws  {
        return this.awb.zzbsi();
    }

    public ActivityRecognitionResult zzbsk() throws RemoteException {
        zzavw();
        return ((zzi) zzavx()).zzkg(getContext().getPackageName());
    }

    public void zzc(@Signature({"(", "Landroid/app/PendingIntent;", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/common/api/Status;", ">;)V"}) PendingIntent $r1, @Signature({"(", "Landroid/app/PendingIntent;", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/common/api/Status;", ">;)V"}) com.google.android.gms.internal.zzqk.zzb<Status> $r2) throws RemoteException {
        zzavw();
        zzab.zzb((Object) $r1, (Object) "PendingIntent must be specified.");
        zzab.zzb((Object) $r2, (Object) "ResultHolder not provided.");
        ((zzi) zzavx()).zzc($r1, new zzrv($r2));
    }

    public void zzc(Location $r1) throws RemoteException {
        this.awb.zzc($r1);
    }

    public void zzce(boolean $z0) throws RemoteException {
        this.awb.zzce($z0);
    }

    public void zzd(@Signature({"(", "Landroid/app/PendingIntent;", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/common/api/Status;", ">;)V"}) PendingIntent $r1, @Signature({"(", "Landroid/app/PendingIntent;", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/common/api/Status;", ">;)V"}) com.google.android.gms.internal.zzqk.zzb<Status> $r2) throws RemoteException {
        zzavw();
        zzab.zzb((Object) $r1, (Object) "PendingIntent must be specified.");
        zzab.zzb((Object) $r2, (Object) "ResultHolder not provided.");
        ((zzi) zzavx()).zzd($r1, new zzrv($r2));
    }

    public void zze(@Signature({"(", "Landroid/app/PendingIntent;", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/common/api/Status;", ">;)V"}) PendingIntent $r1, @Signature({"(", "Landroid/app/PendingIntent;", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/common/api/Status;", ">;)V"}) com.google.android.gms.internal.zzqk.zzb<Status> $r2) throws RemoteException {
        zzavw();
        zzab.zzb((Object) $r1, (Object) "PendingIntent must be specified.");
        zzab.zzb((Object) $r2, (Object) "ResultHolder not provided.");
        ((zzi) zzavx()).zze($r1, new zzrv($r2));
    }

    public void zzf(@Signature({"(", "Landroid/app/PendingIntent;", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/common/api/Status;", ">;)V"}) PendingIntent $r1, @Signature({"(", "Landroid/app/PendingIntent;", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/common/api/Status;", ">;)V"}) com.google.android.gms.internal.zzqk.zzb<Status> $r2) throws RemoteException {
        zzavw();
        zzab.zzb((Object) $r1, (Object) "PendingIntent must be specified.");
        zzab.zzb((Object) $r2, (Object) "ResultHolder not provided.");
        ((zzi) zzavx()).zza($r1, new zzb($r2), getContext().getPackageName());
    }
}
