package android.support.v4.animation;

import android.os.Build.VERSION;
import android.view.View;

public final class AnimatorCompatHelper {
    private static final AnimatorProvider IMPL;

    static {
        if (VERSION.SDK_INT >= 12) {
            IMPL = new HoneycombMr1AnimatorCompatProvider();
        } else {
            IMPL = new DonutAnimatorCompatProvider();
        }
    }

    public static ValueAnimatorCompat emptyValueAnimator() throws  {
        return IMPL.emptyValueAnimator();
    }

    private AnimatorCompatHelper() throws  {
    }

    public static void clearInterpolator(View $r0) throws  {
        IMPL.clearInterpolator($r0);
    }
}
