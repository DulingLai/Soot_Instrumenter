package com.google.android.gms.people.model;

import com.google.android.gms.common.data.Freezable;

/* compiled from: dalvik_source_com.waze.apk */
public interface Owner extends Freezable<Owner> {
    Owner freeze() throws ;

    @Deprecated
    String getAccountGaiaId() throws ;

    String getAccountId() throws ;

    String getAccountName() throws ;

    String getAvatarUrl() throws ;

    int getCoverPhotoHeight() throws ;

    String getCoverPhotoId() throws ;

    String getCoverPhotoUrl() throws ;

    int getCoverPhotoWidth() throws ;

    String getDasherDomain() throws ;

    String getDisplayName() throws ;

    String getFamilyName() throws ;

    String getGivenName() throws ;

    long getLastSuccessfulSyncFinishTimestamp() throws ;

    long getLastSyncFinishTimestamp() throws ;

    long getLastSyncStartTimestamp() throws ;

    int getLastSyncStatus() throws ;

    @Deprecated
    String getPlusPageGaiaId() throws ;

    String getPlusPageId() throws ;

    long getRowId() throws ;

    int isDasherAccount() throws ;

    boolean isPeriodicSyncEnabled() throws ;

    boolean isPlusEnabled() throws ;

    boolean isPlusPage() throws ;

    boolean isSyncCirclesToContactsEnabled() throws ;

    boolean isSyncEnabled() throws ;

    boolean isSyncEvergreenToContactsEnabled() throws ;

    boolean isSyncMeToContactsEnabled() throws ;

    @Deprecated
    boolean isSyncToContactsEnabled() throws ;
}
