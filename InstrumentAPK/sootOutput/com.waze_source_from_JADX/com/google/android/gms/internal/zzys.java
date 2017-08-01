package com.google.android.gms.internal;

import android.accounts.AccountManager;
import android.accounts.AuthenticatorDescription;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncAdapterType;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/* compiled from: dalvik_source_com.waze.apk */
class zzys extends zzyr {
    private final AccountManager aNh = AccountManager.get(this.mContext);
    private final Context mContext;

    public zzys(Context $r1) throws  {
        this.mContext = $r1;
    }

    private static AuthenticatorDescription zza(AuthenticatorDescription[] $r0, String $r1) throws  {
        for (AuthenticatorDescription $r2 : $r0) {
            if ($r1.equals($r2.type)) {
                return $r2;
            }
        }
        return null;
    }

    public List<zzyq> zzccy() throws  {
        zzyq com_google_android_gms_internal_zzyq;
        long $l0 = SystemClock.currentThreadTimeMillis();
        long $l1 = SystemClock.elapsedRealtime();
        ArrayList $r1 = new ArrayList();
        HashSet<String> $r2 = new HashSet();
        AccountManager $r3 = this.aNh;
        SyncAdapterType[] $r4 = ContentResolver.getSyncAdapterTypes();
        AuthenticatorDescription[] $r5 = $r3.getAuthenticatorTypes();
        for (SyncAdapterType $r6 : $r4) {
            String $r7;
            String $r8 = $r6.authority;
            if ("com.android.contacts".equals($r8)) {
                $r7 = $r6.accountType;
                AuthenticatorDescription $r9 = zza($r5, $r7);
                if ($r9 == null) {
                    Log.w("ExAccountTypeManager", new StringBuilder(String.valueOf($r7).length() + 46).append("No authenticator found for type=").append($r7).append(", ignoring it.").toString());
                } else {
                    $r8 = $r9.packageName;
                    String str = $r8;
                    str = String.valueOf($r8);
                    Log.d("ExAccountTypeManager", new StringBuilder((String.valueOf($r7).length() + 56) + String.valueOf(str).length()).append("Registering external account type=").append($r7).append(", resourcePackageName=").append(str).toString());
                    com_google_android_gms_internal_zzyq = new zzyq(this.mContext, $r9.packageName, false);
                    if (com_google_android_gms_internal_zzyq.isInitialized()) {
                        com_google_android_gms_internal_zzyq.accountType = $r9.type;
                        int i = $r9.labelId;
                        int $i4 = i;
                        com_google_android_gms_internal_zzyq.titleRes = i;
                        i = $r9.iconId;
                        $i4 = i;
                        com_google_android_gms_internal_zzyq.iconRes = i;
                        $r1.add(com_google_android_gms_internal_zzyq);
                        $r2.addAll(com_google_android_gms_internal_zzyq.zzccx());
                    }
                }
            }
        }
        if (!$r2.isEmpty()) {
            Log.d("ExAccountTypeManager", "Registering " + $r2.size() + " extension packages");
            for (String $r72 : $r2) {
                com_google_android_gms_internal_zzyq = new zzyq(this.mContext, $r72, true);
                if (com_google_android_gms_internal_zzyq.isInitialized()) {
                    if (com_google_android_gms_internal_zzyq.zzccw()) {
                        $r8 = com_google_android_gms_internal_zzyq.accountType;
                        if (TextUtils.isEmpty($r8)) {
                            Log.w("ExAccountTypeManager", new StringBuilder(String.valueOf($r72).length() + 106).append("Skipping extension package ").append($r72).append(" because the CONTACTS_STRUCTURE metadata doesn't have the accountType attribute").toString());
                        } else {
                            str = com_google_android_gms_internal_zzyq.accountType;
                            String $r11 = com_google_android_gms_internal_zzyq.aMK;
                            Log.d("ExAccountTypeManager", new StringBuilder(((String.valueOf(str).length() + 67) + String.valueOf($r11).length()) + String.valueOf($r72).length()).append("Registering extension package account type=").append(str).append(", dataSet=").append($r11).append(", packageName=").append($r72).toString());
                            $r1.add(com_google_android_gms_internal_zzyq);
                        }
                    } else {
                        Log.w("ExAccountTypeManager", new StringBuilder(String.valueOf($r72).length() + 83).append("Skipping extension package ").append($r72).append(" because it doesn't have the CONTACTS_STRUCTURE metadata").toString());
                    }
                }
            }
        }
        long $l6 = SystemClock.elapsedRealtime();
        Log.i("ExAccountTypeManager", "Loaded meta-data for " + $r1.size() + " account types in " + ($l6 - $l1) + "ms(wall) " + (SystemClock.currentThreadTimeMillis() - $l0) + "ms(cpu)");
        return $r1;
    }
}
