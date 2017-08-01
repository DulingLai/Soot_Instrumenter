package com.waze;

import android.util.Log;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public final class LogTail {
    private int mBufSize = 65536;
    private String mPath;
    private BufferedReader mReader;
    private boolean mSDLogEnabled = false;
    private final String mSDLogPath = (AppService.getExternalStoragePath() + "/waze_log.txt");
    private FileOutputStream mSDLogStream;
    private TailLoop mTailLoop;
    private Thread mTailLoopThread;

    private class TailLoop implements Runnable {
        private TailLoop() throws  {
        }

        public void run() throws  {
            Log.i("TailLoop", "Starting the LogTail");
            while (true) {
                String $r4 = LogTail.this.mReader.readLine();
                if ($r4 != null) {
                    Log.i("NativeLog", $r4);
                    if (LogTail.this.mSDLogEnabled && LogTail.this.mSDLogStream != null) {
                        LogTail.this.mSDLogStream.write(($r4 + "\n").getBytes());
                    }
                } else {
                    try {
                        Thread.sleep(100);
                    } catch (Exception $r1) {
                        Thread.currentThread().interrupt();
                        Log.e("TailLoop", "Error in reading log file" + $r1.getMessage() + $r1.getStackTrace());
                        return;
                    }
                }
            }
        }
    }

    LogTail() throws  {
    }

    LogTail(boolean $z0) throws  {
        this.mSDLogEnabled = $z0;
    }

    public void Open(String $r1) throws  {
        this.mPath = $r1;
        try {
            this.mReader = new BufferedReader(new FileReader(this.mPath), this.mBufSize);
            this.mTailLoop = new TailLoop();
            this.mTailLoopThread = new Thread(this.mTailLoop, "Native Log Thread");
            if (this.mSDLogEnabled) {
                this.mSDLogStream = new FileOutputStream(this.mSDLogPath);
            }
        } catch (Exception $r2) {
            Log.e("LogTail Open", "Error in opening log file" + $r2.getMessage() + $r2.getStackTrace());
        }
    }

    public void StartLogTail() throws  {
        if (this.mReader != null) {
            this.mTailLoopThread.start();
        }
    }

    public void StopLogTail() throws  {
        this.mTailLoopThread.stop();
        if (this.mSDLogEnabled) {
            try {
                this.mSDLogStream.close();
            } catch (IOException $r1) {
                Log.i("Log Tail", "Error closing SD log stream." + $r1.getMessage() + $r1.getStackTrace());
            }
        }
    }

    public void Close() throws  {
        StopLogTail();
        try {
            this.mReader.close();
        } catch (IOException e) {
        }
    }
}
