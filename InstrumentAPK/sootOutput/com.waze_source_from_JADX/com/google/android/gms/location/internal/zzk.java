package com.google.android.gms.location.internal;

import android.app.PendingIntent;
import android.content.ContentProviderClient;
import android.content.Context;
import android.location.Location;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.zze;
import com.google.android.gms.location.zzf;
import dalvik.annotation.Signature;
import java.util.HashMap;
import java.util.Map;

/* compiled from: dalvik_source_com.waze.apk */
public class zzk {
    private Map<LocationListener, zzc> afc = new HashMap();
    private final zzp<zzi> avG;
    private ContentProviderClient avV = null;
    private boolean avW = false;
    private Map<LocationCallback, zza> avX = new HashMap();
    private final Context mContext;

    /* compiled from: dalvik_source_com.waze.apk */
    private static class zza extends com.google.android.gms.location.zze.zza {
        private Handler avY;

        zza(final LocationCallback $r1, Looper $r3) throws  {
            if ($r3 == null) {
                Looper $r2 = Looper.myLooper();
                $r3 = $r2;
                zzab.zza($r2 != null, (Object) "Can't create handler inside thread that has not called Looper.prepare()");
            }
            this.avY = new Handler(this, $r3) {
                final /* synthetic */ zza avZ;

                public void handleMessage(Message $r1) throws  {
                    switch ($r1.what) {
                        case 0:
                            $r1.onLocationResult((LocationResult) $r1.obj);
                            return;
                        case 1:
                            $r1.onLocationAvailability((LocationAvailability) $r1.obj);
                            return;
                        default:
                            return;
                    }
                }
            };
        }

        private void zzb(int $i0, Object $r1) throws  {
            if (this.avY == null) {
                Log.e("LocationClientHelper", "Received a data in client after calling removeLocationUpdates.");
                return;
            }
            Message $r2 = Message.obtain();
            $r2.what = $i0;
            $r2.obj = $r1;
            this.avY.sendMessage($r2);
        }

        public void onLocationAvailability(LocationAvailability $r1) throws  {
            zzb(1, $r1);
        }

        public void onLocationResult(LocationResult $r1) throws  {
            zzb(0, $r1);
        }

        public void release() throws  {
            this.avY = null;
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    private static class zzb extends Handler {
        private final LocationListener awa;

        public zzb(LocationListener $r1) throws  {
            this.awa = $r1;
        }

        public zzb(LocationListener $r1, Looper $r2) throws  {
            super($r2);
            this.awa = $r1;
        }

        public void handleMessage(Message $r1) throws  {
            switch ($r1.what) {
                case 1:
                    this.awa.onLocationChanged(new Location((Location) $r1.obj));
                    return;
                default:
                    Log.e("LocationClientHelper", "unknown message in LocationHandler.handleMessage");
                    return;
            }
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    private static class zzc extends com.google.android.gms.location.zzf.zza {
        private Handler avY;

        zzc(LocationListener $r1, Looper $r2) throws  {
            if ($r2 == null) {
                zzab.zza(Looper.myLooper() != null, (Object) "Can't create handler inside thread that has not called Looper.prepare()");
            }
            this.avY = $r2 == null ? new zzb($r1) : new zzb($r1, $r2);
        }

        public void onLocationChanged(Location $r1) throws  {
            if (this.avY == null) {
                Log.e("LocationClientHelper", "Received a location in client after calling removeLocationUpdates.");
                return;
            }
            Message $r2 = Message.obtain();
            $r2.what = 1;
            $r2.obj = $r1;
            this.avY.sendMessage($r2);
        }

        public void release() throws  {
            this.avY = null;
        }
    }

    public zzk(@Signature({"(", "Landroid/content/Context;", "Lcom/google/android/gms/location/internal/zzp", "<", "Lcom/google/android/gms/location/internal/zzi;", ">;)V"}) Context $r1, @Signature({"(", "Landroid/content/Context;", "Lcom/google/android/gms/location/internal/zzp", "<", "Lcom/google/android/gms/location/internal/zzi;", ">;)V"}) zzp<zzi> $r2) throws  {
        this.mContext = $r1;
        this.avG = $r2;
    }

    private zza zza(LocationCallback $r1, Looper $r2) throws  {
        zza $r6;
        synchronized (this.avX) {
            $r6 = (zza) this.avX.get($r1);
            if ($r6 == null) {
                $r6 = new zza($r1, $r2);
            }
            this.avX.put($r1, $r6);
        }
        return $r6;
    }

    private zzc zza(LocationListener $r1, Looper $r2) throws  {
        zzc $r6;
        synchronized (this.afc) {
            $r6 = (zzc) this.afc.get($r1);
            if ($r6 == null) {
                $r6 = new zzc($r1, $r2);
            }
            this.afc.put($r1, $r6);
        }
        return $r6;
    }

    public Location getLastLocation() throws  {
        this.avG.zzavw();
        try {
            return ((zzi) this.avG.zzavx()).zzkh(this.mContext.getPackageName());
        } catch (RemoteException $r7) {
            throw new IllegalStateException($r7);
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void removeAllListeners() throws  {
        /*
        r23 = this;
        r0 = r23;
        r2 = r0.afc;
        monitor-enter(r2);
        r0 = r23;
        r3 = r0.afc;	 Catch:{ Throwable -> 0x0036 }
        r4 = r3.values();	 Catch:{ Throwable -> 0x0036 }
        r5 = r4.iterator();	 Catch:{ Throwable -> 0x0036 }
    L_0x0011:
        r6 = r5.hasNext();	 Catch:{ Throwable -> 0x0036 }
        if (r6 == 0) goto L_0x0044;
    L_0x0017:
        r7 = r5.next();	 Catch:{ Throwable -> 0x0036 }
        r9 = r7;
        r9 = (com.google.android.gms.location.internal.zzk.zzc) r9;	 Catch:{ Throwable -> 0x0036 }
        r8 = r9;
        if (r8 == 0) goto L_0x0011;
    L_0x0021:
        r0 = r23;
        r10 = r0.avG;	 Catch:{ Throwable -> 0x0036 }
        r11 = r10.zzavx();	 Catch:{ Throwable -> 0x0036 }
        r13 = r11;
        r13 = (com.google.android.gms.location.internal.zzi) r13;	 Catch:{ Throwable -> 0x0036 }
        r12 = r13;
        r15 = 0;
        r14 = com.google.android.gms.location.internal.LocationRequestUpdateData.zza(r8, r15);	 Catch:{ Throwable -> 0x0036 }
        r12.zza(r14);	 Catch:{ Throwable -> 0x0036 }
        goto L_0x0011;
    L_0x0036:
        r16 = move-exception;
        monitor-exit(r2);	 Catch:{ Throwable -> 0x0036 }
        throw r16;	 Catch:{ RemoteException -> 0x0039 }
    L_0x0039:
        r17 = move-exception;
        r18 = new java.lang.IllegalStateException;
        r0 = r18;
        r1 = r17;
        r0.<init>(r1);
        throw r18;
    L_0x0044:
        r0 = r23;
        r3 = r0.afc;	 Catch:{ Throwable -> 0x0036 }
        r3.clear();	 Catch:{ Throwable -> 0x0036 }
        monitor-exit(r2);	 Catch:{ Throwable -> 0x0036 }
        r0 = r23;
        r2 = r0.avX;
        monitor-enter(r2);
        r0 = r23;
        r3 = r0.avX;	 Catch:{ Throwable -> 0x0088 }
        r4 = r3.values();	 Catch:{ Throwable -> 0x0088 }
        r5 = r4.iterator();	 Catch:{ Throwable -> 0x0088 }
    L_0x005d:
        r6 = r5.hasNext();	 Catch:{ Throwable -> 0x0088 }
        if (r6 == 0) goto L_0x008b;
    L_0x0063:
        r7 = r5.next();	 Catch:{ Throwable -> 0x0088 }
        r20 = r7;
        r20 = (com.google.android.gms.location.internal.zzk.zza) r20;	 Catch:{ Throwable -> 0x0088 }
        r19 = r20;
        if (r19 == 0) goto L_0x005d;
    L_0x006f:
        r0 = r23;
        r10 = r0.avG;	 Catch:{ Throwable -> 0x0088 }
        r11 = r10.zzavx();	 Catch:{ Throwable -> 0x0088 }
        r21 = r11;
        r21 = (com.google.android.gms.location.internal.zzi) r21;	 Catch:{ Throwable -> 0x0088 }
        r12 = r21;
        r15 = 0;
        r0 = r19;
        r14 = com.google.android.gms.location.internal.LocationRequestUpdateData.zza(r0, r15);	 Catch:{ Throwable -> 0x0088 }
        r12.zza(r14);	 Catch:{ Throwable -> 0x0088 }
        goto L_0x005d;
    L_0x0088:
        r22 = move-exception;
        monitor-exit(r2);	 Catch:{ Throwable -> 0x0088 }
        throw r22;	 Catch:{ RemoteException -> 0x0039 }
    L_0x008b:
        r0 = r23;
        r3 = r0.avX;	 Catch:{ Throwable -> 0x0088 }
        r3.clear();	 Catch:{ Throwable -> 0x0088 }
        monitor-exit(r2);	 Catch:{ Throwable -> 0x0088 }
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.location.internal.zzk.removeAllListeners():void");
    }

    public void zza(PendingIntent $r1, zzg $r2) throws RemoteException {
        this.avG.zzavw();
        ((zzi) this.avG.zzavx()).zza(LocationRequestUpdateData.zzb($r1, $r2));
    }

    public void zza(Location $r1, int $i0) throws RemoteException {
        this.avG.zzavw();
        ((zzi) this.avG.zzavx()).zza($r1, $i0);
    }

    public void zza(LocationCallback $r1, zzg $r2) throws RemoteException {
        this.avG.zzavw();
        zzab.zzb((Object) $r1, (Object) "Invalid null callback");
        synchronized (this.avX) {
            zze $r7 = (zza) this.avX.remove($r1);
            if ($r7 != null) {
                $r7.release();
                ((zzi) this.avG.zzavx()).zza(LocationRequestUpdateData.zza($r7, $r2));
            }
        }
    }

    public void zza(LocationListener $r1, zzg $r2) throws RemoteException {
        this.avG.zzavw();
        zzab.zzb((Object) $r1, (Object) "Invalid null listener");
        synchronized (this.afc) {
            zzf $r7 = (zzc) this.afc.remove($r1);
            if ($r7 != null) {
                $r7.release();
                ((zzi) this.avG.zzavx()).zza(LocationRequestUpdateData.zza($r7, $r2));
            }
        }
    }

    public void zza(LocationRequest $r1, PendingIntent $r2, zzg $r3) throws RemoteException {
        this.avG.zzavw();
        ((zzi) this.avG.zzavx()).zza(LocationRequestUpdateData.zza(LocationRequestInternal.zzb($r1), $r2, $r3));
    }

    public void zza(LocationRequest $r1, LocationListener $r2, Looper $r3, zzg $r4) throws RemoteException {
        this.avG.zzavw();
        ((zzi) this.avG.zzavx()).zza(LocationRequestUpdateData.zza(LocationRequestInternal.zzb($r1), zza($r2, $r3), $r4));
    }

    public void zza(LocationRequestInternal $r1, LocationCallback $r2, Looper $r3, zzg $r4) throws RemoteException {
        this.avG.zzavw();
        ((zzi) this.avG.zzavx()).zza(LocationRequestUpdateData.zza($r1, zza($r2, $r3), $r4));
    }

    public void zza(zzg $r1) throws RemoteException {
        this.avG.zzavw();
        ((zzi) this.avG.zzavx()).zza($r1);
    }

    public LocationAvailability zzbsi() throws  {
        this.avG.zzavw();
        try {
            return ((zzi) this.avG.zzavx()).zzki(this.mContext.getPackageName());
        } catch (RemoteException $r7) {
            throw new IllegalStateException($r7);
        }
    }

    public void zzbsj() throws  {
        if (this.avW) {
            try {
                zzce(false);
            } catch (RemoteException $r2) {
                throw new IllegalStateException($r2);
            }
        }
    }

    public void zzc(Location $r1) throws RemoteException {
        this.avG.zzavw();
        ((zzi) this.avG.zzavx()).zzc($r1);
    }

    public void zzce(boolean $z0) throws RemoteException {
        this.avG.zzavw();
        ((zzi) this.avG.zzavx()).zzce($z0);
        this.avW = $z0;
    }
}
