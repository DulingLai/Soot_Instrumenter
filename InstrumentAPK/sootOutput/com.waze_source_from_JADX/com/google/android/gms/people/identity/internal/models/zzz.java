package com.google.android.gms.people.identity.internal.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import java.util.HashSet;
import java.util.Set;

/* compiled from: dalvik_source_com.waze.apk */
public class zzz implements Creator<PersonReferenceImpl> {
    static void zza(PersonReferenceImpl $r0, Parcel $r1, int $i0) throws  {
        int $i1 = zzb.zzea($r1);
        Set $r2 = $r0.aOu;
        if ($r2.contains(Integer.valueOf(1))) {
            zzb.zzc($r1, 1, $r0.mVersionCode);
        }
        if ($r2.contains(Integer.valueOf(2))) {
            zzb.zza($r1, 2, $r0.mName, true);
        }
        if ($r2.contains(Integer.valueOf(3))) {
            zzb.zza($r1, 3, $r0.Gu, true);
        }
        if ($r2.contains(Integer.valueOf(4))) {
            zzb.zza($r1, 4, $r0.aQd, $i0, true);
        }
        zzb.zzaj($r1, $i1);
    }

    public /* synthetic */ Object createFromParcel(Parcel $r1) throws  {
        return zzth($r1);
    }

    public /* synthetic */ Object[] newArray(int $i0) throws  {
        return zzabz($i0);
    }

    public PersonReferenceImpl[] zzabz(int $i0) throws  {
        return new PersonReferenceImpl[$i0];
    }

    public PersonReferenceImpl zzth(Parcel $r1) throws  {
        ImageReferenceImpl $r2 = null;
        int $i0 = zza.zzdz($r1);
        HashSet $r3 = new HashSet();
        int $i1 = 0;
        String $r4 = null;
        String $r5 = null;
        while ($r1.dataPosition() < $i0) {
            int $i2 = zza.zzdy($r1);
            switch (zza.zziv($i2)) {
                case 1:
                    $i1 = zza.zzg($r1, $i2);
                    $r3.add(Integer.valueOf(1));
                    break;
                case 2:
                    $r5 = zza.zzq($r1, $i2);
                    $r3.add(Integer.valueOf(2));
                    break;
                case 3:
                    $r4 = zza.zzq($r1, $i2);
                    $r3.add(Integer.valueOf(3));
                    break;
                case 4:
                    Parcelable $r8 = zza.zza($r1, $i2, ImageReferenceImpl.CREATOR);
                    $r3.add(Integer.valueOf(4));
                    $r2 = (ImageReferenceImpl) $r8;
                    break;
                default:
                    zza.zzb($r1, $i2);
                    break;
            }
        }
        if ($r1.dataPosition() == $i0) {
            return new PersonReferenceImpl($r3, $i1, $r5, $r4, $r2);
        }
        throw new zza.zza("Overread allowed size end=" + $i0, $r1);
    }
}
