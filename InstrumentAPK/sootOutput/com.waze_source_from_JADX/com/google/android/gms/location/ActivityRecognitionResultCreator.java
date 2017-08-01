package com.google.android.gms.location;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import java.util.ArrayList;

/* compiled from: dalvik_source_com.waze.apk */
public class ActivityRecognitionResultCreator implements Creator<ActivityRecognitionResult> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void zza(ActivityRecognitionResult $r0, Parcel $r1, int i) throws  {
        i = zzb.zzea($r1);
        zzb.zzc($r1, 1, $r0.auq, false);
        zzb.zza($r1, 2, $r0.aur);
        zzb.zza($r1, 3, $r0.aus);
        zzb.zzc($r1, 4, $r0.aut);
        zzb.zza($r1, 5, $r0.extras, false);
        zzb.zzc($r1, 1000, $r0.getVersionCode());
        zzb.zzaj($r1, i);
    }

    public ActivityRecognitionResult createFromParcel(Parcel $r1) throws  {
        long $l0 = 0;
        Bundle $r2 = null;
        int $i1 = 0;
        int $i2 = zza.zzdz($r1);
        long $l3 = 0;
        ArrayList $r3 = null;
        int $i4 = 0;
        while ($r1.dataPosition() < $i2) {
            int $i5 = zza.zzdy($r1);
            switch (zza.zziv($i5)) {
                case 1:
                    $r3 = zza.zzc($r1, $i5, DetectedActivity.CREATOR);
                    break;
                case 2:
                    $l3 = zza.zzi($r1, $i5);
                    break;
                case 3:
                    $l0 = zza.zzi($r1, $i5);
                    break;
                case 4:
                    $i1 = zza.zzg($r1, $i5);
                    break;
                case 5:
                    $r2 = zza.zzs($r1, $i5);
                    break;
                case 1000:
                    $i4 = zza.zzg($r1, $i5);
                    break;
                default:
                    zza.zzb($r1, $i5);
                    break;
            }
        }
        if ($r1.dataPosition() == $i2) {
            return new ActivityRecognitionResult($i4, $r3, $l3, $l0, $i1, $r2);
        }
        throw new zza.zza("Overread allowed size end=" + $i2, $r1);
    }

    public ActivityRecognitionResult[] newArray(int $i0) throws  {
        return new ActivityRecognitionResult[$i0];
    }
}
