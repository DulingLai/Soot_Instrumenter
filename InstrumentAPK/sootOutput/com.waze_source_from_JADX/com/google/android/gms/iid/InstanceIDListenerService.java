package com.google.android.gms.iid;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;
import com.facebook.internal.NativeProtocol;
import com.google.android.gms.gcm.GoogleCloudMessaging;

/* compiled from: dalvik_source_com.waze.apk */
public class InstanceIDListenerService extends Service {
    static String ACTION = NativeProtocol.WEB_DIALOG_ACTION;
    private static String aqP = "gcm.googleapis.com/refresh";
    private static String atK = "google.com/iid";
    private static String atL = "CMD";
    MessengerCompat atI = new MessengerCompat(new Handler(this, Looper.getMainLooper()) {
        final /* synthetic */ InstanceIDListenerService atO;

        public void handleMessage(Message $r1) throws  {
            this.atO.zza($r1, MessengerCompat.zzc($r1));
        }
    });
    BroadcastReceiver atJ = new C07452(this);
    int atM;
    int atN;

    /* compiled from: dalvik_source_com.waze.apk */
    class C07452 extends BroadcastReceiver {
        final /* synthetic */ InstanceIDListenerService atO;

        C07452(InstanceIDListenerService $r1) throws  {
            this.atO = $r1;
        }

        public void onReceive(Context context, Intent $r2) throws  {
            if (Log.isLoggable("InstanceID", 3)) {
                $r2.getStringExtra(GoogleCloudMessaging.REGISTRATION_ID);
                String $r4 = String.valueOf($r2.getExtras());
                Log.d("InstanceID", new StringBuilder(String.valueOf($r4).length() + 46).append("Received GSF callback using dynamic receiver: ").append($r4).toString());
            }
            this.atO.zzq($r2);
            this.atO.stop();
        }
    }

    static void zza(Context $r0, zzc $r1) throws  {
        $r1.zzbry();
        Intent $r2 = new Intent("com.google.android.gms.iid.InstanceID");
        $r2.putExtra(atL, "RST");
        $r2.setPackage($r0.getPackageName());
        $r0.startService($r2);
    }

    private void zza(Message $r1, int $i0) throws  {
        Rpc.findAppIDPackage(this);
        getPackageManager();
        if ($i0 == Rpc.atU || $i0 == Rpc.atT) {
            zzq((Intent) $r1.obj);
            return;
        }
        int $i2 = Rpc.atT;
        String str = "InstanceID";
        Log.w(str, "Message from unexpected caller " + $i0 + " mine=" + $i2 + " appid=" + Rpc.atU);
    }

    static void zzdb(Context $r0) throws  {
        Intent $r1 = new Intent("com.google.android.gms.iid.InstanceID");
        $r1.setPackage($r0.getPackageName());
        $r1.putExtra(atL, "SYNC");
        $r0.startService($r1);
    }

    public IBinder onBind(Intent $r1) throws  {
        return ($r1 == null || !"com.google.android.gms.iid.InstanceID".equals($r1.getAction())) ? null : this.atI.getBinder();
    }

    public void onCreate() throws  {
        IntentFilter $r1 = new IntentFilter("com.google.android.c2dm.intent.REGISTRATION");
        $r1.addCategory(getPackageName());
        registerReceiver(this.atJ, $r1, "com.google.android.c2dm.permission.RECEIVE", null);
    }

    public void onDestroy() throws  {
        unregisterReceiver(this.atJ);
    }

    public int onStartCommand(Intent $r1, int i, int $i1) throws  {
        zzvx($i1);
        if ($r1 == null) {
            stop();
            return 2;
        }
        try {
            if ("com.google.android.gms.iid.InstanceID".equals($r1.getAction())) {
                if (VERSION.SDK_INT <= 18) {
                    Intent $r5 = (Intent) $r1.getParcelableExtra("GSF");
                    if ($r5 != null) {
                        startService($r5);
                        return 1;
                    }
                }
                zzq($r1);
            }
            stop();
            if ($r1.getStringExtra("from") != null) {
                WakefulBroadcastReceiver.completeWakefulIntent($r1);
            }
            return 2;
        } finally {
            stop();
        }
    }

    public void onTokenRefresh() throws  {
    }

    void stop() throws  {
        synchronized (this) {
            this.atM--;
            if (this.atM == 0) {
                stopSelf(this.atN);
            }
            if (Log.isLoggable("InstanceID", 3)) {
                int $i1 = this.atM;
                String str = "InstanceID";
                Log.d(str, "Stop " + $i1 + " " + this.atN);
            }
        }
    }

    public void zzcd(boolean z) throws  {
        onTokenRefresh();
    }

    public void zzq(Intent $r1) throws  {
        InstanceID $r4;
        String $r2 = $r1.getStringExtra(InstanceID.OPTION_SUBTYPE);
        String $r3 = $r2;
        if ($r2 == null) {
            $r4 = InstanceID.getInstance(this);
        } else {
            Bundle $r8 = new Bundle();
            $r8.putString(InstanceID.OPTION_SUBTYPE, $r2);
            $r4 = InstanceID.getInstance(this, $r8);
        }
        String $r5 = $r1.getStringExtra(atL);
        if ($r1.getStringExtra("error") == null) {
            if ($r1.getStringExtra(GoogleCloudMessaging.REGISTRATION_ID) == null) {
                if (Log.isLoggable("InstanceID", 3)) {
                    String $r6 = String.valueOf($r1.getExtras());
                    Log.d("InstanceID", new StringBuilder(((String.valueOf($r2).length() + 18) + String.valueOf($r5).length()) + String.valueOf($r6).length()).append("Service command ").append($r2).append(" ").append($r5).append(" ").append($r6).toString());
                }
                if ($r1.getStringExtra("unregistered") != null) {
                    zzc $r11 = $r4.zzbru();
                    if ($r2 == null) {
                        $r3 = "";
                    }
                    $r11.zzke($r3);
                    $r4.zzbrv().zzaa($r1);
                    return;
                }
                if (aqP.equals($r1.getStringExtra("from"))) {
                    $r4.zzbru().zzke($r2);
                    zzcd(false);
                    return;
                } else if ("RST".equals($r5)) {
                    $r4.zzbrt();
                    zzcd(true);
                    return;
                } else if ("RST_FULL".equals($r5)) {
                    if (!$r4.zzbru().isEmpty()) {
                        $r4.zzbru().zzbry();
                        zzcd(true);
                        return;
                    }
                    return;
                } else if ("SYNC".equals($r5)) {
                    $r4.zzbru().zzke($r2);
                    zzcd(false);
                    return;
                } else if (!"PING".equals($r5)) {
                    return;
                } else {
                    return;
                }
            }
        }
        if (Log.isLoggable("InstanceID", 3)) {
            $r5 = "Register result in service ";
            $r2 = String.valueOf($r2);
            Log.d("InstanceID", $r2.length() != 0 ? $r5.concat($r2) : new String("Register result in service "));
        }
        $r4.zzbrv().zzaa($r1);
    }

    void zzvx(int $i0) throws  {
        synchronized (this) {
            this.atM++;
            if ($i0 > this.atN) {
                this.atN = $i0;
            }
        }
    }
}
