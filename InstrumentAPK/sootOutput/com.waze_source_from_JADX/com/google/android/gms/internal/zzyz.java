package com.google.android.gms.internal;

import android.os.RemoteException;
import android.text.TextUtils;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.people.InteractionFeedback;
import com.google.android.gms.people.People.zzb;
import com.google.android.gms.people.internal.zzl;
import com.google.android.gms.people.internal.zzn;
import com.google.android.gms.people.internal.zzq;
import com.google.android.gms.people.model.AutocompleteBuffer;
import dalvik.annotation.Signature;

/* compiled from: dalvik_source_com.waze.apk */
public class zzyz implements InteractionFeedback {
    public PendingResult<Result> sendAutocompleteSelectedFeedback(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/people/model/AutocompleteBuffer;", "II)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Result;", ">;"}) GoogleApiClient $r1, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/people/model/AutocompleteBuffer;", "II)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Result;", ">;"}) AutocompleteBuffer $r2, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/people/model/AutocompleteBuffer;", "II)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Result;", ">;"}) int $i0, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/people/model/AutocompleteBuffer;", "II)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Result;", ">;"}) int $i1) throws  {
        return sendAutocompleteSelectedFeedback($r1, $r2, $i0, $i1, 0);
    }

    public PendingResult<Result> sendAutocompleteSelectedFeedback(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/people/model/AutocompleteBuffer;", "IIJ)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Result;", ">;"}) GoogleApiClient $r1, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/people/model/AutocompleteBuffer;", "IIJ)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Result;", ">;"}) AutocompleteBuffer $r2, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/people/model/AutocompleteBuffer;", "IIJ)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Result;", ">;"}) int $i0, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/people/model/AutocompleteBuffer;", "IIJ)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Result;", ">;"}) int $i1, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/people/model/AutocompleteBuffer;", "IIJ)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Result;", ">;"}) long $l2) throws  {
        if (zzl.isEnabled()) {
            zzl.zzh("sendAutocompleteSelectedFeedback", $r2, Integer.valueOf($i1), Long.valueOf($l2));
        }
        final AutocompleteBuffer autocompleteBuffer = $r2;
        final int i = $i0;
        final int i2 = $i1;
        final long j = $l2;
        return $r1.zzd(new zzb(this, $r1) {
            final /* synthetic */ zzyz aTB;

            protected void zza(zzn $r1) throws RemoteException {
                $r1.zza((zzqk.zzb) this, autocompleteBuffer, i, i2, j);
            }
        });
    }

    public PendingResult<Result> sendAutocompleteShownFeedback(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/people/model/AutocompleteBuffer;", "I)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Result;", ">;"}) GoogleApiClient $r1, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/people/model/AutocompleteBuffer;", "I)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Result;", ">;"}) AutocompleteBuffer $r2, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/people/model/AutocompleteBuffer;", "I)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Result;", ">;"}) int $i0) throws  {
        return sendAutocompleteShownFeedback($r1, $r2, $i0, 0);
    }

    public PendingResult<Result> sendAutocompleteShownFeedback(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/people/model/AutocompleteBuffer;", "IJ)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Result;", ">;"}) GoogleApiClient $r1, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/people/model/AutocompleteBuffer;", "IJ)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Result;", ">;"}) AutocompleteBuffer $r2, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/people/model/AutocompleteBuffer;", "IJ)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Result;", ">;"}) int $i0, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/people/model/AutocompleteBuffer;", "IJ)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Result;", ">;"}) long $l1) throws  {
        if (zzl.isEnabled()) {
            zzl.zzh("sendAutocompleteShownFeedback", $r2, Integer.valueOf($i0), Long.valueOf($l1));
        }
        final AutocompleteBuffer autocompleteBuffer = $r2;
        final int i = $i0;
        final long j = $l1;
        return $r1.zzd(new zzb(this, $r1) {
            final /* synthetic */ zzyz aTB;

            protected void zza(zzn $r1) throws RemoteException {
                $r1.zza((zzqk.zzb) this, autocompleteBuffer, -1, i, j);
            }
        });
    }

    public PendingResult<Result> sendFeedback(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "I)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Result;", ">;"}) GoogleApiClient $r1, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "I)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Result;", ">;"}) final String $r2, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "I)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Result;", ">;"}) final int $i0) throws  {
        if (zzl.isEnabled()) {
            zzl.zzh("sendFeedback", $r2, Integer.valueOf($i0));
        }
        return $r1.zzd(new zzb(this, $r1) {
            final /* synthetic */ zzyz aTB;

            protected void zza(zzn $r1) throws RemoteException {
                $r1.zzh((zzqk.zzb) this, $r2, $i0);
            }
        });
    }

    public PendingResult<Result> sendFeedback(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "[", "Ljava/lang/String;", "I)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Result;", ">;"}) GoogleApiClient $r1, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "[", "Ljava/lang/String;", "I)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Result;", ">;"}) String[] $r2, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "[", "Ljava/lang/String;", "I)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Result;", ">;"}) final int $i0) throws  {
        if (zzl.isEnabled()) {
            zzl.zzh("sendFeedback", $r2, Integer.valueOf($i0));
        }
        final String $r5 = TextUtils.join(zzq.aRb, $r2);
        return $r1.zzd(new zzb(this, $r1) {
            final /* synthetic */ zzyz aTB;

            protected void zza(zzn $r1) throws RemoteException {
                $r1.zzh((zzqk.zzb) this, $r5, $i0);
            }
        });
    }
}
