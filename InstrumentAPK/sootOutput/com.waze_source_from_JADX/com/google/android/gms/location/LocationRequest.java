package com.google.android.gms.location;

import android.os.Parcel;
import android.os.SystemClock;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.zzaa;

/* compiled from: dalvik_source_com.waze.apk */
public final class LocationRequest extends AbstractSafeParcelable {
    public static final LocationRequestCreator CREATOR = new LocationRequestCreator();
    public static final int PRIORITY_BALANCED_POWER_ACCURACY = 102;
    public static final int PRIORITY_HIGH_ACCURACY = 100;
    public static final int PRIORITY_LOW_POWER = 104;
    public static final int PRIORITY_NO_POWER = 105;
    boolean afs;
    long auE;
    long ave;
    long avf;
    int avg;
    float avh;
    long avi;
    int mPriority;
    private final int mVersionCode;

    public LocationRequest() throws  {
        this.mVersionCode = 1;
        this.mPriority = 102;
        this.ave = 3600000;
        this.avf = 600000;
        this.afs = false;
        this.auE = Long.MAX_VALUE;
        this.avg = ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
        this.avh = 0.0f;
        this.avi = 0;
    }

    LocationRequest(int $i0, int $i1, long $l2, long $l3, boolean $z0, long $l4, int $i5, float $f0, long $l6) throws  {
        this.mVersionCode = $i0;
        this.mPriority = $i1;
        this.ave = $l2;
        this.avf = $l3;
        this.afs = $z0;
        this.auE = $l4;
        this.avg = $i5;
        this.avh = $f0;
        this.avi = $l6;
    }

    public static LocationRequest create() throws  {
        return new LocationRequest();
    }

    private static void zzbb(long $l0) throws  {
        if ($l0 < 0) {
            throw new IllegalArgumentException("invalid interval: " + $l0);
        }
    }

    private static void zzd(float $f0) throws  {
        if ($f0 < 0.0f) {
            throw new IllegalArgumentException("invalid displacement: " + $f0);
        }
    }

    private static void zzwg(int $i0) throws  {
        switch ($i0) {
            case 100:
            case 102:
            case 104:
            case 105:
                return;
            case 101:
            case 103:
                break;
            default:
                break;
        }
        throw new IllegalArgumentException("invalid quality: " + $i0);
    }

    public static String zzwh(int $i0) throws  {
        switch ($i0) {
            case 100:
                return "PRIORITY_HIGH_ACCURACY";
            case 101:
            case 103:
                break;
            case 102:
                return "PRIORITY_BALANCED_POWER_ACCURACY";
            case 104:
                return "PRIORITY_LOW_POWER";
            case 105:
                return "PRIORITY_NO_POWER";
            default:
                break;
        }
        return "???";
    }

    public boolean equals(Object $r1) throws  {
        if (this == $r1) {
            return true;
        }
        if (!($r1 instanceof LocationRequest)) {
            return false;
        }
        LocationRequest $r2 = (LocationRequest) $r1;
        return this.mPriority == $r2.mPriority && this.ave == $r2.ave && this.avf == $r2.avf && this.afs == $r2.afs && this.auE == $r2.auE && this.avg == $r2.avg && this.avh == $r2.avh;
    }

    public long getExpirationTime() throws  {
        return this.auE;
    }

    public long getFastestInterval() throws  {
        return this.avf;
    }

    public long getInterval() throws  {
        return this.ave;
    }

    public long getMaxWaitTime() throws  {
        long $l0 = this.avi;
        return $l0 < this.ave ? this.ave : $l0;
    }

    public int getNumUpdates() throws  {
        return this.avg;
    }

    public int getPriority() throws  {
        return this.mPriority;
    }

    public float getSmallestDisplacement() throws  {
        return this.avh;
    }

    int getVersionCode() throws  {
        return this.mVersionCode;
    }

    public int hashCode() throws  {
        return zzaa.hashCode(Integer.valueOf(this.mPriority), Long.valueOf(this.ave), Long.valueOf(this.avf), Boolean.valueOf(this.afs), Long.valueOf(this.auE), Integer.valueOf(this.avg), Float.valueOf(this.avh));
    }

    public LocationRequest setExpirationDuration(long $l0) throws  {
        long $l1 = SystemClock.elapsedRealtime();
        if ($l0 > Long.MAX_VALUE - $l1) {
            this.auE = Long.MAX_VALUE;
        } else {
            this.auE = $l1 + $l0;
        }
        if (this.auE >= 0) {
            return this;
        }
        this.auE = 0;
        return this;
    }

    public LocationRequest setExpirationTime(long $l0) throws  {
        this.auE = $l0;
        if (this.auE >= 0) {
            return this;
        }
        this.auE = 0;
        return this;
    }

    public LocationRequest setFastestInterval(long $l0) throws  {
        zzbb($l0);
        this.afs = true;
        this.avf = $l0;
        return this;
    }

    public LocationRequest setInterval(long $l0) throws  {
        zzbb($l0);
        this.ave = $l0;
        if (this.afs) {
            return this;
        }
        this.avf = (long) (((double) this.ave) / 6.0d);
        return this;
    }

    public LocationRequest setMaxWaitTime(long $l0) throws  {
        zzbb($l0);
        this.avi = $l0;
        return this;
    }

    public LocationRequest setNumUpdates(int $i0) throws  {
        if ($i0 <= 0) {
            throw new IllegalArgumentException("invalid numUpdates: " + $i0);
        }
        this.avg = $i0;
        return this;
    }

    public LocationRequest setPriority(int $i0) throws  {
        zzwg($i0);
        this.mPriority = $i0;
        return this;
    }

    public LocationRequest setSmallestDisplacement(float $f0) throws  {
        zzd($f0);
        this.avh = $f0;
        return this;
    }

    public String toString() throws  {
        StringBuilder $r1 = new StringBuilder();
        $r1.append("Request[").append(zzwh(this.mPriority));
        if (this.mPriority != 105) {
            $r1.append(" requested=");
            $r1.append(this.ave).append("ms");
        }
        $r1.append(" fastest=");
        $r1.append(this.avf).append("ms");
        if (this.avi > this.ave) {
            $r1.append(" maxWait=");
            $r1.append(this.avi).append("ms");
        }
        if (this.auE != Long.MAX_VALUE) {
            long $l1 = this.auE - SystemClock.elapsedRealtime();
            $r1.append(" expireIn=");
            $r1.append($l1).append("ms");
        }
        if (this.avg != ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED) {
            $r1.append(" num=").append(this.avg);
        }
        $r1.append(']');
        return $r1.toString();
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        LocationRequestCreator.zza(this, $r1, $i0);
    }
}
