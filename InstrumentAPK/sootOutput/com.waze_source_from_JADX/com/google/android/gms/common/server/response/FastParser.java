package com.google.android.gms.common.server.response;

import android.util.Log;
import com.google.android.gms.common.server.response.FastJsonResponse.Field;
import com.google.android.gms.common.util.zzn;
import com.waze.inbox.InboxNativeManager;
import dalvik.annotation.Signature;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Stack;

/* compiled from: dalvik_source_com.waze.apk */
public class FastParser<T extends FastJsonResponse> {
    private static final char[] Li = new char[]{'u', 'l', 'l'};
    private static final char[] Lj = new char[]{'r', 'u', 'e'};
    private static final char[] Lk = new char[]{'r', 'u', 'e', '\"'};
    private static final char[] Ll = new char[]{'a', 'l', 's', 'e'};
    private static final char[] Lm = new char[]{'a', 'l', 's', 'e', '\"'};
    private static final char[] Ln = new char[]{'\n'};
    private static final zza<Integer> Lp = new C07121();
    private static final zza<Long> Lq = new C07132();
    private static final zza<Float> Lr = new C07143();
    private static final zza<Double> Ls = new C07154();
    private static final zza<Boolean> Lt = new C07165();
    private static final zza<String> Lu = new C07176();
    private static final zza<BigInteger> Lv = new C07187();
    private static final zza<BigDecimal> Lw = new C07198();
    private final char[] Ld = new char[1];
    private final char[] Le = new char[32];
    private final char[] Lf = new char[1024];
    private final StringBuilder Lg = new StringBuilder(32);
    private final StringBuilder Lh = new StringBuilder(1024);
    private final Stack<Integer> Lo = new Stack();

    /* compiled from: dalvik_source_com.waze.apk */
    private interface zza<O> {
        O zzi(@Signature({"(", "Lcom/google/android/gms/common/server/response/FastParser;", "Ljava/io/BufferedReader;", ")TO;"}) FastParser fastParser, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastParser;", "Ljava/io/BufferedReader;", ")TO;"}) BufferedReader bufferedReader) throws ParseException, IOException;
    }

    /* compiled from: dalvik_source_com.waze.apk */
    class C07121 implements zza<Integer> {
        C07121() throws  {
        }

        public Integer zzh(FastParser $r1, BufferedReader $r2) throws ParseException, IOException {
            return Integer.valueOf($r1.zze($r2));
        }

        public /* synthetic */ Object zzi(FastParser $r1, BufferedReader $r2) throws ParseException, IOException {
            return zzh($r1, $r2);
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    class C07132 implements zza<Long> {
        C07132() throws  {
        }

        public /* synthetic */ Object zzi(FastParser $r1, BufferedReader $r2) throws ParseException, IOException {
            return zzj($r1, $r2);
        }

        public Long zzj(FastParser $r1, BufferedReader $r2) throws ParseException, IOException {
            return Long.valueOf($r1.zzf($r2));
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    class C07143 implements zza<Float> {
        C07143() throws  {
        }

        public /* synthetic */ Object zzi(FastParser $r1, BufferedReader $r2) throws ParseException, IOException {
            return zzk($r1, $r2);
        }

        public Float zzk(FastParser $r1, BufferedReader $r2) throws ParseException, IOException {
            return Float.valueOf($r1.zzh($r2));
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    class C07154 implements zza<Double> {
        C07154() throws  {
        }

        public /* synthetic */ Object zzi(FastParser $r1, BufferedReader $r2) throws ParseException, IOException {
            return zzl($r1, $r2);
        }

        public Double zzl(FastParser $r1, BufferedReader $r2) throws ParseException, IOException {
            return Double.valueOf($r1.zzi($r2));
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    class C07165 implements zza<Boolean> {
        C07165() throws  {
        }

        public /* synthetic */ Object zzi(FastParser $r1, BufferedReader $r2) throws ParseException, IOException {
            return zzm($r1, $r2);
        }

        public Boolean zzm(FastParser $r1, BufferedReader $r2) throws ParseException, IOException {
            return Boolean.valueOf($r1.zza($r2, false));
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    class C07176 implements zza<String> {
        C07176() throws  {
        }

        public /* synthetic */ Object zzi(FastParser $r1, BufferedReader $r2) throws ParseException, IOException {
            return zzn($r1, $r2);
        }

        public String zzn(FastParser $r1, BufferedReader $r2) throws ParseException, IOException {
            return $r1.zzc($r2);
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    class C07187 implements zza<BigInteger> {
        C07187() throws  {
        }

        public /* synthetic */ Object zzi(FastParser $r1, BufferedReader $r2) throws ParseException, IOException {
            return zzo($r1, $r2);
        }

        public BigInteger zzo(FastParser $r1, BufferedReader $r2) throws ParseException, IOException {
            return $r1.zzg($r2);
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    class C07198 implements zza<BigDecimal> {
        C07198() throws  {
        }

        public /* synthetic */ Object zzi(FastParser $r1, BufferedReader $r2) throws ParseException, IOException {
            return zzp($r1, $r2);
        }

        public BigDecimal zzp(FastParser $r1, BufferedReader $r2) throws ParseException, IOException {
            return $r1.zzj($r2);
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static class ParseException extends Exception {
        public ParseException(String $r1) throws  {
            super($r1);
        }

        public ParseException(String $r1, Throwable $r2) throws  {
            super($r1, $r2);
        }

        public ParseException(Throwable $r1) throws  {
            super($r1);
        }
    }

    private int zza(BufferedReader $r1, char[] $r2) throws ParseException, IOException {
        char $c0 = zzk($r1);
        if ($c0 == '\u0000') {
            throw new ParseException("Unexpected EOF");
        } else if ($c0 == ',') {
            throw new ParseException("Missing value");
        } else if ($c0 == 'n') {
            zzb($r1, Li);
            return 0;
        } else {
            int $i1;
            $r1.mark(1024);
            if ($c0 == '\"') {
                boolean $z0 = false;
                $i1 = 0;
                while ($i1 < $r2.length && $r1.read($r2, $i1, 1) != -1) {
                    $c0 = $r2[$i1];
                    if (Character.isISOControl($c0)) {
                        throw new ParseException("Unexpected control character while reading string");
                    } else if ($c0 != '\"' || $z0) {
                        $z0 = $c0 == '\\' ? !$z0 : false;
                        $i1++;
                    } else {
                        $r1.reset();
                        $r1.skip((long) ($i1 + 1));
                        return $i1;
                    }
                }
            }
            $r2[0] = $c0;
            $i1 = 1;
            while ($i1 < $r2.length && $r1.read($r2, $i1, 1) != -1) {
                if ($r2[$i1] == '}' || $r2[$i1] == ',' || Character.isWhitespace($r2[$i1]) || $r2[$i1] == ']') {
                    $r1.reset();
                    $r1.skip((long) ($i1 - 1));
                    $r2[$i1] = '\u0000';
                    return $i1;
                }
                $i1++;
            }
            if ($i1 == $r2.length) {
                throw new ParseException("Absurdly long value");
            }
            throw new ParseException("Unexpected EOF");
        }
    }

    private static int zza(char[] $r0, int $i0) throws ParseException {
        int $i1 = 0;
        if ($i0 > 0) {
            int $i3;
            boolean $z0;
            int $i4;
            if ($r0[0] == '-') {
                $i3 = Integer.MIN_VALUE;
                $z0 = true;
                $i4 = 1;
            } else {
                $i3 = InboxNativeManager.INBOX_STATUS_FAILURE_TIMEOUT;
                $z0 = false;
                $i4 = 0;
            }
            if ($i4 < $i0) {
                $i1 = Character.digit($r0[$i4], 10);
                if ($i1 < 0) {
                    throw new ParseException("Unexpected non-digit character");
                }
                $i1 = -$i1;
                $i4++;
            }
            while ($i4 < $i0) {
                int $i5 = $i4 + 1;
                $i4 = Character.digit($r0[$i4], 10);
                if ($i4 < 0) {
                    throw new ParseException("Unexpected non-digit character");
                } else if ($i1 < -214748364) {
                    throw new ParseException("Number too large");
                } else {
                    $i1 *= 10;
                    if ($i1 < $i3 + $i4) {
                        throw new ParseException("Number too large");
                    }
                    $i1 -= $i4;
                    $i4 = $i5;
                }
            }
            if (!$z0) {
                return -$i1;
            }
            if ($i4 > 1) {
                return $i1;
            }
            throw new ParseException("No digits to parse");
        }
        throw new ParseException("No number to parse");
    }

    private String zza(BufferedReader $r1) throws ParseException, IOException {
        this.Lo.push(Integer.valueOf(2));
        char $c0 = zzk($r1);
        switch ($c0) {
            case '\"':
                this.Lo.push(Integer.valueOf(3));
                String $r6 = zzb($r1, this.Le, this.Lg, null);
                zzje(3);
                if (zzk($r1) == ':') {
                    return $r6;
                }
                throw new ParseException("Expected key/value separator");
            case ']':
                zzje(2);
                zzje(1);
                zzje(5);
                return null;
            case '}':
                zzje(2);
                return null;
            default:
                throw new ParseException("Unexpected token: " + $c0);
        }
    }

    private String zza(BufferedReader $r1, char[] $r2, StringBuilder $r3, char[] $r4) throws ParseException, IOException {
        switch (zzk($r1)) {
            case '\"':
                return zzb($r1, $r2, $r3, $r4);
            case 'n':
                zzb($r1, Li);
                return null;
            default:
                throw new ParseException("Expected string");
        }
    }

    private <T extends FastJsonResponse> ArrayList<T> zza(@Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Ljava/io/BufferedReader;", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;)", "Ljava/util/ArrayList", "<TT;>;"}) BufferedReader $r1, @Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Ljava/io/BufferedReader;", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;)", "Ljava/util/ArrayList", "<TT;>;"}) Field<?, ?> $r2) throws ParseException, IOException {
        ArrayList $r3 = new ArrayList();
        char $c0 = zzk($r1);
        switch ($c0) {
            case ']':
                zzje(5);
                return $r3;
            case 'n':
                zzb($r1, Li);
                zzje(5);
                return null;
            case '{':
                this.Lo.push(Integer.valueOf(1));
                while (true) {
                    try {
                        FastJsonResponse $r9 = $r2.newConcreteTypeInstance();
                        if (!zzc($r1, $r9)) {
                            return $r3;
                        }
                        $r3.add($r9);
                        $c0 = zzk($r1);
                        switch ($c0) {
                            case ',':
                                if (zzk($r1) != '{') {
                                    throw new ParseException("Expected start of next object in array");
                                }
                                this.Lo.push(Integer.valueOf(1));
                            case ']':
                                zzje(5);
                                return $r3;
                            default:
                                throw new ParseException("Unexpected token: " + $c0);
                        }
                    } catch (InstantiationException $r11) {
                        throw new ParseException("Error instantiating inner object", $r11);
                    } catch (Throwable $r12) {
                        throw new ParseException("Error instantiating inner object", $r12);
                    }
                }
            default:
                throw new ParseException("Unexpected token: " + $c0);
        }
    }

    private <O> ArrayList<O> zza(@Signature({"<O:", "Ljava/lang/Object;", ">(", "Ljava/io/BufferedReader;", "Lcom/google/android/gms/common/server/response/FastParser$zza", "<TO;>;)", "Ljava/util/ArrayList", "<TO;>;"}) BufferedReader $r1, @Signature({"<O:", "Ljava/lang/Object;", ">(", "Ljava/io/BufferedReader;", "Lcom/google/android/gms/common/server/response/FastParser$zza", "<TO;>;)", "Ljava/util/ArrayList", "<TO;>;"}) zza<O> $r2) throws ParseException, IOException {
        char $c0 = zzk($r1);
        if ($c0 != 'n') {
            if ($c0 == '[') {
                this.Lo.push(Integer.valueOf(5));
                ArrayList $r4 = new ArrayList();
                while (true) {
                    $r1.mark(1024);
                    switch (zzk($r1)) {
                        case '\u0000':
                            throw new ParseException("Unexpected EOF");
                        case ',':
                            break;
                        case ']':
                            zzje(5);
                            return $r4;
                        default:
                            $r1.reset();
                            $r4.add($r2.zzi(this, $r1));
                            break;
                    }
                }
            }
            throw new ParseException("Expected start of array");
        }
        zzb($r1, Li);
        return null;
    }

    private void zza(@Signature({"(", "Ljava/io/BufferedReader;", "TT;)V"}) BufferedReader $r1, @Signature({"(", "Ljava/io/BufferedReader;", "TT;)V"}) T $r2) throws ParseException, IOException {
        char $c0 = zzk($r1);
        switch ($c0) {
            case '\u0000':
                throw new ParseException("No data to parse");
            case '[':
                this.Lo.push(Integer.valueOf(5));
                zzb($r1, (FastJsonResponse) $r2);
                return;
            case '{':
                this.Lo.push(Integer.valueOf(1));
                zzc($r1, (FastJsonResponse) $r2);
                return;
            default:
                throw new ParseException("Unexpected token: " + $c0);
        }
    }

    private boolean zza(BufferedReader $r1, boolean $z0) throws ParseException, IOException {
        char $c0 = zzk($r1);
        switch ($c0) {
            case '\"':
                if (!$z0) {
                    return zza($r1, true);
                }
                throw new ParseException("No boolean value found in string");
            case 'f':
                zzb($r1, $z0 ? Lm : Ll);
                return false;
            case 'n':
                zzb($r1, Li);
                return false;
            case 't':
                zzb($r1, $z0 ? Lk : Lj);
                return true;
            default:
                throw new ParseException("Unexpected token: " + $c0);
        }
    }

    private boolean zza(char[] $r1, char $c0) throws  {
        if ($r1 == null) {
            return false;
        }
        for (char $c3 : $r1) {
            if ($c3 == $c0) {
                return true;
            }
        }
        return false;
    }

    private static long zzb(char[] $r0, int $i0) throws ParseException {
        if ($i0 > 0) {
            long $l3;
            boolean $z0;
            byte $b4;
            int $i5;
            long $l6;
            if ($r0[0] == '-') {
                $l3 = Long.MIN_VALUE;
                $z0 = true;
                $b4 = (byte) 1;
            } else {
                $z0 = false;
                $l3 = -9223372036854775807L;
                $b4 = (byte) 0;
            }
            if ($b4 < $i0) {
                $i5 = Character.digit($r0[$b4], 10);
                if ($i5 < 0) {
                    throw new ParseException("Unexpected non-digit character");
                }
                $l6 = (long) (-$i5);
                $i5 = $b4 + 1;
            } else {
                $l6 = 0;
                byte $i52 = $b4;
            }
            while ($i5 < $i0) {
                int $i1 = $i5 + 1;
                $i5 = Character.digit($r0[$i5], 10);
                if ($i5 < 0) {
                    throw new ParseException("Unexpected non-digit character");
                } else if ($l6 < -922337203685477580L) {
                    throw new ParseException("Number too large");
                } else {
                    $l6 *= 10;
                    long $l7 = (long) $i5;
                    if ($l6 < $l7 + $l3) {
                        throw new ParseException("Number too large");
                    }
                    $l7 = (long) $i5;
                    $l6 -= $l7;
                    $i5 = $i1;
                }
            }
            if (!$z0) {
                return -$l6;
            }
            if ($i5 > 1) {
                return $l6;
            }
            throw new ParseException("No digits to parse");
        }
        throw new ParseException("No number to parse");
    }

    private java.lang.String zzb(java.io.BufferedReader r15) throws com.google.android.gms.common.server.response.FastParser.ParseException, java.io.IOException {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:83:0x0145
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
        r14 = this;
        r0 = 1024; // 0x400 float:1.435E-42 double:5.06E-321;
        r15.mark(r0);
        r1 = r14.zzk(r15);
        switch(r1) {
            case 34: goto L_0x0038;
            case 44: goto L_0x014e;
            case 91: goto L_0x00dc;
            case 123: goto L_0x0090;
            default: goto L_0x000c;
        };
    L_0x000c:
        goto L_0x000d;
    L_0x000d:
        r15.reset();
        r2 = r14.Lf;
        r14.zza(r15, r2);
    L_0x0015:
        r1 = r14.zzk(r15);
        switch(r1) {
            case 44: goto L_0x0156;
            case 125: goto L_0x015f;
            default: goto L_0x001c;
        };
    L_0x001c:
        goto L_0x001d;
    L_0x001d:
        r3 = new com.google.android.gms.common.server.response.FastParser$ParseException;
        r4 = new java.lang.StringBuilder;
        r0 = 18;
        r4.<init>(r0);
        r5 = "Unexpected token ";
        r4 = r4.append(r5);
        r4 = r4.append(r1);
        r6 = r4.toString();
        r3.<init>(r6);
        throw r3;
    L_0x0038:
        r2 = r14.Ld;
        r7 = r15.read(r2);
        r0 = -1;
        if (r7 != r0) goto L_0x0049;
    L_0x0041:
        r3 = new com.google.android.gms.common.server.response.FastParser$ParseException;
        r5 = "Unexpected EOF while parsing string";
        r3.<init>(r5);
        throw r3;
    L_0x0049:
        r2 = r14.Ld;
        r0 = 0;
        r1 = r2[r0];
        r8 = 0;
    L_0x004f:
        r0 = 34;
        if (r1 != r0) goto L_0x0055;
    L_0x0053:
        if (r8 == 0) goto L_0x0015;
    L_0x0055:
        r0 = 92;
        if (r1 != r0) goto L_0x0077;
    L_0x0059:
        if (r8 != 0) goto L_0x0075;
    L_0x005b:
        goto L_0x005f;
    L_0x005c:
        goto L_0x0015;
    L_0x005f:
        r8 = 1;
    L_0x0060:
        r2 = r14.Ld;
        r7 = r15.read(r2);
        goto L_0x006a;
    L_0x0067:
        goto L_0x0015;
    L_0x006a:
        r0 = -1;
        if (r7 != r0) goto L_0x0079;
    L_0x006d:
        r3 = new com.google.android.gms.common.server.response.FastParser$ParseException;
        r5 = "Unexpected EOF while parsing string";
        r3.<init>(r5);
        throw r3;
    L_0x0075:
        r8 = 0;
        goto L_0x0060;
    L_0x0077:
        r8 = 0;
        goto L_0x0060;
    L_0x0079:
        r2 = r14.Ld;
        r0 = 0;
        r1 = r2[r0];
        goto L_0x0082;
    L_0x007f:
        goto L_0x0015;
    L_0x0082:
        r9 = java.lang.Character.isISOControl(r1);
        if (r9 == 0) goto L_0x016c;
    L_0x0088:
        r3 = new com.google.android.gms.common.server.response.FastParser$ParseException;
        r5 = "Unexpected control character while reading string";
        r3.<init>(r5);
        throw r3;
    L_0x0090:
        r10 = r14.Lo;
        r0 = 1;
        r11 = java.lang.Integer.valueOf(r0);
        r10.push(r11);
        r0 = 32;
        r15.mark(r0);
        r1 = r14.zzk(r15);
        r0 = 125; // 0x7d float:1.75E-43 double:6.2E-322;
        if (r1 != r0) goto L_0x00ac;
    L_0x00a7:
        r0 = 1;
        r14.zzje(r0);
        goto L_0x005c;
    L_0x00ac:
        r0 = 34;
        if (r1 != r0) goto L_0x00c1;
    L_0x00b0:
        r15.reset();
        r14.zza(r15);
    L_0x00b6:
        r6 = r14.zzb(r15);
        if (r6 != 0) goto L_0x00b6;
    L_0x00bc:
        r0 = 1;
        r14.zzje(r0);
        goto L_0x0067;
    L_0x00c1:
        r3 = new com.google.android.gms.common.server.response.FastParser$ParseException;
        r4 = new java.lang.StringBuilder;
        r0 = 18;
        r4.<init>(r0);
        r5 = "Unexpected token ";
        r4 = r4.append(r5);
        r4 = r4.append(r1);
        r6 = r4.toString();
        r3.<init>(r6);
        throw r3;
    L_0x00dc:
        r10 = r14.Lo;
        r0 = 5;
        r11 = java.lang.Integer.valueOf(r0);
        r10.push(r11);
        r0 = 32;
        r15.mark(r0);
        r1 = r14.zzk(r15);
        r0 = 93;
        if (r1 != r0) goto L_0x00f8;
    L_0x00f3:
        r0 = 5;
        r14.zzje(r0);
        goto L_0x007f;
    L_0x00f8:
        r15.reset();
        r7 = 1;
        r9 = 0;
        r8 = 0;
    L_0x00fe:
        if (r7 <= 0) goto L_0x0149;
    L_0x0100:
        r1 = r14.zzk(r15);
        if (r1 != 0) goto L_0x010e;
    L_0x0106:
        r3 = new com.google.android.gms.common.server.response.FastParser$ParseException;
        r5 = "Unexpected EOF while parsing array";
        r3.<init>(r5);
        throw r3;
    L_0x010e:
        r12 = java.lang.Character.isISOControl(r1);
        if (r12 == 0) goto L_0x011c;
    L_0x0114:
        r3 = new com.google.android.gms.common.server.response.FastParser$ParseException;
        r5 = "Unexpected control character while reading array";
        r3.<init>(r5);
        throw r3;
    L_0x011c:
        r0 = 34;
        if (r1 != r0) goto L_0x016b;
    L_0x0120:
        if (r8 != 0) goto L_0x016b;
    L_0x0122:
        if (r9 != 0) goto L_0x013f;
    L_0x0124:
        r9 = 1;
    L_0x0125:
        r0 = 91;
        if (r1 != r0) goto L_0x0166;
    L_0x0129:
        if (r9 != 0) goto L_0x0166;
    L_0x012b:
        r7 = r7 + 1;
    L_0x012d:
        r0 = 93;
        if (r1 != r0) goto L_0x0165;
    L_0x0131:
        if (r9 != 0) goto L_0x0165;
    L_0x0133:
        r7 = r7 + -1;
    L_0x0135:
        r0 = 92;
        if (r1 != r0) goto L_0x0143;
    L_0x0139:
        if (r9 == 0) goto L_0x0143;
    L_0x013b:
        if (r8 != 0) goto L_0x0141;
    L_0x013d:
        r8 = 1;
    L_0x013e:
        goto L_0x00fe;
    L_0x013f:
        r9 = 0;
        goto L_0x0125;
    L_0x0141:
        r8 = 0;
        goto L_0x013e;
    L_0x0143:
        r8 = 0;
        goto L_0x00fe;
        goto L_0x0149;
    L_0x0146:
        goto L_0x0015;
    L_0x0149:
        r0 = 5;
        r14.zzje(r0);
        goto L_0x0146;
    L_0x014e:
        r3 = new com.google.android.gms.common.server.response.FastParser$ParseException;
        r5 = "Missing value";
        r3.<init>(r5);
        throw r3;
    L_0x0156:
        r0 = 2;
        r14.zzje(r0);
        r6 = r14.zza(r15);
        return r6;
    L_0x015f:
        r0 = 2;
        r14.zzje(r0);
        r13 = 0;
        return r13;
    L_0x0165:
        goto L_0x0135;
    L_0x0166:
        goto L_0x012d;
        goto L_0x016b;
    L_0x0168:
        goto L_0x004f;
    L_0x016b:
        goto L_0x0125;
    L_0x016c:
        goto L_0x0168;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.server.response.FastParser.zzb(java.io.BufferedReader):java.lang.String");
    }

    private String zzb(BufferedReader $r1, char[] $r2, StringBuilder $r3, char[] $r4) throws ParseException, IOException {
        int $i2;
        $r3.setLength(0);
        $r1.mark($r2.length);
        boolean $z0 = false;
        boolean $z1 = false;
        loop0:
        while (true) {
            int $i1 = $r1.read($r2);
            if ($i1 != -1) {
                $i2 = 0;
                while ($i2 < $i1) {
                    char $c0 = $r2[$i2];
                    if (!Character.isISOControl($c0) || zza($r4, $c0)) {
                        if ($c0 == '\"' && !$z1) {
                            break loop0;
                        }
                        if ($c0 == '\\') {
                            $z1 = !$z1;
                            $z0 = true;
                        } else {
                            $z1 = false;
                        }
                        $i2++;
                    } else {
                        throw new ParseException("Unexpected control character while reading string");
                    }
                }
                $r3.append($r2, 0, $i1);
                $r1.mark($r2.length);
            } else {
                throw new ParseException("Unexpected EOF while parsing string");
            }
        }
        $r3.append($r2, 0, $i2);
        $r1.reset();
        $r1.skip((long) ($i2 + 1));
        return $z0 ? zzn.zzhe($r3.toString()) : $r3.toString();
    }

    private void zzb(BufferedReader $r1, FastJsonResponse $r2) throws ParseException, IOException {
        Map $r3 = $r2.getFieldMappings();
        if ($r3.size() != 1) {
            throw new ParseException("Object array response class must have a single Field");
        }
        Field $r9 = (Field) ((Entry) $r3.entrySet().iterator().next()).getValue();
        FastJsonResponse fastJsonResponse = $r2;
        fastJsonResponse.addConcreteTypeArrayInternal($r9, $r9.getOutputFieldName(), zza($r1, $r9));
    }

    private void zzb(BufferedReader $r1, char[] $r2) throws ParseException, IOException {
        int $i1 = 0;
        while ($i1 < $r2.length) {
            int $i0 = $r1.read(this.Le, 0, $r2.length - $i1);
            if ($i0 == -1) {
                throw new ParseException("Unexpected EOF");
            }
            for (int $i2 = 0; $i2 < $i0; $i2++) {
                if ($r2[$i2 + $i1] != this.Le[$i2]) {
                    throw new ParseException("Unexpected character");
                }
            }
            $i1 += $i0;
        }
    }

    private String zzc(BufferedReader $r1) throws ParseException, IOException {
        return zza($r1, this.Le, this.Lg, null);
    }

    private boolean zzc(java.io.BufferedReader r38, com.google.android.gms.common.server.response.FastJsonResponse r39) throws com.google.android.gms.common.server.response.FastParser.ParseException, java.io.IOException {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:23:0x00c2
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
        r37 = this;
        r0 = r39;
        r4 = r0.getFieldMappings();
        r0 = r37;
        r1 = r38;
        r5 = r0.zza(r1);
        r6 = r5;
        if (r5 != 0) goto L_0x001a;
    L_0x0011:
        r7 = 1;
        r0 = r37;
        r0.zzje(r7);
        r7 = 0;
        return r7;
    L_0x0019:
        r6 = 0;
    L_0x001a:
        if (r6 == 0) goto L_0x0377;
    L_0x001c:
        r8 = r4.get(r6);
        r10 = r8;
        r10 = (com.google.android.gms.common.server.response.FastJsonResponse.Field) r10;
        r9 = r10;
        if (r9 != 0) goto L_0x002f;
    L_0x0026:
        r0 = r37;
        r1 = r38;
        r6 = r0.zzb(r1);
        goto L_0x001a;
    L_0x002f:
        r0 = r37;
        r11 = r0.Lo;
        r7 = 4;
        r12 = java.lang.Integer.valueOf(r7);
        r11.push(r12);
        r13 = r9.getTypeIn();
        switch(r13) {
            case 0: goto L_0x0064;
            case 1: goto L_0x00ca;
            case 2: goto L_0x0108;
            case 3: goto L_0x0132;
            case 4: goto L_0x0164;
            case 5: goto L_0x018e;
            case 6: goto L_0x01c0;
            case 7: goto L_0x01f3;
            case 8: goto L_0x0223;
            case 9: goto L_0x024b;
            case 10: goto L_0x0273;
            case 11: goto L_0x0287;
            default: goto L_0x0042;
        };
    L_0x0042:
        goto L_0x0043;
    L_0x0043:
        r14 = new com.google.android.gms.common.server.response.FastParser$ParseException;
        r13 = r9.getTypeIn();
        r15 = new java.lang.StringBuilder;
        r7 = 30;
        r15.<init>(r7);
        r16 = "Invalid field type ";
        r0 = r16;
        r15 = r15.append(r0);
        r15 = r15.append(r13);
        r5 = r15.toString();
        r14.<init>(r5);
        throw r14;
    L_0x0064:
        r17 = r9.isTypeInArray();
        if (r17 == 0) goto L_0x00b4;
    L_0x006a:
        r18 = Lp;
        r0 = r37;
        r1 = r38;
        r2 = r18;
        r19 = r0.zza(r1, r2);
        r0 = r39;
        r1 = r19;
        r0.setIntegers(r9, r1);
    L_0x007d:
        r7 = 4;
        r0 = r37;
        r0.zzje(r7);
        r7 = 2;
        r0 = r37;
        r0.zzje(r7);
        r0 = r37;
        r1 = r38;
        r20 = r0.zzk(r1);
        switch(r20) {
            case 44: goto L_0x036e;
            case 125: goto L_0x0019;
            default: goto L_0x0094;
        };
    L_0x0094:
        goto L_0x0095;
    L_0x0095:
        r14 = new com.google.android.gms.common.server.response.FastParser$ParseException;
        r15 = new java.lang.StringBuilder;
        r7 = 55;
        r15.<init>(r7);
        r16 = "Expected end of object or field separator, but found: ";
        r0 = r16;
        r15 = r15.append(r0);
        r0 = r20;
        r15 = r15.append(r0);
        r5 = r15.toString();
        r14.<init>(r5);
        throw r14;
    L_0x00b4:
        r0 = r37;
        r1 = r38;
        r13 = r0.zze(r1);
        r0 = r39;
        r0.setInteger(r9, r13);
        goto L_0x007d;
        goto L_0x00c6;
    L_0x00c3:
        goto L_0x007d;
    L_0x00c6:
        goto L_0x00ca;
    L_0x00c7:
        goto L_0x007d;
    L_0x00ca:
        r17 = r9.isTypeInArray();
        if (r17 == 0) goto L_0x00f0;
    L_0x00d0:
        goto L_0x00d4;
    L_0x00d1:
        goto L_0x007d;
    L_0x00d4:
        r18 = Lv;
        r0 = r37;
        r1 = r38;
        r2 = r18;
        r19 = r0.zza(r1, r2);
        goto L_0x00e4;
    L_0x00e1:
        goto L_0x007d;
    L_0x00e4:
        r0 = r39;
        r1 = r19;
        r0.setBigIntegers(r9, r1);
        goto L_0x00ef;
    L_0x00ec:
        goto L_0x007d;
    L_0x00ef:
        goto L_0x007d;
    L_0x00f0:
        r0 = r37;
        r1 = r38;
        r21 = r0.zzg(r1);
        goto L_0x00fc;
    L_0x00f9:
        goto L_0x007d;
    L_0x00fc:
        r0 = r39;
        r1 = r21;
        r0.setBigInteger(r9, r1);
        goto L_0x0107;
    L_0x0104:
        goto L_0x007d;
    L_0x0107:
        goto L_0x00c3;
    L_0x0108:
        r17 = r9.isTypeInArray();
        if (r17 == 0) goto L_0x0122;
    L_0x010e:
        r18 = Lq;
        r0 = r37;
        r1 = r38;
        r2 = r18;
        r19 = r0.zza(r1, r2);
        r0 = r39;
        r1 = r19;
        r0.setLongs(r9, r1);
        goto L_0x00c7;
    L_0x0122:
        r0 = r37;
        r1 = r38;
        r22 = r0.zzf(r1);
        r0 = r39;
        r1 = r22;
        r0.setLong(r9, r1);
        goto L_0x00d1;
    L_0x0132:
        r17 = r9.isTypeInArray();
        if (r17 == 0) goto L_0x0154;
    L_0x0138:
        goto L_0x013c;
    L_0x0139:
        goto L_0x00f9;
    L_0x013c:
        r18 = Lr;
        r0 = r37;
        r1 = r38;
        r2 = r18;
        r19 = r0.zza(r1, r2);
        goto L_0x014c;
    L_0x0149:
        goto L_0x0104;
    L_0x014c:
        r0 = r39;
        r1 = r19;
        r0.setFloats(r9, r1);
        goto L_0x00e1;
    L_0x0154:
        r0 = r37;
        r1 = r38;
        r24 = r0.zzh(r1);
        r0 = r39;
        r1 = r24;
        r0.setFloat(r9, r1);
        goto L_0x00ec;
    L_0x0164:
        r17 = r9.isTypeInArray();
        if (r17 == 0) goto L_0x017e;
    L_0x016a:
        r18 = Ls;
        r0 = r37;
        r1 = r38;
        r2 = r18;
        r19 = r0.zza(r1, r2);
        r0 = r39;
        r1 = r19;
        r0.setDoubles(r9, r1);
        goto L_0x0139;
    L_0x017e:
        r0 = r37;
        r1 = r38;
        r25 = r0.zzi(r1);
        r0 = r39;
        r1 = r25;
        r0.setDouble(r9, r1);
        goto L_0x0149;
    L_0x018e:
        r17 = r9.isTypeInArray();
        if (r17 == 0) goto L_0x01ac;
    L_0x0194:
        r18 = Lw;
        r0 = r37;
        r1 = r38;
        r2 = r18;
        r19 = r0.zza(r1, r2);
        goto L_0x01a4;
    L_0x01a1:
        goto L_0x007d;
    L_0x01a4:
        r0 = r39;
        r1 = r19;
        r0.setBigDecimals(r9, r1);
        goto L_0x01a1;
    L_0x01ac:
        r0 = r37;
        r1 = r38;
        r27 = r0.zzj(r1);
        goto L_0x01b8;
    L_0x01b5:
        goto L_0x007d;
    L_0x01b8:
        r0 = r39;
        r1 = r27;
        r0.setBigDecimal(r9, r1);
        goto L_0x01b5;
    L_0x01c0:
        r17 = r9.isTypeInArray();
        if (r17 == 0) goto L_0x01de;
    L_0x01c6:
        r18 = Lt;
        r0 = r37;
        r1 = r38;
        r2 = r18;
        r19 = r0.zza(r1, r2);
        goto L_0x01d6;
    L_0x01d3:
        goto L_0x007d;
    L_0x01d6:
        r0 = r39;
        r1 = r19;
        r0.setBooleans(r9, r1);
        goto L_0x01d3;
    L_0x01de:
        r7 = 0;
        r0 = r37;
        r1 = r38;
        r17 = r0.zza(r1, r7);
        goto L_0x01eb;
    L_0x01e8:
        goto L_0x007d;
    L_0x01eb:
        r0 = r39;
        r1 = r17;
        r0.setBoolean(r9, r1);
        goto L_0x01e8;
    L_0x01f3:
        r17 = r9.isTypeInArray();
        if (r17 == 0) goto L_0x0211;
    L_0x01f9:
        r18 = Lu;
        r0 = r37;
        r1 = r38;
        r2 = r18;
        r19 = r0.zza(r1, r2);
        goto L_0x0209;
    L_0x0206:
        goto L_0x007d;
    L_0x0209:
        r0 = r39;
        r1 = r19;
        r0.setStrings(r9, r1);
        goto L_0x0206;
    L_0x0211:
        r0 = r37;
        r1 = r38;
        r5 = r0.zzc(r1);
        goto L_0x021d;
    L_0x021a:
        goto L_0x007d;
    L_0x021d:
        r0 = r39;
        r0.setString(r9, r5);
        goto L_0x021a;
    L_0x0223:
        r0 = r37;
        r0 = r0.Lf;
        r28 = r0;
        r0 = r37;
        r15 = r0.Lh;
        r29 = Ln;
        r0 = r37;
        r1 = r38;
        r2 = r28;
        r3 = r29;
        r5 = r0.zza(r1, r2, r15, r3);
        r30 = com.google.android.gms.common.util.zzc.decode(r5);
        goto L_0x0243;
    L_0x0240:
        goto L_0x007d;
    L_0x0243:
        r0 = r39;
        r1 = r30;
        r0.setDecodedBytes(r9, r1);
        goto L_0x0240;
    L_0x024b:
        r0 = r37;
        r0 = r0.Lf;
        r28 = r0;
        r0 = r37;
        r15 = r0.Lh;
        r29 = Ln;
        r0 = r37;
        r1 = r38;
        r2 = r28;
        r3 = r29;
        r5 = r0.zza(r1, r2, r15, r3);
        r30 = com.google.android.gms.common.util.zzc.zzhd(r5);
        goto L_0x026b;
    L_0x0268:
        goto L_0x007d;
    L_0x026b:
        r0 = r39;
        r1 = r30;
        r0.setDecodedBytes(r9, r1);
        goto L_0x0268;
    L_0x0273:
        r0 = r37;
        r1 = r38;
        r31 = r0.zzd(r1);
        goto L_0x027f;
    L_0x027c:
        goto L_0x007d;
    L_0x027f:
        r0 = r39;
        r1 = r31;
        r0.setStringMap(r9, r1);
        goto L_0x027c;
    L_0x0287:
        r17 = r9.isTypeInArray();
        if (r17 == 0) goto L_0x02ec;
    L_0x028d:
        r0 = r37;
        r1 = r38;
        r20 = r0.zzk(r1);
        r7 = 110; // 0x6e float:1.54E-43 double:5.43E-322;
        r0 = r20;
        if (r0 != r7) goto L_0x02b8;
    L_0x029b:
        r28 = Li;
        r0 = r37;
        r1 = r38;
        r2 = r28;
        r0.zzb(r1, r2);
        r5 = r9.getOutputFieldName();
        goto L_0x02ae;
    L_0x02ab:
        goto L_0x007d;
    L_0x02ae:
        r32 = 0;
        r0 = r39;
        r1 = r32;
        r0.addConcreteTypeArrayInternal(r9, r5, r1);
        goto L_0x02ab;
    L_0x02b8:
        r0 = r37;
        r11 = r0.Lo;
        r7 = 5;
        r12 = java.lang.Integer.valueOf(r7);
        r11.push(r12);
        r7 = 91;
        r0 = r20;
        if (r0 == r7) goto L_0x02d4;
    L_0x02ca:
        r14 = new com.google.android.gms.common.server.response.FastParser$ParseException;
        r16 = "Expected array start";
        r0 = r16;
        r14.<init>(r0);
        throw r14;
    L_0x02d4:
        r5 = r9.getOutputFieldName();
        r0 = r37;
        r1 = r38;
        r19 = r0.zza(r1, r9);
        goto L_0x02e4;
    L_0x02e1:
        goto L_0x007d;
    L_0x02e4:
        r0 = r39;
        r1 = r19;
        r0.addConcreteTypeArrayInternal(r9, r5, r1);
        goto L_0x02e1;
    L_0x02ec:
        r0 = r37;
        r1 = r38;
        r20 = r0.zzk(r1);
        r7 = 110; // 0x6e float:1.54E-43 double:5.43E-322;
        r0 = r20;
        if (r0 != r7) goto L_0x0317;
    L_0x02fa:
        r28 = Li;
        r0 = r37;
        r1 = r38;
        r2 = r28;
        r0.zzb(r1, r2);
        r5 = r9.getOutputFieldName();
        goto L_0x030d;
    L_0x030a:
        goto L_0x007d;
    L_0x030d:
        r32 = 0;
        r0 = r39;
        r1 = r32;
        r0.addConcreteTypeInternal(r9, r5, r1);
        goto L_0x030a;
    L_0x0317:
        r0 = r37;
        r11 = r0.Lo;
        r7 = 1;
        r12 = java.lang.Integer.valueOf(r7);
        r11.push(r12);
        r7 = 123; // 0x7b float:1.72E-43 double:6.1E-322;
        r0 = r20;
        if (r0 == r7) goto L_0x0333;
    L_0x0329:
        r14 = new com.google.android.gms.common.server.response.FastParser$ParseException;
        r16 = "Expected start of object";
        r0 = r16;
        r14.<init>(r0);
        throw r14;
    L_0x0333:
        r33 = r9.newConcreteTypeInstance();	 Catch:{ InstantiationException -> 0x0350, IllegalAccessException -> 0x035d }
        r0 = r37;	 Catch:{ InstantiationException -> 0x0350, IllegalAccessException -> 0x035d }
        r1 = r38;	 Catch:{ InstantiationException -> 0x0350, IllegalAccessException -> 0x035d }
        r2 = r33;	 Catch:{ InstantiationException -> 0x0350, IllegalAccessException -> 0x035d }
        r0.zzc(r1, r2);	 Catch:{ InstantiationException -> 0x0350, IllegalAccessException -> 0x035d }
        r5 = r9.getOutputFieldName();	 Catch:{ InstantiationException -> 0x0350, IllegalAccessException -> 0x035d }
        goto L_0x0348;	 Catch:{ InstantiationException -> 0x0350, IllegalAccessException -> 0x035d }
    L_0x0345:
        goto L_0x007d;	 Catch:{ InstantiationException -> 0x0350, IllegalAccessException -> 0x035d }
    L_0x0348:
        r0 = r39;	 Catch:{ InstantiationException -> 0x0350, IllegalAccessException -> 0x035d }
        r1 = r33;	 Catch:{ InstantiationException -> 0x0350, IllegalAccessException -> 0x035d }
        r0.addConcreteTypeInternal(r9, r5, r1);	 Catch:{ InstantiationException -> 0x0350, IllegalAccessException -> 0x035d }
        goto L_0x0345;
    L_0x0350:
        r34 = move-exception;
        r14 = new com.google.android.gms.common.server.response.FastParser$ParseException;
        r16 = "Error instantiating inner object";
        r0 = r16;
        r1 = r34;
        r14.<init>(r0, r1);
        throw r14;
    L_0x035d:
        r35 = move-exception;
        r14 = new com.google.android.gms.common.server.response.FastParser$ParseException;
        r16 = "Error instantiating inner object";
        r0 = r16;
        r1 = r35;
        r14.<init>(r0, r1);
        throw r14;
        goto L_0x036e;
    L_0x036b:
        goto L_0x001a;
    L_0x036e:
        r0 = r37;
        r1 = r38;
        r6 = r0.zza(r1);
        goto L_0x036b;
    L_0x0377:
        r0 = r39;
        r36 = r0.zzaxt();
        if (r36 == 0) goto L_0x0386;
    L_0x037f:
        r0 = r36;
        r1 = r39;
        r0.zza(r1);
    L_0x0386:
        r7 = 1;
        r0 = r37;
        r0.zzje(r7);
        r7 = 1;
        return r7;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.server.response.FastParser.zzc(java.io.BufferedReader, com.google.android.gms.common.server.response.FastJsonResponse):boolean");
    }

    private HashMap<String, String> zzd(@Signature({"(", "Ljava/io/BufferedReader;", ")", "Ljava/util/HashMap", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;"}) BufferedReader $r1) throws ParseException, IOException {
        char $c0 = zzk($r1);
        if ($c0 != 'n') {
            if ($c0 == '{') {
                this.Lo.push(Integer.valueOf(1));
                HashMap $r6 = new HashMap();
                while (true) {
                    switch (zzk($r1)) {
                        case '\u0000':
                            throw new ParseException("Unexpected EOF");
                        case '\"':
                            String $r8 = zzb($r1, this.Le, this.Lg, null);
                            String $r9;
                            if (zzk($r1) == ':') {
                                if (zzk($r1) == '\"') {
                                    $r6.put($r8, zzb($r1, this.Le, this.Lg, null));
                                    $c0 = zzk($r1);
                                    if ($c0 == ',') {
                                        break;
                                    } else if ($c0 == '}') {
                                        zzje(1);
                                        return $r6;
                                    } else {
                                        throw new ParseException("Unexpected character while parsing string map: " + $c0);
                                    }
                                }
                                $r9 = "Expected String value for key ";
                                $r8 = String.valueOf($r8);
                                throw new ParseException($r8.length() != 0 ? $r9.concat($r8) : new String("Expected String value for key "));
                            }
                            $r9 = "No map value found for key ";
                            $r8 = String.valueOf($r8);
                            throw new ParseException($r8.length() != 0 ? $r9.concat($r8) : new String("No map value found for key "));
                        case '}':
                            zzje(1);
                            return $r6;
                        default:
                            break;
                    }
                }
            }
            throw new ParseException("Expected start of a map object");
        }
        zzb($r1, Li);
        return null;
    }

    private int zze(BufferedReader $r1) throws ParseException, IOException {
        int $i0 = zza($r1, this.Lf);
        return $i0 == 0 ? 0 : zza(this.Lf, $i0);
    }

    private long zzf(BufferedReader $r1) throws ParseException, IOException {
        int $i0 = zza($r1, this.Lf);
        return $i0 == 0 ? 0 : zzb(this.Lf, $i0);
    }

    private BigInteger zzg(BufferedReader $r1) throws ParseException, IOException {
        int $i0 = zza($r1, this.Lf);
        return $i0 == 0 ? null : new BigInteger(new String(this.Lf, 0, $i0));
    }

    private float zzh(BufferedReader $r1) throws ParseException, IOException {
        int $i0 = zza($r1, this.Lf);
        return $i0 == 0 ? 0.0f : Float.parseFloat(new String(this.Lf, 0, $i0));
    }

    private double zzi(BufferedReader $r1) throws ParseException, IOException {
        int $i0 = zza($r1, this.Lf);
        return $i0 == 0 ? 0.0d : Double.parseDouble(new String(this.Lf, 0, $i0));
    }

    private BigDecimal zzj(BufferedReader $r1) throws ParseException, IOException {
        int $i0 = zza($r1, this.Lf);
        return $i0 == 0 ? null : new BigDecimal(new String(this.Lf, 0, $i0));
    }

    private void zzje(int $i0) throws ParseException {
        if (this.Lo.isEmpty()) {
            throw new ParseException("Expected state " + $i0 + " but had empty stack");
        }
        int $i1 = ((Integer) this.Lo.pop()).intValue();
        if ($i1 != $i0) {
            throw new ParseException("Expected state " + $i0 + " but had " + $i1);
        }
    }

    private char zzk(BufferedReader $r1) throws ParseException, IOException {
        if ($r1.read(this.Ld) == -1) {
            return '\u0000';
        }
        while (Character.isWhitespace(this.Ld[0])) {
            if ($r1.read(this.Ld) == -1) {
                return '\u0000';
            }
        }
        return this.Ld[0];
    }

    public void parse(@Signature({"(", "Ljava/io/InputStream;", "TT;)V"}) InputStream $r1, @Signature({"(", "Ljava/io/InputStream;", "TT;)V"}) T $r2) throws ParseException {
        BufferedReader $r4 = new BufferedReader(new InputStreamReader($r1), 1024);
        try {
            this.Lo.push(Integer.valueOf(0));
            zza($r4, (FastJsonResponse) $r2);
            zzje(0);
            try {
                $r4.close();
            } catch (IOException e) {
                Log.w("FastParser", "Failed to close reader while parsing.");
            }
        } catch (Throwable $r8) {
            throw new ParseException($r8);
        } catch (Throwable th) {
            try {
                $r4.close();
            } catch (IOException e2) {
                Log.w("FastParser", "Failed to close reader while parsing.");
            }
        }
    }

    public void parse(@Signature({"(", "Ljava/lang/String;", "TT;)V"}) String $r1, @Signature({"(", "Ljava/lang/String;", "TT;)V"}) T $r2) throws ParseException {
        InputStream $r4 = new ByteArrayInputStream($r1.getBytes());
        try {
            parse($r4, (FastJsonResponse) $r2);
        } finally {
            try {
                $r4.close();
            } catch (IOException e) {
                Log.w("FastParser", "Failed to close the input stream while parsing.");
            }
        }
    }
}
