package com.google.android.gms.people.model;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.people.internal.agg.PhoneEmailDecoder.EmailDecoder;
import com.google.android.gms.people.internal.agg.PhoneEmailDecoder.PhoneDecoder;
import com.google.android.gms.people.internal.zzr;

/* compiled from: dalvik_source_com.waze.apk */
public final class PersonBuffer extends DataBufferWithSyncInfo<Person> {
    private final PhoneDecoder aMk;
    private final EmailDecoder aMl;

    public PersonBuffer(DataHolder $r1, PhoneDecoder $r2, EmailDecoder $r3) throws  {
        super($r1);
        this.aMk = $r2;
        this.aMl = $r3;
    }

    public Person get(int $i0) throws  {
        return new zzr(this.DW, $i0, zzava(), this.aMk, this.aMl);
    }

    public String toString() throws  {
        return "People:size=" + getCount();
    }
}
