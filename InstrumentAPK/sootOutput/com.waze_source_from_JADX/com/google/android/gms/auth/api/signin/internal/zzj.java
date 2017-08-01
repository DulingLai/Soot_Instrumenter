package com.google.android.gms.auth.api.signin.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

/* compiled from: dalvik_source_com.waze.apk */
public class zzj implements Creator<SignInConfiguration> {
    static void zza(SignInConfiguration $r0, Parcel $r1, int $i0) throws  {
        int $i1 = zzb.zzea($r1);
        zzb.zzc($r1, 1, $r0.versionCode);
        zzb.zza($r1, 2, $r0.zzaey(), false);
        zzb.zza($r1, 5, $r0.zzaez(), $i0, false);
        zzb.zzaj($r1, $i1);
    }

    public /* synthetic */ Object createFromParcel(Parcel $r1) throws  {
        return zzaz($r1);
    }

    public /* synthetic */ Object[] newArray(int $i0) throws  {
        return zzdf($i0);
    }

    public SignInConfiguration zzaz(Parcel $r1) throws  {
        GoogleSignInOptions $r2 = null;
        int $i0 = zza.zzdz($r1);
        int $i1 = 0;
        String $r3 = null;
        while ($r1.dataPosition() < $i0) {
            int $i2 = zza.zzdy($r1);
            switch (zza.zziv($i2)) {
                case 1:
                    $i1 = zza.zzg($r1, $i2);
                    continue;
                case 2:
                    $r3 = zza.zzq($r1, $i2);
                    continue;
                case 3:
                case 4:
                    break;
                case 5:
                    $r2 = (GoogleSignInOptions) zza.zza($r1, $i2, GoogleSignInOptions.CREATOR);
                    continue;
                default:
                    break;
            }
            zza.zzb($r1, $i2);
        }
        if ($r1.dataPosition() == $i0) {
            return new SignInConfiguration($i1, $r3, $r2);
        }
        throw new zza.zza("Overread allowed size end=" + $i0, $r1);
    }

    public SignInConfiguration[] zzdf(int $i0) throws  {
        return new SignInConfiguration[$i0];
    }
}
