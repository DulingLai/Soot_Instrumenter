package com.google.android.gms.people;

import android.os.Bundle;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.people.Graph.LoadPeopleResult;
import com.google.android.gms.people.People.BundleResult;
import com.google.android.gms.people.internal.zzl;
import dalvik.annotation.Signature;

/* compiled from: dalvik_source_com.waze.apk */
public interface InternalApi {

    /* compiled from: dalvik_source_com.waze.apk */
    public interface LoadPeopleForAspenResult extends LoadPeopleResult {
        String getNextPageToken() throws ;
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static class LoadPeopleForAspenOptions {
        public static final LoadPeopleForAspenOptions aMa = new LoadPeopleForAspenOptions();
        private String Yf;
        private int aMb = 20;
        private String zzaoj;

        public int getPageSize() throws  {
            return this.aMb;
        }

        public String getPageToken() throws  {
            return this.Yf;
        }

        public String getQuery() throws  {
            return this.zzaoj;
        }

        public LoadPeopleForAspenOptions setPageSize(int $i0) throws  {
            this.aMb = $i0;
            return this;
        }

        public LoadPeopleForAspenOptions setPageToken(String $r1) throws  {
            this.Yf = $r1;
            return this;
        }

        public LoadPeopleForAspenOptions setQuery(String $r1) throws  {
            this.zzaoj = $r1;
            return this;
        }

        public String toString() throws  {
            return zzl.zzd("mQuery", this.zzaoj, "mPageToken", this.Yf, "mPageSize", Integer.valueOf(this.aMb));
        }
    }

    PendingResult<BundleResult> internalCall(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Landroid/os/Bundle;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/People$BundleResult;", ">;"}) GoogleApiClient googleApiClient, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Landroid/os/Bundle;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/People$BundleResult;", ">;"}) Bundle bundle) throws ;

    PendingResult<LoadPeopleForAspenResult> loadPeopleForAspen(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/people/InternalApi$LoadPeopleForAspenOptions;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/InternalApi$LoadPeopleForAspenResult;", ">;"}) GoogleApiClient googleApiClient, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/people/InternalApi$LoadPeopleForAspenOptions;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/InternalApi$LoadPeopleForAspenResult;", ">;"}) String str, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/people/InternalApi$LoadPeopleForAspenOptions;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/InternalApi$LoadPeopleForAspenResult;", ">;"}) String str2, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/people/InternalApi$LoadPeopleForAspenOptions;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/InternalApi$LoadPeopleForAspenResult;", ">;"}) LoadPeopleForAspenOptions loadPeopleForAspenOptions) throws ;
}
