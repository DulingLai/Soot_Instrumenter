package android.support.v4.os;

import android.os.Build.VERSION;

public final class TraceCompat {
    public static void beginSection(String $r0) throws  {
        if (VERSION.SDK_INT >= 18) {
            TraceJellybeanMR2.beginSection($r0);
        }
    }

    public static void endSection() throws  {
        if (VERSION.SDK_INT >= 18) {
            TraceJellybeanMR2.endSection();
        }
    }

    private TraceCompat() throws  {
    }
}
