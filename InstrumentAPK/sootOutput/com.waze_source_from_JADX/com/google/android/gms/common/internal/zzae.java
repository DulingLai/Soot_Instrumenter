package com.google.android.gms.common.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

/* compiled from: dalvik_source_com.waze.apk */
public class zzae implements Creator<SignInButtonConfig> {
    static void zza(SignInButtonConfig $r0, Parcel $r1, int $i0) throws  {
        int $i1 = zzb.zzea($r1);
        zzb.zzc($r1, 1, $r0.mVersionCode);
        zzb.zzc($r1, 2, $r0.zzaxh());
        zzb.zzc($r1, 3, $r0.zzaxi());
        zzb.zza($r1, 4, $r0.zzaxj(), $i0, false);
        zzb.zzaj($r1, $i1);
    }

    public /* synthetic */ Object createFromParcel(Parcel $r1) throws  {
        return zzdw($r1);
    }

    public /* synthetic */ Object[] newArray(int $i0) throws  {
        return zzit($i0);
    }

    public SignInButtonConfig zzdw(Parcel $r1) throws  {
        int $i0 = 0;
        int $i1 = zza.zzdz($r1);
        Scope[] $r2 = null;
        int $i2 = 0;
        int $i3 = 0;
        while ($r1.dataPosition() < $i1) {
            int $i4 = zza.zzdy($r1);
            switch (zza.zziv($i4)) {
                case 1:
                    $i3 = zza.zzg($r1, $i4);
                    break;
                case 2:
                    $i2 = zza.zzg($r1, $i4);
                    break;
                case 3:
                    $i0 = zza.zzg($r1, $i4);
                    break;
                case 4:
                    $r2 = (Scope[]) zza.zzb($r1, $i4, Scope.CREATOR);
                    break;
                default:
                    zza.zzb($r1, $i4);
                    break;
            }
        }
        if ($r1.dataPosition() == $i1) {
            return new SignInButtonConfig($i3, $i2, $i0, $r2);
        }
        throw new zza.zza("Overread allowed size end=" + $i1, $r1);
    }

    public SignInButtonConfig[] zzit(int $i0) throws  {
        return new SignInButtonConfig[$i0];
    }
}
