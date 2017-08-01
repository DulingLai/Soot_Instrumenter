package com.google.android.gms.people.identity.internal.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.people.identity.internal.models.PersonImpl.AboutsImpl;
import com.google.android.gms.people.identity.internal.models.PersonImpl.MetadataImpl;
import java.util.HashSet;
import java.util.Set;

/* compiled from: dalvik_source_com.waze.apk */
public class zza implements Creator<AboutsImpl> {
    static void zza(AboutsImpl $r0, Parcel $r1, int $i0) throws  {
        int $i1 = zzb.zzea($r1);
        Set $r2 = $r0.aOu;
        if ($r2.contains(Integer.valueOf(1))) {
            zzb.zzc($r1, 1, $r0.mVersionCode);
        }
        if ($r2.contains(Integer.valueOf(2))) {
            zzb.zza($r1, 2, $r0.aOZ, $i0, true);
        }
        if ($r2.contains(Integer.valueOf(3))) {
            zzb.zza($r1, 3, $r0.zzcft, true);
        }
        if ($r2.contains(Integer.valueOf(4))) {
            zzb.zza($r1, 4, $r0.mValue, true);
        }
        zzb.zzaj($r1, $i1);
    }

    public /* synthetic */ Object createFromParcel(Parcel $r1) throws  {
        return zzsl($r1);
    }

    public /* synthetic */ Object[] newArray(int $i0) throws  {
        return zzaax($i0);
    }

    public AboutsImpl[] zzaax(int $i0) throws  {
        return new AboutsImpl[$i0];
    }

    public AboutsImpl zzsl(Parcel $r1) throws  {
        String $r2 = null;
        int $i0 = com.google.android.gms.common.internal.safeparcel.zza.zzdz($r1);
        HashSet $r3 = new HashSet();
        int $i1 = 0;
        String $r4 = null;
        MetadataImpl $r5 = null;
        while ($r1.dataPosition() < $i0) {
            int $i2 = com.google.android.gms.common.internal.safeparcel.zza.zzdy($r1);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zziv($i2)) {
                case 1:
                    $i1 = com.google.android.gms.common.internal.safeparcel.zza.zzg($r1, $i2);
                    $r3.add(Integer.valueOf(1));
                    break;
                case 2:
                    Parcelable $r8 = com.google.android.gms.common.internal.safeparcel.zza.zza($r1, $i2, MetadataImpl.CREATOR);
                    $r3.add(Integer.valueOf(2));
                    $r5 = (MetadataImpl) $r8;
                    break;
                case 3:
                    $r4 = com.google.android.gms.common.internal.safeparcel.zza.zzq($r1, $i2);
                    $r3.add(Integer.valueOf(3));
                    break;
                case 4:
                    $r2 = com.google.android.gms.common.internal.safeparcel.zza.zzq($r1, $i2);
                    $r3.add(Integer.valueOf(4));
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb($r1, $i2);
                    break;
            }
        }
        if ($r1.dataPosition() == $i0) {
            return new AboutsImpl($r3, $i1, $r5, $r4, $r2);
        }
        throw new com.google.android.gms.common.internal.safeparcel.zza.zza("Overread allowed size end=" + $i0, $r1);
    }
}
