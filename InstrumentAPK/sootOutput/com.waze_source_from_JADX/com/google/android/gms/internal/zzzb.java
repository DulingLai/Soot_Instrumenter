package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.people.Notifications;
import com.google.android.gms.people.Notifications.OnDataChanged;
import com.google.android.gms.people.People;
import com.google.android.gms.people.People.zzb;
import com.google.android.gms.people.internal.zzl;
import com.google.android.gms.people.internal.zzn;
import com.google.android.gms.people.internal.zzn.zzaa;
import dalvik.annotation.Signature;

/* compiled from: dalvik_source_com.waze.apk */
public class zzzb implements Notifications {
    private PendingResult<Result> zza(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/people/Notifications$OnDataChanged;", "Ljava/lang/String;", "Ljava/lang/String;", "I)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Result;", ">;"}) GoogleApiClient $r1, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/people/Notifications$OnDataChanged;", "Ljava/lang/String;", "Ljava/lang/String;", "I)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Result;", ">;"}) OnDataChanged $r2, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/people/Notifications$OnDataChanged;", "Ljava/lang/String;", "Ljava/lang/String;", "I)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Result;", ">;"}) String $r3, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/people/Notifications$OnDataChanged;", "Ljava/lang/String;", "Ljava/lang/String;", "I)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Result;", ">;"}) String $r4, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/people/Notifications$OnDataChanged;", "Ljava/lang/String;", "Ljava/lang/String;", "I)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Result;", ">;"}) int $i0) throws  {
        final zzaa zza = ((zzn) $r1.zza(People.aMc)).zza($r1, $r2);
        final String str = $r3;
        final String str2 = $r4;
        final int i = $i0;
        return $r1.zzc(new zzb(this, $r1) {
            final /* synthetic */ zzzb aTN;

            protected void zza(zzn $r1) throws RemoteException {
                $r1.zza(zza, str, str2, i);
                zzc(Status.CQ);
            }
        });
    }

    public PendingResult<Result> registerOnDataChangedListenerForAllOwners(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/people/Notifications$OnDataChanged;", "I)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Result;", ">;"}) GoogleApiClient $r1, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/people/Notifications$OnDataChanged;", "I)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Result;", ">;"}) OnDataChanged $r2, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/people/Notifications$OnDataChanged;", "I)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Result;", ">;"}) int $i0) throws  {
        if (zzl.isEnabled()) {
            zzl.zzh("registerOnDataChangedListenerForAllOwners", Integer.valueOf($i0));
        }
        return zza($r1, $r2, null, null, $i0);
    }

    public PendingResult<Result> registerOnDataChangedListenerForOwner(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/people/Notifications$OnDataChanged;", "Ljava/lang/String;", "Ljava/lang/String;", "I)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Result;", ">;"}) GoogleApiClient $r1, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/people/Notifications$OnDataChanged;", "Ljava/lang/String;", "Ljava/lang/String;", "I)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Result;", ">;"}) OnDataChanged $r2, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/people/Notifications$OnDataChanged;", "Ljava/lang/String;", "Ljava/lang/String;", "I)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Result;", ">;"}) String $r3, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/people/Notifications$OnDataChanged;", "Ljava/lang/String;", "Ljava/lang/String;", "I)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Result;", ">;"}) String $r4, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/people/Notifications$OnDataChanged;", "Ljava/lang/String;", "Ljava/lang/String;", "I)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Result;", ">;"}) int $i0) throws  {
        if ($r3 == null) {
            throw new IllegalArgumentException("account must not be null");
        }
        if (zzl.isEnabled()) {
            zzl.zzh("registerOnDataChangedListenerForOwner", $r3, $r4, Integer.valueOf($i0));
        }
        return zza($r1, $r2, $r3, $r4, $i0);
    }

    public PendingResult<Result> unregisterOnDataChangedListener(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/people/Notifications$OnDataChanged;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Result;", ">;"}) GoogleApiClient $r1, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/people/Notifications$OnDataChanged;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Result;", ">;"}) final OnDataChanged $r2) throws  {
        if (zzl.isEnabled()) {
            zzl.zzh("unregisterOnDataChangedListener", new Object[0]);
        }
        return $r1.zzc(new zzb(this, $r1) {
            final /* synthetic */ zzzb aTN;

            protected void zza(zzn $r1) throws RemoteException {
                $r1.zza($r2);
                zzc(Status.CQ);
            }
        });
    }
}
