package com.google.android.gms.internal;

import android.os.Bundle;
import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzqk.zzb;
import com.google.android.gms.people.InternalApi;
import com.google.android.gms.people.InternalApi.LoadPeopleForAspenOptions;
import com.google.android.gms.people.InternalApi.LoadPeopleForAspenResult;
import com.google.android.gms.people.People.BundleResult;
import com.google.android.gms.people.People.zza;
import com.google.android.gms.people.internal.zzl;
import com.google.android.gms.people.internal.zzn;
import com.google.android.gms.people.model.PersonBuffer;
import dalvik.annotation.Signature;

/* compiled from: dalvik_source_com.waze.apk */
public class zzza implements InternalApi {
    public PendingResult<BundleResult> internalCall(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Landroid/os/Bundle;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/People$BundleResult;", ">;"}) GoogleApiClient $r1, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Landroid/os/Bundle;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/People$BundleResult;", ">;"}) final Bundle $r2) throws  {
        if (zzl.isEnabled()) {
            zzl.zzh("internalCall", $r2);
        }
        return $r1.zzc(new zza<BundleResult>(this, $r1) {
            final /* synthetic */ zzza aTH;

            protected void zza(zzn $r1) throws RemoteException {
                $r1.zza((zzb) this, $r2);
            }

            protected /* synthetic */ Result zzb(Status $r1) throws  {
                return zzgk($r1);
            }

            protected BundleResult zzgk(final Status $r1) throws  {
                return new BundleResult(this) {
                    final /* synthetic */ C09312 aTK;

                    public Bundle getBundle() throws  {
                        return null;
                    }

                    public Status getStatus() throws  {
                        return $r1;
                    }

                    public void release() throws  {
                    }
                };
            }
        });
    }

    public PendingResult<LoadPeopleForAspenResult> loadPeopleForAspen(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/people/InternalApi$LoadPeopleForAspenOptions;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/InternalApi$LoadPeopleForAspenResult;", ">;"}) GoogleApiClient $r1, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/people/InternalApi$LoadPeopleForAspenOptions;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/InternalApi$LoadPeopleForAspenResult;", ">;"}) String $r2, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/people/InternalApi$LoadPeopleForAspenOptions;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/InternalApi$LoadPeopleForAspenResult;", ">;"}) String $r3, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/people/InternalApi$LoadPeopleForAspenOptions;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/InternalApi$LoadPeopleForAspenResult;", ">;"}) LoadPeopleForAspenOptions $r4) throws  {
        if (zzl.isEnabled()) {
            zzl.zzh("loadPeopleForAspen", $r2, $r3, $r4);
        }
        final String str = $r2;
        final String str2 = $r3;
        final LoadPeopleForAspenOptions loadPeopleForAspenOptions = $r4;
        return $r1.zzc(new zza<LoadPeopleForAspenResult>(this, $r1) {
            final /* synthetic */ zzza aTH;

            protected void zza(zzn $r1) throws RemoteException {
                $r1.zza((zzb) this, str, str2, loadPeopleForAspenOptions);
            }

            protected /* synthetic */ Result zzb(Status $r1) throws  {
                return zzgj($r1);
            }

            protected LoadPeopleForAspenResult zzgj(final Status $r1) throws  {
                return new LoadPeopleForAspenResult(this) {
                    final /* synthetic */ C09291 aTI;

                    public String getNextPageToken() throws  {
                        return null;
                    }

                    public PersonBuffer getPeople() throws  {
                        return null;
                    }

                    public Status getStatus() throws  {
                        return $r1;
                    }

                    public void release() throws  {
                    }
                };
            }
        });
    }
}
