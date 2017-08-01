package android.support.graphics.drawable;

import android.graphics.Path;
import android.util.Log;
import com.waze.FriendsBarFragment;
import dalvik.annotation.Signature;
import java.util.ArrayList;

class PathParser {
    private static final String LOGTAG = "PathParser";

    private static class ExtractFloatResult {
        int mEndPosition;
        boolean mEndWithNegOrDot;

        private ExtractFloatResult() throws  {
        }
    }

    public static class PathDataNode {
        float[] params;
        char type;

        private static void addCommand(android.graphics.Path r28, float[] r29, char r30, char r31, float[] r32) throws  {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:27:0x0099 in {2, 7, 8, 9, 10, 11, 12, 13, 14, 18, 20, 21, 23, 25, 26, 28, 41, 44, 47, 50, 53, 62, 63, 65, 74, 75, 77, 80, 83, 92, 93, 95, 104, 105, 107, 111, 114, 116, 118, 119, 122, 125, 127, 129, 130, 132} preds:[]
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.computeDominators(BlockProcessor.java:129)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.visit(BlockProcessor.java:38)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r10 = 2;
            r12 = 0;
            r11 = r29[r12];
            r12 = 1;
            r13 = r29[r12];
            r12 = 2;
            r14 = r29[r12];
            r12 = 3;
            r15 = r29[r12];
            r12 = 4;
            r16 = r29[r12];
            r12 = 5;
            r17 = r29[r12];
            switch(r31) {
                case 65: goto L_0x004f;
                case 67: goto L_0x004b;
                case 72: goto L_0x0049;
                case 76: goto L_0x0047;
                case 77: goto L_0x0047;
                case 81: goto L_0x004d;
                case 83: goto L_0x004d;
                case 84: goto L_0x0047;
                case 86: goto L_0x0049;
                case 90: goto L_0x0030;
                case 97: goto L_0x004f;
                case 99: goto L_0x004b;
                case 104: goto L_0x0049;
                case 108: goto L_0x0047;
                case 109: goto L_0x0047;
                case 113: goto L_0x004d;
                case 115: goto L_0x004d;
                case 116: goto L_0x0047;
                case 118: goto L_0x0049;
                case 122: goto L_0x0030;
                default: goto L_0x0016;
            };
        L_0x0016:
            goto L_0x0017;
        L_0x0017:
            r18 = 0;
        L_0x0019:
            r0 = r32;
            r0 = r0.length;
            r19 = r0;
            r0 = r18;
            r1 = r19;
            if (r0 >= r1) goto L_0x043f;
        L_0x0024:
            switch(r31) {
                case 65: goto L_0x03eb;
                case 67: goto L_0x01a9;
                case 72: goto L_0x0124;
                case 76: goto L_0x00f7;
                case 77: goto L_0x009d;
                case 81: goto L_0x02d5;
                case 83: goto L_0x0241;
                case 84: goto L_0x0347;
                case 86: goto L_0x0151;
                case 97: goto L_0x0393;
                case 99: goto L_0x0163;
                case 104: goto L_0x010d;
                case 108: goto L_0x00d3;
                case 109: goto L_0x0051;
                case 113: goto L_0x029d;
                case 115: goto L_0x01e3;
                case 116: goto L_0x02ff;
                case 118: goto L_0x0136;
                default: goto L_0x0027;
            };
        L_0x0027:
            goto L_0x0028;
        L_0x0028:
            r30 = r31;
            r0 = r18;
            r0 = r0 + r10;
            r18 = r0;
            goto L_0x0019;
        L_0x0030:
            r0 = r28;
            r0.close();
            r11 = r16;
            r13 = r17;
            r14 = r16;
            r15 = r17;
            r0 = r28;
            r1 = r16;
            r2 = r17;
            r0.moveTo(r1, r2);
            goto L_0x0017;
        L_0x0047:
            r10 = 2;
            goto L_0x0017;
        L_0x0049:
            r10 = 1;
            goto L_0x0017;
        L_0x004b:
            r10 = 6;
            goto L_0x0017;
        L_0x004d:
            r10 = 4;
            goto L_0x0017;
        L_0x004f:
            r10 = 7;
            goto L_0x0017;
        L_0x0051:
            r19 = r18 + 0;
            r20 = r32[r19];
            r0 = r20;
            r11 = r11 + r0;
            r19 = r18 + 1;
            r20 = r32[r19];
            r0 = r20;
            r13 = r13 + r0;
            if (r18 <= 0) goto L_0x007b;
        L_0x0061:
            goto L_0x0065;
        L_0x0062:
            goto L_0x0028;
        L_0x0065:
            r19 = r18 + 0;
            r20 = r32[r19];
            r19 = r18 + 1;
            r21 = r32[r19];
            goto L_0x0071;
        L_0x006e:
            goto L_0x0028;
        L_0x0071:
            r0 = r28;
            r1 = r20;
            r2 = r21;
            r0.rLineTo(r1, r2);
            goto L_0x0028;
        L_0x007b:
            r19 = r18 + 0;
            r16 = r32[r19];
            goto L_0x0083;
        L_0x0080:
            goto L_0x0028;
        L_0x0083:
            r19 = r18 + 1;
            r17 = r32[r19];
            goto L_0x008b;
        L_0x0088:
            goto L_0x0028;
        L_0x008b:
            r0 = r28;
            r1 = r16;
            r2 = r17;
            r0.rMoveTo(r1, r2);
            r16 = r11;
            r17 = r13;
            goto L_0x0028;
            goto L_0x009d;
        L_0x009a:
            goto L_0x0028;
        L_0x009d:
            r19 = r18 + 0;
            r11 = r32[r19];
            r19 = r18 + 1;
            r13 = r32[r19];
            if (r18 <= 0) goto L_0x00b9;
        L_0x00a7:
            r19 = r18 + 0;
            r20 = r32[r19];
            r19 = r18 + 1;
            r21 = r32[r19];
            r0 = r28;
            r1 = r20;
            r2 = r21;
            r0.lineTo(r1, r2);
            goto L_0x0062;
        L_0x00b9:
            r19 = r18 + 0;
            r16 = r32[r19];
            r19 = r18 + 1;
            r17 = r32[r19];
            r0 = r28;
            r1 = r16;
            r2 = r17;
            r0.moveTo(r1, r2);
            goto L_0x00ce;
        L_0x00cb:
            goto L_0x0088;
        L_0x00ce:
            r16 = r11;
            r17 = r13;
            goto L_0x006e;
        L_0x00d3:
            r19 = r18 + 0;
            r20 = r32[r19];
            r19 = r18 + 1;
            r21 = r32[r19];
            goto L_0x00df;
        L_0x00dc:
            goto L_0x009a;
        L_0x00df:
            r0 = r28;
            r1 = r20;
            r2 = r21;
            r0.rLineTo(r1, r2);
            r19 = r18 + 0;
            r20 = r32[r19];
            r0 = r20;
            r11 = r11 + r0;
            r19 = r18 + 1;
            r20 = r32[r19];
            r0 = r20;
            r13 = r13 + r0;
            goto L_0x0080;
        L_0x00f7:
            r19 = r18 + 0;
            r13 = r32[r19];
            r19 = r18 + 1;
            r11 = r32[r19];
            r0 = r28;
            r0.lineTo(r13, r11);
            r19 = r18 + 0;
            r11 = r32[r19];
            r19 = r18 + 1;
            r13 = r32[r19];
            goto L_0x00cb;
        L_0x010d:
            r19 = r18 + 0;
            r20 = r32[r19];
            r22 = 0;
            r0 = r28;
            r1 = r20;
            r2 = r22;
            r0.rLineTo(r1, r2);
            r19 = r18 + 0;
            r20 = r32[r19];
            r0 = r20;
            r11 = r11 + r0;
            goto L_0x00dc;
        L_0x0124:
            r19 = r18 + 0;
            r11 = r32[r19];
            r0 = r28;
            r0.lineTo(r11, r13);
            r19 = r18 + 0;
            goto L_0x0133;
        L_0x0130:
            goto L_0x0028;
        L_0x0133:
            r11 = r32[r19];
            goto L_0x0130;
        L_0x0136:
            r19 = r18 + 0;
            r20 = r32[r19];
            r22 = 0;
            r0 = r28;
            r1 = r22;
            r2 = r20;
            r0.rLineTo(r1, r2);
            r19 = r18 + 0;
            r20 = r32[r19];
            goto L_0x014d;
        L_0x014a:
            goto L_0x0028;
        L_0x014d:
            r0 = r20;
            r13 = r13 + r0;
            goto L_0x014a;
        L_0x0151:
            r19 = r18 + 0;
            r13 = r32[r19];
            r0 = r28;
            r0.lineTo(r11, r13);
            r19 = r18 + 0;
            goto L_0x0160;
        L_0x015d:
            goto L_0x0028;
        L_0x0160:
            r13 = r32[r19];
            goto L_0x015d;
        L_0x0163:
            r19 = r18 + 0;
            r14 = r32[r19];
            r19 = r18 + 1;
            r15 = r32[r19];
            r19 = r18 + 2;
            r20 = r32[r19];
            r19 = r18 + 3;
            r21 = r32[r19];
            r19 = r18 + 4;
            r23 = r32[r19];
            r19 = r18 + 5;
            r24 = r32[r19];
            r0 = r28;
            r1 = r14;
            r2 = r15;
            r3 = r20;
            r4 = r21;
            r5 = r23;
            r6 = r24;
            r0.rCubicTo(r1, r2, r3, r4, r5, r6);
            r19 = r18 + 2;
            r14 = r32[r19];
            r14 = r11 + r14;
            r19 = r18 + 3;
            r15 = r32[r19];
            r15 = r13 + r15;
            r19 = r18 + 4;
            r20 = r32[r19];
            r0 = r20;
            r11 = r11 + r0;
            r19 = r18 + 5;
            r20 = r32[r19];
            goto L_0x01a5;
        L_0x01a2:
            goto L_0x0028;
        L_0x01a5:
            r0 = r20;
            r13 = r13 + r0;
            goto L_0x01a2;
        L_0x01a9:
            r19 = r18 + 0;
            r13 = r32[r19];
            r19 = r18 + 1;
            r11 = r32[r19];
            r19 = r18 + 2;
            r14 = r32[r19];
            r19 = r18 + 3;
            r15 = r32[r19];
            r19 = r18 + 4;
            r20 = r32[r19];
            r19 = r18 + 5;
            r21 = r32[r19];
            r0 = r28;
            r1 = r13;
            r2 = r11;
            r3 = r14;
            r4 = r15;
            r5 = r20;
            r6 = r21;
            r0.cubicTo(r1, r2, r3, r4, r5, r6);
            r19 = r18 + 4;
            r11 = r32[r19];
            r19 = r18 + 5;
            r13 = r32[r19];
            r19 = r18 + 2;
            r14 = r32[r19];
            r19 = r18 + 3;
            goto L_0x01e0;
        L_0x01dd:
            goto L_0x0028;
        L_0x01e0:
            r15 = r32[r19];
            goto L_0x01dd;
        L_0x01e3:
            r20 = 0;
            r21 = 0;
            r12 = 99;
            r0 = r30;
            if (r0 == r12) goto L_0x01ff;
        L_0x01ed:
            r12 = 115; // 0x73 float:1.61E-43 double:5.7E-322;
            r0 = r30;
            if (r0 == r12) goto L_0x01ff;
        L_0x01f3:
            r12 = 67;
            r0 = r30;
            if (r0 == r12) goto L_0x01ff;
        L_0x01f9:
            r12 = 83;
            r0 = r30;
            if (r0 != r12) goto L_0x0203;
        L_0x01ff:
            r20 = r11 - r14;
            r21 = r13 - r15;
        L_0x0203:
            r19 = r18 + 0;
            r14 = r32[r19];
            r19 = r18 + 1;
            r15 = r32[r19];
            r19 = r18 + 2;
            r23 = r32[r19];
            r19 = r18 + 3;
            r24 = r32[r19];
            r0 = r28;
            r1 = r20;
            r2 = r21;
            r3 = r14;
            r4 = r15;
            r5 = r23;
            r6 = r24;
            r0.rCubicTo(r1, r2, r3, r4, r5, r6);
            r19 = r18 + 0;
            r14 = r32[r19];
            r14 = r11 + r14;
            r19 = r18 + 1;
            r15 = r32[r19];
            r15 = r13 + r15;
            r19 = r18 + 2;
            r20 = r32[r19];
            r0 = r20;
            r11 = r11 + r0;
            r19 = r18 + 3;
            r20 = r32[r19];
            goto L_0x023d;
        L_0x023a:
            goto L_0x0028;
        L_0x023d:
            r0 = r20;
            r13 = r13 + r0;
            goto L_0x023a;
        L_0x0241:
            r20 = r11;
            r21 = r13;
            r12 = 99;
            r0 = r30;
            if (r0 == r12) goto L_0x025d;
        L_0x024b:
            r12 = 115; // 0x73 float:1.61E-43 double:5.7E-322;
            r0 = r30;
            if (r0 == r12) goto L_0x025d;
        L_0x0251:
            r12 = 67;
            r0 = r30;
            if (r0 == r12) goto L_0x025d;
        L_0x0257:
            r12 = 83;
            r0 = r30;
            if (r0 != r12) goto L_0x026b;
        L_0x025d:
            r22 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
            r11 = r22 * r11;
            r20 = r11 - r14;
            r22 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
            r13 = r22 * r13;
            r21 = r13 - r15;
        L_0x026b:
            r19 = r18 + 0;
            r13 = r32[r19];
            r19 = r18 + 1;
            r11 = r32[r19];
            r19 = r18 + 2;
            r14 = r32[r19];
            r19 = r18 + 3;
            r15 = r32[r19];
            r0 = r28;
            r1 = r20;
            r2 = r21;
            r3 = r13;
            r4 = r11;
            r5 = r14;
            r6 = r15;
            r0.cubicTo(r1, r2, r3, r4, r5, r6);
            r19 = r18 + 0;
            r14 = r32[r19];
            r19 = r18 + 1;
            r15 = r32[r19];
            r19 = r18 + 2;
            r11 = r32[r19];
            r19 = r18 + 3;
            goto L_0x029a;
        L_0x0297:
            goto L_0x0028;
        L_0x029a:
            r13 = r32[r19];
            goto L_0x0297;
        L_0x029d:
            r19 = r18 + 0;
            r14 = r32[r19];
            r19 = r18 + 1;
            r15 = r32[r19];
            r19 = r18 + 2;
            r20 = r32[r19];
            r19 = r18 + 3;
            r21 = r32[r19];
            r0 = r28;
            r1 = r20;
            r2 = r21;
            r0.rQuadTo(r14, r15, r1, r2);
            r19 = r18 + 0;
            r14 = r32[r19];
            r14 = r11 + r14;
            r19 = r18 + 1;
            r15 = r32[r19];
            r15 = r13 + r15;
            r19 = r18 + 2;
            r20 = r32[r19];
            r0 = r20;
            r11 = r11 + r0;
            r19 = r18 + 3;
            r20 = r32[r19];
            goto L_0x02d1;
        L_0x02ce:
            goto L_0x0028;
        L_0x02d1:
            r0 = r20;
            r13 = r13 + r0;
            goto L_0x02ce;
        L_0x02d5:
            r19 = r18 + 0;
            r13 = r32[r19];
            r19 = r18 + 1;
            r11 = r32[r19];
            r19 = r18 + 2;
            r14 = r32[r19];
            r19 = r18 + 3;
            r15 = r32[r19];
            r0 = r28;
            r0.quadTo(r13, r11, r14, r15);
            r19 = r18 + 0;
            r14 = r32[r19];
            r19 = r18 + 1;
            r15 = r32[r19];
            r19 = r18 + 2;
            r11 = r32[r19];
            r19 = r18 + 3;
            goto L_0x02fc;
        L_0x02f9:
            goto L_0x0028;
        L_0x02fc:
            r13 = r32[r19];
            goto L_0x02f9;
        L_0x02ff:
            r20 = 0;
            r21 = 0;
            r12 = 113; // 0x71 float:1.58E-43 double:5.6E-322;
            r0 = r30;
            if (r0 == r12) goto L_0x031b;
        L_0x0309:
            r12 = 116; // 0x74 float:1.63E-43 double:5.73E-322;
            r0 = r30;
            if (r0 == r12) goto L_0x031b;
        L_0x030f:
            r12 = 81;
            r0 = r30;
            if (r0 == r12) goto L_0x031b;
        L_0x0315:
            r12 = 84;
            r0 = r30;
            if (r0 != r12) goto L_0x031f;
        L_0x031b:
            r20 = r11 - r14;
            r21 = r13 - r15;
        L_0x031f:
            r19 = r18 + 0;
            r14 = r32[r19];
            r19 = r18 + 1;
            r15 = r32[r19];
            r0 = r28;
            r1 = r20;
            r2 = r21;
            r0.rQuadTo(r1, r2, r14, r15);
            r14 = r11 + r20;
            r15 = r13 + r21;
            r19 = r18 + 0;
            r20 = r32[r19];
            r0 = r20;
            r11 = r11 + r0;
            r19 = r18 + 1;
            r20 = r32[r19];
            goto L_0x0343;
        L_0x0340:
            goto L_0x0028;
        L_0x0343:
            r0 = r20;
            r13 = r13 + r0;
            goto L_0x0340;
        L_0x0347:
            r20 = r11;
            r21 = r13;
            r12 = 113; // 0x71 float:1.58E-43 double:5.6E-322;
            r0 = r30;
            if (r0 == r12) goto L_0x0363;
        L_0x0351:
            r12 = 116; // 0x74 float:1.63E-43 double:5.73E-322;
            r0 = r30;
            if (r0 == r12) goto L_0x0363;
        L_0x0357:
            r12 = 81;
            r0 = r30;
            if (r0 == r12) goto L_0x0363;
        L_0x035d:
            r12 = 84;
            r0 = r30;
            if (r0 != r12) goto L_0x0371;
        L_0x0363:
            r22 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
            r11 = r22 * r11;
            r20 = r11 - r14;
            r22 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
            r13 = r22 * r13;
            r21 = r13 - r15;
        L_0x0371:
            r19 = r18 + 0;
            r13 = r32[r19];
            r19 = r18 + 1;
            r11 = r32[r19];
            r0 = r28;
            r1 = r20;
            r2 = r21;
            r0.quadTo(r1, r2, r13, r11);
            r14 = r20;
            r15 = r21;
            r19 = r18 + 0;
            r11 = r32[r19];
            r19 = r18 + 1;
            goto L_0x0390;
        L_0x038d:
            goto L_0x0028;
        L_0x0390:
            r13 = r32[r19];
            goto L_0x038d;
        L_0x0393:
            r19 = r18 + 5;
            r14 = r32[r19];
            r14 = r14 + r11;
            r19 = r18 + 6;
            r15 = r32[r19];
            r15 = r15 + r13;
            r19 = r18 + 0;
            r20 = r32[r19];
            r19 = r18 + 1;
            r21 = r32[r19];
            r19 = r18 + 2;
            r23 = r32[r19];
            r19 = r18 + 3;
            r24 = r32[r19];
            r22 = 0;
            r25 = (r24 > r22 ? 1 : (r24 == r22 ? 0 : -1));
            if (r25 == 0) goto L_0x03e5;
        L_0x03b3:
            r26 = 1;
        L_0x03b5:
            r19 = r18 + 4;
            r24 = r32[r19];
            r22 = 0;
            r25 = (r24 > r22 ? 1 : (r24 == r22 ? 0 : -1));
            if (r25 == 0) goto L_0x03e8;
        L_0x03bf:
            r27 = 1;
        L_0x03c1:
            r0 = r28;
            r1 = r11;
            r2 = r13;
            r3 = r14;
            r4 = r15;
            r5 = r20;
            r6 = r21;
            r7 = r23;
            r8 = r26;
            r9 = r27;
            drawArc(r0, r1, r2, r3, r4, r5, r6, r7, r8, r9);
            r19 = r18 + 5;
            r14 = r32[r19];
            r11 = r11 + r14;
            r19 = r18 + 6;
            r14 = r32[r19];
            r13 = r13 + r14;
            r14 = r11;
            goto L_0x03e3;
        L_0x03e0:
            goto L_0x0028;
        L_0x03e3:
            r15 = r13;
            goto L_0x03e0;
        L_0x03e5:
            r26 = 0;
            goto L_0x03b5;
        L_0x03e8:
            r27 = 0;
            goto L_0x03c1;
        L_0x03eb:
            r19 = r18 + 5;
            r14 = r32[r19];
            r19 = r18 + 6;
            r15 = r32[r19];
            r19 = r18 + 0;
            r20 = r32[r19];
            r19 = r18 + 1;
            r21 = r32[r19];
            r19 = r18 + 2;
            r23 = r32[r19];
            r19 = r18 + 3;
            r24 = r32[r19];
            r22 = 0;
            r25 = (r24 > r22 ? 1 : (r24 == r22 ? 0 : -1));
            if (r25 == 0) goto L_0x0439;
        L_0x0409:
            r26 = 1;
        L_0x040b:
            r19 = r18 + 4;
            r24 = r32[r19];
            r22 = 0;
            r25 = (r24 > r22 ? 1 : (r24 == r22 ? 0 : -1));
            if (r25 == 0) goto L_0x043c;
        L_0x0415:
            r27 = 1;
        L_0x0417:
            r0 = r28;
            r1 = r11;
            r2 = r13;
            r3 = r14;
            r4 = r15;
            r5 = r20;
            r6 = r21;
            r7 = r23;
            r8 = r26;
            r9 = r27;
            drawArc(r0, r1, r2, r3, r4, r5, r6, r7, r8, r9);
            r19 = r18 + 5;
            r11 = r32[r19];
            r19 = r18 + 6;
            r13 = r32[r19];
            r14 = r11;
            goto L_0x0437;
        L_0x0434:
            goto L_0x0028;
        L_0x0437:
            r15 = r13;
            goto L_0x0434;
        L_0x0439:
            r26 = 0;
            goto L_0x040b;
        L_0x043c:
            r27 = 0;
            goto L_0x0417;
        L_0x043f:
            r12 = 0;
            r29[r12] = r11;
            r12 = 1;
            r29[r12] = r13;
            r12 = 2;
            r29[r12] = r14;
            r12 = 3;
            r29[r12] = r15;
            r12 = 4;
            r29[r12] = r16;
            r12 = 5;
            r29[r12] = r17;
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: android.support.graphics.drawable.PathParser.PathDataNode.addCommand(android.graphics.Path, float[], char, char, float[]):void");
        }

        private PathDataNode(char $c0, float[] $r1) throws  {
            this.type = $c0;
            this.params = $r1;
        }

        private PathDataNode(PathDataNode $r1) throws  {
            this.type = $r1.type;
            this.params = PathParser.copyOfRange($r1.params, 0, $r1.params.length);
        }

        public static void nodesToPath(PathDataNode[] $r0, Path $r1) throws  {
            float[] $r2 = new float[6];
            char $c0 = 'm';
            for (int $i1 = 0; $i1 < $r0.length; $i1++) {
                addCommand($r1, $r2, $c0, $r0[$i1].type, $r0[$i1].params);
                $c0 = $r0[$i1].type;
            }
        }

        public void interpolatePathDataNode(PathDataNode $r1, PathDataNode $r2, float $f0) throws  {
            for (int $i0 = 0; $i0 < $r1.params.length; $i0++) {
                this.params[$i0] = ($r1.params[$i0] * (1.0f - $f0)) + ($r2.params[$i0] * $f0);
            }
        }

        private static void drawArc(Path $r0, float $f0, float $f1, float $f2, float $f3, float a, float b, float $f6, boolean $z0, boolean $z1) throws  {
            double $d12 = (double) $f6;
            double d = $d12;
            d = Math.toRadians($d12);
            double $d0 = Math.cos(d);
            double $d1 = Math.sin(d);
            $d12 = (double) $f0;
            double d2 = $d12 * $d0;
            $d12 = (double) $f1;
            d2 = (d2 + ($d12 * $d1)) / ((double) a);
            $d12 = -$f0;
            float $f7 = $d12;
            $d12 = (double) $d12;
            double d3 = $d12 * $d1;
            $d12 = (double) $f1;
            d3 = (d3 + ($d12 * $d0)) / ((double) b);
            $d12 = (double) $f2;
            double d4 = $d12 * $d0;
            $d12 = (double) $f3;
            d4 = (d4 + ($d12 * $d1)) / ((double) a);
            $d12 = -$f2;
            $f7 = $d12;
            $d12 = (double) $d12;
            double d5 = $d12 * $d1;
            $d12 = (double) $f3;
            d5 = (d5 + ($d12 * $d0)) / ((double) b);
            double $d4 = d2 - d4;
            double $d5 = d3 - d5;
            double $d8 = (d2 + d4) / 2.0d;
            double d6 = (d3 + d5) / 2.0d;
            double d7 = ($d4 * $d4) + ($d5 * $d5);
            if (d7 == 0.0d) {
                Log.w(PathParser.LOGTAG, " Points are coincident");
                return;
            }
            double d8 = (FriendsBarFragment.END_LOCATION_POSITION / d7) - 0.25d;
            if (d8 < 0.0d) {
                Log.w(PathParser.LOGTAG, "Points are too far apart " + d7);
                float $f72 = Math.sqrt(d7) / 1.99999d;
                long j = $f72;
                $f7 = (float) $f72;
                drawArc($r0, $f0, $f1, $f2, $f3, a * $f7, b * $f7, $f6, $z0, $z1);
                return;
            }
            d7 = Math.sqrt(d8);
            $d4 = d7 * $d4;
            $d5 = d7 * $d5;
            if ($z0 == $z1) {
                $d5 = $d8 - $d5;
                $d8 = d6 + $d4;
            } else {
                $d5 = $d8 + $d5;
                $d8 = d6 - $d4;
            }
            d2 = Math.atan2(d3 - $d8, d2 - $d5);
            d3 = Math.atan2(d5 - $d8, d4 - $d5) - d2;
            if (d3 >= 0.0d) {
                $z0 = true;
            } else {
                $z0 = false;
            }
            if ($z1 != $z0) {
                d3 = d3 > 0.0d ? d3 - 6.283185307179586d : d3 + 6.283185307179586d;
            }
            d5 = $d5 * ((double) a);
            $d5 = $d8 * ((double) b);
            arcToBezier($r0, (d5 * $d0) - ($d5 * $d1), (d5 * $d1) + ($d5 * $d0), (double) a, (double) b, (double) $f0, (double) $f1, d, d2, d3);
        }

        private static void arcToBezier(Path $r0, double $d0, double $d1, double $d2, double $d3, double $d13, double $d14, double $d4, double $d5, double $d6) throws  {
            int $i0 = (int) Math.ceil(Math.abs((4.0d * $d6) / 3.141592653589793d));
            double $d7 = $d5;
            double $d15 = Math.cos($d4);
            $d4 = Math.sin($d4);
            double $d12 = Math.cos($d5);
            double $d16 = Math.sin($d5);
            double $d52 = -$d2;
            $d52 *= $d15;
            $d5 = ($d52 * $d16) - (($d3 * $d4) * $d12);
            $d52 = -$d2;
            $d16 = (($d52 * $d4) * $d16) + (($d3 * $d15) * $d12);
            $d6 /= (double) $i0;
            for (int $i1 = 0; $i1 < $i0; $i1++) {
                $d12 = $d7 + $d6;
                double $d17 = Math.sin($d12);
                double $d11 = Math.cos($d12);
                $d52 = ($d2 * $d15) * $d11;
                double d = $d52;
                d = ($d52 + $d0) - (($d3 * $d4) * $d17);
                $d52 = ($d2 * $d4) * $d11;
                double d2 = $d52;
                d2 = ($d52 + $d1) + (($d3 * $d15) * $d17);
                $d52 = -$d2;
                $d52 *= $d15;
                double d3 = ($d52 * $d17) - (($d3 * $d4) * $d11);
                $d52 = -$d2;
                $d11 = (($d52 * $d4) * $d17) + (($d3 * $d15) * $d11);
                $d52 = ($d12 - $d7) / 2.0d;
                $d17 = $d52;
                $d17 = Math.tan($d52);
                $d52 = Math.sqrt(4.0d + ((3.0d * $d17) * $d17)) - FriendsBarFragment.END_LOCATION_POSITION;
                $d17 = $d52;
                $d7 = (Math.sin($d12 - $d7) * $d52) / 3.0d;
                $r0.cubicTo((float) ($d13 + ($d7 * $d5)), (float) ($d14 + ($d7 * $d16)), (float) (d - ($d7 * d3)), (float) (d2 - ($d7 * $d11)), (float) d, (float) d2);
                $d7 = $d12;
                $d13 = d;
                $d14 = d2;
                $d5 = d3;
                $d16 = $d11;
            }
        }
    }

    private static float[] getFloats(java.lang.String r21) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x005e in list []
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
        r3 = 1;
        r5 = 0;
        r0 = r21;
        r4 = r0.charAt(r5);
        r5 = 122; // 0x7a float:1.71E-43 double:6.03E-322;
        if (r4 != r5) goto L_0x0020;
    L_0x000c:
        r6 = 1;
    L_0x000d:
        r5 = 0;
        r0 = r21;
        r4 = r0.charAt(r5);
        r5 = 90;
        if (r4 != r5) goto L_0x0022;
    L_0x0018:
        r3 = r6 | r3;
        if (r3 == 0) goto L_0x0024;
    L_0x001c:
        r5 = 0;
        r7 = new float[r5];
        return r7;
    L_0x0020:
        r6 = 0;
        goto L_0x000d;
    L_0x0022:
        r3 = 0;
        goto L_0x0018;
    L_0x0024:
        r0 = r21;	 Catch:{ NumberFormatException -> 0x0064 }
        r8 = r0.length();	 Catch:{ NumberFormatException -> 0x0064 }
        r7 = new float[r8];
        r8 = 1;
        r9 = new android.support.graphics.drawable.PathParser$ExtractFloatResult;	 Catch:{ NumberFormatException -> 0x0064 }
        r10 = 0;	 Catch:{ NumberFormatException -> 0x0064 }
        r9.<init>();	 Catch:{ NumberFormatException -> 0x0064 }
        r0 = r21;	 Catch:{ NumberFormatException -> 0x0064 }
        r11 = r0.length();	 Catch:{ NumberFormatException -> 0x0064 }
        r12 = 0;
    L_0x003a:
        if (r8 >= r11) goto L_0x005e;	 Catch:{ NumberFormatException -> 0x0064 }
    L_0x003c:
        r0 = r21;	 Catch:{ NumberFormatException -> 0x0064 }
        extract(r0, r8, r9);	 Catch:{ NumberFormatException -> 0x0064 }
        r13 = r9.mEndPosition;
        if (r8 >= r13) goto L_0x009a;
    L_0x0045:
        r14 = r12 + 1;	 Catch:{ NumberFormatException -> 0x0064 }
        r0 = r21;	 Catch:{ NumberFormatException -> 0x0064 }
        r15 = r0.substring(r8, r13);	 Catch:{ NumberFormatException -> 0x0064 }
        r16 = java.lang.Float.parseFloat(r15);	 Catch:{ NumberFormatException -> 0x0064 }
        r7[r12] = r16;
    L_0x0053:
        r3 = r9.mEndWithNegOrDot;
        if (r3 == 0) goto L_0x005a;
    L_0x0057:
        r8 = r13;
        r12 = r14;
        goto L_0x003a;
    L_0x005a:
        r8 = r13 + 1;
        r12 = r14;
        goto L_0x003a;
    L_0x005e:
        r5 = 0;	 Catch:{ NumberFormatException -> 0x0064 }
        r7 = copyOfRange(r7, r5, r12);	 Catch:{ NumberFormatException -> 0x0064 }
        return r7;
    L_0x0064:
        r17 = move-exception;
        r18 = new java.lang.RuntimeException;
        r19 = new java.lang.StringBuilder;
        r0 = r19;
        r0.<init>();
        r20 = "error in parsing \"";
        r0 = r19;
        r1 = r20;
        r19 = r0.append(r1);
        r0 = r19;
        r1 = r21;
        r19 = r0.append(r1);
        r20 = "\"";
        r0 = r19;
        r1 = r20;
        r19 = r0.append(r1);
        r0 = r19;
        r21 = r0.toString();
        r0 = r18;
        r1 = r21;
        r2 = r17;
        r0.<init>(r1, r2);
        throw r18;
    L_0x009a:
        r14 = r12;
        goto L_0x0053;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.graphics.drawable.PathParser.getFloats(java.lang.String):float[]");
    }

    PathParser() throws  {
    }

    private static float[] copyOfRange(float[] $r0, int $i0, int $i1) throws  {
        if ($i0 > $i1) {
            throw new IllegalArgumentException();
        }
        int $i2 = $r0.length;
        if ($i0 < 0 || $i0 > $i2) {
            throw new ArrayIndexOutOfBoundsException();
        }
        $i1 -= $i0;
        float[] $r1 = new float[$i1];
        System.arraycopy($r0, $i0, $r1, 0, Math.min($i1, $i2 - $i0));
        return $r1;
    }

    public static Path createPathFromPathData(String $r0) throws  {
        Path $r3 = new Path();
        PathDataNode[] $r4 = createNodesFromPathData($r0);
        if ($r4 == null) {
            return null;
        }
        try {
            PathDataNode.nodesToPath($r4, $r3);
            return $r3;
        } catch (RuntimeException $r1) {
            throw new RuntimeException("Error in parsing " + $r0, $r1);
        }
    }

    public static PathDataNode[] createNodesFromPathData(String $r0) throws  {
        if ($r0 == null) {
            return null;
        }
        int $i0 = 0;
        int $i1 = 1;
        ArrayList $r1 = new ArrayList();
        while ($i1 < $r0.length()) {
            $i1 = nextStart($r0, $i1);
            String $r3 = $r0.substring($i0, $i1).trim();
            if ($r3.length() > 0) {
                addNode($r1, $r3.charAt(0), getFloats($r3));
            }
            $i0 = $i1;
            $i1++;
        }
        if ($i1 - $i0 == 1 && $i0 < $r0.length()) {
            addNode($r1, $r0.charAt($i0), new float[0]);
        }
        return (PathDataNode[]) $r1.toArray(new PathDataNode[$r1.size()]);
    }

    public static PathDataNode[] deepCopyNodes(PathDataNode[] $r0) throws  {
        if ($r0 == null) {
            return null;
        }
        PathDataNode[] $r2 = new PathDataNode[$r0.length];
        for (int $i0 = 0; $i0 < $r0.length; $i0++) {
            $r2[$i0] = new PathDataNode($r0[$i0]);
        }
        return $r2;
    }

    public static boolean canMorph(PathDataNode[] $r0, PathDataNode[] $r1) throws  {
        if ($r0 == null) {
            return false;
        }
        if ($r1 == null) {
            return false;
        }
        if ($r0.length != $r1.length) {
            return false;
        }
        for (int $i0 = 0; $i0 < $r0.length; $i0++) {
            if ($r0[$i0].type != $r1[$i0].type) {
                return false;
            }
            if ($r0[$i0].params.length != $r1[$i0].params.length) {
                return false;
            }
        }
        return true;
    }

    public static void updateNodes(PathDataNode[] $r0, PathDataNode[] $r1) throws  {
        for (int $i0 = 0; $i0 < $r1.length; $i0++) {
            $r0[$i0].type = $r1[$i0].type;
            for (int $i1 = 0; $i1 < $r1[$i0].params.length; $i1++) {
                $r0[$i0].params[$i1] = $r1[$i0].params[$i1];
            }
        }
    }

    private static int nextStart(String $r0, int $i0) throws  {
        while ($i0 < $r0.length()) {
            char $c2 = $r0.charAt($i0);
            if ((($c2 - 65) * ($c2 - 90) <= 0 || ($c2 - 97) * ($c2 - 122) <= 0) && $c2 != 'e' && $c2 != 'E') {
                return $i0;
            }
            $i0++;
        }
        return $i0;
    }

    private static void addNode(@Signature({"(", "Ljava/util/ArrayList", "<", "Landroid/support/graphics/drawable/PathParser$PathDataNode;", ">;C[F)V"}) ArrayList<PathDataNode> $r0, @Signature({"(", "Ljava/util/ArrayList", "<", "Landroid/support/graphics/drawable/PathParser$PathDataNode;", ">;C[F)V"}) char $c0, @Signature({"(", "Ljava/util/ArrayList", "<", "Landroid/support/graphics/drawable/PathParser$PathDataNode;", ">;C[F)V"}) float[] $r1) throws  {
        $r0.add(new PathDataNode($c0, $r1));
    }

    private static void extract(String $r0, int $i0, ExtractFloatResult $r1) throws  {
        int $i1;
        boolean $z1 = false;
        $r1.mEndWithNegOrDot = false;
        boolean $z2 = false;
        boolean $z3 = false;
        for ($i1 = $i0; $i1 < $r0.length(); $i1++) {
            boolean $z0 = $z3;
            $z3 = false;
            switch ($r0.charAt($i1)) {
                case ' ':
                case ',':
                    $z1 = true;
                    break;
                case '-':
                    if (!($i1 == $i0 || $z0)) {
                        $z1 = true;
                        $r1.mEndWithNegOrDot = true;
                        break;
                    }
                case '.':
                    if (!$z2) {
                        $z2 = true;
                        break;
                    }
                    $z1 = true;
                    $r1.mEndWithNegOrDot = true;
                    break;
                case 'E':
                case 'e':
                    $z3 = true;
                    break;
                default:
                    break;
            }
            if ($z1) {
                $r1.mEndPosition = $i1;
            }
        }
        $r1.mEndPosition = $i1;
    }
}
