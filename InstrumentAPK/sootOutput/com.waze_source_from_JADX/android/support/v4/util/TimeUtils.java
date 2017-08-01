package android.support.v4.util;

import java.io.PrintWriter;

public final class TimeUtils {
    public static final int HUNDRED_DAY_FIELD_LEN = 19;
    private static final int SECONDS_PER_DAY = 86400;
    private static final int SECONDS_PER_HOUR = 3600;
    private static final int SECONDS_PER_MINUTE = 60;
    private static char[] sFormatStr = new char[24];
    private static final Object sFormatSync = new Object();

    private static int accumField(int $i0, int $i1, boolean $z0, int $i2) throws  {
        if ($i0 > 99 || ($z0 && $i2 >= 3)) {
            return $i1 + 3;
        }
        if ($i0 > 9 || ($z0 && $i2 >= 2)) {
            return $i1 + 2;
        }
        return ($z0 || $i0 > 0) ? $i1 + 1 : 0;
    }

    private static int printField(char[] $r0, int $i3, char $c0, int $i4, boolean $z0, int $i1) throws  {
        if (!$z0 && $i3 <= 0) {
            return $i4;
        }
        int $i2 = $i4;
        if (($z0 && $i1 >= 3) || $i3 > 99) {
            int $i5 = $i3 / 100;
            $r0[$i4] = (char) ($i5 + 48);
            $i4++;
            $i3 -= $i5 * 100;
        }
        if (($z0 && $i1 >= 2) || $i3 > 9 || $i2 != $i4) {
            $i1 = $i3 / 10;
            $r0[$i4] = (char) ($i1 + 48);
            $i4++;
            $i3 -= $i1 * 10;
        }
        $r0[$i4] = (char) ($i3 + 48);
        $i3 = $i4 + 1;
        $r0[$i3] = $c0;
        return $i3 + 1;
    }

    private static int formatDurationLocked(long $l1, int $i2) throws  {
        if (sFormatStr.length < $i2) {
            sFormatStr = new char[$i2];
        }
        char[] $r0 = sFormatStr;
        if ($l1 == 0) {
            $i2--;
            while (0 < $i2) {
                $r0[0] = ' ';
            }
            $r0[0] = '0';
            return 1;
        }
        char $c4;
        byte $b3;
        Object obj;
        boolean z;
        if ($l1 > 0) {
            $c4 = '+';
        } else {
            $c4 = '-';
            $l1 = -$l1;
        }
        int $i0 = (int) ($l1 % 1000);
        double $d0 = $l1 / 1000;
        double d = $d0;
        $d0 = (double) $d0;
        double d2 = $d0;
        int $i6 = (int) Math.floor($d0);
        int $i7 = $i6;
        int $i8 = 0;
        int $i9 = 0;
        int $i10 = 0;
        if ($i6 > SECONDS_PER_DAY) {
            $i8 = $i6 / SECONDS_PER_DAY;
            $i7 = $i6 - (SECONDS_PER_DAY * $i8);
        }
        if ($i7 > 3600) {
            int $i62 = $i7 / 3600;
            $i9 = $i62;
            $i7 -= $i62 * 3600;
        }
        if ($i7 > 60) {
            $i10 = $i7 / 60;
            $i7 -= $i10 * 60;
        }
        $i6 = 0;
        if ($i2 != 0) {
            boolean z2;
            int $i11 = accumField($i8, 1, false, 0);
            if ($i11 > 0) {
                z2 = true;
            } else {
                z2 = false;
            }
            $i11 += accumField($i9, 1, z2, 2);
            if ($i11 > 0) {
                z2 = true;
            } else {
                z2 = false;
            }
            $i11 += accumField($i10, 1, z2, 2);
            if ($i11 > 0) {
                z2 = true;
            } else {
                z2 = false;
            }
            $i11 += accumField($i7, 1, z2, 2);
            if ($i11 > 0) {
                $b3 = (byte) 3;
            } else {
                $b3 = (byte) 0;
            }
            for ($i11 += accumField($i0, 2, true, $b3) + 1; $i11 < $i2; $i11++) {
                $r0[$i6] = ' ';
                $i6++;
            }
        }
        $r0[$i6] = $c4;
        $i6++;
        if ($i2 != 0) {
            obj = 1;
        } else {
            obj = null;
        }
        $i2 = printField($r0, $i8, 'd', $i6, false, 0);
        if ($i2 != $i6) {
            z = true;
        } else {
            z = false;
        }
        if (obj != null) {
            $b3 = (byte) 2;
        } else {
            $b3 = (byte) 0;
        }
        $i2 = printField($r0, $i9, 'h', $i2, z, $b3);
        if ($i2 != $i6) {
            z = true;
        } else {
            z = false;
        }
        if (obj != null) {
            $b3 = (byte) 2;
        } else {
            $b3 = (byte) 0;
        }
        $i2 = printField($r0, $i10, 'm', $i2, z, $b3);
        if ($i2 != $i6) {
            z = true;
        } else {
            z = false;
        }
        if (obj != null) {
            $b3 = (byte) 2;
        } else {
            $b3 = (byte) 0;
        }
        $i2 = printField($r0, $i7, 's', $i2, z, $b3);
        if (obj == null || $i2 == $i6) {
            $b3 = (byte) 0;
        } else {
            $b3 = (byte) 3;
        }
        $i2 = printField($r0, $i0, 'm', $i2, true, $b3);
        $r0[$i2] = 's';
        return $i2 + 1;
    }

    public static void formatDuration(long $l0, StringBuilder $r0) throws  {
        synchronized (sFormatSync) {
            $r0.append(sFormatStr, 0, formatDurationLocked($l0, 0));
        }
    }

    public static void formatDuration(long $l0, PrintWriter $r0, int $i1) throws  {
        synchronized (sFormatSync) {
            $r0.print(new String(sFormatStr, 0, formatDurationLocked($l0, $i1)));
        }
    }

    public static void formatDuration(long $l0, PrintWriter $r0) throws  {
        formatDuration($l0, $r0, 0);
    }

    public static void formatDuration(long $l0, long $l1, PrintWriter $r0) throws  {
        if ($l0 == 0) {
            $r0.print("--");
        } else {
            formatDuration($l0 - $l1, $r0, 0);
        }
    }

    private TimeUtils() throws  {
    }
}
