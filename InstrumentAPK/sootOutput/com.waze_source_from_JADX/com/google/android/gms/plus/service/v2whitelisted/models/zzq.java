package com.google.android.gms.plus.service.v2whitelisted.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.plus.service.v2whitelisted.models.Person.CoverPhotos;
import java.util.HashSet;
import java.util.Set;

/* compiled from: dalvik_source_com.waze.apk */
public class zzq implements Creator<CoverPhotos> {
    static void zza(CoverPhotos $r0, Parcel $r1, int $i0) throws  {
        int $i1 = zzb.zzea($r1);
        Set $r2 = $r0.aOu;
        if ($r2.contains(Integer.valueOf(1))) {
            zzb.zzc($r1, 1, $r0.mVersionCode);
        }
        if ($r2.contains(Integer.valueOf(2))) {
            zzb.zzc($r1, 2, $r0.zzair);
        }
        if ($r2.contains(Integer.valueOf(3))) {
            zzb.zza($r1, 3, $r0.zzbgd, true);
        }
        if ($r2.contains(Integer.valueOf(5))) {
            zzb.zza($r1, 5, $r0.aUC);
        }
        if ($r2.contains(Integer.valueOf(6))) {
            zzb.zza($r1, 6, $r0.aZT, $i0, true);
        }
        if ($r2.contains(Integer.valueOf(7))) {
            zzb.zza($r1, 7, $r0.zzad, true);
        }
        if ($r2.contains(Integer.valueOf(8))) {
            zzb.zzc($r1, 8, $r0.zzaiq);
        }
        zzb.zzaj($r1, $i1);
    }

    public /* synthetic */ Object createFromParcel(Parcel $r1) throws  {
        return zzwe($r1);
    }

    public /* synthetic */ Object[] newArray(int $i0) throws  {
        return zzafh($i0);
    }

    public CoverPhotos[] zzafh(int $i0) throws  {
        return new CoverPhotos[$i0];
    }

    public CoverPhotos zzwe(Parcel $r1) throws  {
        String $r2 = null;
        int $i0 = 0;
        int $i1 = zza.zzdz($r1);
        HashSet $r3 = new HashSet();
        Mergedpeoplemetadata $r4 = null;
        boolean $z0 = false;
        String $r5 = null;
        int $i2 = 0;
        int $i3 = 0;
        while ($r1.dataPosition() < $i1) {
            int $i4 = zza.zzdy($r1);
            switch (zza.zziv($i4)) {
                case 1:
                    $i3 = zza.zzg($r1, $i4);
                    $r3.add(Integer.valueOf(1));
                    continue;
                case 2:
                    $i2 = zza.zzg($r1, $i4);
                    $r3.add(Integer.valueOf(2));
                    continue;
                case 3:
                    $r5 = zza.zzq($r1, $i4);
                    $r3.add(Integer.valueOf(3));
                    continue;
                case 4:
                    break;
                case 5:
                    $z0 = zza.zzc($r1, $i4);
                    $r3.add(Integer.valueOf(5));
                    continue;
                case 6:
                    Parcelable $r8 = zza.zza($r1, $i4, Mergedpeoplemetadata.CREATOR);
                    $r3.add(Integer.valueOf(6));
                    $r4 = (Mergedpeoplemetadata) $r8;
                    continue;
                case 7:
                    $r2 = zza.zzq($r1, $i4);
                    $r3.add(Integer.valueOf(7));
                    continue;
                case 8:
                    $i0 = zza.zzg($r1, $i4);
                    $r3.add(Integer.valueOf(8));
                    continue;
                default:
                    break;
            }
            zza.zzb($r1, $i4);
        }
        if ($r1.dataPosition() == $i1) {
            return new CoverPhotos($r3, $i3, $i2, $r5, $z0, $r4, $r2, $i0);
        }
        throw new zza.zza("Overread allowed size end=" + $i1, $r1);
    }
}
