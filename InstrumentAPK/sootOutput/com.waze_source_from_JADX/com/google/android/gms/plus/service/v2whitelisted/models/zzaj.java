package com.google.android.gms.plus.service.v2whitelisted.models;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata.ProfileOwnerStats;
import java.util.HashSet;
import java.util.Set;

/* compiled from: dalvik_source_com.waze.apk */
public class zzaj implements Creator<ProfileOwnerStats> {
    static void zza(ProfileOwnerStats $r0, Parcel $r1, int i) throws  {
        i = zzb.zzea($r1);
        Set $r2 = $r0.aOu;
        if ($r2.contains(Integer.valueOf(1))) {
            zzb.zzc($r1, 1, $r0.mVersionCode);
        }
        if ($r2.contains(Integer.valueOf(2))) {
            zzb.zza($r1, 2, $r0.aQa);
        }
        if ($r2.contains(Integer.valueOf(3))) {
            zzb.zza($r1, 3, $r0.aQb);
        }
        zzb.zzaj($r1, i);
    }

    public /* synthetic */ Object createFromParcel(Parcel $r1) throws  {
        return zzwx($r1);
    }

    public /* synthetic */ Object[] newArray(int $i0) throws  {
        return zzaga($i0);
    }

    public ProfileOwnerStats[] zzaga(int $i0) throws  {
        return new ProfileOwnerStats[$i0];
    }

    public ProfileOwnerStats zzwx(Parcel $r1) throws  {
        long $l0 = 0;
        int $i1 = zza.zzdz($r1);
        HashSet $r2 = new HashSet();
        int $i2 = 0;
        long $l3 = 0;
        while ($r1.dataPosition() < $i1) {
            int $i4 = zza.zzdy($r1);
            switch (zza.zziv($i4)) {
                case 1:
                    $i2 = zza.zzg($r1, $i4);
                    $r2.add(Integer.valueOf(1));
                    break;
                case 2:
                    $l3 = zza.zzi($r1, $i4);
                    $r2.add(Integer.valueOf(2));
                    break;
                case 3:
                    $l0 = zza.zzi($r1, $i4);
                    $r2.add(Integer.valueOf(3));
                    break;
                default:
                    zza.zzb($r1, $i4);
                    break;
            }
        }
        if ($r1.dataPosition() == $i1) {
            return new ProfileOwnerStats($r2, $i2, $l3, $l0);
        }
        throw new zza.zza("Overread allowed size end=" + $i1, $r1);
    }
}
