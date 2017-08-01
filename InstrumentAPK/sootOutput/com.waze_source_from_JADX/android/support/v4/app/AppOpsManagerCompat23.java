package android.support.v4.app;

import android.app.AppOpsManager;
import android.content.Context;

class AppOpsManagerCompat23 {
    AppOpsManagerCompat23() throws  {
    }

    public static String permissionToOp(String $r0) throws  {
        return AppOpsManager.permissionToOp($r0);
    }

    public static int noteOp(Context $r0, String $r1, int $i0, String $r2) throws  {
        return ((AppOpsManager) $r0.getSystemService(AppOpsManager.class)).noteOp($r1, $i0, $r2);
    }

    public static int noteProxyOp(Context $r0, String $r1, String $r2) throws  {
        return ((AppOpsManager) $r0.getSystemService(AppOpsManager.class)).noteProxyOp($r1, $r2);
    }
}
