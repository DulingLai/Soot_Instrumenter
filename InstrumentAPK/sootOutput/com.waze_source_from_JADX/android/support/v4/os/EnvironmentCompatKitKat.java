package android.support.v4.os;

import android.os.Environment;
import java.io.File;

class EnvironmentCompatKitKat {
    EnvironmentCompatKitKat() throws  {
    }

    public static String getStorageState(File $r0) throws  {
        return Environment.getStorageState($r0);
    }
}
