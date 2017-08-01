package com.google.android.gms.internal;

import android.os.DeadObjectException;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.util.SparseArray;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.FirebaseApiNotAvailableException;
import com.google.firebase.FirebaseException;
import dalvik.annotation.Signature;

/* compiled from: dalvik_source_com.waze.apk */
public abstract class zzqg {
    public final int CY;
    public final int bG;

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class zza extends zzqg {
        public final com.google.android.gms.internal.zzqk.zza<? extends Result, com.google.android.gms.common.api.Api.zzb> CZ;

        public zza(@Signature({"(II", "Lcom/google/android/gms/internal/zzqk$zza", "<+", "Lcom/google/android/gms/common/api/Result;", "Lcom/google/android/gms/common/api/Api$zzb;", ">;)V"}) int $i0, @Signature({"(II", "Lcom/google/android/gms/internal/zzqk$zza", "<+", "Lcom/google/android/gms/common/api/Result;", "Lcom/google/android/gms/common/api/Api$zzb;", ">;)V"}) int $i1, @Signature({"(II", "Lcom/google/android/gms/internal/zzqk$zza", "<+", "Lcom/google/android/gms/common/api/Result;", "Lcom/google/android/gms/common/api/Api$zzb;", ">;)V"}) com.google.android.gms.internal.zzqk.zza<? extends Result, com.google.android.gms.common.api.Api.zzb> $r1) throws  {
            super($i0, $i1);
            this.CZ = $r1;
        }

        public boolean cancel() throws  {
            return this.CZ.zzasm();
        }

        public void zza(@Signature({"(", "Landroid/util/SparseArray", "<", "Lcom/google/android/gms/internal/zzsb;", ">;)V"}) SparseArray<zzsb> $r1) throws  {
            zzsb $r4 = (zzsb) $r1.get(this.CY);
            if ($r4 != null) {
                $r4.zzg(this.CZ);
            }
        }

        public void zzae(@NonNull Status $r1) throws  {
            this.CZ.zzag($r1);
        }

        public void zzb(com.google.android.gms.common.api.Api.zzb $r1) throws DeadObjectException {
            this.CZ.zzb($r1);
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class zzb<TResult> extends zzqg {
        private static final Status Dc = new Status(8, "Connection to Google Play services was lost while executing the API call.");
        private final zzrz<com.google.android.gms.common.api.Api.zzb, TResult> Da;
        private final TaskCompletionSource<TResult> Db;

        public zzb(@Signature({"(II", "Lcom/google/android/gms/internal/zzrz", "<", "Lcom/google/android/gms/common/api/Api$zzb;", "TTResult;>;", "Lcom/google/android/gms/tasks/TaskCompletionSource", "<TTResult;>;)V"}) int $i0, @Signature({"(II", "Lcom/google/android/gms/internal/zzrz", "<", "Lcom/google/android/gms/common/api/Api$zzb;", "TTResult;>;", "Lcom/google/android/gms/tasks/TaskCompletionSource", "<TTResult;>;)V"}) int $i1, @Signature({"(II", "Lcom/google/android/gms/internal/zzrz", "<", "Lcom/google/android/gms/common/api/Api$zzb;", "TTResult;>;", "Lcom/google/android/gms/tasks/TaskCompletionSource", "<TTResult;>;)V"}) zzrz<com.google.android.gms.common.api.Api.zzb, TResult> $r1, @Signature({"(II", "Lcom/google/android/gms/internal/zzrz", "<", "Lcom/google/android/gms/common/api/Api$zzb;", "TTResult;>;", "Lcom/google/android/gms/tasks/TaskCompletionSource", "<TTResult;>;)V"}) TaskCompletionSource<TResult> $r2) throws  {
            super($i0, $i1);
            this.Db = $r2;
            this.Da = $r1;
        }

        public void zzae(@NonNull Status $r1) throws  {
            if ($r1.getStatusCode() == 8) {
                this.Db.setException((Exception) new FirebaseException($r1.getStatusMessage()));
            } else {
                this.Db.setException((Exception) new FirebaseApiNotAvailableException($r1.getStatusMessage()));
            }
        }

        public void zzb(com.google.android.gms.common.api.Api.zzb $r1) throws DeadObjectException {
            try {
                this.Da.zza($r1, this.Db);
            } catch (DeadObjectException $r4) {
                zzae(Dc);
                throw $r4;
            } catch (RemoteException e) {
                zzae(Dc);
            }
        }
    }

    public zzqg(int $i0, int $i1) throws  {
        this.CY = $i0;
        this.bG = $i1;
    }

    public boolean cancel() throws  {
        return true;
    }

    public void zza(@Signature({"(", "Landroid/util/SparseArray", "<", "Lcom/google/android/gms/internal/zzsb;", ">;)V"}) SparseArray<zzsb> sparseArray) throws  {
    }

    public abstract void zzae(@NonNull Status status) throws ;

    public abstract void zzb(com.google.android.gms.common.api.Api.zzb com_google_android_gms_common_api_Api_zzb) throws DeadObjectException;
}
