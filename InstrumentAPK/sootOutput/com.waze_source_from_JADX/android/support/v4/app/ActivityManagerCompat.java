package android.support.v4.app;

import android.app.ActivityManager;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;

public final class ActivityManagerCompat {
    private ActivityManagerCompat() throws  {
    }

    public static boolean isLowRamDevice(@NonNull ActivityManager $r0) throws  {
        if (VERSION.SDK_INT >= 19) {
            return ActivityManagerCompatKitKat.isLowRamDevice($r0);
        }
        return false;
    }
}
