package com.google.android.gms.signin.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.ResolveAccountRequest;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

/* compiled from: dalvik_source_com.waze.apk */
public class zzh implements Creator<SignInRequest> {
    static void zza(SignInRequest $r0, Parcel $r1, int $i0) throws  {
        int $i1 = zzb.zzea($r1);
        zzb.zzc($r1, 1, $r0.mVersionCode);
        zzb.zza($r1, 2, $r0.zzcnf(), $i0, false);
        zzb.zzaj($r1, $i1);
    }

    public /* synthetic */ Object createFromParcel(Parcel $r1) throws  {
        return zzzh($r1);
    }

    public /* synthetic */ Object[] newArray(int $i0) throws  {
        return zzaim($i0);
    }

    public SignInRequest[] zzaim(int $i0) throws  {
        return new SignInRequest[$i0];
    }

    public SignInRequest zzzh(Parcel $r1) throws  {
        int $i0 = zza.zzdz($r1);
        int $i1 = 0;
        ResolveAccountRequest $r2 = null;
        while ($r1.dataPosition() < $i0) {
            int $i2 = zza.zzdy($r1);
            switch (zza.zziv($i2)) {
                case 1:
                    $i1 = zza.zzg($r1, $i2);
                    break;
                case 2:
                    $r2 = (ResolveAccountRequest) zza.zza($r1, $i2, ResolveAccountRequest.CREATOR);
                    break;
                default:
                    zza.zzb($r1, $i2);
                    break;
            }
        }
        if ($r1.dataPosition() == $i0) {
            return new SignInRequest($i1, $r2);
        }
        throw new zza.zza("Overread allowed size end=" + $i0, $r1);
    }
}
