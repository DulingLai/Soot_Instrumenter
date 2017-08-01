package com.google.android.gms.internal;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* compiled from: dalvik_source_com.waze.apk */
public abstract class zzqz {
    private static final ExecutorService Fg = Executors.newFixedThreadPool(2, new zzsz("GAC_Executor"));

    public static ExecutorService zzatt() throws  {
        return Fg;
    }
}
