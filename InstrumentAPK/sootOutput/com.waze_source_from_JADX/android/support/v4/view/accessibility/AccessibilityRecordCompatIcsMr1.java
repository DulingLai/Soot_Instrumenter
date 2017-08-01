package android.support.v4.view.accessibility;

import android.view.accessibility.AccessibilityRecord;

class AccessibilityRecordCompatIcsMr1 {
    AccessibilityRecordCompatIcsMr1() throws  {
    }

    public static int getMaxScrollX(Object $r0) throws  {
        return ((AccessibilityRecord) $r0).getMaxScrollX();
    }

    public static int getMaxScrollY(Object $r0) throws  {
        return ((AccessibilityRecord) $r0).getMaxScrollY();
    }

    public static void setMaxScrollX(Object $r0, int $i0) throws  {
        ((AccessibilityRecord) $r0).setMaxScrollX($i0);
    }

    public static void setMaxScrollY(Object $r0, int $i0) throws  {
        ((AccessibilityRecord) $r0).setMaxScrollY($i0);
    }
}
