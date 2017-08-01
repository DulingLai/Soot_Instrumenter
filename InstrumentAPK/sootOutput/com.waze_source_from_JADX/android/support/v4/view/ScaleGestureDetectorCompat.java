package android.support.v4.view;

import android.os.Build.VERSION;

public final class ScaleGestureDetectorCompat {
    static final ScaleGestureDetectorImpl IMPL;

    interface ScaleGestureDetectorImpl {
        boolean isQuickScaleEnabled(Object obj) throws ;

        void setQuickScaleEnabled(Object obj, boolean z) throws ;
    }

    private static class BaseScaleGestureDetectorImpl implements ScaleGestureDetectorImpl {
        public boolean isQuickScaleEnabled(Object o) throws  {
            return false;
        }

        private BaseScaleGestureDetectorImpl() throws  {
        }

        public void setQuickScaleEnabled(Object o, boolean enabled) throws  {
        }
    }

    private static class ScaleGestureDetectorCompatKitKatImpl implements ScaleGestureDetectorImpl {
        private ScaleGestureDetectorCompatKitKatImpl() throws  {
        }

        public void setQuickScaleEnabled(Object $r1, boolean $z0) throws  {
            ScaleGestureDetectorCompatKitKat.setQuickScaleEnabled($r1, $z0);
        }

        public boolean isQuickScaleEnabled(Object $r1) throws  {
            return ScaleGestureDetectorCompatKitKat.isQuickScaleEnabled($r1);
        }
    }

    static {
        if (VERSION.SDK_INT >= 19) {
            IMPL = new ScaleGestureDetectorCompatKitKatImpl();
        } else {
            IMPL = new BaseScaleGestureDetectorImpl();
        }
    }

    private ScaleGestureDetectorCompat() throws  {
    }

    public static void setQuickScaleEnabled(Object $r0, boolean $z0) throws  {
        IMPL.setQuickScaleEnabled($r0, $z0);
    }

    public static boolean isQuickScaleEnabled(Object $r0) throws  {
        return IMPL.isQuickScaleEnabled($r0);
    }
}
