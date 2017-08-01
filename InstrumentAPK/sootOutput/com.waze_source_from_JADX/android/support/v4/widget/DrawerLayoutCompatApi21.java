package android.support.v4.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.View.OnApplyWindowInsetsListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.WindowInsets;
import com.waze.strings.DisplayStrings;

class DrawerLayoutCompatApi21 {
    private static final int[] THEME_ATTRS = new int[]{16843828};

    static class InsetsListener implements OnApplyWindowInsetsListener {
        InsetsListener() throws  {
        }

        public WindowInsets onApplyWindowInsets(View $r1, WindowInsets $r2) throws  {
            ((DrawerLayoutImpl) $r1).setChildInsets($r2, $r2.getSystemWindowInsetTop() > 0);
            return $r2.consumeSystemWindowInsets();
        }
    }

    DrawerLayoutCompatApi21() throws  {
    }

    public static void configureApplyInsets(View $r0) throws  {
        if ($r0 instanceof DrawerLayoutImpl) {
            $r0.setOnApplyWindowInsetsListener(new InsetsListener());
            $r0.setSystemUiVisibility(DisplayStrings.DS_HTML_MAKE_SURE_WAZE_SWITCHED_ON);
        }
    }

    public static void dispatchChildInsets(View $r0, Object $r1, int $i0) throws  {
        WindowInsets $r2 = (WindowInsets) $r1;
        if ($i0 == 3) {
            $r2 = $r2.replaceSystemWindowInsets($r2.getSystemWindowInsetLeft(), $r2.getSystemWindowInsetTop(), 0, $r2.getSystemWindowInsetBottom());
        } else if ($i0 == 5) {
            $r2 = $r2.replaceSystemWindowInsets(0, $r2.getSystemWindowInsetTop(), $r2.getSystemWindowInsetRight(), $r2.getSystemWindowInsetBottom());
        }
        $r0.dispatchApplyWindowInsets($r2);
    }

    public static void applyMarginInsets(MarginLayoutParams $r0, Object $r1, int $i0) throws  {
        WindowInsets $r2 = (WindowInsets) $r1;
        if ($i0 == 3) {
            $r2 = $r2.replaceSystemWindowInsets($r2.getSystemWindowInsetLeft(), $r2.getSystemWindowInsetTop(), 0, $r2.getSystemWindowInsetBottom());
        } else if ($i0 == 5) {
            $r2 = $r2.replaceSystemWindowInsets(0, $r2.getSystemWindowInsetTop(), $r2.getSystemWindowInsetRight(), $r2.getSystemWindowInsetBottom());
        }
        $r0.leftMargin = $r2.getSystemWindowInsetLeft();
        $r0.topMargin = $r2.getSystemWindowInsetTop();
        $r0.rightMargin = $r2.getSystemWindowInsetRight();
        $r0.bottomMargin = $r2.getSystemWindowInsetBottom();
    }

    public static int getTopInset(Object $r0) throws  {
        return $r0 != null ? ((WindowInsets) $r0).getSystemWindowInsetTop() : 0;
    }

    public static Drawable getDefaultStatusBarBackground(Context $r0) throws  {
        TypedArray $r2 = $r0.obtainStyledAttributes(THEME_ATTRS);
        try {
            Drawable $r3 = $r2.getDrawable(0);
            return $r3;
        } finally {
            $r2.recycle();
        }
    }
}
