package com.google.android.gms.auth.firstparty.shared;

import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.GoogleSignatureVerifier;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.common.stats.zzb;
import com.google.android.gms.common.zza;
import dalvik.annotation.Signature;

/* compiled from: dalvik_source_com.waze.apk */
public abstract class BlockingServiceClient {
    private final String TAG = "BlockingServiceClient";
    private final Context mContext;

    /* compiled from: dalvik_source_com.waze.apk */
    protected interface Call<R> {
        R exec(@Signature({"(", "Landroid/os/IBinder;", ")TR;"}) IBinder iBinder) throws RemoteException;
    }

    protected BlockingServiceClient(Context $r1) throws  {
        this.mContext = $r1.getApplicationContext();
    }

    protected <R> R exec(@Signature({"<R:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/auth/firstparty/shared/BlockingServiceClient$Call", "<TR;>;)TR;"}) Call<R> $r1) throws  {
        zzab.zzgq("Calling this from your main thread can lead to deadlock");
        Intent $r2 = getServiceIntent();
        PackageManager $r4 = this.mContext.getPackageManager();
        ResolveInfo $r5 = $r4.resolveService($r2, 0);
        if ($r5 == null || $r5.serviceInfo == null) {
            String $r8 = "Can't resolve a service for intent: ";
            String $r9 = String.valueOf($r2.toString());
            throw new IllegalStateException($r9.length() != 0 ? $r8.concat($r9) : new String("Can't resolve a service for intent: "));
        }
        ServiceInfo $r6 = $r5.serviceInfo;
        if (GoogleSignatureVerifier.getInstance(this.mContext).isPackageGoogleSigned($r4, $r6.packageName)) {
            $r2.setPackage($r6.packageName);
            zza com_google_android_gms_common_zza = new zza();
            if (!zzb.zzayj().zza(this.mContext, $r2, (ServiceConnection) com_google_android_gms_common_zza, 1)) {
                return null;
            }
            try {
                Object $r15 = $r1.exec(com_google_android_gms_common_zza.zzara());
                zzb.zzayj().zza(this.mContext, (ServiceConnection) com_google_android_gms_common_zza);
                return $r15;
            } catch (Throwable $r16) {
                Log.w("BlockingServiceClient", $r16);
                throw new RuntimeException($r16);
            } catch (Throwable $r19) {
                Log.w("BlockingServiceClient", $r19);
                throw new RuntimeException($r19);
            } catch (Throwable th) {
                zzb.zzayj().zza(this.mContext, (ServiceConnection) com_google_android_gms_common_zza);
            }
        } else {
            SecurityException $r11 = new SecurityException("Resolving service is not provided by Google!");
            Log.w("BlockingServiceClient", $r11);
            throw $r11;
        }
    }

    protected abstract Intent getServiceIntent() throws ;
}
