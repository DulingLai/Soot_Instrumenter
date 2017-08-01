package com.google.android.gms.common.data;

/* compiled from: dalvik_source_com.waze.apk */
public interface Freezable<T> {
    T freeze() throws ;

    boolean isDataValid() throws ;
}
