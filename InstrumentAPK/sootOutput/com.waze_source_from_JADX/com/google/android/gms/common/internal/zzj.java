package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

/* compiled from: dalvik_source_com.waze.apk */
public class zzj implements Creator<GetServiceRequest> {
    static void zza(GetServiceRequest $r0, Parcel $r1, int $i0) throws  {
        int $i1 = zzb.zzea($r1);
        zzb.zzc($r1, 1, $r0.version);
        zzb.zzc($r1, 2, $r0.Ju);
        zzb.zzc($r1, 3, $r0.Jv);
        zzb.zza($r1, 4, $r0.Jw, false);
        zzb.zza($r1, 5, $r0.Jx, false);
        zzb.zza($r1, 6, $r0.Jy, $i0, false);
        zzb.zza($r1, 7, $r0.Jz, false);
        zzb.zza($r1, 8, $r0.JA, $i0, false);
        zzb.zza($r1, 9, $r0.JB);
        zzb.zzaj($r1, $i1);
    }

    public /* synthetic */ Object createFromParcel(Parcel $r1) throws  {
        return zzdt($r1);
    }

    public /* synthetic */ Object[] newArray(int $i0) throws  {
        return zzin($i0);
    }

    public GetServiceRequest zzdt(Parcel $r1) throws  {
        int $i0 = 0;
        Account $r2 = null;
        int $i1 = zza.zzdz($r1);
        long $l2 = 0;
        Bundle $r3 = null;
        Scope[] $r4 = null;
        IBinder $r5 = null;
        String $r6 = null;
        int $i3 = 0;
        int $i4 = 0;
        while ($r1.dataPosition() < $i1) {
            int $i5 = zza.zzdy($r1);
            switch (zza.zziv($i5)) {
                case 1:
                    $i4 = zza.zzg($r1, $i5);
                    break;
                case 2:
                    $i3 = zza.zzg($r1, $i5);
                    break;
                case 3:
                    $i0 = zza.zzg($r1, $i5);
                    break;
                case 4:
                    $r6 = zza.zzq($r1, $i5);
                    break;
                case 5:
                    $r5 = zza.zzr($r1, $i5);
                    break;
                case 6:
                    $r4 = (Scope[]) zza.zzb($r1, $i5, Scope.CREATOR);
                    break;
                case 7:
                    $r3 = zza.zzs($r1, $i5);
                    break;
                case 8:
                    $r2 = (Account) zza.zza($r1, $i5, Account.CREATOR);
                    break;
                case 9:
                    $l2 = zza.zzi($r1, $i5);
                    break;
                default:
                    zza.zzb($r1, $i5);
                    break;
            }
        }
        if ($r1.dataPosition() == $i1) {
            return new GetServiceRequest($i4, $i3, $i0, $r6, $r5, $r4, $r3, $r2, $l2);
        }
        throw new zza.zza("Overread allowed size end=" + $i1, $r1);
    }

    public GetServiceRequest[] zzin(int $i0) throws  {
        return new GetServiceRequest[$i0];
    }
}
