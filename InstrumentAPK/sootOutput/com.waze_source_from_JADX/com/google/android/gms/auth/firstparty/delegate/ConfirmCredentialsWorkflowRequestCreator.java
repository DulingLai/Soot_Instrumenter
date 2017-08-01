package com.google.android.gms.auth.firstparty.delegate;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.auth.firstparty.shared.AppDescription;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

/* compiled from: dalvik_source_com.waze.apk */
public class ConfirmCredentialsWorkflowRequestCreator implements Creator<ConfirmCredentialsWorkflowRequest> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void zza(ConfirmCredentialsWorkflowRequest $r0, Parcel $r1, int $i0) throws  {
        int $i1 = zzb.zzea($r1);
        zzb.zzc($r1, 1, $r0.version);
        zzb.zza($r1, 2, $r0.accountName, false);
        zzb.zza($r1, 3, $r0.callingAppDescription, $i0, false);
        zzb.zza($r1, 4, $r0.options, false);
        zzb.zza($r1, 5, $r0.account, $i0, false);
        zzb.zza($r1, 6, $r0.amResponse, $i0, false);
        zzb.zzaj($r1, $i1);
    }

    public ConfirmCredentialsWorkflowRequest createFromParcel(Parcel $r1) throws  {
        AccountAuthenticatorResponse $r2 = null;
        int $i0 = zza.zzdz($r1);
        int $i1 = 0;
        Bundle $r3 = new Bundle();
        Account $r4 = null;
        AppDescription $r5 = null;
        String $r6 = null;
        while ($r1.dataPosition() < $i0) {
            int $i2 = zza.zzdy($r1);
            switch (zza.zziv($i2)) {
                case 1:
                    $i1 = zza.zzg($r1, $i2);
                    break;
                case 2:
                    $r6 = zza.zzq($r1, $i2);
                    break;
                case 3:
                    $r5 = (AppDescription) zza.zza($r1, $i2, (Creator) AppDescription.CREATOR);
                    break;
                case 4:
                    $r3 = zza.zzs($r1, $i2);
                    break;
                case 5:
                    $r4 = (Account) zza.zza($r1, $i2, Account.CREATOR);
                    break;
                case 6:
                    $r2 = (AccountAuthenticatorResponse) zza.zza($r1, $i2, AccountAuthenticatorResponse.CREATOR);
                    break;
                default:
                    zza.zzb($r1, $i2);
                    break;
            }
        }
        if ($r1.dataPosition() == $i0) {
            return new ConfirmCredentialsWorkflowRequest($i1, $r6, $r5, $r3, $r4, $r2);
        }
        throw new zza.zza("Overread allowed size end=" + $i0, $r1);
    }

    public ConfirmCredentialsWorkflowRequest[] newArray(int $i0) throws  {
        return new ConfirmCredentialsWorkflowRequest[$i0];
    }
}
