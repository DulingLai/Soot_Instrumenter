package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

/* compiled from: dalvik_source_com.waze.apk */
public class LocationRequestCreator implements Creator<LocationRequest> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void zza(LocationRequest $r0, Parcel $r1, int i) throws  {
        i = zzb.zzea($r1);
        zzb.zzc($r1, 1, $r0.mPriority);
        zzb.zza($r1, 2, $r0.ave);
        zzb.zza($r1, 3, $r0.avf);
        zzb.zza($r1, 4, $r0.afs);
        zzb.zza($r1, 5, $r0.auE);
        zzb.zzc($r1, 6, $r0.avg);
        zzb.zza($r1, 7, $r0.avh);
        zzb.zzc($r1, 1000, $r0.getVersionCode());
        zzb.zza($r1, 8, $r0.avi);
        zzb.zzaj($r1, i);
    }

    public LocationRequest createFromParcel(Parcel $r1) throws  {
        int $i0 = zza.zzdz($r1);
        int $i1 = 0;
        int $i2 = 102;
        long $l3 = 3600000;
        long $l4 = 600000;
        boolean $z0 = false;
        long $l5 = Long.MAX_VALUE;
        int $i6 = ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
        float $f0 = 0.0f;
        long $l7 = 0;
        while ($r1.dataPosition() < $i0) {
            int $i8 = zza.zzdy($r1);
            switch (zza.zziv($i8)) {
                case 1:
                    $i2 = zza.zzg($r1, $i8);
                    break;
                case 2:
                    $l3 = zza.zzi($r1, $i8);
                    break;
                case 3:
                    $l4 = zza.zzi($r1, $i8);
                    break;
                case 4:
                    $z0 = zza.zzc($r1, $i8);
                    break;
                case 5:
                    $l5 = zza.zzi($r1, $i8);
                    break;
                case 6:
                    $i6 = zza.zzg($r1, $i8);
                    break;
                case 7:
                    $f0 = zza.zzl($r1, $i8);
                    break;
                case 8:
                    $l7 = zza.zzi($r1, $i8);
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
            return new LocationRequest($i1, $i2, $l3, $l4, $z0, $l5, $i6, $f0, $l7);
        }
        throw new zza.zza("Overread allowed size end=" + $i0, $r1);
    }

    public LocationRequest[] newArray(int $i0) throws  {
        return new LocationRequest[$i0];
    }
}
