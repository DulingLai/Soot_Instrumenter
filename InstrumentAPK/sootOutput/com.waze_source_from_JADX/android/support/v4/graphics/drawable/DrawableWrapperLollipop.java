package android.support.v4.graphics.drawable;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Outline;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.InsetDrawable;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

class DrawableWrapperLollipop extends DrawableWrapperKitKat {

    private static class DrawableWrapperStateLollipop extends DrawableWrapperState {
        DrawableWrapperStateLollipop(@Nullable DrawableWrapperState $r1, @Nullable Resources $r2) throws  {
            super($r1, $r2);
        }

        public Drawable newDrawable(@Nullable Resources $r1) throws  {
            return new DrawableWrapperLollipop(this, $r1);
        }
    }

    DrawableWrapperLollipop(Drawable $r1) throws  {
        super($r1);
    }

    DrawableWrapperLollipop(DrawableWrapperState $r1, Resources $r2) throws  {
        super($r1, $r2);
    }

    public void setHotspot(float $f0, float $f1) throws  {
        this.mDrawable.setHotspot($f0, $f1);
    }

    public void setHotspotBounds(int $i0, int $i1, int $i2, int $i3) throws  {
        this.mDrawable.setHotspotBounds($i0, $i1, $i2, $i3);
    }

    public void getOutline(Outline $r1) throws  {
        this.mDrawable.getOutline($r1);
    }

    public Rect getDirtyBounds() throws  {
        return this.mDrawable.getDirtyBounds();
    }

    public void setTintList(ColorStateList $r1) throws  {
        if (isCompatTintEnabled()) {
            super.setTintList($r1);
        } else {
            this.mDrawable.setTintList($r1);
        }
    }

    public void setTint(int $i0) throws  {
        if (isCompatTintEnabled()) {
            super.setTint($i0);
        } else {
            this.mDrawable.setTint($i0);
        }
    }

    public void setTintMode(Mode $r1) throws  {
        if (isCompatTintEnabled()) {
            super.setTintMode($r1);
        } else {
            this.mDrawable.setTintMode($r1);
        }
    }

    public boolean setState(int[] $r1) throws  {
        if (!super.setState($r1)) {
            return false;
        }
        invalidateSelf();
        return true;
    }

    protected boolean isCompatTintEnabled() throws  {
        if (VERSION.SDK_INT != 21) {
            return false;
        }
        Drawable $r1 = this.mDrawable;
        return ($r1 instanceof GradientDrawable) || ($r1 instanceof DrawableContainer) || ($r1 instanceof InsetDrawable);
    }

    @NonNull
    DrawableWrapperState mutateConstantState() throws  {
        return new DrawableWrapperStateLollipop(this.mState, null);
    }
}
