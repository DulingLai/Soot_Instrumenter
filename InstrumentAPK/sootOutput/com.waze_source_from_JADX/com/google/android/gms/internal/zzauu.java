package com.google.android.gms.internal;

import java.io.IOException;
import java.io.StringWriter;

/* compiled from: dalvik_source_com.waze.apk */
public abstract class zzauu {
    public Number hb() throws  {
        throw new UnsupportedOperationException(getClass().getSimpleName());
    }

    public String hc() throws  {
        throw new UnsupportedOperationException(getClass().getSimpleName());
    }

    public double hd() throws  {
        throw new UnsupportedOperationException(getClass().getSimpleName());
    }

    public long he() throws  {
        throw new UnsupportedOperationException(getClass().getSimpleName());
    }

    public int hf() throws  {
        throw new UnsupportedOperationException(getClass().getSimpleName());
    }

    public boolean hg() throws  {
        throw new UnsupportedOperationException(getClass().getSimpleName());
    }

    public boolean hh() throws  {
        return this instanceof zzaur;
    }

    public boolean hi() throws  {
        return this instanceof zzaux;
    }

    public boolean hj() throws  {
        return this instanceof zzava;
    }

    public boolean hk() throws  {
        return this instanceof zzauw;
    }

    public zzaux hl() throws  {
        if (hi()) {
            return (zzaux) this;
        }
        String $r3 = String.valueOf(this);
        throw new IllegalStateException(new StringBuilder(String.valueOf($r3).length() + 19).append("Not a JSON Object: ").append($r3).toString());
    }

    public zzaur hm() throws  {
        if (hh()) {
            return (zzaur) this;
        }
        throw new IllegalStateException("This is not a JSON Array.");
    }

    public zzava hn() throws  {
        if (hj()) {
            return (zzava) this;
        }
        throw new IllegalStateException("This is not a JSON Primitive.");
    }

    Boolean ho() throws  {
        throw new UnsupportedOperationException(getClass().getSimpleName());
    }

    public String toString() throws  {
        try {
            StringWriter $r2 = new StringWriter();
            zzawn $r3 = new zzawn($r2);
            $r3.setLenient(true);
            zzavv.zzb(this, $r3);
            return $r2.toString();
        } catch (IOException $r4) {
            throw new AssertionError($r4);
        }
    }
}
