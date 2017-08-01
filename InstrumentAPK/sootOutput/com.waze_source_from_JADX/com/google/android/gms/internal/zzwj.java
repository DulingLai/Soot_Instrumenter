package com.google.android.gms.internal;

import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy;
import dalvik.annotation.Signature;
import java.util.concurrent.Callable;

/* compiled from: dalvik_source_com.waze.apk */
public class zzwj {
    public static <T> T zzb(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Ljava/util/concurrent/Callable", "<TT;>;)TT;"}) Callable<T> $r0) throws  {
        ThreadPolicy $r1 = StrictMode.getThreadPolicy();
        try {
            StrictMode.setThreadPolicy(ThreadPolicy.LAX);
            Object $r3 = $r0.call();
            StrictMode.setThreadPolicy($r1);
            return $r3;
        } catch (Throwable th) {
            StrictMode.setThreadPolicy($r1);
            return null;
        }
    }
}
