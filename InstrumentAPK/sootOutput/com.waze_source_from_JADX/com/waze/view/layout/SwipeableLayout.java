package com.waze.view.layout;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.RelativeLayout;
import com.abaltatech.mcp.weblink.sdk.widgets.IWLPostChildrenDraw;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.LayoutManager;
import com.waze.analytics.AnalyticsBuilder;
import com.waze.analytics.AnalyticsEvents;
import com.waze.map.CanvasFont;
import com.waze.pioneer.PioneerManager;
import com.waze.utils.PixelMeasure;
import com.waze.view.anim.EasingInterpolators;
import com.waze.view.anim.ViewPropertyAnimatorHelper;
import com.waze.weblink.WeblinkManager;

public class SwipeableLayout extends RelativeLayout implements IWLPostChildrenDraw {
    public static final long AUTO_OPEN_ANIMATION_DURATION = 300;
    private static final int MAX_BORDER_WIDTH_DP = 8;
    private static final float SWIPEABLE_SIDE_RATIO = 0.05f;
    private SwipeableLayoutActionProvider mActionProvider;
    private Paint mBorderPaint;
    private Paint mFillPaint;
    private Paint mFramePaint;
    private final int mFrameWidthPx;
    private int mInitialTouchDownX;
    private boolean mIsAnimating;
    private boolean mIsCompletingTransition;
    private boolean mIsExtended;
    private boolean mIsLeftGesture;
    private boolean mIsSwipeEnabled;
    private boolean mIsSwipeOpenEnabled;
    private boolean mIsSwipeRightEnabled;
    private boolean mIsSwipeRightOpenEnabled;
    private boolean mIsSwiping;
    private int mLastTouchX;
    private SwipeableLayoutListener mLeftSwipeableLayoutListener;
    private boolean mLeftTapEnabled;
    private View mLeftTouchView;
    private final int mMaxBorderWidthPx;
    private float mOverlayRatio;
    private SwipeableLayoutListener mRightSwipeableLayoutListener;
    private boolean mRightTapEnabled;
    private View mRightTouchView;
    private SwipeMode mSwipeMode;
    private int mSwipeableSideLimitPx;
    private long mTouchDownTime;

    public interface SwipeableLayoutActionProvider {
        void close();

        void extend();

        void retract();

        void snapClosed();
    }

    class C29961 implements SwipeableLayoutActionProvider {
        C29961() {
        }

        public void extend() {
            SwipeableLayout.this.extendInternal();
        }

        public void retract() {
            SwipeableLayout.this.retractInternal();
        }

        public void close() {
            SwipeableLayout.this.closeInternal();
        }

        public void snapClosed() {
            SwipeableLayout.this.snapShutInternal();
        }
    }

    class C30016 implements AnimatorListener {
        C30016() {
        }

        public void onAnimationStart(Animator animation) {
        }

        public void onAnimationEnd(Animator animation) {
            SwipeableLayout.this.mIsAnimating = false;
            SwipeableLayout.this.mIsSwiping = false;
            SwipeableLayout.this.mSwipeMode = SwipeMode.NoSwipe;
        }

        public void onAnimationCancel(Animator animation) {
            SwipeableLayout.this.mIsAnimating = false;
        }

        public void onAnimationRepeat(Animator animation) {
        }
    }

    private enum SwipeMode {
        NoSwipe,
        LeftSwipe,
        RightSwipe
    }

    public interface SwipeableLayoutListener {
        void onSwipeChanged(float f);
    }

    public interface TransitionDoneListener {
        void done();
    }

    public SwipeableLayout(Context context) {
        this(context, null);
    }

    public SwipeableLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeableLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mSwipeMode = SwipeMode.NoSwipe;
        this.mIsAnimating = false;
        this.mIsSwipeEnabled = true;
        this.mIsSwipeRightEnabled = false;
        this.mIsSwipeOpenEnabled = true;
        this.mIsSwipeRightOpenEnabled = false;
        if (isInEditMode()) {
            PixelMeasure.setResources(getResources());
        }
        this.mMaxBorderWidthPx = PixelMeasure.dp(8);
        this.mFrameWidthPx = PixelMeasure.dp(1);
        this.mSwipeableSideLimitPx = PixelMeasure.dp(40);
        init();
    }

    private void init() {
        setWillNotDraw(false);
        this.mBorderPaint = new Paint();
        this.mBorderPaint.setARGB(255, 255, 255, 255);
        this.mBorderPaint.setStyle(Style.STROKE);
        this.mFramePaint = new Paint();
        this.mFramePaint.setARGB(255, 0, 0, 0);
        this.mFramePaint.setStyle(Style.STROKE);
        this.mFramePaint.setStrokeWidth((float) this.mFrameWidthPx);
        this.mFillPaint = new Paint();
        this.mFillPaint.setXfermode(new PorterDuffXfermode(Mode.SRC_OVER));
        this.mFillPaint.setARGB(255, 0, 0, 0);
        this.mIsSwipeEnabled = true;
        this.mIsSwipeOpenEnabled = true;
        this.mIsSwipeRightEnabled = false;
        this.mIsSwipeRightOpenEnabled = false;
        this.mActionProvider = new C29961();
    }

    private int getSwipeableSideWidth() {
        return getTranslationX() == 0.0f ? (int) (((float) getMeasuredWidth()) * SWIPEABLE_SIDE_RATIO) : this.mSwipeableSideLimitPx;
    }

    public void setTapToOpen(boolean leftTapEnabled, boolean rightTapEnabled) {
        this.mLeftTapEnabled = leftTapEnabled;
        this.mRightTapEnabled = rightTapEnabled;
    }

    public boolean isSwipeEnabled() {
        return this.mIsSwipeEnabled && (this.mIsSwipeOpenEnabled || isOpened());
    }

    public boolean isSwipeRightEnabled() {
        return this.mIsSwipeRightEnabled && (this.mIsSwipeRightOpenEnabled || isOpened());
    }

    public boolean isOpened() {
        return getTranslationX() != 0.0f;
    }

    public void setSwipeEnabled(boolean swipeEnabled) {
        this.mIsSwipeEnabled = swipeEnabled;
    }

    public void setSwipeRightEnabled(boolean swipeEnabled) {
        this.mIsSwipeRightEnabled = swipeEnabled;
    }

    public void setSwipeOpenEnabled(boolean swipeOpenEnabled) {
        this.mIsSwipeOpenEnabled = swipeOpenEnabled;
    }

    public void setSwipeRightOpenEnabled(boolean swipeOpenEnabled) {
        this.mIsSwipeRightOpenEnabled = swipeOpenEnabled;
    }

    public SwipeableLayoutActionProvider getActionProvider() {
        return this.mActionProvider;
    }

    public void setLeftSwipeListener(SwipeableLayoutListener listener) {
        this.mLeftSwipeableLayoutListener = listener;
    }

    public void setRightSwipeListener(SwipeableLayoutListener listener) {
        this.mRightSwipeableLayoutListener = listener;
    }

    public void setLeftSwipeAdditionalTouchView(View leftTouchView) {
        this.mLeftTouchView = leftTouchView;
    }

    public void setRightSwipeAdditionalTouchView(View rightTouchView) {
        this.mRightTouchView = rightTouchView;
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (consumeTouchEvent(ev)) {
            return true;
        }
        return super.onInterceptTouchEvent(ev);
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (consumeTouchEvent(event)) {
            return true;
        }
        return super.onTouchEvent(event);
    }

    private boolean consumeTouchEvent(MotionEvent event) {
        boolean z = false;
        if (!isSwipeEnabled() || this.mIsCompletingTransition) {
            return false;
        }
        int eventX = (int) (event.getX() + getTranslationX());
        int touchDelta;
        if (!this.mIsSwiping) {
            switch (event.getAction()) {
                case 0:
                    if (isLeftSwipeHit(eventX, (int) event.getY())) {
                        Log.i("SwipeableLayout", "Left swipe touch down registered");
                        this.mSwipeMode = getTranslationX() == 0.0f ? SwipeMode.LeftSwipe : SwipeMode.RightSwipe;
                        if (getTranslationX() != 0.0f) {
                            eventX = (int) (((float) eventX) - getTranslationX());
                        }
                        this.mInitialTouchDownX = eventX;
                        this.mLastTouchX = this.mInitialTouchDownX;
                    } else if (isRightSwipeHit(eventX, (int) event.getY())) {
                        Log.i("SwipeableLayout", "Right swipe touch down registered");
                        this.mSwipeMode = getTranslationX() == 0.0f ? SwipeMode.RightSwipe : SwipeMode.LeftSwipe;
                        if (getTranslationX() != 0.0f) {
                            eventX = (int) (((float) eventX) - getTranslationX());
                        }
                        this.mInitialTouchDownX = eventX;
                        this.mLastTouchX = this.mInitialTouchDownX;
                    } else {
                        this.mSwipeMode = SwipeMode.NoSwipe;
                    }
                    if (!this.mIsSwipeRightEnabled && this.mSwipeMode == SwipeMode.RightSwipe) {
                        this.mSwipeMode = SwipeMode.NoSwipe;
                    }
                    this.mTouchDownTime = System.currentTimeMillis();
                    break;
                case 1:
                case 3:
                    if (this.mSwipeMode != SwipeMode.NoSwipe) {
                        Log.i("SwipeableLayout", "Touch up/cancel registered, performing auto open/close");
                        if (this.mLastTouchX < this.mSwipeableSideLimitPx && getTranslationX() == 0.0f && this.mLeftTapEnabled) {
                            openSwipe(true);
                        } else if (this.mLastTouchX > getMeasuredWidth() - this.mSwipeableSideLimitPx && getTranslationX() == 0.0f && this.mRightTapEnabled) {
                            openSwipe(false);
                        } else if (getTranslationX() != 0.0f) {
                            closeInternal();
                        }
                    }
                    this.mIsSwiping = false;
                    break;
                case 2:
                    touchDelta = (int) Math.abs(((float) (eventX - this.mInitialTouchDownX)) - getTranslationX());
                    if (this.mSwipeMode != SwipeMode.NoSwipe && touchDelta > PixelMeasure.dp(8)) {
                        Log.i("SwipeableLayout", "Swipe move crossed threshold, beginning movement. Touch delta = " + touchDelta);
                        if (eventX < this.mInitialTouchDownX) {
                            z = true;
                        }
                        this.mIsLeftGesture = z;
                        this.mIsSwiping = true;
                        return true;
                    }
                default:
                    break;
            }
        }
        switch (event.getAction()) {
            case 1:
            case 3:
                if (this.mSwipeMode == SwipeMode.LeftSwipe) {
                    AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_MAIN_MENU_SHOWN).addParam("ACTION", AnalyticsEvents.ANALYTICS_EVENT_VALUE_SWIPE).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_UP_TIME, AppService.timeSinceCreated()).send();
                } else if (this.mSwipeMode == SwipeMode.RightSwipe && this.mIsLeftGesture) {
                    AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_CARPOOL_PANEL_OPENING).addParam("TYPE", "SCREEN_PANNED").send();
                }
                completeTransition(300, ViewPropertyAnimatorHelper.STANDARD_INTERPOLATOR);
                break;
            case 2:
                touchDelta = eventX - this.mInitialTouchDownX;
                if (this.mSwipeMode == SwipeMode.LeftSwipe) {
                    if (touchDelta < 0) {
                        touchDelta = 0;
                    }
                    if (touchDelta > getMeasuredWidth() - this.mSwipeableSideLimitPx) {
                        touchDelta = getMeasuredWidth() - this.mSwipeableSideLimitPx;
                    }
                    applyTransition(touchDelta, ((float) touchDelta) / ((float) (getMeasuredWidth() - this.mSwipeableSideLimitPx)));
                } else if (this.mSwipeMode == SwipeMode.RightSwipe) {
                    if (touchDelta > 0) {
                        touchDelta = 0;
                    }
                    if (touchDelta < (-getMeasuredWidth()) + this.mSwipeableSideLimitPx) {
                        touchDelta = (-getMeasuredWidth()) + this.mSwipeableSideLimitPx;
                    }
                    applyTransition(touchDelta, ((float) touchDelta) / ((float) (this.mSwipeableSideLimitPx - getMeasuredWidth())));
                }
                if (eventX < this.mLastTouchX) {
                    z = true;
                }
                this.mIsLeftGesture = z;
                this.mLastTouchX = eventX;
                return true;
        }
        if (getTranslationX() == 0.0f) {
            return false;
        }
        return true;
    }

    private boolean isLeftSwipeHit(int x, int y) {
        boolean isSideHit;
        if (x < getSwipeableSideWidth()) {
            isSideHit = true;
        } else {
            isSideHit = false;
        }
        boolean isViewHit = false;
        if (AppService.getMainActivity() == null || AppService.getMainActivity().getLayoutMgr() == null || !AppService.getMainActivity().getLayoutMgr().isNavListReadyForDisplay()) {
            return false;
        }
        if (this.mLeftTouchView != null && getTranslationX() == 0.0f) {
            Rect hitRect = new Rect();
            this.mLeftTouchView.getGlobalVisibleRect(hitRect);
            hitRect.offset(0, -getStatusBarHeightOffset());
            isViewHit = hitRect.contains(x, y);
        }
        if (isSideHit || isViewHit) {
            return true;
        }
        return false;
    }

    private boolean isRightSwipeHit(int x, int y) {
        boolean isSideHit;
        if (x > getMeasuredWidth() - getSwipeableSideWidth()) {
            isSideHit = true;
        } else {
            isSideHit = false;
        }
        boolean isViewHit = false;
        if (this.mRightTouchView != null && getTranslationX() == 0.0f) {
            Rect hitRect = new Rect();
            this.mRightTouchView.getGlobalVisibleRect(hitRect);
            hitRect.offset(0, -getStatusBarHeightOffset());
            isViewHit = hitRect.contains(x, y);
        }
        if (isSideHit || isViewHit) {
            return true;
        }
        return false;
    }

    private int getStatusBarHeightOffset() {
        Rect rect = new Rect();
        AppService.getActiveActivity().getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        return rect.top;
    }

    private void closeInternal() {
        this.mIsExtended = false;
        if (getTranslationX() > 0.0f) {
            SwipeMode swipeMode = this.mSwipeMode;
            this.mSwipeMode = SwipeMode.LeftSwipe;
            this.mIsLeftGesture = true;
            completeTransition(300, ViewPropertyAnimatorHelper.STANDARD_INTERPOLATOR);
        } else if (getTranslationX() < 0.0f) {
            this.mSwipeMode = SwipeMode.RightSwipe;
            this.mIsLeftGesture = false;
            completeTransition(300, ViewPropertyAnimatorHelper.STANDARD_INTERPOLATOR);
        }
    }

    private void snapShutInternal() {
        this.mIsExtended = false;
        applyTransition(0, 0.0f);
    }

    public void openSwipe(boolean bLeft) {
        openSwipe(bLeft, null);
    }

    public void openSwipe(boolean bLeft, TransitionDoneListener listener) {
        openSwipe(bLeft, false, listener);
    }

    public void openSwipe(boolean bLeft, boolean immediately, TransitionDoneListener listener) {
        long duration = immediately ? 0 : 300;
        if (!this.mIsExtended) {
            if (bLeft) {
                AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_MAIN_MENU_SHOWN).addParam("ACTION", "TAP").addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_UP_TIME, AppService.timeSinceCreated()).send();
                SwipeMode swipeMode = this.mSwipeMode;
                this.mSwipeMode = SwipeMode.LeftSwipe;
                this.mIsLeftGesture = false;
                completeTransition(duration, EasingInterpolators.SOFT_BOUNCE_OUT, listener);
                return;
            }
            this.mSwipeMode = SwipeMode.RightSwipe;
            this.mIsLeftGesture = true;
            completeTransition(duration, EasingInterpolators.SOFT_BOUNCE_OUT, listener);
        }
    }

    private void completeTransition(long duration, Interpolator interpolator) {
        completeTransition(duration, interpolator, null);
    }

    private void completeTransition(final long duration, final Interpolator interpolator, final TransitionDoneListener listener) {
        if (!this.mIsSwipeRightEnabled && this.mSwipeMode == SwipeMode.RightSwipe) {
            return;
        }
        if (Looper.myLooper() != Looper.getMainLooper()) {
            Log.i("SwipeableLayout", "completeTransition called from a non-UI thread. Recalling on UI thread.");
            post(new Runnable() {
                public void run() {
                    SwipeableLayout.this.completeTransition(duration, interpolator);
                }
            });
            return;
        }
        float targetRatio;
        int targetDelta;
        this.mIsCompletingTransition = true;
        final int startDelta = (int) getTranslationX();
        final float startRatio = this.mOverlayRatio;
        if (this.mSwipeMode == SwipeMode.LeftSwipe) {
            if (this.mIsLeftGesture) {
                targetRatio = 0.0f;
                targetDelta = 0;
            } else {
                targetRatio = 1.0f;
                targetDelta = getMeasuredWidth() - this.mSwipeableSideLimitPx;
            }
        } else if (this.mIsLeftGesture) {
            targetRatio = 1.0f;
            targetDelta = this.mSwipeableSideLimitPx - getMeasuredWidth();
        } else {
            targetRatio = 0.0f;
            targetDelta = 0;
        }
        ValueAnimator animator = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
        animator.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                float animationRatio = ((Float) animation.getAnimatedValue()).floatValue();
                int currentDelta = startDelta + ((int) (((float) (targetDelta - startDelta)) * animationRatio));
                SwipeableLayout.this.applyTransition(currentDelta, startRatio + ((targetRatio - startRatio) * animationRatio));
            }
        });
        animator.setDuration((long) ((int) (((float) duration) * Math.abs(startRatio - targetRatio))));
        animator.setInterpolator(interpolator);
        animator.addListener(new AnimatorListener() {
            public void onAnimationStart(Animator animation) {
            }

            public void onAnimationEnd(Animator animation) {
                SwipeableLayout.this.mIsAnimating = false;
                SwipeableLayout.this.mIsSwiping = false;
                SwipeableLayout.this.mSwipeMode = SwipeMode.NoSwipe;
                SwipeableLayout.this.mIsCompletingTransition = false;
                if (listener != null) {
                    listener.done();
                }
            }

            public void onAnimationCancel(Animator animation) {
                SwipeableLayout.this.mIsAnimating = false;
            }

            public void onAnimationRepeat(Animator animation) {
            }
        });
        this.mIsAnimating = true;
        animator.start();
    }

    public void transitionToRightSwipableLayout() {
        if (getTranslationX() == 0.0f) {
            openSwipe(false);
        } else if (getTranslationX() >= 0.0f) {
            this.mIsLeftGesture = true;
            final int startDelta = (int) getTranslationX();
            final float startRatio = this.mOverlayRatio;
            ValueAnimator animator = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
            animator.addUpdateListener(new AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    float targetRatio;
                    int targetDelta;
                    float stepRatio;
                    float animationRatio = ((Float) animation.getAnimatedValue()).floatValue();
                    if (animationRatio <= CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR) {
                        SwipeableLayout.this.mSwipeMode = SwipeMode.LeftSwipe;
                        targetRatio = 0.0f;
                        targetDelta = 0;
                        stepRatio = animationRatio / CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR;
                    } else {
                        SwipeableLayout.this.mSwipeMode = SwipeMode.RightSwipe;
                        targetRatio = 1.0f;
                        targetDelta = SwipeableLayout.this.mSwipeableSideLimitPx - SwipeableLayout.this.getMeasuredWidth();
                        stepRatio = (animationRatio - CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR) / CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR;
                    }
                    int currentDelta = startDelta + ((int) (((float) (targetDelta - startDelta)) * stepRatio));
                    SwipeableLayout.this.applyTransition(currentDelta, startRatio + ((targetRatio - startRatio) * stepRatio));
                }
            });
            animator.setDuration(300);
            animator.setInterpolator(ViewPropertyAnimatorHelper.STANDARD_INTERPOLATOR);
            animator.addListener(new C30016());
            this.mIsAnimating = true;
            animator.start();
        }
    }

    private void applyTransition(int delta, float ratio) {
        setTranslationX((float) delta);
        this.mOverlayRatio = ratio;
        if (this.mSwipeMode == SwipeMode.LeftSwipe && this.mLeftSwipeableLayoutListener != null) {
            this.mLeftSwipeableLayoutListener.onSwipeChanged(ratio);
            if (this.mRightSwipeableLayoutListener != null) {
                this.mRightSwipeableLayoutListener.onSwipeChanged(0.0f);
            }
        } else if (this.mSwipeMode == SwipeMode.RightSwipe && this.mRightSwipeableLayoutListener != null) {
            this.mRightSwipeableLayoutListener.onSwipeChanged(ratio);
            if (this.mLeftSwipeableLayoutListener != null) {
                this.mLeftSwipeableLayoutListener.onSwipeChanged(0.0f);
            }
        }
        if (VERSION.SDK_INT >= 21) {
            float limitedRatio = Math.max(Math.min(ratio, 1.0f), 0.0f);
            setElevation(((float) PixelMeasure.dp(12)) * limitedRatio);
            applyStatusBarTransition(limitedRatio);
        }
        postInvalidate();
    }

    @TargetApi(21)
    private void applyStatusBarTransition(float ratio) {
        int fullRatioColor = getResources().getColor(C1283R.color.BlueDeepLight);
        AppService.getActiveActivity().getWindow().setStatusBarColor(Color.argb(255, Color.red(-16777216) + ((int) (((float) (Color.red(fullRatioColor) - Color.red(-16777216))) * ratio)), Color.green(-16777216) + ((int) (((float) (Color.green(fullRatioColor) - Color.green(-16777216))) * ratio)), Color.blue(-16777216) + ((int) (((float) (Color.blue(fullRatioColor) - Color.blue(-16777216))) * ratio))));
    }

    public boolean isExtended() {
        return this.mIsExtended;
    }

    private void extendInternal() {
        ViewPropertyAnimatorHelper.initAnimation(this).translationX((float) (getTranslationX() > 0.0f ? getMeasuredWidth() : -getMeasuredWidth()));
        this.mIsExtended = true;
    }

    private void retractInternal() {
        ViewPropertyAnimatorHelper.initAnimation(this).translationX((float) (getTranslationX() > 0.0f ? getMeasuredWidth() - this.mSwipeableSideLimitPx : this.mSwipeableSideLimitPx - getMeasuredWidth()));
        this.mIsExtended = false;
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            if (WeblinkManager.getInstance().isConnectedToClient()) {
                this.mSwipeableSideLimitPx = PixelMeasure.dp(350);
            } else {
                this.mSwipeableSideLimitPx = PixelMeasure.dp(40);
            }
            int padding = this.mIsExtended ? 0 : this.mSwipeableSideLimitPx;
            if (getTranslationX() > 0.0f) {
                applyTransition(getMeasuredWidth() - padding, 1.0f);
            } else if (getTranslationX() < 0.0f) {
                applyTransition(padding - getMeasuredWidth(), 1.0f);
            }
        }
    }

    public View getChildAt(int index) {
        View result = super.getChildAt(index);
        if (result != null || index < 0 || index >= getChildCount()) {
            return result;
        }
        View stubView = new View(getContext());
        stubView.setVisibility(8);
        return stubView;
    }

    protected void dispatchDraw(Canvas canvas) {
        try {
            super.dispatchDraw(canvas);
            if (!WeblinkManager.getInstance().isConnectedToClient() && !PioneerManager.isActive() && this.mOverlayRatio > 0.0f) {
                this.mFillPaint.setAlpha((int) (180.0f * this.mOverlayRatio));
                canvas.drawRect(0.0f, 0.0f, (float) getMeasuredWidth(), (float) getMeasuredHeight(), this.mFillPaint);
                float borderWidth = ((float) this.mMaxBorderWidthPx) * this.mOverlayRatio;
                this.mBorderPaint.setStrokeWidth(borderWidth);
                this.mBorderPaint.setAlpha((int) (255.0f * Math.min(this.mOverlayRatio * LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN, 1.0f)));
                canvas.drawRect(borderWidth / LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN, borderWidth / LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN, ((float) getMeasuredWidth()) - (borderWidth / LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN), ((float) getMeasuredHeight()) - (borderWidth / LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN), this.mBorderPaint);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void onChildrenDrawn(Canvas canvas) {
        try {
            if (this.mOverlayRatio > 0.0f) {
                this.mFillPaint.setAlpha((int) (180.0f * this.mOverlayRatio));
                canvas.drawRect(0.0f, 0.0f, (float) getMeasuredWidth(), (float) getMeasuredHeight(), this.mFillPaint);
                float borderWidth = ((float) this.mMaxBorderWidthPx) * this.mOverlayRatio;
                this.mBorderPaint.setStrokeWidth(borderWidth);
                this.mBorderPaint.setAlpha((int) (255.0f * Math.min(this.mOverlayRatio * LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN, 1.0f)));
                canvas.drawRect(borderWidth / LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN, borderWidth / LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN, ((float) getMeasuredWidth()) - (borderWidth / LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN), ((float) getMeasuredHeight()) - (borderWidth / LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN), this.mBorderPaint);
            }
        } catch (NullPointerException e) {
        }
    }

    public boolean isAnimating() {
        return this.mIsAnimating;
    }
}
