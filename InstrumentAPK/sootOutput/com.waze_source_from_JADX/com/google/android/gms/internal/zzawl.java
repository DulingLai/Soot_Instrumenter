package com.google.android.gms.internal;

import com.abaltatech.mcp.mcs.utils.FilenameUtils;
import com.facebook.internal.ServerProtocol;
import com.waze.analytics.AnalyticsEvents;
import java.io.Closeable;
import java.io.IOException;
import java.io.Reader;

/* compiled from: dalvik_source_com.waze.apk */
public class zzawl implements Closeable {
    private static final char[] caE = ")]}'\n".toCharArray();
    private int bga = 0;
    private boolean caF = false;
    private final char[] caG = new char[1024];
    private int caH = 0;
    private int caI = 0;
    private int caJ = 0;
    private long caK;
    private int caL;
    private String caM;
    private int[] caN = new int[32];
    private int caO = 0;
    private String[] caP;
    private int[] caQ;
    private final Reader in;
    private int pos = 0;

    /* compiled from: dalvik_source_com.waze.apk */
    static class C08131 extends zzavq {
        C08131() throws  {
        }

        public void zzi(zzawl $r1) throws IOException {
            boolean $z0 = $r1;
            $r1 = $z0;
            if ($z0 instanceof zzawb) {
                ((zzawb) $r1).hF();
                return;
            }
            int $i0 = $r1.caJ;
            int $i1 = $i0;
            if ($i0 == 0) {
                $i1 = $r1.hP();
            }
            if ($i1 == 13) {
                $r1.caJ = 9;
            } else if ($i1 == 12) {
                $r1.caJ = 8;
            } else if ($i1 == 14) {
                $r1.caJ = 10;
            } else {
                String $r5 = String.valueOf($r1.hC());
                $i0 = $r1.getLineNumber();
                $i1 = $r1.getColumnNumber();
                String $r6 = $r1.getPath();
                throw new IllegalStateException(new StringBuilder((String.valueOf($r5).length() + 70) + String.valueOf($r6).length()).append("Expected a name but was ").append($r5).append(" ").append(" at line ").append($i0).append(" column ").append($i1).append(" path ").append($r6).toString());
            }
        }
    }

    static {
        zzavq.bYx = new C08131();
    }

    public zzawl(Reader $r1) throws  {
        int[] $r3 = this.caN;
        int $i0 = this.caO;
        this.caO = $i0 + 1;
        $r3[$i0] = 6;
        this.caP = new String[32];
        this.caQ = new int[32];
        if ($r1 == null) {
            throw new NullPointerException("in == null");
        }
        this.in = $r1;
    }

    private int getColumnNumber() throws  {
        return (this.pos - this.caI) + 1;
    }

    private int getLineNumber() throws  {
        return this.caH + 1;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int hP() throws java.io.IOException {
        /*
        r11 = this;
        r0 = r11.caN;
        r1 = r11.caO;
        r1 = r1 + -1;
        r1 = r0[r1];
        r2 = 1;
        if (r1 != r2) goto L_0x0032;
    L_0x000b:
        r0 = r11.caN;
        r3 = r11.caO;
        r3 = r3 + -1;
        r2 = 2;
        r0[r3] = r2;
    L_0x0014:
        r2 = 1;
        r3 = r11.zzei(r2);
        switch(r3) {
            case 34: goto L_0x0171;
            case 39: goto L_0x0167;
            case 44: goto L_0x014c;
            case 59: goto L_0x014c;
            case 91: goto L_0x0180;
            case 93: goto L_0x0144;
            case 123: goto L_0x0185;
            default: goto L_0x001c;
        };
    L_0x001c:
        goto L_0x001d;
    L_0x001d:
        r1 = r11.pos;
        r1 = r1 + -1;
        r11.pos = r1;
        r1 = r11.caO;
        r2 = 1;
        if (r1 != r2) goto L_0x002b;
    L_0x0028:
        r11.hU();
    L_0x002b:
        r1 = r11.hQ();
        if (r1 == 0) goto L_0x018a;
    L_0x0031:
        return r1;
    L_0x0032:
        r2 = 2;
        if (r1 != r2) goto L_0x004e;
    L_0x0035:
        r2 = 1;
        r3 = r11.zzei(r2);
        switch(r3) {
            case 44: goto L_0x0014;
            case 59: goto L_0x004a;
            case 93: goto L_0x0045;
            default: goto L_0x003d;
        };
    L_0x003d:
        goto L_0x003e;
    L_0x003e:
        r5 = "Unterminated array";
        r4 = r11.zzyr(r5);
        throw r4;
    L_0x0045:
        r2 = 4;
        r11.caJ = r2;
        r2 = 4;
        return r2;
    L_0x004a:
        r11.hU();
        goto L_0x0014;
    L_0x004e:
        r2 = 3;
        if (r1 == r2) goto L_0x0054;
    L_0x0051:
        r2 = 5;
        if (r1 != r2) goto L_0x00bf;
    L_0x0054:
        r0 = r11.caN;
        r3 = r11.caO;
        r3 = r3 + -1;
        r2 = 4;
        r0[r3] = r2;
        r2 = 5;
        if (r1 != r2) goto L_0x0078;
    L_0x0060:
        r2 = 1;
        r3 = r11.zzei(r2);
        switch(r3) {
            case 44: goto L_0x0078;
            case 59: goto L_0x0075;
            case 125: goto L_0x0070;
            default: goto L_0x0068;
        };
    L_0x0068:
        goto L_0x0069;
    L_0x0069:
        r5 = "Unterminated object";
        r4 = r11.zzyr(r5);
        throw r4;
    L_0x0070:
        r2 = 2;
        r11.caJ = r2;
        r2 = 2;
        return r2;
    L_0x0075:
        r11.hU();
    L_0x0078:
        r2 = 1;
        r3 = r11.zzei(r2);
        switch(r3) {
            case 34: goto L_0x0098;
            case 39: goto L_0x009f;
            case 125: goto L_0x00a9;
            default: goto L_0x0080;
        };
    L_0x0080:
        goto L_0x0081;
    L_0x0081:
        r11.hU();
        r1 = r11.pos;
        r1 = r1 + -1;
        r11.pos = r1;
        r6 = (char) r3;
        r7 = r11.zzm(r6);
        if (r7 == 0) goto L_0x00b8;
    L_0x0091:
        r2 = 14;
        r11.caJ = r2;
        r2 = 14;
        return r2;
    L_0x0098:
        r2 = 13;
        r11.caJ = r2;
        r2 = 13;
        return r2;
    L_0x009f:
        r11.hU();
        r2 = 12;
        r11.caJ = r2;
        r2 = 12;
        return r2;
    L_0x00a9:
        r2 = 5;
        if (r1 == r2) goto L_0x00b1;
    L_0x00ac:
        r2 = 2;
        r11.caJ = r2;
        r2 = 2;
        return r2;
    L_0x00b1:
        r5 = "Expected name";
        r4 = r11.zzyr(r5);
        throw r4;
    L_0x00b8:
        r5 = "Expected name";
        r4 = r11.zzyr(r5);
        throw r4;
    L_0x00bf:
        r2 = 4;
        if (r1 != r2) goto L_0x0100;
    L_0x00c2:
        r0 = r11.caN;
        r3 = r11.caO;
        r3 = r3 + -1;
        r2 = 5;
        r0[r3] = r2;
        r2 = 1;
        r3 = r11.zzei(r2);
        switch(r3) {
            case 58: goto L_0x0014;
            case 59: goto L_0x00d4;
            case 60: goto L_0x00d4;
            case 61: goto L_0x00db;
            default: goto L_0x00d3;
        };
    L_0x00d3:
        goto L_0x00d4;
    L_0x00d4:
        r5 = "Expected ':'";
        r4 = r11.zzyr(r5);
        throw r4;
    L_0x00db:
        r11.hU();
        r3 = r11.pos;
        r8 = r11.bga;
        if (r3 < r8) goto L_0x00eb;
    L_0x00e4:
        r2 = 1;
        r7 = r11.zzarq(r2);
        if (r7 == 0) goto L_0x0014;
    L_0x00eb:
        r9 = r11.caG;
        r3 = r11.pos;
        r6 = r9[r3];
        r2 = 62;
        if (r6 != r2) goto L_0x0014;
    L_0x00f5:
        r3 = r11.pos;
        r3 = r3 + 1;
        goto L_0x00fd;
    L_0x00fa:
        goto L_0x0014;
    L_0x00fd:
        r11.pos = r3;
        goto L_0x00fa;
    L_0x0100:
        r2 = 6;
        if (r1 != r2) goto L_0x0118;
    L_0x0103:
        r7 = r11.caF;
        if (r7 == 0) goto L_0x010a;
    L_0x0107:
        r11.hX();
    L_0x010a:
        r0 = r11.caN;
        r3 = r11.caO;
        r3 = r3 + -1;
        goto L_0x0114;
    L_0x0111:
        goto L_0x0014;
    L_0x0114:
        r2 = 7;
        r0[r3] = r2;
        goto L_0x0111;
    L_0x0118:
        r2 = 7;
        if (r1 != r2) goto L_0x0138;
    L_0x011b:
        r2 = 0;
        r3 = r11.zzei(r2);
        r2 = -1;
        if (r3 != r2) goto L_0x012a;
    L_0x0123:
        r2 = 17;
        r11.caJ = r2;
        r2 = 17;
        return r2;
    L_0x012a:
        r11.hU();
        r3 = r11.pos;
        r3 = r3 + -1;
        goto L_0x0135;
    L_0x0132:
        goto L_0x0014;
    L_0x0135:
        r11.pos = r3;
        goto L_0x0132;
    L_0x0138:
        r2 = 8;
        if (r1 != r2) goto L_0x0014;
    L_0x013c:
        r10 = new java.lang.IllegalStateException;
        r5 = "JsonReader is closed";
        r10.<init>(r5);
        throw r10;
    L_0x0144:
        r2 = 1;
        if (r1 != r2) goto L_0x014c;
    L_0x0147:
        r2 = 4;
        r11.caJ = r2;
        r2 = 4;
        return r2;
    L_0x014c:
        r2 = 1;
        if (r1 == r2) goto L_0x0152;
    L_0x014f:
        r2 = 2;
        if (r1 != r2) goto L_0x0160;
    L_0x0152:
        r11.hU();
        r1 = r11.pos;
        r1 = r1 + -1;
        r11.pos = r1;
        r2 = 7;
        r11.caJ = r2;
        r2 = 7;
        return r2;
    L_0x0160:
        r5 = "Unexpected value";
        r4 = r11.zzyr(r5);
        throw r4;
    L_0x0167:
        r11.hU();
        r2 = 8;
        r11.caJ = r2;
        r2 = 8;
        return r2;
    L_0x0171:
        r1 = r11.caO;
        r2 = 1;
        if (r1 != r2) goto L_0x0179;
    L_0x0176:
        r11.hU();
    L_0x0179:
        r2 = 9;
        r11.caJ = r2;
        r2 = 9;
        return r2;
    L_0x0180:
        r2 = 3;
        r11.caJ = r2;
        r2 = 3;
        return r2;
    L_0x0185:
        r2 = 1;
        r11.caJ = r2;
        r2 = 1;
        return r2;
    L_0x018a:
        r1 = r11.hR();
        if (r1 != 0) goto L_0x01ad;
    L_0x0190:
        r9 = r11.caG;
        r1 = r11.pos;
        r6 = r9[r1];
        r7 = r11.zzm(r6);
        if (r7 != 0) goto L_0x01a3;
    L_0x019c:
        r5 = "Expected value";
        r4 = r11.zzyr(r5);
        throw r4;
    L_0x01a3:
        r11.hU();
        r2 = 10;
        r11.caJ = r2;
        r2 = 10;
        return r2;
    L_0x01ad:
        return r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzawl.hP():int");
    }

    private int hQ() throws IOException {
        String $r2;
        byte $b2;
        char $c1 = this.caG[this.pos];
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
            if (this.pos + $i3 >= this.bga && !zzarq($i3 + 1)) {
                return 0;
            }
            $c1 = this.caG[this.pos + $i3];
            if ($c1 != $r2.charAt($i3) && $c1 != $r3.charAt($i3)) {
                return 0;
            }
            $i3++;
        }
        if ((this.pos + $i0 < this.bga || zzarq($i0 + 1)) && zzm(this.caG[this.pos + $i0])) {
            return 0;
        }
        this.pos += $i0;
        this.caJ = $b2;
        return $b2;
    }

    private int hR() throws java.io.IOException {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:48:0x0093 in {6, 14, 17, 18, 29, 31, 32, 35, 37, 40, 42, 47, 49, 51, 54, 56, 60, 66, 68, 74, 75, 76, 77, 80, 85, 94, 96} preds:[]
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
        r4 = r0.caG;
        r5 = 0;
        r7 = 0;
        r8 = 1;
        r9 = 0;
        r10 = 0;
        r0 = r24;
        r11 = r0.bga;
        r0 = r24;
        r12 = r0.pos;
    L_0x0012:
        r13 = r12 + r10;
        if (r13 != r11) goto L_0x0056;
    L_0x0016:
        r11 = r4.length;
        if (r10 != r11) goto L_0x001b;
    L_0x0019:
        r14 = 0;
        return r14;
    L_0x001b:
        r11 = r10 + 1;
        r0 = r24;
        r15 = r0.zzarq(r11);
        if (r15 != 0) goto L_0x004e;
    L_0x0025:
        r14 = 2;
        if (r9 != r14) goto L_0x00f7;
    L_0x0028:
        if (r8 == 0) goto L_0x00f7;
    L_0x002a:
        r17 = -9223372036854775808;
        r16 = (r5 > r17 ? 1 : (r5 == r17 ? 0 : -1));
        if (r16 != 0) goto L_0x0035;
    L_0x0033:
        if (r7 == 0) goto L_0x00f7;
    L_0x0035:
        if (r7 == 0) goto L_0x00f5;
    L_0x0037:
        r0 = r24;
        r0.caK = r5;
        r0 = r24;
        r11 = r0.pos;
        r10 = r11 + r10;
        r0 = r24;
        r0.pos = r10;
        r14 = 15;
        r0 = r24;
        r0.caJ = r14;
        r14 = 15;
        return r14;
    L_0x004e:
        r0 = r24;
        r12 = r0.pos;
        r0 = r24;
        r11 = r0.bga;
    L_0x0056:
        r13 = r12 + r10;
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
        r15 = r0.zzm(r1);
        if (r15 == 0) goto L_0x0025;
    L_0x0074:
        r14 = 0;
        return r14;
    L_0x0076:
        if (r9 != 0) goto L_0x007d;
    L_0x0078:
        r9 = 1;
        r7 = 1;
    L_0x007a:
        r10 = r10 + 1;
        goto L_0x0012;
    L_0x007d:
        r14 = 5;
        if (r9 != r14) goto L_0x0082;
    L_0x0080:
        r9 = 6;
        goto L_0x007a;
    L_0x0082:
        r14 = 0;
        return r14;
    L_0x0084:
        r14 = 5;
        if (r9 != r14) goto L_0x0089;
    L_0x0087:
        r9 = 6;
        goto L_0x007a;
    L_0x0089:
        r14 = 0;
        return r14;
    L_0x008b:
        r14 = 2;
        if (r9 == r14) goto L_0x0091;
    L_0x008e:
        r14 = 4;
        if (r9 != r14) goto L_0x0097;
    L_0x0091:
        r9 = 5;
        goto L_0x007a;
        goto L_0x0097;
    L_0x0094:
        goto L_0x0037;
    L_0x0097:
        r14 = 0;
        return r14;
    L_0x0099:
        r14 = 2;
        if (r9 != r14) goto L_0x009e;
    L_0x009c:
        r9 = 3;
        goto L_0x007a;
    L_0x009e:
        r14 = 0;
        return r14;
    L_0x00a0:
        r14 = 1;
        if (r9 == r14) goto L_0x00a5;
    L_0x00a3:
        if (r9 != 0) goto L_0x00ab;
    L_0x00a5:
        r13 = r19 + -48;
        r13 = -r13;
        r5 = (long) r13;
        r9 = 2;
        goto L_0x007a;
    L_0x00ab:
        r14 = 2;
        if (r9 != r14) goto L_0x00e8;
    L_0x00ae:
        r17 = 0;
        r16 = (r5 > r17 ? 1 : (r5 == r17 ? 0 : -1));
        if (r16 != 0) goto L_0x00b6;
    L_0x00b4:
        r14 = 0;
        return r14;
    L_0x00b6:
        r17 = 10;
        r20 = r17 * r5;
        goto L_0x00be;
    L_0x00bb:
        goto L_0x007a;
    L_0x00be:
        r13 = r19 + -48;
        r0 = (long) r13;
        r22 = r0;
        r0 = r20;
        r2 = r22;
        r0 = r0 - r2;
        r20 = r0;
        r17 = -922337203685477580; // 0xf333333333333334 float:4.1723254E-8 double:-8.390303882365713E246;
        r16 = (r5 > r17 ? 1 : (r5 == r17 ? 0 : -1));
        if (r16 > 0) goto L_0x00e0;
    L_0x00d3:
        r17 = -922337203685477580; // 0xf333333333333334 float:4.1723254E-8 double:-8.390303882365713E246;
        r16 = (r5 > r17 ? 1 : (r5 == r17 ? 0 : -1));
        if (r16 != 0) goto L_0x00e6;
    L_0x00dc:
        r16 = (r20 > r5 ? 1 : (r20 == r5 ? 0 : -1));
        if (r16 >= 0) goto L_0x00e6;
    L_0x00e0:
        r15 = 1;
    L_0x00e1:
        r5 = r20;
        r8 = r15 & r8;
        goto L_0x007a;
    L_0x00e6:
        r15 = 0;
        goto L_0x00e1;
    L_0x00e8:
        r14 = 3;
        if (r9 != r14) goto L_0x00ed;
    L_0x00eb:
        r9 = 4;
        goto L_0x007a;
    L_0x00ed:
        r14 = 5;
        if (r9 == r14) goto L_0x00f3;
    L_0x00f0:
        r14 = 6;
        if (r9 != r14) goto L_0x010f;
    L_0x00f3:
        r9 = 7;
        goto L_0x007a;
    L_0x00f5:
        r5 = -r5;
        goto L_0x0094;
    L_0x00f7:
        r14 = 2;
        if (r9 == r14) goto L_0x0100;
    L_0x00fa:
        r14 = 4;
        if (r9 == r14) goto L_0x0100;
    L_0x00fd:
        r14 = 7;
        if (r9 != r14) goto L_0x010d;
    L_0x0100:
        r0 = r24;
        r0.caL = r10;
        r14 = 16;
        r0 = r24;
        r0.caJ = r14;
        r14 = 16;
        return r14;
    L_0x010d:
        r14 = 0;
        return r14;
    L_0x010f:
        goto L_0x00bb;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzawl.hR():int");
    }

    private String hS() throws IOException {
        StringBuilder $r1 = null;
        int $i0 = 0;
        while (true) {
            String $r3;
            if (this.pos + $i0 < this.bga) {
                switch (this.caG[this.pos + $i0]) {
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
                        hU();
                        break;
                    default:
                        $i0++;
                        continue;
                }
            } else if ($i0 >= this.caG.length) {
                if ($r1 == null) {
                    $r1 = new StringBuilder();
                }
                $r1.append(this.caG, this.pos, $i0);
                this.pos = $i0 + this.pos;
                $i0 = !zzarq(1) ? 0 : 0;
            } else if (zzarq($i0 + 1)) {
            }
            if ($r1 == null) {
                $r3 = new String(this.caG, this.pos, $i0);
            } else {
                $r1.append(this.caG, this.pos, $i0);
                $r3 = $r1.toString();
            }
            this.pos = $i0 + this.pos;
            return $r3;
        }
    }

    private void hT() throws IOException {
        do {
            int $i0 = 0;
            while (this.pos + $i0 < this.bga) {
                switch (this.caG[this.pos + $i0]) {
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
                        hU();
                        break;
                    default:
                        $i0++;
                }
                this.pos = $i0 + this.pos;
                return;
            }
            this.pos = $i0 + this.pos;
        } while (zzarq(1));
    }

    private void hU() throws IOException {
        if (!this.caF) {
            throw zzyr("Use JsonReader.setLenient(true) to accept malformed JSON");
        }
    }

    private void hV() throws IOException {
        char $c2;
        do {
            if (this.pos < this.bga || zzarq(1)) {
                char[] $r1 = this.caG;
                int $i1 = this.pos;
                this.pos = $i1 + 1;
                $c2 = $r1[$i1];
                if ($c2 == '\n') {
                    this.caH++;
                    this.caI = this.pos;
                    return;
                }
            } else {
                return;
            }
        } while ($c2 != '\r');
    }

    private char hW() throws IOException {
        if (this.pos != this.bga || zzarq(1)) {
            char[] $r2 = this.caG;
            int $i0 = this.pos;
            this.pos = $i0 + 1;
            char $c2 = $r2[$i0];
            switch ($c2) {
                case '\n':
                    this.caH++;
                    this.caI = this.pos;
                    return $c2;
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
                    if (this.pos + 4 <= this.bga || zzarq(4)) {
                        int $i1 = this.pos;
                        $i0 = $i1 + 4;
                        $c2 = '\u0000';
                        while ($i1 < $i0) {
                            char $c3 = this.caG[$i1];
                            $c2 = (char) ($c2 << 4);
                            if ($c3 >= '0' && $c3 <= '9') {
                                $c2 = (char) ($c2 + ($c3 - 48));
                            } else if ($c3 >= 'a' && $c3 <= 'f') {
                                $c2 = (char) ($c2 + (($c3 - 97) + 10));
                            } else if ($c3 < 'A' || $c3 > 'F') {
                                String $r4 = "\\u";
                                String $r5 = String.valueOf(new String(this.caG, this.pos, 4));
                                throw new NumberFormatException($r5.length() != 0 ? $r4.concat($r5) : new String("\\u"));
                            } else {
                                $c2 = (char) ($c2 + (($c3 - 65) + 10));
                            }
                            $i1++;
                        }
                        this.pos += 4;
                        return $c2;
                    }
                    throw zzyr("Unterminated escape sequence");
                default:
                    return $c2;
            }
        }
        throw zzyr("Unterminated escape sequence");
    }

    private void hX() throws IOException {
        zzei(true);
        this.pos--;
        if (this.pos + caE.length <= this.bga || zzarq(caE.length)) {
            int $i0 = 0;
            while ($i0 < caE.length) {
                if (this.caG[this.pos + $i0] == caE[$i0]) {
                    $i0++;
                } else {
                    return;
                }
            }
            this.pos += caE.length;
        }
    }

    private void zzarp(int $i0) throws  {
        int[] $r1;
        if (this.caO == this.caN.length) {
            $r1 = new int[(this.caO * 2)];
            int[] $r2 = new int[(this.caO * 2)];
            String[] $r3 = new String[(this.caO * 2)];
            System.arraycopy(this.caN, 0, $r1, 0, this.caO);
            System.arraycopy(this.caQ, 0, $r2, 0, this.caO);
            System.arraycopy(this.caP, 0, $r3, 0, this.caO);
            this.caN = $r1;
            this.caQ = $r2;
            this.caP = $r3;
        }
        $r1 = this.caN;
        int $i1 = this.caO;
        this.caO = $i1 + 1;
        $r1[$i1] = $i0;
    }

    private boolean zzarq(int $i2) throws IOException {
        char[] $r1 = this.caG;
        this.caI -= this.pos;
        if (this.bga != this.pos) {
            this.bga -= this.pos;
            System.arraycopy($r1, this.pos, $r1, 0, this.bga);
        } else {
            this.bga = 0;
        }
        this.pos = 0;
        do {
            int $i1 = this.in.read($r1, this.bga, $r1.length - this.bga);
            if ($i1 == -1) {
                return false;
            }
            this.bga = $i1 + this.bga;
            if (this.caH == 0 && this.caI == 0 && this.bga > 0 && $r1[0] == '﻿') {
                this.pos++;
                this.caI++;
                $i2++;
            }
        } while (this.bga < $i2);
        return true;
    }

    private int zzei(boolean r15) throws java.io.IOException {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:13:0x0066 in {6, 7, 11, 12, 14, 18, 22, 25, 32, 36, 40, 47, 49} preds:[]
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
        r14 = this;
        r0 = r14.caG;
        r1 = r14.pos;
        r2 = r14.bga;
    L_0x0006:
        if (r1 != r2) goto L_0x0050;
    L_0x0008:
        r14.pos = r1;
        r4 = 1;
        r3 = r14.zzarq(r4);
        if (r3 != 0) goto L_0x004c;
    L_0x0011:
        if (r15 == 0) goto L_0x00e6;
    L_0x0013:
        r5 = new java.io.EOFException;
        r7 = "End of input at line ";
        r6 = java.lang.String.valueOf(r7);
        r2 = r14.getLineNumber();
        r8 = r14.getColumnNumber();
        r9 = new java.lang.StringBuilder;
        r10 = java.lang.String.valueOf(r6);
        r1 = r10.length();
        r1 = r1 + 30;
        r9.<init>(r1);
        r9 = r9.append(r6);
        r9 = r9.append(r2);
        r7 = " column ";
        r9 = r9.append(r7);
        r9 = r9.append(r8);
        r6 = r9.toString();
        r5.<init>(r6);
        throw r5;
    L_0x004c:
        r1 = r14.pos;
        r2 = r14.bga;
    L_0x0050:
        r8 = r1 + 1;
        r11 = r0[r1];
        r4 = 10;
        if (r11 != r4) goto L_0x006a;
    L_0x0058:
        r1 = r14.caH;
        r1 = r1 + 1;
        r14.caH = r1;
        r14.caI = r8;
        goto L_0x0064;
    L_0x0061:
        goto L_0x0006;
    L_0x0064:
        r1 = r8;
        goto L_0x0006;
        goto L_0x006a;
    L_0x0067:
        goto L_0x0006;
    L_0x006a:
        r4 = 32;
        if (r11 == r4) goto L_0x00e8;
    L_0x006e:
        goto L_0x0072;
    L_0x006f:
        goto L_0x0006;
    L_0x0072:
        r4 = 13;
        if (r11 == r4) goto L_0x00e8;
    L_0x0076:
        goto L_0x007a;
    L_0x0077:
        goto L_0x0006;
    L_0x007a:
        r4 = 9;
        if (r11 != r4) goto L_0x0080;
    L_0x007e:
        r1 = r8;
        goto L_0x0006;
    L_0x0080:
        r4 = 47;
        if (r11 != r4) goto L_0x00d2;
    L_0x0084:
        r14.pos = r8;
        if (r8 != r2) goto L_0x009c;
    L_0x0088:
        r2 = r14.pos;
        r2 = r2 + -1;
        r14.pos = r2;
        r4 = 2;
        r3 = r14.zzarq(r4);
        r2 = r14.pos;
        r2 = r2 + 1;
        r14.pos = r2;
        if (r3 != 0) goto L_0x009c;
    L_0x009b:
        return r11;
    L_0x009c:
        r14.hU();
        r2 = r14.pos;
        r12 = r0[r2];
        switch(r12) {
            case 42: goto L_0x00a8;
            case 47: goto L_0x00c4;
            default: goto L_0x00a6;
        };
    L_0x00a6:
        goto L_0x00a7;
    L_0x00a7:
        return r11;
    L_0x00a8:
        r2 = r14.pos;
        r2 = r2 + 1;
        r14.pos = r2;
        r7 = "*/";
        r3 = r14.zzyq(r7);
        if (r3 != 0) goto L_0x00bd;
    L_0x00b6:
        r7 = "Unterminated comment";
        r13 = r14.zzyr(r7);
        throw r13;
    L_0x00bd:
        r2 = r14.pos;
        r1 = r2 + 2;
        r2 = r14.bga;
        goto L_0x0061;
    L_0x00c4:
        r2 = r14.pos;
        r2 = r2 + 1;
        r14.pos = r2;
        r14.hV();
        r1 = r14.pos;
        r2 = r14.bga;
        goto L_0x0067;
    L_0x00d2:
        r4 = 35;
        if (r11 != r4) goto L_0x00e3;
    L_0x00d6:
        r14.pos = r8;
        r14.hU();
        r14.hV();
        r1 = r14.pos;
        r2 = r14.bga;
        goto L_0x006f;
    L_0x00e3:
        r14.pos = r8;
        return r11;
    L_0x00e6:
        r4 = -1;
        return r4;
    L_0x00e8:
        r1 = r8;
        goto L_0x0077;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzawl.zzei(boolean):int");
    }

    private boolean zzm(char $c0) throws IOException {
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
                hU();
                break;
            default:
                return true;
        }
        return false;
    }

    private String zzn(char $c0) throws IOException {
        char[] $r1 = this.caG;
        StringBuilder $r2 = new StringBuilder();
        do {
            int $i1 = this.pos;
            int $i2 = this.bga;
            int $i3 = $i1;
            while ($i3 < $i2) {
                int $i4 = $i3 + 1;
                char $c5 = $r1[$i3];
                if ($c5 == $c0) {
                    this.pos = $i4;
                    $r2.append($r1, $i1, ($i4 - $i1) - 1);
                    return $r2.toString();
                }
                if ($c5 == '\\') {
                    this.pos = $i4;
                    $r2.append($r1, $i1, ($i4 - $i1) - 1);
                    $r2.append(hW());
                    $i1 = this.pos;
                    $i2 = this.bga;
                    $i4 = $i1;
                } else if ($c5 == '\n') {
                    this.caH++;
                    this.caI = $i4;
                }
                $i3 = $i4;
            }
            $r2.append($r1, $i1, $i3 - $i1);
            this.pos = $i3;
        } while (zzarq(1));
        throw zzyr("Unterminated string");
    }

    private void zzo(char $c0) throws IOException {
        char[] $r1 = this.caG;
        do {
            int $i2 = this.bga;
            int $i3 = this.pos;
            while ($i3 < $i2) {
                int $i1 = $i3 + 1;
                char $c4 = $r1[$i3];
                if ($c4 == $c0) {
                    this.pos = $i1;
                    return;
                }
                if ($c4 == '\\') {
                    this.pos = $i1;
                    hW();
                    $i1 = this.pos;
                    $i2 = this.bga;
                } else if ($c4 == '\n') {
                    this.caH++;
                    this.caI = $i1;
                }
                $i3 = $i1;
            }
            this.pos = $i3;
        } while (zzarq(1));
        throw zzyr("Unterminated string");
    }

    private boolean zzyq(String $r1) throws IOException {
        while (true) {
            if (this.pos + $r1.length() > this.bga && !zzarq($r1.length())) {
                return false;
            }
            if (this.caG[this.pos] == '\n') {
                this.caH++;
                this.caI = this.pos + 1;
            } else {
                int $i0 = 0;
                while ($i0 < $r1.length()) {
                    if (this.caG[this.pos + $i0] == $r1.charAt($i0)) {
                        $i0++;
                    }
                }
                return true;
            }
            this.pos++;
        }
    }

    private IOException zzyr(String $r1) throws IOException {
        int $i0 = getLineNumber();
        int $i1 = getColumnNumber();
        String $r3 = getPath();
        throw new zzawo(new StringBuilder((String.valueOf($r1).length() + 45) + String.valueOf($r3).length()).append($r1).append(" at line ").append($i0).append(" column ").append($i1).append(" path ").append($r3).toString());
    }

    public void beginArray() throws IOException {
        int $i0 = this.caJ;
        if ($i0 == 0) {
            $i0 = hP();
        }
        if ($i0 == 3) {
            zzarp(1);
            this.caQ[this.caO - 1] = 0;
            this.caJ = 0;
            return;
        }
        String $r4 = String.valueOf(hC());
        $i0 = getLineNumber();
        int $i1 = getColumnNumber();
        String $r5 = getPath();
        throw new IllegalStateException(new StringBuilder((String.valueOf($r4).length() + 74) + String.valueOf($r5).length()).append("Expected BEGIN_ARRAY but was ").append($r4).append(" at line ").append($i0).append(" column ").append($i1).append(" path ").append($r5).toString());
    }

    public void beginObject() throws IOException {
        int $i0 = this.caJ;
        if ($i0 == 0) {
            $i0 = hP();
        }
        if ($i0 == 1) {
            zzarp(3);
            this.caJ = 0;
            return;
        }
        String $r3 = String.valueOf(hC());
        $i0 = getLineNumber();
        int $i1 = getColumnNumber();
        String $r4 = getPath();
        throw new IllegalStateException(new StringBuilder((String.valueOf($r3).length() + 75) + String.valueOf($r4).length()).append("Expected BEGIN_OBJECT but was ").append($r3).append(" at line ").append($i0).append(" column ").append($i1).append(" path ").append($r4).toString());
    }

    public void close() throws IOException {
        this.caJ = 0;
        this.caN[0] = 8;
        this.caO = 1;
        this.in.close();
    }

    public void endArray() throws IOException {
        int $i0 = this.caJ;
        if ($i0 == 0) {
            $i0 = hP();
        }
        if ($i0 == 4) {
            this.caO--;
            int[] $r1 = this.caQ;
            $i0 = this.caO - 1;
            $r1[$i0] = $r1[$i0] + 1;
            this.caJ = 0;
            return;
        }
        String $r4 = String.valueOf(hC());
        $i0 = getLineNumber();
        int $i1 = getColumnNumber();
        String $r5 = getPath();
        throw new IllegalStateException(new StringBuilder((String.valueOf($r4).length() + 72) + String.valueOf($r5).length()).append("Expected END_ARRAY but was ").append($r4).append(" at line ").append($i0).append(" column ").append($i1).append(" path ").append($r5).toString());
    }

    public void endObject() throws IOException {
        int $i0 = this.caJ;
        if ($i0 == 0) {
            $i0 = hP();
        }
        if ($i0 == 2) {
            this.caO--;
            this.caP[this.caO] = null;
            int[] $r2 = this.caQ;
            $i0 = this.caO - 1;
            $r2[$i0] = $r2[$i0] + 1;
            this.caJ = 0;
            return;
        }
        String $r5 = String.valueOf(hC());
        $i0 = getLineNumber();
        int $i1 = getColumnNumber();
        String $r6 = getPath();
        throw new IllegalStateException(new StringBuilder((String.valueOf($r5).length() + 73) + String.valueOf($r6).length()).append("Expected END_OBJECT but was ").append($r5).append(" at line ").append($i0).append(" column ").append($i1).append(" path ").append($r6).toString());
    }

    public String getPath() throws  {
        StringBuilder $r1 = new StringBuilder().append('$');
        int $i0 = this.caO;
        for (int $i1 = 0; $i1 < $i0; $i1++) {
            switch (this.caN[$i1]) {
                case 1:
                case 2:
                    $r1.append('[').append(this.caQ[$i1]).append(']');
                    break;
                case 3:
                case 4:
                case 5:
                    $r1.append(FilenameUtils.EXTENSION_SEPARATOR);
                    if (this.caP[$i1] == null) {
                        break;
                    }
                    $r1.append(this.caP[$i1]);
                    break;
                default:
                    break;
            }
        }
        return $r1.toString();
    }

    public zzawm hC() throws IOException {
        int $i0 = this.caJ;
        if ($i0 == 0) {
            $i0 = hP();
        }
        switch ($i0) {
            case 1:
                return zzawm.BEGIN_OBJECT;
            case 2:
                return zzawm.END_OBJECT;
            case 3:
                return zzawm.BEGIN_ARRAY;
            case 4:
                return zzawm.END_ARRAY;
            case 5:
            case 6:
                return zzawm.BOOLEAN;
            case 7:
                return zzawm.NULL;
            case 8:
            case 9:
            case 10:
            case 11:
                return zzawm.STRING;
            case 12:
            case 13:
            case 14:
                return zzawm.NAME;
            case 15:
            case 16:
                return zzawm.NUMBER;
            case 17:
                return zzawm.END_DOCUMENT;
            default:
                throw new AssertionError();
        }
    }

    public boolean hasNext() throws IOException {
        int $i0 = this.caJ;
        if ($i0 == 0) {
            $i0 = hP();
        }
        return ($i0 == 2 || $i0 == 4) ? false : true;
    }

    public final boolean isLenient() throws  {
        return this.caF;
    }

    public boolean nextBoolean() throws IOException {
        int $i0 = this.caJ;
        if ($i0 == 0) {
            $i0 = hP();
        }
        int[] $r1;
        if ($i0 == 5) {
            this.caJ = 0;
            $r1 = this.caQ;
            $i0 = this.caO - 1;
            $r1[$i0] = $r1[$i0] + 1;
            return true;
        } else if ($i0 == 6) {
            this.caJ = 0;
            $r1 = this.caQ;
            $i0 = this.caO - 1;
            $r1[$i0] = $r1[$i0] + 1;
            return false;
        } else {
            String $r4 = String.valueOf(hC());
            $i0 = getLineNumber();
            int $i1 = getColumnNumber();
            String $r5 = getPath();
            throw new IllegalStateException(new StringBuilder((String.valueOf($r4).length() + 72) + String.valueOf($r5).length()).append("Expected a boolean but was ").append($r4).append(" at line ").append($i0).append(" column ").append($i1).append(" path ").append($r5).toString());
        }
    }

    public double nextDouble() throws IOException {
        int $i0 = this.caJ;
        if ($i0 == 0) {
            $i0 = hP();
        }
        if ($i0 == 15) {
            this.caJ = 0;
            int[] $r1 = this.caQ;
            $i0 = this.caO - 1;
            $r1[$i0] = $r1[$i0] + 1;
            return (double) this.caK;
        }
        if ($i0 == 16) {
            this.caM = new String(this.caG, this.pos, this.caL);
            this.pos += this.caL;
        } else if ($i0 == 8 || $i0 == 9) {
            this.caM = zzn($i0 == 8 ? '\'' : '\"');
        } else if ($i0 == 10) {
            this.caM = hS();
        } else if ($i0 != 11) {
            String $r2 = String.valueOf(hC());
            $i0 = getLineNumber();
            int $i1 = getColumnNumber();
            String $r6 = getPath();
            int $i3 = (String.valueOf($r2).length() + 71) + String.valueOf($r6).length();
            int i = $i3;
            throw new IllegalStateException("Expected a double but was " + $r2 + " at line " + $i0 + " column " + $i1 + " path " + $r6);
        }
        this.caJ = 11;
        double $d0 = Double.parseDouble(this.caM);
        if (this.caF || !(Double.isNaN($d0) || Double.isInfinite($d0))) {
            this.caM = null;
            this.caJ = 0;
            $r1 = this.caQ;
            $i0 = this.caO - 1;
            $r1[$i0] = $r1[$i0] + 1;
            return $d0;
        }
        $i0 = getLineNumber();
        $i1 = getColumnNumber();
        $r2 = getPath();
        throw new zzawo(new StringBuilder(String.valueOf($r2).length() + 102).append("JSON forbids NaN and infinities: ").append($d0).append(" at line ").append($i0).append(" column ").append($i1).append(" path ").append($r2).toString());
    }

    public int nextInt() throws IOException {
        int $i0 = this.caJ;
        if ($i0 == 0) {
            $i0 = hP();
        }
        int $i4;
        if ($i0 == 15) {
            $i0 = (int) this.caK;
            if (this.caK != ((long) $i0)) {
                long $l1 = this.caK;
                $i0 = getLineNumber();
                $i4 = getColumnNumber();
                String $r2 = getPath();
                throw new NumberFormatException(new StringBuilder(String.valueOf($r2).length() + 89).append("Expected an int but was ").append($l1).append(" at line ").append($i0).append(" column ").append($i4).append(" path ").append($r2).toString());
            }
            this.caJ = 0;
            int[] $r5 = this.caQ;
            $i4 = this.caO - 1;
            $r5[$i4] = $r5[$i4] + 1;
            return $i0;
        }
        if ($i0 == 16) {
            this.caM = new String(this.caG, this.pos, this.caL);
            this.pos += this.caL;
        } else if ($i0 == 8 || $i0 == 9) {
            this.caM = zzn($i0 == 8 ? '\'' : '\"');
            try {
                $i0 = Integer.parseInt(this.caM);
                this.caJ = 0;
                $r5 = this.caQ;
                $i4 = this.caO - 1;
                $r5[$i4] = $r5[$i4] + 1;
                return $i0;
            } catch (NumberFormatException e) {
            }
        } else {
            $r2 = String.valueOf(hC());
            $i0 = getLineNumber();
            $i4 = getColumnNumber();
            String $r4 = getPath();
            throw new IllegalStateException(new StringBuilder((String.valueOf($r2).length() + 69) + String.valueOf($r4).length()).append("Expected an int but was ").append($r2).append(" at line ").append($i0).append(" column ").append($i4).append(" path ").append($r4).toString());
        }
        this.caJ = 11;
        double $d0 = Double.parseDouble(this.caM);
        $i0 = (int) $d0;
        if (((double) $i0) != $d0) {
            $r2 = this.caM;
            $i0 = getLineNumber();
            $i4 = getColumnNumber();
            $r4 = getPath();
            throw new NumberFormatException(new StringBuilder((String.valueOf($r2).length() + 69) + String.valueOf($r4).length()).append("Expected an int but was ").append($r2).append(" at line ").append($i0).append(" column ").append($i4).append(" path ").append($r4).toString());
        }
        this.caM = null;
        this.caJ = 0;
        $r5 = this.caQ;
        $i4 = this.caO - 1;
        $r5[$i4] = $r5[$i4] + 1;
        return $i0;
    }

    public long nextLong() throws IOException {
        int $i0 = this.caJ;
        if ($i0 == 0) {
            $i0 = hP();
        }
        if ($i0 == 15) {
            this.caJ = 0;
            int[] $r1 = this.caQ;
            $i0 = this.caO - 1;
            $r1[$i0] = $r1[$i0] + 1;
            return this.caK;
        }
        long $l2;
        if ($i0 == 16) {
            this.caM = new String(this.caG, this.pos, this.caL);
            this.pos += this.caL;
        } else if ($i0 == 8 || $i0 == 9) {
            this.caM = zzn($i0 == 8 ? '\'' : '\"');
            try {
                $l2 = Long.parseLong(this.caM);
                this.caJ = 0;
                $r1 = this.caQ;
                $i0 = this.caO - 1;
                $r1[$i0] = $r1[$i0] + 1;
                return $l2;
            } catch (NumberFormatException e) {
            }
        } else {
            String $r2 = String.valueOf(hC());
            $i0 = getLineNumber();
            int $i1 = getColumnNumber();
            String $r5 = getPath();
            throw new IllegalStateException(new StringBuilder((String.valueOf($r2).length() + 69) + String.valueOf($r5).length()).append("Expected a long but was ").append($r2).append(" at line ").append($i0).append(" column ").append($i1).append(" path ").append($r5).toString());
        }
        this.caJ = 11;
        double $d0 = Double.parseDouble(this.caM);
        $l2 = (long) $d0;
        if (((double) $l2) != $d0) {
            $r2 = this.caM;
            $i0 = getLineNumber();
            $i1 = getColumnNumber();
            $r5 = getPath();
            throw new NumberFormatException(new StringBuilder((String.valueOf($r2).length() + 69) + String.valueOf($r5).length()).append("Expected a long but was ").append($r2).append(" at line ").append($i0).append(" column ").append($i1).append(" path ").append($r5).toString());
        }
        this.caM = null;
        this.caJ = 0;
        $r1 = this.caQ;
        $i0 = this.caO - 1;
        $r1[$i0] = $r1[$i0] + 1;
        return $l2;
    }

    public String nextName() throws IOException {
        String $r1;
        int $i0 = this.caJ;
        if ($i0 == 0) {
            $i0 = hP();
        }
        if ($i0 == 14) {
            $r1 = hS();
        } else if ($i0 == 12) {
            $r1 = zzn('\'');
        } else if ($i0 == 13) {
            $r1 = zzn('\"');
        } else {
            $r1 = String.valueOf(hC());
            $i0 = getLineNumber();
            int $i1 = getColumnNumber();
            String $r5 = getPath();
            throw new IllegalStateException(new StringBuilder((String.valueOf($r1).length() + 69) + String.valueOf($r5).length()).append("Expected a name but was ").append($r1).append(" at line ").append($i0).append(" column ").append($i1).append(" path ").append($r5).toString());
        }
        this.caJ = 0;
        this.caP[this.caO - 1] = $r1;
        return $r1;
    }

    public void nextNull() throws IOException {
        int $i0 = this.caJ;
        if ($i0 == 0) {
            $i0 = hP();
        }
        if ($i0 == 7) {
            this.caJ = 0;
            int[] $r1 = this.caQ;
            $i0 = this.caO - 1;
            $r1[$i0] = $r1[$i0] + 1;
            return;
        }
        String $r4 = String.valueOf(hC());
        $i0 = getLineNumber();
        int $i1 = getColumnNumber();
        String $r5 = getPath();
        throw new IllegalStateException(new StringBuilder((String.valueOf($r4).length() + 67) + String.valueOf($r5).length()).append("Expected null but was ").append($r4).append(" at line ").append($i0).append(" column ").append($i1).append(" path ").append($r5).toString());
    }

    public String nextString() throws IOException {
        String $r1;
        int $i0 = this.caJ;
        this = this;
        if ($i0 == 0) {
            $i0 = hP();
        }
        if ($i0 == 10) {
            $r1 = hS();
        } else if ($i0 == 8) {
            $r1 = zzn('\'');
        } else if ($i0 == 9) {
            $r1 = zzn('\"');
        } else if ($i0 == 11) {
            $r1 = this.caM;
            this.caM = null;
        } else if ($i0 == 15) {
            $r1 = Long.toString(this.caK);
        } else if ($i0 == 16) {
            $r1 = new String(this.caG, this.pos, this.caL);
            this.pos += this.caL;
        } else {
            $r1 = String.valueOf(hC());
            $i0 = getLineNumber();
            int $i1 = getColumnNumber();
            String $r6 = getPath();
            throw new IllegalStateException(new StringBuilder((String.valueOf($r1).length() + 71) + String.valueOf($r6).length()).append("Expected a string but was ").append($r1).append(" at line ").append($i0).append(" column ").append($i1).append(" path ").append($r6).toString());
        }
        this.caJ = 0;
        int[] $r2 = this.caQ;
        int $i02 = this;
        this = $i02;
        $i0 = $i02.caO - 1;
        $r2[$i0] = $r2[$i0] + 1;
        return $r1;
    }

    public final void setLenient(boolean $z0) throws  {
        this.caF = $z0;
    }

    public void skipValue() throws IOException {
        int $i1 = 0;
        do {
            int $i0 = this.caJ;
            if ($i0 == 0) {
                $i0 = hP();
            }
            if ($i0 == 3) {
                zzarp(1);
                $i1++;
            } else if ($i0 == 1) {
                zzarp(3);
                $i1++;
            } else if ($i0 == 4) {
                this.caO--;
                $i1--;
            } else if ($i0 == 2) {
                this.caO--;
                $i1--;
            } else if ($i0 == 14 || $i0 == 10) {
                hT();
            } else if ($i0 == 8 || $i0 == 12) {
                zzo('\'');
            } else if ($i0 == 9 || $i0 == 13) {
                zzo('\"');
            } else if ($i0 == 16) {
                this.pos += this.caL;
            }
            this.caJ = 0;
        } while ($i1 != 0);
        int[] $r1 = this.caQ;
        $i1 = this.caO - 1;
        $r1[$i1] = $r1[$i1] + 1;
        this.caP[this.caO - 1] = "null";
    }

    public String toString() throws  {
        String $r2 = String.valueOf(getClass().getSimpleName());
        int $i0 = getLineNumber();
        return new StringBuilder(String.valueOf($r2).length() + 39).append($r2).append(" at line ").append($i0).append(" column ").append(getColumnNumber()).toString();
    }
}
