package com.google.android.gms.people.model;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.people.internal.zzc;

/* compiled from: dalvik_source_com.waze.apk */
public final class CircleBuffer extends DataBufferWithSyncInfo<Circle> {
    public CircleBuffer(DataHolder $r1) throws  {
        super($r1);
    }

    public Circle get(int $i0) throws  {
        return new zzc(this.DW, $i0, zzava());
    }

    public String toString() throws  {
        return "Circles:size=" + getCount();
    }
}
