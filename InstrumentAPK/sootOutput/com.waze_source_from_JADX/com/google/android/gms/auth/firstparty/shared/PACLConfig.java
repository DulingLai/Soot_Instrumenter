package com.google.android.gms.auth.firstparty.shared;

import android.os.Parcel;
import android.text.TextUtils;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.zzaa;

/* compiled from: dalvik_source_com.waze.apk */
public class PACLConfig extends AbstractSafeParcelable {
    public static final PACLConfigCreator CREATOR = new PACLConfigCreator();
    String iH;
    String iI;
    final int version;

    PACLConfig(int $i0, String $r1, String $r2) throws  {
        this.version = $i0;
        this.iH = $r1;
        this.iI = $r2;
    }

    public PACLConfig(String $r1, String $r2) throws  {
        this.version = 1;
        this.iH = $r1;
        this.iI = $r2;
    }

    public boolean equals(Object $r1) throws  {
        if (!($r1 instanceof PACLConfig)) {
            return false;
        }
        PACLConfig $r2 = (PACLConfig) $r1;
        return TextUtils.equals(this.iH, $r2.iH) ? TextUtils.equals(this.iI, $r2.iI) : false;
    }

    public String getPacl() throws  {
        return this.iI;
    }

    public String getVisibleActions() throws  {
        return this.iH;
    }

    public int hashCode() throws  {
        return zzaa.hashCode(this.iH, this.iI);
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        PACLConfigCreator.zza(this, $r1, $i0);
    }
}
