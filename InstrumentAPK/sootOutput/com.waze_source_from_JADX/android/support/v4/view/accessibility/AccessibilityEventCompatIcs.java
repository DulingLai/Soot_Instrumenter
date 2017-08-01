package android.support.v4.view.accessibility;

import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityRecord;

class AccessibilityEventCompatIcs {
    AccessibilityEventCompatIcs() throws  {
    }

    public static int getRecordCount(AccessibilityEvent $r0) throws  {
        return $r0.getRecordCount();
    }

    public static void appendRecord(AccessibilityEvent $r0, Object $r1) throws  {
        $r0.appendRecord((AccessibilityRecord) $r1);
    }

    public static Object getRecord(AccessibilityEvent $r0, int $i0) throws  {
        return $r0.getRecord($i0);
    }

    public static void setScrollable(AccessibilityEvent $r0, boolean $z0) throws  {
        $r0.setScrollable($z0);
    }
}
