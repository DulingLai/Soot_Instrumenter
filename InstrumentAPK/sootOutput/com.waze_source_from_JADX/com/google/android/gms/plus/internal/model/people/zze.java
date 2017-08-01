package com.google.android.gms.plus.internal.model.people;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.plus.internal.model.people.PersonEntity.CoverEntity.CoverPhotoEntity;
import java.util.HashSet;
import java.util.Set;

/* compiled from: dalvik_source_com.waze.apk */
public class zze implements Creator<CoverPhotoEntity> {
    static void zza(CoverPhotoEntity $r0, Parcel $r1, int i) throws  {
        i = zzb.zzea($r1);
        Set $r2 = $r0.aOu;
        if ($r2.contains(Integer.valueOf(1))) {
            zzb.zzc($r1, 1, $r0.mVersionCode);
        }
        if ($r2.contains(Integer.valueOf(2))) {
            zzb.zzc($r1, 2, $r0.zzair);
        }
        if ($r2.contains(Integer.valueOf(3))) {
            zzb.zza($r1, 3, $r0.zzad, true);
        }
        if ($r2.contains(Integer.valueOf(4))) {
            zzb.zzc($r1, 4, $r0.zzaiq);
        }
        zzb.zzaj($r1, i);
    }

    public /* synthetic */ Object createFromParcel(Parcel $r1) throws  {
        return zzvi($r1);
    }

    public /* synthetic */ Object[] newArray(int $i0) throws  {
        return zzael($i0);
    }

    public CoverPhotoEntity[] zzael(int $i0) throws  {
        return new CoverPhotoEntity[$i0];
    }

    public CoverPhotoEntity zzvi(Parcel $r1) throws  {
        int $i0 = 0;
        int $i1 = zza.zzdz($r1);
        HashSet $r2 = new HashSet();
        String $r3 = null;
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
                    $r3 = zza.zzq($r1, $i4);
                    $r2.add(Integer.valueOf(3));
                    break;
                case 4:
                    $i0 = zza.zzg($r1, $i4);
                    $r2.add(Integer.valueOf(4));
                    break;
                default:
                    zza.zzb($r1, $i4);
                    break;
            }
        }
        if ($r1.dataPosition() == $i1) {
            return new CoverPhotoEntity($r2, $i3, $i2, $r3, $i0);
        }
        throw new zza.zza("Overread allowed size end=" + $i1, $r1);
    }
}