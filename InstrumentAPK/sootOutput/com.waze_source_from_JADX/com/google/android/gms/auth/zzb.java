package com.google.android.gms.auth;

import android.accounts.Account;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;

/* compiled from: dalvik_source_com.waze.apk */
public class zzb implements Creator<AccountChangeEventsRequest> {
    static void zza(AccountChangeEventsRequest $r0, Parcel $r1, int $i0) throws  {
        int $i1 = com.google.android.gms.common.internal.safeparcel.zzb.zzea($r1);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc($r1, 1, $r0.mVersion);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc($r1, 2, $r0.dN);
        com.google.android.gms.common.internal.safeparcel.zzb.zza($r1, 3, $r0.dL, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza($r1, 4, $r0.f25P, $i0, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzaj($r1, $i1);
    }

    public /* synthetic */ Object createFromParcel(Parcel $r1) throws  {
        return zzai($r1);
    }

    public /* synthetic */ Object[] newArray(int $i0) throws  {
        return zzco($i0);
    }

    public AccountChangeEventsRequest zzai(Parcel $r1) throws  {
        Account $r2 = null;
        int $i0 = 0;
        int $i1 = zza.zzdz($r1);
        String $r3 = null;
        int $i2 = 0;
        while ($r1.dataPosition() < $i1) {
            int $i3 = zza.zzdy($r1);
            switch (zza.zziv($i3)) {
                case 1:
                    $i2 = zza.zzg($r1, $i3);
                    break;
                case 2:
                    $i0 = zza.zzg($r1, $i3);
                    break;
                case 3:
                    $r3 = zza.zzq($r1, $i3);
                    break;
                case 4:
                    $r2 = (Account) zza.zza($r1, $i3, Account.CREATOR);
                    break;
                default:
                    zza.zzb($r1, $i3);
                    break;
            }
        }
        if ($r1.dataPosition() == $i1) {
            return new AccountChangeEventsRequest($i2, $i0, $r3, $r2);
        }
        throw new zza.zza("Overread allowed size end=" + $i1, $r1);
    }

    public AccountChangeEventsRequest[] zzco(int $i0) throws  {
        return new AccountChangeEventsRequest[$i0];
    }
}
