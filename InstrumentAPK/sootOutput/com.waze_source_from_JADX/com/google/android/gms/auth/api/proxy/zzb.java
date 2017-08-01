package com.google.android.gms.auth.api.proxy;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;

/* compiled from: dalvik_source_com.waze.apk */
public class zzb implements Creator<ProxyRequest> {
    static void zza(ProxyRequest $r0, Parcel $r1, int i) throws  {
        i = com.google.android.gms.common.internal.safeparcel.zzb.zzea($r1);
        com.google.android.gms.common.internal.safeparcel.zzb.zza($r1, 1, $r0.url, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc($r1, 2, $r0.httpMethod);
        com.google.android.gms.common.internal.safeparcel.zzb.zza($r1, 3, $r0.timeoutMillis);
        com.google.android.gms.common.internal.safeparcel.zzb.zza($r1, 4, $r0.body, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza($r1, 5, $r0.fk, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc($r1, 1000, $r0.versionCode);
        com.google.android.gms.common.internal.safeparcel.zzb.zzaj($r1, i);
    }

    public /* synthetic */ Object createFromParcel(Parcel $r1) throws  {
        return zzau($r1);
    }

    public /* synthetic */ Object[] newArray(int $i0) throws  {
        return zzda($i0);
    }

    public ProxyRequest zzau(Parcel $r1) throws  {
        int $i0 = 0;
        Bundle $r2 = null;
        int $i1 = zza.zzdz($r1);
        long $l2 = 0;
        byte[] $r3 = null;
        String $r4 = null;
        int $i3 = 0;
        while ($r1.dataPosition() < $i1) {
            int $i4 = zza.zzdy($r1);
            switch (zza.zziv($i4)) {
                case 1:
                    $r4 = zza.zzq($r1, $i4);
                    break;
                case 2:
                    $i0 = zza.zzg($r1, $i4);
                    break;
                case 3:
                    $l2 = zza.zzi($r1, $i4);
                    break;
                case 4:
                    $r3 = zza.zzt($r1, $i4);
                    break;
                case 5:
                    $r2 = zza.zzs($r1, $i4);
                    break;
                case 1000:
                    $i3 = zza.zzg($r1, $i4);
                    break;
                default:
                    zza.zzb($r1, $i4);
                    break;
            }
        }
        if ($r1.dataPosition() == $i1) {
            return new ProxyRequest($i3, $r4, $i0, $l2, $r3, $r2);
        }
        throw new zza.zza("Overread allowed size end=" + $i1, $r1);
    }

    public ProxyRequest[] zzda(int $i0) throws  {
        return new ProxyRequest[$i0];
    }
}
