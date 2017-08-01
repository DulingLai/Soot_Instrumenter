package android.support.v4.graphics.drawable;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

class DrawableWrapperKitKat extends DrawableWrapperHoneycomb {

    private static class DrawableWrapperStateKitKat extends DrawableWrapperState {
        DrawableWrapperStateKitKat(@Nullable DrawableWrapperState $r1, @Nullable Resources $r2) throws  {
            super($r1, $r2);
        }

        public Drawable newDrawable(@Nullable Resources $r1) throws  {
            return new DrawableWrapperKitKat(this, $r1);
        }
    }

    DrawableWrapperKitKat(Drawable $r1) throws  {
        super($r1);
    }

    DrawableWrapperKitKat(DrawableWrapperState $r1, Resources $r2) throws  {
        super($r1, $r2);
    }

    public void setAutoMirrored(boolean $z0) throws  {
        this.mDrawable.setAutoMirrored($z0);
    }

    public boolean isAutoMirrored() throws  {
        return this.mDrawable.isAutoMirrored();
    }

    @NonNull
    DrawableWrapperState mutateConstantState() throws  {
        return new DrawableWrapperStateKitKat(this.mState, null);
    }
}
