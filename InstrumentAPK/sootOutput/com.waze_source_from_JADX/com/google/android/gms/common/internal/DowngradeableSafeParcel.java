package com.google.android.gms.common.internal;

import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import dalvik.annotation.Signature;

/* compiled from: dalvik_source_com.waze.apk */
public abstract class DowngradeableSafeParcel extends AbstractSafeParcelable {
    private static final Object Jq = new Object();
    private static ClassLoader Jr = null;
    private static Integer Js = null;
    private boolean Jt = false;

    protected static ClassLoader zzawp() throws  {
        synchronized (Jq) {
        }
        return null;
    }

    protected static Integer zzawq() throws  {
        synchronized (Jq) {
        }
        return null;
    }

    private static boolean zzd(@Signature({"(", "Ljava/lang/Class", "<*>;)Z"}) Class<?> $r0) throws  {
        try {
            return SafeParcelable.NULL.equals($r0.getField("NULL").get(null));
        } catch (NoSuchFieldException e) {
            return false;
        } catch (IllegalAccessException e2) {
            return false;
        }
    }

    protected static boolean zzgr(String $r0) throws  {
        ClassLoader $r1 = zzawp();
        if ($r1 == null) {
            return true;
        }
        try {
            return zzd($r1.loadClass($r0));
        } catch (Exception e) {
            return false;
        }
    }

    protected boolean zzawr() throws  {
        return false;
    }
}
