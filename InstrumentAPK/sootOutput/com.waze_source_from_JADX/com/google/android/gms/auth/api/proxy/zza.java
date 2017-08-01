package com.google.android.gms.auth.api.proxy;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;

/* compiled from: dalvik_source_com.waze.apk */
public class zza implements Creator<ProxyGrpcRequest> {
    static void zza(ProxyGrpcRequest $r0, Parcel $r1, int i) throws  {
        i = zzb.zzea($r1);
        zzb.zza($r1, 1, $r0.hostname, false);
        zzb.zzc($r1, 2, $r0.port);
        zzb.zza($r1, 3, $r0.timeoutMillis);
        zzb.zza($r1, 4, $r0.body, false);
        zzb.zza($r1, 5, $r0.method, false);
        zzb.zzc($r1, 1000, $r0.versionCode);
        zzb.zzaj($r1, i);
    }

    public /* synthetic */ Object createFromParcel(Parcel $r1) throws  {
        return zzat($r1);
    }

    public /* synthetic */ Object[] newArray(int $i0) throws  {
        return zzcz($i0);
    }

    public ProxyGrpcRequest zzat(Parcel $r1) throws  {
        int $i0 = 0;
        String $r2 = null;
        int $i1 = com.google.android.gms.common.internal.safeparcel.zza.zzdz($r1);
        long $l2 = 0;
        byte[] $r3 = null;
        String $r4 = null;
        int $i3 = 0;
        while ($r1.dataPosition() < $i1) {
            int $i4 = com.google.android.gms.common.internal.safeparcel.zza.zzdy($r1);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zziv($i4)) {
                case 1:
                    $r4 = com.google.android.gms.common.internal.safeparcel.zza.zzq($r1, $i4);
                    break;
                case 2:
                    $i0 = com.google.android.gms.common.internal.safeparcel.zza.zzg($r1, $i4);
                    break;
                case 3:
                    $l2 = com.google.android.gms.common.internal.safeparcel.zza.zzi($r1, $i4);
                    break;
                case 4:
                    $r3 = com.google.android.gms.common.internal.safeparcel.zza.zzt($r1, $i4);
                    break;
                case 5:
                    $r2 = com.google.android.gms.common.internal.safeparcel.zza.zzq($r1, $i4);
                    break;
                case 1000:
                    $i3 = com.google.android.gms.common.internal.safeparcel.zza.zzg($r1, $i4);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb($r1, $i4);
                    break;
            }
        }
        if ($r1.dataPosition() == $i1) {
            return new ProxyGrpcRequest($i3, $r4, $i0, $l2, $r3, $r2);
        }
        throw new com.google.android.gms.common.internal.safeparcel.zza.zza("Overread allowed size end=" + $i1, $r1);
    }

    public ProxyGrpcRequest[] zzcz(int $i0) throws  {
        return new ProxyGrpcRequest[$i0];
    }
}
