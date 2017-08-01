package com.google.android.gms.internal;

import android.support.annotation.Nullable;
import com.google.android.gms.common.api.Api.ApiOptions.Optional;

/* compiled from: dalvik_source_com.waze.apk */
public final class zzaci implements Optional {
    public static final zzaci bgm = new zza().zzcna();
    private final boolean bgn;
    private final boolean bgo;
    private final Long bgp;
    private final Long bgq;
    private final boolean fI;
    private final boolean fK;
    private final String fL;
    private final String fM;

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class zza {
        public zzaci zzcna() throws  {
            return new zzaci(false, false, null, false, null, false, null, null);
        }
    }

    private zzaci(boolean $z0, boolean $z1, String $r1, boolean $z2, String $r2, boolean $z3, Long $r3, Long $r4) throws  {
        this.bgn = $z0;
        this.fI = $z1;
        this.fL = $r1;
        this.fK = $z2;
        this.bgo = $z3;
        this.fM = $r2;
        this.bgp = $r3;
        this.bgq = $r4;
    }

    public boolean zzaej() throws  {
        return this.fI;
    }

    public boolean zzael() throws  {
        return this.fK;
    }

    public String zzaem() throws  {
        return this.fL;
    }

    @Nullable
    public String zzaen() throws  {
        return this.fM;
    }

    public boolean zzcmw() throws  {
        return this.bgn;
    }

    public boolean zzcmx() throws  {
        return this.bgo;
    }

    @Nullable
    public Long zzcmy() throws  {
        return this.bgp;
    }

    @Nullable
    public Long zzcmz() throws  {
        return this.bgq;
    }
}
