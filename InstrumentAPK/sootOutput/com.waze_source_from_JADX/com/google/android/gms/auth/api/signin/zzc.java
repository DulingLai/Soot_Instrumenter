package com.google.android.gms.auth.api.signin;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

/* compiled from: dalvik_source_com.waze.apk */
public class zzc implements Creator<SignInAccount> {
    static void zza(SignInAccount $r0, Parcel $r1, int $i0) throws  {
        int $i1 = zzb.zzea($r1);
        zzb.zzc($r1, 1, $r0.versionCode);
        zzb.zza($r1, 4, $r0.fw, false);
        zzb.zza($r1, 7, $r0.zzaeo(), $i0, false);
        zzb.zza($r1, 8, $r0.zzcva, false);
        zzb.zzaj($r1, $i1);
    }

    public /* synthetic */ Object createFromParcel(Parcel $r1) throws  {
        return zzay($r1);
    }

    public /* synthetic */ Object[] newArray(int $i0) throws  {
        return zzde($i0);
    }

    public SignInAccount zzay(Parcel $r1) throws  {
        int $i0 = zza.zzdz($r1);
        int $i1 = 0;
        String $r2 = "";
        GoogleSignInAccount $r3 = null;
        String $r4 = "";
        while ($r1.dataPosition() < $i0) {
            int $i2 = zza.zzdy($r1);
            switch (zza.zziv($i2)) {
                case 1:
                    $i1 = zza.zzg($r1, $i2);
                    continue;
                case 2:
                case 3:
                case 5:
                case 6:
                    break;
                case 4:
                    $r2 = zza.zzq($r1, $i2);
                    continue;
                case 7:
                    $r3 = (GoogleSignInAccount) zza.zza($r1, $i2, GoogleSignInAccount.CREATOR);
                    continue;
                case 8:
                    $r4 = zza.zzq($r1, $i2);
                    continue;
                default:
                    break;
            }
            zza.zzb($r1, $i2);
        }
        if ($r1.dataPosition() == $i0) {
            return new SignInAccount($i1, $r2, $r3, $r4);
        }
        throw new zza.zza("Overread allowed size end=" + $i0, $r1);
    }

    public SignInAccount[] zzde(int $i0) throws  {
        return new SignInAccount[$i0];
    }
}
