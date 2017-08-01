package com.google.android.gms.people.protomodel;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;
import java.util.ArrayList;
import java.util.List;

/* compiled from: dalvik_source_com.waze.apk */
public class zza implements Creator<BackedUpContactsPerDeviceEntity> {
    static void zza(BackedUpContactsPerDeviceEntity $r0, Parcel $r1, int i) throws  {
        i = zzb.zzea($r1);
        zzb.zzc($r1, 1, $r0.mVersionCode);
        zzb.zza($r1, 2, $r0.getDeviceId(), false);
        zzb.zzc($r1, 3, $r0.getSourceStats(), false);
        zzb.zza($r1, 4, $r0.getDeviceDisplayName(), false);
        zzb.zzaj($r1, i);
    }

    public /* synthetic */ Object createFromParcel(Parcel $r1) throws  {
        return zzul($r1);
    }

    public /* synthetic */ Object[] newArray(int $i0) throws  {
        return zzadh($i0);
    }

    public BackedUpContactsPerDeviceEntity[] zzadh(int $i0) throws  {
        return new BackedUpContactsPerDeviceEntity[$i0];
    }

    public BackedUpContactsPerDeviceEntity zzul(Parcel $r1) throws  {
        String $r2 = null;
        int $i0 = com.google.android.gms.common.internal.safeparcel.zza.zzdz($r1);
        int $i1 = 0;
        ArrayList $r3 = null;
        String $r4 = null;
        while ($r1.dataPosition() < $i0) {
            int $i2 = com.google.android.gms.common.internal.safeparcel.zza.zzdy($r1);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zziv($i2)) {
                case 1:
                    $i1 = com.google.android.gms.common.internal.safeparcel.zza.zzg($r1, $i2);
                    break;
                case 2:
                    $r4 = com.google.android.gms.common.internal.safeparcel.zza.zzq($r1, $i2);
                    break;
                case 3:
                    $r3 = com.google.android.gms.common.internal.safeparcel.zza.zzc($r1, $i2, SourceStatsEntity.CREATOR);
                    break;
                case 4:
                    $r2 = com.google.android.gms.common.internal.safeparcel.zza.zzq($r1, $i2);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb($r1, $i2);
                    break;
            }
        }
        if ($r1.dataPosition() == $i0) {
            return new BackedUpContactsPerDeviceEntity($i1, $r4, (List) $r3, $r2);
        }
        throw new com.google.android.gms.common.internal.safeparcel.zza.zza("Overread allowed size end=" + $i0, $r1);
    }
}
