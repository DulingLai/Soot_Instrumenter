package android.support.v4.util;

public class DebugUtils {
    public static void buildShortClassTag(Object $r0, StringBuilder $r1) throws  {
        if ($r0 == null) {
            $r1.append("null");
            return;
        }
        String $r3 = $r0.getClass().getSimpleName();
        String $r4 = $r3;
        if ($r3 == null || $r3.length() <= 0) {
            $r3 = $r0.getClass().getName();
            $r4 = $r3;
            int $i0 = $r3.lastIndexOf(46);
            if ($i0 > 0) {
                $r4 = $r3.substring($i0 + 1);
            }
        }
        $r1.append($r4);
        $r1.append('{');
        $r1.append(Integer.toHexString(System.identityHashCode($r0)));
    }
}
