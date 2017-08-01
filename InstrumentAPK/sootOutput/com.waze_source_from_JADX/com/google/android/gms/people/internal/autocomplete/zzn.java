package com.google.android.gms.people.internal.autocomplete;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

/* compiled from: dalvik_source_com.waze.apk */
public class zzn implements Creator<PersonImpl> {
    static void zza(PersonImpl $r0, Parcel $r1, int $i0) throws  {
        int $i1 = zzb.zzea($r1);
        zzb.zzc($r1, 1, $r0.mVersionCode);
        zzb.zza($r1, 2, $r0.aUx, $i0, false);
        zzb.zza($r1, 3, $r0.aUy, $i0, false);
        zzb.zza($r1, 4, $r0.aUz, $i0, false);
        zzb.zza($r1, 5, $r0.aUA, $i0, false);
        zzb.zza($r1, 6, $r0.aUB, $i0, false);
        zzb.zzaj($r1, $i1);
    }

    public /* synthetic */ Object createFromParcel(Parcel $r1) throws  {
        return zzue($r1);
    }

    public /* synthetic */ Object[] newArray(int $i0) throws  {
        return zzada($i0);
    }

    public PersonImpl[] zzada(int $i0) throws  {
        return new PersonImpl[$i0];
    }

    public PersonImpl zzue(Parcel $r1) throws  {
        PhotoImpl[] $r2 = null;
        int $i0 = zza.zzdz($r1);
        int $i1 = 0;
        PhoneImpl[] $r3 = null;
        EmailImpl[] $r4 = null;
        NameImpl[] $r5 = null;
        PersonMetadataImpl $r6 = null;
        while ($r1.dataPosition() < $i0) {
            int $i2 = zza.zzdy($r1);
            switch (zza.zziv($i2)) {
                case 1:
                    $i1 = zza.zzg($r1, $i2);
                    break;
                case 2:
                    $r6 = (PersonMetadataImpl) zza.zza($r1, $i2, PersonMetadataImpl.CREATOR);
                    break;
                case 3:
                    $r5 = (NameImpl[]) zza.zzb($r1, $i2, NameImpl.CREATOR);
                    break;
                case 4:
                    $r4 = (EmailImpl[]) zza.zzb($r1, $i2, EmailImpl.CREATOR);
                    break;
                case 5:
                    $r3 = (PhoneImpl[]) zza.zzb($r1, $i2, PhoneImpl.CREATOR);
                    break;
                case 6:
                    $r2 = (PhotoImpl[]) zza.zzb($r1, $i2, PhotoImpl.CREATOR);
                    break;
                default:
                    zza.zzb($r1, $i2);
                    break;
            }
        }
        if ($r1.dataPosition() == $i0) {
            return new PersonImpl($i1, $r6, $r5, $r4, $r3, $r2);
        }
        throw new zza.zza("Overread allowed size end=" + $i0, $r1);
    }
}
