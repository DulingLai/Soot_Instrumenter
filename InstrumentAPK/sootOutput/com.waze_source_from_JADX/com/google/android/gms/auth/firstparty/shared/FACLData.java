package com.google.android.gms.auth.firstparty.shared;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

/* compiled from: dalvik_source_com.waze.apk */
public class FACLData extends AbstractSafeParcelable {
    public static final FACLDataCreator CREATOR = new FACLDataCreator();
    FACLConfig iC;
    String iD;
    boolean iE;
    String iF;
    final int version;

    FACLData(int $i0, FACLConfig $r1, String $r2, boolean $z0, String $r3) throws  {
        this.version = $i0;
        this.iC = $r1;
        this.iD = $r2;
        this.iE = $z0;
        this.iF = $r3;
    }

    public FACLData(FACLConfig $r1, String $r2, String $r3, boolean $z0) throws  {
        this.version = 1;
        this.iC = $r1;
        this.iD = $r2;
        this.iF = $r3;
        this.iE = $z0;
    }

    public String getActivityText() throws  {
        return this.iD;
    }

    public FACLConfig getFaclConfig() throws  {
        return this.iC;
    }

    public String getSpeedbumpText() throws  {
        return this.iF;
    }

    public boolean isSpeedbumpNeeded() throws  {
        return this.iE;
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        FACLDataCreator.zza(this, $r1, $i0);
    }
}
