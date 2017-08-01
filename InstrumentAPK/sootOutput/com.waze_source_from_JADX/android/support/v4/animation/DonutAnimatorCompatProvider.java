package android.support.v4.animation;

import android.view.View;
import java.util.ArrayList;
import java.util.List;

class DonutAnimatorCompatProvider implements AnimatorProvider {

    private static class DonutFloatValueAnimator implements ValueAnimatorCompat {
        private long mDuration = 200;
        private boolean mEnded = false;
        private float mFraction = 0.0f;
        List<AnimatorListenerCompat> mListeners = new ArrayList();
        private Runnable mLoopRunnable = new C00071();
        private long mStartTime;
        private boolean mStarted = false;
        View mTarget;
        List<AnimatorUpdateListenerCompat> mUpdateListeners = new ArrayList();

        class C00071 implements Runnable {
            C00071() throws  {
            }

            public void run() throws  {
                float $f0 = (((float) (DonutFloatValueAnimator.this.getTime() - DonutFloatValueAnimator.this.mStartTime)) * 1.0f) / ((float) DonutFloatValueAnimator.this.mDuration);
                if ($f0 > 1.0f || DonutFloatValueAnimator.this.mTarget.getParent() == null) {
                    $f0 = 1.0f;
                }
                DonutFloatValueAnimator.this.mFraction = $f0;
                DonutFloatValueAnimator.this.notifyUpdateListeners();
                if (DonutFloatValueAnimator.this.mFraction >= 1.0f) {
                    DonutFloatValueAnimator.this.dispatchEnd();
                } else {
                    DonutFloatValueAnimator.this.mTarget.postDelayed(DonutFloatValueAnimator.this.mLoopRunnable, 16);
                }
            }
        }

        private void notifyUpdateListeners() throws  {
            for (int $i0 = this.mUpdateListeners.size() - 1; $i0 >= 0; $i0--) {
                ((AnimatorUpdateListenerCompat) this.mUpdateListeners.get($i0)).onAnimationUpdate(this);
            }
        }

        public void setTarget(View $r1) throws  {
            this.mTarget = $r1;
        }

        public void addListener(AnimatorListenerCompat $r1) throws  {
            this.mListeners.add($r1);
        }

        public void setDuration(long $l0) throws  {
            if (!this.mStarted) {
                this.mDuration = $l0;
            }
        }

        public void start() throws  {
            if (!this.mStarted) {
                this.mStarted = true;
                dispatchStart();
                this.mFraction = 0.0f;
                this.mStartTime = getTime();
                this.mTarget.postDelayed(this.mLoopRunnable, 16);
            }
        }

        private long getTime() throws  {
            return this.mTarget.getDrawingTime();
        }

        private void dispatchStart() throws  {
            for (int $i0 = this.mListeners.size() - 1; $i0 >= 0; $i0--) {
                ((AnimatorListenerCompat) this.mListeners.get($i0)).onAnimationStart(this);
            }
        }

        private void dispatchEnd() throws  {
            for (int $i0 = this.mListeners.size() - 1; $i0 >= 0; $i0--) {
                ((AnimatorListenerCompat) this.mListeners.get($i0)).onAnimationEnd(this);
            }
        }

        private void dispatchCancel() throws  {
            for (int $i0 = this.mListeners.size() - 1; $i0 >= 0; $i0--) {
                ((AnimatorListenerCompat) this.mListeners.get($i0)).onAnimationCancel(this);
            }
        }

        public void cancel() throws  {
            if (!this.mEnded) {
                this.mEnded = true;
                if (this.mStarted) {
                    dispatchCancel();
                }
                dispatchEnd();
            }
        }

        public void addUpdateListener(AnimatorUpdateListenerCompat $r1) throws  {
            this.mUpdateListeners.add($r1);
        }

        public float getAnimatedFraction() throws  {
            return this.mFraction;
        }
    }

    DonutAnimatorCompatProvider() throws  {
    }

    public ValueAnimatorCompat emptyValueAnimator() throws  {
        return new DonutFloatValueAnimator();
    }

    public void clearInterpolator(View view) throws  {
    }
}
