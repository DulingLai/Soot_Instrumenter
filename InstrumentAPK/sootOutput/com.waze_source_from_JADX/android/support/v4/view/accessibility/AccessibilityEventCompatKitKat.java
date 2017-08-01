package android.support.v4.view.accessibility;

import android.view.accessibility.AccessibilityEvent;

class AccessibilityEventCompatKitKat {
    AccessibilityEventCompatKitKat() throws  {
    }

    public static void setContentChangeTypes(AccessibilityEvent $r0, int $i0) throws  {
        $r0.setContentChangeTypes($i0);
    }

    public static int getContentChangeTypes(AccessibilityEvent $r0) throws  {
        return $r0.getContentChangeTypes();
    }
}
