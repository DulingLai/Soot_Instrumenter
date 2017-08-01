package com.google.android.gms.location.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.location.LocationRequest;
import java.util.List;

/* compiled from: dalvik_source_com.waze.apk */
public class zzm implements Creator<LocationRequestInternal> {
    static void zza(LocationRequestInternal $r0, Parcel $r1, int $i0) throws  {
        int $i1 = zzb.zzea($r1);
        zzb.zza($r1, 1, $r0.afq, $i0, false);
        zzb.zza($r1, 4, $r0.aum);
        zzb.zzc($r1, 5, $r0.awe, false);
        zzb.zza($r1, 6, $r0.mTag, false);
        zzb.zza($r1, 7, $r0.awf);
        zzb.zzc($r1, 1000, $r0.getVersionCode());
        zzb.zza($r1, 8, $r0.awg);
        zzb.zzaj($r1, $i1);
    }

    public /* synthetic */ Object createFromParcel(Parcel $r1) throws  {
        return zzpi($r1);
    }

    public /* synthetic */ Object[] newArray(int $i0) throws  {
        return zzwu($i0);
    }

    public LocationRequestInternal zzpi(Parcel $r1) throws  {
        String $r2 = null;
        boolean $z0 = false;
        int $i0 = zza.zzdz($r1);
        boolean $z1 = true;
        List $r3 = LocationRequestInternal.awd;
        boolean $z2 = false;
        LocationRequest $r4 = null;
        int $i1 = 0;
        while ($r1.dataPosition() < $i0) {
            int $i2 = zza.zzdy($r1);
            switch (zza.zziv($i2)) {
                case 1:
                    $r4 = (LocationRequest) zza.zza($r1, $i2, (Creator) LocationRequest.CREATOR);
                    break;
                case 4:
                    $z1 = zza.zzc($r1, $i2);
                    break;
                case 5:
                    $r3 = zza.zzc($r1, $i2, ClientIdentity.CREATOR);
                    break;
                case 6:
                    $r2 = zza.zzq($r1, $i2);
                    break;
                case 7:
                    $z2 = zza.zzc($r1, $i2);
                    break;
                case 8:
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
            return new LocationRequestInternal($i1, $r4, $z1, $r3, $r2, $z2, $z0);
        }
        throw new zza.zza("Overread allowed size end=" + $i0, $r1);
    }

    public LocationRequestInternal[] zzwu(int $i0) throws  {
        return new LocationRequestInternal[$i0];
    }
}
