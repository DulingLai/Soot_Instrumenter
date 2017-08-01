package com.google.android.gms.internal;

import com.google.android.gms.internal.zzae.zza;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

/* compiled from: dalvik_source_com.waze.apk */
public abstract class zzbp implements Callable {
    protected final String TAG = getClass().getSimpleName();
    protected final String className;
    protected final zzax zzaey;
    protected final zza zzaha;
    protected final String zzahf;
    protected Method zzahh;
    protected final int zzahl;
    protected final int zzahm;

    public zzbp(zzax $r1, String $r2, String $r3, zza $r4, int $i0, int $i1) throws  {
        this.zzaey = $r1;
        this.className = $r2;
        this.zzahf = $r3;
        this.zzaha = $r4;
        this.zzahl = $i0;
        this.zzahm = $i1;
    }

    public /* synthetic */ Object call() throws Exception {
        return zzcw();
    }

    protected abstract void zzct() throws IllegalAccessException, InvocationTargetException;

    public java.lang.Void zzcw() throws java.lang.Exception {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x0020 in list [B:23:0x004e, B:26:0x0051]
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
        r18 = this;
        r1 = java.lang.System.nanoTime();	 Catch:{ IllegalAccessException -> 0x004e, InvocationTargetException -> 0x0051 }
        r0 = r18;
        r3 = r0.zzaey;
        r0 = r18;
        r4 = r0.className;
        r0 = r18;	 Catch:{ IllegalAccessException -> 0x004e, InvocationTargetException -> 0x0051 }
        r5 = r0.zzahf;	 Catch:{ IllegalAccessException -> 0x004e, InvocationTargetException -> 0x0051 }
        r6 = r3.zzc(r4, r5);	 Catch:{ IllegalAccessException -> 0x004e, InvocationTargetException -> 0x0051 }
        r0 = r18;
        r0.zzahh = r6;
        r0 = r18;
        r6 = r0.zzahh;
        if (r6 != 0) goto L_0x0020;	 Catch:{ IllegalAccessException -> 0x004e, InvocationTargetException -> 0x0051 }
    L_0x001e:
        r7 = 0;
        return r7;
    L_0x0020:
        r0 = r18;	 Catch:{ IllegalAccessException -> 0x004e, InvocationTargetException -> 0x0051 }
        r0.zzct();	 Catch:{ IllegalAccessException -> 0x004e, InvocationTargetException -> 0x0051 }
        r0 = r18;	 Catch:{ IllegalAccessException -> 0x004e, InvocationTargetException -> 0x0051 }
        r3 = r0.zzaey;	 Catch:{ IllegalAccessException -> 0x004e, InvocationTargetException -> 0x0051 }
        r8 = r3.zzcj();	 Catch:{ IllegalAccessException -> 0x004e, InvocationTargetException -> 0x0051 }
        if (r8 == 0) goto L_0x0054;
    L_0x002f:
        r0 = r18;
        r9 = r0.zzahl;
        r10 = -2147483648; // 0xffffffff80000000 float:-0.0 double:NaN;
        if (r9 == r10) goto L_0x0056;
    L_0x0038:
        r0 = r18;
        r9 = r0.zzahm;
        r0 = r18;	 Catch:{ IllegalAccessException -> 0x004e, InvocationTargetException -> 0x0051 }
        r11 = r0.zzahl;	 Catch:{ IllegalAccessException -> 0x004e, InvocationTargetException -> 0x0051 }
        r12 = java.lang.System.nanoTime();	 Catch:{ IllegalAccessException -> 0x004e, InvocationTargetException -> 0x0051 }
        r1 = r12 - r1;
        r14 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;	 Catch:{ IllegalAccessException -> 0x004e, InvocationTargetException -> 0x0051 }
        r1 = r1 / r14;	 Catch:{ IllegalAccessException -> 0x004e, InvocationTargetException -> 0x0051 }
        r8.zza(r9, r11, r1);	 Catch:{ IllegalAccessException -> 0x004e, InvocationTargetException -> 0x0051 }
        r7 = 0;
        return r7;
    L_0x004e:
        r16 = move-exception;
        r7 = 0;
        return r7;
    L_0x0051:
        r17 = move-exception;
        r7 = 0;
        return r7;
    L_0x0054:
        r7 = 0;
        return r7;
    L_0x0056:
        r7 = 0;
        return r7;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzbp.zzcw():java.lang.Void");
    }
}
