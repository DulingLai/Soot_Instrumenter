package com.google.android.gms.people.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

/* compiled from: dalvik_source_com.waze.apk */
public class zzk implements Creator<ParcelableLoadImageOptions> {
    static void zza(ParcelableLoadImageOptions $r0, Parcel $r1, int i) throws  {
        i = zzb.zzea($r1);
        zzb.zzc($r1, 1, $r0.getImageSize());
        zzb.zzc($r1, 2, $r0.zzcet());
        zzb.zza($r1, 3, $r0.zzceu());
        zzb.zzc($r1, 1000, $r0.getVersionCode());
        zzb.zzaj($r1, i);
    }

    public /* synthetic */ Object createFromParcel(Parcel $r1) throws  {
        return zzts($r1);
    }

    public /* synthetic */ Object[] newArray(int $i0) throws  {
        return zzacm($i0);
    }

    public ParcelableLoadImageOptions[] zzacm(int $i0) throws  {
        return new ParcelableLoadImageOptions[$i0];
    }

    public ParcelableLoadImageOptions zzts(Parcel $r1) throws  {
        boolean $z0 = false;
        int $i0 = zza.zzdz($r1);
        int $i1 = 0;
        int $i2 = 0;
        int $i3 = 0;
        while ($r1.dataPosition() < $i0) {
            int $i4 = zza.zzdy($r1);
            switch (zza.zziv($i4)) {
                case 1:
                    $i2 = zza.zzg($r1, $i4);
                    break;
                case 2:
                    $i1 = zza.zzg($r1, $i4);
                    break;
                case 3:
                    $z0 = zza.zzc($r1, $i4);
                    break;
                case 1000:
                    $i3 = zza.zzg($r1, $i4);
                    break;
                default:
                    zza.zzb($r1, $i4);
                    break;
            }
        }
        if ($r1.dataPosition() == $i0) {
            return new ParcelableLoadImageOptions($i3, $i2, $i1, $z0);
        }
        throw new zza.zza("Overread allowed size end=" + $i0, $r1);
    }
}
