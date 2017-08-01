package com.google.android.gms.internal;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.HandlerThread;
import android.os.Message;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.support.v4.util.ArrayMap;
import android.util.Log;
import android.util.Pair;
import android.util.SparseArray;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.ApiOptions;
import com.google.android.gms.common.api.Api.zze;
import com.google.android.gms.common.api.Api.zzh;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzah;
import com.google.android.gms.common.internal.zzd.zzf;
import com.google.android.gms.common.internal.zzg;
import com.google.android.gms.tasks.TaskCompletionSource;
import dalvik.annotation.Signature;
import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: dalvik_source_com.waze.apk */
public class zzra implements Callback {
    private static zzra Fi;
    private static final Object zzanj = new Object();
    private final GoogleApiAvailability CH;
    private long EG;
    private long EH;
    private long Fh;
    private int Fj;
    private final AtomicInteger Fk;
    private final SparseArray<zzc<?>> Fl;
    private final Map<zzqh<?>, zzc<?>> Fm;
    private zzqp Fn;
    private final Set<zzqh<?>> Fo;
    private final ReferenceQueue<com.google.android.gms.common.api.zzc<?>> Fp;
    private final SparseArray<zza> Fq;
    private zzb Fr;
    private final Context mContext;
    private final Handler mHandler;

    /* compiled from: dalvik_source_com.waze.apk */
    private final class zza extends PhantomReference<com.google.android.gms.common.api.zzc<?>> {
        private final int CY;
        final /* synthetic */ zzra Fs;

        public zza(@Signature({"(", "Lcom/google/android/gms/common/api/zzc;", "I", "Ljava/lang/ref/ReferenceQueue", "<", "Lcom/google/android/gms/common/api/zzc", "<*>;>;)V"}) zzra $r1, @Signature({"(", "Lcom/google/android/gms/common/api/zzc;", "I", "Ljava/lang/ref/ReferenceQueue", "<", "Lcom/google/android/gms/common/api/zzc", "<*>;>;)V"}) com.google.android.gms.common.api.zzc $r2, @Signature({"(", "Lcom/google/android/gms/common/api/zzc;", "I", "Ljava/lang/ref/ReferenceQueue", "<", "Lcom/google/android/gms/common/api/zzc", "<*>;>;)V"}) int $i0, @Signature({"(", "Lcom/google/android/gms/common/api/zzc;", "I", "Ljava/lang/ref/ReferenceQueue", "<", "Lcom/google/android/gms/common/api/zzc", "<*>;>;)V"}) ReferenceQueue<com.google.android.gms.common.api.zzc<?>> $r3) throws  {
            this.Fs = $r1;
            super($r2, $r3);
            this.CY = $i0;
        }

        public void zzatx() throws  {
            this.Fs.mHandler.sendMessage(this.Fs.mHandler.obtainMessage(2, this.CY, 2));
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    private static final class zzb extends Thread {
        private final ReferenceQueue<com.google.android.gms.common.api.zzc<?>> Fp;
        private final SparseArray<zza> Fq;
        private final AtomicBoolean Ft = new AtomicBoolean();

        public zzb(@Signature({"(", "Ljava/lang/ref/ReferenceQueue", "<", "Lcom/google/android/gms/common/api/zzc", "<*>;>;", "Landroid/util/SparseArray", "<", "Lcom/google/android/gms/internal/zzra$zza;", ">;)V"}) ReferenceQueue<com.google.android.gms.common.api.zzc<?>> $r1, @Signature({"(", "Ljava/lang/ref/ReferenceQueue", "<", "Lcom/google/android/gms/common/api/zzc", "<*>;>;", "Landroid/util/SparseArray", "<", "Lcom/google/android/gms/internal/zzra$zza;", ">;)V"}) SparseArray<zza> $r2) throws  {
            super("GoogleApiCleanup");
            this.Fp = $r1;
            this.Fq = $r2;
        }

        public void run() throws  {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x0032 in list [B:17:0x0039]
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:42)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:60)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
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
            r11 = this;
            r0 = r11.Ft;
            r1 = 1;
            r0.set(r1);
            r1 = 10;
            android.os.Process.setThreadPriority(r1);
        L_0x000b:
            r0 = r11.Ft;	 Catch:{ Throwable -> 0x0039 }
            r2 = r0.get();	 Catch:{ InterruptedException -> 0x002a }
            if (r2 == 0) goto L_0x0032;	 Catch:{ Throwable -> 0x0039 }
        L_0x0013:
            r3 = r11.Fp;	 Catch:{ InterruptedException -> 0x002a }
            r4 = r3.remove();	 Catch:{ InterruptedException -> 0x002a }
            r6 = r4;
            r6 = (com.google.android.gms.internal.zzra.zza) r6;
            r5 = r6;
            r7 = r11.Fq;	 Catch:{ InterruptedException -> 0x002a }
            r8 = r5.CY;	 Catch:{ InterruptedException -> 0x002a }
            r7.remove(r8);	 Catch:{ InterruptedException -> 0x002a }
            r5.zzatx();	 Catch:{ InterruptedException -> 0x002a }
            goto L_0x000b;
        L_0x002a:
            r9 = move-exception;
            r0 = r11.Ft;
            r1 = 0;
            r0.set(r1);
            return;
        L_0x0032:
            r0 = r11.Ft;
            r1 = 0;
            r0.set(r1);
            return;
        L_0x0039:
            r10 = move-exception;
            r0 = r11.Ft;
            r1 = 0;
            r0.set(r1);
            throw r10;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzra.zzb.run():void");
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    private class zzc<O extends ApiOptions> implements ConnectionCallbacks, OnConnectionFailedListener {
        private final zzqh<O> Cq;
        private boolean EF;
        private ConnectionResult FA = null;
        final /* synthetic */ zzra Fs;
        private final Queue<zzqg> Fu = new LinkedList();
        private final zze Fv;
        private final com.google.android.gms.common.api.Api.zzb Fw;
        private final SparseArray<zzsb> Fx = new SparseArray();
        private final Set<zzqj> Fy = new HashSet();
        private final SparseArray<Map<Object, com.google.android.gms.internal.zzqk.zza>> Fz = new SparseArray();

        @WorkerThread
        public zzc(@Signature({"(", "Lcom/google/android/gms/common/api/zzc", "<TO;>;)V"}) zzra $r1, @Signature({"(", "Lcom/google/android/gms/common/api/zzc", "<TO;>;)V"}) com.google.android.gms.common.api.zzc<O> $r2) throws  {
            this.Fs = $r1;
            this.Fv = zzc((com.google.android.gms.common.api.zzc) $r2);
            if (this.Fv instanceof zzah) {
                this.Fw = ((zzah) this.Fv).zzaxk();
            } else {
                this.Fw = this.Fv;
            }
            this.Cq = $r2.zzaru();
        }

        @WorkerThread
        private void connect() throws  {
            if (!this.Fv.isConnected() && !this.Fv.isConnecting()) {
                if (this.Fv.zzarn() && this.Fs.Fj != 0) {
                    this.Fs.Fj = this.Fs.CH.isGooglePlayServicesAvailable(this.Fs.mContext);
                    if (this.Fs.Fj != 0) {
                        onConnectionFailed(new ConnectionResult(this.Fs.Fj, null));
                        return;
                    }
                }
                this.Fv.zza(new zzd(this.Fs, this.Fv, this.Cq));
            }
        }

        @WorkerThread
        private void resume() throws  {
            if (this.EF) {
                connect();
            }
        }

        @WorkerThread
        private void zzai(Status $r1) throws  {
            for (zzqg zzae : this.Fu) {
                zzae.zzae($r1);
            }
            this.Fu.clear();
        }

        @WorkerThread
        private void zzatl() throws  {
            if (this.EF) {
                zzaub();
                zzai(this.Fs.CH.isGooglePlayServicesAvailable(this.Fs.mContext) == 18 ? new Status(8, "Connection timed out while waiting for Google Play services update to complete.") : new Status(8, "API failed to connect while resuming due to an unknown error."));
                this.Fv.disconnect();
            }
        }

        @WorkerThread
        private void zzaub() throws  {
            if (this.EF) {
                this.Fs.mHandler.removeMessages(9, this.Cq);
                this.Fs.mHandler.removeMessages(8, this.Cq);
                this.EF = false;
            }
        }

        private void zzauc() throws  {
            this.Fs.mHandler.removeMessages(10, this.Cq);
            this.Fs.mHandler.sendMessageDelayed(this.Fs.mHandler.obtainMessage(10, this.Cq), this.Fs.Fh);
        }

        private void zzaud() throws  {
            if (this.Fv.isConnected() && this.Fz.size() == 0) {
                for (int $i0 = 0; $i0 < this.Fx.size(); $i0++) {
                    if (((zzsb) this.Fx.get(this.Fx.keyAt($i0))).zzaut()) {
                        zzauc();
                        return;
                    }
                }
                this.Fv.disconnect();
            }
        }

        @WorkerThread
        private zze zzc(com.google.android.gms.common.api.zzc $r1) throws  {
            Api $r2 = $r1.zzars();
            if ($r2.zzarm()) {
                zzh $r3 = $r2.zzark();
                return new zzah($r1.getApplicationContext(), this.Fs.mHandler.getLooper(), $r3.zzarp(), this, this, zzg.zzby($r1.getApplicationContext()), $r3.zzv($r1.zzart()));
            }
            return $r1.zzars().zzarj().zza($r1.getApplicationContext(), this.Fs.mHandler.getLooper(), zzg.zzby($r1.getApplicationContext()), $r1.zzart(), this, this);
        }

        @WorkerThread
        private void zzc(zzqg $r1) throws  {
            $r1.zza(this.Fx);
            Map $r4;
            if ($r1.bG == 3) {
                try {
                    $r4 = (Map) this.Fz.get($r1.CY);
                    if ($r4 == null) {
                        ArrayMap $r5 = new ArrayMap(1);
                        this.Fz.put($r1.CY, $r5);
                        $r4 = $r5;
                    }
                    com.google.android.gms.internal.zzqk.zza $r7 = ((com.google.android.gms.internal.zzqg.zza) $r1).CZ;
                    $r4.put(((zzrl) $r7).zzaum(), $r7);
                } catch (ClassCastException e) {
                    throw new IllegalStateException("Listener registration methods must implement ListenerApiMethod");
                }
            } else if ($r1.bG == 4) {
                try {
                    $r4 = (Map) this.Fz.get($r1.CY);
                    zzrl $r8 = (zzrl) ((com.google.android.gms.internal.zzqg.zza) $r1).CZ;
                    if ($r4 != null) {
                        $r4.remove($r8.zzaum());
                    } else {
                        Log.w("GoogleApiManager", "Received call to unregister a listener without a matching registration call.");
                    }
                } catch (ClassCastException e2) {
                    throw new IllegalStateException("Listener unregistration methods must implement ListenerApiMethod");
                }
            }
            try {
                $r1.zzb(this.Fw);
            } catch (DeadObjectException e3) {
                zze $r14 = this.Fv;
                $r14.disconnect();
                onConnectionSuspended(1);
            }
        }

        @WorkerThread
        private void zzj(ConnectionResult $r1) throws  {
            for (zzqj zza : this.Fy) {
                zza.zza(this.Cq, $r1);
            }
            this.Fy.clear();
        }

        boolean isConnected() throws  {
            return this.Fv.isConnected();
        }

        @WorkerThread
        public void onConnected(@Nullable Bundle bundle) throws  {
            zzatz();
            zzj(ConnectionResult.BB);
            zzaub();
            for (int $i0 = 0; $i0 < this.Fz.size(); $i0++) {
                for (com.google.android.gms.internal.zzqk.zza $r10 : ((Map) this.Fz.get(this.Fz.keyAt($i0))).values()) {
                    try {
                        $r10.zzb(this.Fw);
                    } catch (DeadObjectException e) {
                        zze $r12 = this.Fv;
                        $r12.disconnect();
                        onConnectionSuspended(1);
                    }
                }
            }
            zzaty();
            zzauc();
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        @android.support.annotation.WorkerThread
        public void onConnectionFailed(@android.support.annotation.NonNull com.google.android.gms.common.ConnectionResult r26) throws  {
            /*
            r25 = this;
            r0 = r25;
            r0.zzatz();
            r0 = r25;
            r4 = r0.Fs;
            r5 = -1;
            r4.Fj = r5;
            r0 = r25;
            r1 = r26;
            r0.zzj(r1);
            r0 = r25;
            r6 = r0.Fx;
            r5 = 0;
            r7 = r6.keyAt(r5);
            r0 = r25;
            r8 = r0.Fu;
            r9 = r8.isEmpty();
            if (r9 == 0) goto L_0x002e;
        L_0x0027:
            r0 = r26;
            r1 = r25;
            r1.FA = r0;
            return;
        L_0x002e:
            r10 = com.google.android.gms.internal.zzra.zzanj;
            monitor-enter(r10);
            r0 = r25;
            r4 = r0.Fs;	 Catch:{ Throwable -> 0x005e }
            r11 = r4.Fn;	 Catch:{ Throwable -> 0x005e }
            if (r11 == 0) goto L_0x0061;
        L_0x003d:
            r0 = r25;
            r4 = r0.Fs;	 Catch:{ Throwable -> 0x005e }
            r12 = r4.Fo;	 Catch:{ Throwable -> 0x005e }
            r0 = r25;
            r13 = r0.Cq;	 Catch:{ Throwable -> 0x005e }
            r9 = r12.contains(r13);	 Catch:{ Throwable -> 0x005e }
            if (r9 == 0) goto L_0x0061;
        L_0x004f:
            r0 = r25;
            r4 = r0.Fs;	 Catch:{ Throwable -> 0x005e }
            r11 = r4.Fn;	 Catch:{ Throwable -> 0x005e }
            r0 = r26;
            r11.zzb(r0, r7);	 Catch:{ Throwable -> 0x005e }
            monitor-exit(r10);	 Catch:{ Throwable -> 0x005e }
            return;
        L_0x005e:
            r14 = move-exception;
            monitor-exit(r10);	 Catch:{ Throwable -> 0x005e }
            throw r14;
        L_0x0061:
            monitor-exit(r10);	 Catch:{ Throwable -> 0x005e }
            r0 = r25;
            r4 = r0.Fs;
            r0 = r26;
            r9 = r4.zzc(r0, r7);
            if (r9 != 0) goto L_0x0107;
        L_0x006e:
            r0 = r26;
            r7 = r0.getErrorCode();
            r5 = 18;
            if (r7 != r5) goto L_0x007d;
        L_0x0078:
            r5 = 1;
            r0 = r25;
            r0.EF = r5;
        L_0x007d:
            r0 = r25;
            r9 = r0.EF;
            if (r9 == 0) goto L_0x00af;
        L_0x0083:
            r0 = r25;
            r4 = r0.Fs;
            r15 = r4.mHandler;
            r0 = r25;
            r4 = r0.Fs;
            r16 = r4.mHandler;
            r0 = r25;
            r13 = r0.Cq;
            r5 = 8;
            r0 = r16;
            r17 = android.os.Message.obtain(r0, r5, r13);
            r0 = r25;
            r4 = r0.Fs;
            r18 = r4.EH;
            r0 = r17;
            r1 = r18;
            r15.sendMessageDelayed(r0, r1);
            return;
        L_0x00af:
            r20 = new com.google.android.gms.common.api.Status;
            r0 = r25;
            r13 = r0.Cq;
            r21 = r13.zzase();
            r0 = r21;
            r21 = java.lang.String.valueOf(r0);
            r22 = new java.lang.StringBuilder;
            r0 = r21;
            r23 = java.lang.String.valueOf(r0);
            r0 = r23;
            r7 = r0.length();
            r7 = r7 + 38;
            r0 = r22;
            r0.<init>(r7);
            r24 = "API: ";
            r0 = r22;
            r1 = r24;
            r22 = r0.append(r1);
            r0 = r22;
            r1 = r21;
            r22 = r0.append(r1);
            r24 = " is not available on this device.";
            r0 = r22;
            r1 = r24;
            r22 = r0.append(r1);
            r0 = r22;
            r21 = r0.toString();
            r5 = 17;
            r0 = r20;
            r1 = r21;
            r0.<init>(r5, r1);
            r0 = r25;
            r1 = r20;
            r0.zzai(r1);
            return;
        L_0x0107:
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzra.zzc.onConnectionFailed(com.google.android.gms.common.ConnectionResult):void");
        }

        @WorkerThread
        public void onConnectionSuspended(int i) throws  {
            zzatz();
            this.EF = true;
            this.Fs.mHandler.sendMessageDelayed(Message.obtain(this.Fs.mHandler, 8, this.Cq), this.Fs.EH);
            this.Fs.mHandler.sendMessageDelayed(Message.obtain(this.Fs.mHandler, 9, this.Cq), this.Fs.EG);
            this.Fs.Fj = -1;
        }

        @WorkerThread
        public void zzaty() throws  {
            while (this.Fv.isConnected() && !this.Fu.isEmpty()) {
                zzc((zzqg) this.Fu.remove());
            }
        }

        @WorkerThread
        public void zzatz() throws  {
            this.FA = null;
        }

        ConnectionResult zzaua() throws  {
            return this.FA;
        }

        @WorkerThread
        public void zzb(zzqg $r1) throws  {
            if (this.Fv.isConnected()) {
                zzc($r1);
                zzauc();
                return;
            }
            this.Fu.add($r1);
            if (this.FA == null || !this.FA.hasResolution()) {
                connect();
            } else {
                onConnectionFailed(this.FA);
            }
        }

        @WorkerThread
        public void zzb(zzqj $r1) throws  {
            this.Fy.add($r1);
        }

        @WorkerThread
        public void zzh(int $i0, boolean $z0) throws  {
            this = this;
            Iterator $r2 = this.Fu.iterator();
            while ($r2.hasNext()) {
                zzqg $r4 = (zzqg) $r2.next();
                if ($r4.CY == $i0 && $r4.bG != 1 && $r4.cancel()) {
                    $r2.remove();
                }
            }
            ((zzsb) this.Fx.get($i0)).release();
            this.Fz.delete($i0);
            if (!$z0) {
                this.Fx.remove($i0);
                this.Fs.Fq.remove($i0);
                if (this.Fx.size() == 0) {
                    this = this;
                    if (this.Fu.isEmpty()) {
                        zzaub();
                        this.Fv.disconnect();
                        this.Fs.Fm.remove(this.Cq);
                        synchronized (zzra.zzanj) {
                            this.Fs.Fo.remove(this.Cq);
                        }
                    }
                }
            }
        }

        @WorkerThread
        public void zzhx(int $i0) throws  {
            this.Fx.put($i0, new zzsb(this.Cq.zzarl(), this.Fv));
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    private class zzd implements zzf {
        private final zzqh<?> Cq;
        final /* synthetic */ zzra Fs;
        private final zze Fv;

        public zzd(@Signature({"(", "Lcom/google/android/gms/common/api/Api$zze;", "Lcom/google/android/gms/internal/zzqh", "<*>;)V"}) zzra $r1, @Signature({"(", "Lcom/google/android/gms/common/api/Api$zze;", "Lcom/google/android/gms/internal/zzqh", "<*>;)V"}) zze $r2, @Signature({"(", "Lcom/google/android/gms/common/api/Api$zze;", "Lcom/google/android/gms/internal/zzqh", "<*>;)V"}) zzqh<?> $r3) throws  {
            this.Fs = $r1;
            this.Fv = $r2;
            this.Cq = $r3;
        }

        @WorkerThread
        public void zzh(@NonNull ConnectionResult $r1) throws  {
            if ($r1.isSuccess()) {
                this.Fv.zza(null, Collections.emptySet());
            } else {
                ((zzc) this.Fs.Fm.get(this.Cq)).onConnectionFailed($r1);
            }
        }
    }

    private zzra(Context $r1) throws  {
        this($r1, GoogleApiAvailability.getInstance());
    }

    private zzra(Context $r1, GoogleApiAvailability $r2) throws  {
        zzra com_google_android_gms_internal_zzra = this;
        this.EH = 5000;
        this.EG = 120000;
        this.Fh = 10000;
        this.Fj = -1;
        this.Fk = new AtomicInteger(1);
        this.Fl = new SparseArray();
        this.Fm = new ConcurrentHashMap(5, 0.75f, 1);
        this.Fn = null;
        this.Fo = new com.google.android.gms.common.util.zza();
        this.Fp = new ReferenceQueue();
        this.Fq = new SparseArray();
        this.mContext = $r1;
        HandlerThread $r9 = new HandlerThread("GoogleApiHandler", 9);
        $r9.start();
        this.mHandler = new Handler($r9.getLooper(), this);
        this.CH = $r2;
    }

    @MainThread
    public static Pair<zzra, Integer> zza(@Signature({"(", "Landroid/app/Activity;", "Lcom/google/android/gms/common/api/zzc", "<*>;)", "Landroid/util/Pair", "<", "Lcom/google/android/gms/internal/zzra;", "Ljava/lang/Integer;", ">;"}) Activity $r0, @Signature({"(", "Landroid/app/Activity;", "Lcom/google/android/gms/common/api/zzc", "<*>;)", "Landroid/util/Pair", "<", "Lcom/google/android/gms/internal/zzra;", "Ljava/lang/Integer;", ">;"}) com.google.android.gms.common.api.zzc<?> $r1) throws  {
        Pair $r5;
        synchronized (zzanj) {
            $r5 = zza($r0.getApplicationContext(), (com.google.android.gms.common.api.zzc) $r1);
            Fi.zza(zzqp.zza($r0, Fi), (com.google.android.gms.common.api.zzc) $r1);
        }
        return $r5;
    }

    public static Pair<zzra, Integer> zza(@Signature({"(", "Landroid/content/Context;", "Lcom/google/android/gms/common/api/zzc", "<*>;)", "Landroid/util/Pair", "<", "Lcom/google/android/gms/internal/zzra;", "Ljava/lang/Integer;", ">;"}) Context $r0, @Signature({"(", "Landroid/content/Context;", "Lcom/google/android/gms/common/api/zzc", "<*>;)", "Landroid/util/Pair", "<", "Lcom/google/android/gms/internal/zzra;", "Ljava/lang/Integer;", ">;"}) com.google.android.gms.common.api.zzc<?> $r1) throws  {
        Pair $r5;
        synchronized (zzanj) {
            if (Fi == null) {
                Fi = new zzra($r0.getApplicationContext());
            }
            $r5 = Pair.create(Fi, Integer.valueOf(Fi.zzb((com.google.android.gms.common.api.zzc) $r1)));
        }
        return $r5;
    }

    @WorkerThread
    private void zza(@Signature({"(", "Lcom/google/android/gms/common/api/zzc", "<*>;I)V"}) com.google.android.gms.common.api.zzc<?> $r1, @Signature({"(", "Lcom/google/android/gms/common/api/zzc", "<*>;I)V"}) int $i0) throws  {
        zzqh $r2 = $r1.zzaru();
        if (!this.Fm.containsKey($r2)) {
            this.Fm.put($r2, new zzc(this, $r1));
        }
        zzc $r4 = (zzc) this.Fm.get($r2);
        $r4.zzhx($i0);
        this.Fl.put($i0, $r4);
        $r4.connect();
        this.Fq.put($i0, new zza(this, $r1, $i0, this.Fp));
        if (this.Fr == null || !this.Fr.Ft.get()) {
            this.Fr = new zzb(this.Fp, this.Fq);
            this.Fr.start();
        }
    }

    @WorkerThread
    private void zza(zzqg $r1) throws  {
        ((zzc) this.Fl.get($r1.CY)).zzb($r1);
    }

    @MainThread
    private void zza(@Signature({"(", "Lcom/google/android/gms/internal/zzqp;", "Lcom/google/android/gms/common/api/zzc", "<*>;)V"}) zzqp $r1, @Signature({"(", "Lcom/google/android/gms/internal/zzqp;", "Lcom/google/android/gms/common/api/zzc", "<*>;)V"}) com.google.android.gms.common.api.zzc<?> $r2) throws  {
        if (this.Fn != $r1) {
            this.Fo.clear();
            this.Fn = $r1;
        }
        this.Fo.add($r2.zzaru());
        $r1.zza($r2);
    }

    public static zzra zzatu() throws  {
        zzra r2;
        synchronized (zzanj) {
            r2 = Fi;
        }
        return r2;
    }

    @WorkerThread
    private void zzatv() throws  {
        for (zzc $r5 : this.Fm.values()) {
            $r5.zzatz();
            $r5.connect();
        }
    }

    private int zzb(@Signature({"(", "Lcom/google/android/gms/common/api/zzc", "<*>;)I"}) com.google.android.gms.common.api.zzc<?> $r1) throws  {
        int $i0 = this.Fk.getAndIncrement();
        this.mHandler.sendMessage(this.mHandler.obtainMessage(6, $i0, 0, $r1));
        return $i0;
    }

    @WorkerThread
    private void zzg(int $i0, boolean $z0) throws  {
        zzc $r3 = (zzc) this.Fl.get($i0);
        if ($r3 != null) {
            if (!$z0) {
                this.Fl.delete($i0);
            }
            $r3.zzh($i0, $z0);
            return;
        }
        Log.wtf("GoogleApiManager", "onRelease received for unknown instance: " + $i0, new Exception());
    }

    @WorkerThread
    public boolean handleMessage(Message $r1) throws  {
        boolean $z0 = false;
        switch ($r1.what) {
            case 1:
                zza((zzqj) $r1.obj);
                break;
            case 2:
            case 7:
                int $i0 = $r1.arg1;
                if ($r1.arg2 == 1) {
                    $z0 = true;
                }
                zzg($i0, $z0);
                break;
            case 3:
                zzatv();
                break;
            case 4:
                zza((zzqg) $r1.obj);
                break;
            case 5:
                if (this.Fl.get($r1.arg1) != null) {
                    zzc $r9 = (zzc) this.Fl.get($r1.arg1);
                    $r9.zzai(new Status(17, "Error resolution was canceled by the user."));
                    break;
                }
                break;
            case 6:
                zza((com.google.android.gms.common.api.zzc) $r1.obj, $r1.arg1);
                break;
            case 8:
                if (this.Fm.containsKey($r1.obj)) {
                    ((zzc) this.Fm.get($r1.obj)).resume();
                    break;
                }
                break;
            case 9:
                if (this.Fm.containsKey($r1.obj)) {
                    ((zzc) this.Fm.get($r1.obj)).zzatl();
                    break;
                }
                break;
            case 10:
                if (this.Fm.containsKey($r1.obj)) {
                    ((zzc) this.Fm.get($r1.obj)).zzaud();
                    break;
                }
                break;
            default:
                String str = "GoogleApiManager";
                Log.w(str, "Unknown message id: " + $r1.what);
                return false;
        }
        return true;
    }

    public void zza(ConnectionResult $r1, int $i0) throws  {
        if (!zzc($r1, $i0)) {
            this.mHandler.sendMessage(this.mHandler.obtainMessage(5, $i0, 0));
        }
    }

    public <O extends ApiOptions> void zza(@Signature({"<O::", "Lcom/google/android/gms/common/api/Api$ApiOptions;", ">(", "Lcom/google/android/gms/common/api/zzc", "<TO;>;I", "Lcom/google/android/gms/internal/zzqk$zza", "<+", "Lcom/google/android/gms/common/api/Result;", "Lcom/google/android/gms/common/api/Api$zzb;", ">;)V"}) com.google.android.gms.common.api.zzc<O> $r1, @Signature({"<O::", "Lcom/google/android/gms/common/api/Api$ApiOptions;", ">(", "Lcom/google/android/gms/common/api/zzc", "<TO;>;I", "Lcom/google/android/gms/internal/zzqk$zza", "<+", "Lcom/google/android/gms/common/api/Result;", "Lcom/google/android/gms/common/api/Api$zzb;", ">;)V"}) int $i0, @Signature({"<O::", "Lcom/google/android/gms/common/api/Api$ApiOptions;", ">(", "Lcom/google/android/gms/common/api/zzc", "<TO;>;I", "Lcom/google/android/gms/internal/zzqk$zza", "<+", "Lcom/google/android/gms/common/api/Result;", "Lcom/google/android/gms/common/api/Api$zzb;", ">;)V"}) com.google.android.gms.internal.zzqk.zza<? extends Result, com.google.android.gms.common.api.Api.zzb> $r2) throws  {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(4, new com.google.android.gms.internal.zzqg.zza($r1.getInstanceId(), $i0, $r2)));
    }

    public <O extends ApiOptions, TResult> void zza(@Signature({"<O::", "Lcom/google/android/gms/common/api/Api$ApiOptions;", "TResult:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/common/api/zzc", "<TO;>;I", "Lcom/google/android/gms/internal/zzrz", "<", "Lcom/google/android/gms/common/api/Api$zzb;", "TTResult;>;", "Lcom/google/android/gms/tasks/TaskCompletionSource", "<TTResult;>;)V"}) com.google.android.gms.common.api.zzc<O> $r1, @Signature({"<O::", "Lcom/google/android/gms/common/api/Api$ApiOptions;", "TResult:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/common/api/zzc", "<TO;>;I", "Lcom/google/android/gms/internal/zzrz", "<", "Lcom/google/android/gms/common/api/Api$zzb;", "TTResult;>;", "Lcom/google/android/gms/tasks/TaskCompletionSource", "<TTResult;>;)V"}) int $i0, @Signature({"<O::", "Lcom/google/android/gms/common/api/Api$ApiOptions;", "TResult:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/common/api/zzc", "<TO;>;I", "Lcom/google/android/gms/internal/zzrz", "<", "Lcom/google/android/gms/common/api/Api$zzb;", "TTResult;>;", "Lcom/google/android/gms/tasks/TaskCompletionSource", "<TTResult;>;)V"}) zzrz<com.google.android.gms.common.api.Api.zzb, TResult> $r2, @Signature({"<O::", "Lcom/google/android/gms/common/api/Api$ApiOptions;", "TResult:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/common/api/zzc", "<TO;>;I", "Lcom/google/android/gms/internal/zzrz", "<", "Lcom/google/android/gms/common/api/Api$zzb;", "TTResult;>;", "Lcom/google/android/gms/tasks/TaskCompletionSource", "<TTResult;>;)V"}) TaskCompletionSource<TResult> $r3) throws  {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(4, new com.google.android.gms.internal.zzqg.zzb($r1.getInstanceId(), $i0, $r2, $r3)));
    }

    @WorkerThread
    public void zza(zzqj $r1) throws  {
        for (zzqh $r5 : $r1.zzash()) {
            zzc $r7 = (zzc) this.Fm.get($r5);
            if ($r7 == null) {
                $r1.cancel();
                return;
            } else if ($r7.isConnected()) {
                $r1.zza($r5, ConnectionResult.BB);
            } else if ($r7.zzaua() != null) {
                $r1.zza($r5, $r7.zzaua());
            } else {
                $r7.zzb($r1);
            }
        }
    }

    public void zza(zzqp $r1) throws  {
        synchronized (zzanj) {
            if ($r1 == this.Fn) {
                this.Fn = null;
                this.Fo.clear();
            }
        }
    }

    public void zzasf() throws  {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(3));
    }

    boolean zzc(ConnectionResult $r1, int $i0) throws  {
        if (!$r1.hasResolution() && !this.CH.isUserResolvableError($r1.getErrorCode())) {
            return false;
        }
        this.CH.zza(this.mContext, $r1, $i0);
        return true;
    }

    public void zzf(int $i0, boolean $z0) throws  {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(7, $i0, $z0 ? (byte) 1 : (byte) 2));
    }
}
