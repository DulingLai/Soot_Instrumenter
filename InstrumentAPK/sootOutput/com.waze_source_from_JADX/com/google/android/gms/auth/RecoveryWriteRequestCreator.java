package com.google.android.gms.auth;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

/* compiled from: dalvik_source_com.waze.apk */
public class RecoveryWriteRequestCreator implements Creator<RecoveryWriteRequest> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void zza(RecoveryWriteRequest $r0, Parcel $r1, int i) throws  {
        i = zzb.zzea($r1);
        zzb.zzc($r1, 1, $r0.mVersionCode);
        zzb.zza($r1, 2, $r0.mAccount, false);
        zzb.zza($r1, 3, $r0.mSecondaryEmail, false);
        zzb.zza($r1, 4, $r0.mPhoneNumber, false);
        zzb.zza($r1, 5, $r0.mPhoneCountryCode, false);
        zzb.zza($r1, 6, $r0.mIsBroadUse);
        zzb.zzaj($r1, i);
    }

    public RecoveryWriteRequest createFromParcel(Parcel $r1) throws  {
        boolean $z0 = false;
        String $r2 = null;
        int $i0 = zza.zzdz($r1);
        String $r3 = null;
        String $r4 = null;
        String $r5 = null;
        int $i1 = 0;
        while ($r1.dataPosition() < $i0) {
            int $i2 = zza.zzdy($r1);
            switch (zza.zziv($i2)) {
                case 1:
                    $i1 = zza.zzg($r1, $i2);
                    break;
                case 2:
                    $r5 = zza.zzq($r1, $i2);
                    break;
                case 3:
                    $r4 = zza.zzq($r1, $i2);
                    break;
                case 4:
                    $r3 = zza.zzq($r1, $i2);
                    break;
                case 5:
                    $r2 = zza.zzq($r1, $i2);
                    break;
                case 6:
                    $z0 = zza.zzc($r1, $i2);
                    break;
                default:
                    zza.zzb($r1, $i2);
                    break;
            }
        }
        if ($r1.dataPosition() == $i0) {
            return new RecoveryWriteRequest($i1, $r5, $r4, $r3, $r2, $z0);
        }
        throw new zza.zza("Overread allowed size end=" + $i0, $r1);
    }

    public RecoveryWriteRequest[] newArray(int $i0) throws  {
        return new RecoveryWriteRequest[$i0];
    }
}
