package com.google.android.gms.people.internal;

/* compiled from: dalvik_source_com.waze.apk */
public class zzv {
    private final zzw<Integer> aRp = new zzw();

    public void put(String $r1, int $i0) throws  {
        this.aRp.put($r1, Integer.valueOf($i0));
    }

    public String toString() throws  {
        return this.aRp.toString();
    }

    public int zzac(String $r1, int $i0) throws  {
        return ((Integer) this.aRp.zzad($r1, $i0)).intValue();
    }

    public int zzrm(String $r1) throws  {
        return this.aRp.zzrm($r1);
    }
}
