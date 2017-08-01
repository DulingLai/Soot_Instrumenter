package android.support.v4.widget;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.TextView;

class TextViewCompatJbMr1 {
    TextViewCompatJbMr1() throws  {
    }

    public static void setCompoundDrawablesRelative(@NonNull TextView $r0, @Nullable Drawable $r4, @Nullable Drawable $r1, @Nullable Drawable $r2, @Nullable Drawable $r3) throws  {
        boolean $z0 = true;
        if ($r0.getLayoutDirection() != 1) {
            $z0 = false;
        }
        Drawable $r5 = $z0 ? $r2 : $r4;
        if (!$z0) {
            $r4 = $r2;
        }
        $r0.setCompoundDrawables($r5, $r1, $r4, $r3);
    }

    public static void setCompoundDrawablesRelativeWithIntrinsicBounds(@NonNull TextView $r0, @Nullable Drawable $r4, @Nullable Drawable $r1, @Nullable Drawable $r2, @Nullable Drawable $r3) throws  {
        boolean $z0 = true;
        if ($r0.getLayoutDirection() != 1) {
            $z0 = false;
        }
        Drawable $r5 = $z0 ? $r2 : $r4;
        if (!$z0) {
            $r4 = $r2;
        }
        $r0.setCompoundDrawablesWithIntrinsicBounds($r5, $r1, $r4, $r3);
    }

    public static void setCompoundDrawablesRelativeWithIntrinsicBounds(@NonNull TextView $r0, int $i4, int $i0, int $i1, int $i2) throws  {
        boolean $z0 = true;
        if ($r0.getLayoutDirection() != 1) {
            $z0 = false;
        }
        int $i3 = $z0 ? $i1 : $i4;
        if (!$z0) {
            $i4 = $i1;
        }
        $r0.setCompoundDrawablesWithIntrinsicBounds($i3, $i0, $i4, $i2);
    }
}
