package com.google.android.gms.people.model;

/* compiled from: dalvik_source_com.waze.apk */
public interface AggregatedPerson extends Person {
    @Deprecated
    String getAccountName() throws ;

    String getAvatarUrl() throws ;

    Iterable<Long> getContactIds() throws ;

    String getFamilyName() throws ;

    String getGaiaId() throws ;

    String getGivenName() throws ;

    String getName() throws ;

    String getOwnerAccountName() throws ;

    String getOwnerPlusPageId() throws ;

    @Deprecated
    String getPlusPageGaiaId() throws ;

    String getQualifiedId() throws ;

    long getRowId() throws ;

    boolean hasContact() throws ;

    boolean hasPlusPerson() throws ;
}
