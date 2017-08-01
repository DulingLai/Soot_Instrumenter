package com.waze;

import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.widget.Toast;

public abstract class ToastThread extends HandlerThread {
    protected final long LOOPER_CANCEL_DELAY = 10000;
    protected final long TOAST_CANCEL_DELAY = 1000;
    private final long TOAST_DURATION_TEST_PERIOD = 500;
    protected long mDuration;
    private Runnable mDurationTestCallback = null;
    protected volatile Handler mHandler;
    protected Toast mToast = null;

    class C13101 implements Runnable {
        C13101() throws  {
        }

        public void run() throws  {
            if (ToastThread.this.mToast != null) {
                ToastThread.this.cancel();
                ToastThread.this.mToast = null;
            }
        }
    }

    class C13112 implements Runnable {
        C13112() throws  {
        }

        public void run() throws  {
            ToastThread.this.getLooper().quit();
        }
    }

    class C13123 implements Runnable {
        C13123() throws  {
        }

        public void run() throws  {
            if (ToastThread.this.mToast != null) {
                ToastThread.this.cancel();
                ToastThread.this.mToast = null;
            }
        }
    }

    class C13134 implements Runnable {
        C13134() throws  {
        }

        public void run() throws  {
            ToastThread.this.getLooper().quit();
        }
    }

    class C13145 implements Runnable {
        C13145() throws  {
        }

        public void run() throws  {
            try {
                synchronized (ToastThread.this) {
                    if (ToastThread.this.mDuration > 0 && ToastThread.this.mToast != null && ToastThread.this.mHandler != null && Thread.currentThread().isAlive()) {
                        ToastThread.this.mToast.show();
                        ToastThread.this.mHandler.postDelayed(ToastThread.this.mDurationTestCallback, 500);
                        ToastThread $r3 = ToastThread.this;
                        $r3.mDuration -= 500;
                    }
                }
            } catch (Exception $r1) {
                Logger.ee("Error stopping toast", $r1);
            }
        }
    }

    public abstract Toast show() throws ;

    public ToastThread(String $r1) throws  {
        super($r1, -8);
    }

    public void stopToast() throws  {
        try {
            synchronized (this) {
                if (this.mHandler == null) {
                    return;
                }
                this.mHandler.postDelayed(new C13101(), 1000);
                this.mHandler.postDelayed(new C13112(), 11000);
                this.mHandler.removeCallbacks(this.mDurationTestCallback);
                this.mHandler = null;
            }
        } catch (Exception $r1) {
            Log.e(Logger.TAG, "Error stopping toast: " + $r1.getMessage());
            $r1.printStackTrace();
        }
    }

    public final void stopToastImmediately() throws  {
        try {
            synchronized (this) {
                if (this.mHandler == null) {
                    return;
                }
                this.mHandler.removeCallbacks(this.mDurationTestCallback);
                this.mHandler.post(new C13123());
                this.mHandler.postDelayed(new C13134(), 10000);
                this.mHandler = null;
            }
        } catch (Exception $r1) {
            Log.e(Logger.TAG, "Error stopping toast: " + $r1.getMessage());
            $r1.printStackTrace();
        }
    }

    protected void onLooperPrepared() throws  {
        this.mHandler = new Handler();
        this.mToast = show();
        if (this.mToast == null) {
            Log.e(Logger.TAG, "Toast is not initialized properly: stopping the thread");
            getLooper().quit();
        }
    }

    protected void cancel() throws  {
        this.mToast.cancel();
    }

    protected void setCustomDuration(long $l0) throws  {
        this.mDuration = $l0;
        if (this.mHandler != null) {
            this.mDurationTestCallback = new C13145();
            this.mHandler.postDelayed(this.mDurationTestCallback, 500);
        }
    }
}
