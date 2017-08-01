package com.google.android.gms.internal;

import android.accounts.Account;
import android.support.annotation.Nullable;
import android.util.Log;

/* compiled from: dalvik_source_com.waze.apk */
public class zzxo {
    public static String zzh(@Nullable Account $r0) throws  {
        if ($r0 == null) {
            return "null";
        }
        if (Log.isLoggable("GCoreUlr", 2)) {
            return $r0.name;
        }
        return "account#" + ($r0.name.hashCode() % 20) + "#";
    }

    public static String zzh(@Nullable Integer $r0) throws  {
        if ($r0 == null) {
            return "(null)";
        }
        if (Log.isLoggable("GCoreUlr", 2)) {
            return String.valueOf($r0);
        }
        return "tag#" + ($r0.intValue() % 20);
    }
}
