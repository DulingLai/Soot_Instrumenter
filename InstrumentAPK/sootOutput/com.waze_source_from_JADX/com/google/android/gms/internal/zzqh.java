package com.google.android.gms.internal;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.ApiOptions;
import com.google.android.gms.common.api.Api.zzc;
import com.google.android.gms.common.internal.zzaa;
import dalvik.annotation.Signature;

/* compiled from: dalvik_source_com.waze.apk */
public final class zzqh<O extends ApiOptions> {
    private final O Cp;
    private final Api<O> zn;

    public zzqh(@Signature({"(", "Lcom/google/android/gms/common/api/Api", "<TO;>;TO;)V"}) Api<O> $r1, @Signature({"(", "Lcom/google/android/gms/common/api/Api", "<TO;>;TO;)V"}) O $r2) throws  {
        this.zn = $r1;
        this.Cp = $r2;
    }

    public boolean equals(Object $r1) throws  {
        if ($r1 == this) {
            return true;
        }
        if (!($r1 instanceof zzqh)) {
            return false;
        }
        zzqh $r2 = (zzqh) $r1;
        return zzaa.equal(this.zn, $r2.zn) && zzaa.equal(this.Cp, $r2.Cp);
    }

    public int hashCode() throws  {
        return zzaa.hashCode(this.zn, this.Cp);
    }

    public zzc<?> zzarl() throws  {
        return this.zn.zzarl();
    }

    public String zzase() throws  {
        return this.zn.getName();
    }
}
