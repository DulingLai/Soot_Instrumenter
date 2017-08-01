package com.google.android.gms.auth;

import android.app.PendingIntent;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

/* compiled from: dalvik_source_com.waze.apk */
public class RecoveryDecisionCreator implements Creator<RecoveryDecision> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void zza(RecoveryDecision $r0, Parcel $r1, int $i0) throws  {
        int $i1 = zzb.zzea($r1);
        zzb.zzc($r1, 1, $r0.mVersionCode);
        zzb.zza($r1, 2, $r0.recoveryIntent, $i0, false);
        zzb.zza($r1, 3, $r0.showRecoveryInterstitial);
        zzb.zza($r1, 4, $r0.isRecoveryInfoNeeded);
        zzb.zza($r1, 5, $r0.isRecoveryInterstitialAllowed);
        zzb.zza($r1, 6, $r0.recoveryIntentWithoutIntro, $i0, false);
        zzb.zzaj($r1, $i1);
    }

    public RecoveryDecision createFromParcel(Parcel $r1) throws  {
        PendingIntent $r2 = null;
        boolean $z0 = false;
        int $i0 = zza.zzdz($r1);
        boolean $z1 = false;
        boolean $z2 = false;
        PendingIntent $r3 = null;
        int $i1 = 0;
        while ($r1.dataPosition() < $i0) {
            int $i2 = zza.zzdy($r1);
            switch (zza.zziv($i2)) {
                case 1:
                    $i1 = zza.zzg($r1, $i2);
                    break;
                case 2:
                    $r3 = (PendingIntent) zza.zza($r1, $i2, PendingIntent.CREATOR);
                    break;
                case 3:
                    $z2 = zza.zzc($r1, $i2);
                    break;
                case 4:
                    $z1 = zza.zzc($r1, $i2);
                    break;
                case 5:
                    $z0 = zza.zzc($r1, $i2);
                    break;
                case 6:
                    $r2 = (PendingIntent) zza.zza($r1, $i2, PendingIntent.CREATOR);
                    break;
                default:
                    zza.zzb($r1, $i2);
                    break;
            }
        }
        if ($r1.dataPosition() == $i0) {
            return new RecoveryDecision($i1, $r3, $z2, $z1, $z0, $r2);
        }
        throw new zza.zza("Overread allowed size end=" + $i0, $r1);
    }

    public RecoveryDecision[] newArray(int $i0) throws  {
        return new RecoveryDecision[$i0];
    }
}
