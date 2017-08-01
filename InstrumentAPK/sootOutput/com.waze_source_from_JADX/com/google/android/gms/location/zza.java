package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.os.WorkSource;
import com.google.android.gms.common.internal.safeparcel.zzb;

/* compiled from: dalvik_source_com.waze.apk */
public class zza implements Creator<ActivityRecognitionRequest> {
    static void zza(ActivityRecognitionRequest $r0, Parcel $r1, int $i0) throws  {
        int $i1 = zzb.zzea($r1);
        zzb.zza($r1, 1, $r0.getIntervalMillis());
        zzb.zza($r1, 2, $r0.zzbrz());
        zzb.zza($r1, 3, $r0.zzbsa(), $i0, false);
        zzb.zza($r1, 4, $r0.getTag(), false);
        zzb.zza($r1, 5, $r0.zzbsb(), false);
        zzb.zza($r1, 6, $r0.zzbsc());
        zzb.zza($r1, 7, $r0.getAccountName(), false);
        zzb.zzc($r1, 1000, $r0.getVersionCode());
        zzb.zzaj($r1, $i1);
    }

    public /* synthetic */ Object createFromParcel(Parcel $r1) throws  {
        return zzow($r1);
    }

    public /* synthetic */ Object[] newArray(int $i0) throws  {
        return zzvz($i0);
    }

    public ActivityRecognitionRequest zzow(Parcel $r1) throws  {
        boolean $z0 = false;
        String $r2 = null;
        int $i0 = com.google.android.gms.common.internal.safeparcel.zza.zzdz($r1);
        long $l1 = 0;
        int[] $r3 = null;
        String $r4 = null;
        WorkSource $r5 = null;
        boolean $z1 = false;
        int $i2 = 0;
        while ($r1.dataPosition() < $i0) {
            int $i3 = com.google.android.gms.common.internal.safeparcel.zza.zzdy($r1);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zziv($i3)) {
                case 1:
                    $l1 = com.google.android.gms.common.internal.safeparcel.zza.zzi($r1, $i3);
                    break;
                case 2:
                    $z1 = com.google.android.gms.common.internal.safeparcel.zza.zzc($r1, $i3);
                    break;
                case 3:
                    $r5 = (WorkSource) com.google.android.gms.common.internal.safeparcel.zza.zza($r1, $i3, WorkSource.CREATOR);
                    break;
                case 4:
                    $r4 = com.google.android.gms.common.internal.safeparcel.zza.zzq($r1, $i3);
                    break;
                case 5:
                    $r3 = com.google.android.gms.common.internal.safeparcel.zza.zzw($r1, $i3);
                    break;
                case 6:
                    $z0 = com.google.android.gms.common.internal.safeparcel.zza.zzc($r1, $i3);
                    break;
                case 7:
                    $r2 = com.google.android.gms.common.internal.safeparcel.zza.zzq($r1, $i3);
                    break;
                case 1000:
                    $i2 = com.google.android.gms.common.internal.safeparcel.zza.zzg($r1, $i3);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb($r1, $i3);
                    break;
            }
        }
        if ($r1.dataPosition() == $i0) {
            return new ActivityRecognitionRequest($i2, $l1, $z1, $r5, $r4, $r3, $z0, $r2);
        }
        throw new com.google.android.gms.common.internal.safeparcel.zza.zza("Overread allowed size end=" + $i0, $r1);
    }

    public ActivityRecognitionRequest[] zzvz(int $i0) throws  {
        return new ActivityRecognitionRequest[$i0];
    }
}
