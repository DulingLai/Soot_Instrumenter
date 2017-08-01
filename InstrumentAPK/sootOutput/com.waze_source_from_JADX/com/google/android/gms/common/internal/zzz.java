package com.google.android.gms.common.internal;

import android.content.Context;

/* compiled from: dalvik_source_com.waze.apk */
public class zzz {
    private static String Kd;
    private static int Ke;
    private static Object zzanj = new Object();
    private static boolean zzbyo;

    public static String zzca(Context $r0) throws  {
        zzcc($r0);
        return Kd;
    }

    public static int zzcb(Context $r0) throws  {
        zzcc($r0);
        return Ke;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void zzcc(android.content.Context r12) throws  {
        /*
        r0 = zzanj;
        monitor-enter(r0);
        r1 = zzbyo;	 Catch:{ Throwable -> 0x0020 }
        if (r1 == 0) goto L_0x0009;
    L_0x0007:
        monitor-exit(r0);	 Catch:{ Throwable -> 0x0020 }
        return;
    L_0x0009:
        r2 = 1;
        zzbyo = r2;	 Catch:{ Throwable -> 0x0020 }
        r3 = r12.getPackageName();	 Catch:{ Throwable -> 0x0020 }
        r4 = com.google.android.gms.internal.zztc.zzcl(r12);	 Catch:{ Throwable -> 0x0020 }
        r2 = 128; // 0x80 float:1.794E-43 double:6.32E-322;
        r5 = r4.getApplicationInfo(r3, r2);	 Catch:{ NameNotFoundException -> 0x0035 }
        r6 = r5.metaData;	 Catch:{ Throwable -> 0x0020 }
        if (r6 != 0) goto L_0x0023;
    L_0x001e:
        monitor-exit(r0);	 Catch:{ Throwable -> 0x0020 }
        return;
    L_0x0020:
        r7 = move-exception;
        monitor-exit(r0);	 Catch:{ Throwable -> 0x0020 }
        throw r7;
    L_0x0023:
        r8 = "com.google.app.id";
        r3 = r6.getString(r8);	 Catch:{ NameNotFoundException -> 0x0035 }
        Kd = r3;	 Catch:{ NameNotFoundException -> 0x0035 }
        r8 = "com.google.android.gms.version";
        r9 = r6.getInt(r8);	 Catch:{ NameNotFoundException -> 0x0035 }
        Ke = r9;	 Catch:{ Throwable -> 0x0020 }
    L_0x0033:
        monitor-exit(r0);	 Catch:{ Throwable -> 0x0020 }
        return;
    L_0x0035:
        r10 = move-exception;
        r8 = "MetadataValueReader";
        r11 = "This should never happen.";
        android.util.Log.wtf(r8, r11, r10);	 Catch:{ Throwable -> 0x0020 }
        goto L_0x0033;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.internal.zzz.zzcc(android.content.Context):void");
    }
}
