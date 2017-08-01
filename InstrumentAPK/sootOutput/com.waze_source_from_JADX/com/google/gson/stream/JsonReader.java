package com.google.gson.stream;

import com.abaltatech.mcp.mcs.utils.FilenameUtils;
import com.facebook.internal.ServerProtocol;
import com.google.gson.internal.JsonReaderInternalAccess;
import com.google.gson.internal.bind.JsonTreeReader;
import com.waze.analytics.AnalyticsEvents;
import java.io.Closeable;
import java.io.IOException;
import java.io.Reader;

public class JsonReader implements Closeable {
    private static final long MIN_INCOMPLETE_INTEGER = -922337203685477580L;
    private static final char[] NON_EXECUTE_PREFIX = ")]}'\n".toCharArray();
    private static final int NUMBER_CHAR_DECIMAL = 3;
    private static final int NUMBER_CHAR_DIGIT = 2;
    private static final int NUMBER_CHAR_EXP_DIGIT = 7;
    private static final int NUMBER_CHAR_EXP_E = 5;
    private static final int NUMBER_CHAR_EXP_SIGN = 6;
    private static final int NUMBER_CHAR_FRACTION_DIGIT = 4;
    private static final int NUMBER_CHAR_NONE = 0;
    private static final int NUMBER_CHAR_SIGN = 1;
    private static final int PEEKED_BEGIN_ARRAY = 3;
    private static final int PEEKED_BEGIN_OBJECT = 1;
    private static final int PEEKED_BUFFERED = 11;
    private static final int PEEKED_DOUBLE_QUOTED = 9;
    private static final int PEEKED_DOUBLE_QUOTED_NAME = 13;
    private static final int PEEKED_END_ARRAY = 4;
    private static final int PEEKED_END_OBJECT = 2;
    private static final int PEEKED_EOF = 17;
    private static final int PEEKED_FALSE = 6;
    private static final int PEEKED_LONG = 15;
    private static final int PEEKED_NONE = 0;
    private static final int PEEKED_NULL = 7;
    private static final int PEEKED_NUMBER = 16;
    private static final int PEEKED_SINGLE_QUOTED = 8;
    private static final int PEEKED_SINGLE_QUOTED_NAME = 12;
    private static final int PEEKED_TRUE = 5;
    private static final int PEEKED_UNQUOTED = 10;
    private static final int PEEKED_UNQUOTED_NAME = 14;
    private final char[] buffer = new char[1024];
    private final Reader in;
    private boolean lenient = false;
    private int limit = 0;
    private int lineNumber = 0;
    private int lineStart = 0;
    private int[] pathIndices;
    private String[] pathNames;
    int peeked = 0;
    private long peekedLong;
    private int peekedNumberLength;
    private String peekedString;
    private int pos = 0;
    private int[] stack = new int[32];
    private int stackSize = 0;

    static class C10581 extends JsonReaderInternalAccess {
        C10581() throws  {
        }

        public void promoteNameToValue(JsonReader $r1) throws IOException {
            if ($r1 instanceof JsonTreeReader) {
                ((JsonTreeReader) $r1).promoteNameToValue();
                return;
            }
            int $i0 = $r1.peeked;
            if ($i0 == 0) {
                $i0 = $r1.doPeek();
            }
            if ($i0 == 13) {
                $r1.peeked = 9;
            } else if ($i0 == 12) {
                $r1.peeked = 8;
            } else if ($i0 == 14) {
                $r1.peeked = 10;
            } else {
                throw new IllegalStateException("Expected a name but was " + $r1.peek() + " " + " at line " + $r1.getLineNumber() + " column " + $r1.getColumnNumber() + " path " + $r1.getPath());
            }
        }
    }

    private int nextNonWhitespace(boolean r14) throws java.io.IOException {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:11:0x0054
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
        r13 = this;
        r0 = r13.buffer;
        r1 = r13.pos;
        r2 = r13.limit;
    L_0x0006:
        if (r1 != r2) goto L_0x0042;
    L_0x0008:
        r13.pos = r1;
        r4 = 1;
        r3 = r13.fillBuffer(r4);
        if (r3 != 0) goto L_0x003e;
    L_0x0011:
        if (r14 == 0) goto L_0x00d8;
    L_0x0013:
        r5 = new java.io.EOFException;
        r6 = new java.lang.StringBuilder;
        r6.<init>();
        r7 = "End of input at line ";
        r6 = r6.append(r7);
        r8 = r13.getLineNumber();
        r6 = r6.append(r8);
        r7 = " column ";
        r6 = r6.append(r7);
        r8 = r13.getColumnNumber();
        r6 = r6.append(r8);
        r9 = r6.toString();
        r5.<init>(r9);
        throw r5;
    L_0x003e:
        r1 = r13.pos;
        r2 = r13.limit;
    L_0x0042:
        r8 = r1 + 1;
        r10 = r0[r1];
        r4 = 10;
        if (r10 != r4) goto L_0x0058;
    L_0x004a:
        r1 = r13.lineNumber;
        r1 = r1 + 1;
        r13.lineNumber = r1;
        r13.lineStart = r8;
        r1 = r8;
        goto L_0x0006;
        goto L_0x0058;
    L_0x0055:
        goto L_0x0006;
    L_0x0058:
        r4 = 32;
        if (r10 == r4) goto L_0x00da;
    L_0x005c:
        r4 = 13;
        if (r10 == r4) goto L_0x00da;
    L_0x0060:
        goto L_0x0064;
    L_0x0061:
        goto L_0x0006;
    L_0x0064:
        r4 = 9;
        if (r10 != r4) goto L_0x0072;
    L_0x0068:
        r1 = r8;
        goto L_0x006d;
    L_0x006a:
        goto L_0x0006;
    L_0x006d:
        goto L_0x0006;
        goto L_0x0072;
    L_0x006f:
        goto L_0x0006;
    L_0x0072:
        r4 = 47;
        if (r10 != r4) goto L_0x00c4;
    L_0x0076:
        r13.pos = r8;
        if (r8 != r2) goto L_0x008e;
    L_0x007a:
        r8 = r13.pos;
        r8 = r8 + -1;
        r13.pos = r8;
        r4 = 2;
        r3 = r13.fillBuffer(r4);
        r8 = r13.pos;
        r8 = r8 + 1;
        r13.pos = r8;
        if (r3 != 0) goto L_0x008e;
    L_0x008d:
        return r10;
    L_0x008e:
        r13.checkLenient();
        r8 = r13.pos;
        r11 = r0[r8];
        switch(r11) {
            case 42: goto L_0x009a;
            case 47: goto L_0x00b6;
            default: goto L_0x0098;
        };
    L_0x0098:
        goto L_0x0099;
    L_0x0099:
        return r10;
    L_0x009a:
        r8 = r13.pos;
        r8 = r8 + 1;
        r13.pos = r8;
        r7 = "*/";
        r3 = r13.skipTo(r7);
        if (r3 != 0) goto L_0x00af;
    L_0x00a8:
        r7 = "Unterminated comment";
        r12 = r13.syntaxError(r7);
        throw r12;
    L_0x00af:
        r8 = r13.pos;
        r1 = r8 + 2;
        r2 = r13.limit;
        goto L_0x0055;
    L_0x00b6:
        r8 = r13.pos;
        r8 = r8 + 1;
        r13.pos = r8;
        r13.skipToEndOfLine();
        r1 = r13.pos;
        r2 = r13.limit;
        goto L_0x0061;
    L_0x00c4:
        r4 = 35;
        if (r10 != r4) goto L_0x00d5;
    L_0x00c8:
        r13.pos = r8;
        r13.checkLenient();
        r13.skipToEndOfLine();
        r1 = r13.pos;
        r2 = r13.limit;
        goto L_0x006a;
    L_0x00d5:
        r13.pos = r8;
        return r10;
    L_0x00d8:
        r4 = -1;
        return r4;
    L_0x00da:
        r1 = r8;
        goto L_0x006f;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.gson.stream.JsonReader.nextNonWhitespace(boolean):int");
    }

    private int peekNumber() throws java.io.IOException {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:48:0x0093 in {6, 14, 17, 18, 29, 31, 32, 35, 37, 40, 42, 47, 49, 51, 54, 56, 60, 66, 72, 73, 74, 75, 78, 82, 83, 92, 94} preds:[]
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.computeDominators(BlockProcessor.java:129)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:48)
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
        r24 = this;
        r0 = r24;
        r4 = r0.buffer;
        r0 = r24;
        r5 = r0.pos;
        r0 = r24;
        r6 = r0.limit;
        r7 = 0;
        r9 = 0;
        r10 = 1;
        r11 = 0;
        r12 = 0;
    L_0x0012:
        r13 = r5 + r12;
        if (r13 != r6) goto L_0x0056;
    L_0x0016:
        r5 = r4.length;
        if (r12 != r5) goto L_0x001b;
    L_0x0019:
        r14 = 0;
        return r14;
    L_0x001b:
        r5 = r12 + 1;
        r0 = r24;
        r15 = r0.fillBuffer(r5);
        if (r15 != 0) goto L_0x004e;
    L_0x0025:
        r14 = 2;
        if (r11 != r14) goto L_0x00f2;
    L_0x0028:
        if (r10 == 0) goto L_0x00f2;
    L_0x002a:
        r17 = -9223372036854775808;
        r16 = (r7 > r17 ? 1 : (r7 == r17 ? 0 : -1));
        if (r16 != 0) goto L_0x0035;
    L_0x0033:
        if (r9 == 0) goto L_0x00f2;
    L_0x0035:
        if (r9 == 0) goto L_0x00f0;
    L_0x0037:
        r0 = r24;
        r0.peekedLong = r7;
        r0 = r24;
        r5 = r0.pos;
        r12 = r5 + r12;
        r0 = r24;
        r0.pos = r12;
        r14 = 15;
        r0 = r24;
        r0.peeked = r14;
        r14 = 15;
        return r14;
    L_0x004e:
        r0 = r24;
        r5 = r0.pos;
        r0 = r24;
        r6 = r0.limit;
    L_0x0056:
        r13 = r5 + r12;
        r19 = r4[r13];
        switch(r19) {
            case 43: goto L_0x0084;
            case 45: goto L_0x0076;
            case 46: goto L_0x0099;
            case 69: goto L_0x008b;
            case 101: goto L_0x008b;
            default: goto L_0x005d;
        };
    L_0x005d:
        goto L_0x005e;
    L_0x005e:
        r14 = 48;
        r0 = r19;
        if (r0 < r14) goto L_0x006a;
    L_0x0064:
        r14 = 57;
        r0 = r19;
        if (r0 <= r14) goto L_0x00a0;
    L_0x006a:
        r0 = r24;
        r1 = r19;
        r15 = r0.isLiteral(r1);
        if (r15 == 0) goto L_0x0025;
    L_0x0074:
        r14 = 0;
        return r14;
    L_0x0076:
        if (r11 != 0) goto L_0x007d;
    L_0x0078:
        r9 = 1;
        r11 = 1;
    L_0x007a:
        r12 = r12 + 1;
        goto L_0x0012;
    L_0x007d:
        r14 = 5;
        if (r11 != r14) goto L_0x0082;
    L_0x0080:
        r11 = 6;
        goto L_0x007a;
    L_0x0082:
        r14 = 0;
        return r14;
    L_0x0084:
        r14 = 5;
        if (r11 != r14) goto L_0x0089;
    L_0x0087:
        r11 = 6;
        goto L_0x007a;
    L_0x0089:
        r14 = 0;
        return r14;
    L_0x008b:
        r14 = 2;
        if (r11 == r14) goto L_0x0091;
    L_0x008e:
        r14 = 4;
        if (r11 != r14) goto L_0x0097;
    L_0x0091:
        r11 = 5;
        goto L_0x007a;
        goto L_0x0097;
    L_0x0094:
        goto L_0x0037;
    L_0x0097:
        r14 = 0;
        return r14;
    L_0x0099:
        r14 = 2;
        if (r11 != r14) goto L_0x009e;
    L_0x009c:
        r11 = 3;
        goto L_0x007a;
    L_0x009e:
        r14 = 0;
        return r14;
    L_0x00a0:
        r14 = 1;
        if (r11 == r14) goto L_0x00a5;
    L_0x00a3:
        if (r11 != 0) goto L_0x00ab;
    L_0x00a5:
        r13 = r19 + -48;
        r13 = -r13;
        r7 = (long) r13;
        r11 = 2;
        goto L_0x007a;
    L_0x00ab:
        r14 = 2;
        if (r11 != r14) goto L_0x00e3;
    L_0x00ae:
        r17 = 0;
        r16 = (r7 > r17 ? 1 : (r7 == r17 ? 0 : -1));
        if (r16 != 0) goto L_0x00b6;
    L_0x00b4:
        r14 = 0;
        return r14;
    L_0x00b6:
        r17 = 10;
        r20 = r17 * r7;
        r13 = r19 + -48;
        r0 = (long) r13;
        r22 = r0;
        r0 = r20;
        r2 = r22;
        r0 = r0 - r2;
        r20 = r0;
        r17 = -922337203685477580; // 0xf333333333333334 float:4.1723254E-8 double:-8.390303882365713E246;
        r16 = (r7 > r17 ? 1 : (r7 == r17 ? 0 : -1));
        if (r16 > 0) goto L_0x00dc;
    L_0x00cf:
        r17 = -922337203685477580; // 0xf333333333333334 float:4.1723254E-8 double:-8.390303882365713E246;
        r16 = (r7 > r17 ? 1 : (r7 == r17 ? 0 : -1));
        if (r16 != 0) goto L_0x00e1;
    L_0x00d8:
        r16 = (r20 > r7 ? 1 : (r20 == r7 ? 0 : -1));
        if (r16 >= 0) goto L_0x00e1;
    L_0x00dc:
        r15 = 1;
    L_0x00dd:
        r10 = r10 & r15;
        r7 = r20;
        goto L_0x007a;
    L_0x00e1:
        r15 = 0;
        goto L_0x00dd;
    L_0x00e3:
        r14 = 3;
        if (r11 != r14) goto L_0x00e8;
    L_0x00e6:
        r11 = 4;
        goto L_0x007a;
    L_0x00e8:
        r14 = 5;
        if (r11 == r14) goto L_0x00ee;
    L_0x00eb:
        r14 = 6;
        if (r11 != r14) goto L_0x007a;
    L_0x00ee:
        r11 = 7;
        goto L_0x007a;
    L_0x00f0:
        r7 = -r7;
        goto L_0x0094;
    L_0x00f2:
        r14 = 2;
        if (r11 == r14) goto L_0x00fb;
    L_0x00f5:
        r14 = 4;
        if (r11 == r14) goto L_0x00fb;
    L_0x00f8:
        r14 = 7;
        if (r11 != r14) goto L_0x0108;
    L_0x00fb:
        r0 = r24;
        r0.peekedNumberLength = r12;
        r14 = 16;
        r0 = r24;
        r0.peeked = r14;
        r14 = 16;
        return r14;
    L_0x0108:
        r14 = 0;
        return r14;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.gson.stream.JsonReader.peekNumber():int");
    }

    public int nextInt() throws java.io.IOException {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:35:0x01bc in {2, 8, 10, 13, 17, 24, 29, 30, 32, 34, 36} preds:[]
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.computeDominators(BlockProcessor.java:129)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:48)
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
        r25 = this;
        r0 = r25;
        r2 = r0.peeked;
        if (r2 != 0) goto L_0x000c;
    L_0x0006:
        r0 = r25;
        r2 = r0.doPeek();
    L_0x000c:
        r3 = 15;
        if (r2 != r3) goto L_0x0081;
    L_0x0010:
        r0 = r25;
        r4 = r0.peekedLong;
        r2 = (int) r4;
        r0 = r25;
        r4 = r0.peekedLong;
        r6 = (long) r2;
        r8 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1));
        if (r8 == 0) goto L_0x006b;
    L_0x001e:
        r9 = new java.lang.NumberFormatException;
        r10 = new java.lang.StringBuilder;
        r10.<init>();
        r11 = "Expected an int but was ";
        r10 = r10.append(r11);
        r0 = r25;
        r4 = r0.peekedLong;
        r10 = r10.append(r4);
        r11 = " at line ";
        r10 = r10.append(r11);
        r0 = r25;
        r2 = r0.getLineNumber();
        r10 = r10.append(r2);
        r11 = " column ";
        r10 = r10.append(r11);
        r0 = r25;
        r2 = r0.getColumnNumber();
        r10 = r10.append(r2);
        r11 = " path ";
        r10 = r10.append(r11);
        r0 = r25;
        r12 = r0.getPath();
        r10 = r10.append(r12);
        r12 = r10.toString();
        r9.<init>(r12);
        throw r9;
    L_0x006b:
        r3 = 0;
        r0 = r25;
        r0.peeked = r3;
        r0 = r25;
        r13 = r0.pathIndices;
        r0 = r25;
        r14 = r0.stackSize;
        r14 = r14 + -1;
        r15 = r13[r14];
        r15 = r15 + 1;
        r13[r14] = r15;
        return r2;
    L_0x0081:
        r3 = 16;
        if (r2 != r3) goto L_0x0110;
    L_0x0085:
        r12 = new java.lang.String;
        r0 = r25;
        r0 = r0.buffer;
        r16 = r0;
        r0 = r25;
        r2 = r0.pos;
        r0 = r25;
        r14 = r0.peekedNumberLength;
        r0 = r16;
        r12.<init>(r0, r2, r14);
        r0 = r25;
        r0.peekedString = r12;
        r0 = r25;
        r2 = r0.pos;
        r0 = r25;
        r14 = r0.peekedNumberLength;
        r2 = r2 + r14;
        r0 = r25;
        r0.pos = r2;
    L_0x00ab:
        r3 = 11;
        r0 = r25;
        r0.peeked = r3;
        r0 = r25;
        r12 = r0.peekedString;
        r17 = java.lang.Double.parseDouble(r12);
        r0 = r17;
        r2 = (int) r0;
        r0 = (double) r2;
        r19 = r0;
        r8 = (r19 > r17 ? 1 : (r19 == r17 ? 0 : -1));
        if (r8 == 0) goto L_0x019e;
    L_0x00c3:
        r9 = new java.lang.NumberFormatException;
        r10 = new java.lang.StringBuilder;
        r10.<init>();
        r11 = "Expected an int but was ";
        r10 = r10.append(r11);
        r0 = r25;
        r12 = r0.peekedString;
        r10 = r10.append(r12);
        r11 = " at line ";
        r10 = r10.append(r11);
        r0 = r25;
        r2 = r0.getLineNumber();
        r10 = r10.append(r2);
        r11 = " column ";
        r10 = r10.append(r11);
        r0 = r25;
        r2 = r0.getColumnNumber();
        r10 = r10.append(r2);
        r11 = " path ";
        r10 = r10.append(r11);
        r0 = r25;
        r12 = r0.getPath();
        r10 = r10.append(r12);
        r12 = r10.toString();
        r9.<init>(r12);
        throw r9;
    L_0x0110:
        r3 = 8;
        if (r2 == r3) goto L_0x0118;
    L_0x0114:
        r3 = 9;
        if (r2 != r3) goto L_0x014b;
    L_0x0118:
        r3 = 8;
        if (r2 != r3) goto L_0x0148;
    L_0x011c:
        r21 = 39;
    L_0x011e:
        r0 = r25;
        r1 = r21;
        r12 = r0.nextQuotedValue(r1);
        r0 = r25;
        r0.peekedString = r12;
        r0 = r25;
        r12 = r0.peekedString;
        r2 = java.lang.Integer.parseInt(r12);	 Catch:{ NumberFormatException -> 0x01c0 }
        r3 = 0;
        r0 = r25;
        r0.peeked = r3;
        r0 = r25;
        r13 = r0.pathIndices;
        r0 = r25;
        r14 = r0.stackSize;
        r14 = r14 + -1;
        r15 = r13[r14];
        r15 = r15 + 1;
        r13[r14] = r15;
        return r2;
    L_0x0148:
        r21 = 34;
        goto L_0x011e;
    L_0x014b:
        r22 = new java.lang.IllegalStateException;
        r10 = new java.lang.StringBuilder;
        r10.<init>();
        r11 = "Expected an int but was ";
        r10 = r10.append(r11);
        r0 = r25;
        r23 = r0.peek();
        r0 = r23;
        r10 = r10.append(r0);
        r11 = " at line ";
        r10 = r10.append(r11);
        r0 = r25;
        r2 = r0.getLineNumber();
        r10 = r10.append(r2);
        r11 = " column ";
        r10 = r10.append(r11);
        r0 = r25;
        r2 = r0.getColumnNumber();
        r10 = r10.append(r2);
        r11 = " path ";
        r10 = r10.append(r11);
        r0 = r25;
        r12 = r0.getPath();
        r10 = r10.append(r12);
        r12 = r10.toString();
        r0 = r22;
        r0.<init>(r12);
        throw r22;
    L_0x019e:
        r24 = 0;
        r0 = r24;
        r1 = r25;
        r1.peekedString = r0;
        r3 = 0;
        r0 = r25;
        r0.peeked = r3;
        r0 = r25;
        r13 = r0.pathIndices;
        r0 = r25;
        r14 = r0.stackSize;
        r14 = r14 + -1;
        r15 = r13[r14];
        r15 = r15 + 1;
        r13[r14] = r15;
        return r2;
        goto L_0x01c0;
    L_0x01bd:
        goto L_0x00ab;
    L_0x01c0:
        r9 = move-exception;
        goto L_0x01bd;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.gson.stream.JsonReader.nextInt():int");
    }

    static {
        JsonReaderInternalAccess.INSTANCE = new C10581();
    }

    public JsonReader(Reader $r1) throws  {
        int[] $r3 = this.stack;
        int $i0 = this.stackSize;
        this.stackSize = $i0 + 1;
        $r3[$i0] = 6;
        this.pathNames = new String[32];
        this.pathIndices = new int[32];
        if ($r1 == null) {
            throw new NullPointerException("in == null");
        }
        this.in = $r1;
    }

    public final void setLenient(boolean $z0) throws  {
        this.lenient = $z0;
    }

    public final boolean isLenient() throws  {
        return this.lenient;
    }

    public void beginArray() throws IOException {
        int $i0 = this.peeked;
        if ($i0 == 0) {
            $i0 = doPeek();
        }
        if ($i0 == 3) {
            push(1);
            this.pathIndices[this.stackSize - 1] = 0;
            this.peeked = 0;
            return;
        }
        throw new IllegalStateException("Expected BEGIN_ARRAY but was " + peek() + " at line " + getLineNumber() + " column " + getColumnNumber() + " path " + getPath());
    }

    public void endArray() throws IOException {
        int $i0 = this.peeked;
        if ($i0 == 0) {
            $i0 = doPeek();
        }
        if ($i0 == 4) {
            this.stackSize--;
            int[] $r1 = this.pathIndices;
            $i0 = this.stackSize - 1;
            $r1[$i0] = $r1[$i0] + 1;
            this.peeked = 0;
            return;
        }
        throw new IllegalStateException("Expected END_ARRAY but was " + peek() + " at line " + getLineNumber() + " column " + getColumnNumber() + " path " + getPath());
    }

    public void beginObject() throws IOException {
        int $i0 = this.peeked;
        if ($i0 == 0) {
            $i0 = doPeek();
        }
        if ($i0 == 1) {
            push(3);
            this.peeked = 0;
            return;
        }
        throw new IllegalStateException("Expected BEGIN_OBJECT but was " + peek() + " at line " + getLineNumber() + " column " + getColumnNumber() + " path " + getPath());
    }

    public void endObject() throws IOException {
        int $i0 = this.peeked;
        if ($i0 == 0) {
            $i0 = doPeek();
        }
        if ($i0 == 2) {
            this.stackSize--;
            this.pathNames[this.stackSize] = null;
            int[] $r2 = this.pathIndices;
            $i0 = this.stackSize - 1;
            $r2[$i0] = $r2[$i0] + 1;
            this.peeked = 0;
            return;
        }
        throw new IllegalStateException("Expected END_OBJECT but was " + peek() + " at line " + getLineNumber() + " column " + getColumnNumber() + " path " + getPath());
    }

    public boolean hasNext() throws IOException {
        int $i0 = this.peeked;
        if ($i0 == 0) {
            $i0 = doPeek();
        }
        return ($i0 == 2 || $i0 == 4) ? false : true;
    }

    public JsonToken peek() throws IOException {
        int $i0 = this.peeked;
        if ($i0 == 0) {
            $i0 = doPeek();
        }
        switch ($i0) {
            case 1:
                return JsonToken.BEGIN_OBJECT;
            case 2:
                return JsonToken.END_OBJECT;
            case 3:
                return JsonToken.BEGIN_ARRAY;
            case 4:
                return JsonToken.END_ARRAY;
            case 5:
            case 6:
                return JsonToken.BOOLEAN;
            case 7:
                return JsonToken.NULL;
            case 8:
            case 9:
            case 10:
            case 11:
                return JsonToken.STRING;
            case 12:
            case 13:
            case 14:
                return JsonToken.NAME;
            case 15:
            case 16:
                return JsonToken.NUMBER;
            case 17:
                return JsonToken.END_DOCUMENT;
            default:
                throw new AssertionError();
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    int doPeek() throws java.io.IOException {
        /*
        r11 = this;
        r0 = r11.stack;
        r1 = r11.stackSize;
        r1 = r1 + -1;
        r1 = r0[r1];
        r2 = 1;
        if (r1 != r2) goto L_0x002a;
    L_0x000b:
        r0 = r11.stack;
        r3 = r11.stackSize;
        r3 = r3 + -1;
        r2 = 2;
        r0[r3] = r2;
    L_0x0014:
        r2 = 1;
        r3 = r11.nextNonWhitespace(r2);
        switch(r3) {
            case 34: goto L_0x0169;
            case 39: goto L_0x015f;
            case 44: goto L_0x0144;
            case 59: goto L_0x0144;
            case 91: goto L_0x0170;
            case 93: goto L_0x013c;
            case 123: goto L_0x0175;
            default: goto L_0x001c;
        };
    L_0x001c:
        goto L_0x001d;
    L_0x001d:
        r1 = r11.pos;
        r1 = r1 + -1;
        r11.pos = r1;
        r1 = r11.peekKeyword();
        if (r1 == 0) goto L_0x017a;
    L_0x0029:
        return r1;
    L_0x002a:
        r2 = 2;
        if (r1 != r2) goto L_0x0046;
    L_0x002d:
        r2 = 1;
        r3 = r11.nextNonWhitespace(r2);
        switch(r3) {
            case 44: goto L_0x0014;
            case 59: goto L_0x0042;
            case 93: goto L_0x003d;
            default: goto L_0x0035;
        };
    L_0x0035:
        goto L_0x0036;
    L_0x0036:
        r5 = "Unterminated array";
        r4 = r11.syntaxError(r5);
        throw r4;
    L_0x003d:
        r2 = 4;
        r11.peeked = r2;
        r2 = 4;
        return r2;
    L_0x0042:
        r11.checkLenient();
        goto L_0x0014;
    L_0x0046:
        r2 = 3;
        if (r1 == r2) goto L_0x004c;
    L_0x0049:
        r2 = 5;
        if (r1 != r2) goto L_0x00bb;
    L_0x004c:
        r0 = r11.stack;
        r3 = r11.stackSize;
        r3 = r3 + -1;
        r2 = 4;
        r0[r3] = r2;
        r2 = 5;
        if (r1 != r2) goto L_0x0070;
    L_0x0058:
        r2 = 1;
        r3 = r11.nextNonWhitespace(r2);
        switch(r3) {
            case 44: goto L_0x0070;
            case 59: goto L_0x006d;
            case 125: goto L_0x0068;
            default: goto L_0x0060;
        };
    L_0x0060:
        goto L_0x0061;
    L_0x0061:
        r5 = "Unterminated object";
        r4 = r11.syntaxError(r5);
        throw r4;
    L_0x0068:
        r2 = 2;
        r11.peeked = r2;
        r2 = 2;
        return r2;
    L_0x006d:
        r11.checkLenient();
    L_0x0070:
        r2 = 1;
        r3 = r11.nextNonWhitespace(r2);
        switch(r3) {
            case 34: goto L_0x0094;
            case 39: goto L_0x009b;
            case 125: goto L_0x00a5;
            default: goto L_0x0078;
        };
    L_0x0078:
        goto L_0x0079;
    L_0x0079:
        r11.checkLenient();
        r1 = r11.pos;
        r1 = r1 + -1;
        r11.pos = r1;
        goto L_0x0086;
    L_0x0083:
        goto L_0x0014;
    L_0x0086:
        r6 = (char) r3;
        r7 = r11.isLiteral(r6);
        if (r7 == 0) goto L_0x00b4;
    L_0x008d:
        r2 = 14;
        r11.peeked = r2;
        r2 = 14;
        return r2;
    L_0x0094:
        r2 = 13;
        r11.peeked = r2;
        r2 = 13;
        return r2;
    L_0x009b:
        r11.checkLenient();
        r2 = 12;
        r11.peeked = r2;
        r2 = 12;
        return r2;
    L_0x00a5:
        r2 = 5;
        if (r1 == r2) goto L_0x00ad;
    L_0x00a8:
        r2 = 2;
        r11.peeked = r2;
        r2 = 2;
        return r2;
    L_0x00ad:
        r5 = "Expected name";
        r4 = r11.syntaxError(r5);
        throw r4;
    L_0x00b4:
        r5 = "Expected name";
        r4 = r11.syntaxError(r5);
        throw r4;
    L_0x00bb:
        r2 = 4;
        if (r1 != r2) goto L_0x00f8;
    L_0x00be:
        r0 = r11.stack;
        r3 = r11.stackSize;
        r3 = r3 + -1;
        r2 = 5;
        r0[r3] = r2;
        r2 = 1;
        r3 = r11.nextNonWhitespace(r2);
        switch(r3) {
            case 58: goto L_0x0014;
            case 59: goto L_0x00d0;
            case 60: goto L_0x00d0;
            case 61: goto L_0x00d7;
            default: goto L_0x00cf;
        };
    L_0x00cf:
        goto L_0x00d0;
    L_0x00d0:
        r5 = "Expected ':'";
        r4 = r11.syntaxError(r5);
        throw r4;
    L_0x00d7:
        r11.checkLenient();
        r3 = r11.pos;
        r8 = r11.limit;
        if (r3 < r8) goto L_0x00e7;
    L_0x00e0:
        r2 = 1;
        r7 = r11.fillBuffer(r2);
        if (r7 == 0) goto L_0x0014;
    L_0x00e7:
        r9 = r11.buffer;
        r3 = r11.pos;
        r6 = r9[r3];
        r2 = 62;
        if (r6 != r2) goto L_0x0014;
    L_0x00f1:
        r3 = r11.pos;
        r3 = r3 + 1;
        r11.pos = r3;
        goto L_0x0083;
    L_0x00f8:
        r2 = 6;
        if (r1 != r2) goto L_0x0110;
    L_0x00fb:
        r7 = r11.lenient;
        if (r7 == 0) goto L_0x0102;
    L_0x00ff:
        r11.consumeNonExecutePrefix();
    L_0x0102:
        r0 = r11.stack;
        r3 = r11.stackSize;
        r3 = r3 + -1;
        goto L_0x010c;
    L_0x0109:
        goto L_0x0014;
    L_0x010c:
        r2 = 7;
        r0[r3] = r2;
        goto L_0x0109;
    L_0x0110:
        r2 = 7;
        if (r1 != r2) goto L_0x0130;
    L_0x0113:
        r2 = 0;
        r3 = r11.nextNonWhitespace(r2);
        r2 = -1;
        if (r3 != r2) goto L_0x0122;
    L_0x011b:
        r2 = 17;
        r11.peeked = r2;
        r2 = 17;
        return r2;
    L_0x0122:
        r11.checkLenient();
        r3 = r11.pos;
        r3 = r3 + -1;
        goto L_0x012d;
    L_0x012a:
        goto L_0x0014;
    L_0x012d:
        r11.pos = r3;
        goto L_0x012a;
    L_0x0130:
        r2 = 8;
        if (r1 != r2) goto L_0x0014;
    L_0x0134:
        r10 = new java.lang.IllegalStateException;
        r5 = "JsonReader is closed";
        r10.<init>(r5);
        throw r10;
    L_0x013c:
        r2 = 1;
        if (r1 != r2) goto L_0x0144;
    L_0x013f:
        r2 = 4;
        r11.peeked = r2;
        r2 = 4;
        return r2;
    L_0x0144:
        r2 = 1;
        if (r1 == r2) goto L_0x014a;
    L_0x0147:
        r2 = 2;
        if (r1 != r2) goto L_0x0158;
    L_0x014a:
        r11.checkLenient();
        r1 = r11.pos;
        r1 = r1 + -1;
        r11.pos = r1;
        r2 = 7;
        r11.peeked = r2;
        r2 = 7;
        return r2;
    L_0x0158:
        r5 = "Unexpected value";
        r4 = r11.syntaxError(r5);
        throw r4;
    L_0x015f:
        r11.checkLenient();
        r2 = 8;
        r11.peeked = r2;
        r2 = 8;
        return r2;
    L_0x0169:
        r2 = 9;
        r11.peeked = r2;
        r2 = 9;
        return r2;
    L_0x0170:
        r2 = 3;
        r11.peeked = r2;
        r2 = 3;
        return r2;
    L_0x0175:
        r2 = 1;
        r11.peeked = r2;
        r2 = 1;
        return r2;
    L_0x017a:
        r1 = r11.peekNumber();
        if (r1 != 0) goto L_0x019d;
    L_0x0180:
        r9 = r11.buffer;
        r1 = r11.pos;
        r6 = r9[r1];
        r7 = r11.isLiteral(r6);
        if (r7 != 0) goto L_0x0193;
    L_0x018c:
        r5 = "Expected value";
        r4 = r11.syntaxError(r5);
        throw r4;
    L_0x0193:
        r11.checkLenient();
        r2 = 10;
        r11.peeked = r2;
        r2 = 10;
        return r2;
    L_0x019d:
        return r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.gson.stream.JsonReader.doPeek():int");
    }

    private int peekKeyword() throws IOException {
        String $r2;
        byte $b2;
        char $c1 = this.buffer[this.pos];
        String $r3;
        if ($c1 == 't' || $c1 == 'T') {
            $r2 = ServerProtocol.DIALOG_RETURN_SCOPES_TRUE;
            $r3 = AnalyticsEvents.ANALYTICS_EVENT_VALUE_TRUE;
            $b2 = (byte) 5;
        } else if ($c1 == 'f' || $c1 == 'F') {
            $r2 = "false";
            $r3 = AnalyticsEvents.ANALYTICS_EVENT_VALUE_FALSE;
            $b2 = (byte) 6;
        } else if ($c1 != 'n' && $c1 != 'N') {
            return 0;
        } else {
            $r2 = "null";
            $r3 = "NULL";
            $b2 = (byte) 7;
        }
        int $i0 = $r2.length();
        int $i3 = 1;
        while ($i3 < $i0) {
            if (this.pos + $i3 >= this.limit && !fillBuffer($i3 + 1)) {
                return 0;
            }
            $c1 = this.buffer[this.pos + $i3];
            if ($c1 != $r2.charAt($i3) && $c1 != $r3.charAt($i3)) {
                return 0;
            }
            $i3++;
        }
        if ((this.pos + $i0 < this.limit || fillBuffer($i0 + 1)) && isLiteral(this.buffer[this.pos + $i0])) {
            return 0;
        }
        this.pos += $i0;
        this.peeked = $b2;
        return $b2;
    }

    private boolean isLiteral(char $c0) throws IOException {
        switch ($c0) {
            case '\t':
            case '\n':
            case '\f':
            case '\r':
            case ' ':
            case ',':
            case ':':
            case '[':
            case ']':
            case '{':
            case '}':
                break;
            case '#':
            case '/':
            case ';':
            case '=':
            case '\\':
                checkLenient();
                break;
            default:
                return true;
        }
        return false;
    }

    public String nextName() throws IOException {
        String $r1;
        int $i0 = this.peeked;
        if ($i0 == 0) {
            $i0 = doPeek();
        }
        if ($i0 == 14) {
            $r1 = nextUnquotedValue();
        } else if ($i0 == 12) {
            $r1 = nextQuotedValue('\'');
        } else if ($i0 == 13) {
            $r1 = nextQuotedValue('\"');
        } else {
            throw new IllegalStateException("Expected a name but was " + peek() + " at line " + getLineNumber() + " column " + getColumnNumber() + " path " + getPath());
        }
        this.peeked = 0;
        this.pathNames[this.stackSize - 1] = $r1;
        return $r1;
    }

    public String nextString() throws IOException {
        String $r1;
        int $i0 = this.peeked;
        if ($i0 == 0) {
            $i0 = doPeek();
        }
        if ($i0 == 10) {
            $r1 = nextUnquotedValue();
        } else if ($i0 == 8) {
            $r1 = nextQuotedValue('\'');
        } else if ($i0 == 9) {
            $r1 = nextQuotedValue('\"');
        } else if ($i0 == 11) {
            $r1 = this.peekedString;
            this.peekedString = null;
        } else if ($i0 == 15) {
            $r1 = Long.toString(this.peekedLong);
        } else if ($i0 == 16) {
            $r1 = new String(this.buffer, this.pos, this.peekedNumberLength);
            this.pos += this.peekedNumberLength;
        } else {
            throw new IllegalStateException("Expected a string but was " + peek() + " at line " + getLineNumber() + " column " + getColumnNumber() + " path " + getPath());
        }
        this.peeked = 0;
        int[] $r2 = this.pathIndices;
        $i0 = this.stackSize - 1;
        $r2[$i0] = $r2[$i0] + 1;
        return $r1;
    }

    public boolean nextBoolean() throws IOException {
        int $i0 = this.peeked;
        if ($i0 == 0) {
            $i0 = doPeek();
        }
        int[] $r1;
        if ($i0 == 5) {
            this.peeked = 0;
            $r1 = this.pathIndices;
            $i0 = this.stackSize - 1;
            $r1[$i0] = $r1[$i0] + 1;
            return true;
        } else if ($i0 == 6) {
            this.peeked = 0;
            $r1 = this.pathIndices;
            $i0 = this.stackSize - 1;
            $r1[$i0] = $r1[$i0] + 1;
            return false;
        } else {
            throw new IllegalStateException("Expected a boolean but was " + peek() + " at line " + getLineNumber() + " column " + getColumnNumber() + " path " + getPath());
        }
    }

    public void nextNull() throws IOException {
        int $i0 = this.peeked;
        if ($i0 == 0) {
            $i0 = doPeek();
        }
        if ($i0 == 7) {
            this.peeked = 0;
            int[] $r1 = this.pathIndices;
            $i0 = this.stackSize - 1;
            $r1[$i0] = $r1[$i0] + 1;
            return;
        }
        throw new IllegalStateException("Expected null but was " + peek() + " at line " + getLineNumber() + " column " + getColumnNumber() + " path " + getPath());
    }

    public double nextDouble() throws IOException {
        int $i0 = this.peeked;
        if ($i0 == 0) {
            $i0 = doPeek();
        }
        if ($i0 == 15) {
            this.peeked = 0;
            int[] $r1 = this.pathIndices;
            $i0 = this.stackSize - 1;
            $r1[$i0] = $r1[$i0] + 1;
            return (double) this.peekedLong;
        }
        if ($i0 == 16) {
            this.peekedString = new String(this.buffer, this.pos, this.peekedNumberLength);
            this.pos += this.peekedNumberLength;
        } else if ($i0 == 8 || $i0 == 9) {
            char $c3;
            if ($i0 == 8) {
                $c3 = '\'';
            } else {
                $c3 = '\"';
            }
            this.peekedString = nextQuotedValue($c3);
        } else if ($i0 == 10) {
            this.peekedString = nextUnquotedValue();
        } else if ($i0 != 11) {
            throw new IllegalStateException("Expected a double but was " + peek() + " at line " + getLineNumber() + " column " + getColumnNumber() + " path " + getPath());
        }
        this.peeked = 11;
        double $d0 = Double.parseDouble(this.peekedString);
        if (this.lenient || !(Double.isNaN($d0) || Double.isInfinite($d0))) {
            this.peekedString = null;
            this.peeked = 0;
            $r1 = this.pathIndices;
            $i0 = this.stackSize - 1;
            $r1[$i0] = $r1[$i0] + 1;
            return $d0;
        }
        throw new MalformedJsonException("JSON forbids NaN and infinities: " + $d0 + " at line " + getLineNumber() + " column " + getColumnNumber() + " path " + getPath());
    }

    public long nextLong() throws IOException {
        int $i0 = this.peeked;
        if ($i0 == 0) {
            $i0 = doPeek();
        }
        if ($i0 == 15) {
            this.peeked = 0;
            int[] $r1 = this.pathIndices;
            $i0 = this.stackSize - 1;
            $r1[$i0] = $r1[$i0] + 1;
            return this.peekedLong;
        }
        long $l2;
        if ($i0 == 16) {
            this.peekedString = new String(this.buffer, this.pos, this.peekedNumberLength);
            this.pos += this.peekedNumberLength;
        } else if ($i0 == 8 || $i0 == 9) {
            char $c4;
            if ($i0 == 8) {
                $c4 = '\'';
            } else {
                $c4 = '\"';
            }
            this.peekedString = nextQuotedValue($c4);
            try {
                $l2 = Long.parseLong(this.peekedString);
                this.peeked = 0;
                $r1 = this.pathIndices;
                $i0 = this.stackSize - 1;
                $r1[$i0] = $r1[$i0] + 1;
                return $l2;
            } catch (NumberFormatException e) {
            }
        } else {
            throw new IllegalStateException("Expected a long but was " + peek() + " at line " + getLineNumber() + " column " + getColumnNumber() + " path " + getPath());
        }
        this.peeked = 11;
        double $d0 = Double.parseDouble(this.peekedString);
        $l2 = (long) $d0;
        if (((double) $l2) != $d0) {
            throw new NumberFormatException("Expected a long but was " + this.peekedString + " at line " + getLineNumber() + " column " + getColumnNumber() + " path " + getPath());
        }
        this.peekedString = null;
        this.peeked = 0;
        $r1 = this.pathIndices;
        $i0 = this.stackSize - 1;
        $r1[$i0] = $r1[$i0] + 1;
        return $l2;
    }

    private String nextQuotedValue(char $c0) throws IOException {
        char[] $r1 = this.buffer;
        StringBuilder $r2 = new StringBuilder();
        do {
            int $i2 = this.pos;
            int $i3 = this.limit;
            int $i4 = $i2;
            int $i5 = $i2;
            while ($i5 < $i3) {
                $i2 = $i5 + 1;
                char $c1 = $r1[$i5];
                if ($c1 == $c0) {
                    this.pos = $i2;
                    $r2.append($r1, $i4, ($i2 - $i4) - 1);
                    return $r2.toString();
                }
                if ($c1 == '\\') {
                    this.pos = $i2;
                    $r2.append($r1, $i4, ($i2 - $i4) - 1);
                    $r2.append(readEscapeCharacter());
                    $i2 = this.pos;
                    $i3 = this.limit;
                    $i4 = $i2;
                } else if ($c1 == '\n') {
                    this.lineNumber++;
                    this.lineStart = $i2;
                }
                $i5 = $i2;
            }
            $r2.append($r1, $i4, $i5 - $i4);
            this.pos = $i5;
        } while (fillBuffer(1));
        throw syntaxError("Unterminated string");
    }

    private String nextUnquotedValue() throws IOException {
        StringBuilder $r1 = null;
        int $i0 = 0;
        while (true) {
            String $r3;
            if (this.pos + $i0 < this.limit) {
                switch (this.buffer[this.pos + $i0]) {
                    case '\t':
                    case '\n':
                    case '\f':
                    case '\r':
                    case ' ':
                    case ',':
                    case ':':
                    case '[':
                    case ']':
                    case '{':
                    case '}':
                        break;
                    case '#':
                    case '/':
                    case ';':
                    case '=':
                    case '\\':
                        checkLenient();
                        break;
                    default:
                        $i0++;
                        continue;
                }
            } else if ($i0 >= this.buffer.length) {
                if ($r1 == null) {
                    $r1 = new StringBuilder();
                }
                $r1.append(this.buffer, this.pos, $i0);
                this.pos += $i0;
                $i0 = 0;
                if (fillBuffer(1)) {
                }
            } else if (fillBuffer($i0 + 1)) {
            }
            if ($r1 == null) {
                $r3 = new String(this.buffer, this.pos, $i0);
            } else {
                $r1.append(this.buffer, this.pos, $i0);
                $r3 = $r1.toString();
            }
            this.pos += $i0;
            return $r3;
        }
    }

    private void skipQuotedValue(char $c0) throws IOException {
        char[] $r1 = this.buffer;
        do {
            int $i3 = this.limit;
            int $i4 = this.pos;
            while ($i4 < $i3) {
                int $i2 = $i4 + 1;
                char $c1 = $r1[$i4];
                if ($c1 == $c0) {
                    this.pos = $i2;
                    return;
                }
                if ($c1 == '\\') {
                    this.pos = $i2;
                    readEscapeCharacter();
                    $i2 = this.pos;
                    $i3 = this.limit;
                } else if ($c1 == '\n') {
                    this.lineNumber++;
                    this.lineStart = $i2;
                }
                $i4 = $i2;
            }
            this.pos = $i4;
        } while (fillBuffer(1));
        throw syntaxError("Unterminated string");
    }

    private void skipUnquotedValue() throws IOException {
        do {
            int $i0 = 0;
            while (this.pos + $i0 < this.limit) {
                switch (this.buffer[this.pos + $i0]) {
                    case '\t':
                    case '\n':
                    case '\f':
                    case '\r':
                    case ' ':
                    case ',':
                    case ':':
                    case '[':
                    case ']':
                    case '{':
                    case '}':
                        break;
                    case '#':
                    case '/':
                    case ';':
                    case '=':
                    case '\\':
                        checkLenient();
                        break;
                    default:
                        $i0++;
                }
                this.pos += $i0;
                return;
            }
            this.pos += $i0;
        } while (fillBuffer(1));
    }

    public void close() throws IOException {
        this.peeked = 0;
        this.stack[0] = 8;
        this.stackSize = 1;
        this.in.close();
    }

    public void skipValue() throws IOException {
        int $i1 = 0;
        do {
            int $i0 = this.peeked;
            if ($i0 == 0) {
                $i0 = doPeek();
            }
            if ($i0 == 3) {
                push(1);
                $i1++;
            } else if ($i0 == 1) {
                push(3);
                $i1++;
            } else if ($i0 == 4) {
                this.stackSize--;
                $i1--;
            } else if ($i0 == 2) {
                this.stackSize--;
                $i1--;
            } else if ($i0 == 14 || $i0 == 10) {
                skipUnquotedValue();
            } else if ($i0 == 8 || $i0 == 12) {
                skipQuotedValue('\'');
            } else if ($i0 == 9 || $i0 == 13) {
                skipQuotedValue('\"');
            } else if ($i0 == 16) {
                this.pos += this.peekedNumberLength;
            }
            this.peeked = 0;
        } while ($i1 != 0);
        int[] $r1 = this.pathIndices;
        $i1 = this.stackSize - 1;
        $r1[$i1] = $r1[$i1] + 1;
        this.pathNames[this.stackSize - 1] = "null";
    }

    private void push(int $i0) throws  {
        int[] $r1;
        if (this.stackSize == this.stack.length) {
            int[] $r3 = new int[(this.stackSize * 2)];
            $r1 = new int[(this.stackSize * 2)];
            String[] $r2 = new String[(this.stackSize * 2)];
            System.arraycopy(this.stack, 0, $r3, 0, this.stackSize);
            System.arraycopy(this.pathIndices, 0, $r1, 0, this.stackSize);
            System.arraycopy(this.pathNames, 0, $r2, 0, this.stackSize);
            this.stack = $r3;
            this.pathIndices = $r1;
            this.pathNames = $r2;
        }
        $r1 = this.stack;
        int $i1 = this.stackSize;
        this.stackSize = $i1 + 1;
        $r1[$i1] = $i0;
    }

    private boolean fillBuffer(int $i2) throws IOException {
        char[] $r1 = this.buffer;
        this.lineStart -= this.pos;
        if (this.limit != this.pos) {
            this.limit -= this.pos;
            System.arraycopy($r1, this.pos, $r1, 0, this.limit);
        } else {
            this.limit = 0;
        }
        this.pos = 0;
        do {
            int $i1 = this.in.read($r1, this.limit, $r1.length - this.limit);
            if ($i1 == -1) {
                return false;
            }
            this.limit += $i1;
            if (this.lineNumber == 0 && this.lineStart == 0 && this.limit > 0 && $r1[0] == '﻿') {
                this.pos++;
                this.lineStart++;
                $i2++;
            }
        } while (this.limit < $i2);
        return true;
    }

    int getLineNumber() throws  {
        return this.lineNumber + 1;
    }

    int getColumnNumber() throws  {
        return (this.pos - this.lineStart) + 1;
    }

    private void checkLenient() throws IOException {
        if (!this.lenient) {
            throw syntaxError("Use JsonReader.setLenient(true) to accept malformed JSON");
        }
    }

    private void skipToEndOfLine() throws IOException {
        char $c0;
        do {
            if (this.pos < this.limit || fillBuffer(1)) {
                char[] $r1 = this.buffer;
                int $i2 = this.pos;
                this.pos = $i2 + 1;
                $c0 = $r1[$i2];
                if ($c0 == '\n') {
                    this.lineNumber++;
                    this.lineStart = this.pos;
                    return;
                }
            } else {
                return;
            }
        } while ($c0 != '\r');
    }

    private boolean skipTo(String $r1) throws IOException {
        while (true) {
            if (this.pos + $r1.length() > this.limit && !fillBuffer($r1.length())) {
                return false;
            }
            if (this.buffer[this.pos] == '\n') {
                this.lineNumber++;
                this.lineStart = this.pos + 1;
            } else {
                int $i0 = 0;
                while ($i0 < $r1.length()) {
                    if (this.buffer[this.pos + $i0] == $r1.charAt($i0)) {
                        $i0++;
                    }
                }
                return true;
            }
            this.pos++;
        }
    }

    public String toString() throws  {
        return getClass().getSimpleName() + " at line " + getLineNumber() + " column " + getColumnNumber();
    }

    public String getPath() throws  {
        StringBuilder $r1 = new StringBuilder().append('$');
        int $i0 = this.stackSize;
        for (int $i1 = 0; $i1 < $i0; $i1++) {
            switch (this.stack[$i1]) {
                case 1:
                case 2:
                    $r1.append('[').append(this.pathIndices[$i1]).append(']');
                    break;
                case 3:
                case 4:
                case 5:
                    $r1.append(FilenameUtils.EXTENSION_SEPARATOR);
                    if (this.pathNames[$i1] == null) {
                        break;
                    }
                    $r1.append(this.pathNames[$i1]);
                    break;
                default:
                    break;
            }
        }
        return $r1.toString();
    }

    private char readEscapeCharacter() throws IOException {
        if (this.pos != this.limit || fillBuffer(1)) {
            char[] $r1 = this.buffer;
            int $i1 = this.pos;
            this.pos = $i1 + 1;
            char $c0 = $r1[$i1];
            switch ($c0) {
                case '\n':
                    this.lineNumber++;
                    this.lineStart = this.pos;
                    break;
                case 'b':
                    return '\b';
                case 'f':
                    return '\f';
                case 'n':
                    return '\n';
                case 'r':
                    return '\r';
                case 't':
                    return '\t';
                case 'u':
                    if (this.pos + 4 <= this.limit || fillBuffer(4)) {
                        char $c3 = '\u0000';
                        int $i2 = this.pos;
                        $i1 = $i2 + 4;
                        while ($i2 < $i1) {
                            $c0 = this.buffer[$i2];
                            $c3 = (char) ($c3 << 4);
                            if ($c0 >= '0' && $c0 <= '9') {
                                $c3 = (char) (($c0 - 48) + $c3);
                            } else if ($c0 >= 'a' && $c0 <= 'f') {
                                $c3 = (char) ((($c0 - 97) + 10) + $c3);
                            } else if ($c0 < 'A' || $c0 > 'F') {
                                throw new NumberFormatException("\\u" + new String(this.buffer, this.pos, 4));
                            } else {
                                $c3 = (char) ((($c0 - 65) + 10) + $c3);
                            }
                            $i2++;
                        }
                        this.pos += 4;
                        return $c3;
                    }
                    throw syntaxError("Unterminated escape sequence");
                default:
                    break;
            }
            return $c0;
        }
        throw syntaxError("Unterminated escape sequence");
    }

    private IOException syntaxError(String $r1) throws IOException {
        throw new MalformedJsonException($r1 + " at line " + getLineNumber() + " column " + getColumnNumber() + " path " + getPath());
    }

    private void consumeNonExecutePrefix() throws IOException {
        nextNonWhitespace(true);
        this.pos--;
        if (this.pos + NON_EXECUTE_PREFIX.length <= this.limit || fillBuffer(NON_EXECUTE_PREFIX.length)) {
            int $i0 = 0;
            while ($i0 < NON_EXECUTE_PREFIX.length) {
                if (this.buffer[this.pos + $i0] == NON_EXECUTE_PREFIX[$i0]) {
                    $i0++;
                } else {
                    return;
                }
            }
            this.pos += NON_EXECUTE_PREFIX.length;
        }
    }
}
