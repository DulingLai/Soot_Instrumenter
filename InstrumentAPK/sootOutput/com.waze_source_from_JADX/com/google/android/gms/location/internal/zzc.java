package com.google.android.gms.location.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

/* compiled from: dalvik_source_com.waze.apk */
public class zzc implements Creator<ClientIdentity> {
    static void zza(ClientIdentity $r0, Parcel $r1, int i) throws  {
        i = zzb.zzea($r1);
        zzb.zzc($r1, 1, $r0.uid);
        zzb.zza($r1, 2, $r0.packageName, false);
        zzb.zzc($r1, 1000, $r0.getVersionCode());
        zzb.zzaj($r1, i);
    }

    public /* synthetic */ Object createFromParcel(Parcel $r1) throws  {
        return zzpg($r1);
    }

    public /* synthetic */ Object[] newArray(int $i0) throws  {
        return zzwr($i0);
    }

    public ClientIdentity zzpg(Parcel $r1) throws  {
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
            return new ClientIdentity($i2, $i0, $r2);
        }
        throw new zza.zza("Overread allowed size end=" + $i1, $r1);
    }

    public ClientIdentity[] zzwr(int $i0) throws  {
        return new ClientIdentity[$i0];
    }
}
