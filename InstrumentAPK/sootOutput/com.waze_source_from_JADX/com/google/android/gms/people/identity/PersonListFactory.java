package com.google.android.gms.people.identity;

import com.google.android.gms.people.identity.PersonFactory.ContactData;
import com.google.android.gms.people.identity.PersonFactory.OfflineDatabaseData;
import com.google.android.gms.people.identity.PersonFactory.ServiceData;
import dalvik.annotation.Signature;

/* compiled from: dalvik_source_com.waze.apk */
public interface PersonListFactory<PersonType> {

    /* compiled from: dalvik_source_com.waze.apk */
    public interface PersonListItemFactory<PersonType> {
        PersonType get(@Signature({"(I)TPersonType;"}) int i) throws ;

        int getCount() throws ;

        String getQualifiedId(int i) throws ;
    }

    PersonListItemFactory<PersonType> buildList(@Signature({"(", "Lcom/google/android/gms/people/identity/PersonFactory$ServiceData;", "[", "Lcom/google/android/gms/people/identity/PersonFactory$ContactData;", "Lcom/google/android/gms/people/identity/PersonFactory$OfflineDatabaseData;", ")", "Lcom/google/android/gms/people/identity/PersonListFactory$PersonListItemFactory", "<TPersonType;>;"}) ServiceData serviceData, @Signature({"(", "Lcom/google/android/gms/people/identity/PersonFactory$ServiceData;", "[", "Lcom/google/android/gms/people/identity/PersonFactory$ContactData;", "Lcom/google/android/gms/people/identity/PersonFactory$OfflineDatabaseData;", ")", "Lcom/google/android/gms/people/identity/PersonListFactory$PersonListItemFactory", "<TPersonType;>;"}) ContactData[] contactDataArr, @Signature({"(", "Lcom/google/android/gms/people/identity/PersonFactory$ServiceData;", "[", "Lcom/google/android/gms/people/identity/PersonFactory$ContactData;", "Lcom/google/android/gms/people/identity/PersonFactory$OfflineDatabaseData;", ")", "Lcom/google/android/gms/people/identity/PersonListFactory$PersonListItemFactory", "<TPersonType;>;"}) OfflineDatabaseData offlineDatabaseData) throws ;
}
