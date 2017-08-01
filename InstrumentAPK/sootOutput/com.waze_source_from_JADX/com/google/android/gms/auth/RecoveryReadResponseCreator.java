package com.google.android.gms.auth;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import java.util.ArrayList;

/* compiled from: dalvik_source_com.waze.apk */
public class RecoveryReadResponseCreator implements Creator<RecoveryReadResponse> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void zza(RecoveryReadResponse $r0, Parcel $r1, int i) throws  {
        i = zzb.zzea($r1);
        zzb.zzc($r1, 1, $r0.mVersionCode);
        zzb.zza($r1, 2, $r0.mSecondaryEmail, false);
        zzb.zza($r1, 3, $r0.mPhoneNumber, false);
        zzb.zza($r1, 4, $r0.mPhoneCountryCode, false);
        zzb.zzc($r1, 5, $r0.mCountryList, false);
        zzb.zza($r1, 6, $r0.mError, false);
        zzb.zza($r1, 7, $r0.mAction, false);
        zzb.zza($r1, 8, $r0.mAllowedOptions, false);
        zzb.zzaj($r1, i);
    }

    public RecoveryReadResponse createFromParcel(Parcel $r1) throws  {
        String $r2 = null;
        int $i0 = zza.zzdz($r1);
        int $i1 = 0;
        String $r3 = null;
        String $r4 = null;
        ArrayList $r5 = null;
        String $r6 = null;
        String $r7 = null;
        String $r8 = null;
        while ($r1.dataPosition() < $i0) {
            int $i2 = zza.zzdy($r1);
            switch (zza.zziv($i2)) {
                case 1:
                    $i1 = zza.zzg($r1, $i2);
                    break;
                case 2:
                    $r8 = zza.zzq($r1, $i2);
                    break;
                case 3:
                    $r7 = zza.zzq($r1, $i2);
                    break;
                case 4:
                    $r6 = zza.zzq($r1, $i2);
                    break;
                case 5:
                    $r5 = zza.zzc($r1, $i2, Country.CREATOR);
                    break;
                case 6:
                    $r4 = zza.zzq($r1, $i2);
                    break;
                case 7:
                    $r3 = zza.zzq($r1, $i2);
                    break;
                case 8:
                    $r2 = zza.zzq($r1, $i2);
                    break;
                default:
                    zza.zzb($r1, $i2);
                    break;
            }
        }
        if ($r1.dataPosition() == $i0) {
            return new RecoveryReadResponse($i1, $r8, $r7, $r6, $r5, $r4, $r3, $r2);
        }
        throw new zza.zza("Overread allowed size end=" + $i0, $r1);
    }

    public RecoveryReadResponse[] newArray(int $i0) throws  {
        return new RecoveryReadResponse[$i0];
    }
}
