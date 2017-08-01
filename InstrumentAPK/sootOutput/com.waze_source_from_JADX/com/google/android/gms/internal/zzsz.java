package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzab;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: dalvik_source_com.waze.apk */
public class zzsz implements ThreadFactory {
    private final String Ne;
    private final AtomicInteger Nf;
    private final ThreadFactory Ng;
    private final int mPriority;

    public zzsz(String $r1) throws  {
        this($r1, 0);
    }

    public zzsz(String $r1, int $i0) throws  {
        this.Nf = new AtomicInteger();
        this.Ng = Executors.defaultThreadFactory();
        this.Ne = (String) zzab.zzb((Object) $r1, (Object) "Name must not be null");
        this.mPriority = $i0;
    }

    public Thread newThread(Runnable $r1) throws  {
        Thread $r4 = this.Ng.newThread(new zzta($r1, this.mPriority));
        String $r5 = this.Ne;
        $r4.setName(new StringBuilder(String.valueOf($r5).length() + 13).append($r5).append("[").append(this.Nf.getAndIncrement()).append("]").toString());
        return $r4;
    }
}
