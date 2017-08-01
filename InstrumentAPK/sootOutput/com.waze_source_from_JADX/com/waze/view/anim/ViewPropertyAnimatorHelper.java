package com.waze.view.anim;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.Interpolator;

public class ViewPropertyAnimatorHelper {
    public static final long STANDARD_ANIMATION_DURATION = 300;
    public static final Interpolator STANDARD_INTERPOLATOR = EasingInterpolators.SMOOTH_EASE_OUT;

    public static ViewPropertyAnimator initAnimation(View view) {
        return initAnimation(view, 300);
    }

    public static ViewPropertyAnimator initAnimation(View view, long duration) {
        return initAnimation(view, duration, STANDARD_INTERPOLATOR);
    }

    public static ViewPropertyAnimator initAnimation(View view, long duration, Interpolator interpolator) {
        return view.animate().setDuration(duration).setInterpolator(interpolator);
    }

    public static AnimatorListener createAnimationEndListener(final Runnable animationEndRunnable) {
        return new AnimatorListener() {
            public void onAnimationStart(Animator animation) {
            }

            public void onAnimationEnd(Animator animation) {
                if (animationEndRunnable != null) {
                    animationEndRunnable.run();
                }
            }

            public void onAnimationCancel(Animator animation) {
            }

            public void onAnimationRepeat(Animator animation) {
            }
        };
    }

    public static AnimatorListener createGoneWhenDoneListener(final View view) {
        return createAnimationEndListener(new Runnable() {
            public void run() {
                view.setVisibility(8);
            }
        });
    }

    public static AnimatorListener createInvisibleWhenDoneListener(final View view) {
        return createAnimationEndListener(new Runnable() {
            public void run() {
                view.setVisibility(4);
            }
        });
    }
}
