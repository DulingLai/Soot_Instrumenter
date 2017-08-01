package com.google.android.gms.common.api;

import android.app.PendingIntent;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

/* compiled from: dalvik_source_com.waze.apk */
public class zze implements Creator<Status> {
    static void zza(Status $r0, Parcel $r1, int $i0) throws  {
        int $i1 = zzb.zzea($r1);
        zzb.zzc($r1, 1, $r0.getStatusCode());
        zzb.zza($r1, 2, $r0.getStatusMessage(), false);
        zzb.zza($r1, 3, $r0.getPendingIntent(), $i0, false);
        zzb.zzc($r1, 1000, $r0.getVersionCode());
        zzb.zzaj($r1, $i1);
    }

    public /* synthetic */ Object createFromParcel(Parcel $r1) throws  {
        return zzdm($r1);
    }

    public /* synthetic */ Object[] newArray(int $i0) throws  {
        return zzhr($i0);
    }

    public Status zzdm(Parcel $r1) throws  {
        PendingIntent $r2 = null;
        int $i0 = 0;
        int $i1 = zza.zzdz($r1);
        String $r3 = null;
        int $i2 = 0;
        while ($r1.dataPosition() < $i1) {
            int $i3 = zza.zzdy($r1);
            switch (zza.zziv($i3)) {
                case 1:
                    $i0 = zza.zzg($r1, $i3);
                    break;
                case 2:
                    $r3 = zza.zzq($r1, $i3);
                    break;
                case 3:
                    $r2 = (PendingIntent) zza.zza($r1, $i3, PendingIntent.CREATOR);
                    break;
                case 1000:
                    $i2 = zza.zzg($r1, $i3);
                    break;
                default:
                    zza.zzb($r1, $i3);
                    break;
            }
        }
        if ($r1.dataPosition() == $i1) {
            return new Status($i2, $i0, $r3, $r2);
        }
        throw new zza.zza("Overread allowed size end=" + $i1, $r1);
    }

    public Status[] zzhr(int $i0) throws  {
        return new Status[$i0];
    }
}
