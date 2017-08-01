package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.content.Context;
import android.os.Bundle;
import android.os.IInterface;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.Api.zze;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.zzd.zzb;
import com.google.android.gms.common.internal.zzd.zzc;
import com.google.android.gms.common.internal.zzl.zza;
import dalvik.annotation.Signature;
import java.util.Set;

/* compiled from: dalvik_source_com.waze.apk */
public abstract class zzk<T extends IInterface> extends zzd<T> implements zze, zza {
    private final zzg Ep;
    private final Account f26P;
    private final Set<Scope> fN;

    /* compiled from: dalvik_source_com.waze.apk */
    class C07101 implements zzb {
        final /* synthetic */ ConnectionCallbacks JC;

        C07101(ConnectionCallbacks $r1) throws  {
            this.JC = $r1;
        }

        public void onConnected(@Nullable Bundle $r1) throws  {
            this.JC.onConnected($r1);
        }

        public void onConnectionSuspended(int $i0) throws  {
            this.JC.onConnectionSuspended($i0);
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    class C07112 implements zzc {
        final /* synthetic */ OnConnectionFailedListener JD;

        C07112(OnConnectionFailedListener $r1) throws  {
            this.JD = $r1;
        }

        public void onConnectionFailed(@NonNull ConnectionResult $r1) throws  {
            this.JD.onConnectionFailed($r1);
        }
    }

    protected zzk(Context $r1, Looper $r2, int $i0, zzg $r3) throws  {
        this($r1, $r2, zzm.zzbz($r1), GoogleApiAvailability.getInstance(), $i0, $r3, null, null);
    }

    protected zzk(Context $r1, Looper $r2, int $i0, zzg $r3, ConnectionCallbacks $r4, OnConnectionFailedListener $r5) throws  {
        this($r1, $r2, zzm.zzbz($r1), GoogleApiAvailability.getInstance(), $i0, $r3, (ConnectionCallbacks) zzab.zzag($r4), (OnConnectionFailedListener) zzab.zzag($r5));
    }

    protected zzk(Context $r1, Looper $r2, zzm $r3, GoogleApiAvailability $r4, int $i0, zzg $r5, ConnectionCallbacks $r6, OnConnectionFailedListener $r7) throws  {
        super($r1, $r2, $r3, $r4, $i0, zza($r6), zza($r7), $r5.zzawk());
        this.Ep = $r5;
        this.f26P = $r5.getAccount();
        this.fN = zzb($r5.zzawh());
    }

    @Nullable
    private static zzb zza(ConnectionCallbacks $r0) throws  {
        return $r0 == null ? null : new C07101($r0);
    }

    @Nullable
    private static zzc zza(OnConnectionFailedListener $r0) throws  {
        return $r0 == null ? null : new C07112($r0);
    }

    private Set<Scope> zzb(@NonNull @Signature({"(", "Ljava/util/Set", "<", "Lcom/google/android/gms/common/api/Scope;", ">;)", "Ljava/util/Set", "<", "Lcom/google/android/gms/common/api/Scope;", ">;"}) Set<Scope> $r1) throws  {
        Set<Scope> $r2 = zzc($r1);
        for (Scope contains : $r2) {
            if (!$r1.contains(contains)) {
                throw new IllegalStateException("Expanding scopes is not permitted, use implied scopes instead");
            }
        }
        return $r2;
    }

    public final Account getAccount() throws  {
        return this.f26P;
    }

    protected final Set<Scope> zzavz() throws  {
        return this.fN;
    }

    protected final zzg zzaws() throws  {
        return this.Ep;
    }

    @NonNull
    protected Set<Scope> zzc(@NonNull @Signature({"(", "Ljava/util/Set", "<", "Lcom/google/android/gms/common/api/Scope;", ">;)", "Ljava/util/Set", "<", "Lcom/google/android/gms/common/api/Scope;", ">;"}) Set<Scope> $r1) throws  {
        return $r1;
    }
}
