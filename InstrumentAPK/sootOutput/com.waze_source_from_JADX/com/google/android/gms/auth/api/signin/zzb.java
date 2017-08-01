package com.google.android.gms.auth.api.signin;

import android.accounts.Account;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.zza;
import java.util.ArrayList;

/* compiled from: dalvik_source_com.waze.apk */
public class zzb implements Creator<GoogleSignInOptions> {
    static void zza(GoogleSignInOptions $r0, Parcel $r1, int $i0) throws  {
        int $i1 = com.google.android.gms.common.internal.safeparcel.zzb.zzea($r1);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc($r1, 1, $r0.versionCode);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc($r1, 2, $r0.zzaei(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza($r1, 3, $r0.getAccount(), $i0, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza($r1, 4, $r0.zzaej());
        com.google.android.gms.common.internal.safeparcel.zzb.zza($r1, 5, $r0.zzaek());
        com.google.android.gms.common.internal.safeparcel.zzb.zza($r1, 6, $r0.zzael());
        com.google.android.gms.common.internal.safeparcel.zzb.zza($r1, 7, $r0.zzaem(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza($r1, 8, $r0.zzaen(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzaj($r1, $i1);
    }

    public /* synthetic */ Object createFromParcel(Parcel $r1) throws  {
        return zzax($r1);
    }

    public /* synthetic */ Object[] newArray(int $i0) throws  {
        return zzdd($i0);
    }

    public GoogleSignInOptions zzax(Parcel $r1) throws  {
        String $r2 = null;
        boolean $z0 = false;
        int $i0 = zza.zzdz($r1);
        String $r3 = null;
        boolean $z1 = false;
        boolean $z2 = false;
        Account $r4 = null;
        ArrayList $r5 = null;
        int $i1 = 0;
        while ($r1.dataPosition() < $i0) {
            int $i2 = zza.zzdy($r1);
            switch (zza.zziv($i2)) {
                case 1:
                    $i1 = zza.zzg($r1, $i2);
                    break;
                case 2:
                    $r5 = zza.zzc($r1, $i2, Scope.CREATOR);
                    break;
                case 3:
                    $r4 = (Account) zza.zza($r1, $i2, Account.CREATOR);
                    break;
                case 4:
                    $z2 = zza.zzc($r1, $i2);
                    break;
                case 5:
                    $z1 = zza.zzc($r1, $i2);
                    break;
                case 6:
                    $z0 = zza.zzc($r1, $i2);
                    break;
                case 7:
                    $r3 = zza.zzq($r1, $i2);
                    break;
                case 8:
                    $r2 = zza.zzq($r1, $i2);
                    break;
                default:
                    zza.zzb($r1, $i2);
                    break;
            }
        }
        if ($r1.dataPosition() == $i0) {
            return new GoogleSignInOptions($i1, $r5, $r4, $z2, $z1, $z0, $r3, $r2);
        }
        throw new zza.zza("Overread allowed size end=" + $i0, $r1);
    }

    public GoogleSignInOptions[] zzdd(int $i0) throws  {
        return new GoogleSignInOptions[$i0];
    }
}
