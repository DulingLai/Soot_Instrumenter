package com.google.android.gms.auth.firstparty.delegate;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.auth.firstparty.shared.AppDescription;
import com.google.android.gms.auth.firstparty.shared.FACLConfig;
import com.google.android.gms.auth.firstparty.shared.PACLConfig;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

/* compiled from: dalvik_source_com.waze.apk */
public class TokenWorkflowRequestCreator implements Creator<TokenWorkflowRequest> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void zza(TokenWorkflowRequest $r0, Parcel $r1, int $i0) throws  {
        int $i1 = zzb.zzea($r1);
        zzb.zzc($r1, 1, $r0.version);
        zzb.zza($r1, 2, $r0.hz, false);
        zzb.zza($r1, 3, $r0.accountName, false);
        zzb.zza($r1, 4, $r0.options, false);
        zzb.zza($r1, 5, $r0.hA, $i0, false);
        zzb.zza($r1, 6, $r0.hB, $i0, false);
        zzb.zza($r1, 7, $r0.hX);
        zzb.zza($r1, 8, $r0.callingAppDescription, $i0, false);
        zzb.zza($r1, 9, $r0.account, $i0, false);
        zzb.zza($r1, 10, $r0.amResponse, $i0, false);
        zzb.zzaj($r1, $i1);
    }

    public TokenWorkflowRequest createFromParcel(Parcel $r1) throws  {
        boolean $z0 = false;
        AccountAuthenticatorResponse $r2 = null;
        int $i0 = zza.zzdz($r1);
        Bundle $r3 = new Bundle();
        Account $r4 = null;
        AppDescription $r5 = null;
        PACLConfig $r6 = null;
        FACLConfig $r7 = null;
        String $r8 = null;
        String $r9 = null;
        int $i1 = 0;
        while ($r1.dataPosition() < $i0) {
            int $i2 = zza.zzdy($r1);
            switch (zza.zziv($i2)) {
                case 1:
                    $i1 = zza.zzg($r1, $i2);
                    break;
                case 2:
                    $r9 = zza.zzq($r1, $i2);
                    break;
                case 3:
                    $r8 = zza.zzq($r1, $i2);
                    break;
                case 4:
                    $r3 = zza.zzs($r1, $i2);
                    break;
                case 5:
                    $r7 = (FACLConfig) zza.zza($r1, $i2, (Creator) FACLConfig.CREATOR);
                    break;
                case 6:
                    $r6 = (PACLConfig) zza.zza($r1, $i2, (Creator) PACLConfig.CREATOR);
                    break;
                case 7:
                    $z0 = zza.zzc($r1, $i2);
                    break;
                case 8:
                    $r5 = (AppDescription) zza.zza($r1, $i2, (Creator) AppDescription.CREATOR);
                    break;
                case 9:
                    $r4 = (Account) zza.zza($r1, $i2, Account.CREATOR);
                    break;
                case 10:
                    $r2 = (AccountAuthenticatorResponse) zza.zza($r1, $i2, AccountAuthenticatorResponse.CREATOR);
                    break;
                default:
                    zza.zzb($r1, $i2);
                    break;
            }
        }
        if ($r1.dataPosition() == $i0) {
            return new TokenWorkflowRequest($i1, $r9, $r8, $r3, $r7, $r6, $z0, $r5, $r4, $r2);
        }
        throw new zza.zza("Overread allowed size end=" + $i0, $r1);
    }

    public TokenWorkflowRequest[] newArray(int $i0) throws  {
        return new TokenWorkflowRequest[$i0];
    }
}
