package android.support.v4.view;

import android.os.Build.VERSION;
import android.view.ViewConfiguration;

public final class ViewConfigurationCompat {
    static final ViewConfigurationVersionImpl IMPL;

    interface ViewConfigurationVersionImpl {
        int getScaledPagingTouchSlop(ViewConfiguration viewConfiguration) throws ;

        boolean hasPermanentMenuKey(ViewConfiguration viewConfiguration) throws ;
    }

    static class BaseViewConfigurationVersionImpl implements ViewConfigurationVersionImpl {
        public boolean hasPermanentMenuKey(ViewConfiguration config) throws  {
            return true;
        }

        BaseViewConfigurationVersionImpl() throws  {
        }

        public int getScaledPagingTouchSlop(ViewConfiguration $r1) throws  {
            return $r1.getScaledTouchSlop();
        }
    }

    static class FroyoViewConfigurationVersionImpl extends BaseViewConfigurationVersionImpl {
        FroyoViewConfigurationVersionImpl() throws  {
        }

        public int getScaledPagingTouchSlop(ViewConfiguration $r1) throws  {
            return ViewConfigurationCompatFroyo.getScaledPagingTouchSlop($r1);
        }
    }

    static class HoneycombViewConfigurationVersionImpl extends FroyoViewConfigurationVersionImpl {
        public boolean hasPermanentMenuKey(ViewConfiguration config) throws  {
            return false;
        }

        HoneycombViewConfigurationVersionImpl() throws  {
        }
    }

    static class IcsViewConfigurationVersionImpl extends HoneycombViewConfigurationVersionImpl {
        IcsViewConfigurationVersionImpl() throws  {
        }

        public boolean hasPermanentMenuKey(ViewConfiguration $r1) throws  {
            return ViewConfigurationCompatICS.hasPermanentMenuKey($r1);
        }
    }

    static {
        if (VERSION.SDK_INT >= 14) {
            IMPL = new IcsViewConfigurationVersionImpl();
        } else if (VERSION.SDK_INT >= 11) {
            IMPL = new HoneycombViewConfigurationVersionImpl();
        } else if (VERSION.SDK_INT >= 8) {
            IMPL = new FroyoViewConfigurationVersionImpl();
        } else {
            IMPL = new BaseViewConfigurationVersionImpl();
        }
    }

    public static int getScaledPagingTouchSlop(ViewConfiguration $r0) throws  {
        return IMPL.getScaledPagingTouchSlop($r0);
    }

    public static boolean hasPermanentMenuKey(ViewConfiguration $r0) throws  {
        return IMPL.hasPermanentMenuKey($r0);
    }

    private ViewConfigurationCompat() throws  {
    }
}
