package com.google.android.gms.auth.api.signin.internal;

import android.accounts.Account;
import android.content.Context;
import android.content.Intent;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.PendingResults;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.internal.zzrp;
import dalvik.annotation.Signature;
import java.util.HashSet;

/* compiled from: dalvik_source_com.waze.apk */
public class zzc implements GoogleSignInApi {

    /* compiled from: dalvik_source_com.waze.apk */
    private abstract class zza<R extends Result> extends com.google.android.gms.internal.zzqk.zza<R, zzd> {
        final /* synthetic */ zzc fT;

        public zza(zzc $r1, GoogleApiClient $r2) throws  {
            this.fT = $r1;
            super(Auth.GOOGLE_SIGN_IN_API, $r2);
        }
    }

    private OptionalPendingResult<GoogleSignInResult> zza(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/auth/api/signin/GoogleSignInOptions;", ")", "Lcom/google/android/gms/common/api/OptionalPendingResult", "<", "Lcom/google/android/gms/auth/api/signin/GoogleSignInResult;", ">;"}) GoogleApiClient $r1, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/auth/api/signin/GoogleSignInOptions;", ")", "Lcom/google/android/gms/common/api/OptionalPendingResult", "<", "Lcom/google/android/gms/auth/api/signin/GoogleSignInResult;", ">;"}) final GoogleSignInOptions $r2) throws  {
        Log.d("GoogleSignInApiImpl", "trySilentSignIn");
        return new zzrp($r1.zzc(new zza<GoogleSignInResult>(this, $r1) {
            final /* synthetic */ zzc fT;

            protected void zza(zzd $r1) throws RemoteException {
                final zzk $r4 = zzk.zzbc($r1.getContext());
                ((zzh) $r1.zzavx()).zza(new zza(this) {
                    final /* synthetic */ C06741 fV;

                    public void zza(GoogleSignInAccount $r1, Status $r2) throws RemoteException {
                        if ($r1 != null) {
                            $r4.zzb($r1, $r2);
                        }
                        this.fV.zzc(new GoogleSignInResult($r1, $r2));
                    }
                }, $r2);
            }

            protected /* synthetic */ Result zzb(Status $r1) throws  {
                return zzr($r1);
            }

            protected GoogleSignInResult zzr(Status $r1) throws  {
                return new GoogleSignInResult(null, $r1);
            }
        }));
    }

    private boolean zza(Account $r1, Account $r2) throws  {
        return $r1 == null ? $r2 == null : $r1.equals($r2);
    }

    private GoogleSignInOptions zzb(GoogleApiClient $r1) throws  {
        return ((zzd) $r1.zza(Auth.eu)).zzaet();
    }

    public Intent getSignInIntent(GoogleApiClient $r1) throws  {
        zzab.zzag($r1);
        return ((zzd) $r1.zza(Auth.eu)).zzaes();
    }

    public GoogleSignInResult getSignInResultFromIntent(Intent $r1) throws  {
        if ($r1 == null || (!$r1.hasExtra("googleSignInStatus") && !$r1.hasExtra("googleSignInAccount"))) {
            return null;
        }
        GoogleSignInAccount $r4 = (GoogleSignInAccount) $r1.getParcelableExtra("googleSignInAccount");
        Status $r5 = (Status) $r1.getParcelableExtra("googleSignInStatus");
        if ($r4 != null) {
            $r5 = Status.CQ;
        }
        return new GoogleSignInResult($r4, $r5);
    }

    public PendingResult<Status> revokeAccess(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) GoogleApiClient $r1) throws  {
        zzk.zzbc($r1.getContext()).zzafd();
        for (GoogleApiClient zzarx : GoogleApiClient.zzarw()) {
            zzarx.zzarx();
        }
        return $r1.zzd(new zza<Status>(this, $r1) {
            final /* synthetic */ zzc fT;

            /* compiled from: dalvik_source_com.waze.apk */
            class C06771 extends zza {
                final /* synthetic */ C06783 fX;

                C06771(C06783 $r1) throws  {
                    this.fX = $r1;
                }

                public void zzq(Status $r1) throws RemoteException {
                    this.fX.zzc($r1);
                }
            }

            protected void zza(zzd $r1) throws RemoteException {
                ((zzh) $r1.zzavx()).zzc(new C06771(this), $r1.zzaet());
            }

            protected /* synthetic */ Result zzb(Status $r1) throws  {
                return zzd($r1);
            }

            protected Status zzd(Status $r1) throws  {
                return $r1;
            }
        });
    }

    public PendingResult<Status> signOut(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) GoogleApiClient $r1) throws  {
        zzk.zzbc($r1.getContext()).zzafd();
        for (GoogleApiClient zzarx : GoogleApiClient.zzarw()) {
            zzarx.zzarx();
        }
        return $r1.zzd(new zza<Status>(this, $r1) {
            final /* synthetic */ zzc fT;

            /* compiled from: dalvik_source_com.waze.apk */
            class C06751 extends zza {
                final /* synthetic */ C06762 fW;

                C06751(C06762 $r1) throws  {
                    this.fW = $r1;
                }

                public void zzp(Status $r1) throws RemoteException {
                    this.fW.zzc($r1);
                }
            }

            protected void zza(zzd $r1) throws RemoteException {
                ((zzh) $r1.zzavx()).zzb(new C06751(this), $r1.zzaet());
            }

            protected /* synthetic */ Result zzb(Status $r1) throws  {
                return zzd($r1);
            }

            protected Status zzd(Status $r1) throws  {
                return $r1;
            }
        });
    }

    public OptionalPendingResult<GoogleSignInResult> silentSignIn(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", ")", "Lcom/google/android/gms/common/api/OptionalPendingResult", "<", "Lcom/google/android/gms/auth/api/signin/GoogleSignInResult;", ">;"}) GoogleApiClient $r1) throws  {
        GoogleSignInOptions $r2 = zzb($r1);
        GoogleSignInResult $r4 = zza($r1.getContext(), $r2);
        return $r4 != null ? PendingResults.zzb($r4, $r1) : zza($r1, $r2);
    }

    public GoogleSignInResult zza(Context $r1, GoogleSignInOptions $r2) throws  {
        Log.d("GoogleSignInApiImpl", "getSavedSignInResultIfEligible");
        zzab.zzag($r2);
        zzk $r4 = zzk.zzbc($r1);
        GoogleSignInOptions $r5 = $r4.zzafc();
        if ($r5 == null) {
            return null;
        }
        if (!zza($r5.getAccount(), $r2.getAccount())) {
            return null;
        }
        if ($r2.zzaek()) {
            return null;
        }
        if ($r2.zzaej()) {
            if (!$r5.zzaej()) {
                return null;
            }
            if (!$r2.zzaem().equals($r5.zzaem())) {
                return null;
            }
        }
        if (!new HashSet($r5.zzaei()).containsAll(new HashSet($r2.zzaei()))) {
            return null;
        }
        GoogleSignInAccount $r13 = $r4.zzafb();
        return $r13 != null ? !$r13.zza() ? new GoogleSignInResult($r13, Status.CQ) : null : null;
    }
}
