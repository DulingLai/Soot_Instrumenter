package com.google.android.gms.internal;

import android.os.SystemClock;
import android.util.Log;
import com.abaltatech.mcp.mcs.fileupload.FileUploadSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/* compiled from: dalvik_source_com.waze.apk */
public class zzs {
    public static boolean DEBUG = Log.isLoggable(TAG, 2);
    public static String TAG = "Volley";

    /* compiled from: dalvik_source_com.waze.apk */
    static class zza {
        public static final boolean zzbi = zzs.DEBUG;
        private final List<zza> zzbj = new ArrayList();
        private boolean zzbk = false;

        /* compiled from: dalvik_source_com.waze.apk */
        private static class zza {
            public final String name;
            public final long time;
            public final long zzbl;

            public zza(String $r1, long $l0, long $l1) throws  {
                this.name = $r1;
                this.zzbl = $l0;
                this.time = $l1;
            }
        }

        zza() throws  {
        }

        private long zzw() throws  {
            if (this.zzbj.size() == 0) {
                return 0;
            }
            return ((zza) this.zzbj.get(this.zzbj.size() - 1)).time - ((zza) this.zzbj.get(0)).time;
        }

        protected void finalize() throws Throwable {
            if (!this.zzbk) {
                zzd("Request on the loose");
                zzs.zzc("Marker log finalized without finish() - uncaught exit point for request", new Object[0]);
            }
        }

        public synchronized void zza(String $r1, long $l0) throws  {
            if (this.zzbk) {
                throw new IllegalStateException("Marker added to finished log");
            }
            this.zzbj.add(new zza($r1, $l0, SystemClock.elapsedRealtime()));
        }

        public synchronized void zzd(String $r1) throws  {
            this.zzbk = true;
            if (zzw() > 0) {
                long $l2 = ((zza) this.zzbj.get(0)).time;
                zzs.zzb("(%-4d ms) %s", Long.valueOf($l0), $r1);
                for (zza $r4 : this.zzbj) {
                    zzs.zzb("(+%-4d) [%2d] %s", Long.valueOf($r4.time - $l2), Long.valueOf($r4.zzbl), $r4.name);
                    $l2 = $r4.time;
                }
            }
        }
    }

    public static void zza(String $r0, Object... $r1) throws  {
        if (DEBUG) {
            Log.v(TAG, zzd($r0, $r1));
        }
    }

    public static void zza(Throwable $r0, String $r1, Object... $r2) throws  {
        Log.e(TAG, zzd($r1, $r2), $r0);
    }

    public static void zzb(String $r0, Object... $r1) throws  {
        Log.d(TAG, zzd($r0, $r1));
    }

    public static void zzc(String $r0, Object... $r1) throws  {
        Log.e(TAG, zzd($r0, $r1));
    }

    private static String zzd(String $r1, Object... $r0) throws  {
        String $r6;
        if ($r0 != null) {
            $r1 = String.format(Locale.US, $r1, $r0);
        }
        StackTraceElement[] $r3 = new Throwable().fillInStackTrace().getStackTrace();
        for (int $i0 = 2; $i0 < $r3.length; $i0++) {
            if (!$r3[$i0].getClass().equals(zzs.class)) {
                $r6 = $r3[$i0].getClassName();
                $r6 = $r6.substring($r6.lastIndexOf(46) + 1);
                $r6 = $r6.substring($r6.lastIndexOf(36) + 1);
                String $r7 = String.valueOf($r3[$i0].getMethodName());
                $r6 = new StringBuilder((String.valueOf($r6).length() + 1) + String.valueOf($r7).length()).append($r6).append(FileUploadSession.SEPARATOR).append($r7).toString();
                break;
            }
        }
        $r6 = "<unknown>";
        return String.format(Locale.US, "[%d] %s: %s", new Object[]{Long.valueOf(Thread.currentThread().getId()), $r6, $r1});
    }
}
