package com.google.android.gms.location.reporting;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

/* compiled from: dalvik_source_com.waze.apk */
public class ReportingStateCreator implements Creator<ReportingState> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void zza(ReportingState $r0, Parcel $r1, int i) throws  {
        i = zzb.zzea($r1);
        zzb.zzc($r1, 1, $r0.getVersionCode());
        zzb.zzc($r1, 2, $r0.getReportingEnabled());
        zzb.zzc($r1, 3, $r0.getHistoryEnabled());
        zzb.zza($r1, 4, $r0.isAllowed());
        zzb.zza($r1, 5, $r0.isActive());
        zzb.zzc($r1, 7, $r0.getExpectedOptInResult());
        zzb.zza($r1, 8, $r0.zzbto(), false);
        zzb.zzc($r1, 9, $r0.zzbtn());
        zzb.zza($r1, 10, $r0.canAccessSettings());
        zzb.zzaj($r1, i);
    }

    public ReportingState createFromParcel(Parcel $r1) throws  {
        boolean $z0 = false;
        int $i0 = zza.zzdz($r1);
        Integer $r2 = null;
        int $i1 = 0;
        int $i2 = 0;
        boolean $z1 = false;
        boolean $z2 = false;
        int $i3 = 0;
        int $i4 = 0;
        int $i5 = 0;
        while ($r1.dataPosition() < $i0) {
            int $i6 = zza.zzdy($r1);
            switch (zza.zziv($i6)) {
                case 1:
                    $i5 = zza.zzg($r1, $i6);
                    continue;
                case 2:
                    $i4 = zza.zzg($r1, $i6);
                    continue;
                case 3:
                    $i3 = zza.zzg($r1, $i6);
                    continue;
                case 4:
                    $z2 = zza.zzc($r1, $i6);
                    continue;
                case 5:
                    $z1 = zza.zzc($r1, $i6);
                    continue;
                case 6:
                    break;
                case 7:
                    $i2 = zza.zzg($r1, $i6);
                    continue;
                case 8:
                    $r2 = zza.zzh($r1, $i6);
                    continue;
                case 9:
                    $i1 = zza.zzg($r1, $i6);
                    continue;
                case 10:
                    $z0 = zza.zzc($r1, $i6);
                    continue;
                default:
                    break;
            }
            zza.zzb($r1, $i6);
        }
        if ($r1.dataPosition() == $i0) {
            return new ReportingState($i5, $i4, $i3, $z2, $z1, $i2, $i1, $r2, $z0);
        }
        throw new zza.zza("Overread allowed size end=" + $i0, $r1);
    }

    public ReportingState[] newArray(int $i0) throws  {
        return new ReportingState[$i0];
    }
}
