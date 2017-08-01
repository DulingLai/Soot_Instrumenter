package android.support.v4.view.animation;

import android.graphics.Path;
import android.os.Build.VERSION;
import android.view.animation.Interpolator;

public final class PathInterpolatorCompat {
    private PathInterpolatorCompat() throws  {
    }

    public static Interpolator create(Path $r0) throws  {
        if (VERSION.SDK_INT >= 21) {
            return PathInterpolatorCompatApi21.create($r0);
        }
        return PathInterpolatorCompatBase.create($r0);
    }

    public static Interpolator create(float $f0, float $f1) throws  {
        if (VERSION.SDK_INT >= 21) {
            return PathInterpolatorCompatApi21.create($f0, $f1);
        }
        return PathInterpolatorCompatBase.create($f0, $f1);
    }

    public static Interpolator create(float $f0, float $f1, float $f2, float $f3) throws  {
        if (VERSION.SDK_INT >= 21) {
            return PathInterpolatorCompatApi21.create($f0, $f1, $f2, $f3);
        }
        return PathInterpolatorCompatBase.create($f0, $f1, $f2, $f3);
    }
}
