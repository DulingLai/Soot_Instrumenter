package com.waze;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import com.waze.utils.PixelMeasure;
import com.waze.utils.Stopwatch;
import com.waze.utils.VolleyManager;

public class WazeApplication extends Application {
    private static Thread mUIThread;
    public static final Stopwatch startSW = Stopwatch.create("APP START");

    public void onCreate() throws  {
        PixelMeasure.setResources(getResources());
        super.onCreate();
        VolleyManager.getInstance().initializeContext(getApplicationContext());
        startSW.start();
        mUIThread = Thread.currentThread();
    }

    public static boolean isUIThread() throws  {
        return Thread.currentThread() == mUIThread;
    }

    protected void attachBaseContext(Context $r1) throws  {
        super.attachBaseContext($r1);
        MultiDex.install(this);
    }
}
