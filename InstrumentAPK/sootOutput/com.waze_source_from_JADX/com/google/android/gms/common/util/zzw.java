package com.google.android.gms.common.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.Log;
import java.io.File;

/* compiled from: dalvik_source_com.waze.apk */
public class zzw {
    @TargetApi(21)
    public static File getNoBackupFilesDir(Context $r0) throws  {
        return zzr.zzazk() ? $r0.getNoBackupFilesDir() : zze(new File($r0.getApplicationInfo().dataDir, "no_backup"));
    }

    private static synchronized File zze(File $r1) throws  {
        synchronized (zzw.class) {
            try {
                if (!($r1.exists() || $r1.mkdirs() || $r1.exists())) {
                    String $r0 = "Unable to create no-backup dir ";
                    String $r2 = String.valueOf($r1.getPath());
                    Log.w("SupportV4Utils", $r2.length() != 0 ? $r0.concat($r2) : new String("Unable to create no-backup dir "));
                    $r1 = null;
                }
            } catch (Throwable th) {
                Class cls = zzw.class;
            }
        }
        return $r1;
    }
}
