package android.support.v4.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build.VERSION;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Transformation;
import android.widget.AbsListView;

public class SwipeRefreshLayout extends ViewGroup implements NestedScrollingChild, NestedScrollingParent {
    private static final int ALPHA_ANIMATION_DURATION = 300;
    private static final int ANIMATE_TO_START_DURATION = 200;
    private static final int ANIMATE_TO_TRIGGER_DURATION = 200;
    private static final int CIRCLE_BG_LIGHT = -328966;
    private static final int CIRCLE_DIAMETER = 40;
    private static final int CIRCLE_DIAMETER_LARGE = 56;
    private static final float DECELERATE_INTERPOLATION_FACTOR = 2.0f;
    public static final int DEFAULT = 1;
    private static final int DEFAULT_CIRCLE_TARGET = 64;
    private static final float DRAG_RATE = 0.5f;
    private static final int INVALID_POINTER = -1;
    public static final int LARGE = 0;
    private static final int[] LAYOUT_ATTRS = new int[]{16842766};
    private static final String LOG_TAG = SwipeRefreshLayout.class.getSimpleName();
    private static final int MAX_ALPHA = 255;
    private static final float MAX_PROGRESS_ANGLE = 0.8f;
    private static final int SCALE_DOWN_DURATION = 150;
    private static final int STARTING_PROGRESS_ALPHA = 76;
    private int mActivePointerId;
    private Animation mAlphaMaxAnimation;
    private Animation mAlphaStartAnimation;
    private final Animation mAnimateToCorrectPosition;
    private final Animation mAnimateToStartPosition;
    private int mCircleHeight;
    private CircleImageView mCircleView;
    private int mCircleViewIndex;
    private int mCircleWidth;
    private int mCurrentTargetOffsetTop;
    private final DecelerateInterpolator mDecelerateInterpolator;
    protected int mFrom;
    private float mInitialDownY;
    private float mInitialMotionY;
    private boolean mIsBeingDragged;
    private OnRefreshListener mListener;
    private int mMediumAnimationDuration;
    private boolean mNestedScrollInProgress;
    private final NestedScrollingChildHelper mNestedScrollingChildHelper;
    private final NestedScrollingParentHelper mNestedScrollingParentHelper;
    private boolean mNotify;
    private boolean mOriginalOffsetCalculated;
    protected int mOriginalOffsetTop;
    private final int[] mParentOffsetInWindow;
    private final int[] mParentScrollConsumed;
    private MaterialProgressDrawable mProgress;
    private AnimationListener mRefreshListener;
    private boolean mRefreshing;
    private boolean mReturningToStart;
    private boolean mScale;
    private Animation mScaleAnimation;
    private Animation mScaleDownAnimation;
    private Animation mScaleDownToStartAnimation;
    private float mSpinnerFinalOffset;
    private float mStartingScale;
    private View mTarget;
    private float mTotalDragDistance;
    private float mTotalUnconsumed;
    private int mTouchSlop;
    private boolean mUsingCustomStart;

    class C01551 implements AnimationListener {
        C01551() throws  {
        }

        public void onAnimationStart(Animation animation) throws  {
        }

        public void onAnimationRepeat(Animation animation) throws  {
        }

        public void onAnimationEnd(Animation animation) throws  {
            if (SwipeRefreshLayout.this.mRefreshing) {
                SwipeRefreshLayout.this.mProgress.setAlpha(255);
                SwipeRefreshLayout.this.mProgress.start();
                if (SwipeRefreshLayout.this.mNotify && SwipeRefreshLayout.this.mListener != null) {
                    SwipeRefreshLayout.this.mListener.onRefresh();
                }
                SwipeRefreshLayout.this.mCurrentTargetOffsetTop = SwipeRefreshLayout.this.mCircleView.getTop();
                return;
            }
            SwipeRefreshLayout.this.reset();
        }
    }

    class C01562 extends Animation {
        C01562() throws  {
        }

        public void applyTransformation(float $f0, Transformation t) throws  {
            SwipeRefreshLayout.this.setAnimationProgress($f0);
        }
    }

    class C01573 extends Animation {
        C01573() throws  {
        }

        public void applyTransformation(float $f0, Transformation t) throws  {
            SwipeRefreshLayout.this.setAnimationProgress(1.0f - $f0);
        }
    }

    class C01595 implements AnimationListener {
        C01595() throws  {
        }

        public void onAnimationStart(Animation animation) throws  {
        }

        public void onAnimationEnd(Animation animation) throws  {
            if (!SwipeRefreshLayout.this.mScale) {
                SwipeRefreshLayout.this.startScaleDownAnimation(null);
            }
        }

        public void onAnimationRepeat(Animation animation) throws  {
        }
    }

    class C01606 extends Animation {
        C01606() throws  {
        }

        public void applyTransformation(float $f0, Transformation t) throws  {
            int $i1;
            if (SwipeRefreshLayout.this.mUsingCustomStart) {
                $i1 = (int) SwipeRefreshLayout.this.mSpinnerFinalOffset;
            } else {
                $i1 = (int) (SwipeRefreshLayout.this.mSpinnerFinalOffset - ((float) Math.abs(SwipeRefreshLayout.this.mOriginalOffsetTop)));
            }
            SwipeRefreshLayout.this.setTargetOffsetTopAndBottom((SwipeRefreshLayout.this.mFrom + ((int) (((float) ($i1 - SwipeRefreshLayout.this.mFrom)) * $f0))) - SwipeRefreshLayout.this.mCircleView.getTop(), false);
            SwipeRefreshLayout.this.mProgress.setArrowScale(1.0f - $f0);
        }
    }

    class C01617 extends Animation {
        C01617() throws  {
        }

        public void applyTransformation(float $f0, Transformation t) throws  {
            SwipeRefreshLayout.this.moveToStart($f0);
        }
    }

    class C01628 extends Animation {
        C01628() throws  {
        }

        public void applyTransformation(float $f0, Transformation t) throws  {
            SwipeRefreshLayout.this.setAnimationProgress(SwipeRefreshLayout.this.mStartingScale + ((-SwipeRefreshLayout.this.mStartingScale) * $f0));
            SwipeRefreshLayout.this.moveToStart($f0);
        }
    }

    public interface OnRefreshListener {
        void onRefresh() throws ;
    }

    private void moveSpinner(float r24) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:23:0x0172 in {2, 5, 8, 11, 18, 20, 22, 24} preds:[]
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.computeDominators(BlockProcessor.java:129)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.visit(BlockProcessor.java:38)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r23 = this;
        r0 = r23;
        r3 = r0.mProgress;
        r4 = 1;
        r3.showArrow(r4);
        r0 = r23;
        r5 = r0.mTotalDragDistance;
        r5 = r24 / r5;
        r5 = java.lang.Math.abs(r5);
        r7 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r6 = java.lang.Math.min(r7, r5);
        r8 = (double) r6;
        r10 = 4600877379321698714; // 0x3fd999999999999a float:-1.5881868E-23 double:0.4;
        r8 = r8 - r10;
        r10 = 0;
        r8 = java.lang.Math.max(r8, r10);
        r5 = (float) r8;
        r7 = 1084227584; // 0x40a00000 float:5.0 double:5.356796015E-315;
        r5 = r5 * r7;
        r7 = 1077936128; // 0x40400000 float:3.0 double:5.325712093E-315;
        r5 = r5 / r7;
        r0 = r24;
        r12 = java.lang.Math.abs(r0);
        r0 = r23;
        r13 = r0.mTotalDragDistance;
        r12 = r12 - r13;
        r0 = r23;
        r14 = r0.mUsingCustomStart;
        if (r14 == 0) goto L_0x0176;
    L_0x0040:
        r0 = r23;
        r13 = r0.mSpinnerFinalOffset;
        r0 = r23;
        r15 = r0.mOriginalOffsetTop;
        r0 = (float) r15;
        r16 = r0;
        r13 = r13 - r0;
    L_0x004c:
        r7 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r16 = r7 * r13;
        r0 = r16;
        r12 = java.lang.Math.min(r12, r0);
        r12 = r12 / r13;
        r7 = 0;
        r12 = java.lang.Math.max(r7, r12);
        r7 = 1082130432; // 0x40800000 float:4.0 double:5.34643471E-315;
        r16 = r12 / r7;
        r0 = r16;
        r8 = (double) r0;
        r7 = 1082130432; // 0x40800000 float:4.0 double:5.34643471E-315;
        r12 = r12 / r7;
        r0 = (double) r12;
        r17 = r0;
        r10 = 4611686018427387904; // 0x4000000000000000 float:0.0 double:2.0;
        r0 = r17;
        r17 = java.lang.Math.pow(r0, r10);
        r0 = r17;
        r8 = r8 - r0;
        r12 = (float) r8;
        r7 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r12 = r12 * r7;
        r16 = r13 * r12;
        r7 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r0 = r16;
        r0 = r0 * r7;
        r16 = r0;
        r0 = r23;
        r15 = r0.mOriginalOffsetTop;
        r6 = r13 * r6;
        r0 = r16;
        r6 = r6 + r0;
        r0 = (int) r6;
        r19 = r0;
        r15 = r15 + r0;
        r0 = r23;
        r0 = r0.mCircleView;
        r20 = r0;
        r19 = r0.getVisibility();
        if (r19 == 0) goto L_0x00ae;
    L_0x00a2:
        r0 = r23;
        r0 = r0.mCircleView;
        r20 = r0;
        r4 = 0;
        r0 = r20;
        r0.setVisibility(r4);
    L_0x00ae:
        r0 = r23;
        r14 = r0.mScale;
        if (r14 != 0) goto L_0x00d0;
    L_0x00b4:
        r0 = r23;
        r0 = r0.mCircleView;
        r20 = r0;
        r7 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r0 = r20;
        android.support.v4.view.ViewCompat.setScaleX(r0, r7);
        r0 = r23;
        r0 = r0.mCircleView;
        r20 = r0;
        r7 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r0 = r20;
        android.support.v4.view.ViewCompat.setScaleY(r0, r7);
    L_0x00d0:
        r0 = r23;
        r14 = r0.mScale;
        if (r14 == 0) goto L_0x00e8;
    L_0x00d6:
        r0 = r23;
        r6 = r0.mTotalDragDistance;
        r6 = r24 / r6;
        r7 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r6 = java.lang.Math.min(r7, r6);
        r0 = r23;
        r0.setAnimationProgress(r6);
    L_0x00e8:
        r0 = r23;
        r6 = r0.mTotalDragDistance;
        r21 = (r24 > r6 ? 1 : (r24 == r6 ? 0 : -1));
        if (r21 >= 0) goto L_0x017b;
    L_0x00f0:
        r0 = r23;
        r3 = r0.mProgress;
        r19 = r3.getAlpha();
        r4 = 76;
        r0 = r19;
        if (r0 <= r4) goto L_0x0113;
    L_0x00fe:
        r0 = r23;
        r0 = r0.mAlphaStartAnimation;
        r22 = r0;
        r0 = r23;
        r1 = r22;
        r14 = r0.isAnimationRunning(r1);
        if (r14 != 0) goto L_0x0113;
    L_0x010e:
        r0 = r23;
        r0.startProgressAlphaStartAnimation();
    L_0x0113:
        r7 = 1061997773; // 0x3f4ccccd float:0.8 double:5.246966156E-315;
        r24 = r5 * r7;
        r0 = r23;
        r3 = r0.mProgress;
        r7 = 1061997773; // 0x3f4ccccd float:0.8 double:5.246966156E-315;
        r0 = r24;
        r24 = java.lang.Math.min(r7, r0);
        r7 = 0;
        r0 = r24;
        r3.setStartEndTrim(r7, r0);
        r0 = r23;
        r3 = r0.mProgress;
        r7 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r24 = java.lang.Math.min(r7, r5);
        r0 = r24;
        r3.setArrowScale(r0);
        r7 = 1053609165; // 0x3ecccccd float:0.4 double:5.205520926E-315;
        r24 = r7 * r5;
        r7 = -1098907648; // 0xffffffffbe800000 float:-0.25 double:NaN;
        r24 = r7 + r24;
        r7 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r5 = r7 * r12;
        r0 = r24;
        r0 = r0 + r5;
        r24 = r0;
        r7 = 1056964608; // 0x3f000000 float:0.5 double:5.222099017E-315;
        r0 = r24;
        r0 = r0 * r7;
        r24 = r0;
        r0 = r23;
        r3 = r0.mProgress;
        goto L_0x015f;
    L_0x015c:
        goto L_0x0113;
    L_0x015f:
        r0 = r24;
        r3.setProgressRotation(r0);
        r0 = r23;
        r0 = r0.mCurrentTargetOffsetTop;
        r19 = r0;
        r15 = r15 - r0;
        r4 = 1;
        r0 = r23;
        r0.setTargetOffsetTopAndBottom(r15, r4);
        return;
        goto L_0x0176;
    L_0x0173:
        goto L_0x004c;
    L_0x0176:
        r0 = r23;
        r13 = r0.mSpinnerFinalOffset;
        goto L_0x0173;
    L_0x017b:
        r0 = r23;
        r3 = r0.mProgress;
        r19 = r3.getAlpha();
        r4 = 255; // 0xff float:3.57E-43 double:1.26E-321;
        r0 = r19;
        if (r0 >= r4) goto L_0x0113;
    L_0x0189:
        r0 = r23;
        r0 = r0.mAlphaMaxAnimation;
        r22 = r0;
        r0 = r23;
        r1 = r22;
        r14 = r0.isAnimationRunning(r1);
        if (r14 != 0) goto L_0x0113;
    L_0x0199:
        r0 = r23;
        r0.startProgressAlphaMaxAnimation();
        goto L_0x015c;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.widget.SwipeRefreshLayout.moveSpinner(float):void");
    }

    private void reset() throws  {
        this.mCircleView.clearAnimation();
        this.mProgress.stop();
        this.mCircleView.setVisibility(8);
        setColorViewAlpha(255);
        if (this.mScale) {
            setAnimationProgress(0.0f);
        } else {
            setTargetOffsetTopAndBottom(this.mOriginalOffsetTop - this.mCurrentTargetOffsetTop, true);
        }
        this.mCurrentTargetOffsetTop = this.mCircleView.getTop();
    }

    protected void onDetachedFromWindow() throws  {
        super.onDetachedFromWindow();
        reset();
    }

    private void setColorViewAlpha(int $i0) throws  {
        this.mCircleView.getBackground().setAlpha($i0);
        this.mProgress.setAlpha($i0);
    }

    public void setProgressViewOffset(boolean $z0, int $i0, int $i1) throws  {
        this.mScale = $z0;
        this.mCircleView.setVisibility(8);
        this.mCurrentTargetOffsetTop = $i0;
        this.mOriginalOffsetTop = $i0;
        this.mSpinnerFinalOffset = (float) $i1;
        this.mUsingCustomStart = true;
        this.mCircleView.invalidate();
    }

    public void setProgressViewEndTarget(boolean $z0, int $i0) throws  {
        this.mSpinnerFinalOffset = (float) $i0;
        this.mScale = $z0;
        this.mCircleView.invalidate();
    }

    public void setSize(int $i0) throws  {
        if ($i0 == 0 || $i0 == 1) {
            DisplayMetrics $r2 = getResources().getDisplayMetrics();
            int $i1;
            if ($i0 == 0) {
                $i1 = (int) (56.0f * $r2.density);
                this.mCircleWidth = $i1;
                this.mCircleHeight = $i1;
            } else {
                $i1 = (int) (40.0f * $r2.density);
                this.mCircleWidth = $i1;
                this.mCircleHeight = $i1;
            }
            this.mCircleView.setImageDrawable(null);
            this.mProgress.updateSizes($i0);
            this.mCircleView.setImageDrawable(this.mProgress);
        }
    }

    public SwipeRefreshLayout(Context $r1) throws  {
        this($r1, null);
    }

    public SwipeRefreshLayout(Context $r1, AttributeSet $r2) throws  {
        super($r1, $r2);
        this.mRefreshing = false;
        this.mTotalDragDistance = -1.0f;
        this.mParentScrollConsumed = new int[2];
        this.mParentOffsetInWindow = new int[2];
        this.mOriginalOffsetCalculated = false;
        this.mActivePointerId = -1;
        this.mCircleViewIndex = -1;
        this.mRefreshListener = new C01551();
        this.mAnimateToCorrectPosition = new C01606();
        this.mAnimateToStartPosition = new C01617();
        this.mTouchSlop = ViewConfiguration.get($r1).getScaledTouchSlop();
        this.mMediumAnimationDuration = getResources().getInteger(17694721);
        setWillNotDraw(false);
        this.mDecelerateInterpolator = new DecelerateInterpolator(2.0f);
        TypedArray $r10 = $r1.obtainStyledAttributes($r2, LAYOUT_ATTRS);
        setEnabled($r10.getBoolean(0, true));
        $r10.recycle();
        DisplayMetrics $r11 = getResources().getDisplayMetrics();
        float $f0 = $r11.density * 40.0f;
        float f = $f0;
        this.mCircleWidth = (int) $f0;
        $f0 = $r11.density * 40.0f;
        f = $f0;
        this.mCircleHeight = (int) $f0;
        createProgressView();
        ViewCompat.setChildrenDrawingOrderEnabled(this, true);
        this.mSpinnerFinalOffset = 64.0f * $r11.density;
        $f0 = this.mSpinnerFinalOffset;
        f = $f0;
        this.mTotalDragDistance = $f0;
        this.mNestedScrollingParentHelper = new NestedScrollingParentHelper(this);
        this.mNestedScrollingChildHelper = new NestedScrollingChildHelper(this);
        setNestedScrollingEnabled(true);
    }

    protected int getChildDrawingOrder(int $i0, int $i1) throws  {
        if (this.mCircleViewIndex < 0) {
            return $i1;
        }
        if ($i1 == $i0 - 1) {
            return this.mCircleViewIndex;
        }
        return $i1 >= this.mCircleViewIndex ? $i1 + 1 : $i1;
    }

    private void createProgressView() throws  {
        this.mCircleView = new CircleImageView(getContext(), CIRCLE_BG_LIGHT, 20.0f);
        this.mProgress = new MaterialProgressDrawable(getContext(), this);
        this.mProgress.setBackgroundColor(CIRCLE_BG_LIGHT);
        this.mCircleView.setImageDrawable(this.mProgress);
        this.mCircleView.setVisibility(8);
        addView(this.mCircleView);
    }

    public void setOnRefreshListener(OnRefreshListener $r1) throws  {
        this.mListener = $r1;
    }

    private boolean isAlphaUsedForScale() throws  {
        return VERSION.SDK_INT < 11;
    }

    public void setRefreshing(boolean $z0) throws  {
        if (!$z0 || this.mRefreshing == $z0) {
            setRefreshing($z0, false);
            return;
        }
        int $i0;
        this.mRefreshing = $z0;
        if (this.mUsingCustomStart) {
            $i0 = (int) this.mSpinnerFinalOffset;
        } else {
            $i0 = (int) (this.mSpinnerFinalOffset + ((float) this.mOriginalOffsetTop));
        }
        setTargetOffsetTopAndBottom($i0 - this.mCurrentTargetOffsetTop, true);
        this.mNotify = false;
        startScaleUpAnimation(this.mRefreshListener);
    }

    private void startScaleUpAnimation(AnimationListener $r1) throws  {
        this.mCircleView.setVisibility(0);
        if (VERSION.SDK_INT >= 11) {
            this.mProgress.setAlpha(255);
        }
        this.mScaleAnimation = new C01562();
        this.mScaleAnimation.setDuration((long) this.mMediumAnimationDuration);
        if ($r1 != null) {
            this.mCircleView.setAnimationListener($r1);
        }
        this.mCircleView.clearAnimation();
        this.mCircleView.startAnimation(this.mScaleAnimation);
    }

    private void setAnimationProgress(float $f0) throws  {
        if (isAlphaUsedForScale()) {
            setColorViewAlpha((int) (255.0f * $f0));
            return;
        }
        ViewCompat.setScaleX(this.mCircleView, $f0);
        ViewCompat.setScaleY(this.mCircleView, $f0);
    }

    private void setRefreshing(boolean $z0, boolean $z1) throws  {
        if (this.mRefreshing != $z0) {
            this.mNotify = $z1;
            ensureTarget();
            this.mRefreshing = $z0;
            if (this.mRefreshing) {
                animateOffsetToCorrectPosition(this.mCurrentTargetOffsetTop, this.mRefreshListener);
            } else {
                startScaleDownAnimation(this.mRefreshListener);
            }
        }
    }

    private void startScaleDownAnimation(AnimationListener $r1) throws  {
        this.mScaleDownAnimation = new C01573();
        this.mScaleDownAnimation.setDuration(150);
        this.mCircleView.setAnimationListener($r1);
        this.mCircleView.clearAnimation();
        this.mCircleView.startAnimation(this.mScaleDownAnimation);
    }

    private void startProgressAlphaStartAnimation() throws  {
        this.mAlphaStartAnimation = startAlphaAnimation(this.mProgress.getAlpha(), 76);
    }

    private void startProgressAlphaMaxAnimation() throws  {
        this.mAlphaMaxAnimation = startAlphaAnimation(this.mProgress.getAlpha(), 255);
    }

    private Animation startAlphaAnimation(final int $i0, final int $i1) throws  {
        if (this.mScale && isAlphaUsedForScale()) {
            return null;
        }
        C01584 $r1 = new Animation() {
            public void applyTransformation(float $f0, Transformation t) throws  {
                SwipeRefreshLayout.this.mProgress.setAlpha((int) (((float) $i0) + (((float) ($i1 - $i0)) * $f0)));
            }
        };
        $r1.setDuration(300);
        this.mCircleView.setAnimationListener(null);
        this.mCircleView.clearAnimation();
        this.mCircleView.startAnimation($r1);
        return $r1;
    }

    @Deprecated
    public void setProgressBackgroundColor(@Deprecated int $i0) throws  {
        setProgressBackgroundColorSchemeResource($i0);
    }

    public void setProgressBackgroundColorSchemeResource(@ColorRes int $i0) throws  {
        setProgressBackgroundColorSchemeColor(getResources().getColor($i0));
    }

    public void setProgressBackgroundColorSchemeColor(@ColorInt int $i0) throws  {
        this.mCircleView.setBackgroundColor($i0);
        this.mProgress.setBackgroundColor($i0);
    }

    @Deprecated
    public void setColorScheme(@ColorInt @Deprecated int... $r1) throws  {
        setColorSchemeResources($r1);
    }

    public void setColorSchemeResources(@ColorRes int... $r1) throws  {
        Resources $r3 = getResources();
        int[] $r2 = new int[$r1.length];
        for (int $i0 = 0; $i0 < $r1.length; $i0++) {
            $r2[$i0] = $r3.getColor($r1[$i0]);
        }
        setColorSchemeColors($r2);
    }

    @ColorInt
    public void setColorSchemeColors(int... $r1) throws  {
        ensureTarget();
        this.mProgress.setColorSchemeColors($r1);
    }

    public boolean isRefreshing() throws  {
        return this.mRefreshing;
    }

    private void ensureTarget() throws  {
        if (this.mTarget == null) {
            int $i0 = 0;
            while ($i0 < getChildCount()) {
                View $r1 = getChildAt($i0);
                if ($r1.equals(this.mCircleView)) {
                    $i0++;
                } else {
                    this.mTarget = $r1;
                    return;
                }
            }
        }
    }

    public void setDistanceToTriggerSync(int $i0) throws  {
        this.mTotalDragDistance = (float) $i0;
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) throws  {
        left = getMeasuredWidth();
        bottom = getMeasuredHeight();
        if (getChildCount() != 0) {
            if (this.mTarget == null) {
                ensureTarget();
            }
            if (this.mTarget != null) {
                View $r1 = this.mTarget;
                top = getPaddingLeft();
                right = getPaddingTop();
                $r1.layout(top, right, top + ((left - getPaddingLeft()) - getPaddingRight()), right + ((bottom - getPaddingTop()) - getPaddingBottom()));
                int $i4 = this.mCircleView.getMeasuredWidth();
                this.mCircleView.layout((left / 2) - ($i4 / 2), this.mCurrentTargetOffsetTop, (left / 2) + ($i4 / 2), this.mCurrentTargetOffsetTop + this.mCircleView.getMeasuredHeight());
            }
        }
    }

    public void onMeasure(int $i0, int $i1) throws  {
        super.onMeasure($i0, $i1);
        if (this.mTarget == null) {
            ensureTarget();
        }
        if (this.mTarget != null) {
            this.mTarget.measure(MeasureSpec.makeMeasureSpec((getMeasuredWidth() - getPaddingLeft()) - getPaddingRight(), 1073741824), MeasureSpec.makeMeasureSpec((getMeasuredHeight() - getPaddingTop()) - getPaddingBottom(), 1073741824));
            this.mCircleView.measure(MeasureSpec.makeMeasureSpec(this.mCircleWidth, 1073741824), MeasureSpec.makeMeasureSpec(this.mCircleHeight, 1073741824));
            if (!(this.mUsingCustomStart || this.mOriginalOffsetCalculated)) {
                this.mOriginalOffsetCalculated = true;
                $i0 = -this.mCircleView.getMeasuredHeight();
                this.mOriginalOffsetTop = $i0;
                this.mCurrentTargetOffsetTop = $i0;
            }
            this.mCircleViewIndex = -1;
            for ($i0 = 0; $i0 < getChildCount(); $i0++) {
                if (getChildAt($i0) == this.mCircleView) {
                    this.mCircleViewIndex = $i0;
                    return;
                }
            }
        }
    }

    public int getProgressCircleDiameter() throws  {
        return this.mCircleView != null ? this.mCircleView.getMeasuredHeight() : 0;
    }

    public boolean canChildScrollUp() throws  {
        boolean $z0 = false;
        if (VERSION.SDK_INT >= 14) {
            return ViewCompat.canScrollVertically(this.mTarget, -1);
        }
        if (this.mTarget instanceof AbsListView) {
            AbsListView $r2 = (AbsListView) this.mTarget;
            if ($r2.getChildCount() > 0) {
                if ($r2.getFirstVisiblePosition() > 0) {
                    return true;
                }
                if ($r2.getChildAt(0).getTop() < $r2.getPaddingTop()) {
                    return true;
                }
            }
            return false;
        }
        if (ViewCompat.canScrollVertically(this.mTarget, -1) || this.mTarget.getScrollY() > 0) {
            $z0 = true;
        }
        return $z0;
    }

    public boolean onInterceptTouchEvent(MotionEvent $r1) throws  {
        ensureTarget();
        int $i0 = MotionEventCompat.getActionMasked($r1);
        if (this.mReturningToStart && $i0 == 0) {
            this.mReturningToStart = false;
        }
        if (!isEnabled()) {
            return false;
        }
        if (this.mReturningToStart) {
            return false;
        }
        if (canChildScrollUp()) {
            return false;
        }
        if (this.mRefreshing) {
            return false;
        }
        if (this.mNestedScrollInProgress) {
            return false;
        }
        float $f0;
        switch ($i0) {
            case 0:
                setTargetOffsetTopAndBottom(this.mOriginalOffsetTop - this.mCircleView.getTop(), true);
                this.mActivePointerId = MotionEventCompat.getPointerId($r1, 0);
                this.mIsBeingDragged = false;
                $f0 = getMotionEventY($r1, this.mActivePointerId);
                if ($f0 != -1.0f) {
                    this.mInitialDownY = $f0;
                    break;
                }
                return false;
            case 1:
            case 3:
                this.mIsBeingDragged = false;
                this.mActivePointerId = -1;
                break;
            case 2:
                if (this.mActivePointerId == -1) {
                    Log.e(LOG_TAG, "Got ACTION_MOVE event but don't have an active pointer id.");
                    return false;
                }
                $f0 = getMotionEventY($r1, this.mActivePointerId);
                if ($f0 != -1.0f) {
                    if ($f0 - this.mInitialDownY > ((float) this.mTouchSlop) && !this.mIsBeingDragged) {
                        this.mInitialMotionY = this.mInitialDownY + ((float) this.mTouchSlop);
                        this.mIsBeingDragged = true;
                        this.mProgress.setAlpha(76);
                        break;
                    }
                }
                return false;
            case 4:
            case 5:
                break;
            case 6:
                onSecondaryPointerUp($r1);
                break;
            default:
                break;
        }
        return this.mIsBeingDragged;
    }

    private float getMotionEventY(MotionEvent $r1, int $i0) throws  {
        $i0 = MotionEventCompat.findPointerIndex($r1, $i0);
        return $i0 < 0 ? -1.0f : MotionEventCompat.getY($r1, $i0);
    }

    public void requestDisallowInterceptTouchEvent(boolean $z0) throws  {
        if (VERSION.SDK_INT < 21 && (this.mTarget instanceof AbsListView)) {
            return;
        }
        if (this.mTarget == null || ViewCompat.isNestedScrollingEnabled(this.mTarget)) {
            super.requestDisallowInterceptTouchEvent($z0);
        }
    }

    public boolean onStartNestedScroll(View child, View target, int $i0) throws  {
        return (!isEnabled() || this.mReturningToStart || this.mRefreshing || ($i0 & 2) == 0) ? false : true;
    }

    public void onNestedScrollAccepted(View $r1, View $r2, int $i0) throws  {
        this.mNestedScrollingParentHelper.onNestedScrollAccepted($r1, $r2, $i0);
        startNestedScroll($i0 & 2);
        this.mTotalUnconsumed = 0.0f;
        this.mNestedScrollInProgress = true;
    }

    public void onNestedPreScroll(View target, int $i0, int $i1, int[] $r2) throws  {
        if ($i1 > 0 && this.mTotalUnconsumed > 0.0f) {
            if (((float) $i1) > this.mTotalUnconsumed) {
                $r2[1] = $i1 - ((int) this.mTotalUnconsumed);
                this.mTotalUnconsumed = 0.0f;
            } else {
                this.mTotalUnconsumed -= (float) $i1;
                $r2[1] = $i1;
            }
            moveSpinner(this.mTotalUnconsumed);
        }
        if (this.mUsingCustomStart && $i1 > 0 && this.mTotalUnconsumed == 0.0f && Math.abs($i1 - $r2[1]) > 0) {
            this.mCircleView.setVisibility(8);
        }
        int[] $r3 = this.mParentScrollConsumed;
        if (dispatchNestedPreScroll($i0 - $r2[0], $i1 - $r2[1], $r3, null)) {
            $r2[0] = $r2[0] + $r3[0];
            $r2[1] = $r2[1] + $r3[1];
        }
    }

    public int getNestedScrollAxes() throws  {
        return this.mNestedScrollingParentHelper.getNestedScrollAxes();
    }

    public void onStopNestedScroll(View $r1) throws  {
        this.mNestedScrollingParentHelper.onStopNestedScroll($r1);
        this.mNestedScrollInProgress = false;
        if (this.mTotalUnconsumed > 0.0f) {
            finishSpinner(this.mTotalUnconsumed);
            this.mTotalUnconsumed = 0.0f;
        }
        stopNestedScroll();
    }

    public void onNestedScroll(View target, int $i0, int $i1, int $i2, int $i3) throws  {
        dispatchNestedScroll($i0, $i1, $i2, $i3, this.mParentOffsetInWindow);
        $i0 = $i3 + this.mParentOffsetInWindow[1];
        if ($i0 < 0 && !canChildScrollUp()) {
            this.mTotalUnconsumed += (float) Math.abs($i0);
            moveSpinner(this.mTotalUnconsumed);
        }
    }

    public void setNestedScrollingEnabled(boolean $z0) throws  {
        this.mNestedScrollingChildHelper.setNestedScrollingEnabled($z0);
    }

    public boolean isNestedScrollingEnabled() throws  {
        return this.mNestedScrollingChildHelper.isNestedScrollingEnabled();
    }

    public boolean startNestedScroll(int $i0) throws  {
        return this.mNestedScrollingChildHelper.startNestedScroll($i0);
    }

    public void stopNestedScroll() throws  {
        this.mNestedScrollingChildHelper.stopNestedScroll();
    }

    public boolean hasNestedScrollingParent() throws  {
        return this.mNestedScrollingChildHelper.hasNestedScrollingParent();
    }

    public boolean dispatchNestedScroll(int $i0, int $i1, int $i2, int $i3, int[] $r1) throws  {
        return this.mNestedScrollingChildHelper.dispatchNestedScroll($i0, $i1, $i2, $i3, $r1);
    }

    public boolean dispatchNestedPreScroll(int $i0, int $i1, int[] $r1, int[] $r2) throws  {
        return this.mNestedScrollingChildHelper.dispatchNestedPreScroll($i0, $i1, $r1, $r2);
    }

    public boolean onNestedPreFling(View target, float $f0, float $f1) throws  {
        return dispatchNestedPreFling($f0, $f1);
    }

    public boolean onNestedFling(View target, float $f0, float $f1, boolean $z0) throws  {
        return dispatchNestedFling($f0, $f1, $z0);
    }

    public boolean dispatchNestedFling(float $f0, float $f1, boolean $z0) throws  {
        return this.mNestedScrollingChildHelper.dispatchNestedFling($f0, $f1, $z0);
    }

    public boolean dispatchNestedPreFling(float $f0, float $f1) throws  {
        return this.mNestedScrollingChildHelper.dispatchNestedPreFling($f0, $f1);
    }

    private boolean isAnimationRunning(Animation $r1) throws  {
        return ($r1 == null || !$r1.hasStarted() || $r1.hasEnded()) ? false : true;
    }

    private void finishSpinner(float $f0) throws  {
        if ($f0 > this.mTotalDragDistance) {
            setRefreshing(true, true);
            return;
        }
        this.mRefreshing = false;
        this.mProgress.setStartEndTrim(0.0f, 0.0f);
        C01595 $r2 = null;
        if (!this.mScale) {
            $r2 = new C01595();
        }
        animateOffsetToStartPosition(this.mCurrentTargetOffsetTop, $r2);
        this.mProgress.showArrow(false);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean onTouchEvent(android.view.MotionEvent r10) throws  {
        /*
        r9 = this;
        r0 = android.support.v4.view.MotionEventCompat.getActionMasked(r10);
        r1 = r9.mReturningToStart;
        if (r1 == 0) goto L_0x000d;
    L_0x0008:
        if (r0 != 0) goto L_0x000d;
    L_0x000a:
        r2 = 0;
        r9.mReturningToStart = r2;
    L_0x000d:
        r1 = r9.isEnabled();
        if (r1 == 0) goto L_0x009e;
    L_0x0013:
        r1 = r9.mReturningToStart;
        if (r1 != 0) goto L_0x00a0;
    L_0x0017:
        r1 = r9.canChildScrollUp();
        if (r1 != 0) goto L_0x00a2;
    L_0x001d:
        r1 = r9.mNestedScrollInProgress;
        if (r1 == 0) goto L_0x0023;
    L_0x0021:
        r2 = 0;
        return r2;
    L_0x0023:
        switch(r0) {
            case 0: goto L_0x0029;
            case 1: goto L_0x0077;
            case 2: goto L_0x0034;
            case 3: goto L_0x0021;
            case 4: goto L_0x0027;
            case 5: goto L_0x005d;
            case 6: goto L_0x0073;
            default: goto L_0x0026;
        };
    L_0x0026:
        goto L_0x0027;
    L_0x0027:
        r2 = 1;
        return r2;
    L_0x0029:
        r2 = 0;
        r0 = android.support.v4.view.MotionEventCompat.getPointerId(r10, r2);
        r9.mActivePointerId = r0;
        r2 = 0;
        r9.mIsBeingDragged = r2;
        goto L_0x0027;
    L_0x0034:
        r0 = r9.mActivePointerId;
        r0 = android.support.v4.view.MotionEventCompat.findPointerIndex(r10, r0);
        if (r0 >= 0) goto L_0x0045;
    L_0x003c:
        r3 = LOG_TAG;
        r4 = "Got ACTION_MOVE event but have an invalid active pointer id.";
        android.util.Log.e(r3, r4);
        r2 = 0;
        return r2;
    L_0x0045:
        r5 = android.support.v4.view.MotionEventCompat.getY(r10, r0);
        r6 = r9.mInitialMotionY;
        r5 = r5 - r6;
        r7 = 1056964608; // 0x3f000000 float:0.5 double:5.222099017E-315;
        r5 = r5 * r7;
        r1 = r9.mIsBeingDragged;
        if (r1 == 0) goto L_0x0027;
    L_0x0054:
        r7 = 0;
        r8 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1));
        if (r8 <= 0) goto L_0x00a4;
    L_0x0059:
        r9.moveSpinner(r5);
        goto L_0x0027;
    L_0x005d:
        r0 = android.support.v4.view.MotionEventCompat.getActionIndex(r10);
        if (r0 >= 0) goto L_0x006c;
    L_0x0063:
        r3 = LOG_TAG;
        r4 = "Got ACTION_POINTER_DOWN event but have an invalid action index.";
        android.util.Log.e(r3, r4);
        r2 = 0;
        return r2;
    L_0x006c:
        r0 = android.support.v4.view.MotionEventCompat.getPointerId(r10, r0);
        r9.mActivePointerId = r0;
        goto L_0x0027;
    L_0x0073:
        r9.onSecondaryPointerUp(r10);
        goto L_0x0027;
    L_0x0077:
        r0 = r9.mActivePointerId;
        r0 = android.support.v4.view.MotionEventCompat.findPointerIndex(r10, r0);
        if (r0 >= 0) goto L_0x0088;
    L_0x007f:
        r3 = LOG_TAG;
        r4 = "Got ACTION_UP event but don't have an active pointer id.";
        android.util.Log.e(r3, r4);
        r2 = 0;
        return r2;
    L_0x0088:
        r5 = android.support.v4.view.MotionEventCompat.getY(r10, r0);
        r6 = r9.mInitialMotionY;
        r5 = r5 - r6;
        r7 = 1056964608; // 0x3f000000 float:0.5 double:5.222099017E-315;
        r5 = r5 * r7;
        r2 = 0;
        r9.mIsBeingDragged = r2;
        r9.finishSpinner(r5);
        r2 = -1;
        r9.mActivePointerId = r2;
        r2 = 0;
        return r2;
    L_0x009e:
        r2 = 0;
        return r2;
    L_0x00a0:
        r2 = 0;
        return r2;
    L_0x00a2:
        r2 = 0;
        return r2;
    L_0x00a4:
        r2 = 0;
        return r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.widget.SwipeRefreshLayout.onTouchEvent(android.view.MotionEvent):boolean");
    }

    private void animateOffsetToCorrectPosition(int $i0, AnimationListener $r1) throws  {
        this.mFrom = $i0;
        this.mAnimateToCorrectPosition.reset();
        this.mAnimateToCorrectPosition.setDuration(200);
        this.mAnimateToCorrectPosition.setInterpolator(this.mDecelerateInterpolator);
        if ($r1 != null) {
            this.mCircleView.setAnimationListener($r1);
        }
        this.mCircleView.clearAnimation();
        this.mCircleView.startAnimation(this.mAnimateToCorrectPosition);
    }

    private void animateOffsetToStartPosition(int $i0, AnimationListener $r1) throws  {
        if (this.mScale) {
            startScaleDownReturnToStartAnimation($i0, $r1);
            return;
        }
        this.mFrom = $i0;
        this.mAnimateToStartPosition.reset();
        this.mAnimateToStartPosition.setDuration(200);
        this.mAnimateToStartPosition.setInterpolator(this.mDecelerateInterpolator);
        if ($r1 != null) {
            this.mCircleView.setAnimationListener($r1);
        }
        this.mCircleView.clearAnimation();
        this.mCircleView.startAnimation(this.mAnimateToStartPosition);
    }

    private void moveToStart(float $f0) throws  {
        setTargetOffsetTopAndBottom((this.mFrom + ((int) (((float) (this.mOriginalOffsetTop - this.mFrom)) * $f0))) - this.mCircleView.getTop(), false);
    }

    private void startScaleDownReturnToStartAnimation(int $i0, AnimationListener $r1) throws  {
        this.mFrom = $i0;
        if (isAlphaUsedForScale()) {
            this.mStartingScale = (float) this.mProgress.getAlpha();
        } else {
            this.mStartingScale = ViewCompat.getScaleX(this.mCircleView);
        }
        this.mScaleDownToStartAnimation = new C01628();
        this.mScaleDownToStartAnimation.setDuration(150);
        if ($r1 != null) {
            this.mCircleView.setAnimationListener($r1);
        }
        this.mCircleView.clearAnimation();
        this.mCircleView.startAnimation(this.mScaleDownToStartAnimation);
    }

    private void setTargetOffsetTopAndBottom(int $i0, boolean $z0) throws  {
        this.mCircleView.bringToFront();
        this.mCircleView.offsetTopAndBottom($i0);
        this.mCurrentTargetOffsetTop = this.mCircleView.getTop();
        if ($z0 && VERSION.SDK_INT < 11) {
            invalidate();
        }
    }

    private void onSecondaryPointerUp(MotionEvent $r1) throws  {
        int $i0 = MotionEventCompat.getActionIndex($r1);
        if (MotionEventCompat.getPointerId($r1, $i0) == this.mActivePointerId) {
            byte $b3;
            if ($i0 == 0) {
                $b3 = (byte) 1;
            } else {
                $b3 = (byte) 0;
            }
            this.mActivePointerId = MotionEventCompat.getPointerId($r1, $b3);
        }
    }
}
