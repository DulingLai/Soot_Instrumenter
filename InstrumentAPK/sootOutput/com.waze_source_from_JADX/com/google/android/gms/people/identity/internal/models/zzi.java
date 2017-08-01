package com.google.android.gms.people.identity.internal.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.people.identity.internal.models.PersonImpl.EmailsImpl;
import com.google.android.gms.people.identity.internal.models.PersonImpl.MetadataImpl;
import java.util.HashSet;
import java.util.Set;

/* compiled from: dalvik_source_com.waze.apk */
public class zzi implements Creator<EmailsImpl> {
    static void zza(EmailsImpl $r0, Parcel $r1, int $i0) throws  {
        int $i1 = zzb.zzea($r1);
        Set $r2 = $r0.aOu;
        if ($r2.contains(Integer.valueOf(1))) {
            zzb.zzc($r1, 1, $r0.mVersionCode);
        }
        if ($r2.contains(Integer.valueOf(2))) {
            zzb.zza($r1, 2, $r0.aOZ, $i0, true);
        }
        if ($r2.contains(Integer.valueOf(3))) {
            zzb.zza($r1, 3, $r0.aPd, true);
        }
        if ($r2.contains(Integer.valueOf(4))) {
            zzb.zza($r1, 4, $r0.zzcft, true);
        }
        if ($r2.contains(Integer.valueOf(5))) {
            zzb.zza($r1, 5, $r0.mValue, true);
        }
        if ($r2.contains(Integer.valueOf(6))) {
            zzb.zzc($r1, 6, $r0.aMH);
        }
        zzb.zzaj($r1, $i1);
    }

    public /* synthetic */ Object createFromParcel(Parcel $r1) throws  {
        return zzsr($r1);
    }

    public /* synthetic */ Object[] newArray(int $i0) throws  {
        return zzabd($i0);
    }

    public EmailsImpl[] zzabd(int $i0) throws  {
        return new EmailsImpl[$i0];
    }

    public EmailsImpl zzsr(Parcel $r1) throws  {
        int $i0 = 0;
        String $r2 = null;
        int $i1 = zza.zzdz($r1);
        HashSet $r3 = new HashSet();
        String $r4 = null;
        String $r5 = null;
        MetadataImpl $r6 = null;
        int $i2 = 0;
        while ($r1.dataPosition() < $i1) {
            int $i3 = zza.zzdy($r1);
            switch (zza.zziv($i3)) {
                case 1:
                    $i2 = zza.zzg($r1, $i3);
                    $r3.add(Integer.valueOf(1));
                    break;
                case 2:
                    Parcelable $r9 = zza.zza($r1, $i3, MetadataImpl.CREATOR);
                    $r3.add(Integer.valueOf(2));
                    $r6 = (MetadataImpl) $r9;
                    break;
                case 3:
                    $r5 = zza.zzq($r1, $i3);
                    $r3.add(Integer.valueOf(3));
                    break;
                case 4:
                    $r4 = zza.zzq($r1, $i3);
                    $r3.add(Integer.valueOf(4));
                    break;
                case 5:
                    $r2 = zza.zzq($r1, $i3);
                    $r3.add(Integer.valueOf(5));
                    break;
                case 6:
                    $i0 = zza.zzg($r1, $i3);
                    $r3.add(Integer.valueOf(6));
                    break;
                default:
                    zza.zzb($r1, $i3);
                    break;
            }
        }
        if ($r1.dataPosition() == $i1) {
            return new EmailsImpl($r3, $i2, $r6, $r5, $r4, $r2, $i0);
        }
        throw new zza.zza("Overread allowed size end=" + $i1, $r1);
    }
}
