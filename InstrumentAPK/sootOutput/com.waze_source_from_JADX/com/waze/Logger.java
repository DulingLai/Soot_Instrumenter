package com.waze;

import android.util.Log;
import com.abaltatech.mcp.weblink.core.WLTypes;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;

public class Logger {
    private static final int DEBUG_LVL = 1;
    private static final int ERROR_LVL = 4;
    private static final int FATAL_LVL = 5;
    private static final int INFO_LVL = 2;
    private static final String LOG_PREFIX = "Java Layer: ";
    public static final String TAG = "WAZE";
    private static final int WARNING_LVL = 3;
    private static Logger mInstance = null;
    public static boolean mLogAndroidDebug = false;
    public static boolean mLogAndroidEnabled = true;
    public static boolean mLogFileEnabled = true;

    public static class LogCatMonitor extends Thread {
        private boolean mActive = false;
        private Process mLogcatProc = null;
        private boolean mOutFileValid = false;

        public void setOutFileInvalid() throws  {
            this.mOutFileValid = false;
        }

        public LogCatMonitor() throws  {
            setPriority(1);
            setName("Logcat Monitor");
        }

        public void run() throws  {
            Exception $r19;
            Throwable $r22;
            Process $r16;
            BufferedReader $r3 = null;
            FileOutputStream $r4 = null;
            Runtime $r5 = Runtime.getRuntime();
            String[] $r6 = new String[4];
            try {
                $r6[0] = "logcat";
                $r6[1] = "-v";
                $r6[2] = WLTypes.VEHICLEDATA_ATTRIBUTE_TIME;
                $r6[3] = "*:I";
                this.mLogcatProc = $r5.exec($r6);
                BufferedReader $r2 = new BufferedReader(new InputStreamReader(this.mLogcatProc.getInputStream()));
                try {
                    StringBuilder $r1 = new StringBuilder();
                    String $r10 = System.getProperty("line.separator");
                    this.mActive = true;
                    this.mOutFileValid = false;
                    FileOutputStream $r11 = null;
                    while (this.mActive) {
                        try {
                            String $r12 = $r2.readLine();
                            if ($r12 == null) {
                                break;
                            }
                            if (this.mOutFileValid) {
                                $r4 = $r11;
                            } else {
                                $r4 = new FileOutputStream(AppService.getExternalStoragePath() + "/waze/" + ResManager.mLogCatFile, true);
                                this.mOutFileValid = true;
                            }
                            $r1.append($r12);
                            $r1.append($r10);
                            $r4.write($r12.getBytes());
                            $r4.write($r10.getBytes());
                            $r11 = $r4;
                        } catch (Exception e) {
                            $r19 = e;
                            $r4 = $r11;
                            $r3 = $r2;
                        } catch (Throwable th) {
                            $r22 = th;
                            $r4 = $r11;
                            $r3 = $r2;
                        }
                    }
                    synchronized (this.mLogcatProc) {
                        $r16 = this.mLogcatProc;
                        $r16.destroy();
                    }
                    if ($r11 != null) {
                        try {
                            $r11.close();
                        } catch (IOException e2) {
                            Log.e("Waze", "Error closing streams in Logcat thread");
                        }
                    }
                    if ($r2 != null) {
                        $r2.close();
                    }
                } catch (Exception e3) {
                    $r19 = e3;
                    $r3 = $r2;
                } catch (Throwable th2) {
                    $r22 = th2;
                    $r3 = $r2;
                }
            } catch (Exception e4) {
                $r19 = e4;
                try {
                    Log.e("Waze", "Error in Logcat thread: " + $r19.getMessage());
                    $r19.printStackTrace();
                    synchronized (this.mLogcatProc) {
                        $r16 = this.mLogcatProc;
                        $r16.destroy();
                    }
                    if ($r4 != null) {
                        try {
                            $r4.close();
                        } catch (IOException e5) {
                            Log.e("Waze", "Error closing streams in Logcat thread");
                        }
                    }
                    if ($r3 != null) {
                        $r3.close();
                    }
                    Log.e("Waze", "Logcat thread ended");
                } catch (Throwable th3) {
                    $r22 = th3;
                    synchronized (this.mLogcatProc) {
                        $r16 = this.mLogcatProc;
                        $r16.destroy();
                    }
                    if ($r4 != null) {
                        try {
                            $r4.close();
                        } catch (IOException e6) {
                            Log.e("Waze", "Error closing streams in Logcat thread");
                        }
                    }
                    if ($r3 != null) {
                        $r3.close();
                    }
                    throw $r22;
                }
            }
            Log.e("Waze", "Logcat thread ended");
        }

        public void Destroy() throws  {
            this.mActive = false;
            try {
                synchronized (this.mLogcatProc) {
                    this.mLogcatProc.destroy();
                }
            } catch (Exception e) {
                Log.e("Waze", "Error closing streams in Logcat thread");
            }
        }
    }

    private native void WazeLogNTV(int i, String str) throws ;

    public static void create() throws  {
        if (mInstance == null) {
            mInstance = new Logger();
        }
    }

    public static String getStackStr(Throwable $r0) throws  {
        StringWriter $r2 = new StringWriter();
        $r0.printStackTrace(new PrintWriter($r2));
        return $r2.toString();
    }

    public static void m36d(String $r0) throws  {
        if (mLogAndroidDebug) {
            Log.d(TAG, $r0);
        }
        if (mLogFileEnabled) {
            LogData(1, $r0);
        }
    }

    public static void m37d(String $r0, Throwable $r1) throws  {
        m36d($r0 + " " + $r1.getMessage() + " " + getStackStr($r1));
    }

    public static void m41i(String $r0) throws  {
        if (mLogAndroidDebug) {
            Log.i(TAG, $r0);
        }
        if (mLogFileEnabled) {
            LogData(2, $r0);
        }
    }

    public static void ii(String $r0) throws  {
        if (mLogAndroidEnabled) {
            Log.i(TAG, $r0);
        }
        if (mLogFileEnabled) {
            LogData(2, $r0);
        }
    }

    public static void m42i(String $r0, Throwable $r1) throws  {
        m41i($r0 + " " + $r1.getMessage() + " " + getStackStr($r1));
    }

    public static void ii(String $r0, Throwable $r1) throws  {
        ii($r0 + " " + $r1.getMessage() + " " + getStackStr($r1));
    }

    public static void v_(String $r0, String $r1) throws  {
        Log.v("WAZE." + $r0, $r1);
    }

    public static void d_(String $r0, String $r1) throws  {
        Log.d("WAZE." + $r0, $r1);
    }

    public static void i_(String $r0, String $r1) throws  {
        Log.i("WAZE." + $r0, $r1);
    }

    public static void w_(String $r0, String $r1) throws  {
        Log.w("WAZE." + $r0, $r1);
    }

    public static void e_(String $r0, String $r1) throws  {
        Log.e("WAZE." + $r0, $r1);
    }

    public static void m43w(String $r0) throws  {
        if (mLogAndroidDebug) {
            Log.w(TAG, $r0);
        }
        if (mLogFileEnabled) {
            LogData(3, $r0);
        }
    }

    public static void ww(String $r0) throws  {
        if (mLogAndroidEnabled) {
            Log.w(TAG, $r0);
        }
        if (mLogFileEnabled) {
            LogData(3, $r0);
        }
    }

    public static void m44w(String $r0, Throwable $r1) throws  {
        m43w($r0 + " " + $r1.getMessage() + " " + getStackStr($r1));
    }

    public static void ww(String $r0, Throwable $r1) throws  {
        ww($r0 + " " + $r1.getMessage() + " " + getStackStr($r1));
    }

    public static void m38e(String $r0) throws  {
        if (mLogAndroidDebug) {
            Log.e(TAG, $r0);
        }
        if (mLogFileEnabled) {
            LogData(4, $r0);
        }
    }

    public static void m39e(String $r0, Throwable $r1) throws  {
        m38e($r0 + " " + $r1.getMessage() + " " + getStackStr($r1));
    }

    public static void ee(String $r0, Throwable $r1) throws  {
        ee($r0 + " " + $r1.getMessage() + " " + getStackStr($r1));
    }

    public static void ee(String $r0) throws  {
        if (mLogAndroidEnabled) {
            Log.e(TAG, $r0);
        }
        if (mLogFileEnabled) {
            LogData(4, $r0);
        }
    }

    public static void m40f(String $r0) throws  {
        LogData(5, $r0);
    }

    public static String TAG(String $r0) throws  {
        return new String("WAZE [ " + $r0 + " ]");
    }

    private Logger() throws  {
    }

    private static void LogData(final int $i0, final String $r0) throws  {
        if (mInstance != null) {
            try {
                synchronized (mInstance) {
                    NativeManager $r4 = AppService.getNativeManager();
                    if ($r4 == null || $r4.IsNativeThread()) {
                        mInstance.WazeLogNTV($i0, LOG_PREFIX + $r0);
                    } else {
                        $r4.PostRunnable(new Runnable() {
                            public void run() throws  {
                                Logger.mInstance.WazeLogNTV($i0, Logger.LOG_PREFIX + $r0);
                            }
                        });
                    }
                }
            } catch (Exception $r1) {
                Log.w(TAG, "Error in LogData: " + $r1.getMessage());
                $r1.printStackTrace();
            }
        }
    }
}
