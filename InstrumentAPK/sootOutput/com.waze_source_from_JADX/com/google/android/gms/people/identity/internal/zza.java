package com.google.android.gms.people.identity.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;

/* compiled from: dalvik_source_com.waze.apk */
public class zza implements Creator<AccountToken> {
    static void zza(AccountToken $r0, Parcel $r1, int i) throws  {
        i = zzb.zzea($r1);
        zzb.zza($r1, 1, $r0.getAccountName(), false);
        zzb.zza($r1, 2, $r0.zzccz(), false);
        zzb.zzc($r1, 1000, $r0.getVersionCode());
        zzb.zzaj($r1, i);
    }

    public /* synthetic */ Object createFromParcel(Parcel $r1) throws  {
        return zzsi($r1);
    }

    public /* synthetic */ Object[] newArray(int $i0) throws  {
        return zzaau($i0);
    }

    public AccountToken[] zzaau(int $i0) throws  {
        return new AccountToken[$i0];
    }

    public AccountToken zzsi(Parcel $r1) throws  {
        String $r2 = null;
        int $i0 = com.google.android.gms.common.internal.safeparcel.zza.zzdz($r1);
        int $i1 = 0;
        String $r3 = null;
        while ($r1.dataPosition() < $i0) {
            int $i2 = com.google.android.gms.common.internal.safeparcel.zza.zzdy($r1);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zziv($i2)) {
                case 1:
                    $r3 = com.google.android.gms.common.internal.safeparcel.zza.zzq($r1, $i2);
                    break;
                case 2:
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
            return new AccountToken($i1, $r3, $r2);
        }
        throw new com.google.android.gms.common.internal.safeparcel.zza.zza("Overread allowed size end=" + $i0, $r1);
    }
}
