package android.support.v4.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.v4.view.AccessibilityDelegateCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.ScrollingView;
import android.support.v4.view.VelocityTrackerCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.accessibility.AccessibilityEventCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.view.accessibility.AccessibilityRecordCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.FocusFinder;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.View.MeasureSpec;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import java.util.ArrayList;

public class NestedScrollView extends FrameLayout implements NestedScrollingChild, NestedScrollingParent, ScrollingView {
    private static final AccessibilityDelegate ACCESSIBILITY_DELEGATE = new AccessibilityDelegate();
    static final int ANIMATED_SCROLL_GAP = 250;
    private static final int INVALID_POINTER = -1;
    static final float MAX_SCROLL_FACTOR = 0.5f;
    private static final int[] SCROLLVIEW_STYLEABLE = new int[]{16843130};
    private static final String TAG = "NestedScrollView";
    private int mActivePointerId;
    private final NestedScrollingChildHelper mChildHelper;
    private View mChildToScrollTo;
    private EdgeEffectCompat mEdgeGlowBottom;
    private EdgeEffectCompat mEdgeGlowTop;
    private boolean mFillViewport;
    private boolean mIsBeingDragged;
    private boolean mIsLaidOut;
    private boolean mIsLayoutDirty;
    private int mLastMotionY;
    private long mLastScroll;
    private int mMaximumVelocity;
    private int mMinimumVelocity;
    private int mNestedYOffset;
    private OnScrollChangeListener mOnScrollChangeListener;
    private final NestedScrollingParentHelper mParentHelper;
    private SavedState mSavedState;
    private final int[] mScrollConsumed;
    private final int[] mScrollOffset;
    private ScrollerCompat mScroller;
    private boolean mSmoothScrollingEnabled;
    private final Rect mTempRect;
    private int mTouchSlop;
    private VelocityTracker mVelocityTracker;
    private float mVerticalScrollFactor;

    static class AccessibilityDelegate extends AccessibilityDelegateCompat {
        AccessibilityDelegate() throws  {
        }

        public boolean performAccessibilityAction(View $r1, int $i0, Bundle $r2) throws  {
            if (super.performAccessibilityAction($r1, $i0, $r2)) {
                return true;
            }
            NestedScrollView $r3 = (NestedScrollView) $r1;
            if (!$r3.isEnabled()) {
                return false;
            }
            switch ($i0) {
                case 4096:
                    $i0 = Math.min($r3.getScrollY() + (($r3.getHeight() - $r3.getPaddingBottom()) - $r3.getPaddingTop()), $r3.getScrollRange());
                    if ($i0 == $r3.getScrollY()) {
                        return false;
                    }
                    $r3.smoothScrollTo(0, $i0);
                    return true;
                case 8192:
                    $i0 = Math.max($r3.getScrollY() - (($r3.getHeight() - $r3.getPaddingBottom()) - $r3.getPaddingTop()), 0);
                    if ($i0 == $r3.getScrollY()) {
                        return false;
                    }
                    $r3.smoothScrollTo(0, $i0);
                    return true;
                default:
                    return false;
            }
        }

        public void onInitializeAccessibilityNodeInfo(View $r1, AccessibilityNodeInfoCompat $r2) throws  {
            super.onInitializeAccessibilityNodeInfo($r1, $r2);
            NestedScrollView $r3 = (NestedScrollView) $r1;
            $r2.setClassName(ScrollView.class.getName());
            if ($r3.isEnabled()) {
                int $i0 = $r3.getScrollRange();
                if ($i0 > 0) {
                    $r2.setScrollable(true);
                    if ($r3.getScrollY() > 0) {
                        $r2.addAction(8192);
                    }
                    if ($r3.getScrollY() < $i0) {
                        $r2.addAction(4096);
                    }
                }
            }
        }

        public void onInitializeAccessibilityEvent(View $r1, AccessibilityEvent $r2) throws  {
            super.onInitializeAccessibilityEvent($r1, $r2);
            NestedScrollView $r3 = (NestedScrollView) $r1;
            $r2.setClassName(ScrollView.class.getName());
            AccessibilityRecordCompat $r6 = AccessibilityEventCompat.asRecord($r2);
            $r6.setScrollable($r3.getScrollRange() > 0);
            $r6.setScrollX($r3.getScrollX());
            $r6.setScrollY($r3.getScrollY());
            $r6.setMaxScrollX($r3.getScrollX());
            $r6.setMaxScrollY($r3.getScrollRange());
        }
    }

    public interface OnScrollChangeListener {
        void onScrollChange(NestedScrollView nestedScrollView, int i, int i2, int i3, int i4) throws ;
    }

    static class SavedState extends BaseSavedState {
        public static final Creator<SavedState> CREATOR = new C01481();
        public int scrollPosition;

        static class C01481 implements Creator<SavedState> {
            C01481() throws  {
            }

            public SavedState createFromParcel(Parcel $r1) throws  {
                return new SavedState($r1);
            }

            public SavedState[] newArray(int $i0) throws  {
                return new SavedState[$i0];
            }
        }

        SavedState(Parcelable $r1) throws  {
            super($r1);
        }

        public SavedState(Parcel $r1) throws  {
            super($r1);
            this.scrollPosition = $r1.readInt();
        }

        public void writeToParcel(Parcel $r1, int $i0) throws  {
            super.writeToParcel($r1, $i0);
            $r1.writeInt(this.scrollPosition);
        }

        public String toString() throws  {
            return "HorizontalScrollView.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " scrollPosition=" + this.scrollPosition + "}";
        }
    }

    public boolean onInterceptTouchEvent(android.view.MotionEvent r23) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:41:0x0155 in {5, 8, 10, 15, 17, 21, 25, 34, 38, 39, 42} preds:[]
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
        r22 = this;
        r7 = 0;
        r0 = r23;
        r8 = r0.getAction();
        r9 = 2;
        if (r8 != r9) goto L_0x0012;
    L_0x000a:
        r0 = r22;
        r10 = r0.mIsBeingDragged;
        if (r10 == 0) goto L_0x0012;
    L_0x0010:
        r9 = 1;
        return r9;
    L_0x0012:
        r8 = r8 & 255;
        switch(r8) {
            case 0: goto L_0x00a8;
            case 1: goto L_0x0109;
            case 2: goto L_0x001d;
            case 3: goto L_0x0109;
            case 4: goto L_0x0018;
            case 5: goto L_0x0018;
            case 6: goto L_0x0159;
            default: goto L_0x0017;
        };
    L_0x0017:
        goto L_0x0018;
    L_0x0018:
        r0 = r22;
        r7 = r0.mIsBeingDragged;
        return r7;
    L_0x001d:
        r0 = r22;
        r8 = r0.mActivePointerId;
        r9 = -1;
        if (r8 == r9) goto L_0x0018;
    L_0x0024:
        r0 = r23;
        r11 = android.support.v4.view.MotionEventCompat.findPointerIndex(r0, r8);
        r9 = -1;
        if (r11 != r9) goto L_0x004c;
    L_0x002d:
        r12 = new java.lang.StringBuilder;
        r12.<init>();
        r13 = "Invalid pointerId=";
        r12 = r12.append(r13);
        r12 = r12.append(r8);
        r13 = " in onInterceptTouchEvent";
        r12 = r12.append(r13);
        r14 = r12.toString();
        r13 = "NestedScrollView";
        android.util.Log.e(r13, r14);
        goto L_0x0018;
    L_0x004c:
        r0 = r23;
        r15 = android.support.v4.view.MotionEventCompat.getY(r0, r11);
        r8 = (int) r15;
        r0 = r22;
        r11 = r0.mLastMotionY;
        r11 = r8 - r11;
        goto L_0x005d;
    L_0x005a:
        goto L_0x0018;
    L_0x005d:
        r11 = java.lang.Math.abs(r11);
        r0 = r22;
        r0 = r0.mTouchSlop;
        r16 = r0;
        if (r11 <= r0) goto L_0x0018;
    L_0x0069:
        goto L_0x006d;
    L_0x006a:
        goto L_0x0018;
    L_0x006d:
        r0 = r22;
        r11 = r0.getNestedScrollAxes();
        r11 = r11 & 2;
        if (r11 != 0) goto L_0x0018;
    L_0x0077:
        r9 = 1;
        r0 = r22;
        r0.mIsBeingDragged = r9;
        r0 = r22;
        r0.mLastMotionY = r8;
        r0 = r22;
        r0.initVelocityTrackerIfNotExists();
        r0 = r22;
        r0 = r0.mVelocityTracker;
        r17 = r0;
        r1 = r23;
        r0.addMovement(r1);
        goto L_0x0094;
    L_0x0091:
        goto L_0x0018;
    L_0x0094:
        r9 = 0;
        r0 = r22;
        r0.mNestedYOffset = r9;
        r0 = r22;
        r18 = r0.getParent();
        if (r18 == 0) goto L_0x0018;
    L_0x00a1:
        r9 = 1;
        r0 = r18;
        r0.requestDisallowInterceptTouchEvent(r9);
        goto L_0x005a;
    L_0x00a8:
        r0 = r23;
        r15 = r0.getY();
        r8 = (int) r15;
        r0 = r23;
        r15 = r0.getX();
        r11 = (int) r15;
        r0 = r22;
        r10 = r0.inChild(r11, r8);
        if (r10 != 0) goto L_0x00c9;
    L_0x00be:
        r9 = 0;
        r0 = r22;
        r0.mIsBeingDragged = r9;
        r0 = r22;
        r0.recycleVelocityTracker();
        goto L_0x006a;
    L_0x00c9:
        r0 = r22;
        r0.mLastMotionY = r8;
        r9 = 0;
        r0 = r23;
        r8 = android.support.v4.view.MotionEventCompat.getPointerId(r0, r9);
        r0 = r22;
        r0.mActivePointerId = r8;
        r0 = r22;
        r0.initOrResetVelocityTracker();
        r0 = r22;
        r0 = r0.mVelocityTracker;
        r17 = r0;
        r1 = r23;
        r0.addMovement(r1);
        r0 = r22;
        r0 = r0.mScroller;
        r19 = r0;
        r0.computeScrollOffset();
        r0 = r22;
        r0 = r0.mScroller;
        r19 = r0;
        r10 = r0.isFinished();
        if (r10 != 0) goto L_0x00fe;
    L_0x00fd:
        r7 = 1;
    L_0x00fe:
        r0 = r22;
        r0.mIsBeingDragged = r7;
        r9 = 2;
        r0 = r22;
        r0.startNestedScroll(r9);
        goto L_0x0091;
    L_0x0109:
        r9 = 0;
        r0 = r22;
        r0.mIsBeingDragged = r9;
        r9 = -1;
        r0 = r22;
        r0.mActivePointerId = r9;
        r0 = r22;
        r0.recycleVelocityTracker();
        r0 = r22;
        r0 = r0.mScroller;
        r19 = r0;
        r0 = r22;
        r8 = r0.getScrollX();
        r0 = r22;
        r11 = r0.getScrollY();
        r0 = r22;
        r16 = r0.getScrollRange();
        r9 = 0;
        r20 = 0;
        r21 = 0;
        r0 = r19;
        r1 = r8;
        r2 = r11;
        r3 = r9;
        r4 = r20;
        r5 = r21;
        r6 = r16;
        r7 = r0.springBack(r1, r2, r3, r4, r5, r6);
        if (r7 == 0) goto L_0x014f;
    L_0x0146:
        r0 = r22;
        android.support.v4.view.ViewCompat.postInvalidateOnAnimation(r0);
        goto L_0x014f;
    L_0x014c:
        goto L_0x0018;
    L_0x014f:
        r0 = r22;
        r0.stopNestedScroll();
        goto L_0x014c;
        goto L_0x0159;
    L_0x0156:
        goto L_0x0018;
    L_0x0159:
        r0 = r22;
        r1 = r23;
        r0.onSecondaryPointerUp(r1);
        goto L_0x0156;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.widget.NestedScrollView.onInterceptTouchEvent(android.view.MotionEvent):boolean");
    }

    public boolean shouldDelayChildPressedState() throws  {
        return true;
    }

    public NestedScrollView(Context $r1) throws  {
        this($r1, null);
    }

    public NestedScrollView(Context $r1, AttributeSet $r2) throws  {
        this($r1, $r2, 0);
    }

    public NestedScrollView(Context $r1, AttributeSet $r2, int $i0) throws  {
        super($r1, $r2, $i0);
        this.mTempRect = new Rect();
        this.mIsLayoutDirty = true;
        this.mIsLaidOut = false;
        this.mChildToScrollTo = null;
        this.mIsBeingDragged = false;
        this.mSmoothScrollingEnabled = true;
        this.mActivePointerId = -1;
        this.mScrollOffset = new int[2];
        this.mScrollConsumed = new int[2];
        initScrollView();
        TypedArray $r5 = $r1.obtainStyledAttributes($r2, SCROLLVIEW_STYLEABLE, $i0, 0);
        setFillViewport($r5.getBoolean(0, false));
        $r5.recycle();
        this.mParentHelper = new NestedScrollingParentHelper(this);
        this.mChildHelper = new NestedScrollingChildHelper(this);
        setNestedScrollingEnabled(true);
        ViewCompat.setAccessibilityDelegate(this, ACCESSIBILITY_DELEGATE);
    }

    public void setNestedScrollingEnabled(boolean $z0) throws  {
        this.mChildHelper.setNestedScrollingEnabled($z0);
    }

    public boolean isNestedScrollingEnabled() throws  {
        return this.mChildHelper.isNestedScrollingEnabled();
    }

    public boolean startNestedScroll(int $i0) throws  {
        return this.mChildHelper.startNestedScroll($i0);
    }

    public void stopNestedScroll() throws  {
        this.mChildHelper.stopNestedScroll();
    }

    public boolean hasNestedScrollingParent() throws  {
        return this.mChildHelper.hasNestedScrollingParent();
    }

    public boolean dispatchNestedScroll(int $i0, int $i1, int $i2, int $i3, int[] $r1) throws  {
        return this.mChildHelper.dispatchNestedScroll($i0, $i1, $i2, $i3, $r1);
    }

    public boolean dispatchNestedPreScroll(int $i0, int $i1, int[] $r1, int[] $r2) throws  {
        return this.mChildHelper.dispatchNestedPreScroll($i0, $i1, $r1, $r2);
    }

    public boolean dispatchNestedFling(float $f0, float $f1, boolean $z0) throws  {
        return this.mChildHelper.dispatchNestedFling($f0, $f1, $z0);
    }

    public boolean dispatchNestedPreFling(float $f0, float $f1) throws  {
        return this.mChildHelper.dispatchNestedPreFling($f0, $f1);
    }

    public boolean onStartNestedScroll(View child, View target, int $i0) throws  {
        return ($i0 & 2) != 0;
    }

    public void onNestedScrollAccepted(View $r1, View $r2, int $i0) throws  {
        this.mParentHelper.onNestedScrollAccepted($r1, $r2, $i0);
        startNestedScroll(2);
    }

    public void onStopNestedScroll(View $r1) throws  {
        this.mParentHelper.onStopNestedScroll($r1);
        stopNestedScroll();
    }

    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int $i3) throws  {
        dxConsumed = getScrollY();
        scrollBy(0, $i3);
        dxConsumed = getScrollY() - dxConsumed;
        dispatchNestedScroll(0, dxConsumed, 0, $i3 - dxConsumed, null);
    }

    public void onNestedPreScroll(View target, int $i0, int $i1, int[] $r2) throws  {
        dispatchNestedPreScroll($i0, $i1, $r2, null);
    }

    public boolean onNestedFling(View target, float velocityX, float $f1, boolean $z0) throws  {
        if ($z0) {
            return false;
        }
        flingWithNestedDispatch((int) $f1);
        return true;
    }

    public boolean onNestedPreFling(View target, float $f0, float $f1) throws  {
        return dispatchNestedPreFling($f0, $f1);
    }

    public int getNestedScrollAxes() throws  {
        return this.mParentHelper.getNestedScrollAxes();
    }

    protected float getTopFadingEdgeStrength() throws  {
        if (getChildCount() == 0) {
            return 0.0f;
        }
        int $i0 = getVerticalFadingEdgeLength();
        int $i1 = getScrollY();
        return $i1 < $i0 ? ((float) $i1) / ((float) $i0) : 1.0f;
    }

    protected float getBottomFadingEdgeStrength() throws  {
        if (getChildCount() == 0) {
            return 0.0f;
        }
        int $i1 = getVerticalFadingEdgeLength();
        int $i0 = (getChildAt(0).getBottom() - getScrollY()) - (getHeight() - getPaddingBottom());
        return $i0 < $i1 ? ((float) $i0) / ((float) $i1) : 1.0f;
    }

    public int getMaxScrollAmount() throws  {
        return (int) (0.5f * ((float) getHeight()));
    }

    private void initScrollView() throws  {
        this.mScroller = ScrollerCompat.create(getContext(), null);
        setFocusable(true);
        setDescendantFocusability(262144);
        setWillNotDraw(false);
        ViewConfiguration $r3 = ViewConfiguration.get(getContext());
        this.mTouchSlop = $r3.getScaledTouchSlop();
        this.mMinimumVelocity = $r3.getScaledMinimumFlingVelocity();
        this.mMaximumVelocity = $r3.getScaledMaximumFlingVelocity();
    }

    public void addView(View $r1) throws  {
        if (getChildCount() > 0) {
            throw new IllegalStateException("ScrollView can host only one direct child");
        }
        super.addView($r1);
    }

    public void addView(View $r1, int $i0) throws  {
        if (getChildCount() > 0) {
            throw new IllegalStateException("ScrollView can host only one direct child");
        }
        super.addView($r1, $i0);
    }

    public void addView(View $r1, LayoutParams $r2) throws  {
        if (getChildCount() > 0) {
            throw new IllegalStateException("ScrollView can host only one direct child");
        }
        super.addView($r1, $r2);
    }

    public void addView(View $r1, int $i0, LayoutParams $r2) throws  {
        if (getChildCount() > 0) {
            throw new IllegalStateException("ScrollView can host only one direct child");
        }
        super.addView($r1, $i0, $r2);
    }

    public void setOnScrollChangeListener(OnScrollChangeListener $r1) throws  {
        this.mOnScrollChangeListener = $r1;
    }

    private boolean canScroll() throws  {
        View $r1 = getChildAt(0);
        if ($r1 == null) {
            return false;
        }
        return getHeight() < (getPaddingTop() + $r1.getHeight()) + getPaddingBottom();
    }

    public boolean isFillViewport() throws  {
        return this.mFillViewport;
    }

    public void setFillViewport(boolean $z0) throws  {
        if ($z0 != this.mFillViewport) {
            this.mFillViewport = $z0;
            requestLayout();
        }
    }

    public boolean isSmoothScrollingEnabled() throws  {
        return this.mSmoothScrollingEnabled;
    }

    public void setSmoothScrollingEnabled(boolean $z0) throws  {
        this.mSmoothScrollingEnabled = $z0;
    }

    protected void onScrollChanged(int $i0, int $i1, int $i2, int $i3) throws  {
        super.onScrollChanged($i0, $i1, $i2, $i3);
        if (this.mOnScrollChangeListener != null) {
            this.mOnScrollChangeListener.onScrollChange(this, $i0, $i1, $i2, $i3);
        }
    }

    protected void onMeasure(int $i0, int $i1) throws  {
        super.onMeasure($i0, $i1);
        if (this.mFillViewport && MeasureSpec.getMode($i1) != 0 && getChildCount() > 0) {
            View $r1 = getChildAt(0);
            $i1 = getMeasuredHeight();
            if ($r1.getMeasuredHeight() < $i1) {
                $r1.measure(getChildMeasureSpec($i0, getPaddingLeft() + getPaddingRight(), ((FrameLayout.LayoutParams) $r1.getLayoutParams()).width), MeasureSpec.makeMeasureSpec(($i1 - getPaddingTop()) - getPaddingBottom(), 1073741824));
            }
        }
    }

    public boolean dispatchKeyEvent(KeyEvent $r1) throws  {
        return super.dispatchKeyEvent($r1) || executeKeyEvent($r1);
    }

    public boolean executeKeyEvent(KeyEvent $r1) throws  {
        this.mTempRect.setEmpty();
        if (canScroll()) {
            boolean $z0 = false;
            if ($r1.getAction() == 0) {
                switch ($r1.getKeyCode()) {
                    case 19:
                        if (!$r1.isAltPressed()) {
                            $z0 = arrowScroll(33);
                            break;
                        }
                        $z0 = fullScroll(33);
                        break;
                    case 20:
                        if (!$r1.isAltPressed()) {
                            $z0 = arrowScroll(130);
                            break;
                        }
                        $z0 = fullScroll(130);
                        break;
                    case 62:
                        pageScroll($r1.isShiftPressed() ? (short) 33 : (short) 130);
                        break;
                    default:
                        break;
                }
            }
            return $z0;
        } else if (!isFocused()) {
            return false;
        } else {
            if ($r1.getKeyCode() == 4) {
                return false;
            }
            View $r3 = findFocus();
            View $r4 = $r3;
            if ($r3 == this) {
                $r4 = null;
            }
            $r3 = FocusFinder.getInstance().findNextFocus(this, $r4, 130);
            if ($r3 == null) {
                return false;
            }
            if ($r3 == this) {
                return false;
            }
            if ($r3.requestFocus(130)) {
                return true;
            }
            return false;
        }
    }

    private boolean inChild(int $i0, int $i1) throws  {
        if (getChildCount() <= 0) {
            return false;
        }
        int $i2 = getScrollY();
        View $r1 = getChildAt(0);
        if ($i1 < $r1.getTop() - $i2) {
            return false;
        }
        if ($i1 >= $r1.getBottom() - $i2) {
            return false;
        }
        if ($i0 >= $r1.getLeft()) {
            return $i0 < $r1.getRight();
        } else {
            return false;
        }
    }

    private void initOrResetVelocityTracker() throws  {
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        } else {
            this.mVelocityTracker.clear();
        }
    }

    private void initVelocityTrackerIfNotExists() throws  {
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
    }

    private void recycleVelocityTracker() throws  {
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.recycle();
            this.mVelocityTracker = null;
        }
    }

    public void requestDisallowInterceptTouchEvent(boolean $z0) throws  {
        if ($z0) {
            recycleVelocityTracker();
        }
        super.requestDisallowInterceptTouchEvent($z0);
    }

    public boolean onTouchEvent(MotionEvent $r1) throws  {
        initVelocityTrackerIfNotExists();
        MotionEvent $r2 = MotionEvent.obtain($r1);
        int $i2 = MotionEventCompat.getActionMasked($r1);
        if ($i2 == 0) {
            this.mNestedYOffset = 0;
        }
        $r2.offsetLocation(0.0f, (float) this.mNestedYOffset);
        boolean $z0;
        ViewParent $r5;
        int $i3;
        int $i4;
        switch ($i2) {
            case 0:
                if (getChildCount() != 0) {
                    ScrollerCompat $r4 = this.mScroller;
                    $z0 = !$r4.isFinished();
                    this.mIsBeingDragged = $z0;
                    if ($z0) {
                        $r5 = getParent();
                        if ($r5 != null) {
                            $r5.requestDisallowInterceptTouchEvent(true);
                        }
                    }
                    $r4 = this.mScroller;
                    if (!$r4.isFinished()) {
                        $r4 = this.mScroller;
                        $r4.abortAnimation();
                    }
                    this.mLastMotionY = (int) $r1.getY();
                    this.mActivePointerId = MotionEventCompat.getPointerId($r1, 0);
                    startNestedScroll(2);
                    break;
                }
                return false;
            case 1:
                if (this.mIsBeingDragged) {
                    VelocityTracker $r3 = this.mVelocityTracker;
                    $r3.computeCurrentVelocity(1000, (float) this.mMaximumVelocity);
                    $i2 = (int) VelocityTrackerCompat.getYVelocity($r3, this.mActivePointerId);
                    $i3 = Math.abs($i2);
                    $i4 = this.mMinimumVelocity;
                    if ($i3 > $i4) {
                        flingWithNestedDispatch(-$i2);
                    } else {
                        if (this.mScroller.springBack(getScrollX(), getScrollY(), 0, 0, 0, getScrollRange())) {
                            ViewCompat.postInvalidateOnAnimation(this);
                        }
                    }
                }
                this.mActivePointerId = -1;
                endDrag();
                break;
            case 2:
                $i2 = MotionEventCompat.findPointerIndex($r1, this.mActivePointerId);
                if ($i2 != -1) {
                    int i;
                    int $i42 = (int) MotionEventCompat.getY($r1, $i2);
                    $i3 = this.mLastMotionY - $i42;
                    if (dispatchNestedPreScroll(0, $i3, this.mScrollConsumed, this.mScrollOffset)) {
                        $i3 -= this.mScrollConsumed[1];
                        $r2.offsetLocation(0.0f, (float) this.mScrollOffset[1]);
                        $i4 = this.mNestedYOffset + this.mScrollOffset[1];
                        i = $i4;
                        this.mNestedYOffset = $i4;
                    }
                    if (!this.mIsBeingDragged && Math.abs($i3) > this.mTouchSlop) {
                        $r5 = getParent();
                        if ($r5 != null) {
                            $r5.requestDisallowInterceptTouchEvent(true);
                        }
                        this.mIsBeingDragged = true;
                        if ($i3 > 0) {
                            $i4 = this.mTouchSlop;
                            $i3 -= $i4;
                        } else {
                            $i4 = this.mTouchSlop;
                            $i3 += $i4;
                        }
                    }
                    if (this.mIsBeingDragged) {
                        $i4 = $i42 - this.mScrollOffset[1];
                        $i42 = $i4;
                        this.mLastMotionY = $i4;
                        i = getScrollY();
                        $i42 = getScrollRange();
                        int $i0 = ViewCompat.getOverScrollMode(this);
                        if ($i0 == 0 || ($i0 == 1 && $i42 > 0)) {
                            $z0 = true;
                        } else {
                            $z0 = false;
                        }
                        if (overScrollByCompat(0, $i3, 0, getScrollY(), 0, $i42, 0, 0, true) && !hasNestedScrollingParent()) {
                            VelocityTracker $r32 = this.mVelocityTracker;
                            $r32.clear();
                        }
                        int $i5 = getScrollY() - i;
                        if (!dispatchNestedScroll(0, $i5, 0, $i3 - $i5, this.mScrollOffset)) {
                            if ($z0) {
                                EdgeEffectCompat $r10;
                                ensureGlows();
                                i += $i3;
                                float $f1;
                                if (i < 0) {
                                    $f1 = (float) getHeight();
                                    this.mEdgeGlowTop.onPull(((float) $i3) / $f1, MotionEventCompat.getX($r1, $i2) / ((float) getWidth()));
                                    $r10 = this.mEdgeGlowBottom;
                                    if (!$r10.isFinished()) {
                                        $r10 = this.mEdgeGlowBottom;
                                        $r10.onRelease();
                                    }
                                } else if (i > $i42) {
                                    $f1 = (float) getHeight();
                                    float $f12 = $f1;
                                    this.mEdgeGlowBottom.onPull(((float) $i3) / $f1, 1.0f - (MotionEventCompat.getX($r1, $i2) / ((float) getWidth())));
                                    $r10 = this.mEdgeGlowTop;
                                    if (!$r10.isFinished()) {
                                        $r10 = this.mEdgeGlowTop;
                                        $r10.onRelease();
                                    }
                                }
                                if (this.mEdgeGlowTop != null) {
                                    $r10 = this.mEdgeGlowTop;
                                    if ($r10.isFinished()) {
                                        $r10 = this.mEdgeGlowBottom;
                                        if (!$r10.isFinished()) {
                                        }
                                    }
                                    ViewCompat.postInvalidateOnAnimation(this);
                                    break;
                                }
                            }
                        }
                        this.mLastMotionY -= this.mScrollOffset[1];
                        $r2.offsetLocation(0.0f, (float) this.mScrollOffset[1]);
                        this.mNestedYOffset += this.mScrollOffset[1];
                        break;
                    }
                }
                Log.e(TAG, "Invalid pointerId=" + this.mActivePointerId + " in onTouchEvent");
                break;
                break;
            case 3:
                if (this.mIsBeingDragged && getChildCount() > 0) {
                    if (this.mScroller.springBack(getScrollX(), getScrollY(), 0, 0, 0, getScrollRange())) {
                        ViewCompat.postInvalidateOnAnimation(this);
                    }
                }
                this.mActivePointerId = -1;
                endDrag();
                break;
            case 4:
                break;
            case 5:
                $i2 = MotionEventCompat.getActionIndex($r1);
                this.mLastMotionY = (int) MotionEventCompat.getY($r1, $i2);
                this.mActivePointerId = MotionEventCompat.getPointerId($r1, $i2);
                break;
            case 6:
                onSecondaryPointerUp($r1);
                this.mLastMotionY = (int) MotionEventCompat.getY($r1, MotionEventCompat.findPointerIndex($r1, this.mActivePointerId));
                break;
            default:
                break;
        }
        if (this.mVelocityTracker != null) {
            $r32 = this.mVelocityTracker;
            $r32.addMovement($r2);
        }
        $r2.recycle();
        return true;
    }

    private void onSecondaryPointerUp(MotionEvent $r1) throws  {
        int $i0 = ($r1.getAction() & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8;
        if (MotionEventCompat.getPointerId($r1, $i0) == this.mActivePointerId) {
            byte $b3;
            if ($i0 == 0) {
                $b3 = (byte) 1;
            } else {
                $b3 = (byte) 0;
            }
            this.mLastMotionY = (int) MotionEventCompat.getY($r1, $b3);
            this.mActivePointerId = MotionEventCompat.getPointerId($r1, $b3);
            if (this.mVelocityTracker != null) {
                this.mVelocityTracker.clear();
            }
        }
    }

    public boolean onGenericMotionEvent(MotionEvent $r1) throws  {
        if ((MotionEventCompat.getSource($r1) & 2) != 0) {
            switch ($r1.getAction()) {
                case 8:
                    if (!this.mIsBeingDragged) {
                        float $f0 = MotionEventCompat.getAxisValue($r1, 9);
                        if ($f0 != 0.0f) {
                            int $i0 = (int) (getVerticalScrollFactorCompat() * $f0);
                            int $i1 = getScrollRange();
                            int $i3 = getScrollY();
                            $i0 = $i3 - $i0;
                            if ($i0 < 0) {
                                $i0 = 0;
                            } else if ($i0 > $i1) {
                                $i0 = $i1;
                            }
                            if ($i0 != $i3) {
                                super.scrollTo(getScrollX(), $i0);
                                return true;
                            }
                        }
                    }
                    break;
                default:
                    break;
            }
        }
        return false;
    }

    private float getVerticalScrollFactorCompat() throws  {
        if (this.mVerticalScrollFactor == 0.0f) {
            TypedValue $r1 = new TypedValue();
            Context $r2 = getContext();
            if ($r2.getTheme().resolveAttribute(16842829, $r1, true)) {
                this.mVerticalScrollFactor = $r1.getDimension($r2.getResources().getDisplayMetrics());
            } else {
                throw new IllegalStateException("Expected theme to define listPreferredItemHeight.");
            }
        }
        return this.mVerticalScrollFactor;
    }

    protected void onOverScrolled(int $i0, int $i1, boolean clampedX, boolean clampedY) throws  {
        super.scrollTo($i0, $i1);
    }

    boolean overScrollByCompat(int $i0, int $i1, int $i2, int $i3, int $i4, int $i5, int $i6, int $i7, boolean isTouchEvent) throws  {
        int $i8 = ViewCompat.getOverScrollMode(this);
        boolean $z1 = computeHorizontalScrollRange() > computeHorizontalScrollExtent();
        isTouchEvent = computeVerticalScrollRange() > computeVerticalScrollExtent();
        if ($i8 == 0 || ($i8 == 1 && $z1)) {
            $z1 = true;
        } else {
            $z1 = false;
        }
        if ($i8 == 0 || ($i8 == 1 && isTouchEvent)) {
            isTouchEvent = true;
        } else {
            isTouchEvent = false;
        }
        $i0 = $i2 + $i0;
        if (!$z1) {
            $i6 = 0;
        }
        $i1 = $i3 + $i1;
        if (!isTouchEvent) {
            $i7 = 0;
        }
        $i2 = -$i6;
        $i3 = $i6 + $i4;
        $i4 = -$i7;
        $i5 = $i7 + $i5;
        isTouchEvent = false;
        if ($i0 > $i3) {
            $i0 = $i3;
            isTouchEvent = true;
        } else if ($i0 < $i2) {
            $i0 = $i2;
            isTouchEvent = true;
        }
        $z1 = false;
        if ($i1 > $i5) {
            $i1 = $i5;
            $z1 = true;
        } else if ($i1 < $i4) {
            $i1 = $i4;
            $z1 = true;
        }
        if ($z1) {
            this.mScroller.springBack($i0, $i1, 0, 0, 0, getScrollRange());
        }
        onOverScrolled($i0, $i1, isTouchEvent, $z1);
        if (isTouchEvent || $z1) {
            return true;
        }
        return false;
    }

    private int getScrollRange() throws  {
        if (getChildCount() > 0) {
            return Math.max(0, getChildAt(0).getHeight() - ((getHeight() - getPaddingBottom()) - getPaddingTop()));
        }
        return 0;
    }

    private View findFocusableViewInBounds(boolean $z0, int $i0, int $i1) throws  {
        ArrayList $r1 = getFocusables(2);
        View $r2 = null;
        boolean $z1 = false;
        int $i2 = $r1.size();
        for (int $i3 = 0; $i3 < $i2; $i3++) {
            View $r4 = (View) $r1.get($i3);
            int $i4 = $r4.getTop();
            int $i5 = $r4.getBottom();
            if ($i0 < $i5 && $i4 < $i1) {
                boolean $z2;
                if ($i0 >= $i4 || $i5 >= $i1) {
                    $z2 = false;
                } else {
                    $z2 = true;
                }
                if ($r2 == null) {
                    $r2 = $r4;
                    $z1 = $z2;
                } else {
                    boolean $z3 = ($z0 && $i4 < $r2.getTop()) || (!$z0 && $i5 > $r2.getBottom());
                    if ($z1) {
                        if ($z2 && $z3) {
                            $r2 = $r4;
                        }
                    } else if ($z2) {
                        $r2 = $r4;
                        $z1 = true;
                    } else if ($z3) {
                        $r2 = $r4;
                    }
                }
            }
        }
        return $r2;
    }

    public boolean pageScroll(int $i0) throws  {
        boolean $z0;
        if ($i0 == 130) {
            $z0 = true;
        } else {
            $z0 = false;
        }
        int $i1 = getHeight();
        if ($z0) {
            this.mTempRect.top = getScrollY() + $i1;
            int $i2 = getChildCount();
            if ($i2 > 0) {
                View $r2 = getChildAt($i2 - 1);
                if (this.mTempRect.top + $i1 > $r2.getBottom()) {
                    this.mTempRect.top = $r2.getBottom() - $i1;
                }
            }
        } else {
            this.mTempRect.top = getScrollY() - $i1;
            if (this.mTempRect.top < 0) {
                this.mTempRect.top = 0;
            }
        }
        this.mTempRect.bottom = this.mTempRect.top + $i1;
        return scrollAndFocus($i0, this.mTempRect.top, this.mTempRect.bottom);
    }

    public boolean fullScroll(int $i0) throws  {
        boolean $z0;
        if ($i0 == 130) {
            $z0 = true;
        } else {
            $z0 = false;
        }
        int $i1 = getHeight();
        this.mTempRect.top = 0;
        this.mTempRect.bottom = $i1;
        if ($z0) {
            int $i2 = getChildCount();
            if ($i2 > 0) {
                View $r2 = getChildAt($i2 - 1);
                this.mTempRect.bottom = $r2.getBottom() + getPaddingBottom();
                this.mTempRect.top = this.mTempRect.bottom - $i1;
            }
        }
        return scrollAndFocus($i0, this.mTempRect.top, this.mTempRect.bottom);
    }

    private boolean scrollAndFocus(int $i0, int $i1, int $i2) throws  {
        boolean $z1;
        boolean $z0 = true;
        int $i3 = getHeight();
        int $i4 = getScrollY();
        $i3 = $i4 + $i3;
        if ($i0 == 33) {
            $z1 = true;
        } else {
            $z1 = false;
        }
        View $r1 = findFocusableViewInBounds($z1, $i1, $i2);
        View $r2 = $r1;
        if ($r1 == null) {
            $r2 = this;
        }
        if ($i1 < $i4 || $i2 > $i3) {
            if ($z1) {
                $i1 -= $i4;
            } else {
                $i1 = $i2 - $i3;
            }
            doScrollY($i1);
        } else {
            $z0 = false;
        }
        if ($r2 == findFocus()) {
            return $z0;
        }
        $r2.requestFocus($i0);
        return $z0;
    }

    public boolean arrowScroll(int $i0) throws  {
        View $r1 = findFocus();
        View $r2 = $r1;
        if ($r1 == this) {
            $r2 = null;
        }
        $r1 = FocusFinder.getInstance().findNextFocus(this, $r2, $i0);
        int $i2 = getMaxScrollAmount();
        if ($r1 == null || !isWithinDeltaOfScreen($r1, $i2, getHeight())) {
            int $i3 = $i2;
            if ($i0 == 33 && getScrollY() < $i2) {
                $i3 = getScrollY();
            } else if ($i0 == 130 && getChildCount() > 0) {
                int $i4 = getChildAt(0).getBottom();
                int $i1 = (getScrollY() + getHeight()) - getPaddingBottom();
                if ($i4 - $i1 < $i2) {
                    $i3 = $i4 - $i1;
                }
            }
            if ($i3 == 0) {
                return false;
            }
            doScrollY($i0 == 130 ? $i3 : -$i3);
        } else {
            $r1.getDrawingRect(this.mTempRect);
            offsetDescendantRectToMyCoords($r1, this.mTempRect);
            doScrollY(computeScrollDeltaToGetChildRectOnScreen(this.mTempRect));
            $r1.requestFocus($i0);
        }
        if ($r2 != null && $r2.isFocused() && isOffScreen($r2)) {
            $i0 = getDescendantFocusability();
            setDescendantFocusability(131072);
            requestFocus();
            setDescendantFocusability($i0);
        }
        return true;
    }

    private boolean isOffScreen(View $r1) throws  {
        return !isWithinDeltaOfScreen($r1, 0, getHeight());
    }

    private boolean isWithinDeltaOfScreen(View $r1, int $i0, int $i1) throws  {
        $r1.getDrawingRect(this.mTempRect);
        offsetDescendantRectToMyCoords($r1, this.mTempRect);
        return this.mTempRect.bottom + $i0 >= getScrollY() && this.mTempRect.top - $i0 <= getScrollY() + $i1;
    }

    private void doScrollY(int $i0) throws  {
        if ($i0 == 0) {
            return;
        }
        if (this.mSmoothScrollingEnabled) {
            smoothScrollBy(0, $i0);
        } else {
            scrollBy(0, $i0);
        }
    }

    public final void smoothScrollBy(int $i0, int $i2) throws  {
        if (getChildCount() != 0) {
            if (AnimationUtils.currentAnimationTimeMillis() - this.mLastScroll > 250) {
                int $i3 = Math.max(0, getChildAt(0).getHeight() - ((getHeight() - getPaddingBottom()) - getPaddingTop()));
                $i0 = getScrollY();
                this.mScroller.startScroll(getScrollX(), $i0, 0, Math.max(0, Math.min($i0 + $i2, $i3)) - $i0);
                ViewCompat.postInvalidateOnAnimation(this);
            } else {
                if (!this.mScroller.isFinished()) {
                    this.mScroller.abortAnimation();
                }
                scrollBy($i0, $i2);
            }
            this.mLastScroll = AnimationUtils.currentAnimationTimeMillis();
        }
    }

    public final void smoothScrollTo(int $i0, int $i1) throws  {
        smoothScrollBy($i0 - getScrollX(), $i1 - getScrollY());
    }

    public int computeVerticalScrollRange() throws  {
        int $i3 = (getHeight() - getPaddingBottom()) - getPaddingTop();
        if (getChildCount() == 0) {
            return $i3;
        }
        int $i0 = getChildAt(0).getBottom();
        int $i1 = $i0;
        int $i2 = getScrollY();
        $i3 = Math.max(0, $i0 - $i3);
        if ($i2 < 0) {
            $i1 = $i0 - $i2;
        } else if ($i2 > $i3) {
            $i1 = $i0 + ($i2 - $i3);
        }
        return $i1;
    }

    public int computeVerticalScrollOffset() throws  {
        return Math.max(0, super.computeVerticalScrollOffset());
    }

    public int computeVerticalScrollExtent() throws  {
        return super.computeVerticalScrollExtent();
    }

    public int computeHorizontalScrollRange() throws  {
        return super.computeHorizontalScrollRange();
    }

    public int computeHorizontalScrollOffset() throws  {
        return super.computeHorizontalScrollOffset();
    }

    public int computeHorizontalScrollExtent() throws  {
        return super.computeHorizontalScrollExtent();
    }

    protected void measureChild(View $r1, int $i0, int parentHeightMeasureSpec) throws  {
        $r1.measure(getChildMeasureSpec($i0, getPaddingLeft() + getPaddingRight(), $r1.getLayoutParams().width), MeasureSpec.makeMeasureSpec(0, 0));
    }

    protected void measureChildWithMargins(View $r1, int $i0, int $i1, int parentHeightMeasureSpec, int heightUsed) throws  {
        MarginLayoutParams $r3 = (MarginLayoutParams) $r1.getLayoutParams();
        $r1.measure(getChildMeasureSpec($i0, (((getPaddingLeft() + getPaddingRight()) + $r3.leftMargin) + $r3.rightMargin) + $i1, $r3.width), MeasureSpec.makeMeasureSpec($r3.topMargin + $r3.bottomMargin, 0));
    }

    public void computeScroll() throws  {
        boolean $z0 = true;
        if (this.mScroller.computeScrollOffset()) {
            int $i1 = getScrollX();
            int $i2 = getScrollY();
            int $i0 = this.mScroller.getCurrX();
            int $i3 = this.mScroller.getCurrY();
            if ($i1 != $i0 || $i2 != $i3) {
                int $i4 = getScrollRange();
                int $i5 = ViewCompat.getOverScrollMode(this);
                if ($i5 != 0 && ($i5 != 1 || $i4 <= 0)) {
                    $z0 = false;
                }
                overScrollByCompat($i0 - $i1, $i3 - $i2, $i1, $i2, 0, $i4, 0, 0, false);
                if ($z0) {
                    ensureGlows();
                    if ($i3 <= 0 && $i2 > 0) {
                        this.mEdgeGlowTop.onAbsorb((int) this.mScroller.getCurrVelocity());
                    } else if ($i3 >= $i4 && $i2 < $i4) {
                        this.mEdgeGlowBottom.onAbsorb((int) this.mScroller.getCurrVelocity());
                    }
                }
            }
        }
    }

    private void scrollToChild(View $r1) throws  {
        $r1.getDrawingRect(this.mTempRect);
        offsetDescendantRectToMyCoords($r1, this.mTempRect);
        int $i0 = computeScrollDeltaToGetChildRectOnScreen(this.mTempRect);
        if ($i0 != 0) {
            scrollBy(0, $i0);
        }
    }

    private boolean scrollToChildRect(Rect $r1, boolean $z0) throws  {
        boolean $z1;
        int $i0 = computeScrollDeltaToGetChildRectOnScreen($r1);
        if ($i0 != 0) {
            $z1 = true;
        } else {
            $z1 = false;
        }
        if (!$z1) {
            return $z1;
        }
        if ($z0) {
            scrollBy(0, $i0);
            return $z1;
        }
        smoothScrollBy(0, $i0);
        return $z1;
    }

    protected int computeScrollDeltaToGetChildRectOnScreen(Rect $r1) throws  {
        if (getChildCount() == 0) {
            return 0;
        }
        int $i1 = getHeight();
        int $i2 = getScrollY();
        int $i3 = $i2;
        int $i0 = $i2 + $i1;
        int $i4 = getVerticalFadingEdgeLength();
        if ($r1.top > 0) {
            $i3 = $i2 + $i4;
        }
        if ($r1.bottom < getChildAt(0).getHeight()) {
            $i0 -= $i4;
        }
        if ($r1.bottom > $i0 && $r1.top > $i3) {
            if ($r1.height() > $i1) {
                $i3 = 0 + ($r1.top - $i3);
            } else {
                $i3 = 0 + ($r1.bottom - $i0);
            }
            return Math.min($i3, getChildAt(0).getBottom() - $i0);
        } else if ($r1.top >= $i3) {
            return 0;
        } else {
            if ($r1.bottom >= $i0) {
                return 0;
            }
            if ($r1.height() > $i1) {
                $i0 = 0 - ($i0 - $r1.bottom);
            } else {
                $i0 = 0 - ($i3 - $r1.top);
            }
            return Math.max($i0, -getScrollY());
        }
    }

    public void requestChildFocus(View $r1, View $r2) throws  {
        if (this.mIsLayoutDirty) {
            this.mChildToScrollTo = $r2;
        } else {
            scrollToChild($r2);
        }
        super.requestChildFocus($r1, $r2);
    }

    protected boolean onRequestFocusInDescendants(int $i0, Rect $r1) throws  {
        if ($i0 == 2) {
            $i0 = 130;
        } else if ($i0 == 1) {
            $i0 = 33;
        }
        View $r3 = $r1 == null ? FocusFinder.getInstance().findNextFocus(this, null, $i0) : FocusFinder.getInstance().findNextFocusFromRect(this, $r1, $i0);
        if ($r3 == null) {
            return false;
        }
        return !isOffScreen($r3) ? $r3.requestFocus($i0, $r1) : false;
    }

    public boolean requestChildRectangleOnScreen(View $r1, Rect $r2, boolean $z0) throws  {
        $r2.offset($r1.getLeft() - $r1.getScrollX(), $r1.getTop() - $r1.getScrollY());
        return scrollToChildRect($r2, $z0);
    }

    public void requestLayout() throws  {
        this.mIsLayoutDirty = true;
        super.requestLayout();
    }

    protected void onLayout(boolean $z0, int $i0, int $i1, int $i2, int $i3) throws  {
        super.onLayout($z0, $i0, $i1, $i2, $i3);
        this.mIsLayoutDirty = false;
        if (this.mChildToScrollTo != null && isViewDescendantOf(this.mChildToScrollTo, this)) {
            scrollToChild(this.mChildToScrollTo);
        }
        this.mChildToScrollTo = null;
        if (!this.mIsLaidOut) {
            if (this.mSavedState != null) {
                scrollTo(getScrollX(), this.mSavedState.scrollPosition);
                this.mSavedState = null;
            }
            $i0 = Math.max(0, (getChildCount() > 0 ? getChildAt(0).getMeasuredHeight() : 0) - ((($i3 - $i1) - getPaddingBottom()) - getPaddingTop()));
            if (getScrollY() > $i0) {
                scrollTo(getScrollX(), $i0);
            } else if (getScrollY() < 0) {
                scrollTo(getScrollX(), 0);
            }
        }
        scrollTo(getScrollX(), getScrollY());
        this.mIsLaidOut = true;
    }

    public void onAttachedToWindow() throws  {
        this.mIsLaidOut = false;
    }

    protected void onSizeChanged(int $i0, int $i1, int $i2, int $i3) throws  {
        super.onSizeChanged($i0, $i1, $i2, $i3);
        View $r1 = findFocus();
        if ($r1 != null && this != $r1 && isWithinDeltaOfScreen($r1, 0, $i3)) {
            $r1.getDrawingRect(this.mTempRect);
            offsetDescendantRectToMyCoords($r1, this.mTempRect);
            doScrollY(computeScrollDeltaToGetChildRectOnScreen(this.mTempRect));
        }
    }

    private static boolean isViewDescendantOf(View $r0, View $r1) throws  {
        if ($r0 == $r1) {
            return true;
        }
        ViewParent $r2 = $r0.getParent();
        return ($r2 instanceof ViewGroup) && isViewDescendantOf((View) $r2, $r1);
    }

    public void fling(int $i0) throws  {
        if (getChildCount() > 0) {
            int $i1 = (getHeight() - getPaddingBottom()) - getPaddingTop();
            int $i4 = getChildAt(0).getHeight();
            this.mScroller.fling(getScrollX(), getScrollY(), 0, $i0, 0, 0, 0, Math.max(0, $i4 - $i1), 0, $i1 / 2);
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    private void flingWithNestedDispatch(int $i0) throws  {
        int $i1 = getScrollY();
        boolean $z0 = ($i1 > 0 || $i0 > 0) && ($i1 < getScrollRange() || $i0 < 0);
        if (!dispatchNestedPreFling(0.0f, (float) $i0)) {
            dispatchNestedFling(0.0f, (float) $i0, $z0);
            if ($z0) {
                fling($i0);
            }
        }
    }

    private void endDrag() throws  {
        this.mIsBeingDragged = false;
        recycleVelocityTracker();
        stopNestedScroll();
        if (this.mEdgeGlowTop != null) {
            this.mEdgeGlowTop.onRelease();
            this.mEdgeGlowBottom.onRelease();
        }
    }

    public void scrollTo(int $i0, int $i1) throws  {
        if (getChildCount() > 0) {
            View $r1 = getChildAt(0);
            $i0 = clamp($i0, (getWidth() - getPaddingRight()) - getPaddingLeft(), $r1.getWidth());
            $i1 = clamp($i1, (getHeight() - getPaddingBottom()) - getPaddingTop(), $r1.getHeight());
            if ($i0 != getScrollX() || $i1 != getScrollY()) {
                super.scrollTo($i0, $i1);
            }
        }
    }

    private void ensureGlows() throws  {
        if (ViewCompat.getOverScrollMode(this) == 2) {
            this.mEdgeGlowTop = null;
            this.mEdgeGlowBottom = null;
        } else if (this.mEdgeGlowTop == null) {
            Context $r2 = getContext();
            this.mEdgeGlowTop = new EdgeEffectCompat($r2);
            this.mEdgeGlowBottom = new EdgeEffectCompat($r2);
        }
    }

    public void draw(Canvas $r1) throws  {
        super.draw($r1);
        if (this.mEdgeGlowTop != null) {
            int $i1;
            int $i2;
            int $i0 = getScrollY();
            if (!this.mEdgeGlowTop.isFinished()) {
                $i1 = $r1.save();
                $i2 = (getWidth() - getPaddingLeft()) - getPaddingRight();
                $r1.translate((float) getPaddingLeft(), (float) Math.min(0, $i0));
                this.mEdgeGlowTop.setSize($i2, getHeight());
                if (this.mEdgeGlowTop.draw($r1)) {
                    ViewCompat.postInvalidateOnAnimation(this);
                }
                $r1.restoreToCount($i1);
            }
            if (!this.mEdgeGlowBottom.isFinished()) {
                $i1 = $r1.save();
                $i2 = (getWidth() - getPaddingLeft()) - getPaddingRight();
                int $i3 = getHeight();
                $r1.translate((float) ((-$i2) + getPaddingLeft()), (float) (Math.max(getScrollRange(), $i0) + $i3));
                $r1.rotate(180.0f, (float) $i2, 0.0f);
                this.mEdgeGlowBottom.setSize($i2, $i3);
                if (this.mEdgeGlowBottom.draw($r1)) {
                    ViewCompat.postInvalidateOnAnimation(this);
                }
                $r1.restoreToCount($i1);
            }
        }
    }

    private static int clamp(int $i3, int $i0, int $i1) throws  {
        if ($i0 >= $i1 || $i3 < 0) {
            return 0;
        }
        return $i0 + $i3 > $i1 ? $i1 - $i0 : $i3;
    }

    protected void onRestoreInstanceState(Parcelable $r1) throws  {
        if ($r1 instanceof SavedState) {
            SavedState $r2 = (SavedState) $r1;
            super.onRestoreInstanceState($r2.getSuperState());
            this.mSavedState = $r2;
            requestLayout();
            return;
        }
        super.onRestoreInstanceState($r1);
    }

    protected Parcelable onSaveInstanceState() throws  {
        SavedState $r1 = new SavedState(super.onSaveInstanceState());
        $r1.scrollPosition = getScrollY();
        return $r1;
    }
}
