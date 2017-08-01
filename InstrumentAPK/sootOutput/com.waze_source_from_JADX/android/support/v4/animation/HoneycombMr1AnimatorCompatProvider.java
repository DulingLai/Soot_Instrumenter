package android.support.v4.animation;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.view.View;

class HoneycombMr1AnimatorCompatProvider implements AnimatorProvider {
    private TimeInterpolator mDefaultInterpolator;

    static class AnimatorListenerCompatWrapper implements AnimatorListener {
        final ValueAnimatorCompat mValueAnimatorCompat;
        final AnimatorListenerCompat mWrapped;

        public AnimatorListenerCompatWrapper(AnimatorListenerCompat $r1, ValueAnimatorCompat $r2) throws  {
            this.mWrapped = $r1;
            this.mValueAnimatorCompat = $r2;
        }

        public void onAnimationStart(Animator animation) throws  {
            this.mWrapped.onAnimationStart(this.mValueAnimatorCompat);
        }

        public void onAnimationEnd(Animator animation) throws  {
            this.mWrapped.onAnimationEnd(this.mValueAnimatorCompat);
        }

        public void onAnimationCancel(Animator animation) throws  {
            this.mWrapped.onAnimationCancel(this.mValueAnimatorCompat);
        }

        public void onAnimationRepeat(Animator animation) throws  {
            this.mWrapped.onAnimationRepeat(this.mValueAnimatorCompat);
        }
    }

    static class HoneycombValueAnimatorCompat implements ValueAnimatorCompat {
        final Animator mWrapped;

        public HoneycombValueAnimatorCompat(Animator $r1) throws  {
            this.mWrapped = $r1;
        }

        public void setTarget(View $r1) throws  {
            this.mWrapped.setTarget($r1);
        }

        public void addListener(AnimatorListenerCompat $r1) throws  {
            this.mWrapped.addListener(new AnimatorListenerCompatWrapper($r1, this));
        }

        public void setDuration(long $l0) throws  {
            this.mWrapped.setDuration($l0);
        }

        public void start() throws  {
            this.mWrapped.start();
        }

        public void cancel() throws  {
            this.mWrapped.cancel();
        }

        public void addUpdateListener(final AnimatorUpdateListenerCompat $r1) throws  {
            if (this.mWrapped instanceof ValueAnimator) {
                ((ValueAnimator) this.mWrapped).addUpdateListener(new AnimatorUpdateListener() {
                    public void onAnimationUpdate(ValueAnimator animation) throws  {
                        $r1.onAnimationUpdate(HoneycombValueAnimatorCompat.this);
                    }
                });
            }
        }

        public float getAnimatedFraction() throws  {
            return ((ValueAnimator) this.mWrapped).getAnimatedFraction();
        }
    }

    HoneycombMr1AnimatorCompatProvider() throws  {
    }

    public ValueAnimatorCompat emptyValueAnimator() throws  {
        return new HoneycombValueAnimatorCompat(ValueAnimator.ofFloat(new float[]{0.0f, 1.0f}));
    }

    public void clearInterpolator(View $r1) throws  {
        if (this.mDefaultInterpolator == null) {
            this.mDefaultInterpolator = new ValueAnimator().getInterpolator();
        }
        $r1.animate().setInterpolator(this.mDefaultInterpolator);
    }
}
