package android.support.v7.app;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.appcompat.C0192R;
import android.support.v7.view.ActionMode;
import android.support.v7.view.ActionMode.Callback;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.SpinnerAdapter;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public abstract class ActionBar {
    public static final int DISPLAY_HOME_AS_UP = 4;
    public static final int DISPLAY_SHOW_CUSTOM = 16;
    public static final int DISPLAY_SHOW_HOME = 2;
    public static final int DISPLAY_SHOW_TITLE = 8;
    public static final int DISPLAY_USE_LOGO = 1;
    public static final int NAVIGATION_MODE_LIST = 1;
    public static final int NAVIGATION_MODE_STANDARD = 0;
    public static final int NAVIGATION_MODE_TABS = 2;

    @Retention(RetentionPolicy.SOURCE)
    public @interface DisplayOptions {
    }

    public static class LayoutParams extends MarginLayoutParams {
        public int gravity;

        public LayoutParams(@NonNull Context $r1, AttributeSet $r2) throws  {
            super($r1, $r2);
            this.gravity = 0;
            TypedArray $r4 = $r1.obtainStyledAttributes($r2, C0192R.styleable.ActionBarLayout);
            this.gravity = $r4.getInt(C0192R.styleable.ActionBarLayout_android_layout_gravity, 0);
            $r4.recycle();
        }

        public LayoutParams(int $i0, int $i1) throws  {
            super($i0, $i1);
            this.gravity = 0;
            this.gravity = 8388627;
        }

        public LayoutParams(int $i0, int $i1, int $i2) throws  {
            super($i0, $i1);
            this.gravity = 0;
            this.gravity = $i2;
        }

        public LayoutParams(int $i0) throws  {
            this(-2, -1, $i0);
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
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface NavigationMode {
    }

    public interface OnMenuVisibilityListener {
        void onMenuVisibilityChanged(boolean z) throws ;
    }

    public interface OnNavigationListener {
        boolean onNavigationItemSelected(int i, long j) throws ;
    }

    public static abstract class Tab {
        public static final int INVALID_POSITION = -1;

        public abstract CharSequence getContentDescription() throws ;

        public abstract View getCustomView() throws ;

        public abstract Drawable getIcon() throws ;

        public abstract int getPosition() throws ;

        public abstract Object getTag() throws ;

        public abstract CharSequence getText() throws ;

        public abstract void select() throws ;

        public abstract Tab setContentDescription(int i) throws ;

        public abstract Tab setContentDescription(CharSequence charSequence) throws ;

        public abstract Tab setCustomView(int i) throws ;

        public abstract Tab setCustomView(View view) throws ;

        public abstract Tab setIcon(@DrawableRes int i) throws ;

        public abstract Tab setIcon(Drawable drawable) throws ;

        public abstract Tab setTabListener(TabListener tabListener) throws ;

        public abstract Tab setTag(Object obj) throws ;

        public abstract Tab setText(int i) throws ;

        public abstract Tab setText(CharSequence charSequence) throws ;
    }

    public interface TabListener {
        void onTabReselected(Tab tab, FragmentTransaction fragmentTransaction) throws ;

        void onTabSelected(Tab tab, FragmentTransaction fragmentTransaction) throws ;

        void onTabUnselected(Tab tab, FragmentTransaction fragmentTransaction) throws ;
    }

    public abstract void addOnMenuVisibilityListener(OnMenuVisibilityListener onMenuVisibilityListener) throws ;

    public abstract void addTab(Tab tab) throws ;

    public abstract void addTab(Tab tab, int i) throws ;

    public abstract void addTab(Tab tab, int i, boolean z) throws ;

    public abstract void addTab(Tab tab, boolean z) throws ;

    public boolean collapseActionView() throws  {
        return false;
    }

    public abstract View getCustomView() throws ;

    public abstract int getDisplayOptions() throws ;

    public float getElevation() throws  {
        return 0.0f;
    }

    public abstract int getHeight() throws ;

    public int getHideOffset() throws  {
        return 0;
    }

    public abstract int getNavigationItemCount() throws ;

    public abstract int getNavigationMode() throws ;

    public abstract int getSelectedNavigationIndex() throws ;

    @Nullable
    public abstract Tab getSelectedTab() throws ;

    @Nullable
    public abstract CharSequence getSubtitle() throws ;

    public abstract Tab getTabAt(int i) throws ;

    public abstract int getTabCount() throws ;

    public Context getThemedContext() throws  {
        return null;
    }

    @Nullable
    public abstract CharSequence getTitle() throws ;

    public abstract void hide() throws ;

    public boolean invalidateOptionsMenu() throws  {
        return false;
    }

    public boolean isHideOnContentScrollEnabled() throws  {
        return false;
    }

    public abstract boolean isShowing() throws ;

    public boolean isTitleTruncated() throws  {
        return false;
    }

    public abstract Tab newTab() throws ;

    public boolean onKeyShortcut(int keyCode, KeyEvent ev) throws  {
        return false;
    }

    public boolean onMenuKeyEvent(KeyEvent event) throws  {
        return false;
    }

    public boolean openOptionsMenu() throws  {
        return false;
    }

    public abstract void removeAllTabs() throws ;

    public abstract void removeOnMenuVisibilityListener(OnMenuVisibilityListener onMenuVisibilityListener) throws ;

    public abstract void removeTab(Tab tab) throws ;

    public abstract void removeTabAt(int i) throws ;

    boolean requestFocus() throws  {
        return false;
    }

    public abstract void selectTab(Tab tab) throws ;

    public abstract void setBackgroundDrawable(@Nullable Drawable drawable) throws ;

    public abstract void setCustomView(int i) throws ;

    public abstract void setCustomView(View view) throws ;

    public abstract void setCustomView(View view, LayoutParams layoutParams) throws ;

    public abstract void setDisplayHomeAsUpEnabled(boolean z) throws ;

    public abstract void setDisplayOptions(int i) throws ;

    public abstract void setDisplayOptions(int i, int i2) throws ;

    public abstract void setDisplayShowCustomEnabled(boolean z) throws ;

    public abstract void setDisplayShowHomeEnabled(boolean z) throws ;

    public abstract void setDisplayShowTitleEnabled(boolean z) throws ;

    public abstract void setDisplayUseLogoEnabled(boolean z) throws ;

    public abstract void setIcon(@DrawableRes int i) throws ;

    public abstract void setIcon(Drawable drawable) throws ;

    public abstract void setListNavigationCallbacks(SpinnerAdapter spinnerAdapter, OnNavigationListener onNavigationListener) throws ;

    public abstract void setLogo(@DrawableRes int i) throws ;

    public abstract void setLogo(Drawable drawable) throws ;

    public abstract void setNavigationMode(int i) throws ;

    public abstract void setSelectedNavigationItem(int i) throws ;

    public abstract void setSubtitle(int i) throws ;

    public abstract void setSubtitle(CharSequence charSequence) throws ;

    public abstract void setTitle(@StringRes int i) throws ;

    public abstract void setTitle(CharSequence charSequence) throws ;

    public abstract void show() throws ;

    public ActionMode startActionMode(Callback callback) throws  {
        return null;
    }

    public void setStackedBackgroundDrawable(Drawable d) throws  {
    }

    public void setSplitBackgroundDrawable(Drawable d) throws  {
    }

    public void setHomeButtonEnabled(boolean enabled) throws  {
    }

    public void setHomeAsUpIndicator(@Nullable Drawable indicator) throws  {
    }

    public void setHomeAsUpIndicator(@DrawableRes int resId) throws  {
    }

    public void setHomeActionContentDescription(@Nullable CharSequence description) throws  {
    }

    public void setHomeActionContentDescription(@StringRes int resId) throws  {
    }

    public void setHideOnContentScrollEnabled(boolean $z0) throws  {
        if ($z0) {
            throw new UnsupportedOperationException("Hide on content scroll is not supported in this action bar configuration.");
        }
    }

    public void setHideOffset(int $i0) throws  {
        if ($i0 != 0) {
            throw new UnsupportedOperationException("Setting an explicit action bar hide offset is not supported in this action bar configuration.");
        }
    }

    public void setElevation(float $f0) throws  {
        if ($f0 != 0.0f) {
            throw new UnsupportedOperationException("Setting a non-zero elevation is not supported in this action bar configuration.");
        }
    }

    public void setDefaultDisplayHomeAsUpEnabled(boolean enabled) throws  {
    }

    public void setShowHideAnimationEnabled(boolean enabled) throws  {
    }

    public void onConfigurationChanged(Configuration config) throws  {
    }

    public void dispatchMenuVisibilityChanged(boolean visible) throws  {
    }

    public void setWindowTitle(CharSequence title) throws  {
    }

    void onDestroy() throws  {
    }
}
