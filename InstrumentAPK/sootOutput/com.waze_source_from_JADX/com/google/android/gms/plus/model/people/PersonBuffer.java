package com.google.android.gms.plus.model.people;

import com.google.android.gms.common.data.AbstractDataBuffer;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.zzd;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.plus.internal.model.people.PersonEntity;
import com.google.android.gms.plus.internal.model.people.zzk;

/* compiled from: dalvik_source_com.waze.apk */
public final class PersonBuffer extends AbstractDataBuffer<Person> {
    private final zzd<PersonEntity> aZq;

    public PersonBuffer(DataHolder $r1) throws  {
        super($r1);
        if ($r1.zzava() == null || !$r1.zzava().getBoolean("com.google.android.gms.plus.IsSafeParcelable", false)) {
            this.aZq = null;
        } else {
            this.aZq = new zzd($r1, PersonEntity.CREATOR);
        }
    }

    public Person get(int $i0) throws  {
        return this.aZq != null ? (Person) ((SafeParcelable) this.aZq.get($i0)) : new zzk(this.DW, $i0);
    }
}
