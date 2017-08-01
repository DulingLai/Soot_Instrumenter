package com.google.android.gms.internal;

import android.annotation.SuppressLint;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.Account;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.internal.zzf;
import dalvik.annotation.Signature;

/* compiled from: dalvik_source_com.waze.apk */
public final class zzzy implements Account {

    /* compiled from: dalvik_source_com.waze.apk */
    private static abstract class zza extends com.google.android.gms.plus.Plus.zza<Status> {
        private zza(GoogleApiClient $r1) throws  {
            super($r1);
        }

        public /* synthetic */ Result zzb(Status $r1) throws  {
            return zzd($r1);
        }

        public Status zzd(Status $r1) throws  {
            return $r1;
        }
    }

    public void clearDefaultAccount(GoogleApiClient $r1) throws  {
        zzf $r2 = Plus.zzf($r1, false);
        if ($r2 != null) {
            $r2.zzcgq();
        }
    }

    public String getAccountName(GoogleApiClient $r1) throws  {
        return Plus.zzf($r1, true).getAccountName();
    }

    @SuppressLint({"MissingRemoteException"})
    public PendingResult<Status> revokeAccessAndDisconnect(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) GoogleApiClient $r1) throws  {
        return $r1.zzd(new zza(this, $r1) {
            final /* synthetic */ zzzy aYL;

            protected void zza(zzf $r1) throws  {
                $r1.zzai(this);
            }
        });
    }
}
