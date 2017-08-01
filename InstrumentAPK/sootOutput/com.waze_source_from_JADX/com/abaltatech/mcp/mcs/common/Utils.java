package com.abaltatech.mcp.mcs.common;

public class Utils {
    protected static final char[] hexArray = "0123456789ABCDEF".toCharArray();

    public static String bytesToHex(byte[] $r0) throws  {
        return bytesToHex($r0, 0, $r0.length - 1);
    }

    public static String bytesToHex(byte[] $r0, int $i3, int $i4) throws  {
        int $i0;
        if ($i3 < 0) {
            $i3 = 0;
        }
        if ($i4 >= $r0.length) {
            $i4 = $r0.length - 1;
        }
        if ($i4 >= $i3) {
            $i0 = ($i4 - $i3) + 1;
        } else {
            $i0 = 0;
        }
        $i0 = ($i0 * 6) + 2;
        char[] $r1 = new char[$i0];
        $r1[0] = '{';
        $r1[1] = ' ';
        for (int $i5 = $i3; $i5 <= $i4; $i5++) {
            int $i1 = (($i5 - $i3) * 6) + 2;
            short $s2 = $r0[$i5] & (short) 255;
            $r1[$i1 + 0] = '0';
            $r1[$i1 + 1] = 'x';
            $r1[$i1 + 2] = hexArray[$s2 >>> (short) 4];
            $r1[$i1 + 3] = hexArray[$s2 & (short) 15];
            $r1[$i1 + 4] = ',';
            $r1[$i1 + 5] = ' ';
        }
        if ($i0 > 2) {
            $r1[$i0 - 2] = ' ';
        }
        $r1[$i0 - 1] = '}';
        return new String($r1);
    }
}
