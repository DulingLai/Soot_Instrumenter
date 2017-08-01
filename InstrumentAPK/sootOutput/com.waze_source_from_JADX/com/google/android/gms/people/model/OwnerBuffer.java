package com.google.android.gms.people.model;

import com.google.android.gms.common.data.AbstractDataBuffer;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.people.internal.zzj;

/* compiled from: dalvik_source_com.waze.apk */
public final class OwnerBuffer extends AbstractDataBuffer<Owner> {
    public OwnerBuffer(DataHolder $r1) throws  {
        super($r1);
    }

    public Owner get(int $i0) throws  {
        return new zzj(this.DW, $i0);
    }

    public String toString() throws  {
        return "Owner:size=" + getCount();
    }
}
