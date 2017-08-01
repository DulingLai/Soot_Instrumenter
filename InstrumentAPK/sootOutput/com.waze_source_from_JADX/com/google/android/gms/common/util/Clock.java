package com.google.android.gms.common.util;

/* compiled from: dalvik_source_com.waze.apk */
public interface Clock {
    long currentTimeMillis() throws ;

    long elapsedRealtime() throws ;

    long nanoTime() throws ;
}
