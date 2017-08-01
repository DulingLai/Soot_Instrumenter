package com.google.android.gms.people.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;

/* compiled from: dalvik_source_com.waze.apk */
public class zza implements Creator<AccountMetadata> {
    static void zza(AccountMetadata $r0, Parcel $r1, int i) throws  {
        i = zzb.zzea($r1);
        zzb.zzc($r1, 1, $r0.mVersionCode);
        zzb.zza($r1, 2, $r0.isPlusEnabled);
        zzb.zza($r1, 3, $r0.isSyncEnabled);
        zzb.zza($r1, 4, $r0.isPagePeriodicSyncEnabled);
        zzb.zza($r1, 5, $r0.isPageTickleSyncEnabled);
        zzb.zzaj($r1, i);
    }

    public /* synthetic */ Object createFromParcel(Parcel $r1) throws  {
        return zzuj($r1);
    }

    public /* synthetic */ Object[] newArray(int $i0) throws  {
        return zzadf($i0);
    }

    public AccountMetadata[] zzadf(int $i0) throws  {
        return new AccountMetadata[$i0];
    }

    public AccountMetadata zzuj(Parcel $r1) throws  {
        boolean $z0 = false;
        int $i0 = com.google.android.gms.common.internal.safeparcel.zza.zzdz($r1);
        boolean $z1 = false;
        boolean $z2 = false;
        boolean $z3 = false;
        int $i1 = 0;
        while ($r1.dataPosition() < $i0) {
            int $i2 = com.google.android.gms.common.internal.safeparcel.zza.zzdy($r1);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zziv($i2)) {
                case 1:
                    $i1 = com.google.android.gms.common.internal.safeparcel.zza.zzg($r1, $i2);
                    break;
                case 2:
                    $z3 = com.google.android.gms.common.internal.safeparcel.zza.zzc($r1, $i2);
                    break;
                case 3:
                    $z2 = com.google.android.gms.common.internal.safeparcel.zza.zzc($r1, $i2);
                    break;
                case 4:
                    $z1 = com.google.android.gms.common.internal.safeparcel.zza.zzc($r1, $i2);
                    break;
                case 5:
                    $z0 = com.google.android.gms.common.internal.safeparcel.zza.zzc($r1, $i2);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb($r1, $i2);
                    break;
            }
        }
        if ($r1.dataPosition() == $i0) {
            return new AccountMetadata($i1, $z3, $z2, $z1, $z0);
        }
        throw new com.google.android.gms.common.internal.safeparcel.zza.zza("Overread allowed size end=" + $i0, $r1);
    }
}
