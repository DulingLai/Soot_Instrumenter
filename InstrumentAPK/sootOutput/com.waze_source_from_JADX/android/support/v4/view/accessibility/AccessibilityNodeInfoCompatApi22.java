package android.support.v4.view.accessibility;

import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;

class AccessibilityNodeInfoCompatApi22 {
    AccessibilityNodeInfoCompatApi22() throws  {
    }

    public static Object getTraversalBefore(Object $r1) throws  {
        return ((AccessibilityNodeInfo) $r1).getTraversalBefore();
    }

    public static void setTraversalBefore(Object $r1, View $r0) throws  {
        ((AccessibilityNodeInfo) $r1).setTraversalBefore($r0);
    }

    public static void setTraversalBefore(Object $r1, View $r0, int $i0) throws  {
        ((AccessibilityNodeInfo) $r1).setTraversalBefore($r0, $i0);
    }

    public static Object getTraversalAfter(Object $r1) throws  {
        return ((AccessibilityNodeInfo) $r1).getTraversalAfter();
    }

    public static void setTraversalAfter(Object $r1, View $r0) throws  {
        ((AccessibilityNodeInfo) $r1).setTraversalAfter($r0);
    }

    public static void setTraversalAfter(Object $r1, View $r0, int $i0) throws  {
        ((AccessibilityNodeInfo) $r1).setTraversalAfter($r0, $i0);
    }
}
