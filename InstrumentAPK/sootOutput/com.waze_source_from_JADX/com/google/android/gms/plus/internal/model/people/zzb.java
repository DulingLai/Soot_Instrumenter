package com.google.android.gms.plus.internal.model.people;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.plus.internal.model.people.PersonEntity.AgeRangeEntity;
import java.util.HashSet;
import java.util.Set;

/* compiled from: dalvik_source_com.waze.apk */
public class zzb implements Creator<AgeRangeEntity> {
    static void zza(AgeRangeEntity $r0, Parcel $r1, int i) throws  {
        i = com.google.android.gms.common.internal.safeparcel.zzb.zzea($r1);
        Set $r2 = $r0.aOu;
        if ($r2.contains(Integer.valueOf(1))) {
            com.google.android.gms.common.internal.safeparcel.zzb.zzc($r1, 1, $r0.mVersionCode);
        }
        if ($r2.contains(Integer.valueOf(2))) {
            com.google.android.gms.common.internal.safeparcel.zzb.zzc($r1, 2, $r0.aZi);
        }
        if ($r2.contains(Integer.valueOf(3))) {
            com.google.android.gms.common.internal.safeparcel.zzb.zzc($r1, 3, $r0.aZj);
        }
        com.google.android.gms.common.internal.safeparcel.zzb.zzaj($r1, i);
    }

    public /* synthetic */ Object createFromParcel(Parcel $r1) throws  {
        return zzvf($r1);
    }

    public /* synthetic */ Object[] newArray(int $i0) throws  {
        return zzaei($i0);
    }

    public AgeRangeEntity[] zzaei(int $i0) throws  {
        return new AgeRangeEntity[$i0];
    }

    public AgeRangeEntity zzvf(Parcel $r1) throws  {
        int $i0 = 0;
        int $i1 = zza.zzdz($r1);
        HashSet $r2 = new HashSet();
        int $i2 = 0;
        int $i3 = 0;
        while ($r1.dataPosition() < $i1) {
            int $i4 = zza.zzdy($r1);
            switch (zza.zziv($i4)) {
                case 1:
                    $i3 = zza.zzg($r1, $i4);
                    $r2.add(Integer.valueOf(1));
                    break;
                case 2:
                    $i2 = zza.zzg($r1, $i4);
                    $r2.add(Integer.valueOf(2));
                    break;
                case 3:
                    $i0 = zza.zzg($r1, $i4);
                    $r2.add(Integer.valueOf(3));
                    break;
                default:
                    zza.zzb($r1, $i4);
                    break;
            }
        }
        if ($r1.dataPosition() == $i1) {
            return new AgeRangeEntity($r2, $i3, $i2, $i0);
        }
        throw new zza.zza("Overread allowed size end=" + $i1, $r1);
    }
}
