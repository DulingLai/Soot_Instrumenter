package com.google.android.gms.common.api;

import com.google.android.gms.common.internal.zzab;
import dalvik.annotation.Signature;
import java.util.concurrent.TimeUnit;

/* compiled from: dalvik_source_com.waze.apk */
public final class BatchResult implements Result {
    private final PendingResult<?>[] Ck;
    private final Status cp;

    BatchResult(@Signature({"(", "Lcom/google/android/gms/common/api/Status;", "[", "Lcom/google/android/gms/common/api/PendingResult", "<*>;)V"}) Status $r1, @Signature({"(", "Lcom/google/android/gms/common/api/Status;", "[", "Lcom/google/android/gms/common/api/PendingResult", "<*>;)V"}) PendingResult<?>[] $r2) throws  {
        this.cp = $r1;
        this.Ck = $r2;
    }

    public Status getStatus() throws  {
        return this.cp;
    }

    public <R extends Result> R take(@Signature({"<R::", "Lcom/google/android/gms/common/api/Result;", ">(", "Lcom/google/android/gms/common/api/BatchResultToken", "<TR;>;)TR;"}) BatchResultToken<R> $r1) throws  {
        zzab.zzb($r1.mId < this.Ck.length, (Object) "The result token does not belong to this batch");
        return this.Ck[$r1.mId].await(0, TimeUnit.MILLISECONDS);
    }
}
