package com.google.android.gms.people.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.abaltatech.mcp.weblink.core.WLTypes;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.people.internal.zzq;

/* compiled from: dalvik_source_com.waze.apk */
public final class AvatarReference extends AbstractSafeParcelable {
    public static final Creator<AvatarReference> CREATOR = new zzb();
    final String aOw;
    private final int mVersionCode;
    final int zzayf;

    AvatarReference(int $i0, int $i1, String $r1) throws  {
        zzab.zzbm($i1 != 0);
        this.mVersionCode = $i0;
        this.zzayf = $i1;
        this.aOw = $r1;
    }

    public AvatarReference(int $i0, String $r1) throws  {
        this(1, $i0, $r1);
    }

    public static AvatarReference fromPersistableString(String $r0) throws  {
        boolean $z0 = true;
        zzab.zzgy($r0);
        String[] $r2 = zzq.aQZ.split($r0);
        if ($r2.length != 3) {
            $z0 = false;
        }
        zzab.zzb($z0, (Object) "Malformed string");
        try {
            return new AvatarReference(Integer.parseInt($r2[0]), Integer.parseInt($r2[1]), $r2[2]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Malformed string");
        }
    }

    public String getLocation() throws  {
        return this.aOw;
    }

    public int getSource() throws  {
        return this.zzayf;
    }

    public int getVersionCode() throws  {
        return this.mVersionCode;
    }

    public String toPersistableString() throws  {
        return this.mVersionCode + '\u0001' + this.zzayf + '\u0001' + this.aOw;
    }

    public String toString() throws  {
        return zzaa.zzaf(this).zzh("source", Integer.valueOf(this.zzayf)).zzh(WLTypes.VEHICLEDATA_ATTRIBUTE_LOCATION, this.aOw).toString();
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        zzb.zza(this, $r1, $i0);
    }
}
