package android.support.v4.view;

import android.content.res.ColorStateList;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.annotation.FloatRange;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.view.accessibility.AccessibilityNodeProviderCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.WeakHashMap;

public final class ViewCompat {
    public static final int ACCESSIBILITY_LIVE_REGION_ASSERTIVE = 2;
    public static final int ACCESSIBILITY_LIVE_REGION_NONE = 0;
    public static final int ACCESSIBILITY_LIVE_REGION_POLITE = 1;
    private static final long FAKE_FRAME_TIME = 10;
    static final ViewCompatImpl IMPL;
    public static final int IMPORTANT_FOR_ACCESSIBILITY_AUTO = 0;
    public static final int IMPORTANT_FOR_ACCESSIBILITY_NO = 2;
    public static final int IMPORTANT_FOR_ACCESSIBILITY_NO_HIDE_DESCENDANTS = 4;
    public static final int IMPORTANT_FOR_ACCESSIBILITY_YES = 1;
    public static final int LAYER_TYPE_HARDWARE = 2;
    public static final int LAYER_TYPE_NONE = 0;
    public static final int LAYER_TYPE_SOFTWARE = 1;
    public static final int LAYOUT_DIRECTION_INHERIT = 2;
    public static final int LAYOUT_DIRECTION_LOCALE = 3;
    public static final int LAYOUT_DIRECTION_LTR = 0;
    public static final int LAYOUT_DIRECTION_RTL = 1;
    public static final int MEASURED_HEIGHT_STATE_SHIFT = 16;
    public static final int MEASURED_SIZE_MASK = 16777215;
    public static final int MEASURED_STATE_MASK = -16777216;
    public static final int MEASURED_STATE_TOO_SMALL = 16777216;
    public static final int OVER_SCROLL_ALWAYS = 0;
    public static final int OVER_SCROLL_IF_CONTENT_SCROLLS = 1;
    public static final int OVER_SCROLL_NEVER = 2;
    public static final int SCROLL_AXIS_HORIZONTAL = 1;
    public static final int SCROLL_AXIS_NONE = 0;
    public static final int SCROLL_AXIS_VERTICAL = 2;
    public static final int SCROLL_INDICATOR_BOTTOM = 2;
    public static final int SCROLL_INDICATOR_END = 32;
    public static final int SCROLL_INDICATOR_LEFT = 4;
    public static final int SCROLL_INDICATOR_RIGHT = 8;
    public static final int SCROLL_INDICATOR_START = 16;
    public static final int SCROLL_INDICATOR_TOP = 1;
    private static final String TAG = "ViewCompat";

    interface ViewCompatImpl {
        ViewPropertyAnimatorCompat animate(View view) throws ;

        boolean canScrollHorizontally(View view, int i) throws ;

        boolean canScrollVertically(View view, int i) throws ;

        int combineMeasuredStates(int i, int i2) throws ;

        WindowInsetsCompat dispatchApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) throws ;

        void dispatchFinishTemporaryDetach(View view) throws ;

        boolean dispatchNestedFling(View view, float f, float f2, boolean z) throws ;

        boolean dispatchNestedPreFling(View view, float f, float f2) throws ;

        boolean dispatchNestedPreScroll(View view, int i, int i2, int[] iArr, int[] iArr2) throws ;

        boolean dispatchNestedScroll(View view, int i, int i2, int i3, int i4, int[] iArr) throws ;

        void dispatchStartTemporaryDetach(View view) throws ;

        int getAccessibilityLiveRegion(View view) throws ;

        AccessibilityNodeProviderCompat getAccessibilityNodeProvider(View view) throws ;

        float getAlpha(View view) throws ;

        ColorStateList getBackgroundTintList(View view) throws ;

        Mode getBackgroundTintMode(View view) throws ;

        Rect getClipBounds(View view) throws ;

        float getElevation(View view) throws ;

        boolean getFitsSystemWindows(View view) throws ;

        int getImportantForAccessibility(View view) throws ;

        int getLabelFor(View view) throws ;

        int getLayerType(View view) throws ;

        int getLayoutDirection(View view) throws ;

        int getMeasuredHeightAndState(View view) throws ;

        int getMeasuredState(View view) throws ;

        int getMeasuredWidthAndState(View view) throws ;

        int getMinimumHeight(View view) throws ;

        int getMinimumWidth(View view) throws ;

        int getOverScrollMode(View view) throws ;

        int getPaddingEnd(View view) throws ;

        int getPaddingStart(View view) throws ;

        ViewParent getParentForAccessibility(View view) throws ;

        float getPivotX(View view) throws ;

        float getPivotY(View view) throws ;

        float getRotation(View view) throws ;

        float getRotationX(View view) throws ;

        float getRotationY(View view) throws ;

        float getScaleX(View view) throws ;

        float getScaleY(View view) throws ;

        int getScrollIndicators(View view) throws ;

        String getTransitionName(View view) throws ;

        float getTranslationX(View view) throws ;

        float getTranslationY(View view) throws ;

        float getTranslationZ(View view) throws ;

        int getWindowSystemUiVisibility(View view) throws ;

        float getX(View view) throws ;

        float getY(View view) throws ;

        float getZ(View view) throws ;

        boolean hasAccessibilityDelegate(View view) throws ;

        boolean hasNestedScrollingParent(View view) throws ;

        boolean hasOnClickListeners(View view) throws ;

        boolean hasOverlappingRendering(View view) throws ;

        boolean hasTransientState(View view) throws ;

        boolean isAttachedToWindow(View view) throws ;

        boolean isImportantForAccessibility(View view) throws ;

        boolean isLaidOut(View view) throws ;

        boolean isNestedScrollingEnabled(View view) throws ;

        boolean isOpaque(View view) throws ;

        boolean isPaddingRelative(View view) throws ;

        void jumpDrawablesToCurrentState(View view) throws ;

        void offsetLeftAndRight(View view, int i) throws ;

        void offsetTopAndBottom(View view, int i) throws ;

        WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) throws ;

        void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) throws ;

        void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) throws ;

        void onPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) throws ;

        boolean performAccessibilityAction(View view, int i, Bundle bundle) throws ;

        void postInvalidateOnAnimation(View view) throws ;

        void postInvalidateOnAnimation(View view, int i, int i2, int i3, int i4) throws ;

        void postOnAnimation(View view, Runnable runnable) throws ;

        void postOnAnimationDelayed(View view, Runnable runnable, long j) throws ;

        void requestApplyInsets(View view) throws ;

        int resolveSizeAndState(int i, int i2, int i3) throws ;

        void setAccessibilityDelegate(View view, @Nullable AccessibilityDelegateCompat accessibilityDelegateCompat) throws ;

        void setAccessibilityLiveRegion(View view, int i) throws ;

        void setActivated(View view, boolean z) throws ;

        void setAlpha(View view, float f) throws ;

        void setBackgroundTintList(View view, ColorStateList colorStateList) throws ;

        void setBackgroundTintMode(View view, Mode mode) throws ;

        void setChildrenDrawingOrderEnabled(ViewGroup viewGroup, boolean z) throws ;

        void setClipBounds(View view, Rect rect) throws ;

        void setElevation(View view, float f) throws ;

        void setFitsSystemWindows(View view, boolean z) throws ;

        void setHasTransientState(View view, boolean z) throws ;

        void setImportantForAccessibility(View view, int i) throws ;

        void setLabelFor(View view, int i) throws ;

        void setLayerPaint(View view, Paint paint) throws ;

        void setLayerType(View view, int i, Paint paint) throws ;

        void setLayoutDirection(View view, int i) throws ;

        void setNestedScrollingEnabled(View view, boolean z) throws ;

        void setOnApplyWindowInsetsListener(View view, OnApplyWindowInsetsListener onApplyWindowInsetsListener) throws ;

        void setOverScrollMode(View view, int i) throws ;

        void setPaddingRelative(View view, int i, int i2, int i3, int i4) throws ;

        void setPivotX(View view, float f) throws ;

        void setPivotY(View view, float f) throws ;

        void setRotation(View view, float f) throws ;

        void setRotationX(View view, float f) throws ;

        void setRotationY(View view, float f) throws ;

        void setSaveFromParentEnabled(View view, boolean z) throws ;

        void setScaleX(View view, float f) throws ;

        void setScaleY(View view, float f) throws ;

        void setScrollIndicators(View view, int i) throws ;

        void setScrollIndicators(View view, int i, int i2) throws ;

        void setTransitionName(View view, String str) throws ;

        void setTranslationX(View view, float f) throws ;

        void setTranslationY(View view, float f) throws ;

        void setTranslationZ(View view, float f) throws ;

        void setX(View view, float f) throws ;

        void setY(View view, float f) throws ;

        boolean startNestedScroll(View view, int i) throws ;

        void stopNestedScroll(View view) throws ;
    }

    static class BaseViewCompatImpl implements ViewCompatImpl {
        private Method mDispatchFinishTemporaryDetach;
        private Method mDispatchStartTemporaryDetach;
        private boolean mTempDetachBound;
        WeakHashMap<View, ViewPropertyAnimatorCompat> mViewPropertyAnimatorCompatMap = null;

        public int getAccessibilityLiveRegion(View view) throws  {
            return 0;
        }

        public AccessibilityNodeProviderCompat getAccessibilityNodeProvider(View view) throws  {
            return null;
        }

        public float getAlpha(View view) throws  {
            return 1.0f;
        }

        public Rect getClipBounds(View view) throws  {
            return null;
        }

        public float getElevation(View view) throws  {
            return 0.0f;
        }

        public boolean getFitsSystemWindows(View view) throws  {
            return false;
        }

        long getFrameTime() throws  {
            return ViewCompat.FAKE_FRAME_TIME;
        }

        public int getImportantForAccessibility(View view) throws  {
            return 0;
        }

        public int getLabelFor(View view) throws  {
            return 0;
        }

        public int getLayerType(View view) throws  {
            return 0;
        }

        public int getLayoutDirection(View view) throws  {
            return 0;
        }

        public int getMeasuredState(View view) throws  {
            return 0;
        }

        public int getOverScrollMode(View v) throws  {
            return 2;
        }

        public float getPivotX(View view) throws  {
            return 0.0f;
        }

        public float getPivotY(View view) throws  {
            return 0.0f;
        }

        public float getRotation(View view) throws  {
            return 0.0f;
        }

        public float getRotationX(View view) throws  {
            return 0.0f;
        }

        public float getRotationY(View view) throws  {
            return 0.0f;
        }

        public float getScaleX(View view) throws  {
            return 0.0f;
        }

        public float getScaleY(View view) throws  {
            return 0.0f;
        }

        public int getScrollIndicators(View view) throws  {
            return 0;
        }

        public String getTransitionName(View view) throws  {
            return null;
        }

        public float getTranslationX(View view) throws  {
            return 0.0f;
        }

        public float getTranslationY(View view) throws  {
            return 0.0f;
        }

        public float getTranslationZ(View view) throws  {
            return 0.0f;
        }

        public int getWindowSystemUiVisibility(View view) throws  {
            return 0;
        }

        public float getX(View view) throws  {
            return 0.0f;
        }

        public float getY(View view) throws  {
            return 0.0f;
        }

        public boolean hasAccessibilityDelegate(View v) throws  {
            return false;
        }

        public boolean hasOnClickListeners(View view) throws  {
            return false;
        }

        public boolean hasOverlappingRendering(View view) throws  {
            return true;
        }

        public boolean hasTransientState(View view) throws  {
            return false;
        }

        public boolean isImportantForAccessibility(View view) throws  {
            return true;
        }

        public boolean isPaddingRelative(View view) throws  {
            return false;
        }

        public boolean performAccessibilityAction(View view, int action, Bundle arguments) throws  {
            return false;
        }

        BaseViewCompatImpl() throws  {
        }

        public boolean canScrollHorizontally(View $r1, int $i0) throws  {
            return ($r1 instanceof ScrollingView) && canScrollingViewScrollHorizontally((ScrollingView) $r1, $i0);
        }

        public boolean canScrollVertically(View $r1, int $i0) throws  {
            return ($r1 instanceof ScrollingView) && canScrollingViewScrollVertically((ScrollingView) $r1, $i0);
        }

        public void setOverScrollMode(View v, int mode) throws  {
        }

        public void setAccessibilityDelegate(View v, AccessibilityDelegateCompat delegate) throws  {
        }

        public void onPopulateAccessibilityEvent(View v, AccessibilityEvent event) throws  {
        }

        public void onInitializeAccessibilityEvent(View v, AccessibilityEvent event) throws  {
        }

        public void onInitializeAccessibilityNodeInfo(View v, AccessibilityNodeInfoCompat info) throws  {
        }

        public void setHasTransientState(View view, boolean hasTransientState) throws  {
        }

        public void postInvalidateOnAnimation(View $r1) throws  {
            $r1.invalidate();
        }

        public void postInvalidateOnAnimation(View $r1, int $i0, int $i1, int $i2, int $i3) throws  {
            $r1.invalidate($i0, $i1, $i2, $i3);
        }

        public void postOnAnimation(View $r1, Runnable $r2) throws  {
            $r1.postDelayed($r2, getFrameTime());
        }

        public void postOnAnimationDelayed(View $r1, Runnable $r2, long $l0) throws  {
            $r1.postDelayed($r2, getFrameTime() + $l0);
        }

        public void setImportantForAccessibility(View view, int mode) throws  {
        }

        public void setLayerType(View view, int layerType, Paint paint) throws  {
        }

        public void setLabelFor(View view, int id) throws  {
        }

        public void setLayerPaint(View view, Paint p) throws  {
        }

        public void setLayoutDirection(View view, int layoutDirection) throws  {
        }

        public ViewParent getParentForAccessibility(View $r1) throws  {
            return $r1.getParent();
        }

        public boolean isOpaque(View $r1) throws  {
            Drawable $r2 = $r1.getBackground();
            if ($r2 != null) {
                return $r2.getOpacity() == -1;
            } else {
                return false;
            }
        }

        public int resolveSizeAndState(int $i0, int $i1, int childMeasuredState) throws  {
            return View.resolveSize($i0, $i1);
        }

        public int getMeasuredWidthAndState(View $r1) throws  {
            return $r1.getMeasuredWidth();
        }

        public int getMeasuredHeightAndState(View $r1) throws  {
            return $r1.getMeasuredHeight();
        }

        public void setAccessibilityLiveRegion(View view, int mode) throws  {
        }

        public int getPaddingStart(View $r1) throws  {
            return $r1.getPaddingLeft();
        }

        public int getPaddingEnd(View $r1) throws  {
            return $r1.getPaddingRight();
        }

        public void setPaddingRelative(View $r1, int $i0, int $i1, int $i2, int $i3) throws  {
            $r1.setPadding($i0, $i1, $i2, $i3);
        }

        public void dispatchStartTemporaryDetach(View $r1) throws  {
            if (!this.mTempDetachBound) {
                bindTempDetach();
            }
            if (this.mDispatchStartTemporaryDetach != null) {
                try {
                    this.mDispatchStartTemporaryDetach.invoke($r1, new Object[0]);
                    return;
                } catch (Exception $r2) {
                    Log.d("ViewCompat", "Error calling dispatchStartTemporaryDetach", $r2);
                    return;
                }
            }
            $r1.onStartTemporaryDetach();
        }

        public void dispatchFinishTemporaryDetach(View $r1) throws  {
            if (!this.mTempDetachBound) {
                bindTempDetach();
            }
            if (this.mDispatchFinishTemporaryDetach != null) {
                try {
                    this.mDispatchFinishTemporaryDetach.invoke($r1, new Object[0]);
                    return;
                } catch (Exception $r2) {
                    Log.d("ViewCompat", "Error calling dispatchFinishTemporaryDetach", $r2);
                    return;
                }
            }
            $r1.onFinishTemporaryDetach();
        }

        private void bindTempDetach() throws  {
            try {
                this.mDispatchStartTemporaryDetach = View.class.getDeclaredMethod("dispatchStartTemporaryDetach", new Class[0]);
                this.mDispatchFinishTemporaryDetach = View.class.getDeclaredMethod("dispatchFinishTemporaryDetach", new Class[0]);
            } catch (NoSuchMethodException $r1) {
                Log.e("ViewCompat", "Couldn't find method", $r1);
            }
            this.mTempDetachBound = true;
        }

        public int getMinimumWidth(View $r1) throws  {
            return ViewCompatBase.getMinimumWidth($r1);
        }

        public int getMinimumHeight(View $r1) throws  {
            return ViewCompatBase.getMinimumHeight($r1);
        }

        public ViewPropertyAnimatorCompat animate(View $r1) throws  {
            return new ViewPropertyAnimatorCompat($r1);
        }

        public void setRotation(View view, float value) throws  {
        }

        public void setTranslationX(View view, float value) throws  {
        }

        public void setTranslationY(View view, float value) throws  {
        }

        public void setAlpha(View view, float value) throws  {
        }

        public void setRotationX(View view, float value) throws  {
        }

        public void setRotationY(View view, float value) throws  {
        }

        public void setScaleX(View view, float value) throws  {
        }

        public void setScaleY(View view, float value) throws  {
        }

        public void setX(View view, float value) throws  {
        }

        public void setY(View view, float value) throws  {
        }

        public void setPivotX(View view, float value) throws  {
        }

        public void setPivotY(View view, float value) throws  {
        }

        public void setTransitionName(View view, String transitionName) throws  {
        }

        public void requestApplyInsets(View view) throws  {
        }

        public void setElevation(View view, float elevation) throws  {
        }

        public void setTranslationZ(View view, float translationZ) throws  {
        }

        public void setClipBounds(View view, Rect clipBounds) throws  {
        }

        public void setChildrenDrawingOrderEnabled(ViewGroup viewGroup, boolean enabled) throws  {
        }

        public void setFitsSystemWindows(View view, boolean fitSystemWindows) throws  {
        }

        public void jumpDrawablesToCurrentState(View view) throws  {
        }

        public void setOnApplyWindowInsetsListener(View view, OnApplyWindowInsetsListener listener) throws  {
        }

        public WindowInsetsCompat onApplyWindowInsets(View v, WindowInsetsCompat $r2) throws  {
            return $r2;
        }

        public WindowInsetsCompat dispatchApplyWindowInsets(View v, WindowInsetsCompat $r2) throws  {
            return $r2;
        }

        public void setSaveFromParentEnabled(View v, boolean enabled) throws  {
        }

        public void setActivated(View view, boolean activated) throws  {
        }

        public void setNestedScrollingEnabled(View $r1, boolean $z0) throws  {
            if ($r1 instanceof NestedScrollingChild) {
                ((NestedScrollingChild) $r1).setNestedScrollingEnabled($z0);
            }
        }

        public boolean isNestedScrollingEnabled(View $r1) throws  {
            if ($r1 instanceof NestedScrollingChild) {
                return ((NestedScrollingChild) $r1).isNestedScrollingEnabled();
            }
            return false;
        }

        public ColorStateList getBackgroundTintList(View $r1) throws  {
            return ViewCompatBase.getBackgroundTintList($r1);
        }

        public void setBackgroundTintList(View $r1, ColorStateList $r2) throws  {
            ViewCompatBase.setBackgroundTintList($r1, $r2);
        }

        public void setBackgroundTintMode(View $r1, Mode $r2) throws  {
            ViewCompatBase.setBackgroundTintMode($r1, $r2);
        }

        public Mode getBackgroundTintMode(View $r1) throws  {
            return ViewCompatBase.getBackgroundTintMode($r1);
        }

        private boolean canScrollingViewScrollHorizontally(ScrollingView $r1, int $i0) throws  {
            int $i2 = $r1.computeHorizontalScrollOffset();
            int $i1 = $r1.computeHorizontalScrollRange() - $r1.computeHorizontalScrollExtent();
            if ($i1 == 0) {
                return false;
            }
            return $i0 < 0 ? $i2 > 0 : $i2 < $i1 + -1;
        }

        private boolean canScrollingViewScrollVertically(ScrollingView $r1, int $i0) throws  {
            int $i2 = $r1.computeVerticalScrollOffset();
            int $i1 = $r1.computeVerticalScrollRange() - $r1.computeVerticalScrollExtent();
            if ($i1 == 0) {
                return false;
            }
            return $i0 < 0 ? $i2 > 0 : $i2 < $i1 + -1;
        }

        public boolean startNestedScroll(View $r1, int $i0) throws  {
            if ($r1 instanceof NestedScrollingChild) {
                return ((NestedScrollingChild) $r1).startNestedScroll($i0);
            }
            return false;
        }

        public void stopNestedScroll(View $r1) throws  {
            if ($r1 instanceof NestedScrollingChild) {
                ((NestedScrollingChild) $r1).stopNestedScroll();
            }
        }

        public boolean hasNestedScrollingParent(View $r1) throws  {
            if ($r1 instanceof NestedScrollingChild) {
                return ((NestedScrollingChild) $r1).hasNestedScrollingParent();
            }
            return false;
        }

        public boolean dispatchNestedScroll(View $r1, int $i0, int $i1, int $i2, int $i3, int[] $r2) throws  {
            if ($r1 instanceof NestedScrollingChild) {
                return ((NestedScrollingChild) $r1).dispatchNestedScroll($i0, $i1, $i2, $i3, $r2);
            }
            return false;
        }

        public boolean dispatchNestedPreScroll(View $r3, int $i0, int $i1, int[] $r1, int[] $r2) throws  {
            if ($r3 instanceof NestedScrollingChild) {
                return ((NestedScrollingChild) $r3).dispatchNestedPreScroll($i0, $i1, $r1, $r2);
            }
            return false;
        }

        public boolean dispatchNestedFling(View $r1, float $f0, float $f1, boolean $z0) throws  {
            if ($r1 instanceof NestedScrollingChild) {
                return ((NestedScrollingChild) $r1).dispatchNestedFling($f0, $f1, $z0);
            }
            return false;
        }

        public boolean dispatchNestedPreFling(View $r1, float $f0, float $f1) throws  {
            if ($r1 instanceof NestedScrollingChild) {
                return ((NestedScrollingChild) $r1).dispatchNestedPreFling($f0, $f1);
            }
            return false;
        }

        public boolean isLaidOut(View $r1) throws  {
            return ViewCompatBase.isLaidOut($r1);
        }

        public int combineMeasuredStates(int $i0, int $i1) throws  {
            return $i0 | $i1;
        }

        public float getZ(View $r1) throws  {
            return getTranslationZ($r1) + getElevation($r1);
        }

        public boolean isAttachedToWindow(View $r1) throws  {
            return ViewCompatBase.isAttachedToWindow($r1);
        }

        public void setScrollIndicators(View view, int indicators) throws  {
        }

        public void setScrollIndicators(View view, int indicators, int mask) throws  {
        }

        public void offsetLeftAndRight(View $r1, int $i0) throws  {
            ViewCompatBase.offsetLeftAndRight($r1, $i0);
        }

        public void offsetTopAndBottom(View $r1, int $i0) throws  {
            ViewCompatBase.offsetTopAndBottom($r1, $i0);
        }
    }

    static class EclairMr1ViewCompatImpl extends BaseViewCompatImpl {
        EclairMr1ViewCompatImpl() throws  {
        }

        public boolean isOpaque(View $r1) throws  {
            return ViewCompatEclairMr1.isOpaque($r1);
        }

        public void setChildrenDrawingOrderEnabled(ViewGroup $r1, boolean $z0) throws  {
            ViewCompatEclairMr1.setChildrenDrawingOrderEnabled($r1, $z0);
        }
    }

    static class GBViewCompatImpl extends EclairMr1ViewCompatImpl {
        GBViewCompatImpl() throws  {
        }

        public int getOverScrollMode(View $r1) throws  {
            return ViewCompatGingerbread.getOverScrollMode($r1);
        }

        public void setOverScrollMode(View $r1, int $i0) throws  {
            ViewCompatGingerbread.setOverScrollMode($r1, $i0);
        }
    }

    static class HCViewCompatImpl extends GBViewCompatImpl {
        HCViewCompatImpl() throws  {
        }

        long getFrameTime() throws  {
            return ViewCompatHC.getFrameTime();
        }

        public float getAlpha(View $r1) throws  {
            return ViewCompatHC.getAlpha($r1);
        }

        public void setLayerType(View $r1, int $i0, Paint $r2) throws  {
            ViewCompatHC.setLayerType($r1, $i0, $r2);
        }

        public int getLayerType(View $r1) throws  {
            return ViewCompatHC.getLayerType($r1);
        }

        public void setLayerPaint(View $r1, Paint $r2) throws  {
            setLayerType($r1, getLayerType($r1), $r2);
            $r1.invalidate();
        }

        public int resolveSizeAndState(int $i0, int $i1, int $i2) throws  {
            return ViewCompatHC.resolveSizeAndState($i0, $i1, $i2);
        }

        public int getMeasuredWidthAndState(View $r1) throws  {
            return ViewCompatHC.getMeasuredWidthAndState($r1);
        }

        public int getMeasuredHeightAndState(View $r1) throws  {
            return ViewCompatHC.getMeasuredHeightAndState($r1);
        }

        public int getMeasuredState(View $r1) throws  {
            return ViewCompatHC.getMeasuredState($r1);
        }

        public float getTranslationX(View $r1) throws  {
            return ViewCompatHC.getTranslationX($r1);
        }

        public float getTranslationY(View $r1) throws  {
            return ViewCompatHC.getTranslationY($r1);
        }

        public void setTranslationX(View $r1, float $f0) throws  {
            ViewCompatHC.setTranslationX($r1, $f0);
        }

        public void setTranslationY(View $r1, float $f0) throws  {
            ViewCompatHC.setTranslationY($r1, $f0);
        }

        public void setAlpha(View $r1, float $f0) throws  {
            ViewCompatHC.setAlpha($r1, $f0);
        }

        public void setX(View $r1, float $f0) throws  {
            ViewCompatHC.setX($r1, $f0);
        }

        public void setY(View $r1, float $f0) throws  {
            ViewCompatHC.setY($r1, $f0);
        }

        public void setRotation(View $r1, float $f0) throws  {
            ViewCompatHC.setRotation($r1, $f0);
        }

        public void setRotationX(View $r1, float $f0) throws  {
            ViewCompatHC.setRotationX($r1, $f0);
        }

        public void setRotationY(View $r1, float $f0) throws  {
            ViewCompatHC.setRotationY($r1, $f0);
        }

        public void setScaleX(View $r1, float $f0) throws  {
            ViewCompatHC.setScaleX($r1, $f0);
        }

        public void setScaleY(View $r1, float $f0) throws  {
            ViewCompatHC.setScaleY($r1, $f0);
        }

        public void setPivotX(View $r1, float $f0) throws  {
            ViewCompatHC.setPivotX($r1, $f0);
        }

        public void setPivotY(View $r1, float $f0) throws  {
            ViewCompatHC.setPivotY($r1, $f0);
        }

        public float getX(View $r1) throws  {
            return ViewCompatHC.getX($r1);
        }

        public float getY(View $r1) throws  {
            return ViewCompatHC.getY($r1);
        }

        public float getRotation(View $r1) throws  {
            return ViewCompatHC.getRotation($r1);
        }

        public float getRotationX(View $r1) throws  {
            return ViewCompatHC.getRotationX($r1);
        }

        public float getRotationY(View $r1) throws  {
            return ViewCompatHC.getRotationY($r1);
        }

        public float getScaleX(View $r1) throws  {
            return ViewCompatHC.getScaleX($r1);
        }

        public float getScaleY(View $r1) throws  {
            return ViewCompatHC.getScaleY($r1);
        }

        public float getPivotX(View $r1) throws  {
            return ViewCompatHC.getPivotX($r1);
        }

        public float getPivotY(View $r1) throws  {
            return ViewCompatHC.getPivotY($r1);
        }

        public void jumpDrawablesToCurrentState(View $r1) throws  {
            ViewCompatHC.jumpDrawablesToCurrentState($r1);
        }

        public void setSaveFromParentEnabled(View $r1, boolean $z0) throws  {
            ViewCompatHC.setSaveFromParentEnabled($r1, $z0);
        }

        public void setActivated(View $r1, boolean $z0) throws  {
            ViewCompatHC.setActivated($r1, $z0);
        }

        public int combineMeasuredStates(int $i0, int $i1) throws  {
            return ViewCompatHC.combineMeasuredStates($i0, $i1);
        }

        public void offsetLeftAndRight(View $r1, int $i0) throws  {
            ViewCompatHC.offsetLeftAndRight($r1, $i0);
        }

        public void offsetTopAndBottom(View $r1, int $i0) throws  {
            ViewCompatHC.offsetTopAndBottom($r1, $i0);
        }
    }

    static class ICSViewCompatImpl extends HCViewCompatImpl {
        static boolean accessibilityDelegateCheckFailed = false;
        static Field mAccessibilityDelegateField;

        ICSViewCompatImpl() throws  {
        }

        public boolean canScrollHorizontally(View $r1, int $i0) throws  {
            return ViewCompatICS.canScrollHorizontally($r1, $i0);
        }

        public boolean canScrollVertically(View $r1, int $i0) throws  {
            return ViewCompatICS.canScrollVertically($r1, $i0);
        }

        public void onPopulateAccessibilityEvent(View $r1, AccessibilityEvent $r2) throws  {
            ViewCompatICS.onPopulateAccessibilityEvent($r1, $r2);
        }

        public void onInitializeAccessibilityEvent(View $r1, AccessibilityEvent $r2) throws  {
            ViewCompatICS.onInitializeAccessibilityEvent($r1, $r2);
        }

        public void onInitializeAccessibilityNodeInfo(View $r1, AccessibilityNodeInfoCompat $r2) throws  {
            ViewCompatICS.onInitializeAccessibilityNodeInfo($r1, $r2.getInfo());
        }

        public void setAccessibilityDelegate(View $r1, @Nullable AccessibilityDelegateCompat $r2) throws  {
            ViewCompatICS.setAccessibilityDelegate($r1, $r2 == null ? null : $r2.getBridge());
        }

        public boolean hasAccessibilityDelegate(View $r1) throws  {
            boolean $z0 = true;
            if (accessibilityDelegateCheckFailed) {
                return false;
            }
            if (mAccessibilityDelegateField == null) {
                try {
                    mAccessibilityDelegateField = View.class.getDeclaredField("mAccessibilityDelegate");
                    mAccessibilityDelegateField.setAccessible(true);
                } catch (Throwable th) {
                    accessibilityDelegateCheckFailed = true;
                    return false;
                }
            }
            try {
                if (mAccessibilityDelegateField.get($r1) == null) {
                    $z0 = false;
                }
                return $z0;
            } catch (Throwable th2) {
                accessibilityDelegateCheckFailed = true;
                return false;
            }
        }

        public ViewPropertyAnimatorCompat animate(View $r1) throws  {
            if (this.mViewPropertyAnimatorCompatMap == null) {
                this.mViewPropertyAnimatorCompatMap = new WeakHashMap();
            }
            ViewPropertyAnimatorCompat $r4 = (ViewPropertyAnimatorCompat) this.mViewPropertyAnimatorCompatMap.get($r1);
            if ($r4 != null) {
                return $r4;
            }
            $r4 = new ViewPropertyAnimatorCompat($r1);
            this.mViewPropertyAnimatorCompatMap.put($r1, $r4);
            return $r4;
        }

        public void setFitsSystemWindows(View $r1, boolean $z0) throws  {
            ViewCompatICS.setFitsSystemWindows($r1, $z0);
        }
    }

    static class ICSMr1ViewCompatImpl extends ICSViewCompatImpl {
        ICSMr1ViewCompatImpl() throws  {
        }

        public boolean hasOnClickListeners(View $r1) throws  {
            return ViewCompatICSMr1.hasOnClickListeners($r1);
        }
    }

    static class JBViewCompatImpl extends ICSMr1ViewCompatImpl {
        JBViewCompatImpl() throws  {
        }

        public boolean hasTransientState(View $r1) throws  {
            return ViewCompatJB.hasTransientState($r1);
        }

        public void setHasTransientState(View $r1, boolean $z0) throws  {
            ViewCompatJB.setHasTransientState($r1, $z0);
        }

        public void postInvalidateOnAnimation(View $r1) throws  {
            ViewCompatJB.postInvalidateOnAnimation($r1);
        }

        public void postInvalidateOnAnimation(View $r1, int $i0, int $i1, int $i2, int $i3) throws  {
            ViewCompatJB.postInvalidateOnAnimation($r1, $i0, $i1, $i2, $i3);
        }

        public void postOnAnimation(View $r1, Runnable $r2) throws  {
            ViewCompatJB.postOnAnimation($r1, $r2);
        }

        public void postOnAnimationDelayed(View $r1, Runnable $r2, long $l0) throws  {
            ViewCompatJB.postOnAnimationDelayed($r1, $r2, $l0);
        }

        public int getImportantForAccessibility(View $r1) throws  {
            return ViewCompatJB.getImportantForAccessibility($r1);
        }

        public void setImportantForAccessibility(View $r1, int $i0) throws  {
            if ($i0 == 4) {
                $i0 = 2;
            }
            ViewCompatJB.setImportantForAccessibility($r1, $i0);
        }

        public boolean performAccessibilityAction(View $r1, int $i0, Bundle $r2) throws  {
            return ViewCompatJB.performAccessibilityAction($r1, $i0, $r2);
        }

        public AccessibilityNodeProviderCompat getAccessibilityNodeProvider(View $r1) throws  {
            Object $r2 = ViewCompatJB.getAccessibilityNodeProvider($r1);
            if ($r2 != null) {
                return new AccessibilityNodeProviderCompat($r2);
            }
            return null;
        }

        public ViewParent getParentForAccessibility(View $r1) throws  {
            return ViewCompatJB.getParentForAccessibility($r1);
        }

        public int getMinimumWidth(View $r1) throws  {
            return ViewCompatJB.getMinimumWidth($r1);
        }

        public int getMinimumHeight(View $r1) throws  {
            return ViewCompatJB.getMinimumHeight($r1);
        }

        public void requestApplyInsets(View $r1) throws  {
            ViewCompatJB.requestApplyInsets($r1);
        }

        public boolean getFitsSystemWindows(View $r1) throws  {
            return ViewCompatJB.getFitsSystemWindows($r1);
        }

        public boolean hasOverlappingRendering(View $r1) throws  {
            return ViewCompatJB.hasOverlappingRendering($r1);
        }
    }

    static class JbMr1ViewCompatImpl extends JBViewCompatImpl {
        JbMr1ViewCompatImpl() throws  {
        }

        public int getLabelFor(View $r1) throws  {
            return ViewCompatJellybeanMr1.getLabelFor($r1);
        }

        public void setLabelFor(View $r1, int $i0) throws  {
            ViewCompatJellybeanMr1.setLabelFor($r1, $i0);
        }

        public void setLayerPaint(View $r1, Paint $r2) throws  {
            ViewCompatJellybeanMr1.setLayerPaint($r1, $r2);
        }

        public int getLayoutDirection(View $r1) throws  {
            return ViewCompatJellybeanMr1.getLayoutDirection($r1);
        }

        public void setLayoutDirection(View $r1, int $i0) throws  {
            ViewCompatJellybeanMr1.setLayoutDirection($r1, $i0);
        }

        public int getPaddingStart(View $r1) throws  {
            return ViewCompatJellybeanMr1.getPaddingStart($r1);
        }

        public int getPaddingEnd(View $r1) throws  {
            return ViewCompatJellybeanMr1.getPaddingEnd($r1);
        }

        public void setPaddingRelative(View $r1, int $i0, int $i1, int $i2, int $i3) throws  {
            ViewCompatJellybeanMr1.setPaddingRelative($r1, $i0, $i1, $i2, $i3);
        }

        public int getWindowSystemUiVisibility(View $r1) throws  {
            return ViewCompatJellybeanMr1.getWindowSystemUiVisibility($r1);
        }

        public boolean isPaddingRelative(View $r1) throws  {
            return ViewCompatJellybeanMr1.isPaddingRelative($r1);
        }
    }

    static class JbMr2ViewCompatImpl extends JbMr1ViewCompatImpl {
        JbMr2ViewCompatImpl() throws  {
        }

        public void setClipBounds(View $r1, Rect $r2) throws  {
            ViewCompatJellybeanMr2.setClipBounds($r1, $r2);
        }

        public Rect getClipBounds(View $r1) throws  {
            return ViewCompatJellybeanMr2.getClipBounds($r1);
        }
    }

    static class KitKatViewCompatImpl extends JbMr2ViewCompatImpl {
        KitKatViewCompatImpl() throws  {
        }

        public int getAccessibilityLiveRegion(View $r1) throws  {
            return ViewCompatKitKat.getAccessibilityLiveRegion($r1);
        }

        public void setAccessibilityLiveRegion(View $r1, int $i0) throws  {
            ViewCompatKitKat.setAccessibilityLiveRegion($r1, $i0);
        }

        public void setImportantForAccessibility(View $r1, int $i0) throws  {
            ViewCompatJB.setImportantForAccessibility($r1, $i0);
        }

        public boolean isLaidOut(View $r1) throws  {
            return ViewCompatKitKat.isLaidOut($r1);
        }

        public boolean isAttachedToWindow(View $r1) throws  {
            return ViewCompatKitKat.isAttachedToWindow($r1);
        }
    }

    static class LollipopViewCompatImpl extends KitKatViewCompatImpl {
        LollipopViewCompatImpl() throws  {
        }

        public void setTransitionName(View $r1, String $r2) throws  {
            ViewCompatLollipop.setTransitionName($r1, $r2);
        }

        public String getTransitionName(View $r1) throws  {
            return ViewCompatLollipop.getTransitionName($r1);
        }

        public void requestApplyInsets(View $r1) throws  {
            ViewCompatLollipop.requestApplyInsets($r1);
        }

        public void setElevation(View $r1, float $f0) throws  {
            ViewCompatLollipop.setElevation($r1, $f0);
        }

        public float getElevation(View $r1) throws  {
            return ViewCompatLollipop.getElevation($r1);
        }

        public void setTranslationZ(View $r1, float $f0) throws  {
            ViewCompatLollipop.setTranslationZ($r1, $f0);
        }

        public float getTranslationZ(View $r1) throws  {
            return ViewCompatLollipop.getTranslationZ($r1);
        }

        public void setOnApplyWindowInsetsListener(View $r1, OnApplyWindowInsetsListener $r2) throws  {
            ViewCompatLollipop.setOnApplyWindowInsetsListener($r1, $r2);
        }

        public void setNestedScrollingEnabled(View $r1, boolean $z0) throws  {
            ViewCompatLollipop.setNestedScrollingEnabled($r1, $z0);
        }

        public boolean isNestedScrollingEnabled(View $r1) throws  {
            return ViewCompatLollipop.isNestedScrollingEnabled($r1);
        }

        public boolean startNestedScroll(View $r1, int $i0) throws  {
            return ViewCompatLollipop.startNestedScroll($r1, $i0);
        }

        public void stopNestedScroll(View $r1) throws  {
            ViewCompatLollipop.stopNestedScroll($r1);
        }

        public boolean hasNestedScrollingParent(View $r1) throws  {
            return ViewCompatLollipop.hasNestedScrollingParent($r1);
        }

        public boolean dispatchNestedScroll(View $r1, int $i0, int $i1, int $i2, int $i3, int[] $r2) throws  {
            return ViewCompatLollipop.dispatchNestedScroll($r1, $i0, $i1, $i2, $i3, $r2);
        }

        public boolean dispatchNestedPreScroll(View $r1, int $i0, int $i1, int[] $r2, int[] $r3) throws  {
            return ViewCompatLollipop.dispatchNestedPreScroll($r1, $i0, $i1, $r2, $r3);
        }

        public boolean dispatchNestedFling(View $r1, float $f0, float $f1, boolean $z0) throws  {
            return ViewCompatLollipop.dispatchNestedFling($r1, $f0, $f1, $z0);
        }

        public boolean dispatchNestedPreFling(View $r1, float $f0, float $f1) throws  {
            return ViewCompatLollipop.dispatchNestedPreFling($r1, $f0, $f1);
        }

        public boolean isImportantForAccessibility(View $r1) throws  {
            return ViewCompatLollipop.isImportantForAccessibility($r1);
        }

        public ColorStateList getBackgroundTintList(View $r1) throws  {
            return ViewCompatLollipop.getBackgroundTintList($r1);
        }

        public void setBackgroundTintList(View $r1, ColorStateList $r2) throws  {
            ViewCompatLollipop.setBackgroundTintList($r1, $r2);
        }

        public void setBackgroundTintMode(View $r1, Mode $r2) throws  {
            ViewCompatLollipop.setBackgroundTintMode($r1, $r2);
        }

        public Mode getBackgroundTintMode(View $r1) throws  {
            return ViewCompatLollipop.getBackgroundTintMode($r1);
        }

        public WindowInsetsCompat onApplyWindowInsets(View $r1, WindowInsetsCompat $r2) throws  {
            return ViewCompatLollipop.onApplyWindowInsets($r1, $r2);
        }

        public WindowInsetsCompat dispatchApplyWindowInsets(View $r1, WindowInsetsCompat $r2) throws  {
            return ViewCompatLollipop.dispatchApplyWindowInsets($r1, $r2);
        }

        public float getZ(View $r1) throws  {
            return ViewCompatLollipop.getZ($r1);
        }

        public void offsetLeftAndRight(View $r1, int $i0) throws  {
            ViewCompatLollipop.offsetLeftAndRight($r1, $i0);
        }

        public void offsetTopAndBottom(View $r1, int $i0) throws  {
            ViewCompatLollipop.offsetTopAndBottom($r1, $i0);
        }
    }

    static class MarshmallowViewCompatImpl extends LollipopViewCompatImpl {
        MarshmallowViewCompatImpl() throws  {
        }

        public void setScrollIndicators(View $r1, int $i0) throws  {
            ViewCompatMarshmallow.setScrollIndicators($r1, $i0);
        }

        public void setScrollIndicators(View $r1, int $i0, int $i1) throws  {
            ViewCompatMarshmallow.setScrollIndicators($r1, $i0, $i1);
        }

        public int getScrollIndicators(View $r1) throws  {
            return ViewCompatMarshmallow.getScrollIndicators($r1);
        }

        public void offsetLeftAndRight(View $r1, int $i0) throws  {
            ViewCompatMarshmallow.offsetLeftAndRight($r1, $i0);
        }

        public void offsetTopAndBottom(View $r1, int $i0) throws  {
            ViewCompatMarshmallow.offsetTopAndBottom($r1, $i0);
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ScrollIndicators {
    }

    static {
        int $i0 = VERSION.SDK_INT;
        if ($i0 >= 23) {
            IMPL = new MarshmallowViewCompatImpl();
        } else if ($i0 >= 21) {
            IMPL = new LollipopViewCompatImpl();
        } else if ($i0 >= 19) {
            IMPL = new KitKatViewCompatImpl();
        } else if ($i0 >= 17) {
            IMPL = new JbMr1ViewCompatImpl();
        } else if ($i0 >= 16) {
            IMPL = new JBViewCompatImpl();
        } else if ($i0 >= 15) {
            IMPL = new ICSMr1ViewCompatImpl();
        } else if ($i0 >= 14) {
            IMPL = new ICSViewCompatImpl();
        } else if ($i0 >= 11) {
            IMPL = new HCViewCompatImpl();
        } else if ($i0 >= 9) {
            IMPL = new GBViewCompatImpl();
        } else if ($i0 >= 7) {
            IMPL = new EclairMr1ViewCompatImpl();
        } else {
            IMPL = new BaseViewCompatImpl();
        }
    }

    public static boolean canScrollHorizontally(View $r0, int $i0) throws  {
        return IMPL.canScrollHorizontally($r0, $i0);
    }

    public static boolean canScrollVertically(View $r0, int $i0) throws  {
        return IMPL.canScrollVertically($r0, $i0);
    }

    public static int getOverScrollMode(View $r0) throws  {
        return IMPL.getOverScrollMode($r0);
    }

    public static void setOverScrollMode(View $r0, int $i0) throws  {
        IMPL.setOverScrollMode($r0, $i0);
    }

    public static void onPopulateAccessibilityEvent(View $r0, AccessibilityEvent $r1) throws  {
        IMPL.onPopulateAccessibilityEvent($r0, $r1);
    }

    public static void onInitializeAccessibilityEvent(View $r0, AccessibilityEvent $r1) throws  {
        IMPL.onInitializeAccessibilityEvent($r0, $r1);
    }

    public static void onInitializeAccessibilityNodeInfo(View $r0, AccessibilityNodeInfoCompat $r1) throws  {
        IMPL.onInitializeAccessibilityNodeInfo($r0, $r1);
    }

    public static void setAccessibilityDelegate(View $r0, AccessibilityDelegateCompat $r1) throws  {
        IMPL.setAccessibilityDelegate($r0, $r1);
    }

    public static boolean hasAccessibilityDelegate(View $r0) throws  {
        return IMPL.hasAccessibilityDelegate($r0);
    }

    public static boolean hasTransientState(View $r0) throws  {
        return IMPL.hasTransientState($r0);
    }

    public static void setHasTransientState(View $r0, boolean $z0) throws  {
        IMPL.setHasTransientState($r0, $z0);
    }

    public static void postInvalidateOnAnimation(View $r0) throws  {
        IMPL.postInvalidateOnAnimation($r0);
    }

    public static void postInvalidateOnAnimation(View $r0, int $i0, int $i1, int $i2, int $i3) throws  {
        IMPL.postInvalidateOnAnimation($r0, $i0, $i1, $i2, $i3);
    }

    public static void postOnAnimation(View $r0, Runnable $r1) throws  {
        IMPL.postOnAnimation($r0, $r1);
    }

    public static void postOnAnimationDelayed(View $r0, Runnable $r1, long $l0) throws  {
        IMPL.postOnAnimationDelayed($r0, $r1, $l0);
    }

    public static int getImportantForAccessibility(View $r0) throws  {
        return IMPL.getImportantForAccessibility($r0);
    }

    public static void setImportantForAccessibility(View $r0, int $i0) throws  {
        IMPL.setImportantForAccessibility($r0, $i0);
    }

    public static boolean performAccessibilityAction(View $r0, int $i0, Bundle $r1) throws  {
        return IMPL.performAccessibilityAction($r0, $i0, $r1);
    }

    public static AccessibilityNodeProviderCompat getAccessibilityNodeProvider(View $r0) throws  {
        return IMPL.getAccessibilityNodeProvider($r0);
    }

    public static float getAlpha(View $r0) throws  {
        return IMPL.getAlpha($r0);
    }

    public static void setLayerType(View $r0, int $i0, Paint $r1) throws  {
        IMPL.setLayerType($r0, $i0, $r1);
    }

    public static int getLayerType(View $r0) throws  {
        return IMPL.getLayerType($r0);
    }

    public static int getLabelFor(View $r0) throws  {
        return IMPL.getLabelFor($r0);
    }

    public static void setLabelFor(View $r0, @IdRes int $i0) throws  {
        IMPL.setLabelFor($r0, $i0);
    }

    public static void setLayerPaint(View $r0, Paint $r1) throws  {
        IMPL.setLayerPaint($r0, $r1);
    }

    public static int getLayoutDirection(View $r0) throws  {
        return IMPL.getLayoutDirection($r0);
    }

    public static void setLayoutDirection(View $r0, int $i0) throws  {
        IMPL.setLayoutDirection($r0, $i0);
    }

    public static ViewParent getParentForAccessibility(View $r0) throws  {
        return IMPL.getParentForAccessibility($r0);
    }

    public static boolean isOpaque(View $r0) throws  {
        return IMPL.isOpaque($r0);
    }

    public static int resolveSizeAndState(int $i0, int $i1, int $i2) throws  {
        return IMPL.resolveSizeAndState($i0, $i1, $i2);
    }

    public static int getMeasuredWidthAndState(View $r0) throws  {
        return IMPL.getMeasuredWidthAndState($r0);
    }

    public static int getMeasuredHeightAndState(View $r0) throws  {
        return IMPL.getMeasuredHeightAndState($r0);
    }

    public static int getMeasuredState(View $r0) throws  {
        return IMPL.getMeasuredState($r0);
    }

    public static int combineMeasuredStates(int $i0, int $i1) throws  {
        return IMPL.combineMeasuredStates($i0, $i1);
    }

    public static int getAccessibilityLiveRegion(View $r0) throws  {
        return IMPL.getAccessibilityLiveRegion($r0);
    }

    public static void setAccessibilityLiveRegion(View $r0, int $i0) throws  {
        IMPL.setAccessibilityLiveRegion($r0, $i0);
    }

    public static int getPaddingStart(View $r0) throws  {
        return IMPL.getPaddingStart($r0);
    }

    public static int getPaddingEnd(View $r0) throws  {
        return IMPL.getPaddingEnd($r0);
    }

    public static void setPaddingRelative(View $r0, int $i0, int $i1, int $i2, int $i3) throws  {
        IMPL.setPaddingRelative($r0, $i0, $i1, $i2, $i3);
    }

    public static void dispatchStartTemporaryDetach(View $r0) throws  {
        IMPL.dispatchStartTemporaryDetach($r0);
    }

    public static void dispatchFinishTemporaryDetach(View $r0) throws  {
        IMPL.dispatchFinishTemporaryDetach($r0);
    }

    public static float getTranslationX(View $r0) throws  {
        return IMPL.getTranslationX($r0);
    }

    public static float getTranslationY(View $r0) throws  {
        return IMPL.getTranslationY($r0);
    }

    public static int getMinimumWidth(View $r0) throws  {
        return IMPL.getMinimumWidth($r0);
    }

    public static int getMinimumHeight(View $r0) throws  {
        return IMPL.getMinimumHeight($r0);
    }

    public static ViewPropertyAnimatorCompat animate(View $r0) throws  {
        return IMPL.animate($r0);
    }

    public static void setTranslationX(View $r0, float $f0) throws  {
        IMPL.setTranslationX($r0, $f0);
    }

    public static void setTranslationY(View $r0, float $f0) throws  {
        IMPL.setTranslationY($r0, $f0);
    }

    public static void setAlpha(View $r0, @FloatRange(from = 0.0d, to = 1.0d) float $f0) throws  {
        IMPL.setAlpha($r0, $f0);
    }

    public static void setX(View $r0, float $f0) throws  {
        IMPL.setX($r0, $f0);
    }

    public static void setY(View $r0, float $f0) throws  {
        IMPL.setY($r0, $f0);
    }

    public static void setRotation(View $r0, float $f0) throws  {
        IMPL.setRotation($r0, $f0);
    }

    public static void setRotationX(View $r0, float $f0) throws  {
        IMPL.setRotationX($r0, $f0);
    }

    public static void setRotationY(View $r0, float $f0) throws  {
        IMPL.setRotationY($r0, $f0);
    }

    public static void setScaleX(View $r0, float $f0) throws  {
        IMPL.setScaleX($r0, $f0);
    }

    public static void setScaleY(View $r0, float $f0) throws  {
        IMPL.setScaleY($r0, $f0);
    }

    public static float getPivotX(View $r0) throws  {
        return IMPL.getPivotX($r0);
    }

    public static void setPivotX(View $r0, float $f0) throws  {
        IMPL.setPivotX($r0, $f0);
    }

    public static float getPivotY(View $r0) throws  {
        return IMPL.getPivotY($r0);
    }

    public static void setPivotY(View $r0, float $f0) throws  {
        IMPL.setPivotY($r0, $f0);
    }

    public static float getRotation(View $r0) throws  {
        return IMPL.getRotation($r0);
    }

    public static float getRotationX(View $r0) throws  {
        return IMPL.getRotationX($r0);
    }

    public static float getRotationY(View $r0) throws  {
        return IMPL.getRotationY($r0);
    }

    public static float getScaleX(View $r0) throws  {
        return IMPL.getScaleX($r0);
    }

    public static float getScaleY(View $r0) throws  {
        return IMPL.getScaleY($r0);
    }

    public static float getX(View $r0) throws  {
        return IMPL.getX($r0);
    }

    public static float getY(View $r0) throws  {
        return IMPL.getY($r0);
    }

    public static void setElevation(View $r0, float $f0) throws  {
        IMPL.setElevation($r0, $f0);
    }

    public static float getElevation(View $r0) throws  {
        return IMPL.getElevation($r0);
    }

    public static void setTranslationZ(View $r0, float $f0) throws  {
        IMPL.setTranslationZ($r0, $f0);
    }

    public static float getTranslationZ(View $r0) throws  {
        return IMPL.getTranslationZ($r0);
    }

    public static void setTransitionName(View $r0, String $r1) throws  {
        IMPL.setTransitionName($r0, $r1);
    }

    public static String getTransitionName(View $r0) throws  {
        return IMPL.getTransitionName($r0);
    }

    public static int getWindowSystemUiVisibility(View $r0) throws  {
        return IMPL.getWindowSystemUiVisibility($r0);
    }

    public static void requestApplyInsets(View $r0) throws  {
        IMPL.requestApplyInsets($r0);
    }

    public static void setChildrenDrawingOrderEnabled(ViewGroup $r0, boolean $z0) throws  {
        IMPL.setChildrenDrawingOrderEnabled($r0, $z0);
    }

    public static boolean getFitsSystemWindows(View $r0) throws  {
        return IMPL.getFitsSystemWindows($r0);
    }

    public static void setFitsSystemWindows(View $r0, boolean $z0) throws  {
        IMPL.setFitsSystemWindows($r0, $z0);
    }

    public static void jumpDrawablesToCurrentState(View $r0) throws  {
        IMPL.jumpDrawablesToCurrentState($r0);
    }

    public static void setOnApplyWindowInsetsListener(View $r0, OnApplyWindowInsetsListener $r1) throws  {
        IMPL.setOnApplyWindowInsetsListener($r0, $r1);
    }

    public static WindowInsetsCompat onApplyWindowInsets(View $r0, WindowInsetsCompat $r1) throws  {
        return IMPL.onApplyWindowInsets($r0, $r1);
    }

    public static WindowInsetsCompat dispatchApplyWindowInsets(View $r0, WindowInsetsCompat $r1) throws  {
        return IMPL.dispatchApplyWindowInsets($r0, $r1);
    }

    public static void setSaveFromParentEnabled(View $r0, boolean $z0) throws  {
        IMPL.setSaveFromParentEnabled($r0, $z0);
    }

    public static void setActivated(View $r0, boolean $z0) throws  {
        IMPL.setActivated($r0, $z0);
    }

    public static boolean hasOverlappingRendering(View $r0) throws  {
        return IMPL.hasOverlappingRendering($r0);
    }

    public static boolean isPaddingRelative(View $r0) throws  {
        return IMPL.isPaddingRelative($r0);
    }

    public static ColorStateList getBackgroundTintList(View $r0) throws  {
        return IMPL.getBackgroundTintList($r0);
    }

    public static void setBackgroundTintList(View $r0, ColorStateList $r1) throws  {
        IMPL.setBackgroundTintList($r0, $r1);
    }

    public static Mode getBackgroundTintMode(View $r0) throws  {
        return IMPL.getBackgroundTintMode($r0);
    }

    public static void setBackgroundTintMode(View $r0, Mode $r1) throws  {
        IMPL.setBackgroundTintMode($r0, $r1);
    }

    public static void setNestedScrollingEnabled(View $r0, boolean $z0) throws  {
        IMPL.setNestedScrollingEnabled($r0, $z0);
    }

    public static boolean isNestedScrollingEnabled(View $r0) throws  {
        return IMPL.isNestedScrollingEnabled($r0);
    }

    public static boolean startNestedScroll(View $r0, int $i0) throws  {
        return IMPL.startNestedScroll($r0, $i0);
    }

    public static void stopNestedScroll(View $r0) throws  {
        IMPL.stopNestedScroll($r0);
    }

    public static boolean hasNestedScrollingParent(View $r0) throws  {
        return IMPL.hasNestedScrollingParent($r0);
    }

    public static boolean dispatchNestedScroll(View $r0, int $i0, int $i1, int $i2, int $i3, int[] $r1) throws  {
        return IMPL.dispatchNestedScroll($r0, $i0, $i1, $i2, $i3, $r1);
    }

    public static boolean dispatchNestedPreScroll(View $r0, int $i0, int $i1, int[] $r1, int[] $r2) throws  {
        return IMPL.dispatchNestedPreScroll($r0, $i0, $i1, $r1, $r2);
    }

    public static boolean dispatchNestedFling(View $r0, float $f0, float $f1, boolean $z0) throws  {
        return IMPL.dispatchNestedFling($r0, $f0, $f1, $z0);
    }

    public static boolean dispatchNestedPreFling(View $r0, float $f0, float $f1) throws  {
        return IMPL.dispatchNestedPreFling($r0, $f0, $f1);
    }

    public static boolean isLaidOut(View $r0) throws  {
        return IMPL.isLaidOut($r0);
    }

    public static float getZ(View $r0) throws  {
        return IMPL.getZ($r0);
    }

    public static void offsetTopAndBottom(View $r0, int $i0) throws  {
        IMPL.offsetTopAndBottom($r0, $i0);
    }

    public static void offsetLeftAndRight(View $r0, int $i0) throws  {
        IMPL.offsetLeftAndRight($r0, $i0);
    }

    public static void setClipBounds(View $r0, Rect $r1) throws  {
        IMPL.setClipBounds($r0, $r1);
    }

    public static Rect getClipBounds(View $r0) throws  {
        return IMPL.getClipBounds($r0);
    }

    public static boolean isAttachedToWindow(View $r0) throws  {
        return IMPL.isAttachedToWindow($r0);
    }

    public static boolean hasOnClickListeners(View $r0) throws  {
        return IMPL.hasOnClickListeners($r0);
    }

    public static void setScrollIndicators(@NonNull View $r0, int $i0) throws  {
        IMPL.setScrollIndicators($r0, $i0);
    }

    public static void setScrollIndicators(@NonNull View $r0, int $i0, int $i1) throws  {
        IMPL.setScrollIndicators($r0, $i0, $i1);
    }

    public static int getScrollIndicators(@NonNull View $r0) throws  {
        return IMPL.getScrollIndicators($r0);
    }

    private ViewCompat() throws  {
    }
}
