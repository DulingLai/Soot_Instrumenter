package com.google.android.gms.auth.firstparty.shared;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import java.util.ArrayList;

/* compiled from: dalvik_source_com.waze.apk */
public class ScopeDetailCreator implements Creator<ScopeDetail> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void zza(ScopeDetail $r0, Parcel $r1, int $i0) throws  {
        int $i1 = zzb.zzea($r1);
        zzb.zzc($r1, 1, $r0.version);
        zzb.zza($r1, 2, $r0.description, false);
        zzb.zza($r1, 3, $r0.gu, false);
        zzb.zza($r1, 4, $r0.iK, false);
        zzb.zza($r1, 5, $r0.iL, false);
        zzb.zza($r1, 6, $r0.hz, false);
        zzb.zzb($r1, 7, $r0.iM, false);
        zzb.zza($r1, 8, $r0.friendPickerData, $i0, false);
        zzb.zzaj($r1, $i1);
    }

    public ScopeDetail createFromParcel(Parcel $r1) throws  {
        FACLData $r2 = null;
        int $i0 = zza.zzdz($r1);
        int $i1 = 0;
        ArrayList $r3 = new ArrayList();
        String $r4 = null;
        String $r5 = null;
        String $r6 = null;
        String $r7 = null;
        String $r8 = null;
        while ($r1.dataPosition() < $i0) {
            int $i2 = zza.zzdy($r1);
            switch (zza.zziv($i2)) {
                case 1:
                    $i1 = zza.zzg($r1, $i2);
                    break;
                case 2:
                    $r8 = zza.zzq($r1, $i2);
                    break;
                case 3:
                    $r7 = zza.zzq($r1, $i2);
                    break;
                case 4:
                    $r6 = zza.zzq($r1, $i2);
                    break;
                case 5:
                    $r5 = zza.zzq($r1, $i2);
                    break;
                case 6:
                    $r4 = zza.zzq($r1, $i2);
                    break;
                case 7:
                    $r3 = zza.zzae($r1, $i2);
                    break;
                case 8:
                    $r2 = (FACLData) zza.zza($r1, $i2, (Creator) FACLData.CREATOR);
                    break;
                default:
                    zza.zzb($r1, $i2);
                    break;
            }
        }
        if ($r1.dataPosition() == $i0) {
            return new ScopeDetail($i1, $r8, $r7, $r6, $r5, $r4, $r3, $r2);
        }
        throw new zza.zza("Overread allowed size end=" + $i0, $r1);
    }

    public ScopeDetail[] newArray(int $i0) throws  {
        return new ScopeDetail[$i0];
    }
}
