package android.support.v4.view.accessibility;

import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;

class AccessibilityNodeInfoCompatJellybeanMr1 {
    AccessibilityNodeInfoCompatJellybeanMr1() throws  {
    }

    public static void setLabelFor(Object $r1, View $r0) throws  {
        ((AccessibilityNodeInfo) $r1).setLabelFor($r0);
    }

    public static void setLabelFor(Object $r1, View $r0, int $i0) throws  {
        ((AccessibilityNodeInfo) $r1).setLabelFor($r0, $i0);
    }

    public static Object getLabelFor(Object $r1) throws  {
        return ((AccessibilityNodeInfo) $r1).getLabelFor();
    }

    public static void setLabeledBy(Object $r1, View $r0) throws  {
        ((AccessibilityNodeInfo) $r1).setLabeledBy($r0);
    }

    public static void setLabeledBy(Object $r1, View $r0, int $i0) throws  {
        ((AccessibilityNodeInfo) $r1).setLabeledBy($r0, $i0);
    }

    public static Object getLabeledBy(Object $r1) throws  {
        return ((AccessibilityNodeInfo) $r1).getLabeledBy();
    }
}
