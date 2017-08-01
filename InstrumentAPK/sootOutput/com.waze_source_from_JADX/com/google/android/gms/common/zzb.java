package com.google.android.gms.common;

import android.app.PendingIntent;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;

/* compiled from: dalvik_source_com.waze.apk */
public class zzb implements Creator<ConnectionResult> {
    static void zza(ConnectionResult $r0, Parcel $r1, int $i0) throws  {
        int $i1 = com.google.android.gms.common.internal.safeparcel.zzb.zzea($r1);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc($r1, 1, $r0.mVersionCode);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc($r1, 2, $r0.getErrorCode());
        com.google.android.gms.common.internal.safeparcel.zzb.zza($r1, 3, $r0.getResolution(), $i0, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza($r1, 4, $r0.getErrorMessage(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzaj($r1, $i1);
    }

    public /* synthetic */ Object createFromParcel(Parcel $r1) throws  {
        return zzdk($r1);
    }

    public /* synthetic */ Object[] newArray(int $i0) throws  {
        return zzhn($i0);
    }

    public ConnectionResult zzdk(Parcel $r1) throws  {
        String $r2 = null;
        int $i0 = 0;
        int $i1 = zza.zzdz($r1);
        PendingIntent $r3 = null;
        int $i2 = 0;
        while ($r1.dataPosition() < $i1) {
            int $i3 = zza.zzdy($r1);
            switch (zza.zziv($i3)) {
                case 1:
                    $i2 = zza.zzg($r1, $i3);
                    break;
                case 2:
                    $i0 = zza.zzg($r1, $i3);
                    break;
                case 3:
                    $r3 = (PendingIntent) zza.zza($r1, $i3, PendingIntent.CREATOR);
                    break;
                case 4:
                    $r2 = zza.zzq($r1, $i3);
                    break;
                default:
                    zza.zzb($r1, $i3);
                    break;
            }
        }
        if ($r1.dataPosition() == $i1) {
            return new ConnectionResult($i2, $i0, $r3, $r2);
        }
        throw new zza.zza("Overread allowed size end=" + $i1, $r1);
    }

    public ConnectionResult[] zzhn(int $i0) throws  {
        return new ConnectionResult[$i0];
    }
}
