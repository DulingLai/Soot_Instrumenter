package android.support.v4.view.animation;

import android.graphics.Path;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;

class PathInterpolatorCompatApi21 {
    private PathInterpolatorCompatApi21() throws  {
    }

    public static Interpolator create(Path $r0) throws  {
        return new PathInterpolator($r0);
    }

    public static Interpolator create(float $f0, float $f1) throws  {
        return new PathInterpolator($f0, $f1);
    }

    public static Interpolator create(float $f0, float $f1, float $f2, float $f3) throws  {
        return new PathInterpolator($f0, $f1, $f2, $f3);
    }
}
