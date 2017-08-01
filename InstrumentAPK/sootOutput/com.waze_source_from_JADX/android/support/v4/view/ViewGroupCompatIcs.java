package android.support.v4.view;

import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;

class ViewGroupCompatIcs {
    ViewGroupCompatIcs() throws  {
    }

    public static boolean onRequestSendAccessibilityEvent(ViewGroup $r0, View $r1, AccessibilityEvent $r2) throws  {
        return $r0.onRequestSendAccessibilityEvent($r1, $r2);
    }
}
