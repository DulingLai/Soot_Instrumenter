package com.google.android.gms.location.internal;

import android.app.PendingIntent;
import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzqk.zzb;
import com.google.android.gms.location.ActivityRecognition;
import com.google.android.gms.location.ActivityRecognitionApi;
import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.GestureRequest;
import dalvik.annotation.Signature;

/* compiled from: dalvik_source_com.waze.apk */
public class zza implements ActivityRecognitionApi {

    /* compiled from: dalvik_source_com.waze.apk */
    private static abstract class zza extends com.google.android.gms.location.ActivityRecognition.zza<Status> {
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

    public ActivityRecognitionResult getLastActivity(GoogleApiClient $r1) throws  {
        try {
            return ActivityRecognition.zzr($r1).zzbsk();
        } catch (Exception e) {
            return null;
        }
    }

    public PendingResult<Status> removeActivityUpdates(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Landroid/app/PendingIntent;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) GoogleApiClient $r1, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Landroid/app/PendingIntent;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) final PendingIntent $r2) throws  {
        return $r1.zzd(new zza(this, $r1) {
            final /* synthetic */ zza avD;

            protected void zza(zzl $r1) throws RemoteException {
                $r1.zzb($r2);
                zzc(Status.CQ);
            }
        });
    }

    public PendingResult<Status> removeFloorChangeUpdates(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Landroid/app/PendingIntent;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) GoogleApiClient $r1, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Landroid/app/PendingIntent;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) final PendingIntent $r2) throws  {
        return $r1.zzd(new zza(this, $r1) {
            final /* synthetic */ zza avD;

            protected void zza(zzl $r1) throws RemoteException {
                $r1.zzb($r2, this);
            }
        });
    }

    public PendingResult<Status> removeGestureUpdates(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Landroid/app/PendingIntent;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) GoogleApiClient $r1, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Landroid/app/PendingIntent;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) final PendingIntent $r2) throws  {
        return $r1.zzd(new zza(this, $r1) {
            final /* synthetic */ zza avD;

            protected void zza(zzl $r1) throws RemoteException {
                $r1.zzc($r2, this);
            }
        });
    }

    public PendingResult<Status> removeSleepSegmentUpdates(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Landroid/app/PendingIntent;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) GoogleApiClient $r1, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Landroid/app/PendingIntent;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) final PendingIntent $r2) throws  {
        return $r1.zzd(new zza(this, $r1) {
            final /* synthetic */ zza avD;

            protected void zza(zzl $r1) throws RemoteException {
                $r1.zze($r2, this);
            }
        });
    }

    public PendingResult<Status> requestActivityUpdates(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "J", "Landroid/app/PendingIntent;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) GoogleApiClient $r1, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "J", "Landroid/app/PendingIntent;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) long $l0, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "J", "Landroid/app/PendingIntent;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) PendingIntent $r2) throws  {
        final long j = $l0;
        final PendingIntent pendingIntent = $r2;
        return $r1.zzd(new zza(this, $r1) {
            final /* synthetic */ zza avD;

            protected void zza(zzl $r1) throws RemoteException {
                $r1.zza(j, pendingIntent);
                zzc(Status.CQ);
            }
        });
    }

    public PendingResult<Status> requestFloorChangeUpdates(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Landroid/app/PendingIntent;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) GoogleApiClient $r1, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Landroid/app/PendingIntent;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) final PendingIntent $r2) throws  {
        return $r1.zzd(new zza(this, $r1) {
            final /* synthetic */ zza avD;

            protected void zza(zzl $r1) throws RemoteException {
                $r1.zza($r2, (zzb) this);
            }
        });
    }

    public PendingResult<Status> requestGestureUpdates(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/location/GestureRequest;", "Landroid/app/PendingIntent;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) GoogleApiClient $r1, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/location/GestureRequest;", "Landroid/app/PendingIntent;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) final GestureRequest $r2, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/location/GestureRequest;", "Landroid/app/PendingIntent;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) final PendingIntent $r3) throws  {
        return $r1.zzd(new zza(this, $r1) {
            final /* synthetic */ zza avD;

            protected void zza(zzl $r1) throws RemoteException {
                $r1.zza($r2, $r3, (zzb) this);
            }
        });
    }

    public PendingResult<Status> requestSleepSegmentUpdates(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Landroid/app/PendingIntent;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) GoogleApiClient $r1, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Landroid/app/PendingIntent;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) final PendingIntent $r2) throws  {
        return $r1.zzd(new zza(this, $r1) {
            final /* synthetic */ zza avD;

            protected void zza(zzl $r1) throws RemoteException {
                $r1.zzd($r2, this);
            }
        });
    }
}
