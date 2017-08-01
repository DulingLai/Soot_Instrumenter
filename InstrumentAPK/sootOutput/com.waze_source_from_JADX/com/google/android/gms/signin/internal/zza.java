package com.google.android.gms.signin.internal;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;

/* compiled from: dalvik_source_com.waze.apk */
public class zza implements Creator<AuthAccountResult> {
    static void zza(AuthAccountResult $r0, Parcel $r1, int $i0) throws  {
        int $i1 = zzb.zzea($r1);
        zzb.zzc($r1, 1, $r0.mVersionCode);
        zzb.zzc($r1, 2, $r0.zzcnb());
        zzb.zza($r1, 3, $r0.zzcnc(), $i0, false);
        zzb.zzaj($r1, $i1);
    }

    public /* synthetic */ Object createFromParcel(Parcel $r1) throws  {
        return zzze($r1);
    }

    public /* synthetic */ Object[] newArray(int $i0) throws  {
        return zzaii($i0);
    }

    public AuthAccountResult[] zzaii(int $i0) throws  {
        return new AuthAccountResult[$i0];
    }

    public AuthAccountResult zzze(Parcel $r1) throws  {
        int $i0 = 0;
        int $i1 = com.google.android.gms.common.internal.safeparcel.zza.zzdz($r1);
        Intent $r2 = null;
        int $i2 = 0;
        while ($r1.dataPosition() < $i1) {
            int $i3 = com.google.android.gms.common.internal.safeparcel.zza.zzdy($r1);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zziv($i3)) {
                case 1:
                    $i2 = com.google.android.gms.common.internal.safeparcel.zza.zzg($r1, $i3);
                    break;
                case 2:
                    $i0 = com.google.android.gms.common.internal.safeparcel.zza.zzg($r1, $i3);
                    break;
                case 3:
                    $r2 = (Intent) com.google.android.gms.common.internal.safeparcel.zza.zza($r1, $i3, Intent.CREATOR);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb($r1, $i3);
                    break;
            }
        }
        if ($r1.dataPosition() == $i1) {
            return new AuthAccountResult($i2, $i0, $r2);
        }
        throw new com.google.android.gms.common.internal.safeparcel.zza.zza("Overread allowed size end=" + $i1, $r1);
    }
}
