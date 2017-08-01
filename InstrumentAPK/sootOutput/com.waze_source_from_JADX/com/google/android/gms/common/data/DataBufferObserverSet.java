package com.google.android.gms.common.data;

import com.google.android.gms.common.data.DataBufferObserver.Observable;
import java.util.HashSet;
import java.util.Iterator;

/* compiled from: dalvik_source_com.waze.apk */
public final class DataBufferObserverSet implements Observable, DataBufferObserver {
    private HashSet<DataBufferObserver> GN = new HashSet();

    public void addObserver(DataBufferObserver $r1) throws  {
        this.GN.add($r1);
    }

    public void clear() throws  {
        this.GN.clear();
    }

    public boolean hasObservers() throws  {
        return !this.GN.isEmpty();
    }

    public void onDataChanged() throws  {
        Iterator $r2 = this.GN.iterator();
        while ($r2.hasNext()) {
            ((DataBufferObserver) $r2.next()).onDataChanged();
        }
    }

    public void onDataRangeChanged(int $i0, int $i1) throws  {
        Iterator $r2 = this.GN.iterator();
        while ($r2.hasNext()) {
            ((DataBufferObserver) $r2.next()).onDataRangeChanged($i0, $i1);
        }
    }

    public void onDataRangeInserted(int $i0, int $i1) throws  {
        Iterator $r2 = this.GN.iterator();
        while ($r2.hasNext()) {
            ((DataBufferObserver) $r2.next()).onDataRangeInserted($i0, $i1);
        }
    }

    public void onDataRangeMoved(int $i0, int $i1, int $i2) throws  {
        Iterator $r2 = this.GN.iterator();
        while ($r2.hasNext()) {
            ((DataBufferObserver) $r2.next()).onDataRangeMoved($i0, $i1, $i2);
        }
    }

    public void onDataRangeRemoved(int $i0, int $i1) throws  {
        Iterator $r2 = this.GN.iterator();
        while ($r2.hasNext()) {
            ((DataBufferObserver) $r2.next()).onDataRangeRemoved($i0, $i1);
        }
    }

    public void removeObserver(DataBufferObserver $r1) throws  {
        this.GN.remove($r1);
    }
}
