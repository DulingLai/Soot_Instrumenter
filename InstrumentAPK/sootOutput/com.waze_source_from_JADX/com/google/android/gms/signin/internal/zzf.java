package com.google.android.gms.signin.internal;

import android.accounts.Account;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

/* compiled from: dalvik_source_com.waze.apk */
public class zzf implements Creator<RecordConsentRequest> {
    static void zza(RecordConsentRequest $r0, Parcel $r1, int $i0) throws  {
        int $i1 = zzb.zzea($r1);
        zzb.zzc($r1, 1, $r0.mVersionCode);
        zzb.zza($r1, 2, $r0.getAccount(), $i0, false);
        zzb.zza($r1, 3, $r0.zzcnd(), $i0, false);
        zzb.zza($r1, 4, $r0.zzaem(), false);
        zzb.zzaj($r1, $i1);
    }

    public /* synthetic */ Object createFromParcel(Parcel $r1) throws  {
        return zzzg($r1);
    }

    public /* synthetic */ Object[] newArray(int $i0) throws  {
        return zzail($i0);
    }

    public RecordConsentRequest[] zzail(int $i0) throws  {
        return new RecordConsentRequest[$i0];
    }

    public RecordConsentRequest zzzg(Parcel $r1) throws  {
        String $r2 = null;
        int $i0 = zza.zzdz($r1);
        int $i1 = 0;
        Scope[] $r3 = null;
        Account $r4 = null;
        while ($r1.dataPosition() < $i0) {
            int $i2 = zza.zzdy($r1);
            switch (zza.zziv($i2)) {
                case 1:
                    $i1 = zza.zzg($r1, $i2);
                    break;
                case 2:
                    $r4 = (Account) zza.zza($r1, $i2, Account.CREATOR);
                    break;
                case 3:
                    $r3 = (Scope[]) zza.zzb($r1, $i2, Scope.CREATOR);
                    break;
                case 4:
                    $r2 = zza.zzq($r1, $i2);
                    break;
                default:
                    zza.zzb($r1, $i2);
                    break;
            }
        }
        if ($r1.dataPosition() == $i0) {
            return new RecordConsentRequest($i1, $r4, $r3, $r2);
        }
        throw new zza.zza("Overread allowed size end=" + $i0, $r1);
    }
}
