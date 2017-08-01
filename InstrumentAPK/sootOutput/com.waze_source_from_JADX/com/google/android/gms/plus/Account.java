package com.google.android.gms.plus;

import android.support.annotation.RequiresPermission;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import dalvik.annotation.Signature;

/* compiled from: dalvik_source_com.waze.apk */
public interface Account {
    @Deprecated
    void clearDefaultAccount(@Deprecated GoogleApiClient googleApiClient) throws ;

    @RequiresPermission("android.permission.GET_ACCOUNTS")
    String getAccountName(GoogleApiClient googleApiClient) throws ;

    PendingResult<Status> revokeAccessAndDisconnect(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) GoogleApiClient googleApiClient) throws ;
}
