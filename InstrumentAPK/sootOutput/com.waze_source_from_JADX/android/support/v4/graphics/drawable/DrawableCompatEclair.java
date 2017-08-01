package android.support.v4.graphics.drawable;

import android.graphics.drawable.Drawable;

class DrawableCompatEclair {
    DrawableCompatEclair() throws  {
    }

    public static Drawable wrapForTinting(Drawable $r0) throws  {
        if ($r0 instanceof TintAwareDrawable) {
            return $r0;
        }
        return new DrawableWrapperEclair($r0);
    }
}
