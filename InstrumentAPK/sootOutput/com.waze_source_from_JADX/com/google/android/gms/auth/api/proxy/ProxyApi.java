package com.google.android.gms.auth.api.proxy;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import dalvik.annotation.Signature;

/* compiled from: dalvik_source_com.waze.apk */
public interface ProxyApi {

    /* compiled from: dalvik_source_com.waze.apk */
    public interface ProxyResult extends Result {
        ProxyResponse getResponse() throws ;
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public interface SpatulaHeaderResult extends Result {
        String getSpatulaHeader() throws ;
    }

    PendingResult<SpatulaHeaderResult> getSpatulaHeader(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/auth/api/proxy/ProxyApi$SpatulaHeaderResult;", ">;"}) GoogleApiClient googleApiClient) throws ;

    PendingResult<ProxyResult> performProxyRequest(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/auth/api/proxy/ProxyRequest;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/auth/api/proxy/ProxyApi$ProxyResult;", ">;"}) GoogleApiClient googleApiClient, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/auth/api/proxy/ProxyRequest;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/auth/api/proxy/ProxyApi$ProxyResult;", ">;"}) ProxyRequest proxyRequest) throws ;
}
