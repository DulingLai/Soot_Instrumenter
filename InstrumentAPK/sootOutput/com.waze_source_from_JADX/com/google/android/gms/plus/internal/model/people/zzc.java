package com.google.android.gms.plus.internal.model.people;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.plus.internal.model.people.PersonEntity.CoverEntity;
import com.google.android.gms.plus.internal.model.people.PersonEntity.CoverEntity.CoverInfoEntity;
import com.google.android.gms.plus.internal.model.people.PersonEntity.CoverEntity.CoverPhotoEntity;
import java.util.HashSet;
import java.util.Set;

/* compiled from: dalvik_source_com.waze.apk */
public class zzc implements Creator<CoverEntity> {
    static void zza(CoverEntity $r0, Parcel $r1, int $i0) throws  {
        int $i1 = zzb.zzea($r1);
        Set $r2 = $r0.aOu;
        if ($r2.contains(Integer.valueOf(1))) {
            zzb.zzc($r1, 1, $r0.mVersionCode);
        }
        if ($r2.contains(Integer.valueOf(2))) {
            zzb.zza($r1, 2, $r0.aZk, $i0, true);
        }
        if ($r2.contains(Integer.valueOf(3))) {
            zzb.zza($r1, 3, $r0.aZl, $i0, true);
        }
        if ($r2.contains(Integer.valueOf(4))) {
            zzb.zzc($r1, 4, $r0.aZm);
        }
        zzb.zzaj($r1, $i1);
    }

    public /* synthetic */ Object createFromParcel(Parcel $r1) throws  {
        return zzvg($r1);
    }

    public /* synthetic */ Object[] newArray(int $i0) throws  {
        return zzaej($i0);
    }

    public CoverEntity[] zzaej(int $i0) throws  {
        return new CoverEntity[$i0];
    }

    public CoverEntity zzvg(Parcel $r1) throws  {
        CoverPhotoEntity $r2 = null;
        int $i0 = 0;
        int $i1 = zza.zzdz($r1);
        HashSet $r3 = new HashSet();
        CoverInfoEntity $r4 = null;
        int $i2 = 0;
        while ($r1.dataPosition() < $i1) {
            int $i3 = zza.zzdy($r1);
            Parcelable $r7;
            switch (zza.zziv($i3)) {
                case 1:
                    $i2 = zza.zzg($r1, $i3);
                    $r3.add(Integer.valueOf(1));
                    break;
                case 2:
                    $r7 = zza.zza($r1, $i3, (Creator) CoverInfoEntity.CREATOR);
                    $r3.add(Integer.valueOf(2));
                    $r4 = (CoverInfoEntity) $r7;
                    break;
                case 3:
                    $r7 = zza.zza($r1, $i3, (Creator) CoverPhotoEntity.CREATOR);
                    $r3.add(Integer.valueOf(3));
                    $r2 = (CoverPhotoEntity) $r7;
                    break;
                case 4:
                    $i0 = zza.zzg($r1, $i3);
                    $r3.add(Integer.valueOf(4));
                    break;
                default:
                    zza.zzb($r1, $i3);
                    break;
            }
        }
        if ($r1.dataPosition() == $i1) {
            return new CoverEntity($r3, $i2, $r4, $r2, $i0);
        }
        throw new zza.zza("Overread allowed size end=" + $i1, $r1);
    }
}
