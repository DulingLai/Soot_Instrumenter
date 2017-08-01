package android.support.v4.os;

import android.os.CancellationSignal;

class CancellationSignalCompatJellybean {
    CancellationSignalCompatJellybean() throws  {
    }

    public static Object create() throws  {
        return new CancellationSignal();
    }

    public static void cancel(Object $r0) throws  {
        ((CancellationSignal) $r0).cancel();
    }
}
