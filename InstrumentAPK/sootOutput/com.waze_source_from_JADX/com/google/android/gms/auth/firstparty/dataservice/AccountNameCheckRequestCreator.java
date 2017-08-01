package com.google.android.gms.auth.firstparty.dataservice;

import android.accounts.Account;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.auth.firstparty.shared.AppDescription;
import com.google.android.gms.auth.firstparty.shared.CaptchaSolution;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

/* compiled from: dalvik_source_com.waze.apk */
public class AccountNameCheckRequestCreator implements Creator<AccountNameCheckRequest> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void zza(AccountNameCheckRequest $r0, Parcel $r1, int $i0) throws  {
        int $i1 = zzb.zzea($r1);
        zzb.zzc($r1, 1, $r0.version);
        zzb.zza($r1, 2, $r0.gn, false);
        zzb.zza($r1, 3, $r0.go, false);
        zzb.zza($r1, 4, $r0.gp, false);
        zzb.zza($r1, 5, $r0.callingAppDescription, $i0, false);
        zzb.zza($r1, 6, $r0.gq, $i0, false);
        zzb.zza($r1, 7, $r0.gr, $i0, false);
        zzb.zzaj($r1, $i1);
    }

    public AccountNameCheckRequest createFromParcel(Parcel $r1) throws  {
        Account $r2 = null;
        int $i0 = zza.zzdz($r1);
        int $i1 = 0;
        CaptchaSolution $r3 = null;
        AppDescription $r4 = null;
        String $r5 = null;
        String $r6 = null;
        String $r7 = null;
        while ($r1.dataPosition() < $i0) {
            int $i2 = zza.zzdy($r1);
            switch (zza.zziv($i2)) {
                case 1:
                    $i1 = zza.zzg($r1, $i2);
                    break;
                case 2:
                    $r7 = zza.zzq($r1, $i2);
                    break;
                case 3:
                    $r6 = zza.zzq($r1, $i2);
                    break;
                case 4:
                    $r5 = zza.zzq($r1, $i2);
                    break;
                case 5:
                    $r4 = (AppDescription) zza.zza($r1, $i2, (Creator) AppDescription.CREATOR);
                    break;
                case 6:
                    $r3 = (CaptchaSolution) zza.zza($r1, $i2, (Creator) CaptchaSolution.CREATOR);
                    break;
                case 7:
                    $r2 = (Account) zza.zza($r1, $i2, Account.CREATOR);
                    break;
                default:
                    zza.zzb($r1, $i2);
                    break;
            }
        }
        if ($r1.dataPosition() == $i0) {
            return new AccountNameCheckRequest($i1, $r7, $r6, $r5, $r4, $r3, $r2);
        }
        throw new zza.zza("Overread allowed size end=" + $i0, $r1);
    }

    public AccountNameCheckRequest[] newArray(int $i0) throws  {
        return new AccountNameCheckRequest[$i0];
    }
}
