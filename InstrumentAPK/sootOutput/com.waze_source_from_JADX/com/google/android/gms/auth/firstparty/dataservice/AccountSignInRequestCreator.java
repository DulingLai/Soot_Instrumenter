package com.google.android.gms.auth.firstparty.dataservice;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.auth.firstparty.shared.AccountCredentials;
import com.google.android.gms.auth.firstparty.shared.AppDescription;
import com.google.android.gms.auth.firstparty.shared.CaptchaSolution;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

/* compiled from: dalvik_source_com.waze.apk */
public class AccountSignInRequestCreator implements Creator<AccountSignInRequest> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void zza(AccountSignInRequest $r0, Parcel $r1, int $i0) throws  {
        int $i1 = zzb.zzea($r1);
        zzb.zzc($r1, 1, $r0.version);
        zzb.zza($r1, 2, $r0.callingAppDescription, $i0, false);
        zzb.zza($r1, 3, $r0.gK);
        zzb.zza($r1, 4, $r0.gL);
        zzb.zza($r1, 5, $r0.gq, $i0, false);
        zzb.zza($r1, 6, $r0.gM, $i0, false);
        zzb.zzaj($r1, $i1);
    }

    public AccountSignInRequest createFromParcel(Parcel $r1) throws  {
        AccountCredentials $r2 = null;
        boolean $z0 = false;
        int $i0 = zza.zzdz($r1);
        CaptchaSolution $r3 = null;
        boolean $z1 = false;
        AppDescription $r4 = null;
        int $i1 = 0;
        while ($r1.dataPosition() < $i0) {
            int $i2 = zza.zzdy($r1);
            switch (zza.zziv($i2)) {
                case 1:
                    $i1 = zza.zzg($r1, $i2);
                    break;
                case 2:
                    $r4 = (AppDescription) zza.zza($r1, $i2, (Creator) AppDescription.CREATOR);
                    break;
                case 3:
                    $z1 = zza.zzc($r1, $i2);
                    break;
                case 4:
                    $z0 = zza.zzc($r1, $i2);
                    break;
                case 5:
                    $r3 = (CaptchaSolution) zza.zza($r1, $i2, (Creator) CaptchaSolution.CREATOR);
                    break;
                case 6:
                    $r2 = (AccountCredentials) zza.zza($r1, $i2, (Creator) AccountCredentials.CREATOR);
                    break;
                default:
                    zza.zzb($r1, $i2);
                    break;
            }
        }
        if ($r1.dataPosition() == $i0) {
            return new AccountSignInRequest($i1, $r4, $z1, $z0, $r3, $r2);
        }
        throw new zza.zza("Overread allowed size end=" + $i0, $r1);
    }

    public AccountSignInRequest[] newArray(int $i0) throws  {
        return new AccountSignInRequest[$i0];
    }
}
