package com.google.android.gms.plus;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.plus.model.people.Person;
import com.google.android.gms.plus.model.people.PersonBuffer;
import dalvik.annotation.Signature;
import java.util.Collection;

/* compiled from: dalvik_source_com.waze.apk */
public interface People {

    /* compiled from: dalvik_source_com.waze.apk */
    public interface LoadPeopleResult extends Releasable, Result {
        String getNextPageToken() throws ;

        PersonBuffer getPersonBuffer() throws ;
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public interface OrderBy {
        public static final int ALPHABETICAL = 0;
        public static final int BEST = 1;
    }

    @Deprecated
    Person getCurrentPerson(@Deprecated GoogleApiClient googleApiClient) throws ;

    PendingResult<LoadPeopleResult> load(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/util/Collection", "<", "Ljava/lang/String;", ">;)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/plus/People$LoadPeopleResult;", ">;"}) GoogleApiClient googleApiClient, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/util/Collection", "<", "Ljava/lang/String;", ">;)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/plus/People$LoadPeopleResult;", ">;"}) Collection<String> collection) throws ;

    PendingResult<LoadPeopleResult> load(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "[", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/plus/People$LoadPeopleResult;", ">;"}) GoogleApiClient googleApiClient, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "[", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/plus/People$LoadPeopleResult;", ">;"}) String... strArr) throws ;

    PendingResult<LoadPeopleResult> loadConnected(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/plus/People$LoadPeopleResult;", ">;"}) GoogleApiClient googleApiClient) throws ;

    PendingResult<LoadPeopleResult> loadVisible(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "I", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/plus/People$LoadPeopleResult;", ">;"}) GoogleApiClient googleApiClient, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "I", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/plus/People$LoadPeopleResult;", ">;"}) int i, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "I", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/plus/People$LoadPeopleResult;", ">;"}) String str) throws ;

    PendingResult<LoadPeopleResult> loadVisible(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/plus/People$LoadPeopleResult;", ">;"}) GoogleApiClient googleApiClient, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/plus/People$LoadPeopleResult;", ">;"}) String str) throws ;
}
