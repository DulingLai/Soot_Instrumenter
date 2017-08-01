package com.google.android.gms.location;

import android.app.PendingIntent;
import android.support.annotation.RequiresPermission;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import dalvik.annotation.Signature;

/* compiled from: dalvik_source_com.waze.apk */
public interface ActivityRecognitionApi {
    @RequiresPermission("com.google.android.gms.permission.ACTIVITY_RECOGNITION")
    ActivityRecognitionResult getLastActivity(GoogleApiClient googleApiClient) throws ;

    @RequiresPermission("com.google.android.gms.permission.ACTIVITY_RECOGNITION")
    PendingResult<Status> removeActivityUpdates(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Landroid/app/PendingIntent;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) GoogleApiClient googleApiClient, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Landroid/app/PendingIntent;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) PendingIntent pendingIntent) throws ;

    @RequiresPermission("com.google.android.gms.permission.ACTIVITY_RECOGNITION")
    PendingResult<Status> removeFloorChangeUpdates(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Landroid/app/PendingIntent;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) GoogleApiClient googleApiClient, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Landroid/app/PendingIntent;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) PendingIntent pendingIntent) throws ;

    @RequiresPermission("com.google.android.gms.permission.ACTIVITY_RECOGNITION")
    PendingResult<Status> removeGestureUpdates(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Landroid/app/PendingIntent;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) GoogleApiClient googleApiClient, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Landroid/app/PendingIntent;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) PendingIntent pendingIntent) throws ;

    @RequiresPermission("com.google.android.gms.permission.ACTIVITY_RECOGNITION")
    PendingResult<Status> removeSleepSegmentUpdates(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Landroid/app/PendingIntent;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) GoogleApiClient googleApiClient, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Landroid/app/PendingIntent;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) PendingIntent pendingIntent) throws ;

    @RequiresPermission("com.google.android.gms.permission.ACTIVITY_RECOGNITION")
    PendingResult<Status> requestActivityUpdates(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "J", "Landroid/app/PendingIntent;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) GoogleApiClient googleApiClient, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "J", "Landroid/app/PendingIntent;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) long j, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "J", "Landroid/app/PendingIntent;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) PendingIntent pendingIntent) throws ;

    @RequiresPermission("com.google.android.gms.permission.ACTIVITY_RECOGNITION")
    PendingResult<Status> requestFloorChangeUpdates(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Landroid/app/PendingIntent;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) GoogleApiClient googleApiClient, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Landroid/app/PendingIntent;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) PendingIntent pendingIntent) throws ;

    @RequiresPermission("com.google.android.gms.permission.ACTIVITY_RECOGNITION")
    PendingResult<Status> requestGestureUpdates(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/location/GestureRequest;", "Landroid/app/PendingIntent;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) GoogleApiClient googleApiClient, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/location/GestureRequest;", "Landroid/app/PendingIntent;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) GestureRequest gestureRequest, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/location/GestureRequest;", "Landroid/app/PendingIntent;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) PendingIntent pendingIntent) throws ;

    @RequiresPermission("com.google.android.gms.permission.ACTIVITY_RECOGNITION")
    PendingResult<Status> requestSleepSegmentUpdates(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Landroid/app/PendingIntent;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) GoogleApiClient googleApiClient, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Landroid/app/PendingIntent;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) PendingIntent pendingIntent) throws ;
}
