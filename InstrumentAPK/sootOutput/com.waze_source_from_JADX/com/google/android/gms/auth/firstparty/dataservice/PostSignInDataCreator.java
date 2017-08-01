package com.google.android.gms.auth.firstparty.dataservice;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

/* compiled from: dalvik_source_com.waze.apk */
public class PostSignInDataCreator implements Creator<PostSignInData> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void zza(PostSignInData $r0, Parcel $r1, int $i0) throws  {
        int $i1 = zzb.zzea($r1);
        zzb.zzc($r1, 1, $r0.version);
        zzb.zza($r1, 2, $r0.postSignInForeignIntent, $i0, false);
        zzb.zza($r1, 3, $r0.accountInstallationCompletionAction, $i0, false);
        zzb.zzaj($r1, $i1);
    }

    public PostSignInData createFromParcel(Parcel $r1) throws  {
        PendingIntent $r2 = null;
        int $i0 = zza.zzdz($r1);
        int $i1 = 0;
        Intent $r3 = null;
        while ($r1.dataPosition() < $i0) {
            int $i2 = zza.zzdy($r1);
            switch (zza.zziv($i2)) {
                case 1:
                    $i1 = zza.zzg($r1, $i2);
                    break;
                case 2:
                    $r3 = (Intent) zza.zza($r1, $i2, Intent.CREATOR);
                    break;
                case 3:
                    $r2 = (PendingIntent) zza.zza($r1, $i2, PendingIntent.CREATOR);
                    break;
                default:
                    zza.zzb($r1, $i2);
                    break;
            }
        }
        if ($r1.dataPosition() == $i0) {
            return new PostSignInData($i1, $r3, $r2);
        }
        throw new zza.zza("Overread allowed size end=" + $i0, $r1);
    }

    public PostSignInData[] newArray(int $i0) throws  {
        return new PostSignInData[$i0];
    }
}
