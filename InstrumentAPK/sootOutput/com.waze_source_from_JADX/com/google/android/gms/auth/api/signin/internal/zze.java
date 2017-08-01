package com.google.android.gms.auth.api.signin.internal;

/* compiled from: dalvik_source_com.waze.apk */
public class zze {
    static int fZ = 31;
    private int ga = 1;

    public int zzaeu() throws  {
        return this.ga;
    }

    public zze zzar(boolean $z0) throws  {
        this.ga = ($z0 ? (byte) 1 : (byte) 0) + (this.ga * fZ);
        return this;
    }

    public zze zzu(Object $r1) throws  {
        this.ga = ($r1 == null ? 0 : $r1.hashCode()) + (this.ga * fZ);
        return this;
    }
}
