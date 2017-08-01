package com.google.android.gms.gcm;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.support.annotation.RequiresPermission;
import android.util.Log;
import com.google.android.gms.iid.InstanceID;
import com.google.android.gms.iid.Rpc;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: dalvik_source_com.waze.apk */
public class GoogleCloudMessaging {
    public static final String ERROR_MAIN_THREAD = "MAIN_THREAD";
    public static final String ERROR_SERVICE_NOT_AVAILABLE = "SERVICE_NOT_AVAILABLE";
    public static final String INSTANCE_ID_SCOPE = "GCM";
    @Deprecated
    public static final String MESSAGE_TYPE_DELETED = "deleted_messages";
    @Deprecated
    public static final String MESSAGE_TYPE_MESSAGE = "gcm";
    @Deprecated
    public static final String MESSAGE_TYPE_SEND_ERROR = "send_error";
    @Deprecated
    public static final String MESSAGE_TYPE_SEND_EVENT = "send_event";
    public static final String REGISTRATION_ID = "registration_id";
    public static int aqU = 5000000;
    public static int aqV = 6500000;
    public static int aqW = 7000000;
    static GoogleCloudMessaging aqX;
    private static final AtomicInteger ara = new AtomicInteger(1);
    private PendingIntent aqY;
    private Map<String, Handler> aqZ = Collections.synchronizedMap(new HashMap());
    private final BlockingQueue<Intent> arb = new LinkedBlockingQueue();
    final Messenger arc = new Messenger(new Handler(this, Looper.getMainLooper()) {
        final /* synthetic */ GoogleCloudMessaging ard;

        public void handleMessage(Message $r1) throws  {
            if ($r1 == null || !($r1.obj instanceof Intent)) {
                Log.w(GoogleCloudMessaging.INSTANCE_ID_SCOPE, "Dropping invalid message");
            }
            Intent $r3 = (Intent) $r1.obj;
            if ("com.google.android.c2dm.intent.REGISTRATION".equals($r3.getAction())) {
                this.ard.arb.add($r3);
            } else if (!this.ard.zzs($r3)) {
                $r3.setPackage(this.ard.zzagf.getPackageName());
                this.ard.zzagf.sendBroadcast($r3);
            }
        }
    });
    private Context zzagf;

    public static String getGcmPackage(Context $r0) throws  {
        return Rpc.findAppIDPackage($r0);
    }

    public static int getGcmVersion(Context $r0) throws  {
        try {
            return $r0.getPackageManager().getPackageInfo(getGcmPackage($r0), 0).versionCode;
        } catch (NameNotFoundException e) {
            return -1;
        }
    }

    public static synchronized GoogleCloudMessaging getInstance(Context $r0) throws  {
        Class cls = GoogleCloudMessaging.class;
        synchronized (cls) {
            try {
                if (aqX == null) {
                    aqX = new GoogleCloudMessaging();
                    aqX.zzagf = $r0.getApplicationContext();
                }
                GoogleCloudMessaging $r1 = aqX;
                return $r1;
            } finally {
                cls = GoogleCloudMessaging.class;
            }
        }
    }

    static String zza(Intent $r0, String $r1) throws IOException {
        if ($r0 == null) {
            throw new IOException("SERVICE_NOT_AVAILABLE");
        }
        $r1 = $r0.getStringExtra($r1);
        if ($r1 != null) {
            return $r1;
        }
        $r1 = $r0.getStringExtra("error");
        if ($r1 != null) {
            throw new IOException($r1);
        }
        throw new IOException("SERVICE_NOT_AVAILABLE");
    }

    private void zza(String $r1, String $r2, long $l0, int $i1, Bundle $r3) throws IOException {
        if ($r1 == null) {
            throw new IllegalArgumentException("Missing 'to'");
        }
        Intent $r6 = new Intent("com.google.android.gcm.intent.SEND");
        if ($r3 != null) {
            $r6.putExtras($r3);
        }
        zzt($r6);
        $r6.setPackage(getGcmPackage(this.zzagf));
        $r6.putExtra("google.to", $r1);
        $r6.putExtra("google.message_id", $r2);
        $r6.putExtra("google.ttl", Long.toString($l0));
        $r6.putExtra("google.delay", Integer.toString($i1));
        $r6.putExtra("google.from", zzjt($r1));
        if (getGcmPackage(this.zzagf).contains(".gsf")) {
            Bundle $r4 = new Bundle();
            for (String $r12 : $r3.keySet()) {
                String $r122;
                Object $r11 = $r3.get($r122);
                if ($r11 instanceof String) {
                    String $r8 = "gcm.";
                    $r122 = String.valueOf($r122);
                    $r4.putString($r122.length() != 0 ? $r8.concat($r122) : new String("gcm."), (String) $r11);
                }
            }
            $r4.putString("google.to", $r1);
            $r4.putString("google.message_id", $r2);
            InstanceID.getInstance(this.zzagf).zzb(INSTANCE_ID_SCOPE, "upstream", $r4);
            return;
        }
        this.zzagf.sendOrderedBroadcast($r6, "com.google.android.gtalkservice.permission.GTALK_SERVICE");
    }

    private String zzbqw() throws  {
        String $r1 = String.valueOf("google.rpc");
        String $r3 = String.valueOf(String.valueOf(ara.getAndIncrement()));
        return $r3.length() != 0 ? $r1.concat($r3) : new String($r1);
    }

    private String zzjt(String $r1) throws  {
        int $i0 = $r1.indexOf(64);
        if ($i0 > 0) {
            $r1 = $r1.substring(0, $i0);
        }
        return InstanceID.getInstance(this.zzagf).zzbru().zzh("", $r1, INSTANCE_ID_SCOPE);
    }

    private boolean zzs(Intent $r1) throws  {
        String $r2 = $r1.getStringExtra("In-Reply-To");
        String $r3 = $r2;
        if ($r2 == null && $r1.hasExtra("error")) {
            $r3 = $r1.getStringExtra("google.message_id");
        }
        if ($r3 != null) {
            Handler $r6 = (Handler) this.aqZ.remove($r3);
            if ($r6 != null) {
                Message $r7 = Message.obtain();
                $r7.obj = $r1;
                return $r6.sendMessage($r7);
            }
        }
        return false;
    }

    public void close() throws  {
        aqX = null;
        zza.aqL = null;
        zzbqx();
    }

    public String getMessageType(Intent $r1) throws  {
        if (!"com.google.android.c2dm.intent.RECEIVE".equals($r1.getAction())) {
            return null;
        }
        String $r2 = $r1.getStringExtra("message_type");
        return $r2 == null ? MESSAGE_TYPE_MESSAGE : $r2;
    }

    @RequiresPermission("com.google.android.c2dm.permission.RECEIVE")
    @Deprecated
    public synchronized String register(@Deprecated String... $r1) throws IOException {
        String $r2;
        $r2 = zze($r1);
        Bundle $r3 = new Bundle();
        if (getGcmPackage(this.zzagf).contains(".gsf")) {
            $r3.putString("legacy.sender", $r2);
            $r2 = InstanceID.getInstance(this.zzagf).getToken($r2, INSTANCE_ID_SCOPE, $r3);
        } else {
            $r3.putString("sender", $r2);
            $r2 = zza(zzan($r3), REGISTRATION_ID);
        }
        return $r2;
    }

    public Intent rpc(String $r1, Bundle $r2, long $l0) throws IOException {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            throw new IOException("MAIN_THREAD");
        }
        Bundle bundle;
        String $r8 = zzbqw();
        final CountDownLatch $r9 = new CountDownLatch(1);
        final Intent[] $r4 = new Intent[1];
        this.aqZ.put($r8, new Handler(Looper.getMainLooper(), new Callback(this) {
            final /* synthetic */ GoogleCloudMessaging ard;

            public boolean handleMessage(Message $r1) throws  {
                if ($r1 == null || !($r1.obj instanceof Intent)) {
                    return false;
                }
                $r4[0] = (Intent) $r1.obj;
                $r9.countDown();
                return true;
            }
        }));
        if ($r2 == null) {
            bundle = new Bundle();
        }
        bundle = $r2;
        bundle.putParcelable("google.messenger", this.arc);
        aqX.send($r1, $r8, 0, $r2);
        try {
            $r9.await($l0, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
        }
        zzju($r8);
        return $r4[0];
    }

    @RequiresPermission("com.google.android.c2dm.permission.RECEIVE")
    public void send(String $r1, String $r2, long $l0, Bundle $r3) throws IOException {
        zza($r1, $r2, $l0, -1, $r3);
    }

    @RequiresPermission("com.google.android.c2dm.permission.RECEIVE")
    public void send(String $r1, String $r2, Bundle $r3) throws IOException {
        send($r1, $r2, -1, $r3);
    }

    @RequiresPermission("com.google.android.c2dm.permission.RECEIVE")
    public void sendBatched(String $r1, String $r2, int $i0, Bundle $r3) throws IOException {
        zza($r1, $r2, -1, $i0, $r3);
    }

    public void sendHeartbeat() throws  {
        this.zzagf.sendBroadcast(new Intent("com.google.android.intent.action.MCS_HEARTBEAT"));
    }

    @RequiresPermission("com.google.android.c2dm.permission.RECEIVE")
    @Deprecated
    public synchronized void unregister() throws IOException {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            throw new IOException("MAIN_THREAD");
        }
        InstanceID.getInstance(this.zzagf).deleteInstanceID();
    }

    @Deprecated
    Intent zzan(@Deprecated Bundle $r1) throws IOException {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            throw new IOException("MAIN_THREAD");
        } else if (getGcmVersion(this.zzagf) < 0) {
            throw new IOException("Google Play Services missing");
        } else {
            if ($r1 == null) {
                Bundle bundle = new Bundle();
            }
            Intent $r6 = new Intent("com.google.android.c2dm.intent.REGISTER");
            $r6.setPackage(getGcmPackage(this.zzagf));
            zzt($r6);
            $r6.putExtra("google.message_id", zzbqw());
            $r6.putExtras($r1);
            $r6.putExtra("google.messenger", this.arc);
            this.zzagf.startService($r6);
            try {
                return (Intent) this.arb.poll(30000, TimeUnit.MILLISECONDS);
            } catch (InterruptedException $r12) {
                throw new IOException($r12.getMessage());
            }
        }
    }

    synchronized void zzbqx() throws  {
        if (this.aqY != null) {
            this.aqY.cancel();
            this.aqY = null;
        }
    }

    String zze(String... $r1) throws  {
        if ($r1 == null || $r1.length == 0) {
            throw new IllegalArgumentException("No senderIds");
        }
        StringBuilder $r4 = new StringBuilder($r1[0]);
        for (int $i0 = 1; $i0 < $r1.length; $i0++) {
            $r4.append(',').append($r1[$i0]);
        }
        return $r4.toString();
    }

    public void zzju(String $r1) throws  {
        this.aqZ.remove($r1);
    }

    synchronized void zzt(Intent $r1) throws  {
        if (this.aqY == null) {
            Intent $r3 = new Intent();
            $r3.setPackage("com.google.example.invalidpackage");
            this.aqY = PendingIntent.getBroadcast(this.zzagf, 0, $r3, 0);
        }
        $r1.putExtra("app", this.aqY);
    }
}
