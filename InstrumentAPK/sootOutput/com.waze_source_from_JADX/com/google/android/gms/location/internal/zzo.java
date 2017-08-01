package com.google.android.gms.location.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

/* compiled from: dalvik_source_com.waze.apk */
public class zzo implements Creator<ParcelableGeofence> {
    static void zza(ParcelableGeofence $r0, Parcel $r1, int i) throws  {
        i = zzb.zzea($r1);
        zzb.zza($r1, 1, $r0.getRequestId(), false);
        zzb.zza($r1, 2, $r0.getExpirationTime());
        zzb.zza($r1, 3, $r0.zzbso());
        zzb.zza($r1, 4, $r0.getLatitude());
        zzb.zza($r1, 5, $r0.getLongitude());
        zzb.zza($r1, 6, $r0.zzbsp());
        zzb.zzc($r1, 7, $r0.getTransitionTypes());
        zzb.zzc($r1, 1000, $r0.getVersionCode());
        zzb.zzc($r1, 8, $r0.zzbsq());
        zzb.zzc($r1, 9, $r0.zzbsr());
        zzb.zzaj($r1, i);
    }

    public /* synthetic */ Object createFromParcel(Parcel $r1) throws  {
        return zzpk($r1);
    }

    public /* synthetic */ Object[] newArray(int $i0) throws  {
        return zzwy($i0);
    }

    public ParcelableGeofence zzpk(Parcel $r1) throws  {
        int $i0 = zza.zzdz($r1);
        int $i1 = 0;
        String $r2 = null;
        int $i2 = 0;
        short $s3 = (short) 0;
        double $d0 = 0.0d;
        double $d1 = 0.0d;
        float $f0 = 0.0f;
        long $l4 = 0;
        int $i5 = 0;
        int $i6 = -1;
        while ($r1.dataPosition() < $i0) {
            int $i7 = zza.zzdy($r1);
            switch (zza.zziv($i7)) {
                case 1:
                    $r2 = zza.zzq($r1, $i7);
                    break;
                case 2:
                    $l4 = zza.zzi($r1, $i7);
                    break;
                case 3:
                    $s3 = zza.zzf($r1, $i7);
                    break;
                case 4:
                    $d0 = zza.zzn($r1, $i7);
                    break;
                case 5:
                    $d1 = zza.zzn($r1, $i7);
                    break;
                case 6:
                    $f0 = zza.zzl($r1, $i7);
                    break;
                case 7:
                    $i2 = zza.zzg($r1, $i7);
                    break;
                case 8:
                    $i5 = zza.zzg($r1, $i7);
                    break;
                case 9:
                    $i6 = zza.zzg($r1, $i7);
                    break;
                case 1000:
                    $i1 = zza.zzg($r1, $i7);
                    break;
                default:
                    zza.zzb($r1, $i7);
                    break;
            }
        }
        if ($r1.dataPosition() == $i0) {
            return new ParcelableGeofence($i1, $r2, $i2, $s3, $d0, $d1, $f0, $l4, $i5, $i6);
        }
        throw new zza.zza("Overread allowed size end=" + $i0, $r1);
    }

    public ParcelableGeofence[] zzwy(int $i0) throws  {
        return new ParcelableGeofence[$i0];
    }
}
