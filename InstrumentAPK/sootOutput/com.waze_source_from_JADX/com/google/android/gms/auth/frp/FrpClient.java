package com.google.android.gms.auth.frp;

import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.stats.zzb;
import dalvik.annotation.Signature;

/* compiled from: dalvik_source_com.waze.apk */
public class FrpClient {
    private final Context mContext;

    /* compiled from: dalvik_source_com.waze.apk */
    private interface zza<R> {
        R zzb(@Signature({"(", "Lcom/google/android/gms/auth/frp/zza;", ")TR;"}) zza com_google_android_gms_auth_frp_zza) throws RemoteException;
    }

    /* compiled from: dalvik_source_com.waze.apk */
    class C06911 implements zza<Boolean> {
        final /* synthetic */ FrpClient iP;

        C06911(FrpClient $r1) throws  {
            this.iP = $r1;
        }

        public Boolean zza(zza $r1) throws RemoteException {
            return Boolean.valueOf($r1.isChallengeSupported());
        }

        public /* synthetic */ Object zzb(zza $r1) throws RemoteException {
            return zza($r1);
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    class C06922 implements zza<Boolean> {
        final /* synthetic */ FrpClient iP;

        C06922(FrpClient $r1) throws  {
            this.iP = $r1;
        }

        public Boolean zza(zza $r1) throws RemoteException {
            return Boolean.valueOf($r1.isChallengeRequired());
        }

        public /* synthetic */ Object zzb(zza $r1) throws RemoteException {
            return zza($r1);
        }
    }

    public FrpClient(Context $r1) throws  {
        this.mContext = $r1;
    }

    private <R> R zza(@Signature({"<R:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/auth/frp/FrpClient$zza", "<TR;>;)TR;"}) zza<R> $r1) throws RemoteException, InterruptedException {
        Intent $r3 = zzaff();
        long $l0 = Binder.clearCallingIdentity();
        ServiceConnection $r2;
        try {
            $r2 = new com.google.android.gms.common.zza();
            if (zzb.zzayj().zza(this.mContext, $r3, $r2, 1)) {
                Object $r8 = $r1.zzb(com.google.android.gms.auth.frp.zza.zza.zzdc($r2.zzara()));
                zzb.zzayj().zza(this.mContext, $r2);
                Binder.restoreCallingIdentity($l0);
                return $r8;
            }
            Binder.restoreCallingIdentity($l0);
            return null;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity($l0);
        }
    }

    private static Intent zzaff() throws  {
        return new Intent().setPackage("com.google.android.gms").setAction("com.google.android.gms.auth.frp.FRP_BIND").addCategory("android.intent.category.DEFAULT");
    }

    public boolean isChallengeRequired() throws  {
        Exception $r4;
        try {
            return ((Boolean) zza(new C06922(this))).booleanValue();
        } catch (RemoteException e) {
            $r4 = e;
            Log.w("FrpClient", $r4);
            return false;
        } catch (InterruptedException e2) {
            $r4 = e2;
            Log.w("FrpClient", $r4);
            return false;
        }
    }

    public boolean isChallengeSupported() throws  {
        Exception $r4;
        try {
            return ((Boolean) zza(new C06911(this))).booleanValue();
        } catch (RemoteException e) {
            $r4 = e;
            Log.w("FrpClient", $r4);
            return false;
        } catch (InterruptedException e2) {
            $r4 = e2;
            Log.w("FrpClient", $r4);
            return false;
        }
    }

    public UnlockFactoryResetProtectionResponse unlockFactoryResetProtection(final UnlockFactoryResetProtectionRequest $r1) throws  {
        Exception $r5;
        try {
            return (UnlockFactoryResetProtectionResponse) zza(new zza<UnlockFactoryResetProtectionResponse>(this) {
                final /* synthetic */ FrpClient iP;

                public /* synthetic */ Object zzb(zza $r1) throws RemoteException {
                    return zzc($r1);
                }

                public UnlockFactoryResetProtectionResponse zzc(zza $r1) throws RemoteException {
                    return $r1.unlockFactoryResetProtection($r1);
                }
            });
        } catch (RemoteException e) {
            $r5 = e;
            Log.w("FrpClient", $r5);
            return new UnlockFactoryResetProtectionResponse(1);
        } catch (InterruptedException e2) {
            $r5 = e2;
            Log.w("FrpClient", $r5);
            return new UnlockFactoryResetProtectionResponse(1);
        }
    }
}
