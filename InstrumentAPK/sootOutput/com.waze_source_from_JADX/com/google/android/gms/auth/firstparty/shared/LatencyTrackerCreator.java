package com.google.android.gms.auth.firstparty.shared;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

/* compiled from: dalvik_source_com.waze.apk */
public class LatencyTrackerCreator implements Creator<LatencyTracker> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void zza(LatencyTracker $r0, Parcel $r1, int $i0) throws  {
        int $i1 = zzb.zzea($r1);
        zzb.zzc($r1, 1, $r0.mVersion);
        zzb.zza($r1, 2, $r0.mName, false);
        zzb.zza($r1, 3, $r0.iG);
        zzb.zza($r1, 4, $r0.parent, $i0, false);
        zzb.zzaj($r1, $i1);
    }

    public LatencyTracker createFromParcel(Parcel $r1) throws  {
        LatencyTracker $r2 = null;
        int $i0 = zza.zzdz($r1);
        int $i1 = 0;
        long $l2 = 0;
        String $r3 = null;
        while ($r1.dataPosition() < $i0) {
            int $i3 = zza.zzdy($r1);
            switch (zza.zziv($i3)) {
                case 1:
                    $i1 = zza.zzg($r1, $i3);
                    break;
                case 2:
                    $r3 = zza.zzq($r1, $i3);
                    break;
                case 3:
                    $l2 = zza.zzi($r1, $i3);
                    break;
                case 4:
                    $r2 = (LatencyTracker) zza.zza($r1, $i3, (Creator) LatencyTracker.CREATOR);
                    break;
                default:
                    zza.zzb($r1, $i3);
                    break;
            }
        }
        if ($r1.dataPosition() == $i0) {
            return new LatencyTracker($i1, $r3, $l2, $r2);
        }
        throw new zza.zza("Overread allowed size end=" + $i0, $r1);
    }

    public LatencyTracker[] newArray(int $i0) throws  {
        return new LatencyTracker[$i0];
    }
}
