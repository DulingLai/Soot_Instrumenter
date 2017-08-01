package com.google.android.gms.internal;

import dalvik.annotation.Signature;
import java.util.concurrent.BlockingQueue;

/* compiled from: dalvik_source_com.waze.apk */
public class zzc extends Thread {
    private static final boolean DEBUG = zzs.DEBUG;
    private final BlockingQueue<zzk<?>> zzg;
    private final BlockingQueue<zzk<?>> zzh;
    private final zzb zzi;
    private final zzn zzj;
    private volatile boolean zzk = false;

    /* compiled from: dalvik_source_com.waze.apk */
    class C08171 implements Runnable {
        final /* synthetic */ zzk zzl;
        final /* synthetic */ zzc zzm;

        C08171(zzc $r1, zzk $r2) throws  {
            this.zzm = $r1;
            this.zzl = $r2;
        }

        public void run() throws  {
            try {
                this.zzm.zzh.put(this.zzl);
            } catch (InterruptedException e) {
            }
        }
    }

    public zzc(@Signature({"(", "Ljava/util/concurrent/BlockingQueue", "<", "Lcom/google/android/gms/internal/zzk", "<*>;>;", "Ljava/util/concurrent/BlockingQueue", "<", "Lcom/google/android/gms/internal/zzk", "<*>;>;", "Lcom/google/android/gms/internal/zzb;", "Lcom/google/android/gms/internal/zzn;", ")V"}) BlockingQueue<zzk<?>> $r1, @Signature({"(", "Ljava/util/concurrent/BlockingQueue", "<", "Lcom/google/android/gms/internal/zzk", "<*>;>;", "Ljava/util/concurrent/BlockingQueue", "<", "Lcom/google/android/gms/internal/zzk", "<*>;>;", "Lcom/google/android/gms/internal/zzb;", "Lcom/google/android/gms/internal/zzn;", ")V"}) BlockingQueue<zzk<?>> $r2, @Signature({"(", "Ljava/util/concurrent/BlockingQueue", "<", "Lcom/google/android/gms/internal/zzk", "<*>;>;", "Ljava/util/concurrent/BlockingQueue", "<", "Lcom/google/android/gms/internal/zzk", "<*>;>;", "Lcom/google/android/gms/internal/zzb;", "Lcom/google/android/gms/internal/zzn;", ")V"}) zzb $r3, @Signature({"(", "Ljava/util/concurrent/BlockingQueue", "<", "Lcom/google/android/gms/internal/zzk", "<*>;>;", "Ljava/util/concurrent/BlockingQueue", "<", "Lcom/google/android/gms/internal/zzk", "<*>;>;", "Lcom/google/android/gms/internal/zzb;", "Lcom/google/android/gms/internal/zzn;", ")V"}) zzn $r4) throws  {
        super("VolleyCacheDispatcher");
        this.zzg = $r1;
        this.zzh = $r2;
        this.zzi = $r3;
        this.zzj = $r4;
    }

    public void quit() throws  {
        this.zzk = true;
        interrupt();
    }

    public void run() throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:20:0x0059 in {2, 9, 13, 19, 21, 26, 28} preds:[]
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.computeDominators(BlockProcessor.java:129)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.visit(BlockProcessor.java:38)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r21 = this;
        r3 = DEBUG;
        if (r3 == 0) goto L_0x000d;
    L_0x0004:
        r5 = 0;
        r4 = new java.lang.Object[r5];
        r6 = "start new dispatcher";
        com.google.android.gms.internal.zzs.zza(r6, r4);
    L_0x000d:
        r5 = 10;
        android.os.Process.setThreadPriority(r5);
        r0 = r21;
        r7 = r0.zzi;
        r7.initialize();
    L_0x0019:
        r0 = r21;
        r8 = r0.zzg;
        r9 = r8.take();	 Catch:{ InterruptedException -> 0x0036 }
        r11 = r9;	 Catch:{ InterruptedException -> 0x0036 }
        r11 = (com.google.android.gms.internal.zzk) r11;	 Catch:{ InterruptedException -> 0x0036 }
        r10 = r11;	 Catch:{ InterruptedException -> 0x0036 }
        r6 = "cache-queue-take";	 Catch:{ InterruptedException -> 0x0036 }
        r10.zzc(r6);	 Catch:{ InterruptedException -> 0x0036 }
        r3 = r10.isCanceled();	 Catch:{ InterruptedException -> 0x0036 }
        if (r3 == 0) goto L_0x003e;	 Catch:{ InterruptedException -> 0x0036 }
    L_0x0030:
        r6 = "cache-discard-canceled";	 Catch:{ InterruptedException -> 0x0036 }
        r10.zzd(r6);	 Catch:{ InterruptedException -> 0x0036 }
        goto L_0x0019;
    L_0x0036:
        r12 = move-exception;
        r0 = r21;
        r3 = r0.zzk;
        if (r3 == 0) goto L_0x0019;
    L_0x003d:
        return;
    L_0x003e:
        r0 = r21;
        r7 = r0.zzi;
        r13 = r10.zzg();	 Catch:{ InterruptedException -> 0x0036 }
        r14 = r7.zza(r13);	 Catch:{ InterruptedException -> 0x0036 }
        if (r14 != 0) goto L_0x005d;	 Catch:{ InterruptedException -> 0x0036 }
    L_0x004c:
        r6 = "cache-miss";	 Catch:{ InterruptedException -> 0x0036 }
        r10.zzc(r6);	 Catch:{ InterruptedException -> 0x0036 }
        r0 = r21;	 Catch:{ InterruptedException -> 0x0036 }
        r8 = r0.zzh;	 Catch:{ InterruptedException -> 0x0036 }
        r8.put(r10);	 Catch:{ InterruptedException -> 0x0036 }
        goto L_0x0019;
        goto L_0x005d;
    L_0x005a:
        goto L_0x0019;
    L_0x005d:
        r3 = r14.zza();	 Catch:{ InterruptedException -> 0x0036 }
        if (r3 == 0) goto L_0x0077;	 Catch:{ InterruptedException -> 0x0036 }
    L_0x0063:
        r6 = "cache-hit-expired";	 Catch:{ InterruptedException -> 0x0036 }
        r10.zzc(r6);	 Catch:{ InterruptedException -> 0x0036 }
        r10.zza(r14);	 Catch:{ InterruptedException -> 0x0036 }
        r0 = r21;	 Catch:{ InterruptedException -> 0x0036 }
        r8 = r0.zzh;	 Catch:{ InterruptedException -> 0x0036 }
        goto L_0x0073;	 Catch:{ InterruptedException -> 0x0036 }
    L_0x0070:
        goto L_0x0019;	 Catch:{ InterruptedException -> 0x0036 }
    L_0x0073:
        r8.put(r10);	 Catch:{ InterruptedException -> 0x0036 }
        goto L_0x0019;
    L_0x0077:
        r6 = "cache-hit";	 Catch:{ InterruptedException -> 0x0036 }
        r10.zzc(r6);	 Catch:{ InterruptedException -> 0x0036 }
        r15 = new com.google.android.gms.internal.zzi;
        r0 = r14.data;
        r16 = r0;
        r0 = r14.zzf;	 Catch:{ InterruptedException -> 0x0036 }
        r17 = r0;	 Catch:{ InterruptedException -> 0x0036 }
        r0 = r16;	 Catch:{ InterruptedException -> 0x0036 }
        r1 = r17;	 Catch:{ InterruptedException -> 0x0036 }
        r15.<init>(r0, r1);	 Catch:{ InterruptedException -> 0x0036 }
        r18 = r10.zza(r15);	 Catch:{ InterruptedException -> 0x0036 }
        r6 = "cache-hit-parsed";	 Catch:{ InterruptedException -> 0x0036 }
        r10.zzc(r6);	 Catch:{ InterruptedException -> 0x0036 }
        r3 = r14.zzb();	 Catch:{ InterruptedException -> 0x0036 }
        if (r3 != 0) goto L_0x00a8;	 Catch:{ InterruptedException -> 0x0036 }
    L_0x009c:
        r0 = r21;	 Catch:{ InterruptedException -> 0x0036 }
        r0 = r0.zzj;	 Catch:{ InterruptedException -> 0x0036 }
        r19 = r0;	 Catch:{ InterruptedException -> 0x0036 }
        r1 = r18;	 Catch:{ InterruptedException -> 0x0036 }
        r0.zza(r10, r1);	 Catch:{ InterruptedException -> 0x0036 }
        goto L_0x005a;
    L_0x00a8:
        r6 = "cache-hit-refresh-needed";	 Catch:{ InterruptedException -> 0x0036 }
        r10.zzc(r6);	 Catch:{ InterruptedException -> 0x0036 }
        r10.zza(r14);	 Catch:{ InterruptedException -> 0x0036 }
        r5 = 1;
        r0 = r18;
        r0.zzbg = r5;
        r0 = r21;
        r0 = r0.zzj;
        r19 = r0;
        r20 = new com.google.android.gms.internal.zzc$1;	 Catch:{ InterruptedException -> 0x0036 }
        r0 = r20;	 Catch:{ InterruptedException -> 0x0036 }
        r1 = r21;	 Catch:{ InterruptedException -> 0x0036 }
        r0.<init>(r1, r10);	 Catch:{ InterruptedException -> 0x0036 }
        r0 = r19;	 Catch:{ InterruptedException -> 0x0036 }
        r1 = r18;	 Catch:{ InterruptedException -> 0x0036 }
        r2 = r20;	 Catch:{ InterruptedException -> 0x0036 }
        r0.zza(r10, r1, r2);	 Catch:{ InterruptedException -> 0x0036 }
        goto L_0x0070;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzc.run():void");
    }
}
