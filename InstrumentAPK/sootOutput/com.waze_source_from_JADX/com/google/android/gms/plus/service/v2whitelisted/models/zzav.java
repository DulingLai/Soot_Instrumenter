package com.google.android.gms.plus.service.v2whitelisted.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.plus.service.v2whitelisted.models.Person.Taglines;
import java.util.HashSet;
import java.util.Set;

/* compiled from: dalvik_source_com.waze.apk */
public class zzav implements Creator<Taglines> {
    static void zza(Taglines $r0, Parcel $r1, int $i0) throws  {
        int $i1 = zzb.zzea($r1);
        Set $r2 = $r0.aOu;
        if ($r2.contains(Integer.valueOf(1))) {
            zzb.zzc($r1, 1, $r0.mVersionCode);
        }
        if ($r2.contains(Integer.valueOf(2))) {
            zzb.zza($r1, 2, $r0.aZT, $i0, true);
        }
        if ($r2.contains(Integer.valueOf(3))) {
            zzb.zza($r1, 3, $r0.mValue, true);
        }
        zzb.zzaj($r1, $i1);
    }

    public /* synthetic */ Object createFromParcel(Parcel $r1) throws  {
        return zzxj($r1);
    }

    public /* synthetic */ Object[] newArray(int $i0) throws  {
        return zzagm($i0);
    }

    public Taglines[] zzagm(int $i0) throws  {
        return new Taglines[$i0];
    }

    public Taglines zzxj(Parcel $r1) throws  {
        String $r3 = null;
        int $i0 = zza.zzdz($r1);
        HashSet $r2 = new HashSet();
        int $i1 = 0;
        Mergedpeoplemetadata $r4 = null;
        while ($r1.dataPosition() < $i0) {
            int $i2 = zza.zzdy($r1);
            switch (zza.zziv($i2)) {
                case 1:
                    $i1 = zza.zzg($r1, $i2);
                    $r2.add(Integer.valueOf(1));
                    break;
                case 2:
                    Parcelable $r7 = zza.zza($r1, $i2, Mergedpeoplemetadata.CREATOR);
                    $r2.add(Integer.valueOf(2));
                    $r4 = (Mergedpeoplemetadata) $r7;
                    break;
                case 3:
                    $r3 = zza.zzq($r1, $i2);
                    $r2.add(Integer.valueOf(3));
                    break;
                default:
                    zza.zzb($r1, $i2);
                    break;
            }
        }
        if ($r1.dataPosition() == $i0) {
            return new Taglines($r2, $i1, $r4, $r3);
        }
        throw new zza.zza("Overread allowed size end=" + $i0, $r1);
    }
}
