package com.google.android.gms.people.internal.agg;

import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.people.model.EmailAddress;

/* compiled from: dalvik_source_com.waze.apk */
public class zzc implements EmailAddress {
    private final double aRL;
    private final double aRM;
    private final double aRN;
    private final double aRO;
    private final double aRP;
    private final String aRQ;
    private final String aRR;
    private final String aRS;
    private final String aRT;
    private final String aRU;
    private final String mValue;
    private final String zzcft;

    public zzc(String $r1, String $r2) throws  {
        this($r1, $r2, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, null, null, null, null, null);
    }

    public zzc(String $r1, String $r2, double $d0, double $d1, double $d2, double $d3, double $d4, String $r3, String $r4, String $r5, String $r6, String $r7) throws  {
        this.zzcft = $r1;
        this.mValue = $r2;
        this.aRL = $d0;
        this.aRM = $d1;
        this.aRN = $d2;
        this.aRO = $d3;
        this.aRP = $d4;
        this.aRQ = $r3;
        this.aRR = $r4;
        this.aRS = $r5;
        this.aRT = $r6;
        this.aRU = $r7;
    }

    public boolean equals(Object $r2) throws  {
        if (!($r2 instanceof zzc)) {
            return false;
        }
        return zzaa.equal(this.mValue, ((zzc) $r2).mValue);
    }

    public double getAffinity1() throws  {
        return this.aRL;
    }

    public double getAffinity2() throws  {
        return this.aRM;
    }

    public double getAffinity3() throws  {
        return this.aRN;
    }

    public double getAffinity4() throws  {
        return this.aRO;
    }

    public double getAffinity5() throws  {
        return this.aRP;
    }

    public String getLoggingId1() throws  {
        return this.aRQ;
    }

    public String getLoggingId2() throws  {
        return this.aRR;
    }

    public String getLoggingId3() throws  {
        return this.aRS;
    }

    public String getLoggingId4() throws  {
        return this.aRT;
    }

    public String getLoggingId5() throws  {
        return this.aRU;
    }

    public String getType() throws  {
        return this.zzcft;
    }

    public String getValue() throws  {
        return this.mValue;
    }

    public java.lang.String toString() throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:8:0x01a0
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.modifyBlocksTree(BlockProcessor.java:248)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:52)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.visit(BlockProcessor.java:38)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r26 = this;
        r0 = r26;
        r4 = r0.mValue;
        if (r4 == 0) goto L_0x01a4;
    L_0x0006:
        r0 = r26;
        r4 = r0.mValue;
    L_0x000a:
        r0 = r26;
        r5 = r0.zzcft;
        if (r5 == 0) goto L_0x01ab;
    L_0x0010:
        r0 = r26;
        r6 = r0.zzcft;
    L_0x0014:
        r0 = r26;
        r7 = r0.aRL;
        r0 = r26;
        r5 = r0.aRQ;
        r0 = r26;
        r9 = r0.aRM;
        r0 = r26;
        r11 = r0.aRR;
        r0 = r26;
        r12 = r0.aRN;
        r0 = r26;
        r14 = r0.aRS;
        r0 = r26;
        r0 = r0.aRO;
        r15 = r0;
        r0 = r26;
        r0 = r0.aRT;
        r17 = r0;
        r0 = r26;
        r0 = r0.aRP;
        r18 = r0;
        r0 = r26;
        r0 = r0.aRU;
        r20 = r0;
        r21 = new java.lang.StringBuilder;
        r22 = java.lang.String.valueOf(r4);
        r0 = r22;
        r23 = r0.length();
        r0 = r23;
        r0 = r0 + 172;
        r23 = r0;
        r22 = java.lang.String.valueOf(r6);
        r0 = r22;
        r24 = r0.length();
        r0 = r23;
        r1 = r24;
        r0 = r0 + r1;
        r23 = r0;
        r22 = java.lang.String.valueOf(r5);
        r0 = r22;
        r24 = r0.length();
        r0 = r23;
        r1 = r24;
        r0 = r0 + r1;
        r23 = r0;
        r22 = java.lang.String.valueOf(r11);
        r0 = r22;
        r24 = r0.length();
        r0 = r23;
        r1 = r24;
        r0 = r0 + r1;
        r23 = r0;
        r22 = java.lang.String.valueOf(r14);
        r0 = r22;
        r24 = r0.length();
        r0 = r23;
        r1 = r24;
        r0 = r0 + r1;
        r23 = r0;
        r0 = r17;
        r22 = java.lang.String.valueOf(r0);
        r0 = r22;
        r24 = r0.length();
        r0 = r23;
        r1 = r24;
        r0 = r0 + r1;
        r23 = r0;
        r0 = r20;
        r22 = java.lang.String.valueOf(r0);
        r0 = r22;
        r24 = r0.length();
        r0 = r23;
        r1 = r24;
        r0 = r0 + r1;
        r23 = r0;
        r0 = r21;
        r1 = r23;
        r0.<init>(r1);
        r25 = "EmailAddress:[Value=";
        r0 = r21;
        r1 = r25;
        r21 = r0.append(r1);
        r0 = r21;
        r21 = r0.append(r4);
        r25 = " Type=";
        r0 = r21;
        r1 = r25;
        r21 = r0.append(r1);
        r0 = r21;
        r21 = r0.append(r6);
        r25 = " a1=";
        r0 = r21;
        r1 = r25;
        r21 = r0.append(r1);
        r0 = r21;
        r21 = r0.append(r7);
        r25 = ",";
        r0 = r21;
        r1 = r25;
        r21 = r0.append(r1);
        r0 = r21;
        r21 = r0.append(r5);
        r25 = " a2=";
        r0 = r21;
        r1 = r25;
        r21 = r0.append(r1);
        r0 = r21;
        r21 = r0.append(r9);
        r25 = ",";
        r0 = r21;
        r1 = r25;
        r21 = r0.append(r1);
        r0 = r21;
        r21 = r0.append(r11);
        r25 = " a3=";
        r0 = r21;
        r1 = r25;
        r21 = r0.append(r1);
        r0 = r21;
        r21 = r0.append(r12);
        r25 = ",";
        r0 = r21;
        r1 = r25;
        r21 = r0.append(r1);
        r0 = r21;
        r21 = r0.append(r14);
        r25 = " a4=";
        r0 = r21;
        r1 = r25;
        r21 = r0.append(r1);
        r0 = r21;
        r1 = r15;
        r21 = r0.append(r1);
        r25 = ",";
        r0 = r21;
        r1 = r25;
        r21 = r0.append(r1);
        r0 = r21;
        r1 = r17;
        r21 = r0.append(r1);
        r25 = " a5=";
        r0 = r21;
        r1 = r25;
        r21 = r0.append(r1);
        r0 = r21;
        r1 = r18;
        r21 = r0.append(r1);
        r25 = ",";
        r0 = r21;
        r1 = r25;
        r21 = r0.append(r1);
        r0 = r21;
        r1 = r20;
        r21 = r0.append(r1);
        r25 = "]";
        r0 = r21;
        r1 = r25;
        r21 = r0.append(r1);
        r0 = r21;
        r4 = r0.toString();
        return r4;
        goto L_0x01a4;
    L_0x01a1:
        goto L_0x000a;
    L_0x01a4:
        r4 = "null";
        goto L_0x01a1;
        goto L_0x01ab;
    L_0x01a8:
        goto L_0x0014;
    L_0x01ab:
        r6 = "null";
        goto L_0x01a8;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.people.internal.agg.zzc.toString():java.lang.String");
    }
}
