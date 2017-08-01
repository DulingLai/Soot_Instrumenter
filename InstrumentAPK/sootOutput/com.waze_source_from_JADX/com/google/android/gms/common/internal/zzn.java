package com.google.android.gms.common.internal;

import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.IBinder;
import android.os.Message;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/* compiled from: dalvik_source_com.waze.apk */
final class zzn extends zzm implements Callback {
    private final HashMap<zza, zzb> JN = new HashMap();
    private final com.google.android.gms.common.stats.zzb JO;
    private final long JP;
    private final Handler mHandler;
    private final Context zzard;

    /* compiled from: dalvik_source_com.waze.apk */
    private static final class zza {
        private final String JQ;
        private final ComponentName kM;
        private final String mAction;

        public zza(ComponentName $r1) throws  {
            this.mAction = null;
            this.JQ = null;
            this.kM = (ComponentName) zzab.zzag($r1);
        }

        public zza(String $r1) throws  {
            this.mAction = zzab.zzgy($r1);
            this.JQ = "com.google.android.gms";
            this.kM = null;
        }

        public zza(String $r1, String $r2) throws  {
            this.mAction = zzab.zzgy($r1);
            this.JQ = zzab.zzgy($r2);
            this.kM = null;
        }

        public boolean equals(Object $r1) throws  {
            if (this == $r1) {
                return true;
            }
            if (!($r1 instanceof zza)) {
                return false;
            }
            zza $r2 = (zza) $r1;
            return zzaa.equal(this.mAction, $r2.mAction) && zzaa.equal(this.kM, $r2.kM);
        }

        public int hashCode() throws  {
            return zzaa.hashCode(this.mAction, this.kM);
        }

        public String toString() throws  {
            return this.mAction == null ? this.kM.flattenToString() : this.mAction;
        }

        public Intent zzawv() throws  {
            return this.mAction != null ? new Intent(this.mAction).setPackage(this.JQ) : new Intent().setComponent(this.kM);
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    private final class zzb {
        private final zza JR = new zza(this);
        private final Set<ServiceConnection> JS = new HashSet();
        private boolean JT;
        private final zza JU;
        final /* synthetic */ zzn JV;
        private ComponentName kM;
        private int mState = 2;
        private IBinder zzakb;

        /* compiled from: dalvik_source_com.waze.apk */
        public class zza implements ServiceConnection {
            final /* synthetic */ zzb JW;

            public zza(zzb $r1) throws  {
                this.JW = $r1;
            }

            public void onServiceConnected(ComponentName $r1, IBinder $r2) throws  {
                synchronized (this.JW.JV.JN) {
                    this.JW.zzakb = $r2;
                    this.JW.kM = $r1;
                    for (ServiceConnection onServiceConnected : this.JW.JS) {
                        onServiceConnected.onServiceConnected($r1, $r2);
                    }
                    this.JW.mState = 1;
                }
            }

            public void onServiceDisconnected(ComponentName $r1) throws  {
                synchronized (this.JW.JV.JN) {
                    this.JW.zzakb = null;
                    this.JW.kM = $r1;
                    for (ServiceConnection onServiceDisconnected : this.JW.JS) {
                        onServiceDisconnected.onServiceDisconnected($r1);
                    }
                    this.JW.mState = 2;
                }
            }
        }

        public zzb(zzn $r1, zza $r2) throws  {
            this.JV = $r1;
            this.JU = $r2;
        }

        public IBinder getBinder() throws  {
            return this.zzakb;
        }

        public ComponentName getComponentName() throws  {
            return this.kM;
        }

        public int getState() throws  {
            return this.mState;
        }

        public boolean isBound() throws  {
            return this.JT;
        }

        public void zza(ServiceConnection $r1, String $r2) throws  {
            this.JV.JO.zza(this.JV.zzard, $r1, $r2, this.JU.zzawv());
            this.JS.add($r1);
        }

        public boolean zza(ServiceConnection $r1) throws  {
            return this.JS.contains($r1);
        }

        public boolean zzaww() throws  {
            return this.JS.isEmpty();
        }

        public void zzb(ServiceConnection $r1, String str) throws  {
            this.JV.JO.zzb(this.JV.zzard, $r1);
            this.JS.remove($r1);
        }

        @TargetApi(14)
        public void zzgt(String $r1) throws  {
            this.mState = 3;
            this.JT = this.JV.JO.zza(this.JV.zzard, $r1, this.JU.zzawv(), (ServiceConnection) this.JR, 129);
            if (!this.JT) {
                this.mState = 2;
                try {
                    this.JV.JO.zza(this.JV.zzard, this.JR);
                } catch (IllegalArgumentException e) {
                }
            }
        }

        public void zzgu(String str) throws  {
            this.JV.JO.zza(this.JV.zzard, this.JR);
            this.JT = false;
            this.mState = 2;
        }
    }

    zzn(Context $r1) throws  {
        this.zzard = $r1.getApplicationContext();
        this.mHandler = new Handler($r1.getMainLooper(), this);
        this.JO = com.google.android.gms.common.stats.zzb.zzayj();
        this.JP = 5000;
    }

    private boolean zza(zza $r1, ServiceConnection $r2, String $r3) throws  {
        boolean $z0;
        zzab.zzb((Object) $r2, (Object) "ServiceConnection must not be null");
        synchronized (this.JN) {
            zzb $r7 = (zzb) this.JN.get($r1);
            if ($r7 != null) {
                this.mHandler.removeMessages(0, $r7);
                if (!$r7.zza($r2)) {
                    $r7.zza($r2, $r3);
                    switch ($r7.getState()) {
                        case 1:
                            $r2.onServiceConnected($r7.getComponentName(), $r7.getBinder());
                            break;
                        case 2:
                            $r7.zzgt($r3);
                            break;
                        default:
                            break;
                    }
                }
                $r3 = String.valueOf($r1);
                throw new IllegalStateException(new StringBuilder(String.valueOf($r3).length() + 81).append("Trying to bind a GmsServiceConnection that was already connected before.  config=").append($r3).toString());
            }
            $r7 = new zzb(this, $r1);
            $r7.zza($r2, $r3);
            $r7.zzgt($r3);
            this.JN.put($r1, $r7);
            $z0 = $r7.isBound();
        }
        return $z0;
    }

    private void zzb(zza $r1, ServiceConnection $r2, String $r3) throws  {
        zzab.zzb((Object) $r2, (Object) "ServiceConnection must not be null");
        synchronized (this.JN) {
            zzb $r7 = (zzb) this.JN.get($r1);
            if ($r7 == null) {
                $r3 = String.valueOf($r1);
                throw new IllegalStateException(new StringBuilder(String.valueOf($r3).length() + 50).append("Nonexistent connection status for service config: ").append($r3).toString());
            } else if ($r7.zza($r2)) {
                $r7.zzb($r2, $r3);
                if ($r7.zzaww()) {
                    Message $r13 = this.mHandler.obtainMessage(0, $r7);
                    this.mHandler.sendMessageDelayed($r13, this.JP);
                }
            } else {
                $r3 = String.valueOf($r1);
                throw new IllegalStateException(new StringBuilder(String.valueOf($r3).length() + 76).append("Trying to unbind a GmsServiceConnection  that was not bound before.  config=").append($r3).toString());
            }
        }
    }

    public boolean handleMessage(Message $r1) throws  {
        switch ($r1.what) {
            case 0:
                zzb $r4 = (zzb) $r1.obj;
                synchronized (this.JN) {
                    if ($r4.zzaww()) {
                        if ($r4.isBound()) {
                            $r4.zzgu("GmsClientSupervisor");
                        }
                        this.JN.remove($r4.JU);
                    }
                    break;
                }
                return true;
            default:
                return false;
        }
    }

    public boolean zza(ComponentName $r1, ServiceConnection $r2, String $r3) throws  {
        return zza(new zza($r1), $r2, $r3);
    }

    public boolean zza(String $r1, ServiceConnection $r2, String $r3) throws  {
        return zza(new zza($r1), $r2, $r3);
    }

    public boolean zza(String $r1, String $r2, ServiceConnection $r3, String $r4) throws  {
        return zza(new zza($r1, $r2), $r3, $r4);
    }

    public void zzb(ComponentName $r1, ServiceConnection $r2, String $r3) throws  {
        zzb(new zza($r1), $r2, $r3);
    }

    public void zzb(String $r1, ServiceConnection $r2, String $r3) throws  {
        zzb(new zza($r1), $r2, $r3);
    }

    public void zzb(String $r1, String $r2, ServiceConnection $r3, String $r4) throws  {
        zzb(new zza($r1, $r2), $r3, $r4);
    }
}
