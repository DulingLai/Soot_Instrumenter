package android.support.v4.content.res;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.content.res.Resources.Theme;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public final class ResourcesCompat {
    @Nullable
    public static Drawable getDrawable(@NonNull Resources $r0, @DrawableRes int $i0, @Nullable Theme $r1) throws NotFoundException {
        if (VERSION.SDK_INT >= 21) {
            return ResourcesCompatApi21.getDrawable($r0, $i0, $r1);
        }
        return $r0.getDrawable($i0);
    }

    @Nullable
    public static Drawable getDrawableForDensity(@NonNull Resources $r0, @DrawableRes int $i0, int $i1, @Nullable Theme $r1) throws NotFoundException {
        if (VERSION.SDK_INT >= 21) {
            return ResourcesCompatApi21.getDrawableForDensity($r0, $i0, $i1, $r1);
        }
        if (VERSION.SDK_INT >= 15) {
            return ResourcesCompatIcsMr1.getDrawableForDensity($r0, $i0, $i1);
        }
        return $r0.getDrawable($i0);
    }

    @ColorInt
    public static int getColor(@NonNull Resources $r0, @ColorRes int $i0, @Nullable Theme $r1) throws NotFoundException {
        if (VERSION.SDK_INT >= 23) {
            return ResourcesCompatApi23.getColor($r0, $i0, $r1);
        }
        return $r0.getColor($i0);
    }

    @Nullable
    public static ColorStateList getColorStateList(@NonNull Resources $r0, @ColorRes int $i0, @Nullable Theme $r1) throws NotFoundException {
        if (VERSION.SDK_INT >= 23) {
            return ResourcesCompatApi23.getColorStateList($r0, $i0, $r1);
        }
        return $r0.getColorStateList($i0);
    }

    private ResourcesCompat() throws  {
    }
}
