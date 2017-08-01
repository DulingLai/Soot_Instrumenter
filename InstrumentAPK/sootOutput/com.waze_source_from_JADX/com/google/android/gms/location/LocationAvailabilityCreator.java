package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

/* compiled from: dalvik_source_com.waze.apk */
public class LocationAvailabilityCreator implements Creator<LocationAvailability> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void zza(LocationAvailability $r0, Parcel $r1, int i) throws  {
        i = zzb.zzea($r1);
        zzb.zzc($r1, 1, $r0.ava);
        zzb.zzc($r1, 2, $r0.avb);
        zzb.zza($r1, 3, $r0.avc);
        zzb.zzc($r1, 4, $r0.avd);
        zzb.zzc($r1, 1000, $r0.getVersionCode());
        zzb.zzaj($r1, i);
    }

    public LocationAvailability createFromParcel(Parcel $r1) throws  {
        int $i0 = 1;
        int $i1 = zza.zzdz($r1);
        int $i2 = 0;
        int $i3 = 1000;
        long $l4 = 0;
        int $i5 = 1;
        while ($r1.dataPosition() < $i1) {
            int $i6 = zza.zzdy($r1);
            switch (zza.zziv($i6)) {
                case 1:
                    $i5 = zza.zzg($r1, $i6);
                    break;
                case 2:
                    $i0 = zza.zzg($r1, $i6);
                    break;
                case 3:
                    $l4 = zza.zzi($r1, $i6);
                    break;
                case 4:
                    $i3 = zza.zzg($r1, $i6);
                    break;
                case 1000:
                    $i2 = zza.zzg($r1, $i6);
                    break;
                default:
                    zza.zzb($r1, $i6);
                    break;
            }
        }
        if ($r1.dataPosition() == $i1) {
            return new LocationAvailability($i2, $i3, $i5, $i0, $l4);
        }
        throw new zza.zza("Overread allowed size end=" + $i1, $r1);
    }

    public LocationAvailability[] newArray(int $i0) throws  {
        return new LocationAvailability[$i0];
    }
}
