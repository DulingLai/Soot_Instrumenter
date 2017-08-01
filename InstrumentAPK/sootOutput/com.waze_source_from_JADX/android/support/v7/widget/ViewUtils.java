package android.support.v7.widget;

import android.graphics.Rect;
import android.os.Build.VERSION;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.View;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ViewUtils {
    private static final String TAG = "ViewUtils";
    private static Method sComputeFitSystemWindowsMethod = View.class.getDeclaredMethod("computeFitSystemWindows", new Class[]{Rect.class, Rect.class});

    static {
        if (VERSION.SDK_INT >= 18) {
            try {
                if (!sComputeFitSystemWindowsMethod.isAccessible()) {
                    sComputeFitSystemWindowsMethod.setAccessible(true);
                }
            } catch (NoSuchMethodException e) {
                Log.d(TAG, "Could not find method computeFitSystemWindows. Oh well.");
            }
        }
    }

    private ViewUtils() throws  {
    }

    public static boolean isLayoutRtl(View $r0) throws  {
        return ViewCompat.getLayoutDirection($r0) == 1;
    }

    public static int combineMeasuredStates(int $i0, int $i1) throws  {
        return $i0 | $i1;
    }

    public static void computeFitSystemWindows(View $r0, Rect $r1, Rect $r2) throws  {
        if (sComputeFitSystemWindowsMethod != null) {
            try {
                sComputeFitSystemWindowsMethod.invoke($r0, new Object[]{$r1, $r2});
            } catch (Exception $r3) {
                Log.d(TAG, "Could not invoke computeFitSystemWindows", $r3);
            }
        }
    }

    public static void makeOptionalFitsSystemWindows(View $r0) throws  {
        if (VERSION.SDK_INT >= 16) {
            try {
                Method $r3 = $r0.getClass().getMethod("makeOptionalFitsSystemWindows", new Class[0]);
                if (!$r3.isAccessible()) {
                    $r3.setAccessible(true);
                }
                $r3.invoke($r0, new Object[0]);
            } catch (NoSuchMethodException e) {
                Log.d(TAG, "Could not find method makeOptionalFitsSystemWindows. Oh well...");
            } catch (InvocationTargetException $r6) {
                Log.d(TAG, "Could not invoke makeOptionalFitsSystemWindows", $r6);
            } catch (IllegalAccessException $r7) {
                Log.d(TAG, "Could not invoke makeOptionalFitsSystemWindows", $r7);
            }
        }
    }
}
