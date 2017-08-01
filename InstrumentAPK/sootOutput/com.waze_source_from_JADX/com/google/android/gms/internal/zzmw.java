package com.google.android.gms.internal;

import android.accounts.Account;
import android.os.RemoteException;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.accountactivationstate.AccountActivationStateApi;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import dalvik.annotation.Signature;

/* compiled from: dalvik_source_com.waze.apk */
public class zzmw implements AccountActivationStateApi {

    /* compiled from: dalvik_source_com.waze.apk */
    private static abstract class zza<R extends Status> extends com.google.android.gms.internal.zzqk.zza<R, zzmx> {
        public zza(GoogleApiClient $r1) throws  {
            super(Auth.et, $r1);
        }

        protected final void zza(zzmx $r1) throws RemoteException {
            zza((zzna) $r1.zzavx());
        }

        protected abstract void zza(zzna com_google_android_gms_internal_zzna) throws RemoteException;
    }

    public PendingResult<Status> setAccountActivationState(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Landroid/accounts/Account;", "I)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) GoogleApiClient $r1, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Landroid/accounts/Account;", "I)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) final Account $r2, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Landroid/accounts/Account;", "I)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) final int $i0) throws  {
        return $r1.zzd(new zza<Status>(this, $r1) {
            final /* synthetic */ zzmw eD;

            /* compiled from: dalvik_source_com.waze.apk */
            class C08251 extends zzmy {
                final /* synthetic */ C08261 eE;

                C08251(C08261 $r1) throws  {
                    this.eE = $r1;
                }

                public void zzi(Status $r1) throws  {
                    this.eE.zzc($r1);
                }
            }

            protected void zza(zzna $r1) throws RemoteException {
                $r1.zza($r2, $i0, new C08251(this));
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
