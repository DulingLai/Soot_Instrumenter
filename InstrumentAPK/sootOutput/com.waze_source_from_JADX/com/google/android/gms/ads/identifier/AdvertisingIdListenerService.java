package com.google.android.gms.ads.identifier;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import com.google.android.gms.ads.identifier.AdvertisingIdClient.Info;
import com.google.android.gms.common.util.zzx;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* compiled from: dalvik_source_com.waze.apk */
public abstract class AdvertisingIdListenerService extends Service {
    private volatile int zzajz = -1;
    private ExecutorService zzaka;
    private IBinder zzakb;
    private final Object zzakc = new Object();
    private boolean zzakd;

    /* compiled from: dalvik_source_com.waze.apk */
    private class zza extends com.google.android.gms.internal.zzcd.zza {
        final /* synthetic */ AdvertisingIdListenerService zzake;

        private zza(AdvertisingIdListenerService $r1) throws  {
            this.zzake = $r1;
        }

        public void zzb(final Bundle $r1) throws  {
            synchronized (this.zzake.zzakc) {
                if (this.zzake.zzakd) {
                    return;
                }
                this.zzake.zzdv();
                this.zzake.zzaka.execute(new Runnable(this) {
                    final /* synthetic */ zza zzakg;

                    public void run() throws  {
                        this.zzakg.zzake.onAdvertisingIdInfoChanged(new Info($r1.getString("ad_id"), $r1.getBoolean("lat_enabled")));
                    }
                });
            }
        }
    }

    private void zzdv() throws SecurityException {
        int $i0 = Binder.getCallingUid();
        if ($i0 != this.zzajz) {
            if (zzx.zzd(this, $i0)) {
                this.zzajz = $i0;
                return;
            }
            throw new SecurityException("Caller is not GooglePlayServices.");
        }
    }

    public abstract void onAdvertisingIdInfoChanged(Info info) throws ;

    public final IBinder onBind(Intent $r1) throws  {
        return "com.google.android.gms.ads.identifier.BIND_LISTENER".equals($r1.getAction()) ? this.zzakb : null;
    }

    public void onCreate() throws  {
        super.onCreate();
        this.zzaka = Executors.newSingleThreadExecutor();
        this.zzakb = new zza();
    }

    public void onDestroy() throws  {
        synchronized (this.zzakc) {
            this.zzakd = true;
            this.zzaka.shutdown();
        }
        super.onDestroy();
    }
}
