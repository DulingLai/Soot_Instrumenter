package com.google.android.gms.common.server.response;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.server.response.FastJsonResponse.Field;
import com.google.android.gms.common.server.response.FieldMappingDictionary.FieldMapPair;

/* compiled from: dalvik_source_com.waze.apk */
public class FieldMapPairCreator implements Creator<FieldMapPair> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void zza(FieldMapPair $r0, Parcel $r1, int $i0) throws  {
        int $i1 = zzb.zzea($r1);
        zzb.zzc($r1, 1, $r0.versionCode);
        zzb.zza($r1, 2, $r0.zzca, false);
        zzb.zza($r1, 3, $r0.LB, $i0, false);
        zzb.zzaj($r1, $i1);
    }

    public FieldMapPair createFromParcel(Parcel $r1) throws  {
        Field $r2 = null;
        int $i0 = zza.zzdz($r1);
        int $i1 = 0;
        String $r3 = null;
        while ($r1.dataPosition() < $i0) {
            int $i2 = zza.zzdy($r1);
            switch (zza.zziv($i2)) {
                case 1:
                    $i1 = zza.zzg($r1, $i2);
                    break;
                case 2:
                    $r3 = zza.zzq($r1, $i2);
                    break;
                case 3:
                    $r2 = (Field) zza.zza($r1, $i2, Field.CREATOR);
                    break;
                default:
                    zza.zzb($r1, $i2);
                    break;
            }
        }
        if ($r1.dataPosition() == $i0) {
            return new FieldMapPair($i1, $r3, $r2);
        }
        throw new zza.zza("Overread allowed size end=" + $i0, $r1);
    }

    public FieldMapPair[] newArray(int $i0) throws  {
        return new FieldMapPair[$i0];
    }
}
