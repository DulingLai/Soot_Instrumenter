package com.google.android.gms.auth;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;

/* compiled from: dalvik_source_com.waze.apk */
public class zza implements Creator<AccountChangeEvent> {
    static void zza(AccountChangeEvent $r0, Parcel $r1, int i) throws  {
        i = zzb.zzea($r1);
        zzb.zzc($r1, 1, $r0.mVersion);
        zzb.zza($r1, 2, $r0.mId);
        zzb.zza($r1, 3, $r0.dL, false);
        zzb.zzc($r1, 4, $r0.dM);
        zzb.zzc($r1, 5, $r0.dN);
        zzb.zza($r1, 6, $r0.dO, false);
        zzb.zzaj($r1, i);
    }

    public /* synthetic */ Object createFromParcel(Parcel $r1) throws  {
        return zzah($r1);
    }

    public /* synthetic */ Object[] newArray(int $i0) throws  {
        return zzcn($i0);
    }

    public AccountChangeEvent zzah(Parcel $r1) throws  {
        String $r2 = null;
        int $i0 = 0;
        int $i1 = com.google.android.gms.common.internal.safeparcel.zza.zzdz($r1);
        long $l2 = 0;
        int $i3 = 0;
        String $r3 = null;
        int $i4 = 0;
        while ($r1.dataPosition() < $i1) {
            int $i5 = com.google.android.gms.common.internal.safeparcel.zza.zzdy($r1);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zziv($i5)) {
                case 1:
                    $i4 = com.google.android.gms.common.internal.safeparcel.zza.zzg($r1, $i5);
                    break;
                case 2:
                    $l2 = com.google.android.gms.common.internal.safeparcel.zza.zzi($r1, $i5);
                    break;
                case 3:
                    $r3 = com.google.android.gms.common.internal.safeparcel.zza.zzq($r1, $i5);
                    break;
                case 4:
                    $i3 = com.google.android.gms.common.internal.safeparcel.zza.zzg($r1, $i5);
                    break;
                case 5:
                    $i0 = com.google.android.gms.common.internal.safeparcel.zza.zzg($r1, $i5);
                    break;
                case 6:
                    $r2 = com.google.android.gms.common.internal.safeparcel.zza.zzq($r1, $i5);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb($r1, $i5);
                    break;
            }
        }
        if ($r1.dataPosition() == $i1) {
            return new AccountChangeEvent($i4, $l2, $r3, $i3, $i0, $r2);
        }
        throw new com.google.android.gms.common.internal.safeparcel.zza.zza("Overread allowed size end=" + $i1, $r1);
    }

    public AccountChangeEvent[] zzcn(int $i0) throws  {
        return new AccountChangeEvent[$i0];
    }
}
