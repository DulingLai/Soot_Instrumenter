package com.google.android.gms.auth.firstparty.shared;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

/* compiled from: dalvik_source_com.waze.apk */
public class FACLDataCreator implements Creator<FACLData> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void zza(FACLData $r0, Parcel $r1, int $i0) throws  {
        int $i1 = zzb.zzea($r1);
        zzb.zzc($r1, 1, $r0.version);
        zzb.zza($r1, 2, $r0.iC, $i0, false);
        zzb.zza($r1, 3, $r0.iD, false);
        zzb.zza($r1, 4, $r0.iE);
        zzb.zza($r1, 5, $r0.iF, false);
        zzb.zzaj($r1, $i1);
    }

    public FACLData createFromParcel(Parcel $r1) throws  {
        boolean $z0 = false;
        String $r2 = null;
        int $i0 = zza.zzdz($r1);
        String $r3 = null;
        FACLConfig $r4 = null;
        int $i1 = 0;
        while ($r1.dataPosition() < $i0) {
            int $i2 = zza.zzdy($r1);
            switch (zza.zziv($i2)) {
                case 1:
                    $i1 = zza.zzg($r1, $i2);
                    break;
                case 2:
                    $r4 = (FACLConfig) zza.zza($r1, $i2, FACLConfig.CREATOR);
                    break;
                case 3:
                    $r3 = zza.zzq($r1, $i2);
                    break;
                case 4:
                    $z0 = zza.zzc($r1, $i2);
                    break;
                case 5:
                    $r2 = zza.zzq($r1, $i2);
                    break;
                default:
                    zza.zzb($r1, $i2);
                    break;
            }
        }
        if ($r1.dataPosition() == $i0) {
            return new FACLData($i1, $r4, $r3, $z0, $r2);
        }
        throw new zza.zza("Overread allowed size end=" + $i0, $r1);
    }

    public FACLData[] newArray(int $i0) throws  {
        return new FACLData[$i0];
    }
}
