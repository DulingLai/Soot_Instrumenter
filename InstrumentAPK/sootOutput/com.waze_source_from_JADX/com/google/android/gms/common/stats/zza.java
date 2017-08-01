package com.google.android.gms.common.stats;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;

/* compiled from: dalvik_source_com.waze.apk */
public class zza implements Creator<ConnectionEvent> {
    static void zza(ConnectionEvent $r0, Parcel $r1, int i) throws  {
        i = zzb.zzea($r1);
        zzb.zzc($r1, 1, $r0.mVersionCode);
        zzb.zza($r1, 2, $r0.getTimeMillis());
        zzb.zza($r1, 4, $r0.zzaya(), false);
        zzb.zza($r1, 5, $r0.zzayb(), false);
        zzb.zza($r1, 6, $r0.zzayc(), false);
        zzb.zza($r1, 7, $r0.zzayd(), false);
        zzb.zza($r1, 8, $r0.zzaye(), false);
        zzb.zza($r1, 10, $r0.zzayh());
        zzb.zza($r1, 11, $r0.zzayg());
        zzb.zzc($r1, 12, $r0.getEventType());
        zzb.zza($r1, 13, $r0.zzayf(), false);
        zzb.zzaj($r1, i);
    }

    public /* synthetic */ Object createFromParcel(Parcel $r1) throws  {
        return zzei($r1);
    }

    public /* synthetic */ Object[] newArray(int $i0) throws  {
        return zzjg($i0);
    }

    public ConnectionEvent zzei(Parcel $r1) throws  {
        int $i0 = com.google.android.gms.common.internal.safeparcel.zza.zzdz($r1);
        int $i1 = 0;
        long $l2 = 0;
        int $i3 = 0;
        String $r2 = null;
        String $r3 = null;
        String $r4 = null;
        String $r5 = null;
        String $r6 = null;
        String $r7 = null;
        long $l4 = 0;
        long $l5 = 0;
        while ($r1.dataPosition() < $i0) {
            int $i6 = com.google.android.gms.common.internal.safeparcel.zza.zzdy($r1);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zziv($i6)) {
                case 1:
                    $i1 = com.google.android.gms.common.internal.safeparcel.zza.zzg($r1, $i6);
                    continue;
                case 2:
                    $l2 = com.google.android.gms.common.internal.safeparcel.zza.zzi($r1, $i6);
                    continue;
                case 3:
                case 9:
                    break;
                case 4:
                    $r2 = com.google.android.gms.common.internal.safeparcel.zza.zzq($r1, $i6);
                    continue;
                case 5:
                    $r3 = com.google.android.gms.common.internal.safeparcel.zza.zzq($r1, $i6);
                    continue;
                case 6:
                    $r4 = com.google.android.gms.common.internal.safeparcel.zza.zzq($r1, $i6);
                    continue;
                case 7:
                    $r5 = com.google.android.gms.common.internal.safeparcel.zza.zzq($r1, $i6);
                    continue;
                case 8:
                    $r6 = com.google.android.gms.common.internal.safeparcel.zza.zzq($r1, $i6);
                    continue;
                case 10:
                    $l4 = com.google.android.gms.common.internal.safeparcel.zza.zzi($r1, $i6);
                    continue;
                case 11:
                    $l5 = com.google.android.gms.common.internal.safeparcel.zza.zzi($r1, $i6);
                    continue;
                case 12:
                    $i3 = com.google.android.gms.common.internal.safeparcel.zza.zzg($r1, $i6);
                    continue;
                case 13:
                    $r7 = com.google.android.gms.common.internal.safeparcel.zza.zzq($r1, $i6);
                    continue;
                default:
                    break;
            }
            com.google.android.gms.common.internal.safeparcel.zza.zzb($r1, $i6);
        }
        if ($r1.dataPosition() == $i0) {
            return new ConnectionEvent($i1, $l2, $i3, $r2, $r3, $r4, $r5, $r6, $r7, $l4, $l5);
        }
        throw new com.google.android.gms.common.internal.safeparcel.zza.zza("Overread allowed size end=" + $i0, $r1);
    }

    public ConnectionEvent[] zzjg(int $i0) throws  {
        return new ConnectionEvent[$i0];
    }
}
