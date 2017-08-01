package android.support.v4.widget;

import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.TextView;

class TextViewCompatJbMr2 {
    TextViewCompatJbMr2() throws  {
    }

    public static void setCompoundDrawablesRelative(@NonNull TextView $r0, @Nullable Drawable $r1, @Nullable Drawable $r2, @Nullable Drawable $r3, @Nullable Drawable $r4) throws  {
        $r0.setCompoundDrawablesRelative($r1, $r2, $r3, $r4);
    }

    public static void setCompoundDrawablesRelativeWithIntrinsicBounds(@NonNull TextView $r0, @Nullable Drawable $r1, @Nullable Drawable $r2, @Nullable Drawable $r3, @Nullable Drawable $r4) throws  {
        $r0.setCompoundDrawablesRelativeWithIntrinsicBounds($r1, $r2, $r3, $r4);
    }

    public static void setCompoundDrawablesRelativeWithIntrinsicBounds(@NonNull TextView $r0, @DrawableRes int $i0, @DrawableRes int $i1, @DrawableRes int $i2, @DrawableRes int $i3) throws  {
        $r0.setCompoundDrawablesRelativeWithIntrinsicBounds($i0, $i1, $i2, $i3);
    }
}
