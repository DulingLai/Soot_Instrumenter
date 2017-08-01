package com.google.android.gms.people.internal;

import java.util.Iterator;
import java.util.NoSuchElementException;

/* compiled from: dalvik_source_com.waze.apk */
public class zze<T> implements Iterable<T>, Iterator<T> {
    public boolean hasNext() throws  {
        return false;
    }

    public Iterator<T> iterator() throws  {
        return this;
    }

    public T next() throws  {
        throw new NoSuchElementException();
    }

    public void remove() throws  {
        throw new UnsupportedOperationException();
    }
}
