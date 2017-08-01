package android.support.v4.view.accessibility;

import android.view.accessibility.AccessibilityNodeInfo;
import dalvik.annotation.Signature;
import java.util.List;

class AccessibilityNodeInfoCompatJellybeanMr2 {
    AccessibilityNodeInfoCompatJellybeanMr2() throws  {
    }

    public static void setViewIdResourceName(Object $r1, String $r0) throws  {
        ((AccessibilityNodeInfo) $r1).setViewIdResourceName($r0);
    }

    public static String getViewIdResourceName(Object $r1) throws  {
        return ((AccessibilityNodeInfo) $r1).getViewIdResourceName();
    }

    public static List<Object> findAccessibilityNodeInfosByViewId(@Signature({"(", "Ljava/lang/Object;", "Ljava/lang/String;", ")", "Ljava/util/List", "<", "Ljava/lang/Object;", ">;"}) Object $r2, @Signature({"(", "Ljava/lang/Object;", "Ljava/lang/String;", ")", "Ljava/util/List", "<", "Ljava/lang/Object;", ">;"}) String $r0) throws  {
        return ((AccessibilityNodeInfo) $r2).findAccessibilityNodeInfosByViewId($r0);
    }

    public static void setTextSelection(Object $r0, int $i0, int $i1) throws  {
        ((AccessibilityNodeInfo) $r0).setTextSelection($i0, $i1);
    }

    public static int getTextSelectionStart(Object $r0) throws  {
        return ((AccessibilityNodeInfo) $r0).getTextSelectionStart();
    }

    public static int getTextSelectionEnd(Object $r0) throws  {
        return ((AccessibilityNodeInfo) $r0).getTextSelectionEnd();
    }

    public static boolean isEditable(Object $r0) throws  {
        return ((AccessibilityNodeInfo) $r0).isEditable();
    }

    public static void setEditable(Object $r0, boolean $z0) throws  {
        ((AccessibilityNodeInfo) $r0).setEditable($z0);
    }

    public static boolean refresh(Object $r0) throws  {
        return ((AccessibilityNodeInfo) $r0).refresh();
    }
}
