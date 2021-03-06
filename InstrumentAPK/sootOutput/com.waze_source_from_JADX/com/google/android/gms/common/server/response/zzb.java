package com.google.android.gms.common.server.response;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;

/* compiled from: dalvik_source_com.waze.apk */
public class zzb implements Creator<SafeParcelResponse> {
    static void zza(SafeParcelResponse $r0, Parcel $r1, int $i0) throws  {
        int $i1 = com.google.android.gms.common.internal.safeparcel.zzb.zzea($r1);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc($r1, 1, $r0.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.zzb.zza($r1, 2, $r0.zzaxy(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza($r1, 3, $r0.zzaxz(), $i0, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzaj($r1, $i1);
    }

    public /* synthetic */ Object createFromParcel(Parcel $r1) throws  {
        return zzeh($r1);
    }

    public /* synthetic */ Object[] newArray(int $i0) throws  {
        return zzjf($i0);
    }

    public SafeParcelResponse zzeh(Parcel $r1) throws  {
        FieldMappingDictionary $r2 = null;
        int $i0 = zza.zzdz($r1);
        int $i1 = 0;
        Parcel $r3 = null;
        while ($r1.dataPosition() < $i0) {
            int $i2 = zza.zzdy($r1);
            switch (zza.zziv($i2)) {
                case 1:
                    $i1 = zza.zzg($r1, $i2);
                    break;
                case 2:
                    $r3 = zza.zzaf($r1, $i2);
                    break;
                case 3:
                    $r2 = (FieldMappingDictionary) zza.zza($r1, $i2, FieldMappingDictionary.CREATOR);
                    break;
                default:
                    zza.zzb($r1, $i2);
                    break;
            }
        }
        if ($r1.dataPosition() == $i0) {
            return new SafeParcelResponse($i1, $r3, $r2);
        }
        throw new zza.zza("Overread allowed size end=" + $i0, $r1);
    }

    public SafeParcelResponse[] zzjf(int $i0) throws  {
        return new SafeParcelResponse[$i0];
    }
}
