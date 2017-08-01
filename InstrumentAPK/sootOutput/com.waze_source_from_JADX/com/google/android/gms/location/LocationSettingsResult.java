package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

/* compiled from: dalvik_source_com.waze.apk */
public final class LocationSettingsResult extends AbstractSafeParcelable implements Result {
    public static final Creator<LocationSettingsResult> CREATOR = new zzi();
    private final LocationSettingsStates avo;
    private final Status cp;
    private final int mVersionCode;

    LocationSettingsResult(int $i0, Status $r1, LocationSettingsStates $r2) throws  {
        this.mVersionCode = $i0;
        this.cp = $r1;
        this.avo = $r2;
    }

    public LocationSettingsResult(Status $r1) throws  {
        this(1, $r1, null);
    }

    public LocationSettingsStates getLocationSettingsStates() throws  {
        return this.avo;
    }

    public Status getStatus() throws  {
        return this.cp;
    }

    public int getVersionCode() throws  {
        return this.mVersionCode;
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        zzi.zza(this, $r1, $i0);
    }
}
