package com.google.android.gms.plus.service.v2whitelisted.models;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.plus.service.v2whitelisted.models.Person.Emails.Certificates.Status;
import java.util.HashSet;
import java.util.Set;

/* compiled from: dalvik_source_com.waze.apk */
public class zzu implements Creator<Status> {
    static void zza(Status $r0, Parcel $r1, int i) throws  {
        i = zzb.zzea($r1);
        Set $r2 = $r0.aOu;
        if ($r2.contains(Integer.valueOf(1))) {
            zzb.zzc($r1, 1, $r0.mVersionCode);
        }
        if ($r2.contains(Integer.valueOf(2))) {
            zzb.zza($r1, 2, $r0.mCode, true);
        }
        if ($r2.contains(Integer.valueOf(3))) {
            zzb.zza($r1, 3, $r0.aZX, true);
        }
        if ($r2.contains(Integer.valueOf(4))) {
            zzb.zza($r1, 4, $r0.aZY);
        }
        zzb.zzaj($r1, i);
    }

    public /* synthetic */ Object createFromParcel(Parcel $r1) throws  {
        return zzwi($r1);
    }

    public /* synthetic */ Object[] newArray(int $i0) throws  {
        return zzafl($i0);
    }

    public Status[] zzafl(int $i0) throws  {
        return new Status[$i0];
    }

    public Status zzwi(Parcel $r1) throws  {
        String $r2 = null;
        int $i0 = zza.zzdz($r1);
        HashSet $r3 = new HashSet();
        int $i1 = 0;
        long $l2 = 0;
        String $r4 = null;
        while ($r1.dataPosition() < $i0) {
            int $i3 = zza.zzdy($r1);
            switch (zza.zziv($i3)) {
                case 1:
                    $i1 = zza.zzg($r1, $i3);
                    $r3.add(Integer.valueOf(1));
                    break;
                case 2:
                    $r4 = zza.zzq($r1, $i3);
                    $r3.add(Integer.valueOf(2));
                    break;
                case 3:
                    $r2 = zza.zzq($r1, $i3);
                    $r3.add(Integer.valueOf(3));
                    break;
                case 4:
                    $l2 = zza.zzi($r1, $i3);
                    $r3.add(Integer.valueOf(4));
                    break;
                default:
                    zza.zzb($r1, $i3);
                    break;
            }
        }
        if ($r1.dataPosition() == $i0) {
            return new Status($r3, $i1, $r4, $r2, $l2);
        }
        throw new zza.zza("Overread allowed size end=" + $i0, $r1);
    }
}
