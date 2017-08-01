package android.support.v4.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.SystemClock;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.AccessibilityDelegateCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.KeyEventCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewGroupCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityActionCompat;
import android.support.v4.widget.ViewDragHelper.Callback;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import com.waze.map.CanvasFont;
import dalvik.annotation.Signature;
import java.util.ArrayList;
import java.util.List;

public class DrawerLayout extends ViewGroup implements DrawerLayoutImpl {
    private static final boolean ALLOW_EDGE_LOCK = false;
    private static final boolean CAN_HIDE_DESCENDANTS = (VERSION.SDK_INT >= 19);
    private static final boolean CHILDREN_DISALLOW_INTERCEPT = true;
    private static final int DEFAULT_SCRIM_COLOR = -1728053248;
    private static final int DRAWER_ELEVATION = 10;
    static final DrawerLayoutCompatImpl IMPL;
    private static final int[] LAYOUT_ATTRS = new int[]{16842931};
    public static final int LOCK_MODE_LOCKED_CLOSED = 1;
    public static final int LOCK_MODE_LOCKED_OPEN = 2;
    public static final int LOCK_MODE_UNDEFINED = 3;
    public static final int LOCK_MODE_UNLOCKED = 0;
    private static final int MIN_DRAWER_MARGIN = 64;
    private static final int MIN_FLING_VELOCITY = 400;
    private static final int PEEK_DELAY = 160;
    private static final boolean SET_DRAWER_SHADOW_FROM_ELEVATION;
    public static final int STATE_DRAGGING = 1;
    public static final int STATE_IDLE = 0;
    public static final int STATE_SETTLING = 2;
    private static final String TAG = "DrawerLayout";
    private static final float TOUCH_SLOP_SENSITIVITY = 1.0f;
    private final ChildAccessibilityDelegate mChildAccessibilityDelegate;
    private boolean mChildrenCanceledTouch;
    private boolean mDisallowInterceptRequested;
    private boolean mDrawStatusBarBackground;
    private float mDrawerElevation;
    private int mDrawerState;
    private boolean mFirstLayout;
    private boolean mInLayout;
    private float mInitialMotionX;
    private float mInitialMotionY;
    private Object mLastInsets;
    private final ViewDragCallback mLeftCallback;
    private final ViewDragHelper mLeftDragger;
    @Nullable
    @Deprecated
    private DrawerListener mListener;
    private List<DrawerListener> mListeners;
    private int mLockModeEnd;
    private int mLockModeLeft;
    private int mLockModeRight;
    private int mLockModeStart;
    private int mMinDrawerMargin;
    private final ArrayList<View> mNonDrawerViews;
    private final ViewDragCallback mRightCallback;
    private final ViewDragHelper mRightDragger;
    private int mScrimColor;
    private float mScrimOpacity;
    private Paint mScrimPaint;
    private Drawable mShadowEnd;
    private Drawable mShadowLeft;
    private Drawable mShadowLeftResolved;
    private Drawable mShadowRight;
    private Drawable mShadowRightResolved;
    private Drawable mShadowStart;
    private Drawable mStatusBarBackground;
    private CharSequence mTitleLeft;
    private CharSequence mTitleRight;

    public interface DrawerListener {
        void onDrawerClosed(View view) throws ;

        void onDrawerOpened(View view) throws ;

        void onDrawerSlide(View view, float f) throws ;

        void onDrawerStateChanged(int i) throws ;
    }

    class AccessibilityDelegate extends AccessibilityDelegateCompat {
        private final Rect mTmpRect = new Rect();

        AccessibilityDelegate() throws  {
        }

        public void onInitializeAccessibilityNodeInfo(View $r2, AccessibilityNodeInfoCompat $r1) throws  {
            if (DrawerLayout.CAN_HIDE_DESCENDANTS) {
                super.onInitializeAccessibilityNodeInfo($r2, $r1);
            } else {
                AccessibilityNodeInfoCompat $r6 = AccessibilityNodeInfoCompat.obtain($r1);
                super.onInitializeAccessibilityNodeInfo($r2, $r6);
                $r1.setSource($r2);
                ViewParent $r7 = ViewCompat.getParentForAccessibility($r2);
                if ($r7 instanceof View) {
                    $r1.setParent((View) $r7);
                }
                copyNodeInfoNoChildren($r1, $r6);
                $r6.recycle();
                addChildrenForAccessibility($r1, (ViewGroup) $r2);
            }
            $r1.setClassName(DrawerLayout.class.getName());
            $r1.setFocusable(false);
            $r1.setFocused(false);
            $r1.removeAction(AccessibilityActionCompat.ACTION_FOCUS);
            $r1.removeAction(AccessibilityActionCompat.ACTION_CLEAR_FOCUS);
        }

        public void onInitializeAccessibilityEvent(View $r1, AccessibilityEvent $r2) throws  {
            super.onInitializeAccessibilityEvent($r1, $r2);
            $r2.setClassName(DrawerLayout.class.getName());
        }

        public boolean dispatchPopulateAccessibilityEvent(View $r1, AccessibilityEvent $r2) throws  {
            if ($r2.getEventType() != 32) {
                return super.dispatchPopulateAccessibilityEvent($r1, $r2);
            }
            List $r3 = $r2.getText();
            $r1 = DrawerLayout.this.findVisibleDrawer();
            if ($r1 != null) {
                CharSequence $r5 = DrawerLayout.this.getDrawerTitle(DrawerLayout.this.getDrawerViewAbsoluteGravity($r1));
                if ($r5 != null) {
                    $r3.add($r5);
                }
            }
            return true;
        }

        public boolean onRequestSendAccessibilityEvent(ViewGroup $r1, View $r2, AccessibilityEvent $r3) throws  {
            if (DrawerLayout.CAN_HIDE_DESCENDANTS || DrawerLayout.includeChildForAccessibility($r2)) {
                return super.onRequestSendAccessibilityEvent($r1, $r2, $r3);
            }
            return false;
        }

        private void addChildrenForAccessibility(AccessibilityNodeInfoCompat $r1, ViewGroup $r2) throws  {
            int $i0 = $r2.getChildCount();
            for (int $i1 = 0; $i1 < $i0; $i1++) {
                View $r3 = $r2.getChildAt($i1);
                if (DrawerLayout.includeChildForAccessibility($r3)) {
                    $r1.addChild($r3);
                }
            }
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
        }
    }

    final class ChildAccessibilityDelegate extends AccessibilityDelegateCompat {
        ChildAccessibilityDelegate() throws  {
        }

        public void onInitializeAccessibilityNodeInfo(View $r1, AccessibilityNodeInfoCompat $r2) throws  {
            super.onInitializeAccessibilityNodeInfo($r1, $r2);
            if (!DrawerLayout.includeChildForAccessibility($r1)) {
                $r2.setParent(null);
            }
        }
    }

    interface DrawerLayoutCompatImpl {
        void applyMarginInsets(MarginLayoutParams marginLayoutParams, Object obj, int i) throws ;

        void configureApplyInsets(View view) throws ;

        void dispatchChildInsets(View view, Object obj, int i) throws ;

        Drawable getDefaultStatusBarBackground(Context context) throws ;

        int getTopInset(Object obj) throws ;
    }

    static class DrawerLayoutCompatImplApi21 implements DrawerLayoutCompatImpl {
        DrawerLayoutCompatImplApi21() throws  {
        }

        public void configureApplyInsets(View $r1) throws  {
            DrawerLayoutCompatApi21.configureApplyInsets($r1);
        }

        public void dispatchChildInsets(View $r1, Object $r2, int $i0) throws  {
            DrawerLayoutCompatApi21.dispatchChildInsets($r1, $r2, $i0);
        }

        public void applyMarginInsets(MarginLayoutParams $r1, Object $r2, int $i0) throws  {
            DrawerLayoutCompatApi21.applyMarginInsets($r1, $r2, $i0);
        }

        public int getTopInset(Object $r1) throws  {
            return DrawerLayoutCompatApi21.getTopInset($r1);
        }

        public Drawable getDefaultStatusBarBackground(Context $r1) throws  {
            return DrawerLayoutCompatApi21.getDefaultStatusBarBackground($r1);
        }
    }

    static class DrawerLayoutCompatImplBase implements DrawerLayoutCompatImpl {
        public Drawable getDefaultStatusBarBackground(Context context) throws  {
            return null;
        }

        public int getTopInset(Object insets) throws  {
            return 0;
        }

        DrawerLayoutCompatImplBase() throws  {
        }

        public void configureApplyInsets(View drawerLayout) throws  {
        }

        public void dispatchChildInsets(View child, Object insets, int drawerGravity) throws  {
        }

        public void applyMarginInsets(MarginLayoutParams lp, Object insets, int drawerGravity) throws  {
        }
    }

    public static class LayoutParams extends MarginLayoutParams {
        private static final int FLAG_IS_CLOSING = 4;
        private static final int FLAG_IS_OPENED = 1;
        private static final int FLAG_IS_OPENING = 2;
        public int gravity;
        private boolean isPeeking;
        private float onScreen;
        private int openState;

        static /* synthetic */ int access$176(LayoutParams $r0, int $i0) throws  {
            $i0 = $r0.openState | $i0;
            $r0.openState = $i0;
            return $i0;
        }

        public LayoutParams(Context $r1, AttributeSet $r2) throws  {
            super($r1, $r2);
            this.gravity = 0;
            TypedArray $r4 = $r1.obtainStyledAttributes($r2, DrawerLayout.LAYOUT_ATTRS);
            this.gravity = $r4.getInt(0, 0);
            $r4.recycle();
        }

        public LayoutParams(int $i0, int $i1) throws  {
            super($i0, $i1);
            this.gravity = 0;
        }

        public LayoutParams(int $i0, int $i1, int $i2) throws  {
            this($i0, $i1);
            this.gravity = $i2;
        }

        public LayoutParams(LayoutParams $r1) throws  {
            super($r1);
            this.gravity = 0;
            this.gravity = $r1.gravity;
        }

        public LayoutParams(android.view.ViewGroup.LayoutParams $r1) throws  {
            super($r1);
            this.gravity = 0;
        }

        public LayoutParams(MarginLayoutParams $r1) throws  {
            super($r1);
            this.gravity = 0;
        }
    }

    protected static class SavedState extends BaseSavedState {
        public static final Creator<SavedState> CREATOR = new C01421();
        int lockModeEnd;
        int lockModeLeft;
        int lockModeRight;
        int lockModeStart;
        int openDrawerGravity = 0;

        static class C01421 implements Creator<SavedState> {
            C01421() throws  {
            }

            public SavedState createFromParcel(Parcel $r1) throws  {
                return new SavedState($r1);
            }

            public SavedState[] newArray(int $i0) throws  {
                return new SavedState[$i0];
            }
        }

        public SavedState(Parcel $r1) throws  {
            super($r1);
            this.openDrawerGravity = $r1.readInt();
            this.lockModeLeft = $r1.readInt();
            this.lockModeRight = $r1.readInt();
            this.lockModeStart = $r1.readInt();
            this.lockModeEnd = $r1.readInt();
        }

        public SavedState(Parcelable $r1) throws  {
            super($r1);
        }

        public void writeToParcel(Parcel $r1, int $i0) throws  {
            super.writeToParcel($r1, $i0);
            $r1.writeInt(this.openDrawerGravity);
            $r1.writeInt(this.lockModeLeft);
            $r1.writeInt(this.lockModeRight);
            $r1.writeInt(this.lockModeStart);
            $r1.writeInt(this.lockModeEnd);
        }
    }

    public static abstract class SimpleDrawerListener implements DrawerListener {
        public void onDrawerSlide(View drawerView, float slideOffset) throws  {
        }

        public void onDrawerOpened(View drawerView) throws  {
        }

        public void onDrawerClosed(View drawerView) throws  {
        }

        public void onDrawerStateChanged(int newState) throws  {
        }
    }

    private class ViewDragCallback extends Callback {
        private final int mAbsGravity;
        private ViewDragHelper mDragger;
        private final Runnable mPeekRunnable = new C01431();

        class C01431 implements Runnable {
            C01431() throws  {
            }

            public void run() throws  {
                ViewDragCallback.this.peekDrawer();
            }
        }

        public boolean onEdgeLock(int edgeFlags) throws  {
            return false;
        }

        public ViewDragCallback(int $i0) throws  {
            this.mAbsGravity = $i0;
        }

        public void setDragger(ViewDragHelper $r1) throws  {
            this.mDragger = $r1;
        }

        public void removeCallbacks() throws  {
            DrawerLayout.this.removeCallbacks(this.mPeekRunnable);
        }

        public boolean tryCaptureView(View $r1, int pointerId) throws  {
            return DrawerLayout.this.isDrawerView($r1) && DrawerLayout.this.checkDrawerViewAbsoluteGravity($r1, this.mAbsGravity) && DrawerLayout.this.getDrawerLockMode($r1) == 0;
        }

        public void onViewDragStateChanged(int $i0) throws  {
            DrawerLayout.this.updateDrawerState(this.mAbsGravity, $i0, this.mDragger.getCapturedView());
        }

        public void onViewPositionChanged(View $r1, int $i0, int top, int dx, int dy) throws  {
            float $f0;
            byte $b4;
            top = $r1.getWidth();
            if (DrawerLayout.this.checkDrawerViewAbsoluteGravity($r1, 3)) {
                $f0 = ((float) (top + $i0)) / ((float) top);
            } else {
                $f0 = ((float) (DrawerLayout.this.getWidth() - $i0)) / ((float) top);
            }
            DrawerLayout.this.setDrawerViewOffset($r1, $f0);
            if ($f0 == 0.0f) {
                $b4 = (byte) 4;
            } else {
                $b4 = (byte) 0;
            }
            $r1.setVisibility($b4);
            DrawerLayout.this.invalidate();
        }

        public void onViewCaptured(View $r1, int activePointerId) throws  {
            ((LayoutParams) $r1.getLayoutParams()).isPeeking = false;
            closeOtherDrawer();
        }

        private void closeOtherDrawer() throws  {
            byte $b0 = (byte) 3;
            if (this.mAbsGravity == 3) {
                $b0 = (byte) 5;
            }
            View $r1 = DrawerLayout.this.findDrawerWithGravity($b0);
            if ($r1 != null) {
                DrawerLayout.this.closeDrawer($r1);
            }
        }

        public void onViewReleased(View $r1, float $f0, float yvel) throws  {
            yvel = DrawerLayout.this.getDrawerViewOffset($r1);
            int $i0 = $r1.getWidth();
            if (DrawerLayout.this.checkDrawerViewAbsoluteGravity($r1, 3)) {
                $i0 = ($f0 > 0.0f || ($f0 == 0.0f && yvel > CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR)) ? 0 : -$i0;
            } else {
                int $i2 = DrawerLayout.this.getWidth();
                $i0 = ($f0 < 0.0f || ($f0 == 0.0f && yvel > CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR)) ? $i2 - $i0 : $i2;
            }
            this.mDragger.settleCapturedViewAt($i0, $r1.getTop());
            DrawerLayout.this.invalidate();
        }

        public void onEdgeTouched(int edgeFlags, int pointerId) throws  {
            DrawerLayout.this.postDelayed(this.mPeekRunnable, 160);
        }

        private void peekDrawer() throws  {
            View $r4;
            int $i0 = 0;
            int $i1 = this.mDragger.getEdgeSize();
            boolean $z0 = this.mAbsGravity == 3;
            if ($z0) {
                View $r3 = DrawerLayout.this.findDrawerWithGravity(3);
                $r4 = $r3;
                if ($r3 != null) {
                    $i0 = -$r3.getWidth();
                }
                $i1 = $i0 + $i1;
            } else {
                $r4 = DrawerLayout.this.findDrawerWithGravity(5);
                $i1 = DrawerLayout.this.getWidth() - $i1;
            }
            if ($r4 == null) {
                return;
            }
            if ((($z0 && $r4.getLeft() < $i1) || (!$z0 && $r4.getLeft() > $i1)) && DrawerLayout.this.getDrawerLockMode($r4) == 0) {
                LayoutParams $r6 = (LayoutParams) $r4.getLayoutParams();
                this.mDragger.smoothSlideViewTo($r4, $i1, $r4.getTop());
                $r6.isPeeking = true;
                DrawerLayout.this.invalidate();
                closeOtherDrawer();
                DrawerLayout.this.cancelChildViewTouch();
            }
        }

        public void onEdgeDragStarted(int $i0, int $i1) throws  {
            View $r2;
            if (($i0 & 1) == 1) {
                $r2 = DrawerLayout.this.findDrawerWithGravity(3);
            } else {
                $r2 = DrawerLayout.this.findDrawerWithGravity(5);
            }
            if ($r2 != null && DrawerLayout.this.getDrawerLockMode($r2) == 0) {
                this.mDragger.captureChildView($r2, $i1);
            }
        }

        public int getViewHorizontalDragRange(View $r1) throws  {
            return DrawerLayout.this.isDrawerView($r1) ? $r1.getWidth() : 0;
        }

        public int clampViewPositionHorizontal(View $r1, int $i0, int dx) throws  {
            if (DrawerLayout.this.checkDrawerViewAbsoluteGravity($r1, 3)) {
                return Math.max(-$r1.getWidth(), Math.min($i0, 0));
            }
            dx = DrawerLayout.this.getWidth();
            return Math.max(dx - $r1.getWidth(), Math.min($i0, dx));
        }

        public int clampViewPositionVertical(View $r1, int top, int dy) throws  {
            return $r1.getTop();
        }
    }

    static {
        boolean $z0 = true;
        if (VERSION.SDK_INT < 21) {
            $z0 = false;
        }
        SET_DRAWER_SHADOW_FROM_ELEVATION = $z0;
        if (VERSION.SDK_INT >= 21) {
            IMPL = new DrawerLayoutCompatImplApi21();
        } else {
            IMPL = new DrawerLayoutCompatImplBase();
        }
    }

    public DrawerLayout(Context $r1) throws  {
        this($r1, null);
    }

    public DrawerLayout(Context $r1, AttributeSet $r2) throws  {
        this($r1, $r2, 0);
    }

    public DrawerLayout(Context $r1, AttributeSet $r2, int $i0) throws  {
        super($r1, $r2, $i0);
        this.mChildAccessibilityDelegate = new ChildAccessibilityDelegate();
        this.mScrimColor = DEFAULT_SCRIM_COLOR;
        this.mScrimPaint = new Paint();
        this.mFirstLayout = true;
        this.mLockModeLeft = 3;
        this.mLockModeRight = 3;
        this.mLockModeStart = 3;
        this.mLockModeEnd = 3;
        this.mShadowStart = null;
        this.mShadowEnd = null;
        this.mShadowLeft = null;
        this.mShadowRight = null;
        setDescendantFocusability(262144);
        float $f0 = getResources().getDisplayMetrics().density;
        int i = (int) ((64.0f * $f0) + CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR);
        $i0 = i;
        this.mMinDrawerMargin = i;
        float $f1 = 400.0f * $f0;
        this.mLeftCallback = new ViewDragCallback(3);
        this.mRightCallback = new ViewDragCallback(5);
        this.mLeftDragger = ViewDragHelper.create(this, 1.0f, this.mLeftCallback);
        this.mLeftDragger.setEdgeTrackingEnabled(1);
        this.mLeftDragger.setMinVelocity($f1);
        this.mLeftCallback.setDragger(this.mLeftDragger);
        this.mRightDragger = ViewDragHelper.create(this, 1.0f, this.mRightCallback);
        this.mRightDragger.setEdgeTrackingEnabled(2);
        this.mRightDragger.setMinVelocity($f1);
        this.mRightCallback.setDragger(this.mRightDragger);
        setFocusableInTouchMode(true);
        ViewCompat.setImportantForAccessibility(this, 1);
        ViewCompat.setAccessibilityDelegate(this, new AccessibilityDelegate());
        ViewGroupCompat.setMotionEventSplittingEnabled(this, false);
        if (ViewCompat.getFitsSystemWindows(this)) {
            IMPL.configureApplyInsets(this);
            this.mStatusBarBackground = IMPL.getDefaultStatusBarBackground($r1);
        }
        this.mDrawerElevation = 10.0f * $f0;
        this.mNonDrawerViews = new ArrayList();
    }

    public void setDrawerElevation(float $f0) throws  {
        this.mDrawerElevation = $f0;
        for (int $i0 = 0; $i0 < getChildCount(); $i0++) {
            View $r1 = getChildAt($i0);
            if (isDrawerView($r1)) {
                ViewCompat.setElevation($r1, this.mDrawerElevation);
            }
        }
    }

    public float getDrawerElevation() throws  {
        if (SET_DRAWER_SHADOW_FROM_ELEVATION) {
            return this.mDrawerElevation;
        }
        return 0.0f;
    }

    public void setChildInsets(Object $r1, boolean $z0) throws  {
        this.mLastInsets = $r1;
        this.mDrawStatusBarBackground = $z0;
        $z0 = !$z0 && getBackground() == null;
        setWillNotDraw($z0);
        requestLayout();
    }

    public void setDrawerShadow(Drawable $r1, int $i0) throws  {
        if (!SET_DRAWER_SHADOW_FROM_ELEVATION) {
            if (($i0 & GravityCompat.START) == GravityCompat.START) {
                this.mShadowStart = $r1;
            } else if (($i0 & GravityCompat.END) == GravityCompat.END) {
                this.mShadowEnd = $r1;
            } else if (($i0 & 3) == 3) {
                this.mShadowLeft = $r1;
            } else if (($i0 & 5) == 5) {
                this.mShadowRight = $r1;
            } else {
                return;
            }
            resolveShadowDrawables();
            invalidate();
        }
    }

    public void setDrawerShadow(@DrawableRes int $i0, int $i1) throws  {
        setDrawerShadow(getResources().getDrawable($i0), $i1);
    }

    public void setScrimColor(@ColorInt int $i0) throws  {
        this.mScrimColor = $i0;
        invalidate();
    }

    @Deprecated
    public void setDrawerListener(@Deprecated DrawerListener $r1) throws  {
        if (this.mListener != null) {
            removeDrawerListener(this.mListener);
        }
        if ($r1 != null) {
            addDrawerListener($r1);
        }
        this.mListener = $r1;
    }

    public void addDrawerListener(@NonNull DrawerListener $r1) throws  {
        if ($r1 != null) {
            if (this.mListeners == null) {
                this.mListeners = new ArrayList();
            }
            this.mListeners.add($r1);
        }
    }

    public void removeDrawerListener(@NonNull DrawerListener $r1) throws  {
        if ($r1 != null && this.mListeners != null) {
            this.mListeners.remove($r1);
        }
    }

    public void setDrawerLockMode(int $i0) throws  {
        setDrawerLockMode($i0, 3);
        setDrawerLockMode($i0, 5);
    }

    public void setDrawerLockMode(int $i0, int $i1) throws  {
        int $i2 = GravityCompat.getAbsoluteGravity($i1, ViewCompat.getLayoutDirection(this));
        switch ($i1) {
            case 3:
                this.mLockModeLeft = $i0;
                break;
            case 5:
                this.mLockModeRight = $i0;
                break;
            case GravityCompat.START /*8388611*/:
                this.mLockModeStart = $i0;
                break;
            case GravityCompat.END /*8388613*/:
                this.mLockModeEnd = $i0;
                break;
            default:
                break;
        }
        if ($i0 != 0) {
            ViewDragHelper $r1;
            if ($i2 == 3) {
                $r1 = this.mLeftDragger;
            } else {
                $r1 = this.mRightDragger;
            }
            $r1.cancel();
        }
        View $r2;
        switch ($i0) {
            case 1:
                $r2 = findDrawerWithGravity($i2);
                if ($r2 != null) {
                    closeDrawer($r2);
                    return;
                }
                return;
            case 2:
                $r2 = findDrawerWithGravity($i2);
                if ($r2 != null) {
                    openDrawer($r2);
                    return;
                }
                return;
            default:
                return;
        }
    }

    public void setDrawerLockMode(int $i0, View $r1) throws  {
        if (isDrawerView($r1)) {
            setDrawerLockMode($i0, ((LayoutParams) $r1.getLayoutParams()).gravity);
            return;
        }
        throw new IllegalArgumentException("View " + $r1 + " is not a " + "drawer with appropriate layout_gravity");
    }

    public int getDrawerLockMode(int $i0) throws  {
        int $i1 = ViewCompat.getLayoutDirection(this);
        switch ($i0) {
            case 3:
                if (this.mLockModeLeft != 3) {
                    return this.mLockModeLeft;
                }
                if ($i1 == 0) {
                    $i0 = this.mLockModeStart;
                } else {
                    $i0 = this.mLockModeEnd;
                }
                if ($i0 != 3) {
                    return $i0;
                }
                break;
            case 5:
                if (this.mLockModeRight != 3) {
                    return this.mLockModeRight;
                }
                if ($i1 == 0) {
                    $i0 = this.mLockModeEnd;
                } else {
                    $i0 = this.mLockModeStart;
                }
                if ($i0 != 3) {
                    return $i0;
                }
                break;
            case GravityCompat.START /*8388611*/:
                if (this.mLockModeStart != 3) {
                    return this.mLockModeStart;
                }
                if ($i1 == 0) {
                    $i0 = this.mLockModeLeft;
                } else {
                    $i0 = this.mLockModeRight;
                }
                if ($i0 != 3) {
                    return $i0;
                }
                break;
            case GravityCompat.END /*8388613*/:
                if (this.mLockModeEnd != 3) {
                    return this.mLockModeEnd;
                }
                if ($i1 == 0) {
                    $i0 = this.mLockModeRight;
                } else {
                    $i0 = this.mLockModeLeft;
                }
                if ($i0 != 3) {
                    return $i0;
                }
                break;
            default:
                break;
        }
        return 0;
    }

    public int getDrawerLockMode(View $r1) throws  {
        if (isDrawerView($r1)) {
            return getDrawerLockMode(((LayoutParams) $r1.getLayoutParams()).gravity);
        }
        throw new IllegalArgumentException("View " + $r1 + " is not a drawer");
    }

    public void setDrawerTitle(int $i0, CharSequence $r1) throws  {
        $i0 = GravityCompat.getAbsoluteGravity($i0, ViewCompat.getLayoutDirection(this));
        if ($i0 == 3) {
            this.mTitleLeft = $r1;
        } else if ($i0 == 5) {
            this.mTitleRight = $r1;
        }
    }

    @Nullable
    public CharSequence getDrawerTitle(int $i0) throws  {
        $i0 = GravityCompat.getAbsoluteGravity($i0, ViewCompat.getLayoutDirection(this));
        if ($i0 == 3) {
            return this.mTitleLeft;
        }
        return $i0 == 5 ? this.mTitleRight : null;
    }

    void updateDrawerState(int forGravity, int $i1, View $r1) throws  {
        byte $b3;
        forGravity = this.mLeftDragger.getViewDragState();
        int $i2 = this.mRightDragger.getViewDragState();
        if (forGravity == 1 || $i2 == 1) {
            $b3 = (byte) 1;
        } else if (forGravity == 2 || $i2 == 2) {
            $b3 = (byte) 2;
        } else {
            $b3 = (byte) 0;
        }
        if ($r1 != null && $i1 == 0) {
            LayoutParams $r4 = (LayoutParams) $r1.getLayoutParams();
            if ($r4.onScreen == 0.0f) {
                dispatchOnDrawerClosed($r1);
            } else if ($r4.onScreen == 1.0f) {
                dispatchOnDrawerOpened($r1);
            }
        }
        byte $i0 = this.mDrawerState;
        if ($b3 != $i0) {
            this.mDrawerState = $b3;
            if (this.mListeners != null) {
                for (forGravity = this.mListeners.size() - 1; forGravity >= 0; forGravity--) {
                    ((DrawerListener) this.mListeners.get(forGravity)).onDrawerStateChanged($b3);
                }
            }
        }
    }

    void dispatchOnDrawerClosed(View $r1) throws  {
        LayoutParams $r3 = (LayoutParams) $r1.getLayoutParams();
        if (($r3.openState & 1) == 1) {
            $r3.openState = 0;
            if (this.mListeners != null) {
                for (int $i0 = this.mListeners.size() - 1; $i0 >= 0; $i0--) {
                    ((DrawerListener) this.mListeners.get($i0)).onDrawerClosed($r1);
                }
            }
            updateChildrenImportantForAccessibility($r1, false);
            if (hasWindowFocus()) {
                $r1 = getRootView();
                if ($r1 != null) {
                    $r1.sendAccessibilityEvent(32);
                }
            }
        }
    }

    void dispatchOnDrawerOpened(View $r1) throws  {
        LayoutParams $r3 = (LayoutParams) $r1.getLayoutParams();
        if (($r3.openState & 1) == 0) {
            $r3.openState = 1;
            if (this.mListeners != null) {
                for (int $i0 = this.mListeners.size() - 1; $i0 >= 0; $i0--) {
                    ((DrawerListener) this.mListeners.get($i0)).onDrawerOpened($r1);
                }
            }
            updateChildrenImportantForAccessibility($r1, true);
            if (hasWindowFocus()) {
                sendAccessibilityEvent(32);
            }
            $r1.requestFocus();
        }
    }

    private void updateChildrenImportantForAccessibility(View $r1, boolean $z0) throws  {
        int $i0 = getChildCount();
        for (int $i1 = 0; $i1 < $i0; $i1++) {
            View $r2 = getChildAt($i1);
            if (($z0 || isDrawerView($r2)) && !($z0 && $r2 == $r1)) {
                ViewCompat.setImportantForAccessibility($r2, 4);
            } else {
                ViewCompat.setImportantForAccessibility($r2, 1);
            }
        }
    }

    void dispatchOnDrawerSlide(View $r1, float $f0) throws  {
        if (this.mListeners != null) {
            for (int $i0 = this.mListeners.size() - 1; $i0 >= 0; $i0--) {
                ((DrawerListener) this.mListeners.get($i0)).onDrawerSlide($r1, $f0);
            }
        }
    }

    void setDrawerViewOffset(View $r1, float $f0) throws  {
        LayoutParams $r3 = (LayoutParams) $r1.getLayoutParams();
        if ($f0 != $r3.onScreen) {
            $r3.onScreen = $f0;
            dispatchOnDrawerSlide($r1, $f0);
        }
    }

    float getDrawerViewOffset(View $r1) throws  {
        return ((LayoutParams) $r1.getLayoutParams()).onScreen;
    }

    int getDrawerViewAbsoluteGravity(View $r1) throws  {
        return GravityCompat.getAbsoluteGravity(((LayoutParams) $r1.getLayoutParams()).gravity, ViewCompat.getLayoutDirection(this));
    }

    boolean checkDrawerViewAbsoluteGravity(View $r1, int $i0) throws  {
        return (getDrawerViewAbsoluteGravity($r1) & $i0) == $i0;
    }

    View findOpenDrawer() throws  {
        int $i0 = getChildCount();
        for (int $i1 = 0; $i1 < $i0; $i1++) {
            View $r1 = getChildAt($i1);
            if ((((LayoutParams) $r1.getLayoutParams()).openState & 1) == 1) {
                return $r1;
            }
        }
        return null;
    }

    void moveDrawerToOffset(View $r1, float $f0) throws  {
        float $f1 = getDrawerViewOffset($r1);
        int $i0 = $r1.getWidth();
        int $i1 = ((int) (((float) $i0) * $f0)) - ((int) (((float) $i0) * $f1));
        if (!checkDrawerViewAbsoluteGravity($r1, 3)) {
            $i1 = -$i1;
        }
        $r1.offsetLeftAndRight($i1);
        setDrawerViewOffset($r1, $f0);
    }

    View findDrawerWithGravity(int $i0) throws  {
        $i0 = GravityCompat.getAbsoluteGravity($i0, ViewCompat.getLayoutDirection(this)) & 7;
        int $i1 = getChildCount();
        for (int $i2 = 0; $i2 < $i1; $i2++) {
            View $r1 = getChildAt($i2);
            if ((getDrawerViewAbsoluteGravity($r1) & 7) == $i0) {
                return $r1;
            }
        }
        return null;
    }

    static String gravityToString(int $i0) throws  {
        if (($i0 & 3) == 3) {
            return "LEFT";
        }
        if (($i0 & 5) == 5) {
            return "RIGHT";
        }
        return Integer.toHexString($i0);
    }

    protected void onDetachedFromWindow() throws  {
        super.onDetachedFromWindow();
        this.mFirstLayout = true;
    }

    protected void onAttachedToWindow() throws  {
        super.onAttachedToWindow();
        this.mFirstLayout = true;
    }

    protected void onMeasure(int $i0, int $i1) throws  {
        int $i3 = MeasureSpec.getMode($i0);
        int $i4 = MeasureSpec.getMode($i1);
        int $i5 = MeasureSpec.getSize($i0);
        int $i6 = MeasureSpec.getSize($i1);
        if (!($i3 == 1073741824 && $i4 == 1073741824)) {
            if (isInEditMode()) {
                if ($i3 != Integer.MIN_VALUE && $i3 == 0) {
                    $i5 = 300;
                }
                if ($i4 != Integer.MIN_VALUE && $i4 == 0) {
                    $i6 = 300;
                }
            } else {
                throw new IllegalArgumentException("DrawerLayout must be measured with MeasureSpec.EXACTLY.");
            }
        }
        setMeasuredDimension($i5, $i6);
        boolean $z0 = this.mLastInsets != null && ViewCompat.getFitsSystemWindows(this);
        $i3 = ViewCompat.getLayoutDirection(this);
        boolean $z1 = false;
        boolean $z2 = false;
        $i4 = getChildCount();
        for (int $i7 = 0; $i7 < $i4; $i7++) {
            View $r2 = getChildAt($i7);
            if ($r2.getVisibility() != 8) {
                int $i2;
                MarginLayoutParams $r5 = (LayoutParams) $r2.getLayoutParams();
                if ($z0) {
                    $i2 = GravityCompat.getAbsoluteGravity($r5.gravity, $i3);
                    if (ViewCompat.getFitsSystemWindows($r2)) {
                        IMPL.dispatchChildInsets($r2, this.mLastInsets, $i2);
                    } else {
                        IMPL.applyMarginInsets($r5, this.mLastInsets, $i2);
                    }
                }
                int $i8;
                if (isContentView($r2)) {
                    $i2 = $i5 - $r5.leftMargin;
                    $i8 = $r5.rightMargin;
                    $r2.measure(MeasureSpec.makeMeasureSpec($i2 - $i8, 1073741824), MeasureSpec.makeMeasureSpec(($i6 - $r5.topMargin) - $r5.bottomMargin, 1073741824));
                } else if (isDrawerView($r2)) {
                    boolean $z3;
                    if (SET_DRAWER_SHADOW_FROM_ELEVATION && ViewCompat.getElevation($r2) != this.mDrawerElevation) {
                        float $f0 = this.mDrawerElevation;
                        ViewCompat.setElevation($r2, $f0);
                    }
                    $i2 = getDrawerViewAbsoluteGravity($r2) & 7;
                    if ($i2 == 3) {
                        $z3 = true;
                    } else {
                        $z3 = false;
                    }
                    if (!($z3 && $z1) && ($z3 || !$z2)) {
                        if ($z3) {
                            $z1 = true;
                        } else {
                            $z2 = true;
                        }
                        $i2 = this.mMinDrawerMargin;
                        $i8 = $r5.leftMargin;
                        $i2 += $i8;
                        $i8 = $r5.rightMargin;
                        $r2.measure(getChildMeasureSpec($i0, $i2 + $i8, $r5.width), getChildMeasureSpec($i1, $r5.topMargin + $r5.bottomMargin, $r5.height));
                    } else {
                        throw new IllegalStateException("Child drawer has absolute gravity " + gravityToString($i2) + " but this " + TAG + " already has a " + "drawer view along that edge");
                    }
                } else {
                    throw new IllegalStateException("Child " + $r2 + " at index " + $i7 + " does not have a valid layout_gravity - must be Gravity.LEFT, " + "Gravity.RIGHT or Gravity.NO_GRAVITY");
                }
            }
        }
    }

    private void resolveShadowDrawables() throws  {
        if (!SET_DRAWER_SHADOW_FROM_ELEVATION) {
            this.mShadowLeftResolved = resolveLeftShadow();
            this.mShadowRightResolved = resolveRightShadow();
        }
    }

    private Drawable resolveLeftShadow() throws  {
        int $i0 = ViewCompat.getLayoutDirection(this);
        if ($i0 == 0) {
            if (this.mShadowStart != null) {
                mirror(this.mShadowStart, $i0);
                return this.mShadowStart;
            }
        } else if (this.mShadowEnd != null) {
            mirror(this.mShadowEnd, $i0);
            return this.mShadowEnd;
        }
        return this.mShadowLeft;
    }

    private Drawable resolveRightShadow() throws  {
        int $i0 = ViewCompat.getLayoutDirection(this);
        if ($i0 == 0) {
            if (this.mShadowEnd != null) {
                mirror(this.mShadowEnd, $i0);
                return this.mShadowEnd;
            }
        } else if (this.mShadowStart != null) {
            mirror(this.mShadowStart, $i0);
            return this.mShadowStart;
        }
        return this.mShadowRight;
    }

    private boolean mirror(Drawable $r1, int $i0) throws  {
        if ($r1 == null || !DrawableCompat.isAutoMirrored($r1)) {
            return false;
        }
        DrawableCompat.setLayoutDirection($r1, $i0);
        return true;
    }

    protected void onLayout(boolean changed, int $i0, int $i1, int $i2, int $i3) throws  {
        this.mInLayout = true;
        $i0 = $i2 - $i0;
        $i2 = getChildCount();
        for (int $i5 = 0; $i5 < $i2; $i5++) {
            View $r1 = getChildAt($i5);
            if ($r1.getVisibility() != 8) {
                LayoutParams $r3 = (LayoutParams) $r1.getLayoutParams();
                if (isContentView($r1)) {
                    $r1.layout($r3.leftMargin, $r3.topMargin, $r3.leftMargin + $r1.getMeasuredWidth(), $r3.topMargin + $r1.getMeasuredHeight());
                } else {
                    int $i6;
                    float $f0;
                    int $i7 = $r1.getMeasuredWidth();
                    int $i8 = $r1.getMeasuredHeight();
                    if (checkDrawerViewAbsoluteGravity($r1, 3)) {
                        $i6 = (-$i7) + ((int) (((float) $i7) * $r3.onScreen));
                        $f0 = ((float) ($i7 + $i6)) / ((float) $i7);
                    } else {
                        $i6 = $i0 - ((int) (((float) $i7) * $r3.onScreen));
                        $f0 = ((float) ($i0 - $i6)) / ((float) $i7);
                    }
                    changed = $f0 != $r3.onScreen;
                    switch ($r3.gravity & 112) {
                        case 16:
                            int $i4 = $i3 - $i1;
                            int $i9 = ($i4 - $i8) / 2;
                            int $i11 = $r3.topMargin;
                            if ($i9 < $i11) {
                                $i9 = $r3.topMargin;
                            } else {
                                if ($i9 + $i8 > $i4 - $r3.bottomMargin) {
                                    $i9 = ($i4 - $r3.bottomMargin) - $i8;
                                }
                            }
                            $r1.layout($i6, $i9, $i6 + $i7, $i9 + $i8);
                            break;
                        case 80:
                            $i8 = $i3 - $i1;
                            $r1.layout($i6, ($i8 - $r3.bottomMargin) - $r1.getMeasuredHeight(), $i6 + $i7, $i8 - $r3.bottomMargin);
                            break;
                        default:
                            $r1.layout($i6, $r3.topMargin, $i6 + $i7, $r3.topMargin + $i8);
                            break;
                    }
                    if (changed) {
                        setDrawerViewOffset($r1, $f0);
                    }
                    byte $b10 = $r3.onScreen > 0.0f ? (byte) 0 : (byte) 4;
                    if ($r1.getVisibility() != $b10) {
                        $r1.setVisibility($b10);
                    }
                }
            }
        }
        this.mInLayout = false;
        this.mFirstLayout = false;
    }

    public void requestLayout() throws  {
        if (!this.mInLayout) {
            super.requestLayout();
        }
    }

    public void computeScroll() throws  {
        int $i0 = getChildCount();
        float $f0 = 0.0f;
        for (int $i1 = 0; $i1 < $i0; $i1++) {
            $f0 = Math.max($f0, ((LayoutParams) getChildAt($i1).getLayoutParams()).onScreen);
        }
        this.mScrimOpacity = $f0;
        if (this.mLeftDragger.continueSettling(true) | this.mRightDragger.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    private static boolean hasOpaqueBackground(View $r0) throws  {
        Drawable $r1 = $r0.getBackground();
        if ($r1 != null) {
            return $r1.getOpacity() == -1;
        } else {
            return false;
        }
    }

    public void setStatusBarBackground(Drawable $r1) throws  {
        this.mStatusBarBackground = $r1;
        invalidate();
    }

    public Drawable getStatusBarBackgroundDrawable() throws  {
        return this.mStatusBarBackground;
    }

    public void setStatusBarBackground(int $i0) throws  {
        this.mStatusBarBackground = $i0 != 0 ? ContextCompat.getDrawable(getContext(), $i0) : null;
        invalidate();
    }

    public void setStatusBarBackgroundColor(@ColorInt int $i0) throws  {
        this.mStatusBarBackground = new ColorDrawable($i0);
        invalidate();
    }

    public void onRtlPropertiesChanged(int layoutDirection) throws  {
        resolveShadowDrawables();
    }

    public void onDraw(Canvas $r1) throws  {
        super.onDraw($r1);
        if (this.mDrawStatusBarBackground && this.mStatusBarBackground != null) {
            int $i0 = IMPL.getTopInset(this.mLastInsets);
            if ($i0 > 0) {
                this.mStatusBarBackground.setBounds(0, 0, getWidth(), $i0);
                this.mStatusBarBackground.draw($r1);
            }
        }
    }

    protected boolean drawChild(Canvas $r1, View $r2, long $l0) throws  {
        int $i1 = getHeight();
        boolean $z0 = isContentView($r2);
        int $i2 = 0;
        int $i3 = getWidth();
        int $i4 = $r1.save();
        if ($z0) {
            int $i5 = getChildCount();
            for (int $i6 = 0; $i6 < $i5; $i6++) {
                View $r4 = getChildAt($i6);
                if ($r4 != $r2 && $r4.getVisibility() == 0 && hasOpaqueBackground($r4) && isDrawerView($r4) && $r4.getHeight() >= $i1) {
                    int $i7;
                    if (checkDrawerViewAbsoluteGravity($r4, 3)) {
                        $i7 = $r4.getRight();
                        if ($i7 > $i2) {
                            $i2 = $i7;
                        }
                    } else {
                        $i7 = $r4.getLeft();
                        if ($i7 < $i3) {
                            $i3 = $i7;
                        }
                    }
                }
            }
            $r1.clipRect($i2, 0, $i3, getHeight());
        }
        boolean $z1 = super.drawChild($r1, $r2, $l0);
        $r1.restoreToCount($i4);
        float f;
        if (this.mScrimOpacity > 0.0f && $z0) {
            float $f0 = ((float) ((this.mScrimColor & -16777216) >>> 24)) * this.mScrimOpacity;
            f = $f0;
            $i1 = (((int) $f0) << 24) | (this.mScrimColor & 2.3509886E-38f);
            Paint $r3 = this.mScrimPaint;
            $r3.setColor($i1);
            $r1.drawRect((float) $i2, 0.0f, (float) $i3, (float) getHeight(), this.mScrimPaint);
            return $z1;
        } else if (this.mShadowLeftResolved != null && checkDrawerViewAbsoluteGravity($r2, 3)) {
            $r5 = this.mShadowLeftResolved;
            $i1 = $r5.getIntrinsicWidth();
            $i2 = $r2.getRight();
            $r6 = this.mLeftDragger;
            f = Math.max(0.0f, Math.min(((float) $i2) / ((float) $r6.getEdgeSize()), 1.0f));
            this.mShadowLeftResolved.setBounds($i2, $r2.getTop(), $i2 + $i1, $r2.getBottom());
            this.mShadowLeftResolved.setAlpha((int) (255.0f * f));
            $r5 = this.mShadowLeftResolved;
            $r5 = $r5;
            $r5.draw($r1);
            return $z1;
        } else if (this.mShadowRightResolved == null || !checkDrawerViewAbsoluteGravity($r2, 5)) {
            return $z1;
        } else {
            $r5 = this.mShadowRightResolved;
            $i3 = $r5.getIntrinsicWidth();
            $i2 = $r2.getLeft();
            $i1 = getWidth() - $i2;
            $r6 = this.mRightDragger;
            f = Math.max(0.0f, Math.min(((float) $i1) / ((float) $r6.getEdgeSize()), 1.0f));
            this.mShadowRightResolved.setBounds($i2 - $i3, $r2.getTop(), $i2, $r2.getBottom());
            this.mShadowRightResolved.setAlpha((int) (255.0f * f));
            $r5 = this.mShadowRightResolved;
            $r5 = $r5;
            $r5.draw($r1);
            return $z1;
        }
    }

    boolean isContentView(View $r1) throws  {
        return ((LayoutParams) $r1.getLayoutParams()).gravity == 0;
    }

    boolean isDrawerView(View $r1) throws  {
        int $i0 = GravityCompat.getAbsoluteGravity(((LayoutParams) $r1.getLayoutParams()).gravity, ViewCompat.getLayoutDirection($r1));
        if (($i0 & 3) != 0) {
            return true;
        }
        return ($i0 & 5) != 0;
    }

    public boolean onInterceptTouchEvent(MotionEvent $r1) throws  {
        boolean $z0 = this.mLeftDragger.shouldInterceptTouchEvent($r1) | this.mRightDragger.shouldInterceptTouchEvent($r1);
        boolean $z1 = false;
        switch (MotionEventCompat.getActionMasked($r1)) {
            case 0:
                float $f0 = $r1.getX();
                float $f1 = $r1.getY();
                this.mInitialMotionX = $f0;
                this.mInitialMotionY = $f1;
                if (this.mScrimOpacity > 0.0f) {
                    View $r3 = this.mLeftDragger.findTopChildUnder((int) $f0, (int) $f1);
                    if ($r3 != null && isContentView($r3)) {
                        $z1 = true;
                    }
                }
                this.mDisallowInterceptRequested = false;
                this.mChildrenCanceledTouch = false;
                break;
            case 1:
            case 3:
                closeDrawers(true);
                this.mDisallowInterceptRequested = false;
                this.mChildrenCanceledTouch = false;
                break;
            case 2:
                if (this.mLeftDragger.checkTouchSlop(3)) {
                    this.mLeftCallback.removeCallbacks();
                    this.mRightCallback.removeCallbacks();
                    break;
                }
                break;
            default:
                break;
        }
        if ($z0 || $z1 || hasPeekingDrawer() || this.mChildrenCanceledTouch) {
            return true;
        }
        return false;
    }

    public boolean onTouchEvent(MotionEvent $r1) throws  {
        this.mLeftDragger.processTouchEvent($r1);
        this.mRightDragger.processTouchEvent($r1);
        float $f1;
        float $f0;
        switch ($r1.getAction() & 255) {
            case 0:
                $f1 = $r1.getX();
                $f0 = $r1.getY();
                this.mInitialMotionX = $f1;
                this.mInitialMotionY = $f0;
                this.mDisallowInterceptRequested = false;
                this.mChildrenCanceledTouch = false;
                return true;
            case 1:
                $f0 = $r1.getX();
                $f1 = $r1.getY();
                boolean $z0 = true;
                View $r3 = this.mLeftDragger.findTopChildUnder((int) $f0, (int) $f1);
                if ($r3 != null && isContentView($r3)) {
                    $f0 -= this.mInitialMotionX;
                    $f1 -= this.mInitialMotionY;
                    int $i0 = this.mLeftDragger.getTouchSlop();
                    if (($f0 * $f0) + ($f1 * $f1) < ((float) ($i0 * $i0))) {
                        $r3 = findOpenDrawer();
                        if ($r3 != null) {
                            $z0 = getDrawerLockMode($r3) == 2;
                        }
                    }
                }
                closeDrawers($z0);
                this.mDisallowInterceptRequested = false;
                return true;
            case 2:
                break;
            case 3:
                closeDrawers(true);
                this.mDisallowInterceptRequested = false;
                this.mChildrenCanceledTouch = false;
                return true;
            default:
                break;
        }
        return true;
    }

    public void requestDisallowInterceptTouchEvent(boolean $z0) throws  {
        super.requestDisallowInterceptTouchEvent($z0);
        this.mDisallowInterceptRequested = $z0;
        if ($z0) {
            closeDrawers(true);
        }
    }

    public void closeDrawers() throws  {
        closeDrawers(false);
    }

    void closeDrawers(boolean $z0) throws  {
        boolean $z1 = false;
        int $i0 = getChildCount();
        for (int $i1 = 0; $i1 < $i0; $i1++) {
            View $r1 = getChildAt($i1);
            LayoutParams $r3 = (LayoutParams) $r1.getLayoutParams();
            if (isDrawerView($r1) && (!$z0 || $r3.isPeeking)) {
                int $i2 = $r1.getWidth();
                if (checkDrawerViewAbsoluteGravity($r1, 3)) {
                    $z1 |= this.mLeftDragger.smoothSlideViewTo($r1, -$i2, $r1.getTop());
                } else {
                    $z1 |= this.mRightDragger.smoothSlideViewTo($r1, getWidth(), $r1.getTop());
                }
                $r3.isPeeking = false;
            }
        }
        this.mLeftCallback.removeCallbacks();
        this.mRightCallback.removeCallbacks();
        if ($z1) {
            invalidate();
        }
    }

    public void openDrawer(View $r1) throws  {
        if (isDrawerView($r1)) {
            LayoutParams $r6 = (LayoutParams) $r1.getLayoutParams();
            if (this.mFirstLayout) {
                $r6.onScreen = 1.0f;
                $r6.openState = 1;
                updateChildrenImportantForAccessibility($r1, true);
            } else {
                LayoutParams.access$176($r6, 2);
                if (checkDrawerViewAbsoluteGravity($r1, 3)) {
                    this.mLeftDragger.smoothSlideViewTo($r1, 0, $r1.getTop());
                } else {
                    this.mRightDragger.smoothSlideViewTo($r1, getWidth() - $r1.getWidth(), $r1.getTop());
                }
            }
            invalidate();
            return;
        }
        throw new IllegalArgumentException("View " + $r1 + " is not a sliding drawer");
    }

    public void openDrawer(int $i0) throws  {
        View $r2 = findDrawerWithGravity($i0);
        if ($r2 == null) {
            throw new IllegalArgumentException("No drawer view found with gravity " + gravityToString($i0));
        }
        openDrawer($r2);
    }

    public void closeDrawer(View $r1) throws  {
        if (isDrawerView($r1)) {
            LayoutParams $r6 = (LayoutParams) $r1.getLayoutParams();
            if (this.mFirstLayout) {
                $r6.onScreen = 0.0f;
                $r6.openState = 0;
            } else {
                LayoutParams.access$176($r6, 4);
                if (checkDrawerViewAbsoluteGravity($r1, 3)) {
                    this.mLeftDragger.smoothSlideViewTo($r1, -$r1.getWidth(), $r1.getTop());
                } else {
                    this.mRightDragger.smoothSlideViewTo($r1, getWidth(), $r1.getTop());
                }
            }
            invalidate();
            return;
        }
        throw new IllegalArgumentException("View " + $r1 + " is not a sliding drawer");
    }

    public void closeDrawer(int $i0) throws  {
        View $r2 = findDrawerWithGravity($i0);
        if ($r2 == null) {
            throw new IllegalArgumentException("No drawer view found with gravity " + gravityToString($i0));
        }
        closeDrawer($r2);
    }

    public boolean isDrawerOpen(View $r1) throws  {
        if (isDrawerView($r1)) {
            return (((LayoutParams) $r1.getLayoutParams()).openState & 1) == 1;
        } else {
            throw new IllegalArgumentException("View " + $r1 + " is not a drawer");
        }
    }

    public boolean isDrawerOpen(int $i0) throws  {
        View $r1 = findDrawerWithGravity($i0);
        if ($r1 != null) {
            return isDrawerOpen($r1);
        }
        return false;
    }

    public boolean isDrawerVisible(View $r1) throws  {
        if (isDrawerView($r1)) {
            return ((LayoutParams) $r1.getLayoutParams()).onScreen > 0.0f;
        } else {
            throw new IllegalArgumentException("View " + $r1 + " is not a drawer");
        }
    }

    public boolean isDrawerVisible(int $i0) throws  {
        View $r1 = findDrawerWithGravity($i0);
        if ($r1 != null) {
            return isDrawerVisible($r1);
        }
        return false;
    }

    private boolean hasPeekingDrawer() throws  {
        int $i0 = getChildCount();
        for (int $i1 = 0; $i1 < $i0; $i1++) {
            if (((LayoutParams) getChildAt($i1).getLayoutParams()).isPeeking) {
                return true;
            }
        }
        return false;
    }

    protected android.view.ViewGroup.LayoutParams generateDefaultLayoutParams() throws  {
        return new LayoutParams(-1, -1);
    }

    protected android.view.ViewGroup.LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams $r1) throws  {
        if ($r1 instanceof LayoutParams) {
            return new LayoutParams((LayoutParams) $r1);
        }
        return $r1 instanceof MarginLayoutParams ? new LayoutParams((MarginLayoutParams) $r1) : new LayoutParams($r1);
    }

    protected boolean checkLayoutParams(android.view.ViewGroup.LayoutParams $r1) throws  {
        return ($r1 instanceof LayoutParams) && super.checkLayoutParams($r1);
    }

    public android.view.ViewGroup.LayoutParams generateLayoutParams(AttributeSet $r1) throws  {
        return new LayoutParams(getContext(), $r1);
    }

    public void addFocusables(@Signature({"(", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;II)V"}) ArrayList<View> $r1, @Signature({"(", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;II)V"}) int $i0, @Signature({"(", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;II)V"}) int $i1) throws  {
        if (getDescendantFocusability() != 393216) {
            int $i3;
            View $r2;
            int $i2 = getChildCount();
            boolean $z0 = false;
            for ($i3 = 0; $i3 < $i2; $i3++) {
                $r2 = getChildAt($i3);
                if (!isDrawerView($r2)) {
                    this.mNonDrawerViews.add($r2);
                } else if (isDrawerOpen($r2)) {
                    $z0 = true;
                    $r2.addFocusables($r1, $i0, $i1);
                }
            }
            if (!$z0) {
                $i2 = this.mNonDrawerViews.size();
                for ($i3 = 0; $i3 < $i2; $i3++) {
                    $r2 = (View) this.mNonDrawerViews.get($i3);
                    if ($r2.getVisibility() == 0) {
                        $r2.addFocusables($r1, $i0, $i1);
                    }
                }
            }
            this.mNonDrawerViews.clear();
        }
    }

    private boolean hasVisibleDrawer() throws  {
        return findVisibleDrawer() != null;
    }

    private View findVisibleDrawer() throws  {
        int $i0 = getChildCount();
        for (int $i1 = 0; $i1 < $i0; $i1++) {
            View $r1 = getChildAt($i1);
            if (isDrawerView($r1) && isDrawerVisible($r1)) {
                return $r1;
            }
        }
        return null;
    }

    void cancelChildViewTouch() throws  {
        if (!this.mChildrenCanceledTouch) {
            long $l0 = SystemClock.uptimeMillis();
            MotionEvent $r1 = MotionEvent.obtain($l0, $l0, 3, 0.0f, 0.0f, 0);
            int $i1 = getChildCount();
            for (int $i2 = 0; $i2 < $i1; $i2++) {
                getChildAt($i2).dispatchTouchEvent($r1);
            }
            $r1.recycle();
            this.mChildrenCanceledTouch = true;
        }
    }

    public boolean onKeyDown(int $i0, KeyEvent $r1) throws  {
        if ($i0 != 4 || !hasVisibleDrawer()) {
            return super.onKeyDown($i0, $r1);
        }
        KeyEventCompat.startTracking($r1);
        return true;
    }

    public boolean onKeyUp(int $i0, KeyEvent $r1) throws  {
        if ($i0 != 4) {
            return super.onKeyUp($i0, $r1);
        }
        View $r2 = findVisibleDrawer();
        if ($r2 != null && getDrawerLockMode($r2) == 0) {
            closeDrawers();
        }
        return $r2 != null;
    }

    protected void onRestoreInstanceState(Parcelable $r1) throws  {
        if ($r1 instanceof SavedState) {
            SavedState $r2 = (SavedState) $r1;
            super.onRestoreInstanceState($r2.getSuperState());
            if ($r2.openDrawerGravity != 0) {
                View $r3 = findDrawerWithGravity($r2.openDrawerGravity);
                if ($r3 != null) {
                    openDrawer($r3);
                }
            }
            if ($r2.lockModeLeft != 3) {
                setDrawerLockMode($r2.lockModeLeft, 3);
            }
            if ($r2.lockModeRight != 3) {
                setDrawerLockMode($r2.lockModeRight, 5);
            }
            if ($r2.lockModeStart != 3) {
                setDrawerLockMode($r2.lockModeStart, (int) GravityCompat.START);
            }
            if ($r2.lockModeEnd != 3) {
                setDrawerLockMode($r2.lockModeEnd, (int) GravityCompat.END);
                return;
            }
            return;
        }
        super.onRestoreInstanceState($r1);
    }

    protected Parcelable onSaveInstanceState() throws  {
        SavedState $r1 = new SavedState(super.onSaveInstanceState());
        int $i0 = getChildCount();
        for (int $i1 = 0; $i1 < $i0; $i1++) {
            LayoutParams $r5 = (LayoutParams) getChildAt($i1).getLayoutParams();
            boolean $z0 = $r5.openState == 1;
            boolean $z1;
            if ($r5.openState == 2) {
                $z1 = true;
            } else {
                $z1 = false;
            }
            if ($z0 || $z1) {
                $r1.openDrawerGravity = $r5.gravity;
                break;
            }
        }
        $r1.lockModeLeft = this.mLockModeLeft;
        $r1.lockModeRight = this.mLockModeRight;
        $r1.lockModeStart = this.mLockModeStart;
        $r1.lockModeEnd = this.mLockModeEnd;
        return $r1;
    }

    public void addView(View $r1, int $i0, android.view.ViewGroup.LayoutParams $r2) throws  {
        super.addView($r1, $i0, $r2);
        if (findOpenDrawer() != null || isDrawerView($r1)) {
            ViewCompat.setImportantForAccessibility($r1, 4);
        } else {
            ViewCompat.setImportantForAccessibility($r1, 1);
        }
        if (!CAN_HIDE_DESCENDANTS) {
            ViewCompat.setAccessibilityDelegate($r1, this.mChildAccessibilityDelegate);
        }
    }

    private static boolean includeChildForAccessibility(View $r0) throws  {
        return (ViewCompat.getImportantForAccessibility($r0) == 4 || ViewCompat.getImportantForAccessibility($r0) == 2) ? false : true;
    }
}
