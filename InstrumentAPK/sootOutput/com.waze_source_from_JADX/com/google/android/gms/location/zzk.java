package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

/* compiled from: dalvik_source_com.waze.apk */
public class zzk implements Creator<SleepSegmentEvent> {
    static void zza(SleepSegmentEvent $r0, Parcel $r1, int i) throws  {
        i = zzb.zzea($r1);
        zzb.zza($r1, 1, $r0.getStartTimeMillis());
        zzb.zza($r1, 2, $r0.getEndTimeMillis());
        zzb.zzc($r1, 3, $r0.getStatus());
        zzb.zzc($r1, 1000, $r0.getVersionCode());
        zzb.zzaj($r1, i);
    }

    public /* synthetic */ Object createFromParcel(Parcel $r1) throws  {
        return zzpe($r1);
    }

    public /* synthetic */ Object[] newArray(int $i0) throws  {
        return zzwo($i0);
    }

    public SleepSegmentEvent zzpe(Parcel $r1) throws  {
        long $l0 = 0;
        int $i1 = 0;
        int $i2 = zza.zzdz($r1);
        long $l3 = 0;
        int $i4 = 0;
        while ($r1.dataPosition() < $i2) {
            int $i5 = zza.zzdy($r1);
            switch (zza.zziv($i5)) {
                case 1:
                    $l3 = zza.zzi($r1, $i5);
                    break;
                case 2:
                    $l0 = zza.zzi($r1, $i5);
                    break;
                case 3:
                    $i1 = zza.zzg($r1, $i5);
                    break;
                case 1000:
                    $i4 = zza.zzg($r1, $i5);
                    break;
                default:
                    zza.zzb($r1, $i5);
                    break;
            }
        }
        if ($r1.dataPosition() == $i2) {
            return new SleepSegmentEvent($i4, $l3, $l0, $i1);
        }
        throw new zza.zza("Overread allowed size end=" + $i2, $r1);
    }

    public SleepSegmentEvent[] zzwo(int $i0) throws  {
        return new SleepSegmentEvent[$i0];
    }
}
