package android.support.v4.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.v4.view.AccessibilityDelegateCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.widget.ViewDragHelper.Callback;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.View.MeasureSpec;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import com.waze.map.CanvasFont;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class SlidingPaneLayout extends ViewGroup {
    private static final int DEFAULT_FADE_COLOR = -858993460;
    private static final int DEFAULT_OVERHANG_SIZE = 32;
    static final SlidingPanelLayoutImpl IMPL;
    private static final int MIN_FLING_VELOCITY = 400;
    private static final String TAG = "SlidingPaneLayout";
    private boolean mCanSlide;
    private int mCoveredFadeColor;
    private final ViewDragHelper mDragHelper;
    private boolean mFirstLayout;
    private float mInitialMotionX;
    private float mInitialMotionY;
    private boolean mIsUnableToDrag;
    private final int mOverhangSize;
    private PanelSlideListener mPanelSlideListener;
    private int mParallaxBy;
    private float mParallaxOffset;
    private final ArrayList<DisableLayerRunnable> mPostedRunnables;
    private boolean mPreservedOpenState;
    private Drawable mShadowDrawableLeft;
    private Drawable mShadowDrawableRight;
    private float mSlideOffset;
    private int mSlideRange;
    private View mSlideableView;
    private int mSliderFadeColor;
    private final Rect mTmpRect;

    class AccessibilityDelegate extends AccessibilityDelegateCompat {
        private final Rect mTmpRect = new Rect();

        AccessibilityDelegate() throws  {
        }

        public void onInitializeAccessibilityNodeInfo(View $r1, AccessibilityNodeInfoCompat $r2) throws  {
            AccessibilityNodeInfoCompat $r3 = AccessibilityNodeInfoCompat.obtain($r2);
            super.onInitializeAccessibilityNodeInfo($r1, $r3);
            copyNodeInfoNoChildren($r2, $r3);
            $r3.recycle();
            $r2.setClassName(SlidingPaneLayout.class.getName());
            $r2.setSource($r1);
            ViewParent $r6 = ViewCompat.getParentForAccessibility($r1);
            if ($r6 instanceof View) {
                $r2.setParent((View) $r6);
            }
            int $i0 = SlidingPaneLayout.this.getChildCount();
            for (int $i1 = 0; $i1 < $i0; $i1++) {
                $r1 = SlidingPaneLayout.this.getChildAt($i1);
                if (!filter($r1) && $r1.getVisibility() == 0) {
                    ViewCompat.setImportantForAccessibility($r1, 1);
                    $r2.addChild($r1);
                }
            }
        }

        public void onInitializeAccessibilityEvent(View $r1, AccessibilityEvent $r2) throws  {
            super.onInitializeAccessibilityEvent($r1, $r2);
            $r2.setClassName(SlidingPaneLayout.class.getName());
        }

        public boolean onRequestSendAccessibilityEvent(ViewGroup $r1, View $r2, AccessibilityEvent $r3) throws  {
            if (filter($r2)) {
                return false;
            }
            return super.onRequestSendAccessibilityEvent($r1, $r2, $r3);
        }

        public boolean filter(View $r1) throws  {
            return SlidingPaneLayout.this.isDimmed($r1);
        }

        private void copyNodeInfoNoChildren(AccessibilityNodeInfoCompat $r1, AccessibilityNodeInfoCompat $r2) throws  {
            Rect $r3 = this.mTmpRect;
            $r2.getBoundsInParent($r3);
            $r1.setBoundsInParent($r3);
            $r2.getBoundsInScreen($r3);
            $r1.setBoundsInScreen($r3);
            $r1.setVisibleToUser($r2.isVisibleToUser());
            $r1.setPackageName($r2.getPackageName());
            $r1.setClassName($r2.getClassName());
            $r1.setContentDescription($r2.getContentDescription());
            $r1.setEnabled($r2.isEnabled());
            $r1.setClickable($r2.isClickable());
            $r1.setFocusable($r2.isFocusable());
            $r1.setFocused($r2.isFocused());
            $r1.setAccessibilityFocused($r2.isAccessibilityFocused());
            $r1.setSelected($r2.isSelected());
            $r1.setLongClickable($r2.isLongClickable());
            $r1.addAction($r2.getActions());
            $r1.setMovementGranularities($r2.getMovementGranularities());
        }
    }

    private class DisableLayerRunnable implements Runnable {
        final View mChildView;

        DisableLayerRunnable(View $r2) throws  {
            this.mChildView = $r2;
        }

        public void run() throws  {
            if (this.mChildView.getParent() == SlidingPaneLayout.this) {
                ViewCompat.setLayerType(this.mChildView, 0, null);
                SlidingPaneLayout.this.invalidateChildRegion(this.mChildView);
            }
            SlidingPaneLayout.this.mPostedRunnables.remove(this);
        }
    }

    private class DragHelperCallback extends Callback {
        private DragHelperCallback() throws  {
        }

        public boolean tryCaptureView(View $r1, int pointerId) throws  {
            return SlidingPaneLayout.this.mIsUnableToDrag ? false : ((LayoutParams) $r1.getLayoutParams()).slideable;
        }

        public void onViewDragStateChanged(int state) throws  {
            if (SlidingPaneLayout.this.mDragHelper.getViewDragState() != 0) {
                return;
            }
            if (SlidingPaneLayout.this.mSlideOffset == 0.0f) {
                SlidingPaneLayout.this.updateObscuredViewsVisibility(SlidingPaneLayout.this.mSlideableView);
                SlidingPaneLayout.this.dispatchOnPanelClosed(SlidingPaneLayout.this.mSlideableView);
                SlidingPaneLayout.this.mPreservedOpenState = false;
                return;
            }
            SlidingPaneLayout.this.dispatchOnPanelOpened(SlidingPaneLayout.this.mSlideableView);
            SlidingPaneLayout.this.mPreservedOpenState = true;
        }

        public void onViewCaptured(View capturedChild, int activePointerId) throws  {
            SlidingPaneLayout.this.setAllChildrenVisible();
        }

        public void onViewPositionChanged(View changedView, int $i0, int top, int dx, int dy) throws  {
            SlidingPaneLayout.this.onPanelDragged($i0);
            SlidingPaneLayout.this.invalidate();
        }

        public void onViewReleased(View $r1, float $f0, float yvel) throws  {
            int $i0;
            LayoutParams $r3 = (LayoutParams) $r1.getLayoutParams();
            if (SlidingPaneLayout.this.isLayoutRtlSupport()) {
                $i0 = SlidingPaneLayout.this.getPaddingRight() + $r3.rightMargin;
                if ($f0 < 0.0f || ($f0 == 0.0f && SlidingPaneLayout.this.mSlideOffset > CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR)) {
                    $i0 += SlidingPaneLayout.this.mSlideRange;
                }
                $i0 = (SlidingPaneLayout.this.getWidth() - $i0) - SlidingPaneLayout.this.mSlideableView.getWidth();
            } else {
                $i0 = SlidingPaneLayout.this.getPaddingLeft() + $r3.leftMargin;
                if ($f0 > 0.0f || ($f0 == 0.0f && SlidingPaneLayout.this.mSlideOffset > CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR)) {
                    $i0 += SlidingPaneLayout.this.mSlideRange;
                }
            }
            SlidingPaneLayout.this.mDragHelper.settleCapturedViewAt($i0, $r1.getTop());
            SlidingPaneLayout.this.invalidate();
        }

        public int getViewHorizontalDragRange(View child) throws  {
            return SlidingPaneLayout.this.mSlideRange;
        }

        public int clampViewPositionHorizontal(View child, int $i0, int dx) throws  {
            LayoutParams $r4 = (LayoutParams) SlidingPaneLayout.this.mSlideableView.getLayoutParams();
            if (SlidingPaneLayout.this.isLayoutRtlSupport()) {
                dx = SlidingPaneLayout.this.getWidth() - ((SlidingPaneLayout.this.getPaddingRight() + $r4.rightMargin) + SlidingPaneLayout.this.mSlideableView.getWidth());
                return Math.max(Math.min($i0, dx), dx - SlidingPaneLayout.this.mSlideRange);
            }
            dx = SlidingPaneLayout.this.getPaddingLeft() + $r4.leftMargin;
            return Math.min(Math.max($i0, dx), dx + SlidingPaneLayout.this.mSlideRange);
        }

        public int clampViewPositionVertical(View $r1, int top, int dy) throws  {
            return $r1.getTop();
        }

        public void onEdgeDragStarted(int edgeFlags, int $i1) throws  {
            SlidingPaneLayout.this.mDragHelper.captureChildView(SlidingPaneLayout.this.mSlideableView, $i1);
        }
    }

    public static class LayoutParams extends MarginLayoutParams {
        private static final int[] ATTRS = new int[]{16843137};
        Paint dimPaint;
        boolean dimWhenOffset;
        boolean slideable;
        public float weight = 0.0f;

        public LayoutParams() throws  {
            super(-1, -1);
        }

        public LayoutParams(int $i0, int $i1) throws  {
            super($i0, $i1);
        }

        public LayoutParams(android.view.ViewGroup.LayoutParams $r1) throws  {
            super($r1);
        }

        public LayoutParams(MarginLayoutParams $r1) throws  {
            super($r1);
        }

        public LayoutParams(LayoutParams $r1) throws  {
            super($r1);
            this.weight = $r1.weight;
        }

        public LayoutParams(Context $r1, AttributeSet $r2) throws  {
            super($r1, $r2);
            TypedArray $r4 = $r1.obtainStyledAttributes($r2, ATTRS);
            this.weight = $r4.getFloat(0, 0.0f);
            $r4.recycle();
        }
    }

    public interface PanelSlideListener {
        void onPanelClosed(View view) throws ;

        void onPanelOpened(View view) throws ;

        void onPanelSlide(View view, float f) throws ;
    }

    static class SavedState extends BaseSavedState {
        public static final Creator<SavedState> CREATOR = new C01541();
        boolean isOpen;

        static class C01541 implements Creator<SavedState> {
            C01541() throws  {
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

        private SavedState(Parcel $r1) throws  {
            super($r1);
            this.isOpen = $r1.readInt() != 0;
        }

        public void writeToParcel(Parcel $r1, int $i0) throws  {
            super.writeToParcel($r1, $i0);
            $r1.writeInt(this.isOpen ? (byte) 1 : (byte) 0);
        }
    }

    public static class SimplePanelSlideListener implements PanelSlideListener {
        public void onPanelSlide(View panel, float slideOffset) throws  {
        }

        public void onPanelOpened(View panel) throws  {
        }

        public void onPanelClosed(View panel) throws  {
        }
    }

    interface SlidingPanelLayoutImpl {
        void invalidateChildRegion(SlidingPaneLayout slidingPaneLayout, View view) throws ;
    }

    static class SlidingPanelLayoutImplBase implements SlidingPanelLayoutImpl {
        SlidingPanelLayoutImplBase() throws  {
        }

        public void invalidateChildRegion(SlidingPaneLayout $r1, View $r2) throws  {
            ViewCompat.postInvalidateOnAnimation($r1, $r2.getLeft(), $r2.getTop(), $r2.getRight(), $r2.getBottom());
        }
    }

    static class SlidingPanelLayoutImplJB extends SlidingPanelLayoutImplBase {
        private Method mGetDisplayList;
        private Field mRecreateDisplayList;

        SlidingPanelLayoutImplJB() throws  {
            try {
                this.mGetDisplayList = View.class.getDeclaredMethod("getDisplayList", null);
            } catch (NoSuchMethodException $r4) {
                Log.e(SlidingPaneLayout.TAG, "Couldn't fetch getDisplayList method; dimming won't work right.", $r4);
            }
            try {
                this.mRecreateDisplayList = View.class.getDeclaredField("mRecreateDisplayList");
                this.mRecreateDisplayList.setAccessible(true);
            } catch (NoSuchFieldException $r5) {
                Log.e(SlidingPaneLayout.TAG, "Couldn't fetch mRecreateDisplayList field; dimming will be slow.", $r5);
            }
        }

        public void invalidateChildRegion(SlidingPaneLayout $r1, View $r2) throws  {
            if (this.mGetDisplayList == null || this.mRecreateDisplayList == null) {
                $r2.invalidate();
                return;
            }
            try {
                this.mRecreateDisplayList.setBoolean($r2, true);
                this.mGetDisplayList.invoke($r2, null);
            } catch (Exception $r3) {
                Log.e(SlidingPaneLayout.TAG, "Error refreshing display list state", $r3);
            }
            super.invalidateChildRegion($r1, $r2);
        }
    }

    static class SlidingPanelLayoutImplJBMR1 extends SlidingPanelLayoutImplBase {
        SlidingPanelLayoutImplJBMR1() throws  {
        }

        public void invalidateChildRegion(SlidingPaneLayout parent, View $r2) throws  {
            ViewCompat.setLayerPaint($r2, ((LayoutParams) $r2.getLayoutParams()).dimPaint);
        }
    }

    static {
        int $i0 = VERSION.SDK_INT;
        if ($i0 >= 17) {
            IMPL = new SlidingPanelLayoutImplJBMR1();
        } else if ($i0 >= 16) {
            IMPL = new SlidingPanelLayoutImplJB();
        } else {
            IMPL = new SlidingPanelLayoutImplBase();
        }
    }

    public SlidingPaneLayout(Context $r1) throws  {
        this($r1, null);
    }

    public SlidingPaneLayout(Context $r1, AttributeSet $r2) throws  {
        this($r1, $r2, 0);
    }

    public SlidingPaneLayout(Context $r1, AttributeSet $r2, int $i0) throws  {
        super($r1, $r2, $i0);
        this.mSliderFadeColor = DEFAULT_FADE_COLOR;
        this.mFirstLayout = true;
        this.mTmpRect = new Rect();
        this.mPostedRunnables = new ArrayList();
        float $f0 = $r1.getResources().getDisplayMetrics().density;
        this.mOverhangSize = (int) ((32.0f * $f0) + CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR);
        ViewConfiguration.get($r1);
        setWillNotDraw(false);
        ViewCompat.setAccessibilityDelegate(this, new AccessibilityDelegate());
        ViewCompat.setImportantForAccessibility(this, 1);
        this.mDragHelper = ViewDragHelper.create(this, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR, new DragHelperCallback());
        this.mDragHelper.setMinVelocity(400.0f * $f0);
    }

    public void setParallaxDistance(int $i0) throws  {
        this.mParallaxBy = $i0;
        requestLayout();
    }

    public int getParallaxDistance() throws  {
        return this.mParallaxBy;
    }

    public void setSliderFadeColor(@ColorInt int $i0) throws  {
        this.mSliderFadeColor = $i0;
    }

    @ColorInt
    public int getSliderFadeColor() throws  {
        return this.mSliderFadeColor;
    }

    public void setCoveredFadeColor(@ColorInt int $i0) throws  {
        this.mCoveredFadeColor = $i0;
    }

    @ColorInt
    public int getCoveredFadeColor() throws  {
        return this.mCoveredFadeColor;
    }

    public void setPanelSlideListener(PanelSlideListener $r1) throws  {
        this.mPanelSlideListener = $r1;
    }

    void dispatchOnPanelSlide(View $r1) throws  {
        if (this.mPanelSlideListener != null) {
            this.mPanelSlideListener.onPanelSlide($r1, this.mSlideOffset);
        }
    }

    void dispatchOnPanelOpened(View $r1) throws  {
        if (this.mPanelSlideListener != null) {
            this.mPanelSlideListener.onPanelOpened($r1);
        }
        sendAccessibilityEvent(32);
    }

    void dispatchOnPanelClosed(View $r1) throws  {
        if (this.mPanelSlideListener != null) {
            this.mPanelSlideListener.onPanelClosed($r1);
        }
        sendAccessibilityEvent(32);
    }

    void updateObscuredViewsVisibility(View $r1) throws  {
        int $i2;
        int $i4;
        boolean $z0 = isLayoutRtlSupport();
        int $i1 = $z0 ? getWidth() - getPaddingRight() : getPaddingLeft();
        if ($z0) {
            $i2 = getPaddingLeft();
        } else {
            $i2 = getWidth() - getPaddingRight();
        }
        int $i3 = getPaddingTop();
        int $i0 = getHeight() - getPaddingBottom();
        int $i7;
        int $i6;
        int $i5;
        if ($r1 == null || !viewIsOpaque($r1)) {
            $i7 = 0;
            $i6 = 0;
            $i5 = 0;
            $i4 = 0;
        } else {
            $i4 = $r1.getLeft();
            $i5 = $r1.getRight();
            $i6 = $r1.getTop();
            $i7 = $r1.getBottom();
        }
        int $i8 = 0;
        int $i9 = getChildCount();
        while ($i8 < $i9) {
            View $r2 = getChildAt($i8);
            if ($r2 != $r1) {
                int $i10;
                int $i12;
                int i;
                if ($z0) {
                    $i10 = $i2;
                } else {
                    $i10 = $i1;
                }
                $i10 = Math.max($i10, $r2.getLeft());
                int $i11 = Math.max($i3, $r2.getTop());
                if ($z0) {
                    $i12 = $i1;
                } else {
                    $i12 = $i2;
                }
                $i12 = Math.min($i12, $r2.getRight());
                int $i13 = Math.min($i0, $r2.getBottom());
                if ($i10 < $i4 || $i11 < $i6 || $i12 > $i5 || $i13 > $i7) {
                    i = 0;
                } else {
                    i = 4;
                }
                $r2.setVisibility(i);
                $i8++;
            } else {
                return;
            }
        }
    }

    void setAllChildrenVisible() throws  {
        int $i1 = getChildCount();
        for (int $i0 = 0; $i0 < $i1; $i0++) {
            View $r1 = getChildAt($i0);
            if ($r1.getVisibility() == 4) {
                $r1.setVisibility(0);
            }
        }
    }

    private static boolean viewIsOpaque(View $r0) throws  {
        if (ViewCompat.isOpaque($r0)) {
            return true;
        }
        if (VERSION.SDK_INT >= 18) {
            return false;
        }
        Drawable $r1 = $r0.getBackground();
        if ($r1 != null) {
            return $r1.getOpacity() == -1;
        } else {
            return false;
        }
    }

    protected void onAttachedToWindow() throws  {
        super.onAttachedToWindow();
        this.mFirstLayout = true;
    }

    protected void onDetachedFromWindow() throws  {
        super.onDetachedFromWindow();
        this.mFirstLayout = true;
        int $i1 = this.mPostedRunnables.size();
        for (int $i0 = 0; $i0 < $i1; $i0++) {
            ((DisableLayerRunnable) this.mPostedRunnables.get($i0)).run();
        }
        this.mPostedRunnables.clear();
    }

    protected void onMeasure(int $i0, int $i1) throws  {
        int $i8;
        boolean z;
        int $i5 = MeasureSpec.getMode($i0);
        $i0 = MeasureSpec.getSize($i0);
        int $i6 = MeasureSpec.getMode($i1);
        int $i3 = $i6;
        int $i4 = MeasureSpec.getSize($i1);
        if ($i5 != 1073741824) {
            if (!isInEditMode()) {
                throw new IllegalStateException("Width must have an exact value or MATCH_PARENT");
            } else if ($i5 != Integer.MIN_VALUE && $i5 == 0) {
                $i0 = 300;
            }
        } else if ($i6 == 0) {
            if (!isInEditMode()) {
                throw new IllegalStateException("Height must not be UNSPECIFIED");
            } else if ($i6 == 0) {
                $i3 = Integer.MIN_VALUE;
                $i4 = 300;
            }
        }
        $i1 = 0;
        $i5 = -1;
        switch ($i3) {
            case Integer.MIN_VALUE:
                $i5 = ($i4 - getPaddingTop()) - getPaddingBottom();
                break;
            case 1073741824:
                $i5 = ($i4 - getPaddingTop()) - getPaddingBottom();
                $i1 = $i5;
                break;
            default:
                break;
        }
        float $f0 = 0.0f;
        boolean $z0 = false;
        $i4 = ($i0 - getPaddingLeft()) - getPaddingRight();
        $i6 = $i4;
        int $i7 = getChildCount();
        if ($i7 > 2) {
            Log.e(TAG, "onMeasure: More than two child views are not supported.");
        }
        this.mSlideableView = null;
        for ($i8 = 0; $i8 < $i7; $i8++) {
            int $i9;
            int $i11;
            View $r1 = getChildAt($i8);
            LayoutParams $r3 = (LayoutParams) $r1.getLayoutParams();
            if ($r1.getVisibility() == 8) {
                $r3.dimWhenOffset = false;
            } else {
                if ($r3.weight > 0.0f) {
                    float $f1 = $r3.weight;
                    $f0 += $f1;
                    if ($r3.width != 0) {
                    }
                }
                $i9 = $r3.leftMargin + $r3.rightMargin;
                if ($r3.width == -2) {
                    $i9 = MeasureSpec.makeMeasureSpec($i4 - $i9, Integer.MIN_VALUE);
                } else if ($r3.width == -1) {
                    $i9 = MeasureSpec.makeMeasureSpec($i4 - $i9, 1073741824);
                } else {
                    $i9 = MeasureSpec.makeMeasureSpec($r3.width, 1073741824);
                }
                if ($r3.height == -2) {
                    $i11 = MeasureSpec.makeMeasureSpec($i5, Integer.MIN_VALUE);
                } else if ($r3.height == -1) {
                    $i11 = MeasureSpec.makeMeasureSpec($i5, 1073741824);
                } else {
                    $i11 = MeasureSpec.makeMeasureSpec($r3.height, 1073741824);
                }
                $r1.measure($i9, $i11);
                $i9 = $r1.getMeasuredWidth();
                $i11 = $r1.getMeasuredHeight();
                if ($i3 == Integer.MIN_VALUE && $i11 > $i1) {
                    $i1 = Math.min($i11, $i5);
                }
                $i6 -= $i9;
                if ($i6 < 0) {
                    z = true;
                } else {
                    z = false;
                }
                $r3.slideable = z;
                $z0 |= z;
                if ($r3.slideable) {
                    this.mSlideableView = $r1;
                }
            }
        }
        if ($z0 || $f0 > 0.0f) {
            $i3 = $i4 - this.mOverhangSize;
            for ($i8 = 0; $i8 < $i7; $i8++) {
                $r1 = getChildAt($i8);
                if ($r1.getVisibility() != 8) {
                    $r3 = (LayoutParams) $r1.getLayoutParams();
                    if ($r1.getVisibility() != 8) {
                        if ($r3.width != 0 || $r3.weight <= 0.0f) {
                            z = false;
                        } else {
                            z = true;
                        }
                        if (z) {
                            $i9 = 0;
                        } else {
                            $i9 = $r1.getMeasuredWidth();
                        }
                        if ($z0) {
                            View $r5 = this.mSlideableView;
                            if ($r1 != $r5) {
                                if ($r3.width < 0 && ($i9 > $i3 || $r3.weight > 0.0f)) {
                                    if (!z) {
                                        $i9 = MeasureSpec.makeMeasureSpec($r1.getMeasuredHeight(), 1073741824);
                                    } else if ($r3.height == -2) {
                                        $i9 = MeasureSpec.makeMeasureSpec($i5, Integer.MIN_VALUE);
                                    } else if ($r3.height == -1) {
                                        $i9 = MeasureSpec.makeMeasureSpec($i5, 1073741824);
                                    } else {
                                        $i9 = MeasureSpec.makeMeasureSpec($r3.height, 1073741824);
                                    }
                                    $r1.measure(MeasureSpec.makeMeasureSpec($i3, 1073741824), $i9);
                                }
                            }
                        }
                        if ($r3.weight > 0.0f) {
                            if ($r3.width != 0) {
                                $i11 = MeasureSpec.makeMeasureSpec($r1.getMeasuredHeight(), 1073741824);
                            } else if ($r3.height == -2) {
                                $i11 = MeasureSpec.makeMeasureSpec($i5, Integer.MIN_VALUE);
                            } else if ($r3.height == -1) {
                                $i11 = MeasureSpec.makeMeasureSpec($i5, 1073741824);
                            } else {
                                $i11 = MeasureSpec.makeMeasureSpec($r3.height, 1073741824);
                            }
                            int $i2;
                            if ($z0) {
                                $i2 = $i4 - ($r3.leftMargin + $r3.rightMargin);
                                int $i12 = MeasureSpec.makeMeasureSpec($i2, 1073741824);
                                if ($i9 != $i2) {
                                    $r1.measure($i12, $i11);
                                }
                            } else {
                                $i2 = Math.max(0, $i6);
                                $f1 = $r3.weight * ((float) $i2);
                                float f = $f1;
                                int $i22 = $f1 / $f0;
                                int i = $i22;
                                $r1.measure(MeasureSpec.makeMeasureSpec($i9 + ((int) $i22), 1073741824), $i11);
                            }
                        }
                    }
                }
            }
        }
        setMeasuredDimension($i0, (getPaddingTop() + $i1) + getPaddingBottom());
        this.mCanSlide = $z0;
        ViewDragHelper $r6 = this.mDragHelper;
        if ($r6.getViewDragState() != 0 && !$z0) {
            $r6 = this.mDragHelper;
            $r6.abort();
        }
    }

    protected void onLayout(boolean changed, int $i0, int t, int $i2, int b) throws  {
        float $f0;
        changed = isLayoutRtlSupport();
        if (changed) {
            this.mDragHelper.setEdgeTrackingEnabled(2);
        } else {
            this.mDragHelper.setEdgeTrackingEnabled(1);
        }
        $i0 = $i2 - $i0;
        int $i7 = changed ? getPaddingRight() : getPaddingLeft();
        $i2 = changed ? getPaddingLeft() : getPaddingRight();
        b = getPaddingTop();
        t = getChildCount();
        int $i8 = $i7;
        if (this.mFirstLayout) {
            if (this.mCanSlide && this.mPreservedOpenState) {
                $f0 = 1.0f;
            } else {
                $f0 = 0.0f;
            }
            this.mSlideOffset = $f0;
        }
        for (int $i9 = 0; $i9 < t; $i9++) {
            View $r2 = getChildAt($i9);
            if ($r2.getVisibility() != 8) {
                int $i5;
                LayoutParams $r4 = (LayoutParams) $r2.getLayoutParams();
                int $i4 = $r2.getMeasuredWidth();
                int $i10 = 0;
                float $f1;
                if ($r4.slideable) {
                    int $i6 = ($i0 - $i2) - this.mOverhangSize;
                    int i = $i6;
                    $i6 = Math.min($i7, $i6) - $i8;
                    i = $i6;
                    $i6 -= $r4.leftMargin + $r4.rightMargin;
                    i = $i6;
                    this.mSlideRange = $i6;
                    if (changed) {
                        $i5 = $r4.rightMargin;
                    } else {
                        $i5 = $r4.leftMargin;
                    }
                    $r4.dimWhenOffset = (($i8 + $i5) + i) + ($i4 / 2) > $i0 - $i2;
                    $f0 = (float) i;
                    $f1 = this.mSlideOffset;
                    i = (int) ($f0 * $f1);
                    $i8 += i + $i5;
                    $f0 = (float) i;
                    $i6 = this.mSlideRange;
                    $i5 = $i6;
                    $f1 = (float) $i6;
                    this.mSlideOffset = $f0 / $f1;
                } else if (!this.mCanSlide || this.mParallaxBy == 0) {
                    $i8 = $i7;
                } else {
                    $f1 = (float) this.mParallaxBy;
                    $i10 = (int) ((1.0f - this.mSlideOffset) * $f1);
                    $i8 = $i7;
                }
                if (changed) {
                    $i10 = ($i0 - $i8) + $i10;
                    $i5 = $i10 - $i4;
                } else {
                    $i5 = $i8 - $i10;
                    $i10 = $i5 + $i4;
                }
                $r2.layout($i5, b, $i10, b + $r2.getMeasuredHeight());
                $i7 += $r2.getWidth();
            }
        }
        if (this.mFirstLayout) {
            if (this.mCanSlide) {
                if (this.mParallaxBy != 0) {
                    parallaxOtherViews(this.mSlideOffset);
                }
                if (((LayoutParams) this.mSlideableView.getLayoutParams()).dimWhenOffset) {
                    dimChildView(this.mSlideableView, this.mSlideOffset, this.mSliderFadeColor);
                }
            } else {
                for ($i0 = 0; $i0 < t; $i0++) {
                    dimChildView(getChildAt($i0), 0.0f, this.mSliderFadeColor);
                }
            }
            updateObscuredViewsVisibility(this.mSlideableView);
        }
        this.mFirstLayout = false;
    }

    protected void onSizeChanged(int $i0, int $i1, int $i2, int $i3) throws  {
        super.onSizeChanged($i0, $i1, $i2, $i3);
        if ($i0 != $i2) {
            this.mFirstLayout = true;
        }
    }

    public void requestChildFocus(View $r1, View $r2) throws  {
        super.requestChildFocus($r1, $r2);
        if (!isInTouchMode() && !this.mCanSlide) {
            this.mPreservedOpenState = $r1 == this.mSlideableView;
        }
    }

    public boolean onInterceptTouchEvent(MotionEvent $r1) throws  {
        int $i0 = MotionEventCompat.getActionMasked($r1);
        if (!this.mCanSlide && $i0 == 0 && getChildCount() > 1) {
            View $r2 = getChildAt(1);
            if ($r2 != null) {
                this.mPreservedOpenState = !this.mDragHelper.isViewUnder($r2, (int) $r1.getX(), (int) $r1.getY());
            }
        }
        if (!this.mCanSlide || (this.mIsUnableToDrag && $i0 != 0)) {
            this.mDragHelper.cancel();
            return super.onInterceptTouchEvent($r1);
        } else if ($i0 == 3 || $i0 == 1) {
            this.mDragHelper.cancel();
            return false;
        } else {
            boolean $z0 = false;
            float $f0;
            float $f1;
            switch ($i0) {
                case 0:
                    this.mIsUnableToDrag = false;
                    $f0 = $r1.getX();
                    $f1 = $r1.getY();
                    this.mInitialMotionX = $f0;
                    this.mInitialMotionY = $f1;
                    if (this.mDragHelper.isViewUnder(this.mSlideableView, (int) $f0, (int) $f1) && isDimmed(this.mSlideableView)) {
                        $z0 = true;
                        break;
                    }
                case 1:
                    break;
                case 2:
                    $f1 = $r1.getX();
                    $f0 = $r1.getY();
                    $f1 = Math.abs($f1 - this.mInitialMotionX);
                    $f0 = Math.abs($f0 - this.mInitialMotionY);
                    if ($f1 > ((float) this.mDragHelper.getTouchSlop()) && $f0 > $f1) {
                        this.mDragHelper.cancel();
                        this.mIsUnableToDrag = true;
                        return false;
                    }
                default:
                    break;
            }
            if (this.mDragHelper.shouldInterceptTouchEvent($r1) || $z0) {
                return true;
            }
            return false;
        }
    }

    public boolean onTouchEvent(MotionEvent $r1) throws  {
        if (!this.mCanSlide) {
            return super.onTouchEvent($r1);
        }
        this.mDragHelper.processTouchEvent($r1);
        float $f2;
        float $f3;
        switch ($r1.getAction() & 255) {
            case 0:
                $f2 = $r1.getX();
                $f3 = $r1.getY();
                this.mInitialMotionX = $f2;
                this.mInitialMotionY = $f3;
                return true;
            case 1:
                if (!isDimmed(this.mSlideableView)) {
                    return true;
                }
                $f2 = $r1.getX();
                $f3 = $r1.getY();
                float $f0 = $f2 - this.mInitialMotionX;
                float $f1 = $f3 - this.mInitialMotionY;
                int $i0 = this.mDragHelper.getTouchSlop();
                if (($f0 * $f0) + ($f1 * $f1) >= ((float) ($i0 * $i0))) {
                    return true;
                }
                if (!this.mDragHelper.isViewUnder(this.mSlideableView, (int) $f2, (int) $f3)) {
                    return true;
                }
                closePane(this.mSlideableView, 0);
                return true;
            default:
                return true;
        }
    }

    private boolean closePane(View pane, int $i0) throws  {
        if (!this.mFirstLayout && !smoothSlideTo(0.0f, $i0)) {
            return false;
        }
        this.mPreservedOpenState = false;
        return true;
    }

    private boolean openPane(View pane, int $i0) throws  {
        if (!this.mFirstLayout && !smoothSlideTo(1.0f, $i0)) {
            return false;
        }
        this.mPreservedOpenState = true;
        return true;
    }

    @Deprecated
    public void smoothSlideOpen() throws  {
        openPane();
    }

    public boolean openPane() throws  {
        return openPane(this.mSlideableView, 0);
    }

    @Deprecated
    public void smoothSlideClosed() throws  {
        closePane();
    }

    public boolean closePane() throws  {
        return closePane(this.mSlideableView, 0);
    }

    public boolean isOpen() throws  {
        return !this.mCanSlide || this.mSlideOffset == 1.0f;
    }

    @Deprecated
    public boolean canSlide() throws  {
        return this.mCanSlide;
    }

    public boolean isSlideable() throws  {
        return this.mCanSlide;
    }

    private void onPanelDragged(int $i0) throws  {
        if (this.mSlideableView == null) {
            this.mSlideOffset = 0.0f;
            return;
        }
        int $i2;
        boolean $z0 = isLayoutRtlSupport();
        LayoutParams $r3 = (LayoutParams) this.mSlideableView.getLayoutParams();
        int $i1 = this.mSlideableView.getWidth();
        if ($z0) {
            $i0 = (getWidth() - $i0) - $i1;
        }
        $i1 = $z0 ? getPaddingRight() : getPaddingLeft();
        if ($z0) {
            $i2 = $r3.rightMargin;
        } else {
            $i2 = $r3.leftMargin;
        }
        this.mSlideOffset = ((float) ($i0 - ($i1 + $i2))) / ((float) this.mSlideRange);
        if (this.mParallaxBy != 0) {
            parallaxOtherViews(this.mSlideOffset);
        }
        if ($r3.dimWhenOffset) {
            dimChildView(this.mSlideableView, this.mSlideOffset, this.mSliderFadeColor);
        }
        dispatchOnPanelSlide(this.mSlideableView);
    }

    private void dimChildView(View $r1, float $f0, int $i0) throws  {
        LayoutParams $r5 = (LayoutParams) $r1.getLayoutParams();
        if ($f0 > 0.0f && $i0 != 0) {
            $i0 = (((int) (((float) ((-16777216 & $i0) >>> 24)) * $f0)) << 24) | (ViewCompat.MEASURED_SIZE_MASK & $i0);
            if ($r5.dimPaint == null) {
                $r5.dimPaint = new Paint();
            }
            $r5.dimPaint.setColorFilter(new PorterDuffColorFilter($i0, Mode.SRC_OVER));
            if (ViewCompat.getLayerType($r1) != 2) {
                ViewCompat.setLayerType($r1, 2, $r5.dimPaint);
            }
            invalidateChildRegion($r1);
        } else if (ViewCompat.getLayerType($r1) != 0) {
            if ($r5.dimPaint != null) {
                $r5.dimPaint.setColorFilter(null);
            }
            DisableLayerRunnable $r2 = new DisableLayerRunnable($r1);
            this.mPostedRunnables.add($r2);
            ViewCompat.postOnAnimation(this, $r2);
        }
    }

    protected boolean drawChild(Canvas $r1, View $r2, long $l0) throws  {
        boolean $z0;
        LayoutParams $r4 = (LayoutParams) $r2.getLayoutParams();
        int $i1 = $r1.save(2);
        if (!(!this.mCanSlide || $r4.slideable || this.mSlideableView == null)) {
            $r1.getClipBounds(this.mTmpRect);
            if (isLayoutRtlSupport()) {
                this.mTmpRect.left = Math.max(this.mTmpRect.left, this.mSlideableView.getRight());
            } else {
                this.mTmpRect.right = Math.min(this.mTmpRect.right, this.mSlideableView.getLeft());
            }
            $r1.clipRect(this.mTmpRect);
        }
        if (VERSION.SDK_INT >= 11) {
            $z0 = super.drawChild($r1, $r2, $l0);
        } else if (!$r4.dimWhenOffset || this.mSlideOffset <= 0.0f) {
            if ($r2.isDrawingCacheEnabled()) {
                $r2.setDrawingCacheEnabled(false);
            }
            $z0 = super.drawChild($r1, $r2, $l0);
        } else {
            if (!$r2.isDrawingCacheEnabled()) {
                $r2.setDrawingCacheEnabled(true);
            }
            Bitmap $r8 = $r2.getDrawingCache();
            if ($r8 != null) {
                $r1.drawBitmap($r8, (float) $r2.getLeft(), (float) $r2.getTop(), $r4.dimPaint);
                $z0 = false;
            } else {
                Log.e(TAG, "drawChild: child view " + $r2 + " returned null drawing cache");
                $z0 = super.drawChild($r1, $r2, $l0);
            }
        }
        $r1.restoreToCount($i1);
        return $z0;
    }

    private void invalidateChildRegion(View $r1) throws  {
        IMPL.invalidateChildRegion(this, $r1);
    }

    boolean smoothSlideTo(float $f0, int velocity) throws  {
        if (!this.mCanSlide) {
            return false;
        }
        LayoutParams $r3 = (LayoutParams) this.mSlideableView.getLayoutParams();
        if (isLayoutRtlSupport()) {
            velocity = (int) (((float) getWidth()) - ((((float) (getPaddingRight() + $r3.rightMargin)) + (((float) this.mSlideRange) * $f0)) + ((float) this.mSlideableView.getWidth())));
        } else {
            velocity = (int) (((float) (getPaddingLeft() + $r3.leftMargin)) + (((float) this.mSlideRange) * $f0));
        }
        if (!this.mDragHelper.smoothSlideViewTo(this.mSlideableView, velocity, this.mSlideableView.getTop())) {
            return false;
        }
        setAllChildrenVisible();
        ViewCompat.postInvalidateOnAnimation(this);
        return true;
    }

    public void computeScroll() throws  {
        if (!this.mDragHelper.continueSettling(true)) {
            return;
        }
        if (this.mCanSlide) {
            ViewCompat.postInvalidateOnAnimation(this);
        } else {
            this.mDragHelper.abort();
        }
    }

    @Deprecated
    public void setShadowDrawable(@Deprecated Drawable $r1) throws  {
        setShadowDrawableLeft($r1);
    }

    public void setShadowDrawableLeft(Drawable $r1) throws  {
        this.mShadowDrawableLeft = $r1;
    }

    public void setShadowDrawableRight(Drawable $r1) throws  {
        this.mShadowDrawableRight = $r1;
    }

    @Deprecated
    public void setShadowResource(@Deprecated @DrawableRes int $i0) throws  {
        setShadowDrawable(getResources().getDrawable($i0));
    }

    public void setShadowResourceLeft(int $i0) throws  {
        setShadowDrawableLeft(getResources().getDrawable($i0));
    }

    public void setShadowResourceRight(int $i0) throws  {
        setShadowDrawableRight(getResources().getDrawable($i0));
    }

    public void draw(Canvas $r1) throws  {
        Drawable $r2;
        super.draw($r1);
        if (isLayoutRtlSupport()) {
            $r2 = this.mShadowDrawableRight;
        } else {
            $r2 = this.mShadowDrawableLeft;
        }
        View $r3 = getChildCount() > 1 ? getChildAt(1) : null;
        if ($r3 != null && $r2 != null) {
            int $i3;
            int $i4;
            int $i0 = $r3.getTop();
            int $i1 = $r3.getBottom();
            int $i2 = $r2.getIntrinsicWidth();
            if (isLayoutRtlSupport()) {
                $i3 = $r3.getRight();
                $i4 = $i3;
                $i3 += $i2;
            } else {
                $i4 = $r3.getLeft();
                $i3 = $i4;
                $i4 -= $i2;
            }
            $r2.setBounds($i4, $i0, $i3, $i1);
            $r2.draw($r1);
        }
    }

    private void parallaxOtherViews(float $f0) throws  {
        boolean $z1;
        int $i2;
        int $i3;
        View $r1;
        int $i1;
        float $f1;
        boolean $z0 = isLayoutRtlSupport();
        LayoutParams $r3 = (LayoutParams) this.mSlideableView.getLayoutParams();
        if ($r3.dimWhenOffset) {
            if (($z0 ? $r3.rightMargin : $r3.leftMargin) <= 0) {
                $z1 = true;
                $i2 = getChildCount();
                for ($i3 = 0; $i3 < $i2; $i3++) {
                    $r1 = getChildAt($i3);
                    if ($r1 == this.mSlideableView) {
                        $i1 = (int) ((1.0f - this.mParallaxOffset) * ((float) this.mParallaxBy));
                        this.mParallaxOffset = $f0;
                        $i1 -= (int) ((1.0f - $f0) * ((float) this.mParallaxBy));
                        if ($z0) {
                            $i1 = -$i1;
                        }
                        $r1.offsetLeftAndRight($i1);
                        if (!$z1) {
                            if ($z0) {
                                $f1 = 1.0f - this.mParallaxOffset;
                            } else {
                                $f1 = this.mParallaxOffset - 1.0f;
                            }
                            dimChildView($r1, $f1, this.mCoveredFadeColor);
                        }
                    }
                }
            }
        }
        $z1 = false;
        $i2 = getChildCount();
        for ($i3 = 0; $i3 < $i2; $i3++) {
            $r1 = getChildAt($i3);
            if ($r1 == this.mSlideableView) {
                $i1 = (int) ((1.0f - this.mParallaxOffset) * ((float) this.mParallaxBy));
                this.mParallaxOffset = $f0;
                $i1 -= (int) ((1.0f - $f0) * ((float) this.mParallaxBy));
                if ($z0) {
                    $i1 = -$i1;
                }
                $r1.offsetLeftAndRight($i1);
                if (!$z1) {
                    if ($z0) {
                        $f1 = 1.0f - this.mParallaxOffset;
                    } else {
                        $f1 = this.mParallaxOffset - 1.0f;
                    }
                    dimChildView($r1, $f1, this.mCoveredFadeColor);
                }
            }
        }
    }

    protected boolean canScroll(View $r1, boolean $z0, int $i4, int $i0, int $i1) throws  {
        if ($r1 instanceof ViewGroup) {
            ViewGroup $r2 = (ViewGroup) $r1;
            int $i5 = $r1.getScrollX();
            int $i6 = $r1.getScrollY();
            for (int $i7 = $r2.getChildCount() - 1; $i7 >= 0; $i7--) {
                View $r3 = $r2.getChildAt($i7);
                if ($i0 + $i5 >= $r3.getLeft() && $i0 + $i5 < $r3.getRight() && $i1 + $i6 >= $r3.getTop() && $i1 + $i6 < $r3.getBottom()) {
                    if (canScroll($r3, true, $i4, ($i0 + $i5) - $r3.getLeft(), ($i1 + $i6) - $r3.getTop())) {
                        return true;
                    }
                }
            }
        }
        if ($z0) {
            if (!isLayoutRtlSupport()) {
                $i4 = -$i4;
            }
            if (ViewCompat.canScrollHorizontally($r1, $i4)) {
                return true;
            }
        }
        return false;
    }

    boolean isDimmed(View $r1) throws  {
        if ($r1 == null) {
            return false;
        }
        LayoutParams $r3 = (LayoutParams) $r1.getLayoutParams();
        if (!this.mCanSlide) {
            return false;
        }
        if ($r3.dimWhenOffset) {
            return this.mSlideOffset > 0.0f;
        } else {
            return false;
        }
    }

    protected android.view.ViewGroup.LayoutParams generateDefaultLayoutParams() throws  {
        return new LayoutParams();
    }

    protected android.view.ViewGroup.LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams $r1) throws  {
        return $r1 instanceof MarginLayoutParams ? new LayoutParams((MarginLayoutParams) $r1) : new LayoutParams($r1);
    }

    protected boolean checkLayoutParams(android.view.ViewGroup.LayoutParams $r1) throws  {
        return ($r1 instanceof LayoutParams) && super.checkLayoutParams($r1);
    }

    public android.view.ViewGroup.LayoutParams generateLayoutParams(AttributeSet $r1) throws  {
        return new LayoutParams(getContext(), $r1);
    }

    protected Parcelable onSaveInstanceState() throws  {
        SavedState $r1 = new SavedState(super.onSaveInstanceState());
        $r1.isOpen = isSlideable() ? isOpen() : this.mPreservedOpenState;
        return $r1;
    }

    protected void onRestoreInstanceState(Parcelable $r1) throws  {
        if ($r1 instanceof SavedState) {
            SavedState $r2 = (SavedState) $r1;
            super.onRestoreInstanceState($r2.getSuperState());
            if ($r2.isOpen) {
                openPane();
            } else {
                closePane();
            }
            this.mPreservedOpenState = $r2.isOpen;
            return;
        }
        super.onRestoreInstanceState($r1);
    }

    private boolean isLayoutRtlSupport() throws  {
        return ViewCompat.getLayoutDirection(this) == 1;
    }
}
