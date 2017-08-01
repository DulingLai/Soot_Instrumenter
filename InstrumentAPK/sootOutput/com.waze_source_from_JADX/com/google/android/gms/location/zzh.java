package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import java.util.ArrayList;
import java.util.List;

/* compiled from: dalvik_source_com.waze.apk */
public class zzh implements Creator<LocationSettingsRequest> {
    static void zza(LocationSettingsRequest $r0, Parcel $r1, int i) throws  {
        i = zzb.zzea($r1);
        zzb.zzc($r1, 1, $r0.zzbkm(), false);
        zzb.zza($r1, 2, $r0.zzbse());
        zzb.zza($r1, 3, $r0.zzbsf());
        zzb.zzc($r1, 1000, $r0.getVersionCode());
        zzb.zzaj($r1, i);
    }

    public /* synthetic */ Object createFromParcel(Parcel $r1) throws  {
        return zzpb($r1);
    }

    public /* synthetic */ Object[] newArray(int $i0) throws  {
        return zzwj($i0);
    }

    public LocationSettingsRequest zzpb(Parcel $r1) throws  {
        boolean $z0 = false;
        int $i0 = zza.zzdz($r1);
        ArrayList $r2 = null;
        boolean $z1 = false;
        int $i1 = 0;
        while ($r1.dataPosition() < $i0) {
            int $i2 = zza.zzdy($r1);
            switch (zza.zziv($i2)) {
                case 1:
                    $r2 = zza.zzc($r1, $i2, LocationRequest.CREATOR);
                    break;
                case 2:
                    $z1 = zza.zzc($r1, $i2);
                    break;
                case 3:
                    $z0 = zza.zzc($r1, $i2);
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
            return new LocationSettingsRequest($i1, (List) $r2, $z1, $z0);
        }
        throw new zza.zza("Overread allowed size end=" + $i0, $r1);
    }

    public LocationSettingsRequest[] zzwj(int $i0) throws  {
        return new LocationSettingsRequest[$i0];
    }
}
