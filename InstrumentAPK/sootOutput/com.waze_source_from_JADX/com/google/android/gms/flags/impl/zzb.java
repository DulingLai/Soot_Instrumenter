package com.google.android.gms.flags.impl;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.concurrent.Callable;

/* compiled from: dalvik_source_com.waze.apk */
public class zzb {
    private static SharedPreferences agb = null;

    /* compiled from: dalvik_source_com.waze.apk */
    class C07341 implements Callable<SharedPreferences> {
        final /* synthetic */ Context zzals;

        C07341(Context $r1) throws  {
            this.zzals = $r1;
        }

        public /* synthetic */ Object call() throws Exception {
            return zzblj();
        }

        public SharedPreferences zzblj() throws  {
            return this.zzals.getSharedPreferences("google_sdk_flags", 1);
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static android.content.SharedPreferences zzn(android.content.Context r6) throws  {
        /*
        r0 = android.content.SharedPreferences.class;
        monitor-enter(r0);
        r1 = agb;	 Catch:{ Throwable -> 0x001c }
        if (r1 != 0) goto L_0x0016;
    L_0x0007:
        r2 = new com.google.android.gms.flags.impl.zzb$1;	 Catch:{ Throwable -> 0x001c }
        r2.<init>(r6);	 Catch:{ Throwable -> 0x001c }
        r3 = com.google.android.gms.internal.zzwj.zzb(r2);	 Catch:{ Throwable -> 0x001c }
        r4 = r3;
        r4 = (android.content.SharedPreferences) r4;	 Catch:{ Throwable -> 0x001c }
        r1 = r4;
        agb = r1;	 Catch:{ Throwable -> 0x001c }
    L_0x0016:
        r1 = agb;	 Catch:{ Throwable -> 0x001c }
        r0 = android.content.SharedPreferences.class;
        monitor-exit(r0);	 Catch:{ Throwable -> 0x001c }
        return r1;
    L_0x001c:
        r5 = move-exception;
        r0 = android.content.SharedPreferences.class;
        monitor-exit(r0);	 Catch:{ Throwable -> 0x001c }
        throw r5;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.flags.impl.zzb.zzn(android.content.Context):android.content.SharedPreferences");
    }
}
