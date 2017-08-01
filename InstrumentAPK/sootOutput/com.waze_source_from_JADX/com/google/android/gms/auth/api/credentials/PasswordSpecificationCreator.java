package com.google.android.gms.auth.api.credentials;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import java.util.ArrayList;

/* compiled from: dalvik_source_com.waze.apk */
public class PasswordSpecificationCreator implements Creator<PasswordSpecification> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void zza(PasswordSpecification $r0, Parcel $r1, int i) throws  {
        i = zzb.zzea($r1);
        zzb.zza($r1, 1, $r0.eU, false);
        zzb.zzb($r1, 2, $r0.eV, false);
        zzb.zza($r1, 3, $r0.eW, false);
        zzb.zzc($r1, 4, $r0.eX);
        zzb.zzc($r1, 5, $r0.eY);
        zzb.zzc($r1, 1000, $r0.mVersionCode);
        zzb.zzaj($r1, i);
    }

    public PasswordSpecification createFromParcel(Parcel $r1) throws  {
        ArrayList $r2 = null;
        int $i0 = 0;
        int $i1 = zza.zzdz($r1);
        int $i2 = 0;
        ArrayList $r3 = null;
        String $r4 = null;
        int $i3 = 0;
        while ($r1.dataPosition() < $i1) {
            int $i4 = zza.zzdy($r1);
            switch (zza.zziv($i4)) {
                case 1:
                    $r4 = zza.zzq($r1, $i4);
                    break;
                case 2:
                    $r3 = zza.zzae($r1, $i4);
                    break;
                case 3:
                    $r2 = zza.zzad($r1, $i4);
                    break;
                case 4:
                    $i2 = zza.zzg($r1, $i4);
                    break;
                case 5:
                    $i0 = zza.zzg($r1, $i4);
                    break;
                case 1000:
                    $i3 = zza.zzg($r1, $i4);
                    break;
                default:
                    zza.zzb($r1, $i4);
                    break;
            }
        }
        if ($r1.dataPosition() == $i1) {
            return new PasswordSpecification($i3, $r4, $r3, $r2, $i2, $i0);
        }
        throw new zza.zza("Overread allowed size end=" + $i1, $r1);
    }

    public PasswordSpecification[] newArray(int $i0) throws  {
        return new PasswordSpecification[$i0];
    }
}
