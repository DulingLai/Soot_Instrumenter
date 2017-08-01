package com.waze;

import java.util.Timer;

public final class NativeTimerManager {
    private static final int FREMAP_MAX_TASK_COUNT = 32;
    private final String LOG_TAG = "TimerManager";
    private NativeManager mNativeManager;
    private NativeTimerTask[] mTasks;
    private Timer mTimer;

    private native void InitTimerManagerNTV() throws ;

    public NativeTimerManager(NativeManager $r1) throws  {
        this.mNativeManager = $r1;
        this.mTimer = this.mNativeManager.getTimer();
        this.mTasks = new NativeTimerTask[32];
        InitTimerManagerNTV();
    }

    public void AddTask(int $i0, int $i1, int $i2) throws  {
        if (!NativeManager.isShuttingDown()) {
            if ($i0 > 31 || $i0 < 0) {
                Logger.m43w("TimerManagerTask ID is illegal: " + $i0);
                return;
            }
            if ($i2 <= 0) {
                Logger.m43w("TimerManagerInterval is illegal: " + $i2);
            }
            synchronized (this.mTasks) {
                if (this.mTasks[$i0] != null) {
                    RemoveTask($i0);
                }
                this.mTasks[$i0] = new NativeTimerTask($i0, $i1, $i2, this.mNativeManager);
            }
            StartTask($i0, (long) $i2);
        }
    }

    public void RemoveTask(int $i0) throws  {
        if (!NativeManager.isShuttingDown() && $i0 >= 0 && $i0 < 32) {
            synchronized (this.mTasks) {
                if (this.mTasks[$i0] != null) {
                    this.mTasks[$i0].cancel();
                    this.mTasks[$i0] = null;
                }
            }
        }
    }

    public void StartTask(int $i0, long $l1) throws  {
        if (this.mTasks[$i0] != null) {
            if ($l1 == 0) {
                $l1 = 1;
            }
            try {
                this.mTimer.scheduleAtFixedRate(this.mTasks[$i0], $l1, $l1);
                return;
            } catch (Exception $r1) {
                Logger.m44w("Start Task Error!  TaskId: " + $i0 + "Interval: " + $l1, $r1);
                return;
            }
        }
        Logger.m41i("StartTask. Task: " + String.valueOf($i0) + " is not active");
    }

    public void ShutDown() throws  {
        synchronized (this.mTasks) {
            for (int $i0 = 0; $i0 < 32; $i0++) {
                if (this.mTasks[$i0] != null) {
                    this.mTasks[$i0].cancel();
                }
            }
            this.mTimer.cancel();
        }
    }

    public void ShutDownBg() throws  {
        synchronized (this.mTasks) {
            for (int $i0 = 0; $i0 < 32; $i0++) {
                if (this.mTasks[$i0] != null) {
                    this.mTasks[$i0].cancel();
                }
            }
            this.mTimer.purge();
        }
    }

    public boolean ActiveTasksExist() throws  {
        return ActiveTasksCount() > 0;
    }

    public int ActiveTasksCount() throws  {
        int $i0 = 0;
        synchronized (this.mTasks) {
            for (int $i1 = 0; $i1 < 32; $i1++) {
                if (this.mTasks[$i1] != null) {
                    $i0++;
                }
            }
        }
        return $i0;
    }
}
