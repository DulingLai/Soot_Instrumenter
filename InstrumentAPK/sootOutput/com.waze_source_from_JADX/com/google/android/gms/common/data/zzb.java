package com.google.android.gms.common.data;

import com.google.android.gms.common.internal.zzab;
import dalvik.annotation.Signature;
import java.util.Iterator;
import java.util.NoSuchElementException;

/* compiled from: dalvik_source_com.waze.apk */
public class zzb<T> implements Iterator<T> {
    protected final DataBuffer<T> GL;
    protected int GM = -1;

    public zzb(@Signature({"(", "Lcom/google/android/gms/common/data/DataBuffer", "<TT;>;)V"}) DataBuffer<T> $r1) throws  {
        this.GL = (DataBuffer) zzab.zzag($r1);
    }

    public boolean hasNext() throws  {
        return this.GM < this.GL.getCount() + -1;
    }

    public T next() throws  {
        if (hasNext()) {
            DataBuffer $r4 = this.GL;
            int $i0 = this.GM + 1;
            this.GM = $i0;
            return $r4.get($i0);
        }
        throw new NoSuchElementException("Cannot advance the iterator beyond " + this.GM);
    }

    public void remove() throws  {
        throw new UnsupportedOperationException("Cannot remove elements from a DataBufferIterator");
    }
}
