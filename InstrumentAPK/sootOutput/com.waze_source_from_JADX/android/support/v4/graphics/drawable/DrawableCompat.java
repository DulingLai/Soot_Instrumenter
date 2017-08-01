package android.support.v4.graphics.drawable;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import dalvik.annotation.Signature;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public final class DrawableCompat {
    static final DrawableImpl IMPL;

    interface DrawableImpl {
        void applyTheme(Drawable drawable, Theme theme) throws ;

        boolean canApplyTheme(Drawable drawable) throws ;

        int getAlpha(Drawable drawable) throws ;

        ColorFilter getColorFilter(Drawable drawable) throws ;

        int getLayoutDirection(Drawable drawable) throws ;

        void inflate(Drawable drawable, Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Theme theme) throws IOException, XmlPullParserException;

        boolean isAutoMirrored(Drawable drawable) throws ;

        void jumpToCurrentState(Drawable drawable) throws ;

        void setAutoMirrored(Drawable drawable, boolean z) throws ;

        void setHotspot(Drawable drawable, float f, float f2) throws ;

        void setHotspotBounds(Drawable drawable, int i, int i2, int i3, int i4) throws ;

        void setLayoutDirection(Drawable drawable, int i) throws ;

        void setTint(Drawable drawable, int i) throws ;

        void setTintList(Drawable drawable, ColorStateList colorStateList) throws ;

        void setTintMode(Drawable drawable, Mode mode) throws ;

        Drawable wrap(Drawable drawable) throws ;
    }

    static class BaseDrawableImpl implements DrawableImpl {
        public boolean canApplyTheme(Drawable drawable) throws  {
            return false;
        }

        public int getAlpha(Drawable drawable) throws  {
            return 0;
        }

        public ColorFilter getColorFilter(Drawable drawable) throws  {
            return null;
        }

        public int getLayoutDirection(Drawable drawable) throws  {
            return 0;
        }

        public boolean isAutoMirrored(Drawable drawable) throws  {
            return false;
        }

        BaseDrawableImpl() throws  {
        }

        public void jumpToCurrentState(Drawable drawable) throws  {
        }

        public void setAutoMirrored(Drawable drawable, boolean mirrored) throws  {
        }

        public void setHotspot(Drawable drawable, float x, float y) throws  {
        }

        public void setHotspotBounds(Drawable drawable, int left, int top, int right, int bottom) throws  {
        }

        public void setTint(Drawable $r1, int $i0) throws  {
            DrawableCompatBase.setTint($r1, $i0);
        }

        public void setTintList(Drawable $r1, ColorStateList $r2) throws  {
            DrawableCompatBase.setTintList($r1, $r2);
        }

        public void setTintMode(Drawable $r1, Mode $r2) throws  {
            DrawableCompatBase.setTintMode($r1, $r2);
        }

        public Drawable wrap(Drawable $r1) throws  {
            return DrawableCompatBase.wrapForTinting($r1);
        }

        public void setLayoutDirection(Drawable drawable, int layoutDirection) throws  {
        }

        public void applyTheme(Drawable drawable, Theme t) throws  {
        }

        public void inflate(Drawable $r1, Resources $r2, XmlPullParser $r3, AttributeSet $r4, Theme $r5) throws IOException, XmlPullParserException {
            DrawableCompatBase.inflate($r1, $r2, $r3, $r4, $r5);
        }
    }

    static class EclairDrawableImpl extends BaseDrawableImpl {
        EclairDrawableImpl() throws  {
        }

        public Drawable wrap(Drawable $r1) throws  {
            return DrawableCompatEclair.wrapForTinting($r1);
        }
    }

    static class HoneycombDrawableImpl extends EclairDrawableImpl {
        HoneycombDrawableImpl() throws  {
        }

        public void jumpToCurrentState(Drawable $r1) throws  {
            DrawableCompatHoneycomb.jumpToCurrentState($r1);
        }

        public Drawable wrap(Drawable $r1) throws  {
            return DrawableCompatHoneycomb.wrapForTinting($r1);
        }
    }

    static class JellybeanMr1DrawableImpl extends HoneycombDrawableImpl {
        JellybeanMr1DrawableImpl() throws  {
        }

        public void setLayoutDirection(Drawable $r1, int $i0) throws  {
            DrawableCompatJellybeanMr1.setLayoutDirection($r1, $i0);
        }

        public int getLayoutDirection(Drawable $r1) throws  {
            int $i0 = DrawableCompatJellybeanMr1.getLayoutDirection($r1);
            return $i0 >= 0 ? $i0 : 0;
        }
    }

    static class KitKatDrawableImpl extends JellybeanMr1DrawableImpl {
        KitKatDrawableImpl() throws  {
        }

        public void setAutoMirrored(Drawable $r1, boolean $z0) throws  {
            DrawableCompatKitKat.setAutoMirrored($r1, $z0);
        }

        public boolean isAutoMirrored(Drawable $r1) throws  {
            return DrawableCompatKitKat.isAutoMirrored($r1);
        }

        public Drawable wrap(Drawable $r1) throws  {
            return DrawableCompatKitKat.wrapForTinting($r1);
        }

        public int getAlpha(Drawable $r1) throws  {
            return DrawableCompatKitKat.getAlpha($r1);
        }
    }

    static class LollipopDrawableImpl extends KitKatDrawableImpl {
        LollipopDrawableImpl() throws  {
        }

        public void setHotspot(Drawable $r1, float $f0, float $f1) throws  {
            DrawableCompatLollipop.setHotspot($r1, $f0, $f1);
        }

        public void setHotspotBounds(Drawable $r1, int $i0, int $i1, int $i2, int $i3) throws  {
            DrawableCompatLollipop.setHotspotBounds($r1, $i0, $i1, $i2, $i3);
        }

        public void setTint(Drawable $r1, int $i0) throws  {
            DrawableCompatLollipop.setTint($r1, $i0);
        }

        public void setTintList(Drawable $r1, ColorStateList $r2) throws  {
            DrawableCompatLollipop.setTintList($r1, $r2);
        }

        public void setTintMode(Drawable $r1, Mode $r2) throws  {
            DrawableCompatLollipop.setTintMode($r1, $r2);
        }

        public Drawable wrap(Drawable $r1) throws  {
            return DrawableCompatLollipop.wrapForTinting($r1);
        }

        public void applyTheme(Drawable $r1, Theme $r2) throws  {
            DrawableCompatLollipop.applyTheme($r1, $r2);
        }

        public boolean canApplyTheme(Drawable $r1) throws  {
            return DrawableCompatLollipop.canApplyTheme($r1);
        }

        public ColorFilter getColorFilter(Drawable $r1) throws  {
            return DrawableCompatLollipop.getColorFilter($r1);
        }

        public void inflate(Drawable $r1, Resources $r2, XmlPullParser $r3, AttributeSet $r4, Theme $r5) throws IOException, XmlPullParserException {
            DrawableCompatLollipop.inflate($r1, $r2, $r3, $r4, $r5);
        }
    }

    static class MDrawableImpl extends LollipopDrawableImpl {
        MDrawableImpl() throws  {
        }

        public void setLayoutDirection(Drawable $r1, int $i0) throws  {
            DrawableCompatApi23.setLayoutDirection($r1, $i0);
        }

        public int getLayoutDirection(Drawable $r1) throws  {
            return DrawableCompatApi23.getLayoutDirection($r1);
        }

        public Drawable wrap(Drawable $r1) throws  {
            return $r1;
        }
    }

    static {
        int $i0 = VERSION.SDK_INT;
        if ($i0 >= 23) {
            IMPL = new MDrawableImpl();
        } else if ($i0 >= 21) {
            IMPL = new LollipopDrawableImpl();
        } else if ($i0 >= 19) {
            IMPL = new KitKatDrawableImpl();
        } else if ($i0 >= 17) {
            IMPL = new JellybeanMr1DrawableImpl();
        } else if ($i0 >= 11) {
            IMPL = new HoneycombDrawableImpl();
        } else if ($i0 >= 5) {
            IMPL = new EclairDrawableImpl();
        } else {
            IMPL = new BaseDrawableImpl();
        }
    }

    public static void jumpToCurrentState(@NonNull Drawable $r0) throws  {
        IMPL.jumpToCurrentState($r0);
    }

    public static void setAutoMirrored(@NonNull Drawable $r0, boolean $z0) throws  {
        IMPL.setAutoMirrored($r0, $z0);
    }

    public static boolean isAutoMirrored(@NonNull Drawable $r0) throws  {
        return IMPL.isAutoMirrored($r0);
    }

    public static void setHotspot(@NonNull Drawable $r0, float $f0, float $f1) throws  {
        IMPL.setHotspot($r0, $f0, $f1);
    }

    public static void setHotspotBounds(@NonNull Drawable $r0, int $i0, int $i1, int $i2, int $i3) throws  {
        IMPL.setHotspotBounds($r0, $i0, $i1, $i2, $i3);
    }

    public static void setTint(@NonNull Drawable $r0, @ColorInt int $i0) throws  {
        IMPL.setTint($r0, $i0);
    }

    public static void setTintList(@NonNull Drawable $r0, @Nullable ColorStateList $r1) throws  {
        IMPL.setTintList($r0, $r1);
    }

    public static void setTintMode(@NonNull Drawable $r0, @Nullable Mode $r1) throws  {
        IMPL.setTintMode($r0, $r1);
    }

    public static int getAlpha(@NonNull Drawable $r0) throws  {
        return IMPL.getAlpha($r0);
    }

    public static void applyTheme(Drawable $r0, Theme $r1) throws  {
        IMPL.applyTheme($r0, $r1);
    }

    public static boolean canApplyTheme(Drawable $r0) throws  {
        return IMPL.canApplyTheme($r0);
    }

    public static ColorFilter getColorFilter(Drawable $r0) throws  {
        return IMPL.getColorFilter($r0);
    }

    public static void inflate(Drawable $r0, Resources $r1, XmlPullParser $r2, AttributeSet $r3, Theme $r4) throws XmlPullParserException, IOException {
        IMPL.inflate($r0, $r1, $r2, $r3, $r4);
    }

    public static Drawable wrap(@NonNull Drawable $r0) throws  {
        return IMPL.wrap($r0);
    }

    public static <T extends Drawable> T unwrap(@NonNull @Signature({"<T:", "Landroid/graphics/drawable/Drawable;", ">(", "Landroid/graphics/drawable/Drawable;", ")TT;"}) Drawable $r0) throws  {
        if ($r0 instanceof DrawableWrapper) {
            return ((DrawableWrapper) $r0).getWrappedDrawable();
        }
        return $r0;
    }

    public static void setLayoutDirection(@NonNull Drawable $r0, int $i0) throws  {
        IMPL.setLayoutDirection($r0, $i0);
    }

    public static int getLayoutDirection(@NonNull Drawable $r0) throws  {
        return IMPL.getLayoutDirection($r0);
    }

    private DrawableCompat() throws  {
    }
}
