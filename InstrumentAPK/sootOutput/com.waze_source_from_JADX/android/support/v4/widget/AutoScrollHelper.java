package android.support.v4.widget;

import android.content.res.Resources;
import android.os.SystemClock;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewConfiguration;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import com.waze.map.CanvasFont;
import com.waze.strings.DisplayStrings;

public abstract class AutoScrollHelper implements OnTouchListener {
    private static final int DEFAULT_ACTIVATION_DELAY = ViewConfiguration.getTapTimeout();
    private static final int DEFAULT_EDGE_TYPE = 1;
    private static final float DEFAULT_MAXIMUM_EDGE = Float.MAX_VALUE;
    private static final int DEFAULT_MAXIMUM_VELOCITY_DIPS = 1575;
    private static final int DEFAULT_MINIMUM_VELOCITY_DIPS = 315;
    private static final int DEFAULT_RAMP_DOWN_DURATION = 500;
    private static final int DEFAULT_RAMP_UP_DURATION = 500;
    private static final float DEFAULT_RELATIVE_EDGE = 0.2f;
    private static final float DEFAULT_RELATIVE_VELOCITY = 1.0f;
    public static final int EDGE_TYPE_INSIDE = 0;
    public static final int EDGE_TYPE_INSIDE_EXTEND = 1;
    public static final int EDGE_TYPE_OUTSIDE = 2;
    private static final int HORIZONTAL = 0;
    public static final float NO_MAX = Float.MAX_VALUE;
    public static final float NO_MIN = 0.0f;
    public static final float RELATIVE_UNSPECIFIED = 0.0f;
    private static final int VERTICAL = 1;
    private int mActivationDelay;
    private boolean mAlreadyDelayed;
    private boolean mAnimating;
    private final Interpolator mEdgeInterpolator = new AccelerateInterpolator();
    private int mEdgeType;
    private boolean mEnabled;
    private boolean mExclusive;
    private float[] mMaximumEdges = new float[]{Float.MAX_VALUE, Float.MAX_VALUE};
    private float[] mMaximumVelocity = new float[]{Float.MAX_VALUE, Float.MAX_VALUE};
    private float[] mMinimumVelocity = new float[]{0.0f, 0.0f};
    private boolean mNeedsCancel;
    private boolean mNeedsReset;
    private float[] mRelativeEdges = new float[]{0.0f, 0.0f};
    private float[] mRelativeVelocity = new float[]{0.0f, 0.0f};
    private Runnable mRunnable;
    private final ClampedScroller mScroller = new ClampedScroller();
    private final View mTarget;

    private static class ClampedScroller {
        private long mDeltaTime = 0;
        private int mDeltaX = 0;
        private int mDeltaY = 0;
        private int mEffectiveRampDown;
        private int mRampDownDuration;
        private int mRampUpDuration;
        private long mStartTime = Long.MIN_VALUE;
        private long mStopTime = -1;
        private float mStopValue;
        private float mTargetVelocityX;
        private float mTargetVelocityY;

        private float interpolateValue(float $f0) throws  {
            return ((-4.0f * $f0) * $f0) + (4.0f * $f0);
        }

        public void setRampUpDuration(int $i0) throws  {
            this.mRampUpDuration = $i0;
        }

        public void setRampDownDuration(int $i0) throws  {
            this.mRampDownDuration = $i0;
        }

        public void start() throws  {
            this.mStartTime = AnimationUtils.currentAnimationTimeMillis();
            this.mStopTime = -1;
            this.mDeltaTime = this.mStartTime;
            this.mStopValue = CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR;
            this.mDeltaX = 0;
            this.mDeltaY = 0;
        }

        public void requestStop() throws  {
            long $l1 = AnimationUtils.currentAnimationTimeMillis();
            this.mEffectiveRampDown = AutoScrollHelper.constrain((int) ($l1 - this.mStartTime), 0, this.mRampDownDuration);
            this.mStopValue = getValueAt($l1);
            this.mStopTime = $l1;
        }

        public boolean isFinished() throws  {
            return this.mStopTime > 0 && AnimationUtils.currentAnimationTimeMillis() > this.mStopTime + ((long) this.mEffectiveRampDown);
        }

        private float getValueAt(long $l0) throws  {
            if ($l0 < this.mStartTime) {
                return 0.0f;
            }
            if (this.mStopTime < 0 || $l0 < this.mStopTime) {
                return AutoScrollHelper.constrain(((float) ($l0 - this.mStartTime)) / ((float) this.mRampUpDuration), 0.0f, 1.0f) * CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR;
            }
            $l0 -= this.mStopTime;
            return (AutoScrollHelper.constrain(((float) $l0) / ((float) this.mEffectiveRampDown), 0.0f, 1.0f) * this.mStopValue) + (1.0f - this.mStopValue);
        }

        public void computeScrollDelta() throws  {
            if (this.mDeltaTime == 0) {
                throw new RuntimeException("Cannot compute scroll delta before calling start()");
            }
            long $l1 = AnimationUtils.currentAnimationTimeMillis();
            float $f0 = interpolateValue(getValueAt($l1));
            long $l0 = $l1 - this.mDeltaTime;
            this.mDeltaTime = $l1;
            this.mDeltaX = (int) ((((float) $l0) * $f0) * this.mTargetVelocityX);
            this.mDeltaY = (int) ((((float) $l0) * $f0) * this.mTargetVelocityY);
        }

        public void setTargetVelocity(float $f0, float $f1) throws  {
            this.mTargetVelocityX = $f0;
            this.mTargetVelocityY = $f1;
        }

        public int getHorizontalDirection() throws  {
            return (int) (this.mTargetVelocityX / Math.abs(this.mTargetVelocityX));
        }

        public int getVerticalDirection() throws  {
            return (int) (this.mTargetVelocityY / Math.abs(this.mTargetVelocityY));
        }

        public int getDeltaX() throws  {
            return this.mDeltaX;
        }

        public int getDeltaY() throws  {
            return this.mDeltaY;
        }
    }

    private class ScrollAnimationRunnable implements Runnable {
        private ScrollAnimationRunnable() throws  {
        }

        public void run() throws  {
            if (AutoScrollHelper.this.mAnimating) {
                if (AutoScrollHelper.this.mNeedsReset) {
                    AutoScrollHelper.this.mNeedsReset = false;
                    AutoScrollHelper.this.mScroller.start();
                }
                ClampedScroller $r2 = AutoScrollHelper.this.mScroller;
                if ($r2.isFinished() || !AutoScrollHelper.this.shouldAnimate()) {
                    AutoScrollHelper.this.mAnimating = false;
                    return;
                }
                if (AutoScrollHelper.this.mNeedsCancel) {
                    AutoScrollHelper.this.mNeedsCancel = false;
                    AutoScrollHelper.this.cancelTargetTouch();
                }
                $r2.computeScrollDelta();
                AutoScrollHelper.this.scrollTargetBy($r2.getDeltaX(), $r2.getDeltaY());
                ViewCompat.postOnAnimation(AutoScrollHelper.this.mTarget, this);
            }
        }
    }

    public abstract boolean canTargetScrollHorizontally(int i) throws ;

    public abstract boolean canTargetScrollVertically(int i) throws ;

    public abstract void scrollTargetBy(int i, int i2) throws ;

    public AutoScrollHelper(View $r1) throws  {
        this.mTarget = $r1;
        DisplayMetrics $r6 = Resources.getSystem().getDisplayMetrics();
        int $i0 = (int) ((1575.0f * $r6.density) + CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR);
        int $i1 = (int) ((315.0f * $r6.density) + CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR);
        setMaximumVelocity((float) $i0, (float) $i0);
        setMinimumVelocity((float) $i1, (float) $i1);
        setEdgeType(1);
        setMaximumEdges(Float.MAX_VALUE, Float.MAX_VALUE);
        setRelativeEdges(DEFAULT_RELATIVE_EDGE, DEFAULT_RELATIVE_EDGE);
        setRelativeVelocity(1.0f, 1.0f);
        setActivationDelay(DEFAULT_ACTIVATION_DELAY);
        setRampUpDuration(DisplayStrings.DS_NO_NETWORK_CONNECTION__UNABLE_TO_REPORT);
        setRampDownDuration(DisplayStrings.DS_NO_NETWORK_CONNECTION__UNABLE_TO_REPORT);
    }

    public AutoScrollHelper setEnabled(boolean $z0) throws  {
        if (this.mEnabled && !$z0) {
            requestStop();
        }
        this.mEnabled = $z0;
        return this;
    }

    public boolean isEnabled() throws  {
        return this.mEnabled;
    }

    public AutoScrollHelper setExclusive(boolean $z0) throws  {
        this.mExclusive = $z0;
        return this;
    }

    public boolean isExclusive() throws  {
        return this.mExclusive;
    }

    public AutoScrollHelper setMaximumVelocity(float $f0, float $f1) throws  {
        this.mMaximumVelocity[0] = $f0 / 1000.0f;
        this.mMaximumVelocity[1] = $f1 / 1000.0f;
        return this;
    }

    public AutoScrollHelper setMinimumVelocity(float $f0, float $f1) throws  {
        this.mMinimumVelocity[0] = $f0 / 1000.0f;
        this.mMinimumVelocity[1] = $f1 / 1000.0f;
        return this;
    }

    public AutoScrollHelper setRelativeVelocity(float $f0, float $f1) throws  {
        this.mRelativeVelocity[0] = $f0 / 1000.0f;
        this.mRelativeVelocity[1] = $f1 / 1000.0f;
        return this;
    }

    public AutoScrollHelper setEdgeType(int $i0) throws  {
        this.mEdgeType = $i0;
        return this;
    }

    public AutoScrollHelper setRelativeEdges(float $f0, float $f1) throws  {
        this.mRelativeEdges[0] = $f0;
        this.mRelativeEdges[1] = $f1;
        return this;
    }

    public AutoScrollHelper setMaximumEdges(float $f0, float $f1) throws  {
        this.mMaximumEdges[0] = $f0;
        this.mMaximumEdges[1] = $f1;
        return this;
    }

    public AutoScrollHelper setActivationDelay(int $i0) throws  {
        this.mActivationDelay = $i0;
        return this;
    }

    public AutoScrollHelper setRampUpDuration(int $i0) throws  {
        this.mScroller.setRampUpDuration($i0);
        return this;
    }

    public AutoScrollHelper setRampDownDuration(int $i0) throws  {
        this.mScroller.setRampDownDuration($i0);
        return this;
    }

    public boolean onTouch(View $r1, MotionEvent $r2) throws  {
        boolean $z0 = true;
        if (!this.mEnabled) {
            return false;
        }
        switch (MotionEventCompat.getActionMasked($r2)) {
            case 0:
                this.mNeedsCancel = true;
                this.mAlreadyDelayed = false;
                break;
            case 1:
            case 3:
                requestStop();
                break;
            case 2:
                break;
            default:
                break;
        }
        this.mScroller.setTargetVelocity(computeTargetVelocity(0, $r2.getX(), (float) $r1.getWidth(), (float) this.mTarget.getWidth()), computeTargetVelocity(1, $r2.getY(), (float) $r1.getHeight(), (float) this.mTarget.getHeight()));
        if (!this.mAnimating && shouldAnimate()) {
            startAnimating();
        }
        if (!(this.mExclusive && this.mAnimating)) {
            $z0 = false;
        }
        return $z0;
    }

    private boolean shouldAnimate() throws  {
        ClampedScroller $r1 = this.mScroller;
        int $i0 = $r1.getVerticalDirection();
        int $i1 = $r1.getHorizontalDirection();
        return ($i0 != 0 && canTargetScrollVertically($i0)) || ($i1 != 0 && canTargetScrollHorizontally($i1));
    }

    private void startAnimating() throws  {
        if (this.mRunnable == null) {
            this.mRunnable = new ScrollAnimationRunnable();
        }
        this.mAnimating = true;
        this.mNeedsReset = true;
        if (this.mAlreadyDelayed || this.mActivationDelay <= 0) {
            this.mRunnable.run();
        } else {
            ViewCompat.postOnAnimationDelayed(this.mTarget, this.mRunnable, (long) this.mActivationDelay);
        }
        this.mAlreadyDelayed = true;
    }

    private void requestStop() throws  {
        if (this.mNeedsReset) {
            this.mAnimating = false;
        } else {
            this.mScroller.requestStop();
        }
    }

    private float computeTargetVelocity(int $i0, float $f0, float $f1, float $f2) throws  {
        float $f3 = getEdgeValue(this.mRelativeEdges[$i0], $f1, this.mMaximumEdges[$i0], $f0);
        if ($f3 == 0.0f) {
            return 0.0f;
        }
        float $f4 = this.mRelativeVelocity[$i0];
        $f1 = this.mMinimumVelocity[$i0];
        $f0 = this.mMaximumVelocity[$i0];
        $f2 = $f4 * $f2;
        if ($f3 > 0.0f) {
            return constrain($f3 * $f2, $f1, $f0);
        }
        return -constrain((-$f3) * $f2, $f1, $f0);
    }

    private float getEdgeValue(float $f0, float $f1, float $f2, float $f3) throws  {
        $f2 = constrain($f0 * $f1, 0.0f, $f2);
        $f0 = constrainEdgeValue($f1 - $f3, $f2) - constrainEdgeValue($f3, $f2);
        if ($f0 < 0.0f) {
            $f0 = -this.mEdgeInterpolator.getInterpolation(-$f0);
        } else if ($f0 <= 0.0f) {
            return 0.0f;
        } else {
            $f0 = this.mEdgeInterpolator.getInterpolation($f0);
        }
        return constrain($f0, -1.0f, 1.0f);
    }

    private float constrainEdgeValue(float $f0, float $f1) throws  {
        if ($f1 == 0.0f) {
            return 0.0f;
        }
        switch (this.mEdgeType) {
            case 0:
            case 1:
                if ($f0 >= $f1) {
                    return 0.0f;
                }
                if ($f0 >= 0.0f) {
                    return 1.0f - ($f0 / $f1);
                }
                if (!this.mAnimating) {
                    return 0.0f;
                }
                if (this.mEdgeType == 1) {
                    return 1.0f;
                }
                return 0.0f;
            case 2:
                return $f0 < 0.0f ? $f0 / (-$f1) : 0.0f;
            default:
                return 0.0f;
        }
    }

    private static int constrain(int $i0, int $i1, int $i2) throws  {
        if ($i0 > $i2) {
            return $i2;
        }
        return $i0 < $i1 ? $i1 : $i0;
    }

    private static float constrain(float $f0, float $f1, float $f2) throws  {
        if ($f0 > $f2) {
            return $f2;
        }
        return $f0 < $f1 ? $f1 : $f0;
    }

    private void cancelTargetTouch() throws  {
        long $l0 = SystemClock.uptimeMillis();
        MotionEvent $r1 = MotionEvent.obtain($l0, $l0, 3, 0.0f, 0.0f, 0);
        this.mTarget.onTouchEvent($r1);
        $r1.recycle();
    }
}
