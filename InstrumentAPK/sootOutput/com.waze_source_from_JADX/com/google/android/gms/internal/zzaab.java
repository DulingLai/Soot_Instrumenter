package com.google.android.gms.internal;

import android.annotation.SuppressLint;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.People;
import com.google.android.gms.plus.People.LoadPeopleResult;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.internal.zzf;
import com.google.android.gms.plus.model.people.Person;
import com.google.android.gms.plus.model.people.PersonBuffer;
import dalvik.annotation.Signature;
import java.util.Collection;

/* compiled from: dalvik_source_com.waze.apk */
public final class zzaab implements People {

    /* compiled from: dalvik_source_com.waze.apk */
    private static abstract class zza extends com.google.android.gms.plus.Plus.zza<LoadPeopleResult> {
        private zza(GoogleApiClient $r1) throws  {
            super($r1);
        }

        public /* synthetic */ Result zzb(Status $r1) throws  {
            return zzhj($r1);
        }

        public LoadPeopleResult zzhj(final Status $r1) throws  {
            return new LoadPeopleResult(this) {
                final /* synthetic */ zza aYU;

                public String getNextPageToken() throws  {
                    return null;
                }

                public PersonBuffer getPersonBuffer() throws  {
                    return null;
                }

                public Status getStatus() throws  {
                    return $r1;
                }

                public void release() throws  {
                }
            };
        }
    }

    public Person getCurrentPerson(GoogleApiClient $r1) throws  {
        return Plus.zzf($r1, true).zzcgs();
    }

    @SuppressLint({"MissingRemoteException"})
    public PendingResult<LoadPeopleResult> load(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/util/Collection", "<", "Ljava/lang/String;", ">;)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/plus/People$LoadPeopleResult;", ">;"}) GoogleApiClient $r1, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/util/Collection", "<", "Ljava/lang/String;", ">;)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/plus/People$LoadPeopleResult;", ">;"}) final Collection<String> $r2) throws  {
        return $r1.zzc(new zza(this, $r1) {
            final /* synthetic */ zzaab aYR;

            protected void zza(zzf $r1) throws  {
                $r1.zza(this, $r2);
            }
        });
    }

    @SuppressLint({"MissingRemoteException"})
    public PendingResult<LoadPeopleResult> load(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "[", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/plus/People$LoadPeopleResult;", ">;"}) GoogleApiClient $r1, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "[", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/plus/People$LoadPeopleResult;", ">;"}) final String... $r2) throws  {
        return $r1.zzc(new zza(this, $r1) {
            final /* synthetic */ zzaab aYR;

            protected void zza(zzf $r1) throws  {
                $r1.zze(this, $r2);
            }
        });
    }

    @SuppressLint({"MissingRemoteException"})
    public PendingResult<LoadPeopleResult> loadConnected(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/plus/People$LoadPeopleResult;", ">;"}) GoogleApiClient $r1) throws  {
        return $r1.zzc(new zza(this, $r1) {
            final /* synthetic */ zzaab aYR;

            protected void zza(zzf $r1) throws  {
                $r1.zzah(this);
            }
        });
    }

    @SuppressLint({"MissingRemoteException"})
    public PendingResult<LoadPeopleResult> loadVisible(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "I", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/plus/People$LoadPeopleResult;", ">;"}) GoogleApiClient $r1, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "I", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/plus/People$LoadPeopleResult;", ">;"}) final int $i0, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "I", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/plus/People$LoadPeopleResult;", ">;"}) final String $r2) throws  {
        return $r1.zzc(new zza(this, $r1) {
            final /* synthetic */ zzaab aYR;

            protected void zza(zzf $r1) throws  {
                zza($r1.zza(this, $i0, $r2));
            }
        });
    }

    @SuppressLint({"MissingRemoteException"})
    public PendingResult<LoadPeopleResult> loadVisible(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/plus/People$LoadPeopleResult;", ">;"}) GoogleApiClient $r1, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/plus/People$LoadPeopleResult;", ">;"}) final String $r2) throws  {
        return $r1.zzc(new zza(this, $r1) {
            final /* synthetic */ zzaab aYR;

            protected void zza(zzf $r1) throws  {
                zza($r1.zzx(this, $r2));
            }
        });
    }
}
