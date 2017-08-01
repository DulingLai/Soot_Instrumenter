package android.support.v4.os;

import android.os.Build.VERSION;
import android.os.Environment;
import android.util.Log;
import java.io.File;
import java.io.IOException;

public final class EnvironmentCompat {
    public static final String MEDIA_UNKNOWN = "unknown";
    private static final String TAG = "EnvironmentCompat";

    public static String getStorageState(File $r0) throws  {
        if (VERSION.SDK_INT >= 19) {
            return EnvironmentCompatKitKat.getStorageState($r0);
        }
        try {
            if ($r0.getCanonicalPath().startsWith(Environment.getExternalStorageDirectory().getCanonicalPath())) {
                return Environment.getExternalStorageState();
            }
        } catch (IOException $r1) {
            Log.w(TAG, "Failed to resolve canonical path: " + $r1);
        }
        return "unknown";
    }

    private EnvironmentCompat() throws  {
    }
}
