package com.google.android.gms.plus.internal.model.people;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.plus.internal.model.people.PersonEntity.UrlsEntity;
import java.util.HashSet;
import java.util.Set;

/* compiled from: dalvik_source_com.waze.apk */
public class zzj implements Creator<UrlsEntity> {
    static void zza(UrlsEntity $r0, Parcel $r1, int i) throws  {
        i = zzb.zzea($r1);
        Set $r2 = $r0.aOu;
        if ($r2.contains(Integer.valueOf(1))) {
            zzb.zzc($r1, 1, $r0.mVersionCode);
        }
        if ($r2.contains(Integer.valueOf(3))) {
            zzb.zzc($r1, 3, $r0.zzchm());
        }
        if ($r2.contains(Integer.valueOf(4))) {
            zzb.zza($r1, 4, $r0.mValue, true);
        }
        if ($r2.contains(Integer.valueOf(5))) {
            zzb.zza($r1, 5, $r0.mLabel, true);
        }
        if ($r2.contains(Integer.valueOf(6))) {
            zzb.zzc($r1, 6, $r0.bG);
        }
        zzb.zzaj($r1, i);
    }

    public /* synthetic */ Object createFromParcel(Parcel $r1) throws  {
        return zzvn($r1);
    }

    public /* synthetic */ Object[] newArray(int $i0) throws  {
        return zzaeq($i0);
    }

    public UrlsEntity[] zzaeq(int $i0) throws  {
        return new UrlsEntity[$i0];
    }

    public UrlsEntity zzvn(Parcel $r1) throws  {
        String $r2 = null;
        int $i0 = 0;
        int $i1 = zza.zzdz($r1);
        HashSet $r3 = new HashSet();
        int $i2 = 0;
        String $r4 = null;
        int $i3 = 0;
        while ($r1.dataPosition() < $i1) {
            int $i4 = zza.zzdy($r1);
            switch (zza.zziv($i4)) {
                case 1:
                    $i3 = zza.zzg($r1, $i4);
                    $r3.add(Integer.valueOf(1));
                    continue;
                case 2:
                    break;
                case 3:
                    $i0 = zza.zzg($r1, $i4);
                    $r3.add(Integer.valueOf(3));
                    continue;
                case 4:
                    $r2 = zza.zzq($r1, $i4);
                    $r3.add(Integer.valueOf(4));
                    continue;
                case 5:
                    $r4 = zza.zzq($r1, $i4);
                    $r3.add(Integer.valueOf(5));
                    continue;
                case 6:
                    $i2 = zza.zzg($r1, $i4);
                    $r3.add(Integer.valueOf(6));
                    continue;
                default:
                    break;
            }
            zza.zzb($r1, $i4);
        }
        if ($r1.dataPosition() == $i1) {
            return new UrlsEntity($r3, $i3, $r4, $i2, $r2, $i0);
        }
        throw new zza.zza("Overread allowed size end=" + $i1, $r1);
    }
}
