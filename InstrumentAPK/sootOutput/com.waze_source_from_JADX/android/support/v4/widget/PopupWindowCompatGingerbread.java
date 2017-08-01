package android.support.v4.widget;

import android.widget.PopupWindow;
import java.lang.reflect.Method;

class PopupWindowCompatGingerbread {
    private static Method sGetWindowLayoutTypeMethod;
    private static boolean sGetWindowLayoutTypeMethodAttempted;
    private static Method sSetWindowLayoutTypeMethod;
    private static boolean sSetWindowLayoutTypeMethodAttempted;

    PopupWindowCompatGingerbread() throws  {
    }

    static void setWindowLayoutType(PopupWindow $r0, int $i0) throws  {
        if (!sSetWindowLayoutTypeMethodAttempted) {
            try {
                sSetWindowLayoutTypeMethod = PopupWindow.class.getDeclaredMethod("setWindowLayoutType", new Class[]{Integer.TYPE});
                sSetWindowLayoutTypeMethod.setAccessible(true);
            } catch (Exception e) {
            }
            sSetWindowLayoutTypeMethodAttempted = true;
        }
        if (sSetWindowLayoutTypeMethod != null) {
            try {
                sSetWindowLayoutTypeMethod.invoke($r0, new Object[]{Integer.valueOf($i0)});
            } catch (Exception e2) {
            }
        }
    }

    static int getWindowLayoutType(PopupWindow $r0) throws  {
        if (!sGetWindowLayoutTypeMethodAttempted) {
            try {
                sGetWindowLayoutTypeMethod = PopupWindow.class.getDeclaredMethod("getWindowLayoutType", new Class[0]);
                sGetWindowLayoutTypeMethod.setAccessible(true);
            } catch (Exception e) {
            }
            sGetWindowLayoutTypeMethodAttempted = true;
        }
        if (sGetWindowLayoutTypeMethod != null) {
            try {
                return ((Integer) sGetWindowLayoutTypeMethod.invoke($r0, new Object[0])).intValue();
            } catch (Exception e2) {
            }
        }
        return 0;
    }
}
