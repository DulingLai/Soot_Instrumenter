package com.google.android.gms.internal;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.ResultCallbacks;
import com.google.android.gms.common.api.ResultTransform;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.TransformedResult;
import com.google.android.gms.common.internal.zzab;
import dalvik.annotation.Signature;
import java.lang.ref.WeakReference;

/* compiled from: dalvik_source_com.waze.apk */
public class zzsa<R extends Result> extends TransformedResult<R> implements ResultCallback<R> {
    private final Object Dt = new Object();
    private final WeakReference<GoogleApiClient> Dv;
    private ResultTransform<? super R, ? extends Result> Gc = null;
    private zzsa<? extends Result> Gd = null;
    private volatile ResultCallbacks<? super R> Ge = null;
    private PendingResult<R> Gf = null;
    private Status Gg = null;
    private final zza Gh;
    private boolean Gi = false;

    /* compiled from: dalvik_source_com.waze.apk */
    private final class zza extends Handler {
        final /* synthetic */ zzsa Gk;

        public zza(zzsa $r1, Looper $r2) throws  {
            this.Gk = $r1;
            super($r2);
        }

        public void handleMessage(Message $r1) throws  {
            int $i0 = $r1;
            $r1 = $i0;
            switch ($i0.what) {
                case 0:
                    PendingResult $r5 = (PendingResult) $r1.obj;
                    synchronized (this.Gk.Dt) {
                        if ($r5 != null) {
                            if ($r5 instanceof zzrt) {
                                this.Gk.Gd.zzaj(((zzrt) $r5).getStatus());
                            } else {
                                this.Gk.Gd.zza($r5);
                            }
                            break;
                        }
                        this.Gk.Gd.zzaj(new Status(13, "Transform returned null"));
                        break;
                    }
                    return;
                case 1:
                    RuntimeException $r10 = (RuntimeException) $r1.obj;
                    String $r3 = "Runtime exception on the transformation worker thread: ";
                    String $r11 = String.valueOf($r10.getMessage());
                    Log.e("TransformedResultImpl", $r11.length() != 0 ? $r3.concat($r11) : new String("Runtime exception on the transformation worker thread: "));
                    throw $r10;
                default:
                    $i0 = $r1;
                    $r1 = $i0;
                    Log.e("TransformedResultImpl", "TransformationResultHandler received unknown message type: " + $i0.what);
                    return;
            }
        }
    }

    public zzsa(@Signature({"(", "Ljava/lang/ref/WeakReference", "<", "Lcom/google/android/gms/common/api/GoogleApiClient;", ">;)V"}) WeakReference<GoogleApiClient> $r1) throws  {
        zzab.zzb((Object) $r1, (Object) "GoogleApiClient reference must not be null");
        this.Dv = $r1;
        GoogleApiClient $r3 = (GoogleApiClient) this.Dv.get();
        this.Gh = new zza(this, $r3 != null ? $r3.getLooper() : Looper.getMainLooper());
    }

    private void zzaj(Status $r1) throws  {
        synchronized (this.Dt) {
            this.Gg = $r1;
            zzak(this.Gg);
        }
    }

    private void zzak(Status $r1) throws  {
        synchronized (this.Dt) {
            if (this.Gc != null) {
                Object $r12 = this.Gc.onFailure($r1);
                zzab.zzb($r12, (Object) "onFailure must not return null");
                this.Gd.zzaj($r12);
            } else if (zzaur()) {
                this.Ge.onFailure($r1);
            }
        }
    }

    private void zzaup() throws  {
        if (this.Gc != null || this.Ge != null) {
            GoogleApiClient $r5 = (GoogleApiClient) this.Dv.get();
            if (!(this.Gi || this.Gc == null || $r5 == null)) {
                $r5.zza(this);
                this.Gi = true;
            }
            if (this.Gg != null) {
                zzak(this.Gg);
            } else if (this.Gf != null) {
                this.Gf.setResultCallback(this);
            }
        }
    }

    private boolean zzaur() throws  {
        return (this.Ge == null || ((GoogleApiClient) this.Dv.get()) == null) ? false : true;
    }

    private void zze(Result $r1) throws  {
        if ($r1 instanceof Releasable) {
            try {
                ((Releasable) $r1).release();
            } catch (RuntimeException $r3) {
                String $r4 = String.valueOf($r1);
                Log.w("TransformedResultImpl", new StringBuilder(String.valueOf($r4).length() + 18).append("Unable to release ").append($r4).toString(), $r3);
            }
        }
    }

    public void andFinally(@NonNull @Signature({"(", "Lcom/google/android/gms/common/api/ResultCallbacks", "<-TR;>;)V"}) ResultCallbacks<? super R> $r1) throws  {
        boolean $z0 = true;
        synchronized (this.Dt) {
            zzab.zza(this.Ge == null, (Object) "Cannot call andFinally() twice.");
            if (this.Gc != null) {
                $z0 = false;
            }
            zzab.zza($z0, (Object) "Cannot call then() and andFinally() on the same TransformedResult.");
            this.Ge = $r1;
            zzaup();
        }
    }

    public void onResult(@Signature({"(TR;)V"}) final R $r1) throws  {
        synchronized (this.Dt) {
            if (!$r1.getStatus().isSuccess()) {
                zzaj($r1.getStatus());
                zze((Result) $r1);
            } else if (this.Gc != null) {
                zzrs.zzatt().submit(new Runnable(this) {
                    final /* synthetic */ zzsa Gk;

                    @android.support.annotation.WorkerThread
                    public void run() throws  {
                        /* JADX: method processing error */
/*
Error: java.util.NoSuchElementException
	at java.util.HashMap$HashIterator.nextNode(HashMap.java:1431)
	at java.util.HashMap$KeyIterator.next(HashMap.java:1453)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.applyRemove(BlockFinallyExtract.java:535)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.extractFinally(BlockFinallyExtract.java:175)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.processExceptionHandler(BlockFinallyExtract.java:79)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.visit(BlockFinallyExtract.java:51)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                        /*
                        r19 = this;
                        r1 = com.google.android.gms.internal.zzqm.Ds;
                        r3 = 1;	 Catch:{ RuntimeException -> 0x0061, Throwable -> 0x00ab }
                        r2 = java.lang.Boolean.valueOf(r3);	 Catch:{ RuntimeException -> 0x0061, Throwable -> 0x00ab }
                        r1.set(r2);	 Catch:{ RuntimeException -> 0x0061, Throwable -> 0x00ab }
                        r0 = r19;	 Catch:{ RuntimeException -> 0x0061, Throwable -> 0x00ab }
                        r4 = r0.Gk;	 Catch:{ RuntimeException -> 0x0061, Throwable -> 0x00ab }
                        r5 = r4.Gc;	 Catch:{ RuntimeException -> 0x0061, Throwable -> 0x00ab }
                        r0 = r19;	 Catch:{ RuntimeException -> 0x0061, Throwable -> 0x00ab }
                        r6 = r9;	 Catch:{ RuntimeException -> 0x0061, Throwable -> 0x00ab }
                        r7 = r5.onSuccess(r6);	 Catch:{ RuntimeException -> 0x0061, Throwable -> 0x00ab }
                        r0 = r19;	 Catch:{ RuntimeException -> 0x0061, Throwable -> 0x00ab }
                        r4 = r0.Gk;	 Catch:{ RuntimeException -> 0x0061, Throwable -> 0x00ab }
                        r8 = r4.Gh;	 Catch:{ RuntimeException -> 0x0061, Throwable -> 0x00ab }
                        r0 = r19;	 Catch:{ RuntimeException -> 0x0061, Throwable -> 0x00ab }
                        r4 = r0.Gk;	 Catch:{ RuntimeException -> 0x0061, Throwable -> 0x00ab }
                        r9 = r4.Gh;	 Catch:{ RuntimeException -> 0x0061, Throwable -> 0x00ab }
                        r3 = 0;	 Catch:{ RuntimeException -> 0x0061, Throwable -> 0x00ab }
                        r10 = r9.obtainMessage(r3, r7);	 Catch:{ RuntimeException -> 0x0061, Throwable -> 0x00ab }
                        r8.sendMessage(r10);	 Catch:{ RuntimeException -> 0x0061, Throwable -> 0x00ab }
                        r1 = com.google.android.gms.internal.zzqm.Ds;
                        r3 = 0;
                        r2 = java.lang.Boolean.valueOf(r3);
                        r1.set(r2);
                        r0 = r19;
                        r4 = r0.Gk;
                        r0 = r19;
                        r6 = r9;
                        r4.zze(r6);
                        r0 = r19;
                        r4 = r0.Gk;
                        r11 = r4.Dv;
                        r12 = r11.get();
                        r14 = r12;
                        r14 = (com.google.android.gms.common.api.GoogleApiClient) r14;
                        r13 = r14;
                        if (r13 == 0) goto L_0x00dd;
                    L_0x0059:
                        r0 = r19;
                        r4 = r0.Gk;
                        r13.zzb(r4);
                        return;
                    L_0x0061:
                        r15 = move-exception;
                        r0 = r19;	 Catch:{ RuntimeException -> 0x0061, Throwable -> 0x00ab }
                        r4 = r0.Gk;	 Catch:{ RuntimeException -> 0x0061, Throwable -> 0x00ab }
                        r8 = r4.Gh;	 Catch:{ RuntimeException -> 0x0061, Throwable -> 0x00ab }
                        r0 = r19;	 Catch:{ RuntimeException -> 0x0061, Throwable -> 0x00ab }
                        r4 = r0.Gk;	 Catch:{ RuntimeException -> 0x0061, Throwable -> 0x00ab }
                        r9 = r4.Gh;	 Catch:{ RuntimeException -> 0x0061, Throwable -> 0x00ab }
                        r3 = 1;	 Catch:{ RuntimeException -> 0x0061, Throwable -> 0x00ab }
                        r10 = r9.obtainMessage(r3, r15);	 Catch:{ RuntimeException -> 0x0061, Throwable -> 0x00ab }
                        r8.sendMessage(r10);	 Catch:{ RuntimeException -> 0x0061, Throwable -> 0x00ab }
                        r1 = com.google.android.gms.internal.zzqm.Ds;
                        r3 = 0;
                        r2 = java.lang.Boolean.valueOf(r3);
                        r1.set(r2);
                        r0 = r19;
                        r4 = r0.Gk;
                        r0 = r19;
                        r6 = r9;
                        r4.zze(r6);
                        r0 = r19;
                        r4 = r0.Gk;
                        r11 = r4.Dv;
                        r12 = r11.get();
                        r16 = r12;
                        r16 = (com.google.android.gms.common.api.GoogleApiClient) r16;
                        r13 = r16;
                        if (r13 == 0) goto L_0x00de;
                    L_0x00a3:
                        r0 = r19;
                        r4 = r0.Gk;
                        r13.zzb(r4);
                        return;
                    L_0x00ab:
                        r17 = move-exception;
                        r1 = com.google.android.gms.internal.zzqm.Ds;
                        r3 = 0;
                        r2 = java.lang.Boolean.valueOf(r3);
                        r1.set(r2);
                        r0 = r19;
                        r4 = r0.Gk;
                        r0 = r19;
                        r6 = r9;
                        r4.zze(r6);
                        r0 = r19;
                        r4 = r0.Gk;
                        r11 = r4.Dv;
                        r12 = r11.get();
                        r18 = r12;
                        r18 = (com.google.android.gms.common.api.GoogleApiClient) r18;
                        r13 = r18;
                        if (r13 == 0) goto L_0x00dc;
                    L_0x00d5:
                        r0 = r19;
                        r4 = r0.Gk;
                        r13.zzb(r4);
                    L_0x00dc:
                        throw r17;
                    L_0x00dd:
                        return;
                    L_0x00de:
                        return;
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzsa.1.run():void");
                    }
                });
            } else if (zzaur()) {
                this.Ge.onSuccess($r1);
            }
        }
    }

    @NonNull
    public <S extends Result> TransformedResult<S> then(@NonNull @Signature({"<S::", "Lcom/google/android/gms/common/api/Result;", ">(", "Lcom/google/android/gms/common/api/ResultTransform", "<-TR;+TS;>;)", "Lcom/google/android/gms/common/api/TransformedResult", "<TS;>;"}) ResultTransform<? super R, ? extends S> $r1) throws  {
        zzsa $r5;
        boolean $z0 = true;
        synchronized (this.Dt) {
            zzab.zza(this.Gc == null, (Object) "Cannot call then() twice.");
            if (this.Ge != null) {
                $z0 = false;
            }
            zzab.zza($z0, (Object) "Cannot call then() and andFinally() on the same TransformedResult.");
            this.Gc = $r1;
            $r5 = new zzsa(this.Dv);
            this.Gd = $r5;
            zzaup();
        }
        return $r5;
    }

    public void zza(@Signature({"(", "Lcom/google/android/gms/common/api/PendingResult", "<*>;)V"}) PendingResult<?> $r1) throws  {
        synchronized (this.Dt) {
            this.Gf = $r1;
            zzaup();
        }
    }

    void zzauq() throws  {
        this.Ge = null;
    }
}
