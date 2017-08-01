package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.server.FavaDiagnosticsEntity;
import com.google.android.gms.people.GraphUpdate;
import com.google.android.gms.people.GraphUpdate.AddCircleResult;
import com.google.android.gms.people.GraphUpdate.AddPeopleToCircleResult;
import com.google.android.gms.people.GraphUpdate.LoadAddToCircleConsentResult;
import com.google.android.gms.people.GraphUpdate.UpdatePersonCircleResult;
import com.google.android.gms.people.People.zza;
import com.google.android.gms.people.People.zzb;
import com.google.android.gms.people.internal.zzl;
import com.google.android.gms.people.internal.zzn;
import dalvik.annotation.Signature;
import java.util.List;

/* compiled from: dalvik_source_com.waze.apk */
public class zzyx implements GraphUpdate {
    private PendingResult<Result> zza(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Z)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Result;", ">;"}) GoogleApiClient $r1, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Z)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Result;", ">;"}) String $r2, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Z)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Result;", ">;"}) String $r3, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Z)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Result;", ">;"}) String $r4, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Z)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Result;", ">;"}) boolean $z0) throws  {
        final String str = $r2;
        final String str2 = $r3;
        final String str3 = $r4;
        final boolean z = $z0;
        return $r1.zzd(new zzb(this, $r1) {
            final /* synthetic */ zzyx aSW;

            protected void zza(zzn $r1) throws RemoteException {
                $r1.zza((zzqk.zzb) this, str, str2, str3, z);
            }
        });
    }

    private PendingResult<Result> zza(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Z)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Result;", ">;"}) GoogleApiClient $r1, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Z)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Result;", ">;"}) String $r2, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Z)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Result;", ">;"}) String $r3, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Z)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Result;", ">;"}) boolean $z0) throws  {
        final String str = $r2;
        final String str2 = $r3;
        final boolean z = $z0;
        return $r1.zzd(new zzb(this, $r1) {
            final /* synthetic */ zzyx aSW;

            protected void zza(zzn $r1) throws RemoteException {
                $r1.zzd(this, str, str2, z);
            }
        });
    }

    public PendingResult<AddCircleResult> addCircle(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/GraphUpdate$AddCircleResult;", ">;"}) GoogleApiClient $r1, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/GraphUpdate$AddCircleResult;", ">;"}) String $r2, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/GraphUpdate$AddCircleResult;", ">;"}) String $r3, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/GraphUpdate$AddCircleResult;", ">;"}) String $r4, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/GraphUpdate$AddCircleResult;", ">;"}) String $r5) throws  {
        return addCircle($r1, $r2, $r3, $r4, $r5, true);
    }

    public PendingResult<AddCircleResult> addCircle(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Z)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/GraphUpdate$AddCircleResult;", ">;"}) GoogleApiClient $r1, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Z)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/GraphUpdate$AddCircleResult;", ">;"}) String $r2, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Z)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/GraphUpdate$AddCircleResult;", ">;"}) String $r3, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Z)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/GraphUpdate$AddCircleResult;", ">;"}) String $r4, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Z)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/GraphUpdate$AddCircleResult;", ">;"}) String $r5, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Z)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/GraphUpdate$AddCircleResult;", ">;"}) boolean $z0) throws  {
        if (zzl.isEnabled()) {
            zzl.zzh("addCircle", $r2, $r3, $r4, $r5, Boolean.valueOf($z0));
        }
        final String str = $r2;
        final String str2 = $r3;
        final String str3 = $r4;
        final String str4 = $r5;
        final boolean z = $z0;
        return $r1.zzd(new zza<AddCircleResult>(this, $r1) {
            final /* synthetic */ zzyx aSW;

            protected void zza(zzn $r1) throws RemoteException {
                $r1.zza((zzqk.zzb) this, str, str2, str3, str4, z);
            }

            protected /* synthetic */ Result zzb(Status $r1) throws  {
                return zzgd($r1);
            }

            protected AddCircleResult zzgd(final Status $r1) throws  {
                return new AddCircleResult(this) {
                    final /* synthetic */ C09053 aTc;

                    public String getCircleId() throws  {
                        return null;
                    }

                    public String getCircleName() throws  {
                        return null;
                    }

                    public Status getStatus() throws  {
                        return $r1;
                    }
                };
            }
        });
    }

    public PendingResult<AddPeopleToCircleResult> addPeopleToCircle(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/GraphUpdate$AddPeopleToCircleResult;", ">;"}) GoogleApiClient $r1, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/GraphUpdate$AddPeopleToCircleResult;", ">;"}) String $r2, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/GraphUpdate$AddPeopleToCircleResult;", ">;"}) String $r3, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/GraphUpdate$AddPeopleToCircleResult;", ">;"}) String $r4, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/GraphUpdate$AddPeopleToCircleResult;", ">;"}) List<String> $r5) throws  {
        if (zzl.isEnabled()) {
            zzl.zzh("addPeopleToCircle", $r2, $r3, $r4, $r5);
        }
        final String str = $r2;
        final String str2 = $r3;
        final String str3 = $r4;
        final List<String> list = $r5;
        return $r1.zzd(new zza<AddPeopleToCircleResult>(this, $r1) {
            final /* synthetic */ zzyx aSW;

            protected void zza(zzn $r1) throws RemoteException {
                $r1.zza((zzqk.zzb) this, str, str2, str3, list);
            }

            protected /* synthetic */ Result zzb(Status $r1) throws  {
                return zzge($r1);
            }

            protected AddPeopleToCircleResult zzge(final Status $r1) throws  {
                return new AddPeopleToCircleResult(this) {
                    final /* synthetic */ C09096 aTi;

                    public String getCircleId() throws  {
                        return null;
                    }

                    public String getCircleName() throws  {
                        return null;
                    }

                    public String[] getPeopleQualifiedIds() throws  {
                        return null;
                    }

                    public Status getStatus() throws  {
                        return $r1;
                    }
                };
            }
        });
    }

    public PendingResult<Result> blockPerson(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Result;", ">;"}) GoogleApiClient $r1, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Result;", ">;"}) String $r2, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Result;", ">;"}) String $r3, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Result;", ">;"}) String $r4) throws  {
        if (zzl.isEnabled()) {
            zzl.zzh("blockPerson", $r2, $r3, $r4);
        }
        return zza($r1, $r2, $r3, $r4, true);
    }

    public PendingResult<LoadAddToCircleConsentResult> loadAddToCircleConsent(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/GraphUpdate$LoadAddToCircleConsentResult;", ">;"}) GoogleApiClient $r1, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/GraphUpdate$LoadAddToCircleConsentResult;", ">;"}) final String $r2, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/GraphUpdate$LoadAddToCircleConsentResult;", ">;"}) final String $r3) throws  {
        if (zzl.isEnabled()) {
            zzl.zzh("loadAddToCircleConsent", $r2, $r3);
        }
        return $r1.zzc(new zza<LoadAddToCircleConsentResult>(this, $r1) {
            final /* synthetic */ zzyx aSW;

            protected void zza(zzn $r1) throws RemoteException {
                $r1.zze(this, $r2, $r3);
            }

            protected /* synthetic */ Result zzb(Status $r1) throws  {
                return zzgg($r1);
            }

            protected LoadAddToCircleConsentResult zzgg(final Status $r1) throws  {
                return new LoadAddToCircleConsentResult(this) {
                    final /* synthetic */ C09138 aTo;

                    public String getConsentButtonText() throws  {
                        return null;
                    }

                    public String getConsentHtml() throws  {
                        return null;
                    }

                    public String getConsentTitleText() throws  {
                        return null;
                    }

                    public boolean getShowConsent() throws  {
                        return false;
                    }

                    public Status getStatus() throws  {
                        return $r1;
                    }
                };
            }
        });
    }

    public PendingResult<Result> removeCircle(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Result;", ">;"}) GoogleApiClient $r1, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Result;", ">;"}) String $r2, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Result;", ">;"}) String $r3, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Result;", ">;"}) String $r4) throws  {
        if (zzl.isEnabled()) {
            zzl.zzh("removeCircle", $r2, $r3, $r4);
        }
        final String str = $r2;
        final String str2 = $r3;
        final String str3 = $r4;
        return $r1.zzd(new zzb(this, $r1) {
            final /* synthetic */ zzyx aSW;

            protected void zza(zzn $r1) throws RemoteException {
                $r1.zza((zzqk.zzb) this, str, str2, str3);
            }
        });
    }

    public PendingResult<Result> setHasShownAddToCircleConsent(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Result;", ">;"}) GoogleApiClient $r1, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Result;", ">;"}) final String $r2, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Result;", ">;"}) final String $r3) throws  {
        if (zzl.isEnabled()) {
            zzl.zzh("setHasShownAddToCircleConsent", $r2, $r3);
        }
        return $r1.zzd(new zzb(this, $r1) {
            final /* synthetic */ zzyx aSW;

            protected void zza(zzn $r1) throws RemoteException {
                $r1.zzf(this, $r2, $r3);
            }
        });
    }

    public PendingResult<Result> starPerson(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Result;", ">;"}) GoogleApiClient $r1, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Result;", ">;"}) String $r2, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Result;", ">;"}) String $r3) throws  {
        if (zzl.isEnabled()) {
            zzl.zzh("starPerson", $r2, $r3);
        }
        return zza($r1, $r2, $r3, true);
    }

    public PendingResult<Result> unblockPerson(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Result;", ">;"}) GoogleApiClient $r1, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Result;", ">;"}) String $r2, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Result;", ">;"}) String $r3, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Result;", ">;"}) String $r4) throws  {
        if (zzl.isEnabled()) {
            zzl.zzh("unblockPerson", $r2, $r3, $r4);
        }
        return zza($r1, $r2, $r3, $r4, false);
    }

    public PendingResult<Result> unstarPerson(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Result;", ">;"}) GoogleApiClient $r1, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Result;", ">;"}) String $r2, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Result;", ">;"}) String $r3) throws  {
        if (zzl.isEnabled()) {
            zzl.zzh("unstarPerson", $r2, $r3);
        }
        return zza($r1, $r2, $r3, false);
    }

    public PendingResult<Result> updateCircle(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/Boolean;", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Result;", ">;"}) GoogleApiClient $r1, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/Boolean;", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Result;", ">;"}) String $r2, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/Boolean;", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Result;", ">;"}) String $r3, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/Boolean;", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Result;", ">;"}) String $r4, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/Boolean;", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Result;", ">;"}) String $r5, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/Boolean;", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Result;", ">;"}) Boolean $r6, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/Boolean;", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Result;", ">;"}) String $r7) throws  {
        if (zzl.isEnabled()) {
            zzl.zzh("updateCircle", $r2, $r3, $r4, $r5, $r6, $r7);
        }
        final String str = $r2;
        final String str2 = $r3;
        final String str3 = $r4;
        final String str4 = $r5;
        final Boolean bool = $r6;
        final String str5 = $r7;
        return $r1.zzd(new zzb(this, $r1) {
            final /* synthetic */ zzyx aSW;

            protected void zza(zzn $r1) throws RemoteException {
                $r1.zza((zzqk.zzb) this, str, str2, str3, str4, bool, str5);
            }
        });
    }

    public PendingResult<UpdatePersonCircleResult> updatePersonCircles(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/GraphUpdate$UpdatePersonCircleResult;", ">;"}) GoogleApiClient $r1, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/GraphUpdate$UpdatePersonCircleResult;", ">;"}) String $r2, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/GraphUpdate$UpdatePersonCircleResult;", ">;"}) String $r3, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/GraphUpdate$UpdatePersonCircleResult;", ">;"}) String $r4, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/GraphUpdate$UpdatePersonCircleResult;", ">;"}) List<String> $r5, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/GraphUpdate$UpdatePersonCircleResult;", ">;"}) List<String> $r6) throws  {
        return zza($r1, $r2, $r3, $r4, $r5, $r6, null);
    }

    public PendingResult<UpdatePersonCircleResult> zza(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Lcom/google/android/gms/common/server/FavaDiagnosticsEntity;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/GraphUpdate$UpdatePersonCircleResult;", ">;"}) GoogleApiClient $r1, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Lcom/google/android/gms/common/server/FavaDiagnosticsEntity;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/GraphUpdate$UpdatePersonCircleResult;", ">;"}) String $r2, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Lcom/google/android/gms/common/server/FavaDiagnosticsEntity;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/GraphUpdate$UpdatePersonCircleResult;", ">;"}) String $r3, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Lcom/google/android/gms/common/server/FavaDiagnosticsEntity;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/GraphUpdate$UpdatePersonCircleResult;", ">;"}) String $r4, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Lcom/google/android/gms/common/server/FavaDiagnosticsEntity;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/GraphUpdate$UpdatePersonCircleResult;", ">;"}) List<String> $r5, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Lcom/google/android/gms/common/server/FavaDiagnosticsEntity;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/GraphUpdate$UpdatePersonCircleResult;", ">;"}) List<String> $r6, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Lcom/google/android/gms/common/server/FavaDiagnosticsEntity;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/GraphUpdate$UpdatePersonCircleResult;", ">;"}) FavaDiagnosticsEntity $r7) throws  {
        if (zzl.isEnabled()) {
            zzl.zzh("updatePersonCircles", $r2, $r3, $r4, $r5, $r6, $r7);
        }
        final String str = $r2;
        final String str2 = $r3;
        final String str3 = $r4;
        final List<String> list = $r5;
        final List<String> list2 = $r6;
        final FavaDiagnosticsEntity favaDiagnosticsEntity = $r7;
        return $r1.zzd(new zza<UpdatePersonCircleResult>(this, $r1) {
            final /* synthetic */ zzyx aSW;

            protected void zza(zzn $r1) throws RemoteException {
                $r1.zza((zzqk.zzb) this, str, str2, str3, list, list2, favaDiagnosticsEntity);
            }

            protected /* synthetic */ Result zzb(Status $r1) throws  {
                return zzgf($r1);
            }

            protected UpdatePersonCircleResult zzgf(final Status $r1) throws  {
                return new UpdatePersonCircleResult(this) {
                    final /* synthetic */ C09117 aTn;

                    public List<String> getAddedCircles() throws  {
                        return null;
                    }

                    public List<String> getRemovedCircles() throws  {
                        return null;
                    }

                    public Status getStatus() throws  {
                        return $r1;
                    }
                };
            }
        });
    }
}
