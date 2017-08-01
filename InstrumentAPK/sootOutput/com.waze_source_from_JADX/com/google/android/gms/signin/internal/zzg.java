package com.google.android.gms.signin.internal;

import android.accounts.Account;
import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.internal.ResolveAccountRequest;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.common.internal.zzd.zzf;
import com.google.android.gms.common.internal.zzk;
import com.google.android.gms.common.internal.zzq;
import com.google.android.gms.internal.zzach;
import com.google.android.gms.internal.zzaci;
import com.google.android.gms.signin.internal.zze.zza;

/* compiled from: dalvik_source_com.waze.apk */
public class zzg extends zzk<zze> implements zzach {
    private final com.google.android.gms.common.internal.zzg Ep;
    private Integer Jn;
    private final boolean bgw;
    private final Bundle bgx;

    public zzg(Context $r1, Looper $r2, boolean $z0, com.google.android.gms.common.internal.zzg $r3, Bundle $r4, ConnectionCallbacks $r5, OnConnectionFailedListener $r6) throws  {
        super($r1, $r2, 44, $r3, $r5, $r6);
        this.bgw = $z0;
        this.Ep = $r3;
        this.bgx = $r4;
        this.Jn = $r3.zzawn();
    }

    public zzg(Context $r1, Looper $r2, boolean $z0, com.google.android.gms.common.internal.zzg $r3, zzaci com_google_android_gms_internal_zzaci, ConnectionCallbacks $r5, OnConnectionFailedListener $r6) throws  {
        this($r1, $r2, $z0, $r3, zza($r3), $r5, $r6);
    }

    public static Bundle zza(com.google.android.gms.common.internal.zzg $r0) throws  {
        zzaci $r2 = $r0.zzawm();
        Integer $r3 = $r0.zzawn();
        Bundle $r1 = new Bundle();
        $r1.putParcelable("com.google.android.gms.signin.internal.clientRequestedAccount", $r0.getAccount());
        if ($r3 != null) {
            $r1.putInt("com.google.android.gms.common.internal.ClientSettings.sessionId", $r3.intValue());
        }
        if ($r2 == null) {
            return $r1;
        }
        $r1.putBoolean("com.google.android.gms.signin.internal.offlineAccessRequested", $r2.zzcmw());
        $r1.putBoolean("com.google.android.gms.signin.internal.idTokenRequested", $r2.zzaej());
        $r1.putString("com.google.android.gms.signin.internal.serverClientId", $r2.zzaem());
        $r1.putBoolean("com.google.android.gms.signin.internal.usePromptModeForAuthCode", true);
        $r1.putBoolean("com.google.android.gms.signin.internal.forceCodeForRefreshToken", $r2.zzael());
        $r1.putString("com.google.android.gms.signin.internal.hostedDomain", $r2.zzaen());
        $r1.putBoolean("com.google.android.gms.signin.internal.waitForAccessTokenRefresh", $r2.zzcmx());
        if ($r2.zzcmy() != null) {
            $r1.putLong("com.google.android.gms.signin.internal.authApiSignInModuleVersion", $r2.zzcmy().longValue());
        }
        if ($r2.zzcmz() == null) {
            return $r1;
        }
        $r1.putLong("com.google.android.gms.signin.internal.realClientLibraryVersion", $r2.zzcmz().longValue());
        return $r1;
    }

    private ResolveAccountRequest zzcne() throws  {
        Account $r2 = this.Ep.zzavv();
        GoogleSignInAccount $r3 = null;
        if ("<<default account>>".equals($r2.name)) {
            $r3 = com.google.android.gms.auth.api.signin.internal.zzk.zzbc(getContext()).zzafb();
        }
        return new ResolveAccountRequest($r2, this.Jn.intValue(), $r3);
    }

    public void connect() throws  {
        zza((zzf) new zzi(this));
    }

    public void zza(zzq $r1, boolean $z0) throws  {
        try {
            ((zze) zzavx()).zza($r1, this.Jn.intValue(), $z0);
        } catch (RemoteException e) {
            Log.w("SignInClientImpl", "Remote service probably died when saveDefaultAccount is called");
        }
    }

    public void zza(zzd $r1) throws  {
        zzab.zzb((Object) $r1, (Object) "Expecting a valid ISignInCallbacks");
        try {
            ((zze) zzavx()).zza(new SignInRequest(zzcne()), $r1);
        } catch (RemoteException $r6) {
            Log.w("SignInClientImpl", "Remote service probably died when signIn is called");
            try {
                $r1.zzb(new SignInResponse(8));
            } catch (RemoteException e) {
                Log.wtf("SignInClientImpl", "ISignInCallbacks#onSignInComplete should be executed from the same process, unexpected RemoteException.", $r6);
            }
        }
    }

    protected Bundle zzadn() throws  {
        if (!getContext().getPackageName().equals(this.Ep.zzawj())) {
            this.bgx.putString("com.google.android.gms.signin.internal.realClientPackageName", this.Ep.zzawj());
        }
        return this.bgx;
    }

    public boolean zzaec() throws  {
        return this.bgw;
    }

    protected /* synthetic */ IInterface zzbc(IBinder $r1) throws  {
        return zzqh($r1);
    }

    public void zzcmv() throws  {
        try {
            ((zze) zzavx()).zzaik(this.Jn.intValue());
        } catch (RemoteException e) {
            Log.w("SignInClientImpl", "Remote service probably died when clearAccountFromSessionStore is called");
        }
    }

    protected zze zzqh(IBinder $r1) throws  {
        return zza.zzqg($r1);
    }

    protected String zzrg() throws  {
        return "com.google.android.gms.signin.service.START";
    }

    protected String zzrh() throws  {
        return "com.google.android.gms.signin.internal.ISignInService";
    }
}
