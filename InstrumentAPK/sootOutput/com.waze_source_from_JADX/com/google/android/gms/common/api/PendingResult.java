package com.google.android.gms.common.api;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import dalvik.annotation.Signature;
import java.util.concurrent.TimeUnit;

/* compiled from: dalvik_source_com.waze.apk */
public abstract class PendingResult<R extends Result> {

    /* compiled from: dalvik_source_com.waze.apk */
    public interface zza {
        void zzac(Status status) throws ;
    }

    @NonNull
    public abstract R await() throws ;

    @NonNull
    public abstract R await(@Signature({"(J", "Ljava/util/concurrent/TimeUnit;", ")TR;"}) long j, @NonNull @Signature({"(J", "Ljava/util/concurrent/TimeUnit;", ")TR;"}) TimeUnit timeUnit) throws ;

    public abstract void cancel() throws ;

    public abstract boolean isCanceled() throws ;

    public abstract void setResultCallback(@NonNull @Signature({"(", "Lcom/google/android/gms/common/api/ResultCallback", "<-TR;>;)V"}) ResultCallback<? super R> resultCallback) throws ;

    public abstract void setResultCallback(@NonNull @Signature({"(", "Lcom/google/android/gms/common/api/ResultCallback", "<-TR;>;J", "Ljava/util/concurrent/TimeUnit;", ")V"}) ResultCallback<? super R> resultCallback, @Signature({"(", "Lcom/google/android/gms/common/api/ResultCallback", "<-TR;>;J", "Ljava/util/concurrent/TimeUnit;", ")V"}) long j, @NonNull @Signature({"(", "Lcom/google/android/gms/common/api/ResultCallback", "<-TR;>;J", "Ljava/util/concurrent/TimeUnit;", ")V"}) TimeUnit timeUnit) throws ;

    public void store(@NonNull ResultStore resultStore, int i) throws  {
        throw new UnsupportedOperationException();
    }

    @NonNull
    public <S extends Result> TransformedResult<S> then(@NonNull @Signature({"<S::", "Lcom/google/android/gms/common/api/Result;", ">(", "Lcom/google/android/gms/common/api/ResultTransform", "<-TR;+TS;>;)", "Lcom/google/android/gms/common/api/TransformedResult", "<TS;>;"}) ResultTransform<? super R, ? extends S> resultTransform) throws  {
        throw new UnsupportedOperationException();
    }

    public void zza(@NonNull zza com_google_android_gms_common_api_PendingResult_zza) throws  {
        throw new UnsupportedOperationException();
    }

    @Nullable
    public Integer zzasb() throws  {
        throw new UnsupportedOperationException();
    }

    public void zzhp(int i) throws  {
        throw new UnsupportedOperationException();
    }
}
