package android.support.v4.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;

class ViewPropertyAnimatorCompatJB {
    ViewPropertyAnimatorCompatJB() throws  {
    }

    public static void withStartAction(View $r0, Runnable $r1) throws  {
        $r0.animate().withStartAction($r1);
    }

    public static void withEndAction(View $r0, Runnable $r1) throws  {
        $r0.animate().withEndAction($r1);
    }

    public static void withLayer(View $r0) throws  {
        $r0.animate().withLayer();
    }

    public static void setListener(final View $r0, final ViewPropertyAnimatorListener $r1) throws  {
        if ($r1 != null) {
            $r0.animate().setListener(new AnimatorListenerAdapter() {
                public void onAnimationCancel(Animator animation) throws  {
                    $r1.onAnimationCancel($r0);
                }

                public void onAnimationEnd(Animator animation) throws  {
                    $r1.onAnimationEnd($r0);
                }

                public void onAnimationStart(Animator animation) throws  {
                    $r1.onAnimationStart($r0);
                }
            });
        } else {
            $r0.animate().setListener(null);
        }
    }
}
