package com.google.android.gms.people.internal.autocomplete;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;

/* compiled from: dalvik_source_com.waze.apk */
public class zzb implements Creator<AutocompletionImpl> {
    static void zza(AutocompletionImpl $r0, Parcel $r1, int $i0) throws  {
        int $i1 = com.google.android.gms.common.internal.safeparcel.zzb.zzea($r1);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc($r1, 1, $r0.mVersionCode);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc($r1, 2, $r0.aUk);
        com.google.android.gms.common.internal.safeparcel.zzb.zza($r1, 3, $r0.aUl, $i0, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza($r1, 4, $r0.aUm, $i0, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza($r1, 5, $r0.aUn, $i0, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzaj($r1, $i1);
    }

    public /* synthetic */ Object createFromParcel(Parcel $r1) throws  {
        return zztt($r1);
    }

    public /* synthetic */ Object[] newArray(int $i0) throws  {
        return zzacp($i0);
    }

    public AutocompletionImpl[] zzacp(int $i0) throws  {
        return new AutocompletionImpl[$i0];
    }

    public AutocompletionImpl zztt(Parcel $r1) throws  {
        int $i0 = 0;
        DisplayableFieldImpl[] $r2 = null;
        int $i1 = zza.zzdz($r1);
        ContactGroupImpl $r3 = null;
        PersonImpl $r4 = null;
        int $i2 = 0;
        while ($r1.dataPosition() < $i1) {
            int $i3 = zza.zzdy($r1);
            switch (zza.zziv($i3)) {
                case 1:
                    $i2 = zza.zzg($r1, $i3);
                    break;
                case 2:
                    $i0 = zza.zzg($r1, $i3);
                    break;
                case 3:
                    $r4 = (PersonImpl) zza.zza($r1, $i3, PersonImpl.CREATOR);
                    break;
                case 4:
                    $r3 = (ContactGroupImpl) zza.zza($r1, $i3, ContactGroupImpl.CREATOR);
                    break;
                case 5:
                    $r2 = (DisplayableFieldImpl[]) zza.zzb($r1, $i3, DisplayableFieldImpl.CREATOR);
                    break;
                default:
                    zza.zzb($r1, $i3);
                    break;
            }
        }
        if ($r1.dataPosition() == $i1) {
            return new AutocompletionImpl($i2, $i0, $r4, $r3, $r2);
        }
        throw new zza.zza("Overread allowed size end=" + $i1, $r1);
    }
}
