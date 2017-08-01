package android.support.v4.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import android.view.animation.Interpolator;

class ViewPropertyAnimatorCompatICS {
    ViewPropertyAnimatorCompatICS() throws  {
    }

    public static void setDuration(View $r0, long $l0) throws  {
        $r0.animate().setDuration($l0);
    }

    public static void alpha(View $r0, float $f0) throws  {
        $r0.animate().alpha($f0);
    }

    public static void translationX(View $r0, float $f0) throws  {
        $r0.animate().translationX($f0);
    }

    public static void translationY(View $r0, float $f0) throws  {
        $r0.animate().translationY($f0);
    }

    public static long getDuration(View $r0) throws  {
        return $r0.animate().getDuration();
    }

    public static void setInterpolator(View $r0, Interpolator $r1) throws  {
        $r0.animate().setInterpolator($r1);
    }

    public static void setStartDelay(View $r0, long $l0) throws  {
        $r0.animate().setStartDelay($l0);
    }

    public static long getStartDelay(View $r0) throws  {
        return $r0.animate().getStartDelay();
    }

    public static void alphaBy(View $r0, float $f0) throws  {
        $r0.animate().alphaBy($f0);
    }

    public static void rotation(View $r0, float $f0) throws  {
        $r0.animate().rotation($f0);
    }

    public static void rotationBy(View $r0, float $f0) throws  {
        $r0.animate().rotationBy($f0);
    }

    public static void rotationX(View $r0, float $f0) throws  {
        $r0.animate().rotationX($f0);
    }

    public static void rotationXBy(View $r0, float $f0) throws  {
        $r0.animate().rotationXBy($f0);
    }

    public static void rotationY(View $r0, float $f0) throws  {
        $r0.animate().rotationY($f0);
    }

    public static void rotationYBy(View $r0, float $f0) throws  {
        $r0.animate().rotationYBy($f0);
    }

    public static void scaleX(View $r0, float $f0) throws  {
        $r0.animate().scaleX($f0);
    }

    public static void scaleXBy(View $r0, float $f0) throws  {
        $r0.animate().scaleXBy($f0);
    }

    public static void scaleY(View $r0, float $f0) throws  {
        $r0.animate().scaleY($f0);
    }

    public static void scaleYBy(View $r0, float $f0) throws  {
        $r0.animate().scaleYBy($f0);
    }

    public static void cancel(View $r0) throws  {
        $r0.animate().cancel();
    }

    public static void m12x(View $r0, float $f0) throws  {
        $r0.animate().x($f0);
    }

    public static void xBy(View $r0, float $f0) throws  {
        $r0.animate().xBy($f0);
    }

    public static void m13y(View $r0, float $f0) throws  {
        $r0.animate().y($f0);
    }

    public static void yBy(View $r0, float $f0) throws  {
        $r0.animate().yBy($f0);
    }

    public static void translationXBy(View $r0, float $f0) throws  {
        $r0.animate().translationXBy($f0);
    }

    public static void translationYBy(View $r0, float $f0) throws  {
        $r0.animate().translationYBy($f0);
    }

    public static void start(View $r0) throws  {
        $r0.animate().start();
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
