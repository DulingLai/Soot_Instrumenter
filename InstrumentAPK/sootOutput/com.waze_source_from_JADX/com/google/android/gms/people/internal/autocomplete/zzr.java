package com.google.android.gms.people.internal.autocomplete;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

/* compiled from: dalvik_source_com.waze.apk */
public class zzr implements Creator<SubstringImpl> {
    static void zza(SubstringImpl $r0, Parcel $r1, int i) throws  {
        i = zzb.zzea($r1);
        zzb.zzc($r1, 1, $r0.mVersionCode);
        zzb.zzc($r1, 2, $r0.aUD);
        zzb.zzc($r1, 3, $r0.mLength);
        zzb.zzaj($r1, i);
    }

    public /* synthetic */ Object createFromParcel(Parcel $r1) throws  {
        return zzui($r1);
    }

    public /* synthetic */ Object[] newArray(int $i0) throws  {
        return zzade($i0);
    }

    public SubstringImpl[] zzade(int $i0) throws  {
        return new SubstringImpl[$i0];
    }

    public SubstringImpl zzui(Parcel $r1) throws  {
        int $i0 = 0;
        int $i1 = zza.zzdz($r1);
        int $i2 = 0;
        int $i3 = 0;
        while ($r1.dataPosition() < $i1) {
            int $i4 = zza.zzdy($r1);
            switch (zza.zziv($i4)) {
                case 1:
                    $i3 = zza.zzg($r1, $i4);
                    break;
                case 2:
                    $i2 = zza.zzg($r1, $i4);
                    break;
                case 3:
                    $i0 = zza.zzg($r1, $i4);
                    break;
                default:
                    zza.zzb($r1, $i4);
                    break;
            }
        }
        if ($r1.dataPosition() == $i1) {
            return new SubstringImpl($i3, $i2, $i0);
        }
        throw new zza.zza("Overread allowed size end=" + $i1, $r1);
    }
}
