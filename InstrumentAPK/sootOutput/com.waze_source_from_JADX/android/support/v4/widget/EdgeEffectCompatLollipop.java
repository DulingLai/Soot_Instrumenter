package android.support.v4.widget;

import android.widget.EdgeEffect;

class EdgeEffectCompatLollipop {
    EdgeEffectCompatLollipop() throws  {
    }

    public static boolean onPull(Object $r0, float $f0, float $f1) throws  {
        ((EdgeEffect) $r0).onPull($f0, $f1);
        return true;
    }
}
