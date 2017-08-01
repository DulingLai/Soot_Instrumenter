package com.google.android.gms.common.server.converter;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;

/* compiled from: dalvik_source_com.waze.apk */
public class zza implements Creator<ConverterWrapper> {
    static void zza(ConverterWrapper $r0, Parcel $r1, int $i0) throws  {
        int $i1 = zzb.zzea($r1);
        zzb.zzc($r1, 1, $r0.getVersionCode());
        zzb.zza($r1, 2, $r0.zzaxq(), $i0, false);
        zzb.zzaj($r1, $i1);
    }

    public /* synthetic */ Object createFromParcel(Parcel $r1) throws  {
        return zzee($r1);
    }

    public /* synthetic */ Object[] newArray(int $i0) throws  {
        return zzjb($i0);
    }

    public ConverterWrapper zzee(Parcel $r1) throws  {
        int $i0 = com.google.android.gms.common.internal.safeparcel.zza.zzdz($r1);
        int $i1 = 0;
        StringToIntConverter $r2 = null;
        while ($r1.dataPosition() < $i0) {
            int $i2 = com.google.android.gms.common.internal.safeparcel.zza.zzdy($r1);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zziv($i2)) {
                case 1:
                    $i1 = com.google.android.gms.common.internal.safeparcel.zza.zzg($r1, $i2);
                    break;
                case 2:
                    $r2 = (StringToIntConverter) com.google.android.gms.common.internal.safeparcel.zza.zza($r1, $i2, StringToIntConverter.CREATOR);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb($r1, $i2);
                    break;
            }
        }
        if ($r1.dataPosition() == $i0) {
            return new ConverterWrapper($i1, $r2);
        }
        throw new com.google.android.gms.common.internal.safeparcel.zza.zza("Overread allowed size end=" + $i0, $r1);
    }

    public ConverterWrapper[] zzjb(int $i0) throws  {
        return new ConverterWrapper[$i0];
    }
}
