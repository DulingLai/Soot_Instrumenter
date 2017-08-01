package com.google.android.gms.plus.service.v2whitelisted.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.plus.service.v2whitelisted.models.Person.SipAddress;
import java.util.HashSet;
import java.util.Set;

/* compiled from: dalvik_source_com.waze.apk */
public class zzas implements Creator<SipAddress> {
    static void zza(SipAddress $r0, Parcel $r1, int $i0) throws  {
        int $i1 = zzb.zzea($r1);
        Set $r2 = $r0.aOu;
        if ($r2.contains(Integer.valueOf(1))) {
            zzb.zzc($r1, 1, $r0.mVersionCode);
        }
        if ($r2.contains(Integer.valueOf(2))) {
            zzb.zza($r1, 2, $r0.aZT, $i0, true);
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
        return zzxg($r1);
    }

    public /* synthetic */ Object[] newArray(int $i0) throws  {
        return zzagj($i0);
    }

    public SipAddress[] zzagj(int $i0) throws  {
        return new SipAddress[$i0];
    }

    public SipAddress zzxg(Parcel $r1) throws  {
        String $r2 = null;
        int $i0 = zza.zzdz($r1);
        HashSet $r3 = new HashSet();
        int $i1 = 0;
        String $r4 = null;
        Mergedpeoplemetadata $r5 = null;
        while ($r1.dataPosition() < $i0) {
            int $i2 = zza.zzdy($r1);
            switch (zza.zziv($i2)) {
                case 1:
                    $i1 = zza.zzg($r1, $i2);
                    $r3.add(Integer.valueOf(1));
                    break;
                case 2:
                    Parcelable $r8 = zza.zza($r1, $i2, Mergedpeoplemetadata.CREATOR);
                    $r3.add(Integer.valueOf(2));
                    $r5 = (Mergedpeoplemetadata) $r8;
                    break;
                case 3:
                    $r4 = zza.zzq($r1, $i2);
                    $r3.add(Integer.valueOf(3));
                    break;
                case 4:
                    $r2 = zza.zzq($r1, $i2);
                    $r3.add(Integer.valueOf(4));
                    break;
                default:
                    zza.zzb($r1, $i2);
                    break;
            }
        }
        if ($r1.dataPosition() == $i0) {
            return new SipAddress($r3, $i1, $r5, $r4, $r2);
        }
        throw new zza.zza("Overread allowed size end=" + $i0, $r1);
    }
}
