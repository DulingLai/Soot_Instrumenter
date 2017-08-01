package com.google.android.gms.auth.firstparty.dataservice;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.auth.firstparty.shared.AppDescription;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

/* compiled from: dalvik_source_com.waze.apk */
public class PasswordCheckRequestCreator implements Creator<PasswordCheckRequest> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void zza(PasswordCheckRequest $r0, Parcel $r1, int $i0) throws  {
        int $i1 = zzb.zzea($r1);
        zzb.zzc($r1, 1, $r0.version);
        zzb.zza($r1, 2, $r0.accountName, false);
        zzb.zza($r1, 3, $r0.password, false);
        zzb.zza($r1, 4, $r0.go, false);
        zzb.zza($r1, 5, $r0.gp, false);
        zzb.zza($r1, 6, $r0.hx, $i0, false);
        zzb.zzaj($r1, $i1);
    }

    public PasswordCheckRequest createFromParcel(Parcel $r1) throws  {
        AppDescription $r2 = null;
        int $i0 = zza.zzdz($r1);
        int $i1 = 0;
        String $r3 = null;
        String $r4 = null;
        String $r5 = null;
        String $r6 = null;
        while ($r1.dataPosition() < $i0) {
            int $i2 = zza.zzdy($r1);
            switch (zza.zziv($i2)) {
                case 1:
                    $i1 = zza.zzg($r1, $i2);
                    break;
                case 2:
                    $r6 = zza.zzq($r1, $i2);
                    break;
                case 3:
                    $r5 = zza.zzq($r1, $i2);
                    break;
                case 4:
                    $r4 = zza.zzq($r1, $i2);
                    break;
                case 5:
                    $r3 = zza.zzq($r1, $i2);
                    break;
                case 6:
                    $r2 = (AppDescription) zza.zza($r1, $i2, (Creator) AppDescription.CREATOR);
                    break;
                default:
                    zza.zzb($r1, $i2);
                    break;
            }
        }
        if ($r1.dataPosition() == $i0) {
            return new PasswordCheckRequest($i1, $r6, $r5, $r4, $r3, $r2);
        }
        throw new zza.zza("Overread allowed size end=" + $i0, $r1);
    }

    public PasswordCheckRequest[] newArray(int $i0) throws  {
        return new PasswordCheckRequest[$i0];
    }
}
