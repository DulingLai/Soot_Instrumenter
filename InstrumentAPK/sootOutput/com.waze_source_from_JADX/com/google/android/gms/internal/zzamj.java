package com.google.android.gms.internal;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import dalvik.annotation.Signature;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.regex.Pattern;

/* compiled from: dalvik_source_com.waze.apk */
public class zzamj {
    public static final Uri CONTENT_URI = Uri.parse("content://com.google.android.gsf.gservices");
    public static final Uri bEN = Uri.parse("content://com.google.android.gsf.gservices/prefix");
    public static final Pattern bEO = Pattern.compile("^(1|true|t|on|yes|y)$", 2);
    public static final Pattern bEP = Pattern.compile("^(0|false|f|off|no|n)$", 2);
    private static HashMap<String, String> bEQ;
    private static Object bER;
    private static String[] bES = new String[0];
    private static Context bET = null;

    /* compiled from: dalvik_source_com.waze.apk */
    class C07621 extends Thread {
        final /* synthetic */ ContentResolver bEU;

        C07621(String $r1, ContentResolver $r2) throws  {
            this.bEU = $r2;
            super($r1);
        }

        public void run() throws  {
            Looper.prepare();
            this.bEU.registerContentObserver(zzamj.CONTENT_URI, true, new ContentObserver(this, new Handler(Looper.myLooper())) {
                final /* synthetic */ C07621 bEV;

                /* JADX WARNING: inconsistent code. */
                /* Code decompiled incorrectly, please refer to instructions dump. */
                public void onChange(boolean r9) throws  {
                    /*
                    r8 = this;
                    r0 = com.google.android.gms.internal.zzamj.class;
                    monitor-enter(r0);
                    r1 = com.google.android.gms.internal.zzamj.bEQ;	 Catch:{ Throwable -> 0x0028 }
                    r1.clear();	 Catch:{ Throwable -> 0x0028 }
                    r2 = new java.lang.Object;	 Catch:{ Throwable -> 0x0028 }
                    r2.<init>();	 Catch:{ Throwable -> 0x0028 }
                    com.google.android.gms.internal.zzamj.bER = r2;	 Catch:{ Throwable -> 0x0028 }
                    r3 = com.google.android.gms.internal.zzamj.bES;	 Catch:{ Throwable -> 0x0028 }
                    r4 = r3.length;	 Catch:{ Throwable -> 0x0028 }
                    if (r4 <= 0) goto L_0x0024;
                L_0x0019:
                    r5 = r8.bEV;	 Catch:{ Throwable -> 0x0028 }
                    r6 = r5.bEU;	 Catch:{ Throwable -> 0x0028 }
                    r3 = com.google.android.gms.internal.zzamj.bES;	 Catch:{ Throwable -> 0x0028 }
                    com.google.android.gms.internal.zzamj.zzb(r6, r3);	 Catch:{ Throwable -> 0x0028 }
                L_0x0024:
                    r0 = com.google.android.gms.internal.zzamj.class;
                    monitor-exit(r0);	 Catch:{ Throwable -> 0x0028 }
                    return;
                L_0x0028:
                    r7 = move-exception;
                    r0 = com.google.android.gms.internal.zzamj.class;
                    monitor-exit(r0);	 Catch:{ Throwable -> 0x0028 }
                    throw r7;
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzamj.1.1.onChange(boolean):void");
                }
            });
            Looper.loop();
        }
    }

    public static int getInt(ContentResolver $r0, String $r1, int $i0) throws  {
        $r1 = getString($r0, $r1);
        if ($r1 == null) {
            return $i0;
        }
        try {
            $i0 = Integer.parseInt($r1);
            return $i0;
        } catch (NumberFormatException e) {
            return $i0;
        }
    }

    public static long getLong(ContentResolver $r0, String $r1, long $l0) throws  {
        $r1 = getString($r0, $r1);
        if ($r1 == null) {
            return $l0;
        }
        try {
            $l0 = Long.parseLong($r1);
            return $l0;
        } catch (NumberFormatException e) {
            return $l0;
        }
    }

    public static String getString(ContentResolver $r0, String $r1) throws  {
        return zza($r0, $r1, null);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String zza(android.content.ContentResolver r25, java.lang.String r26, java.lang.String r27) throws  {
        /*
        r6 = com.google.android.gms.internal.zzamj.class;
        monitor-enter(r6);
        r0 = r25;
        zza(r0);	 Catch:{ Throwable -> 0x003f }
        r7 = bER;	 Catch:{ Throwable -> 0x003f }
        r8 = bEQ;	 Catch:{ Throwable -> 0x003f }
        r0 = r26;
        r9 = r8.containsKey(r0);	 Catch:{ Throwable -> 0x003f }
        if (r9 == 0) goto L_0x0029;
    L_0x0014:
        r8 = bEQ;	 Catch:{ Throwable -> 0x003f }
        r0 = r26;
        r7 = r8.get(r0);	 Catch:{ Throwable -> 0x003f }
        r10 = r7;
        r10 = (java.lang.String) r10;	 Catch:{ Throwable -> 0x003f }
        r26 = r10;
        if (r26 == 0) goto L_0x0025;
    L_0x0023:
        r27 = r26;
    L_0x0025:
        r6 = com.google.android.gms.internal.zzamj.class;
        monitor-exit(r6);	 Catch:{ Throwable -> 0x003f }
        return r27;
    L_0x0029:
        r6 = com.google.android.gms.internal.zzamj.class;
        monitor-exit(r6);	 Catch:{ Throwable -> 0x003f }
        r11 = bES;
        r12 = r11.length;
        r13 = 0;
    L_0x0030:
        if (r13 >= r12) goto L_0x0044;
    L_0x0032:
        r14 = r11[r13];
        r0 = r26;
        r9 = r0.startsWith(r14);
        if (r9 != 0) goto L_0x00b9;
    L_0x003c:
        r13 = r13 + 1;
        goto L_0x0030;
    L_0x003f:
        r15 = move-exception;
        r6 = com.google.android.gms.internal.zzamj.class;
        monitor-exit(r6);	 Catch:{ Throwable -> 0x003f }
        throw r15;
    L_0x0044:
        r16 = CONTENT_URI;
        r17 = 1;
        r0 = r17;
        r11 = new java.lang.String[r0];
        r17 = 0;
        r11[r17] = r26;
        r19 = 0;
        r20 = 0;
        r21 = 0;
        r0 = r25;
        r1 = r16;
        r2 = r19;
        r3 = r20;
        r4 = r11;
        r5 = r21;
        r18 = r0.query(r1, r2, r3, r4, r5);
        if (r18 == 0) goto L_0x006f;
    L_0x0067:
        r0 = r18;
        r9 = r0.moveToFirst();	 Catch:{ Throwable -> 0x00b0 }
        if (r9 != 0) goto L_0x0082;
    L_0x006f:
        r8 = bEQ;	 Catch:{ Throwable -> 0x00b0 }
        r19 = 0;
        r0 = r26;
        r1 = r19;
        r8.put(r0, r1);	 Catch:{ Throwable -> 0x00b0 }
        if (r18 == 0) goto L_0x00ba;
    L_0x007c:
        r0 = r18;
        r0.close();
        return r27;
    L_0x0082:
        r17 = 1;
        r0 = r18;
        r1 = r17;
        r14 = r0.getString(r1);	 Catch:{ Throwable -> 0x00b0 }
        r6 = com.google.android.gms.internal.zzamj.class;
        monitor-enter(r6);	 Catch:{ Throwable -> 0x00b0 }
        r22 = bER;	 Catch:{ Throwable -> 0x00ab }
        r0 = r22;
        if (r7 != r0) goto L_0x009c;
    L_0x0095:
        r8 = bEQ;	 Catch:{ Throwable -> 0x00ab }
        r0 = r26;
        r8.put(r0, r14);	 Catch:{ Throwable -> 0x00ab }
    L_0x009c:
        r6 = com.google.android.gms.internal.zzamj.class;
        monitor-exit(r6);	 Catch:{ Throwable -> 0x00ab }
        if (r14 == 0) goto L_0x00a3;
    L_0x00a1:
        r27 = r14;
    L_0x00a3:
        if (r18 == 0) goto L_0x00bb;
    L_0x00a5:
        r0 = r18;
        r0.close();
        return r27;
    L_0x00ab:
        r23 = move-exception;
        r6 = com.google.android.gms.internal.zzamj.class;
        monitor-exit(r6);	 Catch:{ Throwable -> 0x00ab }
        throw r23;	 Catch:{ Throwable -> 0x00b0 }
    L_0x00b0:
        r24 = move-exception;
        if (r18 == 0) goto L_0x00b8;
    L_0x00b3:
        r0 = r18;
        r0.close();
    L_0x00b8:
        throw r24;
    L_0x00b9:
        return r27;
    L_0x00ba:
        return r27;
    L_0x00bb:
        return r27;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzamj.zza(android.content.ContentResolver, java.lang.String, java.lang.String):java.lang.String");
    }

    public static Map<String, String> zza(@Signature({"(", "Landroid/content/ContentResolver;", "[", "Ljava/lang/String;", ")", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;"}) ContentResolver $r0, @Signature({"(", "Landroid/content/ContentResolver;", "[", "Ljava/lang/String;", ")", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;"}) String... $r1) throws  {
        Cursor $r3 = $r0.query(bEN, null, null, $r1, null);
        TreeMap $r4 = new TreeMap();
        if ($r3 == null) {
            return $r4;
        }
        while ($r3.moveToNext()) {
            try {
                $r4.put($r3.getString(0), $r3.getString(1));
            } finally {
                $r3.close();
            }
        }
        return $r4;
    }

    private static void zza(ContentResolver $r0) throws  {
        if (bEQ == null) {
            bEQ = new HashMap();
            bER = new Object();
            new C07621("Gservices", $r0).start();
        }
    }

    public static boolean zza(ContentResolver $r0, String $r1, boolean $z0) throws  {
        String $r2 = getString($r0, $r1);
        if ($r2 == null || $r2.equals("")) {
            return $z0;
        }
        if (bEO.matcher($r2).matches()) {
            return true;
        }
        if (bEP.matcher($r2).matches()) {
            return false;
        }
        Log.w("Gservices", new StringBuilder((String.valueOf($r1).length() + 52) + String.valueOf($r2).length()).append("attempt to read gservices key ").append($r1).append(" (value \"").append($r2).append("\") as boolean").toString());
        return $z0;
    }

    public static void zzb(ContentResolver $r0, String... $r1) throws  {
        Map $r3 = zza($r0, $r1);
        Class cls = zzamj.class;
        synchronized (cls) {
            try {
                zza($r0);
                bES = $r1;
                for (Entry $r7 : $r3.entrySet()) {
                    bEQ.put((String) $r7.getKey(), (String) $r7.getValue());
                }
            } finally {
                cls = zzamj.class;
            }
        }
    }
}
