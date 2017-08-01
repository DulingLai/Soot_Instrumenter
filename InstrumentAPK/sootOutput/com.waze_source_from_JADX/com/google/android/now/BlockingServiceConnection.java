package com.google.android.now;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

class BlockingServiceConnection implements ServiceConnection {
    private static final String TAG = "BlockingServiceConnection";
    private final BlockingQueue<IBinder> mBlockingQueue = new LinkedBlockingQueue();
    private boolean mBound = false;
    private AtomicBoolean mUsed = new AtomicBoolean(false);

    BlockingServiceConnection() throws  {
    }

    public void onServiceConnected(ComponentName name, IBinder $r2) throws  {
        this.mBound = true;
        this.mBlockingQueue.clear();
        this.mBlockingQueue.add($r2);
    }

    public void onServiceDisconnected(ComponentName name) throws  {
        this.mBound = false;
    }

    public IBinder getService() throws InterruptedException {
        if (this.mUsed.get()) {
            throw new IllegalStateException();
        }
        this.mUsed.set(true);
        return (IBinder) this.mBlockingQueue.take();
    }

    public void unbindServiceIfConnected(final Context $r1) throws  {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            public void run() throws  {
                if (BlockingServiceConnection.this.mBound) {
                    $r1.unbindService(BlockingServiceConnection.this);
                } else {
                    Log.w(BlockingServiceConnection.TAG, "Service disconnected before unbinding");
                }
            }
        });
    }
}
