package android.support.v4.graphics.drawable;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.ConstantState;
import android.support.annotation.Nullable;

class DrawableWrapperEclair extends DrawableWrapperDonut {

    private static class DrawableWrapperStateEclair extends DrawableWrapperState {
        DrawableWrapperStateEclair(@Nullable DrawableWrapperState $r1, @Nullable Resources $r2) throws  {
            super($r1, $r2);
        }

        public Drawable newDrawable(@Nullable Resources $r1) throws  {
            return new DrawableWrapperEclair(this, $r1);
        }
    }

    DrawableWrapperEclair(Drawable $r1) throws  {
        super($r1);
    }

    DrawableWrapperEclair(DrawableWrapperState $r1, Resources $r2) throws  {
        super($r1, $r2);
    }

    DrawableWrapperState mutateConstantState() throws  {
        return new DrawableWrapperStateEclair(this.mState, null);
    }

    protected Drawable newDrawableFromState(ConstantState $r1, Resources $r2) throws  {
        return $r1.newDrawable($r2);
    }
}
