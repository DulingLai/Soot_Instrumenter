package com.google.android.gms.internal;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.util.Pair;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.ResultStore;
import com.google.android.gms.common.api.ResultTransform;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.TransformedResult;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.common.internal.zzr;
import dalvik.annotation.Signature;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/* compiled from: dalvik_source_com.waze.apk */
public abstract class zzqm<R extends Result> extends PendingResult<R> {
    static final ThreadLocal<Boolean> Ds = new C08331();
    private R CM;
    private boolean DA;
    private zzr DB;
    private Integer DC;
    private volatile zzsa<R> DD;
    private boolean DE;
    private final Object Dt;
    protected final zza<R> Du;
    protected final WeakReference<GoogleApiClient> Dv;
    private final ArrayList<com.google.android.gms.common.api.PendingResult.zza> Dw;
    private ResultCallback<? super R> Dx;
    private zzb Dy;
    private volatile boolean Dz;
    private boolean zzaj;
    private final CountDownLatch zzalw;

    /* compiled from: dalvik_source_com.waze.apk */
    class C08331 extends ThreadLocal<Boolean> {
        C08331() throws  {
        }

        protected /* synthetic */ Object initialValue() throws  {
            return zzasp();
        }

        protected Boolean zzasp() throws  {
            return Boolean.valueOf(false);
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static class zza<R extends Result> extends Handler {
        public zza() throws  {
            this(Looper.getMainLooper());
        }

        public zza(Looper $r1) throws  {
            super($r1);
        }

        public void handleMessage(Message $r1) throws  {
            int $i0 = $r1;
            $r1 = $i0;
            switch ($i0.what) {
                case 1:
                    Pair $r6 = (Pair) $r1.obj;
                    zzb((ResultCallback) $r6.first, (Result) $r6.second);
                    return;
                case 2:
                    ((zzqm) $r1.obj).zzah(Status.CT);
                    return;
                default:
                    $i0 = $r1;
                    $r1 = $i0;
                    String str = "BasePendingResult";
                    Log.wtf(str, "Don't know how to handle message: " + $i0.what, new Exception());
                    return;
            }
        }

        public void zza(@Signature({"(", "Lcom/google/android/gms/common/api/ResultCallback", "<-TR;>;TR;)V"}) ResultCallback<? super R> $r1, @Signature({"(", "Lcom/google/android/gms/common/api/ResultCallback", "<-TR;>;TR;)V"}) R $r2) throws  {
            sendMessage(obtainMessage(1, new Pair($r1, $r2)));
        }

        public void zza(@Signature({"(", "Lcom/google/android/gms/internal/zzqm", "<TR;>;J)V"}) zzqm<R> $r1, @Signature({"(", "Lcom/google/android/gms/internal/zzqm", "<TR;>;J)V"}) long $l0) throws  {
            sendMessageDelayed(obtainMessage(2, $r1), $l0);
        }

        public void zzasq() throws  {
            removeMessages(2);
        }

        protected void zzb(@Signature({"(", "Lcom/google/android/gms/common/api/ResultCallback", "<-TR;>;TR;)V"}) ResultCallback<? super R> $r1, @Signature({"(", "Lcom/google/android/gms/common/api/ResultCallback", "<-TR;>;TR;)V"}) R $r2) throws  {
            try {
                $r1.onResult($r2);
            } catch (RuntimeException $r3) {
                zzqm.zze($r2);
                throw $r3;
            }
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    private final class zzb {
        final /* synthetic */ zzqm DF;

        private zzb(zzqm $r1) throws  {
            this.DF = $r1;
        }

        protected void finalize() throws Throwable {
            zzqm.zze(this.DF.CM);
            super.finalize();
        }
    }

    @Deprecated
    zzqm() throws  {
        this.Dt = new Object();
        this.zzalw = new CountDownLatch(1);
        this.Dw = new ArrayList();
        this.DE = false;
        this.Du = new zza(Looper.getMainLooper());
        this.Dv = new WeakReference(null);
    }

    @Deprecated
    protected zzqm(@Deprecated Looper $r1) throws  {
        this.Dt = new Object();
        this.zzalw = new CountDownLatch(1);
        this.Dw = new ArrayList();
        this.DE = false;
        this.Du = new zza($r1);
        this.Dv = new WeakReference(null);
    }

    protected zzqm(GoogleApiClient $r1) throws  {
        this.Dt = new Object();
        this.zzalw = new CountDownLatch(1);
        this.Dw = new ArrayList();
        this.DE = false;
        this.Du = new zza($r1 != null ? $r1.getLooper() : Looper.getMainLooper());
        this.Dv = new WeakReference($r1);
    }

    private R get() throws  {
        Result r3;
        boolean $z0 = true;
        synchronized (this.Dt) {
            if (this.Dz) {
                $z0 = false;
            }
            zzab.zza($z0, (Object) "Result has already been consumed.");
            zzab.zza(isReady(), (Object) "Result is not ready.");
            r3 = this.CM;
            this.CM = null;
            this.Dx = null;
            this.Dz = true;
        }
        zzasj();
        return r3;
    }

    private void zzd(@Signature({"(TR;)V"}) R $r1) throws  {
        this.CM = $r1;
        this.DB = null;
        this.zzalw.countDown();
        Status $r3 = this.CM.getStatus();
        if (this.zzaj) {
            this.Dx = null;
        } else if (this.Dx != null) {
            this.Du.zzasq();
            this.Du.zza(this.Dx, get());
        } else if (this.CM instanceof Releasable) {
            this.Dy = new zzb();
        }
        Iterator $r5 = this.Dw.iterator();
        while ($r5.hasNext()) {
            ((com.google.android.gms.common.api.PendingResult.zza) $r5.next()).zzac($r3);
        }
        this.Dw.clear();
    }

    public static void zze(Result $r0) throws  {
        if ($r0 instanceof Releasable) {
            try {
                ((Releasable) $r0).release();
            } catch (RuntimeException $r2) {
                String $r3 = String.valueOf($r0);
                Log.w("BasePendingResult", new StringBuilder(String.valueOf($r3).length() + 18).append("Unable to release ").append($r3).toString(), $r2);
            }
        }
    }

    public final R await() throws  {
        boolean $z0 = true;
        zzab.zza(Looper.myLooper() != Looper.getMainLooper(), (Object) "await must not be called on the UI thread");
        zzab.zza(!this.Dz, (Object) "Result has already been consumed");
        if (this.DD != null) {
            $z0 = false;
        }
        zzab.zza($z0, (Object) "Cannot await if then() has been called.");
        try {
            this.zzalw.await();
        } catch (InterruptedException e) {
            zzah(Status.CR);
        }
        zzab.zza(isReady(), (Object) "Result is not ready.");
        return get();
    }

    public final R await(@Signature({"(J", "Ljava/util/concurrent/TimeUnit;", ")TR;"}) long $l0, @Signature({"(J", "Ljava/util/concurrent/TimeUnit;", ")TR;"}) TimeUnit $r1) throws  {
        boolean $z0 = true;
        boolean $z1 = $l0 <= 0 || Looper.myLooper() != Looper.getMainLooper();
        zzab.zza($z1, (Object) "await must not be called on the UI thread when time is greater than zero.");
        zzab.zza(!this.Dz, (Object) "Result has already been consumed.");
        if (this.DD != null) {
            $z0 = false;
        }
        zzab.zza($z0, (Object) "Cannot await if then() has been called.");
        try {
            if (!this.zzalw.await($l0, $r1)) {
                zzah(Status.CT);
            }
        } catch (InterruptedException e) {
            zzah(Status.CR);
        }
        zzab.zza(isReady(), (Object) "Result is not ready.");
        return get();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void cancel() throws  {
        /*
        r8 = this;
        r0 = r8.Dt;
        monitor-enter(r0);
        r1 = r8.zzaj;	 Catch:{ Throwable -> 0x0029 }
        if (r1 != 0) goto L_0x000b;
    L_0x0007:
        r1 = r8.Dz;	 Catch:{ Throwable -> 0x0029 }
        if (r1 == 0) goto L_0x000d;
    L_0x000b:
        monitor-exit(r0);	 Catch:{ Throwable -> 0x0029 }
        return;
    L_0x000d:
        r2 = r8.DB;	 Catch:{ Throwable -> 0x0029 }
        if (r2 == 0) goto L_0x0016;
    L_0x0011:
        r2 = r8.DB;
        r2.cancel();	 Catch:{ RemoteException -> 0x002c }
    L_0x0016:
        r3 = r8.CM;	 Catch:{ Throwable -> 0x0029 }
        zze(r3);	 Catch:{ Throwable -> 0x0029 }
        r4 = 1;
        r8.zzaj = r4;	 Catch:{ Throwable -> 0x0029 }
        r5 = com.google.android.gms.common.api.Status.CU;	 Catch:{ Throwable -> 0x0029 }
        r3 = r8.zzb(r5);	 Catch:{ Throwable -> 0x0029 }
        r8.zzd(r3);	 Catch:{ Throwable -> 0x0029 }
        monitor-exit(r0);	 Catch:{ Throwable -> 0x0029 }
        return;
    L_0x0029:
        r6 = move-exception;
        monitor-exit(r0);	 Catch:{ Throwable -> 0x0029 }
        throw r6;
    L_0x002c:
        r7 = move-exception;
        goto L_0x0016;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzqm.cancel():void");
    }

    public boolean isCanceled() throws  {
        boolean z0;
        synchronized (this.Dt) {
            z0 = this.zzaj;
        }
        return z0;
    }

    public final boolean isReady() throws  {
        return this.zzalw.getCount() == 0;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void setResultCallback(@dalvik.annotation.Signature({"(", "Lcom/google/android/gms/common/api/ResultCallback", "<-TR;>;)V"}) com.google.android.gms.common.api.ResultCallback<? super R> r10) throws  {
        /*
        r9 = this;
        r0 = 1;
        r1 = r9.Dt;
        monitor-enter(r1);
        if (r10 != 0) goto L_0x000b;
    L_0x0006:
        r2 = 0;
        r9.Dx = r2;	 Catch:{ Throwable -> 0x0026 }
        monitor-exit(r1);	 Catch:{ Throwable -> 0x0026 }
        return;
    L_0x000b:
        r3 = r9.Dz;	 Catch:{ Throwable -> 0x0026 }
        if (r3 != 0) goto L_0x0029;
    L_0x000f:
        r3 = 1;
    L_0x0010:
        r4 = "Result has already been consumed.";
        com.google.android.gms.common.internal.zzab.zza(r3, r4);	 Catch:{ Throwable -> 0x0026 }
        r5 = r9.DD;	 Catch:{ Throwable -> 0x0026 }
        if (r5 != 0) goto L_0x002b;
    L_0x0019:
        r4 = "Cannot set callbacks if then() has been called.";
        com.google.android.gms.common.internal.zzab.zza(r0, r4);	 Catch:{ Throwable -> 0x0026 }
        r0 = r9.isCanceled();	 Catch:{ Throwable -> 0x0026 }
        if (r0 == 0) goto L_0x002d;
    L_0x0024:
        monitor-exit(r1);	 Catch:{ Throwable -> 0x0026 }
        return;
    L_0x0026:
        r6 = move-exception;
        monitor-exit(r1);	 Catch:{ Throwable -> 0x0026 }
        throw r6;
    L_0x0029:
        r3 = 0;
        goto L_0x0010;
    L_0x002b:
        r0 = 0;
        goto L_0x0019;
    L_0x002d:
        r0 = r9.isReady();	 Catch:{ Throwable -> 0x0026 }
        if (r0 == 0) goto L_0x003e;
    L_0x0033:
        r7 = r9.Du;	 Catch:{ Throwable -> 0x0026 }
        r8 = r9.get();	 Catch:{ Throwable -> 0x0026 }
        r7.zza(r10, r8);	 Catch:{ Throwable -> 0x0026 }
    L_0x003c:
        monitor-exit(r1);	 Catch:{ Throwable -> 0x0026 }
        return;
    L_0x003e:
        r9.Dx = r10;	 Catch:{ Throwable -> 0x0026 }
        goto L_0x003c;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzqm.setResultCallback(com.google.android.gms.common.api.ResultCallback):void");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void setResultCallback(@dalvik.annotation.Signature({"(", "Lcom/google/android/gms/common/api/ResultCallback", "<-TR;>;J", "Ljava/util/concurrent/TimeUnit;", ")V"}) com.google.android.gms.common.api.ResultCallback<? super R> r10, @dalvik.annotation.Signature({"(", "Lcom/google/android/gms/common/api/ResultCallback", "<-TR;>;J", "Ljava/util/concurrent/TimeUnit;", ")V"}) long r11, @dalvik.annotation.Signature({"(", "Lcom/google/android/gms/common/api/ResultCallback", "<-TR;>;J", "Ljava/util/concurrent/TimeUnit;", ")V"}) java.util.concurrent.TimeUnit r13) throws  {
        /*
        r9 = this;
        r0 = 1;
        r1 = r9.Dt;
        monitor-enter(r1);
        if (r10 != 0) goto L_0x000b;
    L_0x0006:
        r2 = 0;
        r9.Dx = r2;	 Catch:{ Throwable -> 0x0026 }
        monitor-exit(r1);	 Catch:{ Throwable -> 0x0026 }
        return;
    L_0x000b:
        r3 = r9.Dz;	 Catch:{ Throwable -> 0x0026 }
        if (r3 != 0) goto L_0x0029;
    L_0x000f:
        r3 = 1;
    L_0x0010:
        r4 = "Result has already been consumed.";
        com.google.android.gms.common.internal.zzab.zza(r3, r4);	 Catch:{ Throwable -> 0x0026 }
        r5 = r9.DD;	 Catch:{ Throwable -> 0x0026 }
        if (r5 != 0) goto L_0x002b;
    L_0x0019:
        r4 = "Cannot set callbacks if then() has been called.";
        com.google.android.gms.common.internal.zzab.zza(r0, r4);	 Catch:{ Throwable -> 0x0026 }
        r0 = r9.isCanceled();	 Catch:{ Throwable -> 0x0026 }
        if (r0 == 0) goto L_0x002d;
    L_0x0024:
        monitor-exit(r1);	 Catch:{ Throwable -> 0x0026 }
        return;
    L_0x0026:
        r6 = move-exception;
        monitor-exit(r1);	 Catch:{ Throwable -> 0x0026 }
        throw r6;
    L_0x0029:
        r3 = 0;
        goto L_0x0010;
    L_0x002b:
        r0 = 0;
        goto L_0x0019;
    L_0x002d:
        r0 = r9.isReady();	 Catch:{ Throwable -> 0x0026 }
        if (r0 == 0) goto L_0x003e;
    L_0x0033:
        r7 = r9.Du;	 Catch:{ Throwable -> 0x0026 }
        r8 = r9.get();	 Catch:{ Throwable -> 0x0026 }
        r7.zza(r10, r8);	 Catch:{ Throwable -> 0x0026 }
    L_0x003c:
        monitor-exit(r1);	 Catch:{ Throwable -> 0x0026 }
        return;
    L_0x003e:
        r9.Dx = r10;	 Catch:{ Throwable -> 0x0026 }
        r7 = r9.Du;	 Catch:{ Throwable -> 0x0026 }
        r11 = r13.toMillis(r11);	 Catch:{ Throwable -> 0x0026 }
        r7.zza(r9, r11);	 Catch:{ Throwable -> 0x0026 }
        goto L_0x003c;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzqm.setResultCallback(com.google.android.gms.common.api.ResultCallback, long, java.util.concurrent.TimeUnit):void");
    }

    public void store(ResultStore $r1, int $i0) throws  {
        zzab.zzb((Object) $r1, (Object) "ResultStore must not be null.");
        synchronized (this.Dt) {
            zzab.zza(!this.Dz, (Object) "Result has already been consumed.");
            $r1.zza($i0, (PendingResult) this);
        }
    }

    public <S extends Result> TransformedResult<S> then(@Signature({"<S::", "Lcom/google/android/gms/common/api/Result;", ">(", "Lcom/google/android/gms/common/api/ResultTransform", "<-TR;+TS;>;)", "Lcom/google/android/gms/common/api/TransformedResult", "<TS;>;"}) ResultTransform<? super R, ? extends S> $r1) throws  {
        TransformedResult $r6;
        boolean $z0 = true;
        zzab.zza(!this.Dz, (Object) "Result has already been consumed.");
        synchronized (this.Dt) {
            zzab.zza(this.DD == null, (Object) "Cannot call then() twice.");
            if (this.Dx != null) {
                $z0 = false;
            }
            zzab.zza($z0, (Object) "Cannot call then() if callbacks are set.");
            this.DE = true;
            this.DD = new zzsa(this.Dv);
            $r6 = this.DD.then($r1);
            if (isReady()) {
                this.Du.zza(this.DD, get());
            } else {
                this.Dx = this.DD;
            }
        }
        return $r6;
    }

    public final void zza(com.google.android.gms.common.api.PendingResult.zza $r1) throws  {
        boolean $z0 = true;
        zzab.zza(!this.Dz, (Object) "Result has already been consumed.");
        if ($r1 == null) {
            $z0 = false;
        }
        zzab.zzb($z0, (Object) "Callback cannot be null.");
        synchronized (this.Dt) {
            if (isReady()) {
                $r1.zzac(this.CM.getStatus());
            } else {
                this.Dw.add($r1);
            }
        }
    }

    protected final void zza(zzr $r1) throws  {
        synchronized (this.Dt) {
            this.DB = $r1;
        }
    }

    public final void zzah(Status $r1) throws  {
        synchronized (this.Dt) {
            if (!isReady()) {
                zzc(zzb($r1));
                this.DA = true;
            }
        }
    }

    public Integer zzasb() throws  {
        return this.DC;
    }

    protected void zzasj() throws  {
    }

    public boolean zzasm() throws  {
        boolean $z0;
        synchronized (this.Dt) {
            if (((GoogleApiClient) this.Dv.get()) == null || !this.DE) {
                cancel();
            }
            $z0 = isCanceled();
        }
        return $z0;
    }

    public void zzasn() throws  {
        boolean $z0 = this.DE || ((Boolean) Ds.get()).booleanValue();
        this.DE = $z0;
    }

    boolean zzaso() throws  {
        return false;
    }

    protected abstract R zzb(@Signature({"(", "Lcom/google/android/gms/common/api/Status;", ")TR;"}) Status status) throws ;

    public final void zzc(@Signature({"(TR;)V"}) R $r1) throws  {
        boolean $z0 = true;
        synchronized (this.Dt) {
            if (this.DA || this.zzaj || (isReady() && zzaso())) {
                zze($r1);
                return;
            }
            zzab.zza(!isReady(), (Object) "Results have already been set");
            if (this.Dz) {
                $z0 = false;
            }
            zzab.zza($z0, (Object) "Result has already been consumed");
            zzd($r1);
        }
    }

    public void zzhp(int $i0) throws  {
        zzab.zzb(this.DC == null, (Object) "PendingResult should only be stored once.");
        this.DC = Integer.valueOf($i0);
    }
}
