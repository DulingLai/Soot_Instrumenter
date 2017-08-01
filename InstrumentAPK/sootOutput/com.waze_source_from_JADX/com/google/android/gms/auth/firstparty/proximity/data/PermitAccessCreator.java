package com.google.android.gms.auth.firstparty.proximity.data;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

/* compiled from: dalvik_source_com.waze.apk */
public class PermitAccessCreator implements Creator<PermitAccess> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void zza(PermitAccess $r0, Parcel $r1, int i) throws  {
        i = zzb.zzea($r1);
        zzb.zzc($r1, 1, $r0.mVersion);
        zzb.zza($r1, 2, $r0.zzbgd, false);
        zzb.zza($r1, 3, $r0.zzcft, false);
        zzb.zza($r1, 4, $r0.mData, false);
        zzb.zza($r1, 5, $r0.mName, false);
        zzb.zza($r1, 6, $r0.ih);
        zzb.zza($r1, 7, $r0.ii);
        zzb.zza($r1, 8, $r0.ij);
        zzb.zzaj($r1, i);
    }

    public PermitAccess createFromParcel(Parcel $r1) throws  {
        String $r2 = null;
        boolean $z0 = false;
        int $i0 = zza.zzdz($r1);
        boolean $z1 = false;
        boolean $z2 = false;
        byte[] $r3 = null;
        String $r4 = null;
        String $r5 = null;
        int $i1 = 0;
        while ($r1.dataPosition() < $i0) {
            int $i2 = zza.zzdy($r1);
            switch (zza.zziv($i2)) {
                case 1:
                    $i1 = zza.zzg($r1, $i2);
                    break;
                case 2:
                    $r5 = zza.zzq($r1, $i2);
                    break;
                case 3:
                    $r4 = zza.zzq($r1, $i2);
                    break;
                case 4:
                    $r3 = zza.zzt($r1, $i2);
                    break;
                case 5:
                    $r2 = zza.zzq($r1, $i2);
                    break;
                case 6:
                    $z2 = zza.zzc($r1, $i2);
                    break;
                case 7:
                    $z1 = zza.zzc($r1, $i2);
                    break;
                case 8:
                    $z0 = zza.zzc($r1, $i2);
                    break;
                default:
                    zza.zzb($r1, $i2);
                    break;
            }
        }
        if ($r1.dataPosition() == $i0) {
            return new PermitAccess($i1, $r5, $r4, $r3, $r2, $z2, $z1, $z0);
        }
        throw new zza.zza("Overread allowed size end=" + $i0, $r1);
    }

    public PermitAccess[] newArray(int $i0) throws  {
        return new PermitAccess[$i0];
    }
}
