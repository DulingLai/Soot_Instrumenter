package com.google.android.gms.flags.impl;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import com.google.android.gms.common.util.DynamiteApi;
import com.google.android.gms.dynamic.zze;
import com.google.android.gms.flags.impl.zza.zzb;
import com.google.android.gms.flags.impl.zza.zzc;
import com.google.android.gms.flags.impl.zza.zzd;
import com.google.android.gms.internal.zzwh.zza;

@DynamiteApi
/* compiled from: dalvik_source_com.waze.apk */
public class FlagProviderImpl extends zza {
    private boolean zzanl = false;
    private SharedPreferences zzayl;

    public boolean getBooleanFlagValue(String $r1, boolean $z0, int i) throws  {
        return !this.zzanl ? $z0 : zza.zza.zza(this.zzayl, $r1, Boolean.valueOf($z0)).booleanValue();
    }

    public int getIntFlagValue(String $r1, int $i1, int i) throws  {
        return !this.zzanl ? $i1 : zzb.zza(this.zzayl, $r1, Integer.valueOf($i1)).intValue();
    }

    public long getLongFlagValue(String $r1, long $l1, int i) throws  {
        return !this.zzanl ? $l1 : zzc.zza(this.zzayl, $r1, Long.valueOf($l1)).longValue();
    }

    public String getStringFlagValue(String $r1, String $r2, int i) throws  {
        return !this.zzanl ? $r2 : zzd.zza(this.zzayl, $r1, $r2);
    }

    public void init(com.google.android.gms.dynamic.zzd $r1) throws  {
        Context $r3 = (Context) zze.zzae($r1);
        if (!this.zzanl) {
            try {
                this.zzayl = zzb.zzn($r3.createPackageContext("com.google.android.gms", 0));
                this.zzanl = true;
            } catch (NameNotFoundException e) {
            }
        }
    }
}
