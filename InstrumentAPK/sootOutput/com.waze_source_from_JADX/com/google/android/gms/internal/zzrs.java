package com.google.android.gms.internal;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* compiled from: dalvik_source_com.waze.apk */
public abstract class zzrs {
    private static final ExecutorService Fg = new ThreadPoolExecutor(0, 4, 60, TimeUnit.SECONDS, new LinkedBlockingQueue(), new zzsz("GAC_Transform"));

    public static ExecutorService zzatt() throws  {
        return Fg;
    }
}
