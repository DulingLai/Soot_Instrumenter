package com.google.android.gms.people.model;

import android.content.Context;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.people.internal.zzs;

/* compiled from: dalvik_source_com.waze.apk */
public class PhoneNumberBuffer extends DataBufferWithSyncInfo<PhoneNumberEntry> {
    private final Context mContext;

    public PhoneNumberBuffer(DataHolder $r1, Context $r2) throws  {
        super($r1);
        this.mContext = $r2;
    }

    public PhoneNumberEntry get(int $i0) throws  {
        return new zzs(this.DW, $i0, this.DW.zzava(), this.mContext);
    }

    public String toString() throws  {
        return "PhoneNumberBuffer:size=" + getCount();
    }
}
