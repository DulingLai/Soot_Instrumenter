package android.support.v7.app;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle.Delegate;
import android.support.v7.appcompat.C0192R;
import android.support.v7.view.ActionMode;
import android.support.v7.view.SupportMenuInflater;
import android.support.v7.view.WindowCallbackWrapper;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.TintTypedArray;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.Window;
import android.view.Window.Callback;

abstract class AppCompatDelegateImplBase extends AppCompatDelegate {
    ActionBar mActionBar;
    final AppCompatCallback mAppCompatCallback;
    final Callback mAppCompatWindowCallback;
    final Context mContext;
    boolean mHasActionBar;
    private boolean mIsDestroyed;
    boolean mIsFloating;
    MenuInflater mMenuInflater;
    final Callback mOriginalWindowCallback = this.mWindow.getCallback();
    boolean mOverlayActionBar;
    boolean mOverlayActionMode;
    boolean mThemeRead;
    private CharSequence mTitle;
    final Window mWindow;
    boolean mWindowNoTitle;

    private class ActionBarDrawableToggleImpl implements Delegate {
        private ActionBarDrawableToggleImpl() throws  {
        }

        public Drawable getThemeUpIndicator() throws  {
            TintTypedArray $r3 = TintTypedArray.obtainStyledAttributes(getActionBarThemedContext(), null, new int[]{C0192R.attr.homeAsUpIndicator});
            Drawable $r4 = $r3.getDrawable(0);
            $r3.recycle();
            return $r4;
        }

        public Context getActionBarThemedContext() throws  {
            return AppCompatDelegateImplBase.this.getActionBarThemedContext();
        }

        public boolean isNavigationVisible() throws  {
            ActionBar $r2 = AppCompatDelegateImplBase.this.getSupportActionBar();
            return ($r2 == null || ($r2.getDisplayOptions() & 4) == 0) ? false : true;
        }

        public void setActionBarUpIndicator(Drawable $r1, int $i0) throws  {
            ActionBar $r3 = AppCompatDelegateImplBase.this.getSupportActionBar();
            if ($r3 != null) {
                $r3.setHomeAsUpIndicator($r1);
                $r3.setHomeActionContentDescription($i0);
            }
        }

        public void setActionBarDescription(int $i0) throws  {
            ActionBar $r2 = AppCompatDelegateImplBase.this.getSupportActionBar();
            if ($r2 != null) {
                $r2.setHomeActionContentDescription($i0);
            }
        }
    }

    class AppCompatWindowCallbackBase extends WindowCallbackWrapper {
        AppCompatWindowCallbackBase(Callback $r2) throws  {
            super($r2);
        }

        public boolean dispatchKeyEvent(KeyEvent $r1) throws  {
            return AppCompatDelegateImplBase.this.dispatchKeyEvent($r1) || super.dispatchKeyEvent($r1);
        }

        public boolean dispatchKeyShortcutEvent(KeyEvent $r1) throws  {
            return super.dispatchKeyShortcutEvent($r1) || AppCompatDelegateImplBase.this.onKeyShortcut($r1.getKeyCode(), $r1);
        }

        public boolean onCreatePanelMenu(int $i0, Menu $r1) throws  {
            return ($i0 != 0 || ($r1 instanceof MenuBuilder)) ? super.onCreatePanelMenu($i0, $r1) : false;
        }

        public void onContentChanged() throws  {
        }

        public boolean onPreparePanel(int $i0, View $r1, Menu $r2) throws  {
            MenuBuilder $r3 = $r2 instanceof MenuBuilder ? (MenuBuilder) $r2 : null;
            if ($i0 == 0 && $r3 == null) {
                return false;
            }
            if ($r3 != null) {
                $r3.setOverrideVisibleItems(true);
            }
            boolean $z0 = super.onPreparePanel($i0, $r1, $r2);
            if ($r3 == null) {
                return $z0;
            }
            $r3.setOverrideVisibleItems(false);
            return $z0;
        }

        public boolean onMenuOpened(int $i0, Menu $r1) throws  {
            super.onMenuOpened($i0, $r1);
            AppCompatDelegateImplBase.this.onMenuOpened($i0, $r1);
            return true;
        }

        public void onPanelClosed(int $i0, Menu $r1) throws  {
            super.onPanelClosed($i0, $r1);
            AppCompatDelegateImplBase.this.onPanelClosed($i0, $r1);
        }
    }

    public boolean applyDayNight() throws  {
        return false;
    }

    abstract boolean dispatchKeyEvent(KeyEvent keyEvent) throws ;

    abstract void initWindowDecorActionBar() throws ;

    public boolean isHandleNativeActionModesEnabled() throws  {
        return false;
    }

    abstract boolean onKeyShortcut(int i, KeyEvent keyEvent) throws ;

    abstract boolean onMenuOpened(int i, Menu menu) throws ;

    abstract void onPanelClosed(int i, Menu menu) throws ;

    abstract void onTitleChanged(CharSequence charSequence) throws ;

    abstract ActionMode startSupportActionModeFromWindow(ActionMode.Callback callback) throws ;

    AppCompatDelegateImplBase(Context $r1, Window $r2, AppCompatCallback $r3) throws  {
        this.mContext = $r1;
        this.mWindow = $r2;
        this.mAppCompatCallback = $r3;
        if (this.mOriginalWindowCallback instanceof AppCompatWindowCallbackBase) {
            throw new IllegalStateException("AppCompat has already installed itself into the Window");
        }
        this.mAppCompatWindowCallback = wrapWindowCallback(this.mOriginalWindowCallback);
        this.mWindow.setCallback(this.mAppCompatWindowCallback);
    }

    Callback wrapWindowCallback(Callback $r1) throws  {
        return new AppCompatWindowCallbackBase($r1);
    }

    public ActionBar getSupportActionBar() throws  {
        initWindowDecorActionBar();
        return this.mActionBar;
    }

    final ActionBar peekSupportActionBar() throws  {
        return this.mActionBar;
    }

    public MenuInflater getMenuInflater() throws  {
        if (this.mMenuInflater == null) {
            initWindowDecorActionBar();
            this.mMenuInflater = new SupportMenuInflater(this.mActionBar != null ? this.mActionBar.getThemedContext() : this.mContext);
        }
        return this.mMenuInflater;
    }

    public void setLocalNightMode(int mode) throws  {
    }

    public final Delegate getDrawerToggleDelegate() throws  {
        return new ActionBarDrawableToggleImpl();
    }

    final Context getActionBarThemedContext() throws  {
        Context $r1 = null;
        ActionBar $r2 = getSupportActionBar();
        if ($r2 != null) {
            $r1 = $r2.getThemedContext();
        }
        if ($r1 == null) {
            return this.mContext;
        }
        return $r1;
    }

    public void onDestroy() throws  {
        this.mIsDestroyed = true;
    }

    public void setHandleNativeActionModesEnabled(boolean enabled) throws  {
    }

    final boolean isDestroyed() throws  {
        return this.mIsDestroyed;
    }

    final Callback getWindowCallback() throws  {
        return this.mWindow.getCallback();
    }

    public final void setTitle(CharSequence $r1) throws  {
        this.mTitle = $r1;
        onTitleChanged($r1);
    }

    public void onSaveInstanceState(Bundle outState) throws  {
    }

    final CharSequence getTitle() throws  {
        if (this.mOriginalWindowCallback instanceof Activity) {
            return ((Activity) this.mOriginalWindowCallback).getTitle();
        }
        return this.mTitle;
    }
}
