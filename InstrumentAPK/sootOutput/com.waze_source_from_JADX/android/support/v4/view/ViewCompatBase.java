package android.support.v4.view;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;
import android.view.View;
import android.view.ViewParent;
import java.lang.reflect.Field;

class ViewCompatBase {
    private static final String TAG = "ViewCompatBase";
    private static Field sMinHeightField;
    private static boolean sMinHeightFieldFetched;
    private static Field sMinWidthField;
    private static boolean sMinWidthFieldFetched;

    ViewCompatBase() throws  {
    }

    static ColorStateList getBackgroundTintList(View $r1) throws  {
        return $r1 instanceof TintableBackgroundView ? ((TintableBackgroundView) $r1).getSupportBackgroundTintList() : null;
    }

    static void setBackgroundTintList(View $r1, ColorStateList $r0) throws  {
        if ($r1 instanceof TintableBackgroundView) {
            ((TintableBackgroundView) $r1).setSupportBackgroundTintList($r0);
        }
    }

    static Mode getBackgroundTintMode(View $r1) throws  {
        return $r1 instanceof TintableBackgroundView ? ((TintableBackgroundView) $r1).getSupportBackgroundTintMode() : null;
    }

    static void setBackgroundTintMode(View $r1, Mode $r0) throws  {
        if ($r1 instanceof TintableBackgroundView) {
            ((TintableBackgroundView) $r1).setSupportBackgroundTintMode($r0);
        }
    }

    static boolean isLaidOut(View $r0) throws  {
        return $r0.getWidth() > 0 && $r0.getHeight() > 0;
    }

    static int getMinimumWidth(View $r0) throws  {
        if (!sMinWidthFieldFetched) {
            try {
                sMinWidthField = View.class.getDeclaredField("mMinWidth");
                sMinWidthField.setAccessible(true);
            } catch (NoSuchFieldException e) {
            }
            sMinWidthFieldFetched = true;
        }
        if (sMinWidthField != null) {
            try {
                return ((Integer) sMinWidthField.get($r0)).intValue();
            } catch (Exception e2) {
            }
        }
        return 0;
    }

    static int getMinimumHeight(View $r0) throws  {
        if (!sMinHeightFieldFetched) {
            try {
                sMinHeightField = View.class.getDeclaredField("mMinHeight");
                sMinHeightField.setAccessible(true);
            } catch (NoSuchFieldException e) {
            }
            sMinHeightFieldFetched = true;
        }
        if (sMinHeightField != null) {
            try {
                return ((Integer) sMinHeightField.get($r0)).intValue();
            } catch (Exception e2) {
            }
        }
        return 0;
    }

    static boolean isAttachedToWindow(View $r0) throws  {
        return $r0.getWindowToken() != null;
    }

    static void offsetTopAndBottom(View $r0, int $i0) throws  {
        int $i1 = $r0.getTop();
        $r0.offsetTopAndBottom($i0);
        if ($i0 != 0) {
            ViewParent $r1 = $r0.getParent();
            if ($r1 instanceof View) {
                int $i2 = Math.abs($i0);
                ((View) $r1).invalidate($r0.getLeft(), $i1 - $i2, $r0.getRight(), ($r0.getHeight() + $i1) + $i2);
                return;
            }
            $r0.invalidate();
        }
    }

    static void offsetLeftAndRight(View $r0, int $i0) throws  {
        int $i1 = $r0.getLeft();
        $r0.offsetLeftAndRight($i0);
        if ($i0 != 0) {
            ViewParent $r1 = $r0.getParent();
            if ($r1 instanceof View) {
                int $i2 = Math.abs($i0);
                ((View) $r1).invalidate($i1 - $i2, $r0.getTop(), ($r0.getWidth() + $i1) + $i2, $r0.getBottom());
                return;
            }
            $r0.invalidate();
        }
    }
}
