package com.waze.view.drawables;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.graphics.drawable.Drawable;
import android.view.animation.Interpolator;

public abstract class AnimatingDrawable extends Drawable {
    public static final int MAX_LEVEL = 10000;
    public static final int MIN_LEVEL = 0;
    private ValueAnimator mAnimator;

    class C29931 implements AnimatorUpdateListener {
        C29931() {
        }

        public void onAnimationUpdate(ValueAnimator animation) {
            AnimatingDrawable.this.setLevel(((Integer) animation.getAnimatedValue()).intValue());
            AnimatingDrawable.this.invalidateSelf();
        }
    }

    protected boolean onLevelChange(int level) {
        return true;
    }

    protected void animate(final int fromLevel, final int toLevel, long duration, Interpolator interpolator) {
        this.mAnimator = ValueAnimator.ofInt(new int[]{fromLevel, toLevel});
        this.mAnimator.setDuration(duration);
        this.mAnimator.setInterpolator(interpolator);
        this.mAnimator.addUpdateListener(new C29931());
        this.mAnimator.addListener(new AnimatorListener() {
            public void onAnimationStart(Animator animation) {
                AnimatingDrawable.this.setLevel(fromLevel);
            }

            public void onAnimationEnd(Animator animation) {
                AnimatingDrawable.this.setLevel(toLevel);
            }

            public void onAnimationCancel(Animator animation) {
            }

            public void onAnimationRepeat(Animator animation) {
            }
        });
        this.mAnimator.start();
    }

    public void cancelAnimation() {
        if (this.mAnimator != null) {
            this.mAnimator.cancel();
        }
    }
}
