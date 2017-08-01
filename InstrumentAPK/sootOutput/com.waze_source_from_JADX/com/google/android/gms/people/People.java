package com.google.android.gms.people;

import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.ApiOptions.HasOptions;
import com.google.android.gms.common.api.Api.zzf;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.common.internal.zzg;
import com.google.android.gms.internal.zzyu;
import com.google.android.gms.internal.zzyv;
import com.google.android.gms.internal.zzyw;
import com.google.android.gms.internal.zzyx;
import com.google.android.gms.internal.zzyy;
import com.google.android.gms.internal.zzyz;
import com.google.android.gms.internal.zzza;
import com.google.android.gms.internal.zzzb;
import com.google.android.gms.internal.zzzc;
import com.google.android.gms.people.identity.IdentityApi;
import com.google.android.gms.people.identity.internal.zzh;
import com.google.android.gms.people.internal.zzn;

/* compiled from: dalvik_source_com.waze.apk */
public final class People {
    public static final Api<PeopleOptions1p> API_1P = new Api("People.API_1P", aha, aMc);
    public static final Autocomplete AutocompleteApi = new zzyu();
    public static final ContactsSync ContactsSyncApi = new zzyv();
    public static final Graph GraphApi = new zzyw();
    public static final GraphUpdate GraphUpdateApi = new zzyx();
    public static final IdentityApi IdentityApi = new zzh();
    public static final Images ImageApi = new zzyy();
    public static final InteractionFeedback InteractionFeedbackApi = new zzyz();
    public static final InternalApi InternalApi = new zzza();
    public static final Notifications NotificationApi = new zzzb();
    public static final int STATUS_INCOMPLETE = 100;
    public static final int STATUS_NOT_ALLOWED = 101;
    public static final Sync SyncApi = new zzzc();
    public static final zzf<zzn> aMc = new zzf();
    static final com.google.android.gms.common.api.Api.zza<zzn, PeopleOptions1p> aha = new C09691();

    /* compiled from: dalvik_source_com.waze.apk */
    public interface ReleasableResult extends Releasable, Result {
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static abstract class zza<R extends Result> extends com.google.android.gms.internal.zzqk.zza<R, zzn> {
        public zza(GoogleApiClient $r1) throws  {
            super(People.API_1P, $r1);
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static abstract class zzb extends zza<Result> {
        public zzb(GoogleApiClient $r1) throws  {
            super($r1);
        }

        protected Result zzb(Status $r1) throws  {
            return $r1;
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public interface BundleResult extends ReleasableResult {
        Bundle getBundle() throws ;
    }

    /* compiled from: dalvik_source_com.waze.apk */
    class C09691 extends com.google.android.gms.common.api.Api.zza<zzn, PeopleOptions1p> {
        C09691() throws  {
        }

        public zzn zza(Context $r1, Looper $r2, zzg $r3, PeopleOptions1p $r4, ConnectionCallbacks $r5, OnConnectionFailedListener $r6) throws  {
            zzab.zzb((Object) $r4, (Object) "Must provide valid PeopleOptions!");
            return new zzn($r1, $r2, $r5, $r6, String.valueOf($r4.aMd), $r3);
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class PeopleOptions1p implements HasOptions {
        private final int aMd;

        /* compiled from: dalvik_source_com.waze.apk */
        public static final class Builder {
            int aMd = -1;

            public PeopleOptions1p build() throws  {
                zzab.zzb(this.aMd >= 0, (Object) "Must provide valid client application ID!");
                return new PeopleOptions1p();
            }

            public Builder setClientApplicationId(int $i0) throws  {
                this.aMd = $i0;
                return this;
            }
        }

        private PeopleOptions1p(Builder $r1) throws  {
            this.aMd = $r1.aMd;
        }

        public static Builder builder() throws  {
            return new Builder();
        }
    }

    private People() throws  {
    }
}
