package com.google.android.gms.internal;

import com.google.android.gms.ads.internal.config.Flags;
import com.google.android.gms.clearcut.ClearcutLogger;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/* compiled from: dalvik_source_com.waze.apk */
public class zzam {
    protected static volatile ClearcutLogger zzaez = null;
    private static volatile Random zzafb = null;
    private static final Object zzafc = new Object();
    private zzax zzaey;
    protected boolean zzafa = false;

    public zzam(zzax $r1) throws  {
        this.zzaey = $r1;
        Flags.initialize($r1.getContext());
        this.zzafa = ((Boolean) Flags.adShieldInstrumentationEnabled.get()).booleanValue();
        if (this.zzafa && zzaez == null) {
            synchronized (zzafc) {
                if (zzaez == null) {
                    zzaez = new ClearcutLogger($r1.getContext(), "ADSHIELD", null);
                }
            }
        }
    }

    private static Random zzau() throws  {
        if (zzafb == null) {
            synchronized (zzafc) {
                if (zzafb == null) {
                    zzafb = new Random();
                }
            }
        }
        return zzafb;
    }

    public void zza(int r12, int r13, long r14) throws java.io.IOException {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x0044 in list []
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
        r11 = this;
        r0 = r11.zzafa;	 Catch:{ Exception -> 0x0041 }
        if (r0 == 0) goto L_0x0043;
    L_0x0004:
        r1 = zzaez;
        if (r1 == 0) goto L_0x0044;	 Catch:{ Exception -> 0x0041 }
    L_0x0008:
        r2 = r11.zzaey;	 Catch:{ Exception -> 0x0041 }
        r0 = r2.zzci();	 Catch:{ Exception -> 0x0041 }
        if (r0 == 0) goto L_0x0045;
    L_0x0010:
        r3 = new com.google.android.gms.internal.zzad$zza;	 Catch:{ Exception -> 0x0041 }
        r3.<init>();	 Catch:{ Exception -> 0x0041 }
        r2 = r11.zzaey;	 Catch:{ Exception -> 0x0041 }
        r4 = r2.getContext();	 Catch:{ Exception -> 0x0041 }
        r5 = r4.getPackageName();	 Catch:{ Exception -> 0x0041 }
        r3.zzcj = r5;	 Catch:{ Exception -> 0x0041 }
        r6 = java.lang.Long.valueOf(r14);	 Catch:{ Exception -> 0x0041 }
        r3.zzck = r6;	 Catch:{ Exception -> 0x0041 }
        r1 = zzaez;	 Catch:{ Exception -> 0x0041 }
        r7 = com.google.android.gms.internal.zzawz.zzf(r3);	 Catch:{ Exception -> 0x0041 }
        r8 = r1.newEvent(r7);	 Catch:{ Exception -> 0x0041 }
        r8.setEventFlowId(r13);	 Catch:{ Exception -> 0x0041 }
        r8.setEventCode(r12);	 Catch:{ Exception -> 0x0041 }
        r2 = r11.zzaey;	 Catch:{ Exception -> 0x0041 }
        r9 = r2.getGoogleApiClient();	 Catch:{ Exception -> 0x0041 }
        r8.logAsync(r9);	 Catch:{ Exception -> 0x0041 }
        return;
    L_0x0041:
        r10 = move-exception;
        return;
    L_0x0043:
        return;
    L_0x0044:
        return;
    L_0x0045:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzam.zza(int, int, long):void");
    }

    public int zzat() throws  {
        try {
            return ThreadLocalRandom.current().nextInt();
        } catch (NoClassDefFoundError e) {
            return zzau().nextInt();
        } catch (RuntimeException e2) {
            return zzau().nextInt();
        }
    }
}
