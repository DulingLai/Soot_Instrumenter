package android.support.graphics.drawable;

import android.content.res.TypedArray;
import org.xmlpull.v1.XmlPullParser;

class TypedArrayUtils {
    private static final String NAMESPACE = "http://schemas.android.com/apk/res/android";

    TypedArrayUtils() throws  {
    }

    public static boolean hasAttribute(XmlPullParser $r0, String $r1) throws  {
        return $r0.getAttributeValue(NAMESPACE, $r1) != null;
    }

    public static float getNamedFloat(TypedArray $r0, XmlPullParser $r1, String $r2, int $i0, float $f0) throws  {
        return !hasAttribute($r1, $r2) ? $f0 : $r0.getFloat($i0, $f0);
    }

    public static boolean getNamedBoolean(TypedArray $r0, XmlPullParser $r1, String $r2, int $i0, boolean $z0) throws  {
        return !hasAttribute($r1, $r2) ? $z0 : $r0.getBoolean($i0, $z0);
    }

    public static int getNamedInt(TypedArray $r0, XmlPullParser $r1, String $r2, int $i0, int $i1) throws  {
        return !hasAttribute($r1, $r2) ? $i1 : $r0.getInt($i0, $i1);
    }

    public static int getNamedColor(TypedArray $r0, XmlPullParser $r1, String $r2, int $i0, int $i1) throws  {
        return !hasAttribute($r1, $r2) ? $i1 : $r0.getColor($i0, $i1);
    }
}
