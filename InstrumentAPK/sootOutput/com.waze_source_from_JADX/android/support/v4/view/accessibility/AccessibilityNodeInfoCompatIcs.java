package android.support.v4.view.accessibility;

import android.graphics.Rect;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import dalvik.annotation.Signature;
import java.util.List;

class AccessibilityNodeInfoCompatIcs {
    AccessibilityNodeInfoCompatIcs() throws  {
    }

    public static Object obtain() throws  {
        return AccessibilityNodeInfo.obtain();
    }

    public static Object obtain(View $r0) throws  {
        return AccessibilityNodeInfo.obtain($r0);
    }

    public static Object obtain(Object $r1) throws  {
        return AccessibilityNodeInfo.obtain((AccessibilityNodeInfo) $r1);
    }

    public static void addAction(Object $r0, int $i0) throws  {
        ((AccessibilityNodeInfo) $r0).addAction($i0);
    }

    public static void addChild(Object $r1, View $r0) throws  {
        ((AccessibilityNodeInfo) $r1).addChild($r0);
    }

    public static List<Object> findAccessibilityNodeInfosByText(@Signature({"(", "Ljava/lang/Object;", "Ljava/lang/String;", ")", "Ljava/util/List", "<", "Ljava/lang/Object;", ">;"}) Object $r2, @Signature({"(", "Ljava/lang/Object;", "Ljava/lang/String;", ")", "Ljava/util/List", "<", "Ljava/lang/Object;", ">;"}) String $r0) throws  {
        return ((AccessibilityNodeInfo) $r2).findAccessibilityNodeInfosByText($r0);
    }

    public static int getActions(Object $r0) throws  {
        return ((AccessibilityNodeInfo) $r0).getActions();
    }

    public static void getBoundsInParent(Object $r1, Rect $r0) throws  {
        ((AccessibilityNodeInfo) $r1).getBoundsInParent($r0);
    }

    public static void getBoundsInScreen(Object $r1, Rect $r0) throws  {
        ((AccessibilityNodeInfo) $r1).getBoundsInScreen($r0);
    }

    public static Object getChild(Object $r1, int $i0) throws  {
        return ((AccessibilityNodeInfo) $r1).getChild($i0);
    }

    public static int getChildCount(Object $r0) throws  {
        return ((AccessibilityNodeInfo) $r0).getChildCount();
    }

    public static CharSequence getClassName(Object $r1) throws  {
        return ((AccessibilityNodeInfo) $r1).getClassName();
    }

    public static CharSequence getContentDescription(Object $r1) throws  {
        return ((AccessibilityNodeInfo) $r1).getContentDescription();
    }

    public static CharSequence getPackageName(Object $r1) throws  {
        return ((AccessibilityNodeInfo) $r1).getPackageName();
    }

    public static Object getParent(Object $r1) throws  {
        return ((AccessibilityNodeInfo) $r1).getParent();
    }

    public static CharSequence getText(Object $r1) throws  {
        return ((AccessibilityNodeInfo) $r1).getText();
    }

    public static int getWindowId(Object $r0) throws  {
        return ((AccessibilityNodeInfo) $r0).getWindowId();
    }

    public static boolean isCheckable(Object $r0) throws  {
        return ((AccessibilityNodeInfo) $r0).isCheckable();
    }

    public static boolean isChecked(Object $r0) throws  {
        return ((AccessibilityNodeInfo) $r0).isChecked();
    }

    public static boolean isClickable(Object $r0) throws  {
        return ((AccessibilityNodeInfo) $r0).isClickable();
    }

    public static boolean isEnabled(Object $r0) throws  {
        return ((AccessibilityNodeInfo) $r0).isEnabled();
    }

    public static boolean isFocusable(Object $r0) throws  {
        return ((AccessibilityNodeInfo) $r0).isFocusable();
    }

    public static boolean isFocused(Object $r0) throws  {
        return ((AccessibilityNodeInfo) $r0).isFocused();
    }

    public static boolean isLongClickable(Object $r0) throws  {
        return ((AccessibilityNodeInfo) $r0).isLongClickable();
    }

    public static boolean isPassword(Object $r0) throws  {
        return ((AccessibilityNodeInfo) $r0).isPassword();
    }

    public static boolean isScrollable(Object $r0) throws  {
        return ((AccessibilityNodeInfo) $r0).isScrollable();
    }

    public static boolean isSelected(Object $r0) throws  {
        return ((AccessibilityNodeInfo) $r0).isSelected();
    }

    public static boolean performAction(Object $r0, int $i0) throws  {
        return ((AccessibilityNodeInfo) $r0).performAction($i0);
    }

    public static void setBoundsInParent(Object $r1, Rect $r0) throws  {
        ((AccessibilityNodeInfo) $r1).setBoundsInParent($r0);
    }

    public static void setBoundsInScreen(Object $r1, Rect $r0) throws  {
        ((AccessibilityNodeInfo) $r1).setBoundsInScreen($r0);
    }

    public static void setCheckable(Object $r0, boolean $z0) throws  {
        ((AccessibilityNodeInfo) $r0).setCheckable($z0);
    }

    public static void setChecked(Object $r0, boolean $z0) throws  {
        ((AccessibilityNodeInfo) $r0).setChecked($z0);
    }

    public static void setClassName(Object $r1, CharSequence $r0) throws  {
        ((AccessibilityNodeInfo) $r1).setClassName($r0);
    }

    public static void setClickable(Object $r0, boolean $z0) throws  {
        ((AccessibilityNodeInfo) $r0).setClickable($z0);
    }

    public static void setContentDescription(Object $r1, CharSequence $r0) throws  {
        ((AccessibilityNodeInfo) $r1).setContentDescription($r0);
    }

    public static void setEnabled(Object $r0, boolean $z0) throws  {
        ((AccessibilityNodeInfo) $r0).setEnabled($z0);
    }

    public static void setFocusable(Object $r0, boolean $z0) throws  {
        ((AccessibilityNodeInfo) $r0).setFocusable($z0);
    }

    public static void setFocused(Object $r0, boolean $z0) throws  {
        ((AccessibilityNodeInfo) $r0).setFocused($z0);
    }

    public static void setLongClickable(Object $r0, boolean $z0) throws  {
        ((AccessibilityNodeInfo) $r0).setLongClickable($z0);
    }

    public static void setPackageName(Object $r1, CharSequence $r0) throws  {
        ((AccessibilityNodeInfo) $r1).setPackageName($r0);
    }

    public static void setParent(Object $r1, View $r0) throws  {
        ((AccessibilityNodeInfo) $r1).setParent($r0);
    }

    public static void setPassword(Object $r0, boolean $z0) throws  {
        ((AccessibilityNodeInfo) $r0).setPassword($z0);
    }

    public static void setScrollable(Object $r0, boolean $z0) throws  {
        ((AccessibilityNodeInfo) $r0).setScrollable($z0);
    }

    public static void setSelected(Object $r0, boolean $z0) throws  {
        ((AccessibilityNodeInfo) $r0).setSelected($z0);
    }

    public static void setSource(Object $r1, View $r0) throws  {
        ((AccessibilityNodeInfo) $r1).setSource($r0);
    }

    public static void setText(Object $r1, CharSequence $r0) throws  {
        ((AccessibilityNodeInfo) $r1).setText($r0);
    }

    public static void recycle(Object $r0) throws  {
        ((AccessibilityNodeInfo) $r0).recycle();
    }
}
