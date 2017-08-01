package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

/* compiled from: dalvik_source_com.waze.apk */
public class FloorChangeEventCreator implements Creator<FloorChangeEvent> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void zza(FloorChangeEvent $r0, Parcel $r1, int i) throws  {
        i = zzb.zzea($r1);
        zzb.zzc($r1, 1, $r0.getType());
        zzb.zzc($r1, 2, $r0.getConfidence());
        zzb.zza($r1, 3, $r0.getStartTimeMillis());
        zzb.zza($r1, 4, $r0.getEndTimeMillis());
        zzb.zza($r1, 5, $r0.getStartElapsedRealtimeMillis());
        zzb.zza($r1, 6, $r0.getEndElapsedRealtimeMillis());
        zzb.zza($r1, 7, $r0.getElevationChange());
        zzb.zzc($r1, 1000, $r0.getVersionCode());
        zzb.zzaj($r1, i);
    }

    public FloorChangeEvent createFromParcel(Parcel $r1) throws  {
        int $i0 = zza.zzdz($r1);
        int $i1 = 0;
        int $i2 = 0;
        int $i3 = 0;
        long $l4 = 0;
        long $l5 = 0;
        long $l6 = 0;
        long $l7 = 0;
        float $f0 = 0.0f;
        while ($r1.dataPosition() < $i0) {
            int $i8 = zza.zzdy($r1);
            switch (zza.zziv($i8)) {
                case 1:
                    $i2 = zza.zzg($r1, $i8);
                    break;
                case 2:
                    $i3 = zza.zzg($r1, $i8);
                    break;
                case 3:
                    $l4 = zza.zzi($r1, $i8);
                    break;
                case 4:
                    $l5 = zza.zzi($r1, $i8);
                    break;
                case 5:
                    $l6 = zza.zzi($r1, $i8);
                    break;
                case 6:
                    $l7 = zza.zzi($r1, $i8);
                    break;
                case 7:
                    $f0 = zza.zzl($r1, $i8);
                    break;
                case 1000:
                    $i1 = zza.zzg($r1, $i8);
                    break;
                default:
                    zza.zzb($r1, $i8);
                    break;
            }
        }
        if ($r1.dataPosition() == $i0) {
            return new FloorChangeEvent($i1, $i2, $i3, $l4, $l5, $l6, $l7, $f0);
        }
        throw new zza.zza("Overread allowed size end=" + $i0, $r1);
    }

    public FloorChangeEvent[] newArray(int $i0) throws  {
        return new FloorChangeEvent[$i0];
    }
}
