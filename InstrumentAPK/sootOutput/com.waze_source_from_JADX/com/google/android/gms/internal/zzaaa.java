package com.google.android.gms.internal;

import android.annotation.SuppressLint;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.FirstPartyPeople;
import com.google.android.gms.plus.FirstPartyPeople.LoadPersonResult;
import com.google.android.gms.plus.internal.zzf;
import com.google.android.gms.plus.model.people.Person;
import dalvik.annotation.Signature;

/* compiled from: dalvik_source_com.waze.apk */
public final class zzaaa implements FirstPartyPeople {

    /* compiled from: dalvik_source_com.waze.apk */
    private static abstract class zza extends com.google.android.gms.plus.Plus.zza<LoadPersonResult> {
        private zza(GoogleApiClient $r1) throws  {
            super($r1);
        }

        public /* synthetic */ Result zzb(Status $r1) throws  {
            return zzhi($r1);
        }

        public LoadPersonResult zzhi(final Status $r1) throws  {
            return new LoadPersonResult(this) {
                final /* synthetic */ zza aYO;

                public Person getPerson() throws  {
                    return null;
                }

                public Status getStatus() throws  {
                    return $r1;
                }
            };
        }
    }

    @SuppressLint({"MissingRemoteException"})
    public PendingResult<LoadPersonResult> load(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/plus/FirstPartyPeople$LoadPersonResult;", ">;"}) GoogleApiClient $r1, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/plus/FirstPartyPeople$LoadPersonResult;", ">;"}) final String $r2) throws  {
        return $r1.zzc(new zza(this, $r1) {
            final /* synthetic */ zzaaa aYN;

            protected void zza(zzf $r1) throws  {
                $r1.zzy(this, $r2);
            }
        });
    }
}
