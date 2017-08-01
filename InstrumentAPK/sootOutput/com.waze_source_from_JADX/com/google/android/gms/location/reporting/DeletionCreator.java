package com.google.android.gms.location.reporting;

import android.accounts.Account;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

/* compiled from: dalvik_source_com.waze.apk */
public class DeletionCreator implements Creator<Deletion> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void zza(Deletion $r0, Parcel $r1, int $i0) throws  {
        int $i1 = zzb.zzea($r1);
        zzb.zzc($r1, 1, $r0.getVersionCode());
        zzb.zza($r1, 2, $r0.getAccount(), $i0, false);
        zzb.zza($r1, 3, $r0.getStartTimeMs());
        zzb.zza($r1, 4, $r0.getEndTimeMs());
        zzb.zza($r1, 5, $r0.getTimestampMs());
        zzb.zzaj($r1, $i1);
    }

    public Deletion createFromParcel(Parcel $r1) throws  {
        int $i0 = zza.zzdz($r1);
        int $i1 = 0;
        Account $r2 = null;
        long $l2 = 0;
        long $l3 = 0;
        long $l4 = 0;
        while ($r1.dataPosition() < $i0) {
            int $i5 = zza.zzdy($r1);
            switch (zza.zziv($i5)) {
                case 1:
                    $i1 = zza.zzg($r1, $i5);
                    break;
                case 2:
                    $r2 = (Account) zza.zza($r1, $i5, Account.CREATOR);
                    break;
                case 3:
                    $l2 = zza.zzi($r1, $i5);
                    break;
                case 4:
                    $l3 = zza.zzi($r1, $i5);
                    break;
                case 5:
                    $l4 = zza.zzi($r1, $i5);
                    break;
                default:
                    zza.zzb($r1, $i5);
                    break;
            }
        }
        if ($r1.dataPosition() != $i0) {
            throw new zza.zza("Overread allowed size end=" + $i0, $r1);
        }
        return new Deletion($i1, $r2, Long.valueOf($l2), Long.valueOf($l3), Long.valueOf($l4));
    }

    public Deletion[] newArray(int $i0) throws  {
        return new Deletion[$i0];
    }
}
