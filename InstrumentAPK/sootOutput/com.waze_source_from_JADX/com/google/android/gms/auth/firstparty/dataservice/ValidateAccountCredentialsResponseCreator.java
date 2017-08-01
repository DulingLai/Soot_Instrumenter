package com.google.android.gms.auth.firstparty.dataservice;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

/* compiled from: dalvik_source_com.waze.apk */
public class ValidateAccountCredentialsResponseCreator implements Creator<ValidateAccountCredentialsResponse> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void zza(ValidateAccountCredentialsResponse $r0, Parcel $r1, int i) throws  {
        i = zzb.zzea($r1);
        zzb.zzc($r1, 1, $r0.version);
        zzb.zzc($r1, 2, $r0.status);
        zzb.zza($r1, 3, $r0.accountId, false);
        zzb.zzaj($r1, i);
    }

    public ValidateAccountCredentialsResponse createFromParcel(Parcel $r1) throws  {
        int $i0 = 0;
        int $i1 = zza.zzdz($r1);
        String $r2 = null;
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
                    $r2 = zza.zzq($r1, $i3);
                    break;
                default:
                    zza.zzb($r1, $i3);
                    break;
            }
        }
        if ($r1.dataPosition() == $i1) {
            return new ValidateAccountCredentialsResponse($i2, $i0, $r2);
        }
        throw new zza.zza("Overread allowed size end=" + $i1, $r1);
    }

    public ValidateAccountCredentialsResponse[] newArray(int $i0) throws  {
        return new ValidateAccountCredentialsResponse[$i0];
    }
}
