package com.google.android.gms.internal;

import android.accounts.Account;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.location.places.PlaceReport;
import com.google.android.gms.location.reporting.OptInRequest;
import com.google.android.gms.location.reporting.Reporting;
import com.google.android.gms.location.reporting.Reporting.ReportingStateResult;
import com.google.android.gms.location.reporting.Reporting.ReportingUploadResult;
import com.google.android.gms.location.reporting.Reporting.Setting;
import com.google.android.gms.location.reporting.ReportingState;
import com.google.android.gms.location.reporting.UploadRequest;
import com.google.android.gms.location.reporting.UploadRequestResult;
import dalvik.annotation.Signature;

/* compiled from: dalvik_source_com.waze.apk */
public class zzxq implements Reporting {

    /* compiled from: dalvik_source_com.waze.apk */
    private static class zza implements ReportingStateResult {
        private final ReportingState azb;
        private final Status cp;

        public zza(Status $r1, @Nullable ReportingState $r2) throws  {
            this.cp = $r1;
            if ($r1.getStatusCode() == 0) {
                zzab.zzag($r2);
            }
            this.azb = $r2;
        }

        private void zzbtp() throws  {
            if (this.cp.getStatusCode() != 0) {
                String $r3 = String.valueOf(this.cp);
                throw new IllegalStateException(new StringBuilder(String.valueOf($r3).length() + 52).append("Illegal to call this method when status is failure: ").append($r3).toString());
            }
        }

        public int getDeviceTag() throws  {
            zzbtp();
            return this.azb.getDeviceTag();
        }

        public int getExpectedOptInStatusCode() throws  {
            zzbtp();
            return zzxq.zzxw(this.azb.getExpectedOptInResult());
        }

        public int getHistoryEnabledSetting() throws  {
            zzbtp();
            return this.azb.getHistoryEnabled();
        }

        public int getReportingEnabledSetting() throws  {
            zzbtp();
            return this.azb.getReportingEnabled();
        }

        public Status getStatus() throws  {
            return this.cp;
        }

        public boolean isActive() throws  {
            zzbtp();
            return this.azb.isActive();
        }

        public boolean isAllowed() throws  {
            zzbtp();
            return this.azb.isAllowed();
        }

        public boolean isDeferringToMaps() throws  {
            zzbtp();
            Log.wtf("GCoreUlr", "", new UnsupportedOperationException("This method always returns false now, and will eventually be deleted"));
            return false;
        }

        public boolean isHistoryEnabled() throws  {
            return Setting.isOn(getHistoryEnabledSetting());
        }

        public boolean isOptedIn() throws  {
            zzbtp();
            return this.azb.isOptedIn();
        }

        public boolean isReportingEnabled() throws  {
            return Setting.isOn(getReportingEnabledSetting());
        }

        public boolean shouldOptIn() throws  {
            zzbtp();
            return this.azb.shouldOptIn();
        }

        public boolean shouldOptInInsistent() throws  {
            Log.wtf("GCoreUlr", "", new UnsupportedOperationException("Deprecated: please use shouldOptIn(). This method will eventually be deleted."));
            return shouldOptIn();
        }

        public boolean shouldOptInLenient() throws  {
            Log.wtf("GCoreUlr", "", new UnsupportedOperationException("Deprecated: please use shouldOptIn(). This method will eventually be deleted."));
            return shouldOptIn();
        }

        public String toString() throws  {
            String $r2 = String.valueOf(this.cp);
            String $r4 = String.valueOf(this.azb);
            return new StringBuilder((String.valueOf($r2).length() + 52) + String.valueOf($r4).length()).append("ReportingStateResultImpl{mStatus=").append($r2).append(", mReportingState=").append($r4).append("}").toString();
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    private static class zzb implements ReportingUploadResult {
        private final Status cp;
        private final long xG;

        public zzb(Status $r1, long $l0) throws  {
            this.cp = $r1;
            this.xG = $l0;
        }

        public long getRequestId() throws  {
            return this.xG;
        }

        public Status getStatus() throws  {
            return this.cp;
        }

        public String toString() throws  {
            String $r2 = String.valueOf(this.cp);
            return new StringBuilder(String.valueOf($r2).length() + 68).append("ReportingUploadResultImpl{mStatus=").append($r2).append(", mRequestId=").append(this.xG).append("}").toString();
        }
    }

    private static int zzxw(int $i0) throws  {
        switch ($i0) {
            case 0:
                return 0;
            case 1:
                return 3507;
            case 2:
            case 3:
                return 5;
            case 4:
                return 10;
            case 5:
                return 3500;
            case 6:
                return 3501;
            case 7:
                return 3502;
            case 8:
                return 3503;
            case 9:
                break;
            case 10:
                return 3510;
            case 11:
                return 3511;
            default:
                break;
        }
        return 8;
    }

    private static int zzxx(int $i0) throws  {
        switch ($i0) {
            case 0:
                return 0;
            case 2:
                return 3505;
            case 3:
                return 3504;
            case 4:
                return 3500;
            case 5:
                return 3508;
            case 6:
                return 3509;
            case 100:
                return 3506;
            default:
                return 8;
        }
    }

    private static int zzxy(int $i0) throws  {
        switch ($i0) {
            case 0:
                return 0;
            case 1:
                return 3504;
            default:
                return 8;
        }
    }

    public PendingResult<Status> cancelUpload(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "J)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) GoogleApiClient $r1, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "J)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) final long $l0) throws  {
        return $r1.zzd(new com.google.android.gms.location.reporting.ReportingServices.zza<Status>(this, $r1) {
            final /* synthetic */ zzxq ayY;

            protected void zza(zzxp $r1) throws RemoteException {
                zzc(new Status(zzxq.zzxx($r1.zzbd($l0))));
            }

            public /* synthetic */ Result zzb(Status $r1) throws  {
                return zzd($r1);
            }

            public Status zzd(Status $r1) throws  {
                return $r1;
            }
        });
    }

    public PendingResult<ReportingStateResult> getReportingStateSafe(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Landroid/accounts/Account;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/location/reporting/Reporting$ReportingStateResult;", ">;"}) GoogleApiClient $r1, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Landroid/accounts/Account;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/location/reporting/Reporting$ReportingStateResult;", ">;"}) final Account $r2) throws  {
        return $r1.zzc(new com.google.android.gms.location.reporting.ReportingServices.zza<ReportingStateResult>(this, $r1) {
            final /* synthetic */ zzxq ayY;

            protected void zza(zzxp $r1) throws RemoteException {
                zzc(new zza(Status.CQ, $r1.zzf($r2)));
            }

            protected /* synthetic */ Result zzb(Status $r1) throws  {
                return zzfc($r1);
            }

            protected ReportingStateResult zzfc(Status $r1) throws  {
                return new zza($r1, null);
            }
        });
    }

    public PendingResult<Status> reportPlace(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Landroid/accounts/Account;", "Lcom/google/android/gms/location/places/PlaceReport;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) GoogleApiClient $r1, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Landroid/accounts/Account;", "Lcom/google/android/gms/location/places/PlaceReport;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) final Account $r2, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Landroid/accounts/Account;", "Lcom/google/android/gms/location/places/PlaceReport;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) final PlaceReport $r3) throws  {
        return $r1.zzd(new com.google.android.gms.location.reporting.ReportingServices.zza<Status>(this, $r1) {
            final /* synthetic */ zzxq ayY;

            protected void zza(zzxp $r1) throws RemoteException {
                zzc(new Status(zzxq.zzxy($r1.zza($r2, $r3))));
            }

            public /* synthetic */ Result zzb(Status $r1) throws  {
                return zzd($r1);
            }

            public Status zzd(Status $r1) throws  {
                return $r1;
            }
        });
    }

    public PendingResult<ReportingUploadResult> requestUpload(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/location/reporting/UploadRequest;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/location/reporting/Reporting$ReportingUploadResult;", ">;"}) GoogleApiClient $r1, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/location/reporting/UploadRequest;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/location/reporting/Reporting$ReportingUploadResult;", ">;"}) final UploadRequest $r2) throws  {
        return $r1.zzd(new com.google.android.gms.location.reporting.ReportingServices.zza<ReportingUploadResult>(this, $r1) {
            final /* synthetic */ zzxq ayY;

            protected void zza(zzxp $r1) throws RemoteException {
                UploadRequestResult $r5 = $r1.zza($r2);
                zzc(new zzb(new Status(zzxq.zzxx($r5.getResultCode())), $r5.getRequestId()));
            }

            public /* synthetic */ Result zzb(Status $r1) throws  {
                return zzfd($r1);
            }

            public ReportingUploadResult zzfd(Status $r1) throws  {
                return new zzb($r1, -1);
            }
        });
    }

    public PendingResult<Status> tryOptIn(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Landroid/accounts/Account;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) GoogleApiClient $r1, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Landroid/accounts/Account;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) final Account $r2) throws  {
        return $r1.zzd(new com.google.android.gms.location.reporting.ReportingServices.zza<Status>(this, $r1) {
            final /* synthetic */ zzxq ayY;

            protected void zza(zzxp $r1) throws RemoteException {
                zzc(new Status(zzxq.zzxw($r1.zzg($r2))));
            }

            protected /* synthetic */ Result zzb(Status $r1) throws  {
                return zzd($r1);
            }

            protected Status zzd(Status $r1) throws  {
                return $r1;
            }
        });
    }

    public PendingResult<Status> tryOptInRequest(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/location/reporting/OptInRequest;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) GoogleApiClient $r1, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/location/reporting/OptInRequest;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) final OptInRequest $r2) throws  {
        return $r1.zzd(new com.google.android.gms.location.reporting.ReportingServices.zza<Status>(this, $r1) {
            final /* synthetic */ zzxq ayY;

            protected void zza(zzxp $r1) throws RemoteException {
                zzc(new Status(zzxq.zzxw($r1.zza($r2))));
            }

            protected /* synthetic */ Result zzb(Status $r1) throws  {
                return zzd($r1);
            }

            protected Status zzd(Status $r1) throws  {
                return $r1;
            }
        });
    }
}
