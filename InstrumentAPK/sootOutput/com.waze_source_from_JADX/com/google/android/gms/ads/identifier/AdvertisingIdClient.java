package com.google.android.gms.ads.identifier;

import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.common.stats.zzb;
import com.google.android.gms.internal.zzce;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/* compiled from: dalvik_source_com.waze.apk */
public class AdvertisingIdClient {
    private final Context mContext;
    com.google.android.gms.common.zza zzajn;
    zzce zzajo;
    boolean zzajp;
    Object zzajq;
    zza zzajr;
    final long zzajs;

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class Info {
        private final String zzajx;
        private final boolean zzajy;

        public Info(String $r1, boolean $z0) throws  {
            this.zzajx = $r1;
            this.zzajy = $z0;
        }

        public String getId() throws  {
            return this.zzajx;
        }

        public boolean isLimitAdTrackingEnabled() throws  {
            return this.zzajy;
        }

        public String toString() throws  {
            String $r1 = this.zzajx;
            return new StringBuilder(String.valueOf($r1).length() + 7).append("{").append($r1).append("}").append(this.zzajy).toString();
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    static class zza extends Thread {
        private WeakReference<AdvertisingIdClient> zzajt;
        private long zzaju;
        CountDownLatch zzajv = new CountDownLatch(1);
        boolean zzajw = false;

        public zza(AdvertisingIdClient $r1, long $l0) throws  {
            this.zzajt = new WeakReference($r1);
            this.zzaju = $l0;
            start();
        }

        private void disconnect() throws  {
            AdvertisingIdClient $r3 = (AdvertisingIdClient) this.zzajt.get();
            if ($r3 != null) {
                $r3.finish();
                this.zzajw = true;
            }
        }

        public void cancel() throws  {
            this.zzajv.countDown();
        }

        public void run() throws  {
            try {
                if (!this.zzajv.await(this.zzaju, TimeUnit.MILLISECONDS)) {
                    disconnect();
                }
            } catch (InterruptedException e) {
                disconnect();
            }
        }

        public boolean zzdu() throws  {
            return this.zzajw;
        }
    }

    public AdvertisingIdClient(Context $r1) throws  {
        this($r1, 30000);
    }

    public AdvertisingIdClient(Context $r1, long $l0) throws  {
        this.zzajq = new Object();
        zzab.zzag($r1);
        this.mContext = $r1;
        this.zzajp = false;
        this.zzajs = $l0;
    }

    public static Info getAdvertisingIdInfo(Context $r0) throws IOException, IllegalStateException, GooglePlayServicesNotAvailableException, GooglePlayServicesRepairableException {
        AdvertisingIdClient $r1 = new AdvertisingIdClient($r0, -1);
        try {
            $r1.zze(false);
            Info $r2 = $r1.getInfo();
            return $r2;
        } finally {
            $r1.finish();
        }
    }

    public static void setShouldSkipGmsCoreVersionCheck(boolean z) throws  {
    }

    static zzce zza(Context context, com.google.android.gms.common.zza $r1) throws IOException {
        try {
            return com.google.android.gms.internal.zzce.zza.zzg($r1.zzb(10000, TimeUnit.MILLISECONDS));
        } catch (InterruptedException e) {
            throw new IOException("Interrupted exception");
        } catch (Throwable $r7) {
            IOException $r6 = new IOException($r7);
        }
    }

    private void zzdt() throws  {
        synchronized (this.zzajq) {
            if (this.zzajr != null) {
                this.zzajr.cancel();
                try {
                    this.zzajr.join();
                } catch (InterruptedException e) {
                }
            }
            if (this.zzajs > 0) {
                this.zzajr = new zza(this, this.zzajs);
            }
        }
    }

    static com.google.android.gms.common.zza zzh(Context $r0) throws IOException, GooglePlayServicesNotAvailableException, GooglePlayServicesRepairableException {
        try {
            $r0.getPackageManager().getPackageInfo("com.android.vending", 0);
            switch (GoogleApiAvailabilityLight.getInstance().isGooglePlayServicesAvailable($r0)) {
                case 0:
                case 2:
                    ServiceConnection $r6 = new com.google.android.gms.common.zza();
                    Intent $r7 = new Intent("com.google.android.gms.ads.identifier.service.START");
                    $r7.setPackage("com.google.android.gms");
                    try {
                        if (zzb.zzayj().zza($r0, $r7, $r6, 1)) {
                            return $r6;
                        }
                        throw new IOException("Connection failure");
                    } catch (Throwable $r9) {
                        IOException $r3 = new IOException($r9);
                    }
                case 1:
                    break;
                default:
                    break;
            }
            throw new IOException("Google Play services not available");
        } catch (NameNotFoundException e) {
            throw new GooglePlayServicesNotAvailableException(9);
        }
    }

    protected void finalize() throws Throwable {
        finish();
        super.finalize();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void finish() throws  {
        /*
        r10 = this;
        r0 = "Calling this from your main thread can lead to deadlock";
        com.google.android.gms.common.internal.zzab.zzgq(r0);
        monitor-enter(r10);
        r1 = r10.mContext;	 Catch:{ Throwable -> 0x002a }
        if (r1 == 0) goto L_0x000e;
    L_0x000a:
        r2 = r10.zzajn;	 Catch:{ Throwable -> 0x002a }
        if (r2 != 0) goto L_0x0010;
    L_0x000e:
        monitor-exit(r10);	 Catch:{ Throwable -> 0x002a }
        return;
    L_0x0010:
        r3 = r10.zzajp;	 Catch:{ Throwable -> 0x002a }
        if (r3 == 0) goto L_0x001f;
    L_0x0014:
        r4 = com.google.android.gms.common.stats.zzb.zzayj();	 Catch:{ IllegalArgumentException -> 0x002d }
        r1 = r10.mContext;
        r2 = r10.zzajn;	 Catch:{ IllegalArgumentException -> 0x002d }
        r4.zza(r1, r2);	 Catch:{ IllegalArgumentException -> 0x002d }
    L_0x001f:
        r5 = 0;
        r10.zzajp = r5;	 Catch:{ Throwable -> 0x002a }
        r6 = 0;
        r10.zzajo = r6;	 Catch:{ Throwable -> 0x002a }
        r6 = 0;
        r10.zzajn = r6;	 Catch:{ Throwable -> 0x002a }
        monitor-exit(r10);	 Catch:{ Throwable -> 0x002a }
        return;
    L_0x002a:
        r7 = move-exception;
        monitor-exit(r10);	 Catch:{ Throwable -> 0x002a }
        throw r7;
    L_0x002d:
        r8 = move-exception;
        r0 = "AdvertisingIdClient";
        r9 = "AdvertisingIdClient unbindService failed.";
        android.util.Log.i(r0, r9, r8);	 Catch:{ Throwable -> 0x002a }
        goto L_0x001f;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.ads.identifier.AdvertisingIdClient.finish():void");
    }

    public Info getInfo() throws IOException {
        Info $r9;
        zzab.zzgq("Calling this from your main thread can lead to deadlock");
        synchronized (this) {
            if (!this.zzajp) {
                synchronized (this.zzajq) {
                    if (this.zzajr == null || !this.zzajr.zzdu()) {
                        throw new IOException("AdvertisingIdClient is not connected.");
                    }
                }
                try {
                    zze(false);
                    if (!this.zzajp) {
                        throw new IOException("AdvertisingIdClient cannot reconnect.");
                    }
                } catch (RemoteException $r11) {
                    Log.i("AdvertisingIdClient", "GMS remote exception ", $r11);
                    throw new IOException("Remote exception");
                } catch (Exception $r6) {
                    throw new IOException("AdvertisingIdClient cannot reconnect.", $r6);
                }
            }
            zzab.zzag(this.zzajn);
            zzab.zzag(this.zzajo);
            $r9 = new Info(this.zzajo.getId(), this.zzajo.zzf(true));
        }
        zzdt();
        return $r9;
    }

    public void start() throws IOException, IllegalStateException, GooglePlayServicesNotAvailableException, GooglePlayServicesRepairableException {
        zze(true);
    }

    protected void zze(boolean $z0) throws IOException, IllegalStateException, GooglePlayServicesNotAvailableException, GooglePlayServicesRepairableException {
        zzab.zzgq("Calling this from your main thread can lead to deadlock");
        synchronized (this) {
            if (this.zzajp) {
                finish();
            }
            this.zzajn = zzh(this.mContext);
            this.zzajo = zza(this.mContext, this.zzajn);
            this.zzajp = true;
            if ($z0) {
                zzdt();
            }
        }
    }
}
