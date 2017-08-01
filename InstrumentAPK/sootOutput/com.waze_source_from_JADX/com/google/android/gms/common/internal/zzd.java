package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.support.annotation.BinderThread;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.common.api.Scope;
import dalvik.annotation.Signature;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: dalvik_source_com.waze.apk */
public abstract class zzd<T extends IInterface> {
    public static final String[] GOOGLE_PLUS_REQUIRED_FEATURES = new String[]{"service_esmobile", "service_googleme"};
    private final GoogleApiAvailabilityLight Eb;
    private int IA;
    private final zzb IB;
    private final zzc IC;
    private final int IE;
    private final String IF;
    protected AtomicInteger IG;
    private int Io;
    private long Ip;
    private long Iq;
    private int Ir;
    private long Is;
    private final zzm It;
    private final Object Iu;
    private zzu Iv;
    private zzf Iw;
    private T Ix;
    private final ArrayList<zze<?>> Iy;
    private zzh Iz;
    private final Context mContext;
    final Handler mHandler;
    private final Looper zzaih;
    private final Object zzaix;

    /* compiled from: dalvik_source_com.waze.apk */
    protected abstract class zze<TListener> {
        final /* synthetic */ zzd II;
        private boolean IJ = false;
        private TListener mListener;

        public zze(@Signature({"(TT", "Listener;", ")V"}) zzd $r1, @Signature({"(TT", "Listener;", ")V"}) TListener $r2) throws  {
            this.II = $r1;
            this.mListener = $r2;
        }

        public void unregister() throws  {
            zzawd();
            synchronized (this.II.Iy) {
                this.II.Iy.remove(this);
            }
        }

        protected abstract void zzad(@Signature({"(TT", "Listener;", ")V"}) TListener tListener) throws ;

        protected abstract void zzawb() throws ;

        public void zzawc() throws  {
            synchronized (this) {
                Object $r1 = this.mListener;
                if (this.IJ) {
                    String $r2 = String.valueOf(this);
                    Log.w("GmsClient", new StringBuilder(String.valueOf($r2).length() + 47).append("Callback proxy ").append($r2).append(" being reused. This is not safe.").toString());
                }
            }
            if ($r1 != null) {
                try {
                    zzad($r1);
                } catch (RuntimeException $r6) {
                    zzawb();
                    throw $r6;
                }
            }
            zzawb();
            synchronized (this) {
                this.IJ = true;
            }
            unregister();
        }

        public void zzawd() throws  {
            synchronized (this) {
                this.mListener = null;
            }
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    private abstract class zza extends zze<Boolean> {
        public final Bundle IH;
        final /* synthetic */ zzd II;
        public final int statusCode;

        @BinderThread
        protected zza(zzd $r1, int $i0, Bundle $r2) throws  {
            this.II = $r1;
            super($r1, Boolean.valueOf(true));
            this.statusCode = $i0;
            this.IH = $r2;
        }

        protected /* synthetic */ void zzad(Object $r1) throws  {
            zzd((Boolean) $r1);
        }

        protected abstract boolean zzawa() throws ;

        protected void zzawb() throws  {
        }

        protected void zzd(Boolean $r1) throws  {
            PendingIntent $r2 = null;
            if ($r1 == null) {
                this.II.zzb(1, null);
                return;
            }
            switch (this.statusCode) {
                case 0:
                    if (!zzawa()) {
                        this.II.zzb(1, null);
                        zzl(new ConnectionResult(8, null));
                        return;
                    }
                    return;
                case 10:
                    this.II.zzb(1, null);
                    throw new IllegalStateException("A fatal developer error has occurred. Check the logs for further information.");
                default:
                    this.II.zzb(1, null);
                    if (this.IH != null) {
                        $r2 = (PendingIntent) this.IH.getParcelable("pendingIntent");
                    }
                    zzl(new ConnectionResult(this.statusCode, $r2));
                    return;
            }
        }

        protected abstract void zzl(ConnectionResult connectionResult) throws ;
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public interface zzb {
        void onConnected(@Nullable Bundle bundle) throws ;

        void onConnectionSuspended(int i) throws ;
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public interface zzc {
        void onConnectionFailed(@NonNull ConnectionResult connectionResult) throws ;
    }

    /* compiled from: dalvik_source_com.waze.apk */
    final class zzd extends Handler {
        final /* synthetic */ zzd II;

        public zzd(zzd $r1, Looper $r2) throws  {
            this.II = $r1;
            super($r2);
        }

        private void zza(Message $r1) throws  {
            zze $r3 = (zze) $r1.obj;
            $r3.zzawb();
            $r3.unregister();
        }

        private boolean zzb(Message $r1) throws  {
            return $r1.what != 2 ? $r1.what != 1 ? $r1.what == 5 : true : true;
        }

        public void handleMessage(Message $r1) throws  {
            PendingIntent $r2 = null;
            if (this.II.IG.get() != $r1.arg1) {
                if (zzb($r1)) {
                    zza($r1);
                }
            } else if (($r1.what == 1 || $r1.what == 5) && !this.II.isConnecting()) {
                zza($r1);
            } else if ($r1.what == 3) {
                if ($r1.obj instanceof PendingIntent) {
                    $r2 = (PendingIntent) $r1.obj;
                }
                ConnectionResult $r6 = new ConnectionResult($r1.arg2, $r2);
                this.II.Iw.zzh($r6);
                this.II.onConnectionFailed($r6);
            } else if ($r1.what == 4) {
                this.II.zzb(4, null);
                if (this.II.IB != null) {
                    this.II.IB.onConnectionSuspended($r1.arg2);
                }
                this.II.onConnectionSuspended($r1.arg2);
                this.II.zza(4, 1, null);
            } else if ($r1.what == 2 && !this.II.isConnected()) {
                zza($r1);
            } else if (zzb($r1)) {
                ((zze) $r1.obj).zzawc();
            } else {
                Log.wtf("GmsClient", "Don't know how to handle message: " + $r1.what, new Exception());
            }
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public interface zzf {
        void zzh(@NonNull ConnectionResult connectionResult) throws ;
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class zzg extends com.google.android.gms.common.internal.zzt.zza {
        private zzd IK;
        private final int IL;

        public zzg(@NonNull zzd $r1, int $i0) throws  {
            this.IK = $r1;
            this.IL = $i0;
        }

        private void zzawe() throws  {
            this.IK = null;
        }

        @BinderThread
        public void zza(int i, @Nullable Bundle bundle) throws  {
            Log.wtf("GmsClient", "received deprecated onAccountValidationComplete callback, ignoring", new Exception());
        }

        @BinderThread
        public void zza(int $i0, @NonNull IBinder $r1, @Nullable Bundle $r2) throws  {
            zzab.zzb(this.IK, (Object) "onPostInitComplete can be called only once per call to getRemoteService");
            this.IK.zza($i0, $r1, $r2, this.IL);
            zzawe();
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public final class zzh implements ServiceConnection {
        final /* synthetic */ zzd II;
        private final int IL;

        public zzh(zzd $r1, int $i0) throws  {
            this.II = $r1;
            this.IL = $i0;
        }

        public void onServiceConnected(ComponentName componentName, IBinder $r2) throws  {
            zzab.zzb((Object) $r2, (Object) "Expecting a valid IBinder");
            synchronized (this.II.Iu) {
                this.II.Iv = com.google.android.gms.common.internal.zzu.zza.zzgv($r2);
            }
            this.II.zza(0, null, this.IL);
        }

        public void onServiceDisconnected(ComponentName componentName) throws  {
            synchronized (this.II.Iu) {
                this.II.Iv = null;
            }
            this.II.mHandler.sendMessage(this.II.mHandler.obtainMessage(4, this.IL, 1));
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    protected class zzi implements zzf {
        final /* synthetic */ zzd II;

        public zzi(zzd $r1) throws  {
            this.II = $r1;
        }

        public void zzh(@NonNull ConnectionResult $r1) throws  {
            if ($r1.isSuccess()) {
                this.II.zza(null, this.II.zzavz());
            } else if (this.II.IC != null) {
                this.II.IC.onConnectionFailed($r1);
            }
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    protected final class zzj extends zza {
        final /* synthetic */ zzd II;
        public final IBinder IM;

        @BinderThread
        public zzj(zzd $r1, int $i0, IBinder $r2, Bundle $r3) throws  {
            this.II = $r1;
            super($r1, $i0, $r3);
            this.IM = $r2;
        }

        protected boolean zzawa() throws  {
            try {
                String $r2 = this.IM.getInterfaceDescriptor();
                if (this.II.zzrh().equals($r2)) {
                    IInterface $r8 = this.II.zzbc(this.IM);
                    if ($r8 == null) {
                        return false;
                    }
                    if (!this.II.zza(2, 3, $r8)) {
                        return false;
                    }
                    Bundle $r9 = this.II.zzapt();
                    if (this.II.IB != null) {
                        this.II.IB.onConnected($r9);
                    }
                    return true;
                }
                String $r4 = String.valueOf(this.II.zzrh());
                Log.e("GmsClient", new StringBuilder((String.valueOf($r4).length() + 34) + String.valueOf($r2).length()).append("service descriptor mismatch: ").append($r4).append(" vs. ").append($r2).toString());
                return false;
            } catch (RemoteException e) {
                Log.w("GmsClient", "service probably died");
                return false;
            }
        }

        protected void zzl(ConnectionResult $r1) throws  {
            if (this.II.IC != null) {
                this.II.IC.onConnectionFailed($r1);
            }
            this.II.onConnectionFailed($r1);
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    protected final class zzk extends zza {
        final /* synthetic */ zzd II;

        @BinderThread
        public zzk(zzd $r1, int $i0, @Nullable Bundle $r2) throws  {
            this.II = $r1;
            super($r1, $i0, $r2);
        }

        protected boolean zzawa() throws  {
            this.II.Iw.zzh(ConnectionResult.BB);
            return true;
        }

        protected void zzl(ConnectionResult $r1) throws  {
            this.II.Iw.zzh($r1);
            this.II.onConnectionFailed($r1);
        }
    }

    protected zzd(Context $r1, Looper $r2, int $i0, zzb $r3, zzc $r4, String $r5) throws  {
        this($r1, $r2, zzm.zzbz($r1), GoogleApiAvailabilityLight.getInstance(), $i0, (zzb) zzab.zzag($r3), (zzc) zzab.zzag($r4), $r5);
    }

    protected zzd(Context $r1, Looper $r2, zzm $r3, GoogleApiAvailabilityLight $r4, int $i0, zzb $r5, zzc $r6, String $r7) throws  {
        this.zzaix = new Object();
        this.Iu = new Object();
        this.Iy = new ArrayList();
        this.IA = 1;
        this.IG = new AtomicInteger(0);
        this.mContext = (Context) zzab.zzb((Object) $r1, (Object) "Context must not be null");
        this.zzaih = (Looper) zzab.zzb((Object) $r2, (Object) "Looper must not be null");
        this.It = (zzm) zzab.zzb((Object) $r3, (Object) "Supervisor must not be null");
        this.Eb = (GoogleApiAvailabilityLight) zzab.zzb((Object) $r4, (Object) "API availability must not be null");
        this.mHandler = new zzd(this, $r2);
        this.IE = $i0;
        this.IB = $r5;
        this.IC = $r6;
        this.IF = $r7;
    }

    private boolean zza(@Signature({"(IITT;)Z"}) int $i0, @Signature({"(IITT;)Z"}) int $i1, @Signature({"(IITT;)Z"}) T $r1) throws  {
        synchronized (this.zzaix) {
            if (this.IA != $i0) {
                return false;
            }
            zzb($i1, $r1);
            return true;
        }
    }

    private void zzavs() throws  {
        if (this.Iz != null) {
            String $r2 = String.valueOf(zzrg());
            String $r3 = String.valueOf(zzavq());
            Log.e("GmsClient", new StringBuilder((String.valueOf($r2).length() + 70) + String.valueOf($r3).length()).append("Calling connect() while still connected, missing disconnect() for ").append($r2).append(" on ").append($r3).toString());
            this.It.zzb(zzrg(), zzavq(), this.Iz, zzavr());
            this.IG.incrementAndGet();
        }
        this.Iz = new zzh(this, this.IG.get());
        if (!this.It.zza(zzrg(), zzavq(), this.Iz, zzavr())) {
            $r2 = String.valueOf(zzrg());
            $r3 = String.valueOf(zzavq());
            Log.e("GmsClient", new StringBuilder((String.valueOf($r2).length() + 34) + String.valueOf($r3).length()).append("unable to connect to service: ").append($r2).append(" on ").append($r3).toString());
            zza(16, null, this.IG.get());
        }
    }

    private void zzavt() throws  {
        if (this.Iz != null) {
            this.It.zzb(zzrg(), zzavq(), this.Iz, zzavr());
            this.Iz = null;
        }
    }

    private void zzb(@Signature({"(ITT;)V"}) int $i0, @Signature({"(ITT;)V"}) T $r1) throws  {
        boolean $z0 = true;
        if (($i0 == 3) != ($r1 != null)) {
            $z0 = false;
        }
        zzab.zzbn($z0);
        synchronized (this.zzaix) {
            this.IA = $i0;
            this.Ix = $r1;
            zzc($i0, $r1);
            switch ($i0) {
                case 1:
                    zzavt();
                    break;
                case 2:
                    zzavs();
                    break;
                case 3:
                    zza((IInterface) $r1);
                    break;
                default:
                    break;
            }
        }
    }

    public void disconnect() throws  {
        this.IG.incrementAndGet();
        synchronized (this.Iy) {
            int $i0 = this.Iy.size();
            for (int $i1 = 0; $i1 < $i0; $i1++) {
                ((zze) this.Iy.get($i1)).zzawd();
            }
            this.Iy.clear();
        }
        synchronized (this.Iu) {
            this.Iv = null;
        }
        zzb(1, null);
    }

    public void dump(java.lang.String r24, java.io.FileDescriptor r25, java.io.PrintWriter r26, java.lang.String[] r27) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:30:0x01ae
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.modifyBlocksTree(BlockProcessor.java:248)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:52)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.visit(BlockProcessor.java:38)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r23 = this;
        r0 = r23;
        r3 = r0.zzaix;
        monitor-enter(r3);
        r0 = r23;	 Catch:{ Throwable -> 0x01ab }
        r4 = r0.IA;	 Catch:{ Throwable -> 0x01ab }
        r0 = r23;	 Catch:{ Throwable -> 0x01ab }
        r5 = r0.Ix;	 Catch:{ Throwable -> 0x01ab }
        monitor-exit(r3);	 Catch:{ Throwable -> 0x01ab }
        r0 = r26;
        r1 = r24;
        r6 = r0.append(r1);
        r7 = "mConnectState=";
        r6.append(r7);
        switch(r4) {
            case 1: goto L_0x01d6;
            case 2: goto L_0x01b2;
            case 3: goto L_0x01be;
            case 4: goto L_0x01ca;
            default: goto L_0x001e;
        };
    L_0x001e:
        goto L_0x001f;
    L_0x001f:
        r7 = "UNKNOWN";
        r0 = r26;
        r0.print(r7);
    L_0x0026:
        r7 = " mService=";
        r0 = r26;
        r0.append(r7);
        if (r5 != 0) goto L_0x01de;
    L_0x002f:
        r7 = "null";
        r0 = r26;
        r0.println(r7);
    L_0x0036:
        r8 = new java.text.SimpleDateFormat;
        r9 = java.util.Locale.US;
        r7 = "yyyy-MM-dd HH:mm:ss.SSS";
        r8.<init>(r7, r9);
        r0 = r23;
        r10 = r0.Iq;
        r13 = 0;
        r12 = (r10 > r13 ? 1 : (r10 == r13 ? 0 : -1));
        if (r12 <= 0) goto L_0x00a7;
    L_0x004a:
        r0 = r26;
        r1 = r24;
        r6 = r0.append(r1);
        r7 = "lastConnectedTime=";
        r6 = r6.append(r7);
        r0 = r23;
        r10 = r0.Iq;
        r15 = new java.util.Date;
        r0 = r23;
        r0 = r0.Iq;
        r16 = r0;
        r15.<init>(r0);
        r18 = r8.format(r15);
        r0 = r18;
        r18 = java.lang.String.valueOf(r0);
        r19 = new java.lang.StringBuilder;
        r0 = r18;
        r20 = java.lang.String.valueOf(r0);
        r0 = r20;
        r4 = r0.length();
        r4 = r4 + 21;
        r0 = r19;
        r0.<init>(r4);
        r0 = r19;
        r19 = r0.append(r10);
        r7 = " ";
        r0 = r19;
        r19 = r0.append(r7);
        r0 = r19;
        r1 = r18;
        r19 = r0.append(r1);
        r0 = r19;
        r18 = r0.toString();
        r0 = r18;
        r6.println(r0);
    L_0x00a7:
        r0 = r23;
        r10 = r0.Ip;
        r13 = 0;
        r12 = (r10 > r13 ? 1 : (r10 == r13 ? 0 : -1));
        if (r12 <= 0) goto L_0x012c;
    L_0x00b1:
        r0 = r26;
        r1 = r24;
        r6 = r0.append(r1);
        r7 = "lastSuspendedCause=";
        r6.append(r7);
        r0 = r23;
        r4 = r0.Io;
        switch(r4) {
            case 1: goto L_0x020e;
            case 2: goto L_0x021a;
            default: goto L_0x00c5;
        };
    L_0x00c5:
        goto L_0x00c6;
    L_0x00c6:
        r0 = r23;
        r4 = r0.Io;
        r18 = java.lang.String.valueOf(r4);
        r0 = r26;
        r1 = r18;
        r0.append(r1);
    L_0x00d5:
        r7 = " lastSuspendedTime=";
        r0 = r26;
        r6 = r0.append(r7);
        r0 = r23;
        r10 = r0.Ip;
        r15 = new java.util.Date;
        r0 = r23;
        r0 = r0.Ip;
        r16 = r0;
        r15.<init>(r0);
        r18 = r8.format(r15);
        r0 = r18;
        r18 = java.lang.String.valueOf(r0);
        r19 = new java.lang.StringBuilder;
        r0 = r18;
        r20 = java.lang.String.valueOf(r0);
        r0 = r20;
        r4 = r0.length();
        r4 = r4 + 21;
        r0 = r19;
        r0.<init>(r4);
        r0 = r19;
        r19 = r0.append(r10);
        r7 = " ";
        r0 = r19;
        r19 = r0.append(r7);
        r0 = r19;
        r1 = r18;
        r19 = r0.append(r1);
        r0 = r19;
        r18 = r0.toString();
        r0 = r18;
        r6.println(r0);
    L_0x012c:
        r0 = r23;
        r10 = r0.Is;
        r13 = 0;
        r12 = (r10 > r13 ? 1 : (r10 == r13 ? 0 : -1));
        if (r12 <= 0) goto L_0x0222;
    L_0x0136:
        r0 = r26;
        r1 = r24;
        r6 = r0.append(r1);
        r7 = "lastFailedStatus=";
        r6 = r6.append(r7);
        r0 = r23;
        r4 = r0.Ir;
        r24 = com.google.android.gms.common.api.CommonStatusCodes.getStatusCodeString(r4);
        r0 = r24;
        r6.append(r0);
        r7 = " lastFailedTime=";
        r0 = r26;
        r26 = r0.append(r7);
        r0 = r23;
        r10 = r0.Is;
        r15 = new java.util.Date;
        r0 = r23;
        r0 = r0.Is;
        r16 = r0;
        r15.<init>(r0);
        r24 = r8.format(r15);
        r0 = r24;
        r24 = java.lang.String.valueOf(r0);
        r19 = new java.lang.StringBuilder;
        r0 = r24;
        r18 = java.lang.String.valueOf(r0);
        r0 = r18;
        r4 = r0.length();
        r4 = r4 + 21;
        r0 = r19;
        r0.<init>(r4);
        r0 = r19;
        r19 = r0.append(r10);
        r7 = " ";
        r0 = r19;
        r19 = r0.append(r7);
        r0 = r19;
        r1 = r24;
        r19 = r0.append(r1);
        r0 = r19;
        r24 = r0.toString();
        r0 = r26;
        r1 = r24;
        r0.println(r1);
        return;
    L_0x01ab:
        r21 = move-exception;
        monitor-exit(r3);	 Catch:{ Throwable -> 0x01ab }
        throw r21;
        goto L_0x01b2;
    L_0x01af:
        goto L_0x0026;
    L_0x01b2:
        r7 = "CONNECTING";
        r0 = r26;
        r0.print(r7);
        goto L_0x01af;
        goto L_0x01be;
    L_0x01bb:
        goto L_0x0026;
    L_0x01be:
        r7 = "CONNECTED";
        r0 = r26;
        r0.print(r7);
        goto L_0x01bb;
        goto L_0x01ca;
    L_0x01c7:
        goto L_0x0026;
    L_0x01ca:
        r7 = "DISCONNECTING";
        r0 = r26;
        r0.print(r7);
        goto L_0x01c7;
        goto L_0x01d6;
    L_0x01d3:
        goto L_0x0026;
    L_0x01d6:
        r7 = "DISCONNECTED";
        r0 = r26;
        r0.print(r7);
        goto L_0x01d3;
    L_0x01de:
        r0 = r23;
        r18 = r0.zzrh();
        r0 = r26;
        r1 = r18;
        r6 = r0.append(r1);
        r7 = "@";
        r6 = r6.append(r7);
        r22 = r5.asBinder();
        r0 = r22;
        r4 = java.lang.System.identityHashCode(r0);
        r18 = java.lang.Integer.toHexString(r4);
        goto L_0x0204;
    L_0x0201:
        goto L_0x0036;
    L_0x0204:
        r0 = r18;
        r6.println(r0);
        goto L_0x0201;
        goto L_0x020e;
    L_0x020b:
        goto L_0x00d5;
    L_0x020e:
        r7 = "CAUSE_SERVICE_DISCONNECTED";
        r0 = r26;
        r0.append(r7);
        goto L_0x020b;
        goto L_0x021a;
    L_0x0217:
        goto L_0x00d5;
    L_0x021a:
        r7 = "CAUSE_NETWORK_LOST";
        r0 = r26;
        r0.append(r7);
        goto L_0x0217;
    L_0x0222:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.internal.zzd.dump(java.lang.String, java.io.FileDescriptor, java.io.PrintWriter, java.lang.String[]):void");
    }

    public Account getAccount() throws  {
        return null;
    }

    public final Context getContext() throws  {
        return this.mContext;
    }

    public final Looper getLooper() throws  {
        return this.zzaih;
    }

    public boolean isConnected() throws  {
        boolean $z0;
        synchronized (this.zzaix) {
            $z0 = this.IA == 3;
        }
        return $z0;
    }

    public boolean isConnecting() throws  {
        boolean $z0;
        synchronized (this.zzaix) {
            $z0 = this.IA == 2;
        }
        return $z0;
    }

    @CallSuper
    protected void onConnectionFailed(ConnectionResult $r1) throws  {
        this.Ir = $r1.getErrorCode();
        this.Is = System.currentTimeMillis();
    }

    @CallSuper
    protected void onConnectionSuspended(int $i0) throws  {
        this.Io = $i0;
        this.Ip = System.currentTimeMillis();
    }

    protected void zza(int $i0, @Nullable Bundle $r1, int $i1) throws  {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(5, $i1, -1, new zzk(this, $i0, $r1)));
    }

    @BinderThread
    protected void zza(int $i0, IBinder $r1, Bundle $r2, int $i1) throws  {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(1, $i1, -1, new zzj(this, $i0, $r1, $r2)));
    }

    @CallSuper
    protected void zza(@NonNull @Signature({"(TT;)V"}) T t) throws  {
        this.Iq = System.currentTimeMillis();
    }

    @Deprecated
    public final void zza(@Deprecated @Signature({"(", "Lcom/google/android/gms/common/internal/zzd$zze", "<*>;)V"}) zze<?> $r1) throws  {
        synchronized (this.Iy) {
            this.Iy.add($r1);
        }
        this.mHandler.sendMessage(this.mHandler.obtainMessage(2, this.IG.get(), -1, $r1));
    }

    public void zza(@NonNull zzf $r1) throws  {
        this.Iw = (zzf) zzab.zzb((Object) $r1, (Object) "Connection progress callbacks cannot be null.");
        zzb(2, null);
    }

    public void zza(zzf $r1, ConnectionResult $r2) throws  {
        this.Iw = (zzf) zzab.zzb((Object) $r1, (Object) "Connection progress callbacks cannot be null.");
        this.mHandler.sendMessage(this.mHandler.obtainMessage(3, this.IG.get(), $r2.getErrorCode(), $r2.getResolution()));
    }

    @WorkerThread
    public void zza(@Signature({"(", "Lcom/google/android/gms/common/internal/zzq;", "Ljava/util/Set", "<", "Lcom/google/android/gms/common/api/Scope;", ">;)V"}) zzq $r1, @Signature({"(", "Lcom/google/android/gms/common/internal/zzq;", "Ljava/util/Set", "<", "Lcom/google/android/gms/common/api/Scope;", ">;)V"}) Set<Scope> $r2) throws  {
        try {
            GetServiceRequest $r5 = new GetServiceRequest(this.IE).zzgs(this.mContext.getPackageName()).zzs(zzadn());
            if ($r2 != null) {
                $r5.zzi($r2);
            }
            if (zzaec()) {
                $r5.zzc(zzavv()).zzb($r1);
            } else if (zzavy()) {
                $r5.zzc(getAccount());
            }
            synchronized (this.Iu) {
                if (this.Iv != null) {
                    this.Iv.zza(new zzg(this, this.IG.get()), $r5);
                } else {
                    Log.w("GmsClient", "mServiceBroker is null, client disconnected");
                }
            }
        } catch (DeadObjectException e) {
            Log.w("GmsClient", "service died");
            zzil(1);
        } catch (Throwable $r15) {
            Log.w("GmsClient", "Remote exception occurred", $r15);
        }
    }

    protected Bundle zzadn() throws  {
        return new Bundle();
    }

    public boolean zzaec() throws  {
        return false;
    }

    public boolean zzaer() throws  {
        return false;
    }

    public Intent zzaes() throws  {
        throw new UnsupportedOperationException("Not a sign in API");
    }

    public Bundle zzapt() throws  {
        return null;
    }

    public boolean zzarn() throws  {
        return true;
    }

    @Nullable
    public IBinder zzaro() throws  {
        synchronized (this.Iu) {
            if (this.Iv == null) {
                return null;
            }
            IBinder $r2 = this.Iv.asBinder();
            return $r2;
        }
    }

    protected String zzavq() throws  {
        return "com.google.android.gms";
    }

    @Nullable
    protected final String zzavr() throws  {
        return this.IF == null ? this.mContext.getClass().getName() : this.IF;
    }

    public void zzavu() throws  {
        int $i0 = this.Eb.isGooglePlayServicesAvailable(this.mContext);
        if ($i0 != 0) {
            zzb(1, null);
            this.Iw = new zzi(this);
            this.mHandler.sendMessage(this.mHandler.obtainMessage(3, this.IG.get(), $i0));
            return;
        }
        zza(new zzi(this));
    }

    public final Account zzavv() throws  {
        return getAccount() != null ? getAccount() : new Account("<<default account>>", "com.google");
    }

    protected final void zzavw() throws  {
        if (!isConnected()) {
            throw new IllegalStateException("Not connected. Call connect() and wait for onConnected() to be called.");
        }
    }

    public final T zzavx() throws DeadObjectException {
        IInterface $r4;
        synchronized (this.zzaix) {
            if (this.IA == 4) {
                throw new DeadObjectException();
            }
            zzavw();
            zzab.zza(this.Ix != null, (Object) "Client is connected but service is null");
            $r4 = this.Ix;
        }
        return $r4;
    }

    public boolean zzavy() throws  {
        return false;
    }

    protected Set<Scope> zzavz() throws  {
        return Collections.EMPTY_SET;
    }

    @Nullable
    protected abstract T zzbc(@Signature({"(", "Landroid/os/IBinder;", ")TT;"}) IBinder iBinder) throws ;

    void zzc(@Signature({"(ITT;)V"}) int i, @Signature({"(ITT;)V"}) T t) throws  {
    }

    public void zzil(int $i0) throws  {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(4, this.IG.get(), $i0));
    }

    @NonNull
    protected abstract String zzrg() throws ;

    @NonNull
    protected abstract String zzrh() throws ;
}
