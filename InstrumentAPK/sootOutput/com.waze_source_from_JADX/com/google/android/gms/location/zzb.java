package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.location.internal.ParcelableGeofence;
import java.util.ArrayList;
import java.util.List;

/* compiled from: dalvik_source_com.waze.apk */
public class zzb implements Creator<GeofencingRequest> {
    static void zza(GeofencingRequest $r0, Parcel $r1, int i) throws  {
        i = com.google.android.gms.common.internal.safeparcel.zzb.zzea($r1);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc($r1, 1, $r0.zzbsd(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc($r1, 2, $r0.getInitialTrigger());
        com.google.android.gms.common.internal.safeparcel.zzb.zzc($r1, 1000, $r0.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.zzb.zzaj($r1, i);
    }

    public /* synthetic */ Object createFromParcel(Parcel $r1) throws  {
        return zzox($r1);
    }

    public /* synthetic */ Object[] newArray(int $i0) throws  {
        return zzwd($i0);
    }

    public GeofencingRequest zzox(Parcel $r1) throws  {
        int $i0 = 0;
        int $i1 = zza.zzdz($r1);
        ArrayList $r2 = null;
        int $i2 = 0;
        while ($r1.dataPosition() < $i1) {
            int $i3 = zza.zzdy($r1);
            switch (zza.zziv($i3)) {
                case 1:
                    $r2 = zza.zzc($r1, $i3, ParcelableGeofence.CREATOR);
                    break;
                case 2:
                    $i0 = zza.zzg($r1, $i3);
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
            return new GeofencingRequest($i2, (List) $r2, $i0);
        }
        throw new zza.zza("Overread allowed size end=" + $i1, $r1);
    }

    public GeofencingRequest[] zzwd(int $i0) throws  {
        return new GeofencingRequest[$i0];
    }
}
