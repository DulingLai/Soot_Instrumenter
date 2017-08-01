package com.google.android.gms.auth.firstparty.dataservice;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

/* compiled from: dalvik_source_com.waze.apk */
public class ClearTokenRequest extends AbstractSafeParcelable {
    public static final ClearTokenRequestCreator CREATOR = new ClearTokenRequestCreator();
    String gN;
    final int version;

    public ClearTokenRequest() throws  {
        this.version = 1;
    }

    ClearTokenRequest(int $i0, String $r1) throws  {
        this.version = $i0;
        this.gN = $r1;
    }

    public String getToken() throws  {
        return this.gN;
    }

    public ClearTokenRequest setToken(String $r1) throws  {
        this.gN = $r1;
        return this;
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        ClearTokenRequestCreator.zza(this, $r1, $i0);
    }
}
