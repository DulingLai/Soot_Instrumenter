package com.google.android.gms.location;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import dalvik.annotation.Signature;

/* compiled from: dalvik_source_com.waze.apk */
public interface SettingsApi {
    PendingResult<LocationSettingsResult> checkLocationSettings(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/location/LocationSettingsRequest;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/location/LocationSettingsResult;", ">;"}) GoogleApiClient googleApiClient, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/location/LocationSettingsRequest;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/location/LocationSettingsResult;", ">;"}) LocationSettingsRequest locationSettingsRequest) throws ;
}
