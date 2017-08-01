package com.google.android.gms.common.api;

import com.google.android.gms.common.api.PendingResult.zza;
import com.google.android.gms.internal.zzqm;
import dalvik.annotation.Signature;
import java.util.ArrayList;
import java.util.List;

/* compiled from: dalvik_source_com.waze.apk */
public final class Batch extends zzqm<BatchResult> {
    private int Ch;
    private boolean Ci;
    private boolean Cj;
    private final PendingResult<?>[] Ck;
    private final Object zzaix;

    /* compiled from: dalvik_source_com.waze.apk */
    class C06941 implements zza {
        final /* synthetic */ Batch Cl;

        C06941(Batch $r1) throws  {
            this.Cl = $r1;
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void zzac(com.google.android.gms.common.api.Status r10) throws  {
            /*
            r9 = this;
            r0 = r9.Cl;
            r1 = r0.zzaix;
            monitor-enter(r1);
            r0 = r9.Cl;	 Catch:{ Throwable -> 0x0039 }
            r2 = r0.isCanceled();	 Catch:{ Throwable -> 0x0039 }
            if (r2 == 0) goto L_0x0011;
        L_0x000f:
            monitor-exit(r1);	 Catch:{ Throwable -> 0x0039 }
            return;
        L_0x0011:
            r2 = r10.isCanceled();	 Catch:{ Throwable -> 0x0039 }
            if (r2 == 0) goto L_0x003c;
        L_0x0017:
            r0 = r9.Cl;	 Catch:{ Throwable -> 0x0039 }
            r3 = 1;
            r0.Cj = r3;	 Catch:{ Throwable -> 0x0039 }
        L_0x001d:
            r0 = r9.Cl;	 Catch:{ Throwable -> 0x0039 }
            r0.Ch = r0.Ch - 1;	 Catch:{ Throwable -> 0x0039 }
            r0 = r9.Cl;	 Catch:{ Throwable -> 0x0039 }
            r4 = r0.Ch;	 Catch:{ Throwable -> 0x0039 }
            if (r4 != 0) goto L_0x0037;
        L_0x002a:
            r0 = r9.Cl;	 Catch:{ Throwable -> 0x0039 }
            r2 = r0.Cj;	 Catch:{ Throwable -> 0x0039 }
            if (r2 == 0) goto L_0x0049;
        L_0x0032:
            r0 = r9.Cl;	 Catch:{ Throwable -> 0x0039 }
            super.cancel();	 Catch:{ Throwable -> 0x0039 }
        L_0x0037:
            monitor-exit(r1);	 Catch:{ Throwable -> 0x0039 }
            return;
        L_0x0039:
            r5 = move-exception;
            monitor-exit(r1);	 Catch:{ Throwable -> 0x0039 }
            throw r5;
        L_0x003c:
            r2 = r10.isSuccess();	 Catch:{ Throwable -> 0x0039 }
            if (r2 != 0) goto L_0x001d;
        L_0x0042:
            r0 = r9.Cl;	 Catch:{ Throwable -> 0x0039 }
            r3 = 1;
            r0.Ci = r3;	 Catch:{ Throwable -> 0x0039 }
            goto L_0x001d;
        L_0x0049:
            r0 = r9.Cl;	 Catch:{ Throwable -> 0x0039 }
            r2 = r0.Ci;	 Catch:{ Throwable -> 0x0039 }
            if (r2 == 0) goto L_0x0069;
        L_0x0051:
            r10 = new com.google.android.gms.common.api.Status;	 Catch:{ Throwable -> 0x0039 }
            r3 = 13;
            r10.<init>(r3);	 Catch:{ Throwable -> 0x0039 }
        L_0x0058:
            r0 = r9.Cl;	 Catch:{ Throwable -> 0x0039 }
            r6 = new com.google.android.gms.common.api.BatchResult;	 Catch:{ Throwable -> 0x0039 }
            r7 = r9.Cl;	 Catch:{ Throwable -> 0x0039 }
            r8 = r7.Ck;	 Catch:{ Throwable -> 0x0039 }
            r6.<init>(r10, r8);	 Catch:{ Throwable -> 0x0039 }
            r0.zzc(r6);	 Catch:{ Throwable -> 0x0039 }
            goto L_0x0037;
        L_0x0069:
            r10 = com.google.android.gms.common.api.Status.CQ;	 Catch:{ Throwable -> 0x0039 }
            goto L_0x0058;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.api.Batch.1.zzac(com.google.android.gms.common.api.Status):void");
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class Builder {
        private List<PendingResult<?>> Cm = new ArrayList();
        private GoogleApiClient mApiClient;

        public Builder(GoogleApiClient $r1) throws  {
            this.mApiClient = $r1;
        }

        public <R extends Result> BatchResultToken<R> add(@Signature({"<R::", "Lcom/google/android/gms/common/api/Result;", ">(", "Lcom/google/android/gms/common/api/PendingResult", "<TR;>;)", "Lcom/google/android/gms/common/api/BatchResultToken", "<TR;>;"}) PendingResult<R> $r1) throws  {
            BatchResultToken $r2 = new BatchResultToken(this.Cm.size());
            this.Cm.add($r1);
            return $r2;
        }

        public Batch build() throws  {
            return new Batch(this.Cm, this.mApiClient);
        }
    }

    private Batch(@Signature({"(", "Ljava/util/List", "<", "Lcom/google/android/gms/common/api/PendingResult", "<*>;>;", "Lcom/google/android/gms/common/api/GoogleApiClient;", ")V"}) List<PendingResult<?>> $r1, @Signature({"(", "Ljava/util/List", "<", "Lcom/google/android/gms/common/api/PendingResult", "<*>;>;", "Lcom/google/android/gms/common/api/GoogleApiClient;", ")V"}) GoogleApiClient $r2) throws  {
        super($r2);
        this.zzaix = new Object();
        this.Ch = $r1.size();
        this.Ck = new PendingResult[this.Ch];
        if ($r1.isEmpty()) {
            zzc(new BatchResult(Status.CQ, this.Ck));
            return;
        }
        for (int $i0 = 0; $i0 < $r1.size(); $i0++) {
            PendingResult $r7 = (PendingResult) $r1.get($i0);
            this.Ck[$i0] = $r7;
            $r7.zza(new C06941(this));
        }
    }

    public void cancel() throws  {
        super.cancel();
        for (PendingResult $r2 : this.Ck) {
            $r2.cancel();
        }
    }

    public BatchResult createFailedResult(Status $r1) throws  {
        return new BatchResult($r1, this.Ck);
    }

    public /* synthetic */ Result zzb(Status $r1) throws  {
        return createFailedResult($r1);
    }
}
