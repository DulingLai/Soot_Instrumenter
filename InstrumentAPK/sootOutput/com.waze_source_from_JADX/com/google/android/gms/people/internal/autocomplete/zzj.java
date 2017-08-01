package com.google.android.gms.people.internal.autocomplete;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

/* compiled from: dalvik_source_com.waze.apk */
public class zzj implements Creator<GroupExtendedDataImpl> {
    static void zza(GroupExtendedDataImpl $r0, Parcel $r1, int $i0) throws  {
        int $i1 = zzb.zzea($r1);
        zzb.zzc($r1, 1, $r0.mVersionCode);
        zzb.zza($r1, 2, $r0.aUu, $i0, false);
        zzb.zzaj($r1, $i1);
    }

    public /* synthetic */ Object createFromParcel(Parcel $r1) throws  {
        return zzua($r1);
    }

    public /* synthetic */ Object[] newArray(int $i0) throws  {
        return zzacw($i0);
    }

    public GroupExtendedDataImpl[] zzacw(int $i0) throws  {
        return new GroupExtendedDataImpl[$i0];
    }

    public GroupExtendedDataImpl zzua(Parcel $r1) throws  {
        int $i0 = zza.zzdz($r1);
        int $i1 = 0;
        ContactPreferredFieldsEntity[] $r2 = null;
        while ($r1.dataPosition() < $i0) {
            int $i2 = zza.zzdy($r1);
            switch (zza.zziv($i2)) {
                case 1:
                    $i1 = zza.zzg($r1, $i2);
                    break;
                case 2:
                    $r2 = (ContactPreferredFieldsEntity[]) zza.zzb($r1, $i2, ContactPreferredFieldsEntity.CREATOR);
                    break;
                default:
                    zza.zzb($r1, $i2);
                    break;
            }
        }
        if ($r1.dataPosition() == $i0) {
            return new GroupExtendedDataImpl($i1, $r2);
        }
        throw new zza.zza("Overread allowed size end=" + $i0, $r1);
    }
}
