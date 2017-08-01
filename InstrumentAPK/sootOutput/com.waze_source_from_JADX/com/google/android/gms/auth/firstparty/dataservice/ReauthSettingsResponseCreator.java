package com.google.android.gms.auth.firstparty.dataservice;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

/* compiled from: dalvik_source_com.waze.apk */
public class ReauthSettingsResponseCreator implements Creator<ReauthSettingsResponse> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void zza(ReauthSettingsResponse $r0, Parcel $r1, int $i0) throws  {
        int $i1 = zzb.zzea($r1);
        zzb.zzc($r1, 1, $r0.version);
        zzb.zzc($r1, 2, $r0.status);
        zzb.zza($r1, 3, $r0.password, $i0, false);
        zzb.zza($r1, 4, $r0.pin, $i0, false);
        zzb.zzaj($r1, $i1);
    }

    public ReauthSettingsResponse createFromParcel(Parcel $r1) throws  {
        PinSettings $r2 = null;
        int $i0 = 0;
        int $i1 = zza.zzdz($r1);
        PasswordSettings $r3 = null;
        int $i2 = 0;
        while ($r1.dataPosition() < $i1) {
            int $i3 = zza.zzdy($r1);
            switch (zza.zziv($i3)) {
                case 1:
                    $i2 = zza.zzg($r1, $i3);
                    break;
                case 2:
                    $i0 = zza.zzg($r1, $i3);
                    break;
                case 3:
                    $r3 = (PasswordSettings) zza.zza($r1, $i3, PasswordSettings.CREATOR);
                    break;
                case 4:
                    $r2 = (PinSettings) zza.zza($r1, $i3, PinSettings.CREATOR);
                    break;
                default:
                    zza.zzb($r1, $i3);
                    break;
            }
        }
        if ($r1.dataPosition() == $i1) {
            return new ReauthSettingsResponse($i2, $i0, $r3, $r2);
        }
        throw new zza.zza("Overread allowed size end=" + $i1, $r1);
    }

    public ReauthSettingsResponse[] newArray(int $i0) throws  {
        return new ReauthSettingsResponse[$i0];
    }
}
