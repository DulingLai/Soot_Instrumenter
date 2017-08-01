package com.google.android.gms.plus.service.v2whitelisted.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.plus.service.v2whitelisted.models.Person.PlacesLived;
import java.util.HashSet;
import java.util.Set;

/* compiled from: dalvik_source_com.waze.apk */
public class zzaq implements Creator<PlacesLived> {
    static void zza(PlacesLived $r0, Parcel $r1, int $i0) throws  {
        int $i1 = zzb.zzea($r1);
        Set $r2 = $r0.aOu;
        if ($r2.contains(Integer.valueOf(1))) {
            zzb.zzc($r1, 1, $r0.mVersionCode);
        }
        if ($r2.contains(Integer.valueOf(2))) {
            zzb.zza($r1, 2, $r0.aPH);
        }
        if ($r2.contains(Integer.valueOf(3))) {
            zzb.zza($r1, 3, $r0.aZT, $i0, true);
        }
        if ($r2.contains(Integer.valueOf(4))) {
            zzb.zza($r1, 4, $r0.mValue, true);
        }
        zzb.zzaj($r1, $i1);
    }

    public /* synthetic */ Object createFromParcel(Parcel $r1) throws  {
        return zzxe($r1);
    }

    public /* synthetic */ Object[] newArray(int $i0) throws  {
        return zzagh($i0);
    }

    public PlacesLived[] zzagh(int $i0) throws  {
        return new PlacesLived[$i0];
    }

    public PlacesLived zzxe(Parcel $r1) throws  {
        String $r2 = null;
        boolean $z0 = false;
        int $i0 = zza.zzdz($r1);
        HashSet $r3 = new HashSet();
        Mergedpeoplemetadata $r4 = null;
        int $i1 = 0;
        while ($r1.dataPosition() < $i0) {
            int $i2 = zza.zzdy($r1);
            switch (zza.zziv($i2)) {
                case 1:
                    $i1 = zza.zzg($r1, $i2);
                    $r3.add(Integer.valueOf(1));
                    break;
                case 2:
                    $z0 = zza.zzc($r1, $i2);
                    $r3.add(Integer.valueOf(2));
                    break;
                case 3:
                    Parcelable $r7 = zza.zza($r1, $i2, Mergedpeoplemetadata.CREATOR);
                    $r3.add(Integer.valueOf(3));
                    $r4 = (Mergedpeoplemetadata) $r7;
                    break;
                case 4:
                    $r2 = zza.zzq($r1, $i2);
                    $r3.add(Integer.valueOf(4));
                    break;
                default:
                    zza.zzb($r1, $i2);
                    break;
            }
        }
        if ($r1.dataPosition() == $i0) {
            return new PlacesLived($r3, $i1, $z0, $r4, $r2);
        }
        throw new zza.zza("Overread allowed size end=" + $i0, $r1);
    }
}
