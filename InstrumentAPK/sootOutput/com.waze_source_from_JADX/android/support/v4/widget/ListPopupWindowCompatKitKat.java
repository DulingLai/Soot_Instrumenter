package android.support.v4.widget;

import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ListPopupWindow;

class ListPopupWindowCompatKitKat {
    ListPopupWindowCompatKitKat() throws  {
    }

    public static OnTouchListener createDragToOpenListener(Object $r2, View $r0) throws  {
        return ((ListPopupWindow) $r2).createDragToOpenListener($r0);
    }
}
