package com.google.android.gms.people.internal.autocomplete;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

/* compiled from: dalvik_source_com.waze.apk */
public class zzh implements Creator<DisplayableFieldImpl> {
    static void zza(DisplayableFieldImpl $r0, Parcel $r1, int $i0) throws  {
        int $i1 = zzb.zzea($r1);
        zzb.zzc($r1, 1, $r0.mVersionCode);
        zzb.zzc($r1, 2, $r0.bG);
        zzb.zzc($r1, 3, $r0.mIndex);
        zzb.zza($r1, 4, $r0.mValue, false);
        zzb.zza($r1, 5, $r0.aUt, $i0, false);
        zzb.zzaj($r1, $i1);
    }

    public /* synthetic */ Object createFromParcel(Parcel $r1) throws  {
        return zzty($r1);
    }

    public /* synthetic */ Object[] newArray(int $i0) throws  {
        return zzacu($i0);
    }

    public DisplayableFieldImpl[] zzacu(int $i0) throws  {
        return new DisplayableFieldImpl[$i0];
    }

    public DisplayableFieldImpl zzty(Parcel $r1) throws  {
        SubstringImpl[] $r2 = null;
        int $i0 = 0;
        int $i1 = zza.zzdz($r1);
        String $r3 = null;
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
                case 4:
                    $r3 = zza.zzq($r1, $i4);
                    break;
                case 5:
                    $r2 = (SubstringImpl[]) zza.zzb($r1, $i4, SubstringImpl.CREATOR);
                    break;
                default:
                    zza.zzb($r1, $i4);
                    break;
            }
        }
        if ($r1.dataPosition() == $i1) {
            return new DisplayableFieldImpl($i3, $i2, $i0, $r3, $r2);
        }
        throw new zza.zza("Overread allowed size end=" + $i1, $r1);
    }
}
