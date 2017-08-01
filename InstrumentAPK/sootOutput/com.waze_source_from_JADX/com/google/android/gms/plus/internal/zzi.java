package com.google.android.gms.plus.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

/* compiled from: dalvik_source_com.waze.apk */
public class zzi implements Creator<PlusSession> {
    static void zza(PlusSession $r0, Parcel $r1, int $i0) throws  {
        int $i1 = zzb.zzea($r1);
        zzb.zza($r1, 1, $r0.getAccountName(), false);
        zzb.zza($r1, 2, $r0.zzcgv(), false);
        zzb.zza($r1, 3, $r0.zzcgw(), false);
        zzb.zza($r1, 4, $r0.zzcgx(), false);
        zzb.zza($r1, 5, $r0.zzcgy(), false);
        zzb.zza($r1, 6, $r0.zzcgz(), false);
        zzb.zza($r1, 7, $r0.zzapi(), false);
        zzb.zzc($r1, 1000, $r0.getVersionCode());
        zzb.zza($r1, 8, $r0.zzcha(), false);
        zzb.zza($r1, 9, $r0.zzchb(), $i0, false);
        zzb.zzaj($r1, $i1);
    }

    public /* synthetic */ Object createFromParcel(Parcel $r1) throws  {
        return zzvd($r1);
    }

    public /* synthetic */ Object[] newArray(int $i0) throws  {
        return zzaeg($i0);
    }

    public PlusSession[] zzaeg(int $i0) throws  {
        return new PlusSession[$i0];
    }

    public PlusSession zzvd(Parcel $r1) throws  {
        PlusCommonExtras $r2 = null;
        int $i0 = zza.zzdz($r1);
        int $i1 = 0;
        String $r3 = null;
        String $r4 = null;
        String $r5 = null;
        String $r6 = null;
        String[] $r7 = null;
        String[] $r8 = null;
        String[] $r9 = null;
        String $r10 = null;
        while ($r1.dataPosition() < $i0) {
            int $i2 = zza.zzdy($r1);
            switch (zza.zziv($i2)) {
                case 1:
                    $r10 = zza.zzq($r1, $i2);
                    break;
                case 2:
                    $r9 = zza.zzac($r1, $i2);
                    break;
                case 3:
                    $r8 = zza.zzac($r1, $i2);
                    break;
                case 4:
                    $r7 = zza.zzac($r1, $i2);
                    break;
                case 5:
                    $r6 = zza.zzq($r1, $i2);
                    break;
                case 6:
                    $r5 = zza.zzq($r1, $i2);
                    break;
                case 7:
                    $r4 = zza.zzq($r1, $i2);
                    break;
                case 8:
                    $r3 = zza.zzq($r1, $i2);
                    break;
                case 9:
                    $r2 = (PlusCommonExtras) zza.zza($r1, $i2, (Creator) PlusCommonExtras.CREATOR);
                    break;
                case 1000:
                    $i1 = zza.zzg($r1, $i2);
                    break;
                default:
                    zza.zzb($r1, $i2);
                    break;
            }
        }
        if ($r1.dataPosition() == $i0) {
            return new PlusSession($i1, $r10, $r9, $r8, $r7, $r6, $r5, $r4, $r3, $r2);
        }
        throw new zza.zza("Overread allowed size end=" + $i0, $r1);
    }
}
