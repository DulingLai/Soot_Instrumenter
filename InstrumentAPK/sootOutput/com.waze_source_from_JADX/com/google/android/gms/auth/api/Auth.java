package com.google.android.gms.auth.api;

import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.android.gms.auth.api.accountactivationstate.AccountActivationStateApi;
import com.google.android.gms.auth.api.credentials.CredentialsApi;
import com.google.android.gms.auth.api.credentials.PasswordSpecification;
import com.google.android.gms.auth.api.credentials.internal.zze;
import com.google.android.gms.auth.api.credentials.internal.zzg;
import com.google.android.gms.auth.api.proxy.ProxyApi;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.internal.zzc;
import com.google.android.gms.auth.api.signin.internal.zzd;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.ApiOptions.NoOptions;
import com.google.android.gms.common.api.Api.ApiOptions.Optional;
import com.google.android.gms.common.api.Api.zza;
import com.google.android.gms.common.api.Api.zzf;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.internal.zzmw;
import com.google.android.gms.internal.zzmx;
import com.google.android.gms.internal.zznc;
import com.google.android.gms.internal.zznh;
import dalvik.annotation.Signature;
import java.util.Collections;
import java.util.List;

/* compiled from: dalvik_source_com.waze.apk */
public final class Auth {
    public static final Api<NoOptions> ACCOUNT_STATUS_API = new Api("Auth.ACCOUNT_STATUS_API", ex, et);
    public static final AccountActivationStateApi AccountActivationStateApi = new zzmw();
    public static final Api<AuthCredentialsOptions> CREDENTIALS_API = new Api("Auth.CREDENTIALS_API", ew, es);
    public static final CredentialsApi CredentialsApi = new zze();
    public static final Api<GoogleSignInOptions> GOOGLE_SIGN_IN_API = new Api("Auth.GOOGLE_SIGN_IN_API", ey, eu);
    public static final GoogleSignInApi GoogleSignInApi = new zzc();
    public static final Api<AuthProxyOptions> PROXY_API = new Api("Auth.PROXY_API", ev, er);
    public static final ProxyApi ProxyApi = new zznh();
    public static final zzf<zznc> er = new zzf();
    public static final zzf<zzg> es = new zzf();
    public static final zzf<zzmx> et = new zzf();
    public static final zzf<zzd> eu = new zzf();
    private static final zza<zznc, AuthProxyOptions> ev = new C06561();
    private static final zza<zzg, AuthCredentialsOptions> ew = new C06572();
    private static final zza<zzmx, NoOptions> ex = new C06583();
    private static final zza<zzd, GoogleSignInOptions> ey = new C06594();

    /* compiled from: dalvik_source_com.waze.apk */
    class C06561 extends zza<zznc, AuthProxyOptions> {
        C06561() throws  {
        }

        public zznc zza(Context $r1, Looper $r2, com.google.android.gms.common.internal.zzg $r3, AuthProxyOptions $r4, ConnectionCallbacks $r5, OnConnectionFailedListener $r6) throws  {
            return new zznc($r1, $r2, $r3, $r4, $r5, $r6);
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    class C06572 extends zza<zzg, AuthCredentialsOptions> {
        C06572() throws  {
        }

        public zzg zza(Context $r1, Looper $r2, com.google.android.gms.common.internal.zzg $r3, AuthCredentialsOptions $r4, ConnectionCallbacks $r5, OnConnectionFailedListener $r6) throws  {
            return new zzg($r1, $r2, $r3, $r4, $r5, $r6);
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    class C06583 extends zza<zzmx, NoOptions> {
        C06583() throws  {
        }

        public /* synthetic */ Api.zze zza(Context $r1, Looper $r2, com.google.android.gms.common.internal.zzg $r3, Object $r4, ConnectionCallbacks $r5, OnConnectionFailedListener $r6) throws  {
            return zze($r1, $r2, $r3, (NoOptions) $r4, $r5, $r6);
        }

        public zzmx zze(Context $r1, Looper $r2, com.google.android.gms.common.internal.zzg $r3, NoOptions noOptions, ConnectionCallbacks $r5, OnConnectionFailedListener $r6) throws  {
            return new zzmx($r1, $r2, $r3, $r5, $r6);
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    class C06594 extends zza<zzd, GoogleSignInOptions> {
        C06594() throws  {
        }

        public zzd zza(Context $r1, Looper $r2, com.google.android.gms.common.internal.zzg $r3, @Nullable GoogleSignInOptions $r4, ConnectionCallbacks $r5, OnConnectionFailedListener $r6) throws  {
            return new zzd($r1, $r2, $r3, $r4, $r5, $r6);
        }

        public List<Scope> zza(@Nullable @Signature({"(", "Lcom/google/android/gms/auth/api/signin/GoogleSignInOptions;", ")", "Ljava/util/List", "<", "Lcom/google/android/gms/common/api/Scope;", ">;"}) GoogleSignInOptions $r1) throws  {
            return $r1 == null ? Collections.emptyList() : $r1.zzaei();
        }

        public /* synthetic */ List zzt(@Nullable Object $r2) throws  {
            return zza((GoogleSignInOptions) $r2);
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class AuthCredentialsOptions implements Optional {
        private final PasswordSpecification eA;
        private final String ez;

        /* compiled from: dalvik_source_com.waze.apk */
        public static class Builder {
            @NonNull
            private PasswordSpecification eA = PasswordSpecification.DEFAULT;
            private String ez;

            public AuthCredentialsOptions build() throws  {
                return new AuthCredentialsOptions();
            }

            public Builder setConsumerPackage(String $r1) throws  {
                this.ez = $r1;
                return this;
            }

            public Builder setPasswordSpecification(@NonNull PasswordSpecification $r1) throws  {
                this.eA = (PasswordSpecification) zzab.zzag($r1);
                return this;
            }
        }

        private AuthCredentialsOptions(Builder $r1) throws  {
            this.ez = $r1.ez;
            this.eA = $r1.eA;
        }

        public PasswordSpecification getPasswordSpecification() throws  {
            return this.eA;
        }

        public Bundle zzadn() throws  {
            Bundle $r1 = new Bundle();
            $r1.putString("consumer_package", this.ez);
            $r1.putParcelable("password_specification", this.eA);
            return $r1;
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class AuthProxyOptions implements Optional {
        private final Bundle eB;

        /* compiled from: dalvik_source_com.waze.apk */
        public static class Builder {
            private final Bundle eB = new Bundle();

            public AuthProxyOptions build() throws  {
                return new AuthProxyOptions(this.eB);
            }

            public Builder setAuthenticationOptions(Bundle $r1) throws  {
                if ($r1 == null) {
                    return this;
                }
                this.eB.clear();
                this.eB.putAll($r1);
                return this;
            }

            public Builder setConsumerPackage(String $r1) throws  {
                if ($r1 == null) {
                    return this;
                }
                this.eB.putString("consumerPkg", $r1);
                return this;
            }
        }

        private AuthProxyOptions(Bundle $r1) throws  {
            this.eB = $r1;
        }

        public Bundle getAuthenticationOptions() throws  {
            return new Bundle(this.eB);
        }
    }

    private Auth() throws  {
    }
}
