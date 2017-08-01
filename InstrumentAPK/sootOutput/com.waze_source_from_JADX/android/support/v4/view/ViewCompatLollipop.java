package android.support.v4.view;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.view.View;
import android.view.View.OnApplyWindowInsetsListener;
import android.view.ViewParent;
import android.view.WindowInsets;

class ViewCompatLollipop {
    private static ThreadLocal<Rect> sThreadLocalRect;

    ViewCompatLollipop() throws  {
    }

    public static void setTransitionName(View $r0, String $r1) throws  {
        $r0.setTransitionName($r1);
    }

    public static String getTransitionName(View $r0) throws  {
        return $r0.getTransitionName();
    }

    public static void requestApplyInsets(View $r0) throws  {
        $r0.requestApplyInsets();
    }

    public static void setElevation(View $r0, float $f0) throws  {
        $r0.setElevation($f0);
    }

    public static float getElevation(View $r0) throws  {
        return $r0.getElevation();
    }

    public static void setTranslationZ(View $r0, float $f0) throws  {
        $r0.setTranslationZ($f0);
    }

    public static float getTranslationZ(View $r0) throws  {
        return $r0.getTranslationZ();
    }

    public static void setOnApplyWindowInsetsListener(View $r0, final OnApplyWindowInsetsListener $r1) throws  {
        if ($r1 == null) {
            $r0.setOnApplyWindowInsetsListener(null);
        } else {
            $r0.setOnApplyWindowInsetsListener(new OnApplyWindowInsetsListener() {
                public WindowInsets onApplyWindowInsets(View $r1, WindowInsets $r2) throws  {
                    return ((WindowInsetsCompatApi21) $r1.onApplyWindowInsets($r1, new WindowInsetsCompatApi21($r2))).unwrap();
                }
            });
        }
    }

    public static boolean isImportantForAccessibility(View $r0) throws  {
        return $r0.isImportantForAccessibility();
    }

    static ColorStateList getBackgroundTintList(View $r0) throws  {
        return $r0.getBackgroundTintList();
    }

    static void setBackgroundTintList(View $r0, ColorStateList $r1) throws  {
        $r0.setBackgroundTintList($r1);
        if (VERSION.SDK_INT == 21) {
            Drawable $r2 = $r0.getBackground();
            boolean $z0 = ($r0.getBackgroundTintList() == null || $r0.getBackgroundTintMode() == null) ? false : true;
            if ($r2 != null && $z0) {
                if ($r2.isStateful()) {
                    $r2.setState($r0.getDrawableState());
                }
                $r0.setBackground($r2);
            }
        }
    }

    static Mode getBackgroundTintMode(View $r0) throws  {
        return $r0.getBackgroundTintMode();
    }

    static void setBackgroundTintMode(View $r0, Mode $r1) throws  {
        $r0.setBackgroundTintMode($r1);
        if (VERSION.SDK_INT == 21) {
            Drawable $r2 = $r0.getBackground();
            boolean $z0 = ($r0.getBackgroundTintList() == null || $r0.getBackgroundTintMode() == null) ? false : true;
            if ($r2 != null && $z0) {
                if ($r2.isStateful()) {
                    $r2.setState($r0.getDrawableState());
                }
                $r0.setBackground($r2);
            }
        }
    }

    public static WindowInsetsCompat onApplyWindowInsets(View $r0, WindowInsetsCompat $r1) throws  {
        if (!($r1 instanceof WindowInsetsCompatApi21)) {
            return $r1;
        }
        WindowInsets $r3 = ((WindowInsetsCompatApi21) $r1).unwrap();
        WindowInsets $r4 = $r0.onApplyWindowInsets($r3);
        if ($r4 != $r3) {
            return new WindowInsetsCompatApi21($r4);
        }
        return $r1;
    }

    public static WindowInsetsCompat dispatchApplyWindowInsets(View $r0, WindowInsetsCompat $r1) throws  {
        if (!($r1 instanceof WindowInsetsCompatApi21)) {
            return $r1;
        }
        WindowInsets $r3 = ((WindowInsetsCompatApi21) $r1).unwrap();
        WindowInsets $r4 = $r0.dispatchApplyWindowInsets($r3);
        if ($r4 != $r3) {
            return new WindowInsetsCompatApi21($r4);
        }
        return $r1;
    }

    public static void setNestedScrollingEnabled(View $r0, boolean $z0) throws  {
        $r0.setNestedScrollingEnabled($z0);
    }

    public static boolean isNestedScrollingEnabled(View $r0) throws  {
        return $r0.isNestedScrollingEnabled();
    }

    public static boolean startNestedScroll(View $r0, int $i0) throws  {
        return $r0.startNestedScroll($i0);
    }

    public static void stopNestedScroll(View $r0) throws  {
        $r0.stopNestedScroll();
    }

    public static boolean hasNestedScrollingParent(View $r0) throws  {
        return $r0.hasNestedScrollingParent();
    }

    public static boolean dispatchNestedScroll(View $r0, int $i0, int $i1, int $i2, int $i3, int[] $r1) throws  {
        return $r0.dispatchNestedScroll($i0, $i1, $i2, $i3, $r1);
    }

    public static boolean dispatchNestedPreScroll(View $r0, int $i0, int $i1, int[] $r1, int[] $r2) throws  {
        return $r0.dispatchNestedPreScroll($i0, $i1, $r1, $r2);
    }

    public static boolean dispatchNestedFling(View $r0, float $f0, float $f1, boolean $z0) throws  {
        return $r0.dispatchNestedFling($f0, $f1, $z0);
    }

    public static boolean dispatchNestedPreFling(View $r0, float $f0, float $f1) throws  {
        return $r0.dispatchNestedPreFling($f0, $f1);
    }

    public static float getZ(View $r0) throws  {
        return $r0.getZ();
    }

    static void offsetTopAndBottom(View $r0, int $i0) throws  {
        Rect $r1 = getEmptyTempRect();
        boolean $z0 = false;
        ViewParent $r2 = $r0.getParent();
        if ($r2 instanceof View) {
            View $r3 = (View) $r2;
            $r1.set($r3.getLeft(), $r3.getTop(), $r3.getRight(), $r3.getBottom());
            $z0 = !$r1.intersects($r0.getLeft(), $r0.getTop(), $r0.getRight(), $r0.getBottom());
        }
        ViewCompatHC.offsetTopAndBottom($r0, $i0);
        if ($z0 && $r1.intersect($r0.getLeft(), $r0.getTop(), $r0.getRight(), $r0.getBottom())) {
            ((View) $r2).invalidate($r1);
        }
    }

    static void offsetLeftAndRight(View $r0, int $i0) throws  {
        Rect $r1 = getEmptyTempRect();
        boolean $z0 = false;
        ViewParent $r2 = $r0.getParent();
        if ($r2 instanceof View) {
            View $r3 = (View) $r2;
            $r1.set($r3.getLeft(), $r3.getTop(), $r3.getRight(), $r3.getBottom());
            $z0 = !$r1.intersects($r0.getLeft(), $r0.getTop(), $r0.getRight(), $r0.getBottom());
        }
        ViewCompatHC.offsetLeftAndRight($r0, $i0);
        if ($z0 && $r1.intersect($r0.getLeft(), $r0.getTop(), $r0.getRight(), $r0.getBottom())) {
            ((View) $r2).invalidate($r1);
        }
    }

    private static Rect getEmptyTempRect() throws  {
        if (sThreadLocalRect == null) {
            sThreadLocalRect = new ThreadLocal();
        }
        Rect $r2 = (Rect) sThreadLocalRect.get();
        if ($r2 == null) {
            $r2 = new Rect();
            sThreadLocalRect.set($r2);
        }
        $r2.setEmpty();
        return $r2;
    }
}
