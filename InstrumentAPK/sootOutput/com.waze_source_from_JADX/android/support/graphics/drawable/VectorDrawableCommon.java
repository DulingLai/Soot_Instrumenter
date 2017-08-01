package android.support.graphics.drawable;

import android.annotation.TargetApi;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.graphics.drawable.TintAwareDrawable;
import android.util.AttributeSet;

@TargetApi(21)
abstract class VectorDrawableCommon extends Drawable implements TintAwareDrawable {
    Drawable mDelegateDrawable;

    VectorDrawableCommon() throws  {
    }

    static TypedArray obtainAttributes(Resources $r0, Theme $r1, AttributeSet $r2, int[] $r3) throws  {
        if ($r1 == null) {
            return $r0.obtainAttributes($r2, $r3);
        }
        return $r1.obtainStyledAttributes($r2, $r3, 0, 0);
    }

    public void setColorFilter(int $i0, Mode $r1) throws  {
        if (this.mDelegateDrawable != null) {
            this.mDelegateDrawable.setColorFilter($i0, $r1);
        } else {
            super.setColorFilter($i0, $r1);
        }
    }

    public ColorFilter getColorFilter() throws  {
        if (this.mDelegateDrawable != null) {
            return DrawableCompat.getColorFilter(this.mDelegateDrawable);
        }
        return null;
    }

    protected boolean onLevelChange(int $i0) throws  {
        if (this.mDelegateDrawable != null) {
            return this.mDelegateDrawable.setLevel($i0);
        }
        return super.onLevelChange($i0);
    }

    protected void onBoundsChange(Rect $r1) throws  {
        if (this.mDelegateDrawable != null) {
            this.mDelegateDrawable.setBounds($r1);
        } else {
            super.onBoundsChange($r1);
        }
    }

    public void setHotspot(float $f0, float $f1) throws  {
        if (this.mDelegateDrawable != null) {
            DrawableCompat.setHotspot(this.mDelegateDrawable, $f0, $f1);
        }
    }

    public void setHotspotBounds(int $i0, int $i1, int $i2, int $i3) throws  {
        if (this.mDelegateDrawable != null) {
            DrawableCompat.setHotspotBounds(this.mDelegateDrawable, $i0, $i1, $i2, $i3);
        }
    }

    public void setFilterBitmap(boolean $z0) throws  {
        if (this.mDelegateDrawable != null) {
            this.mDelegateDrawable.setFilterBitmap($z0);
        }
    }

    public void jumpToCurrentState() throws  {
        if (this.mDelegateDrawable != null) {
            DrawableCompat.jumpToCurrentState(this.mDelegateDrawable);
        }
    }

    public void setAutoMirrored(boolean $z0) throws  {
        if (this.mDelegateDrawable != null) {
            DrawableCompat.setAutoMirrored(this.mDelegateDrawable, $z0);
        }
    }

    public boolean isAutoMirrored() throws  {
        if (this.mDelegateDrawable != null) {
            DrawableCompat.isAutoMirrored(this.mDelegateDrawable);
        }
        return false;
    }

    public void applyTheme(Theme $r1) throws  {
        if (this.mDelegateDrawable != null) {
            DrawableCompat.applyTheme(this.mDelegateDrawable, $r1);
        }
    }

    public int getLayoutDirection() throws  {
        if (this.mDelegateDrawable != null) {
            DrawableCompat.getLayoutDirection(this.mDelegateDrawable);
        }
        return 0;
    }

    public void clearColorFilter() throws  {
        if (this.mDelegateDrawable != null) {
            this.mDelegateDrawable.clearColorFilter();
        } else {
            super.clearColorFilter();
        }
    }

    public Drawable getCurrent() throws  {
        if (this.mDelegateDrawable != null) {
            return this.mDelegateDrawable.getCurrent();
        }
        return super.getCurrent();
    }

    public int getMinimumWidth() throws  {
        if (this.mDelegateDrawable != null) {
            return this.mDelegateDrawable.getMinimumWidth();
        }
        return super.getMinimumWidth();
    }

    public int getMinimumHeight() throws  {
        if (this.mDelegateDrawable != null) {
            return this.mDelegateDrawable.getMinimumHeight();
        }
        return super.getMinimumHeight();
    }

    public boolean getPadding(Rect $r1) throws  {
        if (this.mDelegateDrawable != null) {
            return this.mDelegateDrawable.getPadding($r1);
        }
        return super.getPadding($r1);
    }

    public int[] getState() throws  {
        if (this.mDelegateDrawable != null) {
            return this.mDelegateDrawable.getState();
        }
        return super.getState();
    }

    public Region getTransparentRegion() throws  {
        if (this.mDelegateDrawable != null) {
            return this.mDelegateDrawable.getTransparentRegion();
        }
        return super.getTransparentRegion();
    }

    public void setChangingConfigurations(int $i0) throws  {
        if (this.mDelegateDrawable != null) {
            this.mDelegateDrawable.setChangingConfigurations($i0);
        } else {
            super.setChangingConfigurations($i0);
        }
    }

    public boolean setState(int[] $r1) throws  {
        if (this.mDelegateDrawable != null) {
            return this.mDelegateDrawable.setState($r1);
        }
        return super.setState($r1);
    }
}
