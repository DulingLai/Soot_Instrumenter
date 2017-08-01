package com.google.android.gms.common.util;

/* compiled from: dalvik_source_com.waze.apk */
public final class zzl {
    public static java.lang.String zza(byte[] r16, int r17, int r18, boolean r19) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:28:0x0085 in {8, 11, 17, 22, 27, 29, 30, 33, 36, 42, 43, 44, 48, 51, 52} preds:[]
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
        if (r16 == 0) goto L_0x0014;
    L_0x0002:
        r0 = r16;
        r1 = r0.length;
        r16 = r0;
        if (r1 == 0) goto L_0x0014;
    L_0x0009:
        if (r17 < 0) goto L_0x0014;
    L_0x000b:
        if (r18 <= 0) goto L_0x0014;
    L_0x000d:
        r1 = r17 + r18;
        r0 = r16;
        r2 = r0.length;
        if (r1 <= r2) goto L_0x0016;
    L_0x0014:
        r3 = 0;
        return r3;
    L_0x0016:
        r4 = 57;
        if (r19 == 0) goto L_0x001c;
    L_0x001a:
        r4 = 75;
    L_0x001c:
        r1 = r18 + 16;
        r1 = r1 + -1;
        r1 = r1 / 16;
        r1 = r4 * r1;
        r5 = new java.lang.StringBuilder;
        r5.<init>(r1);
        r2 = r18;
        r1 = 0;
        r6 = 0;
    L_0x002d:
        if (r2 <= 0) goto L_0x00e1;
    L_0x002f:
        if (r6 != 0) goto L_0x00a1;
    L_0x0031:
        r7 = 65536; // 0x10000 float:9.18355E-41 double:3.2379E-319;
        r0 = r18;
        if (r0 >= r7) goto L_0x0089;
    L_0x0038:
        r7 = 1;
        r8 = new java.lang.Object[r7];
        r0 = r17;
        r9 = java.lang.Integer.valueOf(r0);
        r7 = 0;
        r8[r7] = r9;
        r11 = "%04X:";
        r10 = java.lang.String.format(r11, r8);
        r5.append(r10);
        r1 = r17;
    L_0x004f:
        r7 = 1;
        r8 = new java.lang.Object[r7];
        r4 = r16[r17];
        r7 = 255; // 0xff float:3.57E-43 double:1.26E-321;
        r12 = r4 & r7;
        r9 = java.lang.Integer.valueOf(r12);
        r7 = 0;
        r8[r7] = r9;
        r11 = " %02X";
        r10 = java.lang.String.format(r11, r8);
        r5.append(r10);
        r2 = r2 + -1;
        r6 = r6 + 1;
        if (r19 == 0) goto L_0x00d2;
    L_0x006e:
        r7 = 16;
        if (r6 == r7) goto L_0x0074;
    L_0x0072:
        if (r2 != 0) goto L_0x00d2;
    L_0x0074:
        r7 = 16;
        r13 = r7 - r6;
        if (r13 <= 0) goto L_0x00ab;
    L_0x007a:
        r14 = 0;
    L_0x007b:
        if (r14 >= r13) goto L_0x00ab;
    L_0x007d:
        r11 = "   ";
        r5.append(r11);
        r14 = r14 + 1;
        goto L_0x007b;
        goto L_0x0089;
    L_0x0086:
        goto L_0x002d;
    L_0x0089:
        r7 = 1;
        r8 = new java.lang.Object[r7];
        r0 = r17;
        r9 = java.lang.Integer.valueOf(r0);
        r7 = 0;
        r8[r7] = r9;
        r11 = "%08X:";
        r10 = java.lang.String.format(r11, r8);
        r5.append(r10);
        r1 = r17;
        goto L_0x004f;
    L_0x00a1:
        r7 = 8;
        if (r6 != r7) goto L_0x004f;
    L_0x00a5:
        r11 = " -";
        r5.append(r11);
        goto L_0x004f;
    L_0x00ab:
        r7 = 8;
        if (r13 < r7) goto L_0x00b4;
    L_0x00af:
        r11 = "  ";
        r5.append(r11);
    L_0x00b4:
        r11 = "  ";
        r5.append(r11);
        r13 = 0;
    L_0x00ba:
        if (r13 >= r6) goto L_0x00d2;
    L_0x00bc:
        r14 = r1 + r13;
        r4 = r16[r14];
        r15 = (char) r4;
        r7 = 32;
        if (r15 < r7) goto L_0x00cf;
    L_0x00c5:
        r7 = 126; // 0x7e float:1.77E-43 double:6.23E-322;
        if (r15 > r7) goto L_0x00cf;
    L_0x00c9:
        r5.append(r15);
        r13 = r13 + 1;
        goto L_0x00ba;
    L_0x00cf:
        r15 = 46;
        goto L_0x00c9;
    L_0x00d2:
        r7 = 16;
        if (r6 == r7) goto L_0x00d8;
    L_0x00d6:
        if (r2 != 0) goto L_0x00e6;
    L_0x00d8:
        r7 = 10;
        r5.append(r7);
        r6 = 0;
    L_0x00de:
        r17 = r17 + 1;
        goto L_0x0086;
    L_0x00e1:
        r10 = r5.toString();
        return r10;
    L_0x00e6:
        goto L_0x00de;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.util.zzl.zza(byte[], int, int, boolean):java.lang.String");
    }
}
