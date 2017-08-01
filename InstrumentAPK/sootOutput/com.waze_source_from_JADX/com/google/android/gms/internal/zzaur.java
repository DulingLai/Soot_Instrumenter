package com.google.android.gms.internal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: dalvik_source_com.waze.apk */
public final class zzaur extends zzauu implements Iterable<zzauu> {
    private final List<zzauu> bwY = new ArrayList();

    public boolean equals(Object $r2) throws  {
        return $r2 == this || (($r2 instanceof zzaur) && ((zzaur) $r2).bwY.equals(this.bwY));
    }

    public int hashCode() throws  {
        return this.bwY.hashCode();
    }

    public Number hb() throws  {
        if (this.bwY.size() == 1) {
            return ((zzauu) this.bwY.get(0)).hb();
        }
        throw new IllegalStateException();
    }

    public String hc() throws  {
        if (this.bwY.size() == 1) {
            return ((zzauu) this.bwY.get(0)).hc();
        }
        throw new IllegalStateException();
    }

    public double hd() throws  {
        if (this.bwY.size() == 1) {
            return ((zzauu) this.bwY.get(0)).hd();
        }
        throw new IllegalStateException();
    }

    public long he() throws  {
        if (this.bwY.size() == 1) {
            return ((zzauu) this.bwY.get(0)).he();
        }
        throw new IllegalStateException();
    }

    public int hf() throws  {
        if (this.bwY.size() == 1) {
            return ((zzauu) this.bwY.get(0)).hf();
        }
        throw new IllegalStateException();
    }

    public boolean hg() throws  {
        if (this.bwY.size() == 1) {
            return ((zzauu) this.bwY.get(0)).hg();
        }
        throw new IllegalStateException();
    }

    public Iterator<zzauu> iterator() throws  {
        return this.bwY.iterator();
    }

    public void zzc(zzauu $r2) throws  {
        if ($r2 == null) {
            $r2 = zzauw.bXK;
        }
        this.bwY.add($r2);
    }
}
