package com.google.android.gms.common.server.response;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.server.response.FieldMappingDictionary.Entry;
import com.google.android.gms.common.server.response.FieldMappingDictionary.FieldMapPair;
import java.util.ArrayList;

/* compiled from: dalvik_source_com.waze.apk */
public class FieldMappingDictionaryEntryCreator implements Creator<Entry> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void zza(Entry $r0, Parcel $r1, int i) throws  {
        i = zzb.zzea($r1);
        zzb.zzc($r1, 1, $r0.versionCode);
        zzb.zza($r1, 2, $r0.className, false);
        zzb.zzc($r1, 3, $r0.LA, false);
        zzb.zzaj($r1, i);
    }

    public Entry createFromParcel(Parcel $r1) throws  {
        ArrayList $r2 = null;
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
                    $r2 = zza.zzc($r1, $i2, FieldMapPair.CREATOR);
                    break;
                default:
                    zza.zzb($r1, $i2);
                    break;
            }
        }
        if ($r1.dataPosition() == $i0) {
            return new Entry($i1, $r3, $r2);
        }
        throw new zza.zza("Overread allowed size end=" + $i0, $r1);
    }

    public Entry[] newArray(int $i0) throws  {
        return new Entry[$i0];
    }
}
