package com.google.android.gms.internal;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

/* compiled from: dalvik_source_com.waze.apk */
public final class zzawb extends zzawl {
    private static final Reader bZb = new C07961();
    private static final Object bZc = new Object();
    private final List<Object> bZd = new ArrayList();

    /* compiled from: dalvik_source_com.waze.apk */
    static class C07961 extends Reader {
        C07961() throws  {
        }

        public void close() throws IOException {
            throw new AssertionError();
        }

        public int read(char[] cArr, int i, int i2) throws IOException {
            throw new AssertionError();
        }
    }

    public zzawb(zzauu $r1) throws  {
        super(bZb);
        this.bZd.add($r1);
    }

    private Object hD() throws  {
        return this.bZd.get(this.bZd.size() - 1);
    }

    private Object hE() throws  {
        return this.bZd.remove(this.bZd.size() - 1);
    }

    private void zza(zzawm $r1) throws IOException {
        if (hC() != $r1) {
            String $r4 = String.valueOf($r1);
            String $r5 = String.valueOf(hC());
            throw new IllegalStateException(new StringBuilder((String.valueOf($r4).length() + 18) + String.valueOf($r5).length()).append("Expected ").append($r4).append(" but was ").append($r5).toString());
        }
    }

    public void beginArray() throws IOException {
        zza(zzawm.BEGIN_ARRAY);
        this.bZd.add(((zzaur) hD()).iterator());
    }

    public void beginObject() throws IOException {
        zza(zzawm.BEGIN_OBJECT);
        this.bZd.add(((zzaux) hD()).entrySet().iterator());
    }

    public void close() throws IOException {
        this.bZd.clear();
        this.bZd.add(bZc);
    }

    public void endArray() throws IOException {
        zza(zzawm.END_ARRAY);
        hE();
        hE();
    }

    public void endObject() throws IOException {
        zza(zzawm.END_OBJECT);
        hE();
        hE();
    }

    public zzawm hC() throws IOException {
        if (this.bZd.isEmpty()) {
            return zzawm.END_DOCUMENT;
        }
        Object $r2 = hD();
        if ($r2 instanceof Iterator) {
            boolean $z0 = this.bZd.get(this.bZd.size() - 2) instanceof zzaux;
            Iterator $r5 = (Iterator) $r2;
            if (!$r5.hasNext()) {
                return $z0 ? zzawm.END_OBJECT : zzawm.END_ARRAY;
            } else {
                if ($z0) {
                    return zzawm.NAME;
                }
                this.bZd.add($r5.next());
                return hC();
            }
        } else if ($r2 instanceof zzaux) {
            return zzawm.BEGIN_OBJECT;
        } else {
            if ($r2 instanceof zzaur) {
                return zzawm.BEGIN_ARRAY;
            }
            if ($r2 instanceof zzava) {
                zzava $r7 = (zzava) $r2;
                if ($r7.hr()) {
                    return zzawm.STRING;
                }
                if ($r7.hp()) {
                    return zzawm.BOOLEAN;
                }
                if ($r7.hq()) {
                    return zzawm.NUMBER;
                }
                throw new AssertionError();
            } else if ($r2 instanceof zzauw) {
                return zzawm.NULL;
            } else {
                if ($r2 == bZc) {
                    throw new IllegalStateException("JsonReader is closed");
                }
                throw new AssertionError();
            }
        }
    }

    public void hF() throws IOException {
        zza(zzawm.NAME);
        Entry $r4 = (Entry) ((Iterator) hD()).next();
        this.bZd.add($r4.getValue());
        this.bZd.add(new zzava((String) $r4.getKey()));
    }

    public boolean hasNext() throws IOException {
        zzawm $r1 = hC();
        return ($r1 == zzawm.END_OBJECT || $r1 == zzawm.END_ARRAY) ? false : true;
    }

    public boolean nextBoolean() throws IOException {
        zza(zzawm.BOOLEAN);
        return ((zzava) hE()).hg();
    }

    public double nextDouble() throws IOException {
        zzawm $r1 = hC();
        if ($r1 == zzawm.NUMBER || $r1 == zzawm.STRING) {
            double $d0 = ((zzava) hD()).hd();
            if (isLenient() || !(Double.isNaN($d0) || Double.isInfinite($d0))) {
                hE();
                return $d0;
            }
            throw new NumberFormatException("JSON forbids NaN and infinities: " + $d0);
        }
        String $r4 = String.valueOf(zzawm.NUMBER);
        String $r5 = String.valueOf($r1);
        throw new IllegalStateException(new StringBuilder((String.valueOf($r4).length() + 18) + String.valueOf($r5).length()).append("Expected ").append($r4).append(" but was ").append($r5).toString());
    }

    public int nextInt() throws IOException {
        zzawm $r1 = hC();
        if ($r1 == zzawm.NUMBER || $r1 == zzawm.STRING) {
            int $i0 = ((zzava) hD()).hf();
            hE();
            return $i0;
        }
        String $r4 = String.valueOf(zzawm.NUMBER);
        String $r5 = String.valueOf($r1);
        throw new IllegalStateException(new StringBuilder((String.valueOf($r4).length() + 18) + String.valueOf($r5).length()).append("Expected ").append($r4).append(" but was ").append($r5).toString());
    }

    public long nextLong() throws IOException {
        zzawm $r1 = hC();
        if ($r1 == zzawm.NUMBER || $r1 == zzawm.STRING) {
            long $l2 = ((zzava) hD()).he();
            hE();
            return $l2;
        }
        String $r4 = String.valueOf(zzawm.NUMBER);
        String $r5 = String.valueOf($r1);
        throw new IllegalStateException(new StringBuilder((String.valueOf($r4).length() + 18) + String.valueOf($r5).length()).append("Expected ").append($r4).append(" but was ").append($r5).toString());
    }

    public String nextName() throws IOException {
        zza(zzawm.NAME);
        Entry $r5 = (Entry) ((Iterator) hD()).next();
        this.bZd.add($r5.getValue());
        return (String) $r5.getKey();
    }

    public void nextNull() throws IOException {
        zza(zzawm.NULL);
        hE();
    }

    public String nextString() throws IOException {
        zzawm $r1 = hC();
        if ($r1 == zzawm.STRING || $r1 == zzawm.NUMBER) {
            return ((zzava) hE()).hc();
        }
        String $r4 = String.valueOf(zzawm.STRING);
        String $r5 = String.valueOf($r1);
        throw new IllegalStateException(new StringBuilder((String.valueOf($r4).length() + 18) + String.valueOf($r5).length()).append("Expected ").append($r4).append(" but was ").append($r5).toString());
    }

    public void skipValue() throws IOException {
        if (hC() == zzawm.NAME) {
            nextName();
        } else {
            hE();
        }
    }

    public String toString() throws  {
        return getClass().getSimpleName();
    }
}
