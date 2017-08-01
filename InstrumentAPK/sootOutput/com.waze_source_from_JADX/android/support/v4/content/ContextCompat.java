package android.support.v4.content;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.os.Process;
import android.support.annotation.NonNull;
import android.util.Log;
import com.waze.ResManager;
import java.io.File;

public class ContextCompat {
    private static final String DIR_ANDROID = "Android";
    private static final String DIR_CACHE = "cache";
    private static final String DIR_DATA = "data";
    private static final String DIR_FILES = "files";
    private static final String DIR_OBB = "obb";
    private static final String TAG = "ContextCompat";

    public static boolean startActivities(Context $r0, Intent[] $r1) throws  {
        return startActivities($r0, $r1, null);
    }

    public static boolean startActivities(Context $r0, Intent[] $r1, Bundle $r2) throws  {
        int $i0 = VERSION.SDK_INT;
        if ($i0 >= 16) {
            ContextCompatJellybean.startActivities($r0, $r1, $r2);
            return true;
        } else if ($i0 < 11) {
            return false;
        } else {
            ContextCompatHoneycomb.startActivities($r0, $r1);
            return true;
        }
    }

    public static File[] getObbDirs(Context $r0) throws  {
        int $i0 = VERSION.SDK_INT;
        if ($i0 >= 19) {
            return ContextCompatKitKat.getObbDirs($r0);
        }
        File $r2;
        if ($i0 >= 11) {
            $r2 = ContextCompatHoneycomb.getObbDir($r0);
        } else {
            $r2 = buildPath(Environment.getExternalStorageDirectory(), DIR_ANDROID, DIR_OBB, $r0.getPackageName());
        }
        return new File[]{$r2};
    }

    public static File[] getExternalFilesDirs(Context $r0, String $r1) throws  {
        int $i0 = VERSION.SDK_INT;
        if ($i0 >= 19) {
            return ContextCompatKitKat.getExternalFilesDirs($r0, $r1);
        }
        File $r3;
        if ($i0 >= 8) {
            $r3 = ContextCompatFroyo.getExternalFilesDir($r0, $r1);
        } else {
            $r3 = buildPath(Environment.getExternalStorageDirectory(), DIR_ANDROID, "data", $r0.getPackageName(), DIR_FILES, $r1);
        }
        return new File[]{$r3};
    }

    public static File[] getExternalCacheDirs(Context $r0) throws  {
        int $i0 = VERSION.SDK_INT;
        if ($i0 >= 19) {
            return ContextCompatKitKat.getExternalCacheDirs($r0);
        }
        File $r2;
        if ($i0 >= 8) {
            $r2 = ContextCompatFroyo.getExternalCacheDir($r0);
        } else {
            $r2 = buildPath(Environment.getExternalStorageDirectory(), DIR_ANDROID, "data", $r0.getPackageName(), "cache");
        }
        return new File[]{$r2};
    }

    private static File buildPath(File $r0, String... $r1) throws  {
        int $i0 = $r1.length;
        int $i1 = 0;
        while ($i1 < $i0) {
            File $r3;
            String $r2 = $r1[$i1];
            if ($r0 == null) {
                $r3 = new File($r2);
            } else if ($r2 != null) {
                $r3 = new File($r0, $r2);
            } else {
                $r3 = $r0;
            }
            $i1++;
            $r0 = $r3;
        }
        return $r0;
    }

    public static final Drawable getDrawable(Context $r0, int $i0) throws  {
        if (VERSION.SDK_INT >= 21) {
            return ContextCompatApi21.getDrawable($r0, $i0);
        }
        return $r0.getResources().getDrawable($i0);
    }

    public static final ColorStateList getColorStateList(Context $r0, int $i0) throws  {
        if (VERSION.SDK_INT >= 23) {
            return ContextCompatApi23.getColorStateList($r0, $i0);
        }
        return $r0.getResources().getColorStateList($i0);
    }

    public static final int getColor(Context $r0, int $i0) throws  {
        if (VERSION.SDK_INT >= 23) {
            return ContextCompatApi23.getColor($r0, $i0);
        }
        return $r0.getResources().getColor($i0);
    }

    public static int checkSelfPermission(@NonNull Context $r0, @NonNull String $r1) throws  {
        if ($r1 != null) {
            return $r0.checkPermission($r1, Process.myPid(), Process.myUid());
        }
        throw new IllegalArgumentException("permission is null");
    }

    public final File getNoBackupFilesDir(Context $r1) throws  {
        if (VERSION.SDK_INT >= 21) {
            return ContextCompatApi21.getNoBackupFilesDir($r1);
        }
        return createFilesDir(new File($r1.getApplicationInfo().dataDir, "no_backup"));
    }

    public static File getCodeCacheDir(Context $r0) throws  {
        if (VERSION.SDK_INT >= 21) {
            return ContextCompatApi21.getCodeCacheDir($r0);
        }
        return createFilesDir(new File($r0.getApplicationInfo().dataDir, ResManager.mPkgCodeCacheDir));
    }

    private static synchronized File createFilesDir(File $r0) throws  {
        synchronized (ContextCompat.class) {
            try {
                if (!($r0.exists() || $r0.mkdirs() || $r0.exists())) {
                    Log.w(TAG, "Unable to create files subdir " + $r0.getPath());
                    $r0 = null;
                }
            } catch (Throwable th) {
                Class cls = ContextCompat.class;
            }
        }
        return $r0;
    }
}
