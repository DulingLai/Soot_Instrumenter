package com.google.android.gms.people.identity.internal.models;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import java.util.HashSet;
import java.util.Set;

/* compiled from: dalvik_source_com.waze.apk */
public class zzl implements Creator<ImageReferenceImpl> {
    static void zza(ImageReferenceImpl $r0, Parcel $r1, int i) throws  {
        i = zzb.zzea($r1);
        Set $r2 = $r0.aOu;
        if ($r2.contains(Integer.valueOf(1))) {
            zzb.zzc($r1, 1, $r0.mVersionCode);
        }
        if ($r2.contains(Integer.valueOf(2))) {
            zzb.zzc($r1, 2, $r0.bG);
        }
        if ($r2.contains(Integer.valueOf(3))) {
            zzb.zza($r1, 3, $r0.aOw, true);
        }
        if ($r2.contains(Integer.valueOf(4))) {
            zzb.zza($r1, 4, $r0.mData, true);
        }
        zzb.zzaj($r1, i);
    }

    public /* synthetic */ Object createFromParcel(Parcel $r1) throws  {
        return zzsu($r1);
    }

    public /* synthetic */ Object[] newArray(int $i0) throws  {
        return zzabh($i0);
    }

    public ImageReferenceImpl[] zzabh(int $i0) throws  {
        return new ImageReferenceImpl[$i0];
    }

    public ImageReferenceImpl zzsu(Parcel $r1) throws  {
        byte[] $r2 = null;
        int $i0 = 0;
        int $i1 = zza.zzdz($r1);
        HashSet $r3 = new HashSet();
        String $r4 = null;
        int $i2 = 0;
        while ($r1.dataPosition() < $i1) {
            int $i3 = zza.zzdy($r1);
            switch (zza.zziv($i3)) {
                case 1:
                    $i2 = zza.zzg($r1, $i3);
                    $r3.add(Integer.valueOf(1));
                    break;
                case 2:
                    $i0 = zza.zzg($r1, $i3);
                    $r3.add(Integer.valueOf(2));
                    break;
                case 3:
                    $r4 = zza.zzq($r1, $i3);
                    $r3.add(Integer.valueOf(3));
                    break;
                case 4:
                    $r2 = zza.zzt($r1, $i3);
                    $r3.add(Integer.valueOf(4));
                    break;
                default:
                    zza.zzb($r1, $i3);
                    break;
            }
        }
        if ($r1.dataPosition() == $i1) {
            return new ImageReferenceImpl($r3, $i2, $i0, $r4, $r2);
        }
        throw new zza.zza("Overread allowed size end=" + $i1, $r1);
    }
}
