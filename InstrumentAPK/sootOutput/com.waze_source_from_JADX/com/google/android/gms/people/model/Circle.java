package com.google.android.gms.people.model;

/* compiled from: dalvik_source_com.waze.apk */
public interface Circle {
    @Deprecated
    String getAccountName() throws ;

    String getCircleId() throws ;

    String getCircleName() throws ;

    int getCircleType() throws ;

    long getLastModifiedTime() throws ;

    String getOwnerAccountName() throws ;

    String getOwnerPlusPageId() throws ;

    int getPeopleCount() throws ;

    @Deprecated
    String getPlusPageGaiaId() throws ;

    long getRowId() throws ;

    String getSortKey() throws ;

    int getVisibility() throws ;

    boolean isEnabledForSharing() throws ;

    @Deprecated
    boolean isSyncToContactsEnabled() throws ;
}
