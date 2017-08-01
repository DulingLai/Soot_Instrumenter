package com.google.android.gms.auth.api.credentials;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

/* compiled from: dalvik_source_com.waze.apk */
public class zzc implements Creator<CredentialRequest> {
    static void zza(CredentialRequest $r0, Parcel $r1, int $i0) throws  {
        int $i1 = zzb.zzea($r1);
        zzb.zza($r1, 1, $r0.isPasswordLoginSupported());
        zzb.zza($r1, 2, $r0.getAccountTypes(), false);
        zzb.zza($r1, 3, $r0.getCredentialPickerConfig(), $i0, false);
        zzb.zza($r1, 4, $r0.getCredentialHintPickerConfig(), $i0, false);
        zzb.zzc($r1, 1000, $r0.mVersionCode);
        zzb.zzaj($r1, $i1);
    }

    public /* synthetic */ Object createFromParcel(Parcel $r1) throws  {
        return zzan($r1);
    }

    public /* synthetic */ Object[] newArray(int $i0) throws  {
        return zzct($i0);
    }

    public CredentialRequest zzan(Parcel $r1) throws  {
        boolean $z0 = false;
        CredentialPickerConfig $r2 = null;
        int $i0 = zza.zzdz($r1);
        CredentialPickerConfig $r3 = null;
        String[] $r4 = null;
        int $i1 = 0;
        while ($r1.dataPosition() < $i0) {
            int $i2 = zza.zzdy($r1);
            switch (zza.zziv($i2)) {
                case 1:
                    $z0 = zza.zzc($r1, $i2);
                    break;
                case 2:
                    $r4 = zza.zzac($r1, $i2);
                    break;
                case 3:
                    $r3 = (CredentialPickerConfig) zza.zza($r1, $i2, CredentialPickerConfig.CREATOR);
                    break;
                case 4:
                    $r2 = (CredentialPickerConfig) zza.zza($r1, $i2, CredentialPickerConfig.CREATOR);
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
            return new CredentialRequest($i1, $z0, $r4, $r3, $r2);
        }
        throw new zza.zza("Overread allowed size end=" + $i0, $r1);
    }

    public CredentialRequest[] zzct(int $i0) throws  {
        return new CredentialRequest[$i0];
    }
}
