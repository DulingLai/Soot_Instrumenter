package com.google.android.gms.common.data;

import android.os.Bundle;
import dalvik.annotation.Signature;
import java.util.Iterator;

/* compiled from: dalvik_source_com.waze.apk */
public abstract class AbstractDataBuffer<T> implements DataBuffer<T> {
    protected final DataHolder DW;

    protected AbstractDataBuffer(DataHolder $r1) throws  {
        this.DW = $r1;
        if (this.DW != null) {
            this.DW.zzaa(this);
        }
    }

    @Deprecated
    public final void close() throws  {
        release();
    }

    public abstract T get(@Signature({"(I)TT;"}) int i) throws ;

    public int getCount() throws  {
        return this.DW == null ? 0 : this.DW.getCount();
    }

    @Deprecated
    public boolean isClosed() throws  {
        return this.DW == null || this.DW.isClosed();
    }

    public Iterator<T> iterator() throws  {
        return new zzb(this);
    }

    public void release() throws  {
        if (this.DW != null) {
            this.DW.close();
        }
    }

    public Iterator<T> singleRefIterator() throws  {
        return new zzg(this);
    }

    public Bundle zzava() throws  {
        return this.DW.zzava();
    }
}
