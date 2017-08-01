package android.support.v7.app;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources.Theme;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar.LayoutParams;
import android.support.v7.app.ActionBar.OnMenuVisibilityListener;
import android.support.v7.app.ActionBar.OnNavigationListener;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.appcompat.C0192R;
import android.support.v7.view.WindowCallbackWrapper;
import android.support.v7.view.menu.ListMenuPresenter;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPresenter;
import android.support.v7.widget.DecorToolbar;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.Toolbar.OnMenuItemClickListener;
import android.support.v7.widget.ToolbarWidgetWrapper;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window.Callback;
import android.widget.SpinnerAdapter;
import java.util.ArrayList;

class ToolbarActionBar extends ActionBar {
    private DecorToolbar mDecorToolbar;
    private boolean mLastMenuVisibility;
    private ListMenuPresenter mListMenuPresenter;
    private boolean mMenuCallbackSet;
    private final OnMenuItemClickListener mMenuClicker = new C01872();
    private final Runnable mMenuInvalidator = new C01861();
    private ArrayList<OnMenuVisibilityListener> mMenuVisibilityListeners = new ArrayList();
    private boolean mToolbarMenuPrepared;
    private Callback mWindowCallback;

    class C01861 implements Runnable {
        C01861() throws  {
        }

        public void run() throws  {
            ToolbarActionBar.this.populateOptionsMenu();
        }
    }

    class C01872 implements OnMenuItemClickListener {
        C01872() throws  {
        }

        public boolean onMenuItemClick(MenuItem $r1) throws  {
            return ToolbarActionBar.this.mWindowCallback.onMenuItemSelected(0, $r1);
        }
    }

    private final class ActionMenuPresenterCallback implements MenuPresenter.Callback {
        private boolean mClosingActionMenu;

        private ActionMenuPresenterCallback() throws  {
        }

        public boolean onOpenSubMenu(MenuBuilder $r1) throws  {
            if (ToolbarActionBar.this.mWindowCallback == null) {
                return false;
            }
            ToolbarActionBar.this.mWindowCallback.onMenuOpened(108, $r1);
            return true;
        }

        public void onCloseMenu(MenuBuilder $r1, boolean allMenusAreClosing) throws  {
            if (!this.mClosingActionMenu) {
                this.mClosingActionMenu = true;
                ToolbarActionBar.this.mDecorToolbar.dismissPopupMenus();
                if (ToolbarActionBar.this.mWindowCallback != null) {
                    ToolbarActionBar.this.mWindowCallback.onPanelClosed(108, $r1);
                }
                this.mClosingActionMenu = false;
            }
        }
    }

    private final class MenuBuilderCallback implements MenuBuilder.Callback {
        public boolean onMenuItemSelected(MenuBuilder menu, MenuItem item) throws  {
            return false;
        }

        private MenuBuilderCallback() throws  {
        }

        public void onMenuModeChange(MenuBuilder $r1) throws  {
            if (ToolbarActionBar.this.mWindowCallback == null) {
                return;
            }
            if (ToolbarActionBar.this.mDecorToolbar.isOverflowMenuShowing()) {
                ToolbarActionBar.this.mWindowCallback.onPanelClosed(108, $r1);
            } else if (ToolbarActionBar.this.mWindowCallback.onPreparePanel(0, null, $r1)) {
                ToolbarActionBar.this.mWindowCallback.onMenuOpened(108, $r1);
            }
        }
    }

    private final class PanelMenuPresenterCallback implements MenuPresenter.Callback {
        private PanelMenuPresenterCallback() throws  {
        }

        public void onCloseMenu(MenuBuilder $r1, boolean allMenusAreClosing) throws  {
            if (ToolbarActionBar.this.mWindowCallback != null) {
                ToolbarActionBar.this.mWindowCallback.onPanelClosed(0, $r1);
            }
        }

        public boolean onOpenSubMenu(MenuBuilder $r1) throws  {
            if ($r1 == null && ToolbarActionBar.this.mWindowCallback != null) {
                ToolbarActionBar.this.mWindowCallback.onMenuOpened(0, $r1);
            }
            return true;
        }
    }

    private class ToolbarCallbackWrapper extends WindowCallbackWrapper {
        public ToolbarCallbackWrapper(Callback $r2) throws  {
            super($r2);
        }

        public boolean onPreparePanel(int $i0, View $r1, Menu $r2) throws  {
            boolean $z0 = super.onPreparePanel($i0, $r1, $r2);
            if (!$z0 || ToolbarActionBar.this.mToolbarMenuPrepared) {
                return $z0;
            }
            ToolbarActionBar.this.mDecorToolbar.setMenuPrepared();
            ToolbarActionBar.this.mToolbarMenuPrepared = true;
            return $z0;
        }

        public View onCreatePanelView(int $i0) throws  {
            switch ($i0) {
                case 0:
                    Menu $r4 = ToolbarActionBar.this.mDecorToolbar.getMenu();
                    if (onPreparePanel($i0, null, $r4) && onMenuOpened($i0, $r4)) {
                        return ToolbarActionBar.this.getListMenuView($r4);
                    }
                default:
                    break;
            }
            return super.onCreatePanelView($i0);
        }
    }

    public int getNavigationItemCount() throws  {
        return 0;
    }

    public int getNavigationMode() throws  {
        return 0;
    }

    public int getSelectedNavigationIndex() throws  {
        return -1;
    }

    public int getTabCount() throws  {
        return 0;
    }

    void populateOptionsMenu() throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x0038 in list [B:12:0x002d]
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:42)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:60)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r8 = this;
        r0 = 0;
        r1 = r8.getMenu();
        r2 = r1 instanceof android.support.v7.view.menu.MenuBuilder;
        if (r2 == 0) goto L_0x000d;
    L_0x0009:
        r3 = r1;
        r3 = (android.support.v7.view.menu.MenuBuilder) r3;
        r0 = r3;
    L_0x000d:
        if (r0 == 0) goto L_0x0012;
    L_0x000f:
        r0.stopDispatchingItemsChanged();
    L_0x0012:
        r1.clear();	 Catch:{ Throwable -> 0x0031 }
        r4 = r8.mWindowCallback;	 Catch:{ Throwable -> 0x0031 }
        r5 = 0;	 Catch:{ Throwable -> 0x0031 }
        r2 = r4.onCreatePanelMenu(r5, r1);	 Catch:{ Throwable -> 0x0031 }
        if (r2 == 0) goto L_0x0028;	 Catch:{ Throwable -> 0x0031 }
    L_0x001e:
        r4 = r8.mWindowCallback;	 Catch:{ Throwable -> 0x0031 }
        r5 = 0;	 Catch:{ Throwable -> 0x0031 }
        r6 = 0;	 Catch:{ Throwable -> 0x0031 }
        r2 = r4.onPreparePanel(r5, r6, r1);	 Catch:{ Throwable -> 0x0031 }
        if (r2 != 0) goto L_0x002b;	 Catch:{ Throwable -> 0x0031 }
    L_0x0028:
        r1.clear();	 Catch:{ Throwable -> 0x0031 }
    L_0x002b:
        if (r0 == 0) goto L_0x0038;
    L_0x002d:
        r0.startDispatchingItemsChanged();
        return;
    L_0x0031:
        r7 = move-exception;
        if (r0 == 0) goto L_0x0037;
    L_0x0034:
        r0.startDispatchingItemsChanged();
    L_0x0037:
        throw r7;
    L_0x0038:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.app.ToolbarActionBar.populateOptionsMenu():void");
    }

    public void setDisplayOptions(int r1, int r2) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.DecodeException: Load method exception in method: android.support.v7.app.ToolbarActionBar.setDisplayOptions(int, int):void
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
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.app.ToolbarActionBar.setDisplayOptions(int, int):void");
    }

    public ToolbarActionBar(Toolbar $r1, CharSequence $r2, Callback $r3) throws  {
        this.mDecorToolbar = new ToolbarWidgetWrapper($r1, false);
        this.mWindowCallback = new ToolbarCallbackWrapper($r3);
        this.mDecorToolbar.setWindowCallback(this.mWindowCallback);
        $r1.setOnMenuItemClickListener(this.mMenuClicker);
        this.mDecorToolbar.setWindowTitle($r2);
    }

    public Callback getWrappedWindowCallback() throws  {
        return this.mWindowCallback;
    }

    public void setCustomView(View $r1) throws  {
        setCustomView($r1, new LayoutParams(-2, -2));
    }

    public void setCustomView(View $r1, LayoutParams $r2) throws  {
        if ($r1 != null) {
            $r1.setLayoutParams($r2);
        }
        this.mDecorToolbar.setCustomView($r1);
    }

    public void setCustomView(int $i0) throws  {
        setCustomView(LayoutInflater.from(this.mDecorToolbar.getContext()).inflate($i0, this.mDecorToolbar.getViewGroup(), false));
    }

    public void setIcon(int $i0) throws  {
        this.mDecorToolbar.setIcon($i0);
    }

    public void setIcon(Drawable $r1) throws  {
        this.mDecorToolbar.setIcon($r1);
    }

    public void setLogo(int $i0) throws  {
        this.mDecorToolbar.setLogo($i0);
    }

    public void setLogo(Drawable $r1) throws  {
        this.mDecorToolbar.setLogo($r1);
    }

    public void setStackedBackgroundDrawable(Drawable d) throws  {
    }

    public void setSplitBackgroundDrawable(Drawable d) throws  {
    }

    public void setHomeButtonEnabled(boolean enabled) throws  {
    }

    public void setElevation(float $f0) throws  {
        ViewCompat.setElevation(this.mDecorToolbar.getViewGroup(), $f0);
    }

    public float getElevation() throws  {
        return ViewCompat.getElevation(this.mDecorToolbar.getViewGroup());
    }

    public Context getThemedContext() throws  {
        return this.mDecorToolbar.getContext();
    }

    public boolean isTitleTruncated() throws  {
        return super.isTitleTruncated();
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

    public void setDefaultDisplayHomeAsUpEnabled(boolean enabled) throws  {
    }

    public void setHomeActionContentDescription(int $i0) throws  {
        this.mDecorToolbar.setNavigationContentDescription($i0);
    }

    public void setShowHideAnimationEnabled(boolean enabled) throws  {
    }

    public void onConfigurationChanged(Configuration $r1) throws  {
        super.onConfigurationChanged($r1);
    }

    public void setListNavigationCallbacks(SpinnerAdapter $r1, OnNavigationListener $r2) throws  {
        this.mDecorToolbar.setDropdownParams($r1, new NavItemSelectedListener($r2));
    }

    public void setSelectedNavigationItem(int $i0) throws  {
        switch (this.mDecorToolbar.getNavigationMode()) {
            case 1:
                this.mDecorToolbar.setDropdownSelectedPosition($i0);
                return;
            default:
                throw new IllegalStateException("setSelectedNavigationIndex not valid for current navigation mode");
        }
    }

    public void setTitle(CharSequence $r1) throws  {
        this.mDecorToolbar.setTitle($r1);
    }

    public void setTitle(int $i0) throws  {
        this.mDecorToolbar.setTitle($i0 != 0 ? this.mDecorToolbar.getContext().getText($i0) : null);
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

    public void setSubtitle(int $i0) throws  {
        this.mDecorToolbar.setSubtitle($i0 != 0 ? this.mDecorToolbar.getContext().getText($i0) : null);
    }

    public void setDisplayOptions(int $i0) throws  {
        setDisplayOptions($i0, -1);
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

    public void setBackgroundDrawable(@Nullable Drawable $r1) throws  {
        this.mDecorToolbar.setBackgroundDrawable($r1);
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

    public void setNavigationMode(int $i0) throws  {
        if ($i0 == 2) {
            throw new IllegalArgumentException("Tabs not supported in this configuration");
        }
        this.mDecorToolbar.setNavigationMode($i0);
    }

    public int getDisplayOptions() throws  {
        return this.mDecorToolbar.getDisplayOptions();
    }

    public Tab newTab() throws  {
        throw new UnsupportedOperationException("Tabs are not supported in toolbar action bars");
    }

    public void addTab(Tab tab) throws  {
        throw new UnsupportedOperationException("Tabs are not supported in toolbar action bars");
    }

    public void addTab(Tab tab, boolean setSelected) throws  {
        throw new UnsupportedOperationException("Tabs are not supported in toolbar action bars");
    }

    public void addTab(Tab tab, int position) throws  {
        throw new UnsupportedOperationException("Tabs are not supported in toolbar action bars");
    }

    public void addTab(Tab tab, int position, boolean setSelected) throws  {
        throw new UnsupportedOperationException("Tabs are not supported in toolbar action bars");
    }

    public void removeTab(Tab tab) throws  {
        throw new UnsupportedOperationException("Tabs are not supported in toolbar action bars");
    }

    public void removeTabAt(int position) throws  {
        throw new UnsupportedOperationException("Tabs are not supported in toolbar action bars");
    }

    public void removeAllTabs() throws  {
        throw new UnsupportedOperationException("Tabs are not supported in toolbar action bars");
    }

    public void selectTab(Tab tab) throws  {
        throw new UnsupportedOperationException("Tabs are not supported in toolbar action bars");
    }

    public Tab getSelectedTab() throws  {
        throw new UnsupportedOperationException("Tabs are not supported in toolbar action bars");
    }

    public Tab getTabAt(int index) throws  {
        throw new UnsupportedOperationException("Tabs are not supported in toolbar action bars");
    }

    public int getHeight() throws  {
        return this.mDecorToolbar.getHeight();
    }

    public void show() throws  {
        this.mDecorToolbar.setVisibility(0);
    }

    public void hide() throws  {
        this.mDecorToolbar.setVisibility(8);
    }

    public boolean isShowing() throws  {
        return this.mDecorToolbar.getVisibility() == 0;
    }

    public boolean openOptionsMenu() throws  {
        return this.mDecorToolbar.showOverflowMenu();
    }

    public boolean invalidateOptionsMenu() throws  {
        this.mDecorToolbar.getViewGroup().removeCallbacks(this.mMenuInvalidator);
        ViewCompat.postOnAnimation(this.mDecorToolbar.getViewGroup(), this.mMenuInvalidator);
        return true;
    }

    public boolean collapseActionView() throws  {
        if (!this.mDecorToolbar.hasExpandedActionView()) {
            return false;
        }
        this.mDecorToolbar.collapseActionView();
        return true;
    }

    public boolean onMenuKeyEvent(KeyEvent $r1) throws  {
        if ($r1.getAction() != 1) {
            return true;
        }
        openOptionsMenu();
        return true;
    }

    public boolean onKeyShortcut(int $i0, KeyEvent $r1) throws  {
        Menu $r2 = getMenu();
        if ($r2 == null) {
            return true;
        }
        boolean $z0;
        if (KeyCharacterMap.load($r1 != null ? $r1.getDeviceId() : -1).getKeyboardType() != 1) {
            $z0 = true;
        } else {
            $z0 = false;
        }
        $r2.setQwertyMode($z0);
        $r2.performShortcut($i0, $r1, 0);
        return true;
    }

    void onDestroy() throws  {
        this.mDecorToolbar.getViewGroup().removeCallbacks(this.mMenuInvalidator);
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

    private View getListMenuView(Menu $r1) throws  {
        ensureListMenuPresenter($r1);
        if ($r1 == null) {
            return null;
        }
        if (this.mListMenuPresenter == null) {
            return null;
        }
        return this.mListMenuPresenter.getAdapter().getCount() > 0 ? (View) this.mListMenuPresenter.getMenuView(this.mDecorToolbar.getViewGroup()) : null;
    }

    private void ensureListMenuPresenter(Menu $r1) throws  {
        ListMenuPresenter $r4 = this.mListMenuPresenter;
        this = this;
        if ($r4 == null && ($r1 instanceof MenuBuilder)) {
            MenuBuilder $r5 = (MenuBuilder) $r1;
            Context $r7 = this.mDecorToolbar.getContext();
            TypedValue $r3 = new TypedValue();
            Theme $r9 = $r7.getResources().newTheme();
            $r9.setTo($r7.getTheme());
            $r9.resolveAttribute(C0192R.attr.actionBarPopupTheme, $r3, true);
            if ($r3.resourceId != 0) {
                $r9.applyStyle($r3.resourceId, true);
            }
            $r9.resolveAttribute(C0192R.attr.panelMenuListTheme, $r3, true);
            if ($r3.resourceId != 0) {
                $r9.applyStyle($r3.resourceId, true);
            } else {
                $r9.applyStyle(C0192R.style.Theme_AppCompat_CompactMenu, true);
            }
            Context $r2 = new ContextThemeWrapper($r7, 0);
            $r2.getTheme().setTo($r9);
            this.mListMenuPresenter = new ListMenuPresenter($r2, C0192R.layout.abc_list_menu_item_layout);
            ToolbarActionBar $r42 = this;
            this.mListMenuPresenter.setCallback(new PanelMenuPresenterCallback());
            $r5.addMenuPresenter(this.mListMenuPresenter);
        }
    }

    private Menu getMenu() throws  {
        if (!this.mMenuCallbackSet) {
            this.mDecorToolbar.setMenuCallbacks(new ActionMenuPresenterCallback(), new MenuBuilderCallback());
            this.mMenuCallbackSet = true;
        }
        return this.mDecorToolbar.getMenu();
    }
}
