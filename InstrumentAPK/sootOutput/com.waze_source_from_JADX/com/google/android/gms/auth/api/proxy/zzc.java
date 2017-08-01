package com.google.android.gms.auth.api.proxy;

import android.app.PendingIntent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

/* compiled from: dalvik_source_com.waze.apk */
public class zzc implements Creator<ProxyResponse> {
    static void zza(ProxyResponse $r0, Parcel $r1, int $i0) throws  {
        int $i1 = zzb.zzea($r1);
        zzb.zzc($r1, 1, $r0.googlePlayServicesStatusCode);
        zzb.zza($r1, 2, $r0.recoveryAction, $i0, false);
        zzb.zzc($r1, 3, $r0.statusCode);
        zzb.zza($r1, 4, $r0.fk, false);
        zzb.zza($r1, 5, $r0.body, false);
        zzb.zzc($r1, 1000, $r0.versionCode);
        zzb.zzaj($r1, $i1);
    }

    public /* synthetic */ Object createFromParcel(Parcel $r1) throws  {
        return zzav($r1);
    }

    public /* synthetic */ Object[] newArray(int $i0) throws  {
        return zzdb($i0);
    }

    public ProxyResponse zzav(Parcel $r1) throws  {
        byte[] $r2 = null;
        int $i0 = 0;
        int $i1 = zza.zzdz($r1);
        Bundle $r3 = null;
        PendingIntent $r4 = null;
        int $i2 = 0;
        int $i3 = 0;
        while ($r1.dataPosition() < $i1) {
            int $i4 = zza.zzdy($r1);
            switch (zza.zziv($i4)) {
                case 1:
                    $i2 = zza.zzg($r1, $i4);
                    break;
                case 2:
                    $r4 = (PendingIntent) zza.zza($r1, $i4, PendingIntent.CREATOR);
                    break;
                case 3:
                    $i0 = zza.zzg($r1, $i4);
                    break;
                case 4:
                    $r3 = zza.zzs($r1, $i4);
                    break;
                case 5:
                    $r2 = zza.zzt($r1, $i4);
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
            return new ProxyResponse($i3, $i2, $r4, $i0, $r3, $r2);
        }
        throw new zza.zza("Overread allowed size end=" + $i1, $r1);
    }

    public ProxyResponse[] zzdb(int $i0) throws  {
        return new ProxyResponse[$i0];
    }
}
