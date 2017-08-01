package android.support.v4.view.accessibility;

import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction;
import android.view.accessibility.AccessibilityNodeInfo.CollectionInfo;
import dalvik.annotation.Signature;
import java.util.List;

class AccessibilityNodeInfoCompatApi21 {

    static class CollectionItemInfo {
        CollectionItemInfo() throws  {
        }

        public static boolean isSelected(Object $r0) throws  {
            return ((android.view.accessibility.AccessibilityNodeInfo.CollectionItemInfo) $r0).isSelected();
        }
    }

    AccessibilityNodeInfoCompatApi21() throws  {
    }

    static List<Object> getActionList(@Signature({"(", "Ljava/lang/Object;", ")", "Ljava/util/List", "<", "Ljava/lang/Object;", ">;"}) Object $r1) throws  {
        return ((AccessibilityNodeInfo) $r1).getActionList();
    }

    static void addAction(Object $r0, Object $r1) throws  {
        ((AccessibilityNodeInfo) $r0).addAction((AccessibilityAction) $r1);
    }

    public static boolean removeAction(Object $r0, Object $r1) throws  {
        return ((AccessibilityNodeInfo) $r0).removeAction((AccessibilityAction) $r1);
    }

    public static Object obtainCollectionInfo(int $i0, int $i1, boolean $z0, int $i2) throws  {
        return CollectionInfo.obtain($i0, $i1, $z0, $i2);
    }

    public static Object obtainCollectionItemInfo(int $i0, int $i1, int $i2, int $i3, boolean $z0, boolean $z1) throws  {
        return android.view.accessibility.AccessibilityNodeInfo.CollectionItemInfo.obtain($i0, $i1, $i2, $i3, $z0, $z1);
    }

    public static CharSequence getError(Object $r1) throws  {
        return ((AccessibilityNodeInfo) $r1).getError();
    }

    public static void setError(Object $r1, CharSequence $r0) throws  {
        ((AccessibilityNodeInfo) $r1).setError($r0);
    }

    public static void setMaxTextLength(Object $r0, int $i0) throws  {
        ((AccessibilityNodeInfo) $r0).setMaxTextLength($i0);
    }

    public static int getMaxTextLength(Object $r0) throws  {
        return ((AccessibilityNodeInfo) $r0).getMaxTextLength();
    }

    public static Object getWindow(Object $r1) throws  {
        return ((AccessibilityNodeInfo) $r1).getWindow();
    }

    public static boolean removeChild(Object $r1, View $r0) throws  {
        return ((AccessibilityNodeInfo) $r1).removeChild($r0);
    }

    public static boolean removeChild(Object $r1, View $r0, int $i0) throws  {
        return ((AccessibilityNodeInfo) $r1).removeChild($r0, $i0);
    }

    static Object newAccessibilityAction(int $i0, CharSequence $r0) throws  {
        return new AccessibilityAction($i0, $r0);
    }

    static int getAccessibilityActionId(Object $r0) throws  {
        return ((AccessibilityAction) $r0).getId();
    }

    static CharSequence getAccessibilityActionLabel(Object $r1) throws  {
        return ((AccessibilityAction) $r1).getLabel();
    }
}
