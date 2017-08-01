package android.support.v4.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.widget.EdgeEffect;

class EdgeEffectCompatIcs {
    EdgeEffectCompatIcs() throws  {
    }

    public static Object newEdgeEffect(Context $r0) throws  {
        return new EdgeEffect($r0);
    }

    public static void setSize(Object $r0, int $i0, int $i1) throws  {
        ((EdgeEffect) $r0).setSize($i0, $i1);
    }

    public static boolean isFinished(Object $r0) throws  {
        return ((EdgeEffect) $r0).isFinished();
    }

    public static void finish(Object $r0) throws  {
        ((EdgeEffect) $r0).finish();
    }

    public static boolean onPull(Object $r0, float $f0) throws  {
        ((EdgeEffect) $r0).onPull($f0);
        return true;
    }

    public static boolean onRelease(Object $r0) throws  {
        EdgeEffect $r1 = (EdgeEffect) $r0;
        $r1.onRelease();
        return $r1.isFinished();
    }

    public static boolean onAbsorb(Object $r0, int $i0) throws  {
        ((EdgeEffect) $r0).onAbsorb($i0);
        return true;
    }

    public static boolean draw(Object $r1, Canvas $r0) throws  {
        return ((EdgeEffect) $r1).draw($r0);
    }
}
