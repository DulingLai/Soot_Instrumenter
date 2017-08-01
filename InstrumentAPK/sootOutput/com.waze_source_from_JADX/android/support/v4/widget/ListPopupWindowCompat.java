package android.support.v4.widget;

import android.os.Build.VERSION;
import android.view.View;
import android.view.View.OnTouchListener;

public final class ListPopupWindowCompat {
    static final ListPopupWindowImpl IMPL;

    interface ListPopupWindowImpl {
        OnTouchListener createDragToOpenListener(Object obj, View view) throws ;
    }

    static class BaseListPopupWindowImpl implements ListPopupWindowImpl {
        public OnTouchListener createDragToOpenListener(Object listPopupWindow, View src) throws  {
            return null;
        }

        BaseListPopupWindowImpl() throws  {
        }
    }

    static class KitKatListPopupWindowImpl extends BaseListPopupWindowImpl {
        KitKatListPopupWindowImpl() throws  {
        }

        public OnTouchListener createDragToOpenListener(Object $r1, View $r2) throws  {
            return ListPopupWindowCompatKitKat.createDragToOpenListener($r1, $r2);
        }
    }

    static {
        if (VERSION.SDK_INT >= 19) {
            IMPL = new KitKatListPopupWindowImpl();
        } else {
            IMPL = new BaseListPopupWindowImpl();
        }
    }

    private ListPopupWindowCompat() throws  {
    }

    public static OnTouchListener createDragToOpenListener(Object $r0, View $r1) throws  {
        return IMPL.createDragToOpenListener($r0, $r1);
    }
}
