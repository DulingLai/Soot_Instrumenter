package com.google.android.gms.people.model;

import com.google.android.gms.common.data.AbstractDataBuffer;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.people.Autocomplete.ContactPreferredFields;
import com.google.android.gms.people.internal.autocomplete.zzg;

/* compiled from: dalvik_source_com.waze.apk */
public class ContactGroupPreferredFieldsBuffer extends AbstractDataBuffer<ContactPreferredFields> {
    public ContactGroupPreferredFieldsBuffer(DataHolder $r1) throws  {
        super($r1);
    }

    public ContactPreferredFields get(int $i0) throws  {
        return new zzg(this.DW, $i0);
    }

    public String toString() throws  {
        return "ContactPreferredFields:size=" + getCount();
    }
}
