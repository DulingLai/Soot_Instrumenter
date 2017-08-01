package com.google.android.gms.people;

import android.net.Uri;
import com.google.android.gms.common.api.BooleanResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import dalvik.annotation.Signature;

/* compiled from: dalvik_source_com.waze.apk */
public interface ContactsSync {
    PendingResult<BooleanResult> isSyncToContactsEnabled(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/BooleanResult;", ">;"}) GoogleApiClient googleApiClient) throws ;

    PendingResult<Result> setSyncToContactsEnabled(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Z)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Result;", ">;"}) GoogleApiClient googleApiClient, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Z)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Result;", ">;"}) boolean z) throws ;

    PendingResult<Result> setSyncToContactsSettings(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Z[", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Result;", ">;"}) GoogleApiClient googleApiClient, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Z[", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Result;", ">;"}) String str, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Z[", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Result;", ">;"}) boolean z, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Z[", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Result;", ">;"}) String[] strArr) throws ;

    PendingResult<Result> syncRawContact(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Landroid/net/Uri;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Result;", ">;"}) GoogleApiClient googleApiClient, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Landroid/net/Uri;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Result;", ">;"}) Uri uri) throws ;
}
