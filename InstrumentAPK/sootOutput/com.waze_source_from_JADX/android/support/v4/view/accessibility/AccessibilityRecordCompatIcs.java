package android.support.v4.view.accessibility;

import android.os.Parcelable;
import android.view.View;
import android.view.accessibility.AccessibilityRecord;
import dalvik.annotation.Signature;
import java.util.List;

class AccessibilityRecordCompatIcs {
    AccessibilityRecordCompatIcs() throws  {
    }

    public static Object obtain() throws  {
        return AccessibilityRecord.obtain();
    }

    public static Object obtain(Object $r1) throws  {
        return AccessibilityRecord.obtain((AccessibilityRecord) $r1);
    }

    public static int getAddedCount(Object $r0) throws  {
        return ((AccessibilityRecord) $r0).getAddedCount();
    }

    public static CharSequence getBeforeText(Object $r1) throws  {
        return ((AccessibilityRecord) $r1).getBeforeText();
    }

    public static CharSequence getClassName(Object $r1) throws  {
        return ((AccessibilityRecord) $r1).getClassName();
    }

    public static CharSequence getContentDescription(Object $r1) throws  {
        return ((AccessibilityRecord) $r1).getContentDescription();
    }

    public static int getCurrentItemIndex(Object $r0) throws  {
        return ((AccessibilityRecord) $r0).getCurrentItemIndex();
    }

    public static int getFromIndex(Object $r0) throws  {
        return ((AccessibilityRecord) $r0).getFromIndex();
    }

    public static int getItemCount(Object $r0) throws  {
        return ((AccessibilityRecord) $r0).getItemCount();
    }

    public static Parcelable getParcelableData(Object $r1) throws  {
        return ((AccessibilityRecord) $r1).getParcelableData();
    }

    public static int getRemovedCount(Object $r0) throws  {
        return ((AccessibilityRecord) $r0).getRemovedCount();
    }

    public static int getScrollX(Object $r0) throws  {
        return ((AccessibilityRecord) $r0).getScrollX();
    }

    public static int getScrollY(Object $r0) throws  {
        return ((AccessibilityRecord) $r0).getScrollY();
    }

    public static Object getSource(Object $r1) throws  {
        return ((AccessibilityRecord) $r1).getSource();
    }

    public static List<CharSequence> getText(@Signature({"(", "Ljava/lang/Object;", ")", "Ljava/util/List", "<", "Ljava/lang/CharSequence;", ">;"}) Object $r1) throws  {
        return ((AccessibilityRecord) $r1).getText();
    }

    public static int getToIndex(Object $r0) throws  {
        return ((AccessibilityRecord) $r0).getToIndex();
    }

    public static int getWindowId(Object $r0) throws  {
        return ((AccessibilityRecord) $r0).getWindowId();
    }

    public static boolean isChecked(Object $r0) throws  {
        return ((AccessibilityRecord) $r0).isChecked();
    }

    public static boolean isEnabled(Object $r0) throws  {
        return ((AccessibilityRecord) $r0).isEnabled();
    }

    public static boolean isFullScreen(Object $r0) throws  {
        return ((AccessibilityRecord) $r0).isFullScreen();
    }

    public static boolean isPassword(Object $r0) throws  {
        return ((AccessibilityRecord) $r0).isPassword();
    }

    public static boolean isScrollable(Object $r0) throws  {
        return ((AccessibilityRecord) $r0).isScrollable();
    }

    public static void recycle(Object $r0) throws  {
        ((AccessibilityRecord) $r0).recycle();
    }

    public static void setAddedCount(Object $r0, int $i0) throws  {
        ((AccessibilityRecord) $r0).setAddedCount($i0);
    }

    public static void setBeforeText(Object $r1, CharSequence $r0) throws  {
        ((AccessibilityRecord) $r1).setBeforeText($r0);
    }

    public static void setChecked(Object $r0, boolean $z0) throws  {
        ((AccessibilityRecord) $r0).setChecked($z0);
    }

    public static void setClassName(Object $r1, CharSequence $r0) throws  {
        ((AccessibilityRecord) $r1).setClassName($r0);
    }

    public static void setContentDescription(Object $r1, CharSequence $r0) throws  {
        ((AccessibilityRecord) $r1).setContentDescription($r0);
    }

    public static void setCurrentItemIndex(Object $r0, int $i0) throws  {
        ((AccessibilityRecord) $r0).setCurrentItemIndex($i0);
    }

    public static void setEnabled(Object $r0, boolean $z0) throws  {
        ((AccessibilityRecord) $r0).setEnabled($z0);
    }

    public static void setFromIndex(Object $r0, int $i0) throws  {
        ((AccessibilityRecord) $r0).setFromIndex($i0);
    }

    public static void setFullScreen(Object $r0, boolean $z0) throws  {
        ((AccessibilityRecord) $r0).setFullScreen($z0);
    }

    public static void setItemCount(Object $r0, int $i0) throws  {
        ((AccessibilityRecord) $r0).setItemCount($i0);
    }

    public static void setParcelableData(Object $r1, Parcelable $r0) throws  {
        ((AccessibilityRecord) $r1).setParcelableData($r0);
    }

    public static void setPassword(Object $r0, boolean $z0) throws  {
        ((AccessibilityRecord) $r0).setPassword($z0);
    }

    public static void setRemovedCount(Object $r0, int $i0) throws  {
        ((AccessibilityRecord) $r0).setRemovedCount($i0);
    }

    public static void setScrollX(Object $r0, int $i0) throws  {
        ((AccessibilityRecord) $r0).setScrollX($i0);
    }

    public static void setScrollY(Object $r0, int $i0) throws  {
        ((AccessibilityRecord) $r0).setScrollY($i0);
    }

    public static void setScrollable(Object $r0, boolean $z0) throws  {
        ((AccessibilityRecord) $r0).setScrollable($z0);
    }

    public static void setSource(Object $r1, View $r0) throws  {
        ((AccessibilityRecord) $r1).setSource($r0);
    }

    public static void setToIndex(Object $r0, int $i0) throws  {
        ((AccessibilityRecord) $r0).setToIndex($i0);
    }
}
