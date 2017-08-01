package android.support.v4.graphics.drawable;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

class DrawableWrapperHoneycomb extends DrawableWrapperDonut {

    private static class DrawableWrapperStateHoneycomb extends DrawableWrapperState {
        DrawableWrapperStateHoneycomb(@Nullable DrawableWrapperState $r1, @Nullable Resources $r2) throws  {
            super($r1, $r2);
        }

        public Drawable newDrawable(@Nullable Resources $r1) throws  {
            return new DrawableWrapperHoneycomb(this, $r1);
        }
    }

    DrawableWrapperHoneycomb(Drawable $r1) throws  {
        super($r1);
    }

    DrawableWrapperHoneycomb(DrawableWrapperState $r1, Resources $r2) throws  {
        super($r1, $r2);
    }

    public void jumpToCurrentState() throws  {
        this.mDrawable.jumpToCurrentState();
    }

    @NonNull
    DrawableWrapperState mutateConstantState() throws  {
        return new DrawableWrapperStateHoneycomb(this.mState, null);
    }
}
