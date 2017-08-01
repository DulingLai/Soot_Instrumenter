package android.support.v4.widget;

import android.os.Build.VERSION;
import android.view.View.OnTouchListener;

public final class PopupMenuCompat {
    static final PopupMenuImpl IMPL;

    interface PopupMenuImpl {
        OnTouchListener getDragToOpenListener(Object obj) throws ;
    }

    static class BasePopupMenuImpl implements PopupMenuImpl {
        public OnTouchListener getDragToOpenListener(Object popupMenu) throws  {
            return null;
        }

        BasePopupMenuImpl() throws  {
        }
    }

    static class KitKatPopupMenuImpl extends BasePopupMenuImpl {
        KitKatPopupMenuImpl() throws  {
        }

        public OnTouchListener getDragToOpenListener(Object $r1) throws  {
            return PopupMenuCompatKitKat.getDragToOpenListener($r1);
        }
    }

    static {
        if (VERSION.SDK_INT >= 19) {
            IMPL = new KitKatPopupMenuImpl();
        } else {
            IMPL = new BasePopupMenuImpl();
        }
    }

    private PopupMenuCompat() throws  {
    }

    public static OnTouchListener getDragToOpenListener(Object $r0) throws  {
        return IMPL.getDragToOpenListener($r0);
    }
}
