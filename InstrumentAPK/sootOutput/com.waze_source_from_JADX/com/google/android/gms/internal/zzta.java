package com.google.android.gms.internal;

import android.os.Process;

/* compiled from: dalvik_source_com.waze.apk */
class zzta implements Runnable {
    private final int mPriority;
    private final Runnable zzw;

    public zzta(Runnable $r1, int $i0) throws  {
        this.zzw = $r1;
        this.mPriority = $i0;
    }

    public void run() throws  {
        Process.setThreadPriority(this.mPriority);
        this.zzw.run();
    }
}
