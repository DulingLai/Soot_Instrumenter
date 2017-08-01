package com.google.android.gms.location;

import android.content.Intent;
import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.zzaa;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* compiled from: dalvik_source_com.waze.apk */
public final class LocationAvailability extends AbstractSafeParcelable {
    public static final LocationAvailabilityCreator CREATOR = new LocationAvailabilityCreator();
    public static final int STATUS_INVALID_SCAN = 4;
    @Deprecated
    public static final int STATUS_IN_PROGRESS = 8;
    public static final int STATUS_LOCATION_DISABLED_IN_SETTINGS = 7;
    public static final int STATUS_NO_INFO_IN_DATABASE = 3;
    public static final int STATUS_SCANS_DISABLED_IN_SETTINGS = 6;
    public static final int STATUS_TIMED_OUT_ON_SCAN = 2;
    public static final int STATUS_UNABLE_TO_QUERY_DATABASE = 5;
    public static final int STATUS_UNKNOWN = 1;
    int ava;
    int avb;
    long avc;
    int avd;
    private final int mVersionCode;

    @Retention(RetentionPolicy.SOURCE)
    /* compiled from: dalvik_source_com.waze.apk */
    public @interface NetworkLocationStatusCode {
    }

    LocationAvailability(int $i0, int $i1, int $i2, int $i3, long $l4) throws  {
        this.mVersionCode = $i0;
        this.avd = $i1;
        this.ava = $i2;
        this.avb = $i3;
        this.avc = $l4;
    }

    public static LocationAvailability create(int $i0, int $i1, int $i2, long $l3) throws  {
        return new LocationAvailability(2, $i0, $i1, $i2, $l3);
    }

    public static LocationAvailability extractLocationAvailability(Intent $r0) throws  {
        return !hasLocationAvailability($r0) ? null : (LocationAvailability) $r0.getExtras().getParcelable("com.google.android.gms.location.EXTRA_LOCATION_AVAILABILITY");
    }

    public static boolean hasLocationAvailability(Intent $r0) throws  {
        return $r0 == null ? false : $r0.hasExtra("com.google.android.gms.location.EXTRA_LOCATION_AVAILABILITY");
    }

    public boolean equals(Object $r1) throws  {
        if (!($r1 instanceof LocationAvailability)) {
            return false;
        }
        LocationAvailability $r2 = (LocationAvailability) $r1;
        return this.avd == $r2.avd ? this.ava == $r2.ava ? this.avb == $r2.avb ? this.avc == $r2.avc : false : false : false;
    }

    public int getCellStatus() throws  {
        return this.ava;
    }

    public long getElapsedRealtimeNs() throws  {
        return this.avc;
    }

    int getVersionCode() throws  {
        return this.mVersionCode;
    }

    public int getWifiStatus() throws  {
        return this.avb;
    }

    public int hashCode() throws  {
        return zzaa.hashCode(Integer.valueOf(this.avd), Integer.valueOf(this.ava), Integer.valueOf(this.avb), Long.valueOf(this.avc));
    }

    public boolean isLocationAvailable() throws  {
        return this.avd < 1000;
    }

    public String toString() throws  {
        return "LocationAvailability[isLocationAvailable: " + isLocationAvailable() + "]";
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        LocationAvailabilityCreator.zza(this, $r1, $i0);
    }
}
