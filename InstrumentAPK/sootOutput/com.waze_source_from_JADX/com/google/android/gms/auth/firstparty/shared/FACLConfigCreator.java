package com.google.android.gms.auth.firstparty.shared;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

/* compiled from: dalvik_source_com.waze.apk */
public class FACLConfigCreator implements Creator<FACLConfig> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void zza(FACLConfig $r0, Parcel $r1, int i) throws  {
        i = zzb.zzea($r1);
        zzb.zzc($r1, 1, $r0.version);
        zzb.zza($r1, 2, $r0.iw);
        zzb.zza($r1, 3, $r0.ix, false);
        zzb.zza($r1, 4, $r0.iy);
        zzb.zza($r1, 5, $r0.iz);
        zzb.zza($r1, 6, $r0.iA);
        zzb.zza($r1, 7, $r0.iB);
        zzb.zzaj($r1, i);
    }

    public FACLConfig createFromParcel(Parcel $r1) throws  {
        boolean $z0 = false;
        int $i0 = zza.zzdz($r1);
        String $r2 = null;
        boolean $z1 = false;
        boolean $z2 = false;
        boolean $z3 = false;
        boolean $z4 = false;
        int $i1 = 0;
        while ($r1.dataPosition() < $i0) {
            int $i2 = zza.zzdy($r1);
            switch (zza.zziv($i2)) {
                case 1:
                    $i1 = zza.zzg($r1, $i2);
                    break;
                case 2:
                    $z4 = zza.zzc($r1, $i2);
                    break;
                case 3:
                    $r2 = zza.zzq($r1, $i2);
                    break;
                case 4:
                    $z3 = zza.zzc($r1, $i2);
                    break;
                case 5:
                    $z2 = zza.zzc($r1, $i2);
                    break;
                case 6:
                    $z1 = zza.zzc($r1, $i2);
                    break;
                case 7:
                    $z0 = zza.zzc($r1, $i2);
                    break;
                default:
                    zza.zzb($r1, $i2);
                    break;
            }
        }
        if ($r1.dataPosition() == $i0) {
            return new FACLConfig($i1, $z4, $r2, $z3, $z2, $z1, $z0);
        }
        throw new zza.zza("Overread allowed size end=" + $i0, $r1);
    }

    public FACLConfig[] newArray(int $i0) throws  {
        return new FACLConfig[$i0];
    }
}
