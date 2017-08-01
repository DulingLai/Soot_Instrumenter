package com.google.android.gms.people.internal.autocomplete;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

/* compiled from: dalvik_source_com.waze.apk */
public class zzq implements Creator<PhotoImpl> {
    static void zza(PhotoImpl $r0, Parcel $r1, int i) throws  {
        i = zzb.zzea($r1);
        zzb.zzc($r1, 1, $r0.mVersionCode);
        zzb.zzc($r1, 2, $r0.zzayf);
        zzb.zza($r1, 3, $r0.aOw, false);
        zzb.zza($r1, 4, $r0.aUC);
        zzb.zzaj($r1, i);
    }

    public /* synthetic */ Object createFromParcel(Parcel $r1) throws  {
        return zzuh($r1);
    }

    public /* synthetic */ Object[] newArray(int $i0) throws  {
        return zzadd($i0);
    }

    public PhotoImpl[] zzadd(int $i0) throws  {
        return new PhotoImpl[$i0];
    }

    public PhotoImpl zzuh(Parcel $r1) throws  {
        boolean $z0 = false;
        int $i0 = zza.zzdz($r1);
        String $r2 = null;
        int $i1 = 0;
        int $i2 = 0;
        while ($r1.dataPosition() < $i0) {
            int $i3 = zza.zzdy($r1);
            switch (zza.zziv($i3)) {
                case 1:
                    $i2 = zza.zzg($r1, $i3);
                    break;
                case 2:
                    $i1 = zza.zzg($r1, $i3);
                    break;
                case 3:
                    $r2 = zza.zzq($r1, $i3);
                    break;
                case 4:
                    $z0 = zza.zzc($r1, $i3);
                    break;
                default:
                    zza.zzb($r1, $i3);
                    break;
            }
        }
        if ($r1.dataPosition() == $i0) {
            return new PhotoImpl($i2, $i1, $r2, $z0);
        }
        throw new zza.zza("Overread allowed size end=" + $i0, $r1);
    }
}
