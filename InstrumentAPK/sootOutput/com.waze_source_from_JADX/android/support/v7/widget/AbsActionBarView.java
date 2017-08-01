package android.support.v7.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Build.VERSION;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v7.appcompat.C0192R;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;

abstract class AbsActionBarView extends ViewGroup {
    private static final int FADE_DURATION = 200;
    protected ActionMenuPresenter mActionMenuPresenter;
    protected int mContentHeight;
    private boolean mEatingHover;
    private boolean mEatingTouch;
    protected ActionMenuView mMenuView;
    protected final Context mPopupContext;
    protected final VisibilityAnimListener mVisAnimListener;
    protected ViewPropertyAnimatorCompat mVisibilityAnim;

    class C02041 implements Runnable {
        C02041() throws  {
        }

        public void run() throws  {
            AbsActionBarView.this.showOverflowMenu();
        }
    }

    protected class VisibilityAnimListener implements ViewPropertyAnimatorListener {
        private boolean mCanceled = false;
        int mFinalVisibility;

        protected VisibilityAnimListener() throws  {
        }

        public VisibilityAnimListener withFinalVisibility(ViewPropertyAnimatorCompat $r1, int $i0) throws  {
            AbsActionBarView.this.mVisibilityAnim = $r1;
            this.mFinalVisibility = $i0;
            return this;
        }

        public void onAnimationStart(View view) throws  {
            super.setVisibility(0);
            this.mCanceled = false;
        }

        public void onAnimationEnd(View view) throws  {
            if (!this.mCanceled) {
                AbsActionBarView.this.mVisibilityAnim = null;
                super.setVisibility(this.mFinalVisibility);
            }
        }

        public void onAnimationCancel(View view) throws  {
            this.mCanceled = true;
        }
    }

    protected static int next(int $i0, int $i1, boolean $z0) throws  {
        return $z0 ? $i0 - $i1 : $i0 + $i1;
    }

    AbsActionBarView(Context $r1) throws  {
        this($r1, null);
    }

    AbsActionBarView(Context $r1, AttributeSet $r2) throws  {
        this($r1, $r2, 0);
    }

    AbsActionBarView(Context $r1, AttributeSet $r2, int $i0) throws  {
        super($r1, $r2, $i0);
        this.mVisAnimListener = new VisibilityAnimListener();
        TypedValue $r3 = new TypedValue();
        if (!$r1.getTheme().resolveAttribute(C0192R.attr.actionBarPopupTheme, $r3, true) || $r3.resourceId == 0) {
            this.mPopupContext = $r1;
        } else {
            this.mPopupContext = new ContextThemeWrapper($r1, $r3.resourceId);
        }
    }

    protected void onConfigurationChanged(Configuration $r1) throws  {
        if (VERSION.SDK_INT >= 8) {
            super.onConfigurationChanged($r1);
        }
        TypedArray $r4 = getContext().obtainStyledAttributes(null, C0192R.styleable.ActionBar, C0192R.attr.actionBarStyle, 0);
        setContentHeight($r4.getLayoutDimension(C0192R.styleable.ActionBar_height, 0));
        $r4.recycle();
        if (this.mActionMenuPresenter != null) {
            this.mActionMenuPresenter.onConfigurationChanged($r1);
        }
    }

    public boolean onTouchEvent(MotionEvent $r1) throws  {
        int $i0 = MotionEventCompat.getActionMasked($r1);
        if ($i0 == 0) {
            this.mEatingTouch = false;
        }
        if (!this.mEatingTouch) {
            boolean $z0 = super.onTouchEvent($r1);
            if ($i0 == 0 && !$z0) {
                this.mEatingTouch = true;
            }
        }
        if ($i0 != 1 && $i0 != 3) {
            return true;
        }
        this.mEatingTouch = false;
        return true;
    }

    public boolean onHoverEvent(MotionEvent $r1) throws  {
        int $i0 = MotionEventCompat.getActionMasked($r1);
        if ($i0 == 9) {
            this.mEatingHover = false;
        }
        if (!this.mEatingHover) {
            boolean $z0 = super.onHoverEvent($r1);
            if ($i0 == 9 && !$z0) {
                this.mEatingHover = true;
            }
        }
        if ($i0 != 10 && $i0 != 3) {
            return true;
        }
        this.mEatingHover = false;
        return true;
    }

    public void setContentHeight(int $i0) throws  {
        this.mContentHeight = $i0;
        requestLayout();
    }

    public int getContentHeight() throws  {
        return this.mContentHeight;
    }

    public int getAnimatedVisibility() throws  {
        if (this.mVisibilityAnim != null) {
            return this.mVisAnimListener.mFinalVisibility;
        }
        return getVisibility();
    }

    public ViewPropertyAnimatorCompat setupAnimatorToVisibility(int $i0, long $l1) throws  {
        if (this.mVisibilityAnim != null) {
            this.mVisibilityAnim.cancel();
        }
        if ($i0 == 0) {
            if (getVisibility() != 0) {
                ViewCompat.setAlpha(this, 0.0f);
            }
            ViewPropertyAnimatorCompat $r1 = ViewCompat.animate(this).alpha(1.0f);
            $r1.setDuration($l1);
            $r1.setListener(this.mVisAnimListener.withFinalVisibility($r1, $i0));
            return $r1;
        }
        $r1 = ViewCompat.animate(this).alpha(0.0f);
        $r1.setDuration($l1);
        $r1.setListener(this.mVisAnimListener.withFinalVisibility($r1, $i0));
        return $r1;
    }

    public void animateToVisibility(int $i0) throws  {
        setupAnimatorToVisibility($i0, 200).start();
    }

    public void setVisibility(int $i0) throws  {
        if ($i0 != getVisibility()) {
            if (this.mVisibilityAnim != null) {
                this.mVisibilityAnim.cancel();
            }
            super.setVisibility($i0);
        }
    }

    public boolean showOverflowMenu() throws  {
        if (this.mActionMenuPresenter != null) {
            return this.mActionMenuPresenter.showOverflowMenu();
        }
        return false;
    }

    public void postShowOverflowMenu() throws  {
        post(new C02041());
    }

    public boolean hideOverflowMenu() throws  {
        if (this.mActionMenuPresenter != null) {
            return this.mActionMenuPresenter.hideOverflowMenu();
        }
        return false;
    }

    public boolean isOverflowMenuShowing() throws  {
        if (this.mActionMenuPresenter != null) {
            return this.mActionMenuPresenter.isOverflowMenuShowing();
        }
        return false;
    }

    public boolean isOverflowMenuShowPending() throws  {
        if (this.mActionMenuPresenter != null) {
            return this.mActionMenuPresenter.isOverflowMenuShowPending();
        }
        return false;
    }

    public boolean isOverflowReserved() throws  {
        return this.mActionMenuPresenter != null && this.mActionMenuPresenter.isOverflowReserved();
    }

    public boolean canShowOverflowMenu() throws  {
        return isOverflowReserved() && getVisibility() == 0;
    }

    public void dismissPopupMenus() throws  {
        if (this.mActionMenuPresenter != null) {
            this.mActionMenuPresenter.dismissPopupMenus();
        }
    }

    protected int measureChildView(View $r1, int $i2, int $i0, int $i1) throws  {
        $r1.measure(MeasureSpec.makeMeasureSpec($i2, Integer.MIN_VALUE), $i0);
        return Math.max(0, ($i2 - $r1.getMeasuredWidth()) - $i1);
    }

    protected int positionChild(View $r1, int $i0, int $i1, int $i2, boolean $z0) throws  {
        int $i3 = $r1.getMeasuredWidth();
        int $i4 = $r1.getMeasuredHeight();
        $i1 += ($i2 - $i4) / 2;
        if ($z0) {
            $r1.layout($i0 - $i3, $i1, $i0, $i1 + $i4);
        } else {
            $r1.layout($i0, $i1, $i0 + $i3, $i1 + $i4);
        }
        if ($z0) {
            return -$i3;
        }
        return $i3;
    }
}
