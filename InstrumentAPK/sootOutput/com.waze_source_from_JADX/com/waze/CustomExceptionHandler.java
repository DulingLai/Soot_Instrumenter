package com.waze;

import android.util.Log;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomExceptionHandler implements UncaughtExceptionHandler {
    private UncaughtExceptionHandler defaultUEH = Thread.getDefaultUncaughtExceptionHandler();
    private String localPath = ((AppService.getAppContext().getFilesDir().getParent() + "/") + "/crash_logs");

    private void writeToFile(String $r1, String $r2) throws  {
        try {
            BufferedWriter $r3 = new BufferedWriter(new FileWriter(this.localPath + "/" + $r2));
            $r3.write($r1);
            $r3.flush();
            $r3.close();
        } catch (Exception $r4) {
            $r4.printStackTrace();
        }
    }

    public CustomExceptionHandler() throws  {
        File $r1 = new File(this.localPath + "/aa");
        if ($r1.getParentFile() != null) {
            $r1.getParentFile().mkdirs();
        }
    }

    public void uncaughtException(Thread $r1, Throwable $r2) throws  {
        if (!NativeManager.IsAppStarted()) {
            $r2.printStackTrace();
        } else if (($r2 instanceof RuntimeException) && $r2.getMessage().contains("eglDestroyContex failed")) {
            Log.e(Logger.TAG, "Runtime exception of eglDestroyContex failed occurred");
            Logger.m38e("Runtime exception of eglDestroyContex failed occurred");
        } else {
            if (($r2 instanceof RuntimeException) && $r2.getMessage().contains("failure code: -32")) {
                StackTraceElement[] $r8 = $r2.getStackTrace();
                if ($r8 != null && $r8[0].getClassName().contains("MediaPlayer")) {
                    Log.e(Logger.TAG, "Runtime exception of MediaPlayer occurred");
                    Logger.m38e("Runtime exception of MediaPlayer occurred");
                    return;
                }
            }
            StringWriter $r6 = new StringWriter();
            PrintWriter $r5 = new PrintWriter($r6);
            $r2.printStackTrace($r5);
            String $r7 = $r6.toString();
            $r5.close();
            String $r10 = "Android_" + new SimpleDateFormat("ddMMyy_hh_mm_ss").format(new Date()) + ".plcrash";
            Log.e(Logger.TAG, "" + AppService.getActiveActivity());
            String $r13 = AppService.getActiveActivity() + "\n" + NativeManager.getManufacturer() + ", " + NativeManager.getModel() + ", " + NativeManager.getDevice() + "\n";
            NativeManager.getInstance().Config_Set_Closed_Properly(0, $r7);
            writeToFile($r13 + $r7, $r10);
            UncaughtExceptionHandler uncaughtExceptionHandler = this.defaultUEH;
            UncaughtExceptionHandler $r15 = uncaughtExceptionHandler;
            uncaughtExceptionHandler.uncaughtException($r1, $r2);
        }
    }
}
