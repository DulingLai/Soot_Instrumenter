package com.google.android.gms.internal;

import java.io.IOException;

/* compiled from: dalvik_source_com.waze.apk */
public final class zzawq {
    private final byte[] buffer;
    private int cbj;
    private int cbk;
    private int cbl;
    private int cbm;
    private int cbn;
    private int cbo = ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
    private int cbp;
    private int cbq = 64;
    private int cbr = 67108864;

    private zzawq(byte[] $r1, int $i0, int $i1) throws  {
        this.buffer = $r1;
        this.cbj = $i0;
        this.cbk = $i0 + $i1;
        this.cbm = $i0;
    }

    private void ir() throws  {
        this.cbk += this.cbl;
        int $i0 = this.cbk;
        if ($i0 > this.cbo) {
            this.cbl = $i0 - this.cbo;
            this.cbk -= this.cbl;
            return;
        }
        this.cbl = 0;
    }

    public static int zzaru(int $i0) throws  {
        return ($i0 >>> 1) ^ (-($i0 & 1));
    }

    public static zzawq zzb(byte[] $r0, int $i0, int $i1) throws  {
        return new zzawq($r0, $i0, $i1);
    }

    public static zzawq zzbi(byte[] $r0) throws  {
        return zzb($r0, 0, $r0.length);
    }

    public static long zzdg(long $l0) throws  {
        return ($l0 >>> 1) ^ (-(1 & $l0));
    }

    public int getPosition() throws  {
        return this.cbm - this.cbj;
    }

    public int ie() throws IOException {
        if (it()) {
            this.cbn = 0;
            return 0;
        }
        this.cbn = in();
        if (this.cbn != 0) {
            return this.cbn;
        }
        throw zzawy.iE();
    }

    public void m19if() throws IOException {
        int $i0;
        do {
            $i0 = ie();
            if ($i0 == 0) {
                return;
            }
        } while (zzart($i0));
    }

    public long ig() throws IOException {
        return io();
    }

    public long ih() throws IOException {
        return io();
    }

    public int ii() throws IOException {
        return in();
    }

    public long ij() throws IOException {
        return iq();
    }

    public boolean ik() throws IOException {
        return in() != 0;
    }

    public int il() throws IOException {
        return zzaru(in());
    }

    public long im() throws IOException {
        return zzdg(io());
    }

    public int in() throws IOException {
        byte $b0 = iu();
        if ($b0 >= (byte) 0) {
            return $b0;
        }
        $b0 &= Byte.MAX_VALUE;
        byte $b2 = iu();
        if ($b2 >= (byte) 0) {
            return $b0 | ($b2 << 7);
        }
        int $i1 = $b0 | (($b2 & Byte.MAX_VALUE) << 7);
        $b0 = iu();
        if ($b0 >= (byte) 0) {
            return $i1 | ($b0 << 14);
        }
        $i1 |= ($b0 & Byte.MAX_VALUE) << 14;
        $b0 = iu();
        if ($b0 >= (byte) 0) {
            return $i1 | ($b0 << 21);
        }
        $i1 |= ($b0 & Byte.MAX_VALUE) << 21;
        $b0 = iu();
        $i1 |= $b0 << 28;
        if ($b0 >= (byte) 0) {
            return $i1;
        }
        for (int $i3 = 0; $i3 < 5; $i3++) {
            if (iu() >= (byte) 0) {
                return $i1;
            }
        }
        throw zzawy.iD();
    }

    public long io() throws IOException {
        long $l1 = 0;
        for (int $i0 = 0; $i0 < 64; $i0 += 7) {
            byte $b2 = iu();
            $l1 |= ((long) ($b2 & Byte.MAX_VALUE)) << $i0;
            if (($b2 & (short) 128) == (short) 0) {
                return $l1;
            }
        }
        throw zzawy.iD();
    }

    public int ip() throws IOException {
        return (((iu() & (short) 255) | ((iu() & (short) 255) << 8)) | ((iu() & (short) 255) << 16)) | ((iu() & (short) 255) << 24);
    }

    public long iq() throws IOException {
        byte $b0 = iu();
        byte $b1 = iu();
        return ((((((((((long) $b1) & 255) << 8) | (((long) $b0) & 255)) | ((((long) iu()) & 255) << 16)) | ((((long) iu()) & 255) << 24)) | ((((long) iu()) & 255) << 32)) | ((((long) iu()) & 255) << 40)) | ((((long) iu()) & 255) << 48)) | ((((long) iu()) & 255) << 56);
    }

    public int is() throws  {
        if (this.cbo == ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED) {
            return -1;
        }
        return this.cbo - this.cbm;
    }

    public boolean it() throws  {
        return this.cbm == this.cbk;
    }

    public byte iu() throws IOException {
        if (this.cbm == this.cbk) {
            throw zzawy.iB();
        }
        byte[] $r2 = this.buffer;
        int $i1 = this.cbm;
        this.cbm = $i1 + 1;
        return $r2[$i1];
    }

    public byte[] readBytes() throws IOException {
        int $i1 = in();
        if ($i1 < 0) {
            throw zzawy.iC();
        } else if ($i1 == 0) {
            return zzaxc.cbL;
        } else {
            if ($i1 > this.cbk - this.cbm) {
                throw zzawy.iB();
            }
            byte[] $r2 = new byte[$i1];
            System.arraycopy(this.buffer, this.cbm, $r2, 0, $i1);
            this.cbm = $i1 + this.cbm;
            return $r2;
        }
    }

    public double readDouble() throws IOException {
        return Double.longBitsToDouble(iq());
    }

    public float readFloat() throws IOException {
        return Float.intBitsToFloat(ip());
    }

    public String readString() throws IOException {
        int $i1 = in();
        if ($i1 < 0) {
            throw zzawy.iC();
        } else if ($i1 > this.cbk - this.cbm) {
            throw zzawy.iB();
        } else {
            String $r3 = new String(this.buffer, this.cbm, $i1, zzawx.UTF_8);
            this.cbm = $i1 + this.cbm;
            return $r3;
        }
    }

    public void zza(zzawz $r1) throws IOException {
        int $i1 = in();
        if (this.cbp >= this.cbq) {
            throw zzawy.iH();
        }
        int $i0 = zzarv($i1);
        this.cbp++;
        $r1.mergeFrom(this);
        zzars(0);
        this.cbp--;
        zzarw($i0);
    }

    public void zza(zzawz $r1, int $i0) throws IOException {
        if (this.cbp >= this.cbq) {
            throw zzawy.iH();
        }
        this.cbp++;
        $r1.mergeFrom(this);
        zzars(zzaxc.zzba($i0, 4));
        this.cbp--;
    }

    public void zzars(int $i0) throws zzawy {
        if (this.cbn != $i0) {
            throw zzawy.iF();
        }
    }

    public boolean zzart(int $i0) throws IOException {
        switch (zzaxc.zzasn($i0)) {
            case 0:
                ii();
                return true;
            case 1:
                iq();
                return true;
            case 2:
                zzary(in());
                return true;
            case 3:
                m19if();
                zzars(zzaxc.zzba(zzaxc.zzaso($i0), 4));
                return true;
            case 4:
                return false;
            case 5:
                ip();
                return true;
            default:
                throw zzawy.iG();
        }
    }

    public int zzarv(int $i0) throws zzawy {
        if ($i0 < 0) {
            throw zzawy.iC();
        }
        int $i1 = this.cbm + $i0;
        $i0 = this.cbo;
        if ($i1 > $i0) {
            throw zzawy.iB();
        }
        this.cbo = $i1;
        ir();
        return $i0;
    }

    public void zzarw(int $i0) throws  {
        this.cbo = $i0;
        ir();
    }

    public void zzarx(int $i0) throws  {
        if ($i0 > this.cbm - this.cbj) {
            throw new IllegalArgumentException("Position " + $i0 + " is beyond current " + (this.cbm - this.cbj));
        } else if ($i0 < 0) {
            throw new IllegalArgumentException("Bad position " + $i0);
        } else {
            this.cbm = this.cbj + $i0;
        }
    }

    public void zzary(int $i0) throws IOException {
        if ($i0 < 0) {
            throw zzawy.iC();
        } else if (this.cbm + $i0 > this.cbo) {
            zzary(this.cbo - this.cbm);
            throw zzawy.iB();
        } else if ($i0 <= this.cbk - this.cbm) {
            this.cbm += $i0;
        } else {
            throw zzawy.iB();
        }
    }

    public byte[] zzau(int $i0, int $i1) throws  {
        if ($i1 == 0) {
            return zzaxc.cbL;
        }
        byte[] $r2 = new byte[$i1];
        System.arraycopy(this.buffer, this.cbj + $i0, $r2, 0, $i1);
        return $r2;
    }
}
