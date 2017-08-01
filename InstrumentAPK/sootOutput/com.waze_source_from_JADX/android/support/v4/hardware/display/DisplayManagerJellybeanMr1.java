package android.support.v4.hardware.display;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.view.Display;
import com.facebook.internal.ServerProtocol;

final class DisplayManagerJellybeanMr1 {
    DisplayManagerJellybeanMr1() throws  {
    }

    public static Object getDisplayManager(Context $r0) throws  {
        return $r0.getSystemService(ServerProtocol.DIALOG_PARAM_DISPLAY);
    }

    public static Display getDisplay(Object $r1, int $i0) throws  {
        return ((DisplayManager) $r1).getDisplay($i0);
    }

    public static Display[] getDisplays(Object $r1) throws  {
        return ((DisplayManager) $r1).getDisplays();
    }

    public static Display[] getDisplays(Object $r2, String $r0) throws  {
        return ((DisplayManager) $r2).getDisplays($r0);
    }
}
