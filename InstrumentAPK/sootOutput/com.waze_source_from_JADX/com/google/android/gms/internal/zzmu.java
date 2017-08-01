package com.google.android.gms.internal;

import android.accounts.Account;
import android.os.RemoteException;
import com.google.android.gms.auth.account.WorkAccount;
import com.google.android.gms.auth.account.WorkAccountApi;
import com.google.android.gms.auth.account.WorkAccountApi.AddAccountResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import dalvik.annotation.Signature;

/* compiled from: dalvik_source_com.waze.apk */
public class zzmu implements WorkAccountApi {
    private static final Status em = new Status(13);

    /* compiled from: dalvik_source_com.waze.apk */
    static class zza extends com.google.android.gms.auth.account.zza.zza {
        zza() throws  {
        }

        public void zzap(boolean z) throws  {
            throw new UnsupportedOperationException();
        }

        public void zzb(Account account) throws  {
            throw new UnsupportedOperationException();
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    static class zzb implements AddAccountResult {
        private final Account f33P;
        private final Status cp;

        public zzb(Status $r1, Account $r2) throws  {
            this.cp = $r1;
            this.f33P = $r2;
        }

        public Account getAccount() throws  {
            return this.f33P;
        }

        public Status getStatus() throws  {
            return this.cp;
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    static class zzc implements Result {
        private final Status cp;

        public zzc(Status $r1) throws  {
            this.cp = $r1;
        }

        public Status getStatus() throws  {
            return this.cp;
        }
    }

    public PendingResult<AddAccountResult> addWorkAccount(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/auth/account/WorkAccountApi$AddAccountResult;", ">;"}) GoogleApiClient $r1, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/auth/account/WorkAccountApi$AddAccountResult;", ">;"}) final String $r2) throws  {
        return $r1.zzd(new com.google.android.gms.internal.zzqk.zza<AddAccountResult, zzmv>(this, WorkAccount.API, $r1) {
            final /* synthetic */ zzmu eo;

            /* compiled from: dalvik_source_com.waze.apk */
            class C08211 extends zza {
                final /* synthetic */ C08222 ep;

                C08211(C08222 $r1) throws  {
                    this.ep = $r1;
                }

                public void zzb(Account $r1) throws  {
                    this.ep.zzc(new zzb($r1 != null ? Status.CQ : zzmu.em, $r1));
                }
            }

            protected void zza(zzmv $r1) throws RemoteException {
                ((com.google.android.gms.auth.account.zzb) $r1.zzavx()).zza(new C08211(this), $r2);
            }

            protected /* synthetic */ Result zzb(Status $r1) throws  {
                return zzh($r1);
            }

            protected AddAccountResult zzh(Status $r1) throws  {
                return new zzb($r1, null);
            }
        });
    }

    public PendingResult<Result> removeWorkAccount(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Landroid/accounts/Account;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Result;", ">;"}) GoogleApiClient $r1, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Landroid/accounts/Account;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Result;", ">;"}) final Account $r2) throws  {
        return $r1.zzd(new com.google.android.gms.internal.zzqk.zza<Result, zzmv>(this, WorkAccount.API, $r1) {
            final /* synthetic */ zzmu eo;

            /* compiled from: dalvik_source_com.waze.apk */
            class C08231 extends zza {
                final /* synthetic */ C08243 eq;

                C08231(C08243 $r1) throws  {
                    this.eq = $r1;
                }

                public void zzap(boolean $z0) throws  {
                    this.eq.zzc(new zzc($z0 ? Status.CQ : zzmu.em));
                }
            }

            protected void zza(zzmv $r1) throws RemoteException {
                ((com.google.android.gms.auth.account.zzb) $r1.zzavx()).zza(new C08231(this), $r2);
            }

            protected Result zzb(Status $r1) throws  {
                return new zzc($r1);
            }
        });
    }

    public void setWorkAuthenticatorEnabled(GoogleApiClient $r1, final boolean $z0) throws  {
        $r1.zzd(new com.google.android.gms.internal.zzqk.zza<Result, zzmv>(this, WorkAccount.API, $r1) {
            final /* synthetic */ zzmu eo;

            protected void zza(zzmv $r1) throws RemoteException {
                ((com.google.android.gms.auth.account.zzb) $r1.zzavx()).zzaq($z0);
            }

            protected Result zzb(Status status) throws  {
                return null;
            }
        });
    }
}
