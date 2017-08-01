package com.google.android.gms.common.data;

import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;

/* compiled from: dalvik_source_com.waze.apk */
public class zza implements Creator<BitmapTeleporter> {
    static void zza(BitmapTeleporter $r0, Parcel $r1, int $i0) throws  {
        int $i1 = zzb.zzea($r1);
        zzb.zzc($r1, 1, $r0.mVersionCode);
        zzb.zza($r1, 2, $r0.zzcct, $i0, false);
        zzb.zzc($r1, 3, $r0.bG);
        zzb.zzaj($r1, $i1);
    }

    public /* synthetic */ Object createFromParcel(Parcel $r1) throws  {
        return zzdn($r1);
    }

    public /* synthetic */ Object[] newArray(int $i0) throws  {
        return zzhz($i0);
    }

    public BitmapTeleporter zzdn(Parcel $r1) throws  {
        int $i0 = 0;
        int $i1 = com.google.android.gms.common.internal.safeparcel.zza.zzdz($r1);
        ParcelFileDescriptor $r2 = null;
        int $i2 = 0;
        while ($r1.dataPosition() < $i1) {
            int $i3 = com.google.android.gms.common.internal.safeparcel.zza.zzdy($r1);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zziv($i3)) {
                case 1:
                    $i2 = com.google.android.gms.common.internal.safeparcel.zza.zzg($r1, $i3);
                    break;
                case 2:
                    $r2 = (ParcelFileDescriptor) com.google.android.gms.common.internal.safeparcel.zza.zza($r1, $i3, ParcelFileDescriptor.CREATOR);
                    break;
                case 3:
                    $i0 = com.google.android.gms.common.internal.safeparcel.zza.zzg($r1, $i3);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb($r1, $i3);
                    break;
            }
        }
        if ($r1.dataPosition() == $i1) {
            return new BitmapTeleporter($i2, $r2, $i0);
        }
        throw new com.google.android.gms.common.internal.safeparcel.zza.zza("Overread allowed size end=" + $i1, $r1);
    }

    public BitmapTeleporter[] zzhz(int $i0) throws  {
        return new BitmapTeleporter[$i0];
    }
}
