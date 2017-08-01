package com.google.android.gms.plus;

import android.content.Context;
import android.os.Looper;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.ApiOptions.Optional;
import com.google.android.gms.common.api.Api.zzf;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.common.internal.zzg;
import com.google.android.gms.common.util.zzu;
import com.google.android.gms.internal.zzaaa;
import com.google.android.gms.internal.zzaab;
import com.google.android.gms.internal.zzzy;
import com.google.android.gms.internal.zzzz;
import com.google.android.gms.plus.internal.PlusCommonExtras;
import com.google.android.gms.plus.internal.PlusSession;
import java.util.HashSet;
import java.util.Set;

/* compiled from: dalvik_source_com.waze.apk */
public final class Plus {
    public static final Api<PlusOptions> API = new Api("Plus.API", cb, ca);
    @Deprecated
    public static final Account AccountApi = new zzzy();
    public static final FirstPartyPeople FirstPartyPeopleApi = new zzaaa();
    public static final People PeopleApi = new zzaab();
    public static final Scope SCOPE_PLUS_LOGIN = new Scope(Scopes.PLUS_LOGIN);
    public static final Scope SCOPE_PLUS_PROFILE = new Scope(Scopes.PLUS_ME);
    public static final zza aYn = new zzzz();
    public static final zzf<com.google.android.gms.plus.internal.zzf> ca = new zzf();
    static final com.google.android.gms.common.api.Api.zza<com.google.android.gms.plus.internal.zzf, PlusOptions> cb = new C09981();

    /* compiled from: dalvik_source_com.waze.apk */
    public static abstract class zza<R extends Result> extends com.google.android.gms.internal.zzqk.zza<R, com.google.android.gms.plus.internal.zzf> {
        public zza(GoogleApiClient $r1) throws  {
            super(Plus.ca, $r1);
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    class C09981 extends com.google.android.gms.common.api.Api.zza<com.google.android.gms.plus.internal.zzf, PlusOptions> {
        C09981() throws  {
        }

        public int getPriority() throws  {
            return 2;
        }

        public com.google.android.gms.plus.internal.zzf zza(Context $r1, Looper $r2, zzg $r3, PlusOptions $r8, ConnectionCallbacks $r4, OnConnectionFailedListener $r5) throws  {
            if ($r8 == null) {
                PlusOptions plusOptions = new PlusOptions();
            }
            String[] $r14 = new String[null];
            return new com.google.android.gms.plus.internal.zzf($r1, $r2, $r3, new PlusSession($r3.zzavv().name, zzu.zzd($r3.zzawh()), (String[]) $r8.aYp.toArray($r14), new String[null], $r1.getPackageName(), $r1.getPackageName(), null, new PlusCommonExtras()), $r4, $r5);
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class PlusOptions implements Optional {
        final String aYo;
        final Set<String> aYp;

        /* compiled from: dalvik_source_com.waze.apk */
        public static final class Builder {
            String aYo;
            final Set<String> aYp = new HashSet();

            public Builder addActivityTypes(String... $r1) throws  {
                zzab.zzb((Object) $r1, (Object) "activityTypes may not be null.");
                for (String $r2 : $r1) {
                    this.aYp.add($r2);
                }
                return this;
            }

            public PlusOptions build() throws  {
                return new PlusOptions();
            }

            public Builder setServerClientId(String $r1) throws  {
                this.aYo = $r1;
                return this;
            }
        }

        private PlusOptions() throws  {
            this.aYo = null;
            this.aYp = new HashSet();
        }

        private PlusOptions(Builder $r1) throws  {
            this.aYo = $r1.aYo;
            this.aYp = $r1.aYp;
        }

        public static Builder builder() throws  {
            return new Builder();
        }
    }

    private Plus() throws  {
    }

    public static com.google.android.gms.plus.internal.zzf zzf(GoogleApiClient $r0, boolean $z0) throws  {
        zzab.zzb($r0 != null, (Object) "GoogleApiClient parameter is required.");
        zzab.zza($r0.isConnected(), (Object) "GoogleApiClient must be connected.");
        zzab.zza($r0.zza(API), (Object) "GoogleApiClient is not configured to use the Plus.API Api. Pass this into GoogleApiClient.Builder#addApi() to use this feature.");
        boolean $z1 = $r0.hasConnectedApi(API);
        if (!$z0 || $z1) {
            return $z1 ? (com.google.android.gms.plus.internal.zzf) $r0.zza(ca) : null;
        } else {
            throw new IllegalStateException("GoogleApiClient has an optional Plus.API and is not connected to Plus. Use GoogleApiClient.hasConnectedApi(Plus.API) to guard this call.");
        }
    }
}
