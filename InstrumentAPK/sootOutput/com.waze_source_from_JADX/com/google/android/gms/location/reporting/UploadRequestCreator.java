package com.google.android.gms.location.reporting;

import android.accounts.Account;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

/* compiled from: dalvik_source_com.waze.apk */
public class UploadRequestCreator implements Creator<UploadRequest> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void zza(UploadRequest $r0, Parcel $r1, int $i0) throws  {
        int $i1 = zzb.zzea($r1);
        zzb.zzc($r1, 1, $r0.getVersionCode());
        zzb.zza($r1, 2, $r0.getAccount(), $i0, false);
        zzb.zza($r1, 3, $r0.getReason(), false);
        zzb.zza($r1, 4, $r0.getDurationMillis());
        zzb.zza($r1, 5, $r0.getMovingLatencyMillis());
        zzb.zza($r1, 6, $r0.getStationaryLatencyMillis());
        zzb.zza($r1, 7, $r0.getAppSpecificKey(), false);
        zzb.zzaj($r1, $i1);
    }

    public UploadRequest createFromParcel(Parcel $r1) throws  {
        long $l0 = 0;
        String $r2 = null;
        int $i1 = zza.zzdz($r1);
        int $i2 = 0;
        long $l3 = 0;
        long $l4 = 0;
        String $r3 = null;
        Account $r4 = null;
        while ($r1.dataPosition() < $i1) {
            int $i5 = zza.zzdy($r1);
            switch (zza.zziv($i5)) {
                case 1:
                    $i2 = zza.zzg($r1, $i5);
                    break;
                case 2:
                    $r4 = (Account) zza.zza($r1, $i5, Account.CREATOR);
                    break;
                case 3:
                    $r3 = zza.zzq($r1, $i5);
                    break;
                case 4:
                    $l4 = zza.zzi($r1, $i5);
                    break;
                case 5:
                    $l3 = zza.zzi($r1, $i5);
                    break;
                case 6:
                    $l0 = zza.zzi($r1, $i5);
                    break;
                case 7:
                    $r2 = zza.zzq($r1, $i5);
                    break;
                default:
                    zza.zzb($r1, $i5);
                    break;
            }
        }
        if ($r1.dataPosition() == $i1) {
            return new UploadRequest($i2, $r4, $r3, $l4, $l3, $l0, $r2);
        }
        throw new zza.zza("Overread allowed size end=" + $i1, $r1);
    }

    public UploadRequest[] newArray(int $i0) throws  {
        return new UploadRequest[$i0];
    }
}
