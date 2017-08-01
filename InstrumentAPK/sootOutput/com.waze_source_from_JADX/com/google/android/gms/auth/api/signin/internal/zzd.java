package com.google.android.gms.auth.api.signin.internal;

import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions.Builder;
import com.google.android.gms.auth.api.signin.internal.zzh.zza;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.zzg;
import com.google.android.gms.common.internal.zzk;

/* compiled from: dalvik_source_com.waze.apk */
public class zzd extends zzk<zzh> {
    private final GoogleSignInOptions fY;

    public zzd(Context $r1, Looper $r2, zzg $r3, GoogleSignInOptions $r6, ConnectionCallbacks $r4, OnConnectionFailedListener $r5) throws  {
        super($r1, $r2, 91, $r3, $r4, $r5);
        if ($r6 == null) {
            $r6 = new Builder().build();
        }
        if (!$r3.zzawh().isEmpty()) {
            Builder $r8 = new Builder($r6);
            for (Scope requestScopes : $r3.zzawh()) {
                $r8.requestScopes(requestScopes, new Scope[0]);
            }
            $r6 = $r8.build();
        }
        this.fY = $r6;
    }

    public boolean zzaer() throws  {
        return true;
    }

    public Intent zzaes() throws  {
        SignInConfiguration $r1 = new SignInConfiguration(getContext().getPackageName(), this.fY);
        Intent $r5 = new Intent("com.google.android.gms.auth.GOOGLE_SIGN_IN");
        $r5.setClass(getContext(), SignInHubActivity.class);
        $r5.putExtra("config", $r1);
        return $r5;
    }

    public GoogleSignInOptions zzaet() throws  {
        return this.fY;
    }

    protected /* synthetic */ IInterface zzbc(IBinder $r1) throws  {
        return zzcv($r1);
    }

    protected zzh zzcv(IBinder $r1) throws  {
        return zza.zzcx($r1);
    }

    protected String zzrg() throws  {
        return "com.google.android.gms.auth.api.signin.service.START";
    }

    protected String zzrh() throws  {
        return "com.google.android.gms.auth.api.signin.internal.ISignInService";
    }
}
