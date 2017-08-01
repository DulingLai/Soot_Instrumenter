package com.google.android.gms.auth.firstparty.proximity;

import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.RemoteException;
import com.google.android.gms.auth.firstparty.proximity.data.Authorization;
import com.google.android.gms.auth.firstparty.proximity.data.AuthorizationRequest;
import com.google.android.gms.common.GoogleSignatureVerifier;
import com.google.android.gms.common.stats.zzb;

/* compiled from: dalvik_source_com.waze.apk */
public class AuthorizationClient {
    public static final String BUNDLE_KEY_AUTHORIZATION = "authorization";
    public static final String BUNDLE_KEY_EXCEPTION = "exception";
    private final Context mContext;

    /* compiled from: dalvik_source_com.waze.apk */
    private interface zza {
        Bundle zza(zza com_google_android_gms_auth_firstparty_proximity_zza) throws RemoteException;
    }

    public AuthorizationClient(Context $r1) throws  {
        this.mContext = $r1;
    }

    private Bundle zza(zza $r1) throws ProximityAuthException {
        Intent $r2 = zzafe();
        zzk($r2);
        ServiceConnection $r3 = new com.google.android.gms.common.zza();
        if (zzb.zzayj().zza(this.mContext, $r2, $r3, 1)) {
            try {
                Bundle $r8 = $r1.zza(com.google.android.gms.auth.firstparty.proximity.zza.zza.zzdb($r3.zzara()));
                $r8.setClassLoader(getClass().getClassLoader());
                if ($r8.containsKey(BUNDLE_KEY_EXCEPTION)) {
                    throw ((ProximityAuthException) $r8.getSerializable(BUNDLE_KEY_EXCEPTION));
                }
                zzb.zzayj().zza(this.mContext, $r3);
                return $r8;
            } catch (InterruptedException $r13) {
                throw new ProximityAuthException($r13.getMessage());
            } catch (RemoteException $r16) {
                throw new ProximityAuthException($r16.getMessage());
            } catch (Throwable th) {
                zzb.zzayj().zza(this.mContext, $r3);
            }
        } else {
            String $r14 = String.valueOf(zza.class.getSimpleName());
            throw new ProximityAuthException(new StringBuilder(String.valueOf($r14).length() + 16).append("Cannot bind to ").append($r14).append("!").toString());
        }
    }

    private static Intent zzafe() throws  {
        return new Intent().setPackage("com.google.android.gms").setAction("com.google.android.gms.auth.proximity.AUTHORIZATION").addCategory("android.intent.category.DEFAULT");
    }

    private void zzk(Intent $r1) throws  {
        PackageManager $r3 = this.mContext.getPackageManager();
        this = this;
        for (ResolveInfo resolveInfo : this.mContext.getPackageManager().queryIntentServices($r1, 0)) {
            String $r10 = resolveInfo.serviceInfo.packageName;
            AuthorizationClient $r2 = this;
            this = $r2;
            if (!GoogleSignatureVerifier.getInstance($r2.mContext).isPackageGoogleSigned($r3, $r10)) {
                String $r13 = "AuthorizationClient appears to have been spoofed by: ";
                $r10 = String.valueOf($r10);
                throw new SecurityException($r10.length() != 0 ? $r13.concat($r10) : new String("AuthorizationClient appears to have been spoofed by: "));
            }
        }
    }

    public Authorization authorize(final AuthorizationRequest $r1) throws ProximityAuthException {
        return (Authorization) zza(new zza(this) {
            final /* synthetic */ AuthorizationClient hZ;

            public Bundle zza(zza $r1) throws RemoteException {
                return $r1.zza($r1);
            }
        }).getParcelable(BUNDLE_KEY_AUTHORIZATION);
    }
}
