package com.google.android.gms.location;

import android.app.PendingIntent;
import android.support.annotation.RequiresPermission;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import dalvik.annotation.Signature;
import java.util.List;

/* compiled from: dalvik_source_com.waze.apk */
public interface GeofencingApi {
    @RequiresPermission("android.permission.ACCESS_FINE_LOCATION")
    PendingResult<Status> addGeofences(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/location/GeofencingRequest;", "Landroid/app/PendingIntent;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) GoogleApiClient googleApiClient, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/location/GeofencingRequest;", "Landroid/app/PendingIntent;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) GeofencingRequest geofencingRequest, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/location/GeofencingRequest;", "Landroid/app/PendingIntent;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) PendingIntent pendingIntent) throws ;

    @RequiresPermission("android.permission.ACCESS_FINE_LOCATION")
    @Deprecated
    PendingResult<Status> addGeofences(@Deprecated @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/util/List", "<", "Lcom/google/android/gms/location/Geofence;", ">;", "Landroid/app/PendingIntent;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) GoogleApiClient googleApiClient, @Deprecated @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/util/List", "<", "Lcom/google/android/gms/location/Geofence;", ">;", "Landroid/app/PendingIntent;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) List<Geofence> list, @Deprecated @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/util/List", "<", "Lcom/google/android/gms/location/Geofence;", ">;", "Landroid/app/PendingIntent;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) PendingIntent pendingIntent) throws ;

    PendingResult<Status> removeGeofences(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Landroid/app/PendingIntent;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) GoogleApiClient googleApiClient, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Landroid/app/PendingIntent;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) PendingIntent pendingIntent) throws ;

    PendingResult<Status> removeGeofences(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) GoogleApiClient googleApiClient, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) List<String> list) throws ;
}
