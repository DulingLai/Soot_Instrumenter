package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

/* compiled from: dalvik_source_com.waze.apk */
public class zzac implements Creator<ResolveAccountRequest> {
    static void zza(ResolveAccountRequest $r0, Parcel $r1, int $i0) throws  {
        int $i1 = zzb.zzea($r1);
        zzb.zzc($r1, 1, $r0.mVersionCode);
        zzb.zza($r1, 2, $r0.getAccount(), $i0, false);
        zzb.zzc($r1, 3, $r0.getSessionId());
        zzb.zza($r1, 4, $r0.zzaxc(), $i0, false);
        zzb.zzaj($r1, $i1);
    }

    public /* synthetic */ Object createFromParcel(Parcel $r1) throws  {
        return zzdu($r1);
    }

    public /* synthetic */ Object[] newArray(int $i0) throws  {
        return zzir($i0);
    }

    public ResolveAccountRequest zzdu(Parcel $r1) throws  {
        GoogleSignInAccount $r2 = null;
        int $i0 = 0;
        int $i1 = zza.zzdz($r1);
        Account $r3 = null;
        int $i2 = 0;
        while ($r1.dataPosition() < $i1) {
            int $i3 = zza.zzdy($r1);
            switch (zza.zziv($i3)) {
                case 1:
                    $i2 = zza.zzg($r1, $i3);
                    break;
                case 2:
                    $r3 = (Account) zza.zza($r1, $i3, Account.CREATOR);
                    break;
                case 3:
                    $i0 = zza.zzg($r1, $i3);
                    break;
                case 4:
                    $r2 = (GoogleSignInAccount) zza.zza($r1, $i3, GoogleSignInAccount.CREATOR);
                    break;
                default:
                    zza.zzb($r1, $i3);
                    break;
            }
        }
        if ($r1.dataPosition() == $i1) {
            return new ResolveAccountRequest($i2, $r3, $i0, $r2);
        }
        throw new zza.zza("Overread allowed size end=" + $i1, $r1);
    }

    public ResolveAccountRequest[] zzir(int $i0) throws  {
        return new ResolveAccountRequest[$i0];
    }
}
