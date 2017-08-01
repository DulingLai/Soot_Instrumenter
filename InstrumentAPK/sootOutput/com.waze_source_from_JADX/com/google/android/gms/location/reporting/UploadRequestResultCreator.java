package com.google.android.gms.location.reporting;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

/* compiled from: dalvik_source_com.waze.apk */
public class UploadRequestResultCreator implements Creator<UploadRequestResult> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void zza(UploadRequestResult $r0, Parcel $r1, int i) throws  {
        i = zzb.zzea($r1);
        zzb.zzc($r1, 1, $r0.getVersionCode());
        zzb.zzc($r1, 2, $r0.getResultCode());
        zzb.zza($r1, 3, $r0.getRequestId());
        zzb.zzaj($r1, i);
    }

    public UploadRequestResult createFromParcel(Parcel $r1) throws  {
        int $i0 = 0;
        int $i1 = zza.zzdz($r1);
        long $l2 = 0;
        int $i3 = 0;
        while ($r1.dataPosition() < $i1) {
            int $i4 = zza.zzdy($r1);
            switch (zza.zziv($i4)) {
                case 1:
                    $i3 = zza.zzg($r1, $i4);
                    break;
                case 2:
                    $i0 = zza.zzg($r1, $i4);
                    break;
                case 3:
                    $l2 = zza.zzi($r1, $i4);
                    break;
                default:
                    zza.zzb($r1, $i4);
                    break;
            }
        }
        if ($r1.dataPosition() == $i1) {
            return new UploadRequestResult($i3, $i0, $l2);
        }
        throw new zza.zza("Overread allowed size end=" + $i1, $r1);
    }

    public UploadRequestResult[] newArray(int $i0) throws  {
        return new UploadRequestResult[$i0];
    }
}
