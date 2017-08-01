package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

/* compiled from: dalvik_source_com.waze.apk */
public class zzj implements Creator<LocationSettingsStates> {
    static void zza(LocationSettingsStates $r0, Parcel $r1, int i) throws  {
        i = zzb.zzea($r1);
        zzb.zza($r1, 1, $r0.isGpsUsable());
        zzb.zza($r1, 2, $r0.isNetworkLocationUsable());
        zzb.zza($r1, 3, $r0.isBleUsable());
        zzb.zza($r1, 4, $r0.isGpsPresent());
        zzb.zza($r1, 5, $r0.isNetworkLocationPresent());
        zzb.zza($r1, 6, $r0.isBlePresent());
        zzb.zzc($r1, 1000, $r0.getVersionCode());
        zzb.zzaj($r1, i);
    }

    public /* synthetic */ Object createFromParcel(Parcel $r1) throws  {
        return zzpd($r1);
    }

    public /* synthetic */ Object[] newArray(int $i0) throws  {
        return zzwl($i0);
    }

    public LocationSettingsStates zzpd(Parcel $r1) throws  {
        boolean $z0 = false;
        int $i0 = zza.zzdz($r1);
        boolean $z1 = false;
        boolean $z2 = false;
        boolean $z3 = false;
        boolean $z4 = false;
        boolean $z5 = false;
        int $i1 = 0;
        while ($r1.dataPosition() < $i0) {
            int $i2 = zza.zzdy($r1);
            switch (zza.zziv($i2)) {
                case 1:
                    $z5 = zza.zzc($r1, $i2);
                    break;
                case 2:
                    $z4 = zza.zzc($r1, $i2);
                    break;
                case 3:
                    $z3 = zza.zzc($r1, $i2);
                    break;
                case 4:
                    $z2 = zza.zzc($r1, $i2);
                    break;
                case 5:
                    $z1 = zza.zzc($r1, $i2);
                    break;
                case 6:
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
            return new LocationSettingsStates($i1, $z5, $z4, $z3, $z2, $z1, $z0);
        }
        throw new zza.zza("Overread allowed size end=" + $i0, $r1);
    }

    public LocationSettingsStates[] zzwl(int $i0) throws  {
        return new LocationSettingsStates[$i0];
    }
}
