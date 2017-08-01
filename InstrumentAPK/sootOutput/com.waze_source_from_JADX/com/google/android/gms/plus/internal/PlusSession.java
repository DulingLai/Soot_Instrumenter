package com.google.android.gms.plus.internal;

import android.os.Bundle;
import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.zzaa;
import java.util.Arrays;

/* compiled from: dalvik_source_com.waze.apk */
public class PlusSession extends AbstractSafeParcelable {
    public static final zzi CREATOR = new zzi();
    private final String[] aYF;
    private final String[] aYG;
    private final String[] aYH;
    private final String aYI;
    private final String aYJ;
    private final PlusCommonExtras aYK;
    private final String arz;
    private final String dL;
    private final int mVersionCode;
    private final String xI;

    PlusSession(int $i0, String $r1, String[] $r2, String[] $r3, String[] $r4, String $r5, String $r6, String $r7, String $r8, PlusCommonExtras $r9) throws  {
        this.mVersionCode = $i0;
        this.dL = $r1;
        this.aYF = $r2;
        this.aYG = $r3;
        this.aYH = $r4;
        this.aYI = $r5;
        this.arz = $r6;
        this.xI = $r7;
        this.aYJ = $r8;
        this.aYK = $r9;
    }

    public PlusSession(String $r1, String[] $r2, String[] $r3, String[] $r4, String $r5, String $r6, String $r7, PlusCommonExtras $r8) throws  {
        this.mVersionCode = 1;
        this.dL = $r1;
        this.aYF = $r2;
        this.aYG = $r3;
        this.aYH = $r4;
        this.aYI = $r5;
        this.arz = $r6;
        this.xI = $r7;
        this.aYJ = null;
        this.aYK = $r8;
    }

    public boolean equals(Object $r1) throws  {
        if (!($r1 instanceof PlusSession)) {
            return false;
        }
        PlusSession $r2 = (PlusSession) $r1;
        return this.mVersionCode == $r2.mVersionCode ? zzaa.equal(this.dL, $r2.dL) ? Arrays.equals(this.aYF, $r2.aYF) ? Arrays.equals(this.aYG, $r2.aYG) ? Arrays.equals(this.aYH, $r2.aYH) ? zzaa.equal(this.aYI, $r2.aYI) ? zzaa.equal(this.arz, $r2.arz) ? zzaa.equal(this.xI, $r2.xI) ? zzaa.equal(this.aYJ, $r2.aYJ) ? zzaa.equal(this.aYK, $r2.aYK) : false : false : false : false : false : false : false : false : false;
    }

    public String getAccountName() throws  {
        return this.dL;
    }

    public int getVersionCode() throws  {
        return this.mVersionCode;
    }

    public int hashCode() throws  {
        return zzaa.hashCode(Integer.valueOf(this.mVersionCode), this.dL, this.aYF, this.aYG, this.aYH, this.aYI, this.arz, this.xI, this.aYJ, this.aYK);
    }

    public String toString() throws  {
        return zzaa.zzaf(this).zzh("versionCode", Integer.valueOf(this.mVersionCode)).zzh("accountName", this.dL).zzh("requestedScopes", this.aYF).zzh("visibleActivities", this.aYG).zzh("requiredFeatures", this.aYH).zzh("packageNameForAuth", this.aYI).zzh("callingPackageName", this.arz).zzh("applicationName", this.xI).zzh("extra", this.aYK.toString()).toString();
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        zzi.zza(this, $r1, $i0);
    }

    public String zzapi() throws  {
        return this.xI;
    }

    public String[] zzcgv() throws  {
        return this.aYF;
    }

    public String[] zzcgw() throws  {
        return this.aYG;
    }

    public String[] zzcgx() throws  {
        return this.aYH;
    }

    public String zzcgy() throws  {
        return this.aYI;
    }

    public String zzcgz() throws  {
        return this.arz;
    }

    public String zzcha() throws  {
        return this.aYJ;
    }

    public PlusCommonExtras zzchb() throws  {
        return this.aYK;
    }

    public Bundle zzchc() throws  {
        Bundle $r1 = new Bundle();
        $r1.setClassLoader(PlusCommonExtras.class.getClassLoader());
        this.aYK.zzay($r1);
        return $r1;
    }
}
