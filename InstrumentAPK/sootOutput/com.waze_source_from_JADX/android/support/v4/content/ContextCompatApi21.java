package android.support.v4.content;

import android.content.Context;
import android.graphics.drawable.Drawable;
import java.io.File;

class ContextCompatApi21 {
    ContextCompatApi21() throws  {
    }

    public static Drawable getDrawable(Context $r0, int $i0) throws  {
        return $r0.getDrawable($i0);
    }

    public static File getNoBackupFilesDir(Context $r0) throws  {
        return $r0.getNoBackupFilesDir();
    }

    public static File getCodeCacheDir(Context $r0) throws  {
        return $r0.getCodeCacheDir();
    }
}
