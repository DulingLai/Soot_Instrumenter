package com.google.android.gms.auth.firstparty.dataservice;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.auth.firstparty.shared.AppDescription;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

/* compiled from: dalvik_source_com.waze.apk */
public class OtpRequestCreator implements Creator<OtpRequest> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void zza(OtpRequest $r0, Parcel $r1, int $i0) throws  {
        int $i1 = zzb.zzea($r1);
        zzb.zzc($r1, 1, $r0.mVersion);
        zzb.zza($r1, 2, $r0.accountName, false);
        zzb.zza($r1, 3, $r0.callerDescription, $i0, false);
        zzb.zza($r1, 4, $r0.challenge, false);
        zzb.zza($r1, 5, $r0.isLegacyRequest);
        zzb.zzaj($r1, $i1);
    }

    public OtpRequest createFromParcel(Parcel $r1) throws  {
        boolean $z0 = false;
        byte[] $r2 = null;
        int $i0 = zza.zzdz($r1);
        AppDescription $r3 = null;
        String $r4 = null;
        int $i1 = 0;
        while ($r1.dataPosition() < $i0) {
            int $i2 = zza.zzdy($r1);
            switch (zza.zziv($i2)) {
                case 1:
                    $i1 = zza.zzg($r1, $i2);
                    break;
                case 2:
                    $r4 = zza.zzq($r1, $i2);
                    break;
                case 3:
                    $r3 = (AppDescription) zza.zza($r1, $i2, AppDescription.CREATOR);
                    break;
                case 4:
                    $r2 = zza.zzt($r1, $i2);
                    break;
                case 5:
                    $z0 = zza.zzc($r1, $i2);
                    break;
                default:
                    zza.zzb($r1, $i2);
                    break;
            }
        }
        if ($r1.dataPosition() == $i0) {
            return new OtpRequest($i1, $r4, $r3, $r2, $z0);
        }
        throw new zza.zza("Overread allowed size end=" + $i0, $r1);
    }

    public OtpRequest[] newArray(int $i0) throws  {
        return new OtpRequest[$i0];
    }
}
