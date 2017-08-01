package com.google.android.gms.auth;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import java.util.ArrayList;

/* compiled from: dalvik_source_com.waze.apk */
public class zzd implements Creator<TokenData> {
    static void zza(TokenData $r0, Parcel $r1, int i) throws  {
        i = zzb.zzea($r1);
        zzb.zzc($r1, 1, $r0.mVersionCode);
        zzb.zza($r1, 2, $r0.getToken(), false);
        zzb.zza($r1, 3, $r0.zzadr(), false);
        zzb.zza($r1, 4, $r0.zzads());
        zzb.zza($r1, 5, $r0.zzadt());
        zzb.zzb($r1, 6, $r0.zzadu(), false);
        zzb.zzaj($r1, i);
    }

    public /* synthetic */ Object createFromParcel(Parcel $r1) throws  {
        return zzak($r1);
    }

    public /* synthetic */ Object[] newArray(int $i0) throws  {
        return zzcq($i0);
    }

    public TokenData zzak(Parcel $r1) throws  {
        ArrayList $r2 = null;
        boolean $z0 = false;
        int $i0 = zza.zzdz($r1);
        boolean $z1 = false;
        Long $r3 = null;
        String $r4 = null;
        int $i1 = 0;
        while ($r1.dataPosition() < $i0) {
            int $i2 = zza.zzdy($r1);
            switch (zza.zziv($i2)) {
                case 1:
                    $i1 = zza.zzg($r1, $i2);
                    break;
                case 2:
                    $r4 = zza.zzq($r1, $i2);
                    break;
                case 3:
                    $r3 = zza.zzj($r1, $i2);
                    break;
                case 4:
                    $z1 = zza.zzc($r1, $i2);
                    break;
                case 5:
                    $z0 = zza.zzc($r1, $i2);
                    break;
                case 6:
                    $r2 = zza.zzae($r1, $i2);
                    break;
                default:
                    zza.zzb($r1, $i2);
                    break;
            }
        }
        if ($r1.dataPosition() == $i0) {
            return new TokenData($i1, $r4, $r3, $z1, $z0, $r2);
        }
        throw new zza.zza("Overread allowed size end=" + $i0, $r1);
    }

    public TokenData[] zzcq(int $i0) throws  {
        return new TokenData[$i0];
    }
}
