package android.support.v4.view;

import android.view.View;
import android.view.animation.Interpolator;

class ViewPropertyAnimatorCompatJellybeanMr2 {
    ViewPropertyAnimatorCompatJellybeanMr2() throws  {
    }

    public static Interpolator getInterpolator(View $r0) throws  {
        return (Interpolator) $r0.animate().getInterpolator();
    }
}
