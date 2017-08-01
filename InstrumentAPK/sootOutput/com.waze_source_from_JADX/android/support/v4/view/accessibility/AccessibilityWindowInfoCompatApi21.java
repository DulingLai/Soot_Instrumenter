package android.support.v4.view.accessibility;

import android.graphics.Rect;
import android.view.accessibility.AccessibilityWindowInfo;

class AccessibilityWindowInfoCompatApi21 {
    AccessibilityWindowInfoCompatApi21() throws  {
    }

    public static Object obtain() throws  {
        return AccessibilityWindowInfo.obtain();
    }

    public static Object obtain(Object $r1) throws  {
        return AccessibilityWindowInfo.obtain((AccessibilityWindowInfo) $r1);
    }

    public static int getType(Object $r0) throws  {
        return ((AccessibilityWindowInfo) $r0).getType();
    }

    public static int getLayer(Object $r0) throws  {
        return ((AccessibilityWindowInfo) $r0).getLayer();
    }

    public static Object getRoot(Object $r1) throws  {
        return ((AccessibilityWindowInfo) $r1).getRoot();
    }

    public static Object getParent(Object $r1) throws  {
        return ((AccessibilityWindowInfo) $r1).getParent();
    }

    public static int getId(Object $r0) throws  {
        return ((AccessibilityWindowInfo) $r0).getId();
    }

    public static void getBoundsInScreen(Object $r1, Rect $r0) throws  {
        ((AccessibilityWindowInfo) $r1).getBoundsInScreen($r0);
    }

    public static boolean isActive(Object $r0) throws  {
        return ((AccessibilityWindowInfo) $r0).isActive();
    }

    public static boolean isFocused(Object $r0) throws  {
        return ((AccessibilityWindowInfo) $r0).isFocused();
    }

    public static boolean isAccessibilityFocused(Object $r0) throws  {
        return ((AccessibilityWindowInfo) $r0).isAccessibilityFocused();
    }

    public static int getChildCount(Object $r0) throws  {
        return ((AccessibilityWindowInfo) $r0).getChildCount();
    }

    public static Object getChild(Object $r1, int $i0) throws  {
        return ((AccessibilityWindowInfo) $r1).getChild($i0);
    }

    public static void recycle(Object $r0) throws  {
        ((AccessibilityWindowInfo) $r0).recycle();
    }
}
