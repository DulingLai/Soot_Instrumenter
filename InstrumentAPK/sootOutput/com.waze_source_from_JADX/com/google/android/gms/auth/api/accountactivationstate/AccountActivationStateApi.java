package com.google.android.gms.auth.api.accountactivationstate;

import android.accounts.Account;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import dalvik.annotation.Signature;

/* compiled from: dalvik_source_com.waze.apk */
public interface AccountActivationStateApi {

    /* compiled from: dalvik_source_com.waze.apk */
    public interface StateType {
        public static final int ACTIVE = 1;
        public static final int INACTIVE = 2;
        public static final int INCOGNITO = 3;
    }

    PendingResult<Status> setAccountActivationState(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Landroid/accounts/Account;", "I)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) GoogleApiClient googleApiClient, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Landroid/accounts/Account;", "I)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) Account account, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Landroid/accounts/Account;", "I)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) int i) throws ;
}
