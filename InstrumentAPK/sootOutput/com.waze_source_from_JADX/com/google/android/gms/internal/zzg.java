package com.google.android.gms.internal;

import android.annotation.TargetApi;
import android.net.TrafficStats;
import android.os.Build.VERSION;
import dalvik.annotation.Signature;
import java.util.concurrent.BlockingQueue;

/* compiled from: dalvik_source_com.waze.apk */
public class zzg extends Thread {
    private final zzb zzi;
    private final zzn zzj;
    private volatile boolean zzk = false;
    private final BlockingQueue<zzk<?>> zzx;
    private final zzf zzy;

    public zzg(@Signature({"(", "Ljava/util/concurrent/BlockingQueue", "<", "Lcom/google/android/gms/internal/zzk", "<*>;>;", "Lcom/google/android/gms/internal/zzf;", "Lcom/google/android/gms/internal/zzb;", "Lcom/google/android/gms/internal/zzn;", ")V"}) BlockingQueue<zzk<?>> $r1, @Signature({"(", "Ljava/util/concurrent/BlockingQueue", "<", "Lcom/google/android/gms/internal/zzk", "<*>;>;", "Lcom/google/android/gms/internal/zzf;", "Lcom/google/android/gms/internal/zzb;", "Lcom/google/android/gms/internal/zzn;", ")V"}) zzf $r2, @Signature({"(", "Ljava/util/concurrent/BlockingQueue", "<", "Lcom/google/android/gms/internal/zzk", "<*>;>;", "Lcom/google/android/gms/internal/zzf;", "Lcom/google/android/gms/internal/zzb;", "Lcom/google/android/gms/internal/zzn;", ")V"}) zzb $r3, @Signature({"(", "Ljava/util/concurrent/BlockingQueue", "<", "Lcom/google/android/gms/internal/zzk", "<*>;>;", "Lcom/google/android/gms/internal/zzf;", "Lcom/google/android/gms/internal/zzb;", "Lcom/google/android/gms/internal/zzn;", ")V"}) zzn $r4) throws  {
        super("VolleyNetworkDispatcher");
        this.zzx = $r1;
        this.zzy = $r2;
        this.zzi = $r3;
        this.zzj = $r4;
    }

    @TargetApi(14)
    private void zzb(@Signature({"(", "Lcom/google/android/gms/internal/zzk", "<*>;)V"}) zzk<?> $r1) throws  {
        if (VERSION.SDK_INT >= 14) {
            TrafficStats.setThreadStatsTag($r1.zzf());
        }
    }

    private void zzb(@Signature({"(", "Lcom/google/android/gms/internal/zzk", "<*>;", "Lcom/google/android/gms/internal/zzr;", ")V"}) zzk<?> $r1, @Signature({"(", "Lcom/google/android/gms/internal/zzk", "<*>;", "Lcom/google/android/gms/internal/zzr;", ")V"}) zzr $r2) throws  {
        this.zzj.zza((zzk) $r1, $r1.zzb($r2));
    }

    public void quit() throws  {
        this.zzk = true;
        interrupt();
    }

    public void run() throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x009e in list []
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:42)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:60)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r25 = this;
        r3 = 10;
        android.os.Process.setThreadPriority(r3);
    L_0x0005:
        r4 = android.os.SystemClock.elapsedRealtime();
        r0 = r25;
        r6 = r0.zzx;
        r7 = r6.take();	 Catch:{ InterruptedException -> 0x0036 }
        r9 = r7;
        r9 = (com.google.android.gms.internal.zzk) r9;
        r8 = r9;
        r10 = "network-queue-take";	 Catch:{ zzr -> 0x0026, Exception -> 0x0068 }
        r8.zzc(r10);	 Catch:{ zzr -> 0x0026, Exception -> 0x0068 }
        r11 = r8.isCanceled();	 Catch:{ zzr -> 0x0026, Exception -> 0x0068 }
        if (r11 == 0) goto L_0x003e;	 Catch:{ zzr -> 0x0026, Exception -> 0x0068 }
    L_0x0020:
        r10 = "network-discard-cancelled";	 Catch:{ zzr -> 0x0026, Exception -> 0x0068 }
        r8.zzd(r10);	 Catch:{ zzr -> 0x0026, Exception -> 0x0068 }
        goto L_0x0005;
    L_0x0026:
        r12 = move-exception;
        r13 = android.os.SystemClock.elapsedRealtime();
        r4 = r13 - r4;
        r12.zza(r4);
        r0 = r25;
        r0.zzb(r8, r12);
        goto L_0x0005;
    L_0x0036:
        r15 = move-exception;
        r0 = r25;
        r11 = r0.zzk;
        if (r11 == 0) goto L_0x0005;
    L_0x003d:
        return;
    L_0x003e:
        r0 = r25;	 Catch:{ zzr -> 0x0026, Exception -> 0x0068 }
        r0.zzb(r8);	 Catch:{ zzr -> 0x0026, Exception -> 0x0068 }
        r0 = r25;	 Catch:{ zzr -> 0x0026, Exception -> 0x0068 }
        r0 = r0.zzy;	 Catch:{ zzr -> 0x0026, Exception -> 0x0068 }
        r16 = r0;	 Catch:{ zzr -> 0x0026, Exception -> 0x0068 }
        goto L_0x004d;	 Catch:{ zzr -> 0x0026, Exception -> 0x0068 }
    L_0x004a:
        goto L_0x0005;	 Catch:{ zzr -> 0x0026, Exception -> 0x0068 }
    L_0x004d:
        r17 = r0.zza(r8);	 Catch:{ zzr -> 0x0026, Exception -> 0x0068 }
        r10 = "network-http-complete";	 Catch:{ zzr -> 0x0026, Exception -> 0x0068 }
        r8.zzc(r10);	 Catch:{ zzr -> 0x0026, Exception -> 0x0068 }
        r0 = r17;
        r11 = r0.zzz;
        if (r11 == 0) goto L_0x009e;	 Catch:{ zzr -> 0x0026, Exception -> 0x0068 }
    L_0x005c:
        r11 = r8.zzv();	 Catch:{ zzr -> 0x0026, Exception -> 0x0068 }
        if (r11 == 0) goto L_0x009e;	 Catch:{ zzr -> 0x0026, Exception -> 0x0068 }
    L_0x0062:
        r10 = "not-modified";	 Catch:{ zzr -> 0x0026, Exception -> 0x0068 }
        r8.zzd(r10);	 Catch:{ zzr -> 0x0026, Exception -> 0x0068 }
        goto L_0x0005;
    L_0x0068:
        r18 = move-exception;
        goto L_0x006d;
    L_0x006a:
        goto L_0x0005;
    L_0x006d:
        r3 = 1;
        r0 = new java.lang.Object[r3];
        r19 = r0;
        r0 = r18;
        r20 = r0.toString();
        r3 = 0;
        r19[r3] = r20;
        r10 = "Unhandled exception %s";
        r0 = r18;
        r1 = r19;
        com.google.android.gms.internal.zzs.zza(r0, r10, r1);
        r12 = new com.google.android.gms.internal.zzr;
        r0 = r18;
        r12.<init>(r0);
        r13 = android.os.SystemClock.elapsedRealtime();
        r4 = r13 - r4;
        r12.zza(r4);
        r0 = r25;
        r0 = r0.zzj;
        r21 = r0;
        r0.zza(r8, r12);
        goto L_0x004a;
    L_0x009e:
        r0 = r17;	 Catch:{ zzr -> 0x0026, Exception -> 0x0068 }
        r22 = r8.zza(r0);	 Catch:{ zzr -> 0x0026, Exception -> 0x0068 }
        r10 = "network-parse-complete";	 Catch:{ zzr -> 0x0026, Exception -> 0x0068 }
        r8.zzc(r10);	 Catch:{ zzr -> 0x0026, Exception -> 0x0068 }
        r11 = r8.zzq();	 Catch:{ zzr -> 0x0026, Exception -> 0x0068 }
        if (r11 == 0) goto L_0x00d5;	 Catch:{ zzr -> 0x0026, Exception -> 0x0068 }
    L_0x00af:
        r0 = r22;
        r0 = r0.zzbe;
        r23 = r0;
        if (r23 == 0) goto L_0x00d5;	 Catch:{ zzr -> 0x0026, Exception -> 0x0068 }
    L_0x00b7:
        r0 = r25;	 Catch:{ zzr -> 0x0026, Exception -> 0x0068 }
        r0 = r0.zzi;	 Catch:{ zzr -> 0x0026, Exception -> 0x0068 }
        r24 = r0;	 Catch:{ zzr -> 0x0026, Exception -> 0x0068 }
        r20 = r8.zzg();	 Catch:{ zzr -> 0x0026, Exception -> 0x0068 }
        r0 = r22;	 Catch:{ zzr -> 0x0026, Exception -> 0x0068 }
        r0 = r0.zzbe;	 Catch:{ zzr -> 0x0026, Exception -> 0x0068 }
        r23 = r0;	 Catch:{ zzr -> 0x0026, Exception -> 0x0068 }
        r0 = r24;	 Catch:{ zzr -> 0x0026, Exception -> 0x0068 }
        r1 = r20;	 Catch:{ zzr -> 0x0026, Exception -> 0x0068 }
        r2 = r23;	 Catch:{ zzr -> 0x0026, Exception -> 0x0068 }
        r0.zza(r1, r2);	 Catch:{ zzr -> 0x0026, Exception -> 0x0068 }
        r10 = "network-cache-written";	 Catch:{ zzr -> 0x0026, Exception -> 0x0068 }
        r8.zzc(r10);	 Catch:{ zzr -> 0x0026, Exception -> 0x0068 }
    L_0x00d5:
        r8.zzu();	 Catch:{ zzr -> 0x0026, Exception -> 0x0068 }
        r0 = r25;	 Catch:{ zzr -> 0x0026, Exception -> 0x0068 }
        r0 = r0.zzj;	 Catch:{ zzr -> 0x0026, Exception -> 0x0068 }
        r21 = r0;	 Catch:{ zzr -> 0x0026, Exception -> 0x0068 }
        r1 = r22;	 Catch:{ zzr -> 0x0026, Exception -> 0x0068 }
        r0.zza(r8, r1);	 Catch:{ zzr -> 0x0026, Exception -> 0x0068 }
        goto L_0x006a;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzg.run():void");
    }
}
