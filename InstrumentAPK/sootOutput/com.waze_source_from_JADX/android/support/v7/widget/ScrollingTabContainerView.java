package android.support.v7.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.appcompat.C0192R;
import android.support.v7.view.ActionBarPolicy;
import android.support.v7.widget.LinearLayoutCompat.LayoutParams;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import dalvik.annotation.Signature;

public class ScrollingTabContainerView extends HorizontalScrollView implements OnItemSelectedListener {
    private static final int FADE_DURATION = 200;
    private static final String TAG = "ScrollingTabContainerView";
    private static final Interpolator sAlphaInterpolator = new DecelerateInterpolator();
    private boolean mAllowCollapse;
    private int mContentHeight;
    int mMaxTabWidth;
    private int mSelectedTabIndex;
    int mStackedTabMaxWidth;
    private TabClickListener mTabClickListener;
    private LinearLayoutCompat mTabLayout;
    Runnable mTabSelector;
    private Spinner mTabSpinner;
    protected final VisibilityAnimListener mVisAnimListener = new VisibilityAnimListener();
    protected ViewPropertyAnimatorCompat mVisibilityAnim;

    private class TabAdapter extends BaseAdapter {
        private TabAdapter() throws  {
        }

        public int getCount() throws  {
            return ScrollingTabContainerView.this.mTabLayout.getChildCount();
        }

        public Object getItem(int $i0) throws  {
            return ((TabView) ScrollingTabContainerView.this.mTabLayout.getChildAt($i0)).getTab();
        }

        public long getItemId(int $i0) throws  {
            return (long) $i0;
        }

        public View getView(int $i0, View $r2, ViewGroup parent) throws  {
            if ($r2 == null) {
                return ScrollingTabContainerView.this.createTabView((Tab) getItem($i0), true);
            }
            ((TabView) $r2).bindTab((Tab) getItem($i0));
            return $r2;
        }
    }

    private class TabClickListener implements OnClickListener {
        private TabClickListener() throws  {
        }

        public void onClick(View $r1) throws  {
            ((TabView) $r1).getTab().select();
            int $i0 = ScrollingTabContainerView.this.mTabLayout.getChildCount();
            for (int $i1 = 0; $i1 < $i0; $i1++) {
                boolean $z0;
                View $r6 = ScrollingTabContainerView.this.mTabLayout.getChildAt($i1);
                if ($r6 == $r1) {
                    $z0 = true;
                } else {
                    $z0 = false;
                }
                $r6.setSelected($z0);
            }
        }
    }

    private class TabView extends LinearLayoutCompat implements OnLongClickListener {
        private final int[] BG_ATTRS = new int[]{16842964};
        private View mCustomView;
        private ImageView mIconView;
        private Tab mTab;
        private TextView mTextView;

        public TabView(Context $r2, Tab $r3, boolean $z0) throws  {
            super($r2, null, C0192R.attr.actionBarTabStyle);
            this.mTab = $r3;
            TintTypedArray $r5 = TintTypedArray.obtainStyledAttributes($r2, null, this.BG_ATTRS, C0192R.attr.actionBarTabStyle, 0);
            if ($r5.hasValue(0)) {
                setBackgroundDrawable($r5.getDrawable(0));
            }
            $r5.recycle();
            if ($z0) {
                setGravity(8388627);
            }
            update();
        }

        public void bindTab(Tab $r1) throws  {
            this.mTab = $r1;
            update();
        }

        public void setSelected(boolean $z0) throws  {
            boolean $z1 = isSelected() != $z0;
            super.setSelected($z0);
            if ($z1 && $z0) {
                sendAccessibilityEvent(4);
            }
        }

        public void onInitializeAccessibilityEvent(AccessibilityEvent $r1) throws  {
            super.onInitializeAccessibilityEvent($r1);
            $r1.setClassName(Tab.class.getName());
        }

        public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo $r1) throws  {
            super.onInitializeAccessibilityNodeInfo($r1);
            if (VERSION.SDK_INT >= 14) {
                $r1.setClassName(Tab.class.getName());
            }
        }

        public void onMeasure(int $i0, int $i1) throws  {
            super.onMeasure($i0, $i1);
            if (ScrollingTabContainerView.this.mMaxTabWidth > 0 && getMeasuredWidth() > ScrollingTabContainerView.this.mMaxTabWidth) {
                super.onMeasure(MeasureSpec.makeMeasureSpec(ScrollingTabContainerView.this.mMaxTabWidth, 1073741824), $i1);
            }
        }

        public void update() throws  {
            Tab $r2 = this.mTab;
            View $r4 = $r2.getCustomView();
            if ($r4 != null) {
                ViewParent $r5 = $r4.getParent();
                if ($r5 != this) {
                    if ($r5 != null) {
                        ((ViewGroup) $r5).removeView($r4);
                    }
                    addView($r4);
                }
                this.mCustomView = $r4;
                if (this.mTextView != null) {
                    this.mTextView.setVisibility(8);
                }
                if (this.mIconView != null) {
                    this.mIconView.setVisibility(8);
                    this.mIconView.setImageDrawable(null);
                    return;
                }
                return;
            }
            boolean $z0;
            if (this.mCustomView != null) {
                removeView(this.mCustomView);
                this.mCustomView = null;
            }
            Drawable $r8 = $r2.getIcon();
            CharSequence $r9 = $r2.getText();
            if ($r8 != null) {
                if (this.mIconView == null) {
                    ImageView $r1 = new ImageView(getContext());
                    LayoutParams $r11 = new LayoutParams(-2, -2);
                    $r11.gravity = 16;
                    $r1.setLayoutParams($r11);
                    addView($r1, 0);
                    this.mIconView = $r1;
                }
                this.mIconView.setImageDrawable($r8);
                this.mIconView.setVisibility(0);
            } else if (this.mIconView != null) {
                this.mIconView.setVisibility(8);
                this.mIconView.setImageDrawable(null);
            }
            if (TextUtils.isEmpty($r9)) {
                $z0 = false;
            } else {
                $z0 = true;
            }
            if ($z0) {
                if (this.mTextView == null) {
                    View appCompatTextView = new AppCompatTextView(getContext(), null, C0192R.attr.actionBarTabTextStyle);
                    appCompatTextView.setEllipsize(TruncateAt.END);
                    $r11 = new LayoutParams(-2, -2);
                    $r11.gravity = 16;
                    appCompatTextView.setLayoutParams($r11);
                    addView(appCompatTextView);
                    this.mTextView = appCompatTextView;
                }
                this.mTextView.setText($r9);
                this.mTextView.setVisibility(0);
            } else if (this.mTextView != null) {
                this.mTextView.setVisibility(8);
                this.mTextView.setText(null);
            }
            if (this.mIconView != null) {
                this.mIconView.setContentDescription($r2.getContentDescription());
            }
            if ($z0 || TextUtils.isEmpty($r2.getContentDescription())) {
                setOnLongClickListener(null);
                setLongClickable(false);
                return;
            }
            setOnLongClickListener(this);
        }

        public boolean onLongClick(View v) throws  {
            int[] $r2 = new int[2];
            getLocationOnScreen($r2);
            Context $r3 = getContext();
            int $i1 = getWidth();
            int $i2 = getHeight();
            int $i0 = $r3.getResources().getDisplayMetrics().widthPixels;
            Toast $r8 = Toast.makeText($r3, this.mTab.getContentDescription(), 0);
            $r8.setGravity(49, ($r2[0] + ($i1 / 2)) - ($i0 / 2), $i2);
            $r8.show();
            return true;
        }

        public Tab getTab() throws  {
            return this.mTab;
        }
    }

    protected class VisibilityAnimListener implements ViewPropertyAnimatorListener {
        private boolean mCanceled = false;
        private int mFinalVisibility;

        protected VisibilityAnimListener() throws  {
        }

        public VisibilityAnimListener withFinalVisibility(ViewPropertyAnimatorCompat $r1, int $i0) throws  {
            this.mFinalVisibility = $i0;
            ScrollingTabContainerView.this.mVisibilityAnim = $r1;
            return this;
        }

        public void onAnimationStart(View view) throws  {
            ScrollingTabContainerView.this.setVisibility(0);
            this.mCanceled = false;
        }

        public void onAnimationEnd(View view) throws  {
            if (!this.mCanceled) {
                ScrollingTabContainerView.this.mVisibilityAnim = null;
                ScrollingTabContainerView.this.setVisibility(this.mFinalVisibility);
            }
        }

        public void onAnimationCancel(View view) throws  {
            this.mCanceled = true;
        }
    }

    public ScrollingTabContainerView(Context $r1) throws  {
        super($r1);
        setHorizontalScrollBarEnabled(false);
        ActionBarPolicy $r4 = ActionBarPolicy.get($r1);
        setContentHeight($r4.getTabContainerHeight());
        this.mStackedTabMaxWidth = $r4.getStackedTabMaxWidth();
        this.mTabLayout = createTabLayout();
        addView(this.mTabLayout, new ViewGroup.LayoutParams(-2, -1));
    }

    public void onMeasure(int $i0, int heightMeasureSpec) throws  {
        boolean $z0;
        boolean $z1;
        heightMeasureSpec = MeasureSpec.getMode($i0);
        if (heightMeasureSpec == 1073741824) {
            $z0 = true;
        } else {
            $z0 = false;
        }
        setFillViewport($z0);
        int $i2 = this.mTabLayout.getChildCount();
        if ($i2 <= 1 || !(heightMeasureSpec == 1073741824 || heightMeasureSpec == Integer.MIN_VALUE)) {
            this.mMaxTabWidth = -1;
        } else {
            if ($i2 > 2) {
                this.mMaxTabWidth = (int) (((float) MeasureSpec.getSize($i0)) * 0.4f);
            } else {
                this.mMaxTabWidth = MeasureSpec.getSize($i0) / 2;
            }
            this.mMaxTabWidth = Math.min(this.mMaxTabWidth, this.mStackedTabMaxWidth);
        }
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(this.mContentHeight, 1073741824);
        if ($z0 || !this.mAllowCollapse) {
            $z1 = false;
        } else {
            $z1 = true;
        }
        if ($z1) {
            this.mTabLayout.measure(0, heightMeasureSpec);
            if (this.mTabLayout.getMeasuredWidth() > MeasureSpec.getSize($i0)) {
                performCollapse();
            } else {
                performExpand();
            }
        } else {
            performExpand();
        }
        $i2 = getMeasuredWidth();
        super.onMeasure($i0, heightMeasureSpec);
        $i0 = getMeasuredWidth();
        if ($z0 && $i2 != $i0) {
            setTabSelected(this.mSelectedTabIndex);
        }
    }

    private boolean isCollapsed() throws  {
        return this.mTabSpinner != null && this.mTabSpinner.getParent() == this;
    }

    public void setAllowCollapse(boolean $z0) throws  {
        this.mAllowCollapse = $z0;
    }

    private void performCollapse() throws  {
        if (!isCollapsed()) {
            if (this.mTabSpinner == null) {
                this.mTabSpinner = createSpinner();
            }
            removeView(this.mTabLayout);
            addView(this.mTabSpinner, new ViewGroup.LayoutParams(-2, -1));
            if (this.mTabSpinner.getAdapter() == null) {
                this.mTabSpinner.setAdapter(new TabAdapter());
            }
            if (this.mTabSelector != null) {
                removeCallbacks(this.mTabSelector);
                this.mTabSelector = null;
            }
            this.mTabSpinner.setSelection(this.mSelectedTabIndex);
        }
    }

    private boolean performExpand() throws  {
        if (!isCollapsed()) {
            return false;
        }
        removeView(this.mTabSpinner);
        addView(this.mTabLayout, new ViewGroup.LayoutParams(-2, -1));
        setTabSelected(this.mTabSpinner.getSelectedItemPosition());
        return false;
    }

    public void setTabSelected(int $i0) throws  {
        this.mSelectedTabIndex = $i0;
        int $i1 = this.mTabLayout.getChildCount();
        for (int $i2 = 0; $i2 < $i1; $i2++) {
            boolean $z0;
            View $r2 = this.mTabLayout.getChildAt($i2);
            if ($i2 == $i0) {
                $z0 = true;
            } else {
                $z0 = false;
            }
            $r2.setSelected($z0);
            if ($z0) {
                animateToTab($i0);
            }
        }
        if (this.mTabSpinner != null && $i0 >= 0) {
            this.mTabSpinner.setSelection($i0);
        }
    }

    public void setContentHeight(int $i0) throws  {
        this.mContentHeight = $i0;
        requestLayout();
    }

    private LinearLayoutCompat createTabLayout() throws  {
        LinearLayoutCompat $r1 = new LinearLayoutCompat(getContext(), null, C0192R.attr.actionBarTabBarStyle);
        $r1.setMeasureWithLargestChildEnabled(true);
        $r1.setGravity(17);
        $r1.setLayoutParams(new LayoutParams(-2, -1));
        return $r1;
    }

    private Spinner createSpinner() throws  {
        AppCompatSpinner $r1 = new AppCompatSpinner(getContext(), null, C0192R.attr.actionDropDownStyle);
        $r1.setLayoutParams(new LayoutParams(-2, -1));
        $r1.setOnItemSelectedListener(this);
        return $r1;
    }

    protected void onConfigurationChanged(Configuration $r1) throws  {
        if (VERSION.SDK_INT >= 8) {
            super.onConfigurationChanged($r1);
        }
        ActionBarPolicy $r3 = ActionBarPolicy.get(getContext());
        setContentHeight($r3.getTabContainerHeight());
        this.mStackedTabMaxWidth = $r3.getStackedTabMaxWidth();
    }

    public void animateToVisibility(int $i0) throws  {
        if (this.mVisibilityAnim != null) {
            this.mVisibilityAnim.cancel();
        }
        if ($i0 == 0) {
            if (getVisibility() != 0) {
                ViewCompat.setAlpha(this, 0.0f);
            }
            ViewPropertyAnimatorCompat $r1 = ViewCompat.animate(this).alpha(1.0f);
            $r1.setDuration(200);
            $r1.setInterpolator(sAlphaInterpolator);
            $r1.setListener(this.mVisAnimListener.withFinalVisibility($r1, $i0));
            $r1.start();
            return;
        }
        $r1 = ViewCompat.animate(this).alpha(0.0f);
        $r1.setDuration(200);
        $r1.setInterpolator(sAlphaInterpolator);
        $r1.setListener(this.mVisAnimListener.withFinalVisibility($r1, $i0));
        $r1.start();
    }

    public void animateToTab(int $i0) throws  {
        final View $r1 = this.mTabLayout.getChildAt($i0);
        if (this.mTabSelector != null) {
            removeCallbacks(this.mTabSelector);
        }
        this.mTabSelector = new Runnable() {
            public void run() throws  {
                ScrollingTabContainerView.this.smoothScrollTo($r1.getLeft() - ((ScrollingTabContainerView.this.getWidth() - $r1.getWidth()) / 2), 0);
                ScrollingTabContainerView.this.mTabSelector = null;
            }
        };
        post(this.mTabSelector);
    }

    public void onAttachedToWindow() throws  {
        super.onAttachedToWindow();
        if (this.mTabSelector != null) {
            post(this.mTabSelector);
        }
    }

    public void onDetachedFromWindow() throws  {
        super.onDetachedFromWindow();
        if (this.mTabSelector != null) {
            removeCallbacks(this.mTabSelector);
        }
    }

    private TabView createTabView(Tab $r1, boolean $z0) throws  {
        TabView $r2 = new TabView(getContext(), $r1, $z0);
        if ($z0) {
            $r2.setBackgroundDrawable(null);
            $r2.setLayoutParams(new AbsListView.LayoutParams(-1, this.mContentHeight));
            return $r2;
        }
        $r2.setFocusable(true);
        if (this.mTabClickListener == null) {
            this.mTabClickListener = new TabClickListener();
        }
        $r2.setOnClickListener(this.mTabClickListener);
        return $r2;
    }

    public void addTab(Tab $r1, boolean $z0) throws  {
        TabView $r3 = createTabView($r1, false);
        this.mTabLayout.addView($r3, new LayoutParams(0, -1, 1.0f));
        if (this.mTabSpinner != null) {
            ((TabAdapter) this.mTabSpinner.getAdapter()).notifyDataSetChanged();
        }
        if ($z0) {
            $r3.setSelected(true);
        }
        if (this.mAllowCollapse) {
            requestLayout();
        }
    }

    public void addTab(Tab $r1, int $i0, boolean $z0) throws  {
        TabView $r3 = createTabView($r1, false);
        this.mTabLayout.addView($r3, $i0, new LayoutParams(0, -1, 1.0f));
        if (this.mTabSpinner != null) {
            ((TabAdapter) this.mTabSpinner.getAdapter()).notifyDataSetChanged();
        }
        if ($z0) {
            $r3.setSelected(true);
        }
        if (this.mAllowCollapse) {
            requestLayout();
        }
    }

    public void updateTab(int $i0) throws  {
        ((TabView) this.mTabLayout.getChildAt($i0)).update();
        if (this.mTabSpinner != null) {
            ((TabAdapter) this.mTabSpinner.getAdapter()).notifyDataSetChanged();
        }
        if (this.mAllowCollapse) {
            requestLayout();
        }
    }

    public void removeTabAt(int $i0) throws  {
        this.mTabLayout.removeViewAt($i0);
        if (this.mTabSpinner != null) {
            ((TabAdapter) this.mTabSpinner.getAdapter()).notifyDataSetChanged();
        }
        if (this.mAllowCollapse) {
            requestLayout();
        }
    }

    public void removeAllTabs() throws  {
        this.mTabLayout.removeAllViews();
        if (this.mTabSpinner != null) {
            ((TabAdapter) this.mTabSpinner.getAdapter()).notifyDataSetChanged();
        }
        if (this.mAllowCollapse) {
            requestLayout();
        }
    }

    public void onItemSelected(@Signature({"(", "Landroid/widget/AdapterView", "<*>;", "Landroid/view/View;", "IJ)V"}) AdapterView<?> adapterView, @Signature({"(", "Landroid/widget/AdapterView", "<*>;", "Landroid/view/View;", "IJ)V"}) View $r2, @Signature({"(", "Landroid/widget/AdapterView", "<*>;", "Landroid/view/View;", "IJ)V"}) int position, @Signature({"(", "Landroid/widget/AdapterView", "<*>;", "Landroid/view/View;", "IJ)V"}) long id) throws  {
        ((TabView) $r2).getTab().select();
    }

    public void onNothingSelected(@Signature({"(", "Landroid/widget/AdapterView", "<*>;)V"}) AdapterView<?> adapterView) throws  {
    }
}
