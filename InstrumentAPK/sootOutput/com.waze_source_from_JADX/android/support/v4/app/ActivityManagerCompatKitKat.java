package android.support.v4.app;

import android.app.ActivityManager;

class ActivityManagerCompatKitKat {
    ActivityManagerCompatKitKat() throws  {
    }

    public static boolean isLowRamDevice(ActivityManager $r0) throws  {
        return $r0.isLowRamDevice();
    }
}
