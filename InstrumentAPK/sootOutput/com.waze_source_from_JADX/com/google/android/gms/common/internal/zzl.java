package com.google.android.gms.common.internal;

import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: dalvik_source_com.waze.apk */
public final class zzl implements Callback {
    private final zza JE;
    private final ArrayList<ConnectionCallbacks> JF = new ArrayList();
    final ArrayList<ConnectionCallbacks> JG = new ArrayList();
    private final ArrayList<OnConnectionFailedListener> JH = new ArrayList();
    private volatile boolean JI = false;
    private final AtomicInteger JJ = new AtomicInteger(0);
    private boolean JK = false;
    private final Handler mHandler;
    private final Object zzaix = new Object();

    /* compiled from: dalvik_source_com.waze.apk */
    public interface zza {
        boolean isConnected() throws ;

        Bundle zzapt() throws ;
    }

    public zzl(Looper $r1, zza $r2) throws  {
        this.JE = $r2;
        this.mHandler = new Handler($r1, this);
    }

    public boolean handleMessage(Message $r1) throws  {
        if ($r1.what == 1) {
            ConnectionCallbacks $r3 = (ConnectionCallbacks) $r1.obj;
            synchronized (this.zzaix) {
                if (this.JI && this.JE.isConnected() && this.JF.contains($r3)) {
                    $r3.onConnected(this.JE.zzapt());
                }
            }
            return true;
        }
        String str = "GmsClientEvents";
        Log.wtf(str, "Don't know how to handle message: " + $r1.what, new Exception());
        return false;
    }

    public boolean isConnectionCallbacksRegistered(ConnectionCallbacks $r1) throws  {
        boolean $z0;
        zzab.zzag($r1);
        synchronized (this.zzaix) {
            $z0 = this.JF.contains($r1);
        }
        return $z0;
    }

    public boolean isConnectionFailedListenerRegistered(OnConnectionFailedListener $r1) throws  {
        boolean $z0;
        zzab.zzag($r1);
        synchronized (this.zzaix) {
            $z0 = this.JH.contains($r1);
        }
        return $z0;
    }

    public void registerConnectionCallbacks(ConnectionCallbacks $r1) throws  {
        zzab.zzag($r1);
        synchronized (this.zzaix) {
            if (this.JF.contains($r1)) {
                String $r4 = String.valueOf($r1);
                Log.w("GmsClientEvents", new StringBuilder(String.valueOf($r4).length() + 62).append("registerConnectionCallbacks(): listener ").append($r4).append(" is already registered").toString());
            } else {
                this.JF.add($r1);
            }
        }
        if (this.JE.isConnected()) {
            this.mHandler.sendMessage(this.mHandler.obtainMessage(1, $r1));
        }
    }

    public void registerConnectionFailedListener(OnConnectionFailedListener $r1) throws  {
        zzab.zzag($r1);
        synchronized (this.zzaix) {
            if (this.JH.contains($r1)) {
                String $r4 = String.valueOf($r1);
                Log.w("GmsClientEvents", new StringBuilder(String.valueOf($r4).length() + 67).append("registerConnectionFailedListener(): listener ").append($r4).append(" is already registered").toString());
            } else {
                this.JH.add($r1);
            }
        }
    }

    public void unregisterConnectionCallbacks(ConnectionCallbacks $r1) throws  {
        zzab.zzag($r1);
        synchronized (this.zzaix) {
            if (!this.JF.remove($r1)) {
                String $r4 = String.valueOf($r1);
                Log.w("GmsClientEvents", new StringBuilder(String.valueOf($r4).length() + 52).append("unregisterConnectionCallbacks(): listener ").append($r4).append(" not found").toString());
            } else if (this.JK) {
                this.JG.add($r1);
            }
        }
    }

    public void unregisterConnectionFailedListener(OnConnectionFailedListener $r1) throws  {
        zzab.zzag($r1);
        synchronized (this.zzaix) {
            if (!this.JH.remove($r1)) {
                String $r4 = String.valueOf($r1);
                Log.w("GmsClientEvents", new StringBuilder(String.valueOf($r4).length() + 57).append("unregisterConnectionFailedListener(): listener ").append($r4).append(" not found").toString());
            }
        }
    }

    public void zzawt() throws  {
        this.JI = false;
        this.JJ.incrementAndGet();
    }

    public void zzawu() throws  {
        this.JI = true;
    }

    public void zzio(int $i0) throws  {
        boolean $z0 = false;
        if (Looper.myLooper() == $r0.mHandler.getLooper()) {
            $z0 = true;
        }
        zzab.zza($z0, (Object) "onUnintentionalDisconnection must only be called on the Handler thread");
        $r0.mHandler.removeMessages(1);
        synchronized ($r0.zzaix) {
            $r0.JK = true;
            ArrayList $r5 = new ArrayList($r0.JF);
            boolean $i1 = $r0.JJ.get();
            Iterator $r8 = $r5.iterator();
            while ($r8.hasNext()) {
                ConnectionCallbacks $r10 = (ConnectionCallbacks) $r8.next();
                boolean $z02 = $r0;
                $r0 = $z02;
                if (!$z02.JI || $r0.JJ.get() != $i1) {
                    break;
                } else if ($r0.JF.contains($r10)) {
                    $r10.onConnectionSuspended($i0);
                }
            }
            $r0.JG.clear();
            $r0.JK = false;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void zzm(com.google.android.gms.common.ConnectionResult r19) throws  {
        /*
        r18 = this;
        r1 = android.os.Looper.myLooper();
        r0 = r18;
        r2 = r0.mHandler;
        r3 = r2.getLooper();
        if (r1 != r3) goto L_0x005a;
    L_0x000e:
        r4 = 1;
    L_0x000f:
        r5 = "onConnectionFailure must only be called on the Handler thread";
        com.google.android.gms.common.internal.zzab.zza(r4, r5);
        r0 = r18;
        r2 = r0.mHandler;
        r6 = 1;
        r2.removeMessages(r6);
        r0 = r18;
        r7 = r0.zzaix;
        monitor-enter(r7);
        r8 = new java.util.ArrayList;	 Catch:{ Throwable -> 0x006c }
        r0 = r18;
        r9 = r0.JH;	 Catch:{ Throwable -> 0x006c }
        r8.<init>(r9);	 Catch:{ Throwable -> 0x006c }
        r0 = r18;
        r10 = r0.JJ;	 Catch:{ Throwable -> 0x006c }
        r11 = r10.get();	 Catch:{ Throwable -> 0x006c }
        r12 = r8.iterator();	 Catch:{ Throwable -> 0x006c }
    L_0x0036:
        r4 = r12.hasNext();	 Catch:{ Throwable -> 0x006c }
        if (r4 == 0) goto L_0x006f;
    L_0x003c:
        r13 = r12.next();	 Catch:{ Throwable -> 0x006c }
        r15 = r13;
        r15 = (com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener) r15;	 Catch:{ Throwable -> 0x006c }
        r14 = r15;
        r0 = r18;
        r4 = r0.JI;	 Catch:{ Throwable -> 0x006c }
        if (r4 == 0) goto L_0x0058;
    L_0x004c:
        r0 = r18;
        r10 = r0.JJ;	 Catch:{ Throwable -> 0x006c }
        r16 = r10.get();	 Catch:{ Throwable -> 0x006c }
        r0 = r16;
        if (r0 == r11) goto L_0x005c;
    L_0x0058:
        monitor-exit(r7);	 Catch:{ Throwable -> 0x006c }
        return;
    L_0x005a:
        r4 = 0;
        goto L_0x000f;
    L_0x005c:
        r0 = r18;
        r8 = r0.JH;	 Catch:{ Throwable -> 0x006c }
        r4 = r8.contains(r14);	 Catch:{ Throwable -> 0x006c }
        if (r4 == 0) goto L_0x0036;
    L_0x0066:
        r0 = r19;
        r14.onConnectionFailed(r0);	 Catch:{ Throwable -> 0x006c }
        goto L_0x0036;
    L_0x006c:
        r17 = move-exception;
        monitor-exit(r7);	 Catch:{ Throwable -> 0x006c }
        throw r17;
    L_0x006f:
        monitor-exit(r7);	 Catch:{ Throwable -> 0x006c }
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.internal.zzl.zzm(com.google.android.gms.common.ConnectionResult):void");
    }

    public void zzt(Bundle $r1) throws  {
        boolean $z0 = true;
        zzab.zza(Looper.myLooper() == $r0.mHandler.getLooper(), (Object) "onConnectionSuccess must only be called on the Handler thread");
        synchronized ($r0.zzaix) {
            zzab.zzbm(!$r0.JK);
            $r0.mHandler.removeMessages(1);
            $r0.JK = true;
            if ($r0.JG.size() != 0) {
                $z0 = false;
            }
            zzab.zzbm($z0);
            ArrayList $r6 = new ArrayList($r0.JF);
            int $i0 = $r0.JJ.get();
            Iterator $r9 = $r6.iterator();
            while ($r9.hasNext()) {
                ConnectionCallbacks $r11 = (ConnectionCallbacks) $r9.next();
                boolean $z02 = $r0;
                $r0 = $z02;
                if (!$z02.JI) {
                    break;
                }
                zza $r12 = $r0.JE;
                if (!$r12.isConnected() || $r0.JJ.get() != $i0) {
                    break;
                } else if (!$r0.JG.contains($r11)) {
                    $r11.onConnected($r1);
                }
            }
            $r0.JG.clear();
            $r0.JK = false;
        }
    }
}
