package com.google.android.gms.auth;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

/* compiled from: dalvik_source_com.waze.apk */
public class Country extends AbstractSafeParcelable {
    public static final CountryCreator CREATOR = new CountryCreator();
    public String mCode;
    public String mName;
    final int mVersionCode;

    public Country() throws  {
        this.mVersionCode = 1;
    }

    Country(int $i0, String $r1, String $r2) throws  {
        this.mVersionCode = $i0;
        this.mName = $r1;
        this.mCode = $r2;
    }

    public int getVersionCode() throws  {
        return this.mVersionCode;
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        CountryCreator.zza(this, $r1, $i0);
    }
}
