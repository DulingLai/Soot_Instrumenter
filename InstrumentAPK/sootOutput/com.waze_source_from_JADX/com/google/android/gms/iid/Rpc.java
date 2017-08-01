package com.google.android.gms.iid;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.ConditionVariable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcelable;
import android.os.Process;
import android.os.RemoteException;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import com.facebook.appevents.AppEventsConstants;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.waze.analytics.AnalyticsEvents;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/* compiled from: dalvik_source_com.waze.apk */
public class Rpc {
    public static final String PARAM_APP_VER = "app_ver";
    public static final String PARAM_APP_VER_NAME = "app_ver_name";
    public static final String PARAM_CLIENT_VER = "cliv";
    public static final String PARAM_GMS_VER = "gmsv";
    public static final String PARAM_INSTANCE_ID = "appid";
    public static final String PARAM_OS_VER = "osv";
    public static final String PARAM_PUBLIC_KEY = "pub2";
    public static final String PARAM_SIGNATURE = "sig";
    static String atS = null;
    static int atT = 0;
    static int atU = 0;
    static int atV = 0;
    PendingIntent aqY;
    Messenger arc;
    Map<String, Object> atW = new HashMap();
    Messenger atX;
    MessengerCompat atY;
    long atZ;
    long aua;
    int aub;
    int auc;
    long aud;
    Context zzagf;

    public Rpc(Context $r1) throws  {
        this.zzagf = $r1;
    }

    public static String findAppIDPackage(Context $r0) throws  {
        if (atS != null) {
            return atS;
        }
        atT = Process.myUid();
        PackageManager $r2 = $r0.getPackageManager();
        for (ResolveInfo $r7 : $r2.queryIntentServices(new Intent("com.google.android.c2dm.intent.REGISTER"), 0)) {
            if ($r2.checkPermission("com.google.android.c2dm.permission.RECEIVE", $r7.serviceInfo.packageName) == 0) {
                try {
                    ApplicationInfo $r9 = $r2.getApplicationInfo($r7.serviceInfo.packageName, 0);
                    Log.w("InstanceID/Rpc", "Found " + $r9.uid);
                    atU = $r9.uid;
                    atS = $r7.serviceInfo.packageName;
                    return atS;
                } catch (NameNotFoundException e) {
                }
            } else {
                String $r1 = String.valueOf($r7.serviceInfo.packageName);
                String $r11 = String.valueOf("com.google.android.c2dm.intent.REGISTER");
                Log.w("InstanceID/Rpc", new StringBuilder((String.valueOf($r1).length() + 56) + String.valueOf($r11).length()).append("Possible malicious package ").append($r1).append(" declares ").append($r11).append(" without permission").toString());
            }
        }
        Log.w("InstanceID/Rpc", "Failed to resolve REGISTER intent, falling back");
        try {
            $r9 = $r2.getApplicationInfo("com.google.android.gms", 0);
            atS = $r9.packageName;
            atU = $r9.uid;
            return atS;
        } catch (NameNotFoundException e2) {
            try {
                $r9 = $r2.getApplicationInfo("com.google.android.gsf", 0);
                atS = $r9.packageName;
                atU = $r9.uid;
                return atS;
            } catch (NameNotFoundException e3) {
                Log.w("InstanceID/Rpc", "Both Google Play Services and legacy GSF package are missing");
                return null;
            }
        }
    }

    public static synchronized String nextId() throws  {
        Class cls = Rpc.class;
        synchronized (cls) {
            try {
                int $i1 = atV;
                atV = $i1 + 1;
                String $r0 = Integer.toString($i1);
                return $r0;
            } finally {
                cls = Rpc.class;
            }
        }
    }

    static String zza(KeyPair $r0, String... $r1) throws  {
        try {
            byte[] $r3 = TextUtils.join("\n", $r1).getBytes("UTF-8");
            try {
                PrivateKey $r4 = $r0.getPrivate();
                Signature $r5 = Signature.getInstance($r4 instanceof RSAPrivateKey ? "SHA256withRSA" : "SHA256withECDSA");
                $r5.initSign($r4);
                $r5.update($r3);
                return InstanceID.zzad($r5.sign());
            } catch (GeneralSecurityException $r7) {
                Log.e("InstanceID/Rpc", "Unable to sign registration request", $r7);
                return null;
            }
        } catch (UnsupportedEncodingException $r6) {
            Log.e("InstanceID/Rpc", "Unable to encode string", $r6);
            return null;
        }
    }

    private void zzap(Object $r1) throws  {
        synchronized (getClass()) {
            for (String $r7 : this.atW.keySet()) {
                Object $r6 = this.atW.get($r7);
                this.atW.put($r7, $r1);
                zzh($r6, $r1);
            }
        }
    }

    private Intent zzb(Bundle $r1, KeyPair $r2) throws IOException {
        Intent $r8;
        ConditionVariable $r3 = new ConditionVariable();
        String $r4 = nextId();
        synchronized (getClass()) {
            this.atW.put($r4, $r3);
        }
        zza($r1, $r2, $r4);
        $r3.block(30000);
        synchronized (getClass()) {
            Object $r7 = this.atW.remove($r4);
            if ($r7 instanceof Intent) {
                $r8 = (Intent) $r7;
            } else if ($r7 instanceof String) {
                throw new IOException((String) $r7);
            } else {
                $r4 = String.valueOf($r7);
                Log.w("InstanceID/Rpc", new StringBuilder(String.valueOf($r4).length() + 12).append("No response ").append($r4).toString());
                throw new IOException("TIMEOUT");
            }
        }
        return $r8;
    }

    private static int zzdc(Context $r0) throws  {
        try {
            return $r0.getPackageManager().getPackageInfo(findAppIDPackage($r0), 0).versionCode;
        } catch (NameNotFoundException e) {
            return -1;
        }
    }

    private void zzh(Object $r2, Object $r1) throws  {
        if ($r2 instanceof ConditionVariable) {
            ((ConditionVariable) $r2).open();
        }
        if ($r2 instanceof Messenger) {
            Messenger $r4 = (Messenger) $r2;
            Message $r5 = Message.obtain();
            $r5.obj = $r1;
            try {
                $r4.send($r5);
            } catch (RemoteException $r6) {
                String $r7 = String.valueOf($r6);
                Log.w("InstanceID/Rpc", new StringBuilder(String.valueOf($r7).length() + 24).append("Failed to send response ").append($r7).toString());
            }
        }
    }

    private void zzjz(String $r1) throws  {
        if ("com.google.android.gsf".equals(atS)) {
            this.aub++;
            if (this.aub >= 3) {
                if (this.aub == 3) {
                    this.auc = new Random().nextInt(1000) + 1000;
                }
                this.auc *= 2;
                this.aud = SystemClock.elapsedRealtime() + ((long) this.auc);
                String str = "InstanceID/Rpc";
                Log.w(str, new StringBuilder(String.valueOf($r1).length() + 31).append("Backoff due to ").append($r1).append(" for ").append(this.auc).toString());
            }
        }
    }

    private void zzl(String $r1, Object $r2) throws  {
        synchronized (getClass()) {
            Object $r5 = this.atW.get($r1);
            this.atW.put($r1, $r2);
            zzh($r5, $r2);
        }
    }

    public void handleIncomingMessenger(Message $r1) throws  {
        if ($r1 != null) {
            if ($r1.obj instanceof Intent) {
                Intent $r3 = (Intent) $r1.obj;
                $r3.setExtrasClassLoader(MessengerCompat.class.getClassLoader());
                if ($r3.hasExtra("google.messenger")) {
                    Parcelable $r6 = $r3.getParcelableExtra("google.messenger");
                    if ($r6 instanceof MessengerCompat) {
                        this.atY = (MessengerCompat) $r6;
                    }
                    if ($r6 instanceof Messenger) {
                        this.atX = (Messenger) $r6;
                    }
                }
                zzaa((Intent) $r1.obj);
                return;
            }
            Log.w("InstanceID/Rpc", "Dropping invalid message");
        }
    }

    protected void sendRequest(Intent $r1, String $r2) throws  {
        Message $r7;
        this.atZ = SystemClock.elapsedRealtime();
        Intent intent = $r1;
        intent.putExtra("kid", new StringBuilder(String.valueOf($r2).length() + 5).append("|ID|").append($r2).append("|").toString());
        $r1.putExtra("X-kid", new StringBuilder(String.valueOf($r2).length() + 5).append("|ID|").append($r2).append("|").toString());
        boolean $z0 = "com.google.android.gsf".equals(atS);
        $r2 = $r1.getStringExtra("useGsf");
        if ($r2 != null) {
            $z0 = AppEventsConstants.EVENT_PARAM_VALUE_YES.equals($r2);
        }
        if (Log.isLoggable("InstanceID/Rpc", 3)) {
            $r2 = String.valueOf($r1.getExtras());
            Log.d("InstanceID/Rpc", new StringBuilder(String.valueOf($r2).length() + 8).append("Sending ").append($r2).toString());
        }
        if (this.atX != null) {
            intent = $r1;
            intent.putExtra("google.messenger", this.arc);
            $r7 = Message.obtain();
            $r7.obj = $r1;
            try {
                this.atX.send($r7);
                return;
            } catch (RemoteException e) {
                if (Log.isLoggable("InstanceID/Rpc", 3)) {
                    Log.d("InstanceID/Rpc", "Messenger failed, fallback to startService");
                }
            }
        }
        if ($z0) {
            intent = new Intent("com.google.android.gms.iid.InstanceID");
            Context $r10 = this.zzagf;
            intent.setPackage($r10.getPackageName());
            intent.putExtra("GSF", $r1);
            $r10 = this.zzagf;
            Context $r102 = $r10;
            $r10.startService(intent);
            return;
        }
        intent = $r1;
        intent.putExtra("google.messenger", this.arc);
        $r1.putExtra("messenger2", AppEventsConstants.EVENT_PARAM_VALUE_YES);
        if (this.atY != null) {
            $r7 = Message.obtain();
            $r7.obj = $r1;
            MessengerCompat $r11 = this.atY;
            try {
                $r11.send($r7);
                return;
            } catch (RemoteException e2) {
                if (Log.isLoggable("InstanceID/Rpc", 3)) {
                    Log.d("InstanceID/Rpc", "Messenger failed, fallback to startService");
                }
            }
        }
        $r10 = this.zzagf;
        $r102 = $r10;
        $r10.startService($r1);
    }

    Intent zza(Bundle $r1, KeyPair $r2) throws IOException {
        Intent $r3 = zzb($r1, $r2);
        return ($r3 == null || !$r3.hasExtra("google.messenger")) ? $r3 : zzb($r1, $r2);
    }

    void zza(Bundle $r1, KeyPair $r2, String $r3) throws IOException {
        long $l0 = SystemClock.elapsedRealtime();
        if (this.aud == 0 || $l0 > this.aud) {
            zzbrx();
            if (atS == null) {
                throw new IOException(InstanceID.ERROR_MISSING_INSTANCEID_SERVICE);
            }
            this.atZ = SystemClock.elapsedRealtime();
            Intent $r7 = new Intent("com.google.android.c2dm.intent.REGISTER");
            $r7.setPackage(atS);
            Context $r8 = this.zzagf;
            String $r6 = Integer.toString(zzdc($r8));
            $r1.putString(PARAM_GMS_VER, $r6);
            Bundle bundle = $r1;
            bundle.putString(PARAM_OS_VER, Integer.toString(VERSION.SDK_INT));
            $r8 = this.zzagf;
            $r6 = Integer.toString(InstanceID.zzcz($r8));
            $r1.putString(PARAM_APP_VER, $r6);
            $r8 = this.zzagf;
            $r6 = InstanceID.zzda($r8);
            $r1.putString(PARAM_APP_VER_NAME, $r6);
            $r1.putString(PARAM_CLIENT_VER, AppEventsConstants.EVENT_PARAM_VALUE_YES);
            bundle = $r1;
            bundle.putString("appid", InstanceID.zza($r2));
            $r6 = InstanceID.zzad($r2.getPublic().getEncoded());
            $r1.putString(PARAM_PUBLIC_KEY, $r6);
            String[] $r11 = new String[2];
            $r8 = this.zzagf;
            $r11[0] = $r8.getPackageName();
            $r11[1] = $r6;
            bundle = $r1;
            bundle.putString(PARAM_SIGNATURE, zza($r2, $r11));
            $r7.putExtras($r1);
            zzx($r7);
            sendRequest($r7, $r3);
            return;
        }
        $l0 = this.aud - $l0;
        Log.w("InstanceID/Rpc", "Backoff mode, next request attempt: " + $l0 + " interval: " + this.auc);
        throw new IOException(InstanceID.ERROR_BACKOFF);
    }

    public void zzaa(Intent $r1) throws  {
        if ($r1 != null) {
            String $r2 = $r1.getAction();
            String $r3;
            if ("com.google.android.c2dm.intent.REGISTRATION".equals($r2) || "com.google.android.gms.iid.InstanceID".equals($r2)) {
                $r2 = $r1.getStringExtra(GoogleCloudMessaging.REGISTRATION_ID);
                if ($r2 == null) {
                    $r2 = $r1.getStringExtra("unregistered");
                }
                if ($r2 == null) {
                    zzz($r1);
                    return;
                }
                this.atZ = SystemClock.elapsedRealtime();
                this.aud = 0;
                this.aub = 0;
                this.auc = 0;
                if (Log.isLoggable("InstanceID/Rpc", 3)) {
                    $r3 = String.valueOf($r1.getExtras());
                    Log.d("InstanceID/Rpc", new StringBuilder((String.valueOf($r2).length() + 16) + String.valueOf($r3).length()).append("AppIDResponse: ").append($r2).append(" ").append($r3).toString());
                }
                $r3 = null;
                if ($r2.startsWith("|")) {
                    String[] $r7 = $r2.split("\\|");
                    if (!AnalyticsEvents.ANALYTICS_EVENT_INFO_ID.equals($r7[1])) {
                        $r3 = "Unexpected structured response ";
                        $r2 = String.valueOf($r2);
                        Log.w("InstanceID/Rpc", $r2.length() != 0 ? $r3.concat($r2) : new String("Unexpected structured response "));
                    }
                    $r2 = $r7[2];
                    if ($r7.length > 4) {
                        Context $r8;
                        if ("SYNC".equals($r7[3])) {
                            $r8 = this.zzagf;
                            InstanceIDListenerService.zzdb($r8);
                        } else if ("RST".equals($r7[3])) {
                            Context $r82 = this.zzagf;
                            $r8 = this.zzagf;
                            InstanceIDListenerService.zza($r82, InstanceID.getInstance($r8).zzbru());
                            $r1.removeExtra(GoogleCloudMessaging.REGISTRATION_ID);
                            zzl($r2, $r1);
                            return;
                        }
                    }
                    $r3 = $r7[$r7.length - 1];
                    if ($r3.startsWith(":")) {
                        $r3 = $r3.substring(1);
                    }
                    $r1.putExtra(GoogleCloudMessaging.REGISTRATION_ID, $r3);
                    $r3 = $r2;
                }
                if ($r3 == null) {
                    zzap($r1);
                } else {
                    zzl($r3, $r1);
                }
            } else if (Log.isLoggable("InstanceID/Rpc", 3)) {
                $r2 = "Unexpected response ";
                $r3 = String.valueOf($r1.getAction());
                Log.d("InstanceID/Rpc", $r3.length() != 0 ? $r2.concat($r3) : new String("Unexpected response "));
            }
        } else if (Log.isLoggable("InstanceID/Rpc", 3)) {
            Log.d("InstanceID/Rpc", "Unexpected response: null");
        }
    }

    void zzbrx() throws  {
        if (this.arc == null) {
            findAppIDPackage(this.zzagf);
            this.arc = new Messenger(new Handler(this, Looper.getMainLooper()) {
                final /* synthetic */ Rpc aue;

                public void handleMessage(Message $r1) throws  {
                    this.aue.handleIncomingMessenger($r1);
                }
            });
        }
    }

    synchronized void zzx(Intent $r1) throws  {
        if (this.aqY == null) {
            Intent $r3 = new Intent();
            $r3.setPackage("com.google.example.invalidpackage");
            this.aqY = PendingIntent.getBroadcast(this.zzagf, 0, $r3, 0);
        }
        $r1.putExtra("app", this.aqY);
    }

    String zzy(Intent $r1) throws IOException {
        if ($r1 == null) {
            throw new IOException("SERVICE_NOT_AVAILABLE");
        }
        String $r3 = $r1.getStringExtra(GoogleCloudMessaging.REGISTRATION_ID);
        String $r4 = $r3;
        if ($r3 == null) {
            $r4 = $r1.getStringExtra("unregistered");
        }
        $r1.getLongExtra("Retry-After", 0);
        if ($r4 != null) {
            if ($r4 == null) {
                return $r4;
            }
            $r3 = $r1.getStringExtra("error");
            if ($r3 == null) {
                throw new IOException($r3);
            }
            $r3 = String.valueOf($r1.getExtras());
            Log.w("InstanceID/Rpc", new StringBuilder(String.valueOf($r3).length() + 29).append("Unexpected response from GCM ").append($r3).toString(), new Throwable());
            throw new IOException("SERVICE_NOT_AVAILABLE");
        } else if ($r4 == null) {
            return $r4;
        } else {
            $r3 = $r1.getStringExtra("error");
            if ($r3 == null) {
                $r3 = String.valueOf($r1.getExtras());
                Log.w("InstanceID/Rpc", new StringBuilder(String.valueOf($r3).length() + 29).append("Unexpected response from GCM ").append($r3).toString(), new Throwable());
                throw new IOException("SERVICE_NOT_AVAILABLE");
            }
            throw new IOException($r3);
        }
    }

    void zzz(Intent $r1) throws  {
        String $r3 = $r1.getStringExtra("error");
        if ($r3 == null) {
            $r3 = String.valueOf($r1.getExtras());
            Log.w("InstanceID/Rpc", new StringBuilder(String.valueOf($r3).length() + 49).append("Unexpected response, no error or registration id ").append($r3).toString());
            return;
        }
        if (Log.isLoggable("InstanceID/Rpc", 3)) {
            String $r2 = "Received InstanceID error ";
            String $r6 = String.valueOf($r3);
            Log.d("InstanceID/Rpc", $r6.length() != 0 ? $r2.concat($r6) : new String("Received InstanceID error "));
        }
        if ($r3.startsWith("|")) {
            String[] $r7 = $r3.split("\\|");
            if (!AnalyticsEvents.ANALYTICS_EVENT_INFO_ID.equals($r7[1])) {
                $r2 = "Unexpected structured response ";
                $r3 = String.valueOf($r3);
                Log.w("InstanceID/Rpc", $r3.length() != 0 ? $r2.concat($r3) : new String("Unexpected structured response "));
            }
            if ($r7.length > 2) {
                $r2 = $r7[2];
                $r3 = $r7[3];
                if ($r3.startsWith(":")) {
                    $r3 = $r3.substring(1);
                }
            } else {
                $r3 = "UNKNOWN";
                $r2 = null;
            }
            $r1.putExtra("error", $r3);
        } else {
            $r2 = null;
        }
        if ($r2 == null) {
            zzap($r3);
        } else {
            zzl($r2, $r3);
        }
        long $l1 = $r1.getLongExtra("Retry-After", 0);
        if ($l1 > 0) {
            this.aua = SystemClock.elapsedRealtime();
            this.auc = ((int) $l1) * 1000;
            long $l3 = (long) this.auc;
            this.aud = SystemClock.elapsedRealtime() + $l3;
            Log.w("InstanceID/Rpc", "Explicit request from server to backoff: " + this.auc);
        } else if ("SERVICE_NOT_AVAILABLE".equals($r3) || "AUTHENTICATION_FAILED".equals($r3)) {
            zzjz($r3);
        }
    }
}
