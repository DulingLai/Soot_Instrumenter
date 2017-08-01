package com.google.android.gms.auth.firstparty.dataservice;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.auth.firstparty.shared.AccountCredentials;
import com.google.android.gms.auth.firstparty.shared.CaptchaSolution;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

/* compiled from: dalvik_source_com.waze.apk */
public class UpdateCredentialsRequestCreator implements Creator<UpdateCredentialsRequest> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void zza(UpdateCredentialsRequest $r0, Parcel $r1, int $i0) throws  {
        int $i1 = zzb.zzea($r1);
        zzb.zzc($r1, 1, $r0.version);
        zzb.zza($r1, 2, $r0.gM, $i0, false);
        zzb.zza($r1, 3, $r0.gq, $i0, false);
        zzb.zzaj($r1, $i1);
    }

    public UpdateCredentialsRequest createFromParcel(Parcel $r1) throws  {
        CaptchaSolution $r2 = null;
        int $i0 = zza.zzdz($r1);
        int $i1 = 0;
        AccountCredentials $r3 = null;
        while ($r1.dataPosition() < $i0) {
            int $i2 = zza.zzdy($r1);
            switch (zza.zziv($i2)) {
                case 1:
                    $i1 = zza.zzg($r1, $i2);
                    break;
                case 2:
                    $r3 = (AccountCredentials) zza.zza($r1, $i2, AccountCredentials.CREATOR);
                    break;
                case 3:
                    $r2 = (CaptchaSolution) zza.zza($r1, $i2, CaptchaSolution.CREATOR);
                    break;
                default:
                    zza.zzb($r1, $i2);
                    break;
            }
        }
        if ($r1.dataPosition() == $i0) {
            return new UpdateCredentialsRequest($i1, $r3, $r2);
        }
        throw new zza.zza("Overread allowed size end=" + $i0, $r1);
    }

    public UpdateCredentialsRequest[] newArray(int $i0) throws  {
        return new UpdateCredentialsRequest[$i0];
    }
}
