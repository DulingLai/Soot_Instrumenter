package android.support.v7.app;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import dalvik.annotation.Signature;

public class ActionBarDrawerToggle implements DrawerListener {
    private final Delegate mActivityImpl;
    private final int mCloseDrawerContentDescRes;
    private boolean mDrawerIndicatorEnabled;
    private final DrawerLayout mDrawerLayout;
    private boolean mHasCustomUpIndicator;
    private Drawable mHomeAsUpIndicator;
    private final int mOpenDrawerContentDescRes;
    private DrawerToggle mSlider;
    private OnClickListener mToolbarNavigationClickListener;
    private boolean mWarnedForDisplayHomeAsUp;

    public interface DelegateProvider {
        @Nullable
        Delegate getDrawerToggleDelegate() throws ;
    }

    class C01651 implements OnClickListener {
        C01651() throws  {
        }

        public void onClick(View $r1) throws  {
            if (ActionBarDrawerToggle.this.mDrawerIndicatorEnabled) {
                ActionBarDrawerToggle.this.toggle();
            } else if (ActionBarDrawerToggle.this.mToolbarNavigationClickListener != null) {
                ActionBarDrawerToggle.this.mToolbarNavigationClickListener.onClick($r1);
            }
        }
    }

    public interface Delegate {
        Context getActionBarThemedContext() throws ;

        Drawable getThemeUpIndicator() throws ;

        boolean isNavigationVisible() throws ;

        void setActionBarDescription(@StringRes int i) throws ;

        void setActionBarUpIndicator(Drawable drawable, @StringRes int i) throws ;
    }

    interface DrawerToggle {
        float getPosition() throws ;

        void setPosition(float f) throws ;
    }

    static class DrawerArrowDrawableToggle extends DrawerArrowDrawable implements DrawerToggle {
        private final Activity mActivity;

        public DrawerArrowDrawableToggle(Activity $r1, Context $r2) throws  {
            super($r2);
            this.mActivity = $r1;
        }

        public void setPosition(float $f0) throws  {
            if ($f0 == 1.0f) {
                setVerticalMirror(true);
            } else if ($f0 == 0.0f) {
                setVerticalMirror(false);
            }
            setProgress($f0);
        }

        public float getPosition() throws  {
            return getProgress();
        }
    }

    static class DummyDelegate implements Delegate {
        final Activity mActivity;

        public Drawable getThemeUpIndicator() throws  {
            return null;
        }

        public boolean isNavigationVisible() throws  {
            return true;
        }

        DummyDelegate(Activity $r1) throws  {
            this.mActivity = $r1;
        }

        public void setActionBarUpIndicator(Drawable upDrawable, @StringRes int contentDescRes) throws  {
        }

        public void setActionBarDescription(@StringRes int contentDescRes) throws  {
        }

        public Context getActionBarThemedContext() throws  {
            return this.mActivity;
        }
    }

    private static class HoneycombDelegate implements Delegate {
        final Activity mActivity;
        SetIndicatorInfo mSetIndicatorInfo;

        private HoneycombDelegate(Activity $r1) throws  {
            this.mActivity = $r1;
        }

        public Drawable getThemeUpIndicator() throws  {
            return ActionBarDrawerToggleHoneycomb.getThemeUpIndicator(this.mActivity);
        }

        public Context getActionBarThemedContext() throws  {
            ActionBar $r2 = this.mActivity.getActionBar();
            if ($r2 != null) {
                return $r2.getThemedContext();
            }
            return this.mActivity;
        }

        public boolean isNavigationVisible() throws  {
            ActionBar $r2 = this.mActivity.getActionBar();
            return ($r2 == null || ($r2.getDisplayOptions() & 4) == 0) ? false : true;
        }

        public void setActionBarUpIndicator(Drawable $r1, int $i0) throws  {
            this.mActivity.getActionBar().setDisplayShowHomeEnabled(true);
            this.mSetIndicatorInfo = ActionBarDrawerToggleHoneycomb.setActionBarUpIndicator(this.mSetIndicatorInfo, this.mActivity, $r1, $i0);
            this.mActivity.getActionBar().setDisplayShowHomeEnabled(false);
        }

        public void setActionBarDescription(int $i0) throws  {
            this.mSetIndicatorInfo = ActionBarDrawerToggleHoneycomb.setActionBarDescription(this.mSetIndicatorInfo, this.mActivity, $i0);
        }
    }

    private static class JellybeanMr2Delegate implements Delegate {
        final Activity mActivity;

        private JellybeanMr2Delegate(Activity $r1) throws  {
            this.mActivity = $r1;
        }

        public Drawable getThemeUpIndicator() throws  {
            TypedArray $r3 = getActionBarThemedContext().obtainStyledAttributes(null, new int[]{16843531}, 16843470, 0);
            Drawable $r4 = $r3.getDrawable(0);
            $r3.recycle();
            return $r4;
        }

        public Context getActionBarThemedContext() throws  {
            ActionBar $r2 = this.mActivity.getActionBar();
            if ($r2 != null) {
                return $r2.getThemedContext();
            }
            return this.mActivity;
        }

        public boolean isNavigationVisible() throws  {
            ActionBar $r2 = this.mActivity.getActionBar();
            return ($r2 == null || ($r2.getDisplayOptions() & 4) == 0) ? false : true;
        }

        public void setActionBarUpIndicator(Drawable $r1, int $i0) throws  {
            ActionBar $r3 = this.mActivity.getActionBar();
            if ($r3 != null) {
                $r3.setHomeAsUpIndicator($r1);
                $r3.setHomeActionContentDescription($i0);
            }
        }

        public void setActionBarDescription(int $i0) throws  {
            ActionBar $r2 = this.mActivity.getActionBar();
            if ($r2 != null) {
                $r2.setHomeActionContentDescription($i0);
            }
        }
    }

    static class ToolbarCompatDelegate implements Delegate {
        final CharSequence mDefaultContentDescription;
        final Drawable mDefaultUpIndicator;
        final Toolbar mToolbar;

        public boolean isNavigationVisible() throws  {
            return true;
        }

        ToolbarCompatDelegate(Toolbar $r1) throws  {
            this.mToolbar = $r1;
            this.mDefaultUpIndicator = $r1.getNavigationIcon();
            this.mDefaultContentDescription = $r1.getNavigationContentDescription();
        }

        public void setActionBarUpIndicator(Drawable $r1, @StringRes int $i0) throws  {
            this.mToolbar.setNavigationIcon($r1);
            setActionBarDescription($i0);
        }

        public void setActionBarDescription(@StringRes int $i0) throws  {
            if ($i0 == 0) {
                this.mToolbar.setNavigationContentDescription(this.mDefaultContentDescription);
            } else {
                this.mToolbar.setNavigationContentDescription($i0);
            }
        }

        public Drawable getThemeUpIndicator() throws  {
            return this.mDefaultUpIndicator;
        }

        public Context getActionBarThemedContext() throws  {
            return this.mToolbar.getContext();
        }
    }

    public ActionBarDrawerToggle(Activity $r1, DrawerLayout $r2, @StringRes int $i0, @StringRes int $i1) throws  {
        this($r1, null, $r2, null, $i0, $i1);
    }

    public ActionBarDrawerToggle(Activity $r1, DrawerLayout $r2, Toolbar $r3, @StringRes int $i0, @StringRes int $i1) throws  {
        this($r1, $r3, $r2, null, $i0, $i1);
    }

    <T extends Drawable & DrawerToggle> ActionBarDrawerToggle(@Signature({"<T:", "Landroid/graphics/drawable/Drawable;", ":", "Landroid/support/v7/app/ActionBarDrawerToggle$DrawerToggle;", ">(", "Landroid/app/Activity;", "Landroid/support/v7/widget/Toolbar;", "Landroid/support/v4/widget/DrawerLayout;", "TT;II)V"}) Activity $r1, @Signature({"<T:", "Landroid/graphics/drawable/Drawable;", ":", "Landroid/support/v7/app/ActionBarDrawerToggle$DrawerToggle;", ">(", "Landroid/app/Activity;", "Landroid/support/v7/widget/Toolbar;", "Landroid/support/v4/widget/DrawerLayout;", "TT;II)V"}) Toolbar $r2, @Signature({"<T:", "Landroid/graphics/drawable/Drawable;", ":", "Landroid/support/v7/app/ActionBarDrawerToggle$DrawerToggle;", ">(", "Landroid/app/Activity;", "Landroid/support/v7/widget/Toolbar;", "Landroid/support/v4/widget/DrawerLayout;", "TT;II)V"}) DrawerLayout $r3, @Signature({"<T:", "Landroid/graphics/drawable/Drawable;", ":", "Landroid/support/v7/app/ActionBarDrawerToggle$DrawerToggle;", ">(", "Landroid/app/Activity;", "Landroid/support/v7/widget/Toolbar;", "Landroid/support/v4/widget/DrawerLayout;", "TT;II)V"}) T $r4, @StringRes @Signature({"<T:", "Landroid/graphics/drawable/Drawable;", ":", "Landroid/support/v7/app/ActionBarDrawerToggle$DrawerToggle;", ">(", "Landroid/app/Activity;", "Landroid/support/v7/widget/Toolbar;", "Landroid/support/v4/widget/DrawerLayout;", "TT;II)V"}) int $i0, @StringRes @Signature({"<T:", "Landroid/graphics/drawable/Drawable;", ":", "Landroid/support/v7/app/ActionBarDrawerToggle$DrawerToggle;", ">(", "Landroid/app/Activity;", "Landroid/support/v7/widget/Toolbar;", "Landroid/support/v4/widget/DrawerLayout;", "TT;II)V"}) int $i1) throws  {
        ActionBarDrawerToggle actionBarDrawerToggle = this;
        this.mDrawerIndicatorEnabled = true;
        this.mWarnedForDisplayHomeAsUp = false;
        if ($r2 != null) {
            this.mActivityImpl = new ToolbarCompatDelegate($r2);
            $r2.setNavigationOnClickListener(new C01651());
        } else if ($r1 instanceof DelegateProvider) {
            this.mActivityImpl = ((DelegateProvider) $r1).getDrawerToggleDelegate();
        } else if (VERSION.SDK_INT >= 18) {
            this.mActivityImpl = new JellybeanMr2Delegate($r1);
        } else if (VERSION.SDK_INT >= 11) {
            this.mActivityImpl = new HoneycombDelegate($r1);
        } else {
            this.mActivityImpl = new DummyDelegate($r1);
        }
        this.mDrawerLayout = $r3;
        this.mOpenDrawerContentDescRes = $i0;
        this.mCloseDrawerContentDescRes = $i1;
        if ($r4 == null) {
            this.mSlider = new DrawerArrowDrawableToggle($r1, this.mActivityImpl.getActionBarThemedContext());
        } else {
            this.mSlider = (DrawerToggle) $r4;
        }
        this.mHomeAsUpIndicator = getThemeUpIndicator();
    }

    public void syncState() throws  {
        if (this.mDrawerLayout.isDrawerOpen((int) GravityCompat.START)) {
            this.mSlider.setPosition(1.0f);
        } else {
            this.mSlider.setPosition(0.0f);
        }
        if (this.mDrawerIndicatorEnabled) {
            setActionBarUpIndicator((Drawable) this.mSlider, this.mDrawerLayout.isDrawerOpen((int) GravityCompat.START) ? this.mCloseDrawerContentDescRes : this.mOpenDrawerContentDescRes);
        }
    }

    public void onConfigurationChanged(Configuration newConfig) throws  {
        if (!this.mHasCustomUpIndicator) {
            this.mHomeAsUpIndicator = getThemeUpIndicator();
        }
        syncState();
    }

    public boolean onOptionsItemSelected(MenuItem $r1) throws  {
        if ($r1 == null || $r1.getItemId() != 16908332 || !this.mDrawerIndicatorEnabled) {
            return false;
        }
        toggle();
        return true;
    }

    private void toggle() throws  {
        int $i0 = this.mDrawerLayout.getDrawerLockMode((int) GravityCompat.START);
        if (this.mDrawerLayout.isDrawerVisible((int) GravityCompat.START) && $i0 != 2) {
            this.mDrawerLayout.closeDrawer((int) GravityCompat.START);
        } else if ($i0 != 1) {
            this.mDrawerLayout.openDrawer((int) GravityCompat.START);
        }
    }

    public void setHomeAsUpIndicator(Drawable $r1) throws  {
        if ($r1 == null) {
            this.mHomeAsUpIndicator = getThemeUpIndicator();
            this.mHasCustomUpIndicator = false;
        } else {
            this.mHomeAsUpIndicator = $r1;
            this.mHasCustomUpIndicator = true;
        }
        if (!this.mDrawerIndicatorEnabled) {
            setActionBarUpIndicator(this.mHomeAsUpIndicator, 0);
        }
    }

    public void setHomeAsUpIndicator(int $i0) throws  {
        Drawable $r1 = null;
        if ($i0 != 0) {
            $r1 = this.mDrawerLayout.getResources().getDrawable($i0);
        }
        setHomeAsUpIndicator($r1);
    }

    public boolean isDrawerIndicatorEnabled() throws  {
        return this.mDrawerIndicatorEnabled;
    }

    public void setDrawerIndicatorEnabled(boolean $z0) throws  {
        if ($z0 != this.mDrawerIndicatorEnabled) {
            if ($z0) {
                setActionBarUpIndicator((Drawable) this.mSlider, this.mDrawerLayout.isDrawerOpen((int) GravityCompat.START) ? this.mCloseDrawerContentDescRes : this.mOpenDrawerContentDescRes);
            } else {
                setActionBarUpIndicator(this.mHomeAsUpIndicator, 0);
            }
            this.mDrawerIndicatorEnabled = $z0;
        }
    }

    public void onDrawerSlide(View drawerView, float $f0) throws  {
        this.mSlider.setPosition(Math.min(1.0f, Math.max(0.0f, $f0)));
    }

    public void onDrawerOpened(View drawerView) throws  {
        this.mSlider.setPosition(1.0f);
        if (this.mDrawerIndicatorEnabled) {
            setActionBarDescription(this.mCloseDrawerContentDescRes);
        }
    }

    public void onDrawerClosed(View drawerView) throws  {
        this.mSlider.setPosition(0.0f);
        if (this.mDrawerIndicatorEnabled) {
            setActionBarDescription(this.mOpenDrawerContentDescRes);
        }
    }

    public void onDrawerStateChanged(int newState) throws  {
    }

    public OnClickListener getToolbarNavigationClickListener() throws  {
        return this.mToolbarNavigationClickListener;
    }

    public void setToolbarNavigationClickListener(OnClickListener $r1) throws  {
        this.mToolbarNavigationClickListener = $r1;
    }

    void setActionBarUpIndicator(Drawable $r1, int $i0) throws  {
        if (!(this.mWarnedForDisplayHomeAsUp || this.mActivityImpl.isNavigationVisible())) {
            Log.w("ActionBarDrawerToggle", "DrawerToggle may not show up because NavigationIcon is not visible. You may need to call actionbar.setDisplayHomeAsUpEnabled(true);");
            this.mWarnedForDisplayHomeAsUp = true;
        }
        this.mActivityImpl.setActionBarUpIndicator($r1, $i0);
    }

    void setActionBarDescription(int $i0) throws  {
        this.mActivityImpl.setActionBarDescription($i0);
    }

    Drawable getThemeUpIndicator() throws  {
        return this.mActivityImpl.getThemeUpIndicator();
    }
}
