package android.support.multidex;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build.VERSION;
import android.util.Log;
import com.waze.ResManager;
import dalvik.annotation.Signature;
import dalvik.system.DexFile;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipFile;

public final class MultiDex {
    private static final boolean IS_VM_MULTIDEX_CAPABLE = isVMMultidexCapable(System.getProperty("java.vm.version"));
    private static final int MAX_SUPPORTED_SDK_VERSION = 20;
    private static final int MIN_SDK_VERSION = 4;
    private static final String OLD_SECONDARY_FOLDER_NAME = "secondary-dexes";
    private static final String SECONDARY_FOLDER_NAME = (ResManager.mPkgCodeCacheDir + File.separator + OLD_SECONDARY_FOLDER_NAME);
    static final String TAG = "MultiDex";
    private static final int VM_WITH_MULTIDEX_VERSION_MAJOR = 2;
    private static final int VM_WITH_MULTIDEX_VERSION_MINOR = 1;
    private static final Set<String> installedApk = new HashSet();

    private static final class V14 {
        private V14() throws  {
        }

        private static void install(@Signature({"(", "Ljava/lang/ClassLoader;", "Ljava/util/List", "<", "Ljava/io/File;", ">;", "Ljava/io/File;", ")V"}) ClassLoader $r0, @Signature({"(", "Ljava/lang/ClassLoader;", "Ljava/util/List", "<", "Ljava/io/File;", ">;", "Ljava/io/File;", ")V"}) List<File> $r1, @Signature({"(", "Ljava/lang/ClassLoader;", "Ljava/util/List", "<", "Ljava/io/File;", ">;", "Ljava/io/File;", ")V"}) File $r2) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException {
            Object $r4 = MultiDex.findField($r0, "pathList").get($r0);
            MultiDex.expandFieldArray($r4, "dexElements", makeDexElements($r4, new ArrayList($r1), $r2));
        }

        private static Object[] makeDexElements(@Signature({"(", "Ljava/lang/Object;", "Ljava/util/ArrayList", "<", "Ljava/io/File;", ">;", "Ljava/io/File;", ")[", "Ljava/lang/Object;"}) Object $r0, @Signature({"(", "Ljava/lang/Object;", "Ljava/util/ArrayList", "<", "Ljava/io/File;", ">;", "Ljava/io/File;", ")[", "Ljava/lang/Object;"}) ArrayList<File> $r1, @Signature({"(", "Ljava/lang/Object;", "Ljava/util/ArrayList", "<", "Ljava/io/File;", ">;", "Ljava/io/File;", ")[", "Ljava/lang/Object;"}) File $r2) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
            return (Object[]) MultiDex.findMethod($r0, "makeDexElements", ArrayList.class, File.class).invoke($r0, new Object[]{$r1, $r2});
        }
    }

    private static final class V19 {
        private V19() throws  {
        }

        private static void install(@Signature({"(", "Ljava/lang/ClassLoader;", "Ljava/util/List", "<", "Ljava/io/File;", ">;", "Ljava/io/File;", ")V"}) ClassLoader $r0, @Signature({"(", "Ljava/lang/ClassLoader;", "Ljava/util/List", "<", "Ljava/io/File;", ">;", "Ljava/io/File;", ")V"}) List<File> $r1, @Signature({"(", "Ljava/lang/ClassLoader;", "Ljava/util/List", "<", "Ljava/io/File;", ">;", "Ljava/io/File;", ")V"}) File $r2) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException {
            Object $r6 = MultiDex.findField($r0, "pathList").get($r0);
            ArrayList $r4 = new ArrayList();
            MultiDex.expandFieldArray($r6, "dexElements", makeDexElements($r6, new ArrayList($r1), $r2, $r4));
            if ($r4.size() > 0) {
                IOException[] $r3;
                Iterator $r9 = $r4.iterator();
                while ($r9.hasNext()) {
                    Log.w(MultiDex.TAG, "Exception in makeDexElement", (IOException) $r9.next());
                }
                Field $r5 = MultiDex.findField($r0, "dexElementsSuppressedExceptions");
                Object $r11 = (IOException[]) $r5.get($r0);
                if ($r11 == null) {
                    $r3 = (IOException[]) $r4.toArray(new IOException[$r4.size()]);
                } else {
                    int $i0 = $r4.size();
                    int $i1 = $r11.length;
                    $r3 = new IOException[($i0 + $i1)];
                    $r4.toArray($r3);
                    System.arraycopy($r11, 0, $r3, $r4.size(), $r11.length);
                }
                $r5.set($r0, $r3);
            }
        }

        private static Object[] makeDexElements(@Signature({"(", "Ljava/lang/Object;", "Ljava/util/ArrayList", "<", "Ljava/io/File;", ">;", "Ljava/io/File;", "Ljava/util/ArrayList", "<", "Ljava/io/IOException;", ">;)[", "Ljava/lang/Object;"}) Object $r0, @Signature({"(", "Ljava/lang/Object;", "Ljava/util/ArrayList", "<", "Ljava/io/File;", ">;", "Ljava/io/File;", "Ljava/util/ArrayList", "<", "Ljava/io/IOException;", ">;)[", "Ljava/lang/Object;"}) ArrayList<File> $r1, @Signature({"(", "Ljava/lang/Object;", "Ljava/util/ArrayList", "<", "Ljava/io/File;", ">;", "Ljava/io/File;", "Ljava/util/ArrayList", "<", "Ljava/io/IOException;", ">;)[", "Ljava/lang/Object;"}) File $r2, @Signature({"(", "Ljava/lang/Object;", "Ljava/util/ArrayList", "<", "Ljava/io/File;", ">;", "Ljava/io/File;", "Ljava/util/ArrayList", "<", "Ljava/io/IOException;", ">;)[", "Ljava/lang/Object;"}) ArrayList<IOException> $r3) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
            return (Object[]) MultiDex.findMethod($r0, "makeDexElements", ArrayList.class, File.class, ArrayList.class).invoke($r0, new Object[]{$r1, $r2, $r3});
        }
    }

    private static final class V4 {
        private V4() throws  {
        }

        private static void install(@Signature({"(", "Ljava/lang/ClassLoader;", "Ljava/util/List", "<", "Ljava/io/File;", ">;)V"}) ClassLoader $r0, @Signature({"(", "Ljava/lang/ClassLoader;", "Ljava/util/List", "<", "Ljava/io/File;", ">;)V"}) List<File> $r1) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, IOException {
            int $i0 = $r1.size();
            Field $r7 = MultiDex.findField($r0, "path");
            StringBuilder $r6 = new StringBuilder((String) $r7.get($r0));
            String[] $r4 = new String[$i0];
            File[] $r3 = new File[$i0];
            ZipFile[] $r5 = new ZipFile[$i0];
            DexFile[] $r2 = new DexFile[$i0];
            ListIterator $r10 = $r1.listIterator();
            while ($r10.hasNext()) {
                File $r11 = (File) $r10.next();
                String $r9 = $r11.getAbsolutePath();
                $r6.append(':').append($r9);
                $i0 = $r10.previousIndex();
                $r4[$i0] = $r9;
                $r3[$i0] = $r11;
                $r5[$i0] = new ZipFile($r11);
                $r2[$i0] = DexFile.loadDex($r9, $r9 + ".dex", 0);
            }
            $r7.set($r0, $r6.toString());
            MultiDex.expandFieldArray($r0, "mPaths", $r4);
            MultiDex.expandFieldArray($r0, "mFiles", $r3);
            MultiDex.expandFieldArray($r0, "mZips", $r5);
            MultiDex.expandFieldArray($r0, "mDexs", $r2);
        }
    }

    private MultiDex() throws  {
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void install(android.content.Context r19) throws  {
        /*
        r1 = "MultiDex";
        r2 = "install";
        android.util.Log.i(r1, r2);
        r3 = IS_VM_MULTIDEX_CAPABLE;
        if (r3 == 0) goto L_0x0013;
    L_0x000b:
        r1 = "MultiDex";
        r2 = "VM has multidex support, MultiDex support library is disabled.";
        android.util.Log.i(r1, r2);
        return;
    L_0x0013:
        r4 = android.os.Build.VERSION.SDK_INT;
        r5 = 4;
        if (r4 >= r5) goto L_0x0044;
    L_0x0018:
        r6 = new java.lang.RuntimeException;
        r7 = new java.lang.StringBuilder;
        r7.<init>();
        r1 = "Multi dex installation failed. SDK ";
        r7 = r7.append(r1);
        r4 = android.os.Build.VERSION.SDK_INT;
        r7 = r7.append(r4);
        r1 = " is unsupported. Min SDK version is ";
        r7 = r7.append(r1);
        r5 = 4;
        r7 = r7.append(r5);
        r1 = ".";
        r7 = r7.append(r1);
        r8 = r7.toString();
        r6.<init>(r8);
        throw r6;
    L_0x0044:
        r0 = r19;
        r9 = getApplicationInfo(r0);	 Catch:{ Exception -> 0x005e }
        if (r9 == 0) goto L_0x0153;
    L_0x004c:
        r10 = installedApk;	 Catch:{ Exception -> 0x005e }
        monitor-enter(r10);	 Catch:{ Exception -> 0x005e }
        r8 = r9.sourceDir;	 Catch:{ Throwable -> 0x005b }
        r11 = installedApk;	 Catch:{ Throwable -> 0x005b }
        r3 = r11.contains(r8);	 Catch:{ Throwable -> 0x005b }
        if (r3 == 0) goto L_0x0089;
    L_0x0059:
        monitor-exit(r10);	 Catch:{ Throwable -> 0x005b }
        return;
    L_0x005b:
        r12 = move-exception;
        monitor-exit(r10);	 Catch:{ Throwable -> 0x005b }
        throw r12;	 Catch:{ Exception -> 0x005e }
    L_0x005e:
        r13 = move-exception;
        r1 = "MultiDex";
        r2 = "Multidex installation failure";
        android.util.Log.e(r1, r2, r13);
        r6 = new java.lang.RuntimeException;
        r7 = new java.lang.StringBuilder;
        r7.<init>();
        r1 = "Multi dex installation failed (";
        r7 = r7.append(r1);
        r8 = r13.getMessage();
        r7 = r7.append(r8);
        r1 = ").";
        r7 = r7.append(r1);
        r8 = r7.toString();
        r6.<init>(r8);
        throw r6;
    L_0x0089:
        r11 = installedApk;	 Catch:{ Throwable -> 0x005b }
        r11.add(r8);	 Catch:{ Throwable -> 0x005b }
        r4 = android.os.Build.VERSION.SDK_INT;	 Catch:{ Throwable -> 0x005b }
        r5 = 20;
        if (r4 <= r5) goto L_0x00dc;
    L_0x0094:
        r7 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x005b }
        r7.<init>();	 Catch:{ Throwable -> 0x005b }
        r1 = "MultiDex is not guaranteed to work in SDK version ";
        r7 = r7.append(r1);	 Catch:{ Throwable -> 0x005b }
        r4 = android.os.Build.VERSION.SDK_INT;	 Catch:{ Throwable -> 0x005b }
        r7 = r7.append(r4);	 Catch:{ Throwable -> 0x005b }
        r1 = ": SDK version higher than ";
        r7 = r7.append(r1);	 Catch:{ Throwable -> 0x005b }
        r5 = 20;
        r7 = r7.append(r5);	 Catch:{ Throwable -> 0x005b }
        r1 = " should be backed by ";
        r7 = r7.append(r1);	 Catch:{ Throwable -> 0x005b }
        r1 = "runtime with built-in multidex capabilty but it's not the ";
        r7 = r7.append(r1);	 Catch:{ Throwable -> 0x005b }
        r1 = "case here: java.vm.version=\"";
        r7 = r7.append(r1);	 Catch:{ Throwable -> 0x005b }
        r1 = "java.vm.version";
        r8 = java.lang.System.getProperty(r1);	 Catch:{ Throwable -> 0x005b }
        r7 = r7.append(r8);	 Catch:{ Throwable -> 0x005b }
        r1 = "\"";
        r7 = r7.append(r1);	 Catch:{ Throwable -> 0x005b }
        r8 = r7.toString();	 Catch:{ Throwable -> 0x005b }
        r1 = "MultiDex";
        android.util.Log.w(r1, r8);	 Catch:{ Throwable -> 0x005b }
    L_0x00dc:
        r0 = r19;
        r14 = r0.getClassLoader();	 Catch:{ RuntimeException -> 0x00ed }
        if (r14 != 0) goto L_0x00f7;
    L_0x00e4:
        r1 = "MultiDex";
        r2 = "Context class loader is null. Must be running in test mode. Skip patching.";
        android.util.Log.e(r1, r2);	 Catch:{ Throwable -> 0x005b }
        monitor-exit(r10);	 Catch:{ Throwable -> 0x005b }
        return;
    L_0x00ed:
        r6 = move-exception;
        r1 = "MultiDex";
        r2 = "Failure while trying to obtain Context class loader. Must be running in test mode. Skip patching.";
        android.util.Log.w(r1, r2, r6);	 Catch:{ Throwable -> 0x005b }
        monitor-exit(r10);	 Catch:{ Throwable -> 0x005b }
        return;
    L_0x00f7:
        r0 = r19;
        clearOldDexDir(r0);	 Catch:{ Throwable -> 0x0124 }
    L_0x00fc:
        r15 = new java.io.File;	 Catch:{ Throwable -> 0x005b }
        r8 = r9.dataDir;	 Catch:{ Throwable -> 0x005b }
        r16 = SECONDARY_FOLDER_NAME;	 Catch:{ Throwable -> 0x005b }
        r0 = r16;
        r15.<init>(r8, r0);	 Catch:{ Throwable -> 0x005b }
        r5 = 0;
        r0 = r19;
        r17 = android.support.multidex.MultiDexExtractor.load(r0, r9, r15, r5);	 Catch:{ Throwable -> 0x005b }
        r0 = r17;
        r3 = checkValidZipFiles(r0);	 Catch:{ Throwable -> 0x005b }
        if (r3 == 0) goto L_0x012f;
    L_0x0116:
        r0 = r17;
        installSecondaryDexes(r14, r15, r0);	 Catch:{ Throwable -> 0x005b }
    L_0x011b:
        monitor-exit(r10);	 Catch:{ Throwable -> 0x005b }
        r1 = "MultiDex";
        r2 = "install done";
        android.util.Log.i(r1, r2);
        return;
    L_0x0124:
        r18 = move-exception;
        r1 = "MultiDex";
        r2 = "Something went wrong when trying to clear old MultiDex extraction, continuing without cleaning.";
        r0 = r18;
        android.util.Log.w(r1, r2, r0);	 Catch:{ Throwable -> 0x005b }
        goto L_0x00fc;
    L_0x012f:
        r1 = "MultiDex";
        r2 = "Files were not valid zip files.  Forcing a reload.";
        android.util.Log.w(r1, r2);	 Catch:{ Throwable -> 0x005b }
        r5 = 1;
        r0 = r19;
        r17 = android.support.multidex.MultiDexExtractor.load(r0, r9, r15, r5);	 Catch:{ Throwable -> 0x005b }
        r0 = r17;
        r3 = checkValidZipFiles(r0);	 Catch:{ Throwable -> 0x005b }
        if (r3 == 0) goto L_0x014b;
    L_0x0145:
        r0 = r17;
        installSecondaryDexes(r14, r15, r0);	 Catch:{ Throwable -> 0x005b }
        goto L_0x011b;
    L_0x014b:
        r6 = new java.lang.RuntimeException;	 Catch:{ Throwable -> 0x005b }
        r1 = "Zip files were not valid.";
        r6.<init>(r1);	 Catch:{ Throwable -> 0x005b }
        throw r6;	 Catch:{ Throwable -> 0x005b }
    L_0x0153:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.multidex.MultiDex.install(android.content.Context):void");
    }

    private static ApplicationInfo getApplicationInfo(Context $r0) throws NameNotFoundException {
        try {
            PackageManager $r2 = $r0.getPackageManager();
            String $r3 = $r0.getPackageName();
            if ($r2 == null) {
                return null;
            }
            if ($r3 == null) {
                return null;
            }
            return $r2.getApplicationInfo($r3, 128);
        } catch (RuntimeException $r1) {
            Log.w(TAG, "Failure while trying to obtain ApplicationInfo from Context. Must be running in test mode. Skip patching.", $r1);
            return null;
        }
    }

    static boolean isVMMultidexCapable(String $r0) throws  {
        boolean $z0 = false;
        if ($r0 != null) {
            Matcher $r2 = Pattern.compile("(\\d+)\\.(\\d+)(\\.\\d+)?").matcher($r0);
            if ($r2.matches()) {
                try {
                    int $i0 = Integer.parseInt($r2.group(1));
                    $z0 = $i0 > 2 || ($i0 == 2 && Integer.parseInt($r2.group(2)) >= 1);
                } catch (NumberFormatException e) {
                }
            }
        }
        Log.i(TAG, "VM with version " + $r0 + ($z0 ? " has multidex support" : " does not have multidex support"));
        return $z0;
    }

    private static void installSecondaryDexes(@Signature({"(", "Ljava/lang/ClassLoader;", "Ljava/io/File;", "Ljava/util/List", "<", "Ljava/io/File;", ">;)V"}) ClassLoader $r0, @Signature({"(", "Ljava/lang/ClassLoader;", "Ljava/io/File;", "Ljava/util/List", "<", "Ljava/io/File;", ">;)V"}) File $r1, @Signature({"(", "Ljava/lang/ClassLoader;", "Ljava/io/File;", "Ljava/util/List", "<", "Ljava/io/File;", ">;)V"}) List<File> $r2) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, IOException {
        if (!$r2.isEmpty()) {
            if (VERSION.SDK_INT >= 19) {
                V19.install($r0, $r2, $r1);
            } else if (VERSION.SDK_INT >= 14) {
                V14.install($r0, $r2, $r1);
            } else {
                V4.install($r0, $r2);
            }
        }
    }

    private static boolean checkValidZipFiles(@Signature({"(", "Ljava/util/List", "<", "Ljava/io/File;", ">;)Z"}) List<File> $r0) throws  {
        for (File verifyZipFile : $r0) {
            if (!MultiDexExtractor.verifyZipFile(verifyZipFile)) {
                return false;
            }
        }
        return true;
    }

    private static Field findField(Object $r0, String $r1) throws NoSuchFieldException {
        Class $r2 = $r0.getClass();
        while ($r2 != null) {
            try {
                Field $r3 = $r2.getDeclaredField($r1);
                if ($r3.isAccessible()) {
                    return $r3;
                }
                $r3.setAccessible(true);
                return $r3;
            } catch (NoSuchFieldException e) {
                $r2 = $r2.getSuperclass();
            }
        }
        throw new NoSuchFieldException("Field " + $r1 + " not found in " + $r0.getClass());
    }

    private static Method findMethod(@Signature({"(", "Ljava/lang/Object;", "Ljava/lang/String;", "[", "Ljava/lang/Class", "<*>;)", "Ljava/lang/reflect/Method;"}) Object $r0, @Signature({"(", "Ljava/lang/Object;", "Ljava/lang/String;", "[", "Ljava/lang/Class", "<*>;)", "Ljava/lang/reflect/Method;"}) String $r1, @Signature({"(", "Ljava/lang/Object;", "Ljava/lang/String;", "[", "Ljava/lang/Class", "<*>;)", "Ljava/lang/reflect/Method;"}) Class<?>... $r2) throws NoSuchMethodException {
        Class $r3 = $r0.getClass();
        while ($r3 != null) {
            try {
                Method $r4 = $r3.getDeclaredMethod($r1, $r2);
                if ($r4.isAccessible()) {
                    return $r4;
                }
                $r4.setAccessible(true);
                return $r4;
            } catch (NoSuchMethodException e) {
                $r3 = $r3.getSuperclass();
            }
        }
        throw new NoSuchMethodException("Method " + $r1 + " with parameters " + Arrays.asList($r2) + " not found in " + $r0.getClass());
    }

    private static void expandFieldArray(Object $r0, String $r1, Object[] $r2) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Field $r3 = findField($r0, $r1);
        Object[] $r5 = (Object[]) $r3.get($r0);
        Object[] $r7 = (Object[]) Array.newInstance($r5.getClass().getComponentType(), $r5.length + $r2.length);
        System.arraycopy($r5, 0, $r7, 0, $r5.length);
        System.arraycopy($r2, 0, $r7, $r5.length, $r2.length);
        $r3.set($r0, $r7);
    }

    private static void clearOldDexDir(Context $r0) throws Exception {
        File $r1 = new File($r0.getFilesDir(), OLD_SECONDARY_FOLDER_NAME);
        if ($r1.isDirectory()) {
            Log.i(TAG, "Clearing old secondary dex dir (" + $r1.getPath() + ").");
            File[] $r5 = $r1.listFiles();
            if ($r5 == null) {
                Log.w(TAG, "Failed to list secondary dex dir content (" + $r1.getPath() + ").");
                return;
            }
            for (File $r2 : $r5) {
                Log.i(TAG, "Trying to delete old file " + $r2.getPath() + " of size " + $r2.length());
                if ($r2.delete()) {
                    Log.i(TAG, "Deleted old file " + $r2.getPath());
                } else {
                    Log.w(TAG, "Failed to delete old file " + $r2.getPath());
                }
            }
            if ($r1.delete()) {
                Log.i(TAG, "Deleted old secondary dex dir " + $r1.getPath());
                return;
            }
            Log.w(TAG, "Failed to delete secondary dex dir " + $r1.getPath());
        }
    }
}
