package com.google.android.gms.security;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.internal.zzab;
import java.lang.reflect.Method;

/* compiled from: dalvik_source_com.waze.apk */
public class ProviderInstaller {
    public static final String PROVIDER_NAME = "GmsCore_OpenSSL";
    private static final GoogleApiAvailabilityLight bgh = GoogleApiAvailabilityLight.getInstance();
    private static Method bgi = null;
    private static final Object zzanj = new Object();

    /* compiled from: dalvik_source_com.waze.apk */
    class C10011 extends AsyncTask<Void, Void, Integer> {
        final /* synthetic */ ProviderInstallListener bgj;
        final /* synthetic */ Context zzals;

        C10011(Context $r1, ProviderInstallListener $r2) throws  {
            this.zzals = $r1;
            this.bgj = $r2;
        }

        protected /* synthetic */ Object doInBackground(Object[] $r2) throws  {
            return zzb((Void[]) $r2);
        }

        protected /* synthetic */ void onPostExecute(Object $r1) throws  {
            zzi((Integer) $r1);
        }

        protected Integer zzb(Void... voidArr) throws  {
            try {
                ProviderInstaller.installIfNeeded(this.zzals);
                return Integer.valueOf(0);
            } catch (GooglePlayServicesRepairableException $r4) {
                return Integer.valueOf($r4.getConnectionStatusCode());
            } catch (GooglePlayServicesNotAvailableException $r5) {
                return Integer.valueOf($r5.errorCode);
            }
        }

        protected void zzi(Integer $r1) throws  {
            if ($r1.intValue() == 0) {
                this.bgj.onProviderInstalled();
                return;
            }
            this.bgj.onProviderInstallFailed($r1.intValue(), ProviderInstaller.bgh.getErrorResolutionIntent(this.zzals, $r1.intValue(), "pi"));
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public interface ProviderInstallListener {
        void onProviderInstallFailed(int i, Intent intent) throws ;

        void onProviderInstalled() throws ;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void installIfNeeded(android.content.Context r14) throws com.google.android.gms.common.GooglePlayServicesRepairableException, com.google.android.gms.common.GooglePlayServicesNotAvailableException {
        /*
        r0 = "Context must not be null";
        com.google.android.gms.common.internal.zzab.zzb(r14, r0);
        r1 = bgh;
        r1.zzbm(r14);
        r14 = com.google.android.gms.common.GooglePlayServicesUtilLight.getRemoteContext(r14);
        if (r14 != 0) goto L_0x001f;
    L_0x0010:
        r0 = "ProviderInstaller";
        r2 = "Failed to get remote context";
        android.util.Log.e(r0, r2);
        r3 = new com.google.android.gms.common.GooglePlayServicesNotAvailableException;
        r4 = 8;
        r3.<init>(r4);
        throw r3;
    L_0x001f:
        r5 = zzanj;
        monitor-enter(r5);
        r6 = bgi;
        if (r6 != 0) goto L_0x0029;
    L_0x0026:
        zzdm(r14);	 Catch:{ Exception -> 0x0037 }
    L_0x0029:
        r6 = bgi;	 Catch:{ Exception -> 0x0037 }
        r4 = 1;
        r7 = new java.lang.Object[r4];	 Catch:{ Exception -> 0x0037 }
        r4 = 0;
        r7[r4] = r14;	 Catch:{ Exception -> 0x0037 }
        r8 = 0;
        r6.invoke(r8, r7);	 Catch:{ Exception -> 0x0037 }
        monitor-exit(r5);	 Catch:{ Throwable -> 0x0059 }
        return;
    L_0x0037:
        r9 = move-exception;
        r10 = "Failed to install provider: ";
        r11 = r9.getMessage();	 Catch:{ Throwable -> 0x0059 }
        r11 = java.lang.String.valueOf(r11);	 Catch:{ Throwable -> 0x0059 }
        r12 = r11.length();	 Catch:{ Throwable -> 0x0059 }
        if (r12 == 0) goto L_0x005c;
    L_0x0048:
        r10 = r10.concat(r11);	 Catch:{ Throwable -> 0x0059 }
    L_0x004c:
        r0 = "ProviderInstaller";
        android.util.Log.e(r0, r10);	 Catch:{ Throwable -> 0x0059 }
        r3 = new com.google.android.gms.common.GooglePlayServicesNotAvailableException;	 Catch:{ Throwable -> 0x0059 }
        r4 = 8;
        r3.<init>(r4);	 Catch:{ Throwable -> 0x0059 }
        throw r3;	 Catch:{ Throwable -> 0x0059 }
    L_0x0059:
        r13 = move-exception;
        monitor-exit(r5);	 Catch:{ Throwable -> 0x0059 }
        throw r13;
    L_0x005c:
        r10 = new java.lang.String;	 Catch:{ Throwable -> 0x0059 }
        r0 = "Failed to install provider: ";
        r10.<init>(r0);	 Catch:{ Throwable -> 0x0059 }
        goto L_0x004c;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.security.ProviderInstaller.installIfNeeded(android.content.Context):void");
    }

    public static void installIfNeededAsync(Context $r0, ProviderInstallListener $r1) throws  {
        zzab.zzb((Object) $r0, (Object) "Context must not be null");
        zzab.zzb((Object) $r1, (Object) "Listener must not be null");
        zzab.zzgp("Must be called on the UI thread");
        new C10011($r0, $r1).execute(new Void[0]);
    }

    private static void zzdm(Context $r0) throws ClassNotFoundException, NoSuchMethodException {
        bgi = $r0.getClassLoader().loadClass("com.google.android.gms.common.security.ProviderInstallerImpl").getMethod("insertProvider", new Class[]{Context.class});
    }
}
