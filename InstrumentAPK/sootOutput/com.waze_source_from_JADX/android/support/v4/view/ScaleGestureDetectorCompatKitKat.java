package android.support.v4.view;

import android.view.ScaleGestureDetector;

class ScaleGestureDetectorCompatKitKat {
    private ScaleGestureDetectorCompatKitKat() throws  {
    }

    public static void setQuickScaleEnabled(Object $r0, boolean $z0) throws  {
        ((ScaleGestureDetector) $r0).setQuickScaleEnabled($z0);
    }

    public static boolean isQuickScaleEnabled(Object $r0) throws  {
        return ((ScaleGestureDetector) $r0).isQuickScaleEnabled();
    }
}
