package com.android.volley;

import android.os.SystemClock;
import android.util.Log;
import com.abaltatech.mcp.mcs.fileupload.FileUploadSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class VolleyLog {
    public static boolean DEBUG = Log.isLoggable(TAG, 2);
    public static String TAG = "Volley";

    static class MarkerLog {
        public static final boolean ENABLED = VolleyLog.DEBUG;
        private static final long MIN_DURATION_FOR_LOGGING_MS = 0;
        private boolean mFinished = false;
        private final List<Marker> mMarkers = new ArrayList();

        private static class Marker {
            public final String name;
            public final long thread;
            public final long time;

            public Marker(String $r1, long $l0, long $l1) throws  {
                this.name = $r1;
                this.thread = $l0;
                this.time = $l1;
            }
        }

        MarkerLog() throws  {
        }

        public synchronized void add(String $r1, long $l0) throws  {
            if (this.mFinished) {
                throw new IllegalStateException("Marker added to finished log");
            }
            this.mMarkers.add(new Marker($r1, $l0, SystemClock.elapsedRealtime()));
        }

        public synchronized void finish(String $r1) throws  {
            this.mFinished = true;
            if (getTotalDuration() > 0) {
                long $l2 = ((Marker) this.mMarkers.get(0)).time;
                VolleyLog.m15d("(%-4d ms) %s", Long.valueOf($l0), $r1);
                for (Marker $r4 : this.mMarkers) {
                    VolleyLog.m15d("(+%-4d) [%2d] %s", Long.valueOf($r4.time - $l2), Long.valueOf($r4.thread), $r4.name);
                    $l2 = $r4.time;
                }
            }
        }

        protected void finalize() throws Throwable {
            if (!this.mFinished) {
                finish("Request on the loose");
                VolleyLog.m16e("Marker log finalized without finish() - uncaught exit point for request", new Object[0]);
            }
        }

        private long getTotalDuration() throws  {
            if (this.mMarkers.size() == 0) {
                return 0;
            }
            return ((Marker) this.mMarkers.get(this.mMarkers.size() - 1)).time - ((Marker) this.mMarkers.get(0)).time;
        }
    }

    public static void setTag(String $r0) throws  {
        m15d("Changing log tag to %s", $r0);
        TAG = $r0;
        DEBUG = Log.isLoggable(TAG, 2);
    }

    public static void m18v(String $r0, Object... $r1) throws  {
        if (DEBUG) {
            Log.v(TAG, buildMessage($r0, $r1));
        }
    }

    public static void m15d(String $r0, Object... $r1) throws  {
        Log.d(TAG, buildMessage($r0, $r1));
    }

    public static void m16e(String $r0, Object... $r1) throws  {
        Log.e(TAG, buildMessage($r0, $r1));
    }

    public static void m17e(Throwable $r0, String $r1, Object... $r2) throws  {
        Log.e(TAG, buildMessage($r1, $r2), $r0);
    }

    public static void wtf(String $r0, Object... $r1) throws  {
        Log.wtf(TAG, buildMessage($r0, $r1));
    }

    public static void wtf(Throwable $r0, String $r1, Object... $r2) throws  {
        Log.wtf(TAG, buildMessage($r1, $r2), $r0);
    }

    private static String buildMessage(String $r0, Object... $r1) throws  {
        if ($r1 != null) {
            $r0 = String.format(Locale.US, $r0, $r1);
        }
        StackTraceElement[] $r3 = new Throwable().fillInStackTrace().getStackTrace();
        String $r4 = "<unknown>";
        for (int $i0 = 2; $i0 < $r3.length; $i0++) {
            if (!$r3[$i0].getClass().equals(VolleyLog.class)) {
                $r4 = $r3[$i0].getClassName();
                $r4 = $r4.substring($r4.lastIndexOf(46) + 1);
                $r4 = $r4.substring($r4.lastIndexOf(36) + 1) + FileUploadSession.SEPARATOR + $r3[$i0].getMethodName();
                break;
            }
        }
        return String.format(Locale.US, "[%d] %s: %s", new Object[]{Long.valueOf(Thread.currentThread().getId()), $r4, $r0});
    }
}
