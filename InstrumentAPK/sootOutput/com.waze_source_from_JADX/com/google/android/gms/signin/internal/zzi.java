package com.google.android.gms.signin.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.ResolveAccountResponse;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

/* compiled from: dalvik_source_com.waze.apk */
public class zzi implements Creator<SignInResponse> {
    static void zza(SignInResponse $r0, Parcel $r1, int $i0) throws  {
        int $i1 = zzb.zzea($r1);
        zzb.zzc($r1, 1, $r0.mVersionCode);
        zzb.zza($r1, 2, $r0.zzaxe(), $i0, false);
        zzb.zza($r1, 3, $r0.zzcng(), $i0, false);
        zzb.zzaj($r1, $i1);
    }

    public /* synthetic */ Object createFromParcel(Parcel $r1) throws  {
        return zzzi($r1);
    }

    public /* synthetic */ Object[] newArray(int $i0) throws  {
        return zzain($i0);
    }

    public SignInResponse[] zzain(int $i0) throws  {
        return new SignInResponse[$i0];
    }

    public SignInResponse zzzi(Parcel $r1) throws  {
        ResolveAccountResponse $r2 = null;
        int $i0 = zza.zzdz($r1);
        int $i1 = 0;
        ConnectionResult $r3 = null;
        while ($r1.dataPosition() < $i0) {
            int $i2 = zza.zzdy($r1);
            switch (zza.zziv($i2)) {
                case 1:
                    $i1 = zza.zzg($r1, $i2);
                    break;
                case 2:
                    $r3 = (ConnectionResult) zza.zza($r1, $i2, ConnectionResult.CREATOR);
                    break;
                case 3:
                    $r2 = (ResolveAccountResponse) zza.zza($r1, $i2, ResolveAccountResponse.CREATOR);
                    break;
                default:
                    zza.zzb($r1, $i2);
                    break;
            }
        }
        if ($r1.dataPosition() == $i0) {
            return new SignInResponse($i1, $r3, $r2);
        }
        throw new zza.zza("Overread allowed size end=" + $i0, $r1);
    }
}
