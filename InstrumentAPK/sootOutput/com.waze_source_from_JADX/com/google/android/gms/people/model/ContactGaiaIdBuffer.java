package com.google.android.gms.people.model;

import com.google.android.gms.common.data.AbstractDataBuffer;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.people.internal.zzd;

/* compiled from: dalvik_source_com.waze.apk */
public final class ContactGaiaIdBuffer extends AbstractDataBuffer<ContactGaiaId> {
    public ContactGaiaIdBuffer(DataHolder $r1) throws  {
        super($r1);
    }

    public ContactGaiaId get(int $i0) throws  {
        return new zzd(this.DW, $i0);
    }

    public String toString() throws  {
        return "ContactGaiaId:size=" + getCount();
    }
}
