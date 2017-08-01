package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.os.Binder;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.GooglePlayServicesUtilLight;

/* compiled from: dalvik_source_com.waze.apk */
public class zza extends com.google.android.gms.common.internal.zzq.zza {
    int zzajz;

    public static Account zza(zzq $r0) throws  {
        if ($r0 == null) {
            return null;
        }
        long $l0 = Binder.clearCallingIdentity();
        try {
            Account $r1 = $r0.getAccount();
            return $r1;
        } catch (RemoteException e) {
            Log.w("AccountAccessor", "Remote account accessor probably died");
            return null;
        } finally {
            Binder.restoreCallingIdentity($l0);
        }
    }

    public boolean equals(Object $r1) throws  {
        if (this == $r1) {
            return true;
        }
        if (!($r1 instanceof zza)) {
            return false;
        }
        this = (zza) $r1;
        throw new NullPointerException("This statement would have triggered an Exception: $u-1 = virtualinvoke $u1.<android.accounts.Account: boolean equals(java.lang.Object)>(null)");
    }

    public Account getAccount() throws  {
        int $i0 = Binder.getCallingUid();
        if ($i0 == this.zzajz) {
            return null;
        }
        if (GooglePlayServicesUtilLight.zzd(null, $i0)) {
            this.zzajz = $i0;
            return null;
        }
        throw new SecurityException("Caller is not GooglePlayServices");
    }
}
