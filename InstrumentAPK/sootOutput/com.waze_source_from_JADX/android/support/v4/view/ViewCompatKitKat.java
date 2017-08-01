package android.support.v4.view;

import android.view.View;

class ViewCompatKitKat {
    ViewCompatKitKat() throws  {
    }

    public static int getAccessibilityLiveRegion(View $r0) throws  {
        return $r0.getAccessibilityLiveRegion();
    }

    public static void setAccessibilityLiveRegion(View $r0, int $i0) throws  {
        $r0.setAccessibilityLiveRegion($i0);
    }

    public static boolean isLaidOut(View $r0) throws  {
        return $r0.isLaidOut();
    }

    public static boolean isAttachedToWindow(View $r0) throws  {
        return $r0.isAttachedToWindow();
    }
}
