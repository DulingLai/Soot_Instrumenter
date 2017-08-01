package com.waze.ifs.ui;

import android.app.Activity;
import android.util.Log;
import com.waze.AppService;
import com.waze.LocationFactory;
import com.waze.Logger;
import com.waze.NativeManager;
import com.waze.ifs.async.RunnableExecutor;

public final class ActivityLifetimeHandler {
    static Boolean bIsStopForeground = Boolean.valueOf(false);
    private static volatile boolean mIsInBackground = false;

    public static void startHandler(Activity $r0) throws  {
        C17051 $r1 = new RunnableExecutor($r0) {
            public void event() throws  {
                NativeManager $r1 = AppService.getNativeManager();
                $r1.SaveSystemSettings();
                $r1.RestoreAppSettings();
                $r1.onAppForeground();
                if (NativeManager.getInstance().IsShutdownActive()) {
                    ActivityLifetimeHandler.StartWaze();
                    NativeManager.getInstance().SetShutDownActive(false);
                }
            }
        };
        mIsInBackground = false;
        if (AppService.IsInitialized()) {
            AppService.getInstance().stopForeground();
        }
        if (AppService.IsAppRunning()) {
            $r1.event();
        } else {
            NativeManager.registerOnAppStartedEvent($r1);
        }
    }

    public static void stopHandler() throws  {
        mIsInBackground = true;
        try {
            if (AppService.IsInitialized() && !NativeManager.isShuttingDown()) {
                AppService.getInstance().startForeground();
            }
            NativeManager $r2 = AppService.getNativeManager();
            if ($r2 != null) {
                $r2.RestoreSystemSettings();
                $r2.onAppBackground();
                if (NativeManager.getInstance().IsShutdownActive()) {
                    StopWaze();
                }
            }
        } catch (IllegalArgumentException $r0) {
            Log.e(Logger.TAG, "ActivityLifetimeHandler stopHandler error", $r0);
        }
    }

    public static void StopWaze() throws  {
        NativeManager.getInstance().SetShutDownActive(true);
        NativeManager.getInstance().RealtimeLogout();
    }

    public static void ShutDownWaze() throws  {
        AppService.getInstance().stopForeground();
        LocationFactory.getInstance().stop();
    }

    public static void StartWaze() throws  {
        LocationFactory.getInstance().start();
        NativeManager.getInstance().RealtimeLogin();
    }

    public static boolean isInBackground() throws  {
        return mIsInBackground;
    }

    public static void screenOffHandler() throws  {
        NativeManager $r0 = AppService.getNativeManager();
        if ($r0 != null) {
            $r0.logAnalyticsFlush();
        }
    }

    public static void destroyHandler() throws  {
        if (ActivityBase.getCount() == 0) {
            ShutdownManager.onActivitiesDestroy();
        }
    }
}
