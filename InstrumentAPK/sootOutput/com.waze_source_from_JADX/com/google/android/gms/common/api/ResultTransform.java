package com.google.android.gms.common.api;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import com.google.android.gms.internal.zzrt;
import dalvik.annotation.Signature;

/* compiled from: dalvik_source_com.waze.apk */
public abstract class ResultTransform<R extends Result, S extends Result> {
    @NonNull
    public final PendingResult<S> createFailedResult(@NonNull @Signature({"(", "Lcom/google/android/gms/common/api/Status;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<TS;>;"}) Status $r1) throws  {
        return new zzrt($r1);
    }

    @NonNull
    public Status onFailure(@NonNull Status $r1) throws  {
        return $r1;
    }

    @Nullable
    @WorkerThread
    public abstract PendingResult<S> onSuccess(@NonNull @Signature({"(TR;)", "Lcom/google/android/gms/common/api/PendingResult", "<TS;>;"}) R r) throws ;
}
