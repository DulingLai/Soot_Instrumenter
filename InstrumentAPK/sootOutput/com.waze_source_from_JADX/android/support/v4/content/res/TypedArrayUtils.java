package android.support.v4.content.res;

import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.AnyRes;
import android.support.annotation.StyleableRes;

public class TypedArrayUtils {
    public static boolean getBoolean(TypedArray $r0, @StyleableRes int $i0, @StyleableRes int $i1, boolean $z0) throws  {
        return $r0.getBoolean($i0, $r0.getBoolean($i1, $z0));
    }

    public static Drawable getDrawable(TypedArray $r0, @StyleableRes int $i0, @StyleableRes int $i1) throws  {
        Drawable $r1 = $r0.getDrawable($i0);
        if ($r1 == null) {
            return $r0.getDrawable($i1);
        }
        return $r1;
    }

    public static int getInt(TypedArray $r0, @StyleableRes int $i0, @StyleableRes int $i1, int $i2) throws  {
        return $r0.getInt($i0, $r0.getInt($i1, $i2));
    }

    @AnyRes
    public static int getResourceId(TypedArray $r0, @StyleableRes int $i0, @StyleableRes int $i1, @AnyRes int $i2) throws  {
        return $r0.getResourceId($i0, $r0.getResourceId($i1, $i2));
    }

    public static String getString(TypedArray $r0, @StyleableRes int $i0, @StyleableRes int $i1) throws  {
        String $r1 = $r0.getString($i0);
        if ($r1 == null) {
            return $r0.getString($i1);
        }
        return $r1;
    }

    public static CharSequence[] getTextArray(TypedArray $r0, @StyleableRes int $i0, @StyleableRes int $i1) throws  {
        CharSequence[] $r1 = $r0.getTextArray($i0);
        if ($r1 == null) {
            return $r0.getTextArray($i1);
        }
        return $r1;
    }
}
