package com.google.android.gms.common;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.google.android.gms.common.internal.zzab;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* compiled from: dalvik_source_com.waze.apk */
public class zza implements ServiceConnection {
    private final BlockingQueue<IBinder> BA = new LinkedBlockingQueue();
    boolean Bz = false;

    public void onServiceConnected(ComponentName componentName, IBinder $r2) throws  {
        this.BA.add($r2);
    }

    public void onServiceDisconnected(ComponentName componentName) throws  {
    }

    public IBinder zzara() throws InterruptedException {
        zzab.zzgq("BlockingServiceConnection.getService() called on main thread");
        if (this.Bz) {
            throw new IllegalStateException("Cannot call get on this connection more than once");
        }
        this.Bz = true;
        return (IBinder) this.BA.take();
    }

    public IBinder zzb(long $l0, TimeUnit $r1) throws InterruptedException, TimeoutException {
        zzab.zzgq("BlockingServiceConnection.getServiceWithTimeout() called on main thread");
        if (this.Bz) {
            throw new IllegalStateException("Cannot call get on this connection more than once");
        }
        this.Bz = true;
        IBinder $r5 = (IBinder) this.BA.poll($l0, $r1);
        if ($r5 != null) {
            return $r5;
        }
        throw new TimeoutException("Timed out waiting for the service connection");
    }
}
