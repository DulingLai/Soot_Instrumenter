package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

/* compiled from: dalvik_source_com.waze.apk */
public class zzad implements Creator<ResolveAccountResponse> {
    static void zza(ResolveAccountResponse $r0, Parcel $r1, int $i0) throws  {
        int $i1 = zzb.zzea($r1);
        zzb.zzc($r1, 1, $r0.mVersionCode);
        zzb.zza($r1, 2, $r0.Il, false);
        zzb.zza($r1, 3, $r0.zzaxe(), $i0, false);
        zzb.zza($r1, 4, $r0.zzaxf());
        zzb.zza($r1, 5, $r0.zzaxg());
        zzb.zzaj($r1, $i1);
    }

    public /* synthetic */ Object createFromParcel(Parcel $r1) throws  {
        return zzdv($r1);
    }

    public /* synthetic */ Object[] newArray(int $i0) throws  {
        return zzis($i0);
    }

    public ResolveAccountResponse zzdv(Parcel $r1) throws  {
        ConnectionResult $r2 = null;
        boolean $z0 = false;
        int $i0 = zza.zzdz($r1);
        boolean $z1 = false;
        IBinder $r3 = null;
        int $i1 = 0;
        while ($r1.dataPosition() < $i0) {
            int $i2 = zza.zzdy($r1);
            switch (zza.zziv($i2)) {
                case 1:
                    $i1 = zza.zzg($r1, $i2);
                    break;
                case 2:
                    $r3 = zza.zzr($r1, $i2);
                    break;
                case 3:
                    $r2 = (ConnectionResult) zza.zza($r1, $i2, ConnectionResult.CREATOR);
                    break;
                case 4:
                    $z1 = zza.zzc($r1, $i2);
                    break;
                case 5:
                    $z0 = zza.zzc($r1, $i2);
                    break;
                default:
                    zza.zzb($r1, $i2);
                    break;
            }
        }
        if ($r1.dataPosition() == $i0) {
            return new ResolveAccountResponse($i1, $r3, $r2, $z1, $z0);
        }
        throw new zza.zza("Overread allowed size end=" + $i0, $r1);
    }

    public ResolveAccountResponse[] zzis(int $i0) throws  {
        return new ResolveAccountResponse[$i0];
    }
}
