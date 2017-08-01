package android.support.v7.app;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter;
import android.support.v4.view.ViewPropertyAnimatorUpdateListener;
import android.support.v7.app.ActionBar.LayoutParams;
import android.support.v7.app.ActionBar.OnMenuVisibilityListener;
import android.support.v7.app.ActionBar.OnNavigationListener;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBar.TabListener;
import android.support.v7.appcompat.C0192R;
import android.support.v7.view.ActionBarPolicy;
import android.support.v7.view.ActionMode;
import android.support.v7.view.ActionMode.Callback;
import android.support.v7.view.SupportMenuInflater;
import android.support.v7.view.ViewPropertyAnimatorCompatSet;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.view.menu.SubMenuBuilder;
import android.support.v7.widget.ActionBarContainer;
import android.support.v7.widget.ActionBarContextView;
import android.support.v7.widget.ActionBarOverlayLayout;
import android.support.v7.widget.ActionBarOverlayLayout.ActionBarVisibilityCallback;
import android.support.v7.widget.AppCompatDrawableManager;
import android.support.v7.widget.DecorToolbar;
import android.support.v7.widget.ScrollingTabContainerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.SpinnerAdapter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class WindowDecorActionBar extends ActionBar implements ActionBarVisibilityCallback {
    static final /* synthetic */ boolean $assertionsDisabled = (!WindowDecorActionBar.class.desiredAssertionStatus());
    private static final boolean ALLOW_SHOW_HIDE_ANIMATIONS;
    private static final long FADE_IN_DURATION_MS = 200;
    private static final long FADE_OUT_DURATION_MS = 100;
    private static final int INVALID_POSITION = -1;
    private static final String TAG = "WindowDecorActionBar";
    private static final Interpolator sHideInterpolator = new AccelerateInterpolator();
    private static final Interpolator sShowInterpolator = new DecelerateInterpolator();
    ActionModeImpl mActionMode;
    private Activity mActivity;
    private ActionBarContainer mContainerView;
    private boolean mContentAnimations = true;
    private View mContentView;
    private Context mContext;
    private ActionBarContextView mContextView;
    private int mCurWindowVisibility = 0;
    private ViewPropertyAnimatorCompatSet mCurrentShowAnim;
    private DecorToolbar mDecorToolbar;
    ActionMode mDeferredDestroyActionMode;
    Callback mDeferredModeDestroyCallback;
    private Dialog mDialog;
    private boolean mDisplayHomeAsUpSet;
    private boolean mHasEmbeddedTabs;
    private boolean mHiddenByApp;
    private boolean mHiddenBySystem;
    final ViewPropertyAnimatorListener mHideListener = new C01891();
    boolean mHideOnContentScroll;
    private boolean mLastMenuVisibility;
    private ArrayList<OnMenuVisibilityListener> mMenuVisibilityListeners = new ArrayList();
    private boolean mNowShowing = true;
    private ActionBarOverlayLayout mOverlayLayout;
    private int mSavedTabPosition = -1;
    private TabImpl mSelectedTab;
    private boolean mShowHideAnimationEnabled;
    final ViewPropertyAnimatorListener mShowListener = new C01902();
    private boolean mShowingForMode;
    private ScrollingTabContainerView mTabScrollView;
    private ArrayList<TabImpl> mTabs = new ArrayList();
    private Context mThemedContext;
    final ViewPropertyAnimatorUpdateListener mUpdateListener = new C01913();

    class C01891 extends ViewPropertyAnimatorListenerAdapter {
        C01891() throws  {
        }

        public void onAnimationEnd(View view) throws  {
            if (WindowDecorActionBar.this.mContentAnimations && WindowDecorActionBar.this.mContentView != null) {
                ViewCompat.setTranslationY(WindowDecorActionBar.this.mContentView, 0.0f);
                ViewCompat.setTranslationY(WindowDecorActionBar.this.mContainerView, 0.0f);
            }
            WindowDecorActionBar.this.mContainerView.setVisibility(8);
            WindowDecorActionBar.this.mContainerView.setTransitioning(false);
            WindowDecorActionBar.this.mCurrentShowAnim = null;
            WindowDecorActionBar.this.completeDeferredDestroyActionMode();
            if (WindowDecorActionBar.this.mOverlayLayout != null) {
                ViewCompat.requestApplyInsets(WindowDecorActionBar.this.mOverlayLayout);
            }
        }
    }

    class C01902 extends ViewPropertyAnimatorListenerAdapter {
        C01902() throws  {
        }

        public void onAnimationEnd(View view) throws  {
            WindowDecorActionBar.this.mCurrentShowAnim = null;
            WindowDecorActionBar.this.mContainerView.requestLayout();
        }
    }

    class C01913 implements ViewPropertyAnimatorUpdateListener {
        C01913() throws  {
        }

        public void onAnimationUpdate(View view) throws  {
            ((View) WindowDecorActionBar.this.mContainerView.getParent()).invalidate();
        }
    }

    public class ActionModeImpl extends ActionMode implements MenuBuilder.Callback {
        private final Context mActionModeContext;
        private Callback mCallback;
        private WeakReference<View> mCustomView;
        private final MenuBuilder mMenu;

        public ActionModeImpl(Context $r2, Callback $r3) throws  {
            this.mActionModeContext = $r2;
            this.mCallback = $r3;
            this.mMenu = new MenuBuilder($r2).setDefaultShowAsAction(1);
            this.mMenu.setCallback(this);
        }

        public MenuInflater getMenuInflater() throws  {
            return new SupportMenuInflater(this.mActionModeContext);
        }

        public Menu getMenu() throws  {
            return this.mMenu;
        }

        public void finish() throws  {
            if (WindowDecorActionBar.this.mActionMode == this) {
                if (WindowDecorActionBar.checkShowingFlags(WindowDecorActionBar.this.mHiddenByApp, WindowDecorActionBar.this.mHiddenBySystem, false)) {
                    this.mCallback.onDestroyActionMode(this);
                } else {
                    WindowDecorActionBar.this.mDeferredDestroyActionMode = this;
                    WindowDecorActionBar.this.mDeferredModeDestroyCallback = this.mCallback;
                }
                this.mCallback = null;
                WindowDecorActionBar.this.animateToMode(false);
                WindowDecorActionBar.this.mContextView.closeMode();
                WindowDecorActionBar.this.mDecorToolbar.getViewGroup().sendAccessibilityEvent(32);
                WindowDecorActionBar.this.mOverlayLayout.setHideOnContentScrollEnabled(WindowDecorActionBar.this.mHideOnContentScroll);
                WindowDecorActionBar.this.mActionMode = null;
            }
        }

        public void invalidate() throws  {
            if (WindowDecorActionBar.this.mActionMode == this) {
                this.mMenu.stopDispatchingItemsChanged();
                try {
                    this.mCallback.onPrepareActionMode(this, this.mMenu);
                } finally {
                    this.mMenu.startDispatchingItemsChanged();
                }
            }
        }

        public boolean dispatchOnCreate() throws  {
            this.mMenu.stopDispatchingItemsChanged();
            try {
                boolean $z0 = this.mCallback.onCreateActionMode(this, this.mMenu);
                return $z0;
            } finally {
                this.mMenu.startDispatchingItemsChanged();
            }
        }

        public void setCustomView(View $r1) throws  {
            WindowDecorActionBar.this.mContextView.setCustomView($r1);
            this.mCustomView = new WeakReference($r1);
        }

        public void setSubtitle(CharSequence $r1) throws  {
            WindowDecorActionBar.this.mContextView.setSubtitle($r1);
        }

        public void setTitle(CharSequence $r1) throws  {
            WindowDecorActionBar.this.mContextView.setTitle($r1);
        }

        public void setTitle(int $i0) throws  {
            setTitle(WindowDecorActionBar.this.mContext.getResources().getString($i0));
        }

        public void setSubtitle(int $i0) throws  {
            setSubtitle(WindowDecorActionBar.this.mContext.getResources().getString($i0));
        }

        public CharSequence getTitle() throws  {
            return WindowDecorActionBar.this.mContextView.getTitle();
        }

        public CharSequence getSubtitle() throws  {
            return WindowDecorActionBar.this.mContextView.getSubtitle();
        }

        public void setTitleOptionalHint(boolean $z0) throws  {
            super.setTitleOptionalHint($z0);
            WindowDecorActionBar.this.mContextView.setTitleOptional($z0);
        }

        public boolean isTitleOptional() throws  {
            return WindowDecorActionBar.this.mContextView.isTitleOptional();
        }

        public View getCustomView() throws  {
            return this.mCustomView != null ? (View) this.mCustomView.get() : null;
        }

        public boolean onMenuItemSelected(MenuBuilder menu, MenuItem $r2) throws  {
            if (this.mCallback != null) {
                return this.mCallback.onActionItemClicked(this, $r2);
            }
            return false;
        }

        public void onCloseMenu(MenuBuilder menu, boolean allMenusAreClosing) throws  {
        }

        public boolean onSubMenuSelected(SubMenuBuilder $r1) throws  {
            if (this.mCallback == null) {
                return false;
            }
            if (!$r1.hasVisibleItems()) {
                return true;
            }
            new MenuPopupHelper(WindowDecorActionBar.this.getThemedContext(), $r1).show();
            return true;
        }

        public void onCloseSubMenu(SubMenuBuilder menu) throws  {
        }

        public void onMenuModeChange(MenuBuilder menu) throws  {
            if (this.mCallback != null) {
                invalidate();
                WindowDecorActionBar.this.mContextView.showOverflowMenu();
            }
        }
    }

    public class TabImpl extends Tab {
        private TabListener mCallback;
        private CharSequence mContentDesc;
        private View mCustomView;
        private Drawable mIcon;
        private int mPosition = -1;
        private Object mTag;
        private CharSequence mText;

        public Object getTag() throws  {
            return this.mTag;
        }

        public Tab setTag(Object $r1) throws  {
            this.mTag = $r1;
            return this;
        }

        public TabListener getCallback() throws  {
            return this.mCallback;
        }

        public Tab setTabListener(TabListener $r1) throws  {
            this.mCallback = $r1;
            return this;
        }

        public View getCustomView() throws  {
            return this.mCustomView;
        }

        public Tab setCustomView(View $r1) throws  {
            this.mCustomView = $r1;
            if (this.mPosition < 0) {
                return this;
            }
            WindowDecorActionBar.this.mTabScrollView.updateTab(this.mPosition);
            return this;
        }

        public Tab setCustomView(int $i0) throws  {
            return setCustomView(LayoutInflater.from(WindowDecorActionBar.this.getThemedContext()).inflate($i0, null));
        }

        public Drawable getIcon() throws  {
            return this.mIcon;
        }

        public int getPosition() throws  {
            return this.mPosition;
        }

        public void setPosition(int $i0) throws  {
            this.mPosition = $i0;
        }

        public CharSequence getText() throws  {
            return this.mText;
        }

        public Tab setIcon(Drawable $r1) throws  {
            this.mIcon = $r1;
            if (this.mPosition < 0) {
                return this;
            }
            WindowDecorActionBar.this.mTabScrollView.updateTab(this.mPosition);
            return this;
        }

        public Tab setIcon(int $i0) throws  {
            return setIcon(AppCompatDrawableManager.get().getDrawable(WindowDecorActionBar.this.mContext, $i0));
        }

        public Tab setText(CharSequence $r1) throws  {
            this.mText = $r1;
            if (this.mPosition < 0) {
                return this;
            }
            WindowDecorActionBar.this.mTabScrollView.updateTab(this.mPosition);
            return this;
        }

        public Tab setText(int $i0) throws  {
            return setText(WindowDecorActionBar.this.mContext.getResources().getText($i0));
        }

        public void select() throws  {
            WindowDecorActionBar.this.selectTab(this);
        }

        public Tab setContentDescription(int $i0) throws  {
            return setContentDescription(WindowDecorActionBar.this.mContext.getResources().getText($i0));
        }

        public Tab setContentDescription(CharSequence $r1) throws  {
            this.mContentDesc = $r1;
            if (this.mPosition < 0) {
                return this;
            }
            WindowDecorActionBar.this.mTabScrollView.updateTab(this.mPosition);
            return this;
        }

        public CharSequence getContentDescription() throws  {
            return this.mContentDesc;
        }
    }

    public void setDisplayOptions(int r1, int r2) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.DecodeException: Load method exception in method: android.support.v7.app.WindowDecorActionBar.setDisplayOptions(int, int):void
	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:116)
	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:249)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
Caused by: jadx.core.utils.exceptions.DecodeException: Unknown instruction: not-int
	at jadx.core.dex.instructions.InsnDecoder.decode(InsnDecoder.java:568)
	at jadx.core.dex.instructions.InsnDecoder.process(InsnDecoder.java:56)
	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:102)
	... 5 more
*/
        /*
        // Can't load method instructions.
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.app.WindowDecorActionBar.setDisplayOptions(int, int):void");
    }

    static {
        boolean $z1 = true;
        if (VERSION.SDK_INT < 14) {
            $z1 = false;
        }
        ALLOW_SHOW_HIDE_ANIMATIONS = $z1;
    }

    public WindowDecorActionBar(Activity $r1, boolean $z0) throws  {
        this.mActivity = $r1;
        View $r7 = $r1.getWindow().getDecorView();
        init($r7);
        if (!$z0) {
            this.mContentView = $r7.findViewById(16908290);
        }
    }

    public WindowDecorActionBar(Dialog $r1) throws  {
        this.mDialog = $r1;
        init($r1.getWindow().getDecorView());
    }

    public WindowDecorActionBar(View $r1) throws  {
        if ($assertionsDisabled || $r1.isInEditMode()) {
            init($r1);
            return;
        }
        throw new AssertionError();
    }

    private void init(View $r1) throws  {
        this.mOverlayLayout = (ActionBarOverlayLayout) $r1.findViewById(C0192R.id.decor_content_parent);
        if (this.mOverlayLayout != null) {
            this.mOverlayLayout.setActionBarVisibilityCallback(this);
        }
        this.mDecorToolbar = getDecorToolbar($r1.findViewById(C0192R.id.action_bar));
        this.mContextView = (ActionBarContextView) $r1.findViewById(C0192R.id.action_context_bar);
        this.mContainerView = (ActionBarContainer) $r1.findViewById(C0192R.id.action_bar_container);
        if (this.mDecorToolbar == null || this.mContextView == null || this.mContainerView == null) {
            throw new IllegalStateException(getClass().getSimpleName() + " can only be used " + "with a compatible window decor layout");
        }
        this.mContext = this.mDecorToolbar.getContext();
        boolean z = (this.mDecorToolbar.getDisplayOptions() & 4) != 0;
        if (z) {
            this.mDisplayHomeAsUpSet = true;
        }
        Context $r12 = this.mContext;
        ActionBarPolicy $r13 = ActionBarPolicy.get($r12);
        if ($r13.enableHomeButtonByDefault() || z) {
            z = true;
        } else {
            z = false;
        }
        setHomeButtonEnabled(z);
        setHasEmbeddedTabs($r13.hasEmbeddedTabs());
        TypedArray $r14 = this.mContext.obtainStyledAttributes(null, C0192R.styleable.ActionBar, C0192R.attr.actionBarStyle, 0);
        if ($r14.getBoolean(C0192R.styleable.ActionBar_hideOnContentScroll, false)) {
            setHideOnContentScrollEnabled(true);
        }
        int $i0 = $r14.getDimensionPixelSize(C0192R.styleable.ActionBar_elevation, 0);
        if ($i0 != 0) {
            setElevation((float) $i0);
        }
        $r14.recycle();
    }

    private DecorToolbar getDecorToolbar(View $r2) throws  {
        if ($r2 instanceof DecorToolbar) {
            return (DecorToolbar) $r2;
        }
        if ($r2 instanceof Toolbar) {
            return ((Toolbar) $r2).getWrapper();
        }
        throw new IllegalStateException(new StringBuilder().append("Can't make a decor toolbar out of ").append($r2).toString() != null ? $r2.getClass().getSimpleName() : "null");
    }

    public void setElevation(float $f0) throws  {
        ViewCompat.setElevation(this.mContainerView, $f0);
    }

    public float getElevation() throws  {
        return ViewCompat.getElevation(this.mContainerView);
    }

    public void onConfigurationChanged(Configuration newConfig) throws  {
        setHasEmbeddedTabs(ActionBarPolicy.get(this.mContext).hasEmbeddedTabs());
    }

    private void setHasEmbeddedTabs(boolean $z0) throws  {
        boolean $z2;
        boolean $z1 = true;
        this.mHasEmbeddedTabs = $z0;
        if (this.mHasEmbeddedTabs) {
            this.mContainerView.setTabContainer(null);
            this.mDecorToolbar.setEmbeddedTabView(this.mTabScrollView);
        } else {
            this.mDecorToolbar.setEmbeddedTabView(null);
            this.mContainerView.setTabContainer(this.mTabScrollView);
        }
        if (getNavigationMode() == 2) {
            $z0 = true;
        } else {
            $z0 = false;
        }
        if (this.mTabScrollView != null) {
            if ($z0) {
                this.mTabScrollView.setVisibility(0);
                if (this.mOverlayLayout != null) {
                    ViewCompat.requestApplyInsets(this.mOverlayLayout);
                }
            } else {
                this.mTabScrollView.setVisibility(8);
            }
        }
        DecorToolbar $r1 = this.mDecorToolbar;
        if (this.mHasEmbeddedTabs || !$z0) {
            $z2 = false;
        } else {
            $z2 = true;
        }
        $r1.setCollapsible($z2);
        ActionBarOverlayLayout $r4 = this.mOverlayLayout;
        if (this.mHasEmbeddedTabs || !$z0) {
            $z1 = false;
        }
        $r4.setHasNonEmbeddedTabs($z1);
    }

    private void ensureTabsExist() throws  {
        if (this.mTabScrollView == null) {
            ScrollingTabContainerView $r1 = new ScrollingTabContainerView(this.mContext);
            if (this.mHasEmbeddedTabs) {
                $r1.setVisibility(0);
                this.mDecorToolbar.setEmbeddedTabView($r1);
            } else {
                if (getNavigationMode() == 2) {
                    $r1.setVisibility(0);
                    if (this.mOverlayLayout != null) {
                        ViewCompat.requestApplyInsets(this.mOverlayLayout);
                    }
                } else {
                    $r1.setVisibility(8);
                }
                this.mContainerView.setTabContainer($r1);
            }
            this.mTabScrollView = $r1;
        }
    }

    void completeDeferredDestroyActionMode() throws  {
        if (this.mDeferredModeDestroyCallback != null) {
            this.mDeferredModeDestroyCallback.onDestroyActionMode(this.mDeferredDestroyActionMode);
            this.mDeferredDestroyActionMode = null;
            this.mDeferredModeDestroyCallback = null;
        }
    }

    public void onWindowVisibilityChanged(int $i0) throws  {
        this.mCurWindowVisibility = $i0;
    }

    public void setShowHideAnimationEnabled(boolean $z0) throws  {
        this.mShowHideAnimationEnabled = $z0;
        if (!$z0 && this.mCurrentShowAnim != null) {
            this.mCurrentShowAnim.cancel();
        }
    }

    public void addOnMenuVisibilityListener(OnMenuVisibilityListener $r1) throws  {
        this.mMenuVisibilityListeners.add($r1);
    }

    public void removeOnMenuVisibilityListener(OnMenuVisibilityListener $r1) throws  {
        this.mMenuVisibilityListeners.remove($r1);
    }

    public void dispatchMenuVisibilityChanged(boolean $z0) throws  {
        if ($z0 != this.mLastMenuVisibility) {
            this.mLastMenuVisibility = $z0;
            int $i0 = this.mMenuVisibilityListeners.size();
            for (int $i1 = 0; $i1 < $i0; $i1++) {
                ((OnMenuVisibilityListener) this.mMenuVisibilityListeners.get($i1)).onMenuVisibilityChanged($z0);
            }
        }
    }

    public void setCustomView(int $i0) throws  {
        setCustomView(LayoutInflater.from(getThemedContext()).inflate($i0, this.mDecorToolbar.getViewGroup(), false));
    }

    public void setDisplayUseLogoEnabled(boolean $z0) throws  {
        setDisplayOptions($z0 ? (byte) 1 : (byte) 0, 1);
    }

    public void setDisplayShowHomeEnabled(boolean $z0) throws  {
        setDisplayOptions($z0 ? (byte) 2 : (byte) 0, 2);
    }

    public void setDisplayHomeAsUpEnabled(boolean $z0) throws  {
        setDisplayOptions($z0 ? (byte) 4 : (byte) 0, 4);
    }

    public void setDisplayShowTitleEnabled(boolean $z0) throws  {
        setDisplayOptions($z0 ? (byte) 8 : (byte) 0, 8);
    }

    public void setDisplayShowCustomEnabled(boolean $z0) throws  {
        setDisplayOptions($z0 ? (byte) 16 : (byte) 0, 16);
    }

    public void setHomeButtonEnabled(boolean $z0) throws  {
        this.mDecorToolbar.setHomeButtonEnabled($z0);
    }

    public void setTitle(int $i0) throws  {
        setTitle(this.mContext.getString($i0));
    }

    public void setSubtitle(int $i0) throws  {
        setSubtitle(this.mContext.getString($i0));
    }

    public void setSelectedNavigationItem(int $i0) throws  {
        switch (this.mDecorToolbar.getNavigationMode()) {
            case 1:
                this.mDecorToolbar.setDropdownSelectedPosition($i0);
                return;
            case 2:
                selectTab((Tab) this.mTabs.get($i0));
                return;
            default:
                throw new IllegalStateException("setSelectedNavigationIndex not valid for current navigation mode");
        }
    }

    public void removeAllTabs() throws  {
        cleanupTabs();
    }

    private void cleanupTabs() throws  {
        if (this.mSelectedTab != null) {
            selectTab(null);
        }
        this.mTabs.clear();
        if (this.mTabScrollView != null) {
            this.mTabScrollView.removeAllTabs();
        }
        this.mSavedTabPosition = -1;
    }

    public void setTitle(CharSequence $r1) throws  {
        this.mDecorToolbar.setTitle($r1);
    }

    public void setWindowTitle(CharSequence $r1) throws  {
        this.mDecorToolbar.setWindowTitle($r1);
    }

    public boolean requestFocus() throws  {
        ViewGroup $r2 = this.mDecorToolbar.getViewGroup();
        if ($r2 == null || $r2.hasFocus()) {
            return false;
        }
        $r2.requestFocus();
        return true;
    }

    public void setSubtitle(CharSequence $r1) throws  {
        this.mDecorToolbar.setSubtitle($r1);
    }

    public void setDisplayOptions(int $i0) throws  {
        if (($i0 & 4) != 0) {
            this.mDisplayHomeAsUpSet = true;
        }
        this.mDecorToolbar.setDisplayOptions($i0);
    }

    public void setBackgroundDrawable(Drawable $r1) throws  {
        this.mContainerView.setPrimaryBackground($r1);
    }

    public void setStackedBackgroundDrawable(Drawable $r1) throws  {
        this.mContainerView.setStackedBackground($r1);
    }

    public void setSplitBackgroundDrawable(Drawable d) throws  {
    }

    public View getCustomView() throws  {
        return this.mDecorToolbar.getCustomView();
    }

    public CharSequence getTitle() throws  {
        return this.mDecorToolbar.getTitle();
    }

    public CharSequence getSubtitle() throws  {
        return this.mDecorToolbar.getSubtitle();
    }

    public int getNavigationMode() throws  {
        return this.mDecorToolbar.getNavigationMode();
    }

    public int getDisplayOptions() throws  {
        return this.mDecorToolbar.getDisplayOptions();
    }

    public ActionMode startActionMode(Callback $r1) throws  {
        if (this.mActionMode != null) {
            this.mActionMode.finish();
        }
        this.mOverlayLayout.setHideOnContentScrollEnabled(false);
        this.mContextView.killMode();
        ActionModeImpl $r2 = new ActionModeImpl(this.mContextView.getContext(), $r1);
        if (!$r2.dispatchOnCreate()) {
            return null;
        }
        $r2.invalidate();
        this.mContextView.initForMode($r2);
        animateToMode(true);
        this.mContextView.sendAccessibilityEvent(32);
        this.mActionMode = $r2;
        return $r2;
    }

    private void configureTab(Tab $r1, int $i0) throws  {
        TabImpl $r2 = (TabImpl) $r1;
        if ($r2.getCallback() == null) {
            throw new IllegalStateException("Action Bar Tab must have a Callback");
        }
        $r2.setPosition($i0);
        this.mTabs.add($i0, $r2);
        int $i1 = this.mTabs.size();
        for ($i0++; $i0 < $i1; $i0++) {
            ((TabImpl) this.mTabs.get($i0)).setPosition($i0);
        }
    }

    public void addTab(Tab $r1) throws  {
        addTab($r1, this.mTabs.isEmpty());
    }

    public void addTab(Tab $r1, int $i0) throws  {
        addTab($r1, $i0, this.mTabs.isEmpty());
    }

    public void addTab(Tab $r1, boolean $z0) throws  {
        ensureTabsExist();
        this.mTabScrollView.addTab($r1, $z0);
        configureTab($r1, this.mTabs.size());
        if ($z0) {
            selectTab($r1);
        }
    }

    public void addTab(Tab $r1, int $i0, boolean $z0) throws  {
        ensureTabsExist();
        this.mTabScrollView.addTab($r1, $i0, $z0);
        configureTab($r1, $i0);
        if ($z0) {
            selectTab($r1);
        }
    }

    public Tab newTab() throws  {
        return new TabImpl();
    }

    public void removeTab(Tab $r1) throws  {
        removeTabAt($r1.getPosition());
    }

    public void removeTabAt(int $i0) throws  {
        if (this.mTabScrollView != null) {
            int $i1 = this.mSelectedTab != null ? this.mSelectedTab.getPosition() : this.mSavedTabPosition;
            this.mTabScrollView.removeTabAt($i0);
            TabImpl $r2 = (TabImpl) this.mTabs.remove($i0);
            if ($r2 != null) {
                $r2.setPosition(-1);
            }
            int $i2 = this.mTabs.size();
            for (int $i3 = $i0; $i3 < $i2; $i3++) {
                ((TabImpl) this.mTabs.get($i3)).setPosition($i3);
            }
            if ($i1 == $i0) {
                selectTab(this.mTabs.isEmpty() ? null : (TabImpl) this.mTabs.get(Math.max(0, $i0 - 1)));
            }
        }
    }

    public void selectTab(Tab $r1) throws  {
        int $i0 = -1;
        if (getNavigationMode() != 2) {
            this.mSavedTabPosition = $r1 != null ? $r1.getPosition() : -1;
            return;
        }
        FragmentTransaction $r7;
        if (!(this.mActivity instanceof FragmentActivity) || this.mDecorToolbar.getViewGroup().isInEditMode()) {
            $r7 = null;
        } else {
            $r7 = ((FragmentActivity) this.mActivity).getSupportFragmentManager().beginTransaction().disallowAddToBackStack();
        }
        if (this.mSelectedTab != $r1) {
            ScrollingTabContainerView $r10 = this.mTabScrollView;
            if ($r1 != null) {
                $i0 = $r1.getPosition();
            }
            $r10.setTabSelected($i0);
            if (this.mSelectedTab != null) {
                this.mSelectedTab.getCallback().onTabUnselected(this.mSelectedTab, $r7);
            }
            this.mSelectedTab = (TabImpl) $r1;
            if (this.mSelectedTab != null) {
                this.mSelectedTab.getCallback().onTabSelected(this.mSelectedTab, $r7);
            }
        } else if (this.mSelectedTab != null) {
            this.mSelectedTab.getCallback().onTabReselected(this.mSelectedTab, $r7);
            this.mTabScrollView.animateToTab($r1.getPosition());
        }
        if ($r7 != null && !$r7.isEmpty()) {
            $r7.commit();
        }
    }

    public Tab getSelectedTab() throws  {
        return this.mSelectedTab;
    }

    public int getHeight() throws  {
        return this.mContainerView.getHeight();
    }

    public void enableContentAnimations(boolean $z0) throws  {
        this.mContentAnimations = $z0;
    }

    public void show() throws  {
        if (this.mHiddenByApp) {
            this.mHiddenByApp = false;
            updateVisibility(false);
        }
    }

    private void showForActionMode() throws  {
        if (!this.mShowingForMode) {
            this.mShowingForMode = true;
            if (this.mOverlayLayout != null) {
                this.mOverlayLayout.setShowingForActionMode(true);
            }
            updateVisibility(false);
        }
    }

    public void showForSystem() throws  {
        if (this.mHiddenBySystem) {
            this.mHiddenBySystem = false;
            updateVisibility(true);
        }
    }

    public void hide() throws  {
        if (!this.mHiddenByApp) {
            this.mHiddenByApp = true;
            updateVisibility(false);
        }
    }

    private void hideForActionMode() throws  {
        if (this.mShowingForMode) {
            this.mShowingForMode = false;
            if (this.mOverlayLayout != null) {
                this.mOverlayLayout.setShowingForActionMode(false);
            }
            updateVisibility(false);
        }
    }

    public void hideForSystem() throws  {
        if (!this.mHiddenBySystem) {
            this.mHiddenBySystem = true;
            updateVisibility(true);
        }
    }

    public void setHideOnContentScrollEnabled(boolean $z0) throws  {
        if (!$z0 || this.mOverlayLayout.isInOverlayMode()) {
            this.mHideOnContentScroll = $z0;
            this.mOverlayLayout.setHideOnContentScrollEnabled($z0);
            return;
        }
        throw new IllegalStateException("Action bar must be in overlay mode (Window.FEATURE_OVERLAY_ACTION_BAR) to enable hide on content scroll");
    }

    public boolean isHideOnContentScrollEnabled() throws  {
        return this.mOverlayLayout.isHideOnContentScrollEnabled();
    }

    public int getHideOffset() throws  {
        return this.mOverlayLayout.getActionBarHideOffset();
    }

    public void setHideOffset(int $i0) throws  {
        if ($i0 == 0 || this.mOverlayLayout.isInOverlayMode()) {
            this.mOverlayLayout.setActionBarHideOffset($i0);
            return;
        }
        throw new IllegalStateException("Action bar must be in overlay mode (Window.FEATURE_OVERLAY_ACTION_BAR) to set a non-zero hide offset");
    }

    private static boolean checkShowingFlags(boolean $z0, boolean $z1, boolean $z2) throws  {
        if ($z2) {
            return true;
        }
        return ($z0 || $z1) ? false : true;
    }

    private void updateVisibility(boolean $z0) throws  {
        if (checkShowingFlags(this.mHiddenByApp, this.mHiddenBySystem, this.mShowingForMode)) {
            if (!this.mNowShowing) {
                this.mNowShowing = true;
                doShow($z0);
            }
        } else if (this.mNowShowing) {
            this.mNowShowing = false;
            doHide($z0);
        }
    }

    public void doShow(boolean $z0) throws  {
        if (this.mCurrentShowAnim != null) {
            this.mCurrentShowAnim.cancel();
        }
        this.mContainerView.setVisibility(0);
        if (this.mCurWindowVisibility == 0 && ALLOW_SHOW_HIDE_ANIMATIONS && (this.mShowHideAnimationEnabled || $z0)) {
            ViewCompat.setTranslationY(this.mContainerView, 0.0f);
            float $f0 = (float) (-this.mContainerView.getHeight());
            if ($z0) {
                int[] $r2 = new int[]{0, 0};
                this.mContainerView.getLocationInWindow($r2);
                $f0 -= (float) $r2[1];
            }
            ViewCompat.setTranslationY(this.mContainerView, $f0);
            ViewPropertyAnimatorCompatSet $r1 = new ViewPropertyAnimatorCompatSet();
            ViewPropertyAnimatorCompat $r4 = ViewCompat.animate(this.mContainerView).translationY(0.0f);
            $r4.setUpdateListener(this.mUpdateListener);
            $r1.play($r4);
            if (this.mContentAnimations && this.mContentView != null) {
                ViewCompat.setTranslationY(this.mContentView, $f0);
                $r1.play(ViewCompat.animate(this.mContentView).translationY(0.0f));
            }
            $r1.setInterpolator(sShowInterpolator);
            $r1.setDuration(250);
            ViewPropertyAnimatorListener $r8 = this.mShowListener;
            $r1.setListener($r8);
            this.mCurrentShowAnim = $r1;
            $r1.start();
        } else {
            ViewCompat.setAlpha(this.mContainerView, 1.0f);
            ViewCompat.setTranslationY(this.mContainerView, 0.0f);
            if (this.mContentAnimations && this.mContentView != null) {
                ViewCompat.setTranslationY(this.mContentView, 0.0f);
            }
            this.mShowListener.onAnimationEnd(null);
        }
        if (this.mOverlayLayout != null) {
            ActionBarOverlayLayout $r9 = this.mOverlayLayout;
            ViewCompat.requestApplyInsets($r9);
        }
    }

    public void doHide(boolean $z0) throws  {
        if (this.mCurrentShowAnim != null) {
            this.mCurrentShowAnim.cancel();
        }
        if (this.mCurWindowVisibility == 0 && ALLOW_SHOW_HIDE_ANIMATIONS && (this.mShowHideAnimationEnabled || $z0)) {
            ViewCompat.setAlpha(this.mContainerView, 1.0f);
            this.mContainerView.setTransitioning(true);
            ViewPropertyAnimatorCompatSet $r1 = new ViewPropertyAnimatorCompatSet();
            float $f0 = (float) (-this.mContainerView.getHeight());
            if ($z0) {
                int[] $r2 = new int[]{0, 0};
                this.mContainerView.getLocationInWindow($r2);
                $f0 -= (float) $r2[1];
            }
            ViewPropertyAnimatorCompat $r4 = ViewCompat.animate(this.mContainerView).translationY($f0);
            $r4.setUpdateListener(this.mUpdateListener);
            $r1.play($r4);
            if (this.mContentAnimations && this.mContentView != null) {
                $r1.play(ViewCompat.animate(this.mContentView).translationY($f0));
            }
            $r1.setInterpolator(sHideInterpolator);
            $r1.setDuration(250);
            ViewPropertyAnimatorListener $r8 = this.mHideListener;
            $r1.setListener($r8);
            this.mCurrentShowAnim = $r1;
            $r1.start();
            return;
        }
        this.mHideListener.onAnimationEnd(null);
    }

    public boolean isShowing() throws  {
        int $i0 = getHeight();
        return this.mNowShowing && ($i0 == 0 || getHideOffset() < $i0);
    }

    public void animateToMode(boolean $z0) throws  {
        ViewPropertyAnimatorCompat $r3;
        ViewPropertyAnimatorCompat $r5;
        if ($z0) {
            showForActionMode();
        } else {
            hideForActionMode();
        }
        if ($z0) {
            $r3 = this.mDecorToolbar.setupAnimatorToVisibility(4, FADE_OUT_DURATION_MS);
            $r5 = this.mContextView.setupAnimatorToVisibility(0, 200);
        } else {
            $r5 = this.mDecorToolbar.setupAnimatorToVisibility(0, 200);
            $r3 = this.mContextView.setupAnimatorToVisibility(8, FADE_OUT_DURATION_MS);
        }
        ViewPropertyAnimatorCompatSet $r1 = new ViewPropertyAnimatorCompatSet();
        $r1.playSequentially($r3, $r5);
        $r1.start();
    }

    public Context getThemedContext() throws  {
        if (this.mThemedContext == null) {
            TypedValue $r1 = new TypedValue();
            this.mContext.getTheme().resolveAttribute(C0192R.attr.actionBarWidgetTheme, $r1, true);
            int $i0 = $r1.resourceId;
            if ($i0 != 0) {
                this.mThemedContext = new ContextThemeWrapper(this.mContext, $i0);
            } else {
                this.mThemedContext = this.mContext;
            }
        }
        return this.mThemedContext;
    }

    public boolean isTitleTruncated() throws  {
        return this.mDecorToolbar != null && this.mDecorToolbar.isTitleTruncated();
    }

    public void setHomeAsUpIndicator(Drawable $r1) throws  {
        this.mDecorToolbar.setNavigationIcon($r1);
    }

    public void setHomeAsUpIndicator(int $i0) throws  {
        this.mDecorToolbar.setNavigationIcon($i0);
    }

    public void setHomeActionContentDescription(CharSequence $r1) throws  {
        this.mDecorToolbar.setNavigationContentDescription($r1);
    }

    public void setHomeActionContentDescription(int $i0) throws  {
        this.mDecorToolbar.setNavigationContentDescription($i0);
    }

    public void onContentScrollStarted() throws  {
        if (this.mCurrentShowAnim != null) {
            this.mCurrentShowAnim.cancel();
            this.mCurrentShowAnim = null;
        }
    }

    public void onContentScrollStopped() throws  {
    }

    public boolean collapseActionView() throws  {
        if (this.mDecorToolbar == null || !this.mDecorToolbar.hasExpandedActionView()) {
            return false;
        }
        this.mDecorToolbar.collapseActionView();
        return true;
    }

    public void setCustomView(View $r1) throws  {
        this.mDecorToolbar.setCustomView($r1);
    }

    public void setCustomView(View $r1, LayoutParams $r2) throws  {
        $r1.setLayoutParams($r2);
        this.mDecorToolbar.setCustomView($r1);
    }

    public void setListNavigationCallbacks(SpinnerAdapter $r1, OnNavigationListener $r2) throws  {
        this.mDecorToolbar.setDropdownParams($r1, new NavItemSelectedListener($r2));
    }

    public int getSelectedNavigationIndex() throws  {
        switch (this.mDecorToolbar.getNavigationMode()) {
            case 1:
                return this.mDecorToolbar.getDropdownSelectedPosition();
            case 2:
                if (this.mSelectedTab != null) {
                    return this.mSelectedTab.getPosition();
                }
                return -1;
            default:
                return -1;
        }
    }

    public int getNavigationItemCount() throws  {
        switch (this.mDecorToolbar.getNavigationMode()) {
            case 1:
                return this.mDecorToolbar.getDropdownItemCount();
            case 2:
                return this.mTabs.size();
            default:
                return 0;
        }
    }

    public int getTabCount() throws  {
        return this.mTabs.size();
    }

    public void setNavigationMode(int $i0) throws  {
        boolean $z1;
        boolean $z0 = true;
        int $i1 = this.mDecorToolbar.getNavigationMode();
        switch ($i1) {
            case 2:
                this.mSavedTabPosition = getSelectedNavigationIndex();
                selectTab(null);
                this.mTabScrollView.setVisibility(8);
                break;
            default:
                break;
        }
        if (!($i1 == $i0 || this.mHasEmbeddedTabs || this.mOverlayLayout == null)) {
            ViewCompat.requestApplyInsets(this.mOverlayLayout);
        }
        this.mDecorToolbar.setNavigationMode($i0);
        switch ($i0) {
            case 2:
                ensureTabsExist();
                this.mTabScrollView.setVisibility(0);
                if (this.mSavedTabPosition != -1) {
                    setSelectedNavigationItem(this.mSavedTabPosition);
                    this.mSavedTabPosition = -1;
                    break;
                }
                break;
            default:
                break;
        }
        DecorToolbar $r1 = this.mDecorToolbar;
        if ($i0 != 2 || this.mHasEmbeddedTabs) {
            $z1 = false;
        } else {
            $z1 = true;
        }
        $r1.setCollapsible($z1);
        ActionBarOverlayLayout $r2 = this.mOverlayLayout;
        if ($i0 != 2 || this.mHasEmbeddedTabs) {
            $z0 = false;
        }
        $r2.setHasNonEmbeddedTabs($z0);
    }

    public Tab getTabAt(int $i0) throws  {
        return (Tab) this.mTabs.get($i0);
    }

    public void setIcon(int $i0) throws  {
        this.mDecorToolbar.setIcon($i0);
    }

    public void setIcon(Drawable $r1) throws  {
        this.mDecorToolbar.setIcon($r1);
    }

    public boolean hasIcon() throws  {
        return this.mDecorToolbar.hasIcon();
    }

    public void setLogo(int $i0) throws  {
        this.mDecorToolbar.setLogo($i0);
    }

    public void setLogo(Drawable $r1) throws  {
        this.mDecorToolbar.setLogo($r1);
    }

    public boolean hasLogo() throws  {
        return this.mDecorToolbar.hasLogo();
    }

    public void setDefaultDisplayHomeAsUpEnabled(boolean $z0) throws  {
        if (!this.mDisplayHomeAsUpSet) {
            setDisplayHomeAsUpEnabled($z0);
        }
    }
}
