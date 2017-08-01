package com.google.android.gms.auth.firstparty.shared;

import android.os.Parcel;
import android.text.TextUtils;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.zzaa;

/* compiled from: dalvik_source_com.waze.apk */
public class FACLConfig extends AbstractSafeParcelable {
    public static final FACLConfigCreator CREATOR = new FACLConfigCreator();
    boolean iA;
    boolean iB;
    boolean iw;
    String ix;
    boolean iy;
    boolean iz;
    final int version;

    FACLConfig(int $i0, boolean $z0, String $r1, boolean $z1, boolean $z2, boolean $z3, boolean $z4) throws  {
        this.version = $i0;
        this.iw = $z0;
        this.ix = $r1;
        this.iy = $z1;
        this.iz = $z2;
        this.iA = $z3;
        this.iB = $z4;
    }

    public FACLConfig(boolean $z0, String $r1, boolean $z1, boolean $z2, boolean $z3, boolean $z4) throws  {
        this.version = 1;
        this.iw = $z0;
        if ($z0) {
            this.ix = "";
        } else {
            this.ix = $r1;
        }
        this.iy = $z1;
        this.iz = $z2;
        this.iA = $z3;
        this.iB = $z4;
    }

    public boolean equals(Object $r1) throws  {
        if (!($r1 instanceof FACLConfig)) {
            return false;
        }
        FACLConfig $r2 = (FACLConfig) $r1;
        return this.iw == $r2.iw ? TextUtils.equals(this.ix, $r2.ix) ? this.iy == $r2.iy ? this.iz == $r2.iz ? this.iA == $r2.iA ? this.iB == $r2.iB : false : false : false : false : false;
    }

    public boolean getShowCircles() throws  {
        return this.iz;
    }

    public boolean getShowContacts() throws  {
        return this.iA;
    }

    public String getVisibleEdges() throws  {
        return this.ix;
    }

    public boolean hasShowCircles() throws  {
        return this.iB;
    }

    public int hashCode() throws  {
        return zzaa.hashCode(Boolean.valueOf(this.iw), this.ix, Boolean.valueOf(this.iy), Boolean.valueOf(this.iz), Boolean.valueOf(this.iA), Boolean.valueOf(this.iB));
    }

    public boolean isAllCirclesVisible() throws  {
        return this.iw;
    }

    public boolean isAllContactsVisible() throws  {
        return this.iy;
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        FACLConfigCreator.zza(this, $r1, $i0);
    }
}
