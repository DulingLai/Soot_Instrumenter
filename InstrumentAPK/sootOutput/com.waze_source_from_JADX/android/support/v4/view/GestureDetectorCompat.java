package android.support.v4.view;

import android.content.Context;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Message;
import android.view.GestureDetector;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;

public final class GestureDetectorCompat {
    private final GestureDetectorCompatImpl mImpl;

    interface GestureDetectorCompatImpl {
        boolean isLongpressEnabled() throws ;

        boolean onTouchEvent(MotionEvent motionEvent) throws ;

        void setIsLongpressEnabled(boolean z) throws ;

        void setOnDoubleTapListener(OnDoubleTapListener onDoubleTapListener) throws ;
    }

    static class GestureDetectorCompatImplBase implements GestureDetectorCompatImpl {
        private static final int DOUBLE_TAP_TIMEOUT = ViewConfiguration.getDoubleTapTimeout();
        private static final int LONGPRESS_TIMEOUT = ViewConfiguration.getLongPressTimeout();
        private static final int LONG_PRESS = 2;
        private static final int SHOW_PRESS = 1;
        private static final int TAP = 3;
        private static final int TAP_TIMEOUT = ViewConfiguration.getTapTimeout();
        private boolean mAlwaysInBiggerTapRegion;
        private boolean mAlwaysInTapRegion;
        private MotionEvent mCurrentDownEvent;
        private boolean mDeferConfirmSingleTap;
        private OnDoubleTapListener mDoubleTapListener;
        private int mDoubleTapSlopSquare;
        private float mDownFocusX;
        private float mDownFocusY;
        private final Handler mHandler;
        private boolean mInLongPress;
        private boolean mIsDoubleTapping;
        private boolean mIsLongpressEnabled;
        private float mLastFocusX;
        private float mLastFocusY;
        private final OnGestureListener mListener;
        private int mMaximumFlingVelocity;
        private int mMinimumFlingVelocity;
        private MotionEvent mPreviousUpEvent;
        private boolean mStillDown;
        private int mTouchSlopSquare;
        private VelocityTracker mVelocityTracker;

        private class GestureHandler extends Handler {
            GestureHandler() throws  {
            }

            GestureHandler(Handler $r2) throws  {
                super($r2.getLooper());
            }

            public void handleMessage(Message $r1) throws  {
                switch ($r1.what) {
                    case 1:
                        GestureDetectorCompatImplBase.this.mListener.onShowPress(GestureDetectorCompatImplBase.this.mCurrentDownEvent);
                        return;
                    case 2:
                        GestureDetectorCompatImplBase.this.dispatchLongPress();
                        return;
                    case 3:
                        if (GestureDetectorCompatImplBase.this.mDoubleTapListener == null) {
                            return;
                        }
                        if (GestureDetectorCompatImplBase.this.mStillDown) {
                            GestureDetectorCompatImplBase.this.mDeferConfirmSingleTap = true;
                            return;
                        } else {
                            GestureDetectorCompatImplBase.this.mDoubleTapListener.onSingleTapConfirmed(GestureDetectorCompatImplBase.this.mCurrentDownEvent);
                            return;
                        }
                    default:
                        throw new RuntimeException("Unknown message " + $r1);
                }
            }
        }

        public GestureDetectorCompatImplBase(Context $r1, OnGestureListener $r3, Handler $r2) throws  {
            if ($r2 != null) {
                this.mHandler = new GestureHandler($r2);
            } else {
                this.mHandler = new GestureHandler();
            }
            this.mListener = $r3;
            if ($r3 instanceof OnDoubleTapListener) {
                setOnDoubleTapListener((OnDoubleTapListener) $r3);
            }
            init($r1);
        }

        private void init(Context $r1) throws  {
            if ($r1 == null) {
                throw new IllegalArgumentException("Context must not be null");
            } else if (this.mListener == null) {
                throw new IllegalArgumentException("OnGestureListener must not be null");
            } else {
                this.mIsLongpressEnabled = true;
                ViewConfiguration $r4 = ViewConfiguration.get($r1);
                int $i0 = $r4.getScaledTouchSlop();
                int $i1 = $r4.getScaledDoubleTapSlop();
                this.mMinimumFlingVelocity = $r4.getScaledMinimumFlingVelocity();
                this.mMaximumFlingVelocity = $r4.getScaledMaximumFlingVelocity();
                this.mTouchSlopSquare = $i0 * $i0;
                this.mDoubleTapSlopSquare = $i1 * $i1;
            }
        }

        public void setOnDoubleTapListener(OnDoubleTapListener $r1) throws  {
            this.mDoubleTapListener = $r1;
        }

        public void setIsLongpressEnabled(boolean $z0) throws  {
            this.mIsLongpressEnabled = $z0;
        }

        public boolean isLongpressEnabled() throws  {
            return this.mIsLongpressEnabled;
        }

        public boolean onTouchEvent(MotionEvent $r1) throws  {
            int $i3;
            int $i0 = $r1.getAction();
            if (this.mVelocityTracker == null) {
                this.mVelocityTracker = VelocityTracker.obtain();
            }
            this.mVelocityTracker.addMovement($r1);
            boolean $z0 = ($i0 & 255) == 6;
            int $i2 = $z0 ? MotionEventCompat.getActionIndex($r1) : -1;
            float $f1 = 0.0f;
            float $f2 = 0.0f;
            int $i1 = MotionEventCompat.getPointerCount($r1);
            for ($i3 = 0; $i3 < $i1; $i3++) {
                if ($i2 != $i3) {
                    $f1 += MotionEventCompat.getX($r1, $i3);
                    $f2 += MotionEventCompat.getY($r1, $i3);
                }
            }
            if ($z0) {
                $i2 = $i1 - 1;
            } else {
                $i2 = $i1;
            }
            $f1 /= (float) $i2;
            $f2 /= (float) $i2;
            $z0 = false;
            OnDoubleTapListener onDoubleTapListener;
            OnDoubleTapListener $r3;
            MotionEvent $r5;
            OnGestureListener onGestureListener;
            OnGestureListener $r7;
            switch ($i0 & 255) {
                case 0:
                    Handler $r4;
                    if (this.mDoubleTapListener != null) {
                        boolean $z1 = this.mHandler.hasMessages(3);
                        if ($z1) {
                            this.mHandler.removeMessages(3);
                        }
                        if (!(this.mCurrentDownEvent == null || this.mPreviousUpEvent == null || !$z1)) {
                            if (isConsideredDoubleTap(this.mCurrentDownEvent, this.mPreviousUpEvent, $r1)) {
                                this.mIsDoubleTapping = true;
                                $z0 = false | this.mDoubleTapListener.onDoubleTap(this.mCurrentDownEvent);
                                onDoubleTapListener = this.mDoubleTapListener;
                                $r3 = onDoubleTapListener;
                                $z0 |= onDoubleTapListener.onDoubleTapEvent($r1);
                            }
                        }
                        this.mHandler.sendEmptyMessageDelayed(3, (long) DOUBLE_TAP_TIMEOUT);
                    }
                    this.mLastFocusX = $f1;
                    this.mDownFocusX = $f1;
                    this.mLastFocusY = $f2;
                    this.mDownFocusY = $f2;
                    if (this.mCurrentDownEvent != null) {
                        $r5 = this.mCurrentDownEvent;
                        $r5.recycle();
                    }
                    this.mCurrentDownEvent = MotionEvent.obtain($r1);
                    this.mAlwaysInTapRegion = true;
                    this.mAlwaysInBiggerTapRegion = true;
                    this.mStillDown = true;
                    this.mInLongPress = false;
                    this.mDeferConfirmSingleTap = false;
                    if (this.mIsLongpressEnabled) {
                        this.mHandler.removeMessages(2);
                        $r4 = this.mHandler;
                        $r5 = this.mCurrentDownEvent;
                        $r4.sendEmptyMessageAtTime(2, ($r5.getDownTime() + ((long) TAP_TIMEOUT)) + ((long) LONGPRESS_TIMEOUT));
                    }
                    $r4 = this.mHandler;
                    $r5 = this.mCurrentDownEvent;
                    $r4.sendEmptyMessageAtTime(1, $r5.getDownTime() + ((long) TAP_TIMEOUT));
                    onGestureListener = this.mListener;
                    $r7 = onGestureListener;
                    return $z0 | onGestureListener.onDown($r1);
                case 1:
                    this.mStillDown = false;
                    MotionEvent $r52 = MotionEvent.obtain($r1);
                    if (this.mIsDoubleTapping) {
                        onDoubleTapListener = this.mDoubleTapListener;
                        $r3 = onDoubleTapListener;
                        $z0 = false | onDoubleTapListener.onDoubleTapEvent($r1);
                    } else if (this.mInLongPress) {
                        this.mHandler.removeMessages(3);
                        this.mInLongPress = false;
                    } else if (this.mAlwaysInTapRegion) {
                        onGestureListener = this.mListener;
                        $r7 = onGestureListener;
                        $z0 = onGestureListener.onSingleTapUp($r1);
                        if (this.mDeferConfirmSingleTap && this.mDoubleTapListener != null) {
                            onDoubleTapListener = this.mDoubleTapListener;
                            $r3 = onDoubleTapListener;
                            onDoubleTapListener.onSingleTapConfirmed($r1);
                        }
                    } else {
                        VelocityTracker $r2 = this.mVelocityTracker;
                        $i1 = MotionEventCompat.getPointerId($r1, 0);
                        $r2.computeCurrentVelocity(1000, (float) this.mMaximumFlingVelocity);
                        $f1 = VelocityTrackerCompat.getYVelocity($r2, $i1);
                        $f2 = VelocityTrackerCompat.getXVelocity($r2, $i1);
                        if (Math.abs($f1) > ((float) this.mMinimumFlingVelocity) || Math.abs($f2) > ((float) this.mMinimumFlingVelocity)) {
                            $z0 = this.mListener.onFling(this.mCurrentDownEvent, $r1, $f2, $f1);
                        }
                    }
                    if (this.mPreviousUpEvent != null) {
                        $r5 = this.mPreviousUpEvent;
                        $r5.recycle();
                    }
                    this.mPreviousUpEvent = $r52;
                    if (this.mVelocityTracker != null) {
                        this.mVelocityTracker.recycle();
                        this.mVelocityTracker = null;
                    }
                    this.mIsDoubleTapping = false;
                    this.mDeferConfirmSingleTap = false;
                    this.mHandler.removeMessages(1);
                    this.mHandler.removeMessages(2);
                    return $z0;
                case 2:
                    if (this.mInLongPress) {
                        return false;
                    }
                    float $f0 = this.mLastFocusX - $f1;
                    float $f3 = this.mLastFocusY - $f2;
                    if (this.mIsDoubleTapping) {
                        onDoubleTapListener = this.mDoubleTapListener;
                        $r3 = onDoubleTapListener;
                        return false | onDoubleTapListener.onDoubleTapEvent($r1);
                    } else if (this.mAlwaysInTapRegion) {
                        $i0 = (int) ($f1 - this.mDownFocusX);
                        $i1 = (int) ($f2 - this.mDownFocusY);
                        $i1 = ($i0 * $i0) + ($i1 * $i1);
                        if ($i1 > this.mTouchSlopSquare) {
                            $z0 = this.mListener.onScroll(this.mCurrentDownEvent, $r1, $f0, $f3);
                            this.mLastFocusX = $f1;
                            this.mLastFocusY = $f2;
                            this.mAlwaysInTapRegion = false;
                            this.mHandler.removeMessages(3);
                            this.mHandler.removeMessages(1);
                            this.mHandler.removeMessages(2);
                        }
                        if ($i1 <= this.mTouchSlopSquare) {
                            return $z0;
                        }
                        this.mAlwaysInBiggerTapRegion = false;
                        return $z0;
                    } else if (Math.abs($f0) < 1.0f && Math.abs($f3) < 1.0f) {
                        return false;
                    } else {
                        $z0 = this.mListener.onScroll(this.mCurrentDownEvent, $r1, $f0, $f3);
                        this.mLastFocusX = $f1;
                        this.mLastFocusY = $f2;
                        return $z0;
                    }
                case 3:
                    cancel();
                    return false;
                case 4:
                    break;
                case 5:
                    this.mLastFocusX = $f1;
                    this.mDownFocusX = $f1;
                    this.mLastFocusY = $f2;
                    this.mDownFocusY = $f2;
                    cancelTaps();
                    return false;
                case 6:
                    this.mLastFocusX = $f1;
                    this.mDownFocusX = $f1;
                    this.mLastFocusY = $f2;
                    this.mDownFocusY = $f2;
                    this.mVelocityTracker.computeCurrentVelocity(1000, (float) this.mMaximumFlingVelocity);
                    $i0 = MotionEventCompat.getActionIndex($r1);
                    $i2 = MotionEventCompat.getPointerId($r1, $i0);
                    $f1 = VelocityTrackerCompat.getXVelocity(this.mVelocityTracker, $i2);
                    $f2 = VelocityTrackerCompat.getYVelocity(this.mVelocityTracker, $i2);
                    for ($i2 = 0; $i2 < $i1; $i2++) {
                        if ($i2 != $i0) {
                            $i3 = MotionEventCompat.getPointerId($r1, $i2);
                            if (($f1 * VelocityTrackerCompat.getXVelocity(this.mVelocityTracker, $i3)) + ($f2 * VelocityTrackerCompat.getYVelocity(this.mVelocityTracker, $i3)) < 0.0f) {
                                this.mVelocityTracker.clear();
                                return false;
                            }
                        }
                    }
                    return false;
                default:
                    break;
            }
            return false;
        }

        private void cancel() throws  {
            this.mHandler.removeMessages(1);
            this.mHandler.removeMessages(2);
            this.mHandler.removeMessages(3);
            this.mVelocityTracker.recycle();
            this.mVelocityTracker = null;
            this.mIsDoubleTapping = false;
            this.mStillDown = false;
            this.mAlwaysInTapRegion = false;
            this.mAlwaysInBiggerTapRegion = false;
            this.mDeferConfirmSingleTap = false;
            if (this.mInLongPress) {
                this.mInLongPress = false;
            }
        }

        private void cancelTaps() throws  {
            this.mHandler.removeMessages(1);
            this.mHandler.removeMessages(2);
            this.mHandler.removeMessages(3);
            this.mIsDoubleTapping = false;
            this.mAlwaysInTapRegion = false;
            this.mAlwaysInBiggerTapRegion = false;
            this.mDeferConfirmSingleTap = false;
            if (this.mInLongPress) {
                this.mInLongPress = false;
            }
        }

        private boolean isConsideredDoubleTap(MotionEvent $r1, MotionEvent $r2, MotionEvent $r3) throws  {
            if (!this.mAlwaysInBiggerTapRegion) {
                return false;
            }
            if ($r3.getEventTime() - $r2.getEventTime() > ((long) DOUBLE_TAP_TIMEOUT)) {
                return false;
            }
            int $i0 = ((int) $r1.getX()) - ((int) $r3.getX());
            int $i1 = ((int) $r1.getY()) - ((int) $r3.getY());
            return ($i0 * $i0) + ($i1 * $i1) < this.mDoubleTapSlopSquare;
        }

        private void dispatchLongPress() throws  {
            this.mHandler.removeMessages(3);
            this.mDeferConfirmSingleTap = false;
            this.mInLongPress = true;
            this.mListener.onLongPress(this.mCurrentDownEvent);
        }
    }

    static class GestureDetectorCompatImplJellybeanMr2 implements GestureDetectorCompatImpl {
        private final GestureDetector mDetector;

        public GestureDetectorCompatImplJellybeanMr2(Context $r1, OnGestureListener $r2, Handler $r3) throws  {
            this.mDetector = new GestureDetector($r1, $r2, $r3);
        }

        public boolean isLongpressEnabled() throws  {
            return this.mDetector.isLongpressEnabled();
        }

        public boolean onTouchEvent(MotionEvent $r1) throws  {
            return this.mDetector.onTouchEvent($r1);
        }

        public void setIsLongpressEnabled(boolean $z0) throws  {
            this.mDetector.setIsLongpressEnabled($z0);
        }

        public void setOnDoubleTapListener(OnDoubleTapListener $r1) throws  {
            this.mDetector.setOnDoubleTapListener($r1);
        }
    }

    public GestureDetectorCompat(Context $r1, OnGestureListener $r2) throws  {
        this($r1, $r2, null);
    }

    public GestureDetectorCompat(Context $r1, OnGestureListener $r2, Handler $r3) throws  {
        if (VERSION.SDK_INT > 17) {
            this.mImpl = new GestureDetectorCompatImplJellybeanMr2($r1, $r2, $r3);
        } else {
            this.mImpl = new GestureDetectorCompatImplBase($r1, $r2, $r3);
        }
    }

    public boolean isLongpressEnabled() throws  {
        return this.mImpl.isLongpressEnabled();
    }

    public boolean onTouchEvent(MotionEvent $r1) throws  {
        return this.mImpl.onTouchEvent($r1);
    }

    public void setIsLongpressEnabled(boolean $z0) throws  {
        this.mImpl.setIsLongpressEnabled($z0);
    }

    public void setOnDoubleTapListener(OnDoubleTapListener $r1) throws  {
        this.mImpl.setOnDoubleTapListener($r1);
    }
}
