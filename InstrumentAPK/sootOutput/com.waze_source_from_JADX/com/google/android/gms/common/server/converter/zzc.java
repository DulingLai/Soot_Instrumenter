package com.google.android.gms.common.server.converter;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.server.converter.StringToIntConverter.Entry;

/* compiled from: dalvik_source_com.waze.apk */
public class zzc implements Creator<Entry> {
    static void zza(Entry $r0, Parcel $r1, int i) throws  {
        i = zzb.zzea($r1);
        zzb.zzc($r1, 1, $r0.versionCode);
        zzb.zza($r1, 2, $r0.stringValue, false);
        zzb.zzc($r1, 3, $r0.KW);
        zzb.zzaj($r1, i);
    }

    public /* synthetic */ Object createFromParcel(Parcel $r1) throws  {
        return zzeg($r1);
    }

    public /* synthetic */ Object[] newArray(int $i0) throws  {
        return zzjd($i0);
    }

    public Entry zzeg(Parcel $r1) throws  {
        int $i0 = 0;
        int $i1 = zza.zzdz($r1);
        String $r2 = null;
        int $i2 = 0;
        while ($r1.dataPosition() < $i1) {
            int $i3 = zza.zzdy($r1);
            switch (zza.zziv($i3)) {
                case 1:
                    $i2 = zza.zzg($r1, $i3);
                    break;
                case 2:
                    $r2 = zza.zzq($r1, $i3);
                    break;
                case 3:
                    $i0 = zza.zzg($r1, $i3);
                    break;
                default:
                    zza.zzb($r1, $i3);
                    break;
            }
        }
        if ($r1.dataPosition() == $i1) {
            return new Entry($i2, $r2, $i0);
        }
        throw new zza.zza("Overread allowed size end=" + $i1, $r1);
    }

    public Entry[] zzjd(int $i0) throws  {
        return new Entry[$i0];
    }
}
