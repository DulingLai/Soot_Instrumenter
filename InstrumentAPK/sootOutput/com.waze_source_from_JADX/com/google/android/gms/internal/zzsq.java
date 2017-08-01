package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzqk.zzb;
import dalvik.annotation.Signature;

/* compiled from: dalvik_source_com.waze.apk */
public final class zzsq implements zzsp {

    /* compiled from: dalvik_source_com.waze.apk */
    private static class zza extends zzsn {
        private final zzb<Status> Ga;

        public zza(@Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/common/api/Status;", ">;)V"}) zzb<Status> $r1) throws  {
            this.Ga = $r1;
        }

        public void zziw(int $i0) throws RemoteException {
            this.Ga.setResult(new Status($i0));
        }
    }

    public PendingResult<Status> zzj(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) GoogleApiClient $r1) throws  {
        return $r1.zzd(new zza(this, $r1) {
            final /* synthetic */ zzsq Kt;

            protected void zza(zzss $r1) throws RemoteException {
                ((zzsu) $r1.zzavx()).zza(new zza(this));
            }
        });
    }
}
