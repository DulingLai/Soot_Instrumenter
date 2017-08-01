package com.google.android.gms.people.internal.autocomplete;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

/* compiled from: dalvik_source_com.waze.apk */
public class zzl implements Creator<ParcelableLoadAutocompleteResultsOptions> {
    static void zza(ParcelableLoadAutocompleteResultsOptions $r0, Parcel $r1, int i) throws  {
        i = zzb.zzea($r1);
        zzb.zzc($r1, 1, $r0.mVersionCode);
        zzb.zzc($r1, 2, $r0.aUv);
        zzb.zza($r1, 3, $r0.aTW);
        zzb.zza($r1, 4, $r0.zzaoj, false);
        zzb.zzaj($r1, i);
    }

    public /* synthetic */ Object createFromParcel(Parcel $r1) throws  {
        return zzuc($r1);
    }

    public /* synthetic */ Object[] newArray(int $i0) throws  {
        return zzacy($i0);
    }

    public ParcelableLoadAutocompleteResultsOptions[] zzacy(int $i0) throws  {
        return new ParcelableLoadAutocompleteResultsOptions[$i0];
    }

    public ParcelableLoadAutocompleteResultsOptions zzuc(Parcel $r1) throws  {
        int $i0 = 0;
        int $i1 = zza.zzdz($r1);
        long $l2 = 0;
        String $r2 = null;
        int $i3 = 0;
        while ($r1.dataPosition() < $i1) {
            int $i4 = zza.zzdy($r1);
            switch (zza.zziv($i4)) {
                case 1:
                    $i3 = zza.zzg($r1, $i4);
                    break;
                case 2:
                    $i0 = zza.zzg($r1, $i4);
                    break;
                case 3:
                    $l2 = zza.zzi($r1, $i4);
                    break;
                case 4:
                    $r2 = zza.zzq($r1, $i4);
                    break;
                default:
                    zza.zzb($r1, $i4);
                    break;
            }
        }
        if ($r1.dataPosition() == $i1) {
            return new ParcelableLoadAutocompleteResultsOptions($i3, $i0, $l2, $r2);
        }
        throw new zza.zza("Overread allowed size end=" + $i1, $r1);
    }
}
