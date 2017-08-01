package android.support.v4.graphics.drawable;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.Callback;
import android.graphics.drawable.Drawable.ConstantState;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

class DrawableWrapperDonut extends Drawable implements Callback, DrawableWrapper, TintAwareDrawable {
    static final Mode DEFAULT_TINT_MODE = Mode.SRC_IN;
    private boolean mColorFilterSet;
    private int mCurrentColor;
    private Mode mCurrentMode;
    Drawable mDrawable;
    private boolean mMutated;
    DrawableWrapperState mState;

    protected static abstract class DrawableWrapperState extends ConstantState {
        int mChangingConfigurations;
        ConstantState mDrawableState;
        ColorStateList mTint = null;
        Mode mTintMode = DrawableWrapperDonut.DEFAULT_TINT_MODE;

        public abstract Drawable newDrawable(@Nullable Resources resources) throws ;

        DrawableWrapperState(@Nullable DrawableWrapperState $r1, @Nullable Resources res) throws  {
            if ($r1 != null) {
                this.mChangingConfigurations = $r1.mChangingConfigurations;
                this.mDrawableState = $r1.mDrawableState;
                this.mTint = $r1.mTint;
                this.mTintMode = $r1.mTintMode;
            }
        }

        public Drawable newDrawable() throws  {
            return newDrawable(null);
        }

        public int getChangingConfigurations() throws  {
            return (this.mDrawableState != null ? this.mDrawableState.getChangingConfigurations() : 0) | this.mChangingConfigurations;
        }

        boolean canConstantState() throws  {
            return this.mDrawableState != null;
        }
    }

    private static class DrawableWrapperStateDonut extends DrawableWrapperState {
        DrawableWrapperStateDonut(@Nullable DrawableWrapperState $r1, @Nullable Resources $r2) throws  {
            super($r1, $r2);
        }

        public Drawable newDrawable(@Nullable Resources $r1) throws  {
            return new DrawableWrapperDonut(this, $r1);
        }
    }

    protected boolean isCompatTintEnabled() throws  {
        return true;
    }

    DrawableWrapperDonut(@NonNull DrawableWrapperState $r1, @Nullable Resources $r2) throws  {
        this.mState = $r1;
        updateLocalState($r2);
    }

    DrawableWrapperDonut(@Nullable Drawable $r1) throws  {
        this.mState = mutateConstantState();
        setWrappedDrawable($r1);
    }

    private void updateLocalState(@Nullable Resources $r1) throws  {
        if (this.mState != null && this.mState.mDrawableState != null) {
            setWrappedDrawable(newDrawableFromState(this.mState.mDrawableState, $r1));
        }
    }

    protected Drawable newDrawableFromState(@NonNull ConstantState $r1, @Nullable Resources res) throws  {
        return $r1.newDrawable();
    }

    public void draw(Canvas $r1) throws  {
        this.mDrawable.draw($r1);
    }

    protected void onBoundsChange(Rect $r1) throws  {
        if (this.mDrawable != null) {
            this.mDrawable.setBounds($r1);
        }
    }

    public void setChangingConfigurations(int $i0) throws  {
        this.mDrawable.setChangingConfigurations($i0);
    }

    public int getChangingConfigurations() throws  {
        return ((this.mState != null ? this.mState.getChangingConfigurations() : 0) | super.getChangingConfigurations()) | this.mDrawable.getChangingConfigurations();
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
        ColorStateList $r2 = (!isCompatTintEnabled() || this.mState == null) ? null : this.mState.mTint;
        if (($r2 == null || !$r2.isStateful()) && !this.mDrawable.isStateful()) {
            return false;
        }
        return true;
    }

    public boolean setState(int[] $r1) throws  {
        return updateTint($r1) || this.mDrawable.setState($r1);
    }

    public int[] getState() throws  {
        return this.mDrawable.getState();
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

    @Nullable
    public ConstantState getConstantState() throws  {
        if (this.mState == null || !this.mState.canConstantState()) {
            return null;
        }
        this.mState.mChangingConfigurations = getChangingConfigurations();
        return this.mState;
    }

    public Drawable mutate() throws  {
        if (this.mMutated || super.mutate() != this) {
            return this;
        }
        this.mState = mutateConstantState();
        if (this.mDrawable != null) {
            this.mDrawable.mutate();
        }
        if (this.mState != null) {
            this.mState.mDrawableState = this.mDrawable != null ? this.mDrawable.getConstantState() : null;
        }
        this.mMutated = true;
        return this;
    }

    @NonNull
    DrawableWrapperState mutateConstantState() throws  {
        return new DrawableWrapperStateDonut(this.mState, null);
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

    public void setTint(int $i0) throws  {
        setTintList(ColorStateList.valueOf($i0));
    }

    public void setTintList(ColorStateList $r1) throws  {
        this.mState.mTint = $r1;
        updateTint(getState());
    }

    public void setTintMode(Mode $r1) throws  {
        this.mState.mTintMode = $r1;
        updateTint(getState());
    }

    private boolean updateTint(int[] $r1) throws  {
        if (!isCompatTintEnabled()) {
            return false;
        }
        ColorStateList $r2 = this.mState.mTint;
        Mode $r3 = this.mState.mTintMode;
        if ($r2 == null || $r3 == null) {
            this.mColorFilterSet = false;
            clearColorFilter();
            return false;
        }
        int $i0 = $r2.getColorForState($r1, $r2.getDefaultColor());
        if (this.mColorFilterSet && $i0 == this.mCurrentColor && $r3 == this.mCurrentMode) {
            return false;
        }
        setColorFilter($i0, $r3);
        this.mCurrentColor = $i0;
        this.mCurrentMode = $r3;
        this.mColorFilterSet = true;
        return true;
    }

    public final Drawable getWrappedDrawable() throws  {
        return this.mDrawable;
    }

    public final void setWrappedDrawable(Drawable $r1) throws  {
        if (this.mDrawable != null) {
            this.mDrawable.setCallback(null);
        }
        this.mDrawable = $r1;
        if ($r1 != null) {
            $r1.setCallback(this);
            $r1.setVisible(isVisible(), true);
            $r1.setState(getState());
            $r1.setLevel(getLevel());
            $r1.setBounds(getBounds());
            if (this.mState != null) {
                this.mState.mDrawableState = $r1.getConstantState();
            }
        }
        invalidateSelf();
    }
}
