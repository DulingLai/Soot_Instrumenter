package com.google.android.gms.location.reporting;

import android.accounts.Account;
import android.content.Intent;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.location.places.PlaceReport;
import dalvik.annotation.Signature;
import java.util.ArrayList;

/* compiled from: dalvik_source_com.waze.apk */
public interface Reporting {
    public static final String DELETE_OPERATION_ACTION = "com.google.android.gms.location.reporting.DELETE_OPERATION";
    public static final String SETTINGS_CHANGED = "com.google.android.gms.location.reporting.SETTINGS_CHANGED";

    /* compiled from: dalvik_source_com.waze.apk */
    public interface ReportingStateResult extends Result {
        int getDeviceTag() throws ;

        int getExpectedOptInStatusCode() throws ;

        int getHistoryEnabledSetting() throws ;

        int getReportingEnabledSetting() throws ;

        boolean isActive() throws ;

        boolean isAllowed() throws ;

        @Deprecated
        boolean isDeferringToMaps() throws ;

        boolean isHistoryEnabled() throws ;

        boolean isOptedIn() throws ;

        boolean isReportingEnabled() throws ;

        boolean shouldOptIn() throws ;

        @Deprecated
        boolean shouldOptInInsistent() throws ;

        @Deprecated
        boolean shouldOptInLenient() throws ;

        String toString() throws ;
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public interface ReportingUploadResult extends Result {
        long getRequestId() throws ;

        String toString() throws ;
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class DeletionHelper {
        public static ArrayList<Deletion> extractDeletions(@Signature({"(", "Landroid/content/Intent;", ")", "Ljava/util/ArrayList", "<", "Lcom/google/android/gms/location/reporting/Deletion;", ">;"}) Intent $r0) throws  {
            zzab.zzbn(Reporting.DELETE_OPERATION_ACTION.equals($r0.getAction()));
            return !$r0.hasExtra("deletions") ? null : $r0.getParcelableArrayListExtra("deletions");
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class Setting {
        @Deprecated
        public static final int AMBIGUOUS = -2;
        public static final int OFF = -1;
        public static final int ON = 1;
        public static final int UNDEFINED = 0;
        public static final int UNKNOWN_OFF = -3;
        public static final int UNKNOWN_ON = 99;

        private Setting() throws  {
        }

        public static boolean isOff(int $i0) throws  {
            return $i0 < 0;
        }

        public static boolean isOn(int $i0) throws  {
            return $i0 > 0;
        }

        public static int sanitize(int $i0) throws  {
            switch ($i0) {
                case -2:
                case -1:
                case 0:
                case 1:
                    return $i0;
                default:
                    return isOn($i0) ? (byte) 99 : (byte) -3;
            }
        }

        public static String toString(int $i0) throws  {
            switch ($i0) {
                case -2:
                    return "Ambiguous";
                case -1:
                    return "Off";
                case 0:
                    return "Undefined";
                case 1:
                    return "On";
                default:
                    return "Unknown";
            }
        }
    }

    PendingResult<Status> cancelUpload(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "J)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) GoogleApiClient googleApiClient, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "J)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) long j) throws ;

    PendingResult<ReportingStateResult> getReportingStateSafe(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Landroid/accounts/Account;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/location/reporting/Reporting$ReportingStateResult;", ">;"}) GoogleApiClient googleApiClient, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Landroid/accounts/Account;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/location/reporting/Reporting$ReportingStateResult;", ">;"}) Account account) throws ;

    PendingResult<Status> reportPlace(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Landroid/accounts/Account;", "Lcom/google/android/gms/location/places/PlaceReport;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) GoogleApiClient googleApiClient, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Landroid/accounts/Account;", "Lcom/google/android/gms/location/places/PlaceReport;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) Account account, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Landroid/accounts/Account;", "Lcom/google/android/gms/location/places/PlaceReport;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) PlaceReport placeReport) throws ;

    PendingResult<ReportingUploadResult> requestUpload(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/location/reporting/UploadRequest;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/location/reporting/Reporting$ReportingUploadResult;", ">;"}) GoogleApiClient googleApiClient, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/location/reporting/UploadRequest;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/location/reporting/Reporting$ReportingUploadResult;", ">;"}) UploadRequest uploadRequest) throws ;

    PendingResult<Status> tryOptIn(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Landroid/accounts/Account;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) GoogleApiClient googleApiClient, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Landroid/accounts/Account;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) Account account) throws ;

    PendingResult<Status> tryOptInRequest(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/location/reporting/OptInRequest;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) GoogleApiClient googleApiClient, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/location/reporting/OptInRequest;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) OptInRequest optInRequest) throws ;
}
