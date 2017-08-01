package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

/* compiled from: dalvik_source_com.waze.apk */
public class zzi implements Creator<LocationSettingsResult> {
    static void zza(LocationSettingsResult $r0, Parcel $r1, int $i0) throws  {
        int $i1 = zzb.zzea($r1);
        zzb.zza($r1, 1, $r0.getStatus(), $i0, false);
        zzb.zza($r1, 2, $r0.getLocationSettingsStates(), $i0, false);
        zzb.zzc($r1, 1000, $r0.getVersionCode());
        zzb.zzaj($r1, $i1);
    }

    public /* synthetic */ Object createFromParcel(Parcel $r1) throws  {
        return zzpc($r1);
    }

    public /* synthetic */ Object[] newArray(int $i0) throws  {
        return zzwk($i0);
    }

    public LocationSettingsResult zzpc(Parcel $r1) throws  {
        LocationSettingsStates $r2 = null;
        int $i0 = zza.zzdz($r1);
        int $i1 = 0;
        Status $r3 = null;
        while ($r1.dataPosition() < $i0) {
            int $i2 = zza.zzdy($r1);
            switch (zza.zziv($i2)) {
                case 1:
                    $r3 = (Status) zza.zza($r1, $i2, Status.CREATOR);
                    break;
                case 2:
                    $r2 = (LocationSettingsStates) zza.zza($r1, $i2, LocationSettingsStates.CREATOR);
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
            return new LocationSettingsResult($i1, $r3, $r2);
        }
        throw new zza.zza("Overread allowed size end=" + $i0, $r1);
    }

    public LocationSettingsResult[] zzwk(int $i0) throws  {
        return new LocationSettingsResult[$i0];
    }
}
