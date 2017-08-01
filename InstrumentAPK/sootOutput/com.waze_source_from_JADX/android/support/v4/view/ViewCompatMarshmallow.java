package android.support.v4.view;

import android.view.View;

class ViewCompatMarshmallow {
    ViewCompatMarshmallow() throws  {
    }

    public static void setScrollIndicators(View $r0, int $i0) throws  {
        $r0.setScrollIndicators($i0);
    }

    public static void setScrollIndicators(View $r0, int $i0, int $i1) throws  {
        $r0.setScrollIndicators($i0, $i1);
    }

    public static int getScrollIndicators(View $r0) throws  {
        return $r0.getScrollIndicators();
    }

    static void offsetTopAndBottom(View $r0, int $i0) throws  {
        $r0.offsetTopAndBottom($i0);
    }

    static void offsetLeftAndRight(View $r0, int $i0) throws  {
        $r0.offsetLeftAndRight($i0);
    }
}
