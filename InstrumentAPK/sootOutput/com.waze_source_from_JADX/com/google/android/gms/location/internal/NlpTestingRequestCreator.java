package com.google.android.gms.location.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

/* compiled from: dalvik_source_com.waze.apk */
public class NlpTestingRequestCreator implements Creator<NlpTestingRequest> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void zza(NlpTestingRequest $r0, Parcel $r1, int i) throws  {
        i = zzb.zzea($r1);
        zzb.zza($r1, 1, $r0.getTriggers());
        zzb.zzc($r1, 1000, $r0.getVersionCode());
        zzb.zzaj($r1, i);
    }

    public NlpTestingRequest createFromParcel(Parcel $r1) throws  {
        int $i0 = zza.zzdz($r1);
        int $i1 = 0;
        long $l2 = 0;
        while ($r1.dataPosition() < $i0) {
            int $i3 = zza.zzdy($r1);
            switch (zza.zziv($i3)) {
                case 1:
                    $l2 = zza.zzi($r1, $i3);
                    break;
                case 1000:
                    $i1 = zza.zzg($r1, $i3);
                    break;
                default:
                    zza.zzb($r1, $i3);
                    break;
            }
        }
        if ($r1.dataPosition() == $i0) {
            return new NlpTestingRequest($i1, $l2);
        }
        throw new zza.zza("Overread allowed size end=" + $i0, $r1);
    }

    public NlpTestingRequest[] newArray(int $i0) throws  {
        return new NlpTestingRequest[$i0];
    }
}
