package com.google.android.gms.common.data;

import android.os.Bundle;
import com.google.android.gms.common.api.Releasable;
import dalvik.annotation.Signature;
import java.util.Iterator;

/* compiled from: dalvik_source_com.waze.apk */
public interface DataBuffer<T> extends Releasable, Iterable<T> {
    @Deprecated
    void close() throws ;

    T get(@Signature({"(I)TT;"}) int i) throws ;

    int getCount() throws ;

    @Deprecated
    boolean isClosed() throws ;

    Iterator<T> iterator() throws ;

    void release() throws ;

    Iterator<T> singleRefIterator() throws ;

    Bundle zzava() throws ;
}
