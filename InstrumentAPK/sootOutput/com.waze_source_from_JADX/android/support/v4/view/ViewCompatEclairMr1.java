package android.support.v4.view;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class ViewCompatEclairMr1 {
    public static final String TAG = "ViewCompat";
    private static Method sChildrenDrawingOrderMethod;

    ViewCompatEclairMr1() throws  {
    }

    public static boolean isOpaque(View $r0) throws  {
        return $r0.isOpaque();
    }

    public static void setChildrenDrawingOrderEnabled(ViewGroup $r0, boolean $z0) throws  {
        if (sChildrenDrawingOrderMethod == null) {
            try {
                sChildrenDrawingOrderMethod = ViewGroup.class.getDeclaredMethod("setChildrenDrawingOrderEnabled", new Class[]{Boolean.TYPE});
            } catch (NoSuchMethodException $r7) {
                Log.e(TAG, "Unable to find childrenDrawingOrderEnabled", $r7);
            }
            sChildrenDrawingOrderMethod.setAccessible(true);
        }
        Method $r2 = sChildrenDrawingOrderMethod;
        Object[] $r5 = new Object[1];
        try {
            $r5[0] = Boolean.valueOf($z0);
            $r2.invoke($r0, $r5);
        } catch (IllegalAccessException $r8) {
            Log.e(TAG, "Unable to invoke childrenDrawingOrderEnabled", $r8);
        } catch (IllegalArgumentException $r9) {
            Log.e(TAG, "Unable to invoke childrenDrawingOrderEnabled", $r9);
        } catch (InvocationTargetException $r10) {
            Log.e(TAG, "Unable to invoke childrenDrawingOrderEnabled", $r10);
        }
    }
}
