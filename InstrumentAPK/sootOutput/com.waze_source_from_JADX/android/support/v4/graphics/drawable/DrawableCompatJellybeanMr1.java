package android.support.v4.graphics.drawable;

import android.graphics.drawable.Drawable;
import android.util.Log;
import java.lang.reflect.Method;

class DrawableCompatJellybeanMr1 {
    private static final String TAG = "DrawableCompatJellybeanMr1";
    private static Method sGetLayoutDirectionMethod;
    private static boolean sGetLayoutDirectionMethodFetched;
    private static Method sSetLayoutDirectionMethod;
    private static boolean sSetLayoutDirectionMethodFetched;

    DrawableCompatJellybeanMr1() throws  {
    }

    public static void setLayoutDirection(Drawable $r0, int $i0) throws  {
        if (!sSetLayoutDirectionMethodFetched) {
            try {
                sSetLayoutDirectionMethod = Drawable.class.getDeclaredMethod("setLayoutDirection", new Class[]{Integer.TYPE});
                sSetLayoutDirectionMethod.setAccessible(true);
            } catch (NoSuchMethodException $r7) {
                Log.i(TAG, "Failed to retrieve setLayoutDirection(int) method", $r7);
            }
            sSetLayoutDirectionMethodFetched = true;
        }
        if (sSetLayoutDirectionMethod != null) {
            try {
                sSetLayoutDirectionMethod.invoke($r0, new Object[]{Integer.valueOf($i0)});
            } catch (Exception $r8) {
                Log.i(TAG, "Failed to invoke setLayoutDirection(int) via reflection", $r8);
                sSetLayoutDirectionMethod = null;
            }
        }
    }

    public static int getLayoutDirection(Drawable $r0) throws  {
        if (!sGetLayoutDirectionMethodFetched) {
            try {
                sGetLayoutDirectionMethod = Drawable.class.getDeclaredMethod("getLayoutDirection", new Class[0]);
                sGetLayoutDirectionMethod.setAccessible(true);
            } catch (NoSuchMethodException $r7) {
                Log.i(TAG, "Failed to retrieve getLayoutDirection() method", $r7);
            }
            sGetLayoutDirectionMethodFetched = true;
        }
        if (sGetLayoutDirectionMethod != null) {
            try {
                return ((Integer) sGetLayoutDirectionMethod.invoke($r0, new Object[0])).intValue();
            } catch (Exception $r8) {
                Log.i(TAG, "Failed to invoke getLayoutDirection() via reflection", $r8);
                sGetLayoutDirectionMethod = null;
            }
        }
        return -1;
    }
}
