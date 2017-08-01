package android.support.v4.app;

import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.IBinder;

public final class BundleCompat {
    private BundleCompat() throws  {
    }

    public static IBinder getBinder(Bundle $r0, String $r1) throws  {
        if (VERSION.SDK_INT >= 18) {
            return BundleCompatJellybeanMR2.getBinder($r0, $r1);
        }
        return BundleCompatDonut.getBinder($r0, $r1);
    }

    public static void putBinder(Bundle $r0, String $r1, IBinder $r2) throws  {
        if (VERSION.SDK_INT >= 18) {
            BundleCompatJellybeanMR2.putBinder($r0, $r1, $r2);
        } else {
            BundleCompatDonut.putBinder($r0, $r1, $r2);
        }
    }
}
