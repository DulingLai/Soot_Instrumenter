package com.google.android.gms.common.api;

import android.support.annotation.NonNull;
import dalvik.annotation.Signature;

/* compiled from: dalvik_source_com.waze.apk */
public abstract class TransformedResult<R extends Result> {
    public abstract void andFinally(@NonNull @Signature({"(", "Lcom/google/android/gms/common/api/ResultCallbacks", "<-TR;>;)V"}) ResultCallbacks<? super R> resultCallbacks) throws ;

    @NonNull
    public abstract <S extends Result> TransformedResult<S> then(@NonNull @Signature({"<S::", "Lcom/google/android/gms/common/api/Result;", ">(", "Lcom/google/android/gms/common/api/ResultTransform", "<-TR;+TS;>;)", "Lcom/google/android/gms/common/api/TransformedResult", "<TS;>;"}) ResultTransform<? super R, ? extends S> resultTransform) throws ;
}
