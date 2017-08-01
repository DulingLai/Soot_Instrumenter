package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

/* compiled from: dalvik_source_com.waze.apk */
public class zzc implements Creator<GestureEvent> {
    static void zza(GestureEvent $r0, Parcel $r1, int i) throws  {
        i = zzb.zzea($r1);
        zzb.zzc($r1, 1, $r0.getType());
        zzb.zza($r1, 2, $r0.getTimeMillis());
        zzb.zza($r1, 3, $r0.getElapsedRealtimeMillis());
        zzb.zzc($r1, 4, $r0.getCount());
        zzb.zza($r1, 5, $r0.isStart());
        zzb.zza($r1, 6, $r0.isEnd());
        zzb.zzc($r1, 1000, $r0.getVersionCode());
        zzb.zzaj($r1, i);
    }

    public /* synthetic */ Object createFromParcel(Parcel $r1) throws  {
        return zzoy($r1);
    }

    public /* synthetic */ Object[] newArray(int $i0) throws  {
        return zzwe($i0);
    }

    public GestureEvent zzoy(Parcel $r1) throws  {
        long $l0 = 0;
        boolean $z0 = false;
        int $i1 = zza.zzdz($r1);
        boolean $z1 = false;
        int $i2 = 0;
        long $l3 = 0;
        int $i4 = 0;
        int $i5 = 0;
        while ($r1.dataPosition() < $i1) {
            int $i6 = zza.zzdy($r1);
            switch (zza.zziv($i6)) {
                case 1:
                    $i4 = zza.zzg($r1, $i6);
                    break;
                case 2:
                    $l3 = zza.zzi($r1, $i6);
                    break;
                case 3:
                    $l0 = zza.zzi($r1, $i6);
                    break;
                case 4:
                    $i2 = zza.zzg($r1, $i6);
                    break;
                case 5:
                    $z1 = zza.zzc($r1, $i6);
                    break;
                case 6:
                    $z0 = zza.zzc($r1, $i6);
                    break;
                case 1000:
                    $i5 = zza.zzg($r1, $i6);
                    break;
                default:
                    zza.zzb($r1, $i6);
                    break;
            }
        }
        if ($r1.dataPosition() == $i1) {
            return new GestureEvent($i5, $i4, $l3, $l0, $i2, $z1, $z0);
        }
        throw new zza.zza("Overread allowed size end=" + $i1, $r1);
    }

    public GestureEvent[] zzwe(int $i0) throws  {
        return new GestureEvent[$i0];
    }
}
