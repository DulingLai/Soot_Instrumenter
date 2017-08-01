package com.google.android.gms.internal;

import com.facebook.internal.ServerProtocol;
import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.io.Writer;

/* compiled from: dalvik_source_com.waze.apk */
public class zzawn implements Closeable, Flushable {
    private static final String[] cbc = new String[128];
    private static final String[] cbd = ((String[]) cbc.clone());
    private boolean bXu;
    private boolean bXv;
    private boolean caF;
    private int[] caN = new int[32];
    private int caO = 0;
    private String cbe;
    private String cbf;
    private final Writer out;
    private String separator;

    static {
        for (int $i0 = 0; $i0 <= 31; $i0++) {
            cbc[$i0] = String.format("\\u%04x", new Object[]{Integer.valueOf($i0)});
        }
        cbc[34] = "\\\"";
        cbc[92] = "\\\\";
        cbc[9] = "\\t";
        cbc[8] = "\\b";
        cbc[10] = "\\n";
        cbc[13] = "\\r";
        cbc[12] = "\\f";
        cbd[60] = "\\u003c";
        cbd[62] = "\\u003e";
        cbd[38] = "\\u0026";
        cbd[61] = "\\u003d";
        cbd[39] = "\\u0027";
    }

    public zzawn(Writer $r1) throws  {
        zzarp(6);
        this.separator = ":";
        this.bXu = true;
        if ($r1 == null) {
            throw new NullPointerException("out == null");
        }
        this.out = $r1;
    }

    private int ia() throws  {
        if (this.caO != 0) {
            return this.caN[this.caO - 1];
        }
        throw new IllegalStateException("JsonWriter is closed.");
    }

    private void ib() throws IOException {
        if (this.cbf != null) {
            id();
            zzys(this.cbf);
            this.cbf = null;
        }
    }

    private void ic() throws IOException {
        if (this.cbe != null) {
            this.out.write("\n");
            int $i1 = this.caO;
            for (int $i0 = 1; $i0 < $i1; $i0++) {
                this.out.write(this.cbe);
            }
        }
    }

    private void id() throws IOException {
        int $i0 = ia();
        if ($i0 == 5) {
            this.out.write(44);
        } else if ($i0 != 3) {
            throw new IllegalStateException("Nesting problem.");
        }
        ic();
        zzarr(4);
    }

    private void zzarp(int $i0) throws  {
        int[] $r1;
        if (this.caO == this.caN.length) {
            $r1 = new int[(this.caO * 2)];
            System.arraycopy(this.caN, 0, $r1, 0, this.caO);
            this.caN = $r1;
        }
        $r1 = this.caN;
        int $i1 = this.caO;
        this.caO = $i1 + 1;
        $r1[$i1] = $i0;
    }

    private void zzarr(int $i0) throws  {
        this.caN[this.caO - 1] = $i0;
    }

    private zzawn zzc(int $i0, int $i1, String $r1) throws IOException {
        int $i2 = ia();
        if ($i2 != $i1 && $i2 != $i0) {
            throw new IllegalStateException("Nesting problem.");
        } else if (this.cbf != null) {
            $r1 = "Dangling name: ";
            String $r3 = String.valueOf(this.cbf);
            throw new IllegalStateException($r3.length() != 0 ? $r1.concat($r3) : new String("Dangling name: "));
        } else {
            this.caO--;
            if ($i2 == $i1) {
                ic();
            }
            this.out.write($r1);
            return this;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void zzel(boolean r8) throws java.io.IOException {
        /*
        r7 = this;
        r0 = r7.ia();
        switch(r0) {
            case 1: goto L_0x002f;
            case 2: goto L_0x0037;
            case 3: goto L_0x0008;
            case 4: goto L_0x0042;
            case 5: goto L_0x0008;
            case 6: goto L_0x001c;
            case 7: goto L_0x0010;
            default: goto L_0x0007;
        };
    L_0x0007:
        goto L_0x0008;
    L_0x0008:
        r1 = new java.lang.IllegalStateException;
        r2 = "Nesting problem.";
        r1.<init>(r2);
        throw r1;
    L_0x0010:
        r3 = r7.caF;
        if (r3 != 0) goto L_0x001c;
    L_0x0014:
        r1 = new java.lang.IllegalStateException;
        r2 = "JSON must have only one top-level value.";
        r1.<init>(r2);
        throw r1;
    L_0x001c:
        r3 = r7.caF;
        if (r3 != 0) goto L_0x002a;
    L_0x0020:
        if (r8 != 0) goto L_0x002a;
    L_0x0022:
        r1 = new java.lang.IllegalStateException;
        r2 = "JSON must start with an array or an object.";
        r1.<init>(r2);
        throw r1;
    L_0x002a:
        r4 = 7;
        r7.zzarr(r4);
        return;
    L_0x002f:
        r4 = 2;
        r7.zzarr(r4);
        r7.ic();
        return;
    L_0x0037:
        r5 = r7.out;
        r4 = 44;
        r5.append(r4);
        r7.ic();
        return;
    L_0x0042:
        r5 = r7.out;
        r6 = r7.separator;
        r5.append(r6);
        r4 = 5;
        r7.zzarr(r4);
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzawn.zzel(boolean):void");
    }

    private zzawn zzr(int $i0, String $r1) throws IOException {
        zzel(true);
        zzarp($i0);
        this.out.write($r1);
        return this;
    }

    private void zzys(String $r1) throws IOException {
        int $i1 = 0;
        String[] $r2 = this.bXv ? cbd : cbc;
        this.out.write("\"");
        int $i2 = $r1.length();
        for (int $i3 = 0; $i3 < $i2; $i3++) {
            char $c4 = $r1.charAt($i3);
            String $r4;
            if ($c4 < '') {
                $r4 = $r2[$c4];
                if ($r4 == null) {
                }
                if ($i1 < $i3) {
                    this.out.write($r1, $i1, $i3 - $i1);
                }
                this.out.write($r4);
                $i1 = $i3 + 1;
            } else {
                if ($c4 == ' ') {
                    $r4 = "\\u2028";
                } else if ($c4 == ' ') {
                    $r4 = "\\u2029";
                }
                if ($i1 < $i3) {
                    this.out.write($r1, $i1, $i3 - $i1);
                }
                this.out.write($r4);
                $i1 = $i3 + 1;
            }
        }
        if ($i1 < $i2) {
            this.out.write($r1, $i1, $i2 - $i1);
        }
        this.out.write("\"");
    }

    public void close() throws IOException {
        this.out.close();
        int $i0 = this.caO;
        if ($i0 > 1 || ($i0 == 1 && this.caN[$i0 - 1] != 7)) {
            throw new IOException("Incomplete document");
        }
        this.caO = 0;
    }

    public void flush() throws IOException {
        if (this.caO == 0) {
            throw new IllegalStateException("JsonWriter is closed.");
        }
        this.out.flush();
    }

    public zzawn hI() throws IOException {
        ib();
        return zzr(1, "[");
    }

    public zzawn hJ() throws IOException {
        return zzc(1, 2, "]");
    }

    public zzawn hK() throws IOException {
        ib();
        return zzr(3, "{");
    }

    public zzawn hL() throws IOException {
        return zzc(3, 5, "}");
    }

    public zzawn hM() throws IOException {
        if (this.cbf != null) {
            if (this.bXu) {
                ib();
            } else {
                this.cbf = null;
                return this;
            }
        }
        zzel(false);
        this.out.write("null");
        return this;
    }

    public final boolean hY() throws  {
        return this.bXv;
    }

    public final boolean hZ() throws  {
        return this.bXu;
    }

    public boolean isLenient() throws  {
        return this.caF;
    }

    public final void setIndent(String $r1) throws  {
        if ($r1.length() == 0) {
            this.cbe = null;
            this.separator = ":";
            return;
        }
        this.cbe = $r1;
        this.separator = ": ";
    }

    public final void setLenient(boolean $z0) throws  {
        this.caF = $z0;
    }

    public zzawn zza(Number $r0) throws IOException {
        if ($r0 == null) {
            return hM();
        }
        ib();
        String $r2 = $r0.toString();
        if (this.caF || !($r2.equals("-Infinity") || $r2.equals("Infinity") || $r2.equals("NaN"))) {
            zzel(false);
            this.out.append($r2);
            return this;
        }
        $r2 = String.valueOf($r0);
        throw new IllegalArgumentException(new StringBuilder(String.valueOf($r2).length() + 39).append("Numeric values must be finite, but was ").append($r2).toString());
    }

    public zzawn zzdf(long $l0) throws IOException {
        ib();
        zzel(false);
        this.out.write(Long.toString($l0));
        return this;
    }

    public zzawn zzeh(boolean $z0) throws IOException {
        ib();
        zzel(false);
        this.out.write($z0 ? ServerProtocol.DIALOG_RETURN_SCOPES_TRUE : "false");
        return this;
    }

    public final void zzej(boolean $z0) throws  {
        this.bXv = $z0;
    }

    public final void zzek(boolean $z0) throws  {
        this.bXu = $z0;
    }

    public zzawn zzyo(String $r1) throws IOException {
        if ($r1 == null) {
            throw new NullPointerException("name == null");
        } else if (this.cbf != null) {
            throw new IllegalStateException();
        } else if (this.caO == 0) {
            throw new IllegalStateException("JsonWriter is closed.");
        } else {
            this.cbf = $r1;
            return this;
        }
    }

    public zzawn zzyp(String $r0) throws IOException {
        if ($r0 == null) {
            return hM();
        }
        ib();
        zzel(false);
        zzys($r0);
        return this;
    }
}
