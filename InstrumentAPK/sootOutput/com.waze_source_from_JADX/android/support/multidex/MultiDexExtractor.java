package android.support.multidex;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.os.Build.VERSION;
import android.util.Log;
import dalvik.annotation.Signature;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

final class MultiDexExtractor {
    private static final int BUFFER_SIZE = 16384;
    private static final String DEX_PREFIX = "classes";
    private static final String DEX_SUFFIX = ".dex";
    private static final String EXTRACTED_NAME_EXT = ".classes";
    private static final String EXTRACTED_SUFFIX = ".zip";
    private static final String KEY_CRC = "crc";
    private static final String KEY_DEX_NUMBER = "dex.number";
    private static final String KEY_TIME_STAMP = "timestamp";
    private static final int MAX_EXTRACT_ATTEMPTS = 3;
    private static final long NO_VALUE = -1;
    private static final String PREFS_FILE = "multidex.version";
    private static final String TAG = "MultiDex";
    private static Method sApplyMethod;

    MultiDexExtractor() throws  {
    }

    static List<File> load(@Signature({"(", "Landroid/content/Context;", "Landroid/content/pm/ApplicationInfo;", "Ljava/io/File;", "Z)", "Ljava/util/List", "<", "Ljava/io/File;", ">;"}) Context $r0, @Signature({"(", "Landroid/content/Context;", "Landroid/content/pm/ApplicationInfo;", "Ljava/io/File;", "Z)", "Ljava/util/List", "<", "Ljava/io/File;", ">;"}) ApplicationInfo $r1, @Signature({"(", "Landroid/content/Context;", "Landroid/content/pm/ApplicationInfo;", "Ljava/io/File;", "Z)", "Ljava/util/List", "<", "Ljava/io/File;", ">;"}) File $r2, @Signature({"(", "Landroid/content/Context;", "Landroid/content/pm/ApplicationInfo;", "Ljava/io/File;", "Z)", "Ljava/util/List", "<", "Ljava/io/File;", ">;"}) boolean $z0) throws IOException {
        List $r7;
        Log.i(TAG, "MultiDexExtractor.load(" + $r1.sourceDir + ", " + $z0 + ")");
        File $r4 = new File($r1.sourceDir);
        long $l0 = getZipCrc($r4);
        List $r8;
        if ($z0 || isModified($r0, $r4, $l0)) {
            Log.i(TAG, "Detected that extraction must be performed.");
            $r8 = performExtractions($r4, $r2);
            $r7 = $r8;
            putStoredApkInfo($r0, getTimeStamp($r4), $l0, $r8.size() + 1);
        } else {
            try {
                $r7 = loadExistingExtractions($r0, $r4, $r2);
            } catch (IOException $r3) {
                Log.w(TAG, "Failed to reload existing extracted secondary dex files, falling back to fresh extraction", $r3);
                $r8 = performExtractions($r4, $r2);
                $r7 = $r8;
                putStoredApkInfo($r0, getTimeStamp($r4), $l0, $r8.size() + 1);
            }
        }
        Log.i(TAG, "load found " + $r7.size() + " secondary dex files");
        return $r7;
    }

    private static List<File> loadExistingExtractions(@Signature({"(", "Landroid/content/Context;", "Ljava/io/File;", "Ljava/io/File;", ")", "Ljava/util/List", "<", "Ljava/io/File;", ">;"}) Context $r0, @Signature({"(", "Landroid/content/Context;", "Ljava/io/File;", "Ljava/io/File;", ")", "Ljava/util/List", "<", "Ljava/io/File;", ">;"}) File $r1, @Signature({"(", "Landroid/content/Context;", "Ljava/io/File;", "Ljava/io/File;", ")", "Ljava/util/List", "<", "Ljava/io/File;", ">;"}) File $r2) throws IOException {
        Log.i(TAG, "loading existing secondary dex files");
        String $r5 = $r1.getName() + EXTRACTED_NAME_EXT;
        int $i0 = getMultiDexPreferences($r0).getInt(KEY_DEX_NUMBER, 1);
        ArrayList $r3 = new ArrayList($i0);
        int $i1 = 2;
        while ($i1 <= $i0) {
            $r1 = new File($r2, $r5 + $i1 + EXTRACTED_SUFFIX);
            if ($r1.isFile()) {
                $r3.add($r1);
                if (verifyZipFile($r1)) {
                    $i1++;
                } else {
                    Log.i(TAG, "Invalid zip file: " + $r1);
                    throw new IOException("Invalid ZIP file.");
                }
            }
            throw new IOException("Missing extracted secondary dex file '" + $r1.getPath() + "'");
        }
        return $r3;
    }

    private static boolean isModified(Context $r0, File $r1, long $l0) throws  {
        SharedPreferences $r2 = getMultiDexPreferences($r0);
        return ($r2.getLong(KEY_TIME_STAMP, -1) == getTimeStamp($r1) && $r2.getLong(KEY_CRC, -1) == $l0) ? false : true;
    }

    private static long getTimeStamp(File $r0) throws  {
        long $l0 = $r0.lastModified();
        if ($l0 == -1) {
            return $l0 - 1;
        }
        return $l0;
    }

    private static long getZipCrc(File $r0) throws IOException {
        long $l0 = ZipUtil.getZipCrc($r0);
        if ($l0 == -1) {
            return $l0 - 1;
        }
        return $l0;
    }

    private static List<File> performExtractions(@Signature({"(", "Ljava/io/File;", "Ljava/io/File;", ")", "Ljava/util/List", "<", "Ljava/io/File;", ">;"}) File $r0, @Signature({"(", "Ljava/io/File;", "Ljava/io/File;", ")", "Ljava/util/List", "<", "Ljava/io/File;", ">;"}) File $r1) throws IOException {
        String $r5 = $r0.getName() + EXTRACTED_NAME_EXT;
        prepareDexDir($r1, $r5);
        ArrayList $r3 = new ArrayList();
        ZipFile $r2 = new ZipFile($r0);
        int $i0 = 2;
        ZipEntry $r7 = $r2.getEntry(DEX_PREFIX + 2 + DEX_SUFFIX);
        while ($r7 != null) {
            File file = new File($r1, $r5 + $i0 + EXTRACTED_SUFFIX);
            $r3.add(file);
            Log.i(TAG, "Extraction is needed for file " + file);
            int $i1 = 0;
            boolean $z0 = false;
            while ($i1 < 3 && !$z0) {
                try {
                    $i1++;
                    extract($r2, $r7, file, $r5);
                    boolean $z1 = verifyZipFile(file);
                    $z0 = $z1;
                    Log.i(TAG, "Extraction " + ($z1 ? "success" : "failed") + " - length " + file.getAbsolutePath() + ": " + file.length());
                    if (!$z1) {
                        file.delete();
                        if (file.exists()) {
                            Log.w(TAG, "Failed to delete corrupted secondary dex '" + file.getPath() + "'");
                        }
                    }
                } finally {
                    try {
                        $r2.close();
                    } catch (Throwable $r10) {
                        Log.w(TAG, "Failed to close resource", $r10);
                    }
                }
            }
            if ($z0) {
                $i0++;
                $r7 = $r2.getEntry(DEX_PREFIX + $i0 + DEX_SUFFIX);
            } else {
                throw new IOException("Could not create zip file " + file.getAbsolutePath() + " for secondary dex (" + $i0 + ")");
            }
        }
        return $r3;
    }

    private static void putStoredApkInfo(Context $r0, long $l0, long $l1, int $i2) throws  {
        Editor $r2 = getMultiDexPreferences($r0).edit();
        $r2.putLong(KEY_TIME_STAMP, $l0);
        $r2.putLong(KEY_CRC, $l1);
        $r2.putInt(KEY_DEX_NUMBER, $i2);
        apply($r2);
    }

    private static SharedPreferences getMultiDexPreferences(Context $r0) throws  {
        return $r0.getSharedPreferences(PREFS_FILE, VERSION.SDK_INT < 11 ? (byte) 0 : (byte) 4);
    }

    private static void prepareDexDir(File $r0, final String $r1) throws IOException {
        mkdirChecked($r0.getParentFile());
        mkdirChecked($r0);
        File[] $r4 = $r0.listFiles(new FileFilter() {
            public boolean accept(File $r1) throws  {
                return !$r1.getName().startsWith($r1);
            }
        });
        if ($r4 == null) {
            Log.w(TAG, "Failed to list secondary dex dir content (" + $r0.getPath() + ").");
            return;
        }
        for (File $r02 : $r4) {
            Log.i(TAG, "Trying to delete old file " + $r02.getPath() + " of size " + $r02.length());
            if ($r02.delete()) {
                Log.i(TAG, "Deleted old file " + $r02.getPath());
            } else {
                Log.w(TAG, "Failed to delete old file " + $r02.getPath());
            }
        }
    }

    private static void mkdirChecked(File $r0) throws IOException {
        $r0.mkdir();
        if (!$r0.isDirectory()) {
            File $r1 = $r0.getParentFile();
            if ($r1 == null) {
                Log.e(TAG, "Failed to create dir " + $r0.getPath() + ". Parent file is null.");
            } else {
                Log.e(TAG, "Failed to create dir " + $r0.getPath() + ". parent file is a dir " + $r1.isDirectory() + ", a file " + $r1.isFile() + ", exists " + $r1.exists() + ", readable " + $r1.canRead() + ", writable " + $r1.canWrite());
            }
            throw new IOException("Failed to create cache directory " + $r0.getPath());
        }
    }

    private static void extract(ZipFile $r0, ZipEntry $r1, File $r2, String $r3) throws IOException, FileNotFoundException {
        Throwable $r13;
        InputStream $r7 = $r0.getInputStream($r1);
        String str = $r3;
        File $r8 = File.createTempFile(str, EXTRACTED_SUFFIX, $r2.getParentFile());
        Log.i(TAG, "Extracting " + $r8.getPath());
        try {
            ZipOutputStream $r6 = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream($r8)));
            try {
                ZipEntry $r5 = new ZipEntry("classes.dex");
                $r5.setTime($r1.getTime());
                $r6.putNextEntry($r5);
                byte[] $r4 = new byte[16384];
                for (int $i1 = $r7.read($r4); $i1 != -1; $i1 = $r7.read($r4)) {
                    $r6.write($r4, 0, $i1);
                }
                $r6.closeEntry();
                $r6.close();
                Log.i(TAG, "Renaming to " + $r2.getPath());
                if ($r8.renameTo($r2)) {
                    closeQuietly($r7);
                    $r8.delete();
                    return;
                }
                throw new IOException("Failed to rename \"" + $r8.getAbsolutePath() + "\" to \"" + $r2.getAbsolutePath() + "\"");
            } catch (Throwable th) {
                $r13 = th;
                closeQuietly($r7);
                $r8.delete();
                throw $r13;
            }
        } catch (Throwable th2) {
            $r13 = th2;
            closeQuietly($r7);
            $r8.delete();
            throw $r13;
        }
    }

    static boolean verifyZipFile(File $r0) throws  {
        try {
            try {
                new ZipFile($r0).close();
                return true;
            } catch (IOException e) {
                Log.w(TAG, "Failed to close zip file: " + $r0.getAbsolutePath());
                return false;
            }
        } catch (ZipException $r5) {
            Log.w(TAG, "File " + $r0.getAbsolutePath() + " is not a valid zip file.", $r5);
        } catch (IOException $r6) {
            Log.w(TAG, "Got an IOException trying to open zip file: " + $r0.getAbsolutePath(), $r6);
        }
    }

    private static void closeQuietly(Closeable $r0) throws  {
        try {
            $r0.close();
        } catch (IOException $r1) {
            Log.w(TAG, "Failed to close resource", $r1);
        }
    }

    static {
        try {
            sApplyMethod = Editor.class.getMethod("apply", new Class[0]);
        } catch (NoSuchMethodException e) {
            sApplyMethod = null;
        }
    }

    private static void apply(Editor $r0) throws  {
        if (sApplyMethod != null) {
            try {
                sApplyMethod.invoke($r0, new Object[0]);
                return;
            } catch (InvocationTargetException e) {
            } catch (IllegalAccessException e2) {
            }
        }
        $r0.commit();
    }
}
