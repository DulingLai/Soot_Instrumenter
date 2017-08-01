package com.google.android.gms.auth.api.credentials;

import android.app.PendingIntent;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import dalvik.annotation.Signature;

/* compiled from: dalvik_source_com.waze.apk */
public interface CredentialsApi {
    public static final int ACTIVITY_RESULT_ADD_ACCOUNT = 1000;
    public static final int ACTIVITY_RESULT_OTHER_ACCOUNT = 1001;
    public static final int CREDENTIAL_PICKER_REQUEST_CODE = 2000;

    PendingResult<Status> delete(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/auth/api/credentials/Credential;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) GoogleApiClient googleApiClient, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/auth/api/credentials/Credential;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) Credential credential) throws ;

    PendingResult<Status> disableAutoSignIn(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) GoogleApiClient googleApiClient) throws ;

    PendingResult<GeneratePasswordRequestResult> generatePassword(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/auth/api/credentials/GeneratePasswordRequestResult;", ">;"}) GoogleApiClient googleApiClient) throws ;

    PendingResult<GeneratePasswordRequestResult> generatePassword(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/auth/api/credentials/PasswordSpecification;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/auth/api/credentials/GeneratePasswordRequestResult;", ">;"}) GoogleApiClient googleApiClient, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/auth/api/credentials/PasswordSpecification;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/auth/api/credentials/GeneratePasswordRequestResult;", ">;"}) PasswordSpecification passwordSpecification) throws ;

    PendingIntent getHintPickerIntent(GoogleApiClient googleApiClient, HintRequest hintRequest) throws ;

    PendingResult<CredentialRequestResult> request(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/auth/api/credentials/CredentialRequest;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/auth/api/credentials/CredentialRequestResult;", ">;"}) GoogleApiClient googleApiClient, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/auth/api/credentials/CredentialRequest;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/auth/api/credentials/CredentialRequestResult;", ">;"}) CredentialRequest credentialRequest) throws ;

    PendingResult<Status> save(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/auth/api/credentials/Credential;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) GoogleApiClient googleApiClient, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/auth/api/credentials/Credential;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) Credential credential) throws ;
}
