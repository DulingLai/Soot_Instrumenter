package com.google.android.gms.common.util;

import android.os.Binder;
import android.os.Process;
import android.util.Log;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/* compiled from: dalvik_source_com.waze.apk */
public class zzs {
    private static String MW = null;

    public static String zzazn() throws  {
        return zzjl(Binder.getCallingPid());
    }

    public static String zzazo() throws  {
        if (MW == null) {
            MW = zzjl(Process.myPid());
        }
        return MW;
    }

    private static String zzjl(int $i0) throws  {
        BufferedReader $r0;
        IOException $r6;
        Throwable $r9;
        try {
            $r0 = new BufferedReader(new FileReader("/proc/" + $i0 + "/cmdline"));
            try {
                String $r3 = $r0.readLine().trim();
                if ($r0 == null) {
                    return $r3;
                }
                try {
                    $r0.close();
                    return $r3;
                } catch (Exception $r4) {
                    Log.w("ProcessUtils", $r4.getMessage(), $r4);
                    return $r3;
                }
            } catch (IOException e) {
                $r6 = e;
                try {
                    Log.e("ProcessUtils", $r6.getMessage(), $r6);
                    if ($r0 != null) {
                        return null;
                    }
                    try {
                        $r0.close();
                        return null;
                    } catch (Exception $r7) {
                        Log.w("ProcessUtils", $r7.getMessage(), $r7);
                        return null;
                    }
                } catch (Throwable th) {
                    $r9 = th;
                    if ($r0 != null) {
                        try {
                            $r0.close();
                        } catch (Exception $r10) {
                            Log.w("ProcessUtils", $r10.getMessage(), $r10);
                        }
                    }
                    throw $r9;
                }
            }
        } catch (IOException e2) {
            $r6 = e2;
            $r0 = null;
            Log.e("ProcessUtils", $r6.getMessage(), $r6);
            if ($r0 != null) {
                return null;
            }
            $r0.close();
            return null;
        } catch (Throwable $r8) {
            $r0 = null;
            $r9 = $r8;
            if ($r0 != null) {
                $r0.close();
            }
            throw $r9;
        }
    }
}
