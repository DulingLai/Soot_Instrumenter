package com.google.android.gms.signin.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import java.util.ArrayList;

/* compiled from: dalvik_source_com.waze.apk */
public class zzc implements Creator<CheckServerAuthResult> {
    static void zza(CheckServerAuthResult $r0, Parcel $r1, int i) throws  {
        i = zzb.zzea($r1);
        zzb.zzc($r1, 1, $r0.mVersionCode);
        zzb.zza($r1, 2, $r0.bgt);
        zzb.zzc($r1, 3, $r0.bgu, false);
        zzb.zzaj($r1, i);
    }

    public /* synthetic */ Object createFromParcel(Parcel $r1) throws  {
        return zzzf($r1);
    }

    public /* synthetic */ Object[] newArray(int $i0) throws  {
        return zzaij($i0);
    }

    public CheckServerAuthResult[] zzaij(int $i0) throws  {
        return new CheckServerAuthResult[$i0];
    }

    public CheckServerAuthResult zzzf(Parcel $r1) throws  {
        boolean $z0 = false;
        int $i0 = zza.zzdz($r1);
        ArrayList $r2 = null;
        int $i1 = 0;
        while ($r1.dataPosition() < $i0) {
            int $i2 = zza.zzdy($r1);
            switch (zza.zziv($i2)) {
                case 1:
                    $i1 = zza.zzg($r1, $i2);
                    break;
                case 2:
                    $z0 = zza.zzc($r1, $i2);
                    break;
                case 3:
                    $r2 = zza.zzc($r1, $i2, Scope.CREATOR);
                    break;
                default:
                    zza.zzb($r1, $i2);
                    break;
            }
        }
        if ($r1.dataPosition() == $i0) {
            return new CheckServerAuthResult($i1, $z0, $r2);
        }
        throw new zza.zza("Overread allowed size end=" + $i0, $r1);
    }
}
