package android.support.v4.widget;

import android.os.Build.VERSION;
import android.view.View;
import android.widget.PopupWindow;

public final class PopupWindowCompat {
    static final PopupWindowImpl IMPL;

    interface PopupWindowImpl {
        boolean getOverlapAnchor(PopupWindow popupWindow) throws ;

        int getWindowLayoutType(PopupWindow popupWindow) throws ;

        void setOverlapAnchor(PopupWindow popupWindow, boolean z) throws ;

        void setWindowLayoutType(PopupWindow popupWindow, int i) throws ;

        void showAsDropDown(PopupWindow popupWindow, View view, int i, int i2, int i3) throws ;
    }

    static class BasePopupWindowImpl implements PopupWindowImpl {
        public boolean getOverlapAnchor(PopupWindow popupWindow) throws  {
            return false;
        }

        public int getWindowLayoutType(PopupWindow popupWindow) throws  {
            return 0;
        }

        BasePopupWindowImpl() throws  {
        }

        public void showAsDropDown(PopupWindow $r1, View $r2, int $i0, int $i1, int gravity) throws  {
            $r1.showAsDropDown($r2, $i0, $i1);
        }

        public void setOverlapAnchor(PopupWindow popupWindow, boolean overlapAnchor) throws  {
        }

        public void setWindowLayoutType(PopupWindow popupWindow, int layoutType) throws  {
        }
    }

    static class GingerbreadPopupWindowImpl extends BasePopupWindowImpl {
        GingerbreadPopupWindowImpl() throws  {
        }

        public void setWindowLayoutType(PopupWindow $r1, int $i0) throws  {
            PopupWindowCompatGingerbread.setWindowLayoutType($r1, $i0);
        }

        public int getWindowLayoutType(PopupWindow $r1) throws  {
            return PopupWindowCompatGingerbread.getWindowLayoutType($r1);
        }
    }

    static class KitKatPopupWindowImpl extends GingerbreadPopupWindowImpl {
        KitKatPopupWindowImpl() throws  {
        }

        public void showAsDropDown(PopupWindow $r1, View $r2, int $i0, int $i1, int $i2) throws  {
            PopupWindowCompatKitKat.showAsDropDown($r1, $r2, $i0, $i1, $i2);
        }
    }

    static class Api21PopupWindowImpl extends KitKatPopupWindowImpl {
        Api21PopupWindowImpl() throws  {
        }

        public void setOverlapAnchor(PopupWindow $r1, boolean $z0) throws  {
            PopupWindowCompatApi21.setOverlapAnchor($r1, $z0);
        }

        public boolean getOverlapAnchor(PopupWindow $r1) throws  {
            return PopupWindowCompatApi21.getOverlapAnchor($r1);
        }
    }

    static class Api23PopupWindowImpl extends Api21PopupWindowImpl {
        Api23PopupWindowImpl() throws  {
        }

        public void setOverlapAnchor(PopupWindow $r1, boolean $z0) throws  {
            PopupWindowCompatApi23.setOverlapAnchor($r1, $z0);
        }

        public boolean getOverlapAnchor(PopupWindow $r1) throws  {
            return PopupWindowCompatApi23.getOverlapAnchor($r1);
        }

        public void setWindowLayoutType(PopupWindow $r1, int $i0) throws  {
            PopupWindowCompatApi23.setWindowLayoutType($r1, $i0);
        }

        public int getWindowLayoutType(PopupWindow $r1) throws  {
            return PopupWindowCompatApi23.getWindowLayoutType($r1);
        }
    }

    static {
        int $i0 = VERSION.SDK_INT;
        if ($i0 >= 23) {
            IMPL = new Api23PopupWindowImpl();
        } else if ($i0 >= 21) {
            IMPL = new Api21PopupWindowImpl();
        } else if ($i0 >= 19) {
            IMPL = new KitKatPopupWindowImpl();
        } else if ($i0 >= 9) {
            IMPL = new GingerbreadPopupWindowImpl();
        } else {
            IMPL = new BasePopupWindowImpl();
        }
    }

    private PopupWindowCompat() throws  {
    }

    public static void showAsDropDown(PopupWindow $r0, View $r1, int $i0, int $i1, int $i2) throws  {
        IMPL.showAsDropDown($r0, $r1, $i0, $i1, $i2);
    }

    public static void setOverlapAnchor(PopupWindow $r0, boolean $z0) throws  {
        IMPL.setOverlapAnchor($r0, $z0);
    }

    public static boolean getOverlapAnchor(PopupWindow $r0) throws  {
        return IMPL.getOverlapAnchor($r0);
    }

    public static void setWindowLayoutType(PopupWindow $r0, int $i0) throws  {
        IMPL.setWindowLayoutType($r0, $i0);
    }

    public static int getWindowLayoutType(PopupWindow $r0) throws  {
        return IMPL.getWindowLayoutType($r0);
    }
}
