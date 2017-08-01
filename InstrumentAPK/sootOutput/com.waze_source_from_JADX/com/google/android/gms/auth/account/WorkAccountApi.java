package com.google.android.gms.auth.account;

import android.accounts.Account;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import dalvik.annotation.Signature;

/* compiled from: dalvik_source_com.waze.apk */
public interface WorkAccountApi {

    /* compiled from: dalvik_source_com.waze.apk */
    public interface AddAccountResult extends Result {
        Account getAccount() throws ;
    }

    PendingResult<AddAccountResult> addWorkAccount(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/auth/account/WorkAccountApi$AddAccountResult;", ">;"}) GoogleApiClient googleApiClient, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/auth/account/WorkAccountApi$AddAccountResult;", ">;"}) String str) throws ;

    PendingResult<Result> removeWorkAccount(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Landroid/accounts/Account;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Result;", ">;"}) GoogleApiClient googleApiClient, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Landroid/accounts/Account;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Result;", ">;"}) Account account) throws ;

    void setWorkAuthenticatorEnabled(GoogleApiClient googleApiClient, boolean z) throws ;
}
