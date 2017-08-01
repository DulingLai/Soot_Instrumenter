package android.support.v7.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.v7.appcompat.C0192R;
import android.support.v7.view.ActionMode;
import android.support.v7.view.ActionMode.Callback;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;

public class ActionBarContainer extends FrameLayout {
    private View mActionBarView;
    Drawable mBackground;
    private View mContextView;
    private int mHeight;
    boolean mIsSplit;
    boolean mIsStacked;
    private boolean mIsTransitioning;
    Drawable mSplitBackground;
    Drawable mStackedBackground;
    private View mTabContainer;

    public ActionMode startActionModeForChild(View child, Callback callback) throws  {
        return null;
    }

    public android.view.ActionMode startActionModeForChild(View originalView, android.view.ActionMode.Callback callback) throws  {
        return null;
    }

    public ActionBarContainer(Context $r1) throws  {
        this($r1, null);
    }

    public ActionBarContainer(Context $r1, AttributeSet $r2) throws  {
        ActionBarBackgroundDrawable $r3;
        boolean $z0 = true;
        super($r1, $r2);
        if (VERSION.SDK_INT >= 21) {
            $r3 = r7;
            ActionBarBackgroundDrawable r7 = new ActionBarBackgroundDrawableV21(this);
        } else {
            $r3 = r8;
            ActionBarBackgroundDrawable r8 = new ActionBarBackgroundDrawable(this);
        }
        setBackgroundDrawable($r3);
        TypedArray $r5 = $r1.obtainStyledAttributes($r2, C0192R.styleable.ActionBar);
        this.mBackground = $r5.getDrawable(C0192R.styleable.ActionBar_background);
        this.mStackedBackground = $r5.getDrawable(C0192R.styleable.ActionBar_backgroundStacked);
        this.mHeight = $r5.getDimensionPixelSize(C0192R.styleable.ActionBar_height, -1);
        if (getId() == C0192R.id.split_action_bar) {
            this.mIsSplit = true;
            this.mSplitBackground = $r5.getDrawable(C0192R.styleable.ActionBar_backgroundSplit);
        }
        $r5.recycle();
        if (this.mIsSplit) {
            if (this.mSplitBackground != null) {
                $z0 = false;
            }
        } else if (!(this.mBackground == null && this.mStackedBackground == null)) {
            $z0 = false;
        }
        setWillNotDraw($z0);
    }

    public void onFinishInflate() throws  {
        super.onFinishInflate();
        this.mActionBarView = findViewById(C0192R.id.action_bar);
        this.mContextView = findViewById(C0192R.id.action_context_bar);
    }

    public void setPrimaryBackground(Drawable $r1) throws  {
        boolean $z0 = true;
        if (this.mBackground != null) {
            this.mBackground.setCallback(null);
            unscheduleDrawable(this.mBackground);
        }
        this.mBackground = $r1;
        if ($r1 != null) {
            $r1.setCallback(this);
            if (this.mActionBarView != null) {
                this.mBackground.setBounds(this.mActionBarView.getLeft(), this.mActionBarView.getTop(), this.mActionBarView.getRight(), this.mActionBarView.getBottom());
            }
        }
        if (this.mIsSplit) {
            if (this.mSplitBackground != null) {
                $z0 = false;
            }
        } else if (!(this.mBackground == null && this.mStackedBackground == null)) {
            $z0 = false;
        }
        setWillNotDraw($z0);
        invalidate();
    }

    public void setStackedBackground(Drawable $r1) throws  {
        boolean $z0 = true;
        if (this.mStackedBackground != null) {
            this.mStackedBackground.setCallback(null);
            unscheduleDrawable(this.mStackedBackground);
        }
        this.mStackedBackground = $r1;
        if ($r1 != null) {
            $r1.setCallback(this);
            if (this.mIsStacked && this.mStackedBackground != null) {
                this.mStackedBackground.setBounds(this.mTabContainer.getLeft(), this.mTabContainer.getTop(), this.mTabContainer.getRight(), this.mTabContainer.getBottom());
            }
        }
        if (this.mIsSplit) {
            if (this.mSplitBackground != null) {
                $z0 = false;
            }
        } else if (!(this.mBackground == null && this.mStackedBackground == null)) {
            $z0 = false;
        }
        setWillNotDraw($z0);
        invalidate();
    }

    public void setSplitBackground(Drawable $r1) throws  {
        boolean $z0 = true;
        if (this.mSplitBackground != null) {
            this.mSplitBackground.setCallback(null);
            unscheduleDrawable(this.mSplitBackground);
        }
        this.mSplitBackground = $r1;
        if ($r1 != null) {
            $r1.setCallback(this);
            if (this.mIsSplit && this.mSplitBackground != null) {
                this.mSplitBackground.setBounds(0, 0, getMeasuredWidth(), getMeasuredHeight());
            }
        }
        if (this.mIsSplit) {
            if (this.mSplitBackground != null) {
                $z0 = false;
            }
        } else if (!(this.mBackground == null && this.mStackedBackground == null)) {
            $z0 = false;
        }
        setWillNotDraw($z0);
        invalidate();
    }

    public void setVisibility(int $i0) throws  {
        boolean $z0;
        super.setVisibility($i0);
        if ($i0 == 0) {
            $z0 = true;
        } else {
            $z0 = false;
        }
        if (this.mBackground != null) {
            this.mBackground.setVisible($z0, false);
        }
        if (this.mStackedBackground != null) {
            this.mStackedBackground.setVisible($z0, false);
        }
        if (this.mSplitBackground != null) {
            this.mSplitBackground.setVisible($z0, false);
        }
    }

    protected boolean verifyDrawable(Drawable $r1) throws  {
        return ($r1 == this.mBackground && !this.mIsSplit) || (($r1 == this.mStackedBackground && this.mIsStacked) || (($r1 == this.mSplitBackground && this.mIsSplit) || super.verifyDrawable($r1)));
    }

    protected void drawableStateChanged() throws  {
        super.drawableStateChanged();
        if (this.mBackground != null && this.mBackground.isStateful()) {
            this.mBackground.setState(getDrawableState());
        }
        if (this.mStackedBackground != null && this.mStackedBackground.isStateful()) {
            this.mStackedBackground.setState(getDrawableState());
        }
        if (this.mSplitBackground != null && this.mSplitBackground.isStateful()) {
            this.mSplitBackground.setState(getDrawableState());
        }
    }

    public void jumpDrawablesToCurrentState() throws  {
        if (VERSION.SDK_INT >= 11) {
            super.jumpDrawablesToCurrentState();
            if (this.mBackground != null) {
                this.mBackground.jumpToCurrentState();
            }
            if (this.mStackedBackground != null) {
                this.mStackedBackground.jumpToCurrentState();
            }
            if (this.mSplitBackground != null) {
                this.mSplitBackground.jumpToCurrentState();
            }
        }
    }

    public void setTransitioning(boolean $z0) throws  {
        int $i0;
        this.mIsTransitioning = $z0;
        if ($z0) {
            $i0 = 393216;
        } else {
            $i0 = 262144;
        }
        setDescendantFocusability($i0);
    }

    public boolean onInterceptTouchEvent(MotionEvent $r1) throws  {
        return this.mIsTransitioning || super.onInterceptTouchEvent($r1);
    }

    public boolean onTouchEvent(MotionEvent $r1) throws  {
        super.onTouchEvent($r1);
        return true;
    }

    public void setTabContainer(ScrollingTabContainerView $r1) throws  {
        if (this.mTabContainer != null) {
            removeView(this.mTabContainer);
        }
        this.mTabContainer = $r1;
        if ($r1 != null) {
            addView($r1);
            LayoutParams $r2 = $r1.getLayoutParams();
            $r2.width = -1;
            $r2.height = -2;
            $r1.setAllowCollapse(false);
        }
    }

    public View getTabContainer() throws  {
        return this.mTabContainer;
    }

    private boolean isCollapsed(View $r1) throws  {
        return $r1 == null || $r1.getVisibility() == 8 || $r1.getMeasuredHeight() == 0;
    }

    private int getMeasuredHeightWithMargins(View $r1) throws  {
        FrameLayout.LayoutParams $r3 = (FrameLayout.LayoutParams) $r1.getLayoutParams();
        return ($r1.getMeasuredHeight() + $r3.topMargin) + $r3.bottomMargin;
    }

    public void onMeasure(int $i0, int $i1) throws  {
        if (this.mActionBarView == null && MeasureSpec.getMode($i1) == Integer.MIN_VALUE && this.mHeight >= 0) {
            $i1 = MeasureSpec.makeMeasureSpec(Math.min(this.mHeight, MeasureSpec.getSize($i1)), Integer.MIN_VALUE);
        }
        super.onMeasure($i0, $i1);
        if (this.mActionBarView != null) {
            int $i2 = MeasureSpec.getMode($i1);
            if (this.mTabContainer != null && this.mTabContainer.getVisibility() != 8 && $i2 != 1073741824) {
                if (!isCollapsed(this.mActionBarView)) {
                    $i0 = getMeasuredHeightWithMargins(this.mActionBarView);
                } else if (isCollapsed(this.mContextView)) {
                    $i0 = 0;
                } else {
                    $i0 = getMeasuredHeightWithMargins(this.mContextView);
                }
                setMeasuredDimension(getMeasuredWidth(), Math.min(getMeasuredHeightWithMargins(this.mTabContainer) + $i0, $i2 == Integer.MIN_VALUE ? MeasureSpec.getSize($i1) : ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED));
            }
        }
    }

    public void onLayout(boolean $z0, int $i0, int $i1, int $i2, int $i3) throws  {
        super.onLayout($z0, $i0, $i1, $i2, $i3);
        View $r1 = this.mTabContainer;
        $z0 = ($r1 == null || $r1.getVisibility() == 8) ? false : true;
        if (!($r1 == null || $r1.getVisibility() == 8)) {
            $i1 = getMeasuredHeight();
            FrameLayout.LayoutParams $r3 = (FrameLayout.LayoutParams) $r1.getLayoutParams();
            $r1.layout($i0, ($i1 - $r1.getMeasuredHeight()) - $r3.bottomMargin, $i2, $i1 - $r3.bottomMargin);
        }
        boolean $z1 = false;
        if (!this.mIsSplit) {
            if (this.mBackground != null) {
                if (this.mActionBarView.getVisibility() == 0) {
                    this.mBackground.setBounds(this.mActionBarView.getLeft(), this.mActionBarView.getTop(), this.mActionBarView.getRight(), this.mActionBarView.getBottom());
                } else if (this.mContextView == null || this.mContextView.getVisibility() != 0) {
                    this.mBackground.setBounds(0, 0, 0, 0);
                } else {
                    this.mBackground.setBounds(this.mContextView.getLeft(), this.mContextView.getTop(), this.mContextView.getRight(), this.mContextView.getBottom());
                }
                $z1 = true;
            }
            this.mIsStacked = $z0;
            if ($z0 && this.mStackedBackground != null) {
                this.mStackedBackground.setBounds($r1.getLeft(), $r1.getTop(), $r1.getRight(), $r1.getBottom());
                $z1 = true;
            }
        } else if (this.mSplitBackground != null) {
            this.mSplitBackground.setBounds(0, 0, getMeasuredWidth(), getMeasuredHeight());
            $z1 = true;
        }
        if ($z1) {
            invalidate();
        }
    }
}
