package com.google.android.gms.common.server;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;

/* compiled from: dalvik_source_com.waze.apk */
public class zza implements Creator<FavaDiagnosticsEntity> {
    static void zza(FavaDiagnosticsEntity $r0, Parcel $r1, int i) throws  {
        i = zzb.zzea($r1);
        zzb.zzc($r1, 1, $r0.mVersionCode);
        zzb.zza($r1, 2, $r0.KQ, false);
        zzb.zzc($r1, 3, $r0.KR);
        zzb.zzaj($r1, i);
    }

    public /* synthetic */ Object createFromParcel(Parcel $r1) throws  {
        return zzed($r1);
    }

    public /* synthetic */ Object[] newArray(int $i0) throws  {
        return zzja($i0);
    }

    public FavaDiagnosticsEntity zzed(Parcel $r1) throws  {
        int $i0 = 0;
        int $i1 = com.google.android.gms.common.internal.safeparcel.zza.zzdz($r1);
        String $r2 = null;
        int $i2 = 0;
        while ($r1.dataPosition() < $i1) {
            int $i3 = com.google.android.gms.common.internal.safeparcel.zza.zzdy($r1);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zziv($i3)) {
                case 1:
                    $i2 = com.google.android.gms.common.internal.safeparcel.zza.zzg($r1, $i3);
                    break;
                case 2:
                    $r2 = com.google.android.gms.common.internal.safeparcel.zza.zzq($r1, $i3);
                    break;
                case 3:
                    $i0 = com.google.android.gms.common.internal.safeparcel.zza.zzg($r1, $i3);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb($r1, $i3);
                    break;
            }
        }
        if ($r1.dataPosition() == $i1) {
            return new FavaDiagnosticsEntity($i2, $r2, $i0);
        }
        throw new com.google.android.gms.common.internal.safeparcel.zza.zza("Overread allowed size end=" + $i1, $r1);
    }

    public FavaDiagnosticsEntity[] zzja(int $i0) throws  {
        return new FavaDiagnosticsEntity[$i0];
    }
}
