package com.google.android.gms.people.model;

/* compiled from: dalvik_source_com.waze.apk */
public interface Person extends Affinities {
    @Deprecated
    String getAccountName() throws ;

    double getAffinity1() throws ;

    double getAffinity2() throws ;

    double getAffinity3() throws ;

    double getAffinity4() throws ;

    double getAffinity5() throws ;

    String getAvatarUrl() throws ;

    String[] getBelongingCircleIds() throws ;

    Iterable<EmailAddress> getEmailAddresses() throws ;

    String getFamilyName() throws ;

    String getGaiaId() throws ;

    String getGivenName() throws ;

    int getInViewerDomain() throws ;

    String getInteractionRankSortKey() throws ;

    long getLastModifiedTime() throws ;

    String getName() throws ;

    String getNameSortKey() throws ;

    String getOwnerAccountName() throws ;

    String getOwnerPlusPageId() throws ;

    Iterable<PhoneNumber> getPhoneNumbers() throws ;

    @Deprecated
    String getPlusPageGaiaId() throws ;

    int getProfileType() throws ;

    String getQualifiedId() throws ;

    long getRowId() throws ;

    boolean isBlocked() throws ;

    boolean isNameVerified() throws ;
}
