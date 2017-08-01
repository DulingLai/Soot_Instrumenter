package com.google.android.gms.people.identity.internal.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.people.identity.internal.models.PersonImpl.ImagesImpl;
import com.google.android.gms.people.identity.internal.models.PersonImpl.MetadataImpl;
import java.util.HashSet;
import java.util.Set;

/* compiled from: dalvik_source_com.waze.apk */
public class zzm implements Creator<ImagesImpl> {
    static void zza(ImagesImpl $r0, Parcel $r1, int $i0) throws  {
        int $i1 = zzb.zzea($r1);
        Set $r2 = $r0.aOu;
        if ($r2.contains(Integer.valueOf(1))) {
            zzb.zzc($r1, 1, $r0.mVersionCode);
        }
        if ($r2.contains(Integer.valueOf(2))) {
            zzb.zza($r1, 2, $r0.aOZ, $i0, true);
        }
        if ($r2.contains(Integer.valueOf(3))) {
            zzb.zza($r1, 3, $r0.aPj, $i0, true);
        }
        if ($r2.contains(Integer.valueOf(4))) {
            zzb.zza($r1, 4, $r0.aPk);
        }
        zzb.zzaj($r1, $i1);
    }

    public /* synthetic */ Object createFromParcel(Parcel $r1) throws  {
        return zzsv($r1);
    }

    public /* synthetic */ Object[] newArray(int $i0) throws  {
        return zzabi($i0);
    }

    public ImagesImpl[] zzabi(int $i0) throws  {
        return new ImagesImpl[$i0];
    }

    public ImagesImpl zzsv(Parcel $r1) throws  {
        ImageReferenceImpl $r2 = null;
        boolean $z0 = false;
        int $i0 = zza.zzdz($r1);
        HashSet $r3 = new HashSet();
        MetadataImpl $r4 = null;
        int $i1 = 0;
        while ($r1.dataPosition() < $i0) {
            int $i2 = zza.zzdy($r1);
            Parcelable $r7;
            switch (zza.zziv($i2)) {
                case 1:
                    $i1 = zza.zzg($r1, $i2);
                    $r3.add(Integer.valueOf(1));
                    break;
                case 2:
                    $r7 = zza.zza($r1, $i2, MetadataImpl.CREATOR);
                    $r3.add(Integer.valueOf(2));
                    $r4 = (MetadataImpl) $r7;
                    break;
                case 3:
                    $r7 = zza.zza($r1, $i2, ImageReferenceImpl.CREATOR);
                    $r3.add(Integer.valueOf(3));
                    $r2 = (ImageReferenceImpl) $r7;
                    break;
                case 4:
                    $z0 = zza.zzc($r1, $i2);
                    $r3.add(Integer.valueOf(4));
                    break;
                default:
                    zza.zzb($r1, $i2);
                    break;
            }
        }
        if ($r1.dataPosition() == $i0) {
            return new ImagesImpl($r3, $i1, $r4, $r2, $z0);
        }
        throw new zza.zza("Overread allowed size end=" + $i0, $r1);
    }
}
