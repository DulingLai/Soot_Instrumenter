package com.google.android.gms.location;

import android.content.Intent;
import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.NonNull;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import dalvik.annotation.Signature;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* compiled from: dalvik_source_com.waze.apk */
public final class LocationResult extends AbstractSafeParcelable {
    public static final Creator<LocationResult> CREATOR = new zzg();
    static final List<Location> avj = Collections.emptyList();
    private final List<Location> avk;
    private final int mVersionCode;

    LocationResult(@Signature({"(I", "Ljava/util/List", "<", "Landroid/location/Location;", ">;)V"}) int $i0, @Signature({"(I", "Ljava/util/List", "<", "Landroid/location/Location;", ">;)V"}) List<Location> $r1) throws  {
        this.mVersionCode = $i0;
        this.avk = $r1;
    }

    public static LocationResult create(@Signature({"(", "Ljava/util/List", "<", "Landroid/location/Location;", ">;)", "Lcom/google/android/gms/location/LocationResult;"}) List<Location> $r1) throws  {
        List $r12;
        if ($r1 == null) {
            $r12 = avj;
        }
        return new LocationResult(2, $r12);
    }

    public static LocationResult extractResult(Intent $r0) throws  {
        return !hasResult($r0) ? null : (LocationResult) $r0.getExtras().getParcelable("com.google.android.gms.location.EXTRA_LOCATION_RESULT");
    }

    public static boolean hasResult(Intent $r0) throws  {
        return $r0 == null ? false : $r0.hasExtra("com.google.android.gms.location.EXTRA_LOCATION_RESULT");
    }

    public boolean equals(Object $r1) throws  {
        boolean $z0 = $r1;
        $r1 = $z0;
        if (!($z0 instanceof LocationResult)) {
            return false;
        }
        LocationResult $r2 = (LocationResult) $r1;
        if ($r2.avk.size() != this.avk.size()) {
            return false;
        }
        Iterator $r5 = this.avk.iterator();
        for (Location $r7 : $r2.avk) {
            if (((Location) $r5.next()).getTime() != $r7.getTime()) {
                return false;
            }
        }
        return true;
    }

    public Location getLastLocation() throws  {
        int $i0 = this.avk.size();
        return $i0 == 0 ? null : (Location) this.avk.get($i0 - 1);
    }

    @NonNull
    public List<Location> getLocations() throws  {
        return this.avk;
    }

    int getVersionCode() throws  {
        return this.mVersionCode;
    }

    public int hashCode() throws  {
        int $i1 = 17;
        for (Location time : this.avk) {
            long $l2 = time.getTime();
            $i1 = ((int) ($l2 ^ ($l2 >>> 32))) + ($i1 * 31);
        }
        return $i1;
    }

    public String toString() throws  {
        String $r2 = String.valueOf(this.avk);
        return new StringBuilder(String.valueOf($r2).length() + 27).append("LocationResult[locations: ").append($r2).append("]").toString();
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        zzg.zza(this, $r1, $i0);
    }
}
