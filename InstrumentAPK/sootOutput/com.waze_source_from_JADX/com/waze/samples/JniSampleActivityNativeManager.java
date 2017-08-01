package com.waze.samples;

import android.util.Log;
import com.waze.Logger;
import com.waze.NativeManager;
import com.waze.ifs.async.RunnableUICallback;

public class JniSampleActivityNativeManager {
    protected static JniSampleActivityNativeManager mInstance = null;

    class C26072 implements Runnable {
        C26072() {
        }

        public void run() {
            JniSampleActivityNativeManager.this.InitNativeLayerNTV();
        }
    }

    public interface ITimeUpdater {
        void updateTime(int i, int i2, int i3);
    }

    public static class TimeData {
        public int hour;
        public int min;
        public int sec;

        public TimeData(int i) {
        }
    }

    private native void InitNativeLayerNTV();

    private native TimeData LoadTimeNTV();

    public static JniSampleActivityNativeManager getInstance() {
        if (mInstance == null) {
            mInstance = new JniSampleActivityNativeManager();
            mInstance.initNativeLayer();
        }
        return mInstance;
    }

    public void runButtonClick(final ITimeUpdater updater) {
        NativeManager.Post(new RunnableUICallback() {
            TimeData mData;

            public void event() {
                Log.w(Logger.TAG, "ButtonEvent - event");
                this.mData = JniSampleActivityNativeManager.this.LoadTimeNTV();
            }

            public void callback() {
                Log.w(Logger.TAG, "ButtonEvent - callback");
                updater.updateTime(this.mData.hour, this.mData.min, this.mData.sec);
            }
        });
    }

    private void initNativeLayer() {
        NativeManager.Post(new C26072());
    }
}
