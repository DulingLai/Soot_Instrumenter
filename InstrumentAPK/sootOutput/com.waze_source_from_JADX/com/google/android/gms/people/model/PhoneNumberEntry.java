package com.google.android.gms.people.model;

import android.support.annotation.RequiresPermission;

/* compiled from: dalvik_source_com.waze.apk */
public interface PhoneNumberEntry {
    String getFocusContactId() throws ;

    Long getLastUpdateTime() throws ;

    String getName() throws ;

    String getOwnerAccountName() throws ;

    String getPhoneNumber() throws ;

    @RequiresPermission("android.permission.READ_CONTACTS")
    String getPhotoUri() throws ;
}
