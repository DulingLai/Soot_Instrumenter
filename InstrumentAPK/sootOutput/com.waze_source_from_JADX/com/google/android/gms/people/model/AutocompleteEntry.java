package com.google.android.gms.people.model;

/* compiled from: dalvik_source_com.waze.apk */
public interface AutocompleteEntry {
    long getAndroidContactDataId() throws ;

    long getAndroidContactId() throws ;

    int getAutocompleteItemType() throws ;

    AvatarReference getAvatarReference() throws ;

    int getDataSource() throws ;

    String getFocusContactId() throws ;

    String getGaiaId() throws ;

    double getItemAffinity1() throws ;

    double getItemAffinity2() throws ;

    double getItemAffinity3() throws ;

    double getItemAffinity4() throws ;

    double getItemAffinity5() throws ;

    long getItemCertificateExpirationMillis() throws ;

    String getItemCertificateStatus() throws ;

    String getItemLoggingId1() throws ;

    String getItemLoggingId2() throws ;

    String getItemLoggingId3() throws ;

    String getItemLoggingId4() throws ;

    String getItemLoggingId5() throws ;

    String getItemValue() throws ;

    String getItemValueCustomLabel() throws ;

    int getItemValueType() throws ;

    String getOwnerAccountName() throws ;

    String getOwnerPlusPageId() throws ;

    String getPeopleV2Id() throws ;

    double getPersonAffinity1() throws ;

    double getPersonAffinity2() throws ;

    double getPersonAffinity3() throws ;

    double getPersonAffinity4() throws ;

    double getPersonAffinity5() throws ;

    String getPersonDisplayName() throws ;

    String getPersonKey() throws ;

    String getPersonLoggingId1() throws ;

    String getPersonLoggingId2() throws ;

    String getPersonLoggingId3() throws ;

    String getPersonLoggingId4() throws ;

    String getPersonLoggingId5() throws ;

    double getPrimarySortedAffinity() throws ;

    String getPrimarySortedLoggingId() throws ;

    int getRowIndex() throws ;

    double getSortedItemAffinity() throws ;

    String getSortedItemLoggingId() throws ;

    double getSortedPersonAffinity() throws ;

    String getSortedPersonLoggingId() throws ;
}
