package com.google.android.gms.location.internal;

import android.app.PendingIntent;
import android.location.Location;
import android.os.Looper;
import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import dalvik.annotation.Signature;

/* compiled from: dalvik_source_com.waze.apk */
public class zzd implements FusedLocationProviderApi {

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

    /* compiled from: dalvik_source_com.waze.apk */
    private static class zzb extends com.google.android.gms.location.internal.zzg.zza {
        private final com.google.android.gms.internal.zzqk.zzb<Status> Ga;

        public zzb(@Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/common/api/Status;", ">;)V"}) com.google.android.gms.internal.zzqk.zzb<Status> $r1) throws  {
            this.Ga = $r1;
        }

        public void zza(FusedLocationProviderResult $r1) throws  {
            this.Ga.setResult($r1.getStatus());
        }
    }

    public PendingResult<Status> flushLocations(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) GoogleApiClient $r1) throws  {
        return $r1.zzd(new zza(this, $r1) {
            final /* synthetic */ zzd avK;

            protected void zza(zzl $r1) throws RemoteException {
                $r1.zza(new zzb(this));
            }
        });
    }

    public Location getLastLocation(GoogleApiClient $r1) throws  {
        try {
            return LocationServices.zzr($r1).getLastLocation();
        } catch (Exception e) {
            return null;
        }
    }

    public LocationAvailability getLocationAvailability(GoogleApiClient $r1) throws  {
        try {
            return LocationServices.zzr($r1).zzbsi();
        } catch (Exception e) {
            return null;
        }
    }

    public PendingResult<Status> injectLocation(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Landroid/location/Location;", "I)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) GoogleApiClient $r1, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Landroid/location/Location;", "I)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) final Location $r2, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Landroid/location/Location;", "I)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) final int $i0) throws  {
        return $r1.zzd(new zza(this, $r1) {
            final /* synthetic */ zzd avK;

            protected void zza(zzl $r1) throws RemoteException {
                $r1.zza($r2, $i0);
                zzc(Status.CQ);
            }
        });
    }

    public PendingResult<Status> removeLocationUpdates(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Landroid/app/PendingIntent;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) GoogleApiClient $r1, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Landroid/app/PendingIntent;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) final PendingIntent $r2) throws  {
        return $r1.zzd(new zza(this, $r1) {
            final /* synthetic */ zzd avK;

            protected void zza(zzl $r1) throws RemoteException {
                $r1.zza($r2, new zzb(this));
            }
        });
    }

    public PendingResult<Status> removeLocationUpdates(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/location/LocationCallback;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) GoogleApiClient $r1, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/location/LocationCallback;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) final LocationCallback $r2) throws  {
        return $r1.zzd(new zza(this, $r1) {
            final /* synthetic */ zzd avK;

            protected void zza(zzl $r1) throws RemoteException {
                $r1.zza($r2, new zzb(this));
            }
        });
    }

    public PendingResult<Status> removeLocationUpdates(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/location/LocationListener;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) GoogleApiClient $r1, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/location/LocationListener;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) final LocationListener $r2) throws  {
        return $r1.zzd(new zza(this, $r1) {
            final /* synthetic */ zzd avK;

            protected void zza(zzl $r1) throws RemoteException {
                $r1.zza($r2, new zzb(this));
            }
        });
    }

    public PendingResult<Status> requestLocationUpdates(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/location/LocationRequest;", "Landroid/app/PendingIntent;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) GoogleApiClient $r1, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/location/LocationRequest;", "Landroid/app/PendingIntent;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) final LocationRequest $r2, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/location/LocationRequest;", "Landroid/app/PendingIntent;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) final PendingIntent $r3) throws  {
        return $r1.zzd(new zza(this, $r1) {
            final /* synthetic */ zzd avK;

            protected void zza(zzl $r1) throws RemoteException {
                $r1.zza($r2, $r3, new zzb(this));
            }
        });
    }

    public PendingResult<Status> requestLocationUpdates(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/location/LocationRequest;", "Lcom/google/android/gms/location/LocationCallback;", "Landroid/os/Looper;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) GoogleApiClient $r1, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/location/LocationRequest;", "Lcom/google/android/gms/location/LocationCallback;", "Landroid/os/Looper;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) LocationRequest $r2, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/location/LocationRequest;", "Lcom/google/android/gms/location/LocationCallback;", "Landroid/os/Looper;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) LocationCallback $r3, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/location/LocationRequest;", "Lcom/google/android/gms/location/LocationCallback;", "Landroid/os/Looper;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) Looper $r4) throws  {
        final LocationRequest locationRequest = $r2;
        final LocationCallback locationCallback = $r3;
        final Looper looper = $r4;
        return $r1.zzd(new zza(this, $r1) {
            final /* synthetic */ zzd avK;

            protected void zza(zzl $r1) throws RemoteException {
                $r1.zza(LocationRequestInternal.zzb(locationRequest), locationCallback, looper, new zzb(this));
            }
        });
    }

    public PendingResult<Status> requestLocationUpdates(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/location/LocationRequest;", "Lcom/google/android/gms/location/LocationListener;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) GoogleApiClient $r1, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/location/LocationRequest;", "Lcom/google/android/gms/location/LocationListener;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) final LocationRequest $r2, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/location/LocationRequest;", "Lcom/google/android/gms/location/LocationListener;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) final LocationListener $r3) throws  {
        return $r1.zzd(new zza(this, $r1) {
            final /* synthetic */ zzd avK;

            protected void zza(zzl $r1) throws RemoteException {
                $r1.zza($r2, $r3, null, new zzb(this));
            }
        });
    }

    public PendingResult<Status> requestLocationUpdates(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/location/LocationRequest;", "Lcom/google/android/gms/location/LocationListener;", "Landroid/os/Looper;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) GoogleApiClient $r1, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/location/LocationRequest;", "Lcom/google/android/gms/location/LocationListener;", "Landroid/os/Looper;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) LocationRequest $r2, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/location/LocationRequest;", "Lcom/google/android/gms/location/LocationListener;", "Landroid/os/Looper;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) LocationListener $r3, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/location/LocationRequest;", "Lcom/google/android/gms/location/LocationListener;", "Landroid/os/Looper;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) Looper $r4) throws  {
        final LocationRequest locationRequest = $r2;
        final LocationListener locationListener = $r3;
        final Looper looper = $r4;
        return $r1.zzd(new zza(this, $r1) {
            final /* synthetic */ zzd avK;

            protected void zza(zzl $r1) throws RemoteException {
                $r1.zza(locationRequest, locationListener, looper, new zzb(this));
            }
        });
    }

    public PendingResult<Status> setMockLocation(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Landroid/location/Location;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) GoogleApiClient $r1, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Landroid/location/Location;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) final Location $r2) throws  {
        return $r1.zzd(new zza(this, $r1) {
            final /* synthetic */ zzd avK;

            protected void zza(zzl $r1) throws RemoteException {
                $r1.zzc($r2);
                zzc(Status.CQ);
            }
        });
    }

    public PendingResult<Status> setMockMode(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Z)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) GoogleApiClient $r1, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Z)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) final boolean $z0) throws  {
        return $r1.zzd(new zza(this, $r1) {
            final /* synthetic */ zzd avK;

            protected void zza(zzl $r1) throws RemoteException {
                $r1.zzce($z0);
                zzc(Status.CQ);
            }
        });
    }
}
