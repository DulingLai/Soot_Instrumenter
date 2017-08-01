package android.support.v4.content.res;

import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.content.res.Resources.Theme;
import android.graphics.drawable.Drawable;

class ResourcesCompatApi21 {
    ResourcesCompatApi21() throws  {
    }

    public static Drawable getDrawable(Resources $r0, int $i0, Theme $r1) throws NotFoundException {
        return $r0.getDrawable($i0, $r1);
    }

    public static Drawable getDrawableForDensity(Resources $r0, int $i0, int $i1, Theme $r1) throws NotFoundException {
        return $r0.getDrawableForDensity($i0, $i1, $r1);
    }
}
