package android.support.v4.widget;

import android.util.Log;
import android.widget.PopupWindow;
import java.lang.reflect.Field;

class PopupWindowCompatApi21 {
    private static final String TAG = "PopupWindowCompatApi21";
    private static Field sOverlapAnchorField = PopupWindow.class.getDeclaredField("mOverlapAnchor");

    PopupWindowCompatApi21() throws  {
    }

    static {
        try {
            sOverlapAnchorField.setAccessible(true);
        } catch (NoSuchFieldException $r0) {
            Log.i(TAG, "Could not fetch mOverlapAnchor field from PopupWindow", $r0);
        }
    }

    static void setOverlapAnchor(PopupWindow $r0, boolean $z0) throws  {
        if (sOverlapAnchorField != null) {
            try {
                sOverlapAnchorField.set($r0, Boolean.valueOf($z0));
            } catch (IllegalAccessException $r1) {
                Log.i(TAG, "Could not set overlap anchor field in PopupWindow", $r1);
            }
        }
    }

    static boolean getOverlapAnchor(PopupWindow $r0) throws  {
        if (sOverlapAnchorField != null) {
            try {
                return ((Boolean) sOverlapAnchorField.get($r0)).booleanValue();
            } catch (IllegalAccessException $r1) {
                Log.i(TAG, "Could not get overlap anchor field in PopupWindow", $r1);
            }
        }
        return false;
    }
}
