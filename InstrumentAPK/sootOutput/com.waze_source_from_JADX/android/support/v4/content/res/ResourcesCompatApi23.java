package android.support.v4.content.res;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.content.res.Resources.Theme;

class ResourcesCompatApi23 {
    ResourcesCompatApi23() throws  {
    }

    public static int getColor(Resources $r0, int $i0, Theme $r1) throws NotFoundException {
        return $r0.getColor($i0, $r1);
    }

    public static ColorStateList getColorStateList(Resources $r0, int $i0, Theme $r1) throws NotFoundException {
        return $r0.getColorStateList($i0, $r1);
    }
}
