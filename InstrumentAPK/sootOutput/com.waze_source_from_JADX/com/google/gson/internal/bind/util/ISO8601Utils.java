package com.google.gson.internal.bind.util;

import com.abaltatech.mcp.mcs.utils.FilenameUtils;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class ISO8601Utils {
    private static final TimeZone TIMEZONE_UTC = TimeZone.getTimeZone(UTC_ID);
    private static final String UTC_ID = "UTC";

    public static java.util.Date parse(java.lang.String r31, java.text.ParsePosition r32) throws java.text.ParseException {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:76:0x013c
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
        r0 = r32;	 Catch:{  }
        r3 = r0.getIndex();	 Catch:{  }
        r4 = r3 + 4;	 Catch:{  }
        r0 = r31;	 Catch:{  }
        r3 = parseInt(r0, r3, r4);	 Catch:{  }
        r6 = 45;	 Catch:{  }
        r0 = r31;	 Catch:{  }
        r5 = checkOffset(r0, r4, r6);	 Catch:{  }
        if (r5 == 0) goto L_0x001a;
    L_0x0018:
        r4 = r4 + 1;
    L_0x001a:
        r7 = r4 + 2;	 Catch:{  }
        r0 = r31;	 Catch:{  }
        r4 = parseInt(r0, r4, r7);	 Catch:{  }
        r6 = 45;	 Catch:{  }
        r0 = r31;	 Catch:{  }
        r5 = checkOffset(r0, r7, r6);	 Catch:{  }
        if (r5 == 0) goto L_0x0353;
    L_0x002c:
        r7 = r7 + 1;
    L_0x002e:
        r8 = r7 + 2;	 Catch:{  }
        r0 = r31;	 Catch:{  }
        r7 = parseInt(r0, r7, r8);	 Catch:{  }
        r9 = 0;
        r10 = 0;
        r11 = 0;
        r12 = 0;	 Catch:{  }
        r6 = 84;	 Catch:{  }
        r0 = r31;	 Catch:{  }
        r5 = checkOffset(r0, r8, r6);	 Catch:{  }
        if (r5 != 0) goto L_0x005d;	 Catch:{  }
    L_0x0044:
        r0 = r31;	 Catch:{  }
        r13 = r0.length();	 Catch:{  }
        if (r13 > r8) goto L_0x005d;
    L_0x004c:
        r14 = new java.util.GregorianCalendar;
        r4 = r4 + -1;	 Catch:{  }
        r14.<init>(r3, r4, r7);	 Catch:{  }
        r0 = r32;	 Catch:{  }
        r0.setIndex(r8);	 Catch:{  }
        r15 = r14.getTime();	 Catch:{  }
        return r15;
    L_0x005d:
        if (r5 == 0) goto L_0x00e6;	 Catch:{  }
    L_0x005f:
        r9 = r8 + 1;
        r10 = r9 + 2;	 Catch:{  }
        r0 = r31;	 Catch:{  }
        r9 = parseInt(r0, r9, r10);	 Catch:{  }
        r6 = 58;	 Catch:{  }
        r0 = r31;	 Catch:{  }
        r5 = checkOffset(r0, r10, r6);	 Catch:{  }
        if (r5 == 0) goto L_0x0075;
    L_0x0073:
        r10 = r10 + 1;
    L_0x0075:
        r8 = r10 + 2;	 Catch:{  }
        r0 = r31;	 Catch:{  }
        r10 = parseInt(r0, r10, r8);	 Catch:{  }
        r6 = 58;	 Catch:{  }
        r0 = r31;	 Catch:{  }
        r5 = checkOffset(r0, r8, r6);	 Catch:{  }
        if (r5 == 0) goto L_0x034d;
    L_0x0087:
        r8 = r8 + 1;
        r13 = r8;	 Catch:{  }
    L_0x008a:
        r0 = r31;	 Catch:{  }
        r8 = r0.length();	 Catch:{  }
        if (r8 <= r13) goto L_0x0347;	 Catch:{  }
    L_0x0092:
        r0 = r31;	 Catch:{  }
        r16 = r0.charAt(r13);	 Catch:{  }
        r6 = 90;
        r0 = r16;
        if (r0 == r6) goto L_0x0347;
    L_0x009e:
        r6 = 43;
        r0 = r16;
        if (r0 == r6) goto L_0x0347;
    L_0x00a4:
        r6 = 45;
        r0 = r16;
        if (r0 == r6) goto L_0x0347;
    L_0x00aa:
        r8 = r13 + 2;	 Catch:{  }
        r0 = r31;	 Catch:{  }
        r13 = parseInt(r0, r13, r8);	 Catch:{  }
        r11 = r13;
        r6 = 59;	 Catch:{  }
        if (r13 <= r6) goto L_0x00bd;	 Catch:{  }
    L_0x00b7:
        r6 = 63;	 Catch:{  }
        if (r13 >= r6) goto L_0x00bd;	 Catch:{  }
    L_0x00bb:
        r11 = 59;	 Catch:{  }
    L_0x00bd:
        r6 = 46;	 Catch:{  }
        r0 = r31;	 Catch:{  }
        r5 = checkOffset(r0, r8, r6);	 Catch:{  }
        if (r5 == 0) goto L_0x00e6;	 Catch:{  }
    L_0x00c7:
        r12 = r8 + 1;
        r8 = r12 + 1;	 Catch:{  }
        r0 = r31;	 Catch:{  }
        r8 = indexOfNonDigit(r0, r8);	 Catch:{  }
        r13 = r12 + 3;	 Catch:{  }
        r17 = java.lang.Math.min(r8, r13);	 Catch:{  }
        r0 = r31;	 Catch:{  }
        r1 = r17;	 Catch:{  }
        r13 = parseInt(r0, r12, r1);	 Catch:{  }
        r12 = r17 - r12;
        switch(r12) {
            case 1: goto L_0x019b;
            case 2: goto L_0x0198;
            default: goto L_0x00e4;
        };
    L_0x00e4:
        goto L_0x00e5;
    L_0x00e5:
        r12 = r13;
    L_0x00e6:
        r0 = r31;	 Catch:{  }
        r13 = r0.length();	 Catch:{  }
        if (r13 > r8) goto L_0x019e;	 Catch:{  }
    L_0x00ee:
        r18 = new java.lang.IllegalArgumentException;	 Catch:{  }
        r19 = "No time zone indicator";	 Catch:{  }
        r0 = r18;	 Catch:{  }
        r1 = r19;	 Catch:{  }
        r0.<init>(r1);	 Catch:{  }
        throw r18;	 Catch:{ NumberFormatException -> 0x02d4, IllegalArgumentException -> 0x030f }
    L_0x00fa:
        r20 = move-exception;
        r21 = r20;
    L_0x00fd:
        if (r31 != 0) goto L_0x0317;
    L_0x00ff:
        r31 = 0;
    L_0x0101:
        r0 = r21;
        r22 = r0.getMessage();
        r23 = r22;
        if (r22 == 0) goto L_0x0113;
    L_0x010b:
        r0 = r22;
        r5 = r0.isEmpty();
        if (r5 == 0) goto L_0x0150;
    L_0x0113:
        r24 = new java.lang.StringBuilder;
        r0 = r24;
        r0.<init>();
        r19 = "(";
        r0 = r24;
        r1 = r19;
        r24 = r0.append(r1);
        r0 = r21;
        r25 = r0.getClass();
        r0 = r25;
        r22 = r0.getName();
        r0 = r24;
        r1 = r22;
        r24 = r0.append(r1);
        goto L_0x0140;
    L_0x0139:
        goto L_0x00e6;
        goto L_0x0140;
    L_0x013d:
        goto L_0x00e6;
    L_0x0140:
        r19 = ")";
        r0 = r24;
        r1 = r19;
        r24 = r0.append(r1);
        r0 = r24;
        r23 = r0.toString();
    L_0x0150:
        r26 = new java.text.ParseException;
        r24 = new java.lang.StringBuilder;
        r0 = r24;
        r0.<init>();
        r19 = "Failed to parse date [";
        r0 = r24;
        r1 = r19;
        r24 = r0.append(r1);
        r0 = r24;
        r1 = r31;
        r24 = r0.append(r1);
        r19 = "]: ";
        r0 = r24;
        r1 = r19;
        r24 = r0.append(r1);
        r0 = r24;
        r1 = r23;
        r24 = r0.append(r1);
        r0 = r24;
        r31 = r0.toString();
        r0 = r32;
        r3 = r0.getIndex();
        r0 = r26;
        r1 = r31;
        r0.<init>(r1, r3);
        r0 = r26;
        r1 = r21;
        r0.initCause(r1);
        throw r26;
    L_0x0198:
        r12 = r13 * 10;
        goto L_0x0139;
    L_0x019b:
        r12 = r13 * 100;
        goto L_0x013d;
    L_0x019e:
        r0 = r31;	 Catch:{  }
        r16 = r0.charAt(r8);	 Catch:{  }
        r6 = 90;
        r0 = r16;
        if (r0 != r6) goto L_0x01e5;
    L_0x01aa:
        r27 = TIMEZONE_UTC;
        r8 = r8 + 1;
    L_0x01ae:
        r14 = new java.util.GregorianCalendar;	 Catch:{  }
        r0 = r27;	 Catch:{  }
        r14.<init>(r0);	 Catch:{  }
        r6 = 0;	 Catch:{  }
        r14.setLenient(r6);	 Catch:{  }
        r6 = 1;	 Catch:{  }
        r14.set(r6, r3);	 Catch:{  }
        r3 = r4 + -1;	 Catch:{  }
        r6 = 2;	 Catch:{  }
        r14.set(r6, r3);	 Catch:{  }
        r6 = 5;	 Catch:{  }
        r14.set(r6, r7);	 Catch:{  }
        r6 = 11;	 Catch:{  }
        r14.set(r6, r9);	 Catch:{  }
        r6 = 12;	 Catch:{  }
        r14.set(r6, r10);	 Catch:{  }
        r6 = 13;	 Catch:{  }
        r14.set(r6, r11);	 Catch:{  }
        r6 = 14;	 Catch:{  }
        r14.set(r6, r12);	 Catch:{  }
        r0 = r32;	 Catch:{  }
        r0.setIndex(r8);	 Catch:{  }
        r15 = r14.getTime();	 Catch:{  }
        return r15;
    L_0x01e5:
        r6 = 43;	 Catch:{  }
        r0 = r16;	 Catch:{  }
        if (r0 == r6) goto L_0x01f1;	 Catch:{  }
    L_0x01eb:
        r6 = 45;	 Catch:{  }
        r0 = r16;	 Catch:{  }
        if (r0 != r6) goto L_0x02dc;	 Catch:{  }
    L_0x01f1:
        r0 = r31;	 Catch:{  }
        r23 = r0.substring(r8);	 Catch:{  }
        r22 = r23;	 Catch:{  }
        r0 = r23;	 Catch:{  }
        r13 = r0.length();	 Catch:{  }
        r6 = 5;	 Catch:{  }
        if (r13 < r6) goto L_0x0224;	 Catch:{  }
    L_0x0202:
        r0 = r22;	 Catch:{  }
        r13 = r0.length();	 Catch:{  }
        r8 = r8 + r13;
        r23 = "+0000";	 Catch:{  }
        r0 = r23;	 Catch:{  }
        r1 = r22;	 Catch:{  }
        r5 = r0.equals(r1);	 Catch:{  }
        if (r5 != 0) goto L_0x0221;
    L_0x0215:
        r23 = "+00:00";	 Catch:{  }
        r0 = r23;	 Catch:{  }
        r1 = r22;	 Catch:{  }
        r5 = r0.equals(r1);	 Catch:{  }
        if (r5 == 0) goto L_0x0244;
    L_0x0221:
        r27 = TIMEZONE_UTC;
        goto L_0x01ae;
    L_0x0224:
        r24 = new java.lang.StringBuilder;	 Catch:{  }
        r0 = r24;	 Catch:{  }
        r0.<init>();	 Catch:{  }
        r0 = r24;	 Catch:{  }
        r1 = r23;	 Catch:{  }
        r24 = r0.append(r1);	 Catch:{  }
        r19 = "00";	 Catch:{  }
        r0 = r24;	 Catch:{  }
        r1 = r19;	 Catch:{  }
        r24 = r0.append(r1);	 Catch:{  }
        r0 = r24;	 Catch:{  }
        r22 = r0.toString();	 Catch:{  }
        goto L_0x0202;	 Catch:{  }
    L_0x0244:
        r24 = new java.lang.StringBuilder;	 Catch:{  }
        r0 = r24;	 Catch:{  }
        r0.<init>();	 Catch:{  }
        r19 = "GMT";	 Catch:{  }
        r0 = r24;	 Catch:{  }
        r1 = r19;	 Catch:{  }
        r24 = r0.append(r1);	 Catch:{  }
        r0 = r24;	 Catch:{  }
        r1 = r22;	 Catch:{  }
        r24 = r0.append(r1);	 Catch:{  }
        r0 = r24;	 Catch:{  }
        r22 = r0.toString();	 Catch:{  }
        r0 = r22;	 Catch:{  }
        r28 = java.util.TimeZone.getTimeZone(r0);	 Catch:{  }
        r27 = r28;	 Catch:{  }
        r0 = r28;	 Catch:{  }
        r23 = r0.getID();	 Catch:{  }
        r0 = r23;	 Catch:{  }
        r1 = r22;	 Catch:{  }
        r5 = r0.equals(r1);	 Catch:{  }
        if (r5 != 0) goto L_0x01ae;	 Catch:{  }
    L_0x027b:
        r19 = ":";	 Catch:{  }
        r29 = "";	 Catch:{  }
        r0 = r23;	 Catch:{  }
        r1 = r19;	 Catch:{  }
        r2 = r29;	 Catch:{  }
        r23 = r0.replace(r1, r2);	 Catch:{  }
        r0 = r23;	 Catch:{  }
        r1 = r22;	 Catch:{  }
        r5 = r0.equals(r1);	 Catch:{  }
        if (r5 != 0) goto L_0x01ae;
    L_0x0293:
        r20 = new java.lang.IndexOutOfBoundsException;
        r24 = new java.lang.StringBuilder;	 Catch:{  }
        r0 = r24;	 Catch:{  }
        r0.<init>();	 Catch:{  }
        r19 = "Mismatching time zone indicator: ";	 Catch:{  }
        r0 = r24;	 Catch:{  }
        r1 = r19;	 Catch:{  }
        r24 = r0.append(r1);	 Catch:{  }
        r0 = r24;	 Catch:{  }
        r1 = r22;	 Catch:{  }
        r24 = r0.append(r1);	 Catch:{  }
        r19 = " given, resolves to ";	 Catch:{  }
        r0 = r24;	 Catch:{  }
        r1 = r19;	 Catch:{  }
        r24 = r0.append(r1);	 Catch:{  }
        r0 = r28;	 Catch:{  }
        r22 = r0.getID();	 Catch:{  }
        r0 = r24;	 Catch:{  }
        r1 = r22;	 Catch:{  }
        r24 = r0.append(r1);	 Catch:{  }
        r0 = r24;	 Catch:{  }
        r22 = r0.toString();	 Catch:{  }
        r0 = r20;	 Catch:{  }
        r1 = r22;	 Catch:{  }
        r0.<init>(r1);	 Catch:{  }
        throw r20;	 Catch:{ IndexOutOfBoundsException -> 0x00fa }
    L_0x02d4:
        r30 = move-exception;
        goto L_0x02d9;
    L_0x02d6:
        goto L_0x00fd;
    L_0x02d9:
        r21 = r30;
        goto L_0x02d6;
    L_0x02dc:
        r20 = new java.lang.IndexOutOfBoundsException;
        r24 = new java.lang.StringBuilder;	 Catch:{  }
        r0 = r24;	 Catch:{  }
        r0.<init>();	 Catch:{  }
        r19 = "Invalid time zone indicator '";	 Catch:{  }
        r0 = r24;	 Catch:{  }
        r1 = r19;	 Catch:{  }
        r24 = r0.append(r1);	 Catch:{  }
        r0 = r24;	 Catch:{  }
        r1 = r16;	 Catch:{  }
        r24 = r0.append(r1);	 Catch:{  }
        r19 = "'";	 Catch:{  }
        r0 = r24;	 Catch:{  }
        r1 = r19;	 Catch:{  }
        r24 = r0.append(r1);	 Catch:{  }
        r0 = r24;	 Catch:{  }
        r22 = r0.toString();	 Catch:{  }
        r0 = r20;	 Catch:{  }
        r1 = r22;	 Catch:{  }
        r0.<init>(r1);	 Catch:{  }
        throw r20;	 Catch:{ IndexOutOfBoundsException -> 0x00fa }
    L_0x030f:
        r18 = move-exception;
        goto L_0x0314;
    L_0x0311:
        goto L_0x00fd;
    L_0x0314:
        r21 = r18;
        goto L_0x0311;
    L_0x0317:
        r24 = new java.lang.StringBuilder;
        r0 = r24;
        r0.<init>();
        r6 = 34;
        r0 = r24;
        r24 = r0.append(r6);
        r0 = r24;
        r1 = r31;
        r24 = r0.append(r1);
        r19 = "'";
        r0 = r24;
        r1 = r19;
        r24 = r0.append(r1);
        goto L_0x033c;
    L_0x0339:
        goto L_0x0101;
    L_0x033c:
        r0 = r24;
        r31 = r0.toString();
        goto L_0x0339;
        goto L_0x0347;
    L_0x0344:
        goto L_0x00e6;
    L_0x0347:
        r8 = r13;
        goto L_0x0344;
        goto L_0x034d;
    L_0x034a:
        goto L_0x008a;
    L_0x034d:
        r13 = r8;
        goto L_0x0352;
    L_0x034f:
        goto L_0x002e;
    L_0x0352:
        goto L_0x034a;
    L_0x0353:
        goto L_0x034f;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.gson.internal.bind.util.ISO8601Utils.parse(java.lang.String, java.text.ParsePosition):java.util.Date");
    }

    public static String format(Date $r0) throws  {
        return format($r0, false, TIMEZONE_UTC);
    }

    public static String format(Date $r0, boolean $z0) throws  {
        return format($r0, $z0, TIMEZONE_UTC);
    }

    public static String format(Date $r0, boolean $z0, TimeZone $r1) throws  {
        GregorianCalendar $r2 = new GregorianCalendar($r1, Locale.US);
        $r2.setTime($r0);
        StringBuilder $r3 = new StringBuilder(("yyyy-MM-ddThh:mm:ss".length() + ($z0 ? ".sss".length() : 0)) + ($r1.getRawOffset() == 0 ? "Z".length() : "+hh:mm".length()));
        padInt($r3, $r2.get(1), "yyyy".length());
        $r3.append('-');
        padInt($r3, $r2.get(2) + 1, "MM".length());
        $r3.append('-');
        padInt($r3, $r2.get(5), "dd".length());
        $r3.append('T');
        padInt($r3, $r2.get(11), "hh".length());
        $r3.append(':');
        padInt($r3, $r2.get(12), "mm".length());
        $r3.append(':');
        padInt($r3, $r2.get(13), "ss".length());
        if ($z0) {
            $r3.append(FilenameUtils.EXTENSION_SEPARATOR);
            padInt($r3, $r2.get(14), "sss".length());
        }
        int $i0 = $r1.getOffset($r2.getTimeInMillis());
        if ($i0 != 0) {
            char $c4;
            int $i1 = Math.abs(($i0 / 60000) / 60);
            int $i3 = Math.abs(($i0 / 60000) % 60);
            if ($i0 < 0) {
                $c4 = '-';
            } else {
                $c4 = '+';
            }
            $r3.append($c4);
            padInt($r3, $i1, "hh".length());
            $r3.append(':');
            padInt($r3, $i3, "mm".length());
        } else {
            $r3.append('Z');
        }
        return $r3.toString();
    }

    private static boolean checkOffset(String $r0, int $i0, char $c1) throws  {
        return $i0 < $r0.length() && $r0.charAt($i0) == $c1;
    }

    private static int parseInt(String $r0, int $i0, int $i1) throws NumberFormatException {
        if ($i0 < 0 || $i1 > $r0.length() || $i0 > $i1) {
            throw new NumberFormatException($r0);
        }
        int $i4;
        int $i2;
        int $i3 = 0;
        if ($i0 < $i1) {
            $i4 = $i0 + 1;
            $i2 = Character.digit($r0.charAt($i0), 10);
            if ($i2 < 0) {
                throw new NumberFormatException("Invalid number: " + $r0.substring($i0, $i1));
            }
            $i3 = -$i2;
        } else {
            $i4 = $i0;
        }
        while ($i4 < $i1) {
            $i2 = $i4 + 1;
            $i4 = Character.digit($r0.charAt($i4), 10);
            if ($i4 < 0) {
                throw new NumberFormatException("Invalid number: " + $r0.substring($i0, $i1));
            }
            $i3 = ($i3 * 10) - $i4;
            $i4 = $i2;
        }
        return -$i3;
    }

    private static void padInt(StringBuilder $r0, int $i0, int $i1) throws  {
        String $r1 = Integer.toString($i0);
        for ($i0 = $i1 - $r1.length(); $i0 > 0; $i0--) {
            $r0.append('0');
        }
        $r0.append($r1);
    }

    private static int indexOfNonDigit(String $r0, int $i0) throws  {
        while ($i0 < $r0.length()) {
            char $c2 = $r0.charAt($i0);
            if ($c2 < '0' || $c2 > '9') {
                return $i0;
            }
            $i0++;
        }
        return $r0.length();
    }
}
