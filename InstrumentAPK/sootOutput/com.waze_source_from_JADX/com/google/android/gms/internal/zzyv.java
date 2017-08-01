package com.google.android.gms.internal;

import android.net.Uri;
import android.os.RemoteException;
import com.google.android.gms.common.api.BooleanResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.people.ContactsSync;
import com.google.android.gms.people.People.zza;
import com.google.android.gms.people.People.zzb;
import com.google.android.gms.people.internal.zzl;
import com.google.android.gms.people.internal.zzn;
import dalvik.annotation.Signature;

/* compiled from: dalvik_source_com.waze.apk */
public class zzyv implements ContactsSync {
    public PendingResult<BooleanResult> isSyncToContactsEnabled(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/BooleanResult;", ">;"}) GoogleApiClient $r1) throws  {
        if (zzl.isEnabled()) {
            zzl.zzh("isSyncToContactsEnabled", new Object[0]);
        }
        return $r1.zzc(new zza<BooleanResult>(this, $r1) {
            final /* synthetic */ zzyv aSx;

            protected void zza(zzn $r1) throws RemoteException {
                zzc(new BooleanResult(Status.CQ, $r1.isSyncToContactsEnabled()));
            }

            protected BooleanResult zzax(Status $r1) throws  {
                return new BooleanResult($r1, false);
            }

            protected /* synthetic */ Result zzb(Status $r1) throws  {
                return zzax($r1);
            }
        });
    }

    public PendingResult<Result> setSyncToContactsEnabled(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Z)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Result;", ">;"}) GoogleApiClient $r1, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Z)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Result;", ">;"}) final boolean $z0) throws  {
        if (zzl.isEnabled()) {
            zzl.zzh("setSyncToContactsEnabled", Boolean.valueOf($z0));
        }
        return $r1.zzd(new zzb(this, $r1) {
            final /* synthetic */ zzyv aSx;

            protected void zza(zzn $r1) throws RemoteException {
                $r1.zzdb($z0);
                zzc(Status.CQ);
            }
        });
    }

    public PendingResult<Result> setSyncToContactsSettings(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Z[", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Result;", ">;"}) GoogleApiClient $r1, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Z[", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Result;", ">;"}) String $r2, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Z[", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Result;", ">;"}) boolean $z0, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Z[", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Result;", ">;"}) String[] $r3) throws  {
        if (zzl.isEnabled()) {
            zzl.zzh("setSyncToContactsSettings", $r2, Boolean.valueOf($z0), $r3);
        }
        final String str = $r2;
        final boolean z = $z0;
        final String[] strArr = $r3;
        return $r1.zzd(new zzb(this, $r1) {
            final /* synthetic */ zzyv aSx;

            protected void zza(zzn $r1) throws RemoteException {
                $r1.zza((zzqk.zzb) this, str, z, strArr);
            }
        });
    }

    public PendingResult<Result> syncRawContact(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Landroid/net/Uri;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Result;", ">;"}) GoogleApiClient $r1, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Landroid/net/Uri;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Result;", ">;"}) final Uri $r2) throws  {
        if (zzl.isEnabled()) {
            zzl.zzh("syncRawContact", $r2);
        }
        return $r1.zzd(new zzb(this, $r1) {
            final /* synthetic */ zzyv aSx;

            protected void zza(zzn $r1) throws RemoteException {
                $r1.zzu($r2);
                zzc(Status.CQ);
            }
        });
    }
}
