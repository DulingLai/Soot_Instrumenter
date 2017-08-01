package com.google.android.gms.auth.firstparty.shared;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

/* compiled from: dalvik_source_com.waze.apk */
public class AppDescriptionCreator implements Creator<AppDescription> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void zza(AppDescription $r0, Parcel $r1, int i) throws  {
        i = zzb.zzea($r1);
        zzb.zzc($r1, 1, $r0.version);
        zzb.zzc($r1, 2, $r0.ir);
        zzb.zza($r1, 3, $r0.zzcap, false);
        zzb.zza($r1, 4, $r0.is, false);
        zzb.zza($r1, 5, $r0.it, false);
        zzb.zza($r1, 6, $r0.gL);
        zzb.zzaj($r1, i);
    }

    public AppDescription createFromParcel(Parcel $r1) throws  {
        String $r2 = null;
        boolean $z0 = false;
        int $i0 = zza.zzdz($r1);
        String $r3 = null;
        String $r4 = null;
        int $i1 = 0;
        int $i2 = 0;
        while ($r1.dataPosition() < $i0) {
            int $i3 = zza.zzdy($r1);
            switch (zza.zziv($i3)) {
                case 1:
                    $i2 = zza.zzg($r1, $i3);
                    break;
                case 2:
                    $i1 = zza.zzg($r1, $i3);
                    break;
                case 3:
                    $r4 = zza.zzq($r1, $i3);
                    break;
                case 4:
                    $r3 = zza.zzq($r1, $i3);
                    break;
                case 5:
                    $r2 = zza.zzq($r1, $i3);
                    break;
                case 6:
                    $z0 = zza.zzc($r1, $i3);
                    break;
                default:
                    zza.zzb($r1, $i3);
                    break;
            }
        }
        if ($r1.dataPosition() == $i0) {
            return new AppDescription($i2, $i1, $r4, $r3, $r2, $z0);
        }
        throw new zza.zza("Overread allowed size end=" + $i0, $r1);
    }

    public AppDescription[] newArray(int $i0) throws  {
        return new AppDescription[$i0];
    }
}
