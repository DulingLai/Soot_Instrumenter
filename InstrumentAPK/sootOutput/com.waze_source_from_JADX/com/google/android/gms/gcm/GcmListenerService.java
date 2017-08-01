package com.google.android.gms.gcm;

import android.annotation.TargetApi;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import java.util.Iterator;

/* compiled from: dalvik_source_com.waze.apk */
public abstract class GcmListenerService extends Service {
    private int aqG;
    private int aqH = 0;
    private final Object zzaix = new Object();

    static void zzah(Bundle $r0) throws  {
        Iterator $r2 = $r0.keySet().iterator();
        while ($r2.hasNext()) {
            String $r4 = (String) $r2.next();
            if ($r4 != null && $r4.startsWith("google.c.")) {
                $r2.remove();
            }
        }
    }

    private void zzbqs() throws  {
        synchronized (this.zzaix) {
            this.aqH--;
            if (this.aqH == 0) {
                zzvh(this.aqG);
            }
        }
    }

    @TargetApi(11)
    private void zzo(final Intent $r1) throws  {
        if (VERSION.SDK_INT >= 11) {
            AsyncTask.THREAD_POOL_EXECUTOR.execute(new Runnable(this) {
                final /* synthetic */ GcmListenerService aqI;

                public void run() throws  {
                    this.aqI.zzp($r1);
                }
            });
        } else {
            new AsyncTask<Void, Void, Void>(this) {
                final /* synthetic */ GcmListenerService aqI;

                protected Void doInBackground(Void... voidArr) throws  {
                    this.aqI.zzp($r1);
                    return null;
                }
            }.execute(new Void[0]);
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void zzp(android.content.Intent r8) throws  {
        /*
        r7 = this;
        r0 = r8.getAction();	 Catch:{ Throwable -> 0x003f }
        r1 = -1;
        r2 = r0.hashCode();	 Catch:{ Throwable -> 0x003f }
        switch(r2) {
            case 366519424: goto L_0x0031;
            default: goto L_0x000c;
        };	 Catch:{ Throwable -> 0x003f }
    L_0x000c:
        goto L_0x000d;
    L_0x000d:
        switch(r1) {
            case 0: goto L_0x003b;
            default: goto L_0x0010;
        };	 Catch:{ Throwable -> 0x003f }
    L_0x0010:
        goto L_0x0011;
    L_0x0011:
        r0 = "Unknown intent action: ";
        r3 = r8.getAction();	 Catch:{ Throwable -> 0x003f }
        r3 = java.lang.String.valueOf(r3);	 Catch:{ Throwable -> 0x003f }
        r2 = r3.length();	 Catch:{ Throwable -> 0x003f }
        if (r2 == 0) goto L_0x0044;
    L_0x0021:
        r0 = r0.concat(r3);	 Catch:{ Throwable -> 0x003f }
    L_0x0025:
        r4 = "GcmListenerService";
        android.util.Log.d(r4, r0);	 Catch:{ Throwable -> 0x003f }
    L_0x002a:
        r7.zzbqs();	 Catch:{ Throwable -> 0x003f }
        android.support.v4.content.WakefulBroadcastReceiver.completeWakefulIntent(r8);
        return;
    L_0x0031:
        r4 = "com.google.android.c2dm.intent.RECEIVE";
        r5 = r0.equals(r4);	 Catch:{ Throwable -> 0x003f }
        if (r5 == 0) goto L_0x000d;
    L_0x0039:
        r1 = 0;
        goto L_0x000d;
    L_0x003b:
        r7.zzq(r8);	 Catch:{ Throwable -> 0x003f }
        goto L_0x002a;
    L_0x003f:
        r6 = move-exception;
        android.support.v4.content.WakefulBroadcastReceiver.completeWakefulIntent(r8);
        throw r6;
    L_0x0044:
        r0 = new java.lang.String;	 Catch:{ Throwable -> 0x003f }
        r4 = "Unknown intent action: ";
        r0.<init>(r4);	 Catch:{ Throwable -> 0x003f }
        goto L_0x0025;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.gcm.GcmListenerService.zzp(android.content.Intent):void");
    }

    private void zzq(Intent $r1) throws  {
        String $r2 = $r1.getStringExtra("message_type");
        String $r3 = $r2;
        if ($r2 == null) {
            $r3 = GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE;
        }
        byte $b0 = (byte) -1;
        switch ($r3.hashCode()) {
            case -2062414158:
                if ($r3.equals(GoogleCloudMessaging.MESSAGE_TYPE_DELETED)) {
                    $b0 = (byte) 1;
                    break;
                }
                break;
            case 102161:
                if ($r3.equals(GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE)) {
                    $b0 = (byte) 0;
                    break;
                }
                break;
            case 814694033:
                if ($r3.equals(GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR)) {
                    $b0 = (byte) 3;
                    break;
                }
                break;
            case 814800675:
                if ($r3.equals(GoogleCloudMessaging.MESSAGE_TYPE_SEND_EVENT)) {
                    $b0 = (byte) 2;
                    break;
                }
                break;
            default:
                break;
        }
        switch ($b0) {
            case (byte) 0:
                zzr($r1);
                return;
            case (byte) 1:
                onDeletedMessages();
                return;
            case (byte) 2:
                onMessageSent($r1.getStringExtra("google.message_id"));
                return;
            case (byte) 3:
                onSendError($r1.getStringExtra("google.message_id"), $r1.getStringExtra("error"));
                return;
            default:
                $r2 = "Received message with unknown type: ";
                $r3 = String.valueOf($r3);
                Log.w("GcmListenerService", $r3.length() != 0 ? $r2.concat($r3) : new String("Received message with unknown type: "));
                return;
        }
    }

    private void zzr(Intent $r1) throws  {
        Bundle $r2 = $r1.getExtras();
        $r2.remove("message_type");
        $r2.remove("android.support.content.wakelockid");
        if (zza.zzai($r2)) {
            if (zza.zzcx(this)) {
                zza.zzaj($r2);
            } else {
                zza.zzcw(this).zzak($r2);
                return;
            }
        }
        String $r4 = $r2.getString("from");
        $r2.remove("from");
        zzah($r2);
        onMessageReceived($r4, $r2);
    }

    public final IBinder onBind(Intent intent) throws  {
        return null;
    }

    public void onDeletedMessages() throws  {
    }

    public void onMessageReceived(String str, Bundle bundle) throws  {
    }

    public void onMessageSent(String str) throws  {
    }

    public void onSendError(String str, String str2) throws  {
    }

    public final int onStartCommand(Intent $r1, int i, int $i1) throws  {
        synchronized (this.zzaix) {
            this.aqG = $i1;
            this.aqH++;
        }
        if ($r1 == null) {
            zzbqs();
            return 2;
        }
        zzo($r1);
        return 3;
    }

    boolean zzvh(int $i0) throws  {
        return stopSelfResult($i0);
    }
}
