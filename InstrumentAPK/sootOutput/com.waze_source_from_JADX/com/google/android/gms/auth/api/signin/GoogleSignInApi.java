package com.google.android.gms.auth.api.signin;

import android.content.Intent;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import dalvik.annotation.Signature;

/* compiled from: dalvik_source_com.waze.apk */
public interface GoogleSignInApi {
    public static final String EXTRA_SIGN_IN_ACCOUNT = "signInAccount";

    Intent getSignInIntent(GoogleApiClient googleApiClient) throws ;

    GoogleSignInResult getSignInResultFromIntent(Intent intent) throws ;

    PendingResult<Status> revokeAccess(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) GoogleApiClient googleApiClient) throws ;

    PendingResult<Status> signOut(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) GoogleApiClient googleApiClient) throws ;

    OptionalPendingResult<GoogleSignInResult> silentSignIn(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", ")", "Lcom/google/android/gms/common/api/OptionalPendingResult", "<", "Lcom/google/android/gms/auth/api/signin/GoogleSignInResult;", ">;"}) GoogleApiClient googleApiClient) throws ;
}
