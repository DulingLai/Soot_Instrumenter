package com.spotify.protocol.coding;

public final class Objects {
    private Objects() throws  {
    }

    public static boolean equals(Object $r0, Object $r1) throws  {
        return $r0 == $r1 || ($r0 != null && $r0.equals($r1));
    }

    public static int hashCode(Object... $r0) throws  {
        if ($r0 == null) {
            return 0;
        }
        int $i1 = 1;
        for (Object $r1 : $r0) {
            int $i2;
            if ($r1 == null) {
                $i2 = 0;
            } else {
                $i2 = $r1.hashCode();
            }
            $i1 = ($i1 * 31) + $i2;
        }
        return $i1;
    }
}
