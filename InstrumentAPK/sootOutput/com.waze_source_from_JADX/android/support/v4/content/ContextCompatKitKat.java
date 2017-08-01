package android.support.v4.content;

import android.content.Context;
import java.io.File;

class ContextCompatKitKat {
    ContextCompatKitKat() throws  {
    }

    public static File[] getExternalCacheDirs(Context $r0) throws  {
        return $r0.getExternalCacheDirs();
    }

    public static File[] getExternalFilesDirs(Context $r0, String $r1) throws  {
        return $r0.getExternalFilesDirs($r1);
    }

    public static File[] getObbDirs(Context $r0) throws  {
        return $r0.getObbDirs();
    }
}
