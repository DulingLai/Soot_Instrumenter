package android.support.v4.graphics.drawable;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

class DrawableCompatLollipop {
    DrawableCompatLollipop() throws  {
    }

    public static void setHotspot(Drawable $r0, float $f0, float $f1) throws  {
        $r0.setHotspot($f0, $f1);
    }

    public static void setHotspotBounds(Drawable $r0, int $i0, int $i1, int $i2, int $i3) throws  {
        $r0.setHotspotBounds($i0, $i1, $i2, $i3);
    }

    public static void setTint(Drawable $r0, int $i0) throws  {
        $r0.setTint($i0);
    }

    public static void setTintList(Drawable $r0, ColorStateList $r1) throws  {
        $r0.setTintList($r1);
    }

    public static void setTintMode(Drawable $r0, Mode $r1) throws  {
        $r0.setTintMode($r1);
    }

    public static Drawable wrapForTinting(Drawable $r0) throws  {
        if ($r0 instanceof TintAwareDrawable) {
            return $r0;
        }
        return new DrawableWrapperLollipop($r0);
    }

    public static void applyTheme(Drawable $r0, Theme $r1) throws  {
        $r0.applyTheme($r1);
    }

    public static boolean canApplyTheme(Drawable $r0) throws  {
        return $r0.canApplyTheme();
    }

    public static ColorFilter getColorFilter(Drawable $r0) throws  {
        return $r0.getColorFilter();
    }

    public static void inflate(Drawable $r0, Resources $r1, XmlPullParser $r2, AttributeSet $r3, Theme $r4) throws IOException, XmlPullParserException {
        $r0.inflate($r1, $r2, $r3, $r4);
    }
}
