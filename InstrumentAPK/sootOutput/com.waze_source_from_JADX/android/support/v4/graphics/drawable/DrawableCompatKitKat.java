package android.support.v4.graphics.drawable;

import android.graphics.drawable.Drawable;

class DrawableCompatKitKat {
    DrawableCompatKitKat() throws  {
    }

    public static void setAutoMirrored(Drawable $r0, boolean $z0) throws  {
        $r0.setAutoMirrored($z0);
    }

    public static boolean isAutoMirrored(Drawable $r0) throws  {
        return $r0.isAutoMirrored();
    }

    public static Drawable wrapForTinting(Drawable $r0) throws  {
        if ($r0 instanceof TintAwareDrawable) {
            return $r0;
        }
        return new DrawableWrapperKitKat($r0);
    }

    public static int getAlpha(Drawable $r0) throws  {
        return $r0.getAlpha();
    }
}
