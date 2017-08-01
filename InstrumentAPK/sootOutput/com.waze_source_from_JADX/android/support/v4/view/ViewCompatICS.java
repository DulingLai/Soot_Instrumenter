package android.support.v4.view;

import android.support.annotation.Nullable;
import android.view.View;
import android.view.View.AccessibilityDelegate;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

class ViewCompatICS {
    ViewCompatICS() throws  {
    }

    public static boolean canScrollHorizontally(View $r0, int $i0) throws  {
        return $r0.canScrollHorizontally($i0);
    }

    public static boolean canScrollVertically(View $r0, int $i0) throws  {
        return $r0.canScrollVertically($i0);
    }

    public static void setAccessibilityDelegate(View $r0, @Nullable Object $r1) throws  {
        $r0.setAccessibilityDelegate((AccessibilityDelegate) $r1);
    }

    public static void onPopulateAccessibilityEvent(View $r0, AccessibilityEvent $r1) throws  {
        $r0.onPopulateAccessibilityEvent($r1);
    }

    public static void onInitializeAccessibilityEvent(View $r0, AccessibilityEvent $r1) throws  {
        $r0.onInitializeAccessibilityEvent($r1);
    }

    public static void onInitializeAccessibilityNodeInfo(View $r0, Object $r1) throws  {
        $r0.onInitializeAccessibilityNodeInfo((AccessibilityNodeInfo) $r1);
    }

    public static void setFitsSystemWindows(View $r0, boolean $z0) throws  {
        $r0.setFitsSystemWindows($z0);
    }
}
