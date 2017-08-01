package com.google.android.gms.location.internal;

import android.annotation.SuppressLint;
import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.location.Geofence;
import java.util.Locale;

/* compiled from: dalvik_source_com.waze.apk */
public class ParcelableGeofence extends AbstractSafeParcelable implements Geofence {
    public static final zzo CREATOR = new zzo();
    private final int auD;
    private final short auF;
    private final double auG;
    private final double auH;
    private final float auI;
    private final int auJ;
    private final int auK;
    private final long awn;
    private final int mVersionCode;
    private final String zzbvk;

    public ParcelableGeofence(int $i0, String $r1, int $i1, short $s2, double $d0, double $d1, float $f0, long $l3, int $i4, int $i5) throws  {
        zzkj($r1);
        zze($f0);
        zze($d0, $d1);
        $i1 = zzww($i1);
        this.mVersionCode = $i0;
        this.auF = $s2;
        this.zzbvk = $r1;
        this.auG = $d0;
        this.auH = $d1;
        this.auI = $f0;
        this.awn = $l3;
        this.auD = $i1;
        this.auJ = $i4;
        this.auK = $i5;
    }

    public ParcelableGeofence(String $r1, int $i0, short $s1, double $d0, double $d1, float $f0, long $l2, int $i3, int $i4) throws  {
        this(1, $r1, $i0, $s1, $d0, $d1, $f0, $l2, $i3, $i4);
    }

    public static ParcelableGeofence zzai(byte[] $r0) throws  {
        Parcel $r1 = Parcel.obtain();
        $r1.unmarshall($r0, 0, $r0.length);
        $r1.setDataPosition(0);
        ParcelableGeofence $r4 = (ParcelableGeofence) CREATOR.createFromParcel($r1);
        $r1.recycle();
        return $r4;
    }

    private static void zze(double $d0, double $d1) throws  {
        if ($d0 > 90.0d || $d0 < -90.0d) {
            throw new IllegalArgumentException("invalid latitude: " + $d0);
        } else if ($d1 > 180.0d || $d1 < -180.0d) {
            throw new IllegalArgumentException("invalid longitude: " + $d1);
        }
    }

    private static void zze(float $f0) throws  {
        if ($f0 <= 0.0f) {
            throw new IllegalArgumentException("invalid radius: " + $f0);
        }
    }

    private static void zzkj(String $r0) throws  {
        if ($r0 == null || $r0.length() > 100) {
            String $r1 = "requestId is null or too long: ";
            $r0 = String.valueOf($r0);
            throw new IllegalArgumentException($r0.length() != 0 ? $r1.concat($r0) : new String("requestId is null or too long: "));
        }
    }

    private static int zzww(int $i0) throws  {
        int $i1 = $i0 & 7;
        if ($i1 != 0) {
            return $i1;
        }
        throw new IllegalArgumentException("No supported transition specified: " + $i0);
    }

    @SuppressLint({"DefaultLocale"})
    private static String zzwx(int $i0) throws  {
        switch ($i0) {
            case 1:
                return "CIRCLE";
            default:
                return null;
        }
    }

    public boolean equals(Object $r1) throws  {
        if (this == $r1) {
            return true;
        }
        if ($r1 == null) {
            return false;
        }
        if (!($r1 instanceof ParcelableGeofence)) {
            return false;
        }
        ParcelableGeofence $r2 = (ParcelableGeofence) $r1;
        return this.auI != $r2.auI ? false : this.auG != $r2.auG ? false : this.auH != $r2.auH ? false : this.auF == $r2.auF;
    }

    public long getExpirationTime() throws  {
        return this.awn;
    }

    public double getLatitude() throws  {
        return this.auG;
    }

    public double getLongitude() throws  {
        return this.auH;
    }

    public String getRequestId() throws  {
        return this.zzbvk;
    }

    public int getTransitionTypes() throws  {
        return this.auD;
    }

    public int getVersionCode() throws  {
        return this.mVersionCode;
    }

    public int hashCode() throws  {
        long $l0 = Double.doubleToLongBits(this.auG);
        int $i2 = ((int) ($l0 ^ ($l0 >>> 32))) + 31;
        $l0 = Double.doubleToLongBits(this.auH);
        return ((((((($i2 * 31) + ((int) ($l0 ^ ($l0 >>> 32)))) * 31) + Float.floatToIntBits(this.auI)) * 31) + this.auF) * 31) + this.auD;
    }

    public String toString() throws  {
        return String.format(Locale.US, "Geofence[%s id:%s transitions:%d %.6f, %.6f %.0fm, resp=%ds, dwell=%dms, @%d]", new Object[]{zzwx(this.auF), this.zzbvk, Integer.valueOf(this.auD), Double.valueOf(this.auG), Double.valueOf(this.auH), Float.valueOf(this.auI), Integer.valueOf(this.auJ / 1000), Integer.valueOf(this.auK), Long.valueOf(this.awn)});
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        zzo $r2 = CREATOR;
        zzo.zza(this, $r1, $i0);
    }

    public short zzbso() throws  {
        return this.auF;
    }

    public float zzbsp() throws  {
        return this.auI;
    }

    public int zzbsq() throws  {
        return this.auJ;
    }

    public int zzbsr() throws  {
        return this.auK;
    }
}
