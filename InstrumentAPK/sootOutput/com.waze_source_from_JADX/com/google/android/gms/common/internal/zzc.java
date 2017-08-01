package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

/* compiled from: dalvik_source_com.waze.apk */
public class zzc implements Creator<AuthAccountRequest> {
    static void zza(AuthAccountRequest $r0, Parcel $r1, int $i0) throws  {
        int $i1 = zzb.zzea($r1);
        zzb.zzc($r1, 1, $r0.mVersionCode);
        zzb.zza($r1, 2, $r0.Il, false);
        zzb.zza($r1, 3, $r0.BY, $i0, false);
        zzb.zza($r1, 4, $r0.Im, false);
        zzb.zza($r1, 5, $r0.In, false);
        zzb.zzaj($r1, $i1);
    }

    public /* synthetic */ Object createFromParcel(Parcel $r1) throws  {
        return zzdr($r1);
    }

    public /* synthetic */ Object[] newArray(int $i0) throws  {
        return zzik($i0);
    }

    public AuthAccountRequest zzdr(Parcel $r1) throws  {
        Integer $r2 = null;
        int $i0 = zza.zzdz($r1);
        int $i1 = 0;
        Integer $r3 = null;
        Scope[] $r4 = null;
        IBinder $r5 = null;
        while ($r1.dataPosition() < $i0) {
            int $i2 = zza.zzdy($r1);
            switch (zza.zziv($i2)) {
                case 1:
                    $i1 = zza.zzg($r1, $i2);
                    break;
                case 2:
                    $r5 = zza.zzr($r1, $i2);
                    break;
                case 3:
                    $r4 = (Scope[]) zza.zzb($r1, $i2, Scope.CREATOR);
                    break;
                case 4:
                    $r3 = zza.zzh($r1, $i2);
                    break;
                case 5:
                    $r2 = zza.zzh($r1, $i2);
                    break;
                default:
                    zza.zzb($r1, $i2);
                    break;
            }
        }
        if ($r1.dataPosition() == $i0) {
            return new AuthAccountRequest($i1, $r5, $r4, $r3, $r2);
        }
        throw new zza.zza("Overread allowed size end=" + $i0, $r1);
    }

    public AuthAccountRequest[] zzik(int $i0) throws  {
        return new AuthAccountRequest[$i0];
    }
}
