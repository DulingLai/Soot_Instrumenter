package com.google.android.gms.auth.firstparty.dataservice;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

/* compiled from: dalvik_source_com.waze.apk */
public class PasswordSettings extends AbstractSafeParcelable {
    public static final PasswordSettingsCreator CREATOR = new PasswordSettingsCreator();
    public final String recoveryUrl;
    public final String status;
    final int version;

    PasswordSettings(int $i0, String $r1, String $r2) throws  {
        this.version = $i0;
        this.status = $r1;
        this.recoveryUrl = $r2;
    }

    public PasswordSettings(String $r1, String $r2) throws  {
        this(2, $r1, $r2);
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        PasswordSettingsCreator.zza(this, $r1, $i0);
    }
}
