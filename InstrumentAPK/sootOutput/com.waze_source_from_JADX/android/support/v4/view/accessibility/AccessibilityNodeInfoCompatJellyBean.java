package android.support.v4.view.accessibility;

import android.os.Bundle;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;

class AccessibilityNodeInfoCompatJellyBean {
    AccessibilityNodeInfoCompatJellyBean() throws  {
    }

    public static void addChild(Object $r1, View $r0, int $i0) throws  {
        ((AccessibilityNodeInfo) $r1).addChild($r0, $i0);
    }

    public static void setSource(Object $r1, View $r0, int $i0) throws  {
        ((AccessibilityNodeInfo) $r1).setSource($r0, $i0);
    }

    public static boolean isVisibleToUser(Object $r0) throws  {
        return ((AccessibilityNodeInfo) $r0).isVisibleToUser();
    }

    public static void setVisibleToUser(Object $r0, boolean $z0) throws  {
        ((AccessibilityNodeInfo) $r0).setVisibleToUser($z0);
    }

    public static boolean performAction(Object $r1, int $i0, Bundle $r0) throws  {
        return ((AccessibilityNodeInfo) $r1).performAction($i0, $r0);
    }

    public static void setMovementGranularities(Object $r0, int $i0) throws  {
        ((AccessibilityNodeInfo) $r0).setMovementGranularities($i0);
    }

    public static int getMovementGranularities(Object $r0) throws  {
        return ((AccessibilityNodeInfo) $r0).getMovementGranularities();
    }

    public static Object obtain(View $r0, int $i0) throws  {
        return AccessibilityNodeInfo.obtain($r0, $i0);
    }

    public static Object findFocus(Object $r1, int $i0) throws  {
        return ((AccessibilityNodeInfo) $r1).findFocus($i0);
    }

    public static Object focusSearch(Object $r1, int $i0) throws  {
        return ((AccessibilityNodeInfo) $r1).focusSearch($i0);
    }

    public static void setParent(Object $r1, View $r0, int $i0) throws  {
        ((AccessibilityNodeInfo) $r1).setParent($r0, $i0);
    }

    public static boolean isAccessibilityFocused(Object $r0) throws  {
        return ((AccessibilityNodeInfo) $r0).isAccessibilityFocused();
    }

    public static void setAccesibilityFocused(Object $r0, boolean $z0) throws  {
        ((AccessibilityNodeInfo) $r0).setAccessibilityFocused($z0);
    }
}
