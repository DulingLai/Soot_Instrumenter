package android.support.v4.app;

import android.os.Bundle;
import android.os.IBinder;

class BundleCompatJellybeanMR2 {
    BundleCompatJellybeanMR2() throws  {
    }

    public static IBinder getBinder(Bundle $r0, String $r1) throws  {
        return $r0.getBinder($r1);
    }

    public static void putBinder(Bundle $r0, String $r1, IBinder $r2) throws  {
        $r0.putBinder($r1, $r2);
    }
}
