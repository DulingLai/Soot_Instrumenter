package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

/* compiled from: dalvik_source_com.waze.apk */
public class DetectedActivityCreator implements Creator<DetectedActivity> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void zza(DetectedActivity $r0, Parcel $r1, int i) throws  {
        i = zzb.zzea($r1);
        zzb.zzc($r1, 1, $r0.aux);
        zzb.zzc($r1, 2, $r0.auy);
        zzb.zzc($r1, 1000, $r0.getVersionCode());
        zzb.zzaj($r1, i);
    }

    public DetectedActivity createFromParcel(Parcel $r1) throws  {
        int $i0 = 0;
        int $i1 = zza.zzdz($r1);
        int $i2 = 0;
        int $i3 = 0;
        while ($r1.dataPosition() < $i1) {
            int $i4 = zza.zzdy($r1);
            switch (zza.zziv($i4)) {
                case 1:
                    $i2 = zza.zzg($r1, $i4);
                    break;
                case 2:
                    $i0 = zza.zzg($r1, $i4);
                    break;
                case 1000:
                    $i3 = zza.zzg($r1, $i4);
                    break;
                default:
                    zza.zzb($r1, $i4);
                    break;
            }
        }
        if ($r1.dataPosition() == $i1) {
            return new DetectedActivity($i3, $i2, $i0);
        }
        throw new zza.zza("Overread allowed size end=" + $i1, $r1);
    }

    public DetectedActivity[] newArray(int $i0) throws  {
        return new DetectedActivity[$i0];
    }
}
