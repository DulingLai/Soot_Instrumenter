package android.support.v4.app;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.Callback;
import android.graphics.drawable.InsetDrawable;
import android.os.Build.VERSION;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.view.MenuItem;
import android.view.View;
import com.waze.LayoutManager;
import com.waze.map.CanvasFont;

@Deprecated
public class ActionBarDrawerToggle implements DrawerListener {
    private static final int ID_HOME = 16908332;
    private static final ActionBarDrawerToggleImpl IMPL;
    private static final float TOGGLE_DRAWABLE_OFFSET = 0.33333334f;
    private final Activity mActivity;
    private final Delegate mActivityImpl;
    private final int mCloseDrawerContentDescRes;
    private Drawable mDrawerImage;
    private final int mDrawerImageResource;
    private boolean mDrawerIndicatorEnabled;
    private final DrawerLayout mDrawerLayout;
    private boolean mHasCustomUpIndicator;
    private Drawable mHomeAsUpIndicator;
    private final int mOpenDrawerContentDescRes;
    private Object mSetIndicatorInfo;
    private SlideDrawable mSlider;

    private interface ActionBarDrawerToggleImpl {
        Drawable getThemeUpIndicator(Activity activity) throws ;

        Object setActionBarDescription(Object obj, Activity activity, int i) throws ;

        Object setActionBarUpIndicator(Object obj, Activity activity, Drawable drawable, int i) throws ;
    }

    private static class ActionBarDrawerToggleImplBase implements ActionBarDrawerToggleImpl {
        public Drawable getThemeUpIndicator(Activity activity) throws  {
            return null;
        }

        private ActionBarDrawerToggleImplBase() throws  {
        }

        public Object setActionBarUpIndicator(Object $r1, Activity activity, Drawable themeImage, int contentDescRes) throws  {
            return $r1;
        }

        public Object setActionBarDescription(Object $r1, Activity activity, int contentDescRes) throws  {
            return $r1;
        }
    }

    private static class ActionBarDrawerToggleImplHC implements ActionBarDrawerToggleImpl {
        private ActionBarDrawerToggleImplHC() throws  {
        }

        public Drawable getThemeUpIndicator(Activity $r1) throws  {
            return ActionBarDrawerToggleHoneycomb.getThemeUpIndicator($r1);
        }

        public Object setActionBarUpIndicator(Object $r1, Activity $r2, Drawable $r3, int $i0) throws  {
            return ActionBarDrawerToggleHoneycomb.setActionBarUpIndicator($r1, $r2, $r3, $i0);
        }

        public Object setActionBarDescription(Object $r1, Activity $r2, int $i0) throws  {
            return ActionBarDrawerToggleHoneycomb.setActionBarDescription($r1, $r2, $i0);
        }
    }

    private static class ActionBarDrawerToggleImplJellybeanMR2 implements ActionBarDrawerToggleImpl {
        private ActionBarDrawerToggleImplJellybeanMR2() throws  {
        }

        public Drawable getThemeUpIndicator(Activity $r1) throws  {
            return ActionBarDrawerToggleJellybeanMR2.getThemeUpIndicator($r1);
        }

        public Object setActionBarUpIndicator(Object $r1, Activity $r2, Drawable $r3, int $i0) throws  {
            return ActionBarDrawerToggleJellybeanMR2.setActionBarUpIndicator($r1, $r2, $r3, $i0);
        }

        public Object setActionBarDescription(Object $r1, Activity $r2, int $i0) throws  {
            return ActionBarDrawerToggleJellybeanMR2.setActionBarDescription($r1, $r2, $i0);
        }
    }

    public interface Delegate {
        @Nullable
        Drawable getThemeUpIndicator() throws ;

        void setActionBarDescription(@StringRes int i) throws ;

        void setActionBarUpIndicator(Drawable drawable, @StringRes int i) throws ;
    }

    public interface DelegateProvider {
        @Nullable
        Delegate getDrawerToggleDelegate() throws ;
    }

    private class SlideDrawable extends InsetDrawable implements Callback {
        private final boolean mHasMirroring;
        private float mOffset;
        private float mPosition;
        private final Rect mTmpRect;
        final /* synthetic */ ActionBarDrawerToggle this$0;

        private SlideDrawable(ActionBarDrawerToggle $r1, Drawable $r2) throws  {
            boolean $z0 = false;
            this.this$0 = $r1;
            super($r2, 0);
            if (VERSION.SDK_INT > 18) {
                $z0 = true;
            }
            this.mHasMirroring = $z0;
            this.mTmpRect = new Rect();
        }

        public void setPosition(float $f0) throws  {
            this.mPosition = $f0;
            invalidateSelf();
        }

        public float getPosition() throws  {
            return this.mPosition;
        }

        public void setOffset(float $f0) throws  {
            this.mOffset = $f0;
            invalidateSelf();
        }

        public void draw(Canvas $r1) throws  {
            byte $b0 = (byte) 1;
            copyBounds(this.mTmpRect);
            $r1.save();
            boolean $z0 = ViewCompat.getLayoutDirection(this.this$0.mActivity.getWindow().getDecorView()) == 1;
            if ($z0) {
                $b0 = (byte) -1;
            }
            int $i1 = this.mTmpRect.width();
            $r1.translate((((-this.mOffset) * ((float) $i1)) * this.mPosition) * ((float) $b0), 0.0f);
            if ($z0 && !this.mHasMirroring) {
                $r1.translate((float) $i1, 0.0f);
                $r1.scale(-1.0f, 1.0f);
            }
            super.draw($r1);
            $r1.restore();
        }
    }

    static {
        int $i0 = VERSION.SDK_INT;
        if ($i0 >= 18) {
            IMPL = new ActionBarDrawerToggleImplJellybeanMR2();
        } else if ($i0 >= 11) {
            IMPL = new ActionBarDrawerToggleImplHC();
        } else {
            IMPL = new ActionBarDrawerToggleImplBase();
        }
    }

    public ActionBarDrawerToggle(Activity $r1, DrawerLayout $r2, @DrawableRes int $i0, @StringRes int $i1, @StringRes int $i2) throws  {
        this($r1, $r2, !assumeMaterial($r1), $i0, $i1, $i2);
    }

    private static boolean assumeMaterial(Context $r0) throws  {
        return $r0.getApplicationInfo().targetSdkVersion >= 21 && VERSION.SDK_INT >= 21;
    }

    public ActionBarDrawerToggle(Activity $r1, DrawerLayout $r2, boolean $z0, @DrawableRes int $i0, @StringRes int $i1, @StringRes int $i2) throws  {
        this.mDrawerIndicatorEnabled = true;
        this.mActivity = $r1;
        if ($r1 instanceof DelegateProvider) {
            this.mActivityImpl = ((DelegateProvider) $r1).getDrawerToggleDelegate();
        } else {
            this.mActivityImpl = null;
        }
        this.mDrawerLayout = $r2;
        this.mDrawerImageResource = $i0;
        this.mOpenDrawerContentDescRes = $i1;
        this.mCloseDrawerContentDescRes = $i2;
        this.mHomeAsUpIndicator = getThemeUpIndicator();
        this.mDrawerImage = ContextCompat.getDrawable($r1, $i0);
        this.mSlider = new SlideDrawable(this.mDrawerImage);
        this.mSlider.setOffset($z0 ? TOGGLE_DRAWABLE_OFFSET : 0.0f);
    }

    public void syncState() throws  {
        if (this.mDrawerLayout.isDrawerOpen((int) GravityCompat.START)) {
            this.mSlider.setPosition(1.0f);
        } else {
            this.mSlider.setPosition(0.0f);
        }
        if (this.mDrawerIndicatorEnabled) {
            setActionBarUpIndicator(this.mSlider, this.mDrawerLayout.isDrawerOpen((int) GravityCompat.START) ? this.mCloseDrawerContentDescRes : this.mOpenDrawerContentDescRes);
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
        Drawable $r2 = null;
        if ($i0 != 0) {
            $r2 = ContextCompat.getDrawable(this.mActivity, $i0);
        }
        setHomeAsUpIndicator($r2);
    }

    public void setDrawerIndicatorEnabled(boolean $z0) throws  {
        if ($z0 != this.mDrawerIndicatorEnabled) {
            if ($z0) {
                setActionBarUpIndicator(this.mSlider, this.mDrawerLayout.isDrawerOpen((int) GravityCompat.START) ? this.mCloseDrawerContentDescRes : this.mOpenDrawerContentDescRes);
            } else {
                setActionBarUpIndicator(this.mHomeAsUpIndicator, 0);
            }
            this.mDrawerIndicatorEnabled = $z0;
        }
    }

    public boolean isDrawerIndicatorEnabled() throws  {
        return this.mDrawerIndicatorEnabled;
    }

    public void onConfigurationChanged(Configuration newConfig) throws  {
        if (!this.mHasCustomUpIndicator) {
            this.mHomeAsUpIndicator = getThemeUpIndicator();
        }
        this.mDrawerImage = ContextCompat.getDrawable(this.mActivity, this.mDrawerImageResource);
        syncState();
    }

    public boolean onOptionsItemSelected(MenuItem $r1) throws  {
        if ($r1 == null || $r1.getItemId() != ID_HOME || !this.mDrawerIndicatorEnabled) {
            return false;
        }
        if (this.mDrawerLayout.isDrawerVisible((int) GravityCompat.START)) {
            this.mDrawerLayout.closeDrawer((int) GravityCompat.START);
        } else {
            this.mDrawerLayout.openDrawer((int) GravityCompat.START);
        }
        return true;
    }

    public void onDrawerSlide(View drawerView, float $f0) throws  {
        float $f1 = this.mSlider.getPosition();
        if ($f0 > CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR) {
            $f0 = Math.max($f1, Math.max(0.0f, $f0 - CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR) * LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN);
        } else {
            $f0 = Math.min($f1, $f0 * LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN);
        }
        this.mSlider.setPosition($f0);
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

    Drawable getThemeUpIndicator() throws  {
        if (this.mActivityImpl != null) {
            return this.mActivityImpl.getThemeUpIndicator();
        }
        return IMPL.getThemeUpIndicator(this.mActivity);
    }

    void setActionBarUpIndicator(Drawable $r1, int $i0) throws  {
        if (this.mActivityImpl != null) {
            this.mActivityImpl.setActionBarUpIndicator($r1, $i0);
        } else {
            this.mSetIndicatorInfo = IMPL.setActionBarUpIndicator(this.mSetIndicatorInfo, this.mActivity, $r1, $i0);
        }
    }

    void setActionBarDescription(int $i0) throws  {
        if (this.mActivityImpl != null) {
            this.mActivityImpl.setActionBarDescription($i0);
        } else {
            this.mSetIndicatorInfo = IMPL.setActionBarDescription(this.mSetIndicatorInfo, this.mActivity, $i0);
        }
    }
}
