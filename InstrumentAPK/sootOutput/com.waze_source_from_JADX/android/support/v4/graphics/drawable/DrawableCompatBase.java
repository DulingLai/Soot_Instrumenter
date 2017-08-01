package android.support.v4.graphics.drawable;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

class DrawableCompatBase {
    DrawableCompatBase() throws  {
    }

    public static void setTint(Drawable $r0, int $i0) throws  {
        if ($r0 instanceof TintAwareDrawable) {
            ((TintAwareDrawable) $r0).setTint($i0);
        }
    }

    public static void setTintList(Drawable $r1, ColorStateList $r0) throws  {
        if ($r1 instanceof TintAwareDrawable) {
            ((TintAwareDrawable) $r1).setTintList($r0);
        }
    }

    public static void setTintMode(Drawable $r1, Mode $r0) throws  {
        if ($r1 instanceof TintAwareDrawable) {
            ((TintAwareDrawable) $r1).setTintMode($r0);
        }
    }

    public static Drawable wrapForTinting(Drawable $r0) throws  {
        if ($r0 instanceof TintAwareDrawable) {
            return $r0;
        }
        return new DrawableWrapperDonut($r0);
    }

    public static void inflate(Drawable $r0, Resources $r1, XmlPullParser $r2, AttributeSet $r3, Theme t) throws IOException, XmlPullParserException {
        $r0.inflate($r1, $r2, $r3);
    }
}
