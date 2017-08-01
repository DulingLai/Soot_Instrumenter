package com.google.android.gms.people.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;

/* compiled from: dalvik_source_com.waze.apk */
public class zzb implements Creator<AvatarReference> {
    static void zza(AvatarReference $r0, Parcel $r1, int i) throws  {
        i = com.google.android.gms.common.internal.safeparcel.zzb.zzea($r1);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc($r1, 1, $r0.getSource());
        com.google.android.gms.common.internal.safeparcel.zzb.zza($r1, 2, $r0.getLocation(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc($r1, 1000, $r0.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.zzb.zzaj($r1, i);
    }

    public /* synthetic */ Object createFromParcel(Parcel $r1) throws  {
        return zzuk($r1);
    }

    public /* synthetic */ Object[] newArray(int $i0) throws  {
        return zzadg($i0);
    }

    public AvatarReference[] zzadg(int $i0) throws  {
        return new AvatarReference[$i0];
    }

    public AvatarReference zzuk(Parcel $r1) throws  {
        int $i0 = 0;
        int $i1 = zza.zzdz($r1);
        String $r2 = null;
        int $i2 = 0;
        while ($r1.dataPosition() < $i1) {
            int $i3 = zza.zzdy($r1);
            switch (zza.zziv($i3)) {
                case 1:
                    $i0 = zza.zzg($r1, $i3);
                    break;
                case 2:
                    $r2 = zza.zzq($r1, $i3);
                    break;
                case 1000:
                    $i2 = zza.zzg($r1, $i3);
                    break;
                default:
                    zza.zzb($r1, $i3);
                    break;
            }
        }
        if ($r1.dataPosition() == $i1) {
            return new AvatarReference($i2, $i0, $r2);
        }
        throw new zza.zza("Overread allowed size end=" + $i1, $r1);
    }
}
