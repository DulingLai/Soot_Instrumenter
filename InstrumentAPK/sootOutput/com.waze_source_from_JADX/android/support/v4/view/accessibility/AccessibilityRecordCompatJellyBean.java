package android.support.v4.view.accessibility;

import android.view.View;
import android.view.accessibility.AccessibilityRecord;

class AccessibilityRecordCompatJellyBean {
    AccessibilityRecordCompatJellyBean() throws  {
    }

    public static void setSource(Object $r1, View $r0, int $i0) throws  {
        ((AccessibilityRecord) $r1).setSource($r0, $i0);
    }
}
