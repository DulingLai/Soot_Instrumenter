package com.google.android.gms.people.identity.internal;

import com.google.android.gms.people.identity.PersonFactory.ContactData;
import com.google.android.gms.people.identity.PersonFactory.OfflineDatabaseData;
import com.google.android.gms.people.identity.PersonFactory.ServiceData;
import com.google.android.gms.people.identity.PersonListFactory;
import com.google.android.gms.people.identity.PersonListFactory.PersonListItemFactory;
import dalvik.annotation.Signature;
import java.util.Collections;
import java.util.List;

/* compiled from: dalvik_source_com.waze.apk */
public abstract class zzg<PersonType> implements PersonListFactory<PersonType> {
    public PersonListItemFactory<PersonType> buildList(@Signature({"(", "Lcom/google/android/gms/people/identity/PersonFactory$ServiceData;", "[", "Lcom/google/android/gms/people/identity/PersonFactory$ContactData;", "Lcom/google/android/gms/people/identity/PersonFactory$OfflineDatabaseData;", ")", "Lcom/google/android/gms/people/identity/PersonListFactory$PersonListItemFactory", "<TPersonType;>;"}) ServiceData $r1, @Signature({"(", "Lcom/google/android/gms/people/identity/PersonFactory$ServiceData;", "[", "Lcom/google/android/gms/people/identity/PersonFactory$ContactData;", "Lcom/google/android/gms/people/identity/PersonFactory$OfflineDatabaseData;", ")", "Lcom/google/android/gms/people/identity/PersonListFactory$PersonListItemFactory", "<TPersonType;>;"}) ContactData[] $r2, @Signature({"(", "Lcom/google/android/gms/people/identity/PersonFactory$ServiceData;", "[", "Lcom/google/android/gms/people/identity/PersonFactory$ContactData;", "Lcom/google/android/gms/people/identity/PersonFactory$OfflineDatabaseData;", ")", "Lcom/google/android/gms/people/identity/PersonListFactory$PersonListItemFactory", "<TPersonType;>;"}) OfflineDatabaseData $r3) throws  {
        List $r5 = $r1 != null ? zza($r1) : $r3 != null ? zza($r3) : Collections.emptyList();
        final List $r6 = $r2 != null ? zza($r2) : Collections.emptyList();
        return new PersonListItemFactory<PersonType>(this) {
            final /* synthetic */ zzg aNC;

            public PersonType get(@Signature({"(I)TPersonType;"}) int $i0) throws  {
                return $i0 < $r5.size() ? $r5.get($i0) : $r6.get($i0 - $r5.size());
            }

            public int getCount() throws  {
                return $r5.size() + $r6.size();
            }

            public String getQualifiedId(int $i0) throws  {
                return this.aNC.zzas(get($i0));
            }
        };
    }

    protected abstract List<PersonType> zza(@Signature({"(", "Lcom/google/android/gms/people/identity/PersonFactory$OfflineDatabaseData;", ")", "Ljava/util/List", "<TPersonType;>;"}) OfflineDatabaseData offlineDatabaseData) throws ;

    protected abstract List<PersonType> zza(@Signature({"(", "Lcom/google/android/gms/people/identity/PersonFactory$ServiceData;", ")", "Ljava/util/List", "<TPersonType;>;"}) ServiceData serviceData) throws ;

    protected abstract List<PersonType> zza(@Signature({"([", "Lcom/google/android/gms/people/identity/PersonFactory$ContactData;", ")", "Ljava/util/List", "<TPersonType;>;"}) ContactData[] contactDataArr) throws ;

    protected abstract String zzas(@Signature({"(TPersonType;)", "Ljava/lang/String;"}) PersonType personType) throws ;
}
