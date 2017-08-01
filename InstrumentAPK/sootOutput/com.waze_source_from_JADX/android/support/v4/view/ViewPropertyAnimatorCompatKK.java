package android.support.v4.view;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.view.View;

class ViewPropertyAnimatorCompatKK {
    ViewPropertyAnimatorCompatKK() throws  {
    }

    public static void setUpdateListener(final View $r0, final ViewPropertyAnimatorUpdateListener $r1) throws  {
        C01291 $r3 = null;
        if ($r1 != null) {
            $r3 = new AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator valueAnimator) throws  {
                    $r1.onAnimationUpdate($r0);
                }
            };
        }
        $r0.animate().setUpdateListener($r3);
    }
}
