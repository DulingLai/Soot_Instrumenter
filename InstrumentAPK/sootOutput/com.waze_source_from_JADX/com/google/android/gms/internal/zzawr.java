package com.google.android.gms.internal;

import java.io.IOException;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ReadOnlyBufferException;

/* compiled from: dalvik_source_com.waze.apk */
public final class zzawr {
    private final ByteBuffer cbs;

    /* compiled from: dalvik_source_com.waze.apk */
    public static class zza extends IOException {
        zza(int $i0, int $i1) throws  {
            super("CodedOutputStream was writing to a flat byte array and ran out of space (pos " + $i0 + " limit " + $i1 + ").");
        }
    }

    private zzawr(ByteBuffer $r1) throws  {
        this.cbs = $r1;
        this.cbs.order(ByteOrder.LITTLE_ENDIAN);
    }

    private zzawr(byte[] $r1, int $i0, int $i1) throws  {
        this(ByteBuffer.wrap($r1, $i0, $i1));
    }

    private static int zza(CharSequence $r0, int $i0) throws  {
        int $i1 = $r0.length();
        int $i2 = 0;
        while ($i0 < $i1) {
            char $c3 = $r0.charAt($i0);
            if ($c3 < 'ࠀ') {
                $i2 += (127 - $c3) >>> 31;
            } else {
                $i2 += 2;
                if ('?' <= $c3 && $c3 <= '?') {
                    if (Character.codePointAt($r0, $i0) < 65536) {
                        throw new IllegalArgumentException("Unpaired surrogate at index " + $i0);
                    }
                    $i0++;
                }
            }
            $i0++;
        }
        return $i2;
    }

    private static int zza(CharSequence $r0, byte[] $r1, int i, int $i1) throws  {
        int $i2 = $r0.length();
        int $i3 = 0;
        $i1 = i + $i1;
        while ($i3 < $i2 && $i3 + i < $i1) {
            char $c5 = $r0.charAt($i3);
            if ($c5 >= '') {
                break;
            }
            $r1[i + $i3] = (byte) $c5;
            $i3++;
        }
        if ($i3 == $i2) {
            return i + $i2;
        }
        int $i4 = i + $i3;
        while ($i3 < $i2) {
            int $i7;
            $c5 = $r0.charAt($i3);
            if ($c5 < '' && $i4 < $i1) {
                $i7 = $i4 + 1;
                $r1[$i4] = (byte) $c5;
            } else if ($c5 < 'ࠀ' && $i4 <= $i1 - 2) {
                i = $i4 + 1;
                $r1[$i4] = (byte) (($c5 >>> '\u0006') | 'π');
                $i7 = i + 1;
                $r1[i] = (byte) (($c5 & '?') | '');
            } else if (($c5 < '?' || '?' < $c5) && $i4 <= $i1 - 3) {
                i = $i4 + 1;
                $r1[$i4] = (byte) (($c5 >>> '\f') | 'Ǡ');
                $i4 = i + 1;
                $r1[i] = (byte) ((($c5 >>> '\u0006') & '?') | '');
                $i7 = $i4 + 1;
                $r1[$i4] = (byte) (($c5 & '?') | '');
            } else if ($i4 <= $i1 - 4) {
                if ($i3 + 1 != $r0.length()) {
                    $i3++;
                    char $c8 = $r0.charAt($i3);
                    if (Character.isSurrogatePair($c5, $c8)) {
                        i = Character.toCodePoint($c5, $c8);
                        int $i9 = $i4 + 1;
                        $r1[$i4] = (byte) ((i >>> 18) | 240);
                        $i4 = $i9 + 1;
                        $r1[$i9] = (byte) (((i >>> 12) & 63) | 128);
                        $i9 = $i4 + 1;
                        $r1[$i4] = (byte) (((i >>> 6) & 63) | 128);
                        $i7 = $i9 + 1;
                        int $i0 = (i & 63) | 128;
                        $r1[$i9] = (byte) $i0;
                    }
                }
                throw new IllegalArgumentException("Unpaired surrogate at index " + ($i3 - 1));
            } else {
                throw new ArrayIndexOutOfBoundsException("Failed writing " + $c5 + " at index " + $i4);
            }
            $i3++;
            $i4 = $i7;
        }
        return $i4;
    }

    private static void zza(CharSequence $r0, ByteBuffer $r1) throws  {
        if ($r1.isReadOnly()) {
            throw new ReadOnlyBufferException();
        } else if ($r1.hasArray()) {
            try {
                $r1.position(zza($r0, $r1.array(), $r1.arrayOffset() + $r1.position(), $r1.remaining()) - $r1.arrayOffset());
            } catch (ArrayIndexOutOfBoundsException $r4) {
                BufferOverflowException $r5 = new BufferOverflowException();
                $r5.initCause($r4);
                throw $r5;
            }
        } else {
            zzb($r0, $r1);
        }
    }

    public static int zzasb(int $i0) throws  {
        return $i0 >= 0 ? zzasg($i0) : 10;
    }

    public static int zzasc(int $i0) throws  {
        return zzasg(zzasi($i0));
    }

    public static int zzase(int $i0) throws  {
        return zzasg(zzaxc.zzba($i0, 0));
    }

    public static int zzasg(int $i0) throws  {
        return ($i0 & -128) == 0 ? 1 : ($i0 & -16384) == 0 ? 2 : (-2097152 & $i0) == 0 ? 3 : (-268435456 & $i0) == 0 ? 4 : 5;
    }

    public static int zzasi(int $i0) throws  {
        return ($i0 << 1) ^ ($i0 >> 31);
    }

    public static int zzax(int $i0, int $i1) throws  {
        return zzase($i0) + zzasb($i1);
    }

    public static int zzay(int $i0, int $i1) throws  {
        return zzase($i0) + zzasc($i1);
    }

    public static int zzb(int $i0, double $d0) throws  {
        return zzase($i0) + zzt($d0);
    }

    public static int zzb(int $i0, zzawz $r0) throws  {
        return (zzase($i0) * 2) + zzd($r0);
    }

    public static int zzb(int $i0, byte[] $r0) throws  {
        return zzase($i0) + zzbl($r0);
    }

    private static void zzb(CharSequence $r0, ByteBuffer $r1) throws  {
        int $i0 = $r0.length();
        int $i1 = 0;
        while ($i1 < $i0) {
            char $c2 = $r0.charAt($i1);
            if ($c2 < '') {
                $r1.put((byte) $c2);
            } else if ($c2 < 'ࠀ') {
                $r1.put((byte) (($c2 >>> '\u0006') | 'π'));
                $r1.put((byte) (($c2 & '?') | ''));
            } else if ($c2 < '?' || '?' < $c2) {
                $r1.put((byte) (($c2 >>> '\f') | 'Ǡ'));
                $r1.put((byte) ((($c2 >>> '\u0006') & '?') | ''));
                $r1.put((byte) (($c2 & '?') | ''));
            } else {
                if ($i1 + 1 != $r0.length()) {
                    $i1++;
                    char $c4 = $r0.charAt($i1);
                    if (Character.isSurrogatePair($c2, $c4)) {
                        int $i5 = Character.toCodePoint($c2, $c4);
                        $r1.put((byte) (($i5 >>> 18) | 240));
                        $r1.put((byte) ((($i5 >>> 12) & 63) | 128));
                        $r1.put((byte) ((($i5 >>> 6) & 63) | 128));
                        $r1.put((byte) (($i5 & 63) | 128));
                    }
                }
                throw new IllegalArgumentException("Unpaired surrogate at index " + ($i1 - 1));
            }
            $i1++;
        }
    }

    public static zzawr zzbj(byte[] $r0) throws  {
        return zzc($r0, 0, $r0.length);
    }

    public static int zzbl(byte[] $r0) throws  {
        return zzasg($r0.length) + $r0.length;
    }

    public static int zzc(int $i0, zzawz $r0) throws  {
        return zzase($i0) + zze($r0);
    }

    public static zzawr zzc(byte[] $r0, int $i0, int $i1) throws  {
        return new zzawr($r0, $i0, $i1);
    }

    public static int zzd(int $i0, float $f0) throws  {
        return zzase($i0) + zzk($f0);
    }

    public static int zzd(zzawz $r0) throws  {
        return $r0.iJ();
    }

    public static int zzdl(long $l0) throws  {
        return zzdq($l0);
    }

    public static int zzdm(long $l0) throws  {
        return zzdq($l0);
    }

    public static int zzdn(long j) throws  {
        return 8;
    }

    public static int zzdo(long $l0) throws  {
        return zzdq(zzds($l0));
    }

    public static int zzdq(long $l0) throws  {
        return (-128 & $l0) == 0 ? 1 : (-16384 & $l0) == 0 ? 2 : (-2097152 & $l0) == 0 ? 3 : (-268435456 & $l0) == 0 ? 4 : (-34359738368L & $l0) == 0 ? 5 : (-4398046511104L & $l0) == 0 ? 6 : (-562949953421312L & $l0) == 0 ? 7 : (-72057594037927936L & $l0) == 0 ? 8 : (Long.MIN_VALUE & $l0) == 0 ? 9 : 10;
    }

    public static long zzds(long $l0) throws  {
        return ($l0 << 1) ^ ($l0 >> 63);
    }

    public static int zze(zzawz $r0) throws  {
        int $i0 = $r0.iJ();
        return $i0 + zzasg($i0);
    }

    private static int zze(CharSequence $r0) throws  {
        int $i0 = $r0.length();
        int $i1 = 0;
        while ($i1 < $i0 && $r0.charAt($i1) < '') {
            $i1++;
        }
        int $i4 = $i0;
        while ($i1 < $i0) {
            char $c2 = $r0.charAt($i1);
            if ($c2 >= 'ࠀ') {
                $i4 += zza($r0, $i1);
                break;
            }
            $i1++;
            $i4 = ((127 - $c2) >>> 31) + $i4;
        }
        if ($i4 >= $i0) {
            return $i4;
        }
        throw new IllegalArgumentException("UTF-8 length does not fit in int: " + (((long) $i4) + 4294967296L));
    }

    public static int zzen(boolean z) throws  {
        return 1;
    }

    public static int zzi(int $i0, long $l1) throws  {
        return zzase($i0) + zzdm($l1);
    }

    public static int zzj(int $i0, long $l1) throws  {
        return zzase($i0) + zzdn($l1);
    }

    public static int zzk(float f) throws  {
        return 4;
    }

    public static int zzk(int $i0, long $l1) throws  {
        return zzase($i0) + zzdo($l1);
    }

    public static int zzn(int $i0, boolean $z0) throws  {
        return zzase($i0) + zzen($z0);
    }

    public static int zzt(double d) throws  {
        return 8;
    }

    public static int zzt(int $i0, String $r0) throws  {
        return zzase($i0) + zzyu($r0);
    }

    public static int zzyu(String $r0) throws  {
        int $i0 = zze((CharSequence) $r0);
        return $i0 + zzasg($i0);
    }

    public int iv() throws  {
        return this.cbs.remaining();
    }

    public void iw() throws  {
        if (iv() != 0) {
            throw new IllegalStateException("Did not write as much data as expected.");
        }
    }

    public void zza(int $i0, double $d0) throws IOException {
        zzaz($i0, 1);
        zzs($d0);
    }

    public void zza(int $i0, zzawz $r1) throws IOException {
        zzaz($i0, 2);
        zzc($r1);
    }

    public void zza(int $i0, byte[] $r1) throws IOException {
        zzaz($i0, 2);
        zzbk($r1);
    }

    public void zzarz(int $i0) throws IOException {
        if ($i0 >= 0) {
            zzasf($i0);
        } else {
            zzdp((long) $i0);
        }
    }

    public void zzasa(int $i0) throws IOException {
        zzasf(zzasi($i0));
    }

    public void zzasd(int $i0) throws IOException {
        zzd((byte) $i0);
    }

    public void zzasf(int $i0) throws IOException {
        while (($i0 & -128) != 0) {
            zzasd(($i0 & 127) | 128);
            $i0 >>>= 7;
        }
        zzasd($i0);
    }

    public void zzash(int $i0) throws IOException {
        if (this.cbs.remaining() < 4) {
            throw new zza(this.cbs.position(), this.cbs.limit());
        }
        this.cbs.putInt($i0);
    }

    public void zzav(int $i0, int $i1) throws IOException {
        zzaz($i0, 0);
        zzarz($i1);
    }

    public void zzaw(int $i0, int $i1) throws IOException {
        zzaz($i0, 0);
        zzasa($i1);
    }

    public void zzaz(int $i0, int $i1) throws IOException {
        zzasf(zzaxc.zzba($i0, $i1));
    }

    public void zzb(zzawz $r1) throws IOException {
        $r1.writeTo(this);
    }

    public void zzbk(byte[] $r1) throws IOException {
        zzasf($r1.length);
        zzbm($r1);
    }

    public void zzbm(byte[] $r1) throws IOException {
        zzd($r1, 0, $r1.length);
    }

    public void zzc(int $i0, float $f0) throws IOException {
        zzaz($i0, 5);
        zzj($f0);
    }

    public void zzc(zzawz $r1) throws IOException {
        zzasf($r1.iI());
        $r1.writeTo(this);
    }

    public void zzd(byte $b0) throws IOException {
        if (this.cbs.hasRemaining()) {
            this.cbs.put($b0);
            return;
        }
        throw new zza(this.cbs.position(), this.cbs.limit());
    }

    public void zzd(byte[] $r1, int $i0, int $i1) throws IOException {
        if (this.cbs.remaining() >= $i1) {
            this.cbs.put($r1, $i0, $i1);
            return;
        }
        throw new zza(this.cbs.position(), this.cbs.limit());
    }

    public void zzdh(long $l0) throws IOException {
        zzdp($l0);
    }

    public void zzdi(long $l0) throws IOException {
        zzdp($l0);
    }

    public void zzdj(long $l0) throws IOException {
        zzdr($l0);
    }

    public void zzdk(long $l0) throws IOException {
        zzdp(zzds($l0));
    }

    public void zzdp(long $l0) throws IOException {
        while ((-128 & $l0) != 0) {
            zzasd((((int) $l0) & 127) | 128);
            $l0 >>>= 7;
        }
        zzasd((int) $l0);
    }

    public void zzdr(long $l0) throws IOException {
        if (this.cbs.remaining() < 8) {
            throw new zza(this.cbs.position(), this.cbs.limit());
        }
        this.cbs.putLong($l0);
    }

    public void zze(int $i0, long $l1) throws IOException {
        zzaz($i0, 0);
        zzdh($l1);
    }

    public void zzem(boolean $z0) throws IOException {
        zzasd($z0 ? (byte) 1 : (byte) 0);
    }

    public void zzf(int $i0, long $l1) throws IOException {
        zzaz($i0, 0);
        zzdi($l1);
    }

    public void zzg(int $i0, long $l1) throws IOException {
        zzaz($i0, 1);
        zzdj($l1);
    }

    public void zzh(int $i0, long $l1) throws IOException {
        zzaz($i0, 0);
        zzdk($l1);
    }

    public void zzj(float $f0) throws IOException {
        zzash(Float.floatToIntBits($f0));
    }

    public void zzm(int $i0, boolean $z0) throws IOException {
        zzaz($i0, 0);
        zzem($z0);
    }

    public void zzs(double $d0) throws IOException {
        zzdr(Double.doubleToLongBits($d0));
    }

    public void zzs(int $i0, String $r1) throws IOException {
        zzaz($i0, 2);
        zzyt($r1);
    }

    public void zzyt(String $r1) throws IOException {
        try {
            int $i0 = zzasg($r1.length());
            if ($i0 == zzasg($r1.length() * 3)) {
                int $i1 = this.cbs.position();
                if (this.cbs.remaining() < $i0) {
                    throw new zza($i0 + $i1, this.cbs.limit());
                }
                this.cbs.position($i1 + $i0);
                zza((CharSequence) $r1, this.cbs);
                int $i2 = this.cbs.position();
                this.cbs.position($i1);
                zzasf(($i2 - $i1) - $i0);
                this.cbs.position($i2);
                return;
            }
            zzasf(zze((CharSequence) $r1));
            zza((CharSequence) $r1, this.cbs);
        } catch (BufferOverflowException $r4) {
            zza $r3 = new zza(this.cbs.position(), this.cbs.limit());
            $r3.initCause($r4);
            throw $r3;
        }
    }
}
