package com.waze.ifs.ui;

import android.util.Log;
import com.waze.AppService;
import com.waze.Logger;

public class ShutdownManager {
    private static boolean mActivitiesDestroyed = false;
    private static boolean mInProcess = false;
    private static boolean mServiceDestroyed = false;

    static class C17521 implements Runnable {
        C17521() throws  {
        }

        public void run() throws  {
            System.runFinalization();
            System.exit(0);
        }
    }

    public static void start() throws  {
        Log.d(Logger.TAG, "Shutdown manager - in process now");
        mInProcess = true;
        checkAndExit();
    }

    public static void onServiceDestroy() throws  {
        Log.d(Logger.TAG, "Shutdown manager - the service is destroyed");
        if (mInProcess) {
            mServiceDestroyed = true;
        }
        checkAndExit();
    }

    public static void onActivitiesDestroy() throws  {
        Log.d(Logger.TAG, "Shutdown manager - all the activities are destroyed");
        mActivitiesDestroyed = true;
        checkAndExit();
    }

    private static void checkAndExit() throws  {
        if (mInProcess && mServiceDestroyed && mActivitiesDestroyed) {
            Log.d(Logger.TAG, "Shutdown manager - all the constraints are satisfied. Aborting VM");
            AppService.Post(new C17521(), 5);
        }
    }
}
