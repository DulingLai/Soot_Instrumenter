package com.google.android.gms.internal;

import java.util.Map.Entry;
import java.util.Set;

/* compiled from: dalvik_source_com.waze.apk */
public final class zzaux extends zzauu {
    private final zzavs<String, zzauu> bXL = new zzavs();

    public Set<Entry<String, zzauu>> entrySet() throws  {
        return this.bXL.entrySet();
    }

    public boolean equals(Object $r2) throws  {
        return $r2 == this || (($r2 instanceof zzaux) && ((zzaux) $r2).bXL.equals(this.bXL));
    }

    public boolean has(String $r1) throws  {
        return this.bXL.containsKey($r1);
    }

    public int hashCode() throws  {
        return this.bXL.hashCode();
    }

    public void zza(String $r1, zzauu $r3) throws  {
        if ($r3 == null) {
            $r3 = zzauw.bXK;
        }
        this.bXL.put($r1, $r3);
    }

    public zzauu zzyl(String $r1) throws  {
        return (zzauu) this.bXL.get($r1);
    }
}
