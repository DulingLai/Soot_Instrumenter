package android.support.v7.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter;
import android.support.v7.appcompat.C0192R;
import android.support.v7.view.menu.ActionMenuItem;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPresenter;
import android.support.v7.widget.Toolbar.LayoutParams;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window.Callback;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import dalvik.annotation.Signature;

public class ToolbarWidgetWrapper implements DecorToolbar {
    private static final int AFFECTS_LOGO_MASK = 3;
    private static final long DEFAULT_FADE_DURATION_MS = 200;
    private static final String TAG = "ToolbarWidgetWrapper";
    private ActionMenuPresenter mActionMenuPresenter;
    private View mCustomView;
    private int mDefaultNavigationContentDescription;
    private Drawable mDefaultNavigationIcon;
    private int mDisplayOpts;
    private final AppCompatDrawableManager mDrawableManager;
    private CharSequence mHomeDescription;
    private Drawable mIcon;
    private Drawable mLogo;
    private boolean mMenuPrepared;
    private Drawable mNavIcon;
    private int mNavigationMode;
    private Spinner mSpinner;
    private CharSequence mSubtitle;
    private View mTabView;
    private CharSequence mTitle;
    private boolean mTitleSet;
    private Toolbar mToolbar;
    private Callback mWindowCallback;

    class C02791 implements OnClickListener {
        final ActionMenuItem mNavItem;

        C02791() throws  {
            ToolbarWidgetWrapper $r1 = ToolbarWidgetWrapper.this;
            Context $r4 = $r1.mToolbar.getContext();
            $r1 = ToolbarWidgetWrapper.this;
            this.mNavItem = new ActionMenuItem($r4, 0, 16908332, 0, 0, $r1.mTitle);
        }

        public void onClick(View v) throws  {
            if (ToolbarWidgetWrapper.this.mWindowCallback != null && ToolbarWidgetWrapper.this.mMenuPrepared) {
                ToolbarWidgetWrapper.this.mWindowCallback.onMenuItemSelected(0, this.mNavItem);
            }
        }
    }

    public ToolbarWidgetWrapper(android.support.v7.widget.Toolbar r20, boolean r21, int r22, int r23) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:43:0x01c4 in {2, 7, 10, 13, 17, 20, 23, 26, 29, 30, 33, 36, 39, 40, 42, 44, 46} preds:[]
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
        r19 = this;
        r0 = r19;
        r0.<init>();
        r2 = 0;
        r0 = r19;
        r0.mNavigationMode = r2;
        r2 = 0;
        r0 = r19;
        r0.mDefaultNavigationContentDescription = r2;
        r0 = r20;
        r1 = r19;
        r1.mToolbar = r0;
        r0 = r20;
        r3 = r0.getTitle();
        r0 = r19;
        r0.mTitle = r3;
        r0 = r20;
        r3 = r0.getSubtitle();
        r0 = r19;
        r0.mSubtitle = r3;
        r0 = r19;
        r3 = r0.mTitle;
        if (r3 == 0) goto L_0x01c8;
    L_0x002f:
        r4 = 1;
    L_0x0030:
        r0 = r19;
        r0.mTitleSet = r4;
        r0 = r20;
        r5 = r0.getNavigationIcon();
        r0 = r19;
        r0.mNavIcon = r5;
        if (r21 == 0) goto L_0x01ca;
    L_0x0040:
        r0 = r20;
        r6 = r0.getContext();
        r7 = android.support.v7.appcompat.C0192R.styleable.ActionBar;
        r8 = android.support.v7.appcompat.C0192R.attr.actionBarStyle;
        r10 = 0;
        r2 = 0;
        r9 = android.support.v7.widget.TintTypedArray.obtainStyledAttributes(r6, r10, r7, r8, r2);
        r8 = android.support.v7.appcompat.C0192R.styleable.ActionBar_title;
        r3 = r9.getText(r8);
        r21 = android.text.TextUtils.isEmpty(r3);
        if (r21 != 0) goto L_0x0061;
    L_0x005c:
        r0 = r19;
        r0.setTitle(r3);
    L_0x0061:
        r8 = android.support.v7.appcompat.C0192R.styleable.ActionBar_subtitle;
        r3 = r9.getText(r8);
        r21 = android.text.TextUtils.isEmpty(r3);
        if (r21 != 0) goto L_0x0072;
    L_0x006d:
        r0 = r19;
        r0.setSubtitle(r3);
    L_0x0072:
        r8 = android.support.v7.appcompat.C0192R.styleable.ActionBar_logo;
        r5 = r9.getDrawable(r8);
        if (r5 == 0) goto L_0x007f;
    L_0x007a:
        r0 = r19;
        r0.setLogo(r5);
    L_0x007f:
        r8 = android.support.v7.appcompat.C0192R.styleable.ActionBar_icon;
        r5 = r9.getDrawable(r8);
        r0 = r19;
        r11 = r0.mNavIcon;
        if (r11 != 0) goto L_0x0092;
    L_0x008b:
        if (r5 == 0) goto L_0x0092;
    L_0x008d:
        r0 = r19;
        r0.setIcon(r5);
    L_0x0092:
        r8 = android.support.v7.appcompat.C0192R.styleable.ActionBar_homeAsUpIndicator;
        r5 = r9.getDrawable(r8);
        if (r5 == 0) goto L_0x009f;
    L_0x009a:
        r0 = r19;
        r0.setNavigationIcon(r5);
    L_0x009f:
        r8 = android.support.v7.appcompat.C0192R.styleable.ActionBar_displayOptions;
        r2 = 0;
        r8 = r9.getInt(r8, r2);
        r0 = r19;
        r0.setDisplayOptions(r8);
        r8 = android.support.v7.appcompat.C0192R.styleable.ActionBar_customNavigationLayout;
        r2 = 0;
        r8 = r9.getResourceId(r8, r2);
        if (r8 == 0) goto L_0x00df;
    L_0x00b4:
        r0 = r19;
        r0 = r0.mToolbar;
        r20 = r0;
        r6 = r0.getContext();
        r12 = android.view.LayoutInflater.from(r6);
        r0 = r19;
        r0 = r0.mToolbar;
        r20 = r0;
        r2 = 0;
        r0 = r20;
        r13 = r12.inflate(r8, r0, r2);
        r0 = r19;
        r0.setCustomView(r13);
        r0 = r19;
        r8 = r0.mDisplayOpts;
        r8 = r8 | 16;
        r0 = r19;
        r0.setDisplayOptions(r8);
    L_0x00df:
        r8 = android.support.v7.appcompat.C0192R.styleable.ActionBar_height;
        r2 = 0;
        r8 = r9.getLayoutDimension(r8, r2);
        if (r8 <= 0) goto L_0x00fd;
    L_0x00e8:
        r0 = r19;
        r0 = r0.mToolbar;
        r20 = r0;
        r14 = r0.getLayoutParams();
        r14.height = r8;
        r0 = r19;
        r0 = r0.mToolbar;
        r20 = r0;
        r0.setLayoutParams(r14);
    L_0x00fd:
        r8 = android.support.v7.appcompat.C0192R.styleable.ActionBar_contentInsetStart;
        r2 = -1;
        r8 = r9.getDimensionPixelOffset(r8, r2);
        r15 = android.support.v7.appcompat.C0192R.styleable.ActionBar_contentInsetEnd;
        r2 = -1;
        r15 = r9.getDimensionPixelOffset(r15, r2);
        if (r8 >= 0) goto L_0x010f;
    L_0x010d:
        if (r15 < 0) goto L_0x0124;
    L_0x010f:
        r0 = r19;
        r0 = r0.mToolbar;
        r20 = r0;
        r2 = 0;
        r8 = java.lang.Math.max(r8, r2);
        r2 = 0;
        r15 = java.lang.Math.max(r15, r2);
        r0 = r20;
        r0.setContentInsetsRelative(r8, r15);
    L_0x0124:
        r8 = android.support.v7.appcompat.C0192R.styleable.ActionBar_titleTextStyle;
        r2 = 0;
        r8 = r9.getResourceId(r8, r2);
        if (r8 == 0) goto L_0x0142;
    L_0x012d:
        r0 = r19;
        r0 = r0.mToolbar;
        r20 = r0;
        r0 = r19;
        r0 = r0.mToolbar;
        r16 = r0;
        r6 = r0.getContext();
        r0 = r20;
        r0.setTitleTextAppearance(r6, r8);
    L_0x0142:
        r8 = android.support.v7.appcompat.C0192R.styleable.ActionBar_subtitleTextStyle;
        r2 = 0;
        r8 = r9.getResourceId(r8, r2);
        if (r8 == 0) goto L_0x0160;
    L_0x014b:
        r0 = r19;
        r0 = r0.mToolbar;
        r20 = r0;
        r0 = r19;
        r0 = r0.mToolbar;
        r16 = r0;
        r6 = r0.getContext();
        r0 = r20;
        r0.setSubtitleTextAppearance(r6, r8);
    L_0x0160:
        r8 = android.support.v7.appcompat.C0192R.styleable.ActionBar_popupTheme;
        r2 = 0;
        r8 = r9.getResourceId(r8, r2);
        if (r8 == 0) goto L_0x0172;
    L_0x0169:
        r0 = r19;
        r0 = r0.mToolbar;
        r20 = r0;
        r0.setPopupTheme(r8);
    L_0x0172:
        r9.recycle();
    L_0x0175:
        r17 = android.support.v7.widget.AppCompatDrawableManager.get();
        r0 = r17;
        r1 = r19;
        r1.mDrawableManager = r0;
        r0 = r19;
        r1 = r22;
        r0.setDefaultNavigationContentDescription(r1);
        r0 = r19;
        r0 = r0.mToolbar;
        r20 = r0;
        r3 = r0.getNavigationContentDescription();
        r0 = r19;
        r0.mHomeDescription = r3;
        r0 = r19;
        r0 = r0.mDrawableManager;
        r17 = r0;
        r0 = r19;
        r6 = r0.getContext();
        r0 = r17;
        r1 = r23;
        r5 = r0.getDrawable(r6, r1);
        r0 = r19;
        r0.setDefaultNavigationIcon(r5);
        r0 = r19;
        r0 = r0.mToolbar;
        r20 = r0;
        r18 = new android.support.v7.widget.ToolbarWidgetWrapper$1;
        r0 = r18;
        r1 = r19;
        r0.<init>();
        r0 = r20;
        r1 = r18;
        r0.setNavigationOnClickListener(r1);
        return;
        goto L_0x01c8;
    L_0x01c5:
        goto L_0x0030;
    L_0x01c8:
        r4 = 0;
        goto L_0x01c5;
    L_0x01ca:
        r0 = r19;
        r8 = r0.detectDisplayOptions();
        r0 = r19;
        r0.mDisplayOpts = r8;
        goto L_0x0175;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.ToolbarWidgetWrapper.<init>(android.support.v7.widget.Toolbar, boolean, int, int):void");
    }

    public ToolbarWidgetWrapper(Toolbar $r1, boolean $z0) throws  {
        this($r1, $z0, C0192R.string.abc_action_bar_up_description, C0192R.drawable.abc_ic_ab_back_mtrl_am_alpha);
    }

    public void setDefaultNavigationContentDescription(int $i0) throws  {
        if ($i0 != this.mDefaultNavigationContentDescription) {
            this.mDefaultNavigationContentDescription = $i0;
            if (TextUtils.isEmpty(this.mToolbar.getNavigationContentDescription())) {
                setNavigationContentDescription(this.mDefaultNavigationContentDescription);
            }
        }
    }

    public void setDefaultNavigationIcon(Drawable $r1) throws  {
        if (this.mDefaultNavigationIcon != $r1) {
            this.mDefaultNavigationIcon = $r1;
            updateNavigationIcon();
        }
    }

    private int detectDisplayOptions() throws  {
        if (this.mToolbar.getNavigationIcon() != null) {
            return (byte) 11 | (byte) 4;
        }
        return 11;
    }

    public ViewGroup getViewGroup() throws  {
        return this.mToolbar;
    }

    public Context getContext() throws  {
        return this.mToolbar.getContext();
    }

    public boolean hasExpandedActionView() throws  {
        return this.mToolbar.hasExpandedActionView();
    }

    public void collapseActionView() throws  {
        this.mToolbar.collapseActionView();
    }

    public void setWindowCallback(Callback $r1) throws  {
        this.mWindowCallback = $r1;
    }

    public void setWindowTitle(CharSequence $r1) throws  {
        if (!this.mTitleSet) {
            setTitleInt($r1);
        }
    }

    public CharSequence getTitle() throws  {
        return this.mToolbar.getTitle();
    }

    public void setTitle(CharSequence $r1) throws  {
        this.mTitleSet = true;
        setTitleInt($r1);
    }

    private void setTitleInt(CharSequence $r1) throws  {
        this.mTitle = $r1;
        if ((this.mDisplayOpts & 8) != 0) {
            this.mToolbar.setTitle($r1);
        }
    }

    public CharSequence getSubtitle() throws  {
        return this.mToolbar.getSubtitle();
    }

    public void setSubtitle(CharSequence $r1) throws  {
        this.mSubtitle = $r1;
        if ((this.mDisplayOpts & 8) != 0) {
            this.mToolbar.setSubtitle($r1);
        }
    }

    public void initProgress() throws  {
        Log.i(TAG, "Progress display unsupported");
    }

    public void initIndeterminateProgress() throws  {
        Log.i(TAG, "Progress display unsupported");
    }

    public boolean hasIcon() throws  {
        return this.mIcon != null;
    }

    public boolean hasLogo() throws  {
        return this.mLogo != null;
    }

    public void setIcon(int $i0) throws  {
        setIcon($i0 != 0 ? this.mDrawableManager.getDrawable(getContext(), $i0) : null);
    }

    public void setIcon(Drawable $r1) throws  {
        this.mIcon = $r1;
        updateToolbarLogo();
    }

    public void setLogo(int $i0) throws  {
        setLogo($i0 != 0 ? this.mDrawableManager.getDrawable(getContext(), $i0) : null);
    }

    public void setLogo(Drawable $r1) throws  {
        this.mLogo = $r1;
        updateToolbarLogo();
    }

    private void updateToolbarLogo() throws  {
        Drawable $r1 = null;
        if ((this.mDisplayOpts & 2) != 0) {
            $r1 = (this.mDisplayOpts & 1) != 0 ? this.mLogo != null ? this.mLogo : this.mIcon : this.mIcon;
        }
        this.mToolbar.setLogo($r1);
    }

    public boolean canShowOverflowMenu() throws  {
        return this.mToolbar.canShowOverflowMenu();
    }

    public boolean isOverflowMenuShowing() throws  {
        return this.mToolbar.isOverflowMenuShowing();
    }

    public boolean isOverflowMenuShowPending() throws  {
        return this.mToolbar.isOverflowMenuShowPending();
    }

    public boolean showOverflowMenu() throws  {
        return this.mToolbar.showOverflowMenu();
    }

    public boolean hideOverflowMenu() throws  {
        return this.mToolbar.hideOverflowMenu();
    }

    public void setMenuPrepared() throws  {
        this.mMenuPrepared = true;
    }

    public void setMenu(Menu $r3, MenuPresenter.Callback $r1) throws  {
        if (this.mActionMenuPresenter == null) {
            this.mActionMenuPresenter = new ActionMenuPresenter(this.mToolbar.getContext());
            this.mActionMenuPresenter.setId(C0192R.id.action_menu_presenter);
        }
        this.mActionMenuPresenter.setCallback($r1);
        this.mToolbar.setMenu((MenuBuilder) $r3, this.mActionMenuPresenter);
    }

    public void dismissPopupMenus() throws  {
        this.mToolbar.dismissPopupMenus();
    }

    public int getDisplayOptions() throws  {
        return this.mDisplayOpts;
    }

    public void setDisplayOptions(int $i0) throws  {
        int $i1 = this.mDisplayOpts ^ $i0;
        this.mDisplayOpts = $i0;
        if ($i1 != 0) {
            if (($i1 & 4) != 0) {
                if (($i0 & 4) != 0) {
                    updateNavigationIcon();
                    updateHomeAccessibility();
                } else {
                    this.mToolbar.setNavigationIcon(null);
                }
            }
            if (($i1 & 3) != 0) {
                updateToolbarLogo();
            }
            if (($i1 & 8) != 0) {
                if (($i0 & 8) != 0) {
                    this.mToolbar.setTitle(this.mTitle);
                    this.mToolbar.setSubtitle(this.mSubtitle);
                } else {
                    this.mToolbar.setTitle(null);
                    this.mToolbar.setSubtitle(null);
                }
            }
            if (($i1 & 16) != 0 && this.mCustomView != null) {
                if (($i0 & 16) != 0) {
                    this.mToolbar.addView(this.mCustomView);
                } else {
                    this.mToolbar.removeView(this.mCustomView);
                }
            }
        }
    }

    public void setEmbeddedTabView(ScrollingTabContainerView $r1) throws  {
        if (this.mTabView != null && this.mTabView.getParent() == this.mToolbar) {
            this.mToolbar.removeView(this.mTabView);
        }
        this.mTabView = $r1;
        if ($r1 != null && this.mNavigationMode == 2) {
            this.mToolbar.addView(this.mTabView, 0);
            LayoutParams $r6 = (LayoutParams) this.mTabView.getLayoutParams();
            $r6.width = -2;
            $r6.height = -2;
            $r6.gravity = 8388691;
            $r1.setAllowCollapse(true);
        }
    }

    public boolean hasEmbeddedTabs() throws  {
        return this.mTabView != null;
    }

    public boolean isTitleTruncated() throws  {
        return this.mToolbar.isTitleTruncated();
    }

    public void setCollapsible(boolean $z0) throws  {
        this.mToolbar.setCollapsible($z0);
    }

    public void setHomeButtonEnabled(boolean enable) throws  {
    }

    public int getNavigationMode() throws  {
        return this.mNavigationMode;
    }

    public void setNavigationMode(int $i0) throws  {
        int $i1 = this.mNavigationMode;
        if ($i0 != $i1) {
            switch ($i1) {
                case 1:
                    if (this.mSpinner != null && this.mSpinner.getParent() == this.mToolbar) {
                        this.mToolbar.removeView(this.mSpinner);
                        break;
                    }
                case 2:
                    if (this.mTabView != null && this.mTabView.getParent() == this.mToolbar) {
                        this.mToolbar.removeView(this.mTabView);
                        break;
                    }
                default:
                    break;
            }
            this.mNavigationMode = $i0;
            switch ($i0) {
                case 0:
                    break;
                case 1:
                    ensureSpinner();
                    this.mToolbar.addView(this.mSpinner, 0);
                    break;
                case 2:
                    if (this.mTabView != null) {
                        this.mToolbar.addView(this.mTabView, 0);
                        LayoutParams $r9 = (LayoutParams) this.mTabView.getLayoutParams();
                        $r9.width = -2;
                        $r9.height = -2;
                        $r9.gravity = 8388691;
                        return;
                    }
                    return;
                default:
                    throw new IllegalArgumentException("Invalid navigation mode " + $i0);
            }
        }
    }

    private void ensureSpinner() throws  {
        if (this.mSpinner == null) {
            this.mSpinner = new AppCompatSpinner(getContext(), null, C0192R.attr.actionDropDownStyle);
            this.mSpinner.setLayoutParams(new LayoutParams(-2, -2, 8388627));
        }
    }

    public void setDropdownParams(SpinnerAdapter $r1, OnItemSelectedListener $r2) throws  {
        ensureSpinner();
        this.mSpinner.setAdapter($r1);
        this.mSpinner.setOnItemSelectedListener($r2);
    }

    public void setDropdownSelectedPosition(int $i0) throws  {
        if (this.mSpinner == null) {
            throw new IllegalStateException("Can't set dropdown selected position without an adapter");
        }
        this.mSpinner.setSelection($i0);
    }

    public int getDropdownSelectedPosition() throws  {
        return this.mSpinner != null ? this.mSpinner.getSelectedItemPosition() : 0;
    }

    public int getDropdownItemCount() throws  {
        return this.mSpinner != null ? this.mSpinner.getCount() : 0;
    }

    public void setCustomView(View $r1) throws  {
        if (!(this.mCustomView == null || (this.mDisplayOpts & 16) == 0)) {
            this.mToolbar.removeView(this.mCustomView);
        }
        this.mCustomView = $r1;
        if ($r1 != null && (this.mDisplayOpts & 16) != 0) {
            this.mToolbar.addView(this.mCustomView);
        }
    }

    public View getCustomView() throws  {
        return this.mCustomView;
    }

    public void animateToVisibility(int $i0) throws  {
        ViewPropertyAnimatorCompat $r1 = setupAnimatorToVisibility($i0, 200);
        if ($r1 != null) {
            $r1.start();
        }
    }

    public ViewPropertyAnimatorCompat setupAnimatorToVisibility(final int $i0, long $l1) throws  {
        return ViewCompat.animate(this.mToolbar).alpha($i0 == 0 ? 1.0f : 0.0f).setDuration($l1).setListener(new ViewPropertyAnimatorListenerAdapter() {
            private boolean mCanceled = false;

            public void onAnimationStart(View view) throws  {
                ToolbarWidgetWrapper.this.mToolbar.setVisibility(0);
            }

            public void onAnimationEnd(View view) throws  {
                if (!this.mCanceled) {
                    ToolbarWidgetWrapper.this.mToolbar.setVisibility($i0);
                }
            }

            public void onAnimationCancel(View view) throws  {
                this.mCanceled = true;
            }
        });
    }

    public void setNavigationIcon(Drawable $r1) throws  {
        this.mNavIcon = $r1;
        updateNavigationIcon();
    }

    public void setNavigationIcon(int $i0) throws  {
        setNavigationIcon($i0 != 0 ? AppCompatDrawableManager.get().getDrawable(getContext(), $i0) : null);
    }

    public void setNavigationContentDescription(CharSequence $r1) throws  {
        this.mHomeDescription = $r1;
        updateHomeAccessibility();
    }

    public void setNavigationContentDescription(int $i0) throws  {
        setNavigationContentDescription($i0 == 0 ? null : getContext().getString($i0));
    }

    private void updateHomeAccessibility() throws  {
        if ((this.mDisplayOpts & 4) == 0) {
            return;
        }
        if (TextUtils.isEmpty(this.mHomeDescription)) {
            this.mToolbar.setNavigationContentDescription(this.mDefaultNavigationContentDescription);
        } else {
            this.mToolbar.setNavigationContentDescription(this.mHomeDescription);
        }
    }

    private void updateNavigationIcon() throws  {
        if ((this.mDisplayOpts & 4) != 0) {
            this.mToolbar.setNavigationIcon(this.mNavIcon != null ? this.mNavIcon : this.mDefaultNavigationIcon);
        }
    }

    public void saveHierarchyState(@Signature({"(", "Landroid/util/SparseArray", "<", "Landroid/os/Parcelable;", ">;)V"}) SparseArray<Parcelable> $r1) throws  {
        this.mToolbar.saveHierarchyState($r1);
    }

    public void restoreHierarchyState(@Signature({"(", "Landroid/util/SparseArray", "<", "Landroid/os/Parcelable;", ">;)V"}) SparseArray<Parcelable> $r1) throws  {
        this.mToolbar.restoreHierarchyState($r1);
    }

    public void setBackgroundDrawable(Drawable $r1) throws  {
        this.mToolbar.setBackgroundDrawable($r1);
    }

    public int getHeight() throws  {
        return this.mToolbar.getHeight();
    }

    public void setVisibility(int $i0) throws  {
        this.mToolbar.setVisibility($i0);
    }

    public int getVisibility() throws  {
        return this.mToolbar.getVisibility();
    }

    public void setMenuCallbacks(MenuPresenter.Callback $r1, MenuBuilder.Callback $r2) throws  {
        this.mToolbar.setMenuCallbacks($r1, $r2);
    }

    public Menu getMenu() throws  {
        return this.mToolbar.getMenu();
    }
}
