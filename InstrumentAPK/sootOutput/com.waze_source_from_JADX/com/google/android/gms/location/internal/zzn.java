package com.google.android.gms.location.internal;

import android.app.PendingIntent;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

/* compiled from: dalvik_source_com.waze.apk */
public class zzn implements Creator<LocationRequestUpdateData> {
    static void zza(LocationRequestUpdateData $r0, Parcel $r1, int $i0) throws  {
        int $i1 = zzb.zzea($r1);
        zzb.zzc($r1, 1, $r0.awh);
        zzb.zza($r1, 2, $r0.awi, $i0, false);
        zzb.zza($r1, 3, $r0.zzbsl(), false);
        zzb.zza($r1, 4, $r0.mPendingIntent, $i0, false);
        zzb.zza($r1, 5, $r0.zzbsm(), false);
        zzb.zza($r1, 6, $r0.zzbsn(), false);
        zzb.zzc($r1, 1000, $r0.getVersionCode());
        zzb.zzaj($r1, $i1);
    }

    public /* synthetic */ Object createFromParcel(Parcel $r1) throws  {
        return zzpj($r1);
    }

    public /* synthetic */ Object[] newArray(int $i0) throws  {
        return zzwv($i0);
    }

    public LocationRequestUpdateData zzpj(Parcel $r1) throws  {
        IBinder $r2 = null;
        int $i0 = zza.zzdz($r1);
        int $i1 = 0;
        int $i2 = 1;
        IBinder $r3 = null;
        PendingIntent $r4 = null;
        IBinder $r5 = null;
        LocationRequestInternal $r6 = null;
        while ($r1.dataPosition() < $i0) {
            int $i3 = zza.zzdy($r1);
            switch (zza.zziv($i3)) {
                case 1:
                    $i2 = zza.zzg($r1, $i3);
                    break;
                case 2:
                    $r6 = (LocationRequestInternal) zza.zza($r1, $i3, (Creator) LocationRequestInternal.CREATOR);
                    break;
                case 3:
                    $r5 = zza.zzr($r1, $i3);
                    break;
                case 4:
                    $r4 = (PendingIntent) zza.zza($r1, $i3, PendingIntent.CREATOR);
                    break;
                case 5:
                    $r3 = zza.zzr($r1, $i3);
                    break;
                case 6:
                    $r2 = zza.zzr($r1, $i3);
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
            return new LocationRequestUpdateData($i1, $i2, $r6, $r5, $r4, $r3, $r2);
        }
        throw new zza.zza("Overread allowed size end=" + $i0, $r1);
    }

    public LocationRequestUpdateData[] zzwv(int $i0) throws  {
        return new LocationRequestUpdateData[$i0];
    }
}
