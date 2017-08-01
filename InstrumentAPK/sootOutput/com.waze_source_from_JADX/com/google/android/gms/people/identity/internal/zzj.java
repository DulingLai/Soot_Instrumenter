package com.google.android.gms.people.identity.internal;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

/* compiled from: dalvik_source_com.waze.apk */
public class zzj implements Creator<ParcelableListOptions> {
    static void zza(ParcelableListOptions $r0, Parcel $r1, int i) throws  {
        i = zzb.zzea($r1);
        zzb.zza($r1, 1, $r0.aOr);
        zzb.zza($r1, 2, $r0.aMu);
        zzb.zza($r1, 3, $r0.cJ, false);
        zzb.zza($r1, 4, $r0.aOs);
        zzb.zza($r1, 5, $r0.aMq, false);
        zzb.zzc($r1, 1000, $r0.getVersionCode());
        zzb.zzaj($r1, i);
    }

    public /* synthetic */ Object createFromParcel(Parcel $r1) throws  {
        return zzsk($r1);
    }

    public /* synthetic */ Object[] newArray(int $i0) throws  {
        return zzaaw($i0);
    }

    public ParcelableListOptions[] zzaaw(int $i0) throws  {
        return new ParcelableListOptions[$i0];
    }

    public ParcelableListOptions zzsk(Parcel $r1) throws  {
        Bundle $r2 = null;
        boolean $z0 = false;
        int $i0 = zza.zzdz($r1);
        String $r3 = null;
        boolean $z1 = false;
        boolean $z2 = false;
        int $i1 = 0;
        while ($r1.dataPosition() < $i0) {
            int $i2 = zza.zzdy($r1);
            switch (zza.zziv($i2)) {
                case 1:
                    $z2 = zza.zzc($r1, $i2);
                    break;
                case 2:
                    $z1 = zza.zzc($r1, $i2);
                    break;
                case 3:
                    $r3 = zza.zzq($r1, $i2);
                    break;
                case 4:
                    $z0 = zza.zzc($r1, $i2);
                    break;
                case 5:
                    $r2 = zza.zzs($r1, $i2);
                    break;
                case 1000:
                    $i1 = zza.zzg($r1, $i2);
                    break;
                default:
                    zza.zzb($r1, $i2);
                    break;
            }
        }
        if ($r1.dataPosition() == $i0) {
            return new ParcelableListOptions($i1, $z2, $z1, $z0, $r3, $r2);
        }
        throw new zza.zza("Overread allowed size end=" + $i0, $r1);
    }
}
