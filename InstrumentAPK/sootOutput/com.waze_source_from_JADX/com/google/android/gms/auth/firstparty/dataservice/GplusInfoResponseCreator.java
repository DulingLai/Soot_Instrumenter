package com.google.android.gms.auth.firstparty.dataservice;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

/* compiled from: dalvik_source_com.waze.apk */
public class GplusInfoResponseCreator implements Creator<GplusInfoResponse> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void zza(GplusInfoResponse $r0, Parcel $r1, int i) throws  {
        i = zzb.zzea($r1);
        zzb.zzc($r1, 1, $r0.version);
        zzb.zza($r1, 2, $r0.hr);
        zzb.zza($r1, 3, $r0.firstName, false);
        zzb.zza($r1, 4, $r0.lastName, false);
        zzb.zza($r1, 5, $r0.hs, false);
        zzb.zza($r1, 6, $r0.ht);
        zzb.zza($r1, 7, $r0.hu);
        zzb.zza($r1, 8, $r0.hv, false);
        zzb.zza($r1, 9, $r0.hq, false);
        zzb.zza($r1, 10, $r0.hw, false);
        zzb.zzaj($r1, i);
    }

    public GplusInfoResponse createFromParcel(Parcel $r1) throws  {
        boolean $z0 = false;
        String $r2 = null;
        int $i0 = zza.zzdz($r1);
        String $r3 = null;
        String $r4 = null;
        boolean $z1 = false;
        String $r5 = null;
        String $r6 = null;
        String $r7 = null;
        boolean $z2 = false;
        int $i1 = 0;
        while ($r1.dataPosition() < $i0) {
            int $i2 = zza.zzdy($r1);
            switch (zza.zziv($i2)) {
                case 1:
                    $i1 = zza.zzg($r1, $i2);
                    break;
                case 2:
                    $z2 = zza.zzc($r1, $i2);
                    break;
                case 3:
                    $r7 = zza.zzq($r1, $i2);
                    break;
                case 4:
                    $r6 = zza.zzq($r1, $i2);
                    break;
                case 5:
                    $r5 = zza.zzq($r1, $i2);
                    break;
                case 6:
                    $z1 = zza.zzc($r1, $i2);
                    break;
                case 7:
                    $z0 = zza.zzc($r1, $i2);
                    break;
                case 8:
                    $r4 = zza.zzq($r1, $i2);
                    break;
                case 9:
                    $r3 = zza.zzq($r1, $i2);
                    break;
                case 10:
                    $r2 = zza.zzq($r1, $i2);
                    break;
                default:
                    zza.zzb($r1, $i2);
                    break;
            }
        }
        if ($r1.dataPosition() == $i0) {
            return new GplusInfoResponse($i1, $z2, $r7, $r6, $r5, $z1, $z0, $r4, $r3, $r2);
        }
        throw new zza.zza("Overread allowed size end=" + $i0, $r1);
    }

    public GplusInfoResponse[] newArray(int $i0) throws  {
        return new GplusInfoResponse[$i0];
    }
}
