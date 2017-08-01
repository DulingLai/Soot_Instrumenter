package com.google.android.gms.auth.api.credentials;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;
import java.util.ArrayList;

/* compiled from: dalvik_source_com.waze.apk */
public class zza implements Creator<Credential> {
    static void zza(Credential $r0, Parcel $r1, int $i0) throws  {
        int $i1 = zzb.zzea($r1);
        zzb.zza($r1, 1, $r0.getId(), false);
        zzb.zza($r1, 2, $r0.getName(), false);
        zzb.zza($r1, 3, $r0.getProfilePictureUri(), $i0, false);
        zzb.zzc($r1, 4, $r0.getIdTokens(), false);
        zzb.zza($r1, 5, $r0.getPassword(), false);
        zzb.zza($r1, 6, $r0.getAccountType(), false);
        zzb.zza($r1, 7, $r0.getGeneratedPassword(), false);
        zzb.zzc($r1, 1000, $r0.mVersionCode);
        zzb.zza($r1, 8, $r0.zzadx(), false);
        zzb.zzaj($r1, $i1);
    }

    public /* synthetic */ Object createFromParcel(Parcel $r1) throws  {
        return zzal($r1);
    }

    public /* synthetic */ Object[] newArray(int $i0) throws  {
        return zzcr($i0);
    }

    public Credential zzal(Parcel $r1) throws  {
        String $r2 = null;
        int $i0 = com.google.android.gms.common.internal.safeparcel.zza.zzdz($r1);
        int $i1 = 0;
        String $r3 = null;
        String $r4 = null;
        String $r5 = null;
        ArrayList $r6 = null;
        Uri $r7 = null;
        String $r8 = null;
        String $r9 = null;
        while ($r1.dataPosition() < $i0) {
            int $i2 = com.google.android.gms.common.internal.safeparcel.zza.zzdy($r1);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zziv($i2)) {
                case 1:
                    $r9 = com.google.android.gms.common.internal.safeparcel.zza.zzq($r1, $i2);
                    break;
                case 2:
                    $r8 = com.google.android.gms.common.internal.safeparcel.zza.zzq($r1, $i2);
                    break;
                case 3:
                    $r7 = (Uri) com.google.android.gms.common.internal.safeparcel.zza.zza($r1, $i2, Uri.CREATOR);
                    break;
                case 4:
                    $r6 = com.google.android.gms.common.internal.safeparcel.zza.zzc($r1, $i2, IdToken.CREATOR);
                    break;
                case 5:
                    $r5 = com.google.android.gms.common.internal.safeparcel.zza.zzq($r1, $i2);
                    break;
                case 6:
                    $r4 = com.google.android.gms.common.internal.safeparcel.zza.zzq($r1, $i2);
                    break;
                case 7:
                    $r3 = com.google.android.gms.common.internal.safeparcel.zza.zzq($r1, $i2);
                    break;
                case 8:
                    $r2 = com.google.android.gms.common.internal.safeparcel.zza.zzq($r1, $i2);
                    break;
                case 1000:
                    $i1 = com.google.android.gms.common.internal.safeparcel.zza.zzg($r1, $i2);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb($r1, $i2);
                    break;
            }
        }
        if ($r1.dataPosition() == $i0) {
            return new Credential($i1, $r9, $r8, $r7, $r6, $r5, $r4, $r3, $r2);
        }
        throw new com.google.android.gms.common.internal.safeparcel.zza.zza("Overread allowed size end=" + $i0, $r1);
    }

    public Credential[] zzcr(int $i0) throws  {
        return new Credential[$i0];
    }
}
