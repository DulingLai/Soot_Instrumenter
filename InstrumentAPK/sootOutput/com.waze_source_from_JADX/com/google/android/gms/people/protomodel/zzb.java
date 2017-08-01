package com.google.android.gms.people.protomodel;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import java.util.ArrayList;
import java.util.List;

/* compiled from: dalvik_source_com.waze.apk */
public class zzb implements Creator<FetchBackUpDeviceContactInfoResponseEntity> {
    static void zza(FetchBackUpDeviceContactInfoResponseEntity $r0, Parcel $r1, int i) throws  {
        i = com.google.android.gms.common.internal.safeparcel.zzb.zzea($r1);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc($r1, 1, $r0.mVersionCode);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc($r1, 2, $r0.getContactsPerDevice(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzaj($r1, i);
    }

    public /* synthetic */ Object createFromParcel(Parcel $r1) throws  {
        return zzum($r1);
    }

    public /* synthetic */ Object[] newArray(int $i0) throws  {
        return zzadi($i0);
    }

    public FetchBackUpDeviceContactInfoResponseEntity[] zzadi(int $i0) throws  {
        return new FetchBackUpDeviceContactInfoResponseEntity[$i0];
    }

    public FetchBackUpDeviceContactInfoResponseEntity zzum(Parcel $r1) throws  {
        int $i0 = zza.zzdz($r1);
        int $i1 = 0;
        ArrayList $r2 = null;
        while ($r1.dataPosition() < $i0) {
            int $i2 = zza.zzdy($r1);
            switch (zza.zziv($i2)) {
                case 1:
                    $i1 = zza.zzg($r1, $i2);
                    break;
                case 2:
                    $r2 = zza.zzc($r1, $i2, BackedUpContactsPerDeviceEntity.CREATOR);
                    break;
                default:
                    zza.zzb($r1, $i2);
                    break;
            }
        }
        if ($r1.dataPosition() == $i0) {
            return new FetchBackUpDeviceContactInfoResponseEntity($i1, (List) $r2);
        }
        throw new zza.zza("Overread allowed size end=" + $i0, $r1);
    }
}
