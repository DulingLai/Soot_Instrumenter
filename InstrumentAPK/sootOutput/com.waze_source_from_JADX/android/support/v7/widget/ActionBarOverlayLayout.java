package android.support.v7.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Parcelable;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter;
import android.support.v4.widget.ScrollerCompat;
import android.support.v7.appcompat.C0192R;
import android.support.v7.view.menu.MenuPresenter;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.Window.Callback;
import com.waze.map.CanvasFont;
import com.waze.strings.DisplayStrings;
import dalvik.annotation.Signature;

public class ActionBarOverlayLayout extends ViewGroup implements NestedScrollingParent, DecorContentParent {
    static final int[] ATTRS = new int[]{C0192R.attr.actionBarSize, 16842841};
    private static final String TAG = "ActionBarOverlayLayout";
    private final int ACTION_BAR_ANIMATE_DELAY;
    private int mActionBarHeight;
    private ActionBarContainer mActionBarTop;
    private ActionBarVisibilityCallback mActionBarVisibilityCallback;
    private final Runnable mAddActionBarHideOffset;
    private boolean mAnimatingForFling;
    private final Rect mBaseContentInsets;
    private final Rect mBaseInnerInsets;
    private ContentFrameLayout mContent;
    private final Rect mContentInsets;
    private ViewPropertyAnimatorCompat mCurrentActionBarTopAnimator;
    private DecorToolbar mDecorToolbar;
    private ScrollerCompat mFlingEstimator;
    private boolean mHasNonEmbeddedTabs;
    private boolean mHideOnContentScroll;
    private int mHideOnContentScrollReference;
    private boolean mIgnoreWindowContentOverlay;
    private final Rect mInnerInsets;
    private final Rect mLastBaseContentInsets;
    private final Rect mLastInnerInsets;
    private int mLastSystemUiVisibility;
    private boolean mOverlayMode;
    private final NestedScrollingParentHelper mParentHelper;
    private final Runnable mRemoveActionBarHideOffset;
    private final ViewPropertyAnimatorListener mTopAnimatorListener;
    private Drawable mWindowContentOverlay;
    private int mWindowVisibility;

    public interface ActionBarVisibilityCallback {
        void enableContentAnimations(boolean z) throws ;

        void hideForSystem() throws ;

        void onContentScrollStarted() throws ;

        void onContentScrollStopped() throws ;

        void onWindowVisibilityChanged(int i) throws ;

        void showForSystem() throws ;
    }

    class C02061 extends ViewPropertyAnimatorListenerAdapter {
        C02061() throws  {
        }

        public void onAnimationEnd(View view) throws  {
            ActionBarOverlayLayout.this.mCurrentActionBarTopAnimator = null;
            ActionBarOverlayLayout.this.mAnimatingForFling = false;
        }

        public void onAnimationCancel(View view) throws  {
            ActionBarOverlayLayout.this.mCurrentActionBarTopAnimator = null;
            ActionBarOverlayLayout.this.mAnimatingForFling = false;
        }
    }

    class C02072 implements Runnable {
        C02072() throws  {
        }

        public void run() throws  {
            ActionBarOverlayLayout.this.haltActionBarHideOffsetAnimations();
            ActionBarOverlayLayout.this.mCurrentActionBarTopAnimator = ViewCompat.animate(ActionBarOverlayLayout.this.mActionBarTop).translationY(0.0f).setListener(ActionBarOverlayLayout.this.mTopAnimatorListener);
        }
    }

    class C02083 implements Runnable {
        C02083() throws  {
        }

        public void run() throws  {
            ActionBarOverlayLayout.this.haltActionBarHideOffsetAnimations();
            ActionBarOverlayLayout.this.mCurrentActionBarTopAnimator = ViewCompat.animate(ActionBarOverlayLayout.this.mActionBarTop).translationY((float) (-ActionBarOverlayLayout.this.mActionBarTop.getHeight())).setListener(ActionBarOverlayLayout.this.mTopAnimatorListener);
        }
    }

    public static class LayoutParams extends MarginLayoutParams {
        public LayoutParams(Context $r1, AttributeSet $r2) throws  {
            super($r1, $r2);
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
    }

    protected void onMeasure(int r28, int r29) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:18:0x01ea in {2, 8, 12, 15, 17, 19, 24, 27} preds:[]
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.computeDominators(BlockProcessor.java:129)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.visit(BlockProcessor.java:38)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r27 = this;
        r0 = r27;
        r0.pullChildren();
        r7 = 0;
        r0 = r27;
        r8 = r0.mActionBarTop;
        r9 = 0;
        r10 = 0;
        r0 = r27;
        r1 = r8;
        r2 = r28;
        r3 = r9;
        r4 = r29;
        r5 = r10;
        r0.measureChildWithMargins(r1, r2, r3, r4, r5);
        r0 = r27;
        r8 = r0.mActionBarTop;
        r11 = r8.getLayoutParams();
        r13 = r11;
        r13 = (android.support.v7.widget.ActionBarOverlayLayout.LayoutParams) r13;
        r12 = r13;
        r0 = r27;
        r8 = r0.mActionBarTop;
        r14 = r8.getMeasuredWidth();
        r15 = r12.leftMargin;
        r14 = r14 + r15;
        r15 = r12.rightMargin;
        r14 = r14 + r15;
        r9 = 0;
        r16 = java.lang.Math.max(r9, r14);
        r0 = r27;
        r8 = r0.mActionBarTop;
        r14 = r8.getMeasuredHeight();
        r15 = r12.topMargin;
        r14 = r14 + r15;
        r15 = r12.bottomMargin;
        r14 = r14 + r15;
        r9 = 0;
        r15 = java.lang.Math.max(r9, r14);
        r0 = r27;
        r8 = r0.mActionBarTop;
        r14 = android.support.v4.view.ViewCompat.getMeasuredState(r8);
        r9 = 0;
        r14 = android.support.v7.widget.ViewUtils.combineMeasuredStates(r9, r14);
        r0 = r27;
        r17 = android.support.v4.view.ViewCompat.getWindowSystemUiVisibility(r0);
        r0 = r17;
        r0 = r0 & 256;
        r17 = r0;
        if (r17 == 0) goto L_0x01ee;
    L_0x0065:
        r18 = 1;
    L_0x0067:
        if (r18 == 0) goto L_0x01f1;
    L_0x0069:
        r0 = r27;
        r7 = r0.mActionBarHeight;
        r0 = r27;
        r0 = r0.mHasNonEmbeddedTabs;
        r19 = r0;
        if (r19 == 0) goto L_0x0086;
    L_0x0075:
        r0 = r27;
        r8 = r0.mActionBarTop;
        r20 = r8.getTabContainer();
        if (r20 == 0) goto L_0x0086;
    L_0x007f:
        r0 = r27;
        r0 = r0.mActionBarHeight;
        r17 = r0;
        r7 = r7 + r0;
    L_0x0086:
        r0 = r27;
        r0 = r0.mContentInsets;
        r21 = r0;
        r0 = r27;
        r0 = r0.mBaseContentInsets;
        r22 = r0;
        r0 = r21;
        r1 = r22;
        r0.set(r1);
        r0 = r27;
        r0 = r0.mInnerInsets;
        r21 = r0;
        r0 = r27;
        r0 = r0.mBaseInnerInsets;
        r22 = r0;
        r0 = r21;
        r1 = r22;
        r0.set(r1);
        r0 = r27;
        r0 = r0.mOverlayMode;
        r19 = r0;
        if (r19 != 0) goto L_0x020c;
    L_0x00b4:
        if (r18 != 0) goto L_0x020c;
    L_0x00b6:
        r0 = r27;
        r0 = r0.mContentInsets;
        r21 = r0;
        r0 = r0.top;
        r17 = r0;
        r7 = r17 + r7;
        r0 = r21;
        r0.top = r7;
        r0 = r27;
        r0 = r0.mContentInsets;
        r21 = r0;
        r7 = r0.bottom;
        r7 = r7 + 0;
        r0 = r21;
        r0.bottom = r7;
    L_0x00d4:
        r0 = r27;
        r0 = r0.mContent;
        r23 = r0;
        r0 = r27;
        r0 = r0.mContentInsets;
        r21 = r0;
        r9 = 1;
        r10 = 1;
        r24 = 1;
        r25 = 1;
        r0 = r27;
        r1 = r23;
        r2 = r21;
        r3 = r9;
        r4 = r10;
        r5 = r24;
        r6 = r25;
        r0.applyInsets(r1, r2, r3, r4, r5, r6);
        r0 = r27;
        r0 = r0.mLastInnerInsets;
        r21 = r0;
        r0 = r27;
        r0 = r0.mInnerInsets;
        r22 = r0;
        r0 = r21;
        r1 = r22;
        r18 = r0.equals(r1);
        if (r18 != 0) goto L_0x0131;
    L_0x010b:
        r0 = r27;
        r0 = r0.mLastInnerInsets;
        r21 = r0;
        r0 = r27;
        r0 = r0.mInnerInsets;
        r22 = r0;
        r0 = r21;
        r1 = r22;
        r0.set(r1);
        r0 = r27;
        r0 = r0.mContent;
        r23 = r0;
        r0 = r27;
        r0 = r0.mInnerInsets;
        r21 = r0;
        r0 = r23;
        r1 = r21;
        r0.dispatchFitSystemWindows(r1);
    L_0x0131:
        r0 = r27;
        r0 = r0.mContent;
        r23 = r0;
        r9 = 0;
        r10 = 0;
        r0 = r27;
        r1 = r23;
        r2 = r28;
        r3 = r9;
        r4 = r29;
        r5 = r10;
        r0.measureChildWithMargins(r1, r2, r3, r4, r5);
        r0 = r27;
        r0 = r0.mContent;
        r23 = r0;
        r11 = r0.getLayoutParams();
        r26 = r11;
        r26 = (android.support.v7.widget.ActionBarOverlayLayout.LayoutParams) r26;
        r12 = r26;
        r0 = r27;
        r0 = r0.mContent;
        r23 = r0;
        r7 = r0.getMeasuredWidth();
        r0 = r12.leftMargin;
        r17 = r0;
        r7 = r7 + r0;
        r0 = r12.rightMargin;
        r17 = r0;
        r7 = r7 + r0;
        r0 = r16;
        r16 = java.lang.Math.max(r0, r7);
        r0 = r27;
        r0 = r0.mContent;
        r23 = r0;
        r7 = r0.getMeasuredHeight();
        r0 = r12.topMargin;
        r17 = r0;
        r7 = r7 + r0;
        r0 = r12.bottomMargin;
        r17 = r0;
        r7 = r7 + r0;
        r15 = java.lang.Math.max(r15, r7);
        r0 = r27;
        r0 = r0.mContent;
        r23 = r0;
        r7 = android.support.v4.view.ViewCompat.getMeasuredState(r0);
        r14 = android.support.v7.widget.ViewUtils.combineMeasuredStates(r14, r7);
        r0 = r27;
        r7 = r0.getPaddingLeft();
        r0 = r27;
        r17 = r0.getPaddingRight();
        r0 = r17;
        r7 = r7 + r0;
        r0 = r16;
        r0 = r0 + r7;
        r16 = r0;
        r0 = r27;
        r7 = r0.getPaddingTop();
        r0 = r27;
        r17 = r0.getPaddingBottom();
        r0 = r17;
        r7 = r7 + r0;
        r15 = r15 + r7;
        r0 = r27;
        r7 = r0.getSuggestedMinimumHeight();
        r15 = java.lang.Math.max(r15, r7);
        r0 = r27;
        r7 = r0.getSuggestedMinimumWidth();
        r0 = r16;
        r16 = java.lang.Math.max(r0, r7);
        r0 = r16;
        r1 = r28;
        r28 = android.support.v4.view.ViewCompat.resolveSizeAndState(r0, r1, r14);
        r14 = r14 << 16;
        r0 = r29;
        r29 = android.support.v4.view.ViewCompat.resolveSizeAndState(r15, r0, r14);
        r0 = r27;
        r1 = r28;
        r2 = r29;
        r0.setMeasuredDimension(r1, r2);
        return;
        goto L_0x01ee;
    L_0x01eb:
        goto L_0x0067;
    L_0x01ee:
        r18 = 0;
        goto L_0x01eb;
    L_0x01f1:
        r0 = r27;
        r8 = r0.mActionBarTop;
        r17 = r8.getVisibility();
        r9 = 8;
        r0 = r17;
        if (r0 == r9) goto L_0x0086;
    L_0x01ff:
        r0 = r27;
        r8 = r0.mActionBarTop;
        goto L_0x0207;
    L_0x0204:
        goto L_0x0086;
    L_0x0207:
        r7 = r8.getMeasuredHeight();
        goto L_0x0204;
    L_0x020c:
        r0 = r27;
        r0 = r0.mInnerInsets;
        r21 = r0;
        r0 = r0.top;
        r17 = r0;
        r7 = r17 + r7;
        r0 = r21;
        r0.top = r7;
        r0 = r27;
        r0 = r0.mInnerInsets;
        r21 = r0;
        r7 = r0.bottom;
        r7 = r7 + 0;
        goto L_0x022a;
    L_0x0227:
        goto L_0x00d4;
    L_0x022a:
        r0 = r21;
        r0.bottom = r7;
        goto L_0x0227;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.ActionBarOverlayLayout.onMeasure(int, int):void");
    }

    public boolean onNestedPreFling(View target, float velocityX, float velocityY) throws  {
        return false;
    }

    public boolean shouldDelayChildPressedState() throws  {
        return false;
    }

    public ActionBarOverlayLayout(Context $r1) throws  {
        this($r1, null);
    }

    public ActionBarOverlayLayout(Context $r1, AttributeSet $r2) throws  {
        super($r1, $r2);
        this.mWindowVisibility = 0;
        this.mBaseContentInsets = new Rect();
        this.mLastBaseContentInsets = new Rect();
        this.mContentInsets = new Rect();
        this.mBaseInnerInsets = new Rect();
        this.mInnerInsets = new Rect();
        this.mLastInnerInsets = new Rect();
        this.ACTION_BAR_ANIMATE_DELAY = DisplayStrings.DS_SENDING_REPORT_FAILED__PLEASE_RESEND_LATER;
        this.mTopAnimatorListener = new C02061();
        this.mRemoveActionBarHideOffset = new C02072();
        this.mAddActionBarHideOffset = new C02083();
        init($r1);
        this.mParentHelper = new NestedScrollingParentHelper(this);
    }

    private void init(Context $r1) throws  {
        boolean $z0 = true;
        TypedArray $r5 = getContext().getTheme().obtainStyledAttributes(ATTRS);
        this.mActionBarHeight = $r5.getDimensionPixelSize(0, 0);
        this.mWindowContentOverlay = $r5.getDrawable(1);
        setWillNotDraw(this.mWindowContentOverlay == null);
        $r5.recycle();
        if ($r1.getApplicationInfo().targetSdkVersion >= 19) {
            $z0 = false;
        }
        this.mIgnoreWindowContentOverlay = $z0;
        this.mFlingEstimator = ScrollerCompat.create($r1);
    }

    protected void onDetachedFromWindow() throws  {
        super.onDetachedFromWindow();
        haltActionBarHideOffsetAnimations();
    }

    public void setActionBarVisibilityCallback(ActionBarVisibilityCallback $r1) throws  {
        this.mActionBarVisibilityCallback = $r1;
        if (getWindowToken() != null) {
            this.mActionBarVisibilityCallback.onWindowVisibilityChanged(this.mWindowVisibility);
            if (this.mLastSystemUiVisibility != 0) {
                onWindowSystemUiVisibilityChanged(this.mLastSystemUiVisibility);
                ViewCompat.requestApplyInsets(this);
            }
        }
    }

    public void setOverlayMode(boolean $z0) throws  {
        this.mOverlayMode = $z0;
        $z0 = $z0 && getContext().getApplicationInfo().targetSdkVersion < 19;
        this.mIgnoreWindowContentOverlay = $z0;
    }

    public boolean isInOverlayMode() throws  {
        return this.mOverlayMode;
    }

    public void setHasNonEmbeddedTabs(boolean $z0) throws  {
        this.mHasNonEmbeddedTabs = $z0;
    }

    public void setShowingForActionMode(boolean showing) throws  {
    }

    protected void onConfigurationChanged(Configuration $r1) throws  {
        if (VERSION.SDK_INT >= 8) {
            super.onConfigurationChanged($r1);
        }
        init(getContext());
        ViewCompat.requestApplyInsets(this);
    }

    public void onWindowSystemUiVisibilityChanged(int $i0) throws  {
        boolean $z2;
        boolean $z0 = true;
        if (VERSION.SDK_INT >= 16) {
            super.onWindowSystemUiVisibilityChanged($i0);
        }
        pullChildren();
        int $i1 = this.mLastSystemUiVisibility ^ $i0;
        this.mLastSystemUiVisibility = $i0;
        boolean $z1 = ($i0 & 4) == 0;
        if (($i0 & 256) != 0) {
            $z2 = true;
        } else {
            $z2 = false;
        }
        if (this.mActionBarVisibilityCallback != null) {
            ActionBarVisibilityCallback $r1 = this.mActionBarVisibilityCallback;
            if ($z2) {
                $z0 = false;
            }
            $r1.enableContentAnimations($z0);
            if ($z1 || !$z2) {
                this.mActionBarVisibilityCallback.showForSystem();
            } else {
                this.mActionBarVisibilityCallback.hideForSystem();
            }
        }
        if (($i1 & 256) != 0 && this.mActionBarVisibilityCallback != null) {
            ViewCompat.requestApplyInsets(this);
        }
    }

    protected void onWindowVisibilityChanged(int $i0) throws  {
        super.onWindowVisibilityChanged($i0);
        this.mWindowVisibility = $i0;
        if (this.mActionBarVisibilityCallback != null) {
            this.mActionBarVisibilityCallback.onWindowVisibilityChanged($i0);
        }
    }

    private boolean applyInsets(View $r1, Rect $r2, boolean $z0, boolean $z1, boolean $z2, boolean $z3) throws  {
        boolean $z4 = false;
        LayoutParams $r4 = (LayoutParams) $r1.getLayoutParams();
        if ($z0 && $r4.leftMargin != $r2.left) {
            $z4 = true;
            $r4.leftMargin = $r2.left;
        }
        if ($z1 && $r4.topMargin != $r2.top) {
            $z4 = true;
            $r4.topMargin = $r2.top;
        }
        if ($z3 && $r4.rightMargin != $r2.right) {
            $z4 = true;
            $r4.rightMargin = $r2.right;
        }
        if (!$z2 || $r4.bottomMargin == $r2.bottom) {
            return $z4;
        }
        $r4.bottomMargin = $r2.bottom;
        return true;
    }

    protected boolean fitSystemWindows(Rect $r1) throws  {
        pullChildren();
        if ((ViewCompat.getWindowSystemUiVisibility(this) & 256) == 0) {
        }
        boolean $z0 = applyInsets(this.mActionBarTop, $r1, true, true, false, true);
        this.mBaseInnerInsets.set($r1);
        ViewUtils.computeFitSystemWindows(this, this.mBaseInnerInsets, this.mBaseContentInsets);
        if (!this.mLastBaseContentInsets.equals(this.mBaseContentInsets)) {
            $z0 = true;
            this.mLastBaseContentInsets.set(this.mBaseContentInsets);
        }
        if (!$z0) {
            return true;
        }
        requestLayout();
        return true;
    }

    protected LayoutParams generateDefaultLayoutParams() throws  {
        return new LayoutParams(-1, -1);
    }

    public LayoutParams generateLayoutParams(AttributeSet $r1) throws  {
        return new LayoutParams(getContext(), $r1);
    }

    protected android.view.ViewGroup.LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams $r1) throws  {
        return new LayoutParams($r1);
    }

    protected boolean checkLayoutParams(android.view.ViewGroup.LayoutParams $r1) throws  {
        return $r1 instanceof LayoutParams;
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) throws  {
        left = getChildCount();
        top = getPaddingLeft();
        getPaddingRight();
        right = getPaddingTop();
        getPaddingBottom();
        for (bottom = 0; bottom < left; bottom++) {
            View $r1 = getChildAt(bottom);
            if ($r1.getVisibility() != 8) {
                LayoutParams $r3 = (LayoutParams) $r1.getLayoutParams();
                int $i4 = top + $r3.leftMargin;
                int $i5 = right + $r3.topMargin;
                $r1.layout($i4, $i5, $i4 + $r1.getMeasuredWidth(), $i5 + $r1.getMeasuredHeight());
            }
        }
    }

    public void draw(Canvas $r1) throws  {
        super.draw($r1);
        if (this.mWindowContentOverlay != null && !this.mIgnoreWindowContentOverlay) {
            int $i0 = this.mActionBarTop.getVisibility() == 0 ? (int) ((((float) this.mActionBarTop.getBottom()) + ViewCompat.getTranslationY(this.mActionBarTop)) + CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR) : 0;
            this.mWindowContentOverlay.setBounds(0, $i0, getWidth(), this.mWindowContentOverlay.getIntrinsicHeight() + $i0);
            this.mWindowContentOverlay.draw($r1);
        }
    }

    public boolean onStartNestedScroll(View child, View target, int $i0) throws  {
        return (($i0 & 2) == 0 || this.mActionBarTop.getVisibility() != 0) ? false : this.mHideOnContentScroll;
    }

    public void onNestedScrollAccepted(View $r1, View $r2, int $i0) throws  {
        this.mParentHelper.onNestedScrollAccepted($r1, $r2, $i0);
        this.mHideOnContentScrollReference = getActionBarHideOffset();
        haltActionBarHideOffsetAnimations();
        if (this.mActionBarVisibilityCallback != null) {
            this.mActionBarVisibilityCallback.onContentScrollStarted();
        }
    }

    public void onNestedScroll(View target, int dxConsumed, int $i1, int dxUnconsumed, int dyUnconsumed) throws  {
        this.mHideOnContentScrollReference += $i1;
        setActionBarHideOffset(this.mHideOnContentScrollReference);
    }

    public void onStopNestedScroll(View target) throws  {
        if (this.mHideOnContentScroll && !this.mAnimatingForFling) {
            if (this.mHideOnContentScrollReference <= this.mActionBarTop.getHeight()) {
                postRemoveActionBarHideOffset();
            } else {
                postAddActionBarHideOffset();
            }
        }
        if (this.mActionBarVisibilityCallback != null) {
            this.mActionBarVisibilityCallback.onContentScrollStopped();
        }
    }

    public boolean onNestedFling(View target, float $f0, float $f1, boolean $z0) throws  {
        if (!this.mHideOnContentScroll || !$z0) {
            return false;
        }
        if (shouldHideActionBarOnFling($f0, $f1)) {
            addActionBarHideOffset();
        } else {
            removeActionBarHideOffset();
        }
        this.mAnimatingForFling = true;
        return true;
    }

    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) throws  {
    }

    public int getNestedScrollAxes() throws  {
        return this.mParentHelper.getNestedScrollAxes();
    }

    void pullChildren() throws  {
        if (this.mContent == null) {
            this.mContent = (ContentFrameLayout) findViewById(C0192R.id.action_bar_activity_content);
            this.mActionBarTop = (ActionBarContainer) findViewById(C0192R.id.action_bar_container);
            this.mDecorToolbar = getDecorToolbar(findViewById(C0192R.id.action_bar));
        }
    }

    private DecorToolbar getDecorToolbar(View $r1) throws  {
        if ($r1 instanceof DecorToolbar) {
            return (DecorToolbar) $r1;
        }
        if ($r1 instanceof Toolbar) {
            return ((Toolbar) $r1).getWrapper();
        }
        throw new IllegalStateException("Can't make a decor toolbar out of " + $r1.getClass().getSimpleName());
    }

    public void setHideOnContentScrollEnabled(boolean $z0) throws  {
        if ($z0 != this.mHideOnContentScroll) {
            this.mHideOnContentScroll = $z0;
            if (!$z0) {
                haltActionBarHideOffsetAnimations();
                setActionBarHideOffset(0);
            }
        }
    }

    public boolean isHideOnContentScrollEnabled() throws  {
        return this.mHideOnContentScroll;
    }

    public int getActionBarHideOffset() throws  {
        return this.mActionBarTop != null ? -((int) ViewCompat.getTranslationY(this.mActionBarTop)) : 0;
    }

    public void setActionBarHideOffset(int $i0) throws  {
        haltActionBarHideOffsetAnimations();
        ViewCompat.setTranslationY(this.mActionBarTop, (float) (-Math.max(0, Math.min($i0, this.mActionBarTop.getHeight()))));
    }

    private void haltActionBarHideOffsetAnimations() throws  {
        removeCallbacks(this.mRemoveActionBarHideOffset);
        removeCallbacks(this.mAddActionBarHideOffset);
        if (this.mCurrentActionBarTopAnimator != null) {
            this.mCurrentActionBarTopAnimator.cancel();
        }
    }

    private void postRemoveActionBarHideOffset() throws  {
        haltActionBarHideOffsetAnimations();
        postDelayed(this.mRemoveActionBarHideOffset, 600);
    }

    private void postAddActionBarHideOffset() throws  {
        haltActionBarHideOffsetAnimations();
        postDelayed(this.mAddActionBarHideOffset, 600);
    }

    private void removeActionBarHideOffset() throws  {
        haltActionBarHideOffsetAnimations();
        this.mRemoveActionBarHideOffset.run();
    }

    private void addActionBarHideOffset() throws  {
        haltActionBarHideOffsetAnimations();
        this.mAddActionBarHideOffset.run();
    }

    private boolean shouldHideActionBarOnFling(float velocityX, float $f1) throws  {
        this.mFlingEstimator.fling(0, 0, 0, (int) $f1, 0, 0, Integer.MIN_VALUE, ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED);
        int $i0 = this.mFlingEstimator.getFinalY();
        ActionBarContainer $r2 = this.mActionBarTop;
        return $i0 > $r2.getHeight();
    }

    public void setWindowCallback(Callback $r1) throws  {
        pullChildren();
        this.mDecorToolbar.setWindowCallback($r1);
    }

    public void setWindowTitle(CharSequence $r1) throws  {
        pullChildren();
        this.mDecorToolbar.setWindowTitle($r1);
    }

    public CharSequence getTitle() throws  {
        pullChildren();
        return this.mDecorToolbar.getTitle();
    }

    public void initFeature(int $i0) throws  {
        pullChildren();
        switch ($i0) {
            case 2:
                this.mDecorToolbar.initProgress();
                return;
            case 5:
                this.mDecorToolbar.initIndeterminateProgress();
                return;
            case 109:
                setOverlayMode(true);
                return;
            default:
                return;
        }
    }

    public void setUiOptions(int uiOptions) throws  {
    }

    public boolean hasIcon() throws  {
        pullChildren();
        return this.mDecorToolbar.hasIcon();
    }

    public boolean hasLogo() throws  {
        pullChildren();
        return this.mDecorToolbar.hasLogo();
    }

    public void setIcon(int $i0) throws  {
        pullChildren();
        this.mDecorToolbar.setIcon($i0);
    }

    public void setIcon(Drawable $r1) throws  {
        pullChildren();
        this.mDecorToolbar.setIcon($r1);
    }

    public void setLogo(int $i0) throws  {
        pullChildren();
        this.mDecorToolbar.setLogo($i0);
    }

    public boolean canShowOverflowMenu() throws  {
        pullChildren();
        return this.mDecorToolbar.canShowOverflowMenu();
    }

    public boolean isOverflowMenuShowing() throws  {
        pullChildren();
        return this.mDecorToolbar.isOverflowMenuShowing();
    }

    public boolean isOverflowMenuShowPending() throws  {
        pullChildren();
        return this.mDecorToolbar.isOverflowMenuShowPending();
    }

    public boolean showOverflowMenu() throws  {
        pullChildren();
        return this.mDecorToolbar.showOverflowMenu();
    }

    public boolean hideOverflowMenu() throws  {
        pullChildren();
        return this.mDecorToolbar.hideOverflowMenu();
    }

    public void setMenuPrepared() throws  {
        pullChildren();
        this.mDecorToolbar.setMenuPrepared();
    }

    public void setMenu(Menu $r1, MenuPresenter.Callback $r2) throws  {
        pullChildren();
        this.mDecorToolbar.setMenu($r1, $r2);
    }

    public void saveToolbarHierarchyState(@Signature({"(", "Landroid/util/SparseArray", "<", "Landroid/os/Parcelable;", ">;)V"}) SparseArray<Parcelable> $r1) throws  {
        pullChildren();
        this.mDecorToolbar.saveHierarchyState($r1);
    }

    public void restoreToolbarHierarchyState(@Signature({"(", "Landroid/util/SparseArray", "<", "Landroid/os/Parcelable;", ">;)V"}) SparseArray<Parcelable> $r1) throws  {
        pullChildren();
        this.mDecorToolbar.restoreHierarchyState($r1);
    }

    public void dismissPopups() throws  {
        pullChildren();
        this.mDecorToolbar.dismissPopupMenus();
    }
}
