package com.abaltatech.mcp.mcs.utils;

public class FilenameUtils {
    public static final char EXTENSION_SEPARATOR = '.';
    private static final char UNIX_SEPARATOR = '/';
    private static final char WINDOWS_SEPARATOR = '\\';

    private static boolean isSeparator(char $c0) throws  {
        return $c0 == UNIX_SEPARATOR || $c0 == WINDOWS_SEPARATOR;
    }

    public static int indexOfLastSeparator(String $r0) throws  {
        return $r0 == null ? -1 : Math.max($r0.lastIndexOf(47), $r0.lastIndexOf(92));
    }

    public static int indexOfExtension(String $r0) throws  {
        if ($r0 == null) {
            return -1;
        }
        int $i0 = $r0.lastIndexOf(46);
        int $i1 = $i0;
        if (indexOfLastSeparator($r0) > $i0) {
            $i1 = -1;
        }
        return $i1;
    }

    public static String getExtension(String $r0) throws  {
        if ($r0 == null) {
            return null;
        }
        int $i0 = indexOfExtension($r0);
        if ($i0 == -1) {
            return "";
        }
        return $r0.substring($i0 + 1);
    }

    public static String getName(String $r0) throws  {
        return $r0 == null ? null : $r0.substring(indexOfLastSeparator($r0) + 1);
    }

    public static String getPath(String $r0) throws  {
        return doGetPath($r0, 1);
    }

    private static String doGetPath(String $r0, int $i0) throws  {
        if ($r0 == null) {
            return null;
        }
        int $i1 = getPrefixLength($r0);
        if ($i1 < 0) {
            return null;
        }
        int $i2 = indexOfLastSeparator($r0);
        $i0 = $i2 + $i0;
        if ($i1 >= $r0.length() || $i2 < 0 || $i1 >= $i0) {
            return "";
        }
        return $r0.substring($i1, $i0);
    }

    public static int getPrefixLength(String $r0) throws  {
        if ($r0 == null) {
            return -1;
        }
        int $i0 = $r0.length();
        if ($i0 == 0) {
            return 0;
        }
        char $c1 = $r0.charAt(0);
        if ($c1 == ':') {
            return -1;
        }
        if ($i0 == 1) {
            if ($c1 == '~') {
                return 2;
            }
            if (isSeparator($c1)) {
                return 1;
            }
            return 0;
        } else if ($c1 == '~') {
            $i2 = $r0.indexOf(47, 1);
            $i3 = $i2;
            $i4 = $r0.indexOf(92, 1);
            int $i5 = $i4;
            if ($i2 == -1 && $i4 == -1) {
                return $i0 + 1;
            }
            if ($i2 == -1) {
                $i3 = $i4;
            }
            if ($i4 == -1) {
                $i5 = $i3;
            }
            return Math.min($i3, $i5) + 1;
        } else {
            char $c6 = $r0.charAt(1);
            if ($c6 == ':') {
                $c1 = Character.toUpperCase($c1);
                if ($c1 < 'A' || $c1 > 'Z') {
                    return -1;
                }
                return ($i0 == 2 || !isSeparator($r0.charAt(2))) ? 2 : 3;
            } else if (!isSeparator($c1) || !isSeparator($c6)) {
                return !isSeparator($c1) ? 0 : 1;
            } else {
                $i0 = $r0.indexOf(47, 2);
                $i2 = $i0;
                $i3 = $r0.indexOf(92, 2);
                $i4 = $i3;
                if (($i0 == -1 && $i3 == -1) || $i0 == 2 || $i3 == 2) {
                    return -1;
                }
                if ($i0 == -1) {
                    $i2 = $i3;
                }
                if ($i3 == -1) {
                    $i4 = $i2;
                }
                return Math.min($i2, $i4) + 1;
            }
        }
    }
}
