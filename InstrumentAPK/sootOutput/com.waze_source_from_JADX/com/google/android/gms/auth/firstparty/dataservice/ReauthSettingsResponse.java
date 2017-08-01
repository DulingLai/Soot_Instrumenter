package com.google.android.gms.auth.firstparty.dataservice;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

/* compiled from: dalvik_source_com.waze.apk */
public class ReauthSettingsResponse extends AbstractSafeParcelable {
    public static final ReauthSettingsResponseCreator CREATOR = new ReauthSettingsResponseCreator();
    public static final String CREDENTIAL_STATUS_ACTIVE = "ACTIVE";
    public static final String CREDENTIAL_STATUS_CONFIGURABLE = "CONFIGURABLE";
    public static final String CREDENTIAL_STATUS_LOCKED = "LOCKED";
    public final PasswordSettings password;
    public final PinSettings pin;
    public final int status;
    final int version;

    public ReauthSettingsResponse(int $i0) throws  {
        this(1, $i0, null, null);
    }

    ReauthSettingsResponse(int $i0, int $i1, PasswordSettings $r1, PinSettings $r2) throws  {
        this.version = $i0;
        this.status = $i1;
        this.password = $r1;
        this.pin = $r2;
    }

    public ReauthSettingsResponse(PasswordSettings $r1, PinSettings $r2) throws  {
        this(1, 0, $r1, $r2);
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        ReauthSettingsResponseCreator.zza(this, $r1, $i0);
    }
}
