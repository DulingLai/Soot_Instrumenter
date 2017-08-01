package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.internal.zzqk.zzb;
import com.google.android.gms.people.Autocomplete;
import com.google.android.gms.people.Autocomplete.AutocompleteOptions;
import com.google.android.gms.people.Autocomplete.AutocompleteResult;
import com.google.android.gms.people.Autocomplete.AutocompleteSession;
import com.google.android.gms.people.Autocomplete.AutocompletionListener;
import com.google.android.gms.people.Autocomplete.ClientConfig;
import com.google.android.gms.people.People;
import com.google.android.gms.people.internal.autocomplete.zza;
import com.google.android.gms.people.internal.zzl;
import com.google.android.gms.people.internal.zzn;
import com.google.android.gms.people.model.AutocompleteBuffer;
import dalvik.annotation.Signature;

/* compiled from: dalvik_source_com.waze.apk */
public class zzyu implements Autocomplete {
    public AutocompleteSession beginAutocompleteSession(GoogleApiClient $r1, ClientConfig $r2, String $r3, AutocompletionListener $r4) throws  {
        return new zza($r1, $r2, $r3, $r4);
    }

    public PendingResult<AutocompleteResult> loadAutocompleteList(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Lcom/google/android/gms/people/Autocomplete$AutocompleteOptions;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Autocomplete$AutocompleteResult;", ">;"}) GoogleApiClient $r1, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Lcom/google/android/gms/people/Autocomplete$AutocompleteOptions;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Autocomplete$AutocompleteResult;", ">;"}) final String $r2, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Lcom/google/android/gms/people/Autocomplete$AutocompleteOptions;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Autocomplete$AutocompleteResult;", ">;"}) final AutocompleteOptions $r3) throws  {
        zzab.zzag($r3);
        if (zzl.isEnabled()) {
            zzl.zzh("loadAutocompleteList", $r2, $r3);
        }
        return $r1.zzc(new People.zza<AutocompleteResult>(this, $r1) {
            final /* synthetic */ zzyu aSu;

            protected void zza(zzn $r1) throws RemoteException {
                $r1.zza((zzb) this, $r2, $r3);
            }

            protected /* synthetic */ Result zzb(Status $r1) throws  {
                return zzfu($r1);
            }

            protected AutocompleteResult zzfu(final Status $r1) throws  {
                return new AutocompleteResult(this) {
                    final /* synthetic */ C08781 aSv;

                    public AutocompleteBuffer getAutocompleteEntries() throws  {
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
