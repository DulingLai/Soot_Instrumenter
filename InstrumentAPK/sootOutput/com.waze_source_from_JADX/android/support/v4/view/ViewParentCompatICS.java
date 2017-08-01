package android.support.v4.view;

import android.view.View;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;

class ViewParentCompatICS {
    ViewParentCompatICS() throws  {
    }

    public static boolean requestSendAccessibilityEvent(ViewParent $r0, View $r1, AccessibilityEvent $r2) throws  {
        return $r0.requestSendAccessibilityEvent($r1, $r2);
    }
}
