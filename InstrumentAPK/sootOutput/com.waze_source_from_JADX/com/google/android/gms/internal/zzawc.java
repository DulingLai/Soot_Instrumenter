package com.google.android.gms.internal;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/* compiled from: dalvik_source_com.waze.apk */
public final class zzawc extends zzawn {
    private static final Writer bZe = new C07971();
    private static final zzava bZf = new zzava("closed");
    private final List<zzauu> bZd = new ArrayList();
    private String bZg;
    private zzauu bZh = zzauw.bXK;

    /* compiled from: dalvik_source_com.waze.apk */
    static class C07971 extends Writer {
        C07971() throws  {
        }

        public void close() throws IOException {
            throw new AssertionError();
        }

        public void flush() throws IOException {
            throw new AssertionError();
        }

        public void write(char[] cArr, int i, int i2) throws  {
            throw new AssertionError();
        }
    }

    public zzawc() throws  {
        super(bZe);
    }

    private zzauu hH() throws  {
        return (zzauu) this.bZd.get(this.bZd.size() - 1);
    }

    private void zzd(zzauu $r1) throws  {
        if (this.bZg != null) {
            if (!$r1.hk() || hZ()) {
                ((zzaux) hH()).zza(this.bZg, $r1);
            }
            this.bZg = null;
        } else if (this.bZd.isEmpty()) {
            this.bZh = $r1;
        } else {
            zzauu $r3 = hH();
            if ($r3 instanceof zzaur) {
                ((zzaur) $r3).zzc($r1);
                return;
            }
            throw new IllegalStateException();
        }
    }

    public void close() throws IOException {
        if (this.bZd.isEmpty()) {
            this.bZd.add(bZf);
            return;
        }
        throw new IOException("Incomplete document");
    }

    public void flush() throws IOException {
    }

    public zzauu hG() throws  {
        if (this.bZd.isEmpty()) {
            return this.bZh;
        }
        String $r3 = String.valueOf(this.bZd);
        throw new IllegalStateException(new StringBuilder(String.valueOf($r3).length() + 34).append("Expected one JSON element but was ").append($r3).toString());
    }

    public zzawn hI() throws IOException {
        zzaur $r1 = new zzaur();
        zzd($r1);
        this.bZd.add($r1);
        return this;
    }

    public zzawn hJ() throws IOException {
        if (this.bZd.isEmpty() || this.bZg != null) {
            throw new IllegalStateException();
        } else if (hH() instanceof zzaur) {
            this.bZd.remove(this.bZd.size() - 1);
            return this;
        } else {
            throw new IllegalStateException();
        }
    }

    public zzawn hK() throws IOException {
        zzaux $r1 = new zzaux();
        zzd($r1);
        this.bZd.add($r1);
        return this;
    }

    public zzawn hL() throws IOException {
        if (this.bZd.isEmpty() || this.bZg != null) {
            throw new IllegalStateException();
        } else if (hH() instanceof zzaux) {
            this.bZd.remove(this.bZd.size() - 1);
            return this;
        } else {
            throw new IllegalStateException();
        }
    }

    public zzawn hM() throws IOException {
        zzd(zzauw.bXK);
        return this;
    }

    public zzawn zza(Number $r0) throws IOException {
        if ($r0 == null) {
            return hM();
        }
        if (!isLenient()) {
            double $d0 = $r0.doubleValue();
            if (Double.isNaN($d0) || Double.isInfinite($d0)) {
                String $r4 = String.valueOf($r0);
                throw new IllegalArgumentException(new StringBuilder(String.valueOf($r4).length() + 33).append("JSON forbids NaN and infinities: ").append($r4).toString());
            }
        }
        zzd(new zzava($r0));
        return this;
    }

    public zzawn zzdf(long $l0) throws IOException {
        zzd(new zzava(Long.valueOf($l0)));
        return this;
    }

    public zzawn zzeh(boolean $z0) throws IOException {
        zzd(new zzava(Boolean.valueOf($z0)));
        return this;
    }

    public zzawn zzyo(String $r1) throws IOException {
        if (this.bZd.isEmpty() || this.bZg != null) {
            throw new IllegalStateException();
        } else if (hH() instanceof zzaux) {
            this.bZg = $r1;
            return this;
        } else {
            throw new IllegalStateException();
        }
    }

    public zzawn zzyp(String $r0) throws IOException {
        if ($r0 == null) {
            return hM();
        }
        zzd(new zzava($r0));
        return this;
    }
}
