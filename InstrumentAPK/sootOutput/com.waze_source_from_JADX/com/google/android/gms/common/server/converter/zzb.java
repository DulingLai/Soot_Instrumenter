package com.google.android.gms.common.server.converter;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.server.converter.StringToIntConverter.Entry;
import java.util.ArrayList;

/* compiled from: dalvik_source_com.waze.apk */
public class zzb implements Creator<StringToIntConverter> {
    static void zza(StringToIntConverter $r0, Parcel $r1, int i) throws  {
        i = com.google.android.gms.common.internal.safeparcel.zzb.zzea($r1);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc($r1, 1, $r0.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.zzb.zzc($r1, 2, $r0.zzaxs(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzaj($r1, i);
    }

    public /* synthetic */ Object createFromParcel(Parcel $r1) throws  {
        return zzef($r1);
    }

    public /* synthetic */ Object[] newArray(int $i0) throws  {
        return zzjc($i0);
    }

    public StringToIntConverter zzef(Parcel $r1) throws  {
        int $i0 = zza.zzdz($r1);
        int $i1 = 0;
        ArrayList $r2 = null;
        while ($r1.dataPosition() < $i0) {
            int $i2 = zza.zzdy($r1);
            switch (zza.zziv($i2)) {
                case 1:
                    $i1 = zza.zzg($r1, $i2);
                    break;
                case 2:
                    $r2 = zza.zzc($r1, $i2, Entry.CREATOR);
                    break;
                default:
                    zza.zzb($r1, $i2);
                    break;
            }
        }
        if ($r1.dataPosition() == $i0) {
            return new StringToIntConverter($i1, $r2);
        }
        throw new zza.zza("Overread allowed size end=" + $i0, $r1);
    }

    public StringToIntConverter[] zzjc(int $i0) throws  {
        return new StringToIntConverter[$i0];
    }
}
