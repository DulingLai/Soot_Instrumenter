package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

/* compiled from: dalvik_source_com.waze.apk */
public class zzl implements Creator<WifiScan> {
    static void zza(WifiScan $r0, Parcel $r1, int i) throws  {
        i = zzb.zzea($r1);
        zzb.zza($r1, 1, $r0.getElapsedRealtimeMs());
        zzb.zza($r1, 2, $r0.avx, false);
        zzb.zzc($r1, 1000, $r0.getVersionCode());
        zzb.zzaj($r1, i);
    }

    public /* synthetic */ Object createFromParcel(Parcel $r1) throws  {
        return zzpf($r1);
    }

    public /* synthetic */ Object[] newArray(int $i0) throws  {
        return zzwq($i0);
    }

    public WifiScan zzpf(Parcel $r1) throws  {
        int $i0 = zza.zzdz($r1);
        int $i1 = 0;
        long $l2 = 0;
        long[] $r2 = WifiScan.avv;
        while ($r1.dataPosition() < $i0) {
            int $i3 = zza.zzdy($r1);
            switch (zza.zziv($i3)) {
                case 1:
                    $l2 = zza.zzi($r1, $i3);
                    break;
                case 2:
                    $r2 = zza.zzx($r1, $i3);
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
            return new WifiScan($i1, $l2, $r2);
        }
        throw new zza.zza("Overread allowed size end=" + $i0, $r1);
    }

    public WifiScan[] zzwq(int $i0) throws  {
        return new WifiScan[$i0];
    }
}
