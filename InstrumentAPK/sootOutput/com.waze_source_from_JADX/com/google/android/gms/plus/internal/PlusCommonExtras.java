package com.google.android.gms.plus.internal;

import android.os.Bundle;
import android.os.Parcel;
import com.google.android.gms.common.annotation.KeepName;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.zzc;
import com.google.android.gms.common.internal.zzaa;

@KeepName
/* compiled from: dalvik_source_com.waze.apk */
public class PlusCommonExtras extends AbstractSafeParcelable {
    public static final zzg CREATOR = new zzg();
    private String aYC;
    private String aYD;
    private final int mVersionCode;

    public PlusCommonExtras() throws  {
        this.mVersionCode = 1;
        this.aYC = "";
        this.aYD = "";
    }

    PlusCommonExtras(int $i0, String $r1, String $r2) throws  {
        this.mVersionCode = $i0;
        this.aYC = $r1;
        this.aYD = $r2;
    }

    public boolean equals(Object $r1) throws  {
        if (!($r1 instanceof PlusCommonExtras)) {
            return false;
        }
        PlusCommonExtras $r2 = (PlusCommonExtras) $r1;
        return this.mVersionCode == $r2.mVersionCode ? zzaa.equal(this.aYC, $r2.aYC) ? zzaa.equal(this.aYD, $r2.aYD) : false : false;
    }

    public int getVersionCode() throws  {
        return this.mVersionCode;
    }

    public int hashCode() throws  {
        return zzaa.hashCode(Integer.valueOf(this.mVersionCode), this.aYC, this.aYD);
    }

    public String toString() throws  {
        return zzaa.zzaf(this).zzh("versionCode", Integer.valueOf(this.mVersionCode)).zzh("Gpsrc", this.aYC).zzh("ClientCallingPackage", this.aYD).toString();
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        zzg.zza(this, $r1, $i0);
    }

    public void zzay(Bundle $r1) throws  {
        $r1.putByteArray("android.gms.plus.internal.PlusCommonExtras.extraPlusCommon", zzc.zza(this));
    }

    public String zzcgt() throws  {
        return this.aYC;
    }

    public String zzcgu() throws  {
        return this.aYD;
    }
}
