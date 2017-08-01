package android.support.v7.graphics.drawable;

import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.Callback;
import android.support.v4.graphics.drawable.DrawableCompat;

public class DrawableWrapper extends Drawable implements Callback {
    private Drawable mDrawable;

    public DrawableWrapper(Drawable $r1) throws  {
        setWrappedDrawable($r1);
    }

    public void draw(Canvas $r1) throws  {
        this.mDrawable.draw($r1);
    }

    protected void onBoundsChange(Rect $r1) throws  {
        this.mDrawable.setBounds($r1);
    }

    public void setChangingConfigurations(int $i0) throws  {
        this.mDrawable.setChangingConfigurations($i0);
    }

    public int getChangingConfigurations() throws  {
        return this.mDrawable.getChangingConfigurations();
    }

    public void setDither(boolean $z0) throws  {
        this.mDrawable.setDither($z0);
    }

    public void setFilterBitmap(boolean $z0) throws  {
        this.mDrawable.setFilterBitmap($z0);
    }

    public void setAlpha(int $i0) throws  {
        this.mDrawable.setAlpha($i0);
    }

    public void setColorFilter(ColorFilter $r1) throws  {
        this.mDrawable.setColorFilter($r1);
    }

    public boolean isStateful() throws  {
        return this.mDrawable.isStateful();
    }

    public boolean setState(int[] $r1) throws  {
        return this.mDrawable.setState($r1);
    }

    public int[] getState() throws  {
        return this.mDrawable.getState();
    }

    public void jumpToCurrentState() throws  {
        DrawableCompat.jumpToCurrentState(this.mDrawable);
    }

    public Drawable getCurrent() throws  {
        return this.mDrawable.getCurrent();
    }

    public boolean setVisible(boolean $z0, boolean $z1) throws  {
        return super.setVisible($z0, $z1) || this.mDrawable.setVisible($z0, $z1);
    }

    public int getOpacity() throws  {
        return this.mDrawable.getOpacity();
    }

    public Region getTransparentRegion() throws  {
        return this.mDrawable.getTransparentRegion();
    }

    public int getIntrinsicWidth() throws  {
        return this.mDrawable.getIntrinsicWidth();
    }

    public int getIntrinsicHeight() throws  {
        return this.mDrawable.getIntrinsicHeight();
    }

    public int getMinimumWidth() throws  {
        return this.mDrawable.getMinimumWidth();
    }

    public int getMinimumHeight() throws  {
        return this.mDrawable.getMinimumHeight();
    }

    public boolean getPadding(Rect $r1) throws  {
        return this.mDrawable.getPadding($r1);
    }

    public void invalidateDrawable(Drawable who) throws  {
        invalidateSelf();
    }

    public void scheduleDrawable(Drawable who, Runnable $r2, long $l0) throws  {
        scheduleSelf($r2, $l0);
    }

    public void unscheduleDrawable(Drawable who, Runnable $r2) throws  {
        unscheduleSelf($r2);
    }

    protected boolean onLevelChange(int $i0) throws  {
        return this.mDrawable.setLevel($i0);
    }

    public void setAutoMirrored(boolean $z0) throws  {
        DrawableCompat.setAutoMirrored(this.mDrawable, $z0);
    }

    public boolean isAutoMirrored() throws  {
        return DrawableCompat.isAutoMirrored(this.mDrawable);
    }

    public void setTint(int $i0) throws  {
        DrawableCompat.setTint(this.mDrawable, $i0);
    }

    public void setTintList(ColorStateList $r1) throws  {
        DrawableCompat.setTintList(this.mDrawable, $r1);
    }

    public void setTintMode(Mode $r1) throws  {
        DrawableCompat.setTintMode(this.mDrawable, $r1);
    }

    public void setHotspot(float $f0, float $f1) throws  {
        DrawableCompat.setHotspot(this.mDrawable, $f0, $f1);
    }

    public void setHotspotBounds(int $i0, int $i1, int $i2, int $i3) throws  {
        DrawableCompat.setHotspotBounds(this.mDrawable, $i0, $i1, $i2, $i3);
    }

    public Drawable getWrappedDrawable() throws  {
        return this.mDrawable;
    }

    public void setWrappedDrawable(Drawable $r1) throws  {
        if (this.mDrawable != null) {
            this.mDrawable.setCallback(null);
        }
        this.mDrawable = $r1;
        if ($r1 != null) {
            $r1.setCallback(this);
        }
    }
}
