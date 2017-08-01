package com.google.android.gms.common.api;

import android.support.annotation.NonNull;
import dalvik.annotation.Signature;

/* compiled from: dalvik_source_com.waze.apk */
public interface ResultCallback<R extends Result> {
    void onResult(@NonNull @Signature({"(TR;)V"}) R r) throws ;
}
