package com.google.android.gms.common.api;

import android.os.Looper;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.internal.zzqm;
import com.google.android.gms.internal.zzrp;
import com.google.android.gms.internal.zzrw;
import dalvik.annotation.Signature;

/* compiled from: dalvik_source_com.waze.apk */
public final class PendingResults {

    /* compiled from: dalvik_source_com.waze.apk */
    private static final class zza<R extends Result> extends zzqm<R> {
        private final R CL;

        public zza(@Signature({"(TR;)V"}) R $r1) throws  {
            super(Looper.getMainLooper());
            this.CL = $r1;
        }

        protected R zzb(@Signature({"(", "Lcom/google/android/gms/common/api/Status;", ")TR;"}) Status $r1) throws  {
            if ($r1.getStatusCode() == this.CL.getStatus().getStatusCode()) {
                return this.CL;
            }
            throw new UnsupportedOperationException("Creating failed results is not supported");
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    private static final class zzb<R extends Result> extends zzqm<R> {
        private final R CM;

        public zzb(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "TR;)V"}) GoogleApiClient $r1, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "TR;)V"}) R $r2) throws  {
            super($r1);
            this.CM = $r2;
        }

        protected R zzb(@Signature({"(", "Lcom/google/android/gms/common/api/Status;", ")TR;"}) Status status) throws  {
            return this.CM;
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    private static final class zzc<R extends Result> extends zzqm<R> {
        public zzc(GoogleApiClient $r1) throws  {
            super($r1);
        }

        protected R zzb(@Signature({"(", "Lcom/google/android/gms/common/api/Status;", ")TR;"}) Status status) throws  {
            throw new UnsupportedOperationException("Creating failed results is not supported");
        }
    }

    private PendingResults() throws  {
    }

    public static PendingResult<Status> canceledPendingResult() throws  {
        zzrw $r0 = new zzrw(Looper.getMainLooper());
        $r0.cancel();
        return $r0;
    }

    public static <R extends Result> PendingResult<R> canceledPendingResult(@Signature({"<R::", "Lcom/google/android/gms/common/api/Result;", ">(TR;)", "Lcom/google/android/gms/common/api/PendingResult", "<TR;>;"}) R $r0) throws  {
        zzab.zzb((Object) $r0, (Object) "Result must not be null");
        zzab.zzb($r0.getStatus().getStatusCode() == 16, (Object) "Status code must be CommonStatusCodes.CANCELED");
        zza $r2 = new zza($r0);
        $r2.cancel();
        return $r2;
    }

    public static <R extends Result> OptionalPendingResult<R> immediatePendingResult(@Signature({"<R::", "Lcom/google/android/gms/common/api/Result;", ">(TR;)", "Lcom/google/android/gms/common/api/OptionalPendingResult", "<TR;>;"}) R $r0) throws  {
        zzab.zzb((Object) $r0, (Object) "Result must not be null");
        zzc $r1 = new zzc(null);
        $r1.zzc($r0);
        return new zzrp($r1);
    }

    public static PendingResult<Status> immediatePendingResult(@Signature({"(", "Lcom/google/android/gms/common/api/Status;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) Status $r0) throws  {
        zzab.zzb((Object) $r0, (Object) "Result must not be null");
        zzrw $r2 = new zzrw(Looper.getMainLooper());
        $r2.zzc($r0);
        return $r2;
    }

    public static <R extends Result> PendingResult<R> zza(@Signature({"<R::", "Lcom/google/android/gms/common/api/Result;", ">(TR;", "Lcom/google/android/gms/common/api/GoogleApiClient;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<TR;>;"}) R $r0, @Signature({"<R::", "Lcom/google/android/gms/common/api/Result;", ">(TR;", "Lcom/google/android/gms/common/api/GoogleApiClient;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<TR;>;"}) GoogleApiClient $r1) throws  {
        zzab.zzb((Object) $r0, (Object) "Result must not be null");
        zzab.zzb(!$r0.getStatus().isSuccess(), (Object) "Status code must not be SUCCESS");
        zzb $r3 = new zzb($r1, $r0);
        $r3.zzc($r0);
        return $r3;
    }

    public static PendingResult<Status> zza(@Signature({"(", "Lcom/google/android/gms/common/api/Status;", "Lcom/google/android/gms/common/api/GoogleApiClient;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) Status $r0, @Signature({"(", "Lcom/google/android/gms/common/api/Status;", "Lcom/google/android/gms/common/api/GoogleApiClient;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) GoogleApiClient $r1) throws  {
        zzab.zzb((Object) $r0, (Object) "Result must not be null");
        zzrw $r2 = new zzrw($r1);
        $r2.zzc($r0);
        return $r2;
    }

    public static <R extends Result> OptionalPendingResult<R> zzb(@Signature({"<R::", "Lcom/google/android/gms/common/api/Result;", ">(TR;", "Lcom/google/android/gms/common/api/GoogleApiClient;", ")", "Lcom/google/android/gms/common/api/OptionalPendingResult", "<TR;>;"}) R $r0, @Signature({"<R::", "Lcom/google/android/gms/common/api/Result;", ">(TR;", "Lcom/google/android/gms/common/api/GoogleApiClient;", ")", "Lcom/google/android/gms/common/api/OptionalPendingResult", "<TR;>;"}) GoogleApiClient $r1) throws  {
        zzab.zzb((Object) $r0, (Object) "Result must not be null");
        zzc $r3 = new zzc($r1);
        $r3.zzc($r0);
        return new zzrp($r3);
    }
}
