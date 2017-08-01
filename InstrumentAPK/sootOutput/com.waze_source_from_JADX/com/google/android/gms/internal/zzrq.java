package com.google.android.gms.internal;

import android.support.annotation.NonNull;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.PendingResult.zza;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.ResultStore;
import com.google.android.gms.common.api.ResultTransform;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.TransformedResult;
import com.google.android.gms.common.internal.zzab;
import dalvik.annotation.Signature;
import java.util.concurrent.TimeUnit;

/* compiled from: dalvik_source_com.waze.apk */
public abstract class zzrq<A extends Result, B extends Result> extends PendingResult<B> {
    private final PendingResult<A> FV;

    public zzrq(@Signature({"(", "Lcom/google/android/gms/common/api/PendingResult", "<TA;>;)V"}) PendingResult<A> $r1) throws  {
        zzab.zzag($r1);
        this.FV = $r1;
    }

    private <T extends Result> ResultTransform<? super A, T> zza(@Signature({"<T::", "Lcom/google/android/gms/common/api/Result;", ">(", "Lcom/google/android/gms/common/api/ResultTransform", "<-TB;TT;>;)", "Lcom/google/android/gms/common/api/ResultTransform", "<-TA;TT;>;"}) final ResultTransform<? super B, T> $r1) throws  {
        return new ResultTransform<A, T>(this) {
            final /* synthetic */ zzrq FW;

            public Status onFailure(Status $r1) throws  {
                return $r1.onFailure($r1);
            }

            public PendingResult<T> onSuccess(@Signature({"(TA;)", "Lcom/google/android/gms/common/api/PendingResult", "<TT;>;"}) A $r1) throws  {
                return $r1.onSuccess(this.FW.zzf($r1));
            }
        };
    }

    public B await() throws  {
        return zzf(this.FV.await());
    }

    public B await(@Signature({"(J", "Ljava/util/concurrent/TimeUnit;", ")TB;"}) long $l0, @Signature({"(J", "Ljava/util/concurrent/TimeUnit;", ")TB;"}) TimeUnit $r1) throws  {
        return zzf(this.FV.await($l0, $r1));
    }

    public void cancel() throws  {
        this.FV.cancel();
    }

    public boolean isCanceled() throws  {
        return this.FV.isCanceled();
    }

    public void setResultCallback(@Signature({"(", "Lcom/google/android/gms/common/api/ResultCallback", "<-TB;>;)V"}) final ResultCallback<? super B> $r1) throws  {
        this.FV.setResultCallback(new ResultCallback<A>(this) {
            final /* synthetic */ zzrq FW;

            public void onResult(@NonNull @Signature({"(TA;)V"}) A $r1) throws  {
                $r1.onResult(this.FW.zzf($r1));
            }
        });
    }

    public void setResultCallback(@Signature({"(", "Lcom/google/android/gms/common/api/ResultCallback", "<-TB;>;J", "Ljava/util/concurrent/TimeUnit;", ")V"}) final ResultCallback<? super B> $r1, @Signature({"(", "Lcom/google/android/gms/common/api/ResultCallback", "<-TB;>;J", "Ljava/util/concurrent/TimeUnit;", ")V"}) long $l0, @Signature({"(", "Lcom/google/android/gms/common/api/ResultCallback", "<-TB;>;J", "Ljava/util/concurrent/TimeUnit;", ")V"}) TimeUnit $r2) throws  {
        this.FV.setResultCallback(new ResultCallback<A>(this) {
            final /* synthetic */ zzrq FW;

            public void onResult(@NonNull @Signature({"(TA;)V"}) A $r1) throws  {
                $r1.onResult(this.FW.zzf($r1));
            }
        }, $l0, $r2);
    }

    public void store(ResultStore $r1, int $i0) throws  {
        this.FV.store($r1, $i0);
    }

    public <S extends Result> TransformedResult<S> then(@Signature({"<S::", "Lcom/google/android/gms/common/api/Result;", ">(", "Lcom/google/android/gms/common/api/ResultTransform", "<-TB;+TS;>;)", "Lcom/google/android/gms/common/api/TransformedResult", "<TS;>;"}) ResultTransform<? super B, ? extends S> $r1) throws  {
        return this.FV.then(zza((ResultTransform) $r1));
    }

    public void zza(zza $r1) throws  {
        this.FV.zza($r1);
    }

    public Integer zzasb() throws  {
        return this.FV.zzasb();
    }

    protected abstract B zzf(@Signature({"(TA;)TB;"}) A a) throws ;

    public void zzhp(int $i0) throws  {
        this.FV.zzhp($i0);
    }
}
