package com.google.android.gms.location.reporting;

import android.accounts.Account;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

/* compiled from: dalvik_source_com.waze.apk */
public class OptInRequestCreator implements Creator<OptInRequest> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void zza(OptInRequest $r0, Parcel $r1, int $i0) throws  {
        int $i1 = zzb.zzea($r1);
        zzb.zzc($r1, 1, $r0.getVersionCode());
        zzb.zza($r1, 2, $r0.getAccount(), $i0, false);
        zzb.zza($r1, 3, $r0.getTag(), false);
        zzb.zzaj($r1, $i1);
    }

    public OptInRequest createFromParcel(Parcel $r1) throws  {
        String $r2 = null;
        int $i0 = zza.zzdz($r1);
        int $i1 = 0;
        Account $r3 = null;
        while ($r1.dataPosition() < $i0) {
            int $i2 = zza.zzdy($r1);
            switch (zza.zziv($i2)) {
                case 1:
                    $i1 = zza.zzg($r1, $i2);
                    break;
                case 2:
                    $r3 = (Account) zza.zza($r1, $i2, Account.CREATOR);
                    break;
                case 3:
                    $r2 = zza.zzq($r1, $i2);
                    break;
                default:
                    zza.zzb($r1, $i2);
                    break;
            }
        }
        if ($r1.dataPosition() == $i0) {
            return new OptInRequest($i1, $r3, $r2);
        }
        throw new zza.zza("Overread allowed size end=" + $i0, $r1);
    }

    public OptInRequest[] newArray(int $i0) throws  {
        return new OptInRequest[$i0];
    }
}
