package com.google.android.gms.gcm;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.KeyguardManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Process;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat.Builder;
import android.text.TextUtils;
import android.util.Log;
import com.facebook.appevents.AppEventsConstants;
import com.google.android.gms.auth.firstparty.recovery.RecoveryParamConstants;
import com.waze.ResManager;
import java.util.Iterator;
import java.util.List;

/* compiled from: dalvik_source_com.waze.apk */
class zza {
    static zza aqL;
    private final Context mContext;

    /* compiled from: dalvik_source_com.waze.apk */
    private class zza extends IllegalArgumentException {
    }

    private zza(Context $r1) throws  {
        this.mContext = $r1.getApplicationContext();
    }

    private void zza(String $r2, Notification $r1) throws  {
        if (Log.isLoggable("GcmNotification", 3)) {
            Log.d("GcmNotification", "Showing notification");
        }
        NotificationManager $r5 = (NotificationManager) this.mContext.getSystemService("notification");
        if (TextUtils.isEmpty($r2)) {
            $r2 = "GCM-Notification:" + SystemClock.uptimeMillis();
        }
        $r5.notify($r2, 0, $r1);
    }

    static boolean zzai(Bundle $r0) throws  {
        return AppEventsConstants.EVENT_PARAM_VALUE_YES.equals(zzf($r0, "gcm.n.e")) || zzf($r0, "gcm.n.icon") != null;
    }

    static void zzaj(Bundle $r0) throws  {
        String $r6;
        Bundle $r1 = new Bundle();
        Iterator $r3 = $r0.keySet().iterator();
        while ($r3.hasNext()) {
            String $r5 = (String) $r3.next();
            $r6 = $r0.getString($r5);
            if ($r5.startsWith("gcm.notification.")) {
                $r5 = $r5.replace("gcm.notification.", "gcm.n.");
            }
            if ($r5.startsWith("gcm.n.")) {
                if (!"gcm.n.e".equals($r5)) {
                    $r1.putString($r5.substring("gcm.n.".length()), $r6);
                }
                $r3.remove();
            }
        }
        $r6 = $r1.getString("sound2");
        if ($r6 != null) {
            $r1.remove("sound2");
            $r1.putString(ResManager.mSoundDir, $r6);
        }
        if (!$r1.isEmpty()) {
            $r0.putBundle("notification", $r1);
        }
    }

    private Notification zzal(Bundle $r1) throws  {
        String $r2 = zzg($r1, "gcm.n.title");
        String $r3 = zzg($r1, "gcm.n.body");
        int $i0 = zzjq(zzf($r1, "gcm.n.icon"));
        String $r4 = zzf($r1, "gcm.n.color");
        Uri $r6 = zzjr(zzf($r1, "gcm.n.sound2"));
        PendingIntent $r7 = zzam($r1);
        Builder $r8 = new Builder(this.mContext).setAutoCancel(true).setSmallIcon($i0);
        if (TextUtils.isEmpty($r2)) {
            $r8.setContentTitle(this.mContext.getApplicationInfo().loadLabel(this.mContext.getPackageManager()));
        } else {
            $r8.setContentTitle($r2);
        }
        if (!TextUtils.isEmpty($r3)) {
            $r8.setContentText($r3);
        }
        if (!TextUtils.isEmpty($r4)) {
            $r8.setColor(Color.parseColor($r4));
        }
        if ($r6 != null) {
            $r8.setSound($r6);
        }
        if ($r7 != null) {
            $r8.setContentIntent($r7);
        }
        return $r8.build();
    }

    private PendingIntent zzam(Bundle $r1) throws  {
        Intent $r3;
        String $r2 = zzf($r1, "gcm.n.click_action");
        if (TextUtils.isEmpty($r2)) {
            $r3 = this.mContext.getPackageManager().getLaunchIntentForPackage(this.mContext.getPackageName());
            if ($r3 == null) {
                Log.w("GcmNotification", "No activity found to launch app");
                return null;
            }
        } else {
            $r3 = new Intent($r2);
            $r3.setPackage(this.mContext.getPackageName());
            $r3.setFlags(268435456);
        }
        Bundle $r5 = new Bundle($r1);
        GcmListenerService.zzah($r5);
        $r3.putExtras($r5);
        for (String $r22 : $r5.keySet()) {
            if ($r22.startsWith("gcm.n.") || $r22.startsWith("gcm.notification.")) {
                $r3.removeExtra($r22);
            }
        }
        return PendingIntent.getActivity(this.mContext, zzbqv(), $r3, 1073741824);
    }

    private int zzbqv() throws  {
        return (int) SystemClock.uptimeMillis();
    }

    static synchronized zza zzcw(Context $r0) throws  {
        Class cls = zza.class;
        synchronized (cls) {
            try {
                if (aqL == null) {
                    aqL = new zza($r0);
                }
                zza $r1 = aqL;
                return $r1;
            } finally {
                cls = zza.class;
            }
        }
    }

    static boolean zzcx(Context $r0) throws  {
        if (((KeyguardManager) $r0.getSystemService("keyguard")).inKeyguardRestrictedInputMode()) {
            return false;
        }
        int $i1 = Process.myPid();
        List<RunningAppProcessInfo> $r4 = ((ActivityManager) $r0.getSystemService(RecoveryParamConstants.VALUE_ACTIVITY)).getRunningAppProcesses();
        if ($r4 == null) {
            return false;
        }
        for (RunningAppProcessInfo $r6 : $r4) {
            if ($r6.pid == $i1) {
                return $r6.importance == 100;
            }
        }
        return false;
    }

    static String zzf(Bundle $r0, String $r1) throws  {
        String $r2 = $r0.getString($r1);
        return $r2 == null ? $r0.getString($r1.replace("gcm.n.", "gcm.notification.")) : $r2;
    }

    private java.lang.String zzg(android.os.Bundle r20, java.lang.String r21) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x0100 in list []
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:42)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:60)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r19 = this;
        r0 = r20;
        r1 = r21;
        r2 = zzf(r0, r1);
        r3 = android.text.TextUtils.isEmpty(r2);
        if (r3 != 0) goto L_0x000f;
    L_0x000e:
        return r2;
    L_0x000f:
        r0 = r21;
        r2 = java.lang.String.valueOf(r0);
        r5 = "_loc_key";
        r4 = java.lang.String.valueOf(r5);
        r6 = r4.length();
        if (r6 == 0) goto L_0x0033;
    L_0x0021:
        r4 = r2.concat(r4);
    L_0x0025:
        r0 = r20;
        r2 = zzf(r0, r4);
        r3 = android.text.TextUtils.isEmpty(r2);
        if (r3 == 0) goto L_0x0039;
    L_0x0031:
        r7 = 0;
        return r7;
    L_0x0033:
        r4 = new java.lang.String;
        r4.<init>(r2);
        goto L_0x0025;
    L_0x0039:
        r0 = r19;
        r8 = r0.mContext;
        r9 = r8.getResources();
        r0 = r19;
        r8 = r0.mContext;
        r4 = r8.getPackageName();
        r5 = "string";
        r6 = r9.getIdentifier(r2, r5, r4);
        if (r6 != 0) goto L_0x00bb;
    L_0x0052:
        r0 = r21;
        r21 = java.lang.String.valueOf(r0);
        r5 = "_loc_key";
        r4 = java.lang.String.valueOf(r5);
        r6 = r4.length();
        if (r6 == 0) goto L_0x00b3;
    L_0x0064:
        r0 = r21;
        r4 = r0.concat(r4);
    L_0x006a:
        r0 = r19;
        r21 = r0.zzjp(r4);
        r0 = r21;
        r21 = java.lang.String.valueOf(r0);
        r10 = new java.lang.StringBuilder;
        r0 = r21;
        r4 = java.lang.String.valueOf(r0);
        r6 = r4.length();
        r6 = r6 + 49;
        r4 = java.lang.String.valueOf(r2);
        r11 = r4.length();
        r6 = r6 + r11;
        r10.<init>(r6);
        r0 = r21;
        r10 = r10.append(r0);
        r5 = " resource not found: ";
        r10 = r10.append(r5);
        r10 = r10.append(r2);
        r5 = " Default value will be used.";
        r10 = r10.append(r5);
        r21 = r10.toString();
        r5 = "GcmNotification";
        r0 = r21;
        android.util.Log.w(r5, r0);
        r7 = 0;
        return r7;
    L_0x00b3:
        r4 = new java.lang.String;
        r0 = r21;
        r4.<init>(r0);
        goto L_0x006a;
    L_0x00bb:
        r0 = r21;
        r4 = java.lang.String.valueOf(r0);
        r5 = "_loc_args";
        r12 = java.lang.String.valueOf(r5);
        r11 = r12.length();
        if (r11 == 0) goto L_0x00e2;
    L_0x00cd:
        r12 = r4.concat(r12);
    L_0x00d1:
        r0 = r20;
        r4 = zzf(r0, r12);
        r3 = android.text.TextUtils.isEmpty(r4);
        if (r3 == 0) goto L_0x00e8;
    L_0x00dd:
        r21 = r9.getString(r6);
        return r21;
    L_0x00e2:
        r12 = new java.lang.String;
        r12.<init>(r4);
        goto L_0x00d1;
    L_0x00e8:
        r13 = new org.json.JSONArray;
        r13.<init>(r4);	 Catch:{ JSONException -> 0x0105, MissingFormatArgumentException -> 0x0175 }
        r11 = r13.length();	 Catch:{ JSONException -> 0x0105, MissingFormatArgumentException -> 0x0175 }
        r14 = new java.lang.String[r11];
        r11 = 0;
    L_0x00f4:
        r15 = r14.length;
        if (r11 >= r15) goto L_0x0100;	 Catch:{ JSONException -> 0x0105, MissingFormatArgumentException -> 0x0175 }
    L_0x00f7:
        r16 = r13.opt(r11);	 Catch:{ JSONException -> 0x0105, MissingFormatArgumentException -> 0x0175 }
        r14[r11] = r16;
        r11 = r11 + 1;
        goto L_0x00f4;
    L_0x0100:
        r21 = r9.getString(r6, r14);	 Catch:{ JSONException -> 0x0105, MissingFormatArgumentException -> 0x0175 }
        return r21;
    L_0x0105:
        r17 = move-exception;
        r0 = r21;
        r21 = java.lang.String.valueOf(r0);
        r5 = "_loc_args";
        r2 = java.lang.String.valueOf(r5);
        r6 = r2.length();
        if (r6 == 0) goto L_0x016d;
    L_0x0118:
        r0 = r21;
        r2 = r0.concat(r2);
    L_0x011e:
        r0 = r19;
        r21 = r0.zzjp(r2);
        r0 = r21;
        r21 = java.lang.String.valueOf(r0);
        r10 = new java.lang.StringBuilder;
        r0 = r21;
        r2 = java.lang.String.valueOf(r0);
        r6 = r2.length();
        r6 = r6 + 41;
        r2 = java.lang.String.valueOf(r4);
        r11 = r2.length();
        r6 = r6 + r11;
        r10.<init>(r6);
        r5 = "Malformed ";
        r10 = r10.append(r5);
        r0 = r21;
        r10 = r10.append(r0);
        r5 = ": ";
        r10 = r10.append(r5);
        r10 = r10.append(r4);
        r5 = "  Default value will be used.";
        r10 = r10.append(r5);
        r21 = r10.toString();
        r5 = "GcmNotification";
        r0 = r21;
        android.util.Log.w(r5, r0);
    L_0x016b:
        r7 = 0;
        return r7;
    L_0x016d:
        r2 = new java.lang.String;
        r0 = r21;
        r2.<init>(r0);
        goto L_0x011e;
    L_0x0175:
        r18 = move-exception;
        r10 = new java.lang.StringBuilder;
        r21 = java.lang.String.valueOf(r2);
        r0 = r21;
        r6 = r0.length();
        r6 = r6 + 58;
        r21 = java.lang.String.valueOf(r4);
        r0 = r21;
        r11 = r0.length();
        r6 = r6 + r11;
        r10.<init>(r6);
        r5 = "Missing format argument for ";
        r10 = r10.append(r5);
        r10 = r10.append(r2);
        r5 = ": ";
        r10 = r10.append(r5);
        r10 = r10.append(r4);
        r5 = " Default value will be used.";
        r10 = r10.append(r5);
        r21 = r10.toString();
        r5 = "GcmNotification";
        r0 = r21;
        r1 = r18;
        android.util.Log.w(r5, r0, r1);
        goto L_0x016b;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.gcm.zza.zzg(android.os.Bundle, java.lang.String):java.lang.String");
    }

    private String zzjp(String $r1) throws  {
        return $r1.substring("gcm.n.".length());
    }

    private int zzjq(String $r1) throws  {
        int $i0;
        if (!TextUtils.isEmpty($r1)) {
            Resources $r3 = this.mContext.getResources();
            $i0 = $r3.getIdentifier($r1, "drawable", this.mContext.getPackageName());
            if ($i0 != 0) {
                return $i0;
            }
            $i0 = $r3.getIdentifier($r1, "mipmap", this.mContext.getPackageName());
            if ($i0 != 0) {
                return $i0;
            }
            Log.w("GcmNotification", new StringBuilder(String.valueOf($r1).length() + 57).append("Icon resource ").append($r1).append(" not found. Notification will use app icon.").toString());
        }
        $i0 = this.mContext.getApplicationInfo().icon;
        return $i0 == 0 ? 17301651 : $i0;
    }

    private Uri zzjr(String $r1) throws  {
        if (TextUtils.isEmpty($r1)) {
            return null;
        }
        if (!"default".equals($r1)) {
            if (this.mContext.getResources().getIdentifier($r1, "raw", this.mContext.getPackageName()) != 0) {
                String $r2 = String.valueOf("android.resource://");
                String $r5 = String.valueOf(this.mContext.getPackageName());
                return Uri.parse(new StringBuilder(((String.valueOf($r2).length() + 5) + String.valueOf($r5).length()) + String.valueOf($r1).length()).append($r2).append($r5).append("/raw/").append($r1).toString());
            }
        }
        return RingtoneManager.getDefaultUri(2);
    }

    boolean zzak(Bundle $r1) throws  {
        try {
            zza(zzf($r1, "gcm.n.tag"), zzal($r1));
            return true;
        } catch (zza $r4) {
            String $r2 = "Failed to show notification: ";
            String $r5 = String.valueOf($r4.getMessage());
            Log.e("GcmNotification", $r5.length() != 0 ? $r2.concat($r5) : new String("Failed to show notification: "));
            return false;
        }
    }
}
