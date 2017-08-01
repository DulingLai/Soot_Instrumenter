package com.google.android.gms.location;

import android.location.Location;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.Nullable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.zzc;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: dalvik_source_com.waze.apk */
public final class WifiScan extends AbstractSafeParcelable {
    public static final Creator<WifiScan> CREATOR = new zzl();
    static final long[] avv = new long[0];
    private final long avw;
    final long[] avx;
    private final int mVersionCode;

    /* compiled from: dalvik_source_com.waze.apk */
    public static class Builder {
        private static final AtomicReference<Builder> avy = new AtomicReference();
        private long LR;
        private int avA;
        private long[] avz;

        private Builder(int $i0, long $l1) throws  {
            zzd($i0, $l1);
        }

        public static Builder create(int $i0, long $l1) throws  {
            Builder $r2 = (Builder) avy.getAndSet(null);
            if ($r2 == null) {
                return new Builder($i0, $l1);
            }
            $r2.zzd($i0, $l1);
            return $r2;
        }

        private void zzd(int $i0, long $l1) throws  {
            this.LR = $l1;
            this.avz = new long[$i0];
            this.avA = 0;
        }

        public Builder addDevice(long $l0, byte $b1) throws  {
            if (this.avA >= this.avz.length) {
                throw new IllegalStateException("Builder is full, have already added devices to capacity");
            }
            this.avz[this.avA] = (((long) $b1) << 48) | $l0;
            this.avA++;
            return this;
        }

        public WifiScan build() throws  {
            if (this.avA != this.avz.length) {
                int $i0 = this.avz.length;
                throw new IllegalStateException("Haven't filled devices yet, expected " + $i0 + " but received " + this.avA);
            }
            WifiScan $r5 = new WifiScan(1, this.LR, this.avz);
            this.avz = null;
            avy.set(this);
            return $r5;
        }
    }

    WifiScan(int $i0, long $l1, long[] $r1) throws  {
        this.avw = $l1;
        this.mVersionCode = $i0;
        if ($r1 == null) {
            $r1 = avv;
        }
        this.avx = $r1;
    }

    @Nullable
    public static WifiScan fromLocation(Location $r0) throws  {
        Bundle $r1 = $r0.getExtras();
        if ($r1 != null) {
            byte[] $r2 = $r1.getByteArray("wifiScan");
            if ($r2 != null) {
                return zzah($r2);
            }
        }
        return null;
    }

    public static WifiScan zzah(byte[] $r0) throws  {
        return (WifiScan) zzc.zza($r0, CREATOR);
    }

    private void zzwp(int $i0) throws  {
        if ($i0 < 0 || $i0 >= getNumDevices()) {
            throw new IndexOutOfBoundsException("Index " + $i0 + " out of bounds: [0, " + getNumDevices() + ")");
        }
    }

    public boolean equals(Object $r1) throws  {
        if (!($r1 instanceof WifiScan)) {
            return false;
        }
        WifiScan $r2 = (WifiScan) $r1;
        return $r2.avw == this.avw ? Arrays.equals($r2.avx, this.avx) : false;
    }

    public long getElapsedRealtimeMs() throws  {
        return this.avw;
    }

    public long getMac(int $i0) throws  {
        zzwp($i0);
        return this.avx[$i0] & 281474976710655L;
    }

    public int getNumDevices() throws  {
        return this.avx.length;
    }

    public byte getPowerLevelDbm(int $i0) throws  {
        zzwp($i0);
        return (byte) ((int) ((this.avx[$i0] & 71776119061217280L) >>> 48));
    }

    int getVersionCode() throws  {
        return this.mVersionCode;
    }

    public int hashCode() throws  {
        return Arrays.hashCode(this.avx);
    }

    public String toString() throws  {
        StringBuilder $r1 = new StringBuilder("WifiScan[elapsed rt: ");
        $r1.append(this.avw);
        int $i1 = getNumDevices();
        for (int $i2 = 0; $i2 < $i1; $i2++) {
            $r1.append(", Device[mac: ").append(getMac($i2));
            $r1.append(", power [dbm]: ").append(getPowerLevelDbm($i2));
            if ($i2 < $i1 - 1) {
                $r1.append("], ");
            } else {
                $r1.append("]");
            }
        }
        $r1.append("]");
        return $r1.toString();
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        zzl.zza(this, $r1, $i0);
    }
}
