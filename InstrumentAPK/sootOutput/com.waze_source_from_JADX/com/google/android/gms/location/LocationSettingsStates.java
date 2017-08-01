package com.google.android.gms.location;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.zzc;

/* compiled from: dalvik_source_com.waze.apk */
public final class LocationSettingsStates extends AbstractSafeParcelable {
    public static final Creator<LocationSettingsStates> CREATOR = new zzj();
    private final boolean avp;
    private final boolean avq;
    private final boolean avr;
    private final boolean avs;
    private final boolean avt;
    private final boolean avu;
    private final int mVersionCode;

    LocationSettingsStates(int $i0, boolean $z0, boolean $z1, boolean $z2, boolean $z3, boolean $z4, boolean $z5) throws  {
        this.mVersionCode = $i0;
        this.avp = $z0;
        this.avq = $z1;
        this.avr = $z2;
        this.avs = $z3;
        this.avt = $z4;
        this.avu = $z5;
    }

    public static LocationSettingsStates fromIntent(Intent $r0) throws  {
        return (LocationSettingsStates) zzc.zza($r0, "com.google.android.gms.location.LOCATION_SETTINGS_STATES", CREATOR);
    }

    public int getVersionCode() throws  {
        return this.mVersionCode;
    }

    public boolean isBlePresent() throws  {
        return this.avu;
    }

    public boolean isBleUsable() throws  {
        return this.avr;
    }

    public boolean isGpsPresent() throws  {
        return this.avs;
    }

    public boolean isGpsUsable() throws  {
        return this.avp;
    }

    public boolean isLocationPresent() throws  {
        return this.avs || this.avt;
    }

    public boolean isLocationUsable() throws  {
        return this.avp || this.avq;
    }

    public boolean isNetworkLocationPresent() throws  {
        return this.avt;
    }

    public boolean isNetworkLocationUsable() throws  {
        return this.avq;
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        zzj.zza(this, $r1, $i0);
    }
}
