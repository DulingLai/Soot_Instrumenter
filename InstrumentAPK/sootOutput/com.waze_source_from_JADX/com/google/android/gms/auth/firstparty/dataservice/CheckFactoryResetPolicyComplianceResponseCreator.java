package com.google.android.gms.auth.firstparty.dataservice;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

/* compiled from: dalvik_source_com.waze.apk */
public class CheckFactoryResetPolicyComplianceResponseCreator implements Creator<CheckFactoryResetPolicyComplianceResponse> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void zza(CheckFactoryResetPolicyComplianceResponse $r0, Parcel $r1, int i) throws  {
        i = zzb.zzea($r1);
        zzb.zzc($r1, 1, $r0.version);
        zzb.zza($r1, 2, $r0.isCompliant);
        zzb.zzaj($r1, i);
    }

    public CheckFactoryResetPolicyComplianceResponse createFromParcel(Parcel $r1) throws  {
        boolean $z0 = false;
        int $i0 = zza.zzdz($r1);
        int $i1 = 0;
        while ($r1.dataPosition() < $i0) {
            int $i2 = zza.zzdy($r1);
            switch (zza.zziv($i2)) {
                case 1:
                    $i1 = zza.zzg($r1, $i2);
                    break;
                case 2:
                    $z0 = zza.zzc($r1, $i2);
                    break;
                default:
                    zza.zzb($r1, $i2);
                    break;
            }
        }
        if ($r1.dataPosition() == $i0) {
            return new CheckFactoryResetPolicyComplianceResponse($i1, $z0);
        }
        throw new zza.zza("Overread allowed size end=" + $i0, $r1);
    }

    public CheckFactoryResetPolicyComplianceResponse[] newArray(int $i0) throws  {
        return new CheckFactoryResetPolicyComplianceResponse[$i0];
    }
}
