package com.google.android.gms.people.internal.autocomplete;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

/* compiled from: dalvik_source_com.waze.apk */
public class zzd implements Creator<ContactGroupImpl> {
    static void zza(ContactGroupImpl $r0, Parcel $r1, int $i0) throws  {
        int $i1 = zzb.zzea($r1);
        zzb.zzc($r1, 1, $r0.mVersionCode);
        zzb.zza($r1, 2, $r0.aUp, $i0, false);
        zzb.zza($r1, 3, $r0.aUq, $i0, false);
        zzb.zza($r1, 4, $r0.aUr, $i0, false);
        zzb.zzc($r1, 5, $r0.aUs);
        zzb.zzaj($r1, $i1);
    }

    public /* synthetic */ Object createFromParcel(Parcel $r1) throws  {
        return zztv($r1);
    }

    public /* synthetic */ Object[] newArray(int $i0) throws  {
        return zzacr($i0);
    }

    public ContactGroupImpl[] zzacr(int $i0) throws  {
        return new ContactGroupImpl[$i0];
    }

    public ContactGroupImpl zztv(Parcel $r1) throws  {
        int $i0 = 0;
        GroupExtendedDataImpl $r2 = null;
        int $i1 = zza.zzdz($r1);
        ContactGroupNameImpl $r3 = null;
        ContactGroupIdImpl $r4 = null;
        int $i2 = 0;
        while ($r1.dataPosition() < $i1) {
            int $i3 = zza.zzdy($r1);
            switch (zza.zziv($i3)) {
                case 1:
                    $i2 = zza.zzg($r1, $i3);
                    break;
                case 2:
                    $r4 = (ContactGroupIdImpl) zza.zza($r1, $i3, ContactGroupIdImpl.CREATOR);
                    break;
                case 3:
                    $r3 = (ContactGroupNameImpl) zza.zza($r1, $i3, ContactGroupNameImpl.CREATOR);
                    break;
                case 4:
                    $r2 = (GroupExtendedDataImpl) zza.zza($r1, $i3, GroupExtendedDataImpl.CREATOR);
                    break;
                case 5:
                    $i0 = zza.zzg($r1, $i3);
                    break;
                default:
                    zza.zzb($r1, $i3);
                    break;
            }
        }
        if ($r1.dataPosition() == $i1) {
            return new ContactGroupImpl($i2, $r4, $r3, $r2, $i0);
        }
        throw new zza.zza("Overread allowed size end=" + $i1, $r1);
    }
}
