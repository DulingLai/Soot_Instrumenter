package com.google.android.gms.internal;

import android.content.Context;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import com.google.android.gms.ads.internal.config.Flags;
import com.google.android.gms.internal.zzae.zza;
import dalvik.annotation.Signature;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/* compiled from: dalvik_source_com.waze.apk */
public abstract class zzaq extends zzao {
    private static final String TAG = zzaq.class.getSimpleName();
    private static long startTime = 0;
    protected static volatile zzax zzaey = null;
    private static Method zzafo;
    static boolean zzafq = false;
    protected static final Object zzaft = new Object();
    protected boolean zzafn = false;
    protected String zzafp;
    protected boolean zzafr = false;
    protected boolean zzafs = false;

    protected zzaq(Context $r1, String $r2) throws  {
        super($r1);
        this.zzafp = $r2;
        this.zzafn = false;
    }

    protected zzaq(Context $r1, String $r2, boolean $z0) throws  {
        super($r1);
        this.zzafp = $r2;
        this.zzafn = $z0;
    }

    static List<Long> zza(@Signature({"(", "Lcom/google/android/gms/internal/zzax;", "Landroid/view/MotionEvent;", "Landroid/util/DisplayMetrics;", ")", "Ljava/util/List", "<", "Ljava/lang/Long;", ">;"}) zzax $r0, @Signature({"(", "Lcom/google/android/gms/internal/zzax;", "Landroid/view/MotionEvent;", "Landroid/util/DisplayMetrics;", ")", "Ljava/util/List", "<", "Ljava/lang/Long;", ">;"}) MotionEvent $r1, @Signature({"(", "Lcom/google/android/gms/internal/zzax;", "Landroid/view/MotionEvent;", "Landroid/util/DisplayMetrics;", ")", "Ljava/util/List", "<", "Ljava/lang/Long;", ">;"}) DisplayMetrics $r2) throws zzaw {
        zzafo = $r0.zzc(zzav.zzcb(), zzav.zzcc());
        if (zzafo == null || $r1 == null) {
            throw new zzaw();
        }
        try {
            return (ArrayList) zzafo.invoke(null, new Object[]{$r1, $r2});
        } catch (IllegalAccessException $r10) {
            throw new zzaw($r10);
        } catch (InvocationTargetException $r11) {
            throw new zzaw($r11);
        }
    }

    protected static synchronized void zza(Context $r0, boolean $z0) throws  {
        synchronized (zzaq.class) {
            try {
                if (!zzafq) {
                    startTime = Calendar.getInstance().getTime().getTime() / 1000;
                    zzaey = zzb($r0, $z0);
                    zzafq = true;
                }
            } catch (Throwable th) {
                Class cls = zzaq.class;
            }
        }
    }

    private static void zza(zzax $r0) throws  {
        List $r1 = Collections.singletonList(Context.class);
        $r0.zza(zzav.zzbn(), zzav.zzbo(), $r1);
        $r0.zza(zzav.zzbl(), zzav.zzbm(), $r1);
        $r0.zza(zzav.zzbx(), zzav.zzby(), $r1);
        $r0.zza(zzav.zzbv(), zzav.zzbw(), $r1);
        $r0.zza(zzav.zzbf(), zzav.zzbg(), $r1);
        $r0.zza(zzav.zzbd(), zzav.zzbe(), $r1);
        $r0.zza(zzav.zzbb(), zzav.zzbc(), $r1);
        $r0.zza(zzav.zzbr(), zzav.zzbs(), $r1);
        $r0.zza(zzav.zzaz(), zzav.zzba(), $r1);
        $r0.zza(zzav.zzcb(), zzav.zzcc(), Arrays.asList(new Class[]{MotionEvent.class, DisplayMetrics.class}));
        $r0.zza(zzav.zzbj(), zzav.zzbk(), Collections.emptyList());
        $r0.zza(zzav.zzbz(), zzav.zzca(), Collections.emptyList());
        $r0.zza(zzav.zzbt(), zzav.zzbu(), Collections.emptyList());
        $r0.zza(zzav.zzbh(), zzav.zzbi(), Collections.emptyList());
        $r0.zza(zzav.zzbp(), zzav.zzbq(), Collections.emptyList());
    }

    protected static zzax zzb(Context $r0, boolean $z0) throws  {
        if (zzaey == null) {
            synchronized (zzaft) {
                if (zzaey == null) {
                    zzax $r2 = zzax.zza($r0, zzav.getKey(), zzav.zzay(), $z0);
                    zza($r2);
                    zzaey = $r2;
                }
            }
        }
        return zzaey;
    }

    protected void zza(zzax $r1, zza $r2) throws  {
        if ($r1.zzcd() != null) {
            zza(zzb($r1, $r2));
        }
    }

    protected void zza(@Signature({"(", "Ljava/util/List", "<", "Ljava/util/concurrent/Callable", "<", "Ljava/lang/Void;", ">;>;)V"}) List<Callable<Void>> $r1) throws  {
        if (zzaey != null) {
            ExecutorService $r3 = zzaey.zzcd();
            if ($r3 != null && !$r1.isEmpty()) {
                try {
                    $r3.invokeAll($r1, ((Long) Flags.zzbbu.get()).longValue(), TimeUnit.MILLISECONDS);
                } catch (InterruptedException $r8) {
                    Log.d(TAG, String.format("class methods got exception: %s", new Object[]{zzay.zza($r8)}));
                }
            }
        }
    }

    protected List<Callable<Void>> zzb(@Signature({"(", "Lcom/google/android/gms/internal/zzax;", "Lcom/google/android/gms/internal/zzae$zza;", ")", "Ljava/util/List", "<", "Ljava/util/concurrent/Callable", "<", "Ljava/lang/Void;", ">;>;"}) zzax $r1, @Signature({"(", "Lcom/google/android/gms/internal/zzax;", "Lcom/google/android/gms/internal/zzae$zza;", ")", "Ljava/util/List", "<", "Ljava/util/concurrent/Callable", "<", "Ljava/lang/Void;", ">;>;"}) zza $r2) throws  {
        int $i1 = $r1.zzat();
        ArrayList $r4 = new ArrayList();
        $r4.add(new zzbb($r1, zzav.zzbn(), zzav.zzbo(), $r2, $i1, 27));
        $r4.add(new zzbg($r1, zzav.zzbj(), zzav.zzbk(), $r2, startTime, $i1, 25));
        $r4.add(new zzbl($r1, zzav.zzbt(), zzav.zzbu(), $r2, $i1, 1));
        $r4.add(new zzbm($r1, zzav.zzbv(), zzav.zzbw(), $r2, $i1, 31));
        $r4.add(new zzbn($r1, zzav.zzbz(), zzav.zzca(), $r2, $i1, 33));
        $r4.add(new zzba($r1, zzav.zzbx(), zzav.zzby(), $r2, $i1, 29));
        $r4.add(new zzbe($r1, zzav.zzbf(), zzav.zzbg(), $r2, $i1, 5));
        $r4.add(new zzbk($r1, zzav.zzbr(), zzav.zzbs(), $r2, $i1, 12));
        $r4.add(new zzaz($r1, zzav.zzaz(), zzav.zzba(), $r2, $i1, 3));
        $r4.add(new zzbd($r1, zzav.zzbd(), zzav.zzbe(), $r2, $i1, 34));
        $r4.add(new zzbc($r1, zzav.zzbb(), zzav.zzbc(), $r2, $i1, 35));
        if (((Boolean) Flags.zzbby.get()).booleanValue()) {
            $r4.add(new zzbf($r1, zzav.zzbh(), zzav.zzbi(), $r2, $i1, 44));
        }
        if (!((Boolean) Flags.zzbcb.get()).booleanValue()) {
            return $r4;
        }
        $r4.add(new zzbj($r1, zzav.zzbp(), zzav.zzbq(), $r2, $i1, 22));
        return $r4;
    }

    protected zza zzc(Context $r1) throws  {
        zza $r2 = new zza();
        if (!TextUtils.isEmpty(this.zzafp)) {
            $r2.zzcr = this.zzafp;
        }
        zzax $r4 = zzb($r1, this.zzafn);
        $r4.zzcr();
        zza($r4, $r2);
        $r4.zzcs();
        return $r2;
    }

    protected List<Callable<Void>> zzc(@Signature({"(", "Lcom/google/android/gms/internal/zzax;", "Lcom/google/android/gms/internal/zzae$zza;", ")", "Ljava/util/List", "<", "Ljava/util/concurrent/Callable", "<", "Ljava/lang/Void;", ">;>;"}) zzax $r1, @Signature({"(", "Lcom/google/android/gms/internal/zzax;", "Lcom/google/android/gms/internal/zzae$zza;", ")", "Ljava/util/List", "<", "Ljava/util/concurrent/Callable", "<", "Ljava/lang/Void;", ">;>;"}) zza $r2) throws  {
        ArrayList $r4 = new ArrayList();
        if ($r1.zzcd() == null) {
            return $r4;
        }
        int $i1 = $r1.zzat();
        $r4.add(new zzbi($r1, $r2));
        $r4.add(new zzbl($r1, zzav.zzbt(), zzav.zzbu(), $r2, $i1, 1));
        $r4.add(new zzbg($r1, zzav.zzbj(), zzav.zzbk(), $r2, startTime, $i1, 25));
        if (((Boolean) Flags.zzbbz.get()).booleanValue()) {
            $r4.add(new zzbf($r1, zzav.zzbh(), zzav.zzbi(), $r2, $i1, 44));
        }
        $r4.add(new zzaz($r1, zzav.zzaz(), zzav.zzba(), $r2, $i1, 3));
        if (((Boolean) Flags.zzbcc.get()).booleanValue()) {
            $r4.add(new zzbj($r1, zzav.zzbp(), zzav.zzbq(), $r2, $i1, 22));
        }
        return $r4;
    }

    protected zza zzd(Context $r1) throws  {
        zza $r2 = new zza();
        if (!TextUtils.isEmpty(this.zzafp)) {
            $r2.zzcr = this.zzafp;
        }
        zzax $r4 = zzb($r1, this.zzafn);
        $r4.zzcr();
        zzd($r4, $r2);
        $r4.zzcs();
        return $r2;
    }

    protected void zzd(com.google.android.gms.internal.zzax r32, com.google.android.gms.internal.zzae.zza r33) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x004b in list []
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
        r31 = this;
        r3 = 0;
        r0 = r31;
        r4 = r0.zzafd;
        r0 = r31;
        r5 = r0.zzafl;
        r0 = r32;	 Catch:{ zzaw -> 0x015a }
        r6 = zza(r0, r4, r5);	 Catch:{ zzaw -> 0x015a }
        r8 = 0;	 Catch:{ zzaw -> 0x015a }
        r7 = r6.get(r8);	 Catch:{ zzaw -> 0x015a }
        r10 = r7;
        r10 = (java.lang.Long) r10;
        r9 = r10;
        r0 = r33;	 Catch:{ zzaw -> 0x015a }
        r0.zzde = r9;	 Catch:{ zzaw -> 0x015a }
        r8 = 1;	 Catch:{ zzaw -> 0x015a }
        r7 = r6.get(r8);	 Catch:{ zzaw -> 0x015a }
        r11 = r7;
        r11 = (java.lang.Long) r11;
        r9 = r11;
        r0 = r33;	 Catch:{ zzaw -> 0x015a }
        r0.zzdf = r9;	 Catch:{ zzaw -> 0x015a }
        r8 = 2;	 Catch:{ zzaw -> 0x015a }
        r7 = r6.get(r8);	 Catch:{ zzaw -> 0x015a }
        r12 = r7;	 Catch:{ zzaw -> 0x015a }
        r12 = (java.lang.Long) r12;	 Catch:{ zzaw -> 0x015a }
        r9 = r12;	 Catch:{ zzaw -> 0x015a }
        r13 = r9.longValue();	 Catch:{ zzaw -> 0x015a }
        r16 = 0;
        r15 = (r13 > r16 ? 1 : (r13 == r16 ? 0 : -1));
        if (r15 < 0) goto L_0x004b;	 Catch:{ zzaw -> 0x015a }
    L_0x003c:
        r8 = 2;	 Catch:{ zzaw -> 0x015a }
        r7 = r6.get(r8);	 Catch:{ zzaw -> 0x015a }
        r18 = r7;
        r18 = (java.lang.Long) r18;
        r9 = r18;
        r0 = r33;	 Catch:{ zzaw -> 0x015a }
        r0.zzdg = r9;	 Catch:{ zzaw -> 0x015a }
    L_0x004b:
        r8 = 3;	 Catch:{ zzaw -> 0x015a }
        r7 = r6.get(r8);	 Catch:{ zzaw -> 0x015a }
        r19 = r7;
        r19 = (java.lang.Long) r19;
        r9 = r19;
        r0 = r33;	 Catch:{ zzaw -> 0x015a }
        r0.zzdu = r9;	 Catch:{ zzaw -> 0x015a }
        r8 = 4;	 Catch:{ zzaw -> 0x015a }
        r7 = r6.get(r8);	 Catch:{ zzaw -> 0x015a }
        r20 = r7;
        r20 = (java.lang.Long) r20;
        r9 = r20;
        r0 = r33;
        r0.zzdv = r9;
    L_0x0069:
        r0 = r31;
        r13 = r0.zzaff;
        r16 = 0;
        r15 = (r13 > r16 ? 1 : (r13 == r16 ? 0 : -1));
        if (r15 <= 0) goto L_0x007f;
    L_0x0073:
        r0 = r31;
        r13 = r0.zzaff;
        r9 = java.lang.Long.valueOf(r13);
        r0 = r33;
        r0.zzdz = r9;
    L_0x007f:
        r0 = r31;
        r13 = r0.zzafg;
        r16 = 0;
        r15 = (r13 > r16 ? 1 : (r13 == r16 ? 0 : -1));
        if (r15 <= 0) goto L_0x0095;
    L_0x0089:
        r0 = r31;
        r13 = r0.zzafg;
        r9 = java.lang.Long.valueOf(r13);
        r0 = r33;
        r0.zzdy = r9;
    L_0x0095:
        r0 = r31;
        r13 = r0.zzafh;
        r16 = 0;
        r15 = (r13 > r16 ? 1 : (r13 == r16 ? 0 : -1));
        if (r15 <= 0) goto L_0x00ab;
    L_0x009f:
        r0 = r31;
        r13 = r0.zzafh;
        r9 = java.lang.Long.valueOf(r13);
        r0 = r33;
        r0.zzdx = r9;
    L_0x00ab:
        r0 = r31;
        r13 = r0.zzafi;
        r16 = 0;
        r15 = (r13 > r16 ? 1 : (r13 == r16 ? 0 : -1));
        if (r15 <= 0) goto L_0x00c1;
    L_0x00b5:
        r0 = r31;
        r13 = r0.zzafi;
        r9 = java.lang.Long.valueOf(r13);
        r0 = r33;
        r0.zzea = r9;
    L_0x00c1:
        r0 = r31;
        r13 = r0.zzafj;
        r16 = 0;
        r15 = (r13 > r16 ? 1 : (r13 == r16 ? 0 : -1));
        if (r15 <= 0) goto L_0x00d7;
    L_0x00cb:
        r0 = r31;
        r13 = r0.zzafj;
        r9 = java.lang.Long.valueOf(r13);
        r0 = r33;
        r0.zzec = r9;
    L_0x00d7:
        r0 = r31;
        r0 = r0.zzafe;
        r21 = r0;
        r22 = r0.size();	 Catch:{ zzaw -> 0x0141 }
        r22 = r22 + -1;
        goto L_0x00e7;
    L_0x00e4:
        goto L_0x0069;
    L_0x00e7:
        if (r22 <= 0) goto L_0x014a;
    L_0x00e9:
        r0 = r22;
        r0 = new com.google.android.gms.internal.zzae.zza.zza[r0];
        r23 = r0;
        r1 = r33;
        r1.zzed = r0;
    L_0x00f3:
        r0 = r22;
        if (r3 >= r0) goto L_0x014a;
    L_0x00f7:
        r0 = r31;	 Catch:{ zzaw -> 0x0141 }
        r0 = r0.zzafe;	 Catch:{ zzaw -> 0x0141 }
        r21 = r0;	 Catch:{ zzaw -> 0x0141 }
        r7 = r0.get(r3);	 Catch:{ zzaw -> 0x0141 }
        r24 = r7;
        r24 = (android.view.MotionEvent) r24;
        r4 = r24;
        r0 = r31;	 Catch:{ zzaw -> 0x0141 }
        r5 = r0.zzafl;	 Catch:{ zzaw -> 0x0141 }
        r0 = r32;	 Catch:{ zzaw -> 0x0141 }
        r6 = zza(r0, r4, r5);	 Catch:{ zzaw -> 0x0141 }
        r25 = new com.google.android.gms.internal.zzae$zza$zza;	 Catch:{ zzaw -> 0x0141 }
        r0 = r25;	 Catch:{ zzaw -> 0x0141 }
        r0.<init>();	 Catch:{ zzaw -> 0x0141 }
        r8 = 0;	 Catch:{ zzaw -> 0x0141 }
        r7 = r6.get(r8);	 Catch:{ zzaw -> 0x0141 }
        r26 = r7;
        r26 = (java.lang.Long) r26;
        r9 = r26;
        r0 = r25;	 Catch:{ zzaw -> 0x0141 }
        r0.zzde = r9;	 Catch:{ zzaw -> 0x0141 }
        r8 = 1;	 Catch:{ zzaw -> 0x0141 }
        r7 = r6.get(r8);	 Catch:{ zzaw -> 0x0141 }
        r27 = r7;
        r27 = (java.lang.Long) r27;
        r9 = r27;
        r0 = r25;
        r0.zzdf = r9;
        r0 = r33;
        r0 = r0.zzed;
        r23 = r0;
        r23[r3] = r25;
        r3 = r3 + 1;
        goto L_0x00f3;
    L_0x0141:
        r28 = move-exception;
        r29 = 0;
        r0 = r29;
        r1 = r33;
        r1.zzed = r0;
    L_0x014a:
        r0 = r31;
        r1 = r32;
        r2 = r33;
        r6 = r0.zzc(r1, r2);
        r0 = r31;
        r0.zza(r6);
        return;
    L_0x015a:
        r30 = move-exception;
        goto L_0x00e4;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzaq.zzd(com.google.android.gms.internal.zzax, com.google.android.gms.internal.zzae$zza):void");
    }
}
