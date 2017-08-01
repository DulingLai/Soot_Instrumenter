package com.google.android.gms.location.places;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.common.internal.zzaa.zza;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.gcm.nts.GcmScheduler;

/* compiled from: dalvik_source_com.waze.apk */
public class PlaceReport extends AbstractSafeParcelable {
    public static final Creator<PlaceReport> CREATOR = new zzi();
    public static final String SOURCE_INFERRED_GEOFENCING = "inferredGeofencing";
    public static final String SOURCE_INFERRED_RADIO_SIGNALS = "inferredRadioSignals";
    public static final String SOURCE_INFERRED_REVERSE_GEOCODING = "inferredReverseGeocoding";
    public static final String SOURCE_INFERRED_SNAPPED_TO_ROAD = "inferredSnappedToRoad";
    public static final String SOURCE_UNKNOWN = "unknown";
    public static final String SOURCE_USER_REPORTED = "userReported";
    private final String awU;
    private final String mTag;
    final int mVersionCode;
    private final String zzcun;

    PlaceReport(int $i0, String $r1, String $r2, String $r3) throws  {
        this.mVersionCode = $i0;
        this.awU = $r1;
        this.mTag = $r2;
        this.zzcun = $r3;
    }

    public static PlaceReport create(String $r0, String $r1) throws  {
        return create($r0, $r1, "unknown");
    }

    public static PlaceReport create(String $r0, String $r1, String $r2) throws  {
        zzab.zzag($r0);
        zzab.zzgy($r1);
        zzab.zzgy($r2);
        zzab.zzb(zzkk($r2), (Object) "Invalid source");
        return new PlaceReport(1, $r0, $r1, $r2);
    }

    private static boolean zzkk(String $r0) throws  {
        byte $b0 = (byte) -1;
        switch ($r0.hashCode()) {
            case -1436706272:
                if ($r0.equals(SOURCE_INFERRED_GEOFENCING)) {
                    $b0 = (byte) 2;
                    break;
                }
                break;
            case -1194968642:
                if ($r0.equals(SOURCE_USER_REPORTED)) {
                    $b0 = (byte) 1;
                    break;
                }
                break;
            case -284840886:
                if ($r0.equals("unknown")) {
                    $b0 = (byte) 0;
                    break;
                }
                break;
            case -262743844:
                if ($r0.equals(SOURCE_INFERRED_REVERSE_GEOCODING)) {
                    $b0 = (byte) 4;
                    break;
                }
                break;
            case 1164924125:
                if ($r0.equals(SOURCE_INFERRED_SNAPPED_TO_ROAD)) {
                    $b0 = (byte) 5;
                    break;
                }
                break;
            case 1287171955:
                if ($r0.equals(SOURCE_INFERRED_RADIO_SIGNALS)) {
                    $b0 = (byte) 3;
                    break;
                }
                break;
            default:
                break;
        }
        switch ($b0) {
            case (byte) 0:
            case (byte) 1:
            case (byte) 2:
            case (byte) 3:
            case (byte) 4:
            case (byte) 5:
                return true;
            default:
                return false;
        }
    }

    public boolean equals(Object $r1) throws  {
        if (!($r1 instanceof PlaceReport)) {
            return false;
        }
        PlaceReport $r2 = (PlaceReport) $r1;
        return zzaa.equal(this.awU, $r2.awU) ? zzaa.equal(this.mTag, $r2.mTag) ? zzaa.equal(this.zzcun, $r2.zzcun) : false : false;
    }

    public String getPlaceId() throws  {
        return this.awU;
    }

    public String getSource() throws  {
        return this.zzcun;
    }

    public String getTag() throws  {
        return this.mTag;
    }

    public int hashCode() throws  {
        return zzaa.hashCode(this.awU, this.mTag, this.zzcun);
    }

    public String toString() throws  {
        zza $r1 = zzaa.zzaf(this);
        $r1.zzh("placeId", this.awU);
        $r1.zzh(GcmScheduler.INTENT_PARAM_TAG, this.mTag);
        if (!"unknown".equals(this.zzcun)) {
            $r1.zzh("source", this.zzcun);
        }
        return $r1.toString();
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        zzi.zza(this, $r1, $i0);
    }
}
