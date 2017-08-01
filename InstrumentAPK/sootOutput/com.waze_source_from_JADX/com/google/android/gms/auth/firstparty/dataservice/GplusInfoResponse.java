package com.google.android.gms.auth.firstparty.dataservice;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

/* compiled from: dalvik_source_com.waze.apk */
public class GplusInfoResponse extends AbstractSafeParcelable {
    public static final GplusInfoResponseCreator CREATOR = new GplusInfoResponseCreator();
    String firstName;
    String hq;
    boolean hr;
    String hs;
    boolean ht;
    boolean hu;
    String hv;
    String hw;
    String lastName;
    final int version;

    GplusInfoResponse(int $i0, boolean $z0, String $r1, String $r2, String $r3, boolean $z1, boolean $z2, String $r4, String $r5, String $r6) throws  {
        this.version = $i0;
        this.hr = $z0;
        this.firstName = $r1;
        this.lastName = $r2;
        this.hs = $r3;
        this.ht = $z1;
        this.hu = $z2;
        this.hv = $r4;
        this.hq = $r5;
        this.hw = $r6;
    }

    public GplusInfoResponse(boolean $z0, String $r1, String $r2, String $r3, boolean $z1, boolean $z2, String $r4, String $r5, String $r6) throws  {
        this.version = 1;
        this.hr = $z0;
        this.firstName = $r1;
        this.lastName = $r2;
        this.hs = $r3;
        this.hv = $r4;
        this.hq = $r5;
        this.ht = $z1;
        this.hu = $z2;
        this.hw = $r6;
    }

    public String getFirstName() throws  {
        return this.firstName;
    }

    public String getLastName() throws  {
        return this.lastName;
    }

    public String getPicasaUser() throws  {
        return this.hs;
    }

    public String getRopRevision() throws  {
        return this.hq;
    }

    public String getRopText() throws  {
        return this.hv;
    }

    public String getWireCode() throws  {
        return this.hw;
    }

    public boolean hasEsMobile() throws  {
        return this.hu;
    }

    public boolean hasGooglePlus() throws  {
        return this.ht;
    }

    public boolean isAllowed() throws  {
        return this.hr;
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        GplusInfoResponseCreator.zza(this, $r1, $i0);
    }
}
