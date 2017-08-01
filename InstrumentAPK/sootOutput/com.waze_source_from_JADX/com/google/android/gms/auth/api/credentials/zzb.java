package com.google.android.gms.auth.api.credentials;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;

/* compiled from: dalvik_source_com.waze.apk */
public class zzb implements Creator<CredentialPickerConfig> {
    static void zza(CredentialPickerConfig $r0, Parcel $r1, int i) throws  {
        i = com.google.android.gms.common.internal.safeparcel.zzb.zzea($r1);
        com.google.android.gms.common.internal.safeparcel.zzb.zza($r1, 1, $r0.shouldShowAddAccountButton());
        com.google.android.gms.common.internal.safeparcel.zzb.zza($r1, 2, $r0.shouldShowCancelButton());
        com.google.android.gms.common.internal.safeparcel.zzb.zza($r1, 3, $r0.isForNewAccount());
        com.google.android.gms.common.internal.safeparcel.zzb.zzc($r1, 1000, $r0.mVersionCode);
        com.google.android.gms.common.internal.safeparcel.zzb.zzaj($r1, i);
    }

    public /* synthetic */ Object createFromParcel(Parcel $r1) throws  {
        return zzam($r1);
    }

    public /* synthetic */ Object[] newArray(int $i0) throws  {
        return zzcs($i0);
    }

    public CredentialPickerConfig zzam(Parcel $r1) throws  {
        boolean $z0 = false;
        int $i0 = zza.zzdz($r1);
        boolean $z1 = false;
        boolean $z2 = false;
        int $i1 = 0;
        while ($r1.dataPosition() < $i0) {
            int $i2 = zza.zzdy($r1);
            switch (zza.zziv($i2)) {
                case 1:
                    $z2 = zza.zzc($r1, $i2);
                    break;
                case 2:
                    $z1 = zza.zzc($r1, $i2);
                    break;
                case 3:
                    $z0 = zza.zzc($r1, $i2);
                    break;
                case 1000:
                    $i1 = zza.zzg($r1, $i2);
                    break;
                default:
                    zza.zzb($r1, $i2);
                    break;
            }
        }
        if ($r1.dataPosition() == $i0) {
            return new CredentialPickerConfig($i1, $z2, $z1, $z0);
        }
        throw new zza.zza("Overread allowed size end=" + $i0, $r1);
    }

    public CredentialPickerConfig[] zzcs(int $i0) throws  {
        return new CredentialPickerConfig[$i0];
    }
}
