package com.google.android.gms.auth.firstparty.dataservice;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

/* compiled from: dalvik_source_com.waze.apk */
public class PinSettingsCreator implements Creator<PinSettings> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void zza(PinSettings $r0, Parcel $r1, int i) throws  {
        i = zzb.zzea($r1);
        zzb.zzc($r1, 1, $r0.version);
        zzb.zza($r1, 2, $r0.status, false);
        zzb.zza($r1, 3, $r0.resetUrl, false);
        zzb.zza($r1, 4, $r0.setupUrl, false);
        zzb.zzc($r1, 5, $r0.length);
        zzb.zza($r1, 6, $r0.recoveryUrl, false);
        zzb.zzaj($r1, i);
    }

    public PinSettings createFromParcel(Parcel $r1) throws  {
        int $i0 = 0;
        String $r2 = null;
        int $i1 = zza.zzdz($r1);
        String $r3 = null;
        String $r4 = null;
        String $r5 = null;
        int $i2 = 0;
        while ($r1.dataPosition() < $i1) {
            int $i3 = zza.zzdy($r1);
            switch (zza.zziv($i3)) {
                case 1:
                    $i2 = zza.zzg($r1, $i3);
                    break;
                case 2:
                    $r5 = zza.zzq($r1, $i3);
                    break;
                case 3:
                    $r4 = zza.zzq($r1, $i3);
                    break;
                case 4:
                    $r3 = zza.zzq($r1, $i3);
                    break;
                case 5:
                    $i0 = zza.zzg($r1, $i3);
                    break;
                case 6:
                    $r2 = zza.zzq($r1, $i3);
                    break;
                default:
                    zza.zzb($r1, $i3);
                    break;
            }
        }
        if ($r1.dataPosition() == $i1) {
            return new PinSettings($i2, $r5, $r4, $r3, $r2, $i0);
        }
        throw new zza.zza("Overread allowed size end=" + $i1, $r1);
    }

    public PinSettings[] newArray(int $i0) throws  {
        return new PinSettings[$i0];
    }
}
