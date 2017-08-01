package com.google.android.gms.common.stats;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import dalvik.annotation.Signature;
import java.util.List;

/* compiled from: dalvik_source_com.waze.apk */
public final class WakeLockEvent extends StatsEvent {
    public static final Creator<WakeLockEvent> CREATOR = new zzg();
    private final long LJ;
    private int LK;
    private final long LR;
    private long LT;
    private final String MA;
    private int MB;
    private final String MC;
    private final float MD;
    private final String Mv;
    private final String Mw;
    private final String Mx;
    private final int My;
    private final List<String> Mz;
    private final long mTimeout;
    final int mVersionCode;

    WakeLockEvent(@Signature({"(IJI", "Ljava/lang/String;", "I", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "JI", "Ljava/lang/String;", "Ljava/lang/String;", "FJ", "Ljava/lang/String;", ")V"}) int $i0, @Signature({"(IJI", "Ljava/lang/String;", "I", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "JI", "Ljava/lang/String;", "Ljava/lang/String;", "FJ", "Ljava/lang/String;", ")V"}) long $l1, @Signature({"(IJI", "Ljava/lang/String;", "I", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "JI", "Ljava/lang/String;", "Ljava/lang/String;", "FJ", "Ljava/lang/String;", ")V"}) int $i2, @Signature({"(IJI", "Ljava/lang/String;", "I", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "JI", "Ljava/lang/String;", "Ljava/lang/String;", "FJ", "Ljava/lang/String;", ")V"}) String $r1, @Signature({"(IJI", "Ljava/lang/String;", "I", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "JI", "Ljava/lang/String;", "Ljava/lang/String;", "FJ", "Ljava/lang/String;", ")V"}) int $i3, @Signature({"(IJI", "Ljava/lang/String;", "I", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "JI", "Ljava/lang/String;", "Ljava/lang/String;", "FJ", "Ljava/lang/String;", ")V"}) List<String> $r2, @Signature({"(IJI", "Ljava/lang/String;", "I", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "JI", "Ljava/lang/String;", "Ljava/lang/String;", "FJ", "Ljava/lang/String;", ")V"}) String $r3, @Signature({"(IJI", "Ljava/lang/String;", "I", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "JI", "Ljava/lang/String;", "Ljava/lang/String;", "FJ", "Ljava/lang/String;", ")V"}) long $l4, @Signature({"(IJI", "Ljava/lang/String;", "I", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "JI", "Ljava/lang/String;", "Ljava/lang/String;", "FJ", "Ljava/lang/String;", ")V"}) int $i5, @Signature({"(IJI", "Ljava/lang/String;", "I", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "JI", "Ljava/lang/String;", "Ljava/lang/String;", "FJ", "Ljava/lang/String;", ")V"}) String $r4, @Signature({"(IJI", "Ljava/lang/String;", "I", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "JI", "Ljava/lang/String;", "Ljava/lang/String;", "FJ", "Ljava/lang/String;", ")V"}) String $r5, @Signature({"(IJI", "Ljava/lang/String;", "I", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "JI", "Ljava/lang/String;", "Ljava/lang/String;", "FJ", "Ljava/lang/String;", ")V"}) float $f0, @Signature({"(IJI", "Ljava/lang/String;", "I", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "JI", "Ljava/lang/String;", "Ljava/lang/String;", "FJ", "Ljava/lang/String;", ")V"}) long $l6, @Signature({"(IJI", "Ljava/lang/String;", "I", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "JI", "Ljava/lang/String;", "Ljava/lang/String;", "FJ", "Ljava/lang/String;", ")V"}) String $r6) throws  {
        this.mVersionCode = $i0;
        this.LJ = $l1;
        this.LK = $i2;
        this.Mv = $r1;
        this.Mw = $r4;
        this.Mx = $r6;
        this.My = $i3;
        this.LT = -1;
        this.Mz = $r2;
        this.MA = $r3;
        this.LR = $l4;
        this.MB = $i5;
        this.MC = $r5;
        this.MD = $f0;
        this.mTimeout = $l6;
    }

    public WakeLockEvent(@Signature({"(JI", "Ljava/lang/String;", "I", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "JI", "Ljava/lang/String;", "Ljava/lang/String;", "FJ", "Ljava/lang/String;", ")V"}) long $l0, @Signature({"(JI", "Ljava/lang/String;", "I", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "JI", "Ljava/lang/String;", "Ljava/lang/String;", "FJ", "Ljava/lang/String;", ")V"}) int $i1, @Signature({"(JI", "Ljava/lang/String;", "I", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "JI", "Ljava/lang/String;", "Ljava/lang/String;", "FJ", "Ljava/lang/String;", ")V"}) String $r1, @Signature({"(JI", "Ljava/lang/String;", "I", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "JI", "Ljava/lang/String;", "Ljava/lang/String;", "FJ", "Ljava/lang/String;", ")V"}) int $i2, @Signature({"(JI", "Ljava/lang/String;", "I", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "JI", "Ljava/lang/String;", "Ljava/lang/String;", "FJ", "Ljava/lang/String;", ")V"}) List<String> $r2, @Signature({"(JI", "Ljava/lang/String;", "I", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "JI", "Ljava/lang/String;", "Ljava/lang/String;", "FJ", "Ljava/lang/String;", ")V"}) String $r3, @Signature({"(JI", "Ljava/lang/String;", "I", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "JI", "Ljava/lang/String;", "Ljava/lang/String;", "FJ", "Ljava/lang/String;", ")V"}) long $l3, @Signature({"(JI", "Ljava/lang/String;", "I", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "JI", "Ljava/lang/String;", "Ljava/lang/String;", "FJ", "Ljava/lang/String;", ")V"}) int $i4, @Signature({"(JI", "Ljava/lang/String;", "I", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "JI", "Ljava/lang/String;", "Ljava/lang/String;", "FJ", "Ljava/lang/String;", ")V"}) String $r4, @Signature({"(JI", "Ljava/lang/String;", "I", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "JI", "Ljava/lang/String;", "Ljava/lang/String;", "FJ", "Ljava/lang/String;", ")V"}) String $r5, @Signature({"(JI", "Ljava/lang/String;", "I", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "JI", "Ljava/lang/String;", "Ljava/lang/String;", "FJ", "Ljava/lang/String;", ")V"}) float $f0, @Signature({"(JI", "Ljava/lang/String;", "I", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "JI", "Ljava/lang/String;", "Ljava/lang/String;", "FJ", "Ljava/lang/String;", ")V"}) long $l5, @Signature({"(JI", "Ljava/lang/String;", "I", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "JI", "Ljava/lang/String;", "Ljava/lang/String;", "FJ", "Ljava/lang/String;", ")V"}) String $r6) throws  {
        this(2, $l0, $i1, $r1, $i2, $r2, $r3, $l3, $i4, $r4, $r5, $f0, $l5, $r6);
    }

    public long getDurationMillis() throws  {
        return this.LT;
    }

    public int getEventType() throws  {
        return this.LK;
    }

    public long getTimeMillis() throws  {
        return this.LJ;
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        zzg.zza(this, $r1, $i0);
    }

    public String zzayf() throws  {
        return this.MA;
    }

    public long zzayh() throws  {
        return this.LR;
    }

    public java.lang.String zzayi() throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:17:0x01db
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.modifyBlocksTree(BlockProcessor.java:248)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:52)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.visit(BlockProcessor.java:38)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r24 = this;
        r3 = "\t";
        r2 = java.lang.String.valueOf(r3);
        r0 = r24;
        r4 = r0.zzayl();
        r4 = java.lang.String.valueOf(r4);
        r3 = "\t";
        r5 = java.lang.String.valueOf(r3);
        r0 = r24;
        r6 = r0.zzayo();
        r3 = "\t";
        r7 = java.lang.String.valueOf(r3);
        r0 = r24;
        r8 = r0.zzayp();
        if (r8 != 0) goto L_0x01ca;
    L_0x002a:
        r9 = "";
    L_0x002c:
        r3 = "\t";
        r10 = java.lang.String.valueOf(r3);
        r0 = r24;
        r11 = r0.zzayq();
        r3 = "\t";
        r12 = java.lang.String.valueOf(r3);
        r0 = r24;
        r13 = r0.zzaym();
        if (r13 != 0) goto L_0x01df;
    L_0x0046:
        r13 = "";
    L_0x0048:
        r3 = "\t";
        r14 = java.lang.String.valueOf(r3);
        r0 = r24;
        r15 = r0.zzayr();
        if (r15 != 0) goto L_0x01ea;
    L_0x0056:
        r15 = "";
    L_0x0058:
        r3 = "\t";
        r16 = java.lang.String.valueOf(r3);
        r0 = r24;
        r17 = r0.zzays();
        r3 = "\t";
        r18 = java.lang.String.valueOf(r3);
        r0 = r24;
        r19 = r0.zzayn();
        if (r19 != 0) goto L_0x01f5;
    L_0x0072:
        r19 = "";
    L_0x0074:
        r20 = new java.lang.StringBuilder;
        r21 = java.lang.String.valueOf(r2);
        r0 = r21;
        r22 = r0.length();
        r22 = r22 + 37;
        r21 = java.lang.String.valueOf(r4);
        r0 = r21;
        r23 = r0.length();
        r0 = r22;
        r1 = r23;
        r0 = r0 + r1;
        r22 = r0;
        r21 = java.lang.String.valueOf(r5);
        r0 = r21;
        r23 = r0.length();
        r0 = r22;
        r1 = r23;
        r0 = r0 + r1;
        r22 = r0;
        r21 = java.lang.String.valueOf(r7);
        r0 = r21;
        r23 = r0.length();
        r0 = r22;
        r1 = r23;
        r0 = r0 + r1;
        r22 = r0;
        r21 = java.lang.String.valueOf(r9);
        r0 = r21;
        r23 = r0.length();
        r0 = r22;
        r1 = r23;
        r0 = r0 + r1;
        r22 = r0;
        r21 = java.lang.String.valueOf(r10);
        r0 = r21;
        r23 = r0.length();
        r0 = r22;
        r1 = r23;
        r0 = r0 + r1;
        r22 = r0;
        r21 = java.lang.String.valueOf(r12);
        r0 = r21;
        r23 = r0.length();
        r0 = r22;
        r1 = r23;
        r0 = r0 + r1;
        r22 = r0;
        r21 = java.lang.String.valueOf(r13);
        r0 = r21;
        r23 = r0.length();
        r0 = r22;
        r1 = r23;
        r0 = r0 + r1;
        r22 = r0;
        r21 = java.lang.String.valueOf(r14);
        r0 = r21;
        r23 = r0.length();
        r0 = r22;
        r1 = r23;
        r0 = r0 + r1;
        r22 = r0;
        r21 = java.lang.String.valueOf(r15);
        r0 = r21;
        r23 = r0.length();
        r0 = r22;
        r1 = r23;
        r0 = r0 + r1;
        r22 = r0;
        r0 = r16;
        r21 = java.lang.String.valueOf(r0);
        r0 = r21;
        r23 = r0.length();
        r0 = r22;
        r1 = r23;
        r0 = r0 + r1;
        r22 = r0;
        r0 = r18;
        r21 = java.lang.String.valueOf(r0);
        r0 = r21;
        r23 = r0.length();
        r0 = r22;
        r1 = r23;
        r0 = r0 + r1;
        r22 = r0;
        r0 = r19;
        r21 = java.lang.String.valueOf(r0);
        r0 = r21;
        r23 = r0.length();
        r0 = r22;
        r1 = r23;
        r0 = r0 + r1;
        r22 = r0;
        r0 = r20;
        r1 = r22;
        r0.<init>(r1);
        r0 = r20;
        r20 = r0.append(r2);
        r0 = r20;
        r20 = r0.append(r4);
        r0 = r20;
        r20 = r0.append(r5);
        r0 = r20;
        r20 = r0.append(r6);
        r0 = r20;
        r20 = r0.append(r7);
        r0 = r20;
        r20 = r0.append(r9);
        r0 = r20;
        r20 = r0.append(r10);
        r0 = r20;
        r20 = r0.append(r11);
        r0 = r20;
        r20 = r0.append(r12);
        r0 = r20;
        r20 = r0.append(r13);
        r0 = r20;
        r20 = r0.append(r14);
        r0 = r20;
        r20 = r0.append(r15);
        r0 = r20;
        r1 = r16;
        r20 = r0.append(r1);
        r0 = r20;
        r1 = r17;
        r20 = r0.append(r1);
        r0 = r20;
        r1 = r18;
        r20 = r0.append(r1);
        r0 = r20;
        r1 = r19;
        r20 = r0.append(r1);
        r0 = r20;
        r2 = r0.toString();
        return r2;
    L_0x01ca:
        r0 = r24;
        r8 = r0.zzayp();
        goto L_0x01d4;
    L_0x01d1:
        goto L_0x002c;
    L_0x01d4:
        r3 = ",";
        r9 = android.text.TextUtils.join(r3, r8);
        goto L_0x01d1;
        goto L_0x01df;
    L_0x01dc:
        goto L_0x0048;
    L_0x01df:
        r0 = r24;
        r13 = r0.zzaym();
        goto L_0x01dc;
        goto L_0x01ea;
    L_0x01e7:
        goto L_0x0058;
    L_0x01ea:
        r0 = r24;
        r15 = r0.zzayr();
        goto L_0x01e7;
        goto L_0x01f5;
    L_0x01f2:
        goto L_0x0074;
    L_0x01f5:
        r0 = r24;
        r19 = r0.zzayn();
        goto L_0x01f2;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.stats.WakeLockEvent.zzayi():java.lang.String");
    }

    public String zzayl() throws  {
        return this.Mv;
    }

    public String zzaym() throws  {
        return this.Mw;
    }

    public String zzayn() throws  {
        return this.Mx;
    }

    public int zzayo() throws  {
        return this.My;
    }

    public List<String> zzayp() throws  {
        return this.Mz;
    }

    public int zzayq() throws  {
        return this.MB;
    }

    public String zzayr() throws  {
        return this.MC;
    }

    public float zzays() throws  {
        return this.MD;
    }

    public long zzayt() throws  {
        return this.mTimeout;
    }
}
