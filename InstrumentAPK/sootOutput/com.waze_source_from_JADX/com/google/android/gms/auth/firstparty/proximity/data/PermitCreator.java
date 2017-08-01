package com.google.android.gms.auth.firstparty.proximity.data;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import java.util.ArrayList;
import java.util.List;

/* compiled from: dalvik_source_com.waze.apk */
public class PermitCreator implements Creator<Permit> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void zza(Permit $r0, Parcel $r1, int $i0) throws  {
        int $i1 = zzb.zzea($r1);
        zzb.zzc($r1, 1, $r0.mVersion);
        zzb.zza($r1, 2, $r0.zzbgd, false);
        zzb.zza($r1, 3, $r0.ia, false);
        zzb.zza($r1, 5, $r0.zzcft, false);
        zzb.zza($r1, 6, $r0.ib, $i0, false);
        zzb.zzc($r1, 7, $r0.ic, false);
        zzb.zzb($r1, 8, $r0.f29if, false);
        zzb.zzaj($r1, $i1);
    }

    public Permit createFromParcel(Parcel $r1) throws  {
        ArrayList $r2 = null;
        int $i0 = zza.zzdz($r1);
        int $i1 = 0;
        ArrayList $r3 = null;
        PermitAccess $r4 = null;
        String $r5 = null;
        String $r6 = null;
        String $r7 = null;
        while ($r1.dataPosition() < $i0) {
            int $i2 = zza.zzdy($r1);
            switch (zza.zziv($i2)) {
                case 1:
                    $i1 = zza.zzg($r1, $i2);
                    continue;
                case 2:
                    $r7 = zza.zzq($r1, $i2);
                    continue;
                case 3:
                    $r6 = zza.zzq($r1, $i2);
                    continue;
                case 4:
                    break;
                case 5:
                    $r5 = zza.zzq($r1, $i2);
                    continue;
                case 6:
                    $r4 = (PermitAccess) zza.zza($r1, $i2, (Creator) PermitAccess.CREATOR);
                    continue;
                case 7:
                    $r3 = zza.zzc($r1, $i2, PermitAccess.CREATOR);
                    continue;
                case 8:
                    $r2 = zza.zzae($r1, $i2);
                    continue;
                default:
                    break;
            }
            zza.zzb($r1, $i2);
        }
        if ($r1.dataPosition() == $i0) {
            return new Permit($i1, $r7, $r6, $r5, $r4, (List) $r3, (List) $r2);
        }
        throw new zza.zza("Overread allowed size end=" + $i0, $r1);
    }

    public Permit[] newArray(int $i0) throws  {
        return new Permit[$i0];
    }
}
