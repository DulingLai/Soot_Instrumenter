package com.google.android.gms.plus.service.v2whitelisted.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.plus.service.v2whitelisted.models.Person.Emails.Certificates;
import com.google.android.gms.plus.service.v2whitelisted.models.Person.Emails.Certificates.Status;
import java.util.HashSet;
import java.util.Set;

/* compiled from: dalvik_source_com.waze.apk */
public class zzt implements Creator<Certificates> {
    static void zza(Certificates $r0, Parcel $r1, int $i0) throws  {
        int $i1 = zzb.zzea($r1);
        Set $r2 = $r0.aOu;
        if ($r2.contains(Integer.valueOf(1))) {
            zzb.zzc($r1, 1, $r0.mVersionCode);
        }
        if ($r2.contains(Integer.valueOf(3))) {
            zzb.zza($r1, 3, $r0.aZT, $i0, true);
        }
        if ($r2.contains(Integer.valueOf(4))) {
            zzb.zza($r1, 4, $r0.aZW, $i0, true);
        }
        zzb.zzaj($r1, $i1);
    }

    public /* synthetic */ Object createFromParcel(Parcel $r1) throws  {
        return zzwh($r1);
    }

    public /* synthetic */ Object[] newArray(int $i0) throws  {
        return zzafk($i0);
    }

    public Certificates[] zzafk(int $i0) throws  {
        return new Certificates[$i0];
    }

    public Certificates zzwh(Parcel $r1) throws  {
        Status $r3 = null;
        int $i0 = zza.zzdz($r1);
        HashSet $r2 = new HashSet();
        int $i1 = 0;
        Mergedpeoplemetadata $r4 = null;
        while ($r1.dataPosition() < $i0) {
            int $i2 = zza.zzdy($r1);
            switch (zza.zziv($i2)) {
                case 1:
                    $i1 = zza.zzg($r1, $i2);
                    $r2.add(Integer.valueOf(1));
                    continue;
                case 2:
                    break;
                case 3:
                    Parcelable $r7 = zza.zza($r1, $i2, Mergedpeoplemetadata.CREATOR);
                    $r2.add(Integer.valueOf(3));
                    $r4 = (Mergedpeoplemetadata) $r7;
                    continue;
                case 4:
                    $r3 = (Status) zza.zza($r1, $i2, Status.CREATOR);
                    $r2.add(Integer.valueOf(4));
                    continue;
                default:
                    break;
            }
            zza.zzb($r1, $i2);
        }
        if ($r1.dataPosition() == $i0) {
            return new Certificates($r2, $i1, $r4, $r3);
        }
        throw new zza.zza("Overread allowed size end=" + $i0, $r1);
    }
}
