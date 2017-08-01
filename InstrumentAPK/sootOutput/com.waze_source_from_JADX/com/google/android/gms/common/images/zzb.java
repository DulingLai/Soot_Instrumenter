package com.google.android.gms.common.images;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;

/* compiled from: dalvik_source_com.waze.apk */
public class zzb implements Creator<WebImage> {
    static void zza(WebImage $r0, Parcel $r1, int $i0) throws  {
        int $i1 = com.google.android.gms.common.internal.safeparcel.zzb.zzea($r1);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc($r1, 1, $r0.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.zzb.zza($r1, 2, $r0.getUrl(), $i0, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc($r1, 3, $r0.getWidth());
        com.google.android.gms.common.internal.safeparcel.zzb.zzc($r1, 4, $r0.getHeight());
        com.google.android.gms.common.internal.safeparcel.zzb.zzaj($r1, $i1);
    }

    public /* synthetic */ Object createFromParcel(Parcel $r1) throws  {
        return zzdq($r1);
    }

    public /* synthetic */ Object[] newArray(int $i0) throws  {
        return zzii($i0);
    }

    public WebImage zzdq(Parcel $r1) throws  {
        int $i0 = 0;
        int $i1 = zza.zzdz($r1);
        Uri $r2 = null;
        int $i2 = 0;
        int $i3 = 0;
        while ($r1.dataPosition() < $i1) {
            int $i4 = zza.zzdy($r1);
            switch (zza.zziv($i4)) {
                case 1:
                    $i3 = zza.zzg($r1, $i4);
                    break;
                case 2:
                    $r2 = (Uri) zza.zza($r1, $i4, Uri.CREATOR);
                    break;
                case 3:
                    $i2 = zza.zzg($r1, $i4);
                    break;
                case 4:
                    $i0 = zza.zzg($r1, $i4);
                    break;
                default:
                    zza.zzb($r1, $i4);
                    break;
            }
        }
        if ($r1.dataPosition() == $i1) {
            return new WebImage($i3, $r2, $i2, $i0);
        }
        throw new zza.zza("Overread allowed size end=" + $i1, $r1);
    }

    public WebImage[] zzii(int $i0) throws  {
        return new WebImage[$i0];
    }
}
