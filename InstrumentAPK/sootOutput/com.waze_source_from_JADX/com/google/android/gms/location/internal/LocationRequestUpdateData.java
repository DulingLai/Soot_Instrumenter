package com.google.android.gms.location.internal;

import android.app.PendingIntent;
import android.os.IBinder;
import android.os.Parcel;
import android.support.annotation.Nullable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.location.zze;
import com.google.android.gms.location.zzf;
import com.google.android.gms.location.zzf.zza;

/* compiled from: dalvik_source_com.waze.apk */
public class LocationRequestUpdateData extends AbstractSafeParcelable {
    public static final zzn CREATOR = new zzn();
    int awh;
    LocationRequestInternal awi;
    zzf awj;
    zze awk;
    zzg awl;
    PendingIntent mPendingIntent;
    private final int mVersionCode;

    LocationRequestUpdateData(int $i0, int $i1, LocationRequestInternal $r1, IBinder $r2, PendingIntent $r3, IBinder $r4, IBinder $r5) throws  {
        zzg $r6 = null;
        this.mVersionCode = $i0;
        this.awh = $i1;
        this.awi = $r1;
        this.awj = $r2 == null ? null : zza.zzkv($r2);
        this.mPendingIntent = $r3;
        this.awk = $r4 == null ? null : zze.zza.zzku($r4);
        if ($r5 != null) {
            $r6 = zzg.zza.zzkx($r5);
        }
        this.awl = $r6;
    }

    public static LocationRequestUpdateData zza(LocationRequestInternal $r0, PendingIntent $r1, @Nullable zzg $r2) throws  {
        return new LocationRequestUpdateData(1, 1, $r0, null, $r1, null, $r2 != null ? $r2.asBinder() : null);
    }

    public static LocationRequestUpdateData zza(LocationRequestInternal $r0, zze $r1, @Nullable zzg $r2) throws  {
        return new LocationRequestUpdateData(1, 1, $r0, null, null, $r1.asBinder(), $r2 != null ? $r2.asBinder() : null);
    }

    public static LocationRequestUpdateData zza(LocationRequestInternal $r0, zzf $r1, @Nullable zzg $r2) throws  {
        return new LocationRequestUpdateData(1, 1, $r0, $r1.asBinder(), null, null, $r2 != null ? $r2.asBinder() : null);
    }

    public static LocationRequestUpdateData zza(zze $r0, @Nullable zzg $r1) throws  {
        return new LocationRequestUpdateData(1, 2, null, null, null, $r0.asBinder(), $r1 != null ? $r1.asBinder() : null);
    }

    public static LocationRequestUpdateData zza(zzf $r0, @Nullable zzg $r1) throws  {
        return new LocationRequestUpdateData(1, 2, null, $r0.asBinder(), null, null, $r1 != null ? $r1.asBinder() : null);
    }

    public static LocationRequestUpdateData zzb(PendingIntent $r0, @Nullable zzg $r1) throws  {
        return new LocationRequestUpdateData(1, 2, null, null, $r0, null, $r1 != null ? $r1.asBinder() : null);
    }

    int getVersionCode() throws  {
        return this.mVersionCode;
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        zzn.zza(this, $r1, $i0);
    }

    IBinder zzbsl() throws  {
        return this.awj == null ? null : this.awj.asBinder();
    }

    IBinder zzbsm() throws  {
        return this.awk == null ? null : this.awk.asBinder();
    }

    IBinder zzbsn() throws  {
        return this.awl == null ? null : this.awl.asBinder();
    }
}
