package android.support.v4.content;

import android.content.Context;
import java.io.File;

class ContextCompatFroyo {
    ContextCompatFroyo() throws  {
    }

    public static File getExternalCacheDir(Context $r0) throws  {
        return $r0.getExternalCacheDir();
    }

    public static File getExternalFilesDir(Context $r0, String $r1) throws  {
        return $r0.getExternalFilesDir($r1);
    }
}
