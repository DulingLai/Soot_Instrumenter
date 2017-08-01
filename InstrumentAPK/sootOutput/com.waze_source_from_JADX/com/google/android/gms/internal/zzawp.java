package com.google.android.gms.internal;

/* compiled from: dalvik_source_com.waze.apk */
public class zzawp {
    private final byte[] cbg = new byte[256];
    private int cbh;
    private int cbi;

    public zzawp(byte[] $r1) throws  {
        int $i0;
        for ($i0 = 0; $i0 < 256; $i0++) {
            this.cbg[$i0] = (byte) $i0;
        }
        int $i2 = 0;
        for ($i0 = 0; $i0 < 256; $i0++) {
            $i2 = (($i2 + this.cbg[$i0]) + $r1[$i0 % $r1.length]) & 255;
            byte $b1 = this.cbg[$i0];
            this.cbg[$i0] = this.cbg[$i2];
            this.cbg[$i2] = $b1;
        }
        this.cbh = 0;
        this.cbi = 0;
    }

    public void zzbh(byte[] $r1) throws  {
        int $i0 = this.cbh;
        int $i1 = this.cbi;
        for (int $i2 = 0; $i2 < $r1.length; $i2++) {
            $i0 = ($i0 + 1) & 255;
            $i1 = ($i1 + this.cbg[$i0]) & 255;
            byte $b4 = this.cbg[$i0];
            this.cbg[$i0] = this.cbg[$i1];
            this.cbg[$i1] = $b4;
            $r1[$i2] = (byte) ($r1[$i2] ^ this.cbg[(this.cbg[$i0] + this.cbg[$i1]) & 255]);
        }
        this.cbh = $i0;
        this.cbi = $i1;
    }
}
