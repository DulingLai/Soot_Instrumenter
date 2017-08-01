package com.waze.ifs.async;

import android.util.Log;
import com.waze.Logger;

public class Waiter {
    private boolean mWaiting = false;

    public synchronized void prepare() throws  {
        this.mWaiting = true;
    }

    public synchronized boolean waiting() throws  {
        return this.mWaiting;
    }

    public synchronized void _wait() throws  {
        while (this.mWaiting) {
            try {
                wait();
            } catch (Exception $r1) {
                Log.e(Logger.TAG, "Error waiting: ", $r1);
            }
        }
        return;
    }

    public synchronized void _notify() throws  {
        this.mWaiting = false;
        notify();
    }

    public synchronized void _notifyAll() throws  {
        this.mWaiting = false;
        notifyAll();
    }
}
