package android.support.v4.widget;

import android.view.View.OnTouchListener;
import android.widget.PopupMenu;

class PopupMenuCompatKitKat {
    PopupMenuCompatKitKat() throws  {
    }

    public static OnTouchListener getDragToOpenListener(Object $r1) throws  {
        return ((PopupMenu) $r1).getDragToOpenListener();
    }
}
