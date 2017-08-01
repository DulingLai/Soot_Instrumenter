package com.google.android.gms.people.identity.internal;

import android.content.Context;
import com.google.android.gms.people.identity.PersonFactory;
import com.google.android.gms.people.identity.PersonFactory.ContactData;
import com.google.android.gms.people.identity.PersonFactory.OfflineDatabaseData;
import com.google.android.gms.people.identity.PersonFactory.ServiceData;
import com.google.android.gms.people.identity.internal.models.PersonImpl;
import com.google.android.gms.people.identity.models.Person;

/* compiled from: dalvik_source_com.waze.apk */
public final class zzd extends zze<PersonImpl> implements PersonFactory<Person> {
    public /* synthetic */ Object build(Context $r1, Object $r2, ServiceData $r3, ContactData $r4, OfflineDatabaseData $r5) throws  {
        return super.zza($r1, $r2, $r3, $r4, $r5);
    }

    protected PersonImpl zzcda() throws  {
        return new PersonImpl();
    }
}
