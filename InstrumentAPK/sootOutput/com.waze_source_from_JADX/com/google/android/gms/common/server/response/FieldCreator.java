package com.google.android.gms.common.server.response;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.server.converter.ConverterWrapper;
import com.google.android.gms.common.server.response.FastJsonResponse.Field;

/* compiled from: dalvik_source_com.waze.apk */
public class FieldCreator implements Creator<Field> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void zza(Field $r0, Parcel $r1, int $i0) throws  {
        int $i1 = zzb.zzea($r1);
        zzb.zzc($r1, 1, $r0.getVersionCode());
        zzb.zzc($r1, 2, $r0.getTypeIn());
        zzb.zza($r1, 3, $r0.isTypeInArray());
        zzb.zzc($r1, 4, $r0.getTypeOut());
        zzb.zza($r1, 5, $r0.isTypeOutArray());
        zzb.zza($r1, 6, $r0.getOutputFieldName(), false);
        zzb.zzc($r1, 7, $r0.getSafeParcelableFieldId());
        zzb.zza($r1, 8, $r0.zzaxu(), false);
        zzb.zza($r1, 9, $r0.zzaxv(), $i0, false);
        zzb.zzaj($r1, $i1);
    }

    public Field createFromParcel(Parcel $r1) throws  {
        ConverterWrapper $r2 = null;
        int $i0 = 0;
        int $i1 = zza.zzdz($r1);
        String $r3 = null;
        String $r4 = null;
        boolean $z0 = false;
        int $i2 = 0;
        boolean $z1 = false;
        int $i3 = 0;
        int $i4 = 0;
        while ($r1.dataPosition() < $i1) {
            int $i5 = zza.zzdy($r1);
            switch (zza.zziv($i5)) {
                case 1:
                    $i4 = zza.zzg($r1, $i5);
                    break;
                case 2:
                    $i3 = zza.zzg($r1, $i5);
                    break;
                case 3:
                    $z1 = zza.zzc($r1, $i5);
                    break;
                case 4:
                    $i2 = zza.zzg($r1, $i5);
                    break;
                case 5:
                    $z0 = zza.zzc($r1, $i5);
                    break;
                case 6:
                    $r4 = zza.zzq($r1, $i5);
                    break;
                case 7:
                    $i0 = zza.zzg($r1, $i5);
                    break;
                case 8:
                    $r3 = zza.zzq($r1, $i5);
                    break;
                case 9:
                    $r2 = (ConverterWrapper) zza.zza($r1, $i5, (Creator) ConverterWrapper.CREATOR);
                    break;
                default:
                    zza.zzb($r1, $i5);
                    break;
            }
        }
        if ($r1.dataPosition() == $i1) {
            return new Field($i4, $i3, $z1, $i2, $z0, $r4, $i0, $r3, $r2);
        }
        throw new zza.zza("Overread allowed size end=" + $i1, $r1);
    }

    public Field[] newArray(int $i0) throws  {
        return new Field[$i0];
    }
}
