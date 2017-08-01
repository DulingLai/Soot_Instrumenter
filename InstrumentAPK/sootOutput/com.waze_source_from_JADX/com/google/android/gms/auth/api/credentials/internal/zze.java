package com.google.android.gms.auth.api.credentials.internal;

import android.app.PendingIntent;
import android.content.Context;
import android.os.RemoteException;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.Auth.AuthCredentialsOptions;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.credentials.CredentialRequest;
import com.google.android.gms.auth.api.credentials.CredentialRequestResult;
import com.google.android.gms.auth.api.credentials.CredentialsApi;
import com.google.android.gms.auth.api.credentials.GeneratePasswordRequestResult;
import com.google.android.gms.auth.api.credentials.HintRequest;
import com.google.android.gms.auth.api.credentials.PasswordSpecification;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.internal.zzqk.zzb;
import dalvik.annotation.Signature;

/* compiled from: dalvik_source_com.waze.apk */
public final class zze implements CredentialsApi {

    /* compiled from: dalvik_source_com.waze.apk */
    private static class zza extends zza {
        private zzb<Status> fi;

        zza(@Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/common/api/Status;", ">;)V"}) zzb<Status> $r1) throws  {
            this.fi = $r1;
        }

        public void zzj(Status $r1) throws  {
            this.fi.setResult($r1);
        }
    }

    private PasswordSpecification zza(GoogleApiClient $r1) throws  {
        AuthCredentialsOptions $r5 = ((zzg) $r1.zza(Auth.es)).zzaeb();
        return ($r5 == null || $r5.getPasswordSpecification() == null) ? PasswordSpecification.DEFAULT : $r5.getPasswordSpecification();
    }

    public PendingResult<Status> delete(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/auth/api/credentials/Credential;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) GoogleApiClient $r1, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/auth/api/credentials/Credential;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) final Credential $r2) throws  {
        return $r1.zzd(new zzf<Status>(this, $r1) {
            final /* synthetic */ zze fd;

            protected void zza(Context context, zzl $r2) throws RemoteException {
                $r2.zza(new zza(this), new DeleteRequest($r2));
            }

            protected /* synthetic */ Result zzb(Status $r1) throws  {
                return zzd($r1);
            }

            protected Status zzd(Status $r1) throws  {
                return $r1;
            }
        });
    }

    public PendingResult<Status> disableAutoSignIn(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) GoogleApiClient $r1) throws  {
        return $r1.zzd(new zzf<Status>(this, $r1) {
            final /* synthetic */ zze fd;

            protected void zza(Context context, zzl $r2) throws RemoteException {
                $r2.zza(new zza(this));
            }

            protected /* synthetic */ Result zzb(Status $r1) throws  {
                return zzd($r1);
            }

            protected Status zzd(Status $r1) throws  {
                return $r1;
            }
        });
    }

    public PendingResult<GeneratePasswordRequestResult> generatePassword(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/auth/api/credentials/GeneratePasswordRequestResult;", ">;"}) GoogleApiClient $r1) throws  {
        zzab.zzb((Object) $r1, (Object) "client must not be null");
        zzab.zzb($r1.zza(Auth.CREDENTIALS_API), (Object) "Auth.CREDENTIALS_API must be added to GoogleApiClient to use this API");
        return generatePassword($r1, zza($r1));
    }

    public PendingResult<GeneratePasswordRequestResult> generatePassword(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/auth/api/credentials/PasswordSpecification;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/auth/api/credentials/GeneratePasswordRequestResult;", ">;"}) GoogleApiClient $r1, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/auth/api/credentials/PasswordSpecification;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/auth/api/credentials/GeneratePasswordRequestResult;", ">;"}) final PasswordSpecification $r2) throws  {
        zzab.zzb((Object) $r1, (Object) "client must not be null");
        zzab.zzb($r1.zza(Auth.CREDENTIALS_API), (Object) "Auth.CREDENTIALS_API must be added to GoogleApiClient to use this API");
        return $r1.zzd(new zzf<GeneratePasswordRequestResult>(this, $r1) {
            final /* synthetic */ zze fd;

            /* compiled from: dalvik_source_com.waze.apk */
            class C06661 extends zza {
                final /* synthetic */ C06673 fh;

                C06661(C06673 $r1) throws  {
                    this.fh = $r1;
                }

                public void zza(Status $r1, String $r2) throws  {
                    this.fh.zzc(new zzj($r1, $r2));
                }

                public void zzj(Status $r1) throws  {
                    this.fh.zzc(new zzj($r1, null));
                }
            }

            protected void zza(Context context, zzl $r2) throws RemoteException {
                $r2.zza(new C06661(this), new GeneratePasswordRequest($r2));
            }

            protected /* synthetic */ Result zzb(Status $r1) throws  {
                return zzm($r1);
            }

            protected GeneratePasswordRequestResult zzm(Status $r1) throws  {
                return new zzj($r1, null);
            }
        });
    }

    public PendingIntent getHintPickerIntent(GoogleApiClient $r1, HintRequest $r2) throws  {
        zzab.zzb((Object) $r1, (Object) "client must not be null");
        zzab.zzb((Object) $r2, (Object) "request must not be null");
        zzab.zzb($r1.zza(Auth.CREDENTIALS_API), (Object) "Auth.CREDENTIALS_API must be added to GoogleApiClient to use this API");
        return PendingIntent.getActivity($r1.getContext(), 2000, zzc.zza($r1.getContext(), $r2, zza($r1)), 268435456);
    }

    public PendingResult<CredentialRequestResult> request(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/auth/api/credentials/CredentialRequest;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/auth/api/credentials/CredentialRequestResult;", ">;"}) GoogleApiClient $r1, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/auth/api/credentials/CredentialRequest;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/auth/api/credentials/CredentialRequestResult;", ">;"}) final CredentialRequest $r2) throws  {
        return $r1.zzc(new zzf<CredentialRequestResult>(this, $r1) {
            final /* synthetic */ zze fd;

            /* compiled from: dalvik_source_com.waze.apk */
            class C06631 extends zza {
                final /* synthetic */ C06641 fe;

                C06631(C06641 $r1) throws  {
                    this.fe = $r1;
                }

                public void zza(Status $r1, Credential $r2) throws  {
                    this.fe.zzc(new zzd($r1, $r2));
                }

                public void zzj(Status $r1) throws  {
                    this.fe.zzc(zzd.zzk($r1));
                }
            }

            protected void zza(Context context, zzl $r2) throws RemoteException {
                $r2.zza(new C06631(this), $r2);
            }

            protected /* synthetic */ Result zzb(Status $r1) throws  {
                return zzl($r1);
            }

            protected CredentialRequestResult zzl(Status $r1) throws  {
                return zzd.zzk($r1);
            }
        });
    }

    public PendingResult<Status> save(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/auth/api/credentials/Credential;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) GoogleApiClient $r1, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/auth/api/credentials/Credential;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Status;", ">;"}) final Credential $r2) throws  {
        return $r1.zzd(new zzf<Status>(this, $r1) {
            final /* synthetic */ zze fd;

            protected void zza(Context context, zzl $r2) throws RemoteException {
                $r2.zza(new zza(this), new SaveRequest($r2));
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
