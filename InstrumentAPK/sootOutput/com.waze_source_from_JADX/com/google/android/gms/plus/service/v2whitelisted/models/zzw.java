package com.google.android.gms.plus.service.v2whitelisted.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.plus.service.v2whitelisted.models.Person.ExtendedData;
import com.google.android.gms.plus.service.v2whitelisted.models.Person.ExtendedData.HangoutsExtendedData;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/* compiled from: dalvik_source_com.waze.apk */
public class zzw implements Creator<ExtendedData> {
    static void zza(ExtendedData $r0, Parcel $r1, int $i0) throws  {
        int $i1 = zzb.zzea($r1);
        Set $r2 = $r0.aOu;
        if ($r2.contains(Integer.valueOf(1))) {
            zzb.zzc($r1, 1, $r0.mVersionCode);
        }
        if ($r2.contains(Integer.valueOf(2))) {
            zzb.zza($r1, 2, $r0.aZZ, $i0, true);
        }
        if ($r2.contains(Integer.valueOf(3))) {
            zzb.zzb($r1, 3, $r0.baa, true);
        }
        zzb.zzaj($r1, $i1);
    }

    public /* synthetic */ Object createFromParcel(Parcel $r1) throws  {
        return zzwk($r1);
    }

    public /* synthetic */ Object[] newArray(int $i0) throws  {
        return zzafn($i0);
    }

    public ExtendedData[] zzafn(int $i0) throws  {
        return new ExtendedData[$i0];
    }

    public ExtendedData zzwk(Parcel $r1) throws  {
        ArrayList $r3 = null;
        int $i0 = zza.zzdz($r1);
        HashSet $r2 = new HashSet();
        int $i1 = 0;
        HangoutsExtendedData $r4 = null;
        while ($r1.dataPosition() < $i0) {
            int $i2 = zza.zzdy($r1);
            switch (zza.zziv($i2)) {
                case 1:
                    $i1 = zza.zzg($r1, $i2);
                    $r2.add(Integer.valueOf(1));
                    break;
                case 2:
                    Parcelable $r7 = zza.zza($r1, $i2, HangoutsExtendedData.CREATOR);
                    $r2.add(Integer.valueOf(2));
                    $r4 = (HangoutsExtendedData) $r7;
                    break;
                case 3:
                    $r3 = zza.zzae($r1, $i2);
                    $r2.add(Integer.valueOf(3));
                    break;
                default:
                    zza.zzb($r1, $i2);
                    break;
            }
        }
        if ($r1.dataPosition() == $i0) {
            return new ExtendedData($r2, $i1, $r4, $r3);
        }
        throw new zza.zza("Overread allowed size end=" + $i0, $r1);
    }
}
