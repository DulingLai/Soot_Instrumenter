package com.google.android.gms.people.identity;

import android.os.Bundle;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.data.DataBuffer;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.people.identity.models.Person;
import com.google.android.gms.people.identity.models.PersonReference;
import dalvik.annotation.Signature;

/* compiled from: dalvik_source_com.waze.apk */
public interface IdentityApi {

    /* compiled from: dalvik_source_com.waze.apk */
    public interface CustomPersonListResult<PersonRefType> extends Releasable, Result {
        PendingResult<CustomPersonListResult<PersonRefType>> getNextPendingResult() throws ;

        DataBuffer<PersonRefType> getPersonBuffer() throws ;

        boolean isResultComplete() throws ;

        void release() throws ;
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public interface CustomPersonResult<PersonType> extends Releasable, Result {
        PendingResult<CustomPersonResult<PersonType>> getNextPendingResult() throws ;

        DataBuffer<PersonType> getPersonBuffer() throws ;

        boolean isLocalResultComplete() throws ;

        boolean isResultComplete() throws ;

        void release() throws ;
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static class GetOptions {
        public final zza aMr;
        public final ResultCallback callback;
        public final boolean useCachedData;
        public final boolean useContactData;
        public final boolean useWebData;

        /* compiled from: dalvik_source_com.waze.apk */
        public static final class zza {
            private zza aMs = new zza().zzcct();
            private boolean aMt = true;
            private boolean aMu = false;
            private boolean aMv = true;

            public zza zzb(zza $r1) throws  {
                this.aMs = (zza) zzab.zzag($r1);
                return this;
            }

            public GetOptions zzccu() throws  {
                return new GetOptions();
            }

            public zza zzcl(boolean $z0) throws  {
                this.aMt = $z0;
                return this;
            }

            public zza zzcm(boolean $z0) throws  {
                this.aMu = $z0;
                return this;
            }

            public zza zzcn(boolean $z0) throws  {
                this.aMv = $z0;
                return this;
            }
        }

        private GetOptions(zza $r1) throws  {
            this.aMr = $r1.aMs;
            this.useCachedData = $r1.aMt;
            this.useWebData = $r1.aMu;
            this.useContactData = $r1.aMv;
            this.callback = null;
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static class ListOptions {
        public final zza aMr;
        public final boolean useCachedData;
        public final boolean useContactData;
        public final boolean useWebData;

        /* compiled from: dalvik_source_com.waze.apk */
        public static final class zza {
            private zza aMs = new zza().zzcct();
            private boolean aMt = true;
            private boolean aMu = false;
            private boolean aMv = true;

            public static zza zza(ListOptions $r0) throws  {
                return new zza().zzco($r0.useCachedData).zzcp($r0.useWebData).zzcq($r0.useContactData).zza(zza.zza($r0.aMr));
            }

            public zza zza(zza $r1) throws  {
                return zzc($r1.zzcct());
            }

            public zza zzc(zza $r1) throws  {
                this.aMs = (zza) zzab.zzag($r1);
                return this;
            }

            public ListOptions zzccv() throws  {
                return new ListOptions();
            }

            public zza zzco(boolean $z0) throws  {
                this.aMt = $z0;
                return this;
            }

            public zza zzcp(boolean $z0) throws  {
                this.aMu = $z0;
                return this;
            }

            public zza zzcq(boolean $z0) throws  {
                this.aMv = $z0;
                return this;
            }
        }

        private ListOptions(zza $r1) throws  {
            this.aMr = $r1.aMs;
            this.useCachedData = $r1.aMt;
            this.useWebData = $r1.aMu;
            this.useContactData = $r1.aMv;
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public interface PersonListResult extends Releasable, Result {
        PendingResult<PersonListResult> getNextPendingResult() throws ;

        DataBuffer<PersonReference> getPersonBuffer() throws ;

        boolean isResultComplete() throws ;

        void release() throws ;
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public interface PersonResult extends Releasable, Result {
        PendingResult<PersonResult> getNextPendingResult() throws ;

        DataBuffer<Person> getPersonBuffer() throws ;

        boolean isLocalResultComplete() throws ;

        boolean isResultComplete() throws ;

        void release() throws ;
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class zza {
        public final String aMn;
        public final Bundle aMo;
        public final String accountName;
        public final String pageId;

        /* compiled from: dalvik_source_com.waze.apk */
        public static final class zza {
            public String aMp;
            public Bundle aMq = new Bundle();
            public String cJ;
            public String dL;

            public static zza zza(zza $r0) throws  {
                return new zza().zzmw($r0.accountName).zzmx($r0.pageId).zzmy($r0.aMn);
            }

            public zza zzbo(String $r1, String $r2) throws  {
                this.aMq.putString($r1, $r2);
                return this;
            }

            public zza zzcct() throws  {
                return new zza();
            }

            public zza zzmw(String $r1) throws  {
                this.dL = $r1;
                return this;
            }

            public zza zzmx(String $r1) throws  {
                this.aMp = $r1;
                return this;
            }

            public zza zzmy(String $r1) throws  {
                this.cJ = $r1;
                return this;
            }
        }

        private zza(zza $r1) throws  {
            this.accountName = $r1.dL;
            this.pageId = $r1.aMp;
            this.aMn = $r1.cJ;
            this.aMo = $r1.aMq;
        }
    }

    <PersonType> PendingResult<CustomPersonResult<PersonType>> getByIds(@Signature({"<PersonType:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/people/identity/IdentityApi$GetOptions;", "Lcom/google/android/gms/people/identity/PersonFactory", "<TPersonType;>;[", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/identity/IdentityApi$CustomPersonResult", "<TPersonType;>;>;"}) GoogleApiClient googleApiClient, @Signature({"<PersonType:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/people/identity/IdentityApi$GetOptions;", "Lcom/google/android/gms/people/identity/PersonFactory", "<TPersonType;>;[", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/identity/IdentityApi$CustomPersonResult", "<TPersonType;>;>;"}) GetOptions getOptions, @Signature({"<PersonType:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/people/identity/IdentityApi$GetOptions;", "Lcom/google/android/gms/people/identity/PersonFactory", "<TPersonType;>;[", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/identity/IdentityApi$CustomPersonResult", "<TPersonType;>;>;"}) PersonFactory<PersonType> personFactory, @Signature({"<PersonType:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/people/identity/IdentityApi$GetOptions;", "Lcom/google/android/gms/people/identity/PersonFactory", "<TPersonType;>;[", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/identity/IdentityApi$CustomPersonResult", "<TPersonType;>;>;"}) String... strArr) throws ;

    PendingResult<PersonResult> getByIds(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/people/identity/IdentityApi$GetOptions;", "[", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/identity/IdentityApi$PersonResult;", ">;"}) GoogleApiClient googleApiClient, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/people/identity/IdentityApi$GetOptions;", "[", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/identity/IdentityApi$PersonResult;", ">;"}) GetOptions getOptions, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/people/identity/IdentityApi$GetOptions;", "[", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/identity/IdentityApi$PersonResult;", ">;"}) String... strArr) throws ;

    PendingResult<PersonListResult> list(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/people/identity/IdentityApi$ListOptions;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/identity/IdentityApi$PersonListResult;", ">;"}) GoogleApiClient googleApiClient, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/people/identity/IdentityApi$ListOptions;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/identity/IdentityApi$PersonListResult;", ">;"}) ListOptions listOptions) throws ;

    <PersonRefType> PendingResult<CustomPersonListResult<PersonRefType>> list(@Signature({"<PersonRefType:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/people/identity/IdentityApi$ListOptions;", "Lcom/google/android/gms/people/identity/PersonListFactory", "<TPersonRefType;>;)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/identity/IdentityApi$CustomPersonListResult", "<TPersonRefType;>;>;"}) GoogleApiClient googleApiClient, @Signature({"<PersonRefType:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/people/identity/IdentityApi$ListOptions;", "Lcom/google/android/gms/people/identity/PersonListFactory", "<TPersonRefType;>;)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/identity/IdentityApi$CustomPersonListResult", "<TPersonRefType;>;>;"}) ListOptions listOptions, @Signature({"<PersonRefType:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/people/identity/IdentityApi$ListOptions;", "Lcom/google/android/gms/people/identity/PersonListFactory", "<TPersonRefType;>;)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/identity/IdentityApi$CustomPersonListResult", "<TPersonRefType;>;>;"}) PersonListFactory<PersonRefType> personListFactory) throws ;

    PendingResult<PersonListResult> listByEmail(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/people/identity/IdentityApi$ListOptions;", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/identity/IdentityApi$PersonListResult;", ">;"}) GoogleApiClient googleApiClient, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/people/identity/IdentityApi$ListOptions;", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/identity/IdentityApi$PersonListResult;", ">;"}) ListOptions listOptions, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/people/identity/IdentityApi$ListOptions;", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/identity/IdentityApi$PersonListResult;", ">;"}) String str) throws ;

    PendingResult<PersonListResult> listByPhone(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/people/identity/IdentityApi$ListOptions;", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/identity/IdentityApi$PersonListResult;", ">;"}) GoogleApiClient googleApiClient, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/people/identity/IdentityApi$ListOptions;", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/identity/IdentityApi$PersonListResult;", ">;"}) ListOptions listOptions, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/people/identity/IdentityApi$ListOptions;", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/identity/IdentityApi$PersonListResult;", ">;"}) String str) throws ;
}
