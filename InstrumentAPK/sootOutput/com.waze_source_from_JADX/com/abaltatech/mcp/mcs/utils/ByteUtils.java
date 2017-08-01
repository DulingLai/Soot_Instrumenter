package com.abaltatech.mcp.mcs.utils;

import android.support.v4.internal.view.SupportMenu;
import android.support.v4.view.InputDeviceCompat;
import android.support.v4.view.MotionEventCompat;
import com.abaltatech.mcp.mcs.fileupload.FileUploadSession;
import com.abaltatech.mcp.mcs.logger.MCSLogger;

public class ByteUtils {
    static final /* synthetic */ boolean $assertionsDisabled;
    protected static final char[] hexArray = "0123456789ABCDEF".toCharArray();
    private static final int maxBytes = 64;

    public static int WriteBits(int r1, int r2, int r3, int r4) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.DecodeException: Load method exception in method: com.abaltatech.mcp.mcs.utils.ByteUtils.WriteBits(int, int, int, int):int
	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:116)
	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:249)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
Caused by: jadx.core.utils.exceptions.DecodeException: Unknown instruction: not-int
	at jadx.core.dex.instructions.InsnDecoder.decode(InsnDecoder.java:568)
	at jadx.core.dex.instructions.InsnDecoder.process(InsnDecoder.java:56)
	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:102)
	... 7 more
*/
        /*
        // Can't load method instructions.
        */
        throw new UnsupportedOperationException("Method not decompiled: com.abaltatech.mcp.mcs.utils.ByteUtils.WriteBits(int, int, int, int):int");
    }

    public static int bitwiseNot(int r1) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.DecodeException: Load method exception in method: com.abaltatech.mcp.mcs.utils.ByteUtils.bitwiseNot(int):int
	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:116)
	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:249)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
Caused by: jadx.core.utils.exceptions.DecodeException: Unknown instruction: not-int
	at jadx.core.dex.instructions.InsnDecoder.decode(InsnDecoder.java:568)
	at jadx.core.dex.instructions.InsnDecoder.process(InsnDecoder.java:56)
	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:102)
	... 7 more
*/
        /*
        // Can't load method instructions.
        */
        throw new UnsupportedOperationException("Method not decompiled: com.abaltatech.mcp.mcs.utils.ByteUtils.bitwiseNot(int):int");
    }

    private static byte toByte(int $i0) throws  {
        if ($i0 > 127) {
            $i0 += InputDeviceCompat.SOURCE_ANY;
        }
        return (byte) $i0;
    }

    static {
        boolean $z0;
        if (ByteUtils.class.desiredAssertionStatus()) {
            $z0 = false;
        } else {
            $z0 = true;
        }
        $assertionsDisabled = $z0;
    }

    public static int toUnsignedInteger(byte $b0) throws  {
        if ($b0 >= (byte) 0) {
            return $b0;
        }
        return $b0 + 256;
    }

    public static int getWord(byte[] $r0, int $i0) throws  {
        return getUnsignedSafe($r0, $i0 + 1) + ((getUnsignedSafe($r0, $i0) << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK);
    }

    public static int getUnsignedSafe(byte[] $r0, int $i0) throws  {
        if ($r0 == null || $i0 >= $r0.length || $i0 < 0) {
            return 0;
        }
        return toUnsignedInteger($r0[$i0]);
    }

    public static int onesComplement(int $i0) throws  {
        int $i1 = $i0;
        while ((SupportMenu.CATEGORY_MASK & $i1) != 0) {
            $i1 = ($i1 & SupportMenu.USER_MASK) + ($i1 >>> 16);
        }
        return $i1;
    }

    public static int getInt(byte[] $r0, int $i0, int $i1) throws  {
        int $i3 = 0;
        int $i4 = 0;
        while ($i4 < 4 && $i4 < $i1 && $i4 + $i0 < $r0.length) {
            $i3 = ($i3 * 256) + toUnsignedInteger($r0[$i4 + $i0]);
            $i4++;
        }
        return $i3;
    }

    public static int sumWords(int $i3, byte[] $r0, int $i0, int $i1) throws  {
        for (int $i4 = 0; $i4 < $i1; $i4++) {
            $i3 += getWord($r0, ($i4 * 2) + $i0);
        }
        return $i3;
    }

    public static String toText(byte[] $r0) throws  {
        String $r1 = "";
        if ($r0 == null) {
            return "";
        }
        if ($r0.length <= 0) {
            return "";
        }
        for (int $i0 = 0; $i0 < $r0.length; $i0++) {
            if ($i0 > 0) {
                $r1 = $r1 + FileUploadSession.SEPARATOR;
            }
            $r1 = $r1 + toUnsignedInteger($r0[$i0]);
        }
        return $r1;
    }

    public static void writeToArray(int $i0, byte[] $r0, int $i1, int $i2) throws  {
        int $i5 = 0;
        while ($i5 < 4 && $i5 < $i2 && $r0 != null) {
            $r0[$i5 + $i1] = (byte) ($i0 >>> ((($i2 - 1) - $i5) * 8));
            $i5++;
        }
    }

    public static void wordToArray(int $i0, byte[] $r0, int $i1) throws  {
        $r0[$i1] = toByte((MotionEventCompat.ACTION_POINTER_INDEX_MASK & $i0) >> 8);
        $r0[$i1 + 1] = toByte($i0 & 255);
    }

    public static int ReadWord(byte[] $r0, int $i0) throws  {
        return getWord($r0, $i0);
    }

    public static void WriteWord(byte[] $r0, int $i0, int $i1) throws  {
        wordToArray($i1, $r0, $i0);
    }

    public static int ReadDWord(byte[] $r0, int $i0) throws  {
        if (!$assertionsDisabled && ($r0 == null || $i0 + 4 > $r0.length)) {
            throw new AssertionError();
        } else if ($r0 == null) {
            return 0;
        } else {
            if ($i0 + 4 <= $r0.length) {
                return (((toUnsignedInteger($r0[$i0]) << 24) | (toUnsignedInteger($r0[$i0 + 1]) << 16)) | (toUnsignedInteger($r0[$i0 + 2]) << 8)) | toUnsignedInteger($r0[$i0 + 3]);
            }
            return 0;
        }
    }

    public static void WriteDWord(byte[] $r0, int $i0, int $i1) throws  {
        if (!$assertionsDisabled && ($r0 == null || $i0 + 4 > $r0.length)) {
            throw new AssertionError();
        } else if ($r0 != null && $i0 + 4 <= $r0.length) {
            $r0[$i0] = (byte) (($i1 >>> 24) & 255);
            $r0[$i0 + 1] = (byte) (($i1 >>> 16) & 255);
            $r0[$i0 + 2] = (byte) (($i1 >>> 16) & 255);
            $r0[$i0 + 3] = (byte) ($i1 & 255);
        }
    }

    public static int ReadBits(int $i0, int $i1, int $i2) throws  {
        return ($i0 >>> $i1) & ((byte) -1 >>> (32 - $i2));
    }

    public static String toString(byte[] $r0, int $i0, int $i3) throws  {
        if ($r0 == null || $i0 >= $r0.length || $i3 <= 0) {
            return "<Incorrect buffer, start or length!>";
        }
        if ($i0 + $i3 > $r0.length) {
            $i3 = $r0.length - $i0;
            if (!$assertionsDisabled) {
                throw new AssertionError();
            }
        }
        StringBuilder $r1 = new StringBuilder();
        int $i4 = 0;
        while ($i4 < $i3) {
            if ($i3 <= 64 || $i4 < 64 || $i4 >= $i3 - 2) {
                short $s2 = $r0[$i4 + $i0] & (short) 255;
                $r1.append(hexArray[$s2 >>> (short) 4]);
                $r1.append(hexArray[$s2 & (short) 15]);
                $r1.append(' ');
            } else if ($i4 == 64) {
                $r1.append("... ");
            }
            $i4++;
        }
        return $r1.toString();
    }

    public static void dumpBuffer(byte[] $r0, int $i0, int $i1) throws  {
        if ($i1 > 0) {
            MCSLogger.log("dumpBuffer", toString($r0, $i0, $i1));
        }
    }
}
