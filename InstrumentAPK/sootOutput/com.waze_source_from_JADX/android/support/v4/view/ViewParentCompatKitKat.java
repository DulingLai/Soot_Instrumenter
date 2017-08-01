package android.support.v4.view;

import android.view.View;
import android.view.ViewParent;

class ViewParentCompatKitKat {
    ViewParentCompatKitKat() throws  {
    }

    public static void notifySubtreeAccessibilityStateChanged(ViewParent $r0, View $r1, View $r2, int $i0) throws  {
        $r0.notifySubtreeAccessibilityStateChanged($r1, $r2, $i0);
    }
}
