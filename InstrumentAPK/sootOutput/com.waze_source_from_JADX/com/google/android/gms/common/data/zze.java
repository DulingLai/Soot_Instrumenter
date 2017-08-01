package com.google.android.gms.common.data;

import android.database.CursorWindow;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

/* compiled from: dalvik_source_com.waze.apk */
public class zze implements Creator<DataHolder> {
    static void zza(DataHolder $r0, Parcel $r1, int $i0) throws  {
        int $i1 = zzb.zzea($r1);
        zzb.zza($r1, 1, $r0.zzave(), false);
        zzb.zza($r1, 2, $r0.zzavf(), $i0, false);
        zzb.zzc($r1, 3, $r0.getStatusCode());
        zzb.zza($r1, 4, $r0.zzava(), false);
        zzb.zzc($r1, 1000, $r0.getVersionCode());
        zzb.zzaj($r1, $i1);
    }

    public /* synthetic */ Object createFromParcel(Parcel $r1) throws  {
        return zzdo($r1);
    }

    public /* synthetic */ Object[] newArray(int $i0) throws  {
        return zzid($i0);
    }

    public DataHolder zzdo(Parcel $r1) throws  {
        int $i0 = 0;
        Bundle $r2 = null;
        int $i1 = zza.zzdz($r1);
        CursorWindow[] $r3 = null;
        String[] $r4 = null;
        int $i2 = 0;
        while ($r1.dataPosition() < $i1) {
            int $i3 = zza.zzdy($r1);
            switch (zza.zziv($i3)) {
                case 1:
                    $r4 = zza.zzac($r1, $i3);
                    break;
                case 2:
                    $r3 = (CursorWindow[]) zza.zzb($r1, $i3, CursorWindow.CREATOR);
                    break;
                case 3:
                    $i0 = zza.zzg($r1, $i3);
                    break;
                case 4:
                    $r2 = zza.zzs($r1, $i3);
                    break;
                case 1000:
                    $i2 = zza.zzg($r1, $i3);
                    break;
                default:
                    zza.zzb($r1, $i3);
                    break;
            }
        }
        if ($r1.dataPosition() != $i1) {
            throw new zza.zza("Overread allowed size end=" + $i1, $r1);
        }
        DataHolder dataHolder = new DataHolder($i2, $r4, $r3, $i0, $r2);
        dataHolder.validateContents();
        return dataHolder;
    }

    public DataHolder[] zzid(int $i0) throws  {
        return new DataHolder[$i0];
    }
}
