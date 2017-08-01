package com.google.android.gms.plus;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.plus.model.people.Person;
import dalvik.annotation.Signature;

/* compiled from: dalvik_source_com.waze.apk */
public interface FirstPartyPeople {

    /* compiled from: dalvik_source_com.waze.apk */
    public interface LoadPersonResult extends Result {
        Person getPerson() throws ;
    }

    PendingResult<LoadPersonResult> load(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/plus/FirstPartyPeople$LoadPersonResult;", ">;"}) GoogleApiClient googleApiClient, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/plus/FirstPartyPeople$LoadPersonResult;", ">;"}) String str) throws ;
}
