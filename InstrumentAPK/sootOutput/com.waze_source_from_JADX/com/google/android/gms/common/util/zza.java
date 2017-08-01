package com.google.android.gms.common.util;

import android.support.v4.util.ArrayMap;
import dalvik.annotation.Signature;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;

/* compiled from: dalvik_source_com.waze.apk */
public class zza<E> extends AbstractSet<E> {
    private final ArrayMap<E, E> MH;

    public zza() throws  {
        this.MH = new ArrayMap();
    }

    public zza(int $i0) throws  {
        this.MH = new ArrayMap($i0);
    }

    public zza(@Signature({"(", "Ljava/util/Collection", "<TE;>;)V"}) Collection<E> $r1) throws  {
        this($r1.size());
        addAll($r1);
    }

    public boolean add(@Signature({"(TE;)Z"}) E $r1) throws  {
        if (this.MH.containsKey($r1)) {
            return false;
        }
        this.MH.put($r1, $r1);
        return true;
    }

    public boolean addAll(@Signature({"(", "Ljava/util/Collection", "<+TE;>;)Z"}) Collection<? extends E> $r1) throws  {
        return $r1 instanceof zza ? zza((zza) $r1) : super.addAll($r1);
    }

    public void clear() throws  {
        this.MH.clear();
    }

    public boolean contains(Object $r1) throws  {
        return this.MH.containsKey($r1);
    }

    public Iterator<E> iterator() throws  {
        return this.MH.keySet().iterator();
    }

    public boolean remove(Object $r1) throws  {
        if (!this.MH.containsKey($r1)) {
            return false;
        }
        this.MH.remove($r1);
        return true;
    }

    public int size() throws  {
        return this.MH.size();
    }

    public boolean zza(@Signature({"(", "Lcom/google/android/gms/common/util/zza", "<+TE;>;)Z"}) zza<? extends E> $r1) throws  {
        int $i0 = size();
        this.MH.putAll($r1.MH);
        return size() > $i0;
    }
}
