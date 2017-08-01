package com.google.android.gms.plus.internal.model.people;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.plus.internal.model.people.PersonEntity.CoverEntity.CoverInfoEntity;
import java.util.HashSet;
import java.util.Set;

/* compiled from: dalvik_source_com.waze.apk */
public class zzd implements Creator<CoverInfoEntity> {
    static void zza(CoverInfoEntity $r0, Parcel $r1, int i) throws  {
        i = zzb.zzea($r1);
        Set $r2 = $r0.aOu;
        if ($r2.contains(Integer.valueOf(1))) {
            zzb.zzc($r1, 1, $r0.mVersionCode);
        }
        if ($r2.contains(Integer.valueOf(2))) {
            zzb.zzc($r1, 2, $r0.aZn);
        }
        if ($r2.contains(Integer.valueOf(3))) {
            zzb.zzc($r1, 3, $r0.aZo);
        }
        zzb.zzaj($r1, i);
    }

    public /* synthetic */ Object createFromParcel(Parcel $r1) throws  {
        return zzvh($r1);
    }

    public /* synthetic */ Object[] newArray(int $i0) throws  {
        return zzaek($i0);
    }

    public CoverInfoEntity[] zzaek(int $i0) throws  {
        return new CoverInfoEntity[$i0];
    }

    public CoverInfoEntity zzvh(Parcel $r1) throws  {
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
            return new CoverInfoEntity($r2, $i3, $i2, $i0);
        }
        throw new zza.zza("Overread allowed size end=" + $i1, $r1);
    }
}
