package com.google.android.gms.internal;

/* compiled from: dalvik_source_com.waze.apk */
public class zzd implements zzo {
    private int zzn;
    private int zzo;
    private final int zzp;
    private final float zzq;

    public zzd() throws  {
        this(2500, 1, 1.0f);
    }

    public zzd(int $i0, int $i1, float $f0) throws  {
        this.zzn = $i0;
        this.zzp = $i1;
        this.zzq = $f0;
    }

    public void zza(zzr $r1) throws zzr {
        this.zzo++;
        this.zzn = (int) (((float) this.zzn) + (((float) this.zzn) * this.zzq));
        if (!zze()) {
            throw $r1;
        }
    }

    public int zzc() throws  {
        return this.zzn;
    }

    public int zzd() throws  {
        return this.zzo;
    }

    protected boolean zze() throws  {
        return this.zzo <= this.zzp;
    }
}
