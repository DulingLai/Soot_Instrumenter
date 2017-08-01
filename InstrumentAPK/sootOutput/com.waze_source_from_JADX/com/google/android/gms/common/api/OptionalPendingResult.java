package com.google.android.gms.common.api;

/* compiled from: dalvik_source_com.waze.apk */
public abstract class OptionalPendingResult<R extends Result> extends PendingResult<R> {
    public abstract R get() throws ;

    public abstract boolean isDone() throws ;
}
