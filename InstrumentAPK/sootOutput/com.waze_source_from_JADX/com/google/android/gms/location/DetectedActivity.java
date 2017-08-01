package com.google.android.gms.location;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.zzaa;
import java.util.Comparator;

/* compiled from: dalvik_source_com.waze.apk */
public class DetectedActivity extends AbstractSafeParcelable {
    public static final DetectedActivityCreator CREATOR = new DetectedActivityCreator();
    public static final int EXITING_VEHICLE = 6;
    public static final int FLOOR_CHANGE = 11;
    public static final int IN_ELEVATOR = 14;
    public static final int IN_VEHICLE = 0;
    public static final int OFF_BODY = 9;
    public static final int ON_BICYCLE = 1;
    public static final int ON_ESCALATOR = 13;
    public static final int ON_FOOT = 2;
    public static final int ON_STAIRS = 12;
    public static final int RUNNING = 8;
    public static final int SLEEPING = 15;
    public static final int STILL = 3;
    public static final int TILTING = 5;
    public static final int TRUSTED_GAIT = 10;
    public static final int UNKNOWN = 4;
    public static final int WALKING = 7;
    public static final Comparator<DetectedActivity> auu = new C09371();
    public static final int[] auv = new int[]{9, 10};
    public static final int[] auw = new int[]{0, 1, 2, 4, 5, 6, 7, 8, 10, 11, 12, 13, 14};
    int aux;
    int auy;
    private final int mVersionCode;

    /* compiled from: dalvik_source_com.waze.apk */
    class C09371 implements Comparator<DetectedActivity> {
        C09371() throws  {
        }

        public /* synthetic */ int compare(Object $r1, Object $r2) throws  {
            return zza((DetectedActivity) $r1, (DetectedActivity) $r2);
        }

        public int zza(DetectedActivity $r1, DetectedActivity $r2) throws  {
            int $i0 = Integer.valueOf($r2.getConfidence()).compareTo(Integer.valueOf($r1.getConfidence()));
            return $i0 == 0 ? Integer.valueOf($r1.getType()).compareTo(Integer.valueOf($r2.getType())) : $i0;
        }
    }

    public DetectedActivity(int $i0, int $i1) throws  {
        this.mVersionCode = 1;
        this.aux = $i0;
        this.auy = $i1;
    }

    public DetectedActivity(int $i0, int $i1, int $i2) throws  {
        this.mVersionCode = $i0;
        this.aux = $i1;
        this.auy = $i2;
    }

    private int zzwa(int $i0) throws  {
        return $i0 > 15 ? 4 : $i0;
    }

    public static String zzwb(int $i0) throws  {
        switch ($i0) {
            case 0:
                return "IN_VEHICLE";
            case 1:
                return "ON_BICYCLE";
            case 2:
                return "ON_FOOT";
            case 3:
                return "STILL";
            case 4:
                return "UNKNOWN";
            case 5:
                return "TILTING";
            case 6:
                break;
            case 7:
                return "WALKING";
            case 8:
                return "RUNNING";
            default:
                break;
        }
        return Integer.toString($i0);
    }

    public boolean equals(Object $r1) throws  {
        if (this == $r1) {
            return true;
        }
        if ($r1 == null || getClass() != $r1.getClass()) {
            return false;
        }
        DetectedActivity $r4 = (DetectedActivity) $r1;
        return this.aux == $r4.aux && this.auy == $r4.auy;
    }

    public int getConfidence() throws  {
        return this.auy;
    }

    public int getType() throws  {
        return zzwa(this.aux);
    }

    public int getVersionCode() throws  {
        return this.mVersionCode;
    }

    public int hashCode() throws  {
        return zzaa.hashCode(Integer.valueOf(this.aux), Integer.valueOf(this.auy));
    }

    public String toString() throws  {
        String $r1 = String.valueOf(zzwb(getType()));
        return new StringBuilder(String.valueOf($r1).length() + 48).append("DetectedActivity [type=").append($r1).append(", confidence=").append(this.auy).append("]").toString();
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        DetectedActivityCreator.zza(this, $r1, $i0);
    }
}
