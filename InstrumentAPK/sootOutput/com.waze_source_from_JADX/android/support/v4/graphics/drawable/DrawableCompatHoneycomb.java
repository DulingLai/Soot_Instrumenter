package android.support.v4.graphics.drawable;

import android.graphics.drawable.Drawable;

class DrawableCompatHoneycomb {
    DrawableCompatHoneycomb() throws  {
    }

    public static void jumpToCurrentState(Drawable $r0) throws  {
        $r0.jumpToCurrentState();
    }

    public static Drawable wrapForTinting(Drawable $r0) throws  {
        if ($r0 instanceof TintAwareDrawable) {
            return $r0;
        }
        return new DrawableWrapperHoneycomb($r0);
    }
}
