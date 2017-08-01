package com.google.android.gms.people.identity.internal;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.people.identity.IdentityApi.ListOptions;

/* compiled from: dalvik_source_com.waze.apk */
public final class ParcelableListOptions extends AbstractSafeParcelable {
    public static final Creator<ParcelableListOptions> CREATOR = new zzj();
    final Bundle aMq;
    final boolean aMu;
    final boolean aOr;
    final boolean aOs;
    final String cJ;
    private final int mVersionCode;

    ParcelableListOptions(int $i0, boolean $z0, boolean $z1, boolean $z2, String $r1, Bundle $r2) throws  {
        this.mVersionCode = $i0;
        this.aOr = $z0;
        this.aMu = $z1;
        this.cJ = $r1;
        this.aOs = $z2;
        if ($r2 == null) {
            $r2 = new Bundle();
        }
        this.aMq = $r2;
    }

    public ParcelableListOptions(ListOptions $r1) throws  {
        this($r1.useCachedData, $r1.useWebData, $r1.useContactData, $r1.aMr.aMn, $r1.aMr.aMo);
    }

    public ParcelableListOptions(boolean $z0, boolean $z1, boolean $z2, String $r1, Bundle $r2) throws  {
        this(1, $z0, $z1, $z2, $r1, $r2);
    }

    public int getVersionCode() throws  {
        return this.mVersionCode;
    }

    public String toString() throws  {
        return zzaa.zzaf(this).zzh("useOfflineDatabase", Boolean.valueOf(this.aOr)).zzh("useWebData", Boolean.valueOf(this.aMu)).zzh("useCP2", Boolean.valueOf(this.aOs)).zzh("endpoint", this.cJ).toString();
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        zzj.zza(this, $r1, $i0);
    }
}
